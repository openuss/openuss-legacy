// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.Date;
import java.util.GregorianCalendar;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.acl.AclManager;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.security.User;
import org.openuss.security.UserDao;

/**
 * JUnit Test for Spring Hibernate InstituteService class.
 * 
 * @see org.openuss.lecture.InstituteService
 * @author Ron Haus
 * @author Florian Dondorf
 * @author Ingo Düppe
 */
public class InstituteServiceIntegrationTest extends InstituteServiceIntegrationTestBase {

	private DepartmentService departmentService;

	private InstituteDao instituteDao;
	
	private DepartmentDao departmentDao;
	
	private AclManager aclManager;

	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		testUtility.createUserSecureContext();
		AcegiUtils.setAclManager(aclManager);
	}

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
		instituteInfo.setEmail("plexus@openuss-plexus.com");
		instituteInfo.setLocale("de_DE");
		instituteInfo.setEnabled(true);
		instituteInfo.setDescription("This is a test Institute");
		instituteInfo.setDepartmentId(departmentOfficial.getId()); // Should be ignored by createInstitute

		// Create a User as Owner
		User owner = testUtility.createUniqueUserInDB();

		// Create Entity
		Long instituteId = instituteService.create(instituteInfo, owner.getId());
		assertNotNull(instituteId);

		// Synchronize with Database
		commit();

		// Test
		Institute instituteTest = instituteDao.load(instituteId);
		assertNotNull(instituteTest);
		assertNull(instituteTest.getDepartment()); // One needs to call applyAtUniversity right after
		instituteService.applyAtDepartment(instituteId, departmentOfficial.getId(), owner.getId());
		assertNotNull(instituteTest.getDepartment());

		commit();
		Department departmentDefault = departmentDao.findByUniversityAndDefault(departmentOfficial.getUniversity(),	true);
		departmentOfficial = departmentDao.load(departmentOfficial.getId());
		assertEquals(departmentDefault.getId(), instituteTest.getDepartment().getId());
		assertTrue(departmentDefault.getInstitutes().contains(instituteTest));
		assertEquals(2, instituteTest.getApplications().size());
		int confirmed = 0;
		int notconfirmed = 0;
		for (Application application : instituteTest.getApplications()) {
			if (application.isConfirmed()) {
				assertEquals(departmentDefault.getId(), application.getDepartment().getId());
				confirmed++;
			} else {
				assertEquals(departmentOfficial.getId(), application.getDepartment().getId());
				notconfirmed++;
			}
		}
		assertEquals(1, confirmed);
		assertEquals(1, notconfirmed);

		// Create an NONOFFICIAL Department
		Department departmentNonOfficial = testUtility.createUniqueDepartmentInDB();
		departmentNonOfficial.setDepartmentType(DepartmentType.NONOFFICIAL);

		// Create another new InstituteInfo object
		InstituteInfo instituteInfo2 = new InstituteInfo();
		instituteInfo2.setName(testUtility.unique("testInstitute"));
		instituteInfo2.setShortcut(testUtility.unique("testI"));
		instituteInfo2.setOwnerName("Administrator");
		instituteInfo2.setEmail("plexus@openuss-plexus.com");
		instituteInfo2.setLocale("de_DE");
		instituteInfo2.setEnabled(true);
		instituteInfo2.setDescription("This is a test Institute");
		instituteInfo2.setDepartmentId(departmentNonOfficial.getId());

		// Create Entity
		Long instituteId2 = this.getInstituteService().create(instituteInfo2, owner.getId());
		assertNotNull(instituteId2);

		// Synchronize with Database
		commit();

		// Test
		Institute instituteTest2 = instituteDao.load(instituteId2);
		assertNotNull(instituteTest2);
		assertNull(instituteTest2.getDepartment()); // One needs to call applyAtUniversity right after
		instituteService.applyAtDepartment(instituteId2, departmentNonOfficial.getId(), owner.getId());
		assertNotNull(instituteTest2.getDepartment());

		instituteTest2 = instituteDao.load(instituteId2);
		departmentNonOfficial = departmentDao.load(departmentNonOfficial.getId());
		assertEquals(departmentNonOfficial.getId(), instituteTest2.getDepartment().getId());
		assertTrue(departmentNonOfficial.getInstitutes().contains(instituteTest2));
		assertEquals(1, instituteTest2.getApplications().size());
		confirmed = 0;
		notconfirmed = 0;
		for (Application application : instituteTest2.getApplications()) {
			if (application.isConfirmed()) {
				assertEquals(departmentNonOfficial.getId(), application.getDepartment().getId());
				confirmed++;
			}
		}
		assertEquals(1, confirmed);
		assertEquals(0, notconfirmed);

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
			logger.info("Got exceptions");
			// success
		}
		this.getInstituteService().removeInstitute(instituteId);

		// Synchronize with Database
		flush();

		// Try to load Institute again
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

		Institute institute2 = instituteDao.load(instituteId);
		assertNull(institute2);

		assertEquals(sizeBefore - 1, department.getInstitutes().size());

		testUtility.destroySecureContext();
		logger.info("----> END access to removeInstitute test");
	}

	public void testFindInstitute() {
		logger.debug("----> BEGIN access to findInstitute test <---- ");
		testUtility.createUserSecureContext();
		
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

		// Create two OFFICIAL Department
		Department departmentOfficial1 = testUtility.createUniqueDepartmentInDB();
		departmentOfficial1.setDepartmentType(DepartmentType.OFFICIAL);
		Department departmentOfficial2 = testUtility.createUniqueDepartmentInDB();
		departmentOfficial2.setDepartmentType(DepartmentType.OFFICIAL);

		// Create two NONOFFICIAL Department
		Department departmentNonOfficial1 = testUtility.createUniqueDepartmentInDB();
		departmentNonOfficial1.setDepartmentType(DepartmentType.NONOFFICIAL);
		Department departmentNonOfficial2 = testUtility.createUniqueDepartmentInDB();
		departmentNonOfficial2.setDepartmentType(DepartmentType.NONOFFICIAL);

		// Create a User
		User user = testUtility.createUniqueUserInDB();

		// Create a Institute
		Institute institute = testUtility.createUniqueInstituteInDB();
		assertEquals(1, institute.getApplications().size());

		flush();

		// Apply at OFFICIAL Department (No confirmation)
		Long applicationId1 = this.getInstituteService().applyAtDepartment(institute.getId(),
				departmentOfficial1.getId(), user.getId());
		flush();
		assertNotNull(applicationId1);
		assertEquals(2, institute.getApplications().size());

		// Apply again at OFFICIAL Department (No confirmation)
		Long applicationId2 = this.getInstituteService().applyAtDepartment(institute.getId(),
				departmentOfficial2.getId(), user.getId());
		flush();
		assertNotNull(applicationId2);
		assertEquals(2, institute.getApplications().size());

		testUtility.createAdminSecureContext();

		// Apply again at OFFICIAL Department (Confirmation)
		Long applicationId3 = this.getInstituteService().applyAtDepartment(institute.getId(),
				departmentOfficial1.getId(), user.getId());
		flush();
		assertNotNull(applicationId3);
		// Since Aspect is not working in local tests, accept manually
		try {
			departmentService.acceptApplication(applicationId3, user.getId());
			assertEquals(1, institute.getApplications().size());
		} catch (DepartmentServiceException dse) {
			logger.debug("seems that aspects are running");
		}

		// Apply again at OFFICIAL Department (Confirmation)
		Long applicationId4 = this.getInstituteService().applyAtDepartment(institute.getId(),
				departmentOfficial2.getId(), user.getId());
		flush();
		assertNotNull(applicationId4);
		try {
			departmentService.acceptApplication(applicationId4, user.getId());
			assertEquals(1, institute.getApplications().size());
		} catch (DepartmentServiceException dse) {
			logger.debug("seems that aspects are running");
		}

		testUtility.destroySecureContext();

		// Apply again at OFFICIAL Department (No confirmation)
		Long applicationId5 = this.getInstituteService().applyAtDepartment(institute.getId(),
				departmentOfficial1.getId(), user.getId());
		flush();
		assertNotNull(applicationId5);
		assertEquals(2, institute.getApplications().size());

		testUtility.createAdminSecureContext();

		// Apply at NONOFFICIAL Department (Confirmation)
		Long applicationId6 = this.getInstituteService().applyAtDepartment(institute.getId(),
				departmentNonOfficial1.getId(), user.getId());
		assertNotNull(applicationId6);
		assertEquals(1, institute.getApplications().size());

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

	@SuppressWarnings("deprecation")
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
		 * ApplicationInfo applicationInfo2 = this.getInstituteService().findApplicationByInstitute(institute.getId());
		 * assertNotNull(applicationInfo2); assertEquals(applicationId, applicationInfo2.getId());
		 */
		logger.info("----> END access to findApplicationByInstitute test");
	}
	
	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext.xml", 
			"classpath*:applicationContext-beans.xml",
			"classpath*:applicationContext-lucene.xml",
			"classpath*:applicationContext-cache.xml", 
			"classpath*:applicationContext-messaging.xml",
			"classpath*:applicationContext-resources.xml",
			"classpath*:applicationContext-aop.xml",
			"classpath*:applicationContext-events.xml",
			"classpath*:testContext.xml", 
			"classpath*:testSecurity.xml", 
			"classpath*:testDataSource.xml"};
	}


	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public void setAclManager(AclManager aclManager) {
		this.aclManager = aclManager;
	}
	
}