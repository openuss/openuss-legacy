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
	 * Add subject to an existing faculty 
	 * 
	 * @throws LectureException
	 */
	public void testAddSubjectToFaculty() throws LectureException{
		Subject subject = createAndCheckSubject();

		lectureService.removeFaculty(faculty.getId());
		commit();
		
		// check if faculty is removed
		faculty = lectureService.getFaculty(faculty.getId());
		assertNull(faculty);
		
		// check if subject is removed
		subject = lectureService.getSubject(subject.getId());
		assertNull(subject);
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
	
	public void testAddEnrollmentToFaculty() throws LectureException {
		Period period = createAndCheckPeriod();
		Subject subject = createAndCheckSubject();
		
		Enrollment enrollment = lectureService.createEnrollment(subject.getId(), period.getId());
		commit();
		// re-attach
		enrollment = lectureService.getEnrollment(enrollment.getId());
		
		// check constraints
		assertNotNull(enrollment);
		assertNotNull(enrollment.getId());
		assertTrue(StringUtils.isNotBlank(enrollment.getShortcut()));
		
		assertNotNull(enrollment.getSubject());
		assertNotNull(enrollment.getPeriod());
		assertNotNull(enrollment.getFaculty());
		
		assertEquals(subject, enrollment.getSubject());
		assertEquals(period, enrollment.getPeriod());
		assertEquals(faculty, enrollment.getFaculty());
		
		assertTrue(enrollment.getPeriod().getEnrollments().contains(enrollment));
		assertTrue(enrollment.getSubject().getEnrollments().contains(enrollment));
		assertTrue(enrollment.getFaculty().getEnrollments().contains(enrollment));
		
		Enrollment enrollment2 = lectureService.createEnrollment(subject.getId(), period.getId());
		commit();
		
		assertNotNull(enrollment2);
		assertNotNull(enrollment2.getId());
		
		lectureService.removeEnrollment(enrollment.getId());
		commit();
		
		assertNull(lectureService.getEnrollment(enrollment.getId()));
		
		faculty = lectureService.getFaculty(faculty.getId());
		period = lectureService.getPeriod(period.getId());
		subject = lectureService.getSubject(subject.getId());
		assertEquals(1, faculty.getEnrollments().size());
		
		assertNotNull("Couldn't find previous Period object.", period);
		assertNotNull("Couldn't find previous Subject object.", subject);
		
		// remove faculty
		lectureService.removeFaculty(faculty.getId());
		commit();
		
		assertNull(lectureService.getEnrollment(enrollment.getId()));
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
	private Subject createAndCheckSubject() throws LectureException {
		Subject subject = LectureFactory.createSubject();
		faculty = lectureService.add(faculty.getId(), subject);
		commit();
		faculty = lectureService.getFaculty(faculty.getId());
		assertTrue(faculty.getSubjects().contains(subject));
		assertNotNull(subject.getId());
		return subject;
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