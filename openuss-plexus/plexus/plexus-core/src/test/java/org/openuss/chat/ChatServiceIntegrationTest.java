// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.chat;

import java.util.Date;
import java.util.List;

import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate ChatService class.
 * @see org.openuss.chat.ChatService
 */
public class ChatServiceIntegrationTest extends ChatServiceIntegrationTestBase {
	public void testRoom(){
		User user = testUtility.createAdminSecureContext();
		Long domainId = new Long(1);
		ChatRoomInfo room = chatService.getRoom(domainId);
		assertNotNull(room);
		List<ChatUserInfo> users = chatService.getChatUsers(domainId);
		assertEquals(0, users.size());
		assertEquals(" ", room.getCurrentTopic());
		room.setCurrentTopic("1");
		chatService.setRoom(room);
		room = chatService.getRoom(domainId);
		assertEquals("1", room.getCurrentTopic());
	}
	
	
	public void testLoginLogout(){
		User user = testUtility.createAdminSecureContext();
		Long domainId = new Long(1);
		ChatRoomInfo room = chatService.getRoom(domainId);
		assertNotNull(room);
		List<ChatUserInfo> users = chatService.getChatUsers(domainId);
		assertEquals(0, users.size());
		chatService.login(domainId);
		users = chatService.getChatUsers(domainId);
		assertEquals(1, users.size());
		chatService.logout(domainId);
		users = chatService.getChatUsers(domainId);
		assertEquals(0, users.size());		
	}
	
	public void testSendMessage(){
		User user = testUtility.createAdminSecureContext();
		Long domainId = new Long(1);
		ChatRoomInfo room = chatService.getRoom(domainId);
		assertNotNull(room);
		List<ChatUserInfo> users = chatService.getChatUsers(domainId);
		assertEquals(0, users.size());
		chatService.login(domainId);
		users = chatService.getChatUsers(domainId);
		assertEquals(1, users.size());
		
		ChatMessageInfo message = new ChatMessageInfo();
		message.setMessage("TestNachricht");
		message.setTime(new Date(System.currentTimeMillis()));
		message.setUsername(user.getUsername());
		
		chatService.sendMessage(message, domainId);
		
		List<ChatMessageInfo> messages = chatService.getMessages(domainId, new Long(0));
		assertNotNull(messages);
		assertEquals(1, messages.size());
		
		chatService.update();
		messages = chatService.getMessages(domainId, new Long(0));
		assertNotNull(messages);
		assertEquals(1, messages.size());
		users = chatService.getChatUsers(domainId);
		assertEquals(1, users.size());
	}
	
}