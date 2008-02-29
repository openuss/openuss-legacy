// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.calendar;

import java.sql.Timestamp;


/**
 * @see org.openuss.calendar.SerialAppointment
 */
public class SerialAppointmentImpl
    extends org.openuss.calendar.SerialAppointmentBase
	implements org.openuss.calendar.SerialAppointment
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 1126431082902617516L;
    
    /**
     * @see org.openuss.calendar.SerialAppointment#deleteSingleAppointments()
     */
    public void deleteSingleAppointment(Appointment appointment)
    {
        // @todo implement public void deleteSingleAppointments()
        throw new java.lang.UnsupportedOperationException("org.openuss.calendar.SerialAppointment.deleteSingleAppointments() Not implemented!");
    }

    /**
     * @see org.openuss.calendar.SerialAppointment#addExceptions(java.util.List)
     */
    public void addExceptions(java.util.List scheduleException)
    {
        // @todo implement public void addExceptions(java.util.List scheduleException)
        throw new java.lang.UnsupportedOperationException("org.openuss.calendar.SerialAppointment.addExceptions(java.util.List scheduleException) Not implemented!");
    }

    /**
     * @see org.openuss.calendar.SerialAppointment#deleteExceptions()
     */
    public void deleteExceptions()
    {
        // @todo implement public void deleteExceptions()
        throw new java.lang.UnsupportedOperationException("org.openuss.calendar.SerialAppointment.deleteExceptions() Not implemented!");
    }

	@Override
	public void addSingleAppointment(Appointment appointment) {
		this.getAppointments().add(appointment);
	}
}