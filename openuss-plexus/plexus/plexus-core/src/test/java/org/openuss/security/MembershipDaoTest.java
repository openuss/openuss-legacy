// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;


/**
 * JUnit Test for Spring Hibernate MembershipDao class.
 * @see org.openuss.security.MembershipDao
 */
public class MembershipDaoTest extends MembershipDaoTestBase {
	
	public void testMembershipDaoCreate() {
		Membership membership = new MembershipImpl();
		assertNull(membership.getId());
		membershipDao.create(membership);
		assertNotNull(membership.getId());
	}
}