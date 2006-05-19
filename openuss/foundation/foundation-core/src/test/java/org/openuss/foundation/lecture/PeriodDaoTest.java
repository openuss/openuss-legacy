// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.foundation.lecture;


/**
 * JUnit Test for Spring Hibernate PeriodDao class.
 * @see org.openuss.foundation.lecture.PeriodDao
 */
public class PeriodDaoTest extends PeriodDaoTestBase {
	
	public void testPeriodDaoCreate() {
		Period period = new PeriodImpl();
		period.setName(" ");
		assertNull(period.getId());
		periodDao.create(period);
		assertNotNull(period.getId());
	}
}