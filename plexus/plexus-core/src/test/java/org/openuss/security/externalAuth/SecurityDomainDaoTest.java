// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.externalAuth;


/**
 * JUnit Test for Spring Hibernate SecurityDomainDao class.
 * @see org.openuss.security.externalAuth.SecurityDomainDao
 */
public class SecurityDomainDaoTest extends SecurityDomainDaoTestBase {
	
	public void testSecurityDomainDaoCreate() {
		SecurityDomain securityDomain = SecurityDomain.Factory.newInstance();
		securityDomain.setShortName(" ");
		securityDomain.setName(" ");
		assertNull(securityDomain.getId());
		securityDomainDao.create(securityDomain);
		assertNotNull(securityDomain.getId());
	}
}
