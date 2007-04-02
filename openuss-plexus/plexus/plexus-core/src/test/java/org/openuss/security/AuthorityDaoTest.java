// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;



/**
 * JUnit Test for Spring Hibernate RoleDao class.
 * @see org.openuss.security.AuthorityDao
 */
public class AuthorityDaoTest extends AuthorityDaoTestBase {
	
	public void testRoleDaoCreate() {
		Authority authority = new AuthorityImpl();
		assertNull(authority.getId());
		authorityDao.create(authority);
		assertNotNull(authority.getId());
	}
}