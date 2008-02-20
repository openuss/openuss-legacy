// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.calendar;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.hibernate.sql.Update;
import org.openuss.groups.UserGroupInfo;
import org.openuss.lecture.CourseInfo;
import org.openuss.security.User;
import org.openuss.security.UserInfo;

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
				appointmentInfo.getStarttime(), appointmentInfo.getSubject());

		// add associations to the source and to the assigned calendars

		calendar.addAppointment(app);

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

		SerialAppointment serialAppointment = getSerialAppointmentDao().create(
				getAppointmentTypeDao().load(
						serialAppointmentInfo.getAppointmentTypeInfo().getId()),
				serialAppointmentInfo.getDescription(),
				serialAppointmentInfo.getEndtime(),
				serialAppointmentInfo.getLocation(),
				serialAppointmentInfo.getRecurrenceEndtime(),
				serialAppointmentInfo.getRecurrencePeriod(),
				serialAppointmentInfo.getRecurrenceType(), true, cal,
				serialAppointmentInfo.getStarttime(),
				serialAppointmentInfo.getSubject());

		cal.addSerialAppointment(serialAppointment);

		// make changes persistent for the source calendar
		getCalendarDao().update(cal);

		// make changes persistent for the subscribed calendars
		ArrayList<Calendar> subscribedCals = new ArrayList<Calendar>();
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
				serialAppointmentInfo.getSubject());

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
			throw new Exception("No calendar found for this domain object");
		} else {
			CalendarInfo calInfo = getCalendarDao().toCalendarInfo(cal);
			return calInfo;
		}

	}

	/**
	 * @see org.openuss.calendar.CalendarService#getNaturalSerialAppointments(org.openuss.calendar.CalendarInfo)
	 */
	protected java.util.List handleGetNaturalSerialAppointments(
			org.openuss.calendar.CalendarInfo calendar)
			throws java.lang.Exception {
		Calendar cal = getCalendarDao().load(calendar.getId());
		List apps = cal.getNaturalSerialAppointments();
		getSerialAppointmentDao().toSerialAppointmentInfoCollection(apps);
		return apps;
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
		// @todo implement protected void
		// handleAddSubscription(org.openuss.calendar.CalendarInfo calendarInfo,
		// org.openuss.security.UserInfo userInfo)
		throw new java.lang.UnsupportedOperationException(
				"org.openuss.calendar.CalendarService.handleAddSubscription(org.openuss.calendar.CalendarInfo calendarInfo, org.openuss.security.UserInfo userInfo) Not implemented!");
	}

	/**
	 * @see org.openuss.calendar.CalendarService#endSubscription(org.openuss.calendar.CalendarInfo,
	 *      org.openuss.security.UserInfo)
	 */
	protected void handleEndSubscription(
			org.openuss.calendar.CalendarInfo calendarInfo)
			throws java.lang.Exception {
		// @todo implement protected void
		// handleEndSubscription(org.openuss.calendar.CalendarInfo calendarInfo,
		// org.openuss.security.UserInfo userInfo)
		throw new java.lang.UnsupportedOperationException(
				"org.openuss.calendar.CalendarService.handleEndSubscription(org.openuss.calendar.CalendarInfo calendarInfo, org.openuss.security.UserInfo userInfo) Not implemented!");
	}

	@Override
	protected List handleGetNaturalSingleAppointments(CalendarInfo calendarInfo)
			throws Exception {
		Calendar cal = getCalendarDao().load(calendarInfo.getId());
		List<Appointment> apps = cal.getNaturalSingleAppointments();
		getAppointmentDao().toAppointmentInfoCollection(apps);
		return apps;

	}

	@Override
	protected List handleGetSingleAppointments(CalendarInfo calendarInfo)
			throws Exception {
		Calendar cal = getCalendarDao().load(calendarInfo.getId());
		List<Appointment> apps = cal.getSingleAppointments();
		
		getAppointmentDao().toAppointmentInfoCollection(apps);
		

		return apps;

	}

	/************** TEMP ************ */

}