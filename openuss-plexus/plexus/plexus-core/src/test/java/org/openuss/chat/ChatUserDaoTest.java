// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.chat;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate ChatUserDao class.
 * @see org.openuss.chat.ChatUserDao
 */
public class ChatUserDaoTest extends ChatUserDaoTestBase {
	
	private TestUtility testUtility;
	
	public void testChatUserDaoCreate() {
		ChatUser chatUser = new ChatUserImpl();
		chatUser.setId(TestUtility.unique());
		chatUser.setDisplayName("DisplayName");
		chatUser.setEmail("openuss@openuss.de");
		chatUserDao.create(chatUser);
		flush();
	}
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
}
