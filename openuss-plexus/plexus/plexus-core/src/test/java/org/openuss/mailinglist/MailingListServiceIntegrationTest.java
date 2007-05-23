// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mailinglist;

import java.util.Date;
import java.util.List;

import org.acegisecurity.acl.AclManager;
import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;

/**
 * JUnit Test for Spring Hibernate MailingListService class.
 * @see org.openuss.mail.MailingListService
 */
public class MailingListServiceIntegrationTest extends MailingListServiceIntegrationTestBase {
	
	public SecurityService securityService;
	
	public TestUtility testUtilty;
	
	private AclManager aclManager;

	private MailingListInfo assist;
	
	private MailingListInfo noAssist;

	private User user;
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		AcegiUtils.setAclManager(aclManager);
		user = testUtility.createSecureContext();
		DomainObject assistDO = generateDOAssist(user);
		getMailingListService().addMailingList(assistDO, "assistML"); 
		assist = getMailingListService().getMailingList(assistDO);
		DomainObject noAssistDO = generateDomainObject();
		getMailingListService().addMailingList(noAssistDO, "noAssistML"); 
		noAssist = getMailingListService().getMailingList(noAssistDO);
	}
	
	
	public void testMailingListSubscription(){
		DomainObject domainObject = generateDomainObject();
		getMailingListService().addMailingList(domainObject, "testName");		
		MailingListInfo mailingList = getMailingListService().getMailingList(domainObject);
		User user = testUtility.createDefaultUserInDB();
		commit();
		//init list of subscribers has to be empty
		List subscribers = getMailingListService().getSubscribers(mailingList);
		assertEquals(0, subscribers.size());
		//add user to mailinglist
		getMailingListService().subscribe(mailingList, user);
		subscribers = getMailingListService().getSubscribers(mailingList);
		assertEquals(1, subscribers.size());
		SubscriberInfo si = (SubscriberInfo) subscribers.get(0);
		assertEquals(false, si.isBlocked());
		assertEquals(user.getDisplayName(), si.getDisplayName());
		assertEquals(user.getEmail(), si.getEmail());
		//delete user from mailingList using first unsubscribe method
		getMailingListService().unsubscribe(si);
		subscribers = getMailingListService().getSubscribers(mailingList);
		assertEquals(0, subscribers.size());
		//add user to mailingList
		getMailingListService().subscribe(mailingList, user);
		subscribers = getMailingListService().getSubscribers(mailingList);
		assertEquals(1, subscribers.size());
		si = (SubscriberInfo) subscribers.get(0);
		assertEquals(false, si.isBlocked());
		assertEquals(user.getDisplayName(), si.getDisplayName());
		assertEquals(user.getEmail(), si.getEmail());
		//delete user from mailingList using second unsubscribe method
		getMailingListService().unsubscribe(mailingList, user);
		subscribers = getMailingListService().getSubscribers(mailingList);
		assertEquals(0, subscribers.size());
		//test unsubscribe methods if user not in mailinglist
		getMailingListService().unsubscribe(mailingList, user);
		getMailingListService().unsubscribe(si);
		subscribers = getMailingListService().getSubscribers(mailingList);
		assertEquals(0, subscribers.size());
	}
	
	public void testSetBlockingState(){
		DomainObject domainObject = generateDomainObject();
		getMailingListService().addMailingList(domainObject, "testName");		
		MailingListInfo mailingList = getMailingListService().getMailingList(domainObject);
		User user = testUtility.createDefaultUserInDB();
		commit();
		//init list of subscribers has to be empty
		List subscribers = getMailingListService().getSubscribers(mailingList);
		assertEquals(0, subscribers.size());
		//add user to mailinglist
		getMailingListService().subscribe(mailingList, user);
		subscribers = getMailingListService().getSubscribers(mailingList);
		assertEquals(1, subscribers.size());
		SubscriberInfo si = (SubscriberInfo) subscribers.get(0);
		assertEquals(false, si.isBlocked());
		assertEquals(user.getDisplayName(), si.getDisplayName());
		assertEquals(user.getEmail(), si.getEmail());
		//set Blocking State of User to true		 
		si.setBlocked(true);
		getMailingListService().setBlockingState(si);
		subscribers = getMailingListService().getSubscribers(mailingList);
		assertEquals(1, subscribers.size());
		si = (SubscriberInfo) subscribers.get(0);
		assertEquals(true, si.isBlocked());
		//set user to unblocked
		si.setBlocked(false);
		getMailingListService().setBlockingState(si);
		subscribers = getMailingListService().getSubscribers(mailingList);
		assertEquals(1, subscribers.size());
		si = (SubscriberInfo) subscribers.get(0);
		assertEquals(false, si.isBlocked());
	}

	private MailDetail generateMailToSend(){
		MailDetail md = new MailDetail();
		md.setSendDate(new Date(System.currentTimeMillis()+100000));
		md.setSubject("TestSubject");
		md.setText("TestText");
		return md;
	}
	
	
	
	public void testGetMails(){
		MailDetail mail = generateMailToSend();		
		getMailingListService().saveMail(assist, mail);
		commit();
		List mails = getMailingListService().getMails(assist, false);
		assertNotNull(mails);
		assertEquals(1, mails.size());
		MailDetail md = getMailingListService().getMail((MailInfo)mails.get(0));
		assertEquals(MailingStatus.DRAFT, md.getStatus());
		assertEquals(mail.getSubject(), md.getSubject());
		assertEquals(mail.getText(), md.getText());
		assertEquals(mail.getSendDate(), md.getSendDate());
		mails = getMailingListService().getMails(noAssist, false);
		assertEquals(0, mails.size());
	}
	
	public void testDeleteMail(){
		MailDetail mail = generateMailToSend();		
		getMailingListService().saveMail(assist, mail);
		commit();
		List mails = getMailingListService().getMails(assist, false);
		assertNotNull(mails);
		assertEquals(1, mails.size());
		MailDetail md = getMailingListService().getMail((MailInfo)mails.get(0));
		assertEquals(MailingStatus.DRAFT, md.getStatus());
		assertEquals(mail.getSubject(), md.getSubject());
		assertEquals(mail.getText(), md.getText());
		assertEquals(mail.getSendDate(), md.getSendDate());
		try {
			getMailingListService().deleteMail(assist, md);
		} catch (MailingListApplicationException e) {
			fail();
		}
		mails = getMailingListService().getMails(assist, false);
		assertEquals(0, mails.size());
		//TODO insert testcase where mail has a message job
	}
	
	public void testSendMail(){
		MailDetail mail = generateMailToSend();		
		getMailingListService().sendMail(assist, mail);
		commit();
		List mails = getMailingListService().getMails(assist, false);
		assertNotNull(mails);
		assertEquals(1, mails.size());
		MailDetail md = getMailingListService().getMail((MailInfo)mails.get(0));
		assertEquals(MailingStatus.PLANNED, md.getStatus());
		assertEquals(mail.getSubject(), md.getSubject());
		assertEquals(mail.getText(), md.getText());
		assertEquals(mail.getSendDate(), md.getSendDate());
		//generate draft, then send draft
		MailDetail mailDraft = generateMailToSend();
		getMailingListService().saveMail(assist, mailDraft);
		MailDetail loadedDraft = getMailingListService().getMail((MailInfo)getMailingListService().getMails(assist, false).get(1));
		assertNotNull(loadedDraft);
		assertNotNull(loadedDraft.getId());
		getMailingListService().sendMail(assist, loadedDraft);
		MailDetail loadedSendMessage = getMailingListService().getMail((MailInfo)getMailingListService().getMails(assist, false).get(1));
		assertEquals(MailingStatus.PLANNED, loadedSendMessage.getStatus());
		assertEquals(mailDraft.getSubject(), loadedSendMessage.getSubject());
		assertEquals(mailDraft.getText(), loadedSendMessage.getText());
	}

	private DomainObject generateDomainObject(){
		DomainObject domainObject = new DefaultDomainObject(testUtility.unique());
		securityService.createObjectIdentity(domainObject, null);
		return domainObject;
	}
	
	private DomainObject generateDOAssist(User user){
		DomainObject domainObject = new DefaultDomainObject(testUtility.unique());
		securityService.createObjectIdentity(domainObject, null);
		securityService.setPermissions(user, domainObject.getId(), LectureAclEntry.ASSIST);		
		return domainObject;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}


	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public TestUtility getTestUtilty() {
		return testUtilty;
	}

	public void setTestUtilty(TestUtility testUtilty) {
		this.testUtilty = testUtilty;
	}


	public AclManager getAclManager() {
		return aclManager;
	}


	public void setAclManager(AclManager aclManager) {
		this.aclManager = aclManager;
	}
}