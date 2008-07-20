package org.openuss.security.acegi.shibboleth;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.verify;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import junit.framework.TestCase;

import org.acegisecurity.AccountExpiredException;
import org.acegisecurity.AuthenticationManager;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.MockAuthenticationManager;
import org.acegisecurity.adapters.PrincipalAcegiUserToken;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.acegisecurity.ui.rememberme.TokenBasedRememberMeServices;
import org.acegisecurity.ui.savedrequest.SavedRequest;
import org.acegisecurity.util.PortResolverImpl;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class ShibbolethAuthenticationProcessingFilterTest extends TestCase {
	
    private final String SHIBBOLETHUSERNAMEHEADERKEY = "REMOTE_USER";
	private final String SHIBBOLETHFIRSTNAMEHEADERKEY = "SHIB_FIRSTNAME";
	private final String SHIBBOLETHLASTNAMEHEADERKEY = "SHIB_LASTNAME";
	private final String SHIBBOLETHEMAILHEADERKEY = "SHIB_MAIL";
	private final String KEY = "shib";
	private final String DEFAULTDOMAINNAME = "shibboleth";
	private final Long DEFAULTDOMAINID = 42L;
	private final String MIGRATIONTARGETURL = "views/migration/migration.html";
	private final String DEFAULTROLE = "ROLE_SHIBBOLETH";


	protected void setUp() throws Exception {
		super.setUp();
		SecurityContextHolder.clearContext();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		SecurityContextHolder.clearContext();
	}

	
	/* ************************************************************************
	 * Tests for superclass AbstractProcessingFilter.                         *
	 * These must also be passed by ShibbolethAuthenticationProcessingFilter. *
	 * Test taken from Acegi framework, but extended and modified.            *
	 **************************************************************************/	
	
	private MockHttpServletRequest createMockRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        request.setServletPath("/j_mock_post");
        request.setScheme("http");
        request.setServerName("www.example.com");
        request.setRequestURI("/mycontext/j_mock_post");
        request.setContextPath("/mycontext");
        
        request.addHeader(SHIBBOLETHUSERNAMEHEADERKEY, "j_sixpack42");
        request.addHeader(SHIBBOLETHFIRSTNAMEHEADERKEY, "Joe");
        request.addHeader(SHIBBOLETHLASTNAMEHEADERKEY, "Sixpack");
        request.addHeader(SHIBBOLETHEMAILHEADERKEY, "j_sixpack42@acegisecurity.org");

        return request;
    }

    private void executeFilterInContainerSimulator(FilterConfig filterConfig, Filter filter, ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filter.init(filterConfig);
        filter.doFilter(request, response, filterChain);
        filter.destroy();
    }

    private SavedRequest makeSavedRequestForUrl() {
        MockHttpServletRequest request = createMockRequest();
        request.setServletPath("/some_protected_file.html");
        request.setScheme("http");
        request.setServerName("www.example.com");
        request.setRequestURI("/mycontext/some_protected_file.html");

        return new SavedRequest(request, new PortResolverImpl());
    }


    public void testDefaultProcessesFilterUrlWithPathParameter() {
        MockHttpServletRequest request = createMockRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ShibbolethAuthenticationProcessingFilter filter = new ShibbolethAuthenticationProcessingFilter();
        filter.setFilterProcessesUrl("/j_acegi_security_check");

        request.setRequestURI("/mycontext/j_acegi_security_check;jsessionid=I8MIONOSTHOR");
        assertTrue(filter.requiresAuthentication(request, response));
    }

    public void testDoFilterWithNonHttpServletRequestDetected() throws Exception {
        AbstractProcessingFilter filter = new ShibbolethAuthenticationProcessingFilter();

        try {
            filter.doFilter(null, new MockHttpServletResponse(), new MockFilterChain());
            fail("Should have thrown ServletException");
        } catch (ServletException expected) {
            assertEquals("Can only process HttpServletRequest", expected.getMessage());
        }
    }

    public void testDoFilterWithNonHttpServletResponseDetected() throws Exception {
        AbstractProcessingFilter filter = new ShibbolethAuthenticationProcessingFilter();

        try {
            filter.doFilter(new MockHttpServletRequest(null, null), null, new MockFilterChain());
            fail("Should have thrown ServletException");
        } catch (ServletException expected) {
            assertEquals("Can only process HttpServletResponse", expected.getMessage());
        }
    }

    public void testFailedAuthenticationRedirectsAppropriately() throws Exception {
        // Setup our HTTP request
        MockHttpServletRequest request = createMockRequest();

        // Setup our filter configuration
        MockFilterConfig config = new MockFilterConfig(null, null);

        boolean returnAfterUnsuccessfulAuthentication = true;
        // Setup our expectation that the filter chain will not be invoked, as we redirect to authenticationFailureUrl
        MockFilterChain chain = new MockFilterChain(!returnAfterUnsuccessfulAuthentication);
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Setup authentication manager
        MockAuthenticationManager authenticationManager = new MockAuthenticationManager(false);
        
        // Setup our test object, to deny access
        ShibbolethAuthenticationProcessingFilter filter = new ShibbolethAuthenticationProcessingFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setShibbolethUsernameHeaderKey(SHIBBOLETHUSERNAMEHEADERKEY);
        filter.setShibbolethFirstNameHeaderKey(SHIBBOLETHFIRSTNAMEHEADERKEY);
        filter.setShibbolethLastNameHeaderKey(SHIBBOLETHLASTNAMEHEADERKEY);
        filter.setShibbolethEmailHeaderKey(SHIBBOLETHEMAILHEADERKEY);
        filter.setKey(KEY);
        filter.setDefaultDomainId(DEFAULTDOMAINID);
        filter.setDefaultDomainName(DEFAULTDOMAINNAME);
        filter.setRedirectEnabled(true);
        filter.setReturnAfterUnsuccessfulAuthentication(returnAfterUnsuccessfulAuthentication);
        filter.setReturnAfterSuccessfulAuthentication(false);
        filter.setAuthenticationFailureUrl("/failed.jsp");

        // Test
        executeFilterInContainerSimulator(config, filter, request, response, chain);

        assertEquals("/mycontext/failed.jsp", response.getRedirectedUrl());
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        //Prepare again, this time using the exception mapping
        
        filter = new ShibbolethAuthenticationProcessingFilter();
        AuthenticationManager authManager = new AuthenticationManager()
        {public org.acegisecurity.Authentication authenticate(org.acegisecurity.Authentication authentication) throws org.acegisecurity.AuthenticationException {
        	throw new AccountExpiredException("Account expired.");}};
        filter.setAuthenticationManager(authManager);
        filter.setShibbolethUsernameHeaderKey(SHIBBOLETHUSERNAMEHEADERKEY);
        filter.setShibbolethFirstNameHeaderKey(SHIBBOLETHFIRSTNAMEHEADERKEY);
        filter.setShibbolethLastNameHeaderKey(SHIBBOLETHLASTNAMEHEADERKEY);
        filter.setShibbolethEmailHeaderKey(SHIBBOLETHEMAILHEADERKEY);
        filter.setKey(KEY);
        filter.setDefaultDomainId(DEFAULTDOMAINID);
        filter.setDefaultDomainName(DEFAULTDOMAINNAME);
        filter.setRedirectEnabled(true);
        filter.setReturnAfterUnsuccessfulAuthentication(true);
        filter.setReturnAfterSuccessfulAuthentication(false);
        filter.setAuthenticationFailureUrl("/failed.jsp");

        Properties exceptionMappings = filter.getExceptionMappings();
        exceptionMappings.setProperty(AccountExpiredException.class.getName(), "/accountExpired.jsp");
        filter.setExceptionMappings(exceptionMappings);
        response = new MockHttpServletResponse();

        // Test
        executeFilterInContainerSimulator(config, filter, request, response, chain);

        assertEquals("/mycontext/accountExpired.jsp", response.getRedirectedUrl());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(8*1024, response.getBufferSize());
    }

    public void testFilterProcessesUrlVariationsRespected() throws Exception {
        // Setup our HTTP request
        MockHttpServletRequest request = createMockRequest();
        request.setServletPath("/j_OTHER_LOCATION");
        request.setRequestURI("/mycontext/j_OTHER_LOCATION");

        // Setup our filter configuration
        MockFilterConfig config = new MockFilterConfig(null, null);

        boolean returnAfterSuccessfulAuthentication = true;
        // Setup our expectation that the filter chain will not be invoked, as we redirect to defaultTargetUrl
        MockFilterChain chain = new MockFilterChain(!returnAfterSuccessfulAuthentication);
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Setup authentication manager
        AuthenticationManager authManager = new AuthenticationManager()
        {public org.acegisecurity.Authentication authenticate(org.acegisecurity.Authentication authentication) throws org.acegisecurity.AuthenticationException {
        	return new UsernamePasswordAuthenticationToken("test","protected",new GrantedAuthority[]{new GrantedAuthorityImpl(DEFAULTROLE)});}};
 
        // Setup our test object, to grant access
        ShibbolethAuthenticationProcessingFilter filter = new ShibbolethAuthenticationProcessingFilter();
        filter.setAuthenticationManager(authManager);
        filter.setShibbolethUsernameHeaderKey(SHIBBOLETHUSERNAMEHEADERKEY);
        filter.setShibbolethFirstNameHeaderKey(SHIBBOLETHFIRSTNAMEHEADERKEY);
        filter.setShibbolethLastNameHeaderKey(SHIBBOLETHLASTNAMEHEADERKEY);
        filter.setShibbolethEmailHeaderKey(SHIBBOLETHEMAILHEADERKEY);
        filter.setKey(KEY);
        filter.setDefaultDomainId(DEFAULTDOMAINID);
        filter.setDefaultDomainName(DEFAULTDOMAINNAME);
        filter.setRedirectEnabled(true);
        filter.setReturnAfterUnsuccessfulAuthentication(true);
        filter.setReturnAfterSuccessfulAuthentication(returnAfterSuccessfulAuthentication);
        filter.setFilterProcessesUrl("/j_OTHER_LOCATION");
        filter.setDefaultTargetUrl("/logged_in.jsp");

        // Test
        executeFilterInContainerSimulator(config, filter, request, response, chain);
        assertEquals("/mycontext/logged_in.jsp", response.getRedirectedUrl());
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("test", SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        assertEquals(8*1024, response.getBufferSize());
    }

    public void testGettersSetters() {
        ShibbolethAuthenticationProcessingFilter filter = new ShibbolethAuthenticationProcessingFilter();
        assertNotNull(filter.getRememberMeServices());
        filter.setRememberMeServices(new TokenBasedRememberMeServices());
        assertEquals(TokenBasedRememberMeServices.class, filter.getRememberMeServices().getClass());

        filter.setAuthenticationFailureUrl("/x");
        assertEquals("/x", filter.getAuthenticationFailureUrl());

        filter.setAuthenticationManager(new MockAuthenticationManager());
        assertTrue(filter.getAuthenticationManager() != null);

        filter.setDefaultTargetUrl("/default");
        assertEquals("/default", filter.getDefaultTargetUrl());

        filter.setFilterProcessesUrl("/p");
        assertEquals("/p", filter.getFilterProcessesUrl());

        filter.setAuthenticationFailureUrl("/fail");
        assertEquals("/fail", filter.getAuthenticationFailureUrl());
        
        // DefaultRolePrefix as set within filter by default.
        String defaultRolePrefix = "ROLE_";
        
        String defaultRoleWithDefaultPrefix = "ROLE_SHIBBOLETH";
        filter.setDefaultRole(defaultRoleWithDefaultPrefix);
        assertEquals(defaultRoleWithDefaultPrefix, filter.getDefaultRole());
        
        // Test: Add prefix, if default role does not contain it.
        String defaultRoleWithoutDefaultPrefix = "SHIBBOLETH";
        filter.setDefaultRole(defaultRoleWithoutDefaultPrefix);
        assertEquals(defaultRolePrefix+defaultRoleWithoutDefaultPrefix, filter.getDefaultRole());

        defaultRolePrefix = "SOME_PREFIX_";
        filter.setDefaultRolePrefix(defaultRolePrefix);
        filter.setDefaultRole(defaultRoleWithoutDefaultPrefix);
        assertEquals(defaultRolePrefix+defaultRoleWithoutDefaultPrefix, filter.getDefaultRole());
        
        filter.setShibbolethUsernameHeaderKey(SHIBBOLETHUSERNAMEHEADERKEY);
        assertEquals(SHIBBOLETHUSERNAMEHEADERKEY, filter.getShibbolethUsernameHeaderKey());

        filter.setShibbolethFirstNameHeaderKey(SHIBBOLETHFIRSTNAMEHEADERKEY);
        assertEquals(SHIBBOLETHFIRSTNAMEHEADERKEY, filter.getShibbolethFirstNameHeaderKey());

        filter.setShibbolethLastNameHeaderKey(SHIBBOLETHLASTNAMEHEADERKEY);
        assertEquals(SHIBBOLETHLASTNAMEHEADERKEY, filter.getShibbolethLastNameHeaderKey());

        filter.setShibbolethEmailHeaderKey(SHIBBOLETHEMAILHEADERKEY);
        assertEquals(SHIBBOLETHEMAILHEADERKEY, filter.getShibbolethEmailHeaderKey());
        
        filter.setKey(KEY);
        assertEquals(KEY, filter.getKey());
        
        filter.setDefaultDomainId(DEFAULTDOMAINID);
        assertEquals(DEFAULTDOMAINID, filter.getDefaultDomainId());
        
        filter.setDefaultDomainName(DEFAULTDOMAINNAME);
        assertEquals(DEFAULTDOMAINNAME, filter.getDefaultDomainName());
        
        filter.setMigrationTargetUrl(MIGRATIONTARGETURL);
        assertEquals(MIGRATIONTARGETURL, filter.getMigrationTargetUrl());
        assertTrue(filter.isMigrationEnabled());

        boolean alwaysUseDefaultTargetUrl = true;
        filter.setAlwaysUseDefaultTargetUrl(alwaysUseDefaultTargetUrl);
        assertTrue(filter.isAlwaysUseDefaultTargetUrl());
                
        boolean redirectEnabled = true;
        filter.setRedirectEnabled(redirectEnabled);
        assertTrue(filter.isRedirectEnabled());
        
        boolean returnAfterSuccessfulAuthentication = true;
        filter.setReturnAfterSuccessfulAuthentication(returnAfterSuccessfulAuthentication);
        assertTrue(filter.isReturnAfterSuccessfulAuthentication());
        
        boolean returnAfterUnsuccessfulAuthentication = true;
        filter.setReturnAfterUnsuccessfulAuthentication(returnAfterUnsuccessfulAuthentication);
        assertTrue(filter.isReturnAfterUnsuccessfulAuthentication());
        
    }

    public void testDefaultUrlMuststartWithSlashOrHttpScheme() {
        ShibbolethAuthenticationProcessingFilter filter = new ShibbolethAuthenticationProcessingFilter();

        filter.setDefaultTargetUrl("/acceptableRelativeUrl");
        filter.setDefaultTargetUrl("http://some.site.org/index.html");
        filter.setDefaultTargetUrl("https://some.site.org/index.html");

        try {
            filter.setDefaultTargetUrl("missingSlash");
            fail("Shouldn't accept default target without leading slash");
        } catch (IllegalArgumentException expected) {}
    }

    public void testIgnoresAnyServletPathOtherThanFilterProcessesUrl()
        throws Exception {
        // Setup our HTTP request
        MockHttpServletRequest request = createMockRequest();
        request.setServletPath("/some.file.html");
        request.setRequestURI("/mycontext/some.file.html");

        // Setup our filter configuration
        MockFilterConfig config = new MockFilterConfig(null, null);

        // Setup our expectation that the filter chain will be invoked, as our request is for a page the filter isn't monitoring
        MockFilterChain chain = new MockFilterChain(true);
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Setup our test object, to deny access
        ShibbolethAuthenticationProcessingFilter filter = new ShibbolethAuthenticationProcessingFilter();

        // Test
        executeFilterInContainerSimulator(config, filter, request, response, chain);
    }

    public void testNormalOperationWithDefaultFilterProcessesUrl()
        throws Exception {
        // Setup our HTTP request
        MockHttpServletRequest request = createMockRequest();

        // Setup our filter configuration
        MockFilterConfig config = new MockFilterConfig(null, null);

        // Setup our expectation that the filter chain will not be invoked, as we redirect to defaultTargetUrl
        MockFilterChain chain = new MockFilterChain(false);
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Setup our test object, to grant access
        ShibbolethAuthenticationProcessingFilter filter = new ShibbolethAuthenticationProcessingFilter();
        filter.setFilterProcessesUrl("/j_mock_post");
        filter.setDefaultTargetUrl("/logged_in.jsp");
        filter.setAuthenticationFailureUrl("/failure.jsp");
        filter.setAuthenticationManager(new MockAuthenticationManager(true));
        filter.afterPropertiesSet();

        // Test
        executeFilterInContainerSimulator(config, filter, request, response, chain);
        assertEquals("/mycontext/logged_in.jsp", response.getRedirectedUrl());
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("test", SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        assertEquals(8*1024, response.getBufferSize());
    }

    public void testStartupDetectsInvalidAuthenticationFailureUrl()
        throws Exception {
        AbstractProcessingFilter filter = new ShibbolethAuthenticationProcessingFilter();
        filter.setAuthenticationManager(new MockAuthenticationManager());
        filter.setDefaultTargetUrl("/");
        filter.setFilterProcessesUrl("/j_acegi_security_check");

        try {
            filter.afterPropertiesSet();
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertEquals("authenticationFailureUrl must be specified", expected.getMessage());
        }
    }

    public void testStartupDetectsInvalidAuthenticationManager()
        throws Exception {
        AbstractProcessingFilter filter = new ShibbolethAuthenticationProcessingFilter();
        filter.setAuthenticationFailureUrl("/failed.jsp");
        filter.setDefaultTargetUrl("/");
        filter.setFilterProcessesUrl("/j_acegi_security_check");

        try {
            filter.afterPropertiesSet();
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertEquals("authenticationManager must be specified", expected.getMessage());
        }
    }

    public void testStartupDetectsInvalidDefaultTargetUrl()
        throws Exception {
        AbstractProcessingFilter filter = new ShibbolethAuthenticationProcessingFilter();
        filter.setAuthenticationFailureUrl("/failed.jsp");
        filter.setAuthenticationManager(new MockAuthenticationManager());
        filter.setFilterProcessesUrl("/j_acegi_security_check");

        try {
            filter.afterPropertiesSet();
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertEquals("defaultTargetUrl must be specified", expected.getMessage());
        }
    }

    public void testStartupDetectsInvalidFilterProcessesUrl()
        throws Exception {
        AbstractProcessingFilter filter = new ShibbolethAuthenticationProcessingFilter();
        filter.setAuthenticationFailureUrl("/failed.jsp");
        filter.setAuthenticationManager(new MockAuthenticationManager());
        filter.setDefaultTargetUrl("/");
        filter.setFilterProcessesUrl(null);

        try {
            filter.afterPropertiesSet();
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertEquals("filterProcessesUrl must be specified", expected.getMessage());
        }
    }

    public void testSuccessLoginThenFailureLoginResultsInSessionLosingToken()
        throws Exception {
        // Setup our HTTP request
        MockHttpServletRequest request = createMockRequest();

        // Setup our filter configuration
        MockFilterConfig config = new MockFilterConfig(null, null);

        // Setup our expectation that the filter chain will not be invoked, as we redirect to defaultTargetUrl
        MockFilterChain chain = new MockFilterChain(false);
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Setup our test object, to grant access
        ShibbolethAuthenticationProcessingFilter filter = new ShibbolethAuthenticationProcessingFilter();
        filter.setFilterProcessesUrl("/j_mock_post");
        filter.setDefaultTargetUrl("/logged_in.jsp");

        // Test
        executeFilterInContainerSimulator(config, filter, request, response, chain);
        assertEquals("/mycontext/logged_in.jsp", response.getRedirectedUrl());
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("test", SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        assertEquals(8*1024, response.getBufferSize());

        // Now try again but this time have filter deny access
        // Setup our HTTP request
        // Setup our expectation that the filter chain will not be invoked, as we redirect to authenticationFailureUrl
        chain = new MockFilterChain(false);
        response = new MockHttpServletResponse();

        // Setup our test object, to deny access
        filter = new ShibbolethAuthenticationProcessingFilter();
        filter.setFilterProcessesUrl("/j_mock_post");
        filter.setAuthenticationFailureUrl("/failed.jsp");

        // Test
        executeFilterInContainerSimulator(config, filter, request, response, chain);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    public void testSuccessfulAuthenticationButWithAlwaysUseDefaultTargetUrlCausesRedirectToDefaultTargetUrl()
        throws Exception {
        // Setup our HTTP request
        MockHttpServletRequest request = createMockRequest();
        request.getSession().setAttribute(AbstractProcessingFilter.ACEGI_SAVED_REQUEST_KEY, makeSavedRequestForUrl());

        // Setup our filter configuration
        MockFilterConfig config = new MockFilterConfig(null, null);

        // Setup our expectation that the filter chain will be invoked, as we want to go to the location requested in the session
        MockFilterChain chain = new MockFilterChain(true);
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Setup our test object, to grant access
        ShibbolethAuthenticationProcessingFilter filter = new ShibbolethAuthenticationProcessingFilter();
        filter.setFilterProcessesUrl("/j_mock_post");
        filter.setDefaultTargetUrl("/foobar");
        assertFalse(filter.isAlwaysUseDefaultTargetUrl()); // check default
        filter.setAlwaysUseDefaultTargetUrl(true);
        assertTrue(filter.isAlwaysUseDefaultTargetUrl()); // check changed

        // Test
        executeFilterInContainerSimulator(config, filter, request, response, chain);
        assertEquals("/mycontext/foobar", response.getRedirectedUrl());
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    public void testSuccessfulAuthenticationCausesRedirectToSessionSpecifiedUrl()
        throws Exception {
        // Setup our HTTP request
        MockHttpServletRequest request = createMockRequest();
        request.getSession().setAttribute(AbstractProcessingFilter.ACEGI_SAVED_REQUEST_KEY, makeSavedRequestForUrl());

        // Setup our filter configuration
        MockFilterConfig config = new MockFilterConfig(null, null);

        // Setup our expectation that the filter chain will be invoked, as we want to go to the location requested in the session
        MockFilterChain chain = new MockFilterChain(true);
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Setup our test object, to grant access
        ShibbolethAuthenticationProcessingFilter filter = new ShibbolethAuthenticationProcessingFilter();
        filter.setFilterProcessesUrl("/j_mock_post");

        // Test
        executeFilterInContainerSimulator(config, filter, request, response, chain);
        assertEquals(makeSavedRequestForUrl().getFullRequestUrl(), response.getRedirectedUrl());
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(8*1024, response.getBufferSize());
    }

    public void testFullDefaultTargetUrlDoesNotHaveContextPathPrepended() throws Exception {
        MockHttpServletRequest request = createMockRequest();
        MockFilterConfig config = new MockFilterConfig(null, null);

        MockFilterChain chain = new MockFilterChain(true);
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Setup our test object, to grant access
        ShibbolethAuthenticationProcessingFilter filter = new ShibbolethAuthenticationProcessingFilter();
        filter.setFilterProcessesUrl("/j_mock_post");
        filter.setDefaultTargetUrl("http://monkeymachine.co.uk/");
        filter.setAlwaysUseDefaultTargetUrl(true);

        executeFilterInContainerSimulator(config, filter, request, response, chain);
        assertEquals("http://monkeymachine.co.uk/", response.getRedirectedUrl());
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    //~ Inner Classes ==================================================================================================

    private class MockFilterChain implements FilterChain {
        private boolean expectToProceed;

        public MockFilterChain(boolean expectToProceed) {
            this.expectToProceed = expectToProceed;
        }

        private MockFilterChain() {
            super();
        }

        public void doFilter(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
            if (expectToProceed) {
                assertTrue(true);
            } else {
                fail("Did not expect filter chain to proceed");
            }
        }
    }    
}
