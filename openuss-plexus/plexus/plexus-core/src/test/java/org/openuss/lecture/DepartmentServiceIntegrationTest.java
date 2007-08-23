// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.List;

import org.acegisecurity.AccessDeniedException;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopDao;
import org.openuss.security.Membership;
import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate DepartmentService class.
 * @see org.openuss.lecture.DepartmentService
 */
public class DepartmentServiceIntegrationTest extends DepartmentServiceIntegrationTestBase {
	
	public void testCreateDepartment() {
		logger.info("----> BEGIN access to create(Department) test");
		
		// Create university with user
		University university = testUtility.createUniqueUniversityInDB();
		int sizeBefore = university.getDepartments().size();
		flush();
		
		//Create a User as Owner
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
		
		//Test
		Long departmentId = departmentService.create(departmentInfo, owner.getId());
		flush();
		assertNotNull(departmentId);
		assertEquals(sizeBefore+1, university.getDepartments().size());
		
		DesktopDao desktopDao = (DesktopDao) this.getApplicationContext().getBean("desktopDao");
		Desktop desktopTest = desktopDao.findByUser(owner);
		assertNotNull(desktopTest);
		assertEquals(1, desktopTest.getDepartments().size());
		
		logger.info("----> END access to create(Department) test");
	}
	
	public void testUpdateDepartment () {
		logger.info("----> BEGIN access to update(Department) test");
		
		//Create university
		University university = testUtility.createUniqueUniversityInDB();
		flush();
		
		//Create department
		DepartmentInfo departmentInfo = new DepartmentInfo();
		departmentInfo.setName("Wirtschaftswissenschaften - FB 4");
		departmentInfo.setDescription("Testdescription");
		departmentInfo.setOwnerName("Administrator");
		departmentInfo.setEnabled(true);
		departmentInfo.setShortcut("FB4");
		departmentInfo.setUniversityId(university.getId());
		departmentInfo.setDefaultDepartment(false);
		departmentInfo.setDepartmentType(DepartmentType.OFFICIAL);
		
		//Create a User as Owner
		User owner = testUtility.createUniqueUserInDB();
		
		Long departmentId = this.getDepartmentService().create(departmentInfo, owner.getId());
		flush();

		//Update department
		departmentInfo.setId(departmentId);
		departmentInfo.setName("Rechtswissenschaften - FB 5");
		departmentInfo.setDescription("TestdescriptionNew");
		departmentInfo.setShortcut("FB5");
		departmentInfo.setDepartmentType(DepartmentType.NONOFFICIAL);
		
		this.getDepartmentService().update(departmentInfo);
		flush();
		
		//Test
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
		
		//Create University
		University university = testUtility.createUniqueUniversityInDB();
		assertEquals(1, university.getDepartments().size());
		flush();
		
		//Create department
		Department department = testUtility.createUniqueDepartmentInDB();
		Long departmentId = department.getId();
		flush();
		
		// Create Desktop
		User user = testUtility.createUniqueUserInDB();
		Desktop desktop = Desktop.Factory.newInstance(user);
		desktop.getDepartments().add(department);
		DesktopDao desktopDao = (DesktopDao) this.getApplicationContext().getBean("desktopDao");
		Desktop desktopCreated = desktopDao.create(desktop);
		assertNotNull(desktopCreated);
		assertEquals(1, desktop.getDepartments().size());
		
		//Remove department
		testUtility.createAdminSecureContext();
		this.getDepartmentService().removeDepartment(department.getId());
		flush();
		
		//Test	
		DepartmentDao departmentDao = (DepartmentDao)this.getApplicationContext().getBean("departmentDao");
		department = departmentDao.load(departmentId);
		assertNull(department);
		
		assertEquals(1, university.getDepartments().size());
		
		Desktop desktopTest = desktopDao.load(desktop.getId());
		assertNotNull(desktopTest);
		assertEquals(0, desktopTest.getDepartments().size());
		
		logger.info("----> END access to remove(Department) test");
	}
	
	public void testFindDepartment() {
		
		//Create University
		University university = testUtility.createUniqueUniversityInDB();
		flush();
		
		//Create department with departmentDao
		Department department = Department.Factory.newInstance();
		department.setName("Wirtschaftswissenschaften - FB 4");
		department.setDescription("Testdescription");
		department.setOwnerName("Administrator");
		department.setEnabled(true);
		department.setShortcut("FB4");
		department.setUniversity(university);
		department.setDepartmentType(DepartmentType.OFFICIAL);
		department.setMembership(Membership.Factory.newInstance());
		
		DepartmentDao departmentDao =
			(DepartmentDao) this.getApplicationContext().getBean("departmentDao");
		departmentDao.create(department);
		flush();
		
		//Test
		DepartmentInfo departmentInfo =
			this.getDepartmentService().findDepartment(department.getId());
		assertNotNull(departmentInfo);
		assertEquals(departmentInfo.getName(), department.getName());
		
		logger.info("----> END access to findDepartment(Department) test");
	}
	
	@SuppressWarnings( { "unchecked" })
	public void testFindDepartmentsByUniversity () {
		
		logger.info("----> BEGIN access to findDepartmentsByUniversity(Department) test");
		
		//Create University
		University university = testUtility.createUniqueUniversityInDB();
		flush();
		
		int sizeBefore = university.getDepartments().size();
		
		//Create DepartmentDao
		DepartmentDao departmentDao = (DepartmentDao) this.getApplicationContext().getBean("departmentDao");		
		
		//Create departments
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
		
		//Test
		List<DepartmentInfo> departments = this.getDepartmentService().findDepartmentsByUniversity(university.getId());
		assertNotNull(departments);
		assertEquals(sizeBefore+2, departments.size());
		
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
		List departmentsEnabled = this.departmentService.findDepartmentsByUniversityAndEnabled(university1.getId(), true);
		assertEquals(sizeBefore1+1, departmentsEnabled.size());

		List departmentsDisabled = this.departmentService.findDepartmentsByUniversityAndEnabled(university2.getId(), true);
		assertEquals(sizeBefore2+1, departmentsDisabled.size());
		
		logger.info("----> END access to findDepartmentsByUniversityAndEnabled test");
	}
	
	public void testFindApplication() {
		logger.info("----> BEGIN access to findApplication test");

		// Create Application
		Application application = this.getTestUtility().createUniqueUnconfirmedApplicationInDB();
		
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
		
		List<ApplicationInfo> applicationInfos = this.getDepartmentService().findApplicationsByDepartment(department1.getId());
		assertNotNull(applicationInfos);
		assertEquals(2, applicationInfos.size());
		assertEquals(department1.getId(), applicationInfos.get(0).getDepartmentId());
		
		applicationInfos = this.getDepartmentService().findApplicationsByDepartment(department2.getId());
		assertNotNull(applicationInfos);
		assertEquals(1, applicationInfos.size());
		assertEquals(department2.getId(), applicationInfos.get(0).getDepartmentId());
		
		logger.info("----> END access to findApplicationsByDepartment test");
	}
	
	public void testSignoffInstitute() {
		logger.info("----> BEGIN access to signoffInstitute test");

		// Create Application
		Application application = this.getTestUtility().createUniqueUnconfirmedApplicationInDB();
		User user = this.getTestUtility().createUniqueUserInDB();
		Long applicationId = application.getId();
		
		Department department = application.getDepartment();
		Institute institute = application.getInstitute();
		
		University university = department.getUniversity();
		
		// Accept Application
		assertFalse(department.getInstitutes().contains(institute));
		this.getDepartmentService().acceptApplication(application.getId(), user.getId());
		assertTrue(application.isConfirmed());
		assertTrue(department.getInstitutes().contains(institute));
		
		// Synchronize with Database
		flush();
		
		assertTrue(application.isConfirmed());
		assertTrue(department.getInstitutes().contains(institute));
		
		// Signoff Institute
		assertNotNull(institute.getApplication());
		assertTrue(university.getDepartments().size()==2);
		
		// Synchronize with Database
		flush();
		
		this.getDepartmentService().signoffInstitute(institute.getId());
		
		// Synchronize with Database
		flush();

		// Test
		assertTrue(!department.getInstitutes().contains(institute));
		ApplicationDao applicationDao = (ApplicationDao) this.getApplicationContext().getBean("applicationDao");
		Application application2 = applicationDao.load(applicationId);
		assertNull(application2);
		
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
		int sizeNonOfficials = 1;
		int sizeOfficials = 0;
		
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
		assertEquals(department2.getName(), departments.get(0).getName());

		departments = this.getDepartmentService().findDepartmentsByType(DepartmentType.NONOFFICIAL);
		assertNotNull(departments);
		assertEquals(sizeNonOfficials, departments.size());
		assertEquals(department5.getName(), departments.get(2).getName());
		
		logger.info("----> END access to findDepartmentsByType test");
	}
	
	public void testIsNoneExistingDepartmentShortcut () {
		logger.debug("----> BEGIN access to isNoneExistingUniversityShortcut test <---- ");
		
		//Create Secure Context
		User user = testUtility.createSecureContext();
		
		// Create Departments
		Department department1 = testUtility.createUniqueDepartmentInDB();
		Department department2 = testUtility.createUniqueDepartmentInDB();
		flush();
		
		// Test
		DepartmentDao departmentDao = (DepartmentDao) this.getApplicationContext().getBean("departmentDao");
		Boolean result = this.getDepartmentService().isNoneExistingDepartmentShortcut(
				departmentDao.toDepartmentInfo(department1), department1.getShortcut());
		assertNotNull(result);
		assertTrue(result);
		
		result = this.getDepartmentService().isNoneExistingDepartmentShortcut(
				departmentDao.toDepartmentInfo(department1), testUtility.unique("department"));
		assertNotNull(result);
		assertTrue(result);
		
		result = this.getDepartmentService().isNoneExistingDepartmentShortcut(
				departmentDao.toDepartmentInfo(department1), department2.getShortcut());
		assertNotNull(result);
		assertFalse(result);
		
		logger.debug("----> END access to isNoneExistingUniversityShortcut test <---- ");
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
}