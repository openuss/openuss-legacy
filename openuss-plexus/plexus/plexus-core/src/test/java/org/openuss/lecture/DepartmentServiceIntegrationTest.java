// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

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
		University university = testUtility.createDefaultUniversityWithDefaultUser();
		flush();
		
		// Create departmentInfo
		DepartmentInfo departmentInfo = new DepartmentInfo();
		departmentInfo.setName("Wirtschaftswissenschaften - FB 4");
		departmentInfo.setDescription("Testdescription");
		departmentInfo.setShortcut("FB4");
		departmentInfo.setUniversityId(university.getId());
		departmentInfo.setDepartmentType(DepartmentType.OFFICIAL);
		
		//Create a User as Owner
		User owner = testUtility.createUserInDB();
		
		//Test
		Long departmentId = departmentService.create(departmentInfo, owner.getId());
		assertNotNull(departmentId);
		
		//Synchronize with Database
		flush();
		
		logger.info("----> END access to create(Department) test");
	}
	
	public void testUpdateDepartment () {
		logger.info("----> BEGIN access to update(Department) test");
		
		//Create university
		University university = testUtility.createDefaultUniversityWithDefaultUser();
		flush();
		
		//Create department
		DepartmentInfo departmentInfo = new DepartmentInfo();
		departmentInfo.setName("Wirtschaftswissenschaften - FB 4");
		departmentInfo.setDescription("Testdescription");
		departmentInfo.setShortcut("FB4");
		departmentInfo.setUniversityId(university.getId());
		departmentInfo.setDepartmentType(DepartmentType.OFFICIAL);
		
		//Create a User as Owner
		User owner = testUtility.createUserInDB();
		
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
		University university = testUtility.createDefaultUniversityWithDefaultUser();
		flush();
		
		//Create department
		DepartmentInfo departmentInfo = new DepartmentInfo();
		departmentInfo.setName("Wirtschaftswissenschaften - FB 4");
		departmentInfo.setDescription("Testdescription");
		departmentInfo.setShortcut("FB4");
		departmentInfo.setUniversityId(university.getId());
		departmentInfo.setDepartmentType(DepartmentType.OFFICIAL);
		
		Long departmentId = 
			this.getDepartmentService().create(departmentInfo, testUtility.createUserInDB().getId());
		flush();
		
		DepartmentDao departmentDao = (DepartmentDao)this.getApplicationContext().getBean("departmentDao");
		Department department = departmentDao.load(departmentId);
		assertNotNull(department);
		
		//Remove department
		this.getDepartmentService().removeDepartment(department.getId());
		flush();
		
		//Test	
		department = departmentDao.load(departmentId);
		assertNull(department);
		
		logger.info("----> END access to remove(Department) test");
	}
	
	public void testFindDepartment() {
		
		//Create University
		University university = testUtility.createDefaultUniversityWithDefaultUser();
		flush();
		
		//Create department with departmentDao
		Department department = Department.Factory.newInstance();
		department.setName("Wirtschaftswissenschaften - FB 4");
		department.setDescription("Testdescription");
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
	}
}