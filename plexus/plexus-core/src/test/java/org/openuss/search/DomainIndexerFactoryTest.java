package org.openuss.search;

import org.openuss.lecture.indexer.FacultyIndexer;
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
			"classpath*:applicationContext-tests.xml",
			"classpath*:applicationContext-lucene.xml",
			"classpath*:applicationContext-cache.xml",
			"classpath*:beanRefFactory",
			"classpath*:testSecurity.xml",
			"classpath*:testDataSource.xml"};
	}	
	
}
