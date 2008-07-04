package org.openuss.migration;

import org.openuss.messaging.MessageService;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;

public class UserMigrationUtilityImpl implements UserMigrationUtility {

	protected MessageService messageService;
	protected SecurityService securityService;
	
	
	public void migrate(UserInfo user, CentralUserData centralUserData) {
		// TODO Auto-generated method stub

	}

	public void reconcile(UserInfo user, boolean haveToSave) {
		// TODO Auto-generated method stub

	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
}
