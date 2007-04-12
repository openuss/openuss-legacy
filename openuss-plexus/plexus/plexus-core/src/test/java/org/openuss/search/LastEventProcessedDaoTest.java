// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.search;


/**
 * JUnit Test for Spring Hibernate LastEventProcessedDao class.
 * @see org.openuss.search.LastEventProcessedDao
 */
public class LastEventProcessedDaoTest extends LastEventProcessedDaoTestBase {
	
	public void testLastEventProcessedDaoCreate() {
		LastEventProcessed lastEventProcessed = new LastEventProcessedImpl();
		assertNull(lastEventProcessed.getId());
		lastEventProcessedDao.create(lastEventProcessed);
		assertNotNull(lastEventProcessed.getId());
	}
}