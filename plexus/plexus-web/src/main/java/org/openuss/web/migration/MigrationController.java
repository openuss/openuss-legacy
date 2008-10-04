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
import org.acegisecurity.providers.dao.salt.ReflectionSaltSource;
import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
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
import org.openuss.migration.CentralUserData;
import org.openuss.migration.UserMigrationUtility;
import org.openuss.security.SecurityDomainUtility;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
import org.openuss.security.acegi.UserInfoDetailsAdapter;
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

	@Property(value="#{saltSource}")
	transient private ReflectionSaltSource saltSource;

	@Property(value="#{passwordEncoder}")
	transient private Md5PasswordEncoder passwordEncoder;
	
	@Property(value="#{userMigrationUtility}")
	transient private UserMigrationUtility userMigrationUtility;
	
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
		final HttpServletRequest request = getRequest();
		final HttpServletResponse response = getResponse();
		final HttpSession session = getSession();
		
		/* 1. Save central authentication to enable user to retry login with OpenUSS credentials.
		 * 2. If no exception occurs, migrate user.
		 * 3. Handle "local user"
		 */		
		// Try to login user
		// Delete domain information from username, if user had entered domain information during login
		username = SecurityDomainUtility.extractUsername(username);
		
		final UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		
		// Set details for authentication request. Preserve existing user details!
		Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (details instanceof UserDetails) {
			authRequest.setDetails(details);
		} else {
			authRequest.setDetails(new WebAuthenticationDetails(request));
		}
		
//		// Set details for authentication request. Preserve existing user details!
//		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
//		authRequest.setDetails(userDetails!=null? userDetails : new WebAuthenticationDetails(request));
		
		session.setAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY, username);
		Authentication auth = null;
		try {
			// Perform authentication with OpenUSS specific credentials
			auth = authenticationManager.authenticate(authRequest);
			
			// Successful authentication -> Migrate user profile
			userMigrationUtility.migrate((UserInfo)auth.getPrincipal(), centralUserData);
			// Reload user
			UserInfo user = securityService.getUser(((UserInfo)auth.getPrincipal()).getId());
			String[] authorities = securityService.getGrantedAuthorities(user); 
			auth = AuthenticationUtils.createSuccessAuthentication(auth, new UserInfoDetailsAdapter(user, authorities));
			// Set session bean here, so that i18n gets correct locale for user.
			setSessionBean(Constants.USER_SESSION_KEY, user);
			addMessage(i18n("migration_done_by_local_login", centralUserData.getAuthenticationDomainName()));		
			// Handle local user
			if (auth.getPrincipal() instanceof UserInfo) {
				// Initialize the security context
				final SecurityContext securityContext = SecurityContextHolder.getContext();
				securityContext.setAuthentication(auth);
				session.setAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY, securityContext);
				rememberMeServices.loginSuccess(request, response, auth);
				// setup user and userPreferences
				sessionTracker.logSessionCreated(getSession());
			}
		
			if (logger.isDebugEnabled()) {
				logger.debug("User: " + username + " switched to active state.");
			}
		} catch (Exception ex) {
			// Authentication failed
			String exceptionMessage = null;
			String migrationMessage = null;
			
			if (ex instanceof UsernameNotFoundException) {
				exceptionMessage = i18n("authentication_error_account_notfound");				
			} else if (ex instanceof CredentialsExpiredException) {
				exceptionMessage = i18n("authentication_error_password_expired");
			} else if (ex instanceof DisabledException) {
			   /* Although centrally authenticated users automatically switch to enabled state (and their profile can be migrated),
			    * we cannot do this, due to the Acegi framework checks the enabled status BEFORE checking for a valid password.
			    * Without checking the password ANY disabled profile could be hijacked by ANY authenticated central user, who has no profile yet.
			    * So we have to check the password on our own. Alternatively we could revoke central authentication and redirect to activation request page.
			    */
				UserInfoDetailsAdapter userInfoDetailsAdapter = new UserInfoDetailsAdapter(securityService.getUserByName(username),null);
				String presentedPassword = authRequest.getCredentials() == null ? "" : authRequest.getCredentials().toString();
				
				if (passwordEncoder.isPasswordValid(userInfoDetailsAdapter.getPassword(), presentedPassword, saltSource.getSalt(userInfoDetailsAdapter))) {
				   userInfoDetailsAdapter.setEnabled(true);
				   auth = AuthenticationUtils.createSuccessAuthentication(authRequest, userInfoDetailsAdapter);
				   // FIXME Ugly - Put in a UserInforDetailsAdapter 
				   userMigrationUtility.migrate(userInfoDetailsAdapter, centralUserData);
    			   // Reload user
				   UserInfo user = securityService.getUser(userInfoDetailsAdapter.getId());
				   auth = AuthenticationUtils.createSuccessAuthentication(auth, new UserInfoDetailsAdapter(user, securityService.getGrantedAuthorities(user)));
				   
				   // Set session bean here, so that i18n gets correct locale for user.
				   setSessionBean(Constants.USER_SESSION_KEY, user);
				   
				   migrationMessage=i18n("migration_done_by_local_login", centralUserData.getAuthenticationDomainName());
				   
				   // Handle local user
				   // Initialize the security context
				   final SecurityContext securityContext = SecurityContextHolder.getContext();
				   securityContext.setAuthentication(auth);
				   session.setAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY, securityContext);
				   rememberMeServices.loginSuccess(request, response, auth);
				   // setup user and userPreferences
//				   injectUserInformationIntoSession(auth);
				   sessionTracker.logSessionCreated(getSession());							   
				} else {
					exceptionMessage = i18n("authentication_error_password_mismatch");
				}
						
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
			// Set messages for next page
			if (exceptionMessage!=null) {
				logger.trace("authentication with OpenUSS credentials failed ", ex);
				addError(exceptionMessage);	
			}else if (migrationMessage != null) {
				addMessage(migrationMessage);
		    }						
		}
		if (auth==null) {
			return Constants.MIGRATION_PAGE;
		} else {
			return forwardToNextView(session);
		}
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

	public UserMigrationUtility getUserMigrationUtility() {
		return userMigrationUtility;
	}
	public void setUserMigrationUtility(UserMigrationUtility userMigrationUtility) {
		this.userMigrationUtility = userMigrationUtility;
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
	public ReflectionSaltSource getSaltSource() {
		return saltSource;
	}
	public void setSaltSource(ReflectionSaltSource saltSource) {
		this.saltSource = saltSource;
	}
	public Md5PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}
	public void setPasswordEncoder(Md5PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
}
