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

	private Institute institute;

	public LectureIntegrationTest() {
		setDefaultRollback(false);
	}
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
		user = testUtility.createAdminSecureContext();
		commit();
		
		institute = LectureFactory.createInstitute();
		institute.setOwner(user);
		assertNull(institute.getId());
		lectureService.createInstitute(institute);
		commit();
	}

	/**
	 * Add courseType to an existing institute 
	 * 
	 * @throws LectureException
	 */
	public void testAddCourseTypeToInstitute() throws LectureException{
		CourseType courseType = createAndCheckCourseType();

		lectureService.removeInstitute(institute.getId());
		commit();
		
		// check if institute is removed
		institute = lectureService.getInstitute(institute.getId());
		assertNull(institute);
		
		// check if courseType is removed
		courseType = lectureService.getCourseType(courseType.getId());
		assertNull(courseType);
	}

	

	/**
	 * Add period to an existing institute 
	 * @throws LectureException
	 */
	public void testAddPeriodToInstitute() throws LectureException {
		Period period = createAndCheckPeriod();

		lectureService.removePeriod(period.getId());
		commit();
		
		lectureService.removeInstitute(institute.getId());
		commit();

		// check if institute is removed
		institute = lectureService.getInstitute(institute.getId());
		assertNull(institute);
		
		// check if period is removed
		period = lectureService.getPeriod(period.getId());
		assertNull(period);
	}
	
	/**
	 * add active period settings 
	 * @throws LectureException
	 */
	public void testAddActivePeriodToInstitute() throws LectureException {
		Period period = createAndCheckPeriod();
		commit();
		lectureService.setActivePeriod(institute.getId(), period);
		commit();
		institute = lectureService.getInstitute(institute.getId());
		
		assertNotNull(institute.getActivePeriod());
		assertEquals(institute.getActivePeriod(), period);
		assertTrue(period.isActive());
		
		// remove active period
		lectureService.removePeriod(period.getId());
		commit();
		period = lectureService.getPeriod(period.getId());
		assertNull(period);
		
		institute = lectureService.getInstitute(institute.getId());
		assertNull(institute.getActivePeriod());

		lectureService.removeInstitute(institute.getId());
		commit();
	}
	
	public void testTwoActivePeriodToInstitute() throws LectureException {
		Period activePeriod = createAndCheckPeriod();
		Period period = createAndCheckPeriod();
		
		lectureService.setActivePeriod(institute.getId(), activePeriod);
		commit();
		institute = lectureService.getInstitute(institute.getId());
		assertEquals(institute.getActivePeriod(), activePeriod);
		
		lectureService.removePeriod(period.getId());
		commit();
		institute = lectureService.getInstitute(institute.getId());
		assertEquals(institute.getActivePeriod(), activePeriod);
		
		lectureService.removeInstitute(institute.getId());
		commit();
	}
	
	public void testAddCourseToInstitute() throws LectureException {
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
		assertNotNull(course.getInstitute());
		
		assertEquals(courseType, course.getCourseType());
		assertEquals(period, course.getPeriod());
		assertEquals(institute, course.getInstitute());
		
		assertTrue(course.getPeriod().getCourses().contains(course));
		assertTrue(course.getCourseType().getCourses().contains(course));
		assertTrue(course.getInstitute().getCourses().contains(course));
		
		Course course2 = lectureService.createCourse(courseType.getId(), period.getId());
		commit();
		
		assertNotNull(course2);
		assertNotNull(course2.getId());
		
		lectureService.removeCourse(course.getId());
		commit();
		
		assertNull(lectureService.getCourse(course.getId()));
		
		institute = lectureService.getInstitute(institute.getId());
		period = lectureService.getPeriod(period.getId());
		courseType = lectureService.getCourseType(courseType.getId());
		assertEquals(1, institute.getCourses().size());
		
		assertNotNull("Couldn't find previous Period object.", period);
		assertNotNull("Couldn't find previous CourseType object.", courseType);
		
		// remove institute
		lectureService.removeInstitute(institute.getId());
		commit();
		
		assertNull(lectureService.getCourse(course.getId()));
	}

	/**
	 * Creates a period, adds it to the institute, make a commit and checks the results
	 * @return
	 * @throws LectureException
	 */
	private Period createAndCheckPeriod() throws LectureException {
		Period period = LectureFactory.createPeriod();
		institute = lectureService.add(institute.getId(), period);
		commit();
		institute = lectureService.getInstitute(institute.getId());
		assertTrue(institute.getPeriods().contains(period));
		assertNotNull(period.getId());
		return period;
	}
	
	/**
	 * Creates a period, adds it to the institute, make a commit and checks the results
	 * @return
	 * @throws LectureException
	 */
	private CourseType createAndCheckCourseType() throws LectureException {
		CourseType courseType = LectureFactory.createCourseType();
		institute = lectureService.add(institute.getId(), courseType);
		commit();
		institute = lectureService.getInstitute(institute.getId());
		assertTrue(institute.getCourseTypes().contains(courseType));
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
				"classpath*:applicationContext-commands.xml",
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