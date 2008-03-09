package org.openuss.web.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.AuthenticationManager;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.event.authentication.AuthenticationSuccessEvent;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.anonymous.AnonymousAuthenticationToken;
import org.acegisecurity.ui.AuthenticationDetailsSource;
import org.acegisecurity.ui.AuthenticationDetailsSourceImpl;
import org.acegisecurity.ui.AuthenticationEntryPoint;
import org.acegisecurity.ui.basicauth.BasicProcessingFilter;
import org.acegisecurity.ui.basicauth.BasicProcessingFilterEntryPoint;
import org.acegisecurity.ui.rememberme.RememberMeServices;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.Assert;

/**
 * Processes a HTTP request's BASIC authorization headers, putting the result into the
 * <code>SecurityContextHolder</code>.<p>For a detailed background on what this filter is designed to process,
 * refer to <A HREF="http://www.faqs.org/rfcs/rfc1945.html">RFC 1945, Section 11.1</A>. Any realm name presented in
 * the HTTP request is ignored.</p>
 *  <p>In summary, this filter is responsible for processing any request that has a HTTP request header of
 * <code>Authorization</code> with an authentication scheme of <code>Basic</code> and a Base64-encoded
 * <code>username:password</code> token. For example, to authenticate user "Aladdin" with password "open sesame" the
 * following header would be presented:</p>
 *  <p><code>Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==</code>.</p>
 *  <p>This filter can be used to provide BASIC authentication services to both remoting protocol clients (such as
 * Hessian and SOAP) as well as standard user agents (such as Internet Explorer and Netscape).</p>
 *  <P>If authentication is successful, the resulting {@link Authentication} object will be placed into the
 * <code>SecurityContextHolder</code>.</p>
 *  <p>If authentication fails and <code>ignoreFailure</code> is <code>false</code> (the default), an {@link
 * AuthenticationEntryPoint} implementation is called. Usually this should be {@link BasicProcessingFilterEntryPoint},
 * which will prompt the user to authenticate again via BASIC authentication.</p>
 *  <p>Basic authentication is an attractive protocol because it is simple and widely deployed. However, it still
 * transmits a password in clear text and as such is undesirable in many situations. Digest authentication is also
 * provided by Acegi Security and should be used instead of Basic authentication wherever possible. See {@link
 * org.acegisecurity.ui.digestauth.DigestProcessingFilter}.</p>
 *  <p>Note that if a {@link #rememberMeServices} is set, this filter will automatically send back remember-me
 * details to the client. Therefore, subsequent requests will not need to present a BASIC authentication header as
 * they will be authenticated using the remember-me mechanism.</p>
 *  <p><b>Do not use this class directly.</b> Instead configure <code>web.xml</code> to use the {@link
 * org.acegisecurity.util.FilterToBeanProxy}.</p>
 * 
 * This is an extension of the <code>BasicProcessingFilter</code> class. 
 * It uses a convenience method structure like the <code>AbstractProcessingFilter</code> to be overridden by subclasses.
 *
 * @author Ben Alex
 * @author Peter Schuh
 * @version $Id: BasicProcessingFilter.java 2277 2007-12-02 02:15:18Z benalex $
 */
public class ExtendedBasicProcessingFilter implements Filter, InitializingBean {
    //~ Static fields/initializers =====================================================================================

	private static final Log logger = LogFactory.getLog(ExtendedBasicProcessingFilter.class);

    //~ Instance fields ================================================================================================

	protected ApplicationEventPublisher eventPublisher;
    private AuthenticationDetailsSource authenticationDetailsSource = new AuthenticationDetailsSourceImpl();
    private AuthenticationEntryPoint authenticationEntryPoint;
    private AuthenticationManager authenticationManager;
    private RememberMeServices rememberMeServices;
    private boolean ignoreFailure = false;

    //~ Methods ========================================================================================================

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.authenticationManager, "An AuthenticationManager is required");
        Assert.notNull(this.authenticationEntryPoint, "An AuthenticationEntryPoint is required");
    }

    public void destroy() {}
        
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (!(request instanceof HttpServletRequest)) {
            throw new ServletException("Can only process HttpServletRequest");
        }

        if (!(response instanceof HttpServletResponse)) {
            throw new ServletException("Can only process HttpServletResponse");
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String header = httpRequest.getHeader("Authorization");

        if (logger.isDebugEnabled()) {
            logger.debug("Authorization header: " + header);
        }

        if ((header != null) && header.startsWith("Basic ")) {
            String base64Token = header.substring(6);
            String token = new String(Base64.decodeBase64(base64Token.getBytes()));

            String username = "";
            String password = "";
            int delim = token.indexOf(":");

            if (delim != -1) {
                username = token.substring(0, delim);
                password = token.substring(delim + 1);
            }

            if (authenticationIsRequired(username)) {
                UsernamePasswordAuthenticationToken authRequest =
                        new UsernamePasswordAuthenticationToken(username, password);
                authRequest.setDetails(authenticationDetailsSource.buildDetails((HttpServletRequest) request));

                Authentication authResult;

                try {
                    authResult = authenticationManager.authenticate(authRequest);
                } catch (AuthenticationException failed) {
                    // Authentication failed
                	
                	unsuccessfulAuthentication(request, response, failed);
                    
                	if (ignoreFailure) {
                        chain.doFilter(request, response);
                    } else {
                        authenticationEntryPoint.commence(request, response, failed);
                    }
                    return;
                }

                // Authentication success
                successfulAuthentication(request, response, authResult);
            }
        }

        chain.doFilter(request, response);
    }

    private boolean authenticationIsRequired(String username) {
        // Only reauthenticate if username doesn't match SecurityContextHolder and user isn't authenticated
        // (see SEC-53)
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();

        if(existingAuth == null || !existingAuth.isAuthenticated()) {
            return true;
        }

        // Limit username comparison to providers which use usernames (ie UsernamePasswordAuthenticationToken)
        // (see SEC-348)

        if (existingAuth instanceof UsernamePasswordAuthenticationToken && !existingAuth.getName().equals(username)) {
            return true;
        }
        
        // Handle unusual condition where an AnonymousAuthenticationToken is already present
        // This shouldn't happen very often, as BasicProcessingFitler is meant to be earlier in the filter
        // chain than AnonymousProcessingFilter. Nevertheless, presence of both an AnonymousAuthenticationToken
        // together with a BASIC authentication request header should indicate reauthentication using the
        // BASIC protocol is desirable. This behaviour is also consistent with that provided by form and digest,
        // both of which force re-authentication if the respective header is detected (and in doing so replace
        // any existing AnonymousAuthenticationToken). See SEC-610.
        if (existingAuth instanceof AnonymousAuthenticationToken) {
        	return true;
        }

        return false;
    }
        
	protected void successfulAuthentication(ServletRequest request, ServletResponse response,
			Authentication authResult) throws IOException {
        // Adaption call successfulAuthentication
        if (logger.isDebugEnabled()) {
            logger.debug("Authentication success: " + authResult.toString());
        }

        SecurityContextHolder.getContext().setAuthentication(authResult);
      
		onSuccessfulAuthentication(request, response, authResult);
		
		// Enable onSuccessfulAuthentication to replace authResult within SecurityContext
		authResult = SecurityContextHolder.getContext().getAuthentication();
		
        if (rememberMeServices != null) {
            rememberMeServices.loginSuccess((HttpServletRequest)request, (HttpServletResponse)response, authResult);
        }

		// Fire event
		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new AuthenticationSuccessEvent(authResult));
		}

	}
	
	protected void unsuccessfulAuthentication(ServletRequest request, ServletResponse response,
			AuthenticationException failed) throws IOException {

		if (logger.isDebugEnabled()) {
			logger.debug("Authentication request failed: " + failed.toString());
        }

        SecurityContextHolder.getContext().setAuthentication(null);

        if (rememberMeServices != null) {
            rememberMeServices.loginFail((HttpServletRequest)request, (HttpServletResponse)response);
        }
        
		onUnsuccessfulAuthentication(request, response, failed);

	}
    	
    protected void onPreAuthentication(ServletRequest request, ServletResponse response) throws AuthenticationException, IOException {
	}
	
    protected void onSuccessfulAuthentication(ServletRequest request, ServletResponse response, Authentication authResult) throws IOException {
	}
		
	protected void onUnsuccessfulAuthentication(ServletRequest request, ServletResponse response,
			AuthenticationException failed) throws IOException {
	}    

    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return authenticationEntryPoint;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void init(FilterConfig arg0) throws ServletException {}

    public boolean isIgnoreFailure() {
        return ignoreFailure;
    }

    public void setAuthenticationDetailsSource(AuthenticationDetailsSource authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setIgnoreFailure(boolean ignoreFailure) {
        this.ignoreFailure = ignoreFailure;
    }

    public void setRememberMeServices(RememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
    }

	public ApplicationEventPublisher getEventPublisher() {
		return eventPublisher;
	}

	public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	public AuthenticationDetailsSource getAuthenticationDetailsSource() {
		return authenticationDetailsSource;
	}

	public RememberMeServices getRememberMeServices() {
		return rememberMeServices;
	}

}