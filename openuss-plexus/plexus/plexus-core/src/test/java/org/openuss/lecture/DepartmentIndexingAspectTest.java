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
 * process of the department indexing.
 * 
 * @author Kai Stettner
 */
public class DepartmentIndexingAspectTest extends AbstractTransactionalDataSourceSpringContextTests {

private static final Logger logger = Logger.getLogger(DepartmentIndexingAspectTest.class);
	
	private DepartmentService departmentService;
	
	private DepartmentDao departmentDao;
	
	private DepartmentIndexingAspect departmentIndexAspectBean;

	private IndexerServiceMock indexerMock ;
	
	private TestUtility testUtility;
	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		indexerMock = new IndexerServiceMock();
		departmentIndexAspectBean.setIndexerService(indexerMock);
	}

	public void testLectureIndex() throws Exception {
	 
		// create dummy user
		User user = testUtility.createUserSecureContext();
		// create dummy department info
		Department department = testUtility.createUniqueDepartmentInDB();
		DepartmentInfo departmentInfo = departmentDao.toDepartmentInfo(department);
		// departmentId is set to NULL before creation
		departmentInfo.setId(null);
		
		Long departmentId = departmentService.create(departmentInfo, user.getId());
		
		// update departmentInfo
		// department id is set after the creation manually
		departmentInfo.setId(departmentId);
		departmentService.update(departmentInfo);
		
		assertEquals(1, indexerMock.create);
		assertEquals(1, indexerMock.update);
		// delete department
		// indexMockDelete should be 1 --> Deleting institute index works properly
		departmentService.removeDepartment(departmentId);
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

	public DepartmentIndexingAspect getDepartmentIndexAspectBean() {
		return departmentIndexAspectBean;
	}

	public void setDepartmentIndexAspectBean(DepartmentIndexingAspect departmentIndexAspectBean) {
		this.departmentIndexAspectBean = departmentIndexAspectBean;
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

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
}
