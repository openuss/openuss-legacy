// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.news;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate NewsItemDao class.
 * @see org.openuss.news.NewsItemDao
 */
public class NewsItemDaoTest extends NewsItemDaoTestBase {
	
	private TestUtility testUtility;
	
	private Date past = new Date(System.currentTimeMillis()-1000000);
	private Date now = new Date(System.currentTimeMillis());
	private Date future = new Date(System.currentTimeMillis()+1000000);
	
	public void testNewsItemDaoCreate() {
		NewsItem newsItem = createNewsItem();
		assertNull(newsItem.getId());
		newsItemDao.create(newsItem);
		assertNotNull(newsItem.getId());
		flush();
		
		NewsItem loaded = newsItemDao.load(newsItem.getId());
		assertNotNull(loaded);
		assertNotNull(loaded.getId());
		
		validateNewsItem(newsItem, loaded);
	}
	
	public void testToNewsItemInfo() {
		NewsItem newsItem = createNewsItem();
		NewsItemInfo info = newsItemDao.toNewsItemInfo(newsItem);
		validateNewsItemInfo(newsItem, info);
		assertTrue(info.isReleased());
		assertFalse(info.isExpired());

		newsItem.setPublishDate(future);
		info = newsItemDao.toNewsItemInfo(newsItem);
		assertFalse(info.isReleased());
		
		newsItem.setExpireDate(past);
		info = newsItemDao.toNewsItemInfo(newsItem);
		assertTrue(info.isExpired());
	}
	
	public void testFindByCriteria() {
		removeAllNewsItem();

		NewsItem item1 = createNewsItem();
		item1.setPublishDate(past);
		item1.setExpireDate(past);
		newsItemDao.create(item1);

		NewsItem item2 = createNewsItem();
		item2.setPublishDate(past);
		item2.setExpireDate(future);
		newsItemDao.create(item2);

		NewsItem item3 = createNewsItem();
		item3.setPublishDate(future);
		item3.setExpireDate(future);
		newsItemDao.create(item3);
		
		NewsItem item4 = createNewsItem();
		item4.setPublishDate(past);
		item4.setExpireDate(null);
		newsItemDao.create(item4);

		flush();
		
		NewsCriteria criteria = new NewsCriteria();
		criteria.setCategory(NewsCategory.GLOBAL);
		criteria.setPublishDate(now);
		criteria.setExpireDate(now);
		
		List<NewsItem> items = newsItemDao.findByCriteria(criteria);
		assertNotNull(items);
		assertEquals(2, items.size());
		assertFalse(items.contains(item1));
		assertTrue(items.contains(item2));
		assertFalse(items.contains(item3));
		assertTrue(items.contains(item4));
	}
	
	public void testFindAndCountByPublisher() {
		removeAllNewsItem();
		
		long publisherId = TestUtility.unique();
		
		NewsItem itemOne = createNewsItem();
		itemOne.setPublisherIdentifier(publisherId);
		newsItemDao.create(itemOne);

		NewsItem itemTwo = createNewsItem();
		itemTwo.setPublisherIdentifier(publisherId);
		newsItemDao.create(itemTwo);

		NewsItem itemThree = createNewsItem();
		newsItemDao.create(itemThree);
		
		flush();
		
		List<NewsItem> items = newsItemDao.findByPublisher(publisherId);
		assertNotNull(items);
		assertEquals(2, items.size());
		assertTrue(items.contains(itemOne));
		assertTrue(items.contains(itemTwo));
		assertFalse(items.contains(itemThree));

		assertEquals(2, newsItemDao.countByPublisher(publisherId));
	}

	public void testFindAndCountByCategory() {
		removeAllNewsItem();
		
		NewsItem itemOne = createNewsItem();
		itemOne.setCategory(NewsCategory.GLOBAL);
		newsItemDao.create(itemOne);
		
		NewsItem itemTwo = createNewsItem();
		itemTwo.setCategory(NewsCategory.DESKTOP);
		newsItemDao.create(itemTwo);
		
		NewsItem itemThree = createNewsItem();
		itemThree.setCategory(NewsCategory.COURSE);
		newsItemDao.create(itemThree);

		NewsItem itemFour = createNewsItem();
		itemFour.setCategory(NewsCategory.INSTITUTE);
		newsItemDao.create(itemFour);

		NewsItem itemFive = createNewsItem();
		itemFive.setCategory(NewsCategory.DESKTOP);
		newsItemDao.create(itemFive);
		
		flush();
		
		List<NewsItem> items = newsItemDao.findByCategory(NewsCategory.DESKTOP);
		assertNotNull(items);
		assertEquals(2, items.size());
		assertFalse(items.contains(itemOne));
		assertTrue(items.contains(itemTwo));
		assertFalse(items.contains(itemThree));
		assertFalse(items.contains(itemFour));
		assertTrue(items.contains(itemFive));
		
		assertEquals(1, newsItemDao.countByCategory(NewsCategory.GLOBAL));
		assertEquals(2, newsItemDao.countByCategory(NewsCategory.DESKTOP));
		assertEquals(1, newsItemDao.countByCategory(NewsCategory.INSTITUTE));
		assertEquals(1, newsItemDao.countByCategory(NewsCategory.COURSE));
	}

	private void removeAllNewsItem() {
		Collection<NewsItem> all = newsItemDao.loadAll();
		newsItemDao.remove(all);
	}
	
	private NewsItem createNewsItem() {
		NewsItem newsItem = NewsItem.Factory.newInstance();
		newsItem.setCategory(NewsCategory.GLOBAL);
		newsItem.setPublisherIdentifier(TestUtility.unique());
		newsItem.setPublisherName("publishername of newsitem can be 250 character long - 123456789012345678012345678012345678901234567890123456789012345678");
		newsItem.setTitle("titel of newsitem can be 250 character long - 123456789012345678012345678012345678901234567890123456789012345678");
		newsItem.setText("text of the newsitem");
		newsItem.setAuthor("author of the newsitem");
		newsItem.setPublishDate(past);
		newsItem.setExpireDate(future);
		newsItem.setPublisherType(PublisherType.COURSE);
		return newsItem;
	}

	private void validateNewsItem(NewsItem expected, NewsItem newsItem) {
		assertEquals(expected.getCategory(), newsItem.getCategory());
		assertEquals(expected.getPublisherIdentifier(), newsItem.getPublisherIdentifier());
		assertEquals(expected.getPublisherName(),newsItem.getPublisherName());
		assertEquals(expected.getTitle(), newsItem.getTitle());
		assertEquals(expected.getText(), newsItem.getText());
		assertEquals(expected.getAuthor(), newsItem.getAuthor());
		assertEquals(expected.getPublishDate(), newsItem.getPublishDate());
		assertEquals(expected.getExpireDate(), newsItem.getExpireDate());
	}

	private void validateNewsItemInfo(NewsItem expected, NewsItemInfo info) {
		assertEquals(expected.getCategory(), info.getCategory());
		assertEquals(expected.getPublisherIdentifier(), info.getPublisherIdentifier());
		assertEquals(expected.getPublisherName(),info.getPublisherName());
		assertEquals(expected.getTitle(), info.getTitle());
		assertEquals(expected.getText(), info.getText());
		assertEquals(expected.getAuthor(), info.getAuthor());
		assertEquals(expected.getPublishDate(), info.getPublishDate());
		assertEquals(expected.getExpireDate(), info.getExpireDate());
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
}