// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import java.sql.Timestamp;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate CourseScheduleDao class.
 * @see org.openuss.seminarpool.CourseScheduleDao
 */
public class CourseScheduleDaoTest extends CourseScheduleDaoTestBase {
	
	private TestUtility testUtility;
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
	public void testCourseScheduleDaoCreate() {
		CourseSchedule courseSchedule = CourseSchedule.Factory.newInstance();
		courseSchedule.setStartTime(new Timestamp(3223423423L));
		courseSchedule.setEndTime(new Timestamp(3223423423L));
		courseSchedule.setDayOfWeek(DayOfWeek.MONDAY);
		courseSchedule.setCourseGroup(testUtility.createCourseGroup());
		assertNull(courseSchedule.getId());
		courseScheduleDao.create(courseSchedule);
		assertNotNull(courseSchedule.getId());
	}
}
