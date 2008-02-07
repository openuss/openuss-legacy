// OpenUSS - Open Source University Support System
package org.openuss.calendar;
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.openuss.lecture.*;

import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;
import org.openuss.groups.GroupInfo;
import org.openuss.groups.AccessType;
import org.openuss.lecture.CourseInfo;
import org.openuss.security.*;

/**
 * JUnit Test for Spring Hibernate CalendarService class.
 * @see org.openuss.calendar.CalendarService
 */
public class CalendarServiceIntegrationTest extends CalendarServiceIntegrationTestBase {
	
	public SecurityService securityService;
	
	public CalendarDao calendarDao;
	
	public UserDao userDao;
	
	public CourseTypeDao courseTypeDao;
	
	public CourseDao courseDao;
	
	public PeriodDao periodDao;
	
	public AppointmentTypeDao appointmentTypeDao;
	
	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	private UserInfo getTestUserInfo() {
		DomainObject domainObjectUser = generateDomainObject();
		
		UserInfo userInfo = new UserInfo();
		userInfo.setId(domainObjectUser.getId());
		userInfo.setUsername("El Matatdor");
		userInfo.setFirstName("Hans");
		userInfo.setLastName("Peter");
		userInfo.setPassword("pwd");
		userInfo.setEmail("ElMatador@Stierkampf.de");
				
		return userInfo;
	}
	
	private AppointmentInfo getTestAppointmentInfo(AppointmentType appType, User user) {
		
		// Startzeit erzeugen
		Date start = new Date();
		
		// Endzeit erzeugen
		Date end = new Date();
		
		AppointmentInfo appInfo = new AppointmentInfo();
		appInfo.setSubject("Subject");
		appInfo.setDescription("Description");
		appInfo.setLocation("Location");
		appInfo.setStarttime(new Timestamp(start.getTime()));
		appInfo.setEndtime(new Timestamp(end.getTime()));
		appInfo.setTimeZone("fdfd");
		appInfo.setAppointmentType(getAppointmentTypeDao().toAppointmentTypeInfo(appType));
		appInfo.setCreator(userDao.toUserInfo(user));
		
		// TODO isSerial setzen beim Erzeugen eines Termins
		appInfo.setIsSerial(false);
		
		return appInfo;
	}
	
	private DomainObject generateDomainObject(){
		DomainObject domainObject = new DefaultDomainObject(TestUtility.unique());
		securityService.createObjectIdentity(domainObject, null);
		//securityService.setPermissions(user, domainId, LectureAclEntry.ASSIST);		
		
		return domainObject;
	}
	

	
	public void testCalendarAdministration() {
		/* Complete Test 1)
	 	 * 1 - createUserCalendar / createCalendar for userInfo object
		 * 2 - createAppointment
		 * 3 - getSingleAppointments / getAllUserAppointments -> not null
		 * 4 - updateAppointment
		 * 5 - getAppointment
		 * 6 - deleteAppointment
		 * 7 - createSerialAppointment
		 * 8 - getSerialAppointments / getAllUserAppointments -> not null
		 * 9 - updateSerialAppointment
		 *10 - getAppointment
		 *11 - deleteSerialAppointment
		 *12 - getSerialAppointments / getAllUserAppointments -> null
		 *13 - deleteCalendar
		 *     End :)
		 */ 
		
		//create user
		User user = userDao.create("user", "pwd", "e@ma.il", true, false, false, false, new Date());
		UserInfo userInfo = (UserInfo)userDao.load(userDao.TRANSFORM_USERINFO, user.getId());
		
		//generate Appointment Type
		AppointmentType standardAppointmentType = getAppointmentTypeDao().create("standard");
		
		//create calendar
		try {
			calendarService.createUserCalendar(userInfo);
			//get calendar from user
			List<Calendar> calendars = calendarService.getUserCalendars(userInfo);
			assertNotNull(calendars);
			assertEquals(1, calendars.size());
			System.out.println("cal-id: "+calendars.get(0).getId());
			CalendarInfo calendar = (CalendarInfo)calendarDao.load(calendarDao.TRANSFORM_CALENDARINFO, calendars.get(0).getId());
			assertNotNull(calendar);
			assertEquals(calendars.get(0).getId(), calendar.getId());
			//create appointment
			AppointmentInfo singleAppointment = getTestAppointmentInfo(standardAppointmentType, user);
			assertNotNull(singleAppointment);
			calendarService.createAppointment(singleAppointment, calendar);
			//get appointments from calendar
			List<AppointmentInfo> appointmentInfos = calendarService.getSingleAppointments(calendar);
			assertEquals(1, appointmentInfos.size());
			//get appointments from user
			List<AppointmentInfo> appointments = calendarService.getAllUserAppointments(userInfo);
			assertNotNull(appointments);
			assertEquals(appointments.get(0).getId(), appointmentInfos.get(0).getId());
		} catch (CalendarApplicationException e) {
			fail();
		}

	}
	
	public void testSubscription() {
		/* Complete Test 2)
		 * requires group + groupCalendar + appointments AND course + courseCalendar + appointments
		 * 1 - createUserCalendar / creatCalendar for userInfo object
		 * 2 - getUserCalendars -> not null, but only userCalendar !
		 * 3 - addSubscription(group)
		 * 4 - getAllUserAppointments -> not null
		 * 5 - getUserCalendars -> user+groupCalendar
		 * 6 - endSubscription(group)
		 * 7 - getAllUserAppointments -> null
		 * 8 - getUserCalendars -> only userCalendar
		 * 9 - addSubscription(course)
		 *10 - getAllUserAppointments -> not null
		 *11 - getUserCalendars -> user+courseCalendar
		 *12 - endSubscription(course)
		 *13 - getAllUserAppointments -> null
		 *14 - getUserCalendars -> only userCalendar
		 *15 - deleteCalendar
		 *     End :)
		 */
		
	}
	
	public void testCourseCalendar() {
		AppointmentType standardAppointmentType = getAppointmentTypeDao().create("standard");
		User user = userDao.create("user", "pwd", "e@ma.il", true, false, false, false, new Date());
		CourseType courseType = getCourseTypeDao().create("long name", "name", "description");
		Period period = getPeriodDao().create("always", "always", new Date(2005), new Date(2007), true);
		Course course = getCourseDao().create("test", org.openuss.lecture.AccessType.OPEN, "pwd", new Boolean(false), new Boolean(false), new Boolean(false), new Boolean(false), new Boolean(false), new Boolean(false), new Boolean(false), "Descr", true, new Boolean(false), new Boolean(false));
		course.setCourseType(courseType);
		course.setPeriod(period);
		CourseInfo courseInfo = getCourseDao().toCourseInfo(course);
		try {
			calendarService.createCalendar(courseInfo);
			CalendarInfo calendarInfo = calendarService.getCalendar(courseInfo);
			assertNotNull(calendarInfo);
			//create appointment
			AppointmentInfo singleAppointmentInfo = getTestAppointmentInfo(standardAppointmentType, user);
			assertNotNull(singleAppointmentInfo);
			calendarService.createAppointment(singleAppointmentInfo, calendarInfo);
			List<AppointmentInfo> appointmentInfos = calendarService.getSingleAppointments(calendarInfo);
			assertEquals(1, appointmentInfos.size());
			
		} catch (CalendarApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}

	
	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setCalendarDao(CalendarDao calendarDao) {
		this.calendarDao = calendarDao;
	}

	public CourseTypeDao getCourseTypeDao() {
		return courseTypeDao;
	}

	public void setCourseTypeDao(CourseTypeDao courseTypeDao) {
		this.courseTypeDao = courseTypeDao;
	}

	public PeriodDao getPeriodDao() {
		return periodDao;
	}

	public void setPeriodDao(PeriodDao periodDao) {
		this.periodDao = periodDao;
	}

	public void setAppointmentTypeDao(AppointmentTypeDao appointmentTypeDao) {
		this.appointmentTypeDao = appointmentTypeDao;
	}
	
	public AppointmentTypeDao getAppointmentTypeDao(){
		return this.appointmentTypeDao;
	}
}