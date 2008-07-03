// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.statistics;

import java.util.List;

import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate OnlineStatisticService class.
 * @see org.openuss.statistics.OnlineStatisticService
 */
public class OnlineStatisticServiceIntegrationTest extends OnlineStatisticServiceIntegrationTestBase {
	
	public void testUserSession() {
		testUtility.createUserSecureContext();
		
		assertTrue(0 == onlineStatisticService.getOnlineInfo().getTotal());
		Long sessionId = onlineStatisticService.logSessionStart(null);
		assertTrue(1 == onlineStatisticService.getOnlineInfo().getTotal());
		List<OnlineUserInfo> onlineUsers = onlineStatisticService.getActiveUsers();
		assertEquals(1, onlineUsers.size());
		onlineStatisticService.logSessionStart(sessionId);
		assertTrue(1 == onlineStatisticService.getOnlineInfo().getTotal());
		onlineStatisticService.logSessionEnd(sessionId);
		assertTrue(0 == onlineStatisticService.getOnlineInfo().getTotal());
	}
	
	public void testRemoveUserSessions(){
		User user = testUtility.createUserSecureContext();
		assertTrue(0 == onlineStatisticService.getOnlineInfo().getTotal());
		Long sessionId = onlineStatisticService.logSessionStart(null);
		assertTrue(1 == onlineStatisticService.getOnlineInfo().getTotal());
		List<OnlineUserInfo> onlineUsers = onlineStatisticService.getActiveUsers();
		assertEquals(1, onlineUsers.size());
		onlineStatisticService.logSessionStart(sessionId);
		assertTrue(1 == onlineStatisticService.getOnlineInfo().getTotal());
		onlineStatisticService.logSessionEnd(sessionId);
		assertTrue(0 == onlineStatisticService.getOnlineInfo().getTotal());
		
		onlineStatisticService.removeUserSessions(user);
	}
}