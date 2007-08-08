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
 * process of the course type indexing.
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
public class CourseTypeIndexingAspectTest extends AbstractTransactionalDataSourceSpringContextTests {
	
	private static final Logger logger = Logger.getLogger(CourseTypeIndexingAspectTest.class);
	
	private CourseTypeService courseTypeService;
	
	private CourseTypeDao courseTypeDao;
	
	private InstituteService instituteService;
	
	private CourseTypeIndexingAspect courseTypeIndexAspectBean;

	private IndexerServiceMock indexerMock ;
	
	private TestUtility testUtility;
	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		indexerMock = new IndexerServiceMock();
		courseTypeIndexAspectBean.setIndexerService(indexerMock);
	}

	public void testLectureIndex() throws Exception {
	 
		// create dummy user
		User user = testUtility.createSecureContext();
		// create dummy institute info
		InstituteInfo instituteInfo = new InstituteInfo(null,"The Superb Institute","TSI","Institute Owner xyz","Germany",false);
		Long instituteId = instituteService.create(instituteInfo, user.getId());
		// create dummy course type info
		CourseTypeInfo courseTypeInfo = new CourseTypeInfo(null,"A superb CourseType","ASCT",instituteId);
		
		Long courseTypeId = courseTypeService.create(courseTypeInfo);
		
		// update course type
		courseTypeService.update(courseTypeInfo);
		
		assertEquals(1, indexerMock.create);
		assertEquals(1, indexerMock.update);
		// delete course type
		// indexMockDelete should be 1 --> Deleting course type index works properly
		courseTypeService.removeCourseType(courseTypeInfo.getId());
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

	public CourseTypeIndexingAspect getCourseTypeIndexAspectBean() {
		return courseTypeIndexAspectBean;
	}

	public void setCourseTypeIndexAspectBean(CourseTypeIndexingAspect courseTypeIndexAspectBean) {
		this.courseTypeIndexAspectBean = courseTypeIndexAspectBean;
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

	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public CourseTypeDao getCourseTypeDao() {
		return courseTypeDao;
	}

	public void setCourseTypeDao(CourseTypeDao courseTypeDao) {
		this.courseTypeDao = courseTypeDao;
	}

	public CourseTypeService getCourseTypeService() {
		return courseTypeService;
	}

	public void setCourseTypeService(CourseTypeService courseTypeService) {
		this.courseTypeService = courseTypeService;
	}	
}
