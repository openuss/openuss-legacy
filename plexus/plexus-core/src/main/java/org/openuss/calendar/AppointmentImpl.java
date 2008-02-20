// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.calendar;

/**
 * @see org.openuss.calendar.Appointment
 */
public class AppointmentImpl
    extends org.openuss.calendar.AppointmentBase
	implements org.openuss.calendar.Appointment
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 646855419845183536L;

    /**
     * @see org.openuss.calendar.Appointment#getCalendaType()
     */
    public java.lang.String getSourceCalendarType()
    {
        return this.getSourceCalendar().getCalendarType().getValue();
    }

}