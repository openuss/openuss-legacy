// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.List;

import org.openuss.TestUtility;
import org.openuss.security.Membership;
import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate DepartmentDao class.
 * 
 * @see org.openuss.lecture.DepartmentDao
 * @author Ron Haus
 */
public class DepartmentDaoTest extends DepartmentDaoTestBase {

	private TestUtility testUtility;

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public void testDepartmentDaoCreate() {
		University university = testUtility.createDefaultUniversityWithDefaultUser();

		Department department = Department.Factory.newInstance();
		department.setName(testUtility.unique("testDepartment"));
		department.setShortcut(testUtility.unique("testD"));
		department.setUniversity(university);

		Department department2 = Department.Factory.newInstance();
		department2.setName(testUtility.unique("testDepartment"));
		department2.setShortcut(testUtility.unique("testD"));
		department2.setUniversity(university);

		// Create Memberships
		List<User> users = new ArrayList<User>(5);
		for (int i = 0; i < 5; i++) {
			users.add(testUtility.createUserInDB());
		}

		Membership membership = Membership.Factory.newInstance();
		membership.setOwner(users.get(0));
		membership.getAspirants().add(users.get(1));
		membership.getAspirants().add(users.get(2));
		membership.getMembers().add(users.get(3));
		membership.getMembers().add(users.get(4));
		department.setMembership(membership);

		Membership membership2 = Membership.Factory.newInstance();
		membership2.setOwner(users.get(0));
		membership2.getAspirants().add(users.get(1));
		membership2.getAspirants().add(users.get(2));
		membership2.getMembers().add(users.get(3));
		membership2.getMembers().add(users.get(4));
		department2.setMembership(membership2);

		assertNull(department.getId());
		assertNull(department2.getId());
		departmentDao.create(department);
		departmentDao.create(department2);
		assertNotNull(department.getId());
		assertNotNull(department2.getId());
	}

	public void testDepartmentDaotoDepartmentInfo() {

		// Create a complete Department
		Department department = Department.Factory.newInstance();
		department.setName(testUtility.unique("testDepartment"));
		department.setShortcut(testUtility.unique("testD"));
		department.setDescription("This is a test Department");
		Membership membership = Membership.Factory.newInstance();
		membership.setOwner(testUtility.createUserInDB());
		department.setMembership(membership);
		department.setUniversity(testUtility.createDefaultUniversityWithDefaultUser());

		departmentDao.create(department);
		assertNotNull(department.getId());

		// Test ValueObject
		DepartmentInfo departmentInfo = departmentDao.toDepartmentInfo(department);

		assertEquals(department.getId(), departmentInfo.getId());
		assertEquals(department.getName(), departmentInfo.getName());
		assertEquals(department.getShortcut(), departmentInfo.getShortcut());
		assertEquals(department.getDescription(), departmentInfo.getDescription());
		assertEquals(department.getType().getValue(), departmentInfo.getDepartmentType());
		assertEquals(department.getUniversity().getId(), departmentInfo.getUniversityId());
	}

	public void testDepartmentDaoDepartmentInfoToEntity() {

		// Create a complete Department
		Department department = Department.Factory.newInstance();
		department.setName(testUtility.unique("testDepartment"));
		department.setShortcut(testUtility.unique("testD"));
		department.setDescription("This is a test Department");
		Membership membership = Membership.Factory.newInstance();
		membership.setOwner(testUtility.createUserInDB());
		department.setMembership(membership);
		department.setUniversity(testUtility.createDefaultUniversityWithDefaultUser());

		departmentDao.create(department);
		assertNotNull(department.getId());

		// Create the corresponding ValueObject
		DepartmentInfo departmentInfo = new DepartmentInfo();
		departmentInfo.setId(department.getId());

		// Test Entity
		Department department2 = departmentDao.departmentInfoToEntity(departmentInfo);

		assertEquals(department2.getId(), department.getId());
		assertEquals(department2.getName(), department.getName());
		assertEquals(department2.getShortcut(), department.getShortcut());
		assertEquals(department2.getDescription(), department.getDescription());
		assertEquals(department2.getType(), department.getType());

	}
}