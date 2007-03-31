// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.viewtracking;

import org.hibernate.HibernateException;

/**
 * @see org.openuss.viewtracking.DomainViewState
 */
public class DomainViewStateDaoImpl extends DomainViewStateDaoBase {
	/**
	 * @see org.openuss.viewtracking.DomainViewStateDao#updateAllToModified(java.lang.Long)
	 */
	protected void handleUpdateAllToModified(final Long domainIdentifier) {
		final String hqlUpdate = "update DomainViewStateImpl as v set v.viewState = 2 where v.domainViewStatePk.domainIdentifier = :domainIdentifier";
		getHibernateTemplate().execute(new org.springframework.orm.hibernate3.HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session session) throws HibernateException {
				int count = session.createQuery(hqlUpdate)
				.setLong("domainIdentifier", domainIdentifier)
				.executeUpdate();
				logger.debug("updated "+count+" records.");
				return count;
			}
		}, true);
	}

	/**
	 * @see org.openuss.viewtracking.DomainViewStateDao#removeAllByDomain(java.lang.Long)
	 */
	protected void handleRemoveAllByDomain(final Long domainIdentifier) {
		final String hqlDelete = "delete DomainViewStateImpl v where v.domainViewStatePk.domainIdentifier = :domainIdentifier";
		getHibernateTemplate().execute(new org.springframework.orm.hibernate3.HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session session) throws HibernateException {
				int count = session.createQuery(hqlDelete)
					.setLong("domainIdentifier", domainIdentifier)
					.executeUpdate();
				
				logger.debug("deleted "+count+" records.");
				return count;
			}
		}, true);

	}
}