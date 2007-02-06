// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.news;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @see org.openuss.news.NewsItem
 */
public class NewsItemDaoImpl extends org.openuss.news.NewsItemDaoBase {

	@Override
	public List findByCriteria(int transform, NewsCriteria criteria) {

		Session sess = this.getSession(false);

		Criteria crit = sess.createCriteria(NewsItem.class);

		crit.createAlias("publisher", "pb");
		crit.createAlias("category", "cat");
		Conjunction conjunction = Restrictions.conjunction();
		if (criteria.getCategoryId() != null)
			conjunction.add(Restrictions.eq("cat.id", criteria.getCategoryId()));
		if (criteria.getPublisherForeignId() != null)
			conjunction.add(Restrictions.eq("pb.foreignId", criteria.getPublisherForeignId()));
		if (criteria.getPublisherClass() != null)
			conjunction.add(Restrictions.eq("pb.foreignClass", criteria.getPublisherClass()));
		if (criteria.getPublishDate() != null)
			conjunction.add(Restrictions.le("publishDate", criteria.getPublishDate()));
		if (criteria.getExpireDate() != null) {
			conjunction.add(
						Restrictions.or(
								Restrictions.ge("expireDate", criteria.getExpireDate()), 
								Restrictions.isNull("expireDate")));
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
}
