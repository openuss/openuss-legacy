// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.acegisecurity.AccessDeniedException;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopDao;
import org.openuss.security.Membership;
import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate DepartmentService class.
 * 
 * @see org.openuss.lecture.DepartmentService
 */
@SuppressWarnings( { "unchecked" })
public class DepartmentServiceIntegrationTest extends DepartmentServiceIntegrationTestBase {

	public void testCreateDepartment() {
		logger.info("----> BEGIN access to create(Department) test");

		// Create university with user
		University university = testUtility.createUniqueUniversityInDB();
		int sizeBefore = university.getDepartments().size();
		flush();

		// Create a User as Owner
		User owner = testUtility.createUniqueUserInDB();

		// Create departmentInfo
		DepartmentInfo departmentInfo = new DepartmentInfo();
		departmentInfo.setName("Wirtschaftswissenschaften - FB 4");
		departmentInfo.setDescription("Testdescription");
		departmentInfo.setShortcut("FB4");
		departmentInfo.setOwnerName("Administrator");
		departmentInfo.setEnabled(true);
		departmentInfo.setUniversityId(university.getId());
		departmentInfo.setDefaultDepartment(false);
		departmentInfo.setDepartmentType(DepartmentType.OFFICIAL);

		// Test
		Long departmentId = departmentService.create(departmentInfo, owner.getId());
		flush();
		assertNotNull(departmentId);
		assertEquals(sizeBefore + 1, university.getDepartments().size());

		DesktopDao desktopDao = (DesktopDao) this.getApplicationContext().getBean("desktopDao");
		Desktop desktopTest = desktopDao.findByUser(owner);
		assertNotNull(desktopTest);
		assertEquals(1, desktopTest.getDepartments().size());

		logger.info("----> END access to create(Department) test");
	}

	public void testUpdateDepartment() {
		logger.info("----> BEGIN access to update(Department) test");

		// Create university
		University university = testUtility.createUniqueUniversityInDB();
		flush();

		// Create department
		DepartmentInfo departmentInfo = new DepartmentInfo();
		departmentInfo.setName("Wirtschaftswissenschaften - FB 4");
		departmentInfo.setDescription("Testdescription");
		departmentInfo.setOwnerName("Administrator");
		departmentInfo.setEnabled(true);
		departmentInfo.setShortcut("FB4");
		departmentInfo.setUniversityId(university.getId());
		departmentInfo.setDefaultDepartment(false);
		departmentInfo.setDepartmentType(DepartmentType.OFFICIAL);

		// Create a User as Owner
		User owner = testUtility.createUniqueUserInDB();

		Long departmentId = this.getDepartmentService().create(departmentInfo, owner.getId());
		flush();

		// Update department
		departmentInfo.setId(departmentId);
		departmentInfo.setName("Rechtswissenschaften - FB 5");
		departmentInfo.setDescription("TestdescriptionNew");
		departmentInfo.setShortcut("FB5");
		departmentInfo.setDepartmentType(DepartmentType.NONOFFICIAL);

		this.getDepartmentService().update(departmentInfo);
		flush();

		// Test
		DepartmentDao departmentDao = (DepartmentDao) this.getApplicationContext().getBean("departmentDao");
		Department department = departmentDao.load(departmentId);
		assertEquals(departmentInfo.getName(), department.getName());
		assertEquals(departmentInfo.getShortcut(), department.getShortcut());
		assertEquals(departmentInfo.getDescription(), department.getDescription());
		assertEquals(departmentInfo.getDepartmentType(), department.getDepartmentType());
		assertEquals(departmentInfo.getUniversityId(), department.getUniversity().getId());

		logger.info("----> END access to update(Department) test");
	}

	public void testRemoveDepartment() {
		logger.info("----> BEGIN access to remove(Department) test");

		// Create department without Institutes
		Department department = testUtility.createUniqueDepartmentInDB();
		Long departmentId = department.getId();
		University university = department.getUniversity();
		int sizeBefore = university.getDepartments().size();
		
		flush();

		// Remove department
		this.getDepartmentService().removeDepartment(department.getId());
		
		flush();

		// Test
		DepartmentDao departmentDao = (DepartmentDao) this.getApplicationContext().getBean("departmentDao");
		department = departmentDao.load(departmentId);
		assertNull(department);
		assertEquals(sizeBefore - 1, university.getDepartments().size());

		logger.info("----> END access to remove(Department) test");
	}
	
	public void testRemoveCompleteDepartmentTree() {
		logger.info("----> BEGIN access to remove(Department) test");

		// Create User
		User user = testUtility.createUniqueUserInDB();
		
		// Create Institute
		Institute institute = testUtility.createUniqueInstituteInDB();
		
		// Create department with Institutes, CourseTypes and Courses
		Course course = testUtility.createUniqueCourseInDB();
		Department department = course.getCourseType().getInstitute().getDepartment();
		Long departmentId = department.getId();
		assertNotNull(departmentId);
		University university = department.getUniversity();
		assertNotNull(university);
		int sizeBefore = university.getDepartments().size();
		
		// Create Application
		Application application = new ApplicationImpl();
		application.setApplicationDate(new Date(new GregorianCalendar(12, 07, 2006).getTimeInMillis()));
		application.setApplyingUser(user);
		application.setConfirmed(false);
		application.setDepartment(department);
		application.setInstitute(institute);
		
		department.getApplications().add(application);
		institute.getApplications().add(application);
		
		flush();

		// Create user secure context
		testUtility.createUserSecureContext();
		try {
			this.getDepartmentService().removeCompleteDepartmentTree(departmentId);
			fail("AccessDeniedException should have been thrown.");
		} catch (AccessDeniedException ade) {
			;
		} finally {
			testUtility.destroySecureContext();
		}
		
		// Create admin secure context
		testUtility.createAdminSecureContext();
		
		// Remove department
		this.getDepartmentService().removeCompleteDepartmentTree(departmentId);
		
		flush();

		// Test
		DepartmentDao departmentDao = (DepartmentDao) this.getApplicationContext().getBean("departmentDao");
		department = departmentDao.load(departmentId);
		assertNull(department);
		assertEquals(sizeBefore - 1, university.getDepartments().size());
		
		testUtility.destroySecureContext();
		logger.info("----> END access to remove(Department) test");
	}

	public void testFindDepartment() {

		// Create University
		University university = testUtility.createUniqueUniversityInDB();
		flush();

		// Create department with departmentDao
		Department department = Department.Factory.newInstance();
		department.setName("Wirtschaftswissenschaften - FB 4");
		department.setDescription("Testdescription");
		department.setOwnerName("Administrator");
		department.setEnabled(true);
		department.setShortcut("FB4");
		department.setUniversity(university);
		department.setDepartmentType(DepartmentType.OFFICIAL);
		department.setMembership(Membership.Factory.newInstance());

		DepartmentDao departmentDao = (DepartmentDao) this.getApplicationContext().getBean("departmentDao");
		departmentDao.create(department);
		flush();

		// Test
		DepartmentInfo departmentInfo = this.getDepartmentService().findDepartment(department.getId());
		assertNotNull(departmentInfo);
		assertEquals(departmentInfo.getName(), department.getName());

		logger.info("----> END access to findDepartment(Department) test");
	}

	@SuppressWarnings( { "unchecked" })
	public void testFindDepartmentsByUniversity() {

		logger.info("----> BEGIN access to findDepartmentsByUniversity(Department) test");

		// Create University
		University university = testUtility.createUniqueUniversityInDB();
		flush();

		int sizeBefore = university.getDepartments().size();

		// Create DepartmentDao
		DepartmentDao departmentDao = (DepartmentDao) this.getApplicationContext().getBean("departmentDao");

		// Create departments
		Department department1 = Department.Factory.newInstance();
		department1.setName("Wirtschaftswissenschaften - FB 4");
		department1.setDescription("Testdescription1");
		department1.setShortcut("FB4");
		department1.setOwnerName("Administrator");
		department1.setEnabled(true);
		department1.setUniversity(university);
		department1.setDepartmentType(DepartmentType.OFFICIAL);
		department1.setMembership(Membership.Factory.newInstance());
		departmentDao.create(department1);

		university.getDepartments().add(department1);

		Department department2 = Department.Factory.newInstance();
		department2.setName("Rechtsswissenschaften - FB 3");
		department2.setDescription("Testdescription2");
		department2.setShortcut("FB3");
		department2.setOwnerName("Administrator");
		department2.setEnabled(true);
		department2.setUniversity(university);
		department2.setDepartmentType(DepartmentType.NONOFFICIAL);
		department2.setMembership(Membership.Factory.newInstance());
		departmentDao.create(department2);

		university.getDepartments().add(department2);

		flush();

		// Test
		List<DepartmentInfo> departments = this.getDepartmentService().findDepartmentsByUniversity(university.getId());
		assertNotNull(departments);
		assertEquals(sizeBefore + 2, departments.size());

		logger.info("----> END access to findDepartmentsByUniversity(Department) test");
	}

	@SuppressWarnings( { "unchecked" })
	public void testFindDepartmentsByUniversityAndEnabled() {
		logger.info("----> BEGIN access to findDepartmentsByUniversityAndEnabled test");

		// Create 2 Universities
		University university1 = testUtility.createUniqueUniversityInDB();
		University university2 = testUtility.createUniqueUniversityInDB();
		int sizeBefore1 = university1.getDepartments().size();
		int sizeBefore2 = university2.getDepartments().size();

		// Create 3 Departments
		Department department1 = testUtility.createUniqueDepartmentInDB();
		university1.add(department1);
		department1.setEnabled(true);
		Department department2 = testUtility.createUniqueDepartmentInDB();
		university1.add(department2);
		department2.setEnabled(false);
		Department department3 = testUtility.createUniqueDepartmentInDB();
		university2.add(department3);
		department3.setEnabled(true);

		// Synchronize with Database
		flush();

		// Test
		List departmentsEnabled = this.departmentService.findDepartmentsByUniversityAndEnabled(university1.getId(),
				true);
		assertEquals(sizeBefore1 + 1, departmentsEnabled.size());

		List departmentsDisabled = this.departmentService.findDepartmentsByUniversityAndEnabled(university2.getId(),
				true);
		assertEquals(sizeBefore2 + 1, departmentsDisabled.size());

		logger.info("----> END access to findDepartmentsByUniversityAndEnabled test");
	}

	public void testFindApplication() {
		logger.info("----> BEGIN access to findApplication test");

		// Create Institute (Application is included)
		Institute institute = testUtility.createUniqueInstituteInDB();
		Application application = institute.getApplications().get(0);

		// Synchronize with Database
		flush();

		ApplicationInfo applicationInfo = this.getDepartmentService().findApplication(application.getId());
		assertNotNull(applicationInfo);
		assertEquals(application.getId(), applicationInfo.getId());

		logger.info("----> END access to findApplication test");
	}

	public void testFindApplicationsByDepartment() {
		logger.info("----> BEGIN access to findApplicationsByDepartment test");

		// Create Department
		Department department1 = testUtility.createUniqueDepartmentInDB();
		Department department2 = testUtility.createUniqueDepartmentInDB();

		// Create Applications
		Application application1 = this.getTestUtility().createUniqueUnconfirmedApplicationInDB();
		application1.setDepartment(department1);
		Application application2 = this.getTestUtility().createUniqueUnconfirmedApplicationInDB();
		application2.setDepartment(department1);
		Application application3 = this.getTestUtility().createUniqueUnconfirmedApplicationInDB();
		application3.setDepartment(department2);

		// Synchronize with Database
		flush();

		List<ApplicationInfo> applicationInfos = this.getDepartmentService().findApplicationsByDepartment(
				department1.getId());
		assertNotNull(applicationInfos);
		assertEquals(2, applicationInfos.size());
		assertEquals(department1.getId(), applicationInfos.get(0).getDepartmentInfo().getId());

		applicationInfos = this.getDepartmentService().findApplicationsByDepartment(department2.getId());
		assertNotNull(applicationInfos);
		assertEquals(1, applicationInfos.size());
		assertEquals(department2.getId(), applicationInfos.get(0).getDepartmentInfo().getId());

		logger.info("----> END access to findApplicationsByDepartment test");
	}

	public void testSignoffInstitute() {
		logger.info("----> BEGIN access to signoffInstitute test");

		// Create Institute
		Institute institute = testUtility.createUniqueInstituteInDB();
		assertNotNull(institute);
		Long departmentId = institute.getDepartment().getId();
		assertEquals(departmentId, institute.getDepartment().getId());

		// Synchronize with Database
		flush();

		// Signoff Institute
		this.getDepartmentService().signoffInstitute(institute.getId());

		assertNotSame(departmentId, institute.getDepartment().getId());

		logger.info("----> END access to signoffInstitute test");
	}

	public void testRejectApplication() {
		logger.info("----> BEGIN access to rejectApplication test");

		// Create Application
		Application application = this.getTestUtility().createUniqueUnconfirmedApplicationInDB();
		Long applicationId = application.getId();

		// Synchronize with Database
		flush();

		Department department = application.getDepartment();
		Institute institute = application.getInstitute();

		assertTrue(!application.isConfirmed());
		assertTrue(!department.getInstitutes().contains(institute));

		// Reject Application
		this.getDepartmentService().rejectApplication(application.getId());

		// Synchronize with Database
		flush();

		// Test
		assertTrue(!department.getInstitutes().contains(institute));
		ApplicationDao applicationDao = (ApplicationDao) this.getApplicationContext().getBean("applicationDao");
		Application application2 = applicationDao.load(applicationId);
		assertNull(application2);

		logger.info("----> END access to rejectApplication test");
	}

	public void testAcceptApplication() {
		logger.info("----> BEGIN access to acceptApplication test");

		// Create Application and User
		Application application = this.getTestUtility().createUniqueUnconfirmedApplicationInDB();
		User user = this.getTestUtility().createUniqueUserInDB();

		// Synchronize with Database
		flush();

		Department department = application.getDepartment();
		Institute institute = application.getInstitute();

		assertTrue(!application.isConfirmed());
		assertTrue(!department.getInstitutes().contains(institute));

		// Accept Application
		this.getDepartmentService().acceptApplication(application.getId(), user.getId());

		// Synchronize with Database
		flush();

		// Test
		assertTrue(application.isConfirmed());
		assertTrue(department.getInstitutes().contains(institute));

		logger.info("----> END access to acceptApplication test");
	}

	public void testFindDepartmentsByType() {
		logger.info("----> BEGIN access to findDepartmentsByType test");

		// Create University
		University university = testUtility.createUniqueUniversityInDB();
		DepartmentDao departmentDao = (DepartmentDao) this.getApplicationContext().getBean("departmentDao");
		List<Department> allDepartments = (List<Department>)departmentDao.loadAll();
		List<Department> officialDepartments = new ArrayList<Department>();
		List<Department> nonOfficialDepartments = new ArrayList<Department>();
		for (Department department : allDepartments) {
			if (department.getDepartmentType() == DepartmentType.OFFICIAL) {
				officialDepartments.add(department);
			}
			else {
				nonOfficialDepartments.add(department);
			}
		}
		int sizeNonOfficials = nonOfficialDepartments.size();
		int sizeOfficials = officialDepartments.size();

		// Create Departments
		Department department1 = testUtility.createUniqueDepartmentInDB();
		sizeNonOfficials++;
		department1.setUniversity(university);
		department1.setDepartmentType(DepartmentType.NONOFFICIAL);
		sizeNonOfficials++;

		Department department2 = testUtility.createUniqueDepartmentInDB();
		sizeNonOfficials++;
		department2.setUniversity(university);
		department2.setDepartmentType(DepartmentType.OFFICIAL);
		sizeOfficials++;

		Department department3 = testUtility.createUniqueDepartmentInDB();
		sizeNonOfficials++;
		department3.setUniversity(university);
		department3.setDepartmentType(DepartmentType.OFFICIAL);
		sizeOfficials++;

		Department department4 = testUtility.createUniqueDepartmentInDB();
		sizeNonOfficials++;
		department4.setUniversity(university);
		department4.setDepartmentType(DepartmentType.NONOFFICIAL);
		sizeNonOfficials++;

		Department department5 = testUtility.createUniqueDepartmentInDB();
		sizeNonOfficials++;
		department5.setUniversity(university);
		department5.setDepartmentType(DepartmentType.NONOFFICIAL);
		sizeNonOfficials++;

		// Synchronize with DB
		flush();

		// Test
		List<DepartmentInfo> departments = this.getDepartmentService().findDepartmentsByType(DepartmentType.OFFICIAL);
		assertNotNull(departments);
		assertEquals(sizeOfficials, departments.size());
		assertEquals(department3.getName(), departments.get(sizeOfficials - 1).getName());

		departments = this.getDepartmentService().findDepartmentsByType(DepartmentType.NONOFFICIAL);
		assertNotNull(departments);
		assertEquals(sizeNonOfficials, departments.size());

		logger.info("----> END access to findDepartmentsByType test");
	}

	public void testFindApplicationsByDepartmentAndConfirmed() {
		logger.info("----> BEGIN access to findApplicationsByDepartmentAndConfirmed test");

		// Create Applications
		Application application1 = testUtility.createUniqueUnconfirmedApplicationInDB();
		assertNotNull(application1);
		assertNotNull(application1.getDepartment());
		assertNotNull(application1.getDepartment().getId());

		Application application2 = testUtility.createUniqueUnconfirmedApplicationInDB();

		// Synchronitze with DB
		flush();

		// Test
		List<ApplicationInfo> applications = this.getDepartmentService().findApplicationsByDepartmentAndConfirmed(
				application1.getDepartment().getId(), false);
		assertNotNull(applications);
		assertEquals(1, applications.size());
		assertEquals(application1.getId(), applications.get(0).getId());

		applications = this.getDepartmentService().findApplicationsByDepartmentAndConfirmed(
				application2.getDepartment().getId(), true);
		assertNotNull(applications);
		assertEquals(0, applications.size());

		applications = this.getDepartmentService().findApplicationsByDepartmentAndConfirmed(
				application2.getDepartment().getId(), false);
		assertNotNull(applications);
		assertEquals(1, applications.size());
		assertEquals(application2.getId(), applications.get(0).getId());

		logger.info("----> END access to findApplicationsByDepartmentAndConfirmed test");
	}

	public void testSetDepartmentStatus() {
		logger.info("----> BEGIN access to setDepartmentStatus test");

		// Create department
		Department department = testUtility.createUniqueDepartmentInDB();
		assertNotNull(department);
		assertTrue(department.isEnabled());

		// Synchronize with DB
		flush();

		testUtility.createUserSecureContext();
		try {
			this.getDepartmentService().setDepartmentStatus(department.getId(), false);
			fail("AccessDeniedException should have been thrown.");

		} catch (AccessDeniedException ade) {
			;
		} finally {
			testUtility.destroySecureContext();
		}

		// Set status
		testUtility.createAdminSecureContext();
		this.getDepartmentService().setDepartmentStatus(department.getId(), false);

		// Load department
		DepartmentDao departmentDao = (DepartmentDao) this.getApplicationContext().getBean("departmentDao");
		Department departmentTest = departmentDao.load(department.getId());

		assertFalse(departmentTest.isEnabled());
		testUtility.destroySecureContext();

		// Set status
		testUtility.createAdminSecureContext();
		this.getDepartmentService().setDepartmentStatus(department.getId(), true);

		// Load department
		departmentDao = (DepartmentDao) this.getApplicationContext().getBean("departmentDao");
		Department departmentTest1 = departmentDao.load(department.getId());

		assertTrue(departmentTest1.isEnabled());
		testUtility.destroySecureContext();

		logger.info("----> END access to setDepartmentStatus test");
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


}