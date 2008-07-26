package org.openuss.security.acegi.shibboleth;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.verify;

import java.io.IOException;
import java.util.TimeZone;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.acegisecurity.AuthenticationManager;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.MockAuthenticationManager;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.acegisecurity.ui.rememberme.RememberMeProcessingFilter;
import org.acegisecurity.ui.savedrequest.SavedRequest;
import org.acegisecurity.util.FilterChainProxy;
import org.acegisecurity.util.PortResolverImpl;
import org.hibernate.SessionFactory;
import org.openuss.TestUtility;
import org.openuss.framework.web.acegi.shibboleth.ShibbolethUserDetails;
import org.openuss.framework.web.acegi.shibboleth.ShibbolethUserDetailsImpl;
import org.openuss.messaging.MessageService;
import org.openuss.migration.UserMigrationUtility;
import org.openuss.migration.UserMigrationUtilityImpl;
import org.openuss.security.SecurityDomainUtility;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import junit.framework.TestCase;

public class PlexusShibbolethIntegrationTest extends AbstractTransactionalDataSourceSpringContextTests {
    private final String SHIBBOLETHUSERNAMEHEADERKEY = "SHIB_REMOTE_USER";
	private final String SHIBBOLETHFIRSTNAMEHEADERKEY = "Shib-Person-givennam";
	private final String SHIBBOLETHLASTNAMEHEADERKEY = "Shib-Person-sn";
	private final String SHIBBOLETHEMAILHEADERKEY = "Shib-Person-mail";
	private final String KEY = "shibboleth";
	private final String DEFAULTDOMAINNAME = "wwu";
	private final Long DEFAULTDOMAINID = 1006L;
	private final String DEFAULTROLE = "ROLE_SHIBBOLETHUSER";
	private final String USERNAME = "test";
	private final String FIRSTNAME = "Joe";
	private final String LASTNAME = "Sixpack";
	private final String EMAIL = "j_sixpack42@acegisecurity.org";
	private final String PASSWORD = "password";
	private final String LOCALE = "de";
	private final String SCHEME = "https";
	private final String SERVERNAME = "localhost:8080";
	private final String CONTEXTPATH = "/plexus-web";
	private final String SECUREDVIEWSURL = "/views/secured/user/profile.faces";
	private final String NOTSECUREDVIEWSURL = "/views/public/login/logout.faces";
	private final String SECUREDRSSFEEDURL = "/rss/secured/documents.xml?course=2236";
	private final String NOTSECUREDRSSFEEDURL = "/rss/public/institute.xml?institute=2192";
	private final String DEFAULTTARGETURL = "/views/welcome.faces";
	private final String LOGINFORMURL = "/views/public/login/login.faces";
	private final String MIGRATIONTARGETURL = "/views/secured/migration/migration.faces";
	private final String SAVEDREQUESTKEY = AbstractProcessingFilter.ACEGI_SAVED_REQUEST_KEY;
	
	private MockHttpServletRequest request;
	private MockFilterConfig config;
	private MockHttpServletResponse response;
	
//	private PlexusShibbolethAuthenticationProcessingFilter filter = new PlexusShibbolethAuthenticationProcessingFilter();
//	private PlexusShibbolethAuthenticationProvider provider = new PlexusShibbolethAuthenticationProvider();
//
//	private PlexusShibbolethAuthenticationProcessingFilter filterWithoutMigration = new PlexusShibbolethAuthenticationProcessingFilter();
//	private PlexusShibbolethAuthenticationProvider providerWithoutMigration = new PlexusShibbolethAuthenticationProvider();

//	public void setUp() {
//		SecurityContextHolder.clearContext();
//		// Setup plexus shibboleth configuration
////		filter.setAuthenticationManager(authenticationManager);
//		filter.setKey(KEY);
//		filter.setShibbolethUsernameHeaderKey(SHIBBOLETHUSERNAMEHEADERKEY);
//		filter.setShibbolethFirstNameHeaderKey(SHIBBOLETHFIRSTNAMEHEADERKEY);
//		filter.setShibbolethLastNameHeaderKey(SHIBBOLETHLASTNAMEHEADERKEY);
//		filter.setShibbolethEmailHeaderKey(SHIBBOLETHEMAILHEADERKEY);
//		filter.setDefaultDomainId(DEFAULTDOMAINID);
//		filter.setDefaultDomainName(DEFAULTDOMAINNAME);
//		filter.setDefaultRole(DEFAULTROLE);
//		filter.setOnlyProcessFilterProcessesUrlEnabled(false);
//		filter.setProcessEachUrlEnabled(true);
//		filter.setReturnAfterSuccessfulAuthentication(true);
//		filter.setReturnAfterUnsuccessfulAuthentication(false);
//		filter.setRedirectOnAuthenticationSuccessEnabled(true);
//		filter.setRedirectOnAuthenticationFailureEnabled(false);
//		filter.setUseRelativeContext(false);
//		filter.setDefaultTargetUrl(DEFAULTTARGETURL);
//		filter.setMigrationTargetUrl(MIGRATIONTARGETURL);
//		filter.setAuthenticationFailureUrl("/nothing, filter is configured not to redirect on authentication failure.");
//
//
////		filterWithoutMigration.setAuthenticationManager(authenticationManager);
//		filterWithoutMigration.setKey(KEY);
//		filterWithoutMigration.setShibbolethUsernameHeaderKey(SHIBBOLETHUSERNAMEHEADERKEY);
//		filterWithoutMigration.setShibbolethFirstNameHeaderKey(SHIBBOLETHFIRSTNAMEHEADERKEY);
//		filterWithoutMigration.setShibbolethLastNameHeaderKey(SHIBBOLETHLASTNAMEHEADERKEY);
//		filterWithoutMigration.setShibbolethEmailHeaderKey(SHIBBOLETHEMAILHEADERKEY);
//		filterWithoutMigration.setDefaultDomainId(DEFAULTDOMAINID);
//		filterWithoutMigration.setDefaultDomainName(DEFAULTDOMAINNAME);
//		filterWithoutMigration.setDefaultRole(DEFAULTROLE);
//		filterWithoutMigration.setOnlyProcessFilterProcessesUrlEnabled(false);
//		filterWithoutMigration.setProcessEachUrlEnabled(true);
//		filterWithoutMigration.setReturnAfterSuccessfulAuthentication(false);
//		filterWithoutMigration.setReturnAfterUnsuccessfulAuthentication(false);
//		filterWithoutMigration.setRedirectOnAuthenticationSuccessEnabled(false);
//		filterWithoutMigration.setRedirectOnAuthenticationFailureEnabled(false);
//		filterWithoutMigration.setDefaultTargetUrl("/nothing, filter is configured not to redirect on authentication success.");
//		filterWithoutMigration.setAuthenticationFailureUrl("/nothing, filter is configured not to redirect on authentication failure.");
//
//	}
	// Tests for plexus configuration
	
	protected SecurityService securityService;
	protected SessionFactory sessionFactory;
	protected TestUtility testUtility;
	protected PlexusShibbolethAuthenticationProcessingFilter shibbolethProcessingFilter;
	protected PlexusShibbolethAuthenticationProvider shibbolethAuthenticationProvider;
	protected PlexusShibbolethAuthenticationProcessingFilter shibbolethProcessingFilterWithoutMigration;
	protected PlexusShibbolethAuthenticationProvider shibbolethAuthenticationProviderWithoutMigration;
	protected FilterChainProxy securityFilterChainProxy;
	// The application filter chain.
	protected MockFilterChain chain = new MockFilterChain(true);

	//~ Constructor
	public PlexusShibbolethIntegrationTest() {
		super();
		super.setAutowireMode(AbstractTransactionalDataSourceSpringContextTests.AUTOWIRE_BY_NAME);
	}

	//~ Convenience methods
	
	private MockHttpServletRequest createMockRequest(String url) {
		MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath(url);
		request.setScheme(SCHEME);
        request.setServerName(SERVERNAME);
        request.setContextPath(CONTEXTPATH);
        request.setRequestURI(request.getContextPath()+url);
        
        request.addHeader(SHIBBOLETHUSERNAMEHEADERKEY, USERNAME);
        request.addHeader(SHIBBOLETHFIRSTNAMEHEADERKEY, FIRSTNAME);
        request.addHeader(SHIBBOLETHLASTNAMEHEADERKEY, LASTNAME);
        request.addHeader(SHIBBOLETHEMAILHEADERKEY, EMAIL);
		return request;
	}
		
    private SavedRequest makeSavedRequestForUrl() {
        MockHttpServletRequest request = createMockRequest(SECUREDVIEWSURL);
        return new SavedRequest(request, new PortResolverImpl());
    }

	private UserInfo createUserInfo() {
		UserInfo user = new UserInfo();
		user.setUsername(USERNAME);
		user.setPassword(PASSWORD);
		user.setEmail(EMAIL);
		user.setLocale(LOCALE);
		user.setTimezone(TimeZone.getDefault().getID());		
		user.setFirstName(FIRSTNAME);
		user.setLastName(LASTNAME);
		return user;
	}
    
    private void generateUnmigratedDisabledUser() {
		securityService.createUser(createUserInfo());
	}
	
	private void generateUnmigratedEnabledUser() {
		UserInfo user = createUserInfo();
		user.setEnabled(true);
		securityService.createUser(user);
	}
	
	private void generateMigratedDisabledUser() {
		UserInfo user = createUserInfo();
		user.setUsername(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME));
		securityService.createUser(user);		
	}
	
	private void generateMigratedEnabledUserNoReconcilationNecessary() {
		UserInfo user = createUserInfo();
		user.setUsername(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME));
		user.setEnabled(true);
		securityService.createUser(user);		
	}

	private void generateMigratedEnabledUserToBeReconciled() {
		UserInfo user = createUserInfo();
		user.setUsername(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME));
		user.setEmail("acme@acme.org");
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setEnabled(true);
		securityService.createUser(user);		
	}

	private void generateLockedUser() {
		UserInfo user = createUserInfo();
		user.setEnabled(true);
		user.setAccountLocked(true);
		securityService.createUser(user);		
	}
	
	private void generateAccountExpiredUser() {
		UserInfo user = createUserInfo();
		user.setEnabled(true);
		user.setAccountExpired(true);
		securityService.createUser(user);		
	}
	
	private void generateCredentialsExpiredUser() {
		UserInfo user = createUserInfo();
		user.setEnabled(true);
		user.setAccountExpired(true);
		securityService.createUser(user);
	}
	
	public void testInjection() {
		assertNotNull(securityService);
		assertNotNull(shibbolethProcessingFilter);
		assertTrue(shibbolethProcessingFilter.isMigrationEnabled());
		assertNotNull(shibbolethAuthenticationProvider);
		assertTrue(shibbolethAuthenticationProvider.isMigrationEnabled());
		assertNotNull(shibbolethProcessingFilterWithoutMigration);
		assertFalse(shibbolethProcessingFilterWithoutMigration.isMigrationEnabled());
		assertNotNull(shibbolethAuthenticationProviderWithoutMigration);
		assertFalse(shibbolethAuthenticationProviderWithoutMigration.isMigrationEnabled());
		assertNotNull(securityFilterChainProxy);
	}

	
	public void testUserCreation() {
		generateUnmigratedEnabledUser();
		UserInfo user = securityService.getUserByName(USERNAME);
		user.setUsername(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, user.getUsername()));
		securityService.saveUser(user);
		assertNotNull(user);
		assertNotNull(user.getId());
		assertTrue(user.isCentralUser());
	}
	
    public void testShibbolethRequestHeadersNotPresent() throws Exception {
        chain.resetCount();
    	request = new MockHttpServletRequest();
        request.setServletPath(SECUREDVIEWSURL);
        request.setScheme(SCHEME);
        request.setServerName(SERVERNAME);
        request.setContextPath(CONTEXTPATH);
        request.setRequestURI(request.getContextPath()+request.getServletPath());
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test SECUREDVIEWSURL. Application filter not invoked, since we redirect to login form url.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertEquals(SCHEME+"://"+SERVERNAME+CONTEXTPATH+LOGINFORMURL, response.getRedirectedUrl());
        assertEquals(request.getScheme()+"://"+request.getServerName()+request.getRequestURI(), ((SavedRequest)request.getSession().getAttribute(SAVEDREQUESTKEY)).getFullRequestUrl());
        assertEquals(0, chain.getCount());

        chain.resetCount();
        request = new MockHttpServletRequest();
        request.setServletPath(NOTSECUREDVIEWSURL);
        request.setScheme(SCHEME);
        request.setServerName(SERVERNAME);
        request.setContextPath(CONTEXTPATH);
        request.setRequestURI(request.getContextPath()+request.getServletPath());
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test NOTSECUREDVIEWSURL. Application filter invoked, since access is granted.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
        assertNull(request.getSession().getAttribute(SAVEDREQUESTKEY));
        assertEquals(1, chain.getCount());

        // Test for other URLs -> unsecured view, (un)secured rss
        
        
//        String savedRequestUrl = ((SavedRequest)request.getSession().getAttribute(AbstractProcessingFilter.ACEGI_SAVED_REQUEST_KEY)).getFullRequestUrl();
//        String redirectUrl = response.getRedirectedUrl();
    }

    public void testShibbolethRequestHeadersCleared() throws Exception {
        // Setup our HTTP request with headers cleared. Shibboleth service provider clears request headers to prevent spoofing.
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/j_mock_post");
        request.setScheme("https");
        request.setServerName("www.example.com");
        request.setRequestURI(CONTEXTPATH+SECUREDVIEWSURL);
        request.setContextPath(CONTEXTPATH);
      
        request.addHeader(SHIBBOLETHUSERNAMEHEADERKEY, "");
        request.addHeader(SHIBBOLETHFIRSTNAMEHEADERKEY, "");
        request.addHeader(SHIBBOLETHLASTNAMEHEADERKEY, "");
        request.addHeader(SHIBBOLETHEMAILHEADERKEY, "");

        boolean continueFilteringIfShibbolethHeadersAreCleared = true;
        // Setup our expectation that the filter chain will not be invoked, as we redirect to authenticationFailureUrl
        MockFilterChain chain = new MockFilterChain(continueFilteringIfShibbolethHeadersAreCleared);

        // Setup requiresAuthentication switches.
        boolean onlyProcessFilterProcessesUrlEnabled = false;
        boolean processEachUrlEnabled = true;
        
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();
        // Test
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
/*
    public void testSuccessfulAuthenticationWithoutRedirectToMigrationPageForMigratedUser() throws Exception {
		
	    // Setup to return.
	    boolean returnAfterSuccessfulAuthentication = true;
	    // Setup our expectation that the filter chain will not be invoked.
	    MockFilterChain chain = new MockFilterChain(!returnAfterSuccessfulAuthentication);
	
        // Setup authentication manager
        AuthenticationManager authManager = new AuthenticationManager()
        {public org.acegisecurity.Authentication authenticate(org.acegisecurity.Authentication authentication) throws org.acegisecurity.AuthenticationException {
        	return new UsernamePasswordAuthenticationToken(USERNAME,"protected",new GrantedAuthority[]{new GrantedAuthorityImpl(DEFAULTROLE)});}};
        	
	    // Setup our test object, to grant access and redirect migrated user to defaultTargetUrl.
	    String defaultTargetUrl = "/foobar";
        filter.setFilterProcessesUrl("/j_mock_post");
	    filter.setDefaultTargetUrl(defaultTargetUrl);
	    filter.setAuthenticationManager(authManager);
	    filter.setReturnAfterSuccessfulAuthentication(returnAfterSuccessfulAuthentication);
		filter.setMigrationTargetUrl(MIGRATIONTARGETURL);
	    // Test
	    executeFilterInContainerSimulator(config, filter, request, response, chain);
	    assertEquals(request.getContextPath()+defaultTargetUrl, response.getRedirectedUrl());
	    assertTrue(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	
	    SecurityContextHolder.clearContext();
	    response = new MockHttpServletResponse();
        // Setup our HTTP request
    	request.getSession().setAttribute(AbstractProcessingFilter.ACEGI_SAVED_REQUEST_KEY, makeSavedRequestForUrl());
	    // Setup our test object, to grant access and redirect migrated user to url within SavedRequest.
    	boolean alwaysUseDefaultTargetUrl = false;
    	filter.setAlwaysUseDefaultTargetUrl(alwaysUseDefaultTargetUrl);
	    // Test
	    executeFilterInContainerSimulator(config, filter, request, response, chain);
	    assertEquals(makeSavedRequestForUrl().getFullRequestUrl(), response.getRedirectedUrl());
	    assertTrue(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken);
    }

    public void testSuccessfulAuthenticationWithoutRedirectButContinuedProcessingOfFilterChain() throws Exception {
		
	    // Setup not to return, but to continue chain.
	    boolean returnAfterSuccessfulAuthentication = false;
	    // Setup our expectation that the filter chain will be invoked.
	    MockFilterChain chain = new MockFilterChain(!returnAfterSuccessfulAuthentication);
	    
	    // Setup our test object, to grant access
	    filter.setFilterProcessesUrl("/j_mock_post");
	    filter.setDefaultTargetUrl("/foobar");
	    filter.setAuthenticationManager(new MockAuthenticationManager(true));
	    filter.setReturnAfterSuccessfulAuthentication(returnAfterSuccessfulAuthentication);
		filter.setRedirectOnAuthenticationSuccessEnabled(false);
	    // Test
	    executeFilterInContainerSimulator(config, filter, request, response, chain);
	    assertNull(response.getRedirectedUrl());
	    assertNotNull(SecurityContextHolder.getContext().getAuthentication());
	    assertTrue(SecurityContextHolder.getContext().getAuthentication().getDetails() instanceof ShibbolethUserDetails);
	    ShibbolethUserDetails sud = (ShibbolethUserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
	    assertEquals(USERNAME,(String)sud.getAttributes().get(ShibbolethUserDetailsImpl.USERNAME_KEY).get());
	    assertEquals(FIRSTNAME,(String)sud.getAttributes().get(ShibbolethUserDetailsImpl.FIRSTNAME_KEY).get());
	    assertEquals(LASTNAME,(String)sud.getAttributes().get(ShibbolethUserDetailsImpl.LASTNAME_KEY).get());
	    assertEquals(EMAIL,(String)sud.getAttributes().get(ShibbolethUserDetailsImpl.EMAIL_KEY).get());
	    assertEquals(DEFAULTDOMAINNAME,(String)sud.getAttributes().get(ShibbolethUserDetailsImpl.AUTHENTICATIONDOMAINNAME_KEY).get());
	    assertEquals(DEFAULTDOMAINID,(Long)sud.getAttributes().get(ShibbolethUserDetailsImpl.AUTHENTICATIONDOMAINID_KEY).get());
	    assertEquals(1, chain.getCount());
	}

    public void testUnsuccessfulAuthenticationWithoutRedirectButContinuedProcessingOfFilterChain() throws Exception {
	    // Setup not to return, but to continue chain.
	    boolean returnAfterUnsuccessfulAuthentication = false;
	    // Setup our expectation that the filter chain will be invoked.
	    MockFilterChain chain = new MockFilterChain(!returnAfterUnsuccessfulAuthentication);
	
	    // Setup our test object, to grant access
	    filter.setFilterProcessesUrl("/j_mock_post");
	    filter.setDefaultTargetUrl("/foobar");
	    filter.setAuthenticationManager(new MockAuthenticationManager(false));
	    filter.setReturnAfterSuccessfulAuthentication(returnAfterUnsuccessfulAuthentication);
		filter.setRedirectOnAuthenticationFailureEnabled(false);
	    // Test
	    executeFilterInContainerSimulator(config, filter, request, response, chain);
	    assertNull(response.getRedirectedUrl());
	    assertNull(SecurityContextHolder.getContext().getAuthentication());
	    assertEquals(1, chain.getCount());
    }
 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	protected void commit() {
		setComplete();
		endTransaction();
		startNewTransaction();
	}
	
	protected void flush() {
		sessionFactory.getCurrentSession().flush();
	}
	
	protected String[] getConfigLocations() {
		return new String[] { 
				"classpath*:applicationContext.xml", 
				"classpath*:applicationContext-beans.xml",
				"classpath*:applicationContext-lucene.xml",
				"classpath*:applicationContext-cache.xml", 
				"classpath*:applicationContext-messaging.xml",
				"classpath*:applicationContext-resources.xml",
				"classpath*:applicationContext-events.xml",
				"classpath*:testContext.xml", 
				"classpath*:testDataSource.xml",
				"classpath*:testShibbolethSecurity.xml"};
	}
//	"classpath*:applicationContext-aop.xml",

	//~ Inner Classes ==================================================================================================

    private class MockFilterChain implements FilterChain {
        private boolean expectToProceed;
        private int count = 0;

        public MockFilterChain(boolean expectToProceed) {
            this.expectToProceed = expectToProceed;
        }

        private MockFilterChain() {
            super();
        }

        public void doFilter(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
            if (expectToProceed) {
            	count++;
                assertTrue(true);
            } else {
                fail("Did not expect filter chain to proceed");
            }
        }

		public void resetCount() {
			setCount(0);
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}		
    }    

    public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	
	public SessionFactory getSessionFactory() {
	    return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) { 
	     this.sessionFactory = sessionFactory;
	}	
	
	public TestUtility getTestUtility() {
		return testUtility;
	}
	
	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public PlexusShibbolethAuthenticationProcessingFilter getShibbolethProcessingFilter() {
		return shibbolethProcessingFilter;
	}

	public void setShibbolethProcessingFilter(
			PlexusShibbolethAuthenticationProcessingFilter shibbolethProcessingFilter) {
		this.shibbolethProcessingFilter = shibbolethProcessingFilter;
	}

	public PlexusShibbolethAuthenticationProvider getShibbolethAuthenticationProvider() {
		return shibbolethAuthenticationProvider;
	}

	public void setShibbolethAuthenticationProvider(
			PlexusShibbolethAuthenticationProvider shibbolethAuthenticationProvider) {
		this.shibbolethAuthenticationProvider = shibbolethAuthenticationProvider;
	}

	public PlexusShibbolethAuthenticationProcessingFilter getShibbolethProcessingFilterWithoutMigration() {
		return shibbolethProcessingFilterWithoutMigration;
	}

	public void setShibbolethProcessingFilterWithoutMigration(
			PlexusShibbolethAuthenticationProcessingFilter shibbolethProcessingFilterWithoutMigration) {
		this.shibbolethProcessingFilterWithoutMigration = shibbolethProcessingFilterWithoutMigration;
	}

	public PlexusShibbolethAuthenticationProvider getShibbolethAuthenticationProviderWithoutMigration() {
		return shibbolethAuthenticationProviderWithoutMigration;
	}

	public void setShibbolethAuthenticationProviderWithoutMigration(
			PlexusShibbolethAuthenticationProvider shibbolethAuthenticationProviderWithoutMigration) {
		this.shibbolethAuthenticationProviderWithoutMigration = shibbolethAuthenticationProviderWithoutMigration;
	}

	public FilterChainProxy getSecurityFilterChainProxy() {
		return securityFilterChainProxy;
	}

	public void setSecurityFilterChainProxy(
			FilterChainProxy securityFilterChainProxy) {
		this.securityFilterChainProxy = securityFilterChainProxy;
	}
}
