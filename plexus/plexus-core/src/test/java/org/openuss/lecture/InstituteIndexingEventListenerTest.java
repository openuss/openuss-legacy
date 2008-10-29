package org.openuss.lecture;

import org.apache.log4j.Logger;
import org.openuss.TestUtility;
import org.openuss.foundation.DomainObject;
import org.openuss.lecture.events.InstituteIndexingEventListener;
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
public class InstituteIndexingEventListenerTest extends AbstractTransactionalDataSourceSpringContextTests {

	private static final Logger logger = Logger.getLogger(InstituteIndexingEventListenerTest.class);
		
	private InstituteService instituteService;
	
	private InstituteDao instituteDao;
	
	private InstituteIndexingEventListener instituteIndexEventListener;

	private IndexerServiceMock indexerMock ;
	
	private TestUtility testUtility;
	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		indexerMock = new IndexerServiceMock();
		instituteIndexEventListener.setIndexerService(indexerMock);
	}

	public void testLectureIndex() throws Exception {
	 
		// Create an OFFICIAL Department
		Department departmentOfficial = testUtility.createUniqueDepartmentInDB();
		departmentOfficial.setDepartmentType(DepartmentType.OFFICIAL);
		
		// Create new InstituteInfo object
		InstituteInfo instituteInfo = new InstituteInfo();
		instituteInfo.setName(testUtility.unique("testInstitute"));
		instituteInfo.setShortcut(testUtility.unique("testI"));
		instituteInfo.setOwnerName("Administrator");
		instituteInfo.setEmail("plexus@openuss-pelxus.com");
		instituteInfo.setLocale("de_DE");
		instituteInfo.setEnabled(false);
		instituteInfo.setDescription("This is a test Institute");
		instituteInfo.setDepartmentId(departmentOfficial.getId()); //Should be ignored by createInstitute

		// Create a User as Owner
		User owner = testUtility.createUniqueUserInDB();
		
		instituteService.create(instituteInfo, owner.getId());
		
		//	Create a default Institute
		Institute institute = testUtility.createUniqueInstituteInDB();
		
		//	Create new InstituteInfo object
		instituteInfo.setId(institute.getId());
		instituteInfo.setName(testUtility.unique("testInstitute"));
		instituteInfo.setShortcut(testUtility.unique("testI"));
		instituteInfo.setOwnerName("Administrator");
		instituteInfo.setEmail("plexus@openuss-pelxus.com");
		instituteInfo.setLocale("de_DE");
		instituteInfo.setEnabled(true);
		instituteInfo.setDescription("This is a test Institute at " + testUtility.unique("time"));
		instituteInfo.setDepartmentId(institute.getDepartment().getId());

		// Update Institute
		this.getInstituteService().update(instituteInfo);
		
		// indexerMockCreate should be 0 due to the fact that institutes are initiated
		// disabled. Therefore they are activated via the updateIndex-method --> indexerMockUpdate should be 1.
		assertEquals(0, indexerMock.create);
		assertEquals(1, indexerMock.update);
		// delete institute
		// indexMockDelete should be 1 --> Deleting institute index works properly
		instituteService.removeInstitute(instituteInfo.getId());
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
//				"classpath*:applicationContext-aop.xml",
				"classpath*:applicationContext-events.xml",
				"classpath*:testContext.xml", 
				"classpath*:testSecurity.xml", 
				"classpath*:testDataSource.xml"};
	}

	public InstituteIndexingEventListener getInstituteIndexEventListener() {
		return instituteIndexEventListener;
	}

	public void setInstituteIndexAspectBean(InstituteIndexingEventListener instituteIndexEventListener) {
		this.instituteIndexEventListener = instituteIndexEventListener;
	}

	
	private static class IndexerServiceMock implements IndexerService {
		
		private int create;
		private int delete;
		private int update;
		private int recreate;
		
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

		public void recreate() throws IndexerApplicationException {
			logger.debug("method recreateIndex: Increment testRecreateIndex");
			recreate++;
		}
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}
	
	
}

