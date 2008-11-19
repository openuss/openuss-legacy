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

/**
 * JUnit Test for Spring Hibernate DepartmentDao class.
 * 
 * @see org.openuss.lecture.DepartmentDao
 * @author Florian Dondorf, Ron Haus
 */
@SuppressWarnings( { "unchecked" })
public class DepartmentDaoTest extends DepartmentDaoTestBase {

	private TestUtility testUtility;

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public void testDepartmentDaoCreate() {

		// TODO: Change cardinality of departments from 0..1 to 1
		// Error was insert, update = false.

		// Create University
		University university = testUtility.createUniqueUniversityInDB();

		// Synchronize with Database
		flush();

		// Create Departments
		Department department1 = new DepartmentImpl();
		department1.setName("Rechtswissenschaften - FB 3");
		department1.setShortcut("FB3");
		department1.setOwnerName("Administrator");
		department1.setEnabled(true);
		department1.setDescription("Testdescription1");
		department1.setDepartmentType(DepartmentType.OFFICIAL);
		department1.setUniversity(university);
		department1.setMembership(Membership.Factory.newInstance());
		university.getDepartments().add(department1);

		Department department2 = new DepartmentImpl();
		department2.setName("Wirtschaftswissenschaften - FB 4");
		department2.setShortcut("FB4");
		department2.setOwnerName("Administrator");
		department2.setEnabled(true);
		department2.setDescription("Testdescription2");
		department2.setDepartmentType(DepartmentType.NONOFFICIAL);
		department2.setUniversity(university);
		department2.setMembership(Membership.Factory.newInstance());
		university.getDepartments().add(department2);

		// Association between departments and a university
		// must be maintained by the university.
		assertNull(department1.getId());
		departmentDao.create(department1);
		assertNotNull(department1.getId());

		assertNull(department2.getId());
		departmentDao.create(department2);
		assertNotNull(department2.getId());

		// Synchronize with Database
		flush();

	}

	public void testDepartmentDaoToDepartmentInfo() {

		// Create university
		University university = testUtility.createUniqueUniversityInDB();
		flush();

		// Create Entity
		Department departmentEntity = new DepartmentImpl();
		departmentEntity.setName("testname");
		departmentEntity.setDescription("testdescription");
		departmentEntity.setDepartmentType(DepartmentType.OFFICIAL);
		departmentEntity.setMembership(Membership.Factory.newInstance());
		departmentEntity.setShortcut("test");
		departmentEntity.setUniversity(university);

		// Create Info-object
		DepartmentInfo departmentInfo = departmentDao.toDepartmentInfo(departmentEntity);

		// Test
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

		// Create university
		University university = testUtility.createUniqueUniversityInDB();
		flush();

		// Create Info-Object
		DepartmentInfo departmentInfo = new DepartmentInfo();
		departmentInfo.setName("testname");
		departmentInfo.setDescription("testdescription");
		departmentInfo.setShortcut("test");
		departmentInfo.setDepartmentType(DepartmentType.OFFICIAL);
		departmentInfo.setUniversityId(university.getId());

		// Create Entity
		Department departmentEntity = departmentDao.departmentInfoToEntity(departmentInfo);

		// Test
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

	public void testDepartmentDaoFindByEnabled() {
		// Count existing departments
		List<Department> allDepartments = (List<Department>)this.getDepartmentDao().loadAll();
		List<Department> enabledDepartments = new ArrayList<Department>();
		List<Department> disabledDepartments = new ArrayList<Department>();
		for (Department department : allDepartments) {
			if (department.isEnabled()) {
				enabledDepartments.add(department);
			}
			else {
				disabledDepartments.add(department);
			}
		}
		int countEnabledDepartments = enabledDepartments.size();
		int countDisabledDepartments = disabledDepartments.size();
		
		// Create 3 Departments (+ 3 automatically as enabled created Standard Departments by the University)
		Department department1 = testUtility.createUniqueDepartmentInDB();
		department1.setEnabled(true);
		countEnabledDepartments++;
		countEnabledDepartments++;
		Department department2 = testUtility.createUniqueDepartmentInDB();
		department2.setEnabled(true);
		countEnabledDepartments++;
		countEnabledDepartments++;
		Department department3 = testUtility.createUniqueDepartmentInDB();
		department3.setEnabled(false);
		countDisabledDepartments++;
		countEnabledDepartments++;

		// Synchronize with Database
		flush();

		// Test
		List departmentsEnabled = this.departmentDao.findByEnabled(true);
		assertEquals(countEnabledDepartments, departmentsEnabled.size());
		assertTrue(departmentsEnabled.contains(department1));
		assertTrue(departmentsEnabled.contains(department2));
		assertFalse(departmentsEnabled.contains(department3));

		List departmentsDisabled = this.departmentDao.findByEnabled(false);
		assertEquals(countDisabledDepartments, departmentsDisabled.size());
		assertFalse(departmentsDisabled.contains(department1));
		assertFalse(departmentsDisabled.contains(department2));
		assertTrue(departmentsDisabled.contains(department3));
	}
	
	public void testDepartmentDaoFindByUniversityAndEnabled() {
		
		// Create 2 Universities
		University university1 = testUtility.createUniqueUniversityInDB();
		University university2 = testUtility.createUniqueUniversityInDB();
		
		// Count departments of universities
		List<Department> allDepartmentsOfUni1 = this.getDepartmentDao().findByUniversity(university1);
		List<Department> allDepartmentsEnabled = new ArrayList<Department>();
		List<Department> allDepartmentsDisabled = new ArrayList<Department>();
		for (Department department : allDepartmentsOfUni1) {
			if (department.isEnabled()) {
				allDepartmentsEnabled.add(department);
			}
			else {
				allDepartmentsDisabled.add(department);
			}
		}
		int countDepartmentsEnabled = allDepartmentsEnabled.size();
		int countDepartmentsDisabled = allDepartmentsDisabled.size();
		
		// Create 3 Departments
		Department department1 = testUtility.createUniqueDepartmentInDB();
		university1.add(department1);
		department1.setEnabled(true);
		countDepartmentsEnabled++;
		Department department2 = testUtility.createUniqueDepartmentInDB();
		university1.add(department2);
		department2.setEnabled(false);
		countDepartmentsDisabled++;
		Department department3 = testUtility.createUniqueDepartmentInDB();
		university2.add(department3);
		department3.setEnabled(true);

		// Synchronize with Database
		flush();

		// Test
		List departmentsEnabled = this.departmentDao.findByUniversityAndEnabled(university1, true);
		assertEquals(countDepartmentsEnabled, departmentsEnabled.size());
		assertTrue(departmentsEnabled.contains(department1));
		assertFalse(departmentsEnabled.contains(department2));
		assertFalse(departmentsEnabled.contains(department3));

		List departmentsDisabled = this.departmentDao.findByUniversityAndEnabled(university1, false);
		assertEquals(countDepartmentsDisabled, departmentsDisabled.size());
		assertFalse(departmentsDisabled.contains(department1));
		assertTrue(departmentsDisabled.contains(department2));
		assertFalse(departmentsDisabled.contains(department3));
	}
	
	public void testDepartmentDaoFindByUniversityAndType() {
		// Create 2 Universities
		University university1 = testUtility.createUniqueUniversityInDB();
		University university2 = testUtility.createUniqueUniversityInDB();
		
		// Create 5 Departments
		Department department1 = testUtility.createUniqueDepartmentInDB();
		university1.add(department1);
		department1.setDepartmentType(DepartmentType.OFFICIAL);
		
		Department department2 = testUtility.createUniqueDepartmentInDB();
		university1.add(department2);
		department2.setDepartmentType(DepartmentType.NONOFFICIAL);
		
		Department department3 = testUtility.createUniqueDepartmentInDB();
		university1.add(department3);
		department3.setDepartmentType(DepartmentType.NONOFFICIAL);
		
		Department department4 = testUtility.createUniqueDepartmentInDB();
		university1.add(department4);
		department4.setDepartmentType(DepartmentType.OFFICIAL);
		
		Department department5 = testUtility.createUniqueDepartmentInDB();
		university2.add(department5);
		department5.setDepartmentType(DepartmentType.NONOFFICIAL);

		// Synchronize with Database
		flush();

		// Test
		List departmentsOfficial = this.departmentDao.findByUniversityAndType(university1, DepartmentType.OFFICIAL);
		assertEquals(2, departmentsOfficial.size());
		assertTrue(departmentsOfficial.contains(department1));
		assertFalse(departmentsOfficial.contains(department2));
		assertFalse(departmentsOfficial.contains(department3));
		assertTrue(departmentsOfficial.contains(department4));
		assertFalse(departmentsOfficial.contains(department5));

		List departmentsNonOfficial = this.departmentDao.findByUniversityAndType(university1, DepartmentType.NONOFFICIAL);
		assertEquals(2+1, departmentsNonOfficial.size()); // + 1 Default Department
		assertFalse(departmentsNonOfficial.contains(department1));
		assertTrue(departmentsNonOfficial.contains(department2));
		assertTrue(departmentsNonOfficial.contains(department3));
		assertFalse(departmentsNonOfficial.contains(department4));
		assertFalse(departmentsNonOfficial.contains(department5));
	}
}