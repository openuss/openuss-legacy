// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate SeminarPriorityDao class.
 * @see org.openuss.seminarpool.SeminarPriorityDao
 */
public class SeminarPriorityDaoTest extends SeminarPriorityDaoTestBase {
	
	private TestUtility testUtility;
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
	public void testSeminarPriorityDaoCreate() {
		SeminarPriority seminarPriority = SeminarPriority.Factory.newInstance();
		seminarPriority.setPriority(3);
		seminarPriority.setSeminarUserRegistration(testUtility.createSeminarUserRegistration());
		seminarPriority.setCourseSeminarPoolAllocation(testUtility.createCourseSeminarpoolAllocation());
		
		assertNull(seminarPriority.getId());
		seminarPriorityDao.create(seminarPriority);
		assertNotNull(seminarPriority.getId());
	}
}