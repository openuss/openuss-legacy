// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.Date;
import java.util.GregorianCalendar;

import org.acegisecurity.AccessDeniedException;
import org.openuss.security.User;
import org.openuss.security.UserDao;

/**
 * JUnit Test for Spring Hibernate InstituteService class.
 * 
 * @see org.openuss.lecture.InstituteService
 * @author Ron Haus
 * @author Florian Dondorf
 */
public class InstituteServiceIntegrationTest extends InstituteServiceIntegrationTestBase {

	public void testCreateInstitute() {
		logger.info("----> BEGIN access to create(Institute) test");

		// Create an OFFICIAL Department
		Department departmentOfficial = testUtility.createUniqueDepartmentInDB();
		departmentOfficial.setDepartmentType(DepartmentType.OFFICIAL);

		// Create new InstituteInfo object
		InstituteInfo instituteInfo = new InstituteInfo();
		instituteInfo.setName(testUtility.unique("testInstitute"));
		instituteInfo.setShortcut(testUtility.unique("testI"));
		instituteInfo.setOwnerName("Administrator");
		instituteInfo.setEnabled(true);
		instituteInfo.setDescription("This is a test Institute");
		instituteInfo.setDepartmentId(departmentOfficial.getId()); //Should be ignored by createInstitute

		// Create a User as Owner
		User owner = testUtility.createUniqueUserInDB();

		// Create Entity
		Long instituteId = instituteService.create(instituteInfo, owner.getId());
		assertNotNull(instituteId);

		// Synchronize with Database
		flush();
		
		// Test
		InstituteDao instituteDao = (InstituteDao) this.getApplicationContext().getBean("instituteDao");
		DepartmentDao departmentDao = (DepartmentDao) this.getApplicationContext().getBean("departmentDao");
		
		Institute instituteTest = instituteDao.load(instituteId);
		assertNotNull(instituteTest);
		assertNull(instituteTest.getDepartment()); // One needs to call applyAtUniversity right after
		instituteService.applyAtDepartment(instituteId, departmentOfficial.getId(), owner.getId());
		assertNotNull(instituteTest.getDepartment());
		
		Department departmentDefault = departmentDao.findByUniversityAndDefault(departmentOfficial.getUniversity(), true);
		assertEquals(departmentDefault.getId(), instituteTest.getDepartment().getId());
		assertTrue(departmentDefault.getInstitutes().contains(instituteTest));
		assertEquals(2, instituteTest.getApplications().size());
		int confirmed = 0;
		int notconfirmed = 0;
		for (Application application:instituteTest.getApplications()) {
			if (application.isConfirmed()) {
				assertEquals(departmentDefault.getId(), application.getDepartment().getId());
				confirmed++;
			} else {
				assertEquals(departmentOfficial.getId(), application.getDepartment().getId());
				notconfirmed++;
			}
		}
		assertEquals(1,confirmed);
		assertEquals(1,notconfirmed);
		
		// Create an NONOFFICIAL Department
		Department departmentNonOfficial = testUtility.createUniqueDepartmentInDB();
		departmentNonOfficial.setDepartmentType(DepartmentType.NONOFFICIAL);

		// Create another new InstituteInfo object
		InstituteInfo instituteInfo2 = new InstituteInfo();
		instituteInfo2.setName(testUtility.unique("testInstitute"));
		instituteInfo2.setShortcut(testUtility.unique("testI"));
		instituteInfo2.setOwnerName("Administrator");
		instituteInfo2.setEnabled(true);
		instituteInfo2.setDescription("This is a test Institute");
		instituteInfo2.setDepartmentId(departmentNonOfficial.getId());
		
		// Create Entity
		Long instituteId2 = this.getInstituteService().create(instituteInfo2, owner.getId());
		assertNotNull(instituteId2);
		
		// Synchronize with Database
		flush();
		
		// Test		
		Institute instituteTest2 = instituteDao.load(instituteId2);
		assertNotNull(instituteTest2);
		assertNull(instituteTest2.getDepartment()); // One needs to call applyAtUniversity right after
		instituteService.applyAtDepartment(instituteId2, departmentNonOfficial.getId(), owner.getId());
		assertNotNull(instituteTest2.getDepartment());
		
		assertEquals(departmentNonOfficial.getId(), instituteTest2.getDepartment().getId());
		assertTrue(departmentNonOfficial.getInstitutes().contains(instituteTest2));
		assertEquals(1, instituteTest2.getApplications().size());
		confirmed = 0;
		notconfirmed = 0;
		for (Application application:instituteTest2.getApplications()) {
			if (application.isConfirmed()) {
				assertEquals(departmentNonOfficial.getId(), application.getDepartment().getId());
				confirmed++;
			}
		}
		assertEquals(1,confirmed);
		assertEquals(0,notconfirmed);

		logger.info("----> END access to create(Institute) test");
	}

	public void testUpdateInstitute() {
		logger.info("----> BEGIN access to update(Institute) test");

		// Create a default Institute
		Institute institute = testUtility.createUniqueInstituteInDB();
		assertNotNull(institute.getId());

		// Create new InstituteInfo object
		InstituteInfo instituteInfo = new InstituteInfo();
		instituteInfo.setId(institute.getId());
		instituteInfo.setName(testUtility.unique("testInstitute"));
		instituteInfo.setShortcut(testUtility.unique("testI"));
		instituteInfo.setOwnerName("Administrator");
		instituteInfo.setEnabled(true);
		instituteInfo.setDescription("This is a test Institute at " + testUtility.unique("time"));
		instituteInfo.setDepartmentId(institute.getDepartment().getId());

		// Check
		assertTrue(instituteInfo.getId().longValue() == institute.getId().longValue());
		assertFalse(instituteInfo.getName().compareTo(institute.getName()) == 0);
		assertFalse(instituteInfo.getShortcut().compareTo(institute.getShortcut()) == 0);
		assertFalse(instituteInfo.getDescription().compareTo(institute.getDescription()) == 0);

		// Synchronize with Database
		flush();

		// Update Institute
		this.getInstituteService().update(instituteInfo);

		// Check
		assertTrue(instituteInfo.getId().longValue() == institute.getId().longValue());
		assertTrue(instituteInfo.getName().compareTo(institute.getName()) == 0);
		assertTrue(instituteInfo.getShortcut().compareTo(institute.getShortcut()) == 0);
		assertTrue(instituteInfo.getDescription().compareTo(institute.getDescription()) == 0);

		// Synchronize with Database
		flush();

		logger.info("----> END access to update(Institute) test");
	}

	public void testRemoveInstitute() {
		logger.info("----> BEGIN access to removeInstitute test");

		// Create an Institute without Courses
		Institute institute = testUtility.createUniqueInstituteInDB();
		Long instituteId = institute.getId();
		assertNotNull(instituteId);
		Department department = institute.getDepartment();
		assertNotNull(department);
		int sizeBefore = department.getInstitutes().size();
		
		// Create Department
		Department department1 = testUtility.createUniqueDepartmentInDB();
		
		// Create Application
		Application application = new ApplicationImpl();
		application.setApplicationDate(new Date(new GregorianCalendar(12, 07, 2006).getTimeInMillis()));
		application.setApplyingUser(testUtility.createUniqueUserInDB());
		application.setConfirmed(false);
		application.add(department1);
		application.add(institute);

		flush();

		// Create an Institute with CourseTypes and Courses
		Course course = testUtility.createUniqueCourseInDB();
		Institute institute2 = course.getCourseType().getInstitute();
		Long instituteId2 = institute2.getId();

		// Synchronize with Database
		flush();

		// Remove Institutes
		try {
			this.getInstituteService().removeInstitute(instituteId2);
			fail("Exception should have been thrown");
		} catch (Exception e) {
			;
		}
		this.getInstituteService().removeInstitute(instituteId);

		// Synchronize with Database
		flush();

		// Try to load Institute again
		InstituteDao instituteDao = (InstituteDao) this.applicationContext.getBean("instituteDao");
		Institute institute3 = instituteDao.load(instituteId);
		assertNull(institute3);

		assertEquals(sizeBefore - 1, department.getInstitutes().size());

		logger.info("----> END access to removeInstitute test");
	}

	public void testRemoveCompleteInstituteTree() {
		logger.info("----> BEGIN access to removeInstitute test");

		// Create an Institute with CourseTypes and Courses
		Course course = testUtility.createUniqueCourseInDB();
		Institute institute = course.getCourseType().getInstitute();
		Long instituteId = institute.getId();
		assertNotNull(instituteId);
		Department department = institute.getDepartment();
		assertNotNull(department);
		int sizeBefore = department.getInstitutes().size();

		// Synchronize with Database
		flush();

		// Create user secure context
		testUtility.createUserSecureContext();
		try {
			this.getInstituteService().removeCompleteInstituteTree(instituteId);
			fail("AccessDeniedException should have been thrown.");
		} catch (AccessDeniedException ade) {
			;
		} finally {
			testUtility.destroySecureContext();
		}

		// Create admin secure context
		testUtility.createAdminSecureContext();

		// Remove Institute
		this.getInstituteService().removeCompleteInstituteTree(instituteId);

		// Synchronize with Database
		flush();

		// Try to load Institute again
		InstituteDao instituteDao = (InstituteDao) this.applicationContext.getBean("instituteDao");
		Institute institute2 = instituteDao.load(instituteId);
		assertNull(institute2);

		assertEquals(sizeBefore - 1, department.getInstitutes().size());

		testUtility.destroySecureContext();
		logger.info("----> END access to removeInstitute test");
	}

	public void testFindInstitute() {
		logger.debug("----> BEGIN access to findInstitute test <---- ");

		// Create institutes
		Institute institute = testUtility.createUniqueInstituteInDB();
		institute.setEnabled(true);

		flush();

		// Test
		InstituteInfo instituteInfo = this.getInstituteService().findInstitute(institute.getId());
		assertNotNull(instituteInfo);
		assertEquals(institute.getId(), instituteInfo.getId());

		logger.debug("----> END access to findInstitute test <---- ");
	}

	public void testApplyAtDepartment() {
		logger.debug("----> BEGIN access to applyAtDepartment test <---- ");
		
		User user = testUtility.createUniqueUserInDB();
		Institute institute = testUtility.createUniqueInstituteInDB();
		DepartmentDao departmentDao = (DepartmentDao) this.applicationContext.getBean("departmentDao");
		Department department = departmentDao.findByUniversityAndDefault(institute.getDepartment().getUniversity(), true);
		
		testUtility.createAdminSecureContext();
		
		Long applicationId = this.getInstituteService().applyAtDepartment(institute.getId(), department.getId(), user.getId());
		assertNotNull(applicationId);
		logger.debug("----> Application "+applicationId+" <---- ");
		
		testUtility.destroySecureContext();
/*
		// Create Department
		Department department = testUtility.createUniqueDepartmentInDB();
		department.setDepartmentType(DepartmentType.NONOFFICIAL);

		// Create Institute
		Institute institute = testUtility.createUniqueInstituteInDB();

		flush();

		// Create Application
		UserDao userDao = (UserDao) this.getApplicationContext().getBean("userDao");
		InstituteDao instituteDao = (InstituteDao) this.getApplicationContext().getBean("instituteDao");
		DepartmentDao departmentDao = (DepartmentDao) this.getApplicationContext().getBean("departmentDao");

		ApplicationInfo applicationInfo = new ApplicationInfo();
		applicationInfo.setDepartmentInfo(departmentDao.toDepartmentInfo(department));
		applicationInfo.setInstituteInfo(instituteDao.toInstituteInfo(institute));
		applicationInfo.setApplyingUserInfo(userDao.toUserInfo(testUtility.createUniqueUserInDB()));

		// Test
		try {
			this.getInstituteService().applyAtDepartment(applicationInfo);
			fail("Exception should have been thrown");
		} catch (Exception e) {
			;
		}

		department.setDepartmentType(DepartmentType.OFFICIAL);
		Long applicationId = this.getInstituteService().applyAtDepartment(applicationInfo);
		assertNotNull(applicationId);
		assertNotNull(institute.getApplication());

		ApplicationDao applicationDao = (ApplicationDao) this.getApplicationContext().getBean("applicationDao");
		Application application = applicationDao.load(applicationId);
		assertEquals(department, application.getDepartment());
		assertEquals(institute, application.getInstitute());
*/
		logger.debug("----> END access to applyAtDepartment test <---- ");
	}

	public void testSetInstituteStatus() {
		logger.info("----> BEGIN access to setInstituteStatus test");

		// Create institute
		Institute institute = testUtility.createUniqueInstituteInDB();
		assertNotNull(institute);
		assertTrue(institute.isEnabled());

		// Synchronize with DB
		flush();

		testUtility.createUserSecureContext();
		try {
			this.getInstituteService().setInstituteStatus(institute.getId(), false);
			fail("AccessDeniedException should have been thrown.");

		} catch (AccessDeniedException ade) {
			;
		} finally {
			testUtility.destroySecureContext();
		}

		testUtility.createAdminSecureContext();
		this.getInstituteService().setInstituteStatus(institute.getId(), false);

		// Load Institute
		InstituteDao instituteDao = (InstituteDao) this.getApplicationContext().getBean("instituteDao");
		Institute instituteTest = instituteDao.load(institute.getId());

		assertFalse(instituteTest.isEnabled());
		testUtility.destroySecureContext();

		testUtility.createAdminSecureContext();
		this.getInstituteService().setInstituteStatus(institute.getId(), true);

		// Load Institute
		Institute instituteTest1 = instituteDao.load(institute.getId());

		assertTrue(instituteTest1.isEnabled());
		testUtility.destroySecureContext();

		logger.info("----> END access to setInstituteStatus test");
	}

	public void testFindApplicationByInstitute() {
		logger.info("----> BEGIN access to findApplicationByInstitute test");

		// Create Department
		Department department = testUtility.createUniqueDepartmentInDB();
		department.setDepartmentType(DepartmentType.OFFICIAL);

		// Create Institute
		Institute institute = testUtility.createUniqueInstituteInDB();

		flush();

		// Create Application
		UserDao userDao = (UserDao) this.getApplicationContext().getBean("userDao");
		InstituteDao instituteDao = (InstituteDao) this.getApplicationContext().getBean("instituteDao");
		DepartmentDao departmentDao = (DepartmentDao) this.getApplicationContext().getBean("departmentDao");

		ApplicationInfo applicationInfo = new ApplicationInfo();
		applicationInfo.setDepartmentInfo(departmentDao.toDepartmentInfo(department));
		applicationInfo.setInstituteInfo(instituteDao.toInstituteInfo(institute));
		applicationInfo.setApplyingUserInfo(userDao.toUserInfo(testUtility.createUniqueUserInDB()));

		Long applicationId = this.getInstituteService().applyAtDepartment(applicationInfo);
		assertNotNull(applicationId);

		// Test
		/*
		ApplicationInfo applicationInfo2 = this.getInstituteService().findApplicationByInstitute(institute.getId());
		assertNotNull(applicationInfo2);
		assertEquals(applicationId, applicationInfo2.getId());
*/
		logger.info("----> END access to findApplicationByInstitute test");
	}
}