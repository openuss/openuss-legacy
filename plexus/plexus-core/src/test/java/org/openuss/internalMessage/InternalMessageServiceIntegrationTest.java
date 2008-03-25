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
		UserInfo sender = getSecurityService().getCurrentUser();
		User recipient = testUtility.createUniqueUserInDB();
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