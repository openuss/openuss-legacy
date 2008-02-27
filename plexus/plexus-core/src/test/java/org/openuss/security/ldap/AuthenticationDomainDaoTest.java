// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;


/**
 * JUnit Test for Spring Hibernate AuthenticationDomainDao class.
 * @see org.openuss.security.ldap.AuthenticationDomainDao
 */
public class AuthenticationDomainDaoTest extends AuthenticationDomainDaoTestBase {
	
	public void testAuthenticationDomainDaoCreate() {
		AuthenticationDomain authenticationDomain = AuthenticationDomain.Factory.newInstance();
		authenticationDomain.setName("OpenuSS ");
		authenticationDomain.setDescription("OpenUSS Domain ");
		assertNull(authenticationDomain.getId());
		authenticationDomainDao.create(authenticationDomain);
		assertNotNull(authenticationDomain.getId());
	}
}
