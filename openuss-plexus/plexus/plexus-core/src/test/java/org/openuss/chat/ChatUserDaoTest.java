// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.chat;


/**
 * JUnit Test for Spring Hibernate ChatUserDao class.
 * @see org.openuss.chat.ChatUserDao
 */
public class ChatUserDaoTest extends ChatUserDaoTestBase {
	
	public void testChatUserDaoCreate() {
		ChatUser chatUser = ChatUser.Factory.newInstance();
		chatUser.setDisplayName("DisplayName");
		chatUser.setEmail("openuss@openuss.de");
		assertNull(chatUser.getId());
		chatUserDao.create(chatUser);
		assertNotNull(chatUser.getId());
	}
}
