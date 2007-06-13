// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.registration;


/**
 * JUnit Test for Spring Hibernate FacultyActivationCodeDao class.
 * @see org.openuss.registration.FacultyActivationCodeDao
 */
public class FacultyActivationCodeDaoTest extends FacultyActivationCodeDaoTestBase {
	
	public void testFacultyActivationCodeDaoCreate() {
		FacultyActivationCode facultyActivationCode = new FacultyActivationCodeImpl();
		assertNull(facultyActivationCode.getId());
		facultyActivationCodeDao.create(facultyActivationCode);
		assertNotNull(facultyActivationCode.getId());
	}
}