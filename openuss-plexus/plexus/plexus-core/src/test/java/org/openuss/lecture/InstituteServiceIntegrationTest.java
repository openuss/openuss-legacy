// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.Date;

import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate InstituteService class.
 * @see org.openuss.lecture.InstituteService
 * @author Ron Haus
 */
public class InstituteServiceIntegrationTest extends InstituteServiceIntegrationTestBase {
	
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
		Long instituteId = this.getInstituteService().create(instituteInfo, owner.getId());
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
		this.getInstituteService().update(instituteInfo);

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
		testUtility.createSecureContext();
		
		//Create an Institute
		Institute institute = testUtility.createUniqueInstituteInDB();
		assertNotNull(institute.getId());
		
		//Get Institute id
		Long id = institute.getId();
		
		//Synchronize with Database
		flush();

		//Remove Institute
		this.getInstituteService().removeInstitute(id);
		
		//Synchronize with Database
		flush();
		
		//Try to load Institute again
		InstituteDao instituteDao = (InstituteDao) this.applicationContext.getBean("instituteDao");
		Institute institute2 = instituteDao.load(id);
		assertNull(institute2);

		logger.info("----> END access to removeInstitute test");		
	}

	public void testFindInstitute () {
		logger.debug("----> BEGIN access to findInstitute test <---- ");
		
		//Create institutes
		Institute institute = testUtility.createUniqueInstituteInDB();
		institute.setEnabled(true);
		
		flush();
		
		//Test
		InstituteInfo instituteInfo = this.getInstituteService().findInstitute(institute.getId());
		assertNotNull(instituteInfo);
		assertEquals(institute.getId(), instituteInfo.getId());
		
		
		logger.debug("----> END access to findInstitute test <---- ");
	}
	
	public void testApplyAtDepartment () {
		logger.debug("----> BEGIN access to applyAtDepartment test <---- ");
		
		// Create Department
		Department department = testUtility.createUniqueDepartmentInDB();
		
		// Create Institute
		Institute institute = testUtility.createUniqueInstituteInDB();
		
		flush();
		
		// Create Application
		ApplicationInfo applicationInfo = new ApplicationInfo();
		applicationInfo.setDepartmentId(department.getId());
		applicationInfo.setInstituteId(institute.getId());
		applicationInfo.setApplyingUserId(testUtility.createUniqueUserInDB().getId());
		
		// Test
		Long applicationId = this.getInstituteService().applyAtDepartment(applicationInfo);
		assertNotNull(applicationId);
		
		ApplicationDao applicationDao = (ApplicationDao) this.getApplicationContext().getBean("applicationDao");
		Application application = applicationDao.load(applicationId);
		assertEquals(department, application.getDepartment());
		assertEquals(institute, application.getInstitute());		
		
		logger.debug("----> END access to applyAtDepartment test <---- ");
	}
	
	public void testRemoveUnconfirmedApplication () {
		logger.debug("----> BEGIN access to removeUnconfirmedDepartment test <---- ");

		// Create Department
		Department department = testUtility.createUniqueDepartmentInDB();
		
		// Create Institute
		Institute institute = testUtility.createUniqueInstituteInDB();
		
		//Apply
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
		
		//Synchronize with Database
		flush();
		
		// Test
		this.getInstituteService().removeUnconfirmedApplication(application.getId());
		Application application2 = applicationDao.load(application.getId());
		assertNull(application2);
		assertFalse(department.getApplications().contains(application));
		assertNull(institute.getApplication());
		
		logger.debug("----> END access to removeUnconfirmedDepartment test <---- ");
	}
}