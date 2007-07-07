// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.openuss.TestUtility;
import org.openuss.security.Membership;


/**
 * JUnit Test for Spring Hibernate DepartmentDao class.
 * @see org.openuss.lecture.DepartmentDao
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
		Membership membership = Membership.Factory.newInstance();
		membership.setOwner(testUtility.createUserInDB());
		department.setMembership(membership);
		
		Department department2 = Department.Factory.newInstance();
		department2.setName(testUtility.unique("testDepartment"));
		department2.setShortcut(testUtility.unique("testD"));
		department2.setUniversity(university);		
		Membership membership2 = Membership.Factory.newInstance();
		membership2.setOwner(testUtility.createUserInDB());
		department2.setMembership(membership2);
		
		assertNull(department.getId());
		assertNull(department2.getId());
		departmentDao.create(department);
		departmentDao.create(department2);
		assertNotNull(department.getId());
		assertNotNull(department2.getId());
	}
}