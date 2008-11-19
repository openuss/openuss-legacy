package org.openuss.migration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.commons.lang.StringUtils;
import org.openuss.messaging.MessageService;
import org.openuss.messaging.MessageServiceException;
import org.openuss.security.SecurityDomainUtility;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
import org.openuss.security.acegi.UserInfoDetailsAdapter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class UserMigrationUtilityImpl implements UserMigrationUtility, InitializingBean {

	protected MessageService messageService;
	protected SecurityService securityService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(securityService, "A security service must be set.");
		Assert.notNull(messageService, "A message service must be set.");		
	}
	
	
	public void migrate(UserInfo user, CentralUserData centralUserData) {
		Authentication preservedAuthentication = SecurityContextHolder.getContext().getAuthentication();
		// Set enabled status for migrated user, since we received a verified email address from a trusted third-party, e. g. a ldap directory or a SSO identity provider.
		user.setEnabled(true);
		UserInfoDetailsAdapter userDetails = new UserInfoDetailsAdapter(user,securityService.getGrantedAuthorities(user));
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		// Generate random password, so that account is likely not to be used for login.
		Random random = new Random();		
		String password = String.valueOf(random.nextLong())+String.valueOf(random.nextLong());
		securityService.changePassword(password);
		user.setUsername(centralUserData.getUsername());
		reconcile(user, centralUserData, true);
		
		// Remove temporary authentication, due to it was only necessary for SecurityService.
		SecurityContextHolder.getContext().setAuthentication(preservedAuthentication);
		
		sendMigrationNotificationEmail(user, centralUserData.getAuthenticationDomainName());		
	}

	public boolean reconcile(UserInfo user, CentralUserData centralUserData, boolean haveToSave) {
		boolean mustSave = haveToSave;
		if (!StringUtils.equalsIgnoreCase(user.getEmail(), centralUserData.getEmail())) {
		    user.setEmail(centralUserData.getEmail());
		    mustSave = true;
		}
		
		if (!StringUtils.equalsIgnoreCase(user.getFirstName(), centralUserData.getFirstName())) {
			user.setFirstName(centralUserData.getFirstName());
			mustSave = true;
		}
		
		if (!StringUtils.equalsIgnoreCase(user.getLastName(), centralUserData.getLastName())) {
			user.setLastName(centralUserData.getLastName());
			mustSave = true;
		}
		
		if (mustSave) {
			securityService.saveUser(user);
		}
		
		return mustSave;
	}
	
	/**
	 * Sends the user an email notification, that her account was migrated and she must login
	 * using her username and password for the specified authentication domain.
	 * 
	 * @param user
	 * @param domainname
	 * @throws MessageServiceException
	 */
	private void sendMigrationNotificationEmail(UserInfo user, String authenticationDomainName) throws MessageServiceException {		
		String username = SecurityDomainUtility.extractUsername(user.getUsername());

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", username);
		parameters.put("domainname", authenticationDomainName);
		
		messageService.sendMessage("user.migration.notification.sender", "user.migration.notification.subject", "migrationnotification",
				parameters, user);
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
