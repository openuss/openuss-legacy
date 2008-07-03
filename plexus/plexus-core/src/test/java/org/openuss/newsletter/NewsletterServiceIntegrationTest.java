// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.newsletter;

import java.util.Date;
import java.util.List;

import org.acegisecurity.acl.AclManager;
import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.security.acl.LectureAclEntry;

/**
 * JUnit Test for Spring Hibernate NewsletterService class.
 * @see org.openuss.mail.NewsletterService
 */
public class NewsletterServiceIntegrationTest extends NewsletterServiceIntegrationTestBase {
	
	public SecurityService securityService;
	
	public TestUtility testUtilty;
	
	private AclManager aclManager;

	private NewsletterInfo assist;
	
	private NewsletterInfo noAssist;

	private User user;
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		AcegiUtils.setAclManager(aclManager);
		user = testUtility.createUserSecureContext();
		DomainObject assistDO = generateDOAssist(user);
		flush();
		getNewsletterService().addNewsletter(assistDO, "assistML"); 
		assist = getNewsletterService().getNewsletter(assistDO);
		DomainObject noAssistDO = generateDomainObject();
		flush();
		getNewsletterService().addNewsletter(noAssistDO, "noAssistML"); 
		noAssist = getNewsletterService().getNewsletter(noAssistDO);
	}
	
	
	public void testNewsletterSubscription(){
		DomainObject domainObject = generateDomainObject();
		getNewsletterService().addNewsletter(domainObject, "testName");		
		NewsletterInfo newsletter = getNewsletterService().getNewsletter(domainObject);
		User user = testUtility.createUniqueUserInDB();
		UserInfo userInfo = securityService.getUser(user.getId());
		flush();
		//Init list of subscribers has to be empty
		List<SubscriberInfo> subscribers = getNewsletterService().getSubscribers(newsletter);
		assertEquals(0, subscribers.size());
		//add user to newsletter
		getNewsletterService().subscribe(newsletter, userInfo);
		subscribers = getNewsletterService().getSubscribers(newsletter);
		assertEquals(1, subscribers.size());
		SubscriberInfo si = (SubscriberInfo) subscribers.get(0);
		assertEquals(false, si.isBlocked());
		assertEquals(user.getDisplayName(), si.getDisplayName());
		assertEquals(user.getEmail(), si.getEmail());
		//delete user from newsletter using first unsubscribe method
		getNewsletterService().unsubscribe(si);
		subscribers = getNewsletterService().getSubscribers(newsletter);
		assertEquals(0, subscribers.size());
		//add user to newsletter
		getNewsletterService().subscribe(newsletter, userInfo);
		subscribers = getNewsletterService().getSubscribers(newsletter);
		assertEquals(1, subscribers.size());
		si = (SubscriberInfo) subscribers.get(0);
		assertEquals(false, si.isBlocked());
		assertEquals(user.getDisplayName(), si.getDisplayName());
		assertEquals(user.getEmail(), si.getEmail());
		//delete user from newsletter using second unsubscribe method
		getNewsletterService().unsubscribe(newsletter, userInfo);
		subscribers = getNewsletterService().getSubscribers(newsletter);
		assertEquals(0, subscribers.size());
		//test unsubscribe methods if user not in newsletter
		getNewsletterService().unsubscribe(newsletter, userInfo);
		getNewsletterService().unsubscribe(si);
		subscribers = getNewsletterService().getSubscribers(newsletter);
		assertEquals(0, subscribers.size());
	}
	
	public void testSetBlockingState(){
		DomainObject domainObject = generateDomainObject();
		getNewsletterService().addNewsletter(domainObject, "testName");		
		NewsletterInfo newsletter = getNewsletterService().getNewsletter(domainObject);
		User user = testUtility.createUniqueUserInDB();
		UserInfo userInfo = securityService.getUser(user.getId());
		flush();
		//Init list of subscribers has to be empty
		List<SubscriberInfo> subscribers = getNewsletterService().getSubscribers(newsletter);
		assertEquals(0, subscribers.size());
		//add user to newsletter
		getNewsletterService().subscribe(newsletter, userInfo);
		subscribers = getNewsletterService().getSubscribers(newsletter);
		assertEquals(1, subscribers.size());
		SubscriberInfo si = (SubscriberInfo) subscribers.get(0);
		assertEquals(false, si.isBlocked());
		assertEquals(user.getDisplayName(), si.getDisplayName());
		assertEquals(user.getEmail(), si.getEmail());
		//set Blocking State of User to true		 
		si.setBlocked(true);
		getNewsletterService().setBlockingState(si);
		subscribers = getNewsletterService().getSubscribers(newsletter);
		assertEquals(1, subscribers.size());
		si = (SubscriberInfo) subscribers.get(0);
		assertEquals(true, si.isBlocked());
		//set user to unblocked
		si.setBlocked(false);
		getNewsletterService().setBlockingState(si);
		subscribers = getNewsletterService().getSubscribers(newsletter);
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
		getNewsletterService().saveMail(assist, mail);
		MailInfo mailInfo = new MailInfo();
		mailInfo.setId(mail.getId());
		getNewsletterService().markAsSend(mailInfo);
		flush();
		List<MailInfo> mails = getNewsletterService().getMails(assist, false);
		assertNotNull(mails);
		assertEquals(1, mails.size());
		MailDetail md = getNewsletterService().getMail((MailInfo)mails.get(0));
		assertEquals(MailingStatus.SEND, md.getStatus());
		assertEquals(mail.getSubject(), md.getSubject());
		assertEquals(mail.getText(), md.getText());
		assertEquals(mail.getSendDate(), md.getSendDate());
		mails = getNewsletterService().getMails(noAssist, false);
		assertEquals(0, mails.size());
	}
	
	public void testDeleteMail(){
		MailDetail mail = generateMailToSend();		
		getNewsletterService().saveMail(assist, mail);
		MailInfo mailInfo = new MailInfo();
		mailInfo.setId(mail.getId());
		getNewsletterService().markAsSend(mailInfo);
		flush();
		List<MailInfo> mails = getNewsletterService().getMails(assist, false);
		assertNotNull(mails);
		assertEquals(1, mails.size());
		MailDetail md = getNewsletterService().getMail((MailInfo)mails.get(0));
		assertEquals(MailingStatus.SEND, md.getStatus());
		assertEquals(mail.getSubject(), md.getSubject());
		assertEquals(mail.getText(), md.getText());
		assertEquals(mail.getSendDate(), md.getSendDate());
		try {
			getNewsletterService().deleteMail(assist, md);
		} catch (NewsletterApplicationException e) {
			fail();
		}
		mails = getNewsletterService().getMails(assist, false);
		assertEquals(0, mails.size());
		//TODO insert test case where mail has a message job
	}
	
	public void testSendMail(){
		MailDetail mail = generateMailToSend();		
		getNewsletterService().sendMail(assist, mail);
		flush();
		MailInfo mailInfo = new MailInfo();
		mailInfo.setId(mail.getId());
		getNewsletterService().markAsSend(mailInfo);
		flush();
		List<MailInfo> mails = getNewsletterService().getMails(assist, false);
		assertNotNull(mails);
		assertEquals(1, mails.size());
		MailDetail md = getNewsletterService().getMail((MailInfo)mails.get(0));
		assertEquals(MailingStatus.SEND, md.getStatus());
		assertEquals(mail.getSubject(), md.getSubject());
		assertEquals(mail.getText(), md.getText());
		assertEquals(mail.getSendDate(), md.getSendDate());
		//generate draft, then send draft
		MailDetail mailDraft = generateMailToSend();
		getNewsletterService().saveMail(assist, mailDraft);
		mailInfo = new MailInfo(); 
		mailInfo.setId(mailDraft.getId());
		MailDetail loadedDraft = getNewsletterService().getMail(mailInfo);
		assertNotNull(loadedDraft);
		assertNotNull(loadedDraft.getId());
		getNewsletterService().sendMail(assist, loadedDraft);
		getNewsletterService().markAsSend(mailInfo);
		MailDetail loadedSendMessage = getNewsletterService().getMail((MailInfo)getNewsletterService().getMails(assist, false).get(1));
		assertEquals(MailingStatus.SEND, loadedSendMessage.getStatus());
		assertEquals(mailDraft.getSubject(), loadedSendMessage.getSubject());
		assertEquals(mailDraft.getText(), loadedSendMessage.getText());
	}
	
	public void testRemoveAllSubscriptions(){
		DomainObject domainObject = generateDomainObject();
		getNewsletterService().addNewsletter(domainObject, "testName");		
		NewsletterInfo newsletter = getNewsletterService().getNewsletter(domainObject);
		User user = testUtility.createUniqueUserInDB();
		UserInfo userInfo = securityService.getUser(user.getId());
		flush();
		//Init list of subscribers has to be empty
		List<SubscriberInfo> subscribers = getNewsletterService().getSubscribers(newsletter);
		assertEquals(0, subscribers.size());
		//add user to newsletter
		getNewsletterService().subscribe(newsletter, userInfo);
		subscribers = getNewsletterService().getSubscribers(newsletter);
		assertEquals(1, subscribers.size());
		SubscriberInfo si = (SubscriberInfo) subscribers.get(0);
		assertEquals(false, si.isBlocked());
		assertEquals(user.getDisplayName(), si.getDisplayName());
		assertEquals(user.getEmail(), si.getEmail());
		
		getNewsletterService().removeAllSubscriptions(user);
		subscribers = getNewsletterService().getSubscribers(newsletter);
		assertEquals(0, subscribers.size());
	}

	private DomainObject generateDomainObject(){
		DomainObject domainObject = new DefaultDomainObject(TestUtility.unique());
		securityService.createObjectIdentity(domainObject, null);
		return domainObject;
	}
	
	private DomainObject generateDOAssist(User user){
		DomainObject domainObject = new DefaultDomainObject(TestUtility.unique());
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