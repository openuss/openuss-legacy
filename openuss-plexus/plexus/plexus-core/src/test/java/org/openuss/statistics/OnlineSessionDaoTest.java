// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.statistics;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.openuss.TestUtility;
import org.openuss.security.User;


/**
 * JUnit Test for Spring Hibernate UserSessionDao class.
 * @see org.openuss.statistics.OnlineSessionDao
 */
public class OnlineSessionDaoTest extends OnlineSessionDaoTestBase {
	
	private TestUtility testUtility;
	
	private static final Date NOW = new Date();
	private static final Date PAST30 = DateUtils.addMinutes(NOW, -30);
	private static final Date PAST45 = DateUtils.addMinutes(NOW, -45);
	private static final Date PAST65 = DateUtils.addMinutes(NOW, -65);

	
	public void testOnlineInfo() {
		OnlineInfo info = getOnlineSessionDao().loadOnlineInfo();
		assertTrue(0 == info.getUsers());
		assertTrue(0 == info.getTotal());
		
		OnlineSession guest = createUserSession(RandomStringUtils.random(30),PAST30, null,null);
		OnlineSession user1 = createUserSession(RandomStringUtils.random(30), PAST30, null, testUtility.createUniqueUserInDB());
		OnlineSession user2 = createUserSession(RandomStringUtils.random(30), PAST45, null, testUtility.createUniqueUserInDB());
		createUserSession(RandomStringUtils.random(30), PAST65, null, testUtility.createUniqueUserInDB());
		
		info = getOnlineSessionDao().loadOnlineInfo();
		assertEquals("Number of Users", Long.valueOf(2), info.getUsers());
		assertEquals("Number of Total", Long.valueOf(3), info.getTotal());
		
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
		OnlineSession session = createUserSession(RandomStringUtils.random(30),PAST30, null, testUtility.createUniqueUserInDB());
		
		List<OnlineSession> sessions = onlineSessionDao.findActiveSessionByUser(session.getUser());
		assertNotNull(sessions);
		assertTrue(sessions.contains(session));
		
		session.setEndTime(NOW);
		onlineSessionDao.update(session);
		
		assertTrue(onlineSessionDao.findActiveSessionByUser(session.getUser()).isEmpty());
	}

	private OnlineSession createUserSession(String sessionId, Date start, Date end, User userOne) {
		OnlineSession session = new OnlineSessionImpl();
		session.setStartTime(start);
		session.setEndTime(end);
		session.setUser(userOne);
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