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
 * @author Florian Dondorf, Ron Haus
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

		//TODO: Change cardinality of departments from 0..1 to 1
		//		Error was insert, update = false.
		
		//Create University
		University university = testUtility.createUniqueUniversityInDB();
		flush();
		
		//Create Departments
		Department department1 = Department.Factory.newInstance();
		department1.setName("Rechtswissenschaften - FB 3");
		department1.setShortcut("FB3");
		department1.setOwnerName("Administrator");
		department1.setEnabled(true);
		department1.setDescription("Testdescription1");
		department1.setDepartmentType(DepartmentType.OFFICIAL);
		department1.setUniversity(university);
		department1.setMembership(Membership.Factory.newInstance());
		university.getDepartments().add(department1);
		
		Department department2 = Department.Factory.newInstance();
		department2.setName("Wirtschaftswissenschaften - FB 4");
		department2.setShortcut("FB4");
		department2.setOwnerName("Administrator");
		department2.setEnabled(true);
		department2.setDescription("Testdescription2");
		department2.setDepartmentType(DepartmentType.NONOFFICIAL);
		department2.setUniversity(university);
		department2.setMembership(Membership.Factory.newInstance());
		university.getDepartments().add(department2);
		
		//Association between departments and a university 
		//must be maintained by the university. 
		departmentDao.create(department1);
		departmentDao.create(department2);
		flush();
		
	}

	public void testDepartmentDaotoDepartmentInfo() {
		
		//Create university
		University university = testUtility.createUniqueUniversityInDB();
		flush();
		
		//Create Entity
		Department departmentEntity = Department.Factory.newInstance();
		departmentEntity.setName("testname");
		departmentEntity.setDescription("testdescription");
		departmentEntity.setDepartmentType(DepartmentType.OFFICIAL);
		departmentEntity.setMembership(Membership.Factory.newInstance());
		departmentEntity.setShortcut("test");
		departmentEntity.setUniversity(university);
		
		//Create Info-object
		DepartmentInfo departmentInfo =
			departmentDao.toDepartmentInfo(departmentEntity);
		
		//Test
		assertEquals(departmentEntity.getId(), departmentInfo.getId());
		assertEquals(departmentEntity.getName(), departmentInfo.getName());
		assertEquals(departmentEntity.getDescription(), departmentInfo.getDescription());
		assertEquals(departmentEntity.getDepartmentType(), departmentInfo.getDepartmentType());
		assertEquals(departmentEntity.getShortcut(), departmentInfo.getShortcut());
		assertEquals(departmentEntity.getUniversity().getId(), departmentInfo.getUniversityId());
		assertEquals(departmentEntity.getAddress(), departmentInfo.getAddress());
		assertEquals(departmentEntity.getCity(), departmentInfo.getCity());
		assertEquals(departmentEntity.getCountry(), departmentInfo.getCountry());
		assertEquals(departmentEntity.getEmail(), departmentInfo.getEmail());
		assertEquals(departmentEntity.getLocale(), departmentInfo.getLocale());
		assertEquals(departmentEntity.getPostcode(), departmentInfo.getPostcode());
		assertEquals(departmentEntity.getTelefax(), departmentInfo.getTelefax());
		assertEquals(departmentEntity.getTelephone(), departmentInfo.getTelephone());
		assertEquals(departmentEntity.getWebsite(), departmentInfo.getWebsite());
		assertEquals(departmentEntity.getTheme(), departmentInfo.getTheme());

	}

	public void testDepartmentDaoDepartmentInfoToEntity() {

		//Create university
		University university = testUtility.createUniqueUniversityInDB();
		flush();
		
		//Create Info-Object
		DepartmentInfo departmentInfo = new DepartmentInfo();
		departmentInfo.setName("testname");
		departmentInfo.setDescription("testdescription");
		departmentInfo.setShortcut("test");
		departmentInfo.setDepartmentType(DepartmentType.OFFICIAL);
		departmentInfo.setUniversityId(university.getId());
		
		//Create Entity
		Department departmentEntity =
			departmentDao.departmentInfoToEntity(departmentInfo);
		
		//Test
		assertEquals(departmentInfo.getId(), departmentEntity.getId());
		assertEquals(departmentInfo.getName(), departmentEntity.getName());
		assertEquals(departmentInfo.getDescription(), departmentEntity.getDescription());
		assertEquals(departmentInfo.getDepartmentType(), departmentEntity.getDepartmentType());
		assertEquals(departmentInfo.getShortcut(), departmentEntity.getShortcut());
		assertEquals(departmentInfo.getUniversityId(), departmentEntity.getUniversity().getId());
		assertEquals(departmentInfo.getAddress(), departmentEntity.getAddress());
		assertEquals(departmentInfo.getCity(), departmentEntity.getCity());
		assertEquals(departmentInfo.getCountry(), departmentEntity.getCountry());
		assertEquals(departmentInfo.getEmail(), departmentEntity.getEmail());
		assertEquals(departmentInfo.getLocale(), departmentEntity.getLocale());
		assertEquals(departmentInfo.getPostcode(), departmentEntity.getPostcode());
		assertEquals(departmentInfo.getTelefax(), departmentEntity.getTelefax());
		assertEquals(departmentInfo.getTelephone(), departmentEntity.getTelephone());
		assertEquals(departmentInfo.getWebsite(), departmentEntity.getWebsite());
		assertEquals(departmentInfo.getTheme(), departmentEntity.getTheme());

	}
}