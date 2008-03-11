// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.calendar;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

/**
 * @see org.openuss.calendar.Calendar
 */
public class CalendarImpl extends org.openuss.calendar.CalendarBase implements
		org.openuss.calendar.Calendar {

	public AppointmentDao appointmentDao;

	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -1219708571206341703L;

	/**
	 * @see org.openuss.calendar.Calendar#deleteAppointment(org.openuss.calendar.Appointment)
	 */
	public void deleteAppointment(org.openuss.calendar.Appointment appointment) {

		// remove associations for every calendar to which the appointment is
		// assigned

		// remove from previously owning calendar
		this.getOwnAppointments().remove(appointment);
		
		// remove from assigned calendars
		Set<Calendar> subscribedCals = this.getSubscribedCalendars();
		if (!subscribedCals.isEmpty()) {
			for (Calendar cal : subscribedCals) {
				appointment.getAssignedCalendars().remove(cal);
				cal.getLinkedAppointments().remove(appointment);
			}
		}
	}

	/**
	 * @see org.openuss.calendar.Calendar#deleteSerialAppointment(org.openuss.calendar.SerialAppointment)
	 */
	public void deleteSerialAppointment(
			org.openuss.calendar.SerialAppointment serialAppointment) {
	
		// get associated, created single appointments
		Set<Appointment> appsToDelete = serialAppointment.getAppointments(); 
		
		// delete from subscribed calendars
		
		Set<Calendar> subscribedCals = this.getSubscribedCalendars();
		if (!subscribedCals.isEmpty()) {
			for (Calendar subscribedCal : subscribedCals) {
				// remove linked single appointments
				subscribedCal.getLinkedAppointments().removeAll(appsToDelete);
				// remove the linked serial appointment
				subscribedCal.getLinkedAppointments().remove(serialAppointment);
			}
		}
		
		// delete from source calendar
		this.getOwnAppointments().removeAll(appsToDelete);
		this.getOwnAppointments().remove(serialAppointment);
	}

	@Override
	public List getNaturalSerialAppointments() {
		ArrayList apps = new ArrayList();
		for (Appointment app : this.getOwnAppointments()) {
			if (app instanceof SerialAppointment){
				apps.add(app);
			}
		}
		for (Appointment app : this.getLinkedAppointments()) {
			if (app instanceof SerialAppointment)
				apps.add(app);
		}
		return apps;
	}

	@Override
	public List getNaturalSingleAppointments() {
		ArrayList apps = new ArrayList();

		// get non-serial own appointments, which are not created by a serial
		// appointment

		for (Appointment app : this.getOwnAppointments()) {
			if (!app.isSerial())
				apps.add(app);
		}

		// get non-serial linked appointments, which are not created by a serial
		// appointment

		for (Appointment app : this.getLinkedAppointments()) {
			if (!app.isSerial())
				apps.add(app);
		}

		return apps;
	}

	@Override
	public List getSingleAppointments() {

		ArrayList apps = new ArrayList();

		// get own single appointments (including calculated single appointments
		// from
		// a serial appointment)

		for (Appointment app : this.getOwnAppointments()) {
			if (!(app instanceof SerialAppointment))
				apps.add(app);
		}

		// get linked single appointments (including calculated single
		// appointments from
		// a serial appointment)

		for (Appointment app : this.getLinkedAppointments()) {
			if (!(app instanceof SerialAppointment))
				apps.add(app);
		}
		return apps;
	}

	public void addAppointment(Appointment appointment) {
				
		// add association to the source calendar
		this.getOwnAppointments().add(appointment);
		appointment.setSourceCalendar(this);

		// add associations to the assigned calendar
		Set<Calendar> subscribedCals = this.getSubscribedCalendars();
		if (!subscribedCals.isEmpty()) {
			for (Calendar cal : subscribedCals) {
				cal.getLinkedAppointments().add(appointment);
				appointment.getAssignedCalendars().add(cal);
			}
		}
		
	}

	/**
	 * not used anymore. Functionality is passed to CalendarServiceImpl.handleCreateSerialAppointment
	 */
	public void addSerialAppointment(SerialAppointment serialAppointment) {

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

		Set<Calendar> subscribedCalendars = this.getSubscribedCalendars();
		
		// Manage the natural serial appointment
		
		// set source calendar for the serial appointment
		serialAppointment.setSourceCalendar(this);
		this.getOwnAppointments().add(serialAppointment);
		
		// add the natural serial appointment to the subscribed calendars
		if (!subscribedCalendars.isEmpty()) {
			for (Calendar cal : subscribedCalendars) {
				cal.getLinkedAppointments().add(serialAppointment);
				serialAppointment.getAssignedCalendars().add(cal);
			}
		}
		
		// calculate resulting single appointments
		while (calculatedEnd.compareTo(absoluteEnd) <= 0) {
			// set appointment data

			Appointment app = Appointment.Factory.newInstance();
			app.setAppointmentType(serialAppointment.getAppointmentType());
			app.setSubject(serialAppointment.getSubject());
			app.setDescription(serialAppointment.getDescription());
			app.setEndtime(new Timestamp(calculatedEnd.getTime().getTime()));
			app.setStarttime(new Timestamp(calculatedStart.getTime().getTime()));
			app.setLocation(serialAppointment.getLocation());
			app.setSerial(true);

			// manage association to the created single appointment for source
			// calendar			
			this.addAppointment(app);

			// associate created appointment to the corresponding serial appointment
			app.setSerialAppointment(serialAppointment);
			

			// add created single appointment to every subscribed calendar and manage
			// associations for them
			
			if (!subscribedCalendars.isEmpty()) {
				for (Calendar cal : subscribedCalendars) {
					// add calculated single appointment
					cal.getLinkedAppointments().add(app);
					// add the assigned calendar to the created appointment
					app.getAssignedCalendars().add(cal);					
				}
			}
			
			// set source calendar for the created appointment
			app.setSourceCalendar(this);
			// add appointment to the own appointments
			this.getOwnAppointments().add(app);

			serialAppointment.addSingleAppointment(app);

			// add timespan depending on the recurrencetype

			calculatedStart.add(field, serialAppointment.getRecurrencePeriod());
			calculatedEnd.add(field, serialAppointment.getRecurrencePeriod());
		}


	}

	public AppointmentDao getAppointmentDao() {
		return appointmentDao;
	}

	public void setAppointmentDao(AppointmentDao appointmentDao) {
		this.appointmentDao = appointmentDao;
	}

}