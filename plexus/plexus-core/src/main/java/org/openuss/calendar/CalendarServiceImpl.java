// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.calendar;

import java.util.Date;
import java.sql.Timestamp;

import org.openuss.groups.GroupInfo;
import org.openuss.lecture.CourseInfo;
import org.openuss.security.UserInfo;
/**
 * @see org.openuss.calendar.CalendarService
 */
public class CalendarServiceImpl
    extends org.openuss.calendar.CalendarServiceBase
{

    /**
     * @see org.openuss.calendar.CalendarService#createUserCalendar(org.openuss.security.UserInfo)
     */
    protected void handleCreateUserCalendar(org.openuss.security.UserInfo user)
        throws java.lang.Exception
    {
        // @todo implement protected void handleCreateUserCalendar(org.openuss.security.UserInfo user)
        throw new java.lang.UnsupportedOperationException("org.openuss.calendar.CalendarService.handleCreateUserCalendar(org.openuss.security.UserInfo user) Not implemented!");
    }

    /**
     * @see org.openuss.calendar.CalendarService#createCalendar(org.openuss.foundation.DomainObject)
     */
    protected void handleCreateCalendar(org.openuss.foundation.DomainObject domainObject)
        throws java.lang.Exception
    {
        // @todo implement protected void handleCreateCalendar(org.openuss.foundation.DomainObject domainObject)
        
    	Calendar cal = Calendar.Factory.newInstance();
    	CalendarType calType;
    	Date date = new Date();
    	
    	// check calendarType
    	if (domainObject instanceof CourseInfo) {           
            calType = CalendarType.course_calendar;
    	} else if (domainObject instanceof GroupInfo) {
    		calType = CalendarType.group_calendar;
    	} else if (domainObject instanceof UserInfo) {
    		calType = CalendarType.user_calendar;
    	} else {
    		throw new CalendarApplicationException ("DomainObject is not valid for calendar type.");
    	}
    		
    	
        // TODO use timenow as param for setLastUpdate in handleCreateCalendar
        
        cal.setLastUpdate(new Timestamp(date.getTime()));
        cal.setCalendarType(calType);
        getCalendarDao().create(cal);
    	
    }

    /**
     * @see org.openuss.calendar.CalendarService#deleteCalendar(org.openuss.calendar.CalendarInfo)
     */
    protected void handleDeleteCalendar(org.openuss.calendar.CalendarInfo calender)
        throws java.lang.Exception
    {
        // @todo implement protected void handleDeleteCalendar(org.openuss.calendar.CalendarInfo calender)
        

        
    	throw new java.lang.UnsupportedOperationException("org.openuss.calendar.CalendarService.handleDeleteCalendar(org.openuss.calendar.CalendarInfo calender) Not implemented!");
    }

    /**
     * @see org.openuss.calendar.CalendarService#createAppointment(org.openuss.calendar.AppointmentInfo, org.openuss.calendar.CalendarInfo)
     */
    protected void handleCreateAppointment(org.openuss.calendar.AppointmentInfo singleAppointment, org.openuss.calendar.CalendarInfo calendar)
        throws java.lang.Exception
    {
        // @todo implement protected void handleCreateAppointment(org.openuss.calendar.AppointmentInfo singleAppointment, org.openuss.calendar.CalendarInfo calendar)
        throw new java.lang.UnsupportedOperationException("org.openuss.calendar.CalendarService.handleCreateAppointment(org.openuss.calendar.AppointmentInfo singleAppointment, org.openuss.calendar.CalendarInfo calendar) Not implemented!");
    }

    /**
     * @see org.openuss.calendar.CalendarService#updateAppointment(org.openuss.calendar.AppointmentInfo, org.openuss.calendar.CalendarInfo)
     */
    protected void handleUpdateAppointment(org.openuss.calendar.AppointmentInfo singleAppointment, org.openuss.calendar.CalendarInfo calendar)
        throws java.lang.Exception
    {
        // @todo implement protected void handleUpdateAppointment(org.openuss.calendar.AppointmentInfo singleAppointment, org.openuss.calendar.CalendarInfo calendar)
        throw new java.lang.UnsupportedOperationException("org.openuss.calendar.CalendarService.handleUpdateAppointment(org.openuss.calendar.AppointmentInfo singleAppointment, org.openuss.calendar.CalendarInfo calendar) Not implemented!");
    }

    /**
     * @see org.openuss.calendar.CalendarService#deleteAppointment(org.openuss.calendar.AppointmentInfo, org.openuss.calendar.CalendarInfo)
     */
    protected void handleDeleteAppointment(org.openuss.calendar.AppointmentInfo singleAppointment, org.openuss.calendar.CalendarInfo calendar)
        throws java.lang.Exception
    {
        // @todo implement protected void handleDeleteAppointment(org.openuss.calendar.AppointmentInfo singleAppointment, org.openuss.calendar.CalendarInfo calendar)
        throw new java.lang.UnsupportedOperationException("org.openuss.calendar.CalendarService.handleDeleteAppointment(org.openuss.calendar.AppointmentInfo singleAppointment, org.openuss.calendar.CalendarInfo calendar) Not implemented!");
    }

    /**
     * @see org.openuss.calendar.CalendarService#createSerialAppointment(org.openuss.calendar.SerialAppointmentInfo, org.openuss.calendar.CalendarInfo)
     */
    protected void handleCreateSerialAppointment(org.openuss.calendar.SerialAppointmentInfo serialAppointment, org.openuss.calendar.CalendarInfo calendar)
        throws java.lang.Exception
    {
        // @todo implement protected void handleCreateSerialAppointment(org.openuss.calendar.SerialAppointmentInfo serialAppointment, org.openuss.calendar.CalendarInfo calendar)
        throw new java.lang.UnsupportedOperationException("org.openuss.calendar.CalendarService.handleCreateSerialAppointment(org.openuss.calendar.SerialAppointmentInfo serialAppointment, org.openuss.calendar.CalendarInfo calendar) Not implemented!");
    }

    /**
     * @see org.openuss.calendar.CalendarService#updateSerialAppointment(org.openuss.calendar.SerialAppointmentInfo, org.openuss.calendar.CalendarInfo)
     */
    protected void handleUpdateSerialAppointment(org.openuss.calendar.SerialAppointmentInfo serialAppointment, org.openuss.calendar.CalendarInfo calendar)
        throws java.lang.Exception
    {
        // @todo implement protected void handleUpdateSerialAppointment(org.openuss.calendar.SerialAppointmentInfo serialAppointment, org.openuss.calendar.CalendarInfo calendar)
        throw new java.lang.UnsupportedOperationException("org.openuss.calendar.CalendarService.handleUpdateSerialAppointment(org.openuss.calendar.SerialAppointmentInfo serialAppointment, org.openuss.calendar.CalendarInfo calendar) Not implemented!");
    }

    /**
     * @see org.openuss.calendar.CalendarService#deleteSerialAppointment(org.openuss.calendar.SerialAppointmentInfo, org.openuss.calendar.CalendarInfo)
     */
    protected void handleDeleteSerialAppointment(org.openuss.calendar.SerialAppointmentInfo serialAppointment, org.openuss.calendar.CalendarInfo calendar)
        throws java.lang.Exception
    {
        // @todo implement protected void handleDeleteSerialAppointment(org.openuss.calendar.SerialAppointmentInfo serialAppointment, org.openuss.calendar.CalendarInfo calendar)
        throw new java.lang.UnsupportedOperationException("org.openuss.calendar.CalendarService.handleDeleteSerialAppointment(org.openuss.calendar.SerialAppointmentInfo serialAppointment, org.openuss.calendar.CalendarInfo calendar) Not implemented!");
    }

    /**
     * @see org.openuss.calendar.CalendarService#getCalendar(org.openuss.foundation.DomainObject)
     */
    protected org.openuss.calendar.CalendarInfo handleGetCalendar(org.openuss.foundation.DomainObject domainObject)
        throws java.lang.Exception
    {
        // @todo implement protected org.openuss.calendar.CalendarInfo handleGetCalendar(org.openuss.foundation.DomainObject domainObject)
        return null;
    }

    /**
     * @see org.openuss.calendar.CalendarService#getSinlgeAppointments(org.openuss.calendar.CalendarInfo)
     */
    protected java.util.List handleGetSinlgeAppointments(org.openuss.calendar.CalendarInfo calendar)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleGetSinlgeAppointments(org.openuss.calendar.CalendarInfo calendar)
        return null;
    }

    /**
     * @see org.openuss.calendar.CalendarService#getSerialAppointments(org.openuss.calendar.CalendarInfo)
     */
    protected java.util.List handleGetSerialAppointments(org.openuss.calendar.CalendarInfo calendar)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleGetSerialAppointments(org.openuss.calendar.CalendarInfo calendar)
        return null;
    }

    /**
     * @see org.openuss.calendar.CalendarService#getAllUserAppointments(org.openuss.security.UserInfo)
     */
    protected java.util.List handleGetAllUserAppointments(org.openuss.security.UserInfo userInfo)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleGetAllUserAppointments(org.openuss.security.UserInfo userInfo)
        return null;
    }

    /**
     * @see org.openuss.calendar.CalendarService#getAppointment(java.lang.Long)
     */
    protected org.openuss.calendar.AppointmentInfo handleGetAppointment(java.lang.Long id)
        throws java.lang.Exception
    {
        // @todo implement protected org.openuss.calendar.AppointmentInfo handleGetAppointment(java.lang.Long id)
        return null;
    }

    /**
     * @see org.openuss.calendar.CalendarService#getSerialAppointment(java.lang.Long)
     */
    protected org.openuss.calendar.SerialAppointmentInfo handleGetSerialAppointment(java.lang.Long id)
        throws java.lang.Exception
    {
        // @todo implement protected org.openuss.calendar.SerialAppointmentInfo handleGetSerialAppointment(java.lang.Long id)
        return null;
    }

    /**
     * @see org.openuss.calendar.CalendarService#getUserCalendars(org.openuss.security.UserInfo)
     */
    protected java.util.List handleGetUserCalendars(org.openuss.security.UserInfo userInfo)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleGetUserCalendars(org.openuss.security.UserInfo userInfo)
        return null;
    }

    /**
     * @see org.openuss.calendar.CalendarService#addSubscription(org.openuss.calendar.CalendarInfo, org.openuss.security.UserInfo)
     */
    protected void handleAddSubscription(org.openuss.calendar.CalendarInfo calendarInfo, org.openuss.security.UserInfo userInfo)
        throws java.lang.Exception
    {
        // @todo implement protected void handleAddSubscription(org.openuss.calendar.CalendarInfo calendarInfo, org.openuss.security.UserInfo userInfo)
        throw new java.lang.UnsupportedOperationException("org.openuss.calendar.CalendarService.handleAddSubscription(org.openuss.calendar.CalendarInfo calendarInfo, org.openuss.security.UserInfo userInfo) Not implemented!");
    }

    /**
     * @see org.openuss.calendar.CalendarService#endSubscription(org.openuss.calendar.CalendarInfo, org.openuss.security.UserInfo)
     */
    protected void handleEndSubscription(org.openuss.calendar.CalendarInfo calendarInfo, org.openuss.security.UserInfo userInfo)
        throws java.lang.Exception
    {
        // @todo implement protected void handleEndSubscription(org.openuss.calendar.CalendarInfo calendarInfo, org.openuss.security.UserInfo userInfo)
        throw new java.lang.UnsupportedOperationException("org.openuss.calendar.CalendarService.handleEndSubscription(org.openuss.calendar.CalendarInfo calendarInfo, org.openuss.security.UserInfo userInfo) Not implemented!");
    }

}