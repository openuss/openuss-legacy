// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.openuss.TestUtility;

/**
 * JUnit Test for Spring Hibernate EnrollmentService class.
 * @see org.openuss.lecture.EnrollmentService
 */
public class EnrollmentServiceIntegrationTest extends EnrollmentServiceIntegrationTestBase {
	
	private FacultyDao facultyDao;
	
	private TestUtility testUtility;

	
	public FacultyDao getFacultyDao() {
		return facultyDao;
	}

	public void setFacultyDao(FacultyDao facultyDao) {
		this.facultyDao = facultyDao;
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
}