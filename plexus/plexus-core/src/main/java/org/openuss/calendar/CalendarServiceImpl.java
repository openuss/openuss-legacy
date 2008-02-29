// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.calendar;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openuss.groups.UserGroupInfo;
import org.openuss.lecture.CourseInfo;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserInfo;

/**
 * @see org.openuss.calendar.CalendarService
 */
public class CalendarServiceImpl extends
		org.openuss.calendar.CalendarServiceBase {

	SecurityService securityService;

	/**
	 * @see org.openuss.calendar.CalendarService#createCalendar(org.openuss.foundation.DomainObject)
	 */
	protected void handleCreateCalendar(
			org.openuss.foundation.DomainObject domainObject)
			throws java.lang.Exception {

		// TODO check wheather the calendar was already created for this domain
		// object
		Calendar cal = Calendar.Factory.newInstance();
		CalendarType calType;
		Date date = new Date();

		// check calendarType
		if (domainObject instanceof CourseInfo) {
			calType = CalendarType.course_calendar;
		} else if (domainObject instanceof UserGroupInfo) {
			calType = CalendarType.group_calendar;
		} else if (domainObject instanceof UserInfo) {
			calType = CalendarType.user_calendar;
		} else {
			throw new CalendarApplicationException(
					"DomainObject is not valid for calendar type.");
		}

		cal.setLastUpdate(new Timestamp(date.getTime()));
		cal.setCalendarType(calType);
		cal.setDomainIdentifier(domainObject.getId());
		getCalendarDao().create(cal);

	}

	/**
	 * @see org.openuss.calendar.CalendarService#createAppointment(org.openuss.calendar.AppointmentInfo,
	 *      org.openuss.calendar.CalendarInfo)
	 */
	protected void handleCreateAppointment(
			org.openuss.calendar.AppointmentInfo appointmentInfo,
			org.openuss.calendar.CalendarInfo calendarInfo)
			throws java.lang.Exception {

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

		if (newApp.getId() == null)
			throw new Exception("Please add Appointment first");

		// load appointment object

		Appointment app = getAppointmentDao().load(newApp.getId());

		if (app.getSourceCalendar().getId() != calendar.getId())
			throw new Exception(
					"Given Calendar is not the source calendar for this appointment");

		// set new data for appointment

		if (newApp.getAppointmentTypeInfo() != null)
			app.setAppointmentType(getAppointmentTypeDao().load(
					newApp.getAppointmentTypeInfo().getId()));
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
	 * @see org.openuss.calendar.CalendarService#deleteAppointment(org.openuss.calendar.AppointmentInfo,
	 *      org.openuss.calendar.CalendarInfo)
	 */
	protected void handleDeleteAppointment(
			org.openuss.calendar.AppointmentInfo singleAppointmentInfo,
			org.openuss.calendar.CalendarInfo calendar)
			throws java.lang.Exception {

		if (singleAppointmentInfo.getId() == null)
			throw new Exception("Appointment does not exist");
		Appointment app = getAppointmentDao().load(
				singleAppointmentInfo.getId());
		if (app.getSourceCalendar().getId() != calendar.getId())
			throw new Exception("Calendar is not source for the appointment");

		// appointment can only be deleted if its not part of a serial
		// appointment
		if (app.isSerial())
			throw new Exception(
					"Cannot delete appointment, it is part of a serial appointment");
		if (app instanceof SerialAppointment)
			throw new Exception(
					"Cannot delete appointment, it is part of a serial appointment");

		// remove associations to all assigned calendars of this appointment
		// (including source calendar)
		app.setSourceCalendar(null);
		Calendar cal = getCalendarDao().load(calendar.getId());
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

		cal.addSerialAppointment(serialAppointment);

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

		// get entities
		Calendar cal = getCalendarDao().load(calendarInfo.getId());

		this.deleteSerialAppointment(serialAppointmentInfo, calendarInfo);

		// set new data

		AppointmentType appType = getAppointmentTypeDao().load(
				serialAppointmentInfo.getAppointmentTypeInfo().getId());

		SerialAppointment newSerialApp = getSerialAppointmentDao().create(
				appType, serialAppointmentInfo.getDescription(),
				serialAppointmentInfo.getEndtime(),
				serialAppointmentInfo.getLocation(),
				serialAppointmentInfo.getRecurrenceEndtime(),
				serialAppointmentInfo.getRecurrencePeriod(),
				serialAppointmentInfo.getRecurrenceType(), true, cal,
				serialAppointmentInfo.getStarttime(),
				serialAppointmentInfo.getSubject(), true);

		// add the serial appointment again with updated data
		this.createSerialAppointment(serialAppointmentInfo, calendarInfo);
	}

	/**
	 * @see org.openuss.calendar.CalendarService#deleteSerialAppointment(org.openuss.calendar.SerialAppointmentInfo,
	 *      org.openuss.calendar.CalendarInfo)
	 */
	protected void handleDeleteSerialAppointment(
			org.openuss.calendar.SerialAppointmentInfo serialAppointmentInfo,
			org.openuss.calendar.CalendarInfo calendarInfo)
			throws java.lang.Exception {

		// create entities from info objects

		Calendar cal = getCalendarDao().load(calendarInfo.getId());
		SerialAppointment serialApp = getSerialAppointmentDao().load(
				serialAppointmentInfo.getId());

		// remove (association) serial appointment and its created single
		// appointments from all calendars
		cal.deleteSerialAppointment(serialApp);

		getCalendarDao().update(cal);

		// update subscribed calendars
		Set<Calendar> subscribedCals = cal.getSubscribedCalendars();
		if (!subscribedCals.isEmpty()) {
			for (Calendar subscribedCal : subscribedCals)
				getCalendarDao().update(subscribedCal);

		}

		// remove associated appointments from the persistent store
		for (Appointment app : serialApp.getAppointments())
			getAppointmentDao().remove(app);

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
		CalendarInfo calInfo = getCalendarDao().toCalendarInfo(cal);
		return calInfo;

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
			serialAppInfos.add(getSerialAppointmentDao().toSerialAppointmentInfo(saIt));
		}
		return serialAppInfos;
	}

	/**
	 * @see org.openuss.calendar.CalendarService#getAppointment(java.lang.Long)
	 */
	protected org.openuss.calendar.AppointmentInfo handleGetAppointment(
			java.lang.Long id) throws java.lang.Exception {
		return (AppointmentInfo) getAppointmentDao().load(
				AppointmentDao.TRANSFORM_APPOINTMENTINFO, id);
	}

	/**
	 * @see org.openuss.calendar.CalendarService#getSerialAppointment(java.lang.Long)
	 */
	protected org.openuss.calendar.SerialAppointmentInfo handleGetSerialAppointment(
			java.lang.Long id) throws java.lang.Exception {
		return (SerialAppointmentInfo) getSerialAppointmentDao().load(
				SerialAppointmentDao.TRANSFORM_SERIALAPPOINTMENTINFO, id);
	}

	/**
	 * @see org.openuss.calendar.CalendarService#addSubscription(org.openuss.calendar.CalendarInfo,
	 *      org.openuss.security.UserInfo)
	 */
	protected void handleAddSubscription(
			org.openuss.calendar.CalendarInfo calendarInfo)
			throws java.lang.Exception {

		User user = getSecurityService().getCurrentUser();

		// add the link between the subscribing and the subscribed
		Calendar calToSubscribe = getCalendarDao().load(calendarInfo.getId());

		Calendar subscribingCal = getCalendarDao().findByDomainIdentifier(
				user.getId());
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

		User user = getSecurityService().getCurrentUser();
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
			throws Exception {
		Calendar cal = getCalendarDao().load(calendarInfo.getId());
		List<Appointment> apps = cal.getNaturalSingleAppointments();
		List<AppointmentInfo> appInfos = new ArrayList<AppointmentInfo>();
		for (Appointment appIt: apps) {
			appInfos.add(getAppointmentDao().toAppointmentInfo(appIt));
		}
		return appInfos;

	}

	@Override
	protected List handleGetSingleAppointments(CalendarInfo calendarInfo)
			throws Exception {
		Calendar cal = getCalendarDao().load(calendarInfo.getId());
		List<Appointment> apps = cal.getSingleAppointments();
		List<AppointmentInfo> appInfos = new ArrayList<AppointmentInfo>();
		for (Appointment appIt : apps) {
			appInfos.add(getAppointmentDao().toAppointmentInfo(appIt));
		}
		return appInfos;

	}

	@Override
	protected List handleGetSubscriptions() throws Exception {
		User user = getSecurityService().getCurrentUser();
		Calendar cal = getCalendarDao().findByDomainIdentifier(user.getId());
		Set<Calendar> subscriptions = cal.getSubscriptions();
		ArrayList<CalendarInfo> subs = new ArrayList<CalendarInfo>();
		// subs.addAll(subscriptions);
		// getCalendarDao().toCalendarInfoCollection(subs);
		for (Calendar calIt : subscriptions) {
			subs.add(getCalendarDao().toCalendarInfo(calIt));
		}
		return subs;
	}

	@Override
	protected void handleAddException(
			SerialAppointmentInfo serialAppointmentInfo,
			AppointmentInfo appointmentInfo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleDeleteException(
			SerialAppointmentInfo serialAppointmentInfo,
			AppointmentInfo appointmentInfo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List handleGetCalculatedAppointments(
			SerialAppointmentInfo serialAppointmentInfo) throws Exception {
		SerialAppointment serialApp = getSerialAppointmentDao().load(serialAppointmentInfo.getId());
		Set<Appointment> calcApps = serialApp.getAppointments();
		List<AppointmentInfo> calAppInfos = new ArrayList<AppointmentInfo>();
		if (!calcApps.isEmpty()) {
			for (Appointment calcAppIt : calcApps) {
				calAppInfos.add(getAppointmentDao().toAppointmentInfo(calcAppIt));
			}
		}
		return calAppInfos;
	}






}