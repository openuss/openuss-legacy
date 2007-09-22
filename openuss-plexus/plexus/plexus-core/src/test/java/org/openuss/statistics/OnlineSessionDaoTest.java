// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.statistics;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.openuss.TestUtility;
import org.openuss.security.User;


/**
 * JUnit Test for Spring Hibernate UserSessionDao class.
 * @see org.openuss.statistics.OnlineSessionDao
 */
public class OnlineSessionDaoTest extends OnlineSessionDaoTestBase {
	
	private TestUtility testUtility;
	
	private static final Date PAST = new Date(System.currentTimeMillis()-1000000);
	private static final Date NOW = new Date(System.currentTimeMillis());

	
	public void testOnlineInfo() {
		OnlineInfo info = getOnlineSessionDao().loadOnlineInfo();
		assertTrue(0 == info.getUsers());
		assertTrue(0 == info.getTotal());
		
		OnlineSession guest = createUserSession(RandomStringUtils.random(30),PAST, null,null);
		OnlineSession user1 = createUserSession(RandomStringUtils.random(30), PAST, null, testUtility.createUserInDB());
		OnlineSession user2 = createUserSession(RandomStringUtils.random(30), PAST, null, testUtility.createUserInDB());
		
		info = getOnlineSessionDao().loadOnlineInfo();
		assertTrue(2 == info.getUsers());
		assertTrue(3 == info.getTotal());
		
		guest.setEndTime(NOW);
		user1.setEndTime(NOW);
		user2.setEndTime(NOW);
		onlineSessionDao.update(guest);
		onlineSessionDao.update(user1);
		onlineSessionDao.update(user2);
		
		info = getOnlineSessionDao().loadOnlineInfo();
		assertTrue(0 == info.getUsers());
		assertTrue(0 == info.getTotal());
	}
	
	public void testFindActiveSessionByUser() {
		OnlineSession session = createUserSession(RandomStringUtils.random(30),PAST, null, testUtility.createUserInDB());
		
		List<OnlineSession> sessions = onlineSessionDao.findActiveSessionByUser(session.getUser());
		assertNotNull(sessions);
		assertTrue(sessions.contains(session));
		
		session.setEndTime(NOW);
		onlineSessionDao.update(session);
		
		assertTrue(onlineSessionDao.findActiveSessionByUser(session.getUser()).isEmpty());
	}

	private OnlineSession createUserSession(String sessionId, Date start, Date end, User userOne) {
		OnlineSession session = OnlineSession.Factory.newInstance(start, end, userOne);
		assertNull(session.getId());
		onlineSessionDao.create(session);
		assertNotNull(session.getId());
		return session;
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
}