package org.openuss.web.security;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.acegisecurity.AccountExpiredException;
import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationManager;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.CredentialsExpiredException;
import org.acegisecurity.DisabledException;
import org.acegisecurity.LockedException;
import org.acegisecurity.context.HttpSessionContextIntegrationFilter;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.acegisecurity.ui.WebAuthenticationDetails;
import org.acegisecurity.ui.rememberme.RememberMeServices;
import org.acegisecurity.ui.rememberme.TokenBasedRememberMeServices;
import org.acegisecurity.ui.savedrequest.SavedRequest;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.acegisecurity.userdetails.ldap.LdapUserDetails;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.security.AttributeMappingKeys;
import org.openuss.security.SecurityDomainUtility;
import org.openuss.security.SecurityService;
import org.openuss.security.UserDeletedException;
import org.openuss.security.UserInfo;
import org.openuss.security.acegi.UserInfoDetailsAdapter;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.migration.CentralUserData;
import org.openuss.web.migration.MigrationUtility;
import org.openuss.web.statistics.OnlineSessionTracker;

/**
 * AuthenticationController handles all processes around user login, logout, and
 * forgotten passwords.
 * 
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "authenticationController", scope = Scope.REQUEST)
@View
public class AuthenticationController extends BasePage {

	private static final Logger logger = Logger.getLogger(AuthenticationController.class);

	public static final String LOGIN = "login";
	public static final String LOGOUT = "logout";

	private String username;
	private String password;
	private String email;

	private boolean rememberMe;

	@Property(value = "#{authenticationManager}")
	private AuthenticationManager authenticationManager;

	@Property(value = "#{rememberMeServices}")
	private RememberMeServices rememberMeServices;

	@Property(value = "#{securityService}")
	private SecurityService securityService;

	@Property(value = "#{sessionTracker}")
	private OnlineSessionTracker sessionTracker;

	@Property(value = "#{centralUserData}")
	private CentralUserData centralUserData;

	@Property(value = "#{migrationUtility}")
	MigrationUtility migrationUtility;

	public AuthenticationController() {
		logger.debug(" created");
	}

	/**
	 * Login a new user.
	 * 
	 * @return Outcome
	 */
	public String login() {
		final HttpServletRequest request = getRequest();
		final HttpServletResponse response = getResponse();
		final HttpSession session = getSession();

		// Delete domain information from username, so that users can enter
		// domain information during login
		username = SecurityDomainUtility.extractUsername(username);

		final UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

		authRequest.setDetails(new WebAuthenticationDetails(request));
		session.setAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY, username);
		Authentication auth = null;
		try {
			// Perform authentication
			auth = getAuthenticationManager().authenticate(authRequest);

			// Handle LDAP user -> LDAP user has been successfully
			// authenticated.
			if (auth.getPrincipal() instanceof LdapUserDetails) {
				mapLdapUserAttributesToCentralUserField((LdapUserDetails) auth.getPrincipal());

				UserInfo user = securityService.getUserByName(centralUserData.getUsername());

				if (user != null) {
					/*
					 * OpenUSS profile of central user found. 1. Check profile,
					 * whether user is locally enabled and not locked or
					 * credentials or account is expired. 2. Either throw
					 * corresponding exceptions or generate authentication
					 * object, as if user had used a local login. 3. Handle
					 * "local user".
					 */
					AuthenticationUtils.checkLocallyAllowanceToLogin(user);
					migrationUtility.reconcile(user, false);
					// FIXME refactor to business layer
					String[] authorities = securityService.getGrantedAuthorities(user);
					auth = AuthenticationUtils.createSuccessAuthentication(authRequest, new UserInfoDetailsAdapter(user,
							authorities));
				} else {
					/*
					 * New central user 1. Try to find OpenUSS profile by email
					 * address 2. If found, check profile, whether user is not
					 * locked or credentials or account is expired. Disabled
					 * users will be enabled. 3. If no exception occurs, migrate
					 * user and redirect her. 3. If not found, redirect user to
					 * migration page.
					 */
					user = securityService.getUserByEmail(centralUserData.getEmail());

					if (user != null) {
						/*
						 * OpenUSS profile of central user found by email. 1.
						 * Enable user profile. (Seldom: User registered without
						 * activating her account. As LDAP user, she should be
						 * enabled anyway. Refers to: Shortened Registration) 2.
						 * Check for exceptions (see above). 3. If no exception
						 * is thrown, put authentication into SecurityContext,
						 * so that SecurityService can find it. 4. Migrate user,
						 * i. e. change username, password, firstname and
						 * lastname. 5. Add message to inform user about
						 * migration. 6. Handle "local user".
						 */
						user.setEnabled(true);
						AuthenticationUtils.checkLocallyAllowanceToLogin(user);
						String[] authorities = securityService.getGrantedAuthorities(user);
						auth = AuthenticationUtils.createSuccessAuthentication(authRequest, new UserInfoDetailsAdapter(user,
								authorities));
						migrationUtility.migrate(user, auth);
						// Set session bean here, so that i18n gets correct
						// locale for user.
						setSessionBean(Constants.USER_SESSION_KEY, user);
						addMessage(i18n("migration_done_by_email_hint", centralUserData.getAuthenticationDomainName()));
					} else {
						/*
						 * OpenUSS profile not found. Central user has to
						 * migrate manually or do an abbreviated registration.
						 * 1. Add message to ask user to migrate or register. 2.
						 * Redirect user to migration page.
						 */
						// Initialize the security context
						final SecurityContext securityContext = SecurityContextHolder.getContext();
						securityContext.setAuthentication(auth);
						session.setAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY, securityContext);
						return Constants.MIGRATION_PAGE;
					}
				}
			}

			// Handle local user
			if (auth.getPrincipal() instanceof UserInfo) {
				// Initialize the security context
				if (securityService.getUserByName(username).isDeleted()) {
					throw new UserDeletedException("authentication_error_account_deleted");
				}
				final SecurityContext securityContext = SecurityContextHolder.getContext();
				securityContext.setAuthentication(auth);
				session.setAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY, securityContext);
				rememberMeServices.loginSuccess(request, response, auth);
				// setup user and userPreferences
				injectUserInformationIntoSession(auth);
				sessionTracker.logSessionCreated(getSession());
			}

			if (logger.isDebugEnabled()) {
				logger.debug("User: " + username + " switched to active state.");
			}
		} catch (Exception ex) {
			// Authentication failed
			String exceptionMessage = null;

			if (ex instanceof UsernameNotFoundException) {
				exceptionMessage = i18n("authentication_error_account_notfound");
			} else if (ex instanceof CredentialsExpiredException) {
				exceptionMessage = i18n("authentication_error_password_expired");
			} else if (ex instanceof DisabledException) {
				exceptionMessage = i18n("authentication_error_account_disabled");
				addError(exceptionMessage);
				return Constants.USER_ACTIVATION_REQUEST_PAGE;
			} else if (ex instanceof LockedException) {
				exceptionMessage = i18n("authentication_error_account_locked");
			} else if (ex instanceof UserDeletedException) {
				exceptionMessage = i18n("authentication_error_account_deleted");
			} else if (ex instanceof AccountExpiredException) {
				exceptionMessage = i18n("authentication_error_account_expired");
			} else if (ex instanceof BadCredentialsException) {
				/*
				 * If no LDAP server is configured a ProviderNotFound exception
				 * is thrown. If all LDAP servers or the last one are/is
				 * unreachable, an AuthenticationException is thrown. !!! But
				 * Acegi only throws the last catched exception!!! So if the
				 * LDAP provider is called BEFORE DAO provider, only DAO
				 * provider exceptions are thrown. If we catch a
				 * BadCredentialsException from DAO provider, the user could
				 * have entered a valid central login, but all servers are
				 * unreachable. Thus we have to inform the user about it, so she
				 * does not try to enter valid credentials "until forever".
				 */
				exceptionMessage = i18n("authentication_error_password_mismatch");
			} else {
				exceptionMessage = ex.getClass().toString();

			}
			addError(exceptionMessage);
			// Set ACEGI variables
			setSessionBean(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY, ex);
			logger.trace("authentication fail ", ex);

			return LOGIN;
		}
		if (auth != null) {
			return forwardToNextView(session);
		} else {
			return LOGIN;
		}
	}

	/**
	 * Check if user have request a view that needed authentication. If so
	 * forward no to this view.
	 * 
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
				if (!target.startsWith("/")) {
					target = "/" + target;
				}
				return target;
			}
			redirect(Constants.SUCCESS);
			return "";
		}
		redirect(Constants.DESKTOP);
		return "";
	}

	private boolean isValidSavedRequest(SavedRequest request) {
		return request != null && request.getFullRequestUrl().contains("/views/");
	}

	private void injectUserInformationIntoSession(Authentication auth) {
		if (auth.getPrincipal() instanceof UserInfo) {
			logger.debug("Principal is: " + auth.getPrincipal());
			user = (UserInfo) auth.getPrincipal();
			securityService.setLoginTime(user);
			setSessionBean(Constants.USER_SESSION_KEY, user);
			refreshDesktop();
		}
	}

	/**
	 * Invalidates the current session and sends an termination cookie.
	 * 
	 * @return outcome is LOGOUT
	 */
	public String logout() {
		logger.debug(".logout - started");

		// invalidate current session
		final HttpServletRequest request = getRequest();
		request.getSession(false).invalidate();

		// Remove Acegi RememberMe cookie (with ContextPath)
		Cookie terminate = new Cookie(TokenBasedRememberMeServices.ACEGI_SECURITY_HASHED_REMEMBER_ME_COOKIE_KEY, "-");
		terminate.setMaxAge(0);

		if (StringUtils.isNotBlank(getRequest().getContextPath())) {
			terminate.setPath(getRequest().getContextPath());
		} else {
			terminate.setPath("/");
		}

		getResponse().addCookie(terminate);

		for (Cookie cookie : getRequest().getCookies()) {
			if (!(StringUtils.equalsIgnoreCase(cookie.getName(), "JSESSIONID"))
					&& !(StringUtils.equalsIgnoreCase(cookie.getName(),
							TokenBasedRememberMeServices.ACEGI_SECURITY_HASHED_REMEMBER_ME_COOKIE_KEY))) {
				terminate = new Cookie(cookie.getName(), null);
				terminate.setMaxAge(0);
				terminate.setPath("/");
				getResponse().addCookie(terminate);
			}
		}

		SecurityContextHolder.clearContext();

		return LOGOUT;
	}

	private void mapLdapUserAttributesToCentralUserField(LdapUserDetails userDetails) {
		try {
			centralUserData.setAuthenticationDomainId((Long) userDetails.getAttributes().get(
					AttributeMappingKeys.AUTHENTICATIONDOMAINID_KEY).get());
			centralUserData.setAuthenticationDomainName((String) userDetails.getAttributes().get(
					AttributeMappingKeys.AUTHENTICATIONDOMAINNAME_KEY).get());
			centralUserData.setUsername((String) userDetails.getAttributes().get(AttributeMappingKeys.USERNAME_KEY).get());
			centralUserData.setUsername(AuthenticationUtils.generateCentralUserLoginName(centralUserData
					.getAuthenticationDomainName(), centralUserData.getUsername()));
			centralUserData.setFirstName((String) userDetails.getAttributes().get(AttributeMappingKeys.FIRSTNAME_KEY).get());
			centralUserData.setLastName((String) userDetails.getAttributes().get(AttributeMappingKeys.LASTNAME_KEY).get());
			centralUserData.setEmail(((String) userDetails.getAttributes().get(AttributeMappingKeys.EMAIL_KEY).get())
					.toLowerCase());
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public RememberMeServices getRememberMeServices() {
		return rememberMeServices;
	}

	public void setRememberMeServices(RememberMeServices rememberMeServices) {
		this.rememberMeServices = rememberMeServices;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public OnlineSessionTracker getSessionTracker() {
		return sessionTracker;
	}

	public void setSessionTracker(OnlineSessionTracker sessionTracker) {
		this.sessionTracker = sessionTracker;
	}

	public CentralUserData getCentralUserData() {
		return centralUserData;
	}

	public void setCentralUserData(CentralUserData centralUserData) {
		this.centralUserData = centralUserData;
	}

	public MigrationUtility getMigrationUtility() {
		return migrationUtility;
	}

	public void setMigrationUtility(MigrationUtility migrationUtility) {
		this.migrationUtility = migrationUtility;
	}

}
