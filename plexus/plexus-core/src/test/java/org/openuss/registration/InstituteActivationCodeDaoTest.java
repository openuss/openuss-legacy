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
 * JUnit Test for Spring Hibernate InstituteActivationCodeDao class.
 * @see org.openuss.registration.InstituteActivationCodeDao
 */
public class InstituteActivationCodeDaoTest extends InstituteActivationCodeDaoTestBase {
	
	private TestUtility testUtility;
	
	public void testInstituteActivationCodeDaoCreate() {
		InstituteActivationCode code = InstituteActivationCode.Factory.newInstance();
		code.setCode(RandomStringUtils.random(40));
		code.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		code.setInstitute(testUtility.createUniqueInstituteInDB());//createPersistInstituteWithDefaultUser());
		
		assertNull(code.getId());
		instituteActivationCodeDao.create(code);
		assertNotNull(code.getId());
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
}