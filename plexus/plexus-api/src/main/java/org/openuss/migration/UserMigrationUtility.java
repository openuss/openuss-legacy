package org.openuss.migration;

import org.openuss.messaging.MessageService;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;

public interface UserMigrationUtility {
	
	public void migrate(UserInfo user, CentralUserData centralUserData);

	public void reconcile(UserInfo user, boolean haveToSave);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public MessageService getMessageService();
	
	public void setMessageService(MessageService messageService);
		
	public SecurityService getSecurityService();
	
	public void setSecurityService(SecurityService securityService);
}
