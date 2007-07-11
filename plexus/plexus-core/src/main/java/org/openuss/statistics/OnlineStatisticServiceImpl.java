// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.statistics;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.openuss.security.User;

/**
 * @see org.openuss.statistics.OnlineStatisticService
 */
public class OnlineStatisticServiceImpl extends OnlineStatisticServiceBase {

	/**
	 * @see org.openuss.statistics.OnlineStatisticService#logSessionStart(java.lang.String,
	 *      java.util.Date)
	 */
	protected Long handleLogSessionStart(Long sessionId) throws Exception {
		User user = getSecurityService().getCurrentUser();
		OnlineSession session;
		
		if (sessionId == null) {
			session = OnlineSession.Factory.newInstance();
			session.setStartTime(new Date());
		} else {
			session = getOnlineSessionDao().load(sessionId);
		}

		if (user != null) {
			session.setUser(user);
			closeOtherActiveUserSessions(user, session);
		}
		if (session.getId() == null) {
			getOnlineSessionDao().create(session);
		} else {
			getOnlineSessionDao().update(session);
		}
		return session.getId();
	}

	private void closeOtherActiveUserSessions(User user, OnlineSession session) {
		List<OnlineSession> userSessions = getOnlineSessionDao().findActiveSessionByUser(user);
		Date now = new Date();
		for (OnlineSession userSession: userSessions) {
			if (!userSession.equals(session)) {
				userSession.setEndTime(now);
			}
		}
		getOnlineSessionDao().update(userSessions);
	}

	/**
	 * @see org.openuss.statistics.OnlineStatisticService#logSessionEnd(java.lang.String)
	 */
	protected void handleLogSessionEnd(Long sessionId) throws Exception {
		Validate.notNull(sessionId, "Parameter sessionId must not be null!");
		OnlineSession session = getOnlineSessionDao().load(sessionId);
		if (session != null) {
			session.setUser(getSecurityService().getCurrentUser());
			session.setEndTime(new Date());
			getOnlineSessionDao().update(session);
		}
	}

	/**
	 * @see org.openuss.statistics.OnlineStatisticService#getOnlineInfo()
	 */
	protected OnlineInfo handleGetOnlineInfo() throws Exception {
		return getOnlineSessionDao().loadOnlineInfo();
	}

	/**
	 * @see org.openuss.statistics.OnlineStatisticService#getActiveUsers()
	 */
	protected List handleGetActiveUsers() throws Exception {
		return getOnlineSessionDao().findActiveUsers(OnlineSessionDao.TRANSFORM_ONLINEUSERINFO);
	}

	@Override
	protected SystemStatisticInfo handleGetSystemStatistics() throws Exception {
		SystemStatisticInfo sysInfo = new SystemStatisticInfo();
		sysInfo = getSystemStatisticDao().toSystemStatisticInfo(getSystemStatisticDao().findNewest());
		if ((sysInfo==null)){			
			sysInfo = getSystemStatisticDao().toSystemStatisticInfo(getSystemStatisticDao().current());
		}
		return sysInfo;
	}

}