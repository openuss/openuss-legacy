// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;


/**
 * JUnit Test for Spring Hibernate UserDnPatternSetDao class.
 * @see org.openuss.security.ldap.UserDnPatternSetDao
 */
public class UserDnPatternSetDaoTest extends UserDnPatternSetDaoTestBase {
	
	public void testUserDnPatternSetDaoCreate() {
		UserDnPatternSet userDnPatternSet = UserDnPatternSet.Factory.newInstance();
		userDnPatternSet.setName("user dn pattern test ");
		assertNull(userDnPatternSet.getId());
		userDnPatternSetDao.create(userDnPatternSet);
		assertNotNull(userDnPatternSet.getId());
	}
}
