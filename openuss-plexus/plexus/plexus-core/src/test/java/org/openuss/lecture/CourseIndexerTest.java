package org.openuss.lecture;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.openuss.search.DomainResult;
import org.openuss.security.User;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * Test case for the spring indexing and searching of institutes.
 * Tests:
 * 	-	Normal Indexing and Searching
 * 	- 	Updating of the index and new search
 * Creating and Deleting of the index is testes via the Callback methods.
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
public class CourseIndexerTest extends AbstractDependencyInjectionSpringContextTests {
	
private static final Logger logger = Logger.getLogger(CourseIndexerTest.class);
	
	private CourseIndexer courseIndexer;
	
	private CourseDao courseDao = new CourseDaoMock();
	
	private LectureSearcher lectureSearcher;
	
	private Course course;
	private Institute institute;
	private CourseType courseType;
	private Period period;
	private LectureBuilder lectureBuilderInstance;
						   	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		logger.debug("Method onSetUp: Started");
		
		lectureBuilderInstance = new LectureBuilder();
		
		courseIndexer.setCourseDao(courseDao);
	
		courseType = lectureBuilderInstance.createCourseType(institute).getCourseType();

		//period = lectureBuilderInstance.addPeriod(institute);
		
		course = lectureBuilderInstance.createCourse(institute,courseType).getCourse();
		
		// set description of course
		course.setDescription("A pretty good course");
		courseDao.create(course);
		
		courseIndexer.setDomainObject(course);
		
		courseIndexer.create();
	}
	
	@Override
	protected void onTearDown() throws Exception {
		super.onTearDown();
		logger.debug("Method onTearDown: Started");
		courseIndexer.delete();
	}

	
	
	public void testIndexingAndSearching() {
		logger.debug("Method testIndexingAndSearching: Started");
		
		List<DomainResult> results = lectureSearcher.search("pretty");
		DomainResult[] resultObjs = (DomainResult[]) results.toArray(new DomainResult[results.size()]);
		logger.debug("--- RESULTS ---> "+ArrayUtils.toString(resultObjs));
		logger.debug(" Result size is: "+results.size());
		assertNotNull(results);
		assertTrue(results.size() == 1);
		
	}
	
	public void testIndexerUpdatingAndSearching() {
		logger.debug("Method testIndexerUpdatingAndSearching: Started");
		// updated description of course
		course.setDescription("A pathetic course!");
		
		courseIndexer.update();
		
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
	public CourseIndexer getCourseIndexer() {
		return courseIndexer;
	}

	public void setCourseIndexer(CourseIndexer courseIndexer) {
		this.courseIndexer = courseIndexer;
	}

	public LectureSearcher getLectureSearcher() {
		return lectureSearcher;
	}

	public void setLectureSearcher(LectureSearcher lectureSearcher) {
		this.lectureSearcher = lectureSearcher;
	}

}
