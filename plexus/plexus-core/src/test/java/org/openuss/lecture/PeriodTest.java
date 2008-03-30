package org.openuss.lecture;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import junit.framework.TestCase;

public class PeriodTest extends TestCase {

	public void testIsActive() {
		
		Date startdate = new Date();
		Date olddate = DateUtils.addDays(startdate, -16); 
		Date nextdate = DateUtils.addDays(startdate, 8);
		Date enddate = DateUtils.addDays(startdate, 16); 
		
		Period periodNow = new PeriodImpl();
		periodNow.setStartdate(startdate);
		periodNow.setEnddate(nextdate);

		assertTrue(periodNow.isActive());
		
		Period periodOld = new PeriodImpl();
		periodOld.setStartdate(olddate);
		periodOld.setEnddate(startdate);
		assertFalse(periodOld.isActive());
		
		Period periodFuture = new PeriodImpl();
		periodFuture.setStartdate(nextdate);
		periodFuture.setEnddate(enddate);
		assertFalse(periodFuture.isActive());
	}
	
	public void testAddCourse() {
		Course course = Course.Factory.newInstance(120L);
		Period period = Period.Factory.newInstance();
		
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
