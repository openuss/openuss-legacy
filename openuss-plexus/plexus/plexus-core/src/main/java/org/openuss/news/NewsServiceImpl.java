// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.news;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.framework.utilities.DomainObjectUtility;

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
		
		if (item.getAttachments() != null && !item.getAttachments().isEmpty()) {
			logger.debug("found "+item.getAttachments().size()+" attachments");
			FolderInfo folder = getDocumentService().getFolder(entity);
			
			for (FileInfo attachment : item.getAttachments()) {
				getDocumentService().createFileEntry(attachment, folder);
			}
		}
	}

	private void saveNewsItem(NewsItemInfo item, NewsItem entity) throws DocumentApplicationException {
		getNewsItemDao().update(entity);
		if (item.getAttachments() == null) {
			item.setAttachments(new ArrayList<FileInfo>());
		}
		List<FileInfo> attachments = item.getAttachments();
		importFileEntries(entity, attachments);
	}

	/**
	 * This method delete all existing file entries within the root folder of the domain object, that are not
	 * listed in the attachment list. Also it will persist all new files into the folder  
	 * @param domain
	 * @param attachments
	 * @throws DocumentApplicationException
	 */
	private void importFileEntries(Object domain, List<FileInfo> attachments) throws DocumentApplicationException {
		List<FileInfo> savedAttachments = getDocumentService().getFileEntries(domain);
		Collection<FileInfo> removedAttachments = CollectionUtils.subtract(savedAttachments, attachments);
		getDocumentService().removeFileEntries(removedAttachments);
		
		FolderInfo folder = getDocumentService().getFolder(domain);
		for (FileInfo attachment: attachments) {
			if (attachment.getId() == null) {
				getDocumentService().createFileEntry(attachment, folder);
			}
		}
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
	protected List handleGetNewsItems(Object publisher) throws Exception {
		return getNewsItemDao().findByPublisher(NewsItemDao.TRANSFORM_NEWSITEMINFO, obtainIdentifier(publisher));
	}


	/**
	 * @see org.openuss.news.NewsService#getCurrentNewsItems(java.lang.Object,
	 *      java.lang.Integer)
	 */
	protected List handleGetCurrentNewsItems(Object publisher, Integer count) throws Exception {
		Long identifier = obtainIdentifier(publisher);
		
		NewsCriteria criteria = new NewsCriteria();
		criteria.setPublisherIdentifier(identifier);
		criteria.setPublishDate(new Date());
		criteria.setExpireDate(new Date());
		criteria.setFetchSize(count);
		
		return getNewsItemDao().findByCriteria(NewsItemDao.TRANSFORM_NEWSITEMINFO, criteria); 
	}

	/**
	 * @see org.openuss.news.NewsService#getCurrentNewsItems(org.openuss.news.NewsCategory,
	 *      java.lang.Integer)
	 */
	protected List handleGetCurrentNewsItems(NewsCategory category, Integer count) throws Exception {
		NewsCriteria criteria = new NewsCriteria();
		criteria.setCategory(category);
		criteria.setPublishDate(new Date());
		criteria.setExpireDate(new Date());
		criteria.setFetchSize(count);
		
		return getNewsItemDao().findByCriteria(NewsItemDao.TRANSFORM_NEWSITEMINFO, criteria); 
	}

	/**
	 * @see org.openuss.news.NewsService#getPublishedNewsItems(java.lang.Object,
	 *      java.lang.Integer, java.lang.Integer)
	 */
	protected List handleGetPublishedNewsItems(Object publisher, Integer firstResult,Integer count) throws Exception {
		Long identifier = obtainIdentifier(publisher);

		NewsCriteria criteria = new NewsCriteria();
		criteria.setPublisherIdentifier(identifier);
		criteria.setPublishDate(new Date());
		criteria.setExpireDate(new Date());
		criteria.setFirstResult(firstResult);
		criteria.setFetchSize(count);
		
		return getNewsItemDao().findByCriteria(NewsItemDao.TRANSFORM_NEWSITEMINFO, criteria); 
	}

	/**
	 * @see org.openuss.news.NewsService#getPublishedNewsItems(org.openuss.news.NewsCategory,
	 *      java.lang.Integer, java.lang.Integer)
	 */
	protected List handleGetPublishedNewsItems(NewsCategory category, Integer firstResult, Integer count) throws Exception {
		NewsCriteria criteria = new NewsCriteria();
		criteria.setCategory(category);
		criteria.setPublishDate(new Date());
		criteria.setExpireDate(new Date());
		criteria.setFirstResult(firstResult);
		criteria.setFetchSize(count);
		
		return getNewsItemDao().findByCriteria(NewsItemDao.TRANSFORM_NEWSITEMINFO, criteria); 
	}

	/**
	 * @see org.openuss.news.NewsService#getPublishedNewsItemsCount(java.lang.Object)
	 */
	protected long handleGetPublishedNewsItemsCount(Object publisher) throws Exception {
		return getNewsItemDao().countByPublisher(obtainIdentifier(publisher));
	}

	/**
	 * @see org.openuss.news.NewsService#getPublishedNewsItemsCount(org.openuss.news.NewsCategory)
	 */
	protected long handleGetPublishedNewsItemsCount(NewsCategory category) throws Exception {
		Validate.notNull(category, "category must not be null");
		return getNewsItemDao().countByCategory(category);
	}

	private Long obtainIdentifier(Object publisher) {
		Validate.notNull(publisher, "publisher must not be null.");
		Long identifier = DomainObjectUtility.identifierFromObject(publisher);
		Validate.notNull(identifier, "publisher must provide an identifier");
		return identifier;
	}
}