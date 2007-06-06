package org.openuss.search;

import org.openuss.lecture.FacultyIndexer;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class DomainIndexerFactoryTest extends AbstractDependencyInjectionSpringContextTests {

	private FacultyIndexer facultyIndexer;
	
	private DomainIndexerFactory domainIndexerFactory;
	
	public void testIndexer() {
		//TODO write tests for DomainIndexerFactory
		
	}
	
	public FacultyIndexer getFacultyIndexer() {
		return facultyIndexer;
	}

	public void setFacultyIndexer(FacultyIndexer facultyIndexer) {
		this.facultyIndexer = facultyIndexer;
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
				"classpath*:testContext.xml", 
				"classpath*:testSecurity.xml", 
				"classpath*:testDataSource.xml"};
	}	
	
}
