package org.openuss.web.migration;

import java.io.IOException;
import java.util.TimeZone;

import javax.servlet.http.HttpSession;

import org.acegisecurity.Authentication;
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.acegisecurity.ui.savedrequest.SavedRequest;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.messaging.MessageService;
import org.openuss.registration.RegistrationException;
import org.openuss.registration.RegistrationService;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserContact;
import org.openuss.security.UserPreferences;
import org.openuss.system.SystemService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.security.RegistrationData;

/**
 * Does an abbreviated registration of an centrally authenticated user, 
 * i. e. it generates an default OpenUSS user profile for such users.
 * 
 * @author Peter Schuh
 *
 */
@Bean(name = "shortenedRegistrationController", scope = Scope.REQUEST)
@View
public class ShortenedRegistrationController extends BasePage {

	@Property(value = "#{registrationService}")
	transient private RegistrationService registrationService;

	@Property(value = "#{securityService}")
	transient private SecurityService securityService;

	@Property(value = "#{messageService}")
	transient private MessageService messageService;

	@Property(value = "#{systemService}")
	transient private SystemService systemService;

	@Property(value="#{centralUserData}")
	CentralUserData centralUserData;
	
	private static final Logger logger = Logger.getLogger(ShortenedRegistrationController.class);	
	
	//~ Reduced registration processing for centrally authenticated user ===========
	
	public String register() throws RegistrationException{
		final HttpSession session = getSession();
		User user = newUser();
		
		registrationService.registrateUser(user);
		return forwardToNextView(session); 
	}

	
	
	/**
	 * Create a new user structure for the registration process.
	 */	
	private User newUser() {
		logger.trace("init registration data");
		String username = centralUserData.getUsername();
		String firstName = centralUserData.getFirstName();
		String lastName = centralUserData.getLastName();
		String email = centralUserData.getEmail();
		
		boolean enabled = true;
		boolean accountExpired = false;
		boolean accountLocked = false;
		boolean credentialsExpired = false;
		User user = User.Factory.newInstance(username,"[protected]",email,enabled,accountExpired,accountLocked,credentialsExpired);
		user.setPreferences(UserPreferences.Factory.newInstance());
		user.setContact(UserContact.Factory.newInstance());
		String locale = getFacesContext().getViewRoot().getLocale().toString();
		user.setLocale(locale);
		// set default user time zone
		user.setTimezone(TimeZone.getDefault().getID());
		return user;
	}

	/**
	 * Check if user have request a view that needed authentication. If so forward no to this view.
	 * @param session
	 * @return outcome
	 */
	private String forwardToNextView(final HttpSession session) {
		// forward to request URL if any
		String urlKey = AbstractProcessingFilter.ACEGI_SAVED_REQUEST_KEY;
		SavedRequest savedRequest = (SavedRequest) session.getAttribute(urlKey);
		session.removeAttribute(urlKey);
		if (isValidSavedRequest(savedRequest)) {
			try {
				getExternalContext().redirect(savedRequest.getFullRequestUrl());
				getFacesContext().responseComplete(); 
			} catch (IOException e) {
				logger.error(e);
				String target = savedRequest.getServletPath() + savedRequest.getPathInfo() + "?" + savedRequest.getQueryString();
				if (!target.startsWith("/"))
					target = "/" + target;
				return target;
			}
			return Constants.SUCCESS;
		}
		return Constants.DESKTOP;
	}
	
	private boolean isValidSavedRequest(SavedRequest request) {
		return request != null && request.getFullRequestUrl().contains("/views/");
	}
	
	private void injectUserInformationIntoSession(Authentication auth) {
		if (auth.getPrincipal() instanceof User) {
			logger.debug("Principal is: "+auth.getPrincipal());
			User details = (User) auth.getPrincipal();
			User user = securityService.getUserByName(details.getUsername());
			securityService.setLoginTime(user);
			setSessionBean(Constants.USER_SESSION_KEY, user);
			
			try {
				DesktopInfo desktop = desktopService2.findDesktopByUser(user.getId());
				setSessionBean(Constants.DESKTOP_INFO, desktop);
			} catch (DesktopException e) {
				logger.error(e);
			}
		}
	}


	//~ Getters and Setters =============================================================

	public RegistrationService getRegistrationService() {
		return registrationService;
	}



	public void setRegistrationService(RegistrationService registrationService) {
		this.registrationService = registrationService;
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



	public SystemService getSystemService() {
		return systemService;
	}



	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}



	public CentralUserData getCentralUserData() {
		return centralUserData;
	}



	public void setCentralUserData(CentralUserData centralUserData) {
		this.centralUserData = centralUserData;
	}
}
