package org.openuss.search;

import java.util.List;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public class SearchEngineTest extends AbstractTransactionalDataSourceSpringContextTests{

	private SimpleDatabaseIndexing indexingDatabase;
	
	private LectureSearchQuery lectureSearcher;
	
	public void testFacultyDatabaseIndexing() {
		logger.debug("--> start indexing");
		indexingDatabase.indexDatabase();
		logger.debug("--> finishing indexing");
		List<String> results = lectureSearcher.search("Becker");
		for(String result : results) {
			logger.debug(" -> "+result);
		}
	}
	
	public SimpleDatabaseIndexing getIndexingDatabase() {
		return indexingDatabase;
	}

	public void setIndexingDatabase(SimpleDatabaseIndexing indexingDatabase) {
		this.indexingDatabase = indexingDatabase;
	}

	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext.xml",
			"classpath*:applicationContext-localDataSource.xml",
			"classpath*:applicationContext-beans.xml", 
			"classpath*:applicationContext-tests.xml",
			"classpath*:applicationContext-lucene.xml",
			"classpath*:applicationContext-cache.xml",
			"classpath*:beanRefFactory",
			"classpath*:testSecurity.xml",
			"classpath*:testDataSource.xml"};
	}

	public LectureSearchQuery getLectureSearcher() {
		return lectureSearcher;
	}

	public void setLectureSearcher(LectureSearchQuery lectureSearchQuery) {
		this.lectureSearcher = lectureSearchQuery;
	}
}
