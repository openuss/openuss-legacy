// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.calendar;

import java.sql.Timestamp;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate CalendarDao class.
 * @see org.openuss.calendar.CalendarDao
 */
public class CalendarDaoTest extends CalendarDaoTestBase {
	
	private long lastUpdate = new TestUtility().unique();
	
	public void testCalendarDaoCreate() {
		Calendar calendar = Calendar.Factory.newInstance();
		calendar.setLastUpdate(new Timestamp(lastUpdate));
		calendar.setCalendarType(CalendarType.course_calendar);
		assertNull(calendar.getId());
		calendarDao.create(calendar);
		assertNotNull(calendar.getId());
	}
}
