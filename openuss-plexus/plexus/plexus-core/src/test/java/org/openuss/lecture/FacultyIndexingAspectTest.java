package org.openuss.lecture;

import org.openuss.TestUtility;
import org.openuss.foundation.DomainObject;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;
import org.openuss.security.User;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * Test case for recreating lecture index command
 * 
 * @author Ingo Dueppe
 */
public class FacultyIndexingAspectTest extends AbstractTransactionalDataSourceSpringContextTests {

	private LectureService lectureService;
	
	private FacultyIndexingAspect facultyIndexAspectBean;

	private IndexerServiceMock indexerMock ;
	
	private TestUtility testUtility;

	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		indexerMock = new IndexerServiceMock();
		facultyIndexAspectBean.setIndexerService(indexerMock);
	}

	public void testLectureIndex() throws Exception {
		User user = testUtility.createSecureContext();
		Faculty faculty = new LectureBuilder().createFaculty(user).getFaculty();
		lectureService.createFaculty(faculty);
		assertEquals(1, indexerMock.create);
		lectureService.persist(faculty);
		assertEquals(1, indexerMock.update);
		lectureService.removeFaculty(faculty.getId());
		assertEquals(1, indexerMock.delete);
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

	public FacultyIndexingAspect getFacultyIndexAspectBean() {
		return facultyIndexAspectBean;
	}

	public void setFacultyIndexAspectBean(FacultyIndexingAspect facultyIndexAspectBean) {
		this.facultyIndexAspectBean = facultyIndexAspectBean;
	}

	
	private class IndexerServiceMock implements IndexerService {
		
		private int create;
		private int delete;
		private int update;
		
		public void createIndex(DomainObject domainObject) throws IndexerApplicationException {
			create++;
		}
		
		public void deleteIndex(DomainObject domainObject) throws IndexerApplicationException {
			delete++;
		}
		
		public void updateIndex(DomainObject domainObject) throws IndexerApplicationException {
			update++;
		}
	}


	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
}

