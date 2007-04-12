// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.search;

import java.util.Date;


/**
 * JUnit Test for Spring Hibernate LastEventProcessedDao class.
 * @see org.openuss.search.LastEventProcessedDao
 */
public class LastEventProcessedDaoTest extends LastEventProcessedDaoTestBase {
	
	public void testLastEventProcessedDaoCreate() {
		
		IndexEvent indexEvent = IndexEvent.Factory.newInstance();
		indexEvent.setEventType(IndexEventType.CREATE);
		indexEvent.setEventTime(new Date());
		indexEvent.setDomainIdentifier(1234L);
		indexEvent.setCommandName("commandName");
		
		LastEventProcessed lastEventProcessed = LastEventProcessed.Factory.newInstance();
		lastEventProcessed.setId(120L);
		lastEventProcessed.setLast(indexEvent);
		
		assertNull(lastEventProcessed.getLast().getId());
		lastEventProcessedDao.create(lastEventProcessed);
		assertNotNull(lastEventProcessed.getLast().getId());
	}
}