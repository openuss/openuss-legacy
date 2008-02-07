// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

import org.openuss.groups.GroupInfo;
import org.openuss.lecture.CourseInfo;
import org.openuss.security.*;
/**
 * @see org.openuss.calendar.CalendarService
 */
public class CalendarServiceImpl
    extends org.openuss.calendar.CalendarServiceBase
{
    /**
     * @see org.openuss.calendar.CalendarService#createUserCalendar(org.openuss.security.UserInfo)
     */
    protected void handleCreateUserCalendar(org.openuss.security.UserInfo userInfo)
        throws java.lang.Exception
    {
    	super.createCalendar(userInfo);    	
    }

    /**
     * @see org.openuss.calendar.CalendarService#createCalendar(org.openuss.foundation.DomainObject)
     */
    protected void handleCreateCalendar(org.openuss.foundation.DomainObject domainObject)
        throws java.lang.Exception
    {
        
    	// TODO check wheather the calendar was already created for this domain object 
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
    		//set owner
    		User owner = getUserDao().load(domainObject.getId());
    		cal.setCalendarOwner(owner);
    		owner.getCalendars().add(cal);
    	} else {
    		throw new CalendarApplicationException ("DomainObject is not valid for calendar type.");
    	}
    	
        cal.setLastUpdate(new Timestamp(date.getTime()));
        cal.setCalendarType(calType);
        cal.setDomainIdentifier(domainObject.getId());
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
    protected void handleCreateAppointment(org.openuss.calendar.AppointmentInfo appointmentInfo, org.openuss.calendar.CalendarInfo calendarInfo)
        throws java.lang.Exception
    {
    	Calendar calendar = getCalendarDao().load(calendarInfo.getId());
    	User creator = null;
    	if(appointmentInfo.getCreator()!=null){
    		creator = getUserDao().load(appointmentInfo.getCreator().getId());
    	}
    	Appointment appointment = getAppointmentDao().create(getAppointmentTypeDao().load(appointmentInfo.getAppointmentType().getId()), calendar, creator, appointmentInfo.getDescription(), appointmentInfo.getEndtime(), false, appointmentInfo.getLocation(), appointmentInfo.getStarttime(), appointmentInfo.getSubject(), appointmentInfo.getTimeZone());
    	calendar.addAppointment(appointment);
    }

    /**
     * @see org.openuss.calendar.CalendarService#updateAppointment(org.openuss.calendar.AppointmentInfo, org.openuss.calendar.CalendarInfo)
     */
    protected void handleUpdateAppointment(org.openuss.calendar.AppointmentInfo newApp, org.openuss.calendar.CalendarInfo calendar)
        throws java.lang.Exception
    {
    	if(newApp.getId()==null)
    		throw new Exception("Please add Appointment first");
    	Appointment app = getAppointmentDao().load(newApp.getId());
    	if (app.getAssignedCalendar().getId()!=calendar.getId())
    		throw new Exception("Calendar/Appointment do not fit");
    	if(newApp.getAppointmentType()!=null)
    		app.setAppointmentType(getAppointmentTypeDao().load(newApp.getAppointmentType().getId()));
    	if(newApp.getCreator()!=null)
    		app.setCreator(getUserDao().load(newApp.getCreator().getId()));
    	app.setDescription(newApp.getDescription());
    	app.setEndtime(newApp.getEndtime());
    	app.setStarttime(newApp.getStarttime());
    	app.setLocation(newApp.getLocation());
    	app.setSubject(newApp.getSubject());
    	app.setTimezone(newApp.getTimeZone());
    	//TODO check whether calendar.lastUpdate should be updated!
    	getAppointmentDao().update(app);
    }

    /**
     * @see org.openuss.calendar.CalendarService#deleteAppointment(org.openuss.calendar.AppointmentInfo, org.openuss.calendar.CalendarInfo)
     */
    protected void handleDeleteAppointment(org.openuss.calendar.AppointmentInfo singleAppointment, org.openuss.calendar.CalendarInfo calendar)
        throws java.lang.Exception
    {
    	if(singleAppointment.getId()==null)
    		throw new Exception("Appointment does not exist");
    	Appointment app = getAppointmentDao().load(singleAppointment.getId());
    	if(app.getAssignedCalendar().getId()!=calendar.getId())
    		throw new Exception("Calendar/Appointment do not fit");
    	if(app.isIsSerial())
    		throw new Exception("Cannot delete appointment, is part of a serial appointment");
    	if(app instanceof SerialAppointment)
    		throw new Exception("Cannot delete appointment, is part of a serial appointment");
    	app.setAssignedCalendar(null);
    	Calendar cal = getCalendarDao().load(calendar.getId());
    	cal.getSingleAppointments().remove(app);
    	getAppointmentDao().remove(app);
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
    	Calendar cal = getCalendarDao().findByDomainIdentifier(domainObject.getId());

        if (cal == null) {
        	System.out.println("calendar is null");
        }
        CalendarInfo calInfo = getCalendarDao().toCalendarInfo(cal);
        
        return calInfo;
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
    	User user = getUserDao().load(userInfo.getId());
    	ArrayList appointments = new ArrayList();
    	for(Calendar cal : user.getCalendars()){
    		ArrayList appointmentInfos = new ArrayList();
    		appointmentInfos.addAll(cal.getSingleAppointments());
    		getAppointmentDao().toAppointmentInfoCollection(appointmentInfos);
    		appointments.addAll(appointmentInfos);
    	}
    	return appointments;
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
    	//@ todo check with gerrit busse what the target of getUserCalendars is!?!
    	ArrayList cal = new ArrayList();
    	cal.add(getCalendarDao().findByDomainIdentifier(userInfo.getId()));
    	System.out.println(cal.get(0).getClass());
        return cal;
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

	@Override
	protected List handleGetSingleAppointments(CalendarInfo calendar)
			throws Exception {
		Calendar cal = getCalendarDao().load(calendar.getId());
		List apps = new ArrayList();
		apps.addAll(cal.getSingleAppointments());
		getAppointmentDao().toAppointmentInfoCollection(apps);
		return apps;
	}
}