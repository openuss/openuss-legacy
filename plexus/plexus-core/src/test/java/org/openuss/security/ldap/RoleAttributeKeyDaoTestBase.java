// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


/**
 * JUnit Test for Spring Hibernate RoleAttributeKeyDao class.
 * @see org.openuss.security.ldap.RoleAttributeKeyDao
 */
public abstract class RoleAttributeKeyDaoTestBase extends AbstractTransactionalDataSourceSpringContextTests {
	
	protected static final Logger logger = Logger.getLogger(RoleAttributeKeyDaoTestBase.class);

	protected RoleAttributeKeyDao roleAttributeKeyDao;
	
	public RoleAttributeKeyDao getRoleAttributeKeyDao() {
		return roleAttributeKeyDao;
	}

	public void setRoleAttributeKeyDao(RoleAttributeKeyDao roleAttributeKeyDao) {
		this.roleAttributeKeyDao = roleAttributeKeyDao;
	}

	public void testRoleAttributeKeyDaoInjection() {
		assertNotNull(roleAttributeKeyDao);
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
	
	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext.xml", 
			"classpath*:applicationContext-beans.xml",
			"classpath*:applicationContext-lucene.xml",
			"classpath*:applicationContext-cache.xml", 
			"classpath*:applicationContext-messaging.xml",
			"classpath*:applicationContext-resources.xml",
			"classpath*:applicationContext-aop.xml",
			"classpath*:testContext.xml", 
			"classpath*:testDisableSecurity.xml", 
			"classpath*:testDataSource.xml"};
	}
	
	
}