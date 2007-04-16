package org.openuss.search;

import org.openuss.lecture.FacultyIndexer;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class DomainIndexerFactoryTest extends AbstractDependencyInjectionSpringContextTests {

	private FacultyIndexer facultyIndexer;
	
	private DomainIndexerFactory domainIndexerFactory;
	
	//TODO write tests for DomainIndexerFactory
	
	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext.xml",
			"classpath*:applicationContext-localDataSource.xml",
			"classpath*:applicationContext-beans.xml", 
			"classpath*:applicationContext-tests.xml",
			"classpath*:applicationContext-cache.xml",
			"classpath*:applicationContext-lucene.xml",
			"classpath*:beanRefFactory",
			"classpath*:testSecurity.xml",
			"classpath*:testDataSource.xml"};
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
	
}
