package org.openuss.web.migration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.messaging.MessageService;
import org.openuss.messaging.MessageServiceException;
import org.openuss.security.SecurityConstants;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserContact;

/**
 * Migrates a user 
 * @author Peter Schuh
 *
 */
@Bean(name="migrationUtility", scope=Scope.REQUEST)
@View
public class MigrationUtility extends BaseBean{

	@Property(value = "#{messageService}")
	MessageService messageService;

	@Property(value="#{centralUserData}")
	CentralUserData centralUserData;
		
	@Property(value="#{securityService}")
	SecurityService securityService;

	public User migrate(User user, Authentication auth) {
		//Put authentication into SecurityContext, so that SecurityService can find it.
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(auth);
		// Generate random password, so that account is likely not to be used for login.
		Random random = new Random();		
		String password = String.valueOf(random.nextLong())+String.valueOf(random.nextLong());
		securityService.changePassword(password);
		user.setUsername(centralUserData.getUsername());
		user.setEmail(centralUserData.getEmail());
		UserContact userContact = user.getContact();
		userContact.setFirstName(centralUserData.getFirstName());
		userContact.setLastName(centralUserData.getLastName());
		securityService.saveUserContact(userContact);
		securityService.saveUser(user);
				
		try {
			sendMigrationNotificationEmail(user, centralUserData.getAuthenticationDomainName());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return user;
	}
	
	/**
	 * Sends the user an email notification, that her account was migrated and she must login
	 * using her username and password for the specified authentication domain.
	 * 
	 * @param user
	 * @param domainname
	 * @throws MessageServiceException
	 */
	private void sendMigrationNotificationEmail(User user, String authenticationDomainName) throws Exception {		

		String username = user.getUsername();
		// Delete domain information from username, if user had entered domain information during login 
		username = username.substring(username.lastIndexOf(SecurityConstants.USERNAME_DOMAIN_DELIMITER)+1);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", username);
		parameters.put("domainname", authenticationDomainName);

		messageService.sendMessage("user.migration.notification.sender", "user.migration.notification.subject", "migrationnotification",
				parameters, user);
	}


	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public CentralUserData getCentralUserData() {
		return centralUserData;
	}

	public void setCentralUserData(CentralUserData centralUserData) {
		this.centralUserData = centralUserData;
	}
}
