package org.openuss.search;

import java.util.List;

import org.openuss.lecture.Faculty;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public class LectureIndexerTest extends AbstractTransactionalDataSourceSpringContextTests{

	private LectureSearchQuery lectureSearcher;
	
	private LectureIndexer lectureIndexer;
	
	public void testLectureIndexing() {
		logger.debug("--> start indexing");
		Faculty faculty = createFaculty();
		lectureIndexer.addFaculty(faculty);
		
		List<String> results = lectureSearcher.search("23456");
		assertNotNull(results);
		assertEquals(1, results.size());
	}
	
	public Faculty createFaculty() {
		Faculty faculty = Faculty.Factory.newInstance();
		faculty.setId(23456L);
		faculty.setName("name");
		faculty.setAddress("address");
		faculty.setOwnername("ownername");
		return faculty;
	}
	

	public LectureSearchQuery getLectureSearcher() {
		return lectureSearcher;
	}

	public void setLectureSearcher(LectureSearchQuery lectureSearchQuery) {
		this.lectureSearcher = lectureSearchQuery;
	}

	public LectureIndexer getLectureIndexer() {
		return lectureIndexer;
	}

	public void setLectureIndexer(LectureIndexer lectureIndexer) {
		this.lectureIndexer = lectureIndexer;
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
}
