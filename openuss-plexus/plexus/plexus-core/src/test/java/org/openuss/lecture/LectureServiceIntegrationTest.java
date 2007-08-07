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
	
	public void testCreateInstitute() {
		logger.info("----> BEGIN access to create(Institute) test");

		//Create new UniversityInfo object
		InstituteInfo instituteInfo = new InstituteInfo();
		instituteInfo.setName(testUtility.unique("testInstitute"));
		instituteInfo.setShortcut(testUtility.unique("testI"));
		instituteInfo.setOwnerName("Administrator");
		instituteInfo.setEnabled(true);
		instituteInfo.setDescription("This is a test Institute");
		
		//Create a User as Owner
		User owner = testUtility.createUniqueUserInDB();
		
		//Create Entity
		Long instituteId = lectureService.create(instituteInfo, owner.getId());
		assertNotNull(instituteId);
		
		//Synchronize with Database
		flush();

		logger.info("----> END access to create(Institute) test");
	}
	
	public void testUpdateInstitute() {
		logger.info("----> BEGIN access to update(Institute) test");

		//Create a default University
		Institute institute = testUtility.createUniqueInstituteInDB();
		assertNotNull(institute.getId());
		
		//Create new UniversityInfo object
		InstituteInfo instituteInfo = new InstituteInfo();
		instituteInfo.setId(institute.getId());
		instituteInfo.setName(testUtility.unique("testInstitute"));
		instituteInfo.setShortcut(testUtility.unique("testI"));
		instituteInfo.setOwnerName("Administrator");
		instituteInfo.setEnabled(true);
		instituteInfo.setDescription("This is a test Institute at "+testUtility.unique("time"));
		
		// Check
		assertTrue(instituteInfo.getId().longValue() == institute.getId().longValue());
		assertFalse(instituteInfo.getName().compareTo(institute.getName()) == 0);
		assertFalse(instituteInfo.getShortcut().compareTo(institute.getShortcut()) == 0);
		assertFalse(instituteInfo.getDescription().compareTo(institute.getDescription()) == 0);

		//Synchronize with Database
		flush();
		
		//Update University
		lectureService.update(instituteInfo);

		//Check
		assertTrue(instituteInfo.getId().longValue() == institute.getId().longValue());
		assertTrue(instituteInfo.getName().compareTo(institute.getName()) == 0);
		assertTrue(instituteInfo.getShortcut().compareTo(institute.getShortcut()) == 0);
		assertTrue(instituteInfo.getDescription().compareTo(institute.getDescription()) == 0);
		
		//Synchronize with Database
		flush();
		
		logger.info("----> END access to update(Institute) test");
	}
	
	public void testRemoveInstitute() {
		logger.info("----> BEGIN access to removeInstitute test");
		
		//Create Secure context
		User user = testUtility.createSecureContext();
		
		//Create an Institute
		Institute institute = testUtility.createUniqueInstituteInDB();
		assertNotNull(institute.getId());
		
		//Get Institute id
		Long id = institute.getId();
		
		//Synchronize with Database
		flush();

		//Remove Institute
		this.getLectureService().removeInstitute(id);
		
		//Synchronize with Database
		flush();
		
		//Try to load Institute again
		InstituteDao instituteDao = (InstituteDao) this.applicationContext.getBean("instituteDao");
		Institute institute2 = instituteDao.load(id);
		assertNull(institute2);

		logger.info("----> END access to removeInstitute test");		
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