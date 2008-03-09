package org.openuss.search;

import org.openuss.lecture.InstituteIndexer;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class DomainIndexerFactoryTest extends AbstractDependencyInjectionSpringContextTests {

	private InstituteIndexer instituteIndexer;
	
	private DomainIndexerFactory domainIndexerFactory;
	
	public void testIndexer() {
		//TODO write tests for DomainIndexerFactory
		
	}
	
	public InstituteIndexer getInstituteIndexer() {
		return instituteIndexer;
	}

	public void setInstituteIndexer(InstituteIndexer instituteIndexer) {
		this.instituteIndexer = instituteIndexer;
	}

	public DomainIndexerFactory getDomainIndexerFactory() {
		return domainIndexerFactory;
	}

	public void setDomainIndexerFactory(DomainIndexerFactory domainIndexerFactory) {
		this.domainIndexerFactory = domainIndexerFactory;
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
				"classpath*:testSecurity.xml", 
				"classpath*:testDataSource.xml"};
	}	
	
}
