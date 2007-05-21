package org.openuss.lecture;

import org.openuss.lecture.indexer.FacultyIndexer;
import org.openuss.security.User;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class FacultyIndexerTest extends AbstractDependencyInjectionSpringContextTests {

	private FacultyIndexer facultyIndexer;
	
	private FacultyDao facultyDao = new FacultyDaoMock();
	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		facultyIndexer.setFacultyDao(facultyDao);
	}

	public void testIndexer() {
		Faculty faculty = new LectureBuilder().createFaculty(User.Factory.newInstance()).getFaculty();

		facultyDao.create(faculty);
		
		facultyIndexer.setDomainObject(faculty);
		facultyIndexer.setCommandType("CREATE");
		facultyIndexer.execute();
		
		faculty.setName("neuer name");
		
		facultyIndexer.setDomainObject(faculty);
		facultyIndexer.setCommandType("UPDATE");
		facultyIndexer.execute();
		
	}
	
	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext.xml", 
			"classpath*:applicationContext-beans.xml",
			"classpath*:applicationContext-tests.xml", 
			"classpath*:applicationContext-lucene.xml",
			"classpath*:applicationContext-cache.xml", 
			"classpath*:applicationContext-messaging.xml",
			"classpath*:applicationContext-resources.xml",
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

}
