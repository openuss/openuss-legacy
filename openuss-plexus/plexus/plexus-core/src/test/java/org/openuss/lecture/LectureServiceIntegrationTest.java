// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.security.AuthorityDao;
import org.openuss.security.User;
import org.openuss.security.UserDao;

/**
 * JUnit Test for Spring Hibernate LectureService class.
 * 
 * @see org.openuss.lecture.LectureService
 * @author Ron Haus
 */
public class LectureServiceIntegrationTest extends LectureServiceIntegrationTestBase {

	private static final Logger logger = Logger.getLogger(LectureServiceIntegrationTest.class);

	private UserDao userDao;

	private AuthorityDao authorityDao;
	
	private CourseTypeDao courseTypeDao;

	private User user;

	public LectureServiceIntegrationTest() {
		setDefaultRollback(true);
	}

	public void testUserDaoInjections() {
		assertNotNull(userDao);
		assertNotNull(authorityDao);
	}
	
	public void testGetInstitutes () {
		logger.debug("----> BEGIN access to getInstitutes test <---- ");
		
		//Create Secure context
		User user = testUtility.createSecureContext();
		
		//Create institutes
		Institute institute1 = testUtility.createUniqueInstituteInDB();
		institute1.setEnabled(true);
		
		Institute institute2 = testUtility.createUniqueInstituteInDB();
		institute1.setEnabled(true);
		
		Institute institute3 = testUtility.createUniqueInstituteInDB();
		institute1.setEnabled(true);
		
		Institute institute4 = testUtility.createUniqueInstituteInDB();
		institute1.setEnabled(false);
		
		//Synchronize with Database
		flush();
		
		// Test
		Institute instituteTest = this.getLectureService().getInstitute(institute2.getId());
		assertNotNull(instituteTest);
		assertEquals(institute2.getId(), instituteTest.getId());
		assertEquals(institute2.getName(), instituteTest.getName());
		assertEquals(institute2.getDescription(), instituteTest.getDescription());
		assertEquals(institute2.getDepartment(), instituteTest.getDepartment());
		assertEquals(institute2.getCourseTypes().size(), instituteTest.getCourseTypes().size());
		assertEquals(institute2.getActiveCourses().size(), instituteTest.getActiveCourses().size());
		assertEquals(institute2.getEnabled(), instituteTest.getEnabled());
		
		logger.debug("----> END access to getInstitutes test <---- ");
	}
	
	public void testGetInstitute () {
		logger.debug("----> BEGIN access to getInstitute test <---- ");
		
		//Create institutes
		Institute institute1 = testUtility.createUniqueInstituteInDB();
		institute1.setEnabled(true);
		
		Institute institute2 = testUtility.createUniqueInstituteInDB();
		institute1.setEnabled(true);
		
		Institute institute3 = testUtility.createUniqueInstituteInDB();
		institute1.setEnabled(true);
		
		logger.debug("----> END access to getInstitute test <---- ");
	}
	
	public void testGetCourseType () {
		logger.debug("----> BEGIN access to getCourseType test <---- ");
		
		//Create Secure context
		User user = testUtility.createSecureContext();
		
		//Create CourseType
		CourseType courseType = testUtility.createUniqueCourseTypeInDB();
		flush();
		
		// Test
		CourseType courseTypeTest = this.getLectureService().getCourseType(courseType.getId());
		assertNotNull(courseTypeTest);
		
		logger.debug("----> END access to getCourseType test <---- ");
	}
	
	public void testGetCourse () {
		logger.debug("----> BEGIN access to getCourse test <---- ");
		
		//Create Secure context
		User user = testUtility.createSecureContext();
		
		//Create Course
		Course course= testUtility.createUniqueCourseInDB();
		flush();
		
		// Test
		Course courseTest = this.getLectureService().getCourse(course.getId());
		assertNotNull(courseTest);
		
		logger.debug("----> END access to getCourse test <---- ");
	}
	
	public void testIsNoneExistingInstituteShortcut () {
		logger.debug("----> BEGIN access to isNoneExistingInstituteShortcut test <---- ");
		
		//Create Secure Context
		User user = testUtility.createSecureContext();
		
		// Create institutes
		Institute institute1= testUtility.createUniqueInstituteInDB();
		Institute institute2= testUtility.createUniqueInstituteInDB();
		flush();
		
		// Test
		Boolean result = this.getLectureService().isNoneExistingInstituteShortcut(institute1, institute1.getShortcut());
		assertNotNull(result);
		assertTrue(result);
		
		result = this.getLectureService().isNoneExistingInstituteShortcut(institute1, testUtility.unique("shortcut"));
		assertNotNull(result);
		assertTrue(result);
		
		result = this.getLectureService().isNoneExistingInstituteShortcut(institute1, institute2.getShortcut());
		assertNotNull(result);
		assertFalse(result);
		
		logger.debug("----> END access to isNoneExistingInstituteShortcut test <---- ");
	}
	
	public void testIsNoneExistingCourseShortcut () {
		logger.debug("----> BEGIN access to isNoneExistingCourseShortcut test <---- ");
		
		//Create Secure Context
		User user = testUtility.createSecureContext();
		
		// Create CourseTypes
		Course course1= testUtility.createUniqueCourseInDB();
		Course course2= testUtility.createUniqueCourseInDB();
		flush();
		
		// Test
		Boolean result = this.getLectureService().isNoneExistingCourseShortcut(course1, course1.getShortcut());
		assertNotNull(result);
		assertTrue(result);
		
		result = this.getLectureService().isNoneExistingCourseShortcut(course1, testUtility.unique("shortcut"));
		assertNotNull(result);
		assertTrue(result);
		
		result = this.getLectureService().isNoneExistingCourseShortcut(course1, course2.getShortcut());
		assertNotNull(result);
		assertFalse(result);
		
		logger.debug("----> END access to isNoneExistingCourseShortcut test <---- ");
	}
	
	public void testIsNoneExistingCourseTypeShortcut () {
		logger.debug("----> BEGIN access to isNoneExistingCourseTypeShortcut test <---- ");
		
		//Create Secure Context
		User user = testUtility.createSecureContext();
		
		// Create CourseTypes
		CourseType courseType1= testUtility.createUniqueCourseTypeInDB();
		CourseType courseType2= testUtility.createUniqueCourseTypeInDB();
		flush();
		
		// Test
		Boolean result = this.getLectureService().isNoneExistingCourseTypeShortcut(courseType1, courseType1.getShortcut());
		assertNotNull(result);
		assertTrue(result);
		
		result = this.getLectureService().isNoneExistingCourseTypeShortcut(courseType1, testUtility.unique("shortcut"));
		assertNotNull(result);
		assertTrue(result);
		
		result = this.getLectureService().isNoneExistingCourseTypeShortcut(courseType1, courseType2.getShortcut());
		assertNotNull(result);
		assertFalse(result);
		
		logger.debug("----> END access to isNoneExistingCourseTypeShortcut test <---- ");
	}
	
	public void testIsNoneExistingCourseTypeName () {
		logger.debug("----> BEGIN access to isNoneExistingCourseTypeName test <---- ");
		
		//Create Secure Context
		User user = testUtility.createSecureContext();
		
		// Create CourseTypes
		CourseType courseType1= testUtility.createUniqueCourseTypeInDB();
		CourseType courseType2= testUtility.createUniqueCourseTypeInDB();
		flush();
		
		// Test
		Boolean result = this.getLectureService().isNoneExistingCourseTypeName(courseType1, courseType1.getName());
		assertNotNull(result);
		assertTrue(result);
		
		result = this.getLectureService().isNoneExistingCourseTypeName(courseType1, testUtility.unique("name"));
		assertNotNull(result);
		assertTrue(result);
		
		result = this.getLectureService().isNoneExistingCourseTypeName(courseType1, courseType2.getName());
		assertNotNull(result);
		assertFalse(result);
		
		logger.debug("----> END access to isNoneExistingCourseTypeName test <---- ");
	}
	
	public void testAddCourseTypeToInstitute() throws LectureException{
		logger.debug("----> add courseType <---- ");
		user = testUtility.createSecureContext();
		Institute institute = createInstitute();
		lectureService.createInstitute(institute);

		commit();
		
		CourseType courseType = CourseType.Factory.newInstance(unique("name"), unique("courseType"));
		institute = lectureService.add(institute.getId(), courseType);
		assertTrue(institute.getCourseTypes().contains(courseType));
		
		commit();
		
		assertNotNull(courseType.getId());
		
		lectureService.removeInstitute(institute.getId());
		setComplete();
	}
	
	
	public void testAddPeriodToInstitute() throws LectureException {
		logger.debug("add period to institute");
		user = testUtility.createSecureContext();
		
		Institute institute = createInstitute();
		lectureService.createInstitute(institute);
		
		commit();
		
		Period period = Period.Factory.newInstance();
		period.setName("WS06/07");
		period.setDescription("Wintersemester 2006 / 2007");
		
		institute = lectureService.add(institute.getId(), period);
		
		//assertTrue(institute.getPeriods().contains(period));
		commit();
		
		assertNotNull(period.getId());
		
		lectureService.removeInstitute(institute.getId());
		
		setComplete();
	}
	
	private Institute createInstitute() {
		Institute institute = Institute.Factory.newInstance();
		institute.setName(unique("institute name"));
		institute.setShortcut(unique("institute"));
		institute.setOwnerName("ownername");
		institute.setEmail("email@institute");
		//institute.setOwner(user);
		institute.setLocale("de");
		return institute;
	}
	
	private String unique(String name) {
		return name+" - "+System.currentTimeMillis();
	}
	

	public AuthorityDao getRoleDao() {
		return authorityDao;
	}
	
	public void setRoleDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}
	
	public UserDao getUserDao() {
		return userDao;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setCourseTypeDao(CourseTypeDao courseTypeDao) {
		this.courseTypeDao = courseTypeDao;
	}

	public CourseTypeDao getCourseTypeDao() {
		return courseTypeDao;
	}

}