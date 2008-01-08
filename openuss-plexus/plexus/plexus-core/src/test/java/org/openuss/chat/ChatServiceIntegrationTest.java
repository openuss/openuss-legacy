// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.chat;

import java.util.List;

import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;

/**
 * JUnit Test for Spring Hibernate ChatService class.
 * 
 * @see org.openuss.chat.ChatService
 */
public class ChatServiceIntegrationTest extends ChatServiceIntegrationTestBase {

	/** default ChatRoomInfo rooom */
	private ChatRoomInfo room;

	/** default DomainObject that belongs to the rooms */
	private DomainObject domainObject;

	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		testUtility.createUserSecureContext();
		domainObject = new DefaultDomainObject(1L);
		room = chatService.createRoom(domainObject, "name", "topic");
	}

	public void testCreateAndDeleteRoom() {
		assertNotNull("room is null", room);
		assertNotNull("room id isn't set", roomId());
		assertEquals("domain id isn't set", Long.valueOf(1L), room.getDomainId());
		assertEquals("name isn't correct", "name", room.getName());
		assertEquals("topic isn't correct", "topic", room.getTopic());
		assertNotNull(room.getCreated());
		flush();
		try {
			chatService.deleteRoom(roomId());
		} catch (ChatRoomServiceException e) {
			fail(e.getMessage());
		}
		flush();
		assertNull(chatService.getRoom(roomId()));
	}
	
	public void testGetRooms() {
		chatService.createRoom(domainObject, "name", "topic");
		assertEquals("Number of Rooms",2,chatService.getRooms(domainObject).size());
	}

	public void testEnterAndLeaveRoom() {
		List<ChatUserInfo> users = chatService.getChatUsers(roomId());
		assertTrue(users.isEmpty());
		chatService.enterRoom(roomId());
		users = chatService.getChatUsers(roomId());
		assertEquals(1, users.size());
		chatService.leaveRoom(roomId());
		users = chatService.getChatUsers(roomId());
		assertTrue("chatusers isn't empty", users.isEmpty());
		assertEquals("chat room doesn't contain to enter / leave messages", 2, chatService.getMessages(roomId()).size());
	}

	public void testSendMessage() {
		chatService.sendMessage(roomId(), "The first test message");
		List<ChatMessageInfo> msgs = chatService.getMessages(roomId());
		assertTrue(1 == msgs.size());
		ChatMessageInfo msg = msgs.get(0);
		assertEquals("The first test message", msg.getText());
		assertEquals("title firstName lastName", msg.getDisplayName());
		assertNotNull("Time is not set.", msg.getTime());
		assertEquals("LastMessageId",msg.getId(), chatService.getLastMessage(roomId()));
	}
	
	public void testDoubleEntry() {
		chatService.enterRoom(roomId());
		chatService.enterRoom(roomId());
		ChatRoomInfo info = chatService.getRoom(roomId());
		assertEquals("Number of Users after multiple entry ",1,info.getOnlineUsers());
		chatService.leaveRoom(roomId());
		
		info = chatService.getRoom(roomId());
		assertEquals("Number of Users after multiple entry ",0,info.getOnlineUsers());
	}

	public void testRoomInformation() {
		chatService.enterRoom(roomId());
		ChatRoomInfo roomInfo = chatService.getRoom(roomId());
		assertEquals("number of online users", 1, roomInfo.getOnlineUsers());
		assertEquals("number of messages", 1, roomInfo.getMessages());
		chatService.sendMessage(roomId(), "text");
		chatService.leaveRoom(roomId());

		roomInfo = chatService.getRoom(roomId());
		assertEquals("number of online users", 0, roomInfo.getOnlineUsers());
		assertEquals("number of messages", 3, roomInfo.getMessages());
	}
	
	private Long roomId() {
		return room.getId();
	}

}