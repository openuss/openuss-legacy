// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mail;

import java.util.Collection;

import org.openuss.security.SecurityService;
import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate MailingListService class.
 * @see org.openuss.mail.MailingListService
 */
public class MailingListServiceIntegrationTest extends MailingListServiceIntegrationTestBase {
	
	public SecurityService securityService;
	
	
	public void testMailingListAddingAndRemoving(){
		User user = testUtility.createUserInDB();
		Long domainObject = new Long(System.currentTimeMillis());
		MailingListInfo ml = mailingListService.getMailingList(domainObject);
		mailingListService.addUserToMailingList(user, ml);
		ml = mailingListService.getMailingList(domainObject);
		assertEquals(0, ml.getRecipients().size());
		Collection<RecipientInfo> recipients = ml.getRecipients();
		assertEquals(1, recipients.size());
		assertEquals(recipients.iterator().next().getName(), user.getName());
		mailingListService.removeUserFromMailingList(user, ml);
		ml = mailingListService.getMailingList(domainObject);
		assertEquals(0, ml.getRecipients().size());		
	}


	public SecurityService getSecurityService() {
		return securityService;
	}


	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
}