package org.openuss.lecture;

import org.apache.log4j.Logger;
import org.openuss.TestUtility;
import org.openuss.aop.UniversityIndexingAspect;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopDao;
import org.openuss.desktop.DesktopImpl;
import org.openuss.foundation.DomainObject;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;
import org.openuss.security.User;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * Test case for the spring aspect initiating the create, update and delete 
 * process of the university indexing.
 * 
 * @author Kai Stettner
 */
public class UniversityIndexingAspectTest extends AbstractTransactionalDataSourceSpringContextTests {
	
private static final Logger logger = Logger.getLogger(UniversityIndexingAspectTest.class);
	
	private UniversityService universityService;
	
	private UniversityDao universityDao;
	
	private UniversityIndexingAspect universityIndexAspectBean;

	private IndexerServiceMock indexerMock ;
	
	private TestUtility testUtility;
	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		indexerMock = new IndexerServiceMock();
		universityIndexAspectBean.setIndexerService(indexerMock);
	}

	public void testLectureIndex() throws Exception {
		
		// Create new UniversityInfo object
		UniversityInfo universityInfo = new UniversityInfo();
		universityInfo.setName(testUtility.unique("testUniversity"));
		universityInfo.setShortcut(testUtility.unique("testU"));
		universityInfo.setOwnerName("Administrator");
		universityInfo.setEnabled(true);
		universityInfo.setDescription("This is a test University");
		universityInfo.setUniversityType(UniversityType.UNIVERSITY);
		
		// Create a User as Owner
		User owner = testUtility.createUniqueUserInDB();

		DesktopDao desktopDao = (DesktopDao) this.getApplicationContext().getBean("desktopDao");
		Desktop desktop = new DesktopImpl();
		desktop.setUser(owner);
		desktopDao.create(desktop);
		Desktop desktopTest = desktopDao.findByUser(owner);
		assertNotNull(desktopTest);
		assertEquals(0, desktopTest.getUniversities().size());
		
		// Create Entity
		testUtility.createAdminSecureContext();
		Long universityId = universityService.createUniversity(universityInfo, owner.getId());
		
		// update universityInfo
		// university id is set after the creation manually
		universityInfo.setId(universityId);
		universityService.update(universityInfo);
		
		assertEquals(1, indexerMock.create);
		assertEquals(1, indexerMock.update);
		// delete university
		// indexMockDelete should be 1 --> Deleting institute index works properly
		universityService.removeCompleteUniversityTree(universityId);
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
			"classpath*:applicationContext-events.xml",
			"classpath*:testContext.xml", 
			"classpath*:testSecurity.xml", 
			"classpath*:testDataSource.xml"};
	}

	public UniversityIndexingAspect getUniversityIndexAspectBean() {
		return universityIndexAspectBean;
	}

	public void setUniversityIndexAspectBean(UniversityIndexingAspect universityIndexAspectBean) {
		this.universityIndexAspectBean = universityIndexAspectBean;
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

	public UniversityService getUniversityService() {
		return universityService;
	}

	public void setUniversityService(UniversityService universityService) {
		this.universityService = universityService;
	}

	public UniversityDao getUniversityDao() {
		return universityDao;
	}

	public void setUniversityDao(UniversityDao universityDao) {
		this.universityDao = universityDao;
	}

}
