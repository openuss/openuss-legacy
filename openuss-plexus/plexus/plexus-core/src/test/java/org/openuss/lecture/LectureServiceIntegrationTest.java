// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

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
		User user = testUtility.createAdminSecureContext();
		
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
		assertEquals(institute2.isEnabled(), instituteTest.isEnabled());
		
		logger.debug("----> END access to getInstitutes test <---- ");
	}
	
	public void testGetInstitute () {
		logger.debug("----> BEGIN access to getInstitute test <---- ");
		
		//Create institutes
		Institute institute1 = testUtility.createUniqueInstituteInDB();
		institute1.setEnabled(true);
		
		Institute institute2 = testUtility.createUniqueInstituteInDB();
		institute2.setEnabled(true);
		
		Institute institute3 = testUtility.createUniqueInstituteInDB();
		institute3.setEnabled(true);
		
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
	
	public void testAddCourseTypeToInstitute() throws LectureException{
		logger.debug("----> add courseType <---- ");
		testUtility.createUserSecureContext();
		Institute institute = testUtility.createUniqueInstituteInDB();//createInstitute();
		//lectureService.createInstitute(institute);

		flush();
		
		CourseType courseType = CourseType.Factory.newInstance(unique("name"), unique("courseType"));
		try {
			institute = lectureService.add(institute.getId(), courseType);
			fail("LectreServiceException should have been thrown.");
		} catch (LectureServiceException lse) {
			;
		}
	}
	
	public void testAddPeriodToInstitute() throws LectureException {
		logger.debug("add period to institute");
		
		Institute institute = testUtility.createUniqueInstituteInDB();
		Period period = testUtility.createUniquePeriodInDB();
		
		try {
			this.getLectureService().add(institute.getId(), period);
			fail("LectureServiceException must have been throen.");
		} catch (LectureServiceException lse) {
			;
		}
		/*
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
		*/
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