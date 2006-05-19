// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.foundation.lecture;


/**
 * JUnit Test for Spring Hibernate EnrollmentDao class.
 * @see org.openuss.foundation.lecture.EnrollmentDao
 */
public class EnrollmentDaoTest extends EnrollmentDaoTestBase {
	
	public void testEnrollmentDaoCreate() {
		Faculty faculty = new FacultyImpl();
		Enrollment enrollment = new EnrollmentImpl();
		enrollment.setFaculty(faculty);
		assertNull(enrollment.getId());
		enrollmentDao.create(enrollment);
		assertNotNull(enrollment.getId());
	}
}