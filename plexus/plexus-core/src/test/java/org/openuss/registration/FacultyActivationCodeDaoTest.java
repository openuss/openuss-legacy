// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.registration;

import java.sql.Timestamp;

import org.apache.commons.lang.RandomStringUtils;
import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate FacultyActivationCodeDao class.
 * @see org.openuss.registration.FacultyActivationCodeDao
 */
public class FacultyActivationCodeDaoTest extends FacultyActivationCodeDaoTestBase {
	
	private TestUtility testUtility;
	
	public void testFacultyActivationCodeDaoCreate() {
		FacultyActivationCode code = FacultyActivationCode.Factory.newInstance();
		code.setCode(RandomStringUtils.random(40));
		code.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		code.setFaculty(testUtility.createPersistFacultyWithDefaultUser());
		
		assertNull(code.getId());
		facultyActivationCodeDao.create(code);
		assertNotNull(code.getId());
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
}