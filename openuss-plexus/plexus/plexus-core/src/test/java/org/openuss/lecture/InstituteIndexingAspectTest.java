package org.openuss.lecture;

import org.openuss.TestUtility;
import org.openuss.foundation.DomainObject;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * Test case for recreating lecture index command
 * 
 * @author Ingo Dueppe
 */
public class InstituteIndexingAspectTest extends AbstractTransactionalDataSourceSpringContextTests {

	private LectureService lectureService;
	
	private InstituteIndexingAspect instituteIndexAspectBean;

	private IndexerServiceMock indexerMock ;
	
	private TestUtility testUtility;

	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		indexerMock = new IndexerServiceMock();
		instituteIndexAspectBean.setIndexerService(indexerMock);
	}

	public void testLectureIndex() throws Exception {
		//FIXME on creation faculties are disabled now, fix indexing according to this. 
		/*
		 
		User user = testUtility.createSecureContext();
		Institute institute = new LectureBuilder().createInstitute(user).getInstitute();
		lectureService.createInstitute(institute);
		assertEquals(1, indexerMock.create);
		lectureService.persist(institute);
		assertEquals(1, indexerMock.update);
		lectureService.removeInstitute(institute.getId());
		assertEquals(1, indexerMock.delete);
		
		*/
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

	public InstituteIndexingAspect getInstituteIndexAspectBean() {
		return instituteIndexAspectBean;
	}

	public void setInstituteIndexAspectBean(InstituteIndexingAspect instituteIndexAspectBean) {
		this.instituteIndexAspectBean = instituteIndexAspectBean;
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

