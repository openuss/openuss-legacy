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
	
	private ChatUserDao chatUserDao;
	
	public void testChatRoomDaoCreate() {
		ChatRoom chatRoom = createChatRoom();
		chatUserDao.create(chatRoom.getOwner());
		chatRoomDao.create(chatRoom);
		flush();
		assertNotNull(chatRoom.getId());
	}

	public void testChatRoomDomainObject() {
		ChatRoom chatRoom = createChatRoom();
		chatUserDao.create(chatRoom.getOwner());
		chatRoomDao.create(chatRoom);
		flush();
		assertNotNull(chatRoom.getId());
		assertEquals("Numbers of rooms",1, chatRoomDao.findChatRoomByDomainId(chatRoom.getDomainId()).size());
	}

	private ChatRoom createChatRoom() {
		ChatRoom chatRoom = new ChatRoomImpl();
		chatRoom.setDomainId(TestUtility.unique());
		chatRoom.setName("Domain Name");
		chatRoom.setTopic("Current Topic");
		chatRoom.setCreated(new Date());
		chatRoom.setOwner( createChatUser());
		assertNull(chatRoom.getId());
		return chatRoom;
	}

	private ChatUser createChatUser() {
		ChatUser chatUser = new ChatUserImpl();
		chatUser.setDisplayName("user");
		chatUser.setEmail("test user");
		ChatUser user = chatUser;
		user.setId(TestUtility.unique());
		return user;
	}
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public ChatUserDao getChatUserDao() {
		return chatUserDao;
	}

	public void setChatUserDao(ChatUserDao chatUserDao) {
		this.chatUserDao = chatUserDao;
	}
}
