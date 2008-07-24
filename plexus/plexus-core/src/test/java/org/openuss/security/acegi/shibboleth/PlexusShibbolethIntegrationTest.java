package org.openuss.security.acegi.shibboleth;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.verify;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.ui.rememberme.RememberMeProcessingFilter;
import org.hibernate.SessionFactory;
import org.openuss.TestUtility;
import org.openuss.messaging.MessageService;
import org.openuss.migration.UserMigrationUtility;
import org.openuss.migration.UserMigrationUtilityImpl;
import org.openuss.security.SecurityService;
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
	private final String MIGRATIONTARGETURL = "/views/secured/migration/migration.faces";
	private final String DEFAULTROLE = "ROLE_SHIBBOLETHUSER";
	private final String DEFAULTTARGETURL = "/views/welcome.faces";
	private final String USERNAME = "test";
	
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
	
	public PlexusShibbolethIntegrationTest() {
		super();
		super.setAutowireMode(AbstractTransactionalDataSourceSpringContextTests.AUTOWIRE_BY_NAME);
	}

	public void testSecurityServiceInjection() {
		assertNotNull(securityService);
		assertNotNull(shibbolethProcessingFilter);
		assertTrue(shibbolethProcessingFilter.isMigrationEnabled());
		assertNotNull(shibbolethAuthenticationProvider);
		assertTrue(shibbolethAuthenticationProvider.isMigrationEnabled());
		assertNotNull(shibbolethProcessingFilterWithoutMigration);
		assertFalse(shibbolethProcessingFilterWithoutMigration.isMigrationEnabled());
		assertNotNull(shibbolethAuthenticationProviderWithoutMigration);
		assertFalse(shibbolethAuthenticationProviderWithoutMigration.isMigrationEnabled());
	}
	
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
}
