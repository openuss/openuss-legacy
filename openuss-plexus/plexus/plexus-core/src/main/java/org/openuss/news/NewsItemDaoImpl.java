// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.news;

import static org.hibernate.criterion.Restrictions.conjunction;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.ge;
import static org.hibernate.criterion.Restrictions.isNull;
import static org.hibernate.criterion.Restrictions.le;
import static org.hibernate.criterion.Restrictions.or;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;

/**
 * @author ingo dueppe
 * @see org.openuss.news.NewsItem
 */
public class NewsItemDaoImpl extends NewsItemDaoBase {

	@Override
	public List findByCriteria(int transform, NewsCriteria criteria) {

		Session sess = this.getSession(false);

		Criteria crit = sess.createCriteria(NewsItem.class);

		Conjunction conjunction = conjunction();
		if (criteria.getCategory() != null) {
			conjunction.add(eq("category", criteria.getCategory()));
		}
		if (criteria.getPublisherIdentifier() != null) {
			conjunction.add(eq("publisherIdentifier", criteria.getPublisherIdentifier()));
		}
		if (criteria.getPublishDate() != null) {
			conjunction.add(le("publishDate", criteria.getPublishDate()));
		}
		if (criteria.getExpireDate() != null) {
			conjunction.add(or(ge("expireDate", criteria.getExpireDate()), isNull("expireDate")));
		}
		crit.add(conjunction);

		if (criteria.getFetchSize() != null)
			crit.setFetchSize(criteria.getFetchSize());
		if (criteria.getFirstResult() != null)
			crit.setFetchSize(criteria.getFirstResult());
		if (criteria.getMaximumResultSize() != null)
			crit.setFetchSize(criteria.getMaximumResultSize());

		crit.addOrder(Order.desc("publishDate"));
		List results = crit.list();
		transformEntities(transform, results);
		return results;
	}

	@Override
	public List findByCriteria(NewsCriteria criteria) {
		return findByCriteria(TRANSFORM_NONE, criteria);
	}

	/**
	 * @see org.openuss.news.NewsItemDao#countByCategory(org.openuss.news.NewsCategory)
	 */
	protected long handleCountByCategory(final NewsCategory category) {
		final String hqlUpdate = "select count(n) from NewsItemImpl n where n.category = :category";
		return (Long) getHibernateTemplate().execute(new org.springframework.orm.hibernate3.HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session session) throws HibernateException {
				Long count = (Long) session.createQuery(hqlUpdate).setParameter("category", category).uniqueResult();
				logger.debug("updated " + count + " records.");
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
				Long count = (Long) session.createQuery(hqlUpdate).setLong("publisherIdentifier", publisherIdentifier)
						.uniqueResult();
				logger.debug("updated " + count + " records.");
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
	public void newsItemInfoToEntity(NewsItemInfo sourceVO, NewsItem targetEntity, boolean copyIfNull) {
		super.newsItemInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}