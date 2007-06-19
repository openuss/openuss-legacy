package org.openuss.lecture;

import org.apache.commons.lang.StringUtils;
import org.openuss.TestUtility;
import org.openuss.security.User;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * JUnit Test for Spring Hibernate LectureService class.
 * 
 * @see org.openuss.lecture.LectureService
 * 
 * @author Ingo Dueppe
 */
public class LectureIntegrationTest extends AbstractTransactionalDataSourceSpringContextTests {

	private TestUtility testUtility;

	private LectureService lectureService;
	
	private User user;

	private Faculty faculty;

	public LectureIntegrationTest() {
		setDefaultRollback(false);
	}
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
		user = testUtility.createAdminSecureContext();
		commit();
		
		faculty = LectureFactory.createFaculty();
		faculty.setOwner(user);
		assertNull(faculty.getId());
		lectureService.createFaculty(faculty);
		commit();
	}

	/**
	 * Add courseType to an existing faculty 
	 * 
	 * @throws LectureException
	 */
	public void testAddCourseTypeToFaculty() throws LectureException{
		CourseType courseType = createAndCheckCourseType();

		lectureService.removeFaculty(faculty.getId());
		commit();
		
		// check if faculty is removed
		faculty = lectureService.getFaculty(faculty.getId());
		assertNull(faculty);
		
		// check if courseType is removed
		courseType = lectureService.getCourseType(courseType.getId());
		assertNull(courseType);
	}

	

	/**
	 * Add period to an existing faculty 
	 * @throws LectureException
	 */
	public void testAddPeriodToFaculty() throws LectureException {
		Period period = createAndCheckPeriod();

		lectureService.removePeriod(period.getId());
		commit();
		
		lectureService.removeFaculty(faculty.getId());
		commit();

		// check if faculty is removed
		faculty = lectureService.getFaculty(faculty.getId());
		assertNull(faculty);
		
		// check if period is removed
		period = lectureService.getPeriod(period.getId());
		assertNull(period);
	}
	
	/**
	 * add active period settings 
	 * @throws LectureException
	 */
	public void testAddActivePeriodToFaculty() throws LectureException {
		Period period = createAndCheckPeriod();
		commit();
		lectureService.setActivePeriod(faculty.getId(), period);
		commit();
		faculty = lectureService.getFaculty(faculty.getId());
		
		assertNotNull(faculty.getActivePeriod());
		assertEquals(faculty.getActivePeriod(), period);
		assertTrue(period.isActive());
		
		// remove active period
		lectureService.removePeriod(period.getId());
		commit();
		period = lectureService.getPeriod(period.getId());
		assertNull(period);
		
		faculty = lectureService.getFaculty(faculty.getId());
		assertNull(faculty.getActivePeriod());

		lectureService.removeFaculty(faculty.getId());
		commit();
	}
	
	public void testTwoActivePeriodToFaculty() throws LectureException {
		Period activePeriod = createAndCheckPeriod();
		Period period = createAndCheckPeriod();
		
		lectureService.setActivePeriod(faculty.getId(), activePeriod);
		commit();
		faculty = lectureService.getFaculty(faculty.getId());
		assertEquals(faculty.getActivePeriod(), activePeriod);
		
		lectureService.removePeriod(period.getId());
		commit();
		faculty = lectureService.getFaculty(faculty.getId());
		assertEquals(faculty.getActivePeriod(), activePeriod);
		
		lectureService.removeFaculty(faculty.getId());
		commit();
	}
	
	public void testAddCourseToFaculty() throws LectureException {
		Period period = createAndCheckPeriod();
		CourseType courseType = createAndCheckCourseType();
		
		Course course = lectureService.createCourse(courseType.getId(), period.getId());
		commit();
		// re-attach
		course = lectureService.getCourse(course.getId());
		
		// check constraints
		assertNotNull(course);
		assertNotNull(course.getId());
		assertTrue(StringUtils.isNotBlank(course.getShortcut()));
		
		assertNotNull(course.getCourseType());
		assertNotNull(course.getPeriod());
		assertNotNull(course.getFaculty());
		
		assertEquals(courseType, course.getCourseType());
		assertEquals(period, course.getPeriod());
		assertEquals(faculty, course.getFaculty());
		
		assertTrue(course.getPeriod().getCourses().contains(course));
		assertTrue(course.getCourseType().getCourses().contains(course));
		assertTrue(course.getFaculty().getCourses().contains(course));
		
		Course course2 = lectureService.createCourse(courseType.getId(), period.getId());
		commit();
		
		assertNotNull(course2);
		assertNotNull(course2.getId());
		
		lectureService.removeCourse(course.getId());
		commit();
		
		assertNull(lectureService.getCourse(course.getId()));
		
		faculty = lectureService.getFaculty(faculty.getId());
		period = lectureService.getPeriod(period.getId());
		courseType = lectureService.getCourseType(courseType.getId());
		assertEquals(1, faculty.getCourses().size());
		
		assertNotNull("Couldn't find previous Period object.", period);
		assertNotNull("Couldn't find previous CourseType object.", courseType);
		
		// remove faculty
		lectureService.removeFaculty(faculty.getId());
		commit();
		
		assertNull(lectureService.getCourse(course.getId()));
	}

	/**
	 * Creates a period, adds it to the faculty, make a commit and checks the results
	 * @return
	 * @throws LectureException
	 */
	private Period createAndCheckPeriod() throws LectureException {
		Period period = LectureFactory.createPeriod();
		faculty = lectureService.add(faculty.getId(), period);
		commit();
		faculty = lectureService.getFaculty(faculty.getId());
		assertTrue(faculty.getPeriods().contains(period));
		assertNotNull(period.getId());
		return period;
	}
	
	/**
	 * Creates a period, adds it to the faculty, make a commit and checks the results
	 * @return
	 * @throws LectureException
	 */
	private CourseType createAndCheckCourseType() throws LectureException {
		CourseType courseType = LectureFactory.createCourseType();
		faculty = lectureService.add(faculty.getId(), courseType);
		commit();
		faculty = lectureService.getFaculty(faculty.getId());
		assertTrue(faculty.getCourseTypes().contains(courseType));
		assertNotNull(courseType.getId());
		return courseType;
	}
	
	private void commit() {
		setComplete();
		endTransaction();
		startNewTransaction();
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

	// --------- Properties --------------------
	
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