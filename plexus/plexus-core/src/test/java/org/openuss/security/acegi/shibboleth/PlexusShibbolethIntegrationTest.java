package org.openuss.security.acegi.shibboleth;

import java.io.IOException;
import java.util.TimeZone;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.context.HttpSessionContextIntegrationFilter;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.acegisecurity.ui.savedrequest.SavedRequest;
import org.acegisecurity.util.FilterChainProxy;
import org.acegisecurity.util.PortResolverImpl;
import org.hibernate.SessionFactory;
import org.openuss.TestUtility;
import org.openuss.security.SecurityDomainUtility;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * @author Peter Schuh
 *
 */
public class PlexusShibbolethIntegrationTest extends AbstractTransactionalDataSourceSpringContextTests {
    private final String SHIBBOLETHUSERNAMEHEADERKEY = "SHIB_REMOTE_USER";
	private final String SHIBBOLETHFIRSTNAMEHEADERKEY = "Shib-Person-givenname";
	private final String SHIBBOLETHLASTNAMEHEADERKEY = "Shib-Person-sn";
	private final String SHIBBOLETHEMAILHEADERKEY = "Shib-Person-mail";
	private final String DEFAULTDOMAINNAME = "wwu";
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
//	private final String MIGRATIONTARGETURL = "/views/secured/migration/migration.faces";
	private final String SAVEDREQUESTKEY = AbstractProcessingFilter.ACEGI_SAVED_REQUEST_KEY;
	
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private SavedRequest savedRequest;
		
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
		
    private SavedRequest makeSavedRequestForUrl(String url) {
        MockHttpServletRequest request = createMockRequest(url);
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
    
//    private void generateUnmigratedDisabledUser() {
//		securityService.createUser(createUserInfo());
//	}
//    
//    private void generateUnmigratedDisabledUserNotFindableByEmail() {
//    	UserInfo user = createUserInfo();
//    	user.setEmail("acme@acme.org");
//		securityService.createUser(user);
//    }
    
	private void generateUnmigratedEnabledUser() {
		UserInfo user = createUserInfo();
		user.setEnabled(true);
		securityService.createUser(user);
	}

//	private void generateMigratedDisabledUserNoReconcilationNecessary() {
//		securityService.createUser(createUserInfo());
//		UserInfo user = securityService.getUserByName(USERNAME);
//		user.setUsername(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME));
//		securityService.saveUser(user);
//	}
	
	private void generateMigratedEnabledUserNoReconcilationNecessary() {
		UserInfo user = createUserInfo();
		user.setEnabled(true);
		securityService.createUser(user);
		user = securityService.getUserByName(USERNAME);
		user.setUsername(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME));
		securityService.saveUser(user);
	}

//	private void generateMigratedEnabledUserToBeReconciled() {
//		UserInfo user = createUserInfo();
//		user.setFirstName("John");
//		user.setLastName("Doe");
//		user.setEnabled(true);
//		securityService.createUser(user);	
//		user = securityService.getUserByName(USERNAME);
//		user.setUsername(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME));
//		securityService.saveUser(user);
//	}
//
//	private void generateLockedUser() {
//		UserInfo user = createUserInfo();
//		user.setEnabled(true);
//		user.setAccountLocked(true);
//		securityService.createUser(user);		
//	}
//	
//	private void generateAccountExpiredUser() {
//		UserInfo user = createUserInfo();
//		user.setEnabled(true);
//		user.setAccountExpired(true);
//		securityService.createUser(user);		
//	}
//	
//	private void generateCredentialsExpiredUser() {
//		UserInfo user = createUserInfo();
//		user.setEnabled(true);
//		user.setAccountExpired(true);
//		securityService.createUser(user);
//	}
	
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
		user = securityService.getUserByName(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME));
		assertNotNull(user);
		assertNotNull(user.getId());
		assertTrue(user.isCentralUser());
	}
	
	//~ Tests for OpenUSS Plexus configuration

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
        // Test SECUREDVIEWSURL. Application filter chain is not invoked, since we redirect to login form url.
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
        // Test NOTSECUREDVIEWSURL. Application filter chain is invoked, since access is granted.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
        assertNull(request.getSession().getAttribute(SAVEDREQUESTKEY));
        assertEquals(1, chain.getCount());

        chain.resetCount();
        request = new MockHttpServletRequest();
        request.setServletPath(SECUREDRSSFEEDURL);
        request.setScheme(SCHEME);
        request.setServerName(SERVERNAME);
        request.setContextPath(CONTEXTPATH);
        request.setRequestURI(request.getContextPath()+request.getServletPath());
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test SECUREDRSSFEEDURL. Application filter chain is not invoked. Basic Authentication is requested.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNotNull(response.getHeader("WWW-Authenticate"));
        assertEquals("Full authentication is required to access this resource", response.getErrorMessage());
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        assertNotNull(request.getSession().getAttribute(SAVEDREQUESTKEY));        
        assertEquals(0, chain.getCount());

        chain.resetCount();
        request = new MockHttpServletRequest();
        request.setServletPath(NOTSECUREDRSSFEEDURL);
        request.setScheme(SCHEME);
        request.setServerName(SERVERNAME);
        request.setContextPath(CONTEXTPATH);
        request.setRequestURI(request.getContextPath()+request.getServletPath());
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test NOTSECUREDRSSFEEDURL. Application filter chain is invoked, since access is granted.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getHeader("WWW-Authenticate"));
        assertNull(request.getSession().getAttribute(SAVEDREQUESTKEY));        
        assertEquals(1, chain.getCount());
    }

    public void testShibbolethRequestHeadersCleared() throws Exception {
        // Setup our HTTP request with headers cleared. Shibboleth service provider clears request headers to prevent spoofing.
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath(SECUREDVIEWSURL);
        request.setScheme(SCHEME);
        request.setServerName(SERVERNAME);
        request.setContextPath(CONTEXTPATH);
        request.setRequestURI(request.getContextPath()+request.getServletPath());
        request.addHeader(SHIBBOLETHUSERNAMEHEADERKEY, "");
        request.addHeader(SHIBBOLETHFIRSTNAMEHEADERKEY, "");
        request.addHeader(SHIBBOLETHLASTNAMEHEADERKEY, "");
        request.addHeader(SHIBBOLETHEMAILHEADERKEY, "");

        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test SECUREDVIEWSURL. Application filter chain is not invoked, since we redirect to login form url.
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
        request.addHeader(SHIBBOLETHUSERNAMEHEADERKEY, "");
        request.addHeader(SHIBBOLETHFIRSTNAMEHEADERKEY, "");
        request.addHeader(SHIBBOLETHLASTNAMEHEADERKEY, "");
        request.addHeader(SHIBBOLETHEMAILHEADERKEY, "");
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test NOTSECUREDVIEWSURL. Application filter chain is invoked, since access is granted.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
        assertNull(request.getSession().getAttribute(SAVEDREQUESTKEY));
        assertEquals(1, chain.getCount());

        chain.resetCount();
        request = new MockHttpServletRequest();
        request.setServletPath(SECUREDRSSFEEDURL);
        request.setScheme(SCHEME);
        request.setServerName(SERVERNAME);
        request.setContextPath(CONTEXTPATH);
        request.setRequestURI(request.getContextPath()+request.getServletPath());
        request.addHeader(SHIBBOLETHUSERNAMEHEADERKEY, "");
        request.addHeader(SHIBBOLETHFIRSTNAMEHEADERKEY, "");
        request.addHeader(SHIBBOLETHLASTNAMEHEADERKEY, "");
        request.addHeader(SHIBBOLETHEMAILHEADERKEY, "");
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test SECUREDRSSFEEDURL. Application filter chain is not invoked. Basic Authentication is requested.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNotNull(response.getHeader("WWW-Authenticate"));
        assertEquals("Full authentication is required to access this resource", response.getErrorMessage());
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        assertNotNull(request.getSession().getAttribute(SAVEDREQUESTKEY));        
        assertEquals(0, chain.getCount());

        chain.resetCount();
        request = new MockHttpServletRequest();
        request.setServletPath(NOTSECUREDRSSFEEDURL);
        request.setScheme(SCHEME);
        request.setServerName(SERVERNAME);
        request.setContextPath(CONTEXTPATH);
        request.setRequestURI(request.getContextPath()+request.getServletPath());
        request.addHeader(SHIBBOLETHUSERNAMEHEADERKEY, "");
        request.addHeader(SHIBBOLETHFIRSTNAMEHEADERKEY, "");
        request.addHeader(SHIBBOLETHLASTNAMEHEADERKEY, "");
        request.addHeader(SHIBBOLETHEMAILHEADERKEY, "");
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test NOTSECUREDRSSFEEDURL. Application filter chain is invoked, since access is granted.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getHeader("WWW-Authenticate"));
        assertNull(request.getSession().getAttribute(SAVEDREQUESTKEY));        
        assertEquals(1, chain.getCount());        
    }    

    public void testSuccessfulAuthenticationWithoutReconciliationForEnabledMigratedUser() throws Exception {
    	generateMigratedEnabledUserNoReconcilationNecessary();
        chain.resetCount();
    	request = createMockRequest(SECUREDVIEWSURL);
    	response = new MockHttpServletResponse();
        // Test DEFAULTTARGETURL. Application filter chain is not invoked, due to redirect to defaultTargetUrl, since there is no saved request.
        securityFilterChainProxy.doFilter(request, response, chain);
	    assertEquals(request.getContextPath()+DEFAULTTARGETURL, response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertEquals(0, chain.getCount());
    	response = new MockHttpServletResponse();
    	// Test DEFAULTTARGETURL. Application filter chain invoked, after redirect and with authentication present within security context.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertEquals(1, chain.getCount());
	    	    
    	request = createMockRequest(DEFAULTTARGETURL);
    	savedRequest = makeSavedRequestForUrl(SECUREDVIEWSURL);
        request.getSession().setAttribute(SAVEDREQUESTKEY, savedRequest);
        chain.resetCount();
    	response = new MockHttpServletResponse();
        // Test SECUREDVIEWSURL. Application filter chain is not invoked, due to redirect to url of saved request.
        securityFilterChainProxy.doFilter(request, response, chain);
	    assertEquals(savedRequest.getFullRequestUrl(), response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertEquals(0, chain.getCount());
    	response = new MockHttpServletResponse();
    	// Test SECUREDVIEWSURL. Application filter chain invoked, after redirect and with authentication present within security context.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertEquals(1, chain.getCount());
	    
    	request = createMockRequest(DEFAULTTARGETURL);
    	savedRequest = makeSavedRequestForUrl(NOTSECUREDVIEWSURL);
        request.getSession().setAttribute(SAVEDREQUESTKEY, savedRequest);
        chain.resetCount();
    	response = new MockHttpServletResponse();
        // Test NOTSECUREDVIEWSURL. Application filter chain is not invoked, due to redirect to url of saved request.
        securityFilterChainProxy.doFilter(request, response, chain);
	    assertEquals(savedRequest.getFullRequestUrl(), response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertEquals(0, chain.getCount());
    	response = new MockHttpServletResponse();
    	// Test NOTSECUREDVIEWSURL. Application filter chain invoked, after redirect and with authentication present within security context.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertEquals(1, chain.getCount());

	    request = createMockRequest(SECUREDRSSFEEDURL);
        chain.resetCount();
    	response = new MockHttpServletResponse();
    	// Test SECUREDRSSFEEDURL. Application filter chain invoked.
    	securityFilterChainProxy.doFilter(request, response, chain);
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertEquals(1, chain.getCount());

    	request = createMockRequest(NOTSECUREDRSSFEEDURL);
        chain.resetCount();
    	response = new MockHttpServletResponse();
    	// Test NOTSECUREDRSSFEEDURL. Application filter chain invoked.
    	securityFilterChainProxy.doFilter(request, response, chain);
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertEquals(1, chain.getCount());
    }
  
    /*
    public void testSuccessfulAuthenticationWithReconciliationForEnabledMigratedUser() throws Exception {
    	UserInfo user;
    	
    	generateMigratedEnabledUserToBeReconciled();
        chain.resetCount();
        user = null;
    	request = createMockRequest(SECUREDVIEWSURL);
    	response = new MockHttpServletResponse();
        // Test DEFAULTTARGETURL. Application filter chain is not invoked, due to redirect to defaultTargetUrl, since there is no saved request.
        securityFilterChainProxy.doFilter(request, response, chain);
	    assertEquals(request.getContextPath()+DEFAULTTARGETURL, response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    user = securityService.getUserByName(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME));
	    assertEquals(FIRSTNAME, user.getFirstName());
	    assertEquals(LASTNAME, user.getLastName());
	    assertEquals(0, chain.getCount());
    	response = new MockHttpServletResponse();
    	// Test DEFAULTTARGETURL. Application filter chain invoked, after redirect and with authentication present within security context.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertEquals(1, chain.getCount());

	    rollback();
	    generateMigratedEnabledUserToBeReconciled();
        chain.resetCount();
        user = null;
    	request = createMockRequest(DEFAULTTARGETURL);
    	savedRequest = makeSavedRequestForUrl(SECUREDVIEWSURL);
        request.getSession().setAttribute(SAVEDREQUESTKEY, savedRequest);
    	response = new MockHttpServletResponse();
        // Test SECUREDVIEWSURL. Application filter chain is not invoked, due to redirect to url of saved request.
        securityFilterChainProxy.doFilter(request, response, chain);
	    assertEquals(savedRequest.getFullRequestUrl(), response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    user = securityService.getUserByName(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME));
	    assertEquals(FIRSTNAME, user.getFirstName());
	    assertEquals(LASTNAME, user.getLastName());
	    assertEquals(0, chain.getCount());
    	response = new MockHttpServletResponse();
    	// Test SECUREDVIEWSURL. Application filter chain invoked, after redirect and with authentication present within security context.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertEquals(1, chain.getCount());

	    rollback();
	    generateMigratedEnabledUserToBeReconciled();
        chain.resetCount();
        user = null;
    	request = createMockRequest(DEFAULTTARGETURL);
    	savedRequest = makeSavedRequestForUrl(NOTSECUREDVIEWSURL);
        request.getSession().setAttribute(SAVEDREQUESTKEY, savedRequest);
    	response = new MockHttpServletResponse();
        // Test NOTSECUREDVIEWSURL. Application filter chain is not invoked, due to redirect to url of saved request.
        securityFilterChainProxy.doFilter(request, response, chain);
	    assertEquals(savedRequest.getFullRequestUrl(), response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    user = securityService.getUserByName(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME));
	    assertEquals(FIRSTNAME, user.getFirstName());
	    assertEquals(LASTNAME, user.getLastName());
	    assertEquals(0, chain.getCount());
    	response = new MockHttpServletResponse();
    	// Test NOTSECUREDVIEWSURL. Application filter chain invoked, after redirect and with authentication present within security context.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertEquals(1, chain.getCount());

	    rollback();
    	generateMigratedEnabledUserToBeReconciled();
    	request = createMockRequest(SECUREDRSSFEEDURL);
        chain.resetCount();
        user = null;
    	response = new MockHttpServletResponse();
    	// Test SECUREDRSSFEEDURL. Application filter chain invoked.
    	securityFilterChainProxy.doFilter(request, response, chain);
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertNull(response.getRedirectedUrl());
	    user = securityService.getUserByName(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME));
	    assertEquals(FIRSTNAME, user.getFirstName());
	    assertEquals(LASTNAME, user.getLastName());
	    assertEquals(1, chain.getCount());
	    
	    rollback();
    	generateMigratedEnabledUserToBeReconciled();
    	request = createMockRequest(NOTSECUREDRSSFEEDURL);
        chain.resetCount();
        user = null;
    	response = new MockHttpServletResponse();
    	// Test NOTSECUREDRSSFEEDURL. Application filter chain invoked.
    	securityFilterChainProxy.doFilter(request, response, chain);
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertNull(response.getRedirectedUrl());
	    user = securityService.getUserByName(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME));
	    assertEquals(FIRSTNAME, user.getFirstName());
	    assertEquals(LASTNAME, user.getLastName());
	    assertEquals(1, chain.getCount());
    }
    */
    
    /*
    public void testSuccessfulAuthenticationWithAutomaticMigration() throws Exception {
    	UserInfo user;
    	
    	generateUnmigratedDisabledUser();
        chain.resetCount();
        user = null;
    	request = createMockRequest(SECUREDVIEWSURL);
    	response = new MockHttpServletResponse();
        // Test DEFAULTTARGETURL. Application filter chain is not invoked, due to redirect to defaultTargetUrl, since there is no saved request.
        securityFilterChainProxy.doFilter(request, response, chain);
	    assertEquals(request.getContextPath()+DEFAULTTARGETURL, response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertNotNull(((ShibbolethUserDetails)((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication().getDetails()).getAttributes().get(SecurityDomainUtility.USER_MIGRATION_INDICATOR_KEY));
	    user = securityService.getUserByName(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME));
	    assertEquals(FIRSTNAME, user.getFirstName());
	    assertEquals(LASTNAME, user.getLastName());
	    assertTrue(((UserInfo)securityService.getUserByName(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME))).isEnabled());
	    assertEquals(0, chain.getCount());
    	response = new MockHttpServletResponse();
    	// Test DEFAULTTARGETURL. Application filter chain invoked, after redirect and with authentication present within security context.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertEquals(1, chain.getCount());

	    rollback();
	    generateUnmigratedDisabledUser();
        chain.resetCount();
        user = null;
    	request = createMockRequest(DEFAULTTARGETURL);
    	savedRequest = makeSavedRequestForUrl(SECUREDVIEWSURL);
        request.getSession().setAttribute(SAVEDREQUESTKEY, savedRequest);
    	response = new MockHttpServletResponse();
        // Test SECUREDVIEWSURL. Application filter chain is not invoked, due to redirect to url of saved request.
        securityFilterChainProxy.doFilter(request, response, chain);
	    assertEquals(savedRequest.getFullRequestUrl(), response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertNotNull(((ShibbolethUserDetails)((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication().getDetails()).getAttributes().get(SecurityDomainUtility.USER_MIGRATION_INDICATOR_KEY));
	    user = securityService.getUserByName(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME));
	    assertEquals(FIRSTNAME, user.getFirstName());
	    assertEquals(LASTNAME, user.getLastName());
	    assertTrue(((UserInfo)securityService.getUserByName(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME))).isEnabled());
	    assertEquals(0, chain.getCount());
    	response = new MockHttpServletResponse();
    	// Test SECUREDVIEWSURL. Application filter chain invoked, after redirect and with authentication present within security context.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertEquals(1, chain.getCount());

	    rollback();
	    generateUnmigratedDisabledUser();
        chain.resetCount();
        user = null;
    	request = createMockRequest(DEFAULTTARGETURL);
    	savedRequest = makeSavedRequestForUrl(NOTSECUREDVIEWSURL);
        request.getSession().setAttribute(SAVEDREQUESTKEY, savedRequest);
    	response = new MockHttpServletResponse();
        // Test NOTSECUREDVIEWSURL. Application filter chain is not invoked, due to redirect to url of saved request.
        securityFilterChainProxy.doFilter(request, response, chain);
	    assertEquals(savedRequest.getFullRequestUrl(), response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertNotNull(((ShibbolethUserDetails)((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication().getDetails()).getAttributes().get(SecurityDomainUtility.USER_MIGRATION_INDICATOR_KEY));
	    user = securityService.getUserByName(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME));
	    assertEquals(FIRSTNAME, user.getFirstName());
	    assertEquals(LASTNAME, user.getLastName());
	    assertTrue(((UserInfo)securityService.getUserByName(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME))).isEnabled());
	    assertEquals(0, chain.getCount());
    	response = new MockHttpServletResponse();
    	// Test NOTSECUREDVIEWSURL. Application filter chain invoked, after redirect and with authentication present within security context.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertEquals(1, chain.getCount());

	    rollback();
    	generateUnmigratedDisabledUser();
    	request = createMockRequest(SECUREDRSSFEEDURL);
        chain.resetCount();
        user = null;
    	response = new MockHttpServletResponse();
    	// Test SECUREDRSSFEEDURL. Application filter chain invoked.
    	securityFilterChainProxy.doFilter(request, response, chain);
	    assertNull(request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY));
	    assertNotNull(response.getHeader("WWW-Authenticate"));
	    assertEquals("Full authentication is required to access this resource", response.getErrorMessage());
	    assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
	    assertNotNull(request.getSession().getAttribute(SAVEDREQUESTKEY));
	    user = securityService.getUserByName(USERNAME);
	    assertEquals(FIRSTNAME, user.getFirstName());
	    assertEquals(LASTNAME, user.getLastName());
	    assertFalse(((UserInfo)securityService.getUserByName(USERNAME)).isEnabled());
        assertEquals(0, chain.getCount());	    

        rollback();
    	generateUnmigratedDisabledUser();
    	request = createMockRequest(NOTSECUREDRSSFEEDURL);
        chain.resetCount();
        user = null;
    	response = new MockHttpServletResponse();
    	// Test NOTSECUREDRSSFEEDURL. Application filter chain invoked.
    	securityFilterChainProxy.doFilter(request, response, chain);
	    assertNull(request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY));
        assertNull(response.getHeader("WWW-Authenticate"));
        assertNull(request.getSession().getAttribute(SAVEDREQUESTKEY));        
	    user = securityService.getUserByName(USERNAME);
        assertEquals(FIRSTNAME, user.getFirstName());
	    assertEquals(LASTNAME, user.getLastName());
	    assertFalse(((UserInfo)securityService.getUserByName(USERNAME)).isEnabled());
        assertEquals(1, chain.getCount());	    
    
        rollback();
    	generateUnmigratedEnabledUser();
        chain.resetCount();
        user = null;
    	request = createMockRequest(SECUREDVIEWSURL);
    	response = new MockHttpServletResponse();
        // Test DEFAULTTARGETURL. Application filter chain is not invoked, due to redirect to defaultTargetUrl, since there is no saved request.
        securityFilterChainProxy.doFilter(request, response, chain);
	    assertEquals(request.getContextPath()+DEFAULTTARGETURL, response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertNotNull(((ShibbolethUserDetails)((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication().getDetails()).getAttributes().get(SecurityDomainUtility.USER_MIGRATION_INDICATOR_KEY));
	    user = securityService.getUserByName(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME));
	    assertEquals(FIRSTNAME, user.getFirstName());
	    assertEquals(LASTNAME, user.getLastName());
	    assertEquals(0, chain.getCount());
    	response = new MockHttpServletResponse();
    	// Test DEFAULTTARGETURL. Application filter chain invoked, after redirect and with authentication present within security context.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertEquals(1, chain.getCount());

	    rollback();
	    generateUnmigratedEnabledUser();
        chain.resetCount();
        user = null;
    	request = createMockRequest(DEFAULTTARGETURL);
    	savedRequest = makeSavedRequestForUrl(SECUREDVIEWSURL);
        request.getSession().setAttribute(SAVEDREQUESTKEY, savedRequest);
    	response = new MockHttpServletResponse();
        // Test SECUREDVIEWSURL. Application filter chain is not invoked, due to redirect to url of saved request.
        securityFilterChainProxy.doFilter(request, response, chain);
	    assertEquals(savedRequest.getFullRequestUrl(), response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertNotNull(((ShibbolethUserDetails)((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication().getDetails()).getAttributes().get(SecurityDomainUtility.USER_MIGRATION_INDICATOR_KEY));
	    user = securityService.getUserByName(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME));
	    assertEquals(FIRSTNAME, user.getFirstName());
	    assertEquals(LASTNAME, user.getLastName());
	    assertEquals(0, chain.getCount());
    	response = new MockHttpServletResponse();
    	// Test SECUREDVIEWSURL. Application filter chain invoked, after redirect and with authentication present within security context.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertEquals(1, chain.getCount());

	    rollback();
	    generateUnmigratedEnabledUser();
        chain.resetCount();
        user = null;
    	request = createMockRequest(DEFAULTTARGETURL);
    	savedRequest = makeSavedRequestForUrl(NOTSECUREDVIEWSURL);
        request.getSession().setAttribute(SAVEDREQUESTKEY, savedRequest);
    	response = new MockHttpServletResponse();
        // Test NOTSECUREDVIEWSURL. Application filter chain is not invoked, due to redirect to url of saved request.
        securityFilterChainProxy.doFilter(request, response, chain);
	    assertEquals(savedRequest.getFullRequestUrl(), response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertNotNull(((ShibbolethUserDetails)((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication().getDetails()).getAttributes().get(SecurityDomainUtility.USER_MIGRATION_INDICATOR_KEY));
	    user = securityService.getUserByName(SecurityDomainUtility.toUsername(DEFAULTDOMAINNAME, USERNAME));
	    assertEquals(FIRSTNAME, user.getFirstName());
	    assertEquals(LASTNAME, user.getLastName());
	    assertEquals(0, chain.getCount());
    	response = new MockHttpServletResponse();
    	// Test SECUREDVIEWSURL. Application filter chain invoked, after redirect and with authentication present within security context.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertEquals(1, chain.getCount());
	    
	    rollback();
    	generateUnmigratedEnabledUser();
    	request = createMockRequest(SECUREDRSSFEEDURL);
        chain.resetCount();
        user = null;
    	response = new MockHttpServletResponse();
    	// Test SECUREDRSSFEEDURL. Application filter chain invoked.
    	securityFilterChainProxy.doFilter(request, response, chain);
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertNull(request.getSession().getAttribute(SAVEDREQUESTKEY));
	    user = securityService.getUserByName(USERNAME);
	    assertEquals(FIRSTNAME, user.getFirstName());
	    assertEquals(LASTNAME, user.getLastName());
        assertEquals(1, chain.getCount());	    

        rollback();
    	generateUnmigratedEnabledUser();
    	request = createMockRequest(NOTSECUREDRSSFEEDURL);
        chain.resetCount();
        user = null;
    	response = new MockHttpServletResponse();
    	// Test NOTSECUREDRSSFEEDURL. Application filter chain invoked.
    	securityFilterChainProxy.doFilter(request, response, chain);
	    assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof UsernamePasswordAuthenticationToken);
	    assertNull(request.getSession().getAttribute(SAVEDREQUESTKEY));
	    user = securityService.getUserByName(USERNAME);
	    assertEquals(FIRSTNAME, user.getFirstName());
	    assertEquals(LASTNAME, user.getLastName());
        assertEquals(1, chain.getCount());	    
    }    
    */
    /*
    public void testSuccessfulAuthenticationAndRedirectToMigrationPage() throws Exception {
        chain.resetCount();
    	request = createMockRequest(SECUREDVIEWSURL);
    	response = new MockHttpServletResponse();
        // Test DEFAULTTARGETURL. Application filter chain is not invoked, due to redirect to defaultTargetUrl, since there is no saved request.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertEquals(request.getContextPath()+MIGRATIONTARGETURL, response.getRedirectedUrl());
        assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof PrincipalAcegiUserToken);
	    assertEquals(0, chain.getCount());
    	response = new MockHttpServletResponse();
    	// Test DEFAULTTARGETURL. Application filter chain invoked, after redirect and with authentication present within security context.
        // Simulate redirect.
    	request.setServletPath(MIGRATIONTARGETURL);
        request.setRequestURI(request.getContextPath()+request.getServletPath());
    	securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
	    assertEquals(1, chain.getCount());
	    
	    generateUnmigratedDisabledUserNotFindableByEmail();
        chain.resetCount();
    	request = createMockRequest(SECUREDVIEWSURL);
    	response = new MockHttpServletResponse();
        // Test DEFAULTTARGETURL. Application filter chain is not invoked, due to redirect to defaultTargetUrl, since there is no saved request.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertEquals(request.getContextPath()+MIGRATIONTARGETURL, response.getRedirectedUrl());
        assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof PrincipalAcegiUserToken);
	    assertEquals(0, chain.getCount());
    	response = new MockHttpServletResponse();
    	// Test DEFAULTTARGETURL. Application filter chain invoked, after redirect and with authentication present within security context.
        // Simulate redirect.
    	request.setServletPath(MIGRATIONTARGETURL);
        request.setRequestURI(request.getContextPath()+request.getServletPath());
    	securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
	    assertEquals(1, chain.getCount());
	    
	    rollback();
    	request = createMockRequest(DEFAULTTARGETURL);
    	savedRequest = makeSavedRequestForUrl(SECUREDVIEWSURL);
        request.getSession().setAttribute(SAVEDREQUESTKEY, savedRequest);
        chain.resetCount();
    	request = createMockRequest(SECUREDVIEWSURL);
    	response = new MockHttpServletResponse();
        // Test DEFAULTTARGETURL. Application filter chain is not invoked, due to redirect to defaultTargetUrl, since there is no saved request.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertEquals(request.getContextPath()+MIGRATIONTARGETURL, response.getRedirectedUrl());
        assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof PrincipalAcegiUserToken);
	    assertEquals(0, chain.getCount());
    	response = new MockHttpServletResponse();
    	// Test DEFAULTTARGETURL. Application filter chain invoked, after redirect and with authentication present within security context.
        // Simulate redirect.
    	request.setServletPath(MIGRATIONTARGETURL);
        request.setRequestURI(request.getContextPath()+request.getServletPath());
    	securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
	    assertEquals(1, chain.getCount());

	    rollback();
	    generateUnmigratedDisabledUserNotFindableByEmail();
    	savedRequest = makeSavedRequestForUrl(SECUREDVIEWSURL);
        request.getSession().setAttribute(SAVEDREQUESTKEY, savedRequest);	    
        chain.resetCount();
    	request = createMockRequest(SECUREDVIEWSURL);
    	response = new MockHttpServletResponse();
        // Test DEFAULTTARGETURL. Application filter chain is not invoked, due to redirect to defaultTargetUrl, since there is no saved request.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertEquals(request.getContextPath()+MIGRATIONTARGETURL, response.getRedirectedUrl());
        assertTrue(((SecurityContext)request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY)).getAuthentication() instanceof PrincipalAcegiUserToken);
	    assertEquals(0, chain.getCount());
    	response = new MockHttpServletResponse();
    	// Test DEFAULTTARGETURL. Application filter chain invoked, after redirect and with authentication present within security context.
        // Simulate redirect.
    	request.setServletPath(MIGRATIONTARGETURL);
        request.setRequestURI(request.getContextPath()+request.getServletPath());
    	securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
	    assertEquals(1, chain.getCount());
    }
     */
    
    /*
    public void testUnsuccessfulAuthenticationDueToLockedUserStatus() throws Exception {
    	generateLockedUser();
        chain.resetCount();
    	request = createMockRequest(SECUREDVIEWSURL);
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test SECUREDVIEWSURL. Application filter chain is not invoked, since we redirect to login form url.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertEquals(SCHEME+"://"+SERVERNAME+CONTEXTPATH+LOGINFORMURL, response.getRedirectedUrl());
        assertEquals(request.getScheme()+"://"+request.getServerName()+request.getRequestURI(), ((SavedRequest)request.getSession().getAttribute(SAVEDREQUESTKEY)).getFullRequestUrl());
        assertEquals(0, chain.getCount());
        
        chain.resetCount();
    	request = createMockRequest(NOTSECUREDVIEWSURL);
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test NOTSECUREDVIEWSURL. Application filter chain is not invoked, since we redirect to login form url.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
        assertEquals(1, chain.getCount());

        chain.resetCount();
    	request = createMockRequest(SECUREDRSSFEEDURL);
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test SECUREDRSSFEEDURL. Application filter chain is not invoked. Basic Authentication is requested.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNotNull(response.getHeader("WWW-Authenticate"));
        assertEquals("Full authentication is required to access this resource", response.getErrorMessage());
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        assertNotNull(request.getSession().getAttribute(SAVEDREQUESTKEY));        
        assertEquals(0, chain.getCount());

        chain.resetCount();
    	request = createMockRequest(NOTSECUREDRSSFEEDURL);
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test NOTSECUREDRSSFEEDURL. Application filter chain is invoked, since access is granted.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getHeader("WWW-Authenticate"));
        assertNull(request.getSession().getAttribute(SAVEDREQUESTKEY));        
        assertEquals(1, chain.getCount());                
    }
*/
    /*
    public void testUnsuccessfulAuthenticationDueToAccountExpiredUserStatus() throws Exception {
    	generateAccountExpiredUser();
        chain.resetCount();
    	request = createMockRequest(SECUREDVIEWSURL);
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test SECUREDVIEWSURL. Application filter chain is not invoked, since we redirect to login form url.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertEquals(SCHEME+"://"+SERVERNAME+CONTEXTPATH+LOGINFORMURL, response.getRedirectedUrl());
        assertEquals(request.getScheme()+"://"+request.getServerName()+request.getRequestURI(), ((SavedRequest)request.getSession().getAttribute(SAVEDREQUESTKEY)).getFullRequestUrl());
        assertEquals(0, chain.getCount());
        
        chain.resetCount();
    	request = createMockRequest(NOTSECUREDVIEWSURL);
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test NOTSECUREDVIEWSURL. Application filter chain is not invoked, since we redirect to login form url.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
        assertEquals(1, chain.getCount());

        chain.resetCount();
    	request = createMockRequest(SECUREDRSSFEEDURL);
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test SECUREDRSSFEEDURL. Application filter chain is not invoked. Basic Authentication is requested.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNotNull(response.getHeader("WWW-Authenticate"));
        assertEquals("Full authentication is required to access this resource", response.getErrorMessage());
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        assertNotNull(request.getSession().getAttribute(SAVEDREQUESTKEY));        
        assertEquals(0, chain.getCount());

        chain.resetCount();
    	request = createMockRequest(NOTSECUREDRSSFEEDURL);
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test NOTSECUREDRSSFEEDURL. Application filter chain is invoked, since access is granted.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getHeader("WWW-Authenticate"));
        assertNull(request.getSession().getAttribute(SAVEDREQUESTKEY));        
        assertEquals(1, chain.getCount());                
    }    
*/
/*
    public void testUnsuccessfulAuthenticationDueToCredentialsExpiredUserStatus() throws Exception {
    	generateCredentialsExpiredUser();
        chain.resetCount();
    	request = createMockRequest(SECUREDVIEWSURL);
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test SECUREDVIEWSURL. Application filter chain is not invoked, since we redirect to login form url.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertEquals(SCHEME+"://"+SERVERNAME+CONTEXTPATH+LOGINFORMURL, response.getRedirectedUrl());
        assertEquals(request.getScheme()+"://"+request.getServerName()+request.getRequestURI(), ((SavedRequest)request.getSession().getAttribute(SAVEDREQUESTKEY)).getFullRequestUrl());
        assertEquals(0, chain.getCount());
        
        chain.resetCount();
    	request = createMockRequest(NOTSECUREDVIEWSURL);
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test NOTSECUREDVIEWSURL. Application filter chain is not invoked, since we redirect to login form url.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
        assertEquals(1, chain.getCount());

        chain.resetCount();
    	request = createMockRequest(SECUREDRSSFEEDURL);
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test SECUREDRSSFEEDURL. Application filter chain is not invoked. Basic Authentication is requested.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNotNull(response.getHeader("WWW-Authenticate"));
        assertEquals("Full authentication is required to access this resource", response.getErrorMessage());
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        assertNotNull(request.getSession().getAttribute(SAVEDREQUESTKEY));        
        assertEquals(0, chain.getCount());

        chain.resetCount();
    	request = createMockRequest(NOTSECUREDRSSFEEDURL);
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test NOTSECUREDRSSFEEDURL. Application filter chain is invoked, since access is granted.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getHeader("WWW-Authenticate"));
        assertNull(request.getSession().getAttribute(SAVEDREQUESTKEY));        
        assertEquals(1, chain.getCount());                
    }
*/
    
    /*
    public void testUnsuccessfulAuthenticationDueToDisabledStatusOfMigratedUser() throws Exception {
    	generateMigratedDisabledUserNoReconcilationNecessary();
        chain.resetCount();
    	request = createMockRequest(SECUREDVIEWSURL);
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test SECUREDVIEWSURL. Application filter chain is not invoked, since we redirect to login form url.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertEquals(SCHEME+"://"+SERVERNAME+CONTEXTPATH+LOGINFORMURL, response.getRedirectedUrl());
        assertEquals(request.getScheme()+"://"+request.getServerName()+request.getRequestURI(), ((SavedRequest)request.getSession().getAttribute(SAVEDREQUESTKEY)).getFullRequestUrl());
        assertEquals(0, chain.getCount());
        
        chain.resetCount();
    	request = createMockRequest(NOTSECUREDVIEWSURL);
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test NOTSECUREDVIEWSURL. Application filter chain is not invoked, since we redirect to login form url.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getRedirectedUrl());
        assertEquals(1, chain.getCount());

        chain.resetCount();
    	request = createMockRequest(SECUREDRSSFEEDURL);
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test SECUREDRSSFEEDURL. Application filter chain is not invoked. Basic Authentication is requested.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNotNull(response.getHeader("WWW-Authenticate"));
        assertEquals("Full authentication is required to access this resource", response.getErrorMessage());
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        assertNotNull(request.getSession().getAttribute(SAVEDREQUESTKEY));        
        assertEquals(0, chain.getCount());

        chain.resetCount();
    	request = createMockRequest(NOTSECUREDRSSFEEDURL);
        // Setup filter. Does not attempt authentication, due to request headers are not present. Continues with next filter instead.
        response = new MockHttpServletResponse();     
        // Test NOTSECUREDRSSFEEDURL. Application filter chain is invoked, since access is granted.
        securityFilterChainProxy.doFilter(request, response, chain);
        assertNull(response.getHeader("WWW-Authenticate"));
        assertNull(request.getSession().getAttribute(SAVEDREQUESTKEY));        
        assertEquals(1, chain.getCount());                
    }    
*/ 
	protected void rollback() {
		endTransaction();
	    startNewTransaction();
	}	
	
	protected void commit() {
		setComplete();
		rollback();
	}
	
	protected void flush() {
		sessionFactory.getCurrentSession().flush();
	}
	
	protected String[] getConfigLocations() {
		// Nonessential configuration files were commented out to accelerate instantiation of application context.
		// If tests fail, add them again.
		return new String[] { 
				"classpath*:applicationContext.xml",
//				"classpath*:applicationContext-beans.xml",
				"classpath*:applicationContext-lucene.xml",
				"classpath*:applicationContext-cache.xml", 
//				"classpath*:applicationContext-messaging.xml",
//				"classpath*:applicationContext-resources.xml",
//				"classpath*:applicationContext-aop.xml",
//				"classpath*:applicationContext-events.xml",
				"classpath*:testContext.xml", 
				"classpath*:testDataSource.xml",
				"classpath*:testShibbolethSecurity.xml"};
	}

	//~ Inner Classes ==================================================================================================

    private class MockFilterChain implements FilterChain {
        private boolean expectToProceed;
        private int count = 0;

        public MockFilterChain(boolean expectToProceed) {
            this.expectToProceed = expectToProceed;
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
