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
		testUtility.createSecureContext();
		domainObject = new DefaultDomainObject(1L);
		room = chatService.createRoom(domainObject, "name", "topic");
	}

	public void testCreateAndDeleteRoom() {
		assertNotNull("room is null",room);
		assertNotNull("room id isn't set", roomId());
		assertEquals("domain id isn't set", new Long(1), room.getDomainId());
		assertEquals("name isn't correct","name", room.getName());
		assertEquals("topic isn't correct", "topic", room.getTopic());
		assertNotNull(room.getCreated());
		flush();
		chatService.deleteRoom(roomId());
		flush();
		assertNull(chatService.getRoom(roomId()));
	}
	
	public void testEnterAndLeaveRoom() {
		List<ChatUserInfo> users = chatService.getChatUsers(roomId());
		assertTrue(users.isEmpty());
		chatService.enterRoom(roomId());
		users = chatService.getChatUsers(roomId());
		assertEquals(1, users.size());
		chatService.leaveRoom(roomId());
		users = chatService.getChatUsers(roomId());
		assertTrue("chatusers isn't empty",users.isEmpty());
	}
	
	public void testSendMessage() {
		chatService.sendMessage(roomId(), "The first test message");
		List<ChatMessageInfo> msgs = chatService.getMessages(roomId());
		assertTrue(1 == msgs.size());
		ChatMessageInfo msg = msgs.get(0);
		assertEquals("The first test message",msg.getText());
		assertEquals("title firstName lastName",msg.getDisplayName());
		assertNotNull("Time is not set.",msg.getTime());
	}
	

	private Long roomId() {
		return room.getId();
	}
	
}