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

import org.openuss.groups.UserGroupInfo;
import org.openuss.lecture.CourseInfo;
import org.openuss.security.User;
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
    	} else if (domainObject instanceof UserGroupInfo) {
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
    	Appointment appointment = getAppointmentDao().create(getAppointmentTypeDao().load(appointmentInfo.getAppointmentType().getId()), calendar, creator, appointmentInfo.getDescription(), appointmentInfo.getEndtime(), appointmentInfo.getLocation(), false, appointmentInfo.getStarttime(), appointmentInfo.getSubject());
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
    	getCalendarDao().load(calendar.getId()).setLastUpdate(new Timestamp(new Date().getTime()));
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
    	if(app.isSerial())
    		throw new Exception("Cannot delete appointment, is part of a serial appointment");
    	if(app instanceof SerialAppointment)
    		throw new Exception("Cannot delete appointment, is part of a serial appointment");
    	app.setAssignedCalendar(null);
    	Calendar cal = getCalendarDao().load(calendar.getId());
    	cal.deleteAppointment(app);
    	getAppointmentDao().remove(app);
    }

    /**
     * @see org.openuss.calendar.CalendarService#createSerialAppointment(org.openuss.calendar.SerialAppointmentInfo, org.openuss.calendar.CalendarInfo)
     */
    protected void handleCreateSerialAppointment(org.openuss.calendar.SerialAppointmentInfo serialAppointmentInfo, org.openuss.calendar.CalendarInfo calendar)
        throws java.lang.Exception
    {
    	if(serialAppointmentInfo.getStarttime().after(serialAppointmentInfo.getEndtime()))
    		throw new Exception("Duration of appointment is negative");
    	if(serialAppointmentInfo.getEndtime().after(serialAppointmentInfo.getRecurrenceEndtime()))
    		throw new Exception("Recurrence endtime before first occurence");
    	Calendar cal = getCalendarDao().load(calendar.getId());
    	SerialAppointment serialAppointment = getSerialAppointmentDao().create(getAppointmentTypeDao().load(serialAppointmentInfo.getAppointmentType().getId()), cal, getUserDao().load(serialAppointmentInfo.getCreator().getId()), serialAppointmentInfo.getDescription(), serialAppointmentInfo.getEndtime(), serialAppointmentInfo.getLocation(), serialAppointmentInfo.getRecurrenceEndtime(), serialAppointmentInfo.getRecurrencePeriod(), serialAppointmentInfo.getRecurrenceType(), true, serialAppointmentInfo.getStarttime(), serialAppointmentInfo.getSubject());
    	cal.addSerialAppointment(serialAppointment);
     	GregorianCalendar absoluteEnd = new GregorianCalendar();
     	absoluteEnd.setTime(serialAppointment.getRecurrenceEndtime());
     	GregorianCalendar calculatedEnd = new GregorianCalendar();
     	calculatedEnd.setTime(serialAppointment.getEndtime());
     	GregorianCalendar calculatedStart = new GregorianCalendar();
     	calculatedStart.setTime(serialAppointment.getStarttime());
     	int field = GregorianCalendar.MONTH;
     	if(serialAppointment.getRecurrenceType().equals(RecurrenceType.daily)){
     		field = GregorianCalendar.DAY_OF_MONTH;
     	} else if(serialAppointment.getRecurrenceType().equals(RecurrenceType.weekly)){
     		field = GregorianCalendar.WEEK_OF_YEAR;
     	} else if(serialAppointment.getRecurrenceType().equals(RecurrenceType.monthly)){
     		field = GregorianCalendar.MONTH;
     	} else if(serialAppointment.getRecurrenceType().equals(RecurrenceType.yearly)){
     		field = GregorianCalendar.YEAR;
     	}
     	 while(calculatedEnd.compareTo(absoluteEnd) <= 0){
     		//TODO Logger!
     		 System.out.println("Generate Appointment "
 					+ calculatedStart.getTime().toGMTString() + " to "
 					+ calculatedEnd.getTime().toGMTString());
     		Appointment app = new AppointmentImpl();
     		app.setAppointmentType(serialAppointment.getAppointmentType());
     		app.setCreator(serialAppointment.getCreator());
     		app.setSubject(serialAppointment.getSubject());
     		app.setDescription(serialAppointment.getDescription());
     		app.setAssignedCalendar(serialAppointment.getAssignedCalendar());
     		app.setEndtime(new Timestamp(calculatedEnd.getTime().getTime()));
     		app.setStarttime(new Timestamp(calculatedStart.getTime().getTime()));    		
     		app.setSerialAppointment(serialAppointment);
     		app.setLocation(serialAppointment.getLocation());
     		getAppointmentDao().create(app);
     		serialAppointment.addSingleAppointment(app);
     		calculatedStart.add(field, serialAppointment.getRecurrencePeriod());
     		calculatedEnd.add(field, serialAppointment.getRecurrencePeriod());
     	}
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
        Calendar cal = getCalendarDao().findByDomainIdentifier(domainObject.getId());

        if (cal == null) {
        	System.out.println("calendar is null");
        }
        CalendarInfo calInfo = getCalendarDao().toCalendarInfo(cal);
        
        return calInfo;
    }


    /**
     * @see org.openuss.calendar.CalendarService#getNaturalSerialAppointments(org.openuss.calendar.CalendarInfo)
     */
    protected java.util.List handleGetNaturalSerialAppointments(org.openuss.calendar.CalendarInfo calendar)
        throws java.lang.Exception
    {
    	Calendar cal = getCalendarDao().load(calendar.getId());
    	List apps = cal.getNaturalSerialAppointments();
    	getSerialAppointmentDao().toSerialAppointmentInfoCollection(apps);
    	return apps;
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
    		appointments.addAll(cal.getSingleAppointments());
    	}
    	getAppointmentDao().toAppointmentInfoCollection(appointments);
    	return appointments;
    }

    /**
     * @see org.openuss.calendar.CalendarService#getAppointment(java.lang.Long)
     */
    protected org.openuss.calendar.AppointmentInfo handleGetAppointment(java.lang.Long id)
        throws java.lang.Exception
    {
        return (AppointmentInfo)getAppointmentDao().load(AppointmentDao.TRANSFORM_APPOINTMENTINFO, id);
    }

    /**
     * @see org.openuss.calendar.CalendarService#getSerialAppointment(java.lang.Long)
     */
    protected org.openuss.calendar.SerialAppointmentInfo handleGetSerialAppointment(java.lang.Long id)
        throws java.lang.Exception
    {
        return (SerialAppointmentInfo)getSerialAppointmentDao().load(SerialAppointmentDao.TRANSFORM_SERIALAPPOINTMENTINFO, id);
     }

    /**
     * @see org.openuss.calendar.CalendarService#getUserCalendars(org.openuss.security.UserInfo)
     */
    protected java.util.List handleGetUserCalendars(org.openuss.security.UserInfo userInfo)
        throws java.lang.Exception
    {
    	ArrayList cal = new ArrayList();
    	cal.addAll(getUserDao().load(userInfo.getId()).getCalendars());
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
	protected List handleGetNaturalSingleAppointments(CalendarInfo calendar)
			throws Exception {
		Calendar cal = getCalendarDao().load(calendar.getId());
		List apps = cal.getNaturalSingleAppointments();
		getAppointmentDao().toAppointmentInfoCollection(apps);
		return apps;
	}
}