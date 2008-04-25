// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.calendar;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.openuss.framework.utilities.TranslationContext;
import org.openuss.groups.UserGroupInfo;
import org.openuss.internationalisation.TranslationApplicationException;
import org.openuss.internationalisation.TranslationTextInfo;
import org.openuss.lecture.CourseInfo;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @see org.openuss.calendar.CalendarService
 */
public class CalendarServiceImpl extends
		org.openuss.calendar.CalendarServiceBase {

	/**
	 * @see org.openuss.calendar.CalendarService#createCalendar(org.openuss.foundation.DomainObject)
	 */
	protected void handleCreateCalendar(
			org.openuss.foundation.DomainObject domainObject)
			throws java.lang.Exception {
		Validate.notNull(domainObject, "Parameter domainObject must not be null.");

		Calendar calendar = getCalendarDao().findByDomainIdentifier(
				domainObject.getId());
		if (calendar == null || calendar.getId() == 2l) {
			Calendar cal = Calendar.Factory.newInstance();
			CalendarType calType;
			Date date = new Date();

			// check calendarType
			if (domainObject instanceof CourseInfo) {
				calType = CalendarType.course_calendar;
			} else if (domainObject instanceof UserGroupInfo) {
				calType = CalendarType.group_calendar;
			} else if ((domainObject instanceof UserInfo)
					|| (domainObject instanceof User)) {
				calType = CalendarType.user_calendar;
			} else {
				throw new CalendarApplicationException(
						"DomainObject is not valid for calendar type.");
			}

			cal.setLastUpdate(new Timestamp(date.getTime()));
			cal.setCalendarType(calType);
			cal.setDomainIdentifier(domainObject.getId());
			cal = getCalendarDao().create(cal);
			getSecurityService().createObjectIdentity(cal, domainObject.getId());
			if (cal.getCalendarType().equals(CalendarType.user_calendar)) {
				getSecurityService().setPermissions(
						getSecurityService().getUserObject(
								getSecurityService().getCurrentUser()), cal,
						LectureAclEntry.ASSIST);
			}
		}

	}

	/**
	 * @see org.openuss.calendar.CalendarService#createAppointment(org.openuss.calendar.AppointmentInfo,
	 *      org.openuss.calendar.CalendarInfo)
	 */
	protected void handleCreateAppointment(
			org.openuss.calendar.AppointmentInfo appointmentInfo,
			org.openuss.calendar.CalendarInfo calendarInfo)
			throws java.lang.Exception {

		if (appointmentInfo.getStarttime().after(appointmentInfo.getEndtime())) {
			throw new Exception("Duration of appointment is negative");
		}
		Calendar calendar = getCalendarDao().load(calendarInfo.getId());

		AppointmentType appType = getAppointmentTypeDao().load(
				appointmentInfo.getAppointmentTypeInfo().getId());

		Appointment app = getAppointmentDao().create(appType,
				appointmentInfo.getDescription(), appointmentInfo.getEndtime(),
				appointmentInfo.getLocation(), false, calendar,
				appointmentInfo.getStarttime(), appointmentInfo.getSubject(),
				true);

		// add associations to the source and to the assigned calendars

		calendar.addAppointment(app);
		getCalendarDao().update(calendar);

	}

	/**
	 * @see org.openuss.calendar.CalendarService#updateAppointment(org.openuss.calendar.AppointmentInfo,
	 *      org.openuss.calendar.CalendarInfo)
	 */
	protected void handleUpdateAppointment(
			org.openuss.calendar.AppointmentInfo newApp,
			org.openuss.calendar.CalendarInfo calendar)
			throws java.lang.Exception {

		if (newApp.getId() == null) {
			throw new Exception("Please add Appointment first");
		}
		// load appointment object

		Appointment app = getAppointmentDao().load(newApp.getId());

		if (!app.getSourceCalendar().getId().equals(calendar.getId())) {
			throw new Exception(
					"Given Calendar is not the source calendar for this appointment");
		}
		// set new data for appointment

		if (newApp.getAppointmentTypeInfo() != null) {
			app.setAppointmentType(getAppointmentTypeDao().load(
					newApp.getAppointmentTypeInfo().getId()));
		}
		app.setDescription(newApp.getDescription());
		app.setEndtime(newApp.getEndtime());
		app.setStarttime(newApp.getStarttime());
		app.setLocation(newApp.getLocation());
		app.setSubject(newApp.getSubject());
		getAppointmentDao().update(app);

		// update calendar information

		getCalendarDao().load(calendar.getId()).setLastUpdate(
				new Timestamp(new Date().getTime()));

	}

	/**
	 * @see org.openuss.calendar.CalendarService#deleteAppointment(org.openuss.calendar.AppointmentInfo)
	 */
	protected void handleDeleteAppointment(
			org.openuss.calendar.AppointmentInfo singleAppointmentInfo)
			throws java.lang.Exception {

		if (singleAppointmentInfo.getId() == null)
			throw new Exception("Appointment does not exist");
		Appointment app = getAppointmentDao().load(
				singleAppointmentInfo.getId());
		// appointment can only be deleted if its not part of a serial
		// appointment
		if (app.isSerial())
			throw new Exception(
					"Cannot delete appointment, it is part of a serial appointment");
		if (app instanceof SerialAppointment)
			throw new Exception(
					"Cannot delete appointment, it is part of a serial appointment");

		Calendar cal = app.getSourceCalendar();
		// remove associations to all assigned calendars of this appointment
		// (including source calendar)
		app.setSourceCalendar(null);
		cal.deleteAppointment(app);

		// finally remove appointment
		getAppointmentDao().remove(app);
		getCalendarDao().update(cal);
		// update all subscribed Cals
		Collection<Calendar> cals = cal.getSubscribedCalendars();
		getCalendarDao().update(cals);

	}

	/**
	 * @see org.openuss.calendar.CalendarService#createSerialAppointment(org.openuss.calendar.SerialAppointmentInfo,
	 *      org.openuss.calendar.CalendarInfo)
	 */
	protected void handleCreateSerialAppointment(
			org.openuss.calendar.SerialAppointmentInfo serialAppointmentInfo,
			org.openuss.calendar.CalendarInfo calendar)
			throws java.lang.Exception {
		if (serialAppointmentInfo.getStarttime().after(
				serialAppointmentInfo.getEndtime()))
			throw new Exception("Duration of appointment is negative");
		if (serialAppointmentInfo.getEndtime().after(
				serialAppointmentInfo.getRecurrenceEndtime()))
			throw new Exception("Recurrence endtime before first occurence");
		Calendar cal = getCalendarDao().load(calendar.getId());

		SerialAppointment serialAppointment = getSerialAppointmentDao()
				.create(
						getAppointmentTypeDao().load(
								serialAppointmentInfo.getAppointmentTypeInfo()
										.getId()),
						serialAppointmentInfo.getDescription(),
						serialAppointmentInfo.getEndtime(),
						serialAppointmentInfo.getLocation(),
						serialAppointmentInfo.getRecurrenceEndtime(),
						serialAppointmentInfo.getRecurrencePeriod(),
						serialAppointmentInfo.getRecurrenceType(), true, cal,
						serialAppointmentInfo.getStarttime(),
						serialAppointmentInfo.getSubject(), true);

		/** ********************** calculate appointments ********************* */

		GregorianCalendar absoluteEnd = new GregorianCalendar();
		absoluteEnd.setTime(serialAppointment.getRecurrenceEndtime());
		GregorianCalendar calculatedEnd = new GregorianCalendar();
		calculatedEnd.setTime(serialAppointment.getEndtime());
		GregorianCalendar calculatedStart = new GregorianCalendar();
		calculatedStart.setTime(serialAppointment.getStarttime());

		// apply recurrence type to field used by gregorian calendar
		int field = GregorianCalendar.MONTH;
		if (serialAppointment.getRecurrenceType().equals(RecurrenceType.daily)) {
			field = GregorianCalendar.DAY_OF_MONTH;
		} else if (serialAppointment.getRecurrenceType().equals(
				RecurrenceType.weekly)) {
			field = GregorianCalendar.WEEK_OF_YEAR;
		} else if (serialAppointment.getRecurrenceType().equals(
				RecurrenceType.monthly)) {
			field = GregorianCalendar.MONTH;
		} else if (serialAppointment.getRecurrenceType().equals(
				RecurrenceType.yearly)) {
			field = GregorianCalendar.YEAR;
		}

		Set<Calendar> subscribedCalendars = cal.getSubscribedCalendars();

		// Manage the natural serial appointment

		// set source calendar for the serial appointment
		serialAppointment.setSourceCalendar(cal);
		cal.getOwnAppointments().add(serialAppointment);

		// add the natural serial appointment to the subscribed calendars
		if (!subscribedCalendars.isEmpty()) {
			for (Calendar subCal : subscribedCalendars) {
				subCal.getLinkedAppointments().add(serialAppointment);
				serialAppointment.getAssignedCalendars().add(subCal);
			}
		}

		// count number of created appointments
		int appCounter = 0;

		// calculate resulting single appointments
		while (calculatedEnd.compareTo(absoluteEnd) <= 0) {
			appCounter++;
			if (appCounter > 500) {
				throw new CalculatedAppointmentException(
						"Too many calculated appointments");
			}

			// set appointment data

			Appointment app = getAppointmentDao().create(
					serialAppointment.getAppointmentType(),
					serialAppointment.getDescription(),
					calculatedEnd.getTime(), serialAppointment.getLocation(),
					true, cal, calculatedStart.getTime(),
					serialAppointment.getSubject(), true);

			// manage association to the created single appointment for source
			// calendar
			cal.addAppointment(app);

			// associate created appointment to the corresponding serial
			// appointment
			app.setSerialAppointment(serialAppointment);

			// add created single appointment to every subscribed calendar and
			// manage
			// associations for them

			if (!subscribedCalendars.isEmpty()) {
				for (Calendar subCal : subscribedCalendars) {
					// add calculated single appointment
					subCal.getLinkedAppointments().add(app);
					// add the assigned calendar to the created appointment
					app.getAssignedCalendars().add(subCal);
				}
			}

			// set source calendar for the created appointment
			app.setSourceCalendar(cal);
			// add appointment to the own appointments
			cal.getOwnAppointments().add(app);

			serialAppointment.addSingleAppointment(app);

			// add timespan depending on the recurrencetype

			calculatedStart.add(field, serialAppointment.getRecurrencePeriod());
			calculatedEnd.add(field, serialAppointment.getRecurrencePeriod());
		}

		/** ************************************************************** */

		getSerialAppointmentDao().update(serialAppointment);
		// make changes persistent for the source calendar
		getCalendarDao().update(cal);

		// make changes persistent for the subscribed calendars
		Set<Calendar> subscribedCals = cal.getSubscribedCalendars();
		if (!subscribedCals.isEmpty()) {
			for (Calendar subscribedCal : subscribedCals) {
				getCalendarDao().update(subscribedCal);
			}
		}

	}

	/**
	 * @see org.openuss.calendar.CalendarService#updateSerialAppointment(org.openuss.calendar.SerialAppointmentInfo,
	 *      org.openuss.calendar.CalendarInfo)
	 */
	protected void handleUpdateSerialAppointment(
			org.openuss.calendar.SerialAppointmentInfo serialAppointmentInfo,
			org.openuss.calendar.CalendarInfo calendarInfo)
			throws java.lang.Exception {

		if (serialAppointmentInfo.getId() == null)
			throw new Exception("Please add Appointment first");

		SerialAppointment app = getSerialAppointmentDao().load(
				serialAppointmentInfo.getId());

		if (!app.getSourceCalendar().getId().equals(calendarInfo.getId()))
			throw new Exception(
					"Given Calendar is not the source calendar for this appointment");

		this.deleteSerialAppointment(serialAppointmentInfo);

		// add the serial appointment again with updated data
		this.createSerialAppointment(serialAppointmentInfo, calendarInfo);
	}

	/**
	 * @see org.openuss.calendar.CalendarService#deleteSerialAppointment(org.openuss.calendar.SerialAppointmentInfo)
	 */
	protected void handleDeleteSerialAppointment(
			org.openuss.calendar.SerialAppointmentInfo serialAppointmentInfo)
			throws java.lang.Exception {

		// create entities from info objects

		SerialAppointment serialApp = getSerialAppointmentDao().load(
				serialAppointmentInfo.getId());
		Calendar cal = serialApp.getSourceCalendar();

		// remove (association) serial appointment and its created single
		// appointments from all calendars
		cal.deleteSerialAppointment(serialApp);

		getCalendarDao().update(cal);

		// update subscribed calendars
		Set<Calendar> subscribedCals = cal.getSubscribedCalendars();
		if (!subscribedCals.isEmpty()) {
			for (Calendar subscribedCal : subscribedCals) {
				getCalendarDao().update(subscribedCal);
			}
		}

		// remove associated appointments from the persistent store
		for (Appointment app : serialApp.getAppointments()) {
			getAppointmentDao().remove(app);
		}
		// finally remove the serial appointment from the persistent store
		getSerialAppointmentDao().remove(serialApp);
	}

	/**
	 * @see org.openuss.calendar.CalendarService#getCalendar(org.openuss.foundation.DomainObject)
	 */
	protected org.openuss.calendar.CalendarInfo handleGetCalendar(
			org.openuss.foundation.DomainObject domainObject)
			throws java.lang.Exception {

		Calendar cal = getCalendarDao().findByDomainIdentifier(
				domainObject.getId());

		if (cal == null) {
			this.createCalendar(domainObject);
			cal = getCalendarDao().findByDomainIdentifier(domainObject.getId());
		}
		return getCalendarDao().toCalendarInfo(cal);
	}

	/**
	 * @see org.openuss.calendar.CalendarService#getNaturalSerialAppointments(org.openuss.calendar.CalendarInfo)
	 */
	protected java.util.List handleGetNaturalSerialAppointments(
			org.openuss.calendar.CalendarInfo calendar)
			throws java.lang.Exception {
		Calendar cal = getCalendarDao().load(calendar.getId());
		List<SerialAppointment> apps = cal.getNaturalSerialAppointments();
		List<SerialAppointmentInfo> serialAppInfos = new ArrayList<SerialAppointmentInfo>();
		for (SerialAppointment saIt : apps) {
			SerialAppointmentInfo serialApp = (getSerialAppointmentDao()
					.toSerialAppointmentInfo(saIt));
			this.translate(serialApp.getAppointmentTypeInfo());
			serialAppInfos.add(serialApp);

		}
		return serialAppInfos;
	}

	/**
	 * @see org.openuss.calendar.CalendarService#getAppointment(java.lang.Long)
	 */
	protected org.openuss.calendar.AppointmentInfo handleGetAppointment(
			java.lang.Long id) throws java.lang.Exception {

		AppointmentInfo app = (AppointmentInfo) getAppointmentDao().load(
				AppointmentDao.TRANSFORM_APPOINTMENTINFO, id);
		this.translate(app.getAppointmentTypeInfo());
		return app;
	}

	/**
	 * @see org.openuss.calendar.CalendarService#getSerialAppointment(java.lang.Long)
	 */
	protected org.openuss.calendar.SerialAppointmentInfo handleGetSerialAppointment(
			java.lang.Long id) throws java.lang.Exception {
		SerialAppointmentInfo serialApp = (SerialAppointmentInfo) getSerialAppointmentDao()
				.load(SerialAppointmentDao.TRANSFORM_SERIALAPPOINTMENTINFO, id);
		this.translate(serialApp.getAppointmentTypeInfo());
		return serialApp;
	}

	/**
	 * @see org.openuss.calendar.CalendarService#addSubscription(org.openuss.calendar.CalendarInfo,
	 *      org.openuss.security.UserInfo)
	 */
	protected void handleAddSubscription(
			org.openuss.calendar.CalendarInfo calendarInfo)
			throws java.lang.Exception {

		User user = getSecurityService().getUserObject(
				getSecurityService().getCurrentUser());

		// add the link between the subscribing and the subscribed
		Calendar calToSubscribe = getCalendarDao().load(calendarInfo.getId());

		Calendar subscribingCal = getCalendarDao().findByDomainIdentifier(
				user.getId());

		// test if the user wants to subscribe his own calendar
		if (subscribingCal.equals(calToSubscribe))
			throw new CalendarApplicationException(
					"It is not possible to subscribe to the own calendar!");
		calToSubscribe.getSubscribedCalendars().add(subscribingCal);
		subscribingCal.getSubscriptions().add(calToSubscribe);

		// add already existing single appointments (including apps generated
		// from serial app)

		// get single apps from subscribed calendar
		List<Appointment> appsToAdd = calToSubscribe.getSingleAppointments();

		for (Appointment app : appsToAdd) {
			subscribingCal.getLinkedAppointments().add(app);
			app.getAssignedCalendars().add(subscribingCal);
		}

		// add natural serial appointments
		List<SerialAppointment> serialApps = calToSubscribe
				.getNaturalSerialAppointments();

		for (SerialAppointment serialApp : serialApps) {
			subscribingCal.getLinkedAppointments().add(serialApp);
			serialApp.getAssignedCalendars().add(subscribingCal);
		}

		getCalendarDao().update(subscribingCal);
		getCalendarDao().update(calToSubscribe);

	}

	/**
	 * @see org.openuss.calendar.CalendarService#endSubscription(org.openuss.calendar.CalendarInfo,
	 *      org.openuss.security.UserInfo)
	 */
	protected void handleEndSubscription(
			org.openuss.calendar.CalendarInfo calendarInfo)
			throws java.lang.Exception {

		User user = getSecurityService().getUserObject(
				getSecurityService().getCurrentUser());
		Calendar parentCal = getCalendarDao().load(calendarInfo.getId());
		Calendar childCal = getCalendarDao().findByDomainIdentifier(
				user.getId());

		// remove single appointments
		List<Appointment> singleApps = parentCal.getSingleAppointments();
		for (Appointment appIt : singleApps) {
			childCal.getLinkedAppointments().remove(appIt);
			appIt.getAssignedCalendars().remove(childCal);
		}

		// remove natural serial appointments
		List<SerialAppointment> serialApps = parentCal
				.getNaturalSerialAppointments();
		for (SerialAppointment serialAppIt : serialApps) {
			childCal.getLinkedAppointments().remove(serialAppIt);
			serialAppIt.getAssignedCalendars().remove(childCal);
		}

		// remove association between calendars
		parentCal.getSubscribedCalendars().remove(childCal);
		childCal.getSubscriptions().remove(parentCal);

		// update all involved calendars
		getCalendarDao().update(parentCal);
		getCalendarDao().update(childCal);

	}

	@Override
	protected List handleGetNaturalSingleAppointments(CalendarInfo calendarInfo)
			throws TranslationApplicationException {
		Calendar cal = getCalendarDao().load(calendarInfo.getId());
		List<Appointment> apps = cal.getNaturalSingleAppointments();
		List<AppointmentInfo> appInfos = new ArrayList<AppointmentInfo>();
		for (Appointment appIt : apps) {
			AppointmentInfo appInfo = getAppointmentDao().toAppointmentInfo(
					appIt);
			this.translate(appInfo.getAppointmentTypeInfo());
			appInfos.add(appInfo);
		}
		this.sortAppointmentInfoList(appInfos);
		return appInfos;

	}

	@Override
	protected List handleGetSingleAppointments(CalendarInfo calendarInfo)
			throws TranslationApplicationException {
		Calendar cal = getCalendarDao().load(calendarInfo.getId());
		List<Appointment> apps = cal.getSingleAppointments();
		List<AppointmentInfo> appInfos = new ArrayList<AppointmentInfo>();
		for (Appointment appIt : apps) {
			AppointmentInfo appInfo = getAppointmentDao().toAppointmentInfo(
					appIt);
			this.translate(appInfo.getAppointmentTypeInfo());
			appInfos.add(appInfo);
		}
		return appInfos;

	}

	@Override
	protected List handleGetSubscriptions() throws CalendarApplicationException {
		User user = getSecurityService().getUserObject(
				getSecurityService().getCurrentUser());
		Calendar cal = getCalendarDao().findByDomainIdentifier(user.getId());
		if (cal == null) {
			this.createCalendar(getUserDao().toUserInfo(user));
			cal = getCalendarDao().findByDomainIdentifier(user.getId());
		}
		Set<Calendar> subscriptions = cal.getSubscriptions();
		ArrayList<CalendarInfo> subs = new ArrayList<CalendarInfo>();
		// subs.addAll(subscriptions);
		// getCalendarDao().toCalendarInfoCollection(subs);
		for (Calendar calIt : subscriptions) {
			CalendarInfo calInfo = getCalendarDao().toCalendarInfo(calIt);
			if (calIt.getCalendarType().equals(CalendarType.course_calendar)) {
				CourseInfo course = getCourseService().findCourse(
						calIt.getDomainIdentifier());
				calInfo.setCalendarOwnerName(course.getName() + " ("
						+ course.getShortcut() + ")");
			}
			if (calIt.getCalendarType().equals(CalendarType.group_calendar)) {
				UserGroupInfo group = getGroupService().getGroupInfo(
						calIt.getDomainIdentifier());
				calInfo.setCalendarOwnerName(group.getName() + " ("
						+ group.getShortcut() + ")");
			}
			// this should never happen, a user should currently not be able to
			// subscribe to another users calendar.
			if (calIt.getCalendarType().equals(CalendarType.user_calendar)) {
				User userCalendar = getSecurityService().getUserObject(
						getSecurityService().getUser(
								calIt.getDomainIdentifier()));
				calInfo.setCalendarOwnerName(userCalendar.getDisplayName());
			}
			subs.add(calInfo);

		}
		return subs;
	}

	@Override
	protected void handleAddException(
			SerialAppointmentInfo serialAppointmentInfo,
			AppointmentInfo appointmentInfo) throws Exception {

		SerialAppointment serialApp = getSerialAppointmentDao().load(
				serialAppointmentInfo.getId());
		Appointment app = getAppointmentDao().load(appointmentInfo.getId());
		Set<Appointment> calculatedApps = serialApp.getAppointments();

		if (!calculatedApps.contains(app))
			throw new CalendarApplicationException(
					"Appointment does not belong to serial appointment");

		// update appointment
		app.setTakingPlace(false);
		getAppointmentDao().update(app);

		// remove appointment from source calendar
		Calendar sourceCal = serialApp.getSourceCalendar();
		sourceCal.getOwnAppointments().remove(app);
		getCalendarDao().update(sourceCal);

		// remove appointment from assigned calendars
		Set<Calendar> assignedCals = serialApp.getAssignedCalendars();
		for (Calendar calIt : assignedCals) {
			calIt.getLinkedAppointments().remove(app);
			getCalendarDao().update(calIt);
		}

	}

	@Override
	protected void handleDeleteException(
			SerialAppointmentInfo serialAppointmentInfo,
			AppointmentInfo appointmentInfo) throws Exception {

		SerialAppointment serialApp = getSerialAppointmentDao().load(
				serialAppointmentInfo.getId());
		Appointment app = getAppointmentDao().load(appointmentInfo.getId());
		Set<Appointment> calcApps = serialApp.getAppointments();
		if (!calcApps.contains(app))
			throw new CalendarApplicationException(
					"Appointment does not belong the serial appointment.");

		app.setTakingPlace(true);
		getAppointmentDao().update(app);

		Calendar sourceCal = app.getSourceCalendar();
		sourceCal.getOwnAppointments().add(app);
		getCalendarDao().update(sourceCal);

		Set<Calendar> assignedCals = app.getAssignedCalendars();
		for (Calendar calIt : assignedCals) {
			calIt.getLinkedAppointments().add(app);
			getCalendarDao().update(calIt);
		}

	}

	@Override
	protected List handleGetCalculatedAppointments(
			SerialAppointmentInfo serialAppointmentInfo) throws Exception {
		SerialAppointment serialApp = getSerialAppointmentDao().load(
				serialAppointmentInfo.getId());
		Set<Appointment> calcApps = serialApp.getAppointments();
		List<AppointmentInfo> calAppInfos = new ArrayList<AppointmentInfo>();
		if (!calcApps.isEmpty()) {
			for (Appointment calcAppIt : calcApps) {
				AppointmentInfo calcApp = getAppointmentDao()
						.toAppointmentInfo(calcAppIt);
				this.translate(calcApp.getAppointmentTypeInfo());
				calAppInfos.add(calcApp);
			}
		}
		return calAppInfos;
	}

	@Override
	protected void handleCreateAppointmentType(
			AppointmentTypeInfo appointmentTypeInfo) throws Exception {
		// check if an apptype with the same name already exists
		AppointmentType existingAppType = getAppointmentTypeDao().findByName(
				appointmentTypeInfo.getName());
		if (existingAppType != null)
			throw new CalendarApplicationException(
					"AppointmentType with the same name is already existing");
		AppointmentType appType = getAppointmentTypeDao().create(
				appointmentTypeInfo.getName());
		TranslationTextInfo translation = new TranslationTextInfo();
		translation.setDomainIdentifier(appType.getId());
		translation.setSubKey(appType.getName());
		translation.setText(appType.getName());
		getTranslationService().addTranslationText(translation, "en");
	}

	@Override
	protected void handleDeleteAppointmentType(
			AppointmentTypeInfo appointmentTypeInfoToDelete,
			AppointmentTypeInfo substitutingAppointmentTypeInfo)
			throws Exception {

		AppointmentType appTypeToDelete = getAppointmentTypeDao().load(
				appointmentTypeInfoToDelete.getId());
		AppointmentType substitutingAppType = getAppointmentTypeDao().load(
				substitutingAppointmentTypeInfo.getId());

		if ((appTypeToDelete == null) || (substitutingAppType == null))
			throw new TranslationApplicationException(
					"One or more appointment types are not found");

		// get appointments associated to the appointment to delete
		Set<Appointment> apps = appTypeToDelete.getAppointments();
		// remove the association to the "old" apptype and add the substitung
		// app type
		for (Appointment appIt : apps) {
			appIt.setAppointmentType(substitutingAppType);
			substitutingAppType.getAppointments().add(appIt);
		}
		// remove the translations of the appointmenttype
		getTranslationService().removeTranslationTexts(
				appointmentTypeInfoToDelete.getId());
		getAppointmentTypeDao().remove(appTypeToDelete);

	}

	@Override
	protected List handleGetAllAppointmentTypes() throws Exception {
		List<AppointmentType> appTypes = (List) getAppointmentTypeDao()
				.loadAll();
		List<AppointmentTypeInfo> appTypeInfos = new ArrayList<AppointmentTypeInfo>();
		for (AppointmentType appTypeIt : appTypes) {
			appTypeInfos.add(getAppointmentTypeDao().toAppointmentTypeInfo(
					appTypeIt));
		}
		return appTypeInfos;
	}

	@Override
	protected void handleUpdateAppointmentType(
			AppointmentTypeInfo appointmentTypeInfo) throws Exception {
		AppointmentType appType = getAppointmentTypeDao().load(
				appointmentTypeInfo.getId());
		appType.setName(appointmentTypeInfo.getName());
		getAppointmentTypeDao().update(appType);

	}

	@Override
	protected List handleGetSingleAppointmentsAfterDate(Timestamp date,
			CalendarInfo calendarInfo) throws Exception {

		Calendar cal = getCalendarDao().load(calendarInfo.getId());
		List<Appointment> apps = cal.getSingleAppointments();
		List<AppointmentInfo> appsAfterDate = new ArrayList<AppointmentInfo>();
		for (Appointment appIt : apps) {
			if (appIt.getEndtime().after(date)) {
				AppointmentInfo appInfo = getAppointmentDao()
						.toAppointmentInfo(appIt);
				this.translate(appInfo.getAppointmentTypeInfo());
				appsAfterDate.add(appInfo);
			}
		}
		this.sortAppointmentInfoList(appsAfterDate);
		return appsAfterDate;
	}

	@Override
	protected List handleGetUpcomingSingleAppointments(CalendarInfo calendarInfo)
			throws Exception {
		// use given method with time now
		return this.getSingleAppointmentsAfterDate(new Timestamp(System
				.currentTimeMillis()), calendarInfo);
	}

	@Override
	protected void handleAddSubscriptionForUser(CalendarInfo calendarInfo,
			Long userId) throws Exception {

		User user = getUserDao().load(userId);

		// add the link between the subscribing and the subscribed
		Calendar calToSubscribe = getCalendarDao().load(calendarInfo.getId());

		Calendar subscribingCal = getCalendarDao().findByDomainIdentifier(
				user.getId());

		// test if the user wants to subscribe his own calendar
		if (subscribingCal.equals(calToSubscribe))
			throw new CalendarApplicationException(
					"It is not possible to subscribe to the own calendar!");
		calToSubscribe.getSubscribedCalendars().add(subscribingCal);
		subscribingCal.getSubscriptions().add(calToSubscribe);

		// add already existing single appointments (including apps generated
		// from serial app)

		// get single apps from subscribed calendar
		List<Appointment> appsToAdd = calToSubscribe.getSingleAppointments();

		for (Appointment app : appsToAdd) {
			subscribingCal.getLinkedAppointments().add(app);
			app.getAssignedCalendars().add(subscribingCal);
		}

		// add natural serial appointments
		List<SerialAppointment> serialApps = calToSubscribe
				.getNaturalSerialAppointments();

		for (SerialAppointment serialApp : serialApps) {
			subscribingCal.getLinkedAppointments().add(serialApp);
			serialApp.getAssignedCalendars().add(subscribingCal);
		}

		getCalendarDao().update(subscribingCal);
		getCalendarDao().update(calToSubscribe);
	}

	private void translate(AppointmentTypeInfo appTI)
			throws TranslationApplicationException {
		appTI
				.setName(getTranslationService().getTranslation(
						appTI.getId(),
						appTI.getName(), "de"));
						// TODO: CORRECT THIS WORKAROUND!!! 
						// TranslationContext.getCurrentInstance().getLocale().toString()));
	}

	private void sortAppointmentInfoList(List<AppointmentInfo> list) {
		Collections.sort(list, new AppointmentInfoComparator());
	}

	/**
	 * Comparator for List of InternalMessageInfo objects. Sorts by message-date
	 */
	private class AppointmentInfoComparator implements
			java.util.Comparator<AppointmentInfo> {

		public int compare(AppointmentInfo o1, AppointmentInfo o2) {
			if (o1.getStarttime().before(o2.getStarttime()))
				return -1;
			if (o2.getStarttime().before(o1.getStarttime()))
				return 1;
			return 0;
		}
	}

	@Override
	protected SerialAppointmentInfo handleGetAssigendSerialAppointment(
			AppointmentInfo appointmentInfo) throws Exception {
		Appointment app = getAppointmentDao().load(appointmentInfo.getId());
		SerialAppointment serialApp = app.getSerialAppointment();
		if (serialApp == null) {
			throw new CalendarApplicationException(
					"No serialappointment for this appointment");
		} else {
			SerialAppointmentInfo serialAppInfo = getSerialAppointmentDao()
					.toSerialAppointmentInfo(serialApp);
			return serialAppInfo;
		}
	}

}