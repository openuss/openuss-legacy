package org.openuss.web.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Session;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.messaging.MessageService;
import org.openuss.registration.RegistrationException;
import org.openuss.registration.RegistrationService;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
import org.openuss.security.ldap.AuthenticationDomainInfo;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;
import org.openuss.web.Constants;
import org.openuss.web.utils.MessageBox;

/**
 * Registration process controller to manage the user registration process
 * 
 * @author Ingo Dueppe
 */
@Bean(name = "registrationController", scope = Scope.REQUEST)
@View
@Session
public class RegistrationController extends BaseBean {

	private static final Logger logger = Logger.getLogger(RegistrationController.class);

	@Property(value = "#{registrationService}")
	transient private RegistrationService registrationService;

	@Property(value = "#{securityService}")
	transient private SecurityService securityService;

	@Property(value = "#{messageService}")
	transient private MessageService messageService;

	@Property(value = "#{systemService}")
	transient private SystemService systemService;

	@Property(value = "#{registrationData}")
	private RegistrationData registrationData;
	
	@Property(value = "#{ldapConfigurationService}")
	private LdapConfigurationService ldapConfigurationService;

	@Property(value = "#{param.username}")
	private String username;

	private String userToken;

	private String changePasswordUrl;

	/**
	 * Performs user registrations and removes user registration information
	 * from the session.
	 * 
	 * @return outcome
	 */
	public String performRegistration() throws RegistrationException {
		UserInfo user = (UserInfo) getSessionBean(Constants.USER_SESSION_KEY);
		if (user == null) {
			addError(i18n("message_error_cannot_perform_registration"));
			return "failure";
		}
		// FIXME set default user time zone
		user.setTimezone(TimeZone.getDefault().getID());

		registrationService.registrateUser(user);
		String activationCode = registrationService.generateActivationCode(user);
		// send verification email
		sendVerificationEmail(user, activationCode);

		removeSessionBean(Constants.REGISTRATION_DATA);
		removeSessionBean(Constants.USER_SESSION_KEY);

		String message = i18n("user_email_verification_send_message", new Object[]{user.getEmail()});
		String title = i18n("user_email_verification_send_message_title");
		return MessageBox.showDefaultMessage(message, title);
	}

	/**
	 * Convince method to send an verification e-mail to the given user
	 * 
	 * @param user
	 * @param activationCode
	 */
	private void sendVerificationEmail(UserInfo user, String activationCode) {
		try {
			String link = "/actions/public/user/activate.faces?code=" + activationCode;

			link = applicationAddress() + link;

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("username", user.getUsername());
			parameters.put("link", link);

			messageService.sendMessage("user.registration.email.verification.sender",
					"user.registration.email.verification.subject", "verification", parameters, user);

		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

	/**
	 * Resends a new activation code to the user. user name property must be set
	 * 
	 * @return outcome
	 */
	public String resendActivationCode() {
		try {
			UserInfo user = securityService.getUserByName(username);
			if (user == null) {
				addError("User does not exist");
				return Constants.FAILURE;
			}
			String activationCode = registrationService.generateActivationCode(user);
			sendVerificationEmail(user, activationCode);
			return MessageBox.showDefaultMessage(i18n("user_email_verification_resend_message"),
					i18n("user_email_verification_resend_message_title"));
		} catch (RegistrationException e) {
			logger.error(e);
			// TODO should be localized
			addError("Error: Couldn't send activation code", e.getMessage());
			return Constants.FAILURE;
		}
	}

	/**
	 * Sends the user an email with a temporary link to a page, where a new
	 * password can be chosen.
	 * 
	 * @param user
	 * @param verificationCode
	 * @throws MessagingException
	 */
	private void sendForgottenPasswordEmail(UserInfo user, String verificationCode) throws Exception {
		String link = "/actions/public/user/password/change.faces?code=" + verificationCode;

		link = applicationAddress() + link;

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", user.getUsername());
		parameters.put("passwordresetlink", link);

		messageService.sendMessage("user.password.request.sender", "user.password.request.subject", "password",
				parameters, user);
	}

	private String applicationAddress() {
		return systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue();
	}

	/**
	 * Generates a new Password and send it per email to the user.
	 * 
	 * @return Outcome SUCCESS | FAILURE
	 * @throws RegistrationException
	 * @throws MessagingException
	 */
	public String forgotPassword() throws RegistrationException, Exception {
		if (registrationService == null) {
			throw new IllegalStateException(
					"RegistrationController isn't connected to a RegistrationService. Check if the property is properly initialized within managed beans configuration.");
		}

		UserInfo user = securityService.getUserByEmail(userToken);
		if (user == null) {
			addError(i18n("username_not_found"));
			return Constants.FAILURE;
		}
		
		if (user.isCentralUser()) {
			AuthenticationDomainInfo domain = findDomainByName(user.getDomainName());
			changePasswordUrl = domain.getChangePasswordUrl();
			return Constants.FAILURE;
		}
		// Handle local user
		String verificationCode = registrationService.generateActivationCode(user);
		sendForgottenPasswordEmail(user, verificationCode);
		showPasswordMailConfirmation(user);
		return Constants.SUCCESS;
	}

	private AuthenticationDomainInfo findDomainByName(String domainName) {
		// FIXME Refactor into business layer
		List<AuthenticationDomainInfo> domains = ldapConfigurationService.getAllDomains();
		for (AuthenticationDomainInfo domain : domains) {
			if (StringUtils.equals(domain.getName(), domainName)) {
				return domain;
			}
		}
		return null;
	}

	private void showPasswordMailConfirmation(UserInfo user) {
		addMessage(i18n("password_mail_send", user.getEmail()));
		MessageBox msgBox = new MessageBox();
		msgBox.setConfirmLabel(i18n("home"));
		msgBox.setConfirmOutcome("home");
		msgBox.setTitle(i18n("password_send_header"));
		setSessionBean(MessageBox.SESSION_KEY, msgBox);
	}

	public RegistrationService getRegistrationService() {
		return registrationService;
	}

	public void setRegistrationService(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public RegistrationData getRegistrationData() {
		return registrationData;
	}

	public void setRegistrationData(RegistrationData registrationData) {
		this.registrationData = registrationData;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public LdapConfigurationService getLdapConfigurationService() {
		return ldapConfigurationService;
	}

	public void setLdapConfigurationService(LdapConfigurationService ldapConfigurationService) {
		this.ldapConfigurationService = ldapConfigurationService;
	}

	public String getChangePasswordUrl() {
		return changePasswordUrl;
	}

	public void setChangePasswordUrl(String changePasswordUrl) {
		this.changePasswordUrl = changePasswordUrl;
	}

}
