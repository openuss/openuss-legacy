// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.Collection;
import java.util.List;

import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopDao;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.security.acl.Permission;

/**
 * JUnit Test for Spring Hibernate CourseService class.
 * @see org.openuss.lecture.CourseService
 * @author Florian Dondorf
 * @author Ron Haus
 */
public class CourseServiceIntegrationTest extends CourseServiceIntegrationTestBase {
	
	private InstituteDao instituteDao;
	
	private SecurityService securityService;

	private User user;

	private CourseInfo courseInfo;
	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();

		//LectureBuilder lectureBuilder = createInstituteStructure(AccessType.APPLICATION);
		
		//user = testUtility.createUserInDB();
		user = testUtility.createUniqueUserInDB();

		//Course course = lectureBuilder.getCourse();
		Course course = testUtility.createUniqueCourseInDB();
		courseInfo = courseService.getCourseInfo(course.getId());
	}

	public void testCreate () {
		logger.debug("----> BEGIN access to create test <---- ");
		
		//Create CourseType
		CourseType courseType = testUtility.createUniqueCourseTypeInDB();
		
		//Create Period
		Period period = testUtility.createUniquePeriodInDB();
		
		//Synchronize with DB
		flush();
		
		//Create CourseInfo
		CourseInfo courseInfo = new CourseInfo();
		courseInfo.setName(testUtility.unique("name"));
		courseInfo.setShortcut(testUtility.unique("course"));
		courseInfo.setDescription(testUtility.unique("description"));
		courseInfo.setPassword(testUtility.unique("password"));
		courseInfo.setCourseTypeId(courseType.getId());
		courseInfo.setCourseTypeDescription(courseType.getDescription());
		courseInfo.setPeriodId(period.getId());
		courseInfo.setPeriodName(period.getName());
		courseInfo.setAccessType(AccessType.OPEN);
		courseInfo.setBraincontest(true);
		courseInfo.setChat(true);
		courseInfo.setDiscussion(true);
		courseInfo.setDocuments(true);
		courseInfo.setFreestylelearning(true);
		courseInfo.setNewsletter(true);
		courseInfo.setWiki(true);
		
		//Test
		Long courseId = this.getCourseService().create(courseInfo);
		assertNotNull(courseId);
		
		CourseDao courseDao = (CourseDao) this.getApplicationContext().getBean("courseDao");
		Course courseTest = courseDao.load(courseId);
		assertNotNull(courseTest);
		assertEquals(courseId, courseTest.getId());
		assertEquals(courseType.getName(), courseTest.getName());
		assertEquals(courseInfo.getDescription(), courseTest.getDescription());
		assertEquals(courseInfo.getShortcut(), courseTest.getShortcut());
		assertEquals(courseInfo.getPassword(), courseTest.getPassword());
		assertEquals(courseInfo.getAccessType(), courseTest.getAccessType());
		assertEquals(courseInfo.getCourseTypeDescription(), courseTest.getCourseType().getDescription());
		assertEquals(courseInfo.getCourseTypeId(), courseTest.getCourseType().getId());
		assertEquals(courseInfo.getPeriodId(), courseTest.getPeriod().getId());
		assertEquals(courseInfo.getPeriodName(), courseTest.getPeriod().getName());
		
		assertEquals(courseInfo.getId(), courseTest.getPeriod().getCourses().get(0).getId());
		assertEquals(courseInfo.getId(), courseTest.getCourseType().getCourses().get(0).getId());
		
		assertEquals(1, courseType.getCourses().size());
		assertEquals(courseType.getName(), courseType.getCourses().get(0).getName());
		assertEquals(courseInfo.getDescription(), courseType.getCourses().get(0).getDescription());
		
		logger.debug("----> END access to create test <---- ");
	}
	
	public void testUpdateCourse () {
		logger.debug("----> BEGIN access to updateCourse test <---- ");
		
		// Create Course
		Course course = testUtility.createUniqueCourseInDB();
		assertNotNull(course);
		
		// Create CourseType
		CourseType courseType = testUtility.createUniqueCourseTypeInDB();
		assertNotNull(courseType);
		
		// Create Period
		Period period = testUtility.createUniquePeriodInDB();
		assertNotNull(period);
		
		// Synchronize with DB
		flush();
		
		// Create CourseInfo
		CourseInfo courseInfo = new CourseInfo();
		courseInfo.setId(course.getId());
		courseInfo.setName(testUtility.unique("name"));
		courseInfo.setShortcut(testUtility.unique("course"));
		courseInfo.setDescription(testUtility.unique("description"));
		courseInfo.setPassword(testUtility.unique("password"));
		courseInfo.setCourseTypeId(courseType.getId());
		courseInfo.setCourseTypeDescription(courseType.getDescription());
		courseInfo.setPeriodId(period.getId());
		courseInfo.setPeriodName(period.getName());
		courseInfo.setAccessType(AccessType.OPEN);
		courseInfo.setBraincontest(true);
		courseInfo.setChat(true);
		courseInfo.setDiscussion(true);
		courseInfo.setDocuments(true);
		courseInfo.setFreestylelearning(true);
		courseInfo.setNewsletter(true);
		courseInfo.setWiki(true);
		
		// Test
		this.getCourseService().updateCourse(courseInfo);
		
		// Load Course
		CourseDao courseDao = (CourseDao) this.getApplicationContext().getBean("courseDao");
		Course courseTest = courseDao.load(courseInfo.getId());
		assertNotNull(courseTest);
		assertEquals(courseInfo.getDescription(), courseTest.getDescription());
		
		// Create CourseInfo with wrong Period
		courseInfo = new CourseInfo();
		courseInfo.setPeriodId(null);
		
		// Test
		try {
			this.getCourseService().updateCourse(courseInfo);
			fail("Exception should have been thrown!");
		} catch (CourseServiceException cse) {
			;
		}
		
		// Create info with wrong courseType
		courseInfo = new CourseInfo();
		courseInfo.setPeriodId(period.getId());
		courseInfo.setCourseTypeId(null);
		
		// Test
		try {
			this.getCourseService().updateCourse(courseInfo);
			fail("Exception should have been thrown!");
		} catch (CourseServiceException cse) {
			;
		}
		
		// Create info with courseType with wrong institute
		CourseType courseTypeTest = testUtility.createUniqueCourseTypeInDB();
		flush();
		courseTypeTest.setInstitute(null);
		
		courseInfo = new CourseInfo();
		courseInfo.setPeriodId(period.getId());
		courseInfo.setCourseTypeId(courseTypeTest.getId());
		
		// Test
		try {
			this.getCourseService().updateCourse(courseInfo);
			fail("Exception should have been thrown!");
		} catch (CourseServiceException cse) {
			;
		}
		
		logger.debug("----> END access to updateCourse test <---- ");
	}
	
	public void testRemoveCourse () {
		logger.debug("----> BEGIN access to removeCourse test <---- ");
		
		//Create User
		User user = testUtility.createUniqueUserInDB();
		
		//Create Courses
		Course course1 = testUtility.createUniqueCourseInDB();
		CourseType courseType1 = course1.getCourseType(); 
		
		Course course2 = testUtility.createUniqueCourseInDB();
		CourseType courseType2 = course2.getCourseType(); 
		
		//Create desktop
		DesktopDao desktopDao = (DesktopDao) this.getApplicationContext().getBean("desktopDao");
		Desktop desktop = Desktop.Factory.newInstance();
		desktop.setUser(user);
		desktopDao.create(desktop);
		
		
		//Link
		desktop.getCourses().add(course1);
		desktop.getCourses().add(course2);
		
		//Synchronize with DB
		flush();
		
		//Test
		CourseDao courseDao = (CourseDao) this.getApplicationContext().getBean("courseDao");
		Course courseTest = courseDao.load(course1.getId());
		assertNotNull(courseTest);
		assertEquals(2, desktop.getCourses().size());
		
		this.getCourseService().removeCourse(course1.getId());
		courseTest = courseDao.load(course1.getId());
		assertNull(courseTest);
		desktop = desktopDao.load(desktop.getId());
		//assertEquals(1, desktop.getCourses().size());
		
		assertEquals(0, courseType1.getCourses().size());
		
		logger.debug("----> END access to removeCourse test <---- ");
	}
	
	public void testAspirantToParticipant() {

		courseService.applyUser(courseInfo, user);
	
		List<CourseMemberInfo> aspirants = courseService.getAspirants(courseInfo);
		assertEquals(1, aspirants.size());
		
		courseService.acceptAspirant(aspirants.get(0).getId());

		Collection<CourseMember> emptyAspirants = courseService.getAspirants(courseInfo);
		assertEquals(0, emptyAspirants.size());
		
		List<CourseMemberInfo> participants = courseService.getParticipants(courseInfo);
		assertEquals(1, participants.size());
		
		commit();
		
		Permission permission = securityService.getPermissions(user, courseInfo);
		assertNotNull(permission);
		
		LectureAclEntry acl = new LectureAclEntry();
		acl.setMask(permission.getMask());
		
		assertTrue(acl.isPermitted(LectureAclEntry.COURSE_PARTICIPANT));
	}

	public void testRejectAspirant() {
		courseService.applyUser(courseInfo, user);
		
		List<CourseMemberInfo> aspirants = courseService.getAspirants(courseInfo);
		assertEquals(1, aspirants.size());
		
		courseService.rejectAspirant(aspirants.get(0).getId());
		
		Collection<CourseMember> emptyAspirants = courseService.getAspirants(courseInfo);
		assertEquals(0, emptyAspirants.size());
		
		List<CourseMemberInfo> emptyParticipants = courseService.getParticipants(courseInfo);
		assertEquals(0, emptyParticipants.size());
	}
	
	public void testAspirants() {
		courseService.addAspirant(courseInfo, user);
	
		List<CourseMemberInfo> aspirants = courseService.getAspirants(courseInfo);
		assertEquals(1, aspirants.size());
		
		courseService.removeMember(aspirants.get(0).getId());

		Collection<CourseMember> emptyAspirants = courseService.getAspirants(courseInfo);
		assertEquals(0, emptyAspirants.size());
	}

	public void testAssistants() {
		courseService.addAssistant(courseInfo, user);
		
		List<CourseMemberInfo> assistants = courseService.getAssistants(courseInfo);
		assertEquals(1, assistants.size());
		
		courseService.removeMember(assistants.get(0).getId());
		
		Collection<CourseMember> emptyAssistant = courseService.getAssistants(courseInfo);
		assertEquals(0, emptyAssistant.size());
	}
	
	public void testParticipant() {
		courseService.addParticipant(courseInfo, user);
		
		List<CourseMemberInfo> participant = courseService.getParticipants(courseInfo);
		assertEquals(1, participant.size());
		
		courseService.removeMember(participant.get(0).getId());
		
		Collection<CourseMember> emptyParticipants = courseService.getParticipants(courseInfo);
		assertEquals(0, emptyParticipants.size());
	}
	
	public void testFindCourse () {
		
		// Create Courses
		Course course1 = testUtility.createUniqueCourseInDB();
		assertNotNull(course1);
		
		// Synchronize with DB
		flush();
		
		// Test
		CourseInfo courseInfo = this.getCourseService().findCourse(course1.getId());
		assertNotNull(courseInfo);
		assertEquals(course1.getDescription(), courseInfo.getDescription());
	}
	
	public void testFindCoursesByCourseType () {
		
		// Create CourseTypes
		CourseType courseType1 = testUtility.createUniqueCourseTypeInDB();
		CourseType courseType2 = testUtility.createUniqueCourseTypeInDB();
		
		// Create Courses
		Course course1 = testUtility.createUniqueCourseInDB();
		course1.setCourseType(courseType1);
		Course course2 = testUtility.createUniqueCourseInDB();
		course2.setCourseType(courseType2);
		Course course3 = testUtility.createUniqueCourseInDB();
		course3.setCourseType(courseType1);
		Course course4 = testUtility.createUniqueCourseInDB();
		course4.setCourseType(courseType2);
		Course course5 = testUtility.createUniqueCourseInDB();
		course5.setCourseType(courseType1);
		
		// Synchronize with DB
		flush();
		
		// Test
		List<CourseInfo> courses = this.getCourseService().findCoursesByCourseType(courseType1.getId());
		assertNotNull(courses);
		assertEquals(3, courses.size());
		assertEquals(course1.getDescription(), courses.get(0).getDescription());
		
		courses = this.getCourseService().findCoursesByCourseType(courseType2.getId());
		assertNotNull(courses);
		assertEquals(2, courses.size());
		assertEquals(course4.getDescription(), courses.get(1).getDescription());
		
	}
	
	
	public void testFindCoursesByPeriodAndInstitute () {
		logger.debug("----> BEGIN access to findCoursesByPeriodAndInstitute test <---- ");
		
		// Create Periods
		Period period1 = testUtility.createUniquePeriodInDB();
		Period period2 = testUtility.createUniquePeriodInDB();
		
		// Create Institutes
		Institute institute1 = testUtility.createUniqueInstituteInDB();
		Institute institute2 = testUtility.createUniqueInstituteInDB();
		
		// Create CourseTypes
		CourseType courseType1 = testUtility.createUniqueCourseTypeInDB();
		courseType1.setInstitute(institute1);
		CourseType courseType2 = testUtility.createUniqueCourseTypeInDB();
		courseType2.setInstitute(institute2);
		
		// Create Courses
		Course course1 = testUtility.createUniqueCourseInDB();
		course1.setCourseType(courseType1);
		course1.setPeriod(period1);
		Course course2 = testUtility.createUniqueCourseInDB();
		course2.setCourseType(courseType1);
		course2.setPeriod(period2);
		Course course3 = testUtility.createUniqueCourseInDB();
		course3.setCourseType(courseType1);
		course3.setPeriod(period1);
		Course course4 = testUtility.createUniqueCourseInDB();
		course4.setCourseType(courseType2);
		course4.setPeriod(period2);
		Course course5 = testUtility.createUniqueCourseInDB();
		course5.setCourseType(courseType2);
		course5.setPeriod(period1);
		Course course6 = testUtility.createUniqueCourseInDB();
		course6.setCourseType(courseType2);
		course6.setPeriod(period2);
		
		// Synchronize with DB
		flush();
		
		// Test
		List<CourseInfo> courses = this.getCourseService().findCoursesByPeriodAndInstitute(
				period1.getId(), institute1.getId());
		assertNotNull(courses);
		assertEquals(2, courses.size());
		assertEquals(course1.getDescription(), courses.get(0).getDescription());
		
		courses = this.getCourseService().findCoursesByPeriodAndInstitute(
				period2.getId(), institute2.getId());
		assertNotNull(courses);
		assertEquals(2, courses.size());
		assertEquals(course6.getDescription(), courses.get(1).getDescription());
		
		courses = this.getCourseService().findCoursesByPeriodAndInstitute(
				period1.getId(), institute2.getId());
		assertNotNull(courses);
		assertEquals(1, courses.size());
		assertEquals(course5.getDescription(), courses.get(0).getDescription());
		
		courses = this.getCourseService().findCoursesByPeriodAndInstitute(
				period2.getId(), institute1.getId());
		assertNotNull(courses);
		assertEquals(1, courses.size());
		assertEquals(course2.getDescription(), courses.get(0).getDescription());
		
		logger.debug("----> END access to findCoursesByPeriodAndInstitute test <---- ");
	}
	
	
	public void testFindAllCoursesByInstitute () {
		logger.debug("----> BEGIN access to findAllCoursesByInstitute test <---- ");
		
		// Create Institutes
		Institute institute1 = testUtility.createUniqueInstituteInDB();
		Institute institute2 = testUtility.createUniqueInstituteInDB();
		
		// Create CourseTypes
		CourseType courseType1 = testUtility.createUniqueCourseTypeInDB();
		institute1.add(courseType1);
		CourseType courseType2 = testUtility.createUniqueCourseTypeInDB();
		institute2.add(courseType2);
		
		// Create Courses
		Course course1 = testUtility.createUniqueCourseInDB();
		courseType1.add(course1);
		Course course2 = testUtility.createUniqueCourseInDB();
		courseType1.add(course2);
		Course course3 = testUtility.createUniqueCourseInDB();
		courseType1.add(course3);
		Course course4 = testUtility.createUniqueCourseInDB();
		courseType1.add(course4);
		Course course5 = testUtility.createUniqueCourseInDB();
		courseType2.add(course5);
		Course course6 = testUtility.createUniqueCourseInDB();
		courseType2.add(course6);
		
		// Synchronize with DB
		flush();
		
		// Test
		List<CourseInfo> courses = this.getCourseService().findAllCoursesByInstitute(institute1.getId());
		assertNotNull(courses);
		assertEquals(4, courses.size());
		assertEquals(course1.getDescription(), courses.get(0).getDescription());
		
		courses = this.getCourseService().findAllCoursesByInstitute(institute2.getId());
		assertNotNull(courses);
		assertEquals(2, courses.size());
		assertEquals(course6.getDescription(), courses.get(1).getDescription());
		
		logger.debug("----> END access to findAllCoursesByInstitute test <---- ");
	}


	public void testIsNoneExistingCourseShortcut () {
		logger.debug("----> BEGIN access to isNoneExistingCourseShortcut test <---- ");
		
		//Create Secure Context
		User user = testUtility.createUserSecureContext();
		
		// Create Courses
		Course course1= testUtility.createUniqueCourseInDB();
		Course course2= testUtility.createUniqueCourseInDB();
		flush();
		
		// Test
		CourseDao courseDao = (CourseDao) this.getApplicationContext().getBean("courseDao");
		Boolean result = this.getCourseService().isNoneExistingCourseShortcut(
				courseDao.toCourseInfo(course1), course1.getShortcut());
		assertNotNull(result);
		assertTrue(result);
		
		result = this.getCourseService().isNoneExistingCourseShortcut(
				courseDao.toCourseInfo(course1), testUtility.unique("shortcut"));
		assertNotNull(result);
		assertTrue(result);
		
		result = this.getCourseService().isNoneExistingCourseShortcut(
				courseDao.toCourseInfo(course1), course2.getShortcut());
		assertNotNull(result);
		assertFalse(result);
		
		logger.debug("----> END access to isNoneExistingCourseShortcut test <---- ");
	}
	
	private LectureBuilder createInstituteStructure(AccessType accessType) {
		LectureBuilder lectureBuilder = new LectureBuilder();
		User owner = testUtility.createUserInDB();

		lectureBuilder.createInstitute(owner)
			.addCourseType()
			.addPeriod()
			.addCourse();
		
		Institute institute = lectureBuilder.getInstitute();
		lectureBuilder.getCourse().setAccessType(accessType);
		
		instituteDao.create(institute);
		
		// define security settings
		securityService.createObjectIdentity(institute, null);
		securityService.createObjectIdentity(lectureBuilder.getCourse(), institute);
		
		commit();
		return lectureBuilder;
	}
	
	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}