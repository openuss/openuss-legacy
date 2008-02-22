// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate CourseGroupDao class.
 * @see org.openuss.seminarpool.CourseGroupDao
 */
public class CourseGroupDaoTest extends CourseGroupDaoTestBase {
	
	private TestUtility testUtility;
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
	public void testCourseGroupDaoCreate() {
		CourseGroup courseGroup = CourseGroup.Factory.newInstance();
		courseGroup.setIsTimeSet(false);
		courseGroup.setIsDefault(true);
		courseGroup.setCapacity(30);
		courseGroup.setName("Name");
		courseGroup.setCourseSeminarpoolAllocation(testUtility.createCourseSeminarpoolAllocation());
		assertNull(courseGroup.getId());
		courseGroupDao.create(courseGroup);
		assertNotNull(courseGroup.getId());
	}
}
