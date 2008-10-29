package org.openuss.wiki;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


/**
 * JUnit Test for Spring Hibernate WikiSiteDao class.
 * @see org.openuss.wiki.WikiSiteDao
 */
public abstract class WikiSiteDaoTestBase extends AbstractTransactionalDataSourceSpringContextTests {
	
	protected static final Logger logger = Logger.getLogger(WikiSiteDaoTestBase.class);

	protected WikiSiteDao wikiSiteDao;
	
	public WikiSiteDao getWikiSiteDao() {
		return wikiSiteDao;
	}

	public void setWikiSiteDao(WikiSiteDao wikiSiteDao) {
		this.wikiSiteDao = wikiSiteDao;
	}

	public void testWikiSiteDaoInjection() {
		assertNotNull(wikiSiteDao);
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