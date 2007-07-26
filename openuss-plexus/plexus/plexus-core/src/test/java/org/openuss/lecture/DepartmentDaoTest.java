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

		//Create University
		University university = testUtility.createDefaultUniversityWithDefaultUser();
		flush();
		
		//Create Departments
		Department department1 = Department.Factory.newInstance();
		department1.setName("Rechtswissenschaften - FB 3");
		department1.setShortcut("FB3");
		department1.setDescription("Testdescription1");
		department1.setDepartmentType(DepartmentType.OFFICIAL);
		//department1.setUniversity(university);
		department1.setMembership(Membership.Factory.newInstance());
		
		Department department2 = Department.Factory.newInstance();
		department2.setName("Wirtschaftswissenschaften - FB 4");
		department2.setShortcut("FB4");
		department2.setDescription("Testdescription2");
		department2.setDepartmentType(DepartmentType.NONOFFICIAL);
		//department2.setUniversity(university);
		department2.setMembership(Membership.Factory.newInstance());
		
		university.getDepartments().add(department1);
		university.getDepartments().add(department2);
		
		departmentDao.create(department1);
		departmentDao.create(department2);
		flush();
		
	}

	public void testDepartmentDaotoDepartmentInfo() {


	}

	public void testDepartmentDaoDepartmentInfoToEntity() {



	}
}