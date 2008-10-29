// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * @see org.openuss.security.Membership
 */
public class MembershipDaoImpl
    extends org.openuss.security.MembershipDaoBase
{

	@Override
	protected List handleFindByAspirant(final User aspirant) throws Exception {
		final String queryString = 
			" SELECT DISTINCT(membership.ID) " +
			" FROM SECURITY_MEMBERSHIP as membership, SECURITY_ASPIRANT_MEMBERSHIP as members " +
			" WHERE members.ASPIRANTS_FK = :userId AND membership.ID = members.MEMBERSHIP_ASPIRANTS_FK";
			return (List) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					Query queryObject = session.createSQLQuery(queryString);
					queryObject.setParameter("userId", aspirant.getId());
					return queryObject.list();
				}
			}, true);
	}

	@Override
	protected List handleFindByMember(final User user) throws Exception {
		final String queryString = 
		" SELECT DISTINCT(membership.ID) " +
		" FROM SECURITY_MEMBERSHIP as membership, SECURITY_MEMBER_MEMBERSHIP as members " +
		" WHERE members.MEMBERS_FK = :userId AND membership.ID = members.MEMBERSHIP_MEMBERS_FK";
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.createSQLQuery(queryString);
				queryObject.setParameter("userId", user.getId());
				return queryObject.list();
			}
		}, true);
	}
	
}