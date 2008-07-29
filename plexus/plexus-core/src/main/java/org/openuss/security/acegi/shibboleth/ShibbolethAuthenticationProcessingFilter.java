package org.openuss.security.acegi.shibboleth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.adapters.PrincipalAcegiUserToken;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.event.authentication.AuthenticationSuccessEvent;
import org.acegisecurity.providers.anonymous.AnonymousAuthenticationToken;
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.acegisecurity.ui.AuthenticationDetailsSource;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuss.framework.web.acegi.shibboleth.ShibbolethUserDetails;
import org.openuss.framework.web.acegi.shibboleth.ShibbolethUserDetailsImpl;
import org.springframework.util.Assert;

/**
 * @author Peter Schuh
 *
 */
public class ShibbolethAuthenticationProcessingFilter extends AbstractProcessingFilter {

	/**
	 * Key of the HTTP header attribute for a user's username.</br> 
	 * Must not be <code>null</code>.
	 */
	protected String shibbolethUsernameHeaderKey = "REMOTE_USER";
	/**
	 * Key of the HTTP header attribute for a user's firstname.</br> 
	 * Must not be <code>null</code>.
	 */
	protected String shibbolethFirstNameHeaderKey = "SHIB_FIRSTNAME";
	/**
	 * Key of the HTTP header attribute for a user's lastname.</br> 
	 * Must not be <code>null</code>.
	 */
	protected String shibbolethLastNameHeaderKey = "SHIB_LASTNAME";
	/**
	 * Key of the HTTP header attribute for a user's email address.</br> 
	 * Must not be <code>null</code>.
	 */
	protected String shibbolethEmailHeaderKey = "SHIB_MAIL";;
	
	/**
     * Indicates, which filter instance has generated the authentication request.</br> 
     * Only <code>PrincipalAcegiUserToken</code> with proper key will be processed by a corresponding <code>ShibbolethAuthenticationProvider</code> implementation.</br>
     * Assure setting the key according to the key property of the corresponding <code>ShibbolethAuthenticationProvider</code> instance.</br>
     * Must not be <code>null</code>.
     */
	protected String key = null;

	protected ShibbolethUserDetails shibbolethUserDetails;
	
	/**
	 * Default role prefix of a default role. If default role is automatically prefixed, if prefix is missing.</br>
	 * Defaults to <code>ROLE_</code>.</br>
	 * Must not be <code>null</code>.
	 */
	protected String defaultRolePrefix = "ROLE_";
	
	/**
	 * Default role, that is assigned to a shibboleth user for an authentication request, that is processed by the corresponding <code>ShibbolethAuthenticationProvider</code>.</br>
	 * Must not be <code>null</code>.
	 */
	protected String defaultRole = "ROLE_SHIBUSER";
	
	/**
	 * Name of the default domain, that is assigned to a shibboleth user's details of an authentication request, that is processed by the corresponding <code>ShibbolethAuthenticationProvider</code>.
	 */
	protected String defaultDomainName = null;

	/**
	 * ID of the default domain, that is assigned to a shibboleth user's details of an authentication request, that is processed by the corresponding <code>ShibbolethAuthenticationProvider</code>.
	 */
	protected Long defaultDomainId;
	
	/**
	 * Enables migration.</br> 
	 * Gets <code>true</code> by setting a <code>migrationTargetUrl</code>.</br>
	 * Take care to also enable migration within corresponding <code>shibbolethAuthenticationProvider</code>!</br>
	 * Defaults to <code>false</code>.
	 */
	protected boolean migrationEnabled = false;
	
	/**
	 * Url the user gets redirected to, if manual migration is necessary.</br>
	 * Take care to also enable migration within corresponding <code>shibbolethAuthenticationProvider</code>!
	 */
	protected String migrationTargetUrl = null;
	
	protected boolean migrationNecessary = false;
	
	/**
	 * Enables HTTP redirect in case of a successful authentication.</br>
	 * Defaults to <code>true</code>.
	 */
	protected boolean redirectOnAuthenticationSuccessEnabled = true;

	/**
	 * Enables HTTP redirect in case of a successful authentication.</br>
	 * Defaults to <code>true</code>.
	 */
	protected boolean redirectOnAuthenticationFailureEnabled = true;
	
	/**
	 * Defines, when an authentication is required.</br>
	 * Behaviour depends on both <code>processEachUrlEnabled</code> <b>and</b> <code>onlyProcessFilterProcessesUrlEnabled</code>.</br>
	 * <p>
	 * <u>Possible four combinations:</u></br>
	 * </p>
	 * <p>
	 * processEachUrlEnabled: <code>false</code></br>
	 * onlyProcessFilterProcessesUrlEnabled: <code>false</code></br>
	 * Results in:</br>
	 * Authentication is required, if and only if current authentication is <code>null</code> or an instance of an <code>AnonymousAuthenticationToken</code> <b>and</b> url of request matches the url, that the filter is set to process.
	 * </p>
	 * <p>
	 * processEachUrlEnabled: <code>true</code></br>
	 * onlyProcessFilterProcessesUrlEnabled: <code>false</code></br>
	 * Results in:</br>
	 * Authentication is required, if current authentication is <code>null</code> or an instance of an <code>AnonymousAuthenticationToken</code>.
	 * </p>
	 * <p>
	 * processEachUrlEnabled: <code>false</code></br>
	 * onlyProcessFilterProcessesUrlEnabled: <code>true</code></br>
	 * Results in:</br>
	 * Authentication is required, if url of request matches the url, that the filter is configured to process.
	 * </p>
	 * <p>
	 * processEachUrlEnabled: <code>true</code></br>
	 * onlyProcessFilterProcessesUrlEnabled: <code>true</code></br>
	 * Results in:</br>
	 * Authentication is required, if current authentication is <code>null</code> or an instance of an <code>AnonymousAuthenticationToken</code> <b>or</b> url of request matches the url, that the filter is set to process.
	 * </p>
	 * <p>
	 * <u>Default behaviour:</u>
	 * </p>
	 * processEachUrlEnabled: <code>false</code></br>
	 * onlyProcessFilterProcessesUrlEnabled: <code>true</code>
	 */
	protected boolean processEachUrlEnabled = false;
	
	/**
	 * @see #processEachUrlEnabled
	 * 
	 */
	protected boolean onlyProcessFilterProcessesUrlEnabled = true;
	
	/**
	 * Enables filter to return after unsuccessful authentication instead of continuing filter chain.</br>
	 * Defaults to <code>false</code>, i. e. proceeding filter chain.
	 */
	protected boolean returnAfterUnsuccessfulAuthentication = false;

	/**
	 * Enables filter to return after successful authentication instead of continuing filter chain.</br>
	 * Defaults to <code>false</code>, i. e. proceeding filter chain.
	 */
	protected boolean returnAfterSuccessfulAuthentication = false;
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	protected AuthenticationDetailsSource authenticationDetailsSource = new ShibbolethAuthenticationDetailsSource();
	
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		Assert.hasLength(shibbolethUsernameHeaderKey, "shibbolethUsernameHeaderKey must be specified");
		Assert.hasLength(shibbolethFirstNameHeaderKey, "shibbolethFirstNameHeaderKey must be specified");
		Assert.hasLength(shibbolethLastNameHeaderKey, "shibbolethLastNameHeaderKey must be specified");
		Assert.hasLength(shibbolethEmailHeaderKey, "shibbolethEmailHeaderKey must be specified");
		Assert.hasLength(key, "key must be specified");
		Assert.hasLength(defaultRole, "defaultRole must be specified");
		doAfterPropertiesSet();
	}
	
	protected void doAfterPropertiesSet() throws Exception {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (!(request instanceof HttpServletRequest)) {
			throw new ServletException("Can only process HttpServletRequest");
		}

		if (!(response instanceof HttpServletResponse)) {
			throw new ServletException("Can only process HttpServletResponse");
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		// Check, if authentication is required and possible.
		// Possible means, that shibboleth request header attributes must be present and set.
		// Remember, that shibboleth request header attributes are always present, if a request 
		// passed the apache shibboleth module. Only their presence does not indicate, that an authentication
		// is possible. For an authentication, they must be set.
		String usernameHeader = httpRequest.getHeader(shibbolethUsernameHeaderKey);
		if (requiresAuthentication(httpRequest, httpResponse) && 
		   (usernameHeader!= null)&& 
		   !("".equals(usernameHeader))) {
			
			if (logger.isDebugEnabled()) {
				logger.debug("Request is to process authentication");
			}
					
			Authentication authResult;
	
			try {
				onPreAuthentication(httpRequest, httpResponse);
				authResult = attemptAuthentication(httpRequest);
				
				// Authentication success
				if (isContinueChainBeforeSuccessfulAuthentication()) {
					chain.doFilter(request, response);
				}
				
				successfulAuthentication(httpRequest, httpResponse, authResult);
				
				if (isReturnAfterSuccessfulAuthentication()) {
					return;
				}
			}
			catch (AuthenticationException failed) {
				// Authentication failed
				// Due to AuthenticationManager throws only the last exception thrown within his ordered provider chain.
				// Since we do not know which provider was called last, it does not make sense to handle all kinds of authentication 
				// exceptions differently.
				unsuccessfulAuthentication(httpRequest, httpResponse, failed);
				
				if (isReturnAfterUnsuccessfulAuthentication()) {
					return;
				}
			}
	
		}
		
		chain.doFilter(request, response);
		
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request) throws AuthenticationException {
	   
	    String username = (String) request.getHeader(shibbolethUsernameHeaderKey);
		String password = "";

		GrantedAuthority[] grantedAuthorities = new GrantedAuthority[]{new GrantedAuthorityImpl(getDefaultRole())};
			
	    PrincipalAcegiUserToken authRequest = new PrincipalAcegiUserToken(getKey(),username,password,grantedAuthorities,username);
	
	    // Allow subclasses to set the "details" property
	    setDetails(request, authRequest);
	
	    // Place the last username attempted into HttpSession for views
	    try {
	    	request.getSession().setAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY, username);	    	
	    }
	    catch (IllegalStateException ignored){	    	
	    }
	    Authentication authentication = this.getAuthenticationManager().authenticate(authRequest);
	    
	    // Set switch for redirect to migration page
	    if (isMigrationEnabled() && authentication.equals(authRequest)) {
	    	setMigrationNecessary(true);
	    }
	    	
	    return authentication;
	}
    
	@Override
	protected String determineTargetUrl(HttpServletRequest request) {
		String targetUrl = null;
		if (isMigrationEnabled() && isMigrationNecessary()) {
			targetUrl = getMigrationTargetUrl();
			setMigrationNecessary(false);
		}
		else {
			// Don't attempt to obtain the url from the saved request if
			// alwaysUsedefaultTargetUrl is set
			targetUrl = isAlwaysUseDefaultTargetUrl() ? null : obtainFullRequestUrl(request);
		}
	
		if (targetUrl == null) {
			targetUrl = getDefaultTargetUrl();
		}

		return targetUrl;
	}
	
    @Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) throws IOException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Authentication success: " + authResult.toString());
		}

		SecurityContextHolder.getContext().setAuthentication(authResult);

		if (logger.isDebugEnabled()) {
			logger.debug("Updated SecurityContextHolder to contain the following Authentication: '" + authResult + "'");
		}

		getRememberMeServices().loginSuccess(request, response, authResult);
		
		// Fire event
		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new AuthenticationSuccessEvent(authResult));
		}

		// Subclasses can override this method, e. g. to do a more specific redirect.
		onSuccessfulAuthentication(request, response, authResult);
		
		if (isRedirectOnAuthenticationSuccessEnabled()) {
			String targetUrl = determineTargetUrl(request);
			sendRedirect(request, response, targetUrl);
		}
	}

	
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {

		SecurityContextHolder.getContext().setAuthentication(null);

		if (logger.isDebugEnabled()) {
			logger.debug("Updated SecurityContextHolder to contain null Authentication");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Authentication request failed: " + failed.toString());
		}

		try {
			request.getSession().setAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY, failed);
		}
		catch (IllegalStateException ignored) {
		}

		getRememberMeServices().loginFail(request, response);

		// Subclasses can override this method, e. g. to do a more specific redirect.
		onUnsuccessfulAuthentication(request, response, failed);
			
		if (isRedirectOnAuthenticationFailureEnabled()) {
			String failureUrl = determineFailureUrl(request, failed);			
			sendRedirect(request, response, failureUrl);			
		}

	}
		
	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean authenticationRequired = ((super.requiresAuthentication(request, response) || isProcessEachUrlEnabled())) && 
		   								 (((authentication == null) || (authentication instanceof AnonymousAuthenticationToken) || (super.requiresAuthentication(request, response) && isOnlyProcessFilterProcessesUrlEnabled())));
		return authenticationRequired;
	}
	
    /**
     * This filter by default responds to <code>/j_acegi_security_check</code>.
     *
     * @return the default
     */
	public String getDefaultFilterProcessesUrl() {
		return "/j_acegi_security_check";
	}

	/**
     * Provided so that subclasses may configure what is put into the authentication request's details
     * property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details set
     */
	protected void setDetails(HttpServletRequest request, PrincipalAcegiUserToken authRequest) {        
    	authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
    
	public class ShibbolethAuthenticationDetailsSource implements AuthenticationDetailsSource {
		
		public Object buildDetails(HttpServletRequest request) {
			shibbolethUserDetails = new ShibbolethUserDetailsImpl();
            shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.USERNAME_KEY, request.getHeader(shibbolethUsernameHeaderKey));
			if (request.getHeader(shibbolethEmailHeaderKey)!=null) {
				shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.EMAIL_KEY, ((String) request.getHeader(shibbolethEmailHeaderKey)).toLowerCase());
			}
			if (request.getHeader(shibbolethFirstNameHeaderKey)!=null) {
				shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.FIRSTNAME_KEY, request.getHeader(shibbolethFirstNameHeaderKey));
			}
			if (request.getHeader(shibbolethLastNameHeaderKey)!=null) {
				shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.LASTNAME_KEY, request.getHeader(shibbolethLastNameHeaderKey));
			}
			if (getDefaultDomainName()!=null) {
				shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.AUTHENTICATIONDOMAINNAME_KEY, getDefaultDomainName());
			}
			if (getDefaultDomainId()!=null) {
				shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.AUTHENTICATIONDOMAINID_KEY, getDefaultDomainId());
			}
			return shibbolethUserDetails;
		}
		
	}
	
		
	public String getDefaultRole() {
		return defaultRole;
	}
	
	
	public void setDefaultRole(String defaultRole) {
		if (defaultRole.toLowerCase().startsWith(defaultRolePrefix.toLowerCase())) { 
			this.defaultRole = defaultRole;
		} else this.defaultRole = getDefaultRolePrefix()+defaultRole;
	}
	
	public String getShibbolethUsernameHeaderKey() {
		return shibbolethUsernameHeaderKey;
	}

	public void setShibbolethUsernameHeaderKey(String shibbolethUsernameHeaderKey) {
		this.shibbolethUsernameHeaderKey = shibbolethUsernameHeaderKey;
	}

	public String getShibbolethFirstNameHeaderKey() {
		return shibbolethFirstNameHeaderKey;
	}

	public void setShibbolethFirstNameHeaderKey(String shibbolethFirstNameHeaderKey) {
		this.shibbolethFirstNameHeaderKey = shibbolethFirstNameHeaderKey;
	}

	public String getShibbolethLastNameHeaderKey() {
		return shibbolethLastNameHeaderKey;
	}

	public void setShibbolethLastNameHeaderKey(String shibbolethLastNameHeaderKey) {
		this.shibbolethLastNameHeaderKey = shibbolethLastNameHeaderKey;
	}

	public String getShibbolethEmailHeaderKey() {
		return shibbolethEmailHeaderKey;
	}

	public void setShibbolethEmailHeaderKey(String shibbolethEmailHeaderKey) {
		this.shibbolethEmailHeaderKey = shibbolethEmailHeaderKey;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDefaultDomainName() {
		return defaultDomainName;
	}

	public void setDefaultDomainName(String defaultDomainName) {
		this.defaultDomainName = defaultDomainName;
	}

	public String getDefaultRolePrefix() {
		return defaultRolePrefix;
	}
	
	public void setDefaultRolePrefix(String defaultRolePrefix) {
		this.defaultRolePrefix = defaultRolePrefix;
	}
	
	public Long getDefaultDomainId() {
		return defaultDomainId;
	}
	
	public void setDefaultDomainId(Long defaultDomainId) {
		this.defaultDomainId = defaultDomainId;
	}

	public AuthenticationDetailsSource getAuthenticationDetailsSource() {
		return authenticationDetailsSource;
	}

	public void setAuthenticationDetailsSource(
			AuthenticationDetailsSource authenticationDetailsSource) {
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

	public boolean isReturnAfterUnsuccessfulAuthentication() {
		return returnAfterUnsuccessfulAuthentication;
	}

	public void setReturnAfterUnsuccessfulAuthentication(
			boolean returnAfterUnsuccessfulAuthentication) {
		this.returnAfterUnsuccessfulAuthentication = returnAfterUnsuccessfulAuthentication;
	}

	public boolean isReturnAfterSuccessfulAuthentication() {
		return returnAfterSuccessfulAuthentication;
	}

	public void setReturnAfterSuccessfulAuthentication(
			boolean returnAfterSuccessfulAuthentication) {
		this.returnAfterSuccessfulAuthentication = returnAfterSuccessfulAuthentication;
	}

	public boolean isMigrationEnabled() {
		return migrationEnabled;
	}

	protected void setMigrationEnabled(boolean migrationEnabled) {
		this.migrationEnabled = migrationEnabled;
	}

	public String getMigrationTargetUrl() {
		return migrationTargetUrl;
	}

	public void setMigrationTargetUrl(String migrationTargetUrl) {
		this.migrationTargetUrl = migrationTargetUrl;
		setMigrationEnabled(migrationTargetUrl == null? false : true);
	}

	protected boolean isMigrationNecessary() {
		return migrationNecessary;
	}

	protected void setMigrationNecessary(boolean migrationNecessary) {
		this.migrationNecessary = migrationNecessary;
	}

	public boolean isProcessEachUrlEnabled() {
		return processEachUrlEnabled;
	}

	public void setProcessEachUrlEnabled(boolean processEachUrlEnabled) {
		this.processEachUrlEnabled = processEachUrlEnabled;
	}

	public boolean isRedirectOnAuthenticationSuccessEnabled() {
		return redirectOnAuthenticationSuccessEnabled;
	}

	public void setRedirectOnAuthenticationSuccessEnabled(
			boolean redirectOnAuthenticationSuccessEnabled) {
		this.redirectOnAuthenticationSuccessEnabled = redirectOnAuthenticationSuccessEnabled;
	}

	public boolean isRedirectOnAuthenticationFailureEnabled() {
		return redirectOnAuthenticationFailureEnabled;
	}

	public void setRedirectOnAuthenticationFailureEnabled(
			boolean redirectOnAuthenticationFailureEnabled) {
		this.redirectOnAuthenticationFailureEnabled = redirectOnAuthenticationFailureEnabled;
	}

	public boolean isOnlyProcessFilterProcessesUrlEnabled() {
		return onlyProcessFilterProcessesUrlEnabled;
	}

	public void setOnlyProcessFilterProcessesUrlEnabled(
			boolean onlyProcessFilterProcessesUrlEnabled) {
		this.onlyProcessFilterProcessesUrlEnabled = onlyProcessFilterProcessesUrlEnabled;
	}
}
