// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.news;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * @see org.openuss.news.NewsService
 * @author Ingo Dueppe
 */
public class NewsServiceImpl extends org.openuss.news.NewsServiceBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NewsServiceImpl.class);
	
	@Override
	protected Collection handleGetCategories() throws Exception {
		return getNewsCategoryDao().loadAll();
	}

	@Override
	protected org.openuss.news.NewsItem handleGetNewsItem(java.lang.Long id) throws java.lang.Exception {
		return getNewsItemDao().load(id);
	}

	@Override
	protected void handleSaveNewsItem(NewsItem item) throws Exception {
		// check if publisher need to be created
		if (item.getPublisher().getId() == null) {
			getNewsPublisherDao().create(item.getPublisher());
		}
		if (item.getId() == null) {
			getNewsItemDao().create(item);
		} else {
			getNewsItemDao().update(item);
		}
	}

	@Override
	protected void handleDeleteNewsItem(Long id) throws Exception {
		Validate.notNull(id, "Parameter id must not be null!");
		
		NewsItem item = getNewsItemDao().load(id);
		if (item != null && item.getAttachmentId() != null) {
			getDocumentService().removeFolderEntry(item.getAttachmentId());
			getNewsItemDao().remove(id);
		}
	}

	@Override
	protected Collection handleGetNewsItems(Long categoryId, Long publisherForeignId, String publisherForeignClass) throws Exception {
		
		NewsCriteria criteria = new NewsCriteria(publisherForeignId, publisherForeignClass, categoryId, null, null);
		
		return getNewsItemDao().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection handleGetNewsItems(Long publisherForeignId, String publisherForeignClass, int count) throws Exception {
		
		
		NewsCriteria criteria = new NewsCriteria(publisherForeignId, publisherForeignClass, null, null, null);
		
		Collection<NewsItem> entities = getNewsItemDao().findByCriteria(criteria);
		
		Collection<NewsItem> results = narrowResults(count, entities);
		
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection handleGetNewsItems(Long categoryId, Long publisherForeignId, String publisherForeignClass, int count) throws Exception {
		
		
		NewsCriteria criteria = new NewsCriteria(publisherForeignId, publisherForeignClass, categoryId, null, null);
		
		Collection<NewsItem> entities = getNewsItemDao().findByCriteria(criteria);
		
		Collection<NewsItem> results = narrowResults(count, entities);
		
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection handleGetNewsItems(Timestamp timestamp, int count) throws Exception {
		
		
		NewsCriteria criteria = new NewsCriteria(null, null, null, timestamp, timestamp);
		criteria.setMaximumResultSize(count);
		
		return getNewsItemDao().findByCriteria(criteria);
				
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection handleGetNewsItems(Timestamp timestamp, int count, Long publisherId) throws Exception {
		
		NewsPublisher publisher = getNewsPublisherDao().load(publisherId);
		
//		if (publisher==null) throw new IllegalArgumentException("The specified publisher dows not exist!\n\n");
		
		NewsCriteria criteria = new NewsCriteria(publisher.getForeignId(), publisher.getForeignClass(), null, timestamp, timestamp);
		
		Collection<NewsItem> entities = getNewsItemDao().findByCriteria(criteria);
		
		Collection<NewsItem> results = narrowResults(count, entities);
		
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection handleGetNewsItems(Timestamp timestamp, int count, Long publisherId, Long categoryId) throws Exception {
		
		NewsPublisher publisher = getNewsPublisherDao().load(publisherId);
		
		NewsCriteria criteria = new NewsCriteria(publisher.getForeignId(), publisher.getForeignClass(), categoryId, timestamp, timestamp);
		
		Collection<NewsItem> entities = getNewsItemDao().findByCriteria(criteria);
		
		Collection<NewsItem> results = narrowResults(count, entities);
		
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection handleGetNewsItems(Timestamp begin, Timestamp end) throws Exception {
		
		if (begin.getTime()== end.getTime()) throw new IllegalArgumentException("\nRelease date is identical with expiration date of the date range\n\n");
		
		if (begin.getTime() > end.getTime()) throw new IllegalArgumentException("\nRelease date is earlier than expiration date of the date range\n\n");
		
		
		NewsCriteria criteria = new NewsCriteria(null, null, null, begin, end);
		
		Collection<NewsItem> results = getNewsItemDao().findByCriteria(criteria);
	
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection handleGetNewsItems(Timestamp begin, Timestamp end, Long publisherId) throws Exception {
	
		if (begin.getTime()== end.getTime()) throw new IllegalArgumentException("\nRelease date is identical with expiration date of the date range\n\n");
		
		if (begin.getTime() > end.getTime()) throw new IllegalArgumentException("\nRelease date is earlier than expiration date of the date range\n\n");
		
		NewsPublisher publisher = getNewsPublisherDao().load(publisherId);
		
		NewsCriteria criteria = new NewsCriteria(publisher.getForeignId(), publisher.getForeignClass(), null, begin, end);
		
		Collection<NewsItem> results = getNewsItemDao().findByCriteria(criteria);
	
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection handleGetNewsItems(Timestamp begin, Timestamp end, Long publisherId, Long categoryId) throws Exception {
		
		if (begin.getTime()== end.getTime()) throw new IllegalArgumentException("\nRelease date is identical with expiration date of the date range\n\n");
		
		if (begin.getTime() > end.getTime()) throw new IllegalArgumentException("\nRelease date is earlier than expiration date of the date range\n\n");
		
		NewsPublisher publisher = getNewsPublisherDao().load(publisherId);
		
		NewsCriteria criteria = new NewsCriteria(publisher.getForeignId(), publisher.getForeignClass(), categoryId, begin, end);
		
		Collection<NewsItem> results = getNewsItemDao().findByCriteria(criteria);
	
		return results;
	}

	/**
	 * @deprecated should be handled within the database or by hibernate.
	 * @param count
	 * @param entities
	 * @return Collection<NewsItem>
	 */
	private Collection<NewsItem> narrowResults(int count, Collection<NewsItem> entities) {
		Collection<NewsItem> results = new ArrayList<NewsItem>(); 
		
		for (NewsItem newsItem : entities) {
			results.add(newsItem);
			if (results.size() >= count) break;
		}
		return results;
	}

	@Override
	protected Collection handleGetCurrentNewsItems(Object publisher, Integer count) throws Exception {
		NewsPublisher newsPublisher = getPublisher(publisher);
		if (newsPublisher == null) {
			return null;
		}
		
		NewsCriteria criteria = new NewsCriteria(newsPublisher.getForeignId(),null,null,new Date(),new Date());
		criteria.setMaximumResultSize(count);
		return getNewsItemDao().findByCriteria(criteria);
	}

	@Override
	protected Collection handleGetCurrentNewsItems(Long categoryId, Integer count) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List handleGetNewsItems(Object publisher) throws Exception {
		NewsPublisher newsPublisher = getPublisher(publisher);
		if (newsPublisher == null)
			return null;
		NewsCriteria criteria = new NewsCriteria(newsPublisher.getForeignId(), null, null, null, null); 
		return getNewsItemDao().findByCriteria(criteria);
	}

	
	@Override
	protected NewsPublisher handleGetPublisher(Object publisher) throws Exception {
		// extract publisher id
		Long foreignId = extractPublisherId(publisher);
		if (foreignId == null) {
			return null;
		}
		// load publisher 
		return getNewsPublisherDao().findPublisher(foreignId);
	}

	@Override
	protected NewsCategory handleGetCategory(Long id) throws Exception {
		return getNewsCategoryDao().load(id);
	}
	
	private Long extractPublisherId(Object publisher) throws IllegalAccessException, InvocationTargetException {
		Class clazz = publisher.getClass();
		try {
			Method method = clazz.getMethod("getId", new Class[] {});
			return (Long) method.invoke(publisher, new Object[]{});
		} catch (NoSuchMethodException e) {
			logger.error(e);
			throw new IllegalArgumentException("Publisher of class '"+clazz+"' does not provide the required getId() method: "+publisher);
		}
	}
}