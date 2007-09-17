// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.chat;

import java.util.Date;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate ChatRoomDao class.
 * @see org.openuss.chat.ChatRoomDao
 */
public class ChatRoomDaoTest extends ChatRoomDaoTestBase {
	
	private TestUtility testUtility;
	
	public void testChatRoomDaoCreate() {
		ChatRoom chatRoom = ChatRoom.Factory.newInstance();
		chatRoom.setDomainId(testUtility.unique());
		chatRoom.setName("Domain Name");
		chatRoom.setTopic("Current Topic");
		chatRoom.setCreated(new Date());
		chatRoom.setOwner( ChatUser.Factory.newInstance("user", "test user"));
		assertNull(chatRoom.getId());
		chatRoomDao.create(chatRoom);
		flush();
		assertNotNull(chatRoom.getId());
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
}
