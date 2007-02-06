// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.news;

import java.sql.Timestamp;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * JUnit Test for Spring Hibernate NewsService class.
 * 
 * @see org.openuss.news.NewsService
 */
public class NewsServiceInputValidationTest extends AbstractDependencyInjectionSpringContextTests {
	
	private static final long PUBLISHER_ID = 12;
	private static final long CATEGORY_ID = 2;
	
	private NewsService newsService;

	public NewsService getNewsManager() {
		return newsService;
	}
	
	public void setNewsManager(NewsService newsService) {
		this.newsService = newsService;
	}
	
	public void testNewsManagerInjection() {
		assertNotNull(newsService);
	}
	
	public void testGetNewsItemsByDateRangeInputValidation() {
		
		long time1 = System.currentTimeMillis()-1;
		long time2 = System.currentTimeMillis();
		long time3 = System.currentTimeMillis()+1;
	
		try {
			newsService.getNewsItems(new Timestamp(time2), new Timestamp(time2));
			fail();
		} catch (RuntimeException e) {

			try {
				newsService.getNewsItems(new Timestamp(time2), new Timestamp(time1));
				fail();
			} catch (RuntimeException e1) {

				try {
					newsService.getNewsItems(new Timestamp(time3), new Timestamp(time2));
					fail();
				} catch (RuntimeException e2) {
					try {
						newsService.getNewsItems(new Timestamp(time3), new Timestamp(time1));
						fail();
					} catch (RuntimeException e3) {
						//do nothing
					}
				}
			}
		}
	}
	
	public void testGetNewsItemsByDateRangeAndPublisherInputValidation() {
		
		long time1 = System.currentTimeMillis()-1;
		long time2 = System.currentTimeMillis();
		long time3 = System.currentTimeMillis()+1;
		
		try {
			newsService.getNewsItems(new Timestamp(time2), new Timestamp(time2), PUBLISHER_ID);
			fail();
		} catch (RuntimeException e) {
			
			try {
				newsService.getNewsItems(new Timestamp(time2), new Timestamp(time1), PUBLISHER_ID);
				fail();
			} catch (RuntimeException e1) {
				
				try {
					newsService.getNewsItems(new Timestamp(time3), new Timestamp(time2), PUBLISHER_ID);
					fail();
				} catch (RuntimeException e2) {
					try {
						newsService.getNewsItems(new Timestamp(time3), new Timestamp(time1), PUBLISHER_ID);
						fail();
					} catch (RuntimeException e3) {
						//do nothing
					}
				}
			}
		}
	}
	
	public void testGetNewsItemsByDateRangeAndPublisherAndCategoryInputValidation() {
		
		long time1 = System.currentTimeMillis()-1;
		long time2 = System.currentTimeMillis();
		long time3 = System.currentTimeMillis()+1;
		
		try {
			newsService.getNewsItems(new Timestamp(time2), new Timestamp(time2), PUBLISHER_ID, CATEGORY_ID);
			fail();
		} catch (RuntimeException e) {
			
			try {
				newsService.getNewsItems(new Timestamp(time2), new Timestamp(time1), PUBLISHER_ID, CATEGORY_ID);
				fail();
			} catch (RuntimeException e1) {
				
				try {
					newsService.getNewsItems(new Timestamp(time3), new Timestamp(time2), PUBLISHER_ID, CATEGORY_ID);
					fail();
				} catch (RuntimeException e2) {
					try {
						newsService.getNewsItems(new Timestamp(time3), new Timestamp(time1), PUBLISHER_ID, CATEGORY_ID);
						fail();
					} catch (RuntimeException e3) {
						//do nothing
					}
				}
			}
		}
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
