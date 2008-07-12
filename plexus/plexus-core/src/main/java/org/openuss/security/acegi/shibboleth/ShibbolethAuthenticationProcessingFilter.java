/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.acegisecurity.ui.AuthenticationDetailsSource;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuss.framework.web.acegi.shibboleth.ShibbolethUserDetails;
import org.openuss.framework.web.acegi.shibboleth.ShibbolethUserDetailsImpl;
import org.springframework.util.Assert;

public class ShibbolethAuthenticationProcessingFilter extends AbstractProcessingFilter {

	protected String shibbolethUsernameHeaderKey = null;
	protected String shibbolethFirstNameHeaderKey = null;
	protected String shibbolethLastNameHeaderKey = null;
	protected String shibbolethEmailHeaderKey = null;
	protected ShibbolethUserDetails shibbolethUserDetails;
	protected String key = null;
	protected String defaultRolePrefix = "ROLE_";
	protected String defaultRole = null;
	protected String defaultDomainName = null;
	protected Long defaultDomainId;
	protected boolean assignDefaultRoleEnabled = false;
	protected boolean redirectEnabled = false;
	protected final Log logger = LogFactory.getLog(this.getClass());
	protected AuthenticationDetailsSource authenticationDetailsSource = new ShibbolethAuthenticationDetailsSource();
	
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(getAuthenticationManager(), "authenticationManager must be specified");
		Assert.hasLength(shibbolethUsernameHeaderKey, "shibbolethUsernameHeaderKey must be specified");
		Assert.hasLength(shibbolethFirstNameHeaderKey, "shibbolethFirstNameHeaderKey must be specified");
		Assert.hasLength(shibbolethLastNameHeaderKey, "shibbolethLastNameHeaderKey must be specified");
		Assert.hasLength(shibbolethEmailHeaderKey, "shibbolethEmailHeaderKey must be specified");
		Assert.hasLength(key, "key must be specified");
	}
	
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
		if (requiresAuthentication(httpRequest, httpResponse) && 
		   (httpRequest.getHeader(shibbolethUsernameHeaderKey)!= null)&& 
		   !("".equals(httpRequest.getHeader(shibbolethUsernameHeaderKey)))) {
			
			if (logger.isDebugEnabled()) {
				logger.debug("Request is to process authentication");
			}
					
			Authentication authResult;
	
			try {
				onPreAuthentication(httpRequest, httpResponse);
				authResult = attemptAuthentication(httpRequest);
			}
			catch (AuthenticationException failed) {
				// Authentication failed
				// Due to AuthenticationManager throws only the last exception thrown within his ordered provider chain.
				// Since we do not know which provider was called last, it does not make sense to handle all kinds of authentication 
				// exceptions differently.
				unsuccessfulAuthentication(httpRequest, httpResponse, failed);
				
				// Return to caller, i. e. the filter chain.
				return;
			}
	
			// Authentication success
			if (isContinueChainBeforeSuccessfulAuthentication()) {
				chain.doFilter(request, response);
			}
	
			successfulAuthentication(httpRequest, httpResponse, authResult);		
		}
		
		chain.doFilter(request, response);
		
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request) throws AuthenticationException {
	   
	    String username = (String) request.getHeader(shibbolethUsernameHeaderKey);
		String password = "";
		GrantedAuthority[] grantedAuthorities = new GrantedAuthorityImpl[0];

		if (isAssignDefaultRoleEnabled()) {
			grantedAuthorities = new GrantedAuthority[]{new GrantedAuthorityImpl(getDefaultRole())};
		}
			
	    PrincipalAcegiUserToken authRequest = new PrincipalAcegiUserToken(getKey(),username,password,grantedAuthorities,username);
	
	    // Allow subclasses to set the "details" property
	    setDetails(request, authRequest);
	
	    // Place the last username attempted into HttpSession for views
	    try {
	    	request.getSession().setAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY, username);	    	
	    }
	    catch (IllegalStateException ignored){	    	
	    }
	
	    return this.getAuthenticationManager().authenticate(authRequest);
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

		// Subclasses can override this method, e. g. to redirect users.
		onSuccessfulAuthentication(request, response, authResult);

		getRememberMeServices().loginSuccess(request, response, authResult);
		
		// Fire event
		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new AuthenticationSuccessEvent(authResult));
		}
		
		if (isRedirectEnabled()) {
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

		// Subclasses can override this method, e. g. to redirect users.
		onUnsuccessfulAuthentication(request, response, failed);
		
		getRememberMeServices().loginFail(request, response);
		
		if (isRedirectEnabled()) {
			String failureUrl = determineFailureUrl(request, failed);			
			sendRedirect(request, response, failureUrl);			
		}

	}
	
	
	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		return SecurityContextHolder.getContext().getAuthentication() == null;
	}
	
	/**
	 * This filter by default responds to every request.
	 *
	 * @return the default
	 */
	public String getDefaultFilterProcessesUrl() {
		return "/";
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
			if (request.getHeader(shibbolethUsernameHeaderKey)!=null) {
				shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.USERNAME_KEY, request.getAttribute(shibbolethUsernameHeaderKey));
				if (request.getAttribute(shibbolethEmailHeaderKey)!=null) {
					shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.EMAIL_KEY, ((String) request.getAttribute(shibbolethEmailHeaderKey)).toLowerCase());
				}
				if (request.getHeader(shibbolethFirstNameHeaderKey)!=null) {
					shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.FIRSTNAME_KEY, request.getAttribute(shibbolethFirstNameHeaderKey));
				}
				if (request.getHeader(shibbolethLastNameHeaderKey)!=null) {
					shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.LASTNAME_KEY, request.getAttribute(shibbolethLastNameHeaderKey));
				}
				if (getDefaultDomainName()!=null) {
					shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.AUTHENTICATIONDOMAINNAME_KEY, getDefaultDomainName());
				}
				if (getDefaultDomainId()!=null) {
					shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.AUTHENTICATIONDOMAINID_KEY, getDefaultDomainId());
				}
				return shibbolethUserDetails;
			}
			else {
				return null;
			}
		}
		
	}
	
		
	public String getDefaultRole() {
		return defaultRole;
	}
	
	
	public void setDefaultRole(String defaultRole) {
		if (defaultRole.toLowerCase().startsWith(defaultRolePrefix.toLowerCase())) { 
			this.defaultRole = defaultRole;
		} else this.defaultRole = defaultRolePrefix+defaultRole;
		setAssignDefaultRoleEnabled(true);
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

	public boolean isAssignDefaultRoleEnabled() {
		return assignDefaultRoleEnabled;
	}

	protected void setAssignDefaultRoleEnabled(boolean assignDefaultRoleEnabled) {
		this.assignDefaultRoleEnabled = assignDefaultRoleEnabled;
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

	public boolean isRedirectEnabled() {
		return redirectEnabled;
	}

	public void setRedirectEnabled(boolean redirectEnabled) {
		this.redirectEnabled = redirectEnabled;
	}

	public AuthenticationDetailsSource getAuthenticationDetailsSource() {
		return authenticationDetailsSource;
	}

	public void setAuthenticationDetailsSource(
			AuthenticationDetailsSource authenticationDetailsSource) {
		this.authenticationDetailsSource = authenticationDetailsSource;
	}
}
