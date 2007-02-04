// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.news;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

/**
 * JUnit Test for Spring Hibernate NewsItemDao class.
 * @see org.openuss.news.NewsItemDao
 */
public class NewsItemDaoTest extends NewsItemDaoTestBase {
	
	private static final String GLOBAL = "Global";
	private static final String USER = "Users";
	private static final String FACULTY = "Faculty";
	private static final String COURSE = "Course";
	
	private static final long PUBLISHER_A = 0;
	private static final long PUBLISHER_B= 1;
	
	private NewsCategoryDao newsCategoryDao;
	private NewsPublisherDao newsPublisherDao;
	
	public void testNewsItemDaoCreate() {
		NewsItem newsItem = NewsItem.Factory.newInstance();
		
		Collection<NewsCategory> categories = setUpCategories();
		Collection<NewsPublisher> publishers = setUpPublishers();
		
		newsItem.setTitle("Title");
		newsItem.setText("Text");
		newsItem.setCategory(getNewsCategory(categories, USER));
		newsItem.setPublisher(getNewsPublisher(publishers, PUBLISHER_A));
		newsItem.setPublishDate(new Timestamp(System.currentTimeMillis()-1));
		newsItem.setExpireDate(new Timestamp(System.currentTimeMillis()+1));
//		newsItem.setAttachment(new byte[2000]);
		assertNull(newsItem.getId());
		newsItemDao.create(newsItem);
		assertNotNull(newsItem.getId());
	}
	
	public void testNewsItemDaoLoadAndRemove() {
		Collection<NewsCategory> categories = setUpCategories();
		Collection<NewsPublisher> publishers = setUpPublishers();
		
		Timestamp time = new Timestamp(System.currentTimeMillis());
		NewsItem newsItem = NewsItem.Factory.newInstance("Title","Text", time, getNewsCategory(categories, GLOBAL), getNewsPublisher(publishers, PUBLISHER_A));
		newsItemDao.create(newsItem);
		assertNotNull(newsItem.getId());
		Long id = newsItem.getId();
		newsItemDao.remove(id);

		newsItem = newsItemDao.load(id);
		assertNull(newsItem);
	}
	
	public void testNewsItemDateUpdate() {
		Collection<NewsCategory> categories = setUpCategories();
		Collection<NewsPublisher> publishers = setUpPublishers();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		NewsItem newsItem = NewsItem.Factory.newInstance("Title","Text", time, getNewsCategory(categories, USER), getNewsPublisher(publishers, PUBLISHER_A));
		newsItemDao.create(newsItem);
		assertNotNull(newsItem.getId());
		newsItem.getPublishDate().setTime(time.getTime()+1);
		newsItemDao.update(newsItem);
		NewsItem item = newsItemDao.load(newsItem.getId());
		assertEquals(item, newsItem );
	}
	
	public void testFindByCriteriaActiveNewsItemsWithoutDateRange() {
		//FIXME this test depends on computer country settings "germany"
		Collection<NewsCategory> categories = setUpCategories();
		Collection<NewsPublisher> publishers = setUpPublishers();
		
		Collection<NewsItem> items = createTestEntities(categories, publishers);
		newsItemDao.create(items);
		
		int testSize;
		NewsCriteria criteria;
		Collection results;
		
		Date now = null;
		try {
			now = DateFormat.getInstance().parse("26.07.2006 18:01");
		} catch (ParseException e1) {
			fail();
		}
		
		//test for active news items without date range
		testSize = 7;
		criteria = new NewsCriteria();
		criteria.setPublishDate(now);
		results = newsItemDao.findByCriteria(criteria);
		
		assertEquals(testSize, results.size());
		
	}
	
	public void testFindByCriteriaActiveNewsItemsWithDateRange() {
		//FIXME ???
		Collection<NewsCategory> categories = setUpCategories();
		Collection<NewsPublisher> publishers = setUpPublishers();
		
		Collection<NewsItem> items = createTestEntities(categories, publishers);
		newsItemDao.create(items);
		
		int testSize;
		NewsCriteria criteria;
		Collection results;
		
		Date now = null;
		try {
			now = DateFormat.getInstance().parse("26.07.2006 20:10");
		} catch (ParseException e1) {
			fail();
		}
		
		//test for active news items with date range
		testSize = 6;
		criteria = new NewsCriteria();
		criteria.setExpireDate(now);
		criteria.setPublishDate(now);
		results = newsItemDao.findByCriteria(criteria);
		
		assertEquals(testSize, results.size());
		
	}
	
	public void testFindByCriteriaPublisherBNewsItems() {
		Collection<NewsCategory> categories = setUpCategories();
		Collection<NewsPublisher> publishers = setUpPublishers();
		
		Collection<NewsItem> items = createTestEntities(categories, publishers);
		newsItemDao.create(items);
		
		int testSize;
		NewsCriteria criteria;
		Collection results;
		
		//test for course news items from publisher B
		testSize = 1;
		criteria = new NewsCriteria();
		criteria.setCategoryId(getNewsCategory(categories, COURSE).getId());
		criteria.setPublisherForeignId(PUBLISHER_B);
		criteria.setPublisherClass("org.openuss.myComponent.MyClassB");
		
		results = newsItemDao.findByCriteria(criteria);
		
		assertEquals(testSize, results.size());
		
	}

	public void testFindByCriteriaUserNewsItems() {
		Collection<NewsCategory> categories = setUpCategories();
		Collection<NewsPublisher> publishers = setUpPublishers();
		
		Collection<NewsItem> items = createTestEntities(categories, publishers);
		newsItemDao.create(items);
		
		int testSize;
		NewsCriteria criteria;
		Collection results;
		
		//test for user news items
		testSize = 2;
		criteria = new NewsCriteria();
		criteria.setCategoryId(getNewsCategory(categories, USER).getId());
		
		results= newsItemDao.findByCriteria(criteria);
		
		assertEquals(testSize, results.size());
		
	}

	public void testFindByCriteriaFacultyNewsItems() {
		Collection<NewsCategory> categories = setUpCategories();
		Collection<NewsPublisher> publishers = setUpPublishers();
		
		Collection<NewsItem> items = createTestEntities(categories, publishers);
		newsItemDao.create(items);
		
		int testSize;
		NewsCriteria criteria;
		Collection results;
		
		//test for faculty news items
		testSize = 16;
		criteria = new NewsCriteria();
		criteria.setCategoryId(getNewsCategory(categories, FACULTY).getId());
		
		results= newsItemDao.findByCriteria(criteria);
		
		assertEquals(testSize, results.size());
		
	}

	public void testFindByCriteriaGlobalNewsItems() {
		Collection<NewsCategory> categories = setUpCategories();
		Collection<NewsPublisher> publishers = setUpPublishers();
		
		Collection<NewsItem> items = createTestEntities(categories, publishers);
		newsItemDao.create(items);
		
		int testSize;
		NewsCriteria criteria;
		Collection results;
		
		//test for global news items
		testSize = 4;
		criteria = new NewsCriteria();
		criteria.setCategoryId(getNewsCategory(categories, GLOBAL).getId());
		
		results = newsItemDao.findByCriteria(criteria);
		
		assertEquals(testSize, results.size());
		
	}

	private Collection<NewsItem> createTestEntities(Collection<NewsCategory> categories, Collection<NewsPublisher> publishers) {

		Timestamp monday0600 = null;
		Timestamp tuesday0600 = null;
		Timestamp wednesday0600 = null;
		Timestamp thursday0600 = null;
		Timestamp friday0600 = null;
		Timestamp saturday0600 = null;
		Timestamp sunday0600 = null;
		
		Timestamp monday1200 = null;
		Timestamp tuesday1200 = null;
		Timestamp wednesday1200 = null;
		Timestamp thursday1200 = null;
		Timestamp friday1200 = null;
		Timestamp saturday1200 = null;
		Timestamp sunday1200 = null;

		Timestamp monday1800 = null;
		Timestamp tuesday1800 = null;
		Timestamp wednesday1800 = null;
		Timestamp thursday1800 = null;
		Timestamp friday1800 = null;
		Timestamp saturday1800 = null;
		Timestamp sunday1800 = null;

		try {
			
			monday0600 = new Timestamp(DateFormat.getInstance().parse("24.07.2006 06:00").getTime());
			tuesday0600 = new Timestamp(DateFormat.getInstance().parse("25.07.2006 06:00").getTime());
			wednesday0600 = new Timestamp(DateFormat.getInstance().parse("26.07.2006 06:00").getTime());
			thursday0600 = new Timestamp(DateFormat.getInstance().parse("27.07.2006 06:00").getTime());
			friday0600 = new Timestamp(DateFormat.getInstance().parse("28.07.2006 06:00").getTime());
			saturday0600 = new Timestamp(DateFormat.getInstance().parse("29.07.2006 06:00").getTime());
			sunday0600 = new Timestamp(DateFormat.getInstance().parse("30.07.2006 06:00").getTime());
			
			monday1200 = new Timestamp(DateFormat.getInstance().parse("24.07.2006 12:00").getTime());
			tuesday1200 = new Timestamp(DateFormat.getInstance().parse("25.07.2006 12:00").getTime());
			wednesday1200 = new Timestamp(DateFormat.getInstance().parse("26.07.2006 12:00").getTime());
			thursday1200 = new Timestamp(DateFormat.getInstance().parse("27.07.2006 12:00").getTime());
			friday1200 = new Timestamp(DateFormat.getInstance().parse("28.07.2006 12:00").getTime());
			saturday1200 = new Timestamp(DateFormat.getInstance().parse("29.07.2006 12:00").getTime());
			sunday1200 = new Timestamp(DateFormat.getInstance().parse("30.07.2006 12:00").getTime());

			monday1800 = new Timestamp(DateFormat.getInstance().parse("24.07.2006 18:00").getTime());
			tuesday1800 = new Timestamp(DateFormat.getInstance().parse("25.07.2006 18:00").getTime());
			wednesday1800 = new Timestamp(DateFormat.getInstance().parse("26.07.2006 18:00").getTime());
			thursday1800 = new Timestamp(DateFormat.getInstance().parse("27.07.2006 18:00").getTime());
			friday1800 = new Timestamp(DateFormat.getInstance().parse("28.07.2006 18:00").getTime());
			saturday1800 = new Timestamp(DateFormat.getInstance().parse("29.07.2006 18:00").getTime());
			sunday1800 = new Timestamp(DateFormat.getInstance().parse("30.07.2006 18:00").getTime());
		} catch (ParseException e) {
			fail();
		}
		
		Collection<NewsItem> items = new Vector<NewsItem>();
		
		NewsItem item11 = NewsItem.Factory.newInstance("title", "text", monday0600, getNewsCategory(categories, GLOBAL), getNewsPublisher(publishers, PUBLISHER_A));
		NewsItem item12 = NewsItem.Factory.newInstance("title", "text", tuesday1200, getNewsCategory(categories, USER), getNewsPublisher(publishers, PUBLISHER_A));
		NewsItem item13 = NewsItem.Factory.newInstance("title", "text", wednesday1800, getNewsCategory(categories, FACULTY), getNewsPublisher(publishers, PUBLISHER_A));
		NewsItem item14 = NewsItem.Factory.newInstance("title", "text", thursday0600, getNewsCategory(categories, COURSE), getNewsPublisher(publishers, PUBLISHER_A));

		NewsItem item15 = NewsItem.Factory.newInstance("title", "text", monday1200, getNewsCategory(categories, GLOBAL), getNewsPublisher(publishers, PUBLISHER_B));
		NewsItem item16 = NewsItem.Factory.newInstance("title", "text", tuesday1800, getNewsCategory(categories, USER), getNewsPublisher(publishers, PUBLISHER_B));
		NewsItem item17 = NewsItem.Factory.newInstance("title", "text", wednesday0600, getNewsCategory(categories, FACULTY), getNewsPublisher(publishers, PUBLISHER_B));
		NewsItem item18 = NewsItem.Factory.newInstance("title", "text", thursday1200, getNewsCategory(categories, COURSE), getNewsPublisher(publishers, PUBLISHER_B));
		
		NewsItem item21 = NewsItem.Factory.newInstance("title", "text", friday1800, getNewsCategory(categories, GLOBAL), getNewsPublisher(publishers, PUBLISHER_A));
		NewsItem item22 = NewsItem.Factory.newInstance("title", "text", saturday0600, getNewsCategory(categories, GLOBAL), getNewsPublisher(publishers, PUBLISHER_B));
		
		NewsItem item301 = NewsItem.Factory.newInstance("title", "text", wednesday1800, monday0600,"author", getNewsCategory(categories, FACULTY), getNewsPublisher(publishers, PUBLISHER_B),null);
		NewsItem item302 = NewsItem.Factory.newInstance("title", "text", saturday0600, monday1200,"author", getNewsCategory(categories, FACULTY), getNewsPublisher(publishers, PUBLISHER_A),null);
		NewsItem item303 = NewsItem.Factory.newInstance("title", "text", saturday1200, monday1800,"author", getNewsCategory(categories, FACULTY), getNewsPublisher(publishers, PUBLISHER_B),null);
		NewsItem item304 = NewsItem.Factory.newInstance("title", "text", saturday1800, tuesday0600,"author", getNewsCategory(categories, FACULTY), getNewsPublisher(publishers, PUBLISHER_A),null);
		NewsItem item305 = NewsItem.Factory.newInstance("title", "text", sunday0600, tuesday1200,"author", getNewsCategory(categories, FACULTY), getNewsPublisher(publishers, PUBLISHER_B),null);
		NewsItem item306 = NewsItem.Factory.newInstance("title", "text", sunday1200, tuesday1800, "author",getNewsCategory(categories, FACULTY), getNewsPublisher(publishers, PUBLISHER_A),null);
		NewsItem item307 = NewsItem.Factory.newInstance("title", "text", sunday1800, wednesday0600, "author",getNewsCategory(categories, FACULTY), getNewsPublisher(publishers, PUBLISHER_A),null);
		NewsItem item308 = NewsItem.Factory.newInstance("title", "text", saturday0600, wednesday1200,"author", getNewsCategory(categories, FACULTY), getNewsPublisher(publishers, PUBLISHER_A),null);
		NewsItem item309 = NewsItem.Factory.newInstance("title", "text", saturday1200, wednesday1800,"author", getNewsCategory(categories, FACULTY), getNewsPublisher(publishers, PUBLISHER_A),null);
		NewsItem item310 = NewsItem.Factory.newInstance("title", "text", saturday1800, thursday0600,"author", getNewsCategory(categories, FACULTY), getNewsPublisher(publishers, PUBLISHER_B),null);
		NewsItem item311 = NewsItem.Factory.newInstance("title", "text", sunday0600, thursday1200,"author", getNewsCategory(categories, FACULTY), getNewsPublisher(publishers, PUBLISHER_A),null);
		NewsItem item312 = NewsItem.Factory.newInstance("title", "text", sunday1200, thursday1800,"author", getNewsCategory(categories, FACULTY), getNewsPublisher(publishers, PUBLISHER_A),null);
		NewsItem item313 = NewsItem.Factory.newInstance("title", "text", sunday1800, friday0600,"author", getNewsCategory(categories, FACULTY), getNewsPublisher(publishers, PUBLISHER_B),null);
		NewsItem item314 = NewsItem.Factory.newInstance("title", "text", saturday1200, friday1200,"author", getNewsCategory(categories, FACULTY), getNewsPublisher(publishers, PUBLISHER_A),null);
		
		items.add(item11);
		items.add(item12);
		items.add(item13);
		items.add(item14);
		items.add(item15);
		items.add(item16);
		items.add(item17);
		items.add(item18);
		items.add(item21);
		items.add(item22);
		items.add(item301);
		items.add(item302);
		items.add(item303);
		items.add(item304);
		items.add(item305);
		items.add(item306);
		items.add(item307);
		items.add(item308);
		items.add(item309);
		items.add(item310);
		items.add(item311);
		items.add(item312);
		items.add(item313);
		items.add(item314);
	
		
		/*
		 * Categories
		 * 
		 * global==4
		 * user==2
		 * faculty==16
		 * course==2
		 * */
		
		/*
		 * Publisher
		 * A==14
		 * B==10
		 */
		
		/*
		 * Date Range
		 * Items active at now == 14
		 * Items less or equal at now for startDate == 15
		 */
		return items;
	}
	
	private Collection<NewsCategory> setUpCategories() {
		Collection<NewsCategory> collection = new Vector<NewsCategory>();

		NewsCategory global = NewsCategory.Factory.newInstance(GLOBAL);
		NewsCategory user = NewsCategory.Factory.newInstance(USER);
		NewsCategory faculty = NewsCategory.Factory.newInstance(FACULTY);
		NewsCategory course = NewsCategory.Factory.newInstance(COURSE);
		
		collection.add(global);
		collection.add(user);
		collection.add(faculty);
		collection.add(course);
		
		newsCategoryDao.create(collection);
		return newsCategoryDao.loadAll();
	}
	
	private Collection<NewsPublisher> setUpPublishers() {
		Collection<NewsPublisher> collection = new Vector<NewsPublisher>();
		
		NewsPublisher publisherA = NewsPublisher.Factory.newInstance(PUBLISHER_A, "Publisher A", "org.openuss.myComponent.MyClassA");
		NewsPublisher publisherB = NewsPublisher.Factory.newInstance(PUBLISHER_B, "Publisher B", "org.openuss.myComponent.MyClassB");
		
		collection.add(publisherA);
		collection.add(publisherB);

		newsPublisherDao.create(collection);
		return newsPublisherDao.loadAll();
	}


	private NewsPublisher getNewsPublisher(Collection<NewsPublisher> publishers, long type) {
		NewsPublisher publisher = null;
		Iterator<NewsPublisher> iter = publishers.iterator();
		while (iter.hasNext()) {
			NewsPublisher element = (NewsPublisher) iter.next();
			if (element.getForeignId()==type) {
				publisher = element;
				break;
			}
		}
		return publisher;
	}
	
	private NewsCategory getNewsCategory(Collection<NewsCategory> categories, String type) {
		NewsCategory category = null;
		Iterator<NewsCategory> iter = categories.iterator();
		while (iter.hasNext()) {
			NewsCategory element = (NewsCategory) iter.next();
			if (element.getName().equalsIgnoreCase(type)) {
				category = element;
				break;
			}
		}
		return category;
	}
	
	public NewsCategoryDao getNewsCategoryDao() {
		return newsCategoryDao;
	}

	public void setNewsCategoryDao(NewsCategoryDao newsCategoryDao) {
		this.newsCategoryDao = newsCategoryDao;
	}

	public NewsPublisherDao getNewsPublisherDao() {
		return newsPublisherDao;
	}

	public void setNewsPublisherDao(NewsPublisherDao newsPublisherDao) {
		this.newsPublisherDao = newsPublisherDao;
	}	
}