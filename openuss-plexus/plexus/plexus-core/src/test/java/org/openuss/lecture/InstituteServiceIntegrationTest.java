// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.Date;

import org.acegisecurity.AccessDeniedException;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopDao;
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

		// Create new UniversityInfo object
		InstituteInfo instituteInfo = new InstituteInfo();
		instituteInfo.setName(testUtility.unique("testInstitute"));
		instituteInfo.setShortcut(testUtility.unique("testI"));
		instituteInfo.setOwnerName("Administrator");
		instituteInfo.setEnabled(true);
		instituteInfo.setDescription("This is a test Institute");
		instituteInfo.setDepartmentId(departmentOfficial.getId());

		// Create a User as Owner
		User owner = testUtility.createUniqueUserInDB();

		// Create Entity
		Long instituteId = this.getInstituteService().create(instituteInfo, owner.getId());
		assertNotNull(instituteId);

		// Test
		InstituteDao instituteDao = (InstituteDao) this.getApplicationContext().getBean("instituteDao");
		Institute instituteTest = instituteDao.load(instituteId);
		assertNotNull(instituteTest.getDepartment());
		assertNotNull(instituteTest.getApplication());
		assertTrue(instituteTest.getDepartment().getApplications().contains(instituteTest.getApplication()));

		DesktopDao desktopDao = (DesktopDao) this.getApplicationContext().getBean("desktopDao");
		Desktop desktop = desktopDao.findByUser(owner);
		assertNotNull(desktop);
		assertEquals(1, desktop.getInstitutes().size());
		
		// Synchronize with Database
		flush();

		// Create an NONOFFICIAL Department
		Department departmentNonOfficial = testUtility.createUniqueDepartmentInDB();
		departmentNonOfficial.setDepartmentType(DepartmentType.NONOFFICIAL);

		// Create new UniversityInfo object
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

		// Test
		Institute instituteTest2 = instituteDao.load(instituteId2);
		assertNotNull(instituteTest2.getDepartment());
		assertNull(instituteTest2.getApplication());

		desktopDao = (DesktopDao) this.getApplicationContext().getBean("desktopDao");
		desktop = desktopDao.findByUser(owner);
		assertNotNull(desktop);
		assertEquals(2, desktop.getInstitutes().size());
		
		// Synchronize with Database
		flush();

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
		
		// Test for Exception
		Institute institute1 = testUtility.createUniqueInstituteInDB();
		assertNotNull(institute1.getId());
		
		// Create official Department
		Department department = testUtility.createUniqueDepartmentInDB();
		department.setDepartmentType(DepartmentType.OFFICIAL);
		
		// Create new InstituteInfo object
		InstituteInfo instituteInfo1 = new InstituteInfo();
		instituteInfo1.setId(institute1.getId());
		instituteInfo1.setDepartmentId(department.getId());
		
		try {
			this.getInstituteService().update(instituteInfo1);
			fail("InstituteServiceException must have been thrown.");
		} catch (InstituteServiceException ise) {
			;
		}

		logger.info("----> END access to update(Institute) test");
	}

	public void testRemoveInstitute() {
		logger.info("----> BEGIN access to removeInstitute test");

		// Create an Institute
		Institute institute = testUtility.createUniqueInstituteInDB();
		assertNotNull(institute.getId());
		Department department = institute.getDepartment();
		assertNotNull(department);
		assertEquals(1, department.getInstitutes().size());
		flush();
		
		// Get Institute id
		Long id = institute.getId();
		
		// Create Desktop
		User user = testUtility.createUniqueUserInDB();
		Desktop desktop = Desktop.Factory.newInstance(user);
		desktop.getInstitutes().add(institute);
		DesktopDao desktopDao = (DesktopDao) this.getApplicationContext().getBean("desktopDao");
		Desktop desktopCreated = desktopDao.create(desktop);
		assertNotNull(desktopCreated);

		// Synchronize with Database
		flush();
		
		// Create user secure context
		testUtility.createUserSecureContext();
		try {
			this.getInstituteService().removeInstitute(institute.getId());
			fail("AccessDeniedException should have been thrown.");
		} catch (AccessDeniedException ade) {
			;
		} finally {
			testUtility.destroySecureContext();
		}
		
		// Create admin secure context
		testUtility.createAdminSecureContext();

		// Remove Institute
		this.getInstituteService().removeInstitute(institute.getId().longValue());

		// Synchronize with Database
		flush();

		// Try to load Institute again
		InstituteDao instituteDao = (InstituteDao) this.applicationContext.getBean("instituteDao");
		Institute institute2 = instituteDao.load(id);
		assertNull(institute2);
		
		assertEquals(0, department.getInstitutes().size());
		
		Desktop desktopTest = desktopDao.load(desktop.getId());
		assertNotNull(desktopTest);
		assertEquals(0, desktopTest.getInstitutes().size());

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

		logger.debug("----> END access to applyAtDepartment test <---- ");
	}

	public void testRemoveUnconfirmedApplication() {
		logger.debug("----> BEGIN access to removeUnconfirmedDepartment test <---- ");

		// Create Department
		Department department = testUtility.createUniqueDepartmentInDB();

		// Create Institute
		Institute institute = testUtility.createUniqueInstituteInDB();

		// Apply
		Application application = Application.Factory.newInstance();
		application.setDepartment(department);
		application.setInstitute(institute);
		application.setApplyingUser(testUtility.createUniqueUserInDB());
		application.setApplicationDate(new Date());

		department.getApplications().add(application);
		institute.setApplication(application);
		ApplicationDao applicationDao = (ApplicationDao) this.getApplicationContext().getBean("applicationDao");
		applicationDao.create(application);
		assertNotNull(application.getId());

		// Synchronize with Database
		flush();

		// Test
		this.getInstituteService().removeUnconfirmedApplication(application.getId());
		Application application2 = applicationDao.load(application.getId());
		assertNull(application2);
		assertFalse(department.getApplications().contains(application));
		assertNull(institute.getApplication());

		logger.debug("----> END access to removeUnconfirmedDepartment test <---- ");
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
		ApplicationInfo applicationInfo2 = this.getInstituteService().findApplicationByInstitute(institute.getId());
		assertNotNull(applicationInfo2);
		assertEquals(applicationId, applicationInfo2.getId());

		logger.info("----> END access to findApplicationByInstitute test");
	}
}