// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.List;

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
		flush();
		
		// Create departmentInfo
		DepartmentInfo departmentInfo = new DepartmentInfo();
		departmentInfo.setName("Wirtschaftswissenschaften - FB 4");
		departmentInfo.setDescription("Testdescription");
		departmentInfo.setShortcut("FB4");
		departmentInfo.setOwnerName("Administrator");
		departmentInfo.setEnabled(true);
		departmentInfo.setUniversityId(university.getId());
		departmentInfo.setDepartmentType(DepartmentType.OFFICIAL);
		
		//Create a User as Owner
		User owner = testUtility.createUniqueUserInDB();
		
		//Test
		Long departmentId = departmentService.create(departmentInfo, owner.getId());
		flush();
		assertNotNull(departmentId);
		assertEquals(1, university.getDepartments().size());
		
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
		flush();
		
		//Create department
		DepartmentInfo departmentInfo = new DepartmentInfo();
		departmentInfo.setName("Wirtschaftswissenschaften - FB 4");
		departmentInfo.setDescription("Testdescription");
		departmentInfo.setOwnerName("Administrator");
		departmentInfo.setEnabled(true);
		departmentInfo.setShortcut("FB4");
		departmentInfo.setUniversityId(university.getId());
		departmentInfo.setDepartmentType(DepartmentType.OFFICIAL);
		
		Long departmentId = 
			this.getDepartmentService().create(departmentInfo, testUtility.createUniqueUserInDB().getId());
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
	
	public void testFindDepartmentsByUniversity () {
		
		//Create University
		University university = testUtility.createUniqueUniversityInDB();
		flush();
		
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
		List<Department> departments = this.getDepartmentService().findDepartmentsByUniversity(university.getId());
		assertNotNull(departments);
		//assertEquals(1, departments.size());
		assertEquals(2, departments.size());
		assertEquals(department1.getName(), departments.get(0).getName());
		//assertEquals(departmentInfo2.getName(), departments.get(1).getName());
		
		logger.info("----> END access to findDepartmentsByUniversity(Department) test");
	}
	
	/*
	public void testFindDepartmentsByUserAndUniversity () {
		
		//Create University
		University university = testUtility.createUniqueUniversityInDB();
		flush();
		
		User user = testUtility.createDefaultUserInDB();
		
		//Create DepartmentDao
		DepartmentDao departmentDao = (DepartmentDao) this.getApplicationContext().getBean("departmentDao");
		
		//Create departments
		Department department1 = Department.Factory.newInstance();
		department1.setName("Wirtschaftswissenschaften - FB 4");
		department1.setDescription("Testdescription1");
		department1.setShortcut("FB4");
		department1.setUniversity(university);
		department1.setDepartmentType(DepartmentType.OFFICIAL);
		Membership membership = Membership.Factory.newInstance();
		membership.getMembers().add(user);
		department1.setMembership(membership);
		departmentDao.create(department1);
		
		university.getDepartments().add(department1);
		
		Department department2 = Department.Factory.newInstance();
		department2.setName("Rechtsswissenschaften - FB 3");
		department2.setDescription("Testdescription2");
		department2.setShortcut("FB3");
		department2.setUniversity(university);
		department2.setDepartmentType(DepartmentType.NONOFFICIAL);
		membership = Membership.Factory.newInstance();
		membership.getMembers().add(user);
		department2.setMembership(membership);
		departmentDao.create(department2);
		
		university.getDepartments().add(department2);
		
		flush();
		
		//Test
		List<Department> departments = this.getDepartmentService().findByUserAndUniversity(user.getId(), university.getId());
		assertNotNull(departments);
		assertEquals(2, departments.size());
		assertEquals(department1.getName(), departments.get(0).getName());
		assertEquals(department2.getName(), departments.get(1).getName());
		
		logger.info("----> END access to findDepartmentsByUserAndUniversity(Department) test");
	}
	*/
}