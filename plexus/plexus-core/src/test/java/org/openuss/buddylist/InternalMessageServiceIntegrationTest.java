// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.buddylist;

import java.util.Date;
import org.openuss.security.*;

/**
 * JUnit Test for Spring Hibernate InternalMessageService class.
 * @see org.openuss.buddylist.InternalMessageService
 */
public class InternalMessageServiceIntegrationTest extends InternalMessageServiceIntegrationTestBase {
	private UserDao userDao;
	
	private InternalMessageDao messageDao;
	
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
	
	public void setMessageDao(InternalMessageDao messageDao){
		this.messageDao = messageDao;
	}
	
	public void testInternalMessages(){
		//create two users
		User sender = userDao.create("user1", "asdf", "asdf@asdf.com", true, false, false, false, new Date());
		User recipient = userDao.create("user2", "asdf", "asdf@asdf.com", true, false, false, false, new Date());
		//create message
		InternalMessageInfo message = (InternalMessageInfo)messageDao.create(messageDao.TRANSFORM_INTERNALMESSAGEINFO, "content", new Date(), false, recipient, sender, "subject");
		message.setSender(userDao.toUserInfo(sender));
		message.setRecipient(userDao.toUserInfo(recipient));
		internalMessageService.sendInternalMessage(message);
		assertEquals(1, internalMessageService.getAllInternalMessages(sender).size());
		assertEquals(1, internalMessageService.getAllReceivedInternalMessages(recipient).size());
		InternalMessageInfo message2 = messageDao.toInternalMessageInfo((InternalMessage)(internalMessageService.getAllReceivedInternalMessages(recipient).get(0)));
		assertEquals(false, message2.isMessageRead());
		//mark message as read
		internalMessageService.setRead(message);
		assertEquals(false, message.isMessageRead());
		//receiver: delete message
		
		//sender: delete message
		
	}
}