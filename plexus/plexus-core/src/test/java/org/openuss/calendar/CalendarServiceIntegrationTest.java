// OpenUSS - Open Source University Support System
package org.openuss.calendar;

/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseDao;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.CourseTypeDao;
import org.openuss.lecture.Period;
import org.openuss.lecture.PeriodDao;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserDao;
import org.openuss.security.UserInfo;

/**
 * JUnit Test for Spring Hibernate CalendarService class.
 * 
 * @see org.openuss.calendar.CalendarService
 */
public class CalendarServiceIntegrationTest extends
		CalendarServiceIntegrationTestBase {

	public SecurityService securityService;

	public CalendarDao calendarDao;

	public UserDao userDao;

	public CourseTypeDao courseTypeDao;

	public CourseDao courseDao;

	public PeriodDao periodDao;

	public AppointmentDao appointmentDao;

	public AppointmentTypeDao appointmentTypeDao;
	
	public TestUtility testUtility = new TestUtility();

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	private AppointmentInfo getTestAppointmentInfo(AppointmentType appType,
			User user) {

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
		appInfo.setAppointmentTypeInfo(getAppointmentTypeDao()
				.toAppointmentTypeInfo(appType));

		// TODO isSerial setzen beim Erzeugen eines Termins
		appInfo.setSerial(false);

		return appInfo;
	}

	public void testCalendarAdministrationSingle() {

//		// create user
//		User user = testUtility.createUniqueUserInDB();
//		UserInfo userInfo = (UserInfo) userDao.load(userDao.TRANSFORM_USERINFO,
//				user.getId());
//
//
//		// generate Appointment Type
//		AppointmentType standardAppointmentType = getAppointmentTypeDao()
//				.create("standard");
//
//		try {
//
//			// create calendar for user
//			calendarService.createCalendar(userInfo);
//			// get calendar from user
//
//			CalendarInfo userCalInfo = calendarService.getCalendar(userInfo);
//			assertNotNull(userCalInfo);
//
//			// create an single appointment for the user calendar
//			AppointmentInfo singleAppointmentInfo = getTestAppointmentInfo(
//					standardAppointmentType, user);
//			calendarService.createAppointment(singleAppointmentInfo,
//					userCalInfo);
//
//			// get natural single appointments from calendar
//			List<AppointmentInfo> naturalSingleAppInfos = calendarService
//					.getNaturalSingleAppointments(userCalInfo);
//			assertEquals(1, naturalSingleAppInfos.size());
//			AppointmentInfo naturalAppInfo = naturalSingleAppInfos.get(0);
//
//			// get (all) single appointments (including generated appointments
//			// from a serial appointment
//
//			List<AppointmentInfo> singleAppInfos = calendarService
//					.getSingleAppointments(userCalInfo);
//			assertEquals(1, singleAppInfos.size());
//			AppointmentInfo singleAppInfo = singleAppInfos.get(0);
//			assertEquals(naturalAppInfo, singleAppInfo);
//
//			assertEquals("Description", naturalAppInfo.getDescription());
//			
//			// test if a appointmenttypeinfo is associated to the appointmentinfo
//			
//			AppointmentInfo appInfo = singleAppInfos.get(0);
//			System.out.println("Type Name: " + appInfo.getAppointmentTypeInfo().getName());
//			assertNotNull(appInfo.getAppointmentTypeInfo());
//
//			// test update appointment
//
//			AppointmentInfo newAppointmentInfo = calendarService
//					.getAppointment(singleAppInfo.getId());
//			
//			newAppointmentInfo.setDescription("new Description");
//			calendarService.updateAppointment(newAppointmentInfo, userCalInfo);
//			List<AppointmentInfo> updatedAppInfos = calendarService
//					.getSingleAppointments(userCalInfo);
//			assertNotNull(updatedAppInfos);
//			assertEquals(updatedAppInfos.get(0).getId(), singleAppInfos.get(0)
//					.getId());
//			assertEquals("new Description", updatedAppInfos.get(0)
//					.getDescription());
//
//			// test delete appointment
//
//			calendarService.deleteAppointment(newAppointmentInfo, userCalInfo);
//			List<AppointmentInfo> appointmentInfos = calendarService
//					.getSingleAppointments(userCalInfo);
//			assertEquals(0, appointmentInfos.size());
//		} catch (CalendarApplicationException e) {
//			fail();
//		}

	}

	public void testCalendarAdministrationSerial() {
//		/*
//		 * Complete test 1.5 7 - createSerialAppointment 8 -
//		 * getSerialAppointments / getAllUserAppointments -> not null 9 -
//		 * updateSerialAppointment 10 - getAppointment 11 -
//		 * deleteSerialAppointment 12 - getSerialAppointments /
//		 * getAllUserAppointments -> null
//		 */
//
//		// generate Appointment Type
//		AppointmentType standardAppointmentType = getAppointmentTypeDao()
//				.create("standard");
//		AppointmentTypeInfo appointmentTypeInfo = getAppointmentTypeDao()
//				.toAppointmentTypeInfo(standardAppointmentType);
//
//		// create user
//		User user = userDao.create("user", "pwd", "e@ma.il", true, false,
//				false, false, new Date());
//		UserInfo userInfo = (UserInfo) userDao.load(userDao.TRANSFORM_USERINFO,
//				user.getId());
//
//		try {
//			calendarService.createCalendar(userInfo);
//			CalendarInfo userCalendarInfo = calendarService
//					.getCalendar(userInfo);
//
//			/* test create serial appointment */
//
//			SerialAppointmentInfo serialAppointmentInfo = new SerialAppointmentInfo();
//			serialAppointmentInfo.setSubject("subject");
//			serialAppointmentInfo.setDescription("description");
//
//			GregorianCalendar gregCal = new GregorianCalendar();
//			gregCal.set(2008, 03, 01, 17, 00);
//
//			serialAppointmentInfo.setStarttime(new Timestamp(gregCal.getTime()
//					.getTime()));
//			gregCal.add(GregorianCalendar.HOUR, 1);
//			serialAppointmentInfo.setEndtime(new Timestamp(gregCal.getTime()
//					.getTime()));
//			serialAppointmentInfo.setLocation("location");
//			gregCal.add(GregorianCalendar.WEEK_OF_YEAR, 5);
//			serialAppointmentInfo.setRecurrenceEndtime(new Timestamp(gregCal
//					.getTime().getTime()));
//			serialAppointmentInfo.setRecurrencePeriod(1);
//			serialAppointmentInfo.setRecurrenceType(RecurrenceType.weekly);
//			serialAppointmentInfo.setAppointmentTypeInfo(appointmentTypeInfo);
//			serialAppointmentInfo.setSerial(true);
//
//			calendarService.createSerialAppointment(serialAppointmentInfo,
//					userCalendarInfo);
//
//			// test if the serial appointment is added (not the generated
//			// single appointments)
//
//			List<SerialAppointmentInfo> serialAppointments = calendarService
//					.getNaturalSerialAppointments(userCalendarInfo);
//			assertNotNull(serialAppointments);
//			assertEquals(1, serialAppointments.size());
//
//			// test if the single appointments are created from the serial
//			// appointment
//
//			List singleAppointments = calendarService
//					.getSingleAppointments(userCalendarInfo);
//			assertNotNull(singleAppointments);
//			assertEquals(6, singleAppointments.size());
//
//			SerialAppointmentInfo serialAppInfo = serialAppointments.get(0);
//			assertNotNull(serialAppInfo.getId());
//			
//
//			/* test update serial appointment */
//
//			serialAppInfo.setDescription("new description");
//			calendarService.updateSerialAppointment(serialAppInfo, userCalendarInfo);
//			
//			List<SerialAppointmentInfo> serialAppInfosAfterUpdate = calendarService.getNaturalSerialAppointments(userCalendarInfo);
//			
//			SerialAppointmentInfo saAfterUpdate = serialAppInfosAfterUpdate.get(0);			
//			assertNotNull(saAfterUpdate);
//			assertEquals(1, serialAppInfosAfterUpdate.size());
//			assertEquals("new description", saAfterUpdate.getDescription());
//			
//			/* test delete serial appointment */
//			
//			calendarService.deleteSerialAppointment(saAfterUpdate,
//					userCalendarInfo);
//			
//			// test if natural serial appointments exist after delete
//			List<SerialAppointmentInfo> serialAppInfosAfterDelete = calendarService
//					.getNaturalSerialAppointments(userCalendarInfo);
//			assertEquals(0, serialAppInfosAfterDelete.size());
//
//			// test if single appointments exist
//			List singleAppInfos = calendarService.getSingleAppointments(userCalendarInfo);
//			assertEquals(0, singleAppInfos.size());
//			
//		} catch (CalendarApplicationException e) {
//			e.printStackTrace();
//			fail();
//		}
	}

	public void testSubscription() {
		/*
		 * Complete Test 2) requires group + groupCalendar + appointments AND
		 * course + courseCalendar + appointments 1 - createUserCalendar /
		 * creatCalendar for userInfo object 2 - getUserCalendars -> not null,
		 * but only userCalendar ! 3 - addSubscription(group) 4 -
		 * getAllUserAppointments -> not null 5 - getUserCalendars ->
		 * user+groupCalendar 6 - endSubscription(group) 7 -
		 * getAllUserAppointments -> null 8 - getUserCalendars -> only
		 * userCalendar 9 - addSubscription(course) 10 - getAllUserAppointments ->
		 * not null 11 - getUserCalendars -> user+courseCalendar 12 -
		 * endSubscription(course) 13 - getAllUserAppointments -> null 14 -
		 * getUserCalendars -> only userCalendar 15 - deleteCalendar End :)
		 */
		
		
		

	}
	

	public void testCourseCalendar() {
//		AppointmentType standardAppointmentType = getAppointmentTypeDao()
//				.create("standard");
//		User user = userDao.create("user", "pwd", "e@ma.il", true, false,
//				false, false, new Date());
//		CourseType courseType = getCourseTypeDao().create("long name", "name",
//				"description");
//		Period period = getPeriodDao().create("always", "always",
//				new Date(2005), new Date(2007), true);
//		Course course = getCourseDao().create("test",
//				org.openuss.lecture.AccessType.OPEN, "pwd", new Boolean(false),
//				new Boolean(false), new Boolean(false), new Boolean(false),
//				new Boolean(false), new Boolean(false), new Boolean(false),
//				"Descr", true, new Boolean(false), new Boolean(false));
//		course.setCourseType(courseType);
//		course.setPeriod(period);
//		CourseInfo courseInfo = getCourseDao().toCourseInfo(course);
//		try {
//			calendarService.createCalendar(courseInfo);
//			CalendarInfo calendarInfo = calendarService.getCalendar(courseInfo);
//			assertNotNull(calendarInfo);
//			// create appointment
//			AppointmentInfo singleAppointmentInfo = getTestAppointmentInfo(
//					standardAppointmentType, user);
//			assertNotNull(singleAppointmentInfo);
//			calendarService.createAppointment(singleAppointmentInfo,
//					calendarInfo);
//			List<AppointmentInfo> appointmentInfos = calendarService
//					.getNaturalSingleAppointments(calendarInfo);
//			assertEquals(1, appointmentInfos.size());
//
//		} catch (CalendarApplicationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			fail();
//		}

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

	public AppointmentTypeDao getAppointmentTypeDao() {
		return this.appointmentTypeDao;
	}

	public AppointmentDao getAppointmentDao() {
		return appointmentDao;
	}

	public void setAppointmentDao(AppointmentDao appDao) {
		this.appointmentDao = appDao;
	}
}