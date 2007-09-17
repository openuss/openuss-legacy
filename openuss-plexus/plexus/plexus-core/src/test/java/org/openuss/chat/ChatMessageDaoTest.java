// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.chat;

import java.util.Date;
import java.util.List;

import org.openuss.TestUtility;

/**
 * JUnit Test for Spring Hibernate ChatMessageDao class.
 * @see org.openuss.chat.ChatMessageDao
 */
public class ChatMessageDaoTest extends ChatMessageDaoTestBase {
	
	private TestUtility testUtility;

	private ChatUserDao chatUserDao;
	
	private ChatRoomDao chatRoomDao;

	private ChatUser chatUser;

	private ChatRoom chatRoom;
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();

		chatUser = ChatUser.Factory.newInstance("user", "test user");
		chatUser.setId(testUtility.unique());
		chatUserDao.create(chatUser);
		
		chatRoom = createChatRoom();
	}

	private ChatRoom createChatRoom() {
		ChatRoom room = ChatRoom.Factory.newInstance(testUtility.unique(),"name","topic",new Date(), chatUser);
		chatRoomDao.create(room);
		return room;
	}

	public void testChatMessageDaoCreate() {
		ChatMessage chatMessage = createMessage(chatUser, chatRoom);

		assertNull(chatMessage.getId());
		chatMessageDao.create(chatMessage);
		flush();
		assertNotNull(chatMessage.getId());
	}

	private ChatMessage createMessage(ChatUser user, ChatRoom room) {
		ChatMessage chatMessage = ChatMessage.Factory.newInstance();
		chatMessage.setText("Message Text");
		chatMessage.setTime(new Date());
		chatMessage.setSender(user);
		chatMessage.setRoom(room);
		return chatMessage;
	}
	
	public void testFinder() {
		ChatRoom chatRoom2 = createChatRoom();
		ChatMessage msg1 = createMessage(chatUser, chatRoom);
		ChatMessage msg2 = createMessage(chatUser, chatRoom);
		ChatMessage msg3 = createMessage(chatUser, chatRoom2);
		ChatMessage msg4 = createMessage(chatUser, chatRoom);
		
		chatMessageDao.create(msg1);
		chatMessageDao.create(msg2);
		chatMessageDao.create(msg3);
		chatMessageDao.create(msg4);
		
		flush();
		List<ChatMessage> msgs = chatMessageDao.findByRoom(chatRoom.getId());
		
		assertEquals(3, msgs.size());
		assertTrue(msgs.contains(msg1));
		assertTrue(msgs.contains(msg2));
		assertFalse(msgs.contains(msg3));
		assertTrue(msgs.contains(msg4));
		
		List<ChatMessage> recently = chatMessageDao.findByRoomAndAfter(chatRoom.getId(), msg2.getId());
		assertEquals(1, recently.size());
		assertTrue(msgs.contains(msg4));
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

	public ChatRoomDao getChatRoomDao() {
		return chatRoomDao;
	}

	public void setChatRoomDao(ChatRoomDao chatRoomDao) {
		this.chatRoomDao = chatRoomDao;
	}

}