// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.internalMessage;

import java.util.*;

import org.acegisecurity.acl.AclManager;
import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.security.*;

/**
 * JUnit Test for Spring Hibernate InternalMessageService class.
 * @see org.openuss.internalMessage.InternalMessageService
 */
public class InternalMessageServiceIntegrationTest extends InternalMessageServiceIntegrationTestBase {
	
	private SecurityService securityService;
	private UserDao userDao;
	private InternalMessageDao internalMessageDao;
	private MessageStatusDao messageStatusDao;
	private AclManager aclManager;
	
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
		AcegiUtils.setAclManager(aclManager);
		testUtility.createUserSecureContext();
		super.onSetUpInTransaction();
	}
	
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void testInternalMessages(){
		//create two users
		User sender = getSecurityService().getCurrentUser();
		User recipient = getUserDao().create("user2", "asdf", "asdf@asdf.com", true, false, false, false, new Date());
		recipient.setFirstName("firstname");
		getUserDao().update(recipient);
		assertEquals(0, getInternalMessageService().getAllSentInternalMessages().size());
		assertEquals(0, getInternalMessageService().getAllReceivedInternalMessages().size());
		//create message
		InternalMessageInfo im = new InternalMessageInfo();
		im.setContent("content");
		im.setSubject("subject");
		im.setMessageDate(new Date());
		im.setSenderDisplayName(sender.getDisplayName());
		im.setSenderId(sender.getId());
		im.setInternalMessageRecipientsInfos(new LinkedList<InternalMessageRecipientsInfo>());
		InternalMessageRecipientsInfo rec = new InternalMessageRecipientsInfo();
		rec.setRead(false);
		rec.setRecipientDisplayName(recipient.getDisplayName());
		rec.setRecipientId(recipient.getId());
		im.getInternalMessageRecipientsInfos().add(rec);
		internalMessageService.sendInternalMessage(im);
		assertEquals(1, internalMessageService.getAllSentInternalMessages().size());
//		System.out.println(((InternalMessageInfo)internalMessageService.getAllSentInternalMessages().get(0)).getInternalMessageRecipientsInfos().get(0).getRecipientDisplayName());
//		assertEquals(" firstname ", ((InternalMessageInfo)internalMessageService.getAllSentInternalMessages().get(0)).getInternalMessageRecipientsInfos().get(0).getRecipientDisplayName());
//		assertEquals(1, internalMessageService.getAllReceivedInternalMessages(recipient).size());
//		InternalMessageInfo message2 = messageDao.toInternalMessageInfo((InternalMessage)(internalMessageService.getAllReceivedInternalMessages(recipient).get(0)));
//		assertEquals(false, message2.isMessageReadByRecipient());
//		//mark message as read
//		internalMessageService.setRead(message2);
//		message2 = (InternalMessageInfo)messageDao.load(messageDao.TRANSFORM_INTERNALMESSAGEINFO, message2.getId());
//		assertEquals(true, message2.isMessageReadByRecipient());
//		//recipient: delete message
//		internalMessageService.deleteInternalMessage(message2, false);
//		assertEquals(1, internalMessageService.getAllInternalMessages(sender).size());
//		assertEquals(0, internalMessageService.getAllReceivedInternalMessages(recipient).size());
//		assertEquals(recipient.getId().longValue(), ((InternalMessageInfo)messageDao.load(messageDao.TRANSFORM_INTERNALMESSAGEINFO, message2.getId())).getRecipientId());
//		//sender: delete message
//		internalMessageService.deleteInternalMessage(message2, true);
//		assertEquals(0, internalMessageService.getAllReceivedInternalMessages(recipient).size());
//		assertEquals(0, internalMessageService.getAllInternalMessages(sender).size());
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public InternalMessageDao getInternalMessageDao() {
		return internalMessageDao;
	}

	public void setInternalMessageDao(InternalMessageDao internalMessageDao) {
		this.internalMessageDao = internalMessageDao;
	}

	public MessageStatusDao getMessageStatusDao() {
		return messageStatusDao;
	}

	public void setMessageStatusDao(MessageStatusDao messageStatusDao) {
		this.messageStatusDao = messageStatusDao;
	}

}