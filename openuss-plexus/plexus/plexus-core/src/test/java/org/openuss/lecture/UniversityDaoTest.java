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

		University university2 = University.Factory.newInstance();
		university2.setName(testUtility.unique("testUniversity"));
		university2.setShortcut(testUtility.unique("testU"));

		// Create Memberships
		List<User> users = new ArrayList<User>(5);
		for (int i = 0; i < 5; i++) {
			users.add(testUtility.createUserInDB());
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
		University university = University.Factory.newInstance();
		university.setName(testUtility.unique("testUniversity"));
		university.setShortcut(testUtility.unique("testU"));
		university.setDescription("This is a test University");
		Membership membership = Membership.Factory.newInstance();
		membership.getMembers().add(testUtility.createUserInDB());
		university.setMembership(membership);
		
		universityDao.create(university);
		assertNotNull(university.getId());
		
		// Test ValueObject
		UniversityInfo universityInfo = universityDao.toUniversityInfo(university);
		
		assertEquals(university.getId(), universityInfo.getId());
		assertEquals(university.getName(), universityInfo.getName());
		assertEquals(university.getShortcut(), universityInfo.getShortcut());
		assertEquals(university.getDescription(), universityInfo.getDescription());
		assertEquals(university.getUniversityType(), universityInfo.getUniversityType());
		
	}
	
	public void testUniversityDaoUniversityInfoToEntity() {
		
		// Create a complete University
		University university = University.Factory.newInstance();
		university.setName(testUtility.unique("testUniversity"));
		university.setShortcut(testUtility.unique("testU"));
		university.setDescription("This is a test University");
		Membership membership = Membership.Factory.newInstance();
		membership.getMembers().add(testUtility.createUserInDB());
		university.setMembership(membership);
		
		universityDao.create(university);
		assertNotNull(university.getId());
		
		// Create the corresponding ValueObject
		UniversityInfo universityInfo = new UniversityInfo();
		universityInfo.setId(university.getId());
		
		// Test toEntity
		University university2 = universityDao.universityInfoToEntity(universityInfo);
		
		assertEquals(university2.getId(), university.getId());
		assertEquals(university2.getName(), university.getName());
		assertEquals(university2.getShortcut(), university.getShortcut());
		assertEquals(university2.getDescription(), university.getDescription());
		assertEquals(university2.getUniversityType(), university.getUniversityType());
		
		// Create a new ValueObject (no Entity available)
		UniversityInfo universityInfo2 = new UniversityInfo();
		universityInfo2.setName(testUtility.unique("testUniversity2"));
		universityInfo2.setShortcut(testUtility.unique("testU2"));
		universityInfo2.setDescription("This is a test University2");
		universityInfo2.setUniversityType(UniversityType.UNIVERSITY);
		
	}
}