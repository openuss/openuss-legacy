// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.news;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


/**
 * JUnit Test for Spring Hibernate NewsPublisherDao class.
 * @see org.openuss.news.NewsPublisherDao
 */
public class NewsPublisherDaoTest extends NewsPublisherDaoTestBase {
	
	public void testCreate() {
		NewsPublisher newsPublisher = new NewsPublisherImpl();
		newsPublisher.setForeignId(0L);
		newsPublisher.setDisplayName(" ");
		newsPublisher.setForeignClass(" ");
		assertNull(newsPublisher.getId());
		newsPublisherDao.create(newsPublisher);
		assertNotNull(newsPublisher.getId());
	}
	
	public void testLoadAndRemove() {
		NewsPublisher publisher = NewsPublisher.Factory.newInstance(0L, "displayName", "org.openuss.foreignComponent.foreignClass");
		newsPublisherDao.create(publisher);
		
		assertNotNull(publisher.getId());

		Long id = publisher.getId();
		newsPublisherDao.remove(id);
		publisher = newsPublisherDao.load(id);
		assertNull(publisher);
	}
	
	public void testUpdate() {
		NewsPublisher publisher = NewsPublisher.Factory.newInstance(0L, "displayName", "org.openuss.foreignComponent.foreignClass");
		newsPublisherDao.create(publisher);
		
		assertNotNull(publisher.getId());
		
		publisher.setForeignId(12L);
		newsPublisherDao.update(publisher);
		NewsPublisher testPublisher = newsPublisherDao.load(publisher.getId());
		
		assertEquals(publisher, testPublisher);
	}
	
	public void testCreateCollectionAndLoadAll() {
		Collection<NewsPublisher> publishers = setUpPublisherCollection(10);
		newsPublisherDao.create(publishers);
		Collection testCollection = newsPublisherDao.loadAll();
		
		assertEquals(publishers, testCollection);
	}
	
	public void testUpdateCollectionLoadAllAndRemove() {
		int size = 20;
		Collection<NewsPublisher> collection = setUpPublisherCollection(size);
		newsPublisherDao.create(collection);
		collection = newsPublisherDao.loadAll();
		
		assertEquals(size, collection.size());

		String testString = null;
		long shiftDescription = 4;
		long i = 0;
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			NewsPublisher element = (NewsPublisher) iter.next();
			element.setForeignId(i+shiftDescription);
			element.setForeignClass("org.openuss.foreignComponent.foreignClass"+i+shiftDescription);
			element.setDisplayName("displayName"+i+shiftDescription);
			if (i==3) {
				testString = element.getDisplayName();
			}
			i++;
		}
		newsPublisherDao.update(collection);
		Collection<NewsPublisher> testCollection = newsPublisherDao.loadAll();
		
		assertEquals(testCollection, collection);
		
		Iterator iter = collection.iterator();
		boolean found = false;
		boolean foundTestString = false;
		while (iter.hasNext()) {
			NewsPublisher element = (NewsPublisher) iter.next();
			Iterator testIter = testCollection.iterator();
			while (testIter.hasNext()) {
				NewsPublisher testElement = (NewsPublisher) testIter.next();
				if (testElement.getId().equals(element.getId())) {
					found = true;
					break;
				}
			}
			
			if (!found) {
				fail();
			}
			
			if (element.getDisplayName()==testString) {
				foundTestString=true;
			}
		}
		
		if (!foundTestString) fail();
		
		newsPublisherDao.remove(collection);
		testCollection = newsPublisherDao.loadAll();
		
		assertNotSame(testCollection, collection);
		assertTrue(testCollection.size()==0);	
	}
	
	private Collection<NewsPublisher> setUpPublisherCollection(int collectionSize) {
		Collection<NewsPublisher> publishers = new Vector<NewsPublisher>();
		for (long i = 0; i < collectionSize; i++) {
			NewsPublisher publisher = NewsPublisher.Factory.newInstance(i+collectionSize, "displayName"+i, "org.openuss.foreignComponent.foreignClass"+i);
			publishers.add(publisher);
		}
		return publishers;
	}
}