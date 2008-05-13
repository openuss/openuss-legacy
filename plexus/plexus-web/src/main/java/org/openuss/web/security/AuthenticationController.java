package org.openuss.web.security;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.acegisecurity.AccountExpiredException;
import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
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
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.statistics.OnlineSessionTracker;


/**
 * AuthenticationController handles all processes around user login, logout, and forgotten passwords. 
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 *
 */
@Bean(name="authenticationController", scope=Scope.REQUEST)
@View
public class AuthenticationController extends BasePage {

	private static final Logger logger = Logger.getLogger(AuthenticationController.class);

	public static final String LOGIN = "login";
	public static final String LOGOUT = "logout";
	
	private String username;
	private String password;
	private String email;
	
	private boolean rememberMe;

	@Property(value="#{authenticationManager}")
	private AuthenticationManager authenticationManager;
	
	@Property(value="#{rememberMeServices}")
	private RememberMeServices rememberMeServices;
	
	@Property(value="#{securityService}")
	private SecurityService securityService;
	
	@Property(value="#{sessionTracker}")
	private OnlineSessionTracker sessionTracker;
	
	public AuthenticationController() {
		logger.debug(" created");
	}
	
	/**
	 * Login a new user.
	 * @return Outcome
	 */
	public String login() {
		final HttpServletRequest request = getRequest();
		final HttpServletResponse response = getResponse();
		final HttpSession session = getSession();

		final UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

		authRequest.setDetails(new WebAuthenticationDetails(request));
		session.setAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY, username);
		Authentication auth = null;
		
		try {
			// Perform authentication
			auth = getAuthenticationManager().authenticate(authRequest);
			// Initialize the security context
			final SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(auth);
			session.setAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY, securityContext);
			rememberMeServices.loginSuccess(request, response, auth);
			// setup user and userPreferences
			injectUserInformationIntoSession(auth);
			sessionTracker.logSessionCreated(getSession());
		
			logger.debug("User: " + username + " switched to active state.");
		} catch (DisabledException ex) {
			logger.debug("User " + username + " is not active");
			addError(i18n("authentication_error_account_disabled"));
			return "/views/public/user/activate/request.xhtml";
		} catch (AuthenticationException ex) {
			// Authentication failed
			String exceptionMessage = null;

			if (ex instanceof UsernameNotFoundException) {
				exceptionMessage = i18n("authentication_error_account_notfound");
			} else if (ex instanceof CredentialsExpiredException) {
				exceptionMessage = i18n("authentication_error_password_expired");
			} else if (ex instanceof DisabledException) {
				exceptionMessage = i18n("authentication_error_account_disabled");
			} else if (ex instanceof LockedException) {
				exceptionMessage = i18n("authentication_error_account_locked");
			} else if (ex instanceof AccountExpiredException) {
				exceptionMessage = i18n("authentication_error_account_expired");
			} else if (ex instanceof BadCredentialsException) { 
				exceptionMessage = i18n("authentication_error_password_mismatch");
			} else {
				exceptionMessage = ex.getMessage();
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
			logger.debug("Principal is: "+auth.getPrincipal());
			user = (UserInfo) auth.getPrincipal();
			securityService.setLoginTime(user);
			setSessionBean(Constants.USER_SESSION_KEY, user);
			refreshDesktop();
		}
	}

	/**
	 * Invalidates the current session and sends an termination cookie.
	 * @return outcome is LOGOUT
	 */
	public String logout() {
		logger.debug(".logout - started");

		// invalidate current session
		final HttpServletRequest request = getRequest();
		request.getSession(false).invalidate();

		// Remove Cookie
		Cookie terminate = new Cookie(TokenBasedRememberMeServices.ACEGI_SECURITY_HASHED_REMEMBER_ME_COOKIE_KEY, "-");
		terminate.setMaxAge(0);
		getResponse().addCookie(terminate);
		
		SecurityContextHolder.clearContext();

		return LOGOUT;
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

}
