// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate LdapConfigurationNotifyService class.
 * @see org.openuss.security.ldap.LdapConfigurationNotifyService
 */
public abstract class LdapConfigurationNotifyServiceIntegrationTestBase extends AbstractTransactionalDataSourceSpringContextTests {
	
	protected static final Logger logger = Logger.getLogger(LdapConfigurationNotifyServiceIntegrationTestBase.class);

	protected LdapConfigurationNotifyService ldapConfigurationNotifyService;
	
	public LdapConfigurationNotifyService getLdapConfigurationNotifyService() {
		return ldapConfigurationNotifyService;
	}

	public void setLdapConfigurationNotifyService(LdapConfigurationNotifyService ldapConfigurationNotifyService) {
		this.ldapConfigurationNotifyService = ldapConfigurationNotifyService;
	}

	public void testLdapConfigurationNotifyServiceInjection() {
		assertNotNull(ldapConfigurationNotifyService);
	}
	
	protected void commit() {
		setComplete();
		endTransaction();
		startNewTransaction();
	}
	
	protected void flush() {
	    sessionFactory.getCurrentSession().flush();
	}
	
	protected SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
	    return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) { 
	     this.sessionFactory = sessionFactory;
	}
	
	protected TestUtility testUtility;
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext.xml", 
			"classpath*:applicationContext-beans.xml",
			"classpath*:applicationContext-lucene.xml",
			"classpath*:applicationContext-cache.xml", 
			"classpath*:applicationContext-messaging.xml",
			"classpath*:applicationContext-resources.xml",
			"classpath*:applicationContext-aop.xml",
			"classpath*:applicationContext-events.xml",
			"classpath*:testContext.xml", 
			"classpath*:testDisableSecurity.xml", 
			"classpath*:testDataSource.xml"};
	}
}