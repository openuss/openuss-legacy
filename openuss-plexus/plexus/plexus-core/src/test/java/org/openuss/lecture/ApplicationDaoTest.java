// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.Date;

import org.openuss.TestUtility;

/**
 * JUnit Test for Spring Hibernate ApplicationDao class.
 * 
 * @see org.openuss.lecture.ApplicationDao
 * @author Ron Haus
 */
public class ApplicationDaoTest extends ApplicationDaoTestBase {

	private TestUtility testUtility;

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public void testApplicationDaoCreate() {
		Application application = Application.Factory.newInstance();
		application.setApplicationDate(new Date());
		application.setDepartment(testUtility.createUniqueDepartmentInDB());
		application.setInstitute(testUtility.createUniqueInstituteInDB());
		application.setConfirmed(false);
		application.setApplyingUser(testUtility.createUniqueUserInDB());
		application.setConfirmingUser(testUtility.createUniqueUserInDB());
		assertNull(application.getId());
		applicationDao.create(application);
		assertNotNull(application.getId());

		// Synchronize with Database
		flush();
	}

	public void testApplicationDaoToApplicationInfo() {

		// Create a complete Application
		Application application = Application.Factory.newInstance();
		application.setApplicationDate(new Date());
		application.setDepartment(testUtility.createUniqueDepartmentInDB());
		application.setInstitute(testUtility.createUniqueInstituteInDB());
		application.setConfirmed(false);
		application.setDescription("A unique Application");
		application.setApplyingUser(testUtility.createUniqueUserInDB());
		application.setConfirmingUser(testUtility.createUniqueUserInDB());
		application.setApplicationDate(new Date());
		application.setConfirmationDate(new Date());
		applicationDao.create(application);
		assertNotNull(application.getId());

		// Test ValueObject
		ApplicationInfo applicationInfo = applicationDao.toApplicationInfo(application);

		assertEquals(application.getId(), applicationInfo.getId());
		assertEquals(application.getDescription(), applicationInfo.getDescription());
		assertEquals(application.getConfirmationDate(), applicationInfo.getConfirmationDate());
		assertEquals(application.getApplicationDate(), applicationInfo.getApplicationDate());
		assertEquals(application.getConfirmed(), applicationInfo.getConfirmed());
		assertEquals(application.getConfirmingUser().getId(), applicationInfo.getConfirmingUserId());
		assertEquals(application.getApplyingUser().getId(), applicationInfo.getApplyingUserId());
		assertEquals(application.getDepartment().getId(), applicationInfo.getDepartmentId());
		assertEquals(application.getInstitute().getId(), applicationInfo.getInstituteId());
	}

	public void testApplicationDaoApplicationInfoToEntity() {

		// Create a complete Application
		Application applicationDefault = Application.Factory.newInstance();
		applicationDefault.setApplicationDate(new Date());
		applicationDefault.setDepartment(testUtility.createUniqueDepartmentInDB());
		applicationDefault.setInstitute(testUtility.createUniqueInstituteInDB());
		applicationDefault.setConfirmed(false);
		applicationDefault.setDescription("A unique Application");
		applicationDefault.setApplyingUser(testUtility.createUniqueUserInDB());
		applicationDefault.setConfirmingUser(testUtility.createUniqueUserInDB());
		applicationDefault.setApplicationDate(new Date());
		applicationDefault.setConfirmationDate(new Date());
		applicationDao.create(applicationDefault);
		assertNotNull(applicationDefault.getId());

		// Create the corresponding ValueObject
		ApplicationInfo applicationInfo1 = new ApplicationInfo();
		applicationInfo1.setId(applicationDefault.getId());
		applicationInfo1.setDescription(applicationDefault.getDescription());
		applicationInfo1.setConfirmed(applicationDefault.getConfirmed());
		applicationInfo1.setApplicationDate(applicationDefault.getApplicationDate());
		applicationInfo1.setConfirmationDate(applicationDefault.getConfirmationDate());
		applicationInfo1.setConfirmingUserId(applicationDefault.getConfirmingUser().getId());
		applicationInfo1.setApplyingUserId(applicationDefault.getApplyingUser().getId());
		applicationInfo1.setDepartmentId(applicationDefault.getDepartment().getId());
		applicationInfo1.setInstituteId(applicationDefault.getInstitute().getId());

		// Test toEntity
		Application application1 = applicationDao.applicationInfoToEntity(applicationInfo1);

		assertEquals(applicationInfo1.getId(), application1.getId());
		assertEquals(applicationInfo1.getDescription(), application1.getDescription());
		assertEquals(applicationInfo1.getConfirmationDate(), application1.getConfirmationDate());
		assertEquals(applicationInfo1.getApplicationDate(), application1.getApplicationDate());
		assertEquals(applicationInfo1.getConfirmed(), application1.getConfirmed());
		assertEquals(applicationInfo1.getConfirmingUserId(), application1.getConfirmingUser().getId());
		assertEquals(applicationInfo1.getApplyingUserId(), application1.getApplyingUser().getId());
		assertEquals(applicationInfo1.getDepartmentId(), application1.getDepartment().getId());
		assertEquals(applicationInfo1.getInstituteId(), application1.getInstitute().getId());

		// Create a new ValueObject (no Entity available)
		ApplicationInfo applicationInfo2 = new ApplicationInfo();
		applicationInfo2.setApplicationDate(new Date());
		applicationInfo2.setDepartmentId(testUtility.createUniqueDepartmentInDB().getId());
		applicationInfo2.setInstituteId(testUtility.createUniqueInstituteInDB().getId());
		applicationInfo2.setConfirmed(false);
		applicationInfo2.setDescription("A unique Application");
		applicationInfo2.setApplyingUserId(testUtility.createUniqueUserInDB().getId());
		applicationInfo2.setConfirmingUserId(testUtility.createUniqueUserInDB().getId());
		applicationInfo2.setApplicationDate(new Date());
		applicationInfo2.setConfirmationDate(new Date());

		// Test toEntity
		Application application2 = applicationDao.applicationInfoToEntity(applicationInfo2);

		assertEquals(applicationInfo2.getId(), application2.getId());
		assertEquals(applicationInfo2.getDescription(), application2.getDescription());
		assertEquals(applicationInfo2.getConfirmationDate(), application2.getConfirmationDate());
		assertEquals(applicationInfo2.getApplicationDate(), application2.getApplicationDate());
		assertEquals(applicationInfo2.getConfirmed(), application2.getConfirmed());
		assertEquals(applicationInfo2.getConfirmingUserId(), application2.getConfirmingUser().getId());
		assertEquals(applicationInfo2.getApplyingUserId(), application2.getApplyingUser().getId());
		assertEquals(applicationInfo2.getDepartmentId(), application2.getDepartment().getId());
		assertEquals(applicationInfo2.getInstituteId(), application2.getInstitute().getId());
	}
}
