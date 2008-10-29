package org.openuss.wiki;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


/**
 * JUnit Test for Spring Hibernate WikiSiteVersionDao class.
 * @see org.openuss.wiki.WikiSiteVersionDao
 */
public abstract class WikiSiteVersionDaoTestBase extends AbstractTransactionalDataSourceSpringContextTests {
	
	protected static final Logger logger = Logger.getLogger(WikiSiteVersionDaoTestBase.class);

	protected WikiSiteVersionDao wikiSiteVersionDao;
	
	public WikiSiteVersionDao getWikiSiteVersionDao() {
		return wikiSiteVersionDao;
	}

	public void setWikiSiteVersionDao(WikiSiteVersionDao wikiSiteVersionDao) {
		this.wikiSiteVersionDao = wikiSiteVersionDao;
	}

	public void testWikiSiteVersionDaoInjection() {
		assertNotNull(wikiSiteVersionDao);
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