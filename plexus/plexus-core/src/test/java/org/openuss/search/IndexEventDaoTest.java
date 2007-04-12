// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.search;

import java.util.Date;
import java.util.List;


/**
 * JUnit Test for Spring Hibernate IndexEventDao class.
 * @see org.openuss.search.IndexEventDao
 */
public class IndexEventDaoTest extends IndexEventDaoTestBase {
	
	public void testIndexEventDaoCreate() {
		IndexEvent indexEvent = createIndexEvent(123L, "facultyIndexer", IndexEventType.CREATE);
		assertNull(indexEvent.getId());
		indexEventDao.create(indexEvent);
		assertNotNull(indexEvent.getId());
	}

	public void testFindAllEventsAfter() {
		IndexEvent event1 = createIndexEventDB(12345L,"facultyIndexer", IndexEventType.CREATE); 
		IndexEvent event2 = createIndexEventDB(1234L,"facultyIndexer", IndexEventType.CREATE); 
		IndexEvent event3 = createIndexEventDB(123L,"facultyIndexer", IndexEventType.DELETE); 
		IndexEvent event4 = createIndexEventDB(12L,"facultyIndexer", IndexEventType.UPDATE); 
		IndexEvent event5 = createIndexEventDB(1L,"facultyIndexer", IndexEventType.DELETE);
		commit();
		List<IndexEvent> events = indexEventDao.findAllEventsAfter(event2.getId());
		
		assertEquals(3, events.size());
		assertFalse(events.contains(event1));
		assertFalse(events.contains(event2));
		assertTrue(events.contains(event3));
		assertTrue(events.contains(event4));
		assertTrue(events.contains(event5));

		event1 = createIndexEventDB(12345L,"facultyIndexer", IndexEventType.CREATE); 
		event2 = createIndexEventDB(1234L,"facultyIndexer", IndexEventType.CREATE); 
		event3 = createIndexEventDB(123L,"facultyIndexer", IndexEventType.DELETE); 
		event4 = createIndexEventDB(12L,"facultyIndexer", IndexEventType.UPDATE); 
		event5 = createIndexEventDB(1L,"facultyIndexer", IndexEventType.DELETE);
		commit();
		events = indexEventDao.findAllEventsAfter(event2.getId());
		
		assertEquals(3, events.size());
		assertFalse(events.contains(event1));
		assertFalse(events.contains(event2));
		assertTrue(events.contains(event3));
		assertTrue(events.contains(event4));
		assertTrue(events.contains(event5));
	}
	
	private void commit() {
		setComplete();
		endTransaction();
		startNewTransaction();
	}

	private IndexEvent createIndexEventDB(Long identifier, String commandName, IndexEventType type) {
		IndexEvent event = createIndexEvent(identifier, commandName, type);
		indexEventDao.create(event);
		assertNotNull(event.getId());
		return event;
	}

	private IndexEvent createIndexEvent(Long identifier, String commandName, IndexEventType type) {
		IndexEvent indexEvent = IndexEvent.Factory.newInstance();
		indexEvent.setEventType(type);
		indexEvent.setEventTime(new Date());
		indexEvent.setDomainIdentifier(identifier);
		indexEvent.setCommandName(commandName);
		return indexEvent;
	}
	
}