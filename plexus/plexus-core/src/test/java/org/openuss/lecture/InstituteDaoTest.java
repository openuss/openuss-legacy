// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.List;

import org.openuss.TestUtility;
import org.openuss.security.Membership;

/**
 * JUnit Test for Spring Hibernate InstituteDao class.
 * 
 * @see org.openuss.lecture.InstituteDao
 * @author Ron Haus, Ingo Dueppe
 */
public class InstituteDaoTest extends InstituteDaoTestBase {

	private TestUtility testUtility;

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public void testInstituteDaoCreate() {
		// Create Department
		Department department = testUtility.createUniqueDepartmentInDB();
		flush();

		// Create Institutes
		Institute institute1 = new InstituteImpl();
		institute1.setName("Lehrstuhl WI");
		institute1.setShortcut("LSWI");
		institute1.setOwnerName("Administrator");
		institute1.setEnabled(true);
		institute1.setDescription("Testdescription1");
		institute1.setMembership(Membership.Factory.newInstance());
		department.add(institute1);

		Institute institute2 = new InstituteImpl();
		institute2.setName("Lehrstuhl Finanzierung");
		institute2.setShortcut("LSF");
		institute2.setOwnerName("Administrator");
		institute2.setEnabled(true);
		institute2.setDescription("Testdescription2");
		institute2.setMembership(Membership.Factory.newInstance());
		department.add(institute2);

		assertNull(institute1.getId());
		instituteDao.create(institute1);
		assertNotNull(institute1.getId());

		assertNull(institute2.getId());
		instituteDao.create(institute2);
		assertNotNull(institute2.getId());

		// Synchronize with Database
		flush();
	}

	public void testUniqueShortcut() {
		// Create Institutes
		Institute institute1 = testUtility.createUniqueInstituteInDB();
		institute1.setShortcut("xxxx");
		instituteDao.update(institute1);
		assertNotNull(institute1.getId());

		Institute institute2 = testUtility.createUniqueInstituteInDB();
		institute2.setShortcut("xxxx");
		instituteDao.update(institute2);
		assertNotNull(institute2.getId());

		try {
			instituteDao.update(institute2);
			// Synchronize with Database
			flush();
		} catch (Exception e) {
			fail("Institute Shortcut must still be unique.");
		}
	}

	@SuppressWarnings( { "unchecked" })
	public void testInstituteDaoLoadAllEnabled() {
		// Get count of current institutes
		int enabledCount = this.getInstituteDao().findByEnabled(true).size();
		int disabledCount = this.getInstituteDao().findByEnabled(false).size();
		
		Department department = testUtility.createUniqueDepartmentInDB();
		
		// Create 3 Institutes
		Institute institute1 = testUtility.createUniqueInstituteInDB();
		institute1.setDepartment(department);
		institute1.setEnabled(true);
		enabledCount++;
		Institute institute2 = testUtility.createUniqueInstituteInDB();
		institute2.setDepartment(department);
		institute2.setEnabled(true);
		enabledCount++;
		Institute institute3 = testUtility.createUniqueInstituteInDB();
		institute3.setDepartment(department);
		institute3.setEnabled(false);
		disabledCount++;

		// Synchronize with Database
		flush();

		// Test
		List institutesEnabled = this.instituteDao.findByEnabled(true);
		assertEquals(enabledCount, institutesEnabled.size());
		assertTrue(institutesEnabled.contains(institute1));
		assertTrue(institutesEnabled.contains(institute2));
		assertFalse(institutesEnabled.contains(institute3));

		List institutesDisabled = this.instituteDao.findByEnabled(false);
		assertEquals(disabledCount, institutesDisabled.size());
		assertFalse(institutesDisabled.contains(institute1));
		assertFalse(institutesDisabled.contains(institute2));
		assertTrue(institutesDisabled.contains(institute3));

	}
	
public void testInstituteDaoToInstituteInfo() {
		
	// Create a complete Institute
	Institute institute = testUtility.createUniqueInstituteInDB();
	assertNotNull(institute.getId());
	
	// Test ValueObject
	InstituteInfo instituteInfo = instituteDao.toInstituteInfo(institute);
	
	assertEquals(institute.getId(), instituteInfo.getId());
	assertEquals(institute.getName(), instituteInfo.getName());
	assertEquals(institute.getShortcut(), instituteInfo.getShortcut());
	assertEquals(institute.getOwnerName(), instituteInfo.getOwnerName());
	assertEquals(institute.isEnabled(), instituteInfo.isEnabled());
	assertEquals(institute.getDescription(), instituteInfo.getDescription());	
	assertEquals(institute.getAddress(), instituteInfo.getAddress());
	assertEquals(institute.getCity(), instituteInfo.getCity());
	assertEquals(institute.getCountry(), instituteInfo.getCountry());
	assertEquals(institute.getEmail(), instituteInfo.getEmail());
	assertEquals(institute.getLocale(), instituteInfo.getLocale());
	assertEquals(institute.getPostcode(), instituteInfo.getPostcode());
	assertEquals(institute.getTelefax(), instituteInfo.getTelefax());
	assertEquals(institute.getTelephone(), instituteInfo.getTelephone());
	assertEquals(institute.getWebsite(), instituteInfo.getWebsite());
	assertEquals(institute.getTheme(), instituteInfo.getTheme());
	assertEquals(institute.getDepartment().getId(), instituteInfo.getDepartmentId());

	}

	public void testInstituteDaoInstituteInfoToEntity() {

		// Create a complete Institute
		Institute instituteDefault = testUtility.createUniqueInstituteInDB();
		assertNotNull(instituteDefault.getId());
		
		// Create the corresponding ValueObject
		InstituteInfo instituteInfo1 = new InstituteInfo();
		instituteInfo1.setId(instituteDefault.getId());
		instituteInfo1.setAddress(instituteDefault.getAddress());
		instituteInfo1.setCity(instituteDefault.getCity());
		instituteInfo1.setCountry(instituteDefault.getCountry());
		instituteInfo1.setDescription(instituteDefault.getDescription());
		instituteInfo1.setEmail(instituteDefault.getEmail());
		instituteInfo1.setEnabled(instituteDefault.isEnabled());
		instituteInfo1.setLocale(instituteDefault.getLocale());
		instituteInfo1.setName(instituteDefault.getName());
		instituteInfo1.setOwnerName(instituteDefault.getOwnerName());
		instituteInfo1.setPostcode(instituteDefault.getPostcode());
		instituteInfo1.setShortcut(instituteDefault.getShortcut());
		instituteInfo1.setTelefax(instituteDefault.getTelefax());
		instituteInfo1.setTelephone(instituteDefault.getTelephone());
		instituteInfo1.setTheme(instituteDefault.getTheme());
		instituteInfo1.setWebsite(instituteDefault.getWebsite());
		instituteInfo1.setDepartmentId(instituteDefault.getDepartment().getId());
		
		// Test toEntity
		Institute institute1 = instituteDao.instituteInfoToEntity(instituteInfo1);
		
		assertEquals(instituteInfo1.getId(), institute1.getId());
		assertEquals(instituteInfo1.getName(), institute1.getName());
		assertEquals(instituteInfo1.getShortcut(), institute1.getShortcut());
		assertEquals(instituteInfo1.getOwnerName(), institute1.getOwnerName());
		assertEquals(instituteInfo1.isEnabled(), institute1.isEnabled());
		assertEquals(instituteInfo1.getDescription(), institute1.getDescription());
		assertEquals(instituteInfo1.getAddress(), institute1.getAddress());
		assertEquals(instituteInfo1.getCity(), institute1.getCity());
		assertEquals(instituteInfo1.getCountry(), institute1.getCountry());
		assertEquals(instituteInfo1.getEmail(), institute1.getEmail());
		assertEquals(instituteInfo1.getLocale(), institute1.getLocale());
		assertEquals(instituteInfo1.getPostcode(), institute1.getPostcode());
		assertEquals(instituteInfo1.getTelefax(), institute1.getTelefax());
		assertEquals(instituteInfo1.getTelephone(), institute1.getTelephone());
		assertEquals(instituteInfo1.getWebsite(), institute1.getWebsite());
		assertEquals(instituteInfo1.getTheme(), institute1.getTheme());
		assertEquals(instituteInfo1.getDepartmentId(), institute1.getDepartment().getId());
		
		
		// Create a new ValueObject (no Entity available)
		InstituteInfo instituteInfo2 = new InstituteInfo();
		instituteInfo2.setName(testUtility.unique("testUniversity2"));
		instituteInfo2.setShortcut(testUtility.unique("testU2"));
		instituteInfo2.setDescription("This is a test University2");
		instituteInfo2.setEnabled(true);
		instituteInfo2.setAddress("Leo 18");
		instituteInfo2.setCity("Münster");
		instituteInfo2.setCountry("Germany");
		instituteInfo2.setEmail("openuss@uni-muenster.de");
		instituteInfo2.setLocale("de");
		instituteInfo2.setPostcode("48149");
		instituteInfo2.setTelefax("0251-telefax");
		instituteInfo2.setTelephone("0251-telephone");
		instituteInfo2.setTheme("plexus");
		instituteInfo2.setWebsite("www.openuss.de");
		instituteInfo2.setDepartmentId(instituteDefault.getDepartment().getId());
		
		
		// Test toEntity
		Institute institute2 = instituteDao.instituteInfoToEntity(instituteInfo2);
		
		assertEquals(instituteInfo2.getId(), institute2.getId());
		assertEquals(instituteInfo2.getName(), institute2.getName());
		assertEquals(instituteInfo2.getShortcut(), institute2.getShortcut());
		assertEquals(instituteInfo2.getOwnerName(), institute2.getOwnerName());
		assertEquals(instituteInfo2.isEnabled(), institute2.isEnabled());
		assertEquals(instituteInfo2.getDescription(), institute2.getDescription());
		assertEquals(instituteInfo2.getAddress(), institute2.getAddress());
		assertEquals(instituteInfo2.getCity(), institute2.getCity());
		assertEquals(instituteInfo2.getCountry(), institute2.getCountry());
		assertEquals(instituteInfo2.getEmail(), institute2.getEmail());
		assertEquals(instituteInfo2.getLocale(), institute2.getLocale());
		assertEquals(instituteInfo2.getPostcode(), institute2.getPostcode());
		assertEquals(instituteInfo2.getTelefax(), institute2.getTelefax());
		assertEquals(instituteInfo2.getTelephone(), institute2.getTelephone());
		assertEquals(instituteInfo2.getWebsite(), institute2.getWebsite());
		assertEquals(instituteInfo2.getTheme(), institute2.getTheme());
		assertEquals(instituteInfo2.getDepartmentId(), institute2.getDepartment().getId());
	}

}
