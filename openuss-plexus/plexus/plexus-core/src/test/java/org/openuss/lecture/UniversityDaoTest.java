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
 * JUnit Test for Spring Hibernate UniversityDao class.
 * 
 * @see org.openuss.lecture.UniversityDao
 * @author Ron Haus
 */
public class UniversityDaoTest extends UniversityDaoTestBase {

	private TestUtility testUtility;

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public void testUniversityDaoCreate() {

		// Create Universities
		University university = University.Factory.newInstance();
		university.setName(testUtility.unique("testUniversity"));
		university.setShortcut(testUtility.unique("testU"));
		university.setOwnerName("Administrator");
		university.setEnabled(true);

		University university2 = University.Factory.newInstance();
		university2.setName(testUtility.unique("testUniversity"));
		university2.setShortcut(testUtility.unique("testU"));
		university2.setOwnerName("Administrator");
		university2.setEnabled(true);

		// Create Memberships
		List<User> users = new ArrayList<User>(5);
		for (int i = 0; i < 5; i++) {
			users.add(testUtility.createUniqueUserInDB());
		}

		Membership membership = Membership.Factory.newInstance();
		membership.getMembers().add(users.get(0));
		membership.getAspirants().add(users.get(1));
		membership.getAspirants().add(users.get(2));
		membership.getMembers().add(users.get(3));
		membership.getMembers().add(users.get(4));

		Membership membership2 = Membership.Factory.newInstance();
		membership.getMembers().add(users.get(0));
		membership2.getAspirants().add(users.get(1));
		membership2.getAspirants().add(users.get(2));
		membership2.getMembers().add(users.get(3));
		membership2.getMembers().add(users.get(4));

		university.setMembership(membership);
		university2.setMembership(membership2);

		// Test DAO
		assertNull(university.getId());
		universityDao.create(university);
		assertNotNull(university.getId());

		assertNull(university2.getId());
		universityDao.create(university2);
		assertNotNull(university2.getId());
	}
	
	public void testUniversityDaoToUniversityInfo() {
		
		// Create a complete University
		University university = testUtility.createUniqueUniversityInDB();
		assertNotNull(university.getId());
		
		// Test ValueObject
		UniversityInfo universityInfo = universityDao.toUniversityInfo(university);
		
		assertEquals(university.getId(), universityInfo.getId());
		assertEquals(university.getName(), universityInfo.getName());
		assertEquals(university.getShortcut(), universityInfo.getShortcut());
		assertEquals(university.getOwnerName(), universityInfo.getOwnerName());
		assertEquals(university.getEnabled(), universityInfo.getEnabled());
		assertEquals(university.getDescription(), universityInfo.getDescription());
		assertEquals(university.getUniversityType(), universityInfo.getUniversityType());		
		assertEquals(university.getAddress(), universityInfo.getAddress());
		assertEquals(university.getCity(), universityInfo.getCity());
		assertEquals(university.getCountry(), universityInfo.getCountry());
		assertEquals(university.getEmail(), universityInfo.getEmail());
		assertEquals(university.getLocale(), universityInfo.getLocale());
		assertEquals(university.getPostcode(), universityInfo.getPostcode());
		assertEquals(university.getTelefax(), universityInfo.getTelefax());
		assertEquals(university.getTelephone(), universityInfo.getTelephone());
		assertEquals(university.getWebsite(), universityInfo.getWebsite());
		assertEquals(university.getTheme(), universityInfo.getTheme());
	}
	
	public void testUniversityDaoUniversityInfoToEntity() {
		
		// Create a complete University
		University universityDefault = testUtility.createUniqueUniversityInDB();
		assertNotNull(universityDefault.getId());
		
		// Create the corresponding ValueObject
		UniversityInfo universityInfo1 = new UniversityInfo();
		universityInfo1.setId(universityDefault.getId());
		
		// Test toEntity
		University university1 = universityDao.universityInfoToEntity(universityInfo1);
		
		assertEquals(universityInfo1.getId(), university1.getId());
		assertEquals(universityInfo1.getName(), university1.getName());
		assertEquals(universityInfo1.getShortcut(), university1.getShortcut());
		assertEquals(universityInfo1.getOwnerName(), university1.getOwnerName());
		assertEquals(universityInfo1.getEnabled(), university1.getEnabled());
		assertEquals(universityInfo1.getDescription(), university1.getDescription());
		assertEquals(universityInfo1.getUniversityType(), university1.getUniversityType());
		assertEquals(universityInfo1.getAddress(), university1.getAddress());
		assertEquals(universityInfo1.getCity(), university1.getCity());
		assertEquals(universityInfo1.getCountry(), university1.getCountry());
		assertEquals(universityInfo1.getEmail(), university1.getEmail());
		assertEquals(universityInfo1.getLocale(), university1.getLocale());
		assertEquals(universityInfo1.getPostcode(), university1.getPostcode());
		assertEquals(universityInfo1.getTelefax(), university1.getTelefax());
		assertEquals(universityInfo1.getTelephone(), university1.getTelephone());
		assertEquals(universityInfo1.getWebsite(), university1.getWebsite());
		assertEquals(universityInfo1.getTheme(), university1.getTheme());
		
		
		// Create a new ValueObject (no Entity available)
		UniversityInfo universityInfo2 = new UniversityInfo();
		universityInfo2.setName(testUtility.unique("testUniversity2"));
		universityInfo2.setShortcut(testUtility.unique("testU2"));
		universityInfo2.setDescription("This is a test University2");
		universityInfo2.setUniversityType(UniversityType.UNIVERSITY);
		universityInfo2.setEnabled(true);
		universityInfo2.setOwnerName("Administrator");
		
		// Test toEntity
		University university2 = universityDao.universityInfoToEntity(universityInfo2);
		
		assertEquals(universityInfo2.getId(), university2.getId());
		assertEquals(universityInfo2.getName(), university2.getName());
		assertEquals(universityInfo2.getShortcut(), university2.getShortcut());
		assertEquals(universityInfo2.getOwnerName(), university2.getOwnerName());
		assertEquals(universityInfo2.getEnabled(), university2.getEnabled());
		assertEquals(universityInfo2.getDescription(), university2.getDescription());
		assertEquals(universityInfo2.getUniversityType(), university2.getUniversityType());
		assertEquals(universityInfo2.getAddress(), university2.getAddress());
		assertEquals(universityInfo2.getCity(), university2.getCity());
		assertEquals(universityInfo2.getCountry(), university2.getCountry());
		assertEquals(universityInfo2.getEmail(), university2.getEmail());
		assertEquals(universityInfo2.getLocale(), university2.getLocale());
		assertEquals(universityInfo2.getPostcode(), university2.getPostcode());
		assertEquals(universityInfo2.getTelefax(), university2.getTelefax());
		assertEquals(universityInfo2.getTelephone(), university2.getTelephone());
		assertEquals(universityInfo2.getWebsite(), university2.getWebsite());
		assertEquals(universityInfo2.getTheme(), university2.getTheme());
	}
}