package org.openuss.migration;

import org.openuss.messaging.MessageService;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;

public interface UserMigrationUtility {

	/**
	 * Application specific migration for users.
	 * 
	 * @param user
	 * @param centralUserData
	 */
	public void migrate(UserInfo user, CentralUserData centralUserData);

	/**
	 * Application specific reconciliation of locally stored user details with
	 * centrally administered user details.
	 * 
	 * @param user
	 * @param centralUserData
	 * @param haveToSave
	 * @return reconciliation status: <code>true</code>, if locally stored user
	 *         details had to be updated.
	 */
	public boolean reconcile(UserInfo user, CentralUserData centralUserData, boolean haveToSave);

	public MessageService getMessageService();

	public void setMessageService(MessageService messageService);

	public SecurityService getSecurityService();

	public void setSecurityService(SecurityService securityService);
}
