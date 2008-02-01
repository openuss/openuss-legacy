package org.openuss.lecture;

import org.apache.log4j.Logger;
import org.openuss.TestUtility;
import org.openuss.aop.CourseIndexingAspect;
import org.openuss.foundation.DomainObject;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * Test case for the spring aspect initiating the create, update and delete 
 * process of the course indexing.
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
public class CourseIndexingAspectTest extends AbstractTransactionalDataSourceSpringContextTests  {

	private static final Logger logger = Logger.getLogger(CourseIndexingAspectTest.class);
	
	private CourseService courseService;
	private CourseDao courseDao;
	private CourseIndexingAspect courseIndexAspectBean;
	private IndexerServiceMock indexerMock ;

	private TestUtility testUtility;
	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		indexerMock = new IndexerServiceMock();
		courseIndexAspectBean.setIndexerService(indexerMock);
	}

	public void testLectureIndex() throws Exception {
	 
		//Create CourseType
		CourseType courseType = testUtility.createUniqueCourseTypeInDB();
		
		//Create Period
		Period period = testUtility.createUniquePeriodInDB();
			
		//Create CourseInfo
		CourseInfo courseInfo = new CourseInfo();
		courseInfo.setName(testUtility.unique("name"));
		courseInfo.setShortcut(testUtility.unique("course"));
		courseInfo.setDescription(testUtility.unique("description"));
		courseInfo.setPassword(testUtility.unique("password"));
		courseInfo.setCourseTypeId(courseType.getId());
		courseInfo.setCourseTypeDescription(courseType.getDescription());
		courseInfo.setPeriodId(period.getId());
		courseInfo.setPeriodName(period.getName());
		courseInfo.setAccessType(AccessType.OPEN);
		courseInfo.setBraincontest(true);
		courseInfo.setChat(true);
		courseInfo.setDiscussion(true);
		courseInfo.setDocuments(true);
		courseInfo.setFreestylelearning(true);
		courseInfo.setNewsletter(true);
		courseInfo.setWiki(true);
		courseInfo.setCollaboration(true);
		courseInfo.setPapersubmission(true);
		
		Long courseId = courseService.create(courseInfo);
		
		// update courseInfo
		courseService.updateCourse(courseInfo);
		
		assertEquals(1, indexerMock.create);
		assertEquals(1, indexerMock.update);
		// delete course
		// indexMockDelete should be 1 --> Deleting institute index works properly
		courseService.removeCourse(courseId);
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

	public CourseIndexingAspect getCourseIndexAspectBean() {
		return courseIndexAspectBean;
	}

	public void setCourseIndexAspectBean(CourseIndexingAspect courseIndexAspectBean) {
		this.courseIndexAspectBean = courseIndexAspectBean;
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

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}
}
