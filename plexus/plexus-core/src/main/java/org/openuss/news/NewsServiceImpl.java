// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.news;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.FileInfo;
import org.openuss.foundation.DomainObject;
import org.openuss.security.Roles;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @author ingo dueppe
 * @see org.openuss.news.NewsService
 */
public class NewsServiceImpl extends org.openuss.news.NewsServiceBase {

	private static final Logger logger = Logger.getLogger(NewsServiceImpl.class);

	/**
	 * @see org.openuss.news.NewsService#saveNewsItem(org.openuss.news.NewsItemInfo)
	 */
	protected void handleSaveNewsItem(NewsItemInfo item) throws Exception {
		Validate.notNull(item, "item must not be null.");
		Validate.notNull(item.getCategory(), "item.category must not be null.");
		Validate.notNull(item.getPublisherIdentifier(), "item.publisherIdentifier must not be null.");
		Validate.notNull(item.getTitle(), "item.title must not be null.");
		Validate.notNull(item.getText(), "item.text must not be null.");
		
		NewsItem entity = getNewsItemDao().newsItemInfoToEntity(item);
		
		if (entity.getId() == null) {
			createNewsItem(item, entity);
		} else {
			saveNewsItem(item, entity);
		}
		getNewsItemDao().toNewsItemInfo(entity, item);
	}

	private void createNewsItem(NewsItemInfo item, NewsItem entity) throws DocumentApplicationException {
		getNewsItemDao().create(entity);
		getSecurityService().createObjectIdentity(entity, item.getPublisherIdentifier());
		
		if (NewsCategory.COURSE != entity.getCategory() && NewsCategory.GROUP != entity.getCategory()) {
			getSecurityService().setPermissions(Roles.ANONYMOUS, entity, LectureAclEntry.READ);
			getSecurityService().setPermissions(Roles.USER, entity, LectureAclEntry.READ);
		} 
		
		if (item.getAttachments() != null && !item.getAttachments().isEmpty()) {
			logger.debug("found "+item.getAttachments().size()+" attachments");
			getDocumentService().diffSave(entity, item.getAttachments());
		}
	}

	private void saveNewsItem(NewsItemInfo item, NewsItem entity) throws DocumentApplicationException {
		getNewsItemDao().update(entity);
		if (item.getAttachments() == null) {
			item.setAttachments(new ArrayList<FileInfo>());
		}
		getDocumentService().diffSave(entity, item.getAttachments());
	}


	/**
	 * @see org.openuss.news.NewsService#getNewsItem(org.openuss.news.NewsItemInfo)
	 */
	protected NewsItemInfo handleGetNewsItem(NewsItemInfo item)	throws Exception {
		validateInfoParameter(item);
		
		NewsItemInfo result = (NewsItemInfo) getNewsItemDao().load(NewsItemDao.TRANSFORM_NEWSITEMINFO, item.getId() );
		
		if (result != null) {
			result.setAttachments(getDocumentService().getFileEntries(result));
		}
		
		return result;
	}

	private void validateInfoParameter(NewsItemInfo item) {
		Validate.notNull(item, "item must not be null.");
		Validate.notNull(item.getId(), "item must provide an id.");
	}


	/**
	 * @see org.openuss.news.NewsService#deleteNewsItem(org.openuss.news.NewsItemInfo)
	 */
	protected void handleDeleteNewsItem(NewsItemInfo item) throws Exception {
		validateInfoParameter(item);
		
		NewsItem entity = (NewsItem) getNewsItemDao().load(item.getId());
		if (entity != null) {
			getNewsItemDao().remove(entity);
			getDocumentService().removeFileEntries(getDocumentService().getFileEntries(entity));
		}
	}

	/**
	 * @see org.openuss.news.NewsService#getNewsItems(java.lang.Object)
	 */
	protected List<NewsItemInfo> handleGetNewsItems(DomainObject publisher) throws Exception {
		Validate.notNull(publisher, "Parameter publisher must not be null!");
		return getNewsItemDao().findByPublisher(NewsItemDao.TRANSFORM_NEWSITEMINFO, publisher.getId());
	}


	/**
	 * @see org.openuss.news.NewsService#getCurrentNewsItems(java.lang.Object,
	 *      java.lang.Integer)
	 */
	protected List<NewsItemInfo> handleGetCurrentNewsItems(DomainObject publisher, Integer count) throws Exception {
		Validate.notNull(publisher, "Parameter publisher must not be null!");
		
		NewsCriteria criteria = new NewsCriteria();
		criteria.setPublisherIdentifier(publisher.getId());
		criteria.setPublishDate(new Date());
		criteria.setExpireDate(new Date());
		criteria.setMaximumResultSize(count);
		
		return getNewsItemDao().findByCriteria(NewsItemDao.TRANSFORM_NEWSITEMINFO, criteria); 
	}

	/**
	 * @see org.openuss.news.NewsService#getCurrentNewsItems(org.openuss.news.NewsCategory,
	 *      java.lang.Integer)
	 */
	protected List<NewsItemInfo> handleGetCurrentNewsItems(NewsCategory category, Integer count) throws Exception {
		NewsCriteria criteria = new NewsCriteria();
		criteria.setCategory(category);
		criteria.setPublishDate(new Date());
		criteria.setExpireDate(new Date());
		criteria.setMaximumResultSize(count);
		
		return getNewsItemDao().findByCriteria(NewsItemDao.TRANSFORM_NEWSITEMINFO, criteria); 
	}

	/**
	 * @see org.openuss.news.NewsService#getPublishedNewsItems(java.lang.Object,
	 *      java.lang.Integer, java.lang.Integer)
	 */
	protected List<NewsItemInfo> handleGetPublishedNewsItems(DomainObject publisher, Integer firstResult,Integer count) throws Exception {
		Validate.notNull(publisher, "Parameter publisher must not be null!");

		NewsCriteria criteria = new NewsCriteria();
		criteria.setPublisherIdentifier(publisher.getId());
		criteria.setPublishDate(new Date());
		criteria.setExpireDate(new Date());
		criteria.setFirstResult(firstResult);
		criteria.setMaximumResultSize(count);
		
		return getNewsItemDao().findByCriteria(NewsItemDao.TRANSFORM_NEWSITEMINFO, criteria); 
	}

	/**
	 * @see org.openuss.news.NewsService#getPublishedNewsItems(org.openuss.news.NewsCategory,
	 *      java.lang.Integer, java.lang.Integer)
	 */
	protected List<NewsItemInfo> handleGetPublishedNewsItems(NewsCategory category, Integer firstResult, Integer count) throws Exception {
		NewsCriteria criteria = new NewsCriteria();
		criteria.setCategory(category);
		criteria.setPublishDate(new Date());
		criteria.setExpireDate(new Date());
		criteria.setFirstResult(firstResult);
		criteria.setMaximumResultSize(count);
		
		return getNewsItemDao().findByCriteria(NewsItemDao.TRANSFORM_NEWSITEMINFO, criteria); 
	}

	/**
	 * @see org.openuss.news.NewsService#getPublishedNewsItemsCount(java.lang.Object)
	 */
	protected long handleGetPublishedNewsItemsCount(DomainObject publisher) throws Exception {
		Validate.notNull(publisher, "Parameter publisher must not be null!");
		return getNewsItemDao().countByPublisher(publisher.getId());
	}

	/**
	 * @see org.openuss.news.NewsService#getPublishedNewsItemsCount(org.openuss.news.NewsCategory)
	 */
	protected long handleGetPublishedNewsItemsCount(NewsCategory category) throws Exception {
		Validate.notNull(category, "category must not be null");
		return getNewsItemDao().countByCategory(category);
	}
}