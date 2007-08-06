package org.openuss.lecture;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.openuss.TestUtility;
import org.openuss.search.DomainResult;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class CourseTypeIndexerTest extends AbstractDependencyInjectionSpringContextTests {
	
private static final Logger logger = Logger.getLogger(CourseTypeIndexerTest.class);
	
	private CourseTypeIndexer courseTypeIndexer;
	
	private CourseTypeDao courseTypeDao = new CourseTypeDaoMock();
	
	private LectureSearcher lectureSearcher;
	
	private CourseType courseType;
	
	private TestUtility testUtility;
						   	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		logger.debug("Method onSetUp: Started");
		
		courseTypeIndexer.setCourseTypeDao(courseTypeDao);
		
		courseType = testUtility.createUniqueCourseTypeInDB();
	
		courseTypeDao.create(courseType);
		
		courseTypeIndexer.setDomainObject(courseType);
		
		courseTypeIndexer.create();
	}
	
	@Override
	protected void onTearDown() throws Exception {
		super.onTearDown();
		logger.debug("Method onTearDown: Started");
		courseTypeIndexer.delete();
	}

	
	
	public void testIndexingAndSearching() {
		logger.debug("Method testIndexingAndSearching: Started");
		
		List<DomainResult> results = lectureSearcher.search("A unique CourseType");
		DomainResult[] resultObjs = (DomainResult[]) results.toArray(new DomainResult[results.size()]);
		logger.debug("--- RESULTS ---> "+ArrayUtils.toString(resultObjs));
		logger.debug(" Result size is: "+results.size());
		assertNotNull(results);
		assertTrue(results.size() == 1);
		
	}
	
	public void testIndexerUpdatingAndSearching() {
		logger.debug("Method testIndexerUpdatingAndSearching: Started");
		// set description to new description "A pathetic CourseType"
		courseType.setDescription("A pathetic CourseType");
		
		courseTypeIndexer.update();
		
		List<DomainResult> results = lectureSearcher.search("pathetic");
		DomainResult[] resultObjs = (DomainResult[]) results.toArray(new DomainResult[results.size()]);
		logger.debug("--- RESULTS ---> "+ArrayUtils.toString(resultObjs));
		logger.debug(" Result size is: "+results.size());
		assertNotNull(results);
		assertTrue(results.size() == 1);
		
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
	public CourseTypeIndexer getDepartmentIndexer() {
		return courseTypeIndexer;
	}

	public void setCourseTypeIndexer(CourseTypeIndexer courseTypeIndexer) {
		this.courseTypeIndexer = courseTypeIndexer;
	}

	public LectureSearcher getLectureSearcher() {
		return lectureSearcher;
	}

	public void setLectureSearcher(LectureSearcher lectureSearcher) {
		this.lectureSearcher = lectureSearcher;
	}
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

}
