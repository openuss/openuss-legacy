// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mailinglist;

import java.util.Collection;

import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;
import org.openuss.mail.RecipientInfo;
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
		DomainObject domainObject = new DefaultDomainObject(testUtility.unique()); 
		MailingListInfo ml = mailingListService.getMailingList(domainObject);
		assertNotNull(ml);
		commit();
		mailingListService.addUserToMailingList(user, ml);
		commit();
		ml = mailingListService.getMailingList(domainObject);
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