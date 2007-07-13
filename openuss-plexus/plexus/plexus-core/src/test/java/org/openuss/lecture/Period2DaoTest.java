// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.Date;


/**
 * JUnit Test for Spring Hibernate Period2Dao class.
 * @see org.openuss.lecture.Period2Dao
 */
public class Period2DaoTest extends Period2DaoTestBase {
	
	public void testPeriod2DaoCreate() {
		Period2 period2 = new Period2Impl();
		period2.setName(" ");
		period2.setDescription(" ");
		period2.setStartDate(new Date());
		period2.setEndDate(new Date());
		assertNull(period2.getId());
		period2Dao.create(period2);
		assertNotNull(period2.getId());
	}
}