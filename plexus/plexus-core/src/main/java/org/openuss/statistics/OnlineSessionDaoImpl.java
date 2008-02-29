// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.statistics;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.openuss.discussion.TopicInfo;
import org.openuss.security.UserImpl;
import org.openuss.viewtracking.ViewState;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * @see org.openuss.statistics.OnlineSession
 */
public class OnlineSessionDaoImpl extends OnlineSessionDaoBase {
	
	@Override
	protected OnlineInfo handleLoadOnlineInfo() throws Exception {
		final Date time = DateUtils.addHours(new Date(), -1);
		final String hqlCount = "select count(*), (select count(*) from OnlineSessionImpl as o " +
				"where o.user is not null and o.endTime is null and o.startTime > :time) " +
				"from OnlineSessionImpl as s where s.endTime is null and s.startTime > :time";
		return (OnlineInfo) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hqlCount);
				query.setDate("time", time);
				Object[] result = (Object[]) query.uniqueResult();
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

	@Override
	protected List handleFindActiveUsers() throws Exception {
		final Date time = DateUtils.addHours(new Date(), -1);
		final String hqlCount = "select o from OnlineSessionImpl as o " +
				"where o.user is not null and o.endTime is null and o.startTime > :time) ";
		return (List) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hqlCount);
				query.setDate("time", time);
				List results = query.list();
				ArrayList<OnlineUserInfo> activeUsers = new ArrayList<OnlineUserInfo>();
				for  (Object result : results){
					activeUsers.add(toOnlineUserInfo((OnlineSessionImpl) result));
				}
				return activeUsers;
			}});
	}
}