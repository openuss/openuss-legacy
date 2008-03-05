// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;


/**
 * JUnit Test for Spring Hibernate UserDnPatternDao class.
 * @see org.openuss.security.ldap.UserDnPatternDao
 */
public class UserDnPatternDaoTest extends UserDnPatternDaoTestBase {
	
	public void testUserDnPatternDaoCreate() {
		UserDnPattern userDnPattern = UserDnPattern.Factory.newInstance();
		userDnPattern.setName("testUserDnPattern name");		
		assertNull(userDnPattern.getId());
		userDnPatternDao.create(userDnPattern);
		assertNotNull(userDnPattern.getId());
	}
}
