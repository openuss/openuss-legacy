// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.search;

import java.util.Date;


/**
 * JUnit Test for Spring Hibernate IndexEventDao class.
 * @see org.openuss.search.IndexEventDao
 */
public class IndexEventDaoTest extends IndexEventDaoTestBase {
	
	public void testIndexEventDaoCreate() {
		IndexEvent indexEvent = new IndexEventImpl();
		indexEvent.setEventType(IndexEventType.CREATE);
		indexEvent.setEventTime(new Date());
		indexEvent.setDomainIdentifier(123L);
		indexEvent.setCommandClass(" ");
		assertNull(indexEvent.getId());
		indexEventDao.create(indexEvent);
		assertNotNull(indexEvent.getId());
	}
}