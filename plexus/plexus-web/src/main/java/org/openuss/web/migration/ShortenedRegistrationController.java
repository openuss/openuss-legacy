package org.openuss.web.migration;

import java.io.IOException;
import java.util.Random;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationManager;
import org.acegisecurity.context.HttpSessionContextIntegrationFilter;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.acegisecurity.ui.WebAuthenticationDetails;
import org.acegisecurity.ui.rememberme.RememberMeServices;
import org.acegisecurity.ui.savedrequest.SavedRequest;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.registration.RegistrationException;
import org.openuss.security.Roles;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserContact;
import org.openuss.security.UserImpl;
import org.openuss.security.UserPreferences;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.security.AuthenticationUtils;
import org.openuss.web.statistics.OnlineSessionTracker;

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

	@Property(value = "#{securityService}")
	transient private SecurityService securityService;

	@Property(value="#{centralUserData}")
	CentralUserData centralUserData;
	
	@Property(value="#{authenticationManager}")
	private AuthenticationManager authenticationManager;
	
	@Property(value="#{rememberMeServices}")
	private RememberMeServices rememberMeServices;
	
	@Property(value="#{sessionTracker}")
	private OnlineSessionTracker sessionTracker;
	
	private static final Logger logger = Logger.getLogger(ShortenedRegistrationController.class);	
	
	//~ Reduced registration processing for centrally authenticated user ===========
	
	public String register() throws RegistrationException{
		final HttpSession session = getSession();
		User user = generateEnabledUserProfile();
		
		//create user
		securityService.createUser(user);
		
		//assign roles to user
		getSecurityService().addAuthorityToGroup(user, Roles.USER);
		getSecurityService().saveUser(user);
		
		
		return login(user);
	}
	
	
	/**
	 * Create a new user structure for the registration process.
	 */	
	private User generateEnabledUserProfile() {
		logger.trace("init registration data");
		// Generate registration information
		String username = centralUserData.getUsername();
		String firstName = centralUserData.getFirstName();
		String lastName = centralUserData.getLastName();
		String email = centralUserData.getEmail();
		// Generate random password, so that account is likely not to be used for login.
		Random random = new Random();		
		String password = String.valueOf(random.nextLong())+String.valueOf(random.nextLong());
		
		// Central user is already authenticated! Therefore an enabled user profile is created.
		boolean enabled = true;
		
		boolean accountExpired = false;
		boolean accountLocked = false;
		boolean credentialsExpired = false;		
		User user = User.Factory.newInstance(username,password,email,enabled,accountExpired,accountLocked,credentialsExpired);
		String locale = getFacesContext().getViewRoot().getLocale().toString();
		String timezone = TimeZone.getDefault().getID();
		UserPreferences userPreferences = UserPreferences.Factory.newInstance();
		userPreferences.setLocale(locale);
		userPreferences.setTimezone(timezone);		
		user.setPreferences(userPreferences);
		UserContact userContact = UserContact.Factory.newInstance(firstName, lastName);
		user.setContact(userContact);
		user.setLocale(locale);
		// set default user time zone
		
		user.setTimezone(timezone);		
		return user;
	}

	//~ Logon methods =================================================================================
	
	public String login(User user) {
		final HttpServletRequest request = getRequest();
		final HttpServletResponse response = getResponse();
		final HttpSession session = getSession();
		String username = user.getUsername();
		final UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,"protected");

		authRequest.setDetails(new WebAuthenticationDetails(request));
		session.setAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY, username);
		Authentication auth = null;
		
		/*  
		 * 1. Load OpenUSS profile.
		 * 2. Generate authentication object, as if user had used a local login.
		 * 3. Handle "local user".
		 */  
		UserImpl principal = (UserImpl) user;
		UserDetails userDetails = principal;
		auth = AuthenticationUtils.createSuccessAuthentication(principal, authRequest, userDetails);

		// Initialize the security context
		final SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(auth);
		session.setAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY, securityContext);
		rememberMeServices.loginSuccess(request, response, auth);
		// setup user and userPreferences
		injectUserInformationIntoSession(auth);
		sessionTracker.logSessionCreated(getSession());
	
		if (logger.isDebugEnabled())
			logger.debug("User: " + username + " switched to active state.");
		return forwardToNextView(session);		
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




	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}


	public CentralUserData getCentralUserData() {
		return centralUserData;
	}



	public void setCentralUserData(CentralUserData centralUserData) {
		this.centralUserData = centralUserData;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}


	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}


	public RememberMeServices getRememberMeServices() {
		return rememberMeServices;
	}


	public void setRememberMeServices(RememberMeServices rememberMeServices) {
		this.rememberMeServices = rememberMeServices;
	}


	public OnlineSessionTracker getSessionTracker() {
		return sessionTracker;
	}


	public void setSessionTracker(OnlineSessionTracker sessionTracker) {
		this.sessionTracker = sessionTracker;
	}
}
