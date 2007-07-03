// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate MembershipDao class.
 * @see org.openuss.security.MembershipDao
 * @author Ron Haus
 */
public class MembershipDaoTest extends MembershipDaoTestBase {
	
	private TestUtility testUtility;
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
	public void testMembershipDaoCreate() {
		Membership membership = Membership.Factory.newInstance();
		membership.setOwner(testUtility.createUserInDB());
		membership.getAspirants().add(testUtility.createUserInDB());
		membership.getMembers().add(testUtility.createUserInDB());
		
		assertNull(membership.getId());
		membershipDao.create(membership);
		assertNotNull(membership.getId());
	}
}