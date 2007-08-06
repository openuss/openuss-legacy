package org.openuss.lecture;

import org.apache.log4j.Logger;
import org.openuss.TestUtility;
import org.openuss.foundation.DomainObject;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;
import org.openuss.security.User;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * Test case for the spring aspect initiating the create, update and delete 
 * process of the institute indexing.
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
public class InstituteIndexingAspectTest extends AbstractTransactionalDataSourceSpringContextTests {

	private static final Logger logger = Logger.getLogger(InstituteIndexingAspectTest.class);
	
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
	 
		//User user = testUtility.createSecureContext();
		//Institute institute = new LectureBuilder().createInstitute(user).getInstitute();
		Institute institute = new LectureBuilder().createInstitute(User.Factory.newInstance()).getInstitute();
		logger.debug(institute.getDescription());
		// create institute
		//lectureService.createInstitute(institute);
		InstituteInfo info = new InstituteInfo(null,"dfd","fd","dfd","df",false);
		lectureService.create(info, 212l);
		// activate institute
		//institute.setEnabled(true);
		//lectureService.persist(institute);
		
		// indexerMockCreate should be 0 due to the fact that institutes are initiated
		// disabled. Therefore they are activated via the updateIndex-method --> indexerMockUpdate should be 1.
		assertEquals(0, indexerMock.create);
		assertEquals(1, indexerMock.update);
		// delete institute
		// indexMockDelete should be 1 --> Deleting institute index works properly
		lectureService.removeInstitute(institute.getId());
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
			logger.debug("method createIndex: Increment testCreateIndex");
			create++;
		}
		
		public void deleteIndex(DomainObject domainObject) throws IndexerApplicationException {
			logger.debug("method deleteIndex: Increment testDeleteIndex");
			delete++;
		}
		
		public void updateIndex(DomainObject domainObject) throws IndexerApplicationException {
			logger.debug("method updateIndex: Increment testUpdateIndex");
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

