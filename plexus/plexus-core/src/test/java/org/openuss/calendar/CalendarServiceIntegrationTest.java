// OpenUSS - Open Source University Support System
package org.openuss.calendar;

/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */

import java.sql.Timestamp;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.acegisecurity.acl.AclManager;
import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.framework.web.jsf.util.AcegiUtils;
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

	private DefaultDomainObject defaultDomainObject;
	private AclManager aclManager;

	@Override
	protected void onSetUpInTransaction() throws Exception {
		AcegiUtils.setAclManager(aclManager);
		testUtility.createUserSecureContext();
		defaultDomainObject = createDomainObject();
		super.onSetUpInTransaction();
	}

	private DefaultDomainObject createDomainObject() {
		DefaultDomainObject defaultDomainObject = new DefaultDomainObject(
				TestUtility.unique());
		securityService.createObjectIdentity(defaultDomainObject, null);
		return defaultDomainObject;
	}

	public void testCreateCalendars() {
		Course course = testUtil.createUniqueCourseInDB();
		CourseInfo courseInfo = testUtil.getCourseDao().toCourseInfo(course);
		User user1 = testUtil.createUniqueUserInDB();
		User user2 = testUtil.createUniqueUserInDB();
		UserInfo userInfo1 = testUtil.getUserDao().toUserInfo(user1);
		UserInfo userInfo2 = testUtil.getUserDao().toUserInfo(user2);
		UserGroup group = testUtil.createUniqueUserGroupInDB();
		UserGroupInfo userGroupInfo = testUtil.getUserGroupDao()
				.toUserGroupInfo(group);

		try {
			calendarService.createCalendar(courseInfo);
			calendarService.createCalendar(userInfo1);
			calendarService.createCalendar(userInfo2);
			calendarService.createCalendar(userGroupInfo);

			CalendarInfo courseCalInfo = calendarService
					.getCalendar(courseInfo);
			assertNotNull(courseCalInfo);
			assertEquals("COURSE", courseCalInfo.getCalendarType().toString());
			CalendarInfo groupCalInfo = calendarService
					.getCalendar(userGroupInfo);
			CalendarInfo userCalInfo = calendarService.getCalendar(userInfo1);

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

			/**
			 * ******test create an single appointment for the user calendar
			 * **********
			 */

			AppointmentInfo singleAppointmentInfo = testUtil
					.getTestAppointmentInfo(standardAppointmentType);
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

			/** ********** test update appointment *********************** */

			AppointmentInfo newAppointmentInfo = calendarService
					.getAppointment(singleAppInfo.getId());

			AppointmentType newAppType = testUtil
					.createAppointmentTypeInDB("new apptype");
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
			assertEquals("new apptype", updatedAppInfos.get(0)
					.getAppointmentTypeInfo().getName());

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

			/** ******** test create serial appointment ***************** */

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

			/** ***************** test update serial appointment **************** */

			serialAppInfo.setDescription("new description");

			// change appointment type
			AppointmentType newAppType = testUtil
					.createAppointmentTypeInDB("new type");
			AppointmentTypeInfo newAppTypeInfo = getAppointmentTypeDao()
					.toAppointmentTypeInfo(newAppType);

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
			assertEquals("new type", saAfterUpdate.getAppointmentTypeInfo()
					.getName());

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

	public void testSubscriptionGroup() {

		User user = getSecurityService().getCurrentUser();
		assertNotNull(user);
		UserInfo currentUserInfo = testUtil.getUserDao().toUserInfo(user);
		assertNotNull(currentUserInfo);

		UserGroup userGroup = testUtil.createUniqueUserGroupInDB();
		assertNotNull(userGroup);
		UserGroupInfo userGroupInfo = testUtil.getUserGroupDao()
				.toUserGroupInfo(userGroup);
		assertNotNull(userGroupInfo);

		try {
			calendarService.createCalendar(currentUserInfo);
			CalendarInfo userCalInfo = calendarService
					.getCalendar(currentUserInfo);
			calendarService.createCalendar(userGroupInfo);
			CalendarInfo groupCalInfo = calendarService
					.getCalendar(userGroupInfo);

			// createAppointment for group

			testUtil.createUniqueAppointmentForCalendarInDB(groupCalInfo);

			List<AppointmentInfo> appInfos = calendarService
					.getSingleAppointments(groupCalInfo);
			assertNotNull(appInfos.get(0));

			// add subscription
			calendarService.addSubscription(groupCalInfo);

			// test if subscription is added

			List<CalendarInfo> subscribedCals = calendarService
					.getSubscriptions();
			assertNotNull(subscribedCals);
			assertEquals(1, subscribedCals.size());
			assertNotNull(subscribedCals.get(0));
			assertEquals("GROUP", subscribedCals.get(0).getCalendarType()
					.toString());

			// test if the appointment is added to the user calendar

			List<AppointmentInfo> appInfosAfterSub = calendarService
					.getNaturalSingleAppointments(userCalInfo);
			assertNotNull(appInfosAfterSub);
			assertEquals(appInfosAfterSub.get(0), appInfos.get(0));

			// add additional appointments to course
			testUtil.createUniqueAppointmentForCalendarInDB(groupCalInfo);

			List<AppointmentInfo> groupAppInfos = calendarService
					.getSingleAppointments(groupCalInfo);
			assertEquals(2, groupAppInfos.size());

			// test if the appointments are added to the user calendar
			List<AppointmentInfo> appInfosAfterSub2 = calendarService
					.getNaturalSingleAppointments(userCalInfo);
			assertEquals(2, appInfosAfterSub2.size());
			assertTrue(groupAppInfos.equals(appInfosAfterSub2));

		} catch (CalendarApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

	public void testSubscriptionCourse() {
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

		User user = getSecurityService().getCurrentUser();
		assertNotNull(user);
		UserInfo currentUserInfo = testUtil.getUserDao().toUserInfo(user);
		assertNotNull(currentUserInfo);

		Course course = testUtil.createUniqueCourseInDB();
		assertNotNull(course);
		CourseInfo courseInfo = testUtil.getCourseDao().toCourseInfo(course);
		assertNotNull(courseInfo);

		try {

			calendarService.createCalendar(currentUserInfo);
			calendarService.createCalendar(courseInfo);
			CalendarInfo courseCalInfo = calendarService
					.getCalendar(courseInfo);
			CalendarInfo userCalInfo = calendarService
					.getCalendar(currentUserInfo);

			// createAppointment for group

			testUtil.createUniqueAppointmentForCalendarInDB(courseCalInfo);

			List<AppointmentInfo> appInfos = calendarService
					.getSingleAppointments(courseCalInfo);
			assertNotNull(appInfos.get(0));

			// add subscription
			calendarService.addSubscription(courseCalInfo);

			// test if subscription is added

			List<CalendarInfo> subscribedCals = calendarService
					.getSubscriptions();
			assertNotNull(subscribedCals);
			assertEquals(1, subscribedCals.size());
			assertEquals("COURSE", subscribedCals.get(0).getCalendarType()
					.toString());

			// test if the appointment is added to the user calendar

			List<AppointmentInfo> appInfosAfterSub = calendarService
					.getNaturalSingleAppointments(userCalInfo);
			assertNotNull(appInfosAfterSub);
			assertEquals(appInfosAfterSub.get(0), appInfos.get(0));

			// add additional appointments to course
			testUtil.createUniqueAppointmentForCalendarInDB(courseCalInfo);

			List<AppointmentInfo> groupAppInfos = calendarService
					.getSingleAppointments(courseCalInfo);
			assertEquals(2, groupAppInfos.size());

			// test if the appointments are added to the user calendar
			List<AppointmentInfo> appInfosAfterSub2 = calendarService
					.getNaturalSingleAppointments(userCalInfo);
			assertEquals(2, appInfosAfterSub2.size());
			assertTrue(groupAppInfos.equals(appInfosAfterSub2));

			// test if apps are also updated

			AppointmentInfo appToUpdate = calendarService
					.getAppointment(appInfosAfterSub2.get(0).getId());
			appToUpdate.setDescription("new description");
			calendarService.updateAppointment(appToUpdate, courseCalInfo);
			AppointmentInfo appAfterUpdate = calendarService
					.getAppointment(appInfosAfterSub2.get(0).getId());
			assertEquals(appToUpdate, appAfterUpdate);

		} catch (CalendarApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Test the sequential subcription of two calendars
	 */
	public void testSubscriptionSingleApp() {
		User user = getSecurityService().getCurrentUser();
		assertNotNull(user);
		UserInfo currentUserInfo = testUtil.getUserDao().toUserInfo(user);
		assertNotNull(currentUserInfo);

		Course course = testUtil.createUniqueCourseInDB();
		assertNotNull(course);
		CourseInfo courseInfo = testUtil.getCourseDao().toCourseInfo(course);
		assertNotNull(courseInfo);

		try {

			calendarService.createCalendar(currentUserInfo);
			calendarService.createCalendar(courseInfo);
			CalendarInfo courseCalInfo = calendarService
					.getCalendar(courseInfo);
			CalendarInfo userCalInfo = calendarService
					.getCalendar(currentUserInfo);

			// createAppointment for course
			testUtil.createUniqueAppointmentForCalendarInDB(courseCalInfo);

			List<AppointmentInfo> appInfos = calendarService
					.getSingleAppointments(courseCalInfo);
			assertNotNull(appInfos.get(0));
			assertEquals(1, appInfos.size());

			// add subscription
			calendarService.addSubscription(courseCalInfo);

			// test if subscription is added

			List<CalendarInfo> subscribedCals = calendarService
					.getSubscriptions();
			assertNotNull(subscribedCals);
			assertEquals(1, subscribedCals.size());
			assertEquals("COURSE", subscribedCals.get(0).getCalendarType()
					.toString());

			// test if the appointment is added to the user calendar

			List<AppointmentInfo> appInfosAfterSub = calendarService
					.getNaturalSingleAppointments(userCalInfo);
			assertNotNull(appInfosAfterSub);
			assertEquals(appInfosAfterSub.get(0), appInfos.get(0));

			// add additional appointments to course
			testUtil.createUniqueAppointmentForCalendarInDB(courseCalInfo);

			List<AppointmentInfo> courseAppInfos = calendarService
					.getSingleAppointments(courseCalInfo);
			assertEquals(2, courseAppInfos.size());

			// test if the appointments are added to the user calendar
			List<AppointmentInfo> appInfosAfterSub2 = calendarService
					.getNaturalSingleAppointments(userCalInfo);
			assertEquals(2, appInfosAfterSub2.size());
			assertTrue(courseAppInfos.equals(appInfosAfterSub2));

			// test subscription to a second calendar (group)

			UserGroup userGroup = testUtil.createUniqueUserGroupInDB();
			UserGroupInfo userGroupInfo = testUtil.getUserGroupDao()
					.toUserGroupInfo(userGroup);
			assertNotNull(userGroupInfo);
			assertNotNull(userGroupInfo.getId());
			calendarService.createCalendar(userGroupInfo);

			CalendarInfo groupCalInfo = calendarService
					.getCalendar(userGroupInfo);

			testUtil.createUniqueAppointmentForCalendarInDB(groupCalInfo);

			calendarService.addSubscription(groupCalInfo);

			List<Calendar> subedCals = calendarService.getSubscriptions();
			assertNotNull(subedCals);
			assertEquals(2, subedCals.size());

			// test if the appointment of the group cal is added to the user cal
			List<Appointment> singleAppInfos = calendarService
					.getSingleAppointments(userCalInfo);
			assertEquals(3, singleAppInfos.size());

			// add a group appointment and test if its added to the user cal
			testUtil.createUniqueAppointmentForCalendarInDB(groupCalInfo);
			List<Appointment> singleAppInfos2 = calendarService
					.getSingleAppointments(userCalInfo);
			assertEquals(4, singleAppInfos2.size());

			// test update and delete for subscribed cals

			// test delete
			List<AppointmentInfo> courseAppInfos2 = calendarService
					.getSingleAppointments(courseCalInfo);
			AppointmentInfo appInfoToDel = courseAppInfos2.get(0);
			assertNotNull(appInfoToDel);
			calendarService.deleteAppointment(appInfoToDel, courseCalInfo);
			List<AppointmentInfo> appInfosAfterDel = calendarService
					.getSingleAppointments(courseCalInfo);
			assertEquals(1, appInfosAfterDel.size());
			List<AppointmentInfo> userAppInfosAfterDel = calendarService
					.getSingleAppointments(userCalInfo);
			assertEquals(3, userAppInfosAfterDel.size());

			// udpate course app
			List<AppointmentInfo> appInfos2 = calendarService
					.getSingleAppointments(courseCalInfo);
			AppointmentInfo appInfo = appInfos2.get(0);
			appInfo.setDescription("New Description");
			calendarService.updateAppointment(appInfo, courseCalInfo);
			Long id = appInfo.getId();
			List<AppointmentInfo> userAppInfos2 = calendarService
					.getSingleAppointments(userCalInfo);
			for (AppointmentInfo appInfoIt : userAppInfos2) {
				if (appInfoIt.getId() == id) {
					assertEquals(appInfoIt.getDescription(), "New Description");
				}
			}

		} catch (CalendarApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

	public void testSubscriptionSerialApp() {
		User user = getSecurityService().getCurrentUser();
		assertNotNull(user);
		UserInfo currentUserInfo = testUtil.getUserDao().toUserInfo(user);
		assertNotNull(currentUserInfo);

		Course course = testUtil.createUniqueCourseInDB();
		assertNotNull(course);
		CourseInfo courseInfo = testUtil.getCourseDao().toCourseInfo(course);
		assertNotNull(courseInfo);

		try {

			AppointmentType standardAppointmentType = testUtil
					.createAppointmentTypeInDB("standard");
			AppointmentTypeInfo appointmentTypeInfo = getAppointmentTypeDao()
					.toAppointmentTypeInfo(standardAppointmentType);

			calendarService.createCalendar(currentUserInfo);
			calendarService.createCalendar(courseInfo);
			CalendarInfo courseCalInfo = calendarService
					.getCalendar(courseInfo);
			CalendarInfo userCalInfo = calendarService
					.getCalendar(currentUserInfo);

			// create serial app for course
			// TODO Serienterminerzeugung ersetzen durch eine Methode in
			// testUtil
			// TODO Erzeugung der Zeitpunkte in den createUnique... Methoden pr�fen
			testUtil.createUniqueSeriallAppForCalendarInDB(courseCalInfo);
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
					courseCalInfo);

			// add subscription
			
			calendarService.addSubscription(courseCalInfo);

			// test if natural serial app is added
			
			List<SerialAppointmentInfo> serialAppInfos = calendarService.getNaturalSerialAppointments(userCalInfo);
			assertNotNull(serialAppInfos);
			assertEquals(1, serialAppInfos.size());

			// test if generated single apps are added
			
			List<AppointmentInfo> singleAppInfos = calendarService.getSingleAppointments(userCalInfo);
			assertNotNull(serialAppInfos);
			System.out.println(singleAppInfos.size());
			assertEquals(6, serialAppInfos.size());

			// create additional serial app for course

			// test if natural serial app is added

			// test if generated single apps are added

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	public void testEndSubscription() {

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