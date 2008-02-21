// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate SeminarConditionDao class.
 * @see org.openuss.seminarpool.SeminarConditionDao
 */
public class SeminarConditionDaoTest extends SeminarConditionDaoTestBase {
	
	private TestUtility testUtility;
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
	public void testSeminarConditionDaoCreate() {
		SeminarCondition seminarCondition = SeminarCondition.Factory.newInstance();
		seminarCondition.setConditionDescription("120 CP Condition");
		seminarCondition.setFieldDescription("Test");
		seminarCondition.setType(ConditionType.CHECKBOX);
		seminarCondition.setSeminarpool(testUtility.createUniqueSeminarpoolinDB());
		assertNull(seminarCondition.getId());
		seminarConditionDao.create(seminarCondition);
		assertNotNull(seminarCondition.getId());
	}
}
