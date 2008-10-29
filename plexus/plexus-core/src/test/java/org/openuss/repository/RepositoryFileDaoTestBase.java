// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.repository;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


/**
 * JUnit Test for Spring Hibernate RepositoryFileDao class.
 * @see org.openuss.repository.RepositoryFileDao
 */
public abstract class RepositoryFileDaoTestBase extends AbstractTransactionalDataSourceSpringContextTests {
	
	protected static final Logger logger = Logger.getLogger(RepositoryFileDaoTestBase.class);

	protected RepositoryFileDao repositoryFileDao;
	
	public RepositoryFileDao getRepositoryFileDao() {
		return repositoryFileDao;
	}

	public void setRepositoryFileDao(RepositoryFileDao repositoryFileDao) {
		this.repositoryFileDao = repositoryFileDao;
	}

	public void testRepositoryFileDaoInjection() {
		assertNotNull(repositoryFileDao);
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