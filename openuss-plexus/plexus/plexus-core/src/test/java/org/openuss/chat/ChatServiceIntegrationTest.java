// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.chat;

import org.openuss.foundation.DefaultDomainObject;


/**
 * JUnit Test for Spring Hibernate ChatService class.
 * @see org.openuss.chat.ChatService
 */
public class ChatServiceIntegrationTest extends ChatServiceIntegrationTestBase {
	
	public void testCreateRoom() {
		testUtility.createSecureContext();
		ChatRoomInfo room = chatService.createRoom(new DefaultDomainObject(1L), "name", "topic");
		assertNotNull("room is null",room);
		assertNotNull("room id isn't set", room.getId());
		assertTrue("domain id isn't set", 1L == room.getDomainId());
		assertEquals("name isn't correct","name", room.getName());
		assertEquals("topic isn't correct", "topic", room.getTopic());
		assertNotNull(room.getCreated());
		flush();
		
	}
	
}