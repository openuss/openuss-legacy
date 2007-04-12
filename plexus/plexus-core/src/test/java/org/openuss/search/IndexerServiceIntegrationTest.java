// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.search;

import java.util.Collection;

import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;

/**
 * JUnit Test for Spring Hibernate IndexerService class.
 * @see org.openuss.search.IndexerService
 */
public class IndexerServiceIntegrationTest extends IndexerServiceIntegrationTestBase {
	
	private IndexEventDao indexEventDao;
	
	public void testCreateIndex() {
		indexEventDao.remove(indexEventDao.loadAll());
		String command = "facultyIndexer";
		DomainObject domain = new DefaultDomainObject(testUtility.unique());
		indexerService.createIndex(domain, command);
		check(command, IndexEventType.CREATE, domain);
	}

	public void testUpdateIndex() {
		indexEventDao.remove(indexEventDao.loadAll());
		String command = "facultyIndexer";
		DomainObject domain = new DefaultDomainObject(testUtility.unique());
		indexerService.updateIndex(domain, command);
		check(command, IndexEventType.UPDATE, domain);
	}

	public void testDeleteIndex() {
		indexEventDao.remove(indexEventDao.loadAll());
		String command = "facultyIndexer";
		DomainObject domain = new DefaultDomainObject(testUtility.unique());
		indexerService.deleteIndex(domain, command);
		check(command, IndexEventType.DELETE, domain);
	}

	private void check(String command, IndexEventType type, DomainObject domain) {
		Collection<IndexEvent> events = indexEventDao.loadAll();
		assertEquals(1, events.size());
		IndexEvent event = events.iterator().next();
		assertEquals(domain.getId(), event.getDomainIdentifier());
		assertEquals(command, event.getCommandName());
		assertEquals(type, event.getEventType());
	}

	public IndexEventDao getIndexEventDao() {
		return indexEventDao;
	}

	public void setIndexEventDao(IndexEventDao indexEventDao) {
		this.indexEventDao = indexEventDao;
	}
	
}