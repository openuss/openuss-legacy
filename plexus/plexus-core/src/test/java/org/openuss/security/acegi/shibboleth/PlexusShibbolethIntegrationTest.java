package org.openuss.security.acegi.shibboleth;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.verify;

import org.acegisecurity.context.SecurityContextHolder;

import junit.framework.TestCase;

public class PlexusShibbolethIntegrationTest extends TestCase {
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
	
	private PlexusShibbolethAuthenticationProcessingFilter filter = new PlexusShibbolethAuthenticationProcessingFilter();
	private PlexusShibbolethAuthenticationProvider provider = new PlexusShibbolethAuthenticationProvider();

	private PlexusShibbolethAuthenticationProcessingFilter filterWithoutMigration = new PlexusShibbolethAuthenticationProcessingFilter();
	private PlexusShibbolethAuthenticationProvider providerWithoutMigration = new PlexusShibbolethAuthenticationProvider();

	public void setUp() {
		SecurityContextHolder.clearContext();
		// Setup plexus shibboleth configuration
//		filter.setAuthenticationManager(authenticationManager);
		filter.setKey(KEY);
		filter.setShibbolethUsernameHeaderKey(SHIBBOLETHUSERNAMEHEADERKEY);
		filter.setShibbolethFirstNameHeaderKey(SHIBBOLETHFIRSTNAMEHEADERKEY);
		filter.setShibbolethLastNameHeaderKey(SHIBBOLETHLASTNAMEHEADERKEY);
		filter.setShibbolethEmailHeaderKey(SHIBBOLETHEMAILHEADERKEY);
		filter.setDefaultDomainId(DEFAULTDOMAINID);
		filter.setDefaultDomainName(DEFAULTDOMAINNAME);
		filter.setDefaultRole(DEFAULTROLE);
		filter.setOnlyProcessFilterProcessesUrlEnabled(false);
		filter.setProcessEachUrlEnabled(true);
		filter.setReturnAfterSuccessfulAuthentication(true);
		filter.setReturnAfterUnsuccessfulAuthentication(false);
		filter.setRedirectOnAuthenticationSuccessEnabled(true);
		filter.setRedirectOnAuthenticationFailureEnabled(false);
		filter.setUseRelativeContext(false);
		filter.setDefaultTargetUrl(DEFAULTTARGETURL);
		filter.setMigrationTargetUrl(MIGRATIONTARGETURL);
		filter.setAuthenticationFailureUrl("/nothing, filter is configured not to redirect on authentication failure.");


//		filterWithoutMigration.setAuthenticationManager(authenticationManager);
		filterWithoutMigration.setKey(KEY);
		filterWithoutMigration.setShibbolethUsernameHeaderKey(SHIBBOLETHUSERNAMEHEADERKEY);
		filterWithoutMigration.setShibbolethFirstNameHeaderKey(SHIBBOLETHFIRSTNAMEHEADERKEY);
		filterWithoutMigration.setShibbolethLastNameHeaderKey(SHIBBOLETHLASTNAMEHEADERKEY);
		filterWithoutMigration.setShibbolethEmailHeaderKey(SHIBBOLETHEMAILHEADERKEY);
		filterWithoutMigration.setDefaultDomainId(DEFAULTDOMAINID);
		filterWithoutMigration.setDefaultDomainName(DEFAULTDOMAINNAME);
		filterWithoutMigration.setDefaultRole(DEFAULTROLE);
		filterWithoutMigration.setOnlyProcessFilterProcessesUrlEnabled(false);
		filterWithoutMigration.setProcessEachUrlEnabled(true);
		filterWithoutMigration.setReturnAfterSuccessfulAuthentication(false);
		filterWithoutMigration.setReturnAfterUnsuccessfulAuthentication(false);
		filterWithoutMigration.setRedirectOnAuthenticationSuccessEnabled(false);
		filterWithoutMigration.setRedirectOnAuthenticationFailureEnabled(false);
		filterWithoutMigration.setDefaultTargetUrl("/nothing, filter is configured not to redirect on authentication success.");
		filterWithoutMigration.setAuthenticationFailureUrl("/nothing, filter is configured not to redirect on authentication failure.");

	}
	// Tests for plexus configuration
	
	
	
}
