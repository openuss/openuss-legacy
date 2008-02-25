// OpenUSS - Open Source University Support System
package org.openuss.calendar;

/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;

import org.openuss.TestUtility;
import org.openuss.groups.UserGroup;
import org.openuss.groups.UserGroupInfo;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseDao;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseTypeDao;
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

	public TestUtility testUtil;
	
	public void testCreateCalendars() {
		Course course = testUtil.createUniqueCourseInDB();
		CourseInfo courseInfo = testUtil.getCourseDao().toCourseInfo(course);
		User user1 = testUtil.createUniqueUserInDB();
		User user2 = testUtil.createUniqueUserInDB();
		UserInfo userInfo1 = testUtil.getUserDao().toUserInfo(user1);
		UserInfo userInfo2 = testUtil.getUserDao().toUserInfo(user2);		
		
		try {
			calendarService.createCalendar(courseInfo);
			calendarService.createCalendar(userInfo1);
			calendarService.createCalendar(userInfo2);
			
			CalendarInfo courseCalInfo = calendarService.getCalendar(courseInfo);
			assertNotNull(courseCalInfo);
			System.out.println("Kalender: " + courseCalInfo.getCalendarType());
			assertEquals("COURSE", courseCalInfo.getCalendarType().toString());
			
			
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	public void testCalendarAdministrationSingle() {

		// create user
		User user = testUtil.createUniqueUserInDB();
		UserInfo userInfo = (UserInfo) userDao.load(userDao.TRANSFORM_USERINFO,
				user.getId());

		// get a generate Appointment Type
		AppointmentType standardAppointmentType = testUtil
				.createAppointmentTypeInDB("standard");

		try {

			// create calendar for user
			calendarService.createCalendar(userInfo);
			// get calendar from user

			CalendarInfo userCalInfo = calendarService.getCalendar(userInfo);
			assertNotNull(userCalInfo);

			/********test create an single appointment for the user calendar ***********/
			
			AppointmentInfo singleAppointmentInfo = testUtil
					.getTestAppointmentInfo(standardAppointmentType, user);
			calendarService.createAppointment(singleAppointmentInfo,
					userCalInfo);

			// get natural single appointments from calendar
			List<AppointmentInfo> naturalSingleAppInfos = calendarService
					.getNaturalSingleAppointments(userCalInfo);
			assertEquals(1, naturalSingleAppInfos.size());
			AppointmentInfo naturalAppInfo = naturalSingleAppInfos.get(0);

			// get (all) single appointments (including generated appointments
			// from a serial appointment

			List<AppointmentInfo> singleAppInfos = calendarService
					.getSingleAppointments(userCalInfo);
			assertEquals(1, singleAppInfos.size());
			AppointmentInfo singleAppInfo = singleAppInfos.get(0);
			assertEquals(naturalAppInfo, singleAppInfo);

			assertEquals("Description", naturalAppInfo.getDescription());

			// test if a appointment type info is associated to the appointment
			// info

			AppointmentInfo appInfo = singleAppInfos.get(0);
			assertNotNull(appInfo.getAppointmentTypeInfo());
			assertEquals("standard", appInfo.getAppointmentTypeInfo().getName());

			/************ test update appointment ************************/

			AppointmentInfo newAppointmentInfo = calendarService
					.getAppointment(singleAppInfo.getId());
			
			AppointmentType newAppType = testUtil.createAppointmentTypeInDB("new apptype");
			AppointmentTypeInfo newAppTypeInfo = getAppointmentTypeDao()
				.toAppointmentTypeInfo(newAppType);

			newAppointmentInfo.setDescription("new Description");
			newAppointmentInfo.setAppointmentTypeInfo(newAppTypeInfo);
			calendarService.updateAppointment(newAppointmentInfo, userCalInfo);
			List<AppointmentInfo> updatedAppInfos = calendarService
					.getSingleAppointments(userCalInfo);
			assertNotNull(updatedAppInfos);
			assertEquals(updatedAppInfos.get(0).getId(), singleAppInfos.get(0)
					.getId());
			assertEquals("new Description", updatedAppInfos.get(0)
					.getDescription());
			assertEquals("new apptype", updatedAppInfos.get(0).getAppointmentTypeInfo().getName());

			// test delete appointment

			calendarService.deleteAppointment(newAppointmentInfo, userCalInfo);
			List<AppointmentInfo> appointmentInfos = calendarService
					.getSingleAppointments(userCalInfo);
			assertEquals(0, appointmentInfos.size());
		} catch (CalendarApplicationException e) {
			fail();
		}

	}

	public void testCalendarAdministrationSerial() {
		/*
		 * Complete test 1.5 7 - createSerialAppointment 8 -
		 * getSerialAppointments / getAllUserAppointments -> not null 9 -
		 * updateSerialAppointment 10 - getAppointment 11 -
		 * deleteSerialAppointment 12 - getSerialAppointments /
		 * getAllUserAppointments -> null
		 */

		// generate Appointment Type
		AppointmentType standardAppointmentType = testUtil
				.createAppointmentTypeInDB("standard");
		AppointmentTypeInfo appointmentTypeInfo = getAppointmentTypeDao()
				.toAppointmentTypeInfo(standardAppointmentType);

		// create user
		User user = testUtil.createUniqueUserInDB();
		UserInfo userInfo = (UserInfo) userDao.load(userDao.TRANSFORM_USERINFO,
				user.getId());

		try {
			calendarService.createCalendar(userInfo);
			CalendarInfo userCalendarInfo = calendarService
					.getCalendar(userInfo);

			/********** test create serial appointment ******************/

			SerialAppointmentInfo serialAppointmentInfo = new SerialAppointmentInfo();
			serialAppointmentInfo.setSubject("subject");
			serialAppointmentInfo.setDescription("description");
			GregorianCalendar gregCal = new GregorianCalendar();
			gregCal.set(2008, 03, 01, 17, 00);
			serialAppointmentInfo.setStarttime(new Timestamp(gregCal.getTime()
					.getTime()));
			gregCal.add(GregorianCalendar.HOUR, 1);
			serialAppointmentInfo.setEndtime(new Timestamp(gregCal.getTime()
					.getTime()));
			serialAppointmentInfo.setLocation("location");
			gregCal.add(GregorianCalendar.WEEK_OF_YEAR, 5);
			serialAppointmentInfo.setRecurrenceEndtime(new Timestamp(gregCal
					.getTime().getTime()));
			serialAppointmentInfo.setRecurrencePeriod(1);
			serialAppointmentInfo.setRecurrenceType(RecurrenceType.weekly);
			serialAppointmentInfo.setAppointmentTypeInfo(appointmentTypeInfo);
			serialAppointmentInfo.setSerial(true);

			calendarService.createSerialAppointment(serialAppointmentInfo,
					userCalendarInfo);

			// test if the serial appointment is added (not the generated
			// single appointments)

			List<SerialAppointmentInfo> serialAppointments = calendarService
					.getNaturalSerialAppointments(userCalendarInfo);
			assertNotNull(serialAppointments);
			assertEquals(1, serialAppointments.size());

			// test if the single appointments are created from the serial
			// appointment

			List singleAppointments = calendarService
					.getSingleAppointments(userCalendarInfo);
			assertNotNull(singleAppointments);
			assertEquals(6, singleAppointments.size());

			SerialAppointmentInfo serialAppInfo = serialAppointments.get(0);
			assertNotNull(serialAppInfo.getId());

			// test if an appointmentType is associated to the serial
			// appointment

			/******************* test update serial appointment *****************/

			serialAppInfo.setDescription("new description");
		
			// change appointment type
			AppointmentType newAppType = testUtil.createAppointmentTypeInDB("new type");
			AppointmentTypeInfo newAppTypeInfo = getAppointmentTypeDao().
				toAppointmentTypeInfo(newAppType);
			
			serialAppInfo.setAppointmentTypeInfo(newAppTypeInfo);
			calendarService.updateSerialAppointment(serialAppInfo,
					userCalendarInfo);

			List<SerialAppointmentInfo> serialAppInfosAfterUpdate = calendarService
					.getNaturalSerialAppointments(userCalendarInfo);

			SerialAppointmentInfo saAfterUpdate = serialAppInfosAfterUpdate
					.get(0);
			assertNotNull(saAfterUpdate);
			assertEquals(1, serialAppInfosAfterUpdate.size());
			assertEquals("new description", saAfterUpdate.getDescription());
			assertEquals("new type", saAfterUpdate.getAppointmentTypeInfo().getName());

			/* test delete serial appointment */

			calendarService.deleteSerialAppointment(saAfterUpdate,
					userCalendarInfo);

			// test if natural serial appointments exist after delete
			List<SerialAppointmentInfo> serialAppInfosAfterDelete = calendarService
					.getNaturalSerialAppointments(userCalendarInfo);
			assertEquals(0, serialAppInfosAfterDelete.size());

			// test if single appointments exist
			List singleAppInfos = calendarService
					.getSingleAppointments(userCalendarInfo);
			assertEquals(0, singleAppInfos.size());

		} catch (CalendarApplicationException e) {
			e.printStackTrace();
			fail();
		}
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
		
		// test subscription to courses
		
		Course course = testUtil.createUniqueCourseInDB();
		CourseInfo courseInfo = testUtil.getCourseDao().toCourseInfo(course);
		User user1 = testUtil.createUniqueUserInDB();
		User user2 = testUtil.createUniqueUserInDB();
		UserInfo userInfo1 = testUtil.getUserDao().toUserInfo(user1);
		UserInfo userInfo2 = testUtil.getUserDao().toUserInfo(user2);		
		
		try {
			calendarService.createCalendar(courseInfo);
			calendarService.createCalendar(userInfo1);
			calendarService.createCalendar(userInfo2);
			
			CalendarInfo user1CalInfo = calendarService.getCalendar(userInfo1);
			CalendarInfo userCalInfo = calendarService.getCalendar(userInfo2);
			CalendarInfo courseCalInfo = calendarService.getCalendar(courseInfo);
			
			// createAppointment for course
			
			testUtil.createUniqueAppointmentForCalendarInDB(courseCalInfo);
			
			List<AppointmentInfo> appInfos = calendarService.getSingleAppointments(courseCalInfo);
			assertNotNull(appInfos.get(0));
			
			// add subscription
			calendarService.addSubscription(courseCalInfo); 
			
			// test if subscription is added
			
			
			// test if the appointments are added to the user calendar
			
			// add additional appointments to course
			
			// test if the appointments are added to the user calendar
			
			
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

	public AppointmentTypeDao getAppointmentTypeDao() {
		return this.appointmentTypeDao;
	}

	public AppointmentDao getAppointmentDao() {
		return appointmentDao;
	}

	public void setAppointmentDao(AppointmentDao appDao) {
		this.appointmentDao = appDao;
	}

	public TestUtility getTestUtil() {
		return testUtil;
	}

	public void setTestUtil(TestUtility testUtil) {
		this.testUtil = testUtil;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}
}