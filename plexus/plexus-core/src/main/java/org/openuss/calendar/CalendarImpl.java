// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.calendar;

import java.util.ArrayList;
import java.util.List;

/**
 * @see org.openuss.calendar.Calendar
 */
public class CalendarImpl extends org.openuss.calendar.CalendarBase implements
		org.openuss.calendar.Calendar {
	
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -1219708571206341703L;

	/**
	 * @see org.openuss.calendar.Calendar#addAppointment(org.openuss.calendar.Appointment)
	 */
	public void addAppointment(org.openuss.calendar.Appointment appointment) {
		super.getAppointments().add(appointment);
		appointment.setAssignedCalendar(this);
	}


	/**
	 * @see org.openuss.calendar.Calendar#deleteAppointment(org.openuss.calendar.Appointment)
	 */
	public void deleteAppointment(org.openuss.calendar.Appointment appointment) {
    	this.getAppointments().remove(appointment);
	}

	/**
	 * @see org.openuss.calendar.Calendar#addSerialAppointment(org.openuss.calendar.SerialAppointment)
	 */
	public void addSerialAppointment(
			org.openuss.calendar.SerialAppointment serialAppointment) {
		this.getAppointments().add(serialAppointment);
	}


	/**
	 * @see org.openuss.calendar.Calendar#deleteSerialAppointment(org.openuss.calendar.SerialAppointment)
	 */
	public void deleteSerialAppointment(
			org.openuss.calendar.SerialAppointment serialAppointment) {
		// @todo implement public void
		// deleteSerialAppointment(org.openuss.calendar.SerialAppointment
		// serialAppointment)
		throw new java.lang.UnsupportedOperationException(
				"org.openuss.calendar.Calendar.deleteSerialAppointment(org.openuss.calendar.SerialAppointment serialAppointment) Not implemented!");
	}


	@Override
	public List getNaturalSerialAppointments() {
		ArrayList apps = new ArrayList();
		for(Appointment app : this.getAppointments()){
			if(app instanceof SerialAppointment)
				apps.add(app);
		}
		return apps;
	}


	@Override
	public List getNaturalSingleAppointments() {
		ArrayList apps = new ArrayList();
		for(Appointment app : this.getAppointments()){
			if(!app.isSerial())
				apps.add(app);
		}
		return apps;
	}


	@Override
	public List getSingleAppointments() {
		ArrayList apps = new ArrayList();
		for(Appointment app : this.getAppointments()){
			if(!(app instanceof SerialAppointment))
				apps.add(app);
		}
		return apps;
	}

}