package org.openuss.web.security;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

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
import org.openuss.security.User;
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

	@Property(value = "#{param.username}")
	private String username;

	private String userToken;

	/**
	 * Performes user registrations and removes user registration information
	 * from the session.
	 * 
	 * @return outcome
	 */
	public String performRegistration() throws RegistrationException {
		User user = (User) getSessionBean(Constants.USER_SESSION_KEY);
		// set default user timezone
		user.setTimezone(TimeZone.getDefault().getID());

		registrationService.registrateUser(user, registrationData.isEnableAssistantRole());
		String activationCode = registrationService.generateActivationCode(user);
		// send verification email
		sendVerificationEmail(user, activationCode);

		removeSessionBean(Constants.REGISTRATION_DATA);
		removeSessionBean(Constants.USER_SESSION_KEY);

		String message = i18n("user_email_verification_send_message", user.getEmail());
		String title = i18n("user_email_verification_send_message_title");
		return MessageBox.showDefaultMessage(message, title);
	}

	/**
	 * Convience method to send an verification e-mail to the given user
	 * 
	 * @param user
	 * @param activationCode
	 */
	private void sendVerificationEmail(User user, String activationCode) {
		try {
			String link = "/actions/public/user/activate.faces?code=" + activationCode;

			link = applicationAddress() + link;

			Map parameters = new HashMap();
			parameters.put("username", user.getUsername());
			parameters.put("link", link);

			messageService.sendMessage("user.registration.email.verification.sender",
					"user.registration.email.verification.subject", "verification", parameters, user);

		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

	/**
	 * Resends a new activation code to the user. username property must be set
	 * 
	 * @return outcome
	 */
	public String resendActivationCode() {
		try {
			User user = securityService.getUserByName(username);
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
	private void sendForgottenPasswordEmail(User user, String verificationCode) throws Exception {
		String link = "/actions/public/user/password/change.faces?code=" + verificationCode;

		link = applicationAddress() + link;

		Map parameters = new HashMap();
		parameters.put("username", user.getUsername());
		parameters.put("passwordresetlink", link);

		messageService.sendMessage("user.password.request.sender", "user.password.request.subject", "password",
				parameters, user);
	}

	private String applicationAddress() {
		// FIXME - must be configurable from outside
		final HttpServletRequest request = getRequest();
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	/**
	 * Generates a new Password and send it per email to the user.
	 * 
	 * @return Outcome SUCCESS | FAILURE
	 * @throws RegistrationException
	 * @throws MessagingException
	 */
	public String forgotPassword() throws RegistrationException, Exception {
		if (registrationService == null)
			throw new IllegalStateException(
					"RegistrationController isn't connected to a RegistrationService. Check if the property is properly initialized within managed beans configuration.");

		User user = securityService.getUserByName(userToken);
		if (user == null) {
			user = securityService.getUserByEmail(userToken);
		}
		if (user == null) {
			addError(i18n("username_not_found"));
			return Constants.FAILURE;
		}
		String verificationCode = registrationService.generateActivationCode(user);

		sendForgottenPasswordEmail(user, verificationCode);
		showPasswordMailConfirmation(user);

		return Constants.SUCCESS;
	}

	private void showPasswordMailConfirmation(User user) {
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

}
