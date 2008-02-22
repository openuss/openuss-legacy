// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate CourseSeminarpoolAllocationDao class.
 * @see org.openuss.seminarpool.CourseSeminarpoolAllocationDao
 */
public class CourseSeminarpoolAllocationDaoTest extends CourseSeminarpoolAllocationDaoTestBase {
	
	private TestUtility testUtility;
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
	
	public void testCourseSeminarpoolAllocationDaoCreate() {
		CourseSeminarpoolAllocation courseSeminarpoolAllocation = CourseSeminarpoolAllocation.Factory.newInstance();
		courseSeminarpoolAllocation.setSeminarpool(testUtility.createUniqueSeminarpoolinDB());
		courseSeminarpoolAllocation.setCourse(testUtility.createUniqueCourseInDB());
		assertNull(courseSeminarpoolAllocation.getId());
		courseSeminarpoolAllocationDao.create(courseSeminarpoolAllocation);
		assertNotNull(courseSeminarpoolAllocation.getId());
	}
}
