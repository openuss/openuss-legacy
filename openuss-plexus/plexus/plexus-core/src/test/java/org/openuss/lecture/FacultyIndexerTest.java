package org.openuss.lecture;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.openuss.search.DomainResult;
import org.openuss.security.User;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class FacultyIndexerTest extends AbstractDependencyInjectionSpringContextTests {

	private FacultyIndexer facultyIndexer;
	
	private FacultyDao facultyDao = new FacultyDaoMock();
	
	private LectureSearcher lectureSearcher;
							   	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		facultyIndexer.setFacultyDao(facultyDao);
	}

	public void testIndexer() {
		Faculty faculty = new LectureBuilder().createFaculty(User.Factory.newInstance()).getFaculty();

		facultyDao.create(faculty);
		
		facultyIndexer.setDomainObject(faculty);
		facultyIndexer.create();
		
		faculty.setName("neuer name");
		
		facultyIndexer.update();
		
		facultyIndexer.delete();
	}
	
	public void testIndexingAndSearching() {
		Faculty faculty = new LectureBuilder().createFaculty(User.Factory.newInstance()).getFaculty();
		faculty.setOwnername("test owner name grob");
		facultyDao.create(faculty);
		
		facultyIndexer.setDomainObject(faculty);
		facultyIndexer.create();
		
		List<String> results = lectureSearcher.search("grob");
		DomainResult[] resultObjs = (DomainResult[]) results.toArray(new DomainResult[results.size()]);
		logger.debug("--- RESULTS ---> "+ArrayUtils.toString(resultObjs));
		assertNotNull(results);
		assertTrue(results.size() >= 1);
		
	}
	
	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext.xml", 
			"classpath*:applicationContext-beans.xml",
			"classpath*:applicationContext-lucene.xml",
			"classpath*:applicationContext-cache.xml", 
			"classpath*:applicationContext-messaging.xml",
			"classpath*:applicationContext-resources.xml",
			"classpath*:testContext.xml", 
			"classpath*:testSecurity.xml", 
			"classpath*:testDataSource.xml"};
	}
	public FacultyIndexer getFacultyIndexer() {
		return facultyIndexer;
	}

	public void setFacultyIndexer(FacultyIndexer facultyIndexer) {
		this.facultyIndexer = facultyIndexer;
	}

	public LectureSearcher getLectureSearcher() {
		return lectureSearcher;
	}

	public void setLectureSearcher(LectureSearcher lectureSearcher) {
		this.lectureSearcher = lectureSearcher;
	}

}
