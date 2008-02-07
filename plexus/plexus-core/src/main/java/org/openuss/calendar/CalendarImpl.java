// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.calendar;

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
		super.getSingleAppointments().add(appointment);
		appointment.setAssignedCalendar(this);
	}

	/**
	 * @see org.openuss.calendar.Calendar#updateAppointment(org.openuss.calendar.Appointment)
	 */
	public void updateAppointment(org.openuss.calendar.Appointment appointment) {
		// @todo implement public void
		// updateAppointment(org.openuss.calendar.Appointment appointment)
		throw new java.lang.UnsupportedOperationException(
				"org.openuss.calendar.Calendar.updateAppointment(org.openuss.calendar.Appointment appointment) Not implemented!");
	}

	/**
	 * @see org.openuss.calendar.Calendar#deleteAppointment(org.openuss.calendar.Appointment)
	 */
	public void deleteAppointment(org.openuss.calendar.Appointment appointment) {
		// @todo implement public void
		// deleteAppointment(org.openuss.calendar.Appointment appointment)
		throw new java.lang.UnsupportedOperationException(
				"org.openuss.calendar.Calendar.deleteAppointment(org.openuss.calendar.Appointment appointment) Not implemented!");
	}

	/**
	 * @see org.openuss.calendar.Calendar#addSerialAppointment(org.openuss.calendar.SerialAppointment)
	 */
	public void addSerialAppointment(
			org.openuss.calendar.SerialAppointment serialAppointment) {
		// @todo implement public void
		// addSerialAppointment(org.openuss.calendar.SerialAppointment
		// serialAppointment)
		throw new java.lang.UnsupportedOperationException(
				"org.openuss.calendar.Calendar.addSerialAppointment(org.openuss.calendar.SerialAppointment serialAppointment) Not implemented!");
	}

	/**
	 * @see org.openuss.calendar.Calendar#updateSerialAppointment(org.openuss.calendar.SerialAppointment)
	 */
	public void updateSerialAppointment(
			org.openuss.calendar.SerialAppointment serialAppointment) {
		// @todo implement public void
		// updateSerialAppointment(org.openuss.calendar.SerialAppointment
		// serialAppointment)
		throw new java.lang.UnsupportedOperationException(
				"org.openuss.calendar.Calendar.updateSerialAppointment(org.openuss.calendar.SerialAppointment serialAppointment) Not implemented!");
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

	/**
	 * @see org.openuss.calendar.Calendar#getAllAppointmentsAsList()
	 */
	public java.util.List getAllAppointmentsAsList() {
		// @todo implement public java.util.List getAllAppointmentsAsList()
		return null;
	}

}