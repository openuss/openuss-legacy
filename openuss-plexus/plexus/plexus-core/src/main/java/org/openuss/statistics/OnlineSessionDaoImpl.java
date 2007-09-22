// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.statistics;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * @see org.openuss.statistics.OnlineSession
 */
public class OnlineSessionDaoImpl extends OnlineSessionDaoBase {
	
	@Override
	protected OnlineInfo handleLoadOnlineInfo() throws Exception {
		final String hqlCount = "select count(*), (select count(*) from OnlineSessionImpl as o where o.user is not null and o.endTime is null) from OnlineSessionImpl as s where s.endTime is null";
		return (OnlineInfo) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Object[] result = (Object[]) session.createQuery(hqlCount).uniqueResult();
				return new OnlineInfo((Long)result[0],(Long)result[1]);
			}});
	}

	/**
	 * @see org.openuss.statistics.OnlineSessionDao#toOnlineUserInfo(org.openuss.statistics.OnlineSession,
	 *      org.openuss.statistics.OnlineUserInfo)
	 */
	public void toOnlineUserInfo(OnlineSession source, OnlineUserInfo target) {
		super.toOnlineUserInfo(source, target);
		if (source.getUser() != null) {
			target.setUserId(source.getUser().getId());
			target.setUsername(source.getUser().getUsername());
			target.setDisplayName(source.getUser().getDisplayName());
			target.setImageId(source.getUser().getImageId());
		}
	}

	public OnlineSession onlineUserInfoToEntity(OnlineUserInfo onlineUserInfo) {
		return null;
	}
}