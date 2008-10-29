package org.openuss.lecture;

import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.lang.time.DateUtils;

public class PeriodTest extends TestCase {

	public void testIsActive() {
		Date today = new Date();
		Date beforeYesterday = DateUtils.addDays(today, -2); 
		Date yesterday = DateUtils.addDays(today, -1); 
		Date tomorrow = DateUtils.addDays(today, 1);
		Date afterTomorrow = DateUtils.addDays(today, 2); 
		
		Period period1 = new PeriodImpl();
		period1.setStartdate(today);
		period1.setEnddate(tomorrow);

		assertTrue(period1.isActive());
		
		Period period2 = new PeriodImpl();
		period2.setStartdate(beforeYesterday);
		period2.setEnddate(yesterday);
		assertFalse(period2.isActive());
		
		Period period3 = new PeriodImpl();
		period3.setStartdate(tomorrow);
		period3.setEnddate(afterTomorrow);
		assertFalse(period3.isActive());

		Period period4 = new PeriodImpl();
		period4.setStartdate(yesterday);
		period4.setEnddate(today);
		assertTrue(period4.isActive());
	}
	
	public void testAddCourse() {
		Course course = new CourseImpl();
		course.setId(120L);
		Period period = new PeriodImpl();
		
		period.add(course);
		assertTrue(period.getCourses().contains(course));
		try {
			period.add(course);
			fail("IllegalStateException expected");
		} catch (IllegalStateException ise) {
			assertNotNull(ise);
		}
		
		try {
			period.add(null);
			fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException iae) {
			assertNotNull(iae);
		}
	}
	

}
