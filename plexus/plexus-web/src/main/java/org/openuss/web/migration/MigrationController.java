package org.openuss.web.migration;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.acegisecurity.AccountExpiredException;
import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.CredentialsExpiredException;
import org.acegisecurity.DisabledException;
import org.acegisecurity.LockedException;
import org.acegisecurity.context.HttpSessionContextIntegrationFilter;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.AuthenticationProvider;
import org.acegisecurity.providers.ProviderManager;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.acegisecurity.ui.WebAuthenticationDetails;
import org.acegisecurity.ui.rememberme.RememberMeServices;
import org.acegisecurity.ui.savedrequest.SavedRequest;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.messaging.MessageService;
import org.openuss.security.SecurityConstants;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserImpl;
import org.openuss.system.SystemService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.security.AuthenticationUtils;
import org.openuss.web.statistics.OnlineSessionTracker;

/**
 * Does migration of an existing OpenUSS user towards a central authentication.
 * 
 * @author Peter Schuh
 *
 */
@Bean(name = "migrationController", scope = Scope.REQUEST)
@View
public class MigrationController extends BasePage {

	@Property(value = "#{securityService}")
	transient private SecurityService securityService;

	@Property(value = "#{messageService}")
	transient private MessageService messageService;

	@Property(value = "#{systemService}")
	transient private SystemService systemService;
	
	@Property(value="#{rememberMeServices}")
	transient private RememberMeServices rememberMeServices;

	@Property(value="#{sessionTracker}")
	private OnlineSessionTracker sessionTracker;

	@Property(value="#{centralUserData}")
	transient private CentralUserData centralUserData;
	
	@Property(value="#{daoAuthenticationProvider}")
	private AuthenticationProvider daoAuthenticationProvider;
	
	@Property(value="#{migrationUtility}")
	transient private MigrationUtility migrationUtility;

	
	Authentication oldCentralAuthentication;
	
	private String username;
	private String password;
	
	ProviderManager authenticationManager = new ProviderManager();
	List<AuthenticationProvider> providers = new Vector<AuthenticationProvider>();
	
	private static final Logger logger = Logger.getLogger(MigrationController.class);

	public MigrationController() {
		logger.debug(" created");
	}
	/**
	 * Migrate an existing user after she has entered her local credentials.
	 * @return Outcome
	 */
	public String performMigration() {
		/* 1. Save central authentication to enable user to retry login with OpenUSS credentials.
		 * 2. If no exception occurs, migrate user.
		 * 3. Handle "local user"
		 */		
		
		// Save central authentication object from SecurityContext, so that user can repeatedly try to enter a valid local login.
		oldCentralAuthentication = SecurityContextHolder.getContext().getAuthentication();
		
		// Try to login user
		final HttpServletRequest request = getRequest();
		final HttpServletResponse response = getResponse();
		final HttpSession session = getSession();
		
		// Important! Ensure that users cannot login using a username of a user profile of a centrally authenticated user. Therefore replace all delimiters.
		username = username.replaceAll("\\"+SecurityConstants.USERNAME_DOMAIN_DELIMITER+"+","");

		final UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

		authRequest.setDetails(new WebAuthenticationDetails(request));
		session.setAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY, username);
		Authentication auth = null;
		try {
			// Perform authentication with OpenUSS specific credentials
			auth = authenticationManager.authenticate(authRequest);
			
			// Successful authentication -> Migrate user profile
			migrationUtility.migrate((User)auth.getPrincipal(), auth);
			
			addError(i18n("migration_done_by_local_login", centralUserData.getAuthenticationDomainName()));		
			// Handle local user
			if (auth.getPrincipal() instanceof User) {
				// Initialize the security context
				final SecurityContext securityContext = SecurityContextHolder.getContext();
				securityContext.setAuthentication(auth);
				session.setAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY, securityContext);
				rememberMeServices.loginSuccess(request, response, auth);
				// setup user and userPreferences
				injectUserInformationIntoSession(auth);
				sessionTracker.logSessionCreated(getSession());
			}
		
			if (logger.isDebugEnabled())
				logger.debug("User: " + username + " switched to active state.");
		} catch (Exception ex) {
			// Authentication failed
			String exceptionMessage = null;
			
			if (ex instanceof UsernameNotFoundException) {
				exceptionMessage = i18n("authentication_error_account_notfound");				
			} else if (ex instanceof CredentialsExpiredException) {
				exceptionMessage = i18n("authentication_error_password_expired");
			} else if (ex instanceof DisabledException) {
					   /* Although centrally authenticated users automatically switch to enabled state (and their profile can be migrated),
					    * we cannot do this here, due to the Acegi framework checks enabled status BEFORE checking for valid password.
					    * Without checking the password ANY disabled profile could be hijacked by ANY authenticated central user, who has no profile yet.
					    * So we commented the following lines out, although they will work. Instead we revoke central authentication and redirect to activation request page.
					    */
					   
					   /*
					   User user = securityService.getUserByName(username);
					   user.setEnabled(true);
					   UserImpl principal = (UserImpl) user;
					   UserDetails userDetails = principal;
					   auth = AuthenticationUtils.createSuccessAuthentication(principal, authRequest, userDetails);
					   user = migrationUtility.migrate(user, auth);
					   exceptionMessage = i18n("migration_done_by_local_login", centralUserData.getAuthenticationDomainName());					   
					   // Handle local user
					   if (auth.getPrincipal() instanceof User) {
						   // Initialize the security context
						   final SecurityContext securityContext = SecurityContextHolder.getContext();
						   securityContext.setAuthentication(auth);
						   session.setAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY, securityContext);
						   rememberMeServices.loginSuccess(request, response, auth);
						   // setup user and userPreferences
						   injectUserInformationIntoSession(auth);
						   sessionTracker.logSessionCreated(getSession());
						}
						*/
						final SecurityContext securityContext = SecurityContextHolder.getContext();
						securityContext.setAuthentication(null);
						exceptionMessage = i18n("authentication_error_account_disabled");
						addError(exceptionMessage);
						return Constants.USER_ACTIVATION_REQUEST_PAGE;

			} else if (ex instanceof LockedException) {
				exceptionMessage = i18n("authentication_error_account_locked");
			} else if (ex instanceof AccountExpiredException) {
				exceptionMessage = i18n("authentication_error_account_expired");
			} else if (ex instanceof BadCredentialsException) { 
				exceptionMessage = i18n("authentication_error_password_mismatch");
			} else if (ex instanceof AuthenticationException) {
				exceptionMessage = i18n("authentication_error_communication");
			} else {
				exceptionMessage = ex.getMessage();
			}
			addError(exceptionMessage);	
			logger.trace("authentication with OpenUSS credentials failed ", ex);			
		}
		if (auth==null)
			return Constants.MIGRATION_PAGE;
		else
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
 
	 public CentralUserData getCentralUserData() {
		return centralUserData;
	}

	public void setCentralUserData(CentralUserData centralUserData) {
		this.centralUserData = centralUserData;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public AuthenticationProvider getDaoAuthenticationProvider() {
		return daoAuthenticationProvider;
	}

	public void setDaoAuthenticationProvider(
			AuthenticationProvider daoAuthenticationProvider) {
		this.daoAuthenticationProvider = daoAuthenticationProvider;
		providers.add(daoAuthenticationProvider);
		authenticationManager.setProviders(providers);
	}
	public MigrationUtility getMigrationUtility() {
		return migrationUtility;
	}
	public void setMigrationUtility(MigrationUtility migrationUtility) {
		this.migrationUtility = migrationUtility;
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