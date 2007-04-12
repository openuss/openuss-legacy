// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mailinglist;

import org.openuss.security.SecurityService;

/**
 * JUnit Test for Spring Hibernate MailingListService class.
 * @see org.openuss.mail.MailingListService
 */
public class MailingListServiceIntegrationTest extends MailingListServiceIntegrationTestBase {
	
	public SecurityService securityService;
	
	
	public void testMailingListAddingAndRemoving(){
	}


	public SecurityService getSecurityService() {
		return securityService;
	}


	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
}