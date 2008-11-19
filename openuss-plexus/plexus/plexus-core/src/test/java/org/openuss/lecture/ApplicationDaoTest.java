// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.Date;

import org.openuss.TestUtility;
import org.openuss.security.UserDao;

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
		Application application = new ApplicationImpl();
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
		Application application = new ApplicationImpl();
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
		assertEquals(application.isConfirmed(), applicationInfo.isConfirmed());
		assertEquals(application.getConfirmingUser().getId(), applicationInfo.getConfirmingUserInfo().getId());
		assertEquals(application.getApplyingUser().getId(), applicationInfo.getApplyingUserInfo().getId());
		assertEquals(application.getDepartment().getId(), applicationInfo.getDepartmentInfo().getId());
		assertEquals(application.getInstitute().getId(), applicationInfo.getInstituteInfo().getId());
	}

	public void testApplicationDaoApplicationInfoToEntity() {

		// Create a complete Application
		Application applicationDefault = new ApplicationImpl();
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

		UserDao userDao = (UserDao) this.getApplicationContext().getBean("userDao");
		InstituteDao instituteDao = (InstituteDao) this.getApplicationContext().getBean("instituteDao");
		DepartmentDao departmentDao = (DepartmentDao) this.getApplicationContext().getBean("departmentDao");
		
		// Create the corresponding ValueObject
		ApplicationInfo applicationInfo1 = new ApplicationInfo();
		applicationInfo1.setId(applicationDefault.getId());
		applicationInfo1.setDescription(applicationDefault.getDescription());
		applicationInfo1.setConfirmed(applicationDefault.isConfirmed());
		applicationInfo1.setApplicationDate(applicationDefault.getApplicationDate());
		applicationInfo1.setConfirmationDate(applicationDefault.getConfirmationDate());
		applicationInfo1.setConfirmingUserInfo(userDao.toUserInfo(applicationDefault.getConfirmingUser()));
		applicationInfo1.setApplyingUserInfo(userDao.toUserInfo(applicationDefault.getApplyingUser()));
		applicationInfo1.setDepartmentInfo(departmentDao.toDepartmentInfo(applicationDefault.getDepartment()));
		applicationInfo1.setInstituteInfo(instituteDao.toInstituteInfo(applicationDefault.getInstitute()));

		// Test toEntity
		Application application1 = applicationDao.applicationInfoToEntity(applicationInfo1);

		assertEquals(applicationInfo1.getId(), application1.getId());
		assertEquals(applicationInfo1.getDescription(), application1.getDescription());
		assertEquals(applicationInfo1.getConfirmationDate(), application1.getConfirmationDate());
		assertEquals(applicationInfo1.getApplicationDate(), application1.getApplicationDate());
		assertEquals(applicationInfo1.isConfirmed(), application1.isConfirmed());
		assertEquals(applicationInfo1.getConfirmingUserInfo().getId(), application1.getConfirmingUser().getId());
		assertEquals(applicationInfo1.getApplyingUserInfo().getId(), application1.getApplyingUser().getId());
		assertEquals(applicationInfo1.getDepartmentInfo().getId(), application1.getDepartment().getId());
		assertEquals(applicationInfo1.getInstituteInfo().getId(), application1.getInstitute().getId());

		// Create a new ValueObject (no Entity available)
		ApplicationInfo applicationInfo2 = new ApplicationInfo();
		applicationInfo2.setApplicationDate(new Date());
		applicationInfo2.setDepartmentInfo(departmentDao.toDepartmentInfo(testUtility.createUniqueDepartmentInDB()));
		applicationInfo2.setInstituteInfo(instituteDao.toInstituteInfo(testUtility.createUniqueInstituteInDB()));
		applicationInfo2.setConfirmed(false);
		applicationInfo2.setDescription("A unique Application");
		applicationInfo2.setApplyingUserInfo(userDao.toUserInfo(testUtility.createUniqueUserInDB()));
		applicationInfo2.setConfirmingUserInfo(userDao.toUserInfo(testUtility.createUniqueUserInDB()));
		applicationInfo2.setApplicationDate(new Date());
		applicationInfo2.setConfirmationDate(new Date());

		// Test toEntity
		Application application2 = applicationDao.applicationInfoToEntity(applicationInfo2);

		assertEquals(applicationInfo2.getId(), application2.getId());
		assertEquals(applicationInfo2.getDescription(), application2.getDescription());
		assertEquals(applicationInfo2.getConfirmationDate(), application2.getConfirmationDate());
		assertEquals(applicationInfo2.getApplicationDate(), application2.getApplicationDate());
		assertEquals(applicationInfo2.isConfirmed(), application2.isConfirmed());
		assertEquals(applicationInfo2.getConfirmingUserInfo().getId(), application2.getConfirmingUser().getId());
		assertEquals(applicationInfo2.getApplyingUserInfo().getId(), application2.getApplyingUser().getId());
		assertEquals(applicationInfo2.getDepartmentInfo().getId(), application2.getDepartment().getId());
		assertEquals(applicationInfo2.getInstituteInfo().getId(), application2.getInstitute().getId());
	}
}
