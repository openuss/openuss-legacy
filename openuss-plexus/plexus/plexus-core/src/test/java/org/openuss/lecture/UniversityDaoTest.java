// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.openuss.TestUtility;
import org.openuss.security.Membership;


/**
 * JUnit Test for Spring Hibernate UniversityDao class.
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
		University university = University.Factory.newInstance();
		university.setName("testUniversity");
		university.setShortcut("testU");
		
		Membership membership = Membership.Factory.newInstance();
		membership.setOwner(testUtility.createUserInDB());
		membership.getAspirants().add(testUtility.createUserInDB());
		membership.getMembers().add(testUtility.createUserInDB());
		university.setMembership(membership);
		
		assertNull(university.getId());
		universityDao.create(university);
		assertNotNull(university.getId());
	}
}