// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mailinglist;

import java.util.List;

import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;
import org.openuss.security.SecurityService;
import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate MailingListService class.
 * @see org.openuss.mail.MailingListService
 */
public class MailingListServiceIntegrationTest extends MailingListServiceIntegrationTestBase {
	
	public SecurityService securityService;
	
	public TestUtility testUtilty;
	
	public void testMailingListSubscription(){
		DomainObject domainObject = generateDomainObject();
		User user = testUtility.createDefaultUserInDB();
		commit();
		//init list of subscribers has to be empty
		List subscribers = getMailingListService().getSubscribers(domainObject);
		assertEquals(0, subscribers.size());
		//add user to mailinglist
		getMailingListService().subscribe(domainObject, user);
		subscribers = getMailingListService().getSubscribers(domainObject);
		assertEquals(1, subscribers.size());
		SubscriberInfo si = (SubscriberInfo) subscribers.get(0);
		assertEquals(false, si.isBlocked());
		assertEquals(user.getDisplayName(), si.getDisplayName());
		assertEquals(user.getEmail(), si.getEmail());
		//delete user from mailingList using first unsubscribe method
		getMailingListService().unsubscribe(si);
		subscribers = getMailingListService().getSubscribers(domainObject);
		assertEquals(0, subscribers.size());
		//add user to mailingList
		getMailingListService().subscribe(domainObject, user);
		subscribers = getMailingListService().getSubscribers(domainObject);
		assertEquals(1, subscribers.size());
		si = (SubscriberInfo) subscribers.get(0);
		assertEquals(false, si.isBlocked());
		assertEquals(user.getDisplayName(), si.getDisplayName());
		assertEquals(user.getEmail(), si.getEmail());
		//delete user from mailingList using second unsubscribe method
		getMailingListService().unsubscribe(domainObject, user);
		subscribers = getMailingListService().getSubscribers(domainObject);
		assertEquals(0, subscribers.size());
		//test unsubscribe methods if user not in mailinglist
		getMailingListService().unsubscribe(domainObject, user);
		getMailingListService().unsubscribe(si);
		subscribers = getMailingListService().getSubscribers(domainObject);
		assertEquals(0, subscribers.size());

	}

	private DomainObject generateDomainObject(){
		DomainObject domainObject = new DefaultDomainObject(testUtility.unique());
		securityService.createObjectIdentity(domainObject, null);
		//securityService.setPermissions(user, domainId, LectureAclEntry.ASSIST);		
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
}