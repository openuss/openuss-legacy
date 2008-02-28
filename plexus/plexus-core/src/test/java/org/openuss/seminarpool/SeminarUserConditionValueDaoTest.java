// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate SeminarUserConditionValueDao class.
 * @see org.openuss.seminarpool.SeminarUserConditionValueDao
 */
public class SeminarUserConditionValueDaoTest extends SeminarUserConditionValueDaoTestBase {

	private TestUtility testUtility;
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
	public void testSeminarUserConditionValueDaoCreate() {
		SeminarUserConditionValue seminarUserConditionValue = SeminarUserConditionValue.Factory.newInstance();
		seminarUserConditionValue.setConditionValue("1");
		seminarUserConditionValue.setSeminarCondition(testUtility.createSeminarCondition());
		seminarUserConditionValue.setSeminarUserRegistration(testUtility.createSeminarUserRegistration());
		assertNull(seminarUserConditionValue.getId());
		seminarUserConditionValueDao.create(seminarUserConditionValue);
		assertNotNull(seminarUserConditionValue.getId());
	}
}
