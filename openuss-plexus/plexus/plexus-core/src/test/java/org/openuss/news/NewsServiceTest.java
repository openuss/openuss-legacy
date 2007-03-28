// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.news;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * JUnit Test for Spring Hibernate NewsService class.
 * 
 * @see org.openuss.news.NewsService
 */
public class NewsServiceTest extends AbstractDependencyInjectionSpringContextTests {
	
	private static final long PUBLISHER_ID = 12;
	private static final long CATEGORY_ID = 2;
	private static final long PUBLISHER_FOREIGN_ID = 12345;
	private static final String PUBLISHER_FOREIGN_CLASS = "org.openuss.myComponent.MyClass";
	private static final int COUNT = 5;
	private static final String PUPLISHER_NAME = "MyPublisher";
	
	private NewsService newsService;

	private NewsItemDao newsItemDaoMock;
	
	private NewsCategoryDao categoryDaoMock;

	private NewsPublisherDao publisherDaoMock;
	

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public void setNewsItemDaoMock(NewsItemDao daoMock) {
		this.newsItemDaoMock = daoMock;
	}

	public NewsItemDao getNewsItemDaoMock() {
		return newsItemDaoMock;
	}

	public NewsCategoryDao getCategoryDaoMock() {
		return categoryDaoMock;
	}

	public void setCategoryDaoMock(NewsCategoryDao categoryMock) {
		this.categoryDaoMock = categoryMock;
	}

	public NewsPublisherDao getPublisherDaoMock() {
		return publisherDaoMock;
	}

	public void setPublisherDaoMock(NewsPublisherDao publisherDaoMock) {
		this.publisherDaoMock = publisherDaoMock;
	}

	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		reset(newsItemDaoMock, publisherDaoMock);
	}

	public void testNewsManagerInjection() {
		assertNotNull(newsService);
	}

	public void testNewsItemDaoInjection() {
		assertNotNull(newsItemDaoMock);
	}
	
	public void testCategoryDaoInjection() {
		assertNotNull(categoryDaoMock);
	}
	
	public void testPublisherDaoInjection() {
		assertNotNull(publisherDaoMock);
	}
	
	public void testGetCategories() {
		List<NewsCategory> categories = new ArrayList<NewsCategory>();		
		expect(categoryDaoMock.loadAll()).andReturn(categories);
		replay(categoryDaoMock);
		
		Collection result = newsService.getCategories();
		assertEquals(categories, result);
		verify(categoryDaoMock);
	}
	
	/*
	public void testSaveNewsItem() {
		NewsItem item = NewsItem.Factory.newInstance("TestTitle","TestText", new Timestamp(System.currentTimeMillis()), NewsCategory.Factory.newInstance("Description"), NewsPublisher.Factory.newInstance(0L, "displayName", "org.openuss.foreignClass"));
		expect(publisherDaoMock.create(item.getPublisher())).andReturn(item.getPublisher()).anyTimes();
		expect(newsItemDaoMock.create(item)).andReturn(item);
		newsItemDaoMock.update(item);
		newsItemDaoMock.update(item);
		replay(newsItemDaoMock);
		newsService.saveNewsItem(item);
		item.setId(10L);
		newsService.saveNewsItem(item);
		item.getPublisher().setId(10L);
		newsService.saveNewsItem(item);
		verify(newsItemDaoMock);
	}
	*/

	public void testGetNewsItem() {
		NewsItem item = NewsItem.Factory.newInstance("TestTitle","TestText", new Timestamp(System.currentTimeMillis()), NewsCategory.Factory.newInstance("Description"), NewsPublisher.Factory.newInstance(0L, "displayName", "org.openuss.foreignClass"));
		item.setId(10L);
		expect(newsItemDaoMock.load(10L)).andReturn(item);
		replay(newsItemDaoMock);
		NewsItem result = newsService.getNewsItem(item.getId());
		assertEquals(item, result);
		verify(newsItemDaoMock);
	}

	public void testDeleteNewsItem() {
		
		NewsItem item = NewsItem.Factory.newInstance("TestTitle","TestText", new Timestamp(System.currentTimeMillis()), NewsCategory.Factory.newInstance("Description"), NewsPublisher.Factory.newInstance(0L, "displayName", "org.openuss.foreignClass"));
		item.setId(10L);
		item.setAttachmentId(10L);
		expect(newsItemDaoMock.load(10L)).andReturn(item);
		newsItemDaoMock.remove(10L);
		replay(newsItemDaoMock);
		newsService.deleteNewsItem(10L);
	
		verify(newsItemDaoMock);
	}

	public void testGetNewsItemsByCategoryAndPublisher() {
		List<NewsItem> items = new ArrayList<NewsItem>();
		NewsCriteria criteria = new NewsCriteria();
		expect(newsItemDaoMock.findByCriteria(eqCriteria(criteria))).andReturn(items);

		replay(newsItemDaoMock);
		
		Collection result = newsService.getNewsItems(CATEGORY_ID, PUBLISHER_FOREIGN_ID, PUBLISHER_FOREIGN_CLASS);
		assertEquals(items, result);
		verify(newsItemDaoMock);
	}
	
	public void testGetNewsItemsByPublisherWithCount() {
		List<NewsItem> items = new ArrayList<NewsItem>();
		items.add(NewsItem.Factory.newInstance());
		NewsCriteria criteria = new NewsCriteria();
		expect(newsItemDaoMock.findByCriteria(eqCriteria(criteria))).andReturn(items);

		replay(newsItemDaoMock);
		
		Collection result = newsService.getNewsItems(PUBLISHER_FOREIGN_ID, PUBLISHER_FOREIGN_CLASS, COUNT);
		assertEquals(items, result);
		
		verify(newsItemDaoMock);
	}
	
	public void testGetNewsItemsByCategoryAndPublisherWithCount() {
		List<NewsItem> items = new ArrayList<NewsItem>();
		NewsCriteria criteria = new NewsCriteria();
		expect(newsItemDaoMock.findByCriteria(eqCriteria(criteria))).andReturn(items);
		
		replay(newsItemDaoMock);
		
		Collection result = newsService.getNewsItems(CATEGORY_ID, PUBLISHER_FOREIGN_ID, PUBLISHER_FOREIGN_CLASS, COUNT);
		assertEquals(items, result);
		
		verify(newsItemDaoMock);
	}
	
	public void testGetActiveNewsItemsWithCount() {
		List<NewsItem> items = new ArrayList<NewsItem>();
		NewsCriteria criteria = new NewsCriteria();
		expect(newsItemDaoMock.findByCriteria(eqCriteria(criteria))).andReturn(items);
		
		replay(newsItemDaoMock);
		
		Collection result = newsService.getNewsItems(new Timestamp(System.currentTimeMillis()), COUNT);
		assertEquals(items, result);
		
		verify(newsItemDaoMock);
	}
	
	public void testGetActiveNewsItemsByPublisherWithCount() {
		NewsPublisher publisher = NewsPublisher.Factory.newInstance(PUBLISHER_FOREIGN_ID, PUPLISHER_NAME, PUBLISHER_FOREIGN_CLASS);
		publisher.setId(PUBLISHER_ID);
		expect(publisherDaoMock.load(PUBLISHER_ID)).andReturn(publisher);
		
		List<NewsItem> items = new ArrayList<NewsItem>();
		NewsCriteria criteria = new NewsCriteria();
		expect(newsItemDaoMock.findByCriteria(eqCriteria(criteria))).andReturn(items);
		
		replay(publisherDaoMock, newsItemDaoMock);
		
		Collection result = newsService.getNewsItems(new Timestamp(System.currentTimeMillis()), COUNT, PUBLISHER_ID);
		assertEquals(items, result);
		
		verify(publisherDaoMock, newsItemDaoMock);
	}
	
	public void testGetActiveNewsItemsByPublisherAndCategoryWithCount() {
		NewsPublisher publisher = NewsPublisher.Factory.newInstance(PUBLISHER_FOREIGN_ID, PUPLISHER_NAME, PUBLISHER_FOREIGN_CLASS);
		publisher.setId(PUBLISHER_ID);
		expect(publisherDaoMock.load(PUBLISHER_ID)).andReturn(publisher);
		
		List<NewsItem> items = new ArrayList<NewsItem>();
		NewsCriteria criteria = new NewsCriteria();
		expect(newsItemDaoMock.findByCriteria(eqCriteria(criteria))).andReturn(items);
		
		replay(publisherDaoMock, newsItemDaoMock);
		
		Collection result = newsService.getNewsItems(new Timestamp(System.currentTimeMillis()), COUNT, PUBLISHER_ID, CATEGORY_ID);
		assertEquals(items, result);
		
		verify(publisherDaoMock, newsItemDaoMock);
	}
	
	public void testGetNewsItemsByDateRange() {
		List<NewsItem> items = new ArrayList<NewsItem>();
		NewsCriteria criteria = new NewsCriteria();
		
		long begin = System.currentTimeMillis()-1;
		long end =System.currentTimeMillis()+1;
		
		expect(newsItemDaoMock.findByCriteria(eqCriteria(criteria))).andReturn(items);
		
		replay(newsItemDaoMock);
		
		Collection result = newsService.getNewsItems(new Timestamp(begin), new Timestamp(end));
		assertEquals(items, result);
		
		verify(newsItemDaoMock);
	}
	
	public void testGetNewsItemsByDateRangeAndPublisher() {
		List<NewsItem> items = new ArrayList<NewsItem>();
		NewsCriteria criteria = new NewsCriteria();
		NewsPublisher publisher = NewsPublisher.Factory.newInstance(PUBLISHER_FOREIGN_ID, PUPLISHER_NAME, PUBLISHER_FOREIGN_CLASS);
		publisher.setId(PUBLISHER_ID);
		
		
		long begin = System.currentTimeMillis()-1;
		long end =System.currentTimeMillis()+1;
		
		expect(newsItemDaoMock.findByCriteria(eqCriteria(criteria))).andReturn(items);
		expect(publisherDaoMock.load(PUBLISHER_ID)).andReturn(publisher);
		
		replay(publisherDaoMock, newsItemDaoMock);
		
		Collection result = newsService.getNewsItems(new Timestamp(begin), new Timestamp(end), PUBLISHER_ID);
		assertEquals(items, result);
		
		verify(publisherDaoMock, newsItemDaoMock);
	}
	
	public void testGetNewsItemsByDateRangeAndPublisherAndCategory() {
		List<NewsItem> items = new ArrayList<NewsItem>();
		NewsCriteria criteria = new NewsCriteria();
		NewsPublisher publisher = NewsPublisher.Factory.newInstance(PUBLISHER_FOREIGN_ID, PUPLISHER_NAME, PUBLISHER_FOREIGN_CLASS);
		publisher.setId(PUBLISHER_ID);
		
		
		long begin = System.currentTimeMillis()-1;
		long end =System.currentTimeMillis()+1;
		
		expect(newsItemDaoMock.findByCriteria(eqCriteria(criteria))).andReturn(items);
		expect(publisherDaoMock.load(PUBLISHER_ID)).andReturn(publisher);
		
		replay(publisherDaoMock, newsItemDaoMock);
		
		Collection result = newsService.getNewsItems(new Timestamp(begin), new Timestamp(end), PUBLISHER_ID, CATEGORY_ID);
		assertEquals(items, result);
		
		verify(publisherDaoMock, newsItemDaoMock);
	}

	//Inner Class to test criteria objects
	private static class NewsCriteriaMatcher implements IArgumentMatcher {
		private NewsCriteria criteria;
		
		public NewsCriteriaMatcher(NewsCriteria criteria) {
			this.criteria = criteria;
		}
		
		public void appendTo(StringBuffer buffer) {
	        buffer.append("eqCriteria(");
	        buffer.append(criteria.getClass().getName());
	        if (criteria.getCategoryId() != null)
	        	buffer.append("expected categoryId = "+criteria.getCategoryId());	        
	        if (criteria.getPublisherForeignId() != null)
	        	buffer.append("expected publisherForeignId = "+criteria.getPublisherForeignId());	        
	        if (criteria.getPublisherClass() != null)
	        	buffer.append("expected publisherClass = "+criteria.getPublisherClass());
	        if (criteria.getPublishDate() != null)
	        	buffer.append("expected dateStart = "+criteria.getPublishDate());
	        if (criteria.getExpireDate() != null)
	        	buffer.append("expected dateEnd = "+criteria.getExpireDate());
	        if (criteria.getMaximumResultSize() != null)
	        	buffer.append("expected maximumResultSize = "+criteria.getMaximumResultSize());
	        buffer.append("\")");
		}
	
		public boolean matches(Object object) {
			if (!(object instanceof NewsCriteria))
				return false;
			NewsCriteria in = (NewsCriteria) object;
			
			if (criteria.getCategoryId() != null && !criteria.getCategoryId().equals(in.getCategoryId()))
				return false;
			if (criteria.getPublisherForeignId() != null && !criteria.getPublisherForeignId().equals(in.getPublisherForeignId()))
				return false;
			if (criteria.getPublisherClass() != null && !criteria.getPublisherClass().equals(in.getPublisherClass()))
				return false;
			if (criteria.getPublishDate() != null && !criteria.getPublishDate().equals(in.getPublishDate()))
				return false;
			if (criteria.getExpireDate() != null && !criteria.getExpireDate().equals(in.getExpireDate()))
				return false;
			if (criteria.getMaximumResultSize() != null && !criteria.getMaximumResultSize().equals(in.getMaximumResultSize()))
				return false;
			return true;
		}
	}

	public static NewsCriteria eqCriteria(NewsCriteria criteria){
		EasyMock.reportMatcher(new NewsCriteriaMatcher(criteria));
		return criteria;
	}

	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext-tests.xml",				
			"classpath*:applicationContext.xml", 
			"classpath*:applicationContext-localDataSource.xml",
			"classpath*:applicationContext-beans.xml", 
			"classpath*:testSecurity.xml",
			"classpath*:beanRefFactory", 
			"classpath*:mocks.xml" };
	}
}
