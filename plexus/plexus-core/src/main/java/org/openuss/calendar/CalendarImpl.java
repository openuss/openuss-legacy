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
	 * @see org.openuss.calendar.Calendar#deleteAppointment(org.openuss.calendar.Appointment)
	 */
	public void deleteAppointment(org.openuss.calendar.Appointment appointment) {
    	
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
		for(Appointment app : this.getOwnAppointments()){
			if(app instanceof SerialAppointment)
				apps.add(app);
		}
		for(Appointment app : this.getLinkedAppointments()){
			if(app instanceof SerialAppointment)
				apps.add(app);
		}
		return apps;
	}


	@Override
	public List getNaturalSingleAppointments() {
		ArrayList apps = new ArrayList();
		
		// get non-serial own appointments, which are not created by a serial appointment 
		
		for(Appointment app : this.getOwnAppointments()){
			if(!app.isSerial())
				apps.add(app);
		}
		
		// get non-serial linked appointments, which are not created by a serial appointment
		
		for(Appointment app : this.getLinkedAppointments()){
			if(!app.isSerial())
				apps.add(app);
		}
		
		return apps;
	}


	@Override
	public List getSingleAppointments() {
		
		ArrayList apps = new ArrayList();
		
		// get own single appointments (including calculated single appointments from
		// a serial appointment)
		
		for(Appointment app : this.getOwnAppointments()){
			if(!(app instanceof SerialAppointment))
				apps.add(app);
		}
		
		// get linked single appointments (including calculated single appointments from
		// a serial appointment)
		
		for(Appointment app : this.getLinkedAppointments()){
			if(!(app instanceof SerialAppointment))
				apps.add(app);
		}
		return apps;
	}


	@Override
	public void addLinkedAppointment(Appointment appointment) {
		this.getLinkedAppointments().add(appointment);
		appointment.getAssignedCalendars().add(this);
		
	}


	@Override
	public void addLinkedSerialAppointment(SerialAppointment serialAppointment) {
		
	}


	@Override
	public void addOwnAppointment(Appointment appointment) {
		this.getOwnAppointments().add(appointment);
		appointment.setSourceCalendar(this);
		
	}


	@Override
	public void addOwnSerialAppointment(SerialAppointment serialAppointment) {
		// TODO Auto-generated method stub
		
	}

}