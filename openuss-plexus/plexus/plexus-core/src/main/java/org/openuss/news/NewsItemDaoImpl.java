// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.news;

import org.apache.commons.lang.Validate;
import org.hibernate.HibernateException;

/**
 * @author ingo dueppe
 * @see org.openuss.news.NewsItem
 */
public class NewsItemDaoImpl extends NewsItemDaoBase {
	/**
	 * @see org.openuss.news.NewsItemDao#countByCategory(org.openuss.news.NewsCategory)
	 */
	protected long handleCountByCategory(final NewsCategory category) {
		final String hqlUpdate = "select count(n) from NewsItemImpl n where n.category = :category";
		return (Long) getHibernateTemplate().execute(new org.springframework.orm.hibernate3.HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session session) throws HibernateException {
				Long count = (Long) session.createQuery(hqlUpdate)
				.setParameter("category", category)
				.uniqueResult();
				logger.debug("updated "+count+" records.");
				return count;
			}
		}, true);
	}

	/**
	 * @see org.openuss.news.NewsItemDao#countByPublisher(java.lang.Long)
	 */
	protected long handleCountByPublisher(final Long publisherIdentifier) {
		final String hqlUpdate = "select count(n) from NewsItemImpl n where n.publisherIdentifier = :publisherIdentifier";
		return (Long) getHibernateTemplate().execute(new org.springframework.orm.hibernate3.HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session session) throws HibernateException {
				Long count = (Long) session.createQuery(hqlUpdate)
				.setLong("publisherIdentifier", publisherIdentifier)
				.uniqueResult();
				logger.debug("updated "+count+" records.");
				return count;
			}
		}, true);
		
	}

	/**
	 * @see org.openuss.news.NewsItemDao#toNewsItemInfo(org.openuss.news.NewsItem,
	 *      org.openuss.news.NewsItemInfo)
	 */
	public void toNewsItemInfo(NewsItem sourceEntity, NewsItemInfo targetVO) {
		super.toNewsItemInfo(sourceEntity, targetVO);
		targetVO.setExpired(sourceEntity.isExpired());
		targetVO.setReleased(sourceEntity.isReleased());
	}

	/**
	 * @see org.openuss.news.NewsItemDao#toNewsItemInfo(org.openuss.news.NewsItem)
	 */
	public NewsItemInfo toNewsItemInfo(final NewsItem entity) {
		// @todo verify behavior of toNewsItemInfo
		return super.toNewsItemInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private NewsItem loadNewsItemFromNewsItemInfo(NewsItemInfo newsItemInfo) {
		Validate.notNull(newsItemInfo, "newsItemInfo must not be null.");

		NewsItem item = null;
		if (newsItemInfo.getId() != null) {
			item = this.load(newsItemInfo.getId());
		}
		if (item == null) {
			item = NewsItem.Factory.newInstance();
		}
		return item;
	}

	/**
	 * @see org.openuss.news.NewsItemDao#newsItemInfoToEntity(org.openuss.news.NewsItemInfo)
	 */
	public NewsItem newsItemInfoToEntity(NewsItemInfo newsItemInfo) {
		NewsItem entity = this.loadNewsItemFromNewsItemInfo(newsItemInfo);
		this.newsItemInfoToEntity(newsItemInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.news.NewsItemDao#newsItemInfoToEntity(org.openuss.news.NewsItemInfo,
	 *      org.openuss.news.NewsItem)
	 */
	public void newsItemInfoToEntity(NewsItemInfo sourceVO, NewsItem targetEntity,	boolean copyIfNull) {
		super.newsItemInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}