package org.openuss.security.acegi.ldap;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.List;
import java.util.Vector;

import javax.naming.CommunicationException;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationServiceException;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.providers.ProviderNotFoundException;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.dao.UserCache;
import org.acegisecurity.providers.dao.cache.NullUserCache;
import org.acegisecurity.userdetails.ldap.LdapUserDetails;
import org.openuss.security.AttributeMappingKeys;
import org.openuss.security.Roles;
import org.openuss.security.SecurityDomainUtility;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.security.ldap.LdapServerType;
import org.springframework.context.MessageSource;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.dao.DataAccessException;

/**
 * Tests <code>ConfigurableLdapAuthenticationProvider</code> class.
 * 
 * @author Peter Schuh
 *
 */
public class ConfigurableLdapAuthenticationProviderTest extends TestCase {

	//~ Tests with faulty, null or empty configurations ================================================================================================

	public void testAuthenticationWithNullValueAsEnabledLdapServer() {

		// Provide arbitrary username and password!
		String username="tester";
		String password="protected";

		List<LdapServerConfiguration> ldapServerConfigurations = null;
		
		LdapConfigurationService ldapConfigurationService = createStrictMock(LdapConfigurationService.class);
		expect(ldapConfigurationService.getEnabledLdapServerConfigurations()).andReturn(ldapServerConfigurations);
		replay(ldapConfigurationService);
		
		MessageSource messageSource = new StaticMessageSource();
		UserCache userCache = new NullUserCache();
		
		ConfigurableLdapAuthenticationProviderImpl ldapAuthenticationProvider = new ConfigurableLdapAuthenticationProviderImpl();
		
		// Initialize provider since it implements InitializingBean interface
		ldapAuthenticationProvider.setLdapConfigurationService(ldapConfigurationService);
		ldapAuthenticationProvider.setMessageSource(messageSource);
		ldapAuthenticationProvider.setUserCache(userCache);
		try {
			ldapAuthenticationProvider.init();	
		} catch (Exception e) {
			// No Exception expected.
			fail();
		}
		
		ldapAuthenticationProvider.setDefaultRole(Roles.LDAPUSER_NAME);		
		Authentication authRequest = new UsernamePasswordAuthenticationToken(username,password);
		// Test
		try {
			ldapAuthenticationProvider.authenticate(authRequest);
			fail();
		} catch (ProviderNotFoundException pnfe) {
			// success
		}		
		verify(ldapConfigurationService);		
	}
	
	public void testAuthenticationWithNoEnabledLdapServer() {

		// Provide arbitrary username and password!
		String username="tester";
		String password="protected";

		List<LdapServerConfiguration> ldapServerConfigurations = new Vector<LdapServerConfiguration>();
		
		LdapConfigurationService ldapConfigurationService = createStrictMock(LdapConfigurationService.class);
		expect(ldapConfigurationService.getEnabledLdapServerConfigurations()).andReturn(ldapServerConfigurations);
		replay(ldapConfigurationService);
		
		MessageSource messageSource = new StaticMessageSource();
		UserCache userCache = new NullUserCache();
		
		ConfigurableLdapAuthenticationProviderImpl ldapAuthenticationProvider = new ConfigurableLdapAuthenticationProviderImpl();
		
		// Initialize provider since it implements InitializingBean interface
		ldapAuthenticationProvider.setLdapConfigurationService(ldapConfigurationService);
		ldapAuthenticationProvider.setMessageSource(messageSource);
		ldapAuthenticationProvider.setUserCache(userCache);
		try {
			ldapAuthenticationProvider.init();	
		} catch (Exception e) {
			// No Exception expected.
			fail();
		}
		
		ldapAuthenticationProvider.setDefaultRole(Roles.LDAPUSER_NAME);		
		Authentication authRequest = new UsernamePasswordAuthenticationToken(username,password);
		// Test
		try {
			ldapAuthenticationProvider.authenticate(authRequest);
			fail();
		} catch (ProviderNotFoundException pnfe) {
			// success
		}		
		verify(ldapConfigurationService);		
	}
	
	public void testAuthenticationWithMissingUrl() {
		
		List<LdapServerConfiguration> ldapServerConfigurations = new Vector<LdapServerConfiguration>();
		LdapServerConfiguration ldapServerConfiguration = new LdapServerConfiguration();
		ldapServerConfiguration.setAuthenticationDomainId(42L);
		ldapServerConfiguration.setAuthenticationDomainName("exampledomain");
		ldapServerConfiguration.setAuthenticationType("DIGEST-MD5");
		ldapServerConfiguration.setEmailKey("mail");
		ldapServerConfiguration.setFirstNameKey("givenName");
		ldapServerConfiguration.setLastNameKey("sn");
		ldapServerConfiguration.setLdapServerType(LdapServerType.ACTIVE_DIRECTORY);
		ldapServerConfiguration.setPort(389);
		ldapServerConfiguration.setRoleAttributeKeys(new String[]{"memberOf"});
		ldapServerConfiguration.setRootDn("dc=uni-muenster,dc=de");
		ldapServerConfiguration.setUseConnectionPool(false);
		ldapServerConfiguration.setUseLdapContext(true);
		ldapServerConfiguration.setUserDnPatterns(new String[]{"cn={0},ou=projekt-benutzer,dc=uni-muenster,dc=de"});
		ldapServerConfiguration.setUsernameKey("cn");
		
		ldapServerConfigurations.add(ldapServerConfiguration);
		
		LdapConfigurationService ldapConfigurationService = createStrictMock(LdapConfigurationService.class);
		expect(ldapConfigurationService.getEnabledLdapServerConfigurations()).andReturn(ldapServerConfigurations);
		replay(ldapConfigurationService);
		
		MessageSource messageSource = new StaticMessageSource();
		UserCache userCache = new NullUserCache();
		
		ConfigurableLdapAuthenticationProviderImpl ldapAuthenticationProvider = new ConfigurableLdapAuthenticationProviderImpl();
		
		// Initialize provider since it implements InitializingBean interface
		ldapAuthenticationProvider.setLdapConfigurationService(ldapConfigurationService);
		ldapAuthenticationProvider.setMessageSource(messageSource);
		ldapAuthenticationProvider.setUserCache(userCache);
		try {
			ldapAuthenticationProvider.init();
			fail();
		} catch (IllegalArgumentException iae) {
			// Success
		} catch (Exception e) {
			// IllegalArgumentException expected.
			fail();			
		}
	}
	
	public void testAuthenticationWithMissingRootDn() {
		
		List<LdapServerConfiguration> ldapServerConfigurations = new Vector<LdapServerConfiguration>();
		LdapServerConfiguration ldapServerConfiguration = new LdapServerConfiguration();
		ldapServerConfiguration.setAuthenticationDomainId(42L);
		ldapServerConfiguration.setAuthenticationDomainName("exampledomain");
		ldapServerConfiguration.setAuthenticationType("DIGEST-MD5");
		ldapServerConfiguration.setEmailKey("mail");
		ldapServerConfiguration.setFirstNameKey("givenName");
		ldapServerConfiguration.setLastNameKey("sn");
		ldapServerConfiguration.setLdapServerType(LdapServerType.ACTIVE_DIRECTORY);
		ldapServerConfiguration.setPort(389);
		ldapServerConfiguration.setProviderUrl("ldap://wwusv1.uni-muenster.de");
		ldapServerConfiguration.setRoleAttributeKeys(new String[]{"memberOf"});
		ldapServerConfiguration.setUseConnectionPool(false);
		ldapServerConfiguration.setUseLdapContext(true);
		ldapServerConfiguration.setUserDnPatterns(new String[]{"cn={0},ou=projekt-benutzer,dc=uni-muenster,dc=de"});
		ldapServerConfiguration.setUsernameKey("cn");
		
		ldapServerConfigurations.add(ldapServerConfiguration);
		
		LdapConfigurationService ldapConfigurationService = createStrictMock(LdapConfigurationService.class);
		expect(ldapConfigurationService.getEnabledLdapServerConfigurations()).andReturn(ldapServerConfigurations);
		replay(ldapConfigurationService);
		
		MessageSource messageSource = new StaticMessageSource();
		UserCache userCache = new NullUserCache();
		
		ConfigurableLdapAuthenticationProviderImpl ldapAuthenticationProvider = new ConfigurableLdapAuthenticationProviderImpl();
		
		// Initialize provider since it implements InitializingBean interface
		ldapAuthenticationProvider.setLdapConfigurationService(ldapConfigurationService);
		ldapAuthenticationProvider.setMessageSource(messageSource);
		ldapAuthenticationProvider.setUserCache(userCache);
		try {
			ldapAuthenticationProvider.init();
			fail();
		} catch (IllegalArgumentException iae) {
			// Success
		} catch (Exception e) {
			// IllegalArgumentException expected.
			fail();			
		}
	}

	public void testAuthenticationWithMissingUserDnPatterns() {
		
		List<LdapServerConfiguration> ldapServerConfigurations = new Vector<LdapServerConfiguration>();
		LdapServerConfiguration ldapServerConfiguration = new LdapServerConfiguration();
		ldapServerConfiguration.setAuthenticationDomainId(42L);
		ldapServerConfiguration.setAuthenticationDomainName("exampledomain");
		ldapServerConfiguration.setAuthenticationType("DIGEST-MD5");
		ldapServerConfiguration.setEmailKey("mail");
		ldapServerConfiguration.setFirstNameKey("givenName");
		ldapServerConfiguration.setLastNameKey("sn");
		ldapServerConfiguration.setLdapServerType(LdapServerType.ACTIVE_DIRECTORY);
		ldapServerConfiguration.setPort(389);
		ldapServerConfiguration.setProviderUrl("ldap://wwusv1.uni-muenster.de");
		ldapServerConfiguration.setRoleAttributeKeys(new String[]{"memberOf"});
		ldapServerConfiguration.setRootDn("dc=uni-muenster,dc=de");
		ldapServerConfiguration.setUseConnectionPool(false);
		ldapServerConfiguration.setUseLdapContext(true);
		ldapServerConfiguration.setUsernameKey("cn");
		
		ldapServerConfigurations.add(ldapServerConfiguration);
		
		LdapConfigurationService ldapConfigurationService = createStrictMock(LdapConfigurationService.class);
		expect(ldapConfigurationService.getEnabledLdapServerConfigurations()).andReturn(ldapServerConfigurations);
		replay(ldapConfigurationService);
		
		MessageSource messageSource = new StaticMessageSource();
		UserCache userCache = new NullUserCache();
		
		ConfigurableLdapAuthenticationProviderImpl ldapAuthenticationProvider = new ConfigurableLdapAuthenticationProviderImpl();
		
		// Initialize provider since it implements InitializingBean interface
		ldapAuthenticationProvider.setLdapConfigurationService(ldapConfigurationService);
		ldapAuthenticationProvider.setMessageSource(messageSource);
		ldapAuthenticationProvider.setUserCache(userCache);
		try {
			ldapAuthenticationProvider.init();
			fail();
		} catch (IllegalArgumentException iae) {
			// Success
		} catch (Exception e) {
			// IllegalArgumentException expected.
			fail();			
		}
	}
	
	public void testAuthenticationWithMissingRoleAttributeKeys() {
		
		List<LdapServerConfiguration> ldapServerConfigurations = new Vector<LdapServerConfiguration>();
		LdapServerConfiguration ldapServerConfiguration = new LdapServerConfiguration();
		ldapServerConfiguration.setAuthenticationDomainId(42L);
		ldapServerConfiguration.setAuthenticationDomainName("exampledomain");
		ldapServerConfiguration.setAuthenticationType("DIGEST-MD5");
		ldapServerConfiguration.setEmailKey("mail");
		ldapServerConfiguration.setFirstNameKey("givenName");
		ldapServerConfiguration.setLastNameKey("sn");
		ldapServerConfiguration.setLdapServerType(LdapServerType.ACTIVE_DIRECTORY);
		ldapServerConfiguration.setPort(389);
		ldapServerConfiguration.setProviderUrl("ldap://wwusv1.uni-muenster.de");		
		ldapServerConfiguration.setRootDn("dc=uni-muenster,dc=de");
		ldapServerConfiguration.setUseConnectionPool(false);
		ldapServerConfiguration.setUseLdapContext(true);
		ldapServerConfiguration.setUserDnPatterns(new String[]{"cn={0},ou=projekt-benutzer,dc=uni-muenster,dc=de"});
		ldapServerConfiguration.setUsernameKey("cn");
		
		ldapServerConfigurations.add(ldapServerConfiguration);
		
		LdapConfigurationService ldapConfigurationService = createStrictMock(LdapConfigurationService.class);
		expect(ldapConfigurationService.getEnabledLdapServerConfigurations()).andReturn(ldapServerConfigurations);
		replay(ldapConfigurationService);
		
		MessageSource messageSource = new StaticMessageSource();
		UserCache userCache = new NullUserCache();
		
		ConfigurableLdapAuthenticationProviderImpl ldapAuthenticationProvider = new ConfigurableLdapAuthenticationProviderImpl();
		
		// Initialize provider since it implements InitializingBean interface
		ldapAuthenticationProvider.setLdapConfigurationService(ldapConfigurationService);
		ldapAuthenticationProvider.setMessageSource(messageSource);
		ldapAuthenticationProvider.setUserCache(userCache);
		try {
			ldapAuthenticationProvider.init();
			fail();
		} catch (IllegalArgumentException iae) {
			// Success			
		} catch (Exception e) {
			// IllegalArgumentException expected.
			fail();			
		}
	}	

	//~ Tests for Active Directory Configuration ================================================================================================	

	public void testSuccessfulAuthenticationViaActiveDirectoryServerWithoutDomainInformationWithinUsername() {
		
		// Provide valid username and password!
		String username="";
		String password="";
		if ("".equals(username) || "".equals(password))
			return;
		
		String defaultRolePrefix="ROLE_";
		
		List<LdapServerConfiguration> ldapServerConfigurations = new Vector<LdapServerConfiguration>();
		LdapServerConfiguration ldapServerConfiguration = new LdapServerConfiguration();
		ldapServerConfiguration.setAuthenticationDomainId(42L);
		ldapServerConfiguration.setAuthenticationDomainName("exampledomain");
		ldapServerConfiguration.setAuthenticationType("DIGEST-MD5");
		ldapServerConfiguration.setEmailKey("mail");
		ldapServerConfiguration.setFirstNameKey("givenName");
		ldapServerConfiguration.setLastNameKey("sn");
		ldapServerConfiguration.setLdapServerType(LdapServerType.ACTIVE_DIRECTORY);
		ldapServerConfiguration.setPort(389);
		ldapServerConfiguration.setProviderUrl("ldap://wwusv1.uni-muenster.de");
		ldapServerConfiguration.setRoleAttributeKeys(new String[]{"memberOf"});
		ldapServerConfiguration.setRootDn("dc=uni-muenster,dc=de");
		ldapServerConfiguration.setUseConnectionPool(false);
		ldapServerConfiguration.setUseLdapContext(true);
		ldapServerConfiguration.setUserDnPatterns(new String[]{"cn={0},ou=projekt-benutzer,dc=uni-muenster,dc=de"});
		ldapServerConfiguration.setUsernameKey("cn");
		
		ldapServerConfigurations.add(ldapServerConfiguration);
		
		LdapConfigurationService ldapConfigurationService = createStrictMock(LdapConfigurationService.class);
		expect(ldapConfigurationService.getEnabledLdapServerConfigurations()).andReturn(ldapServerConfigurations);
		replay(ldapConfigurationService);
		
		MessageSource messageSource = new StaticMessageSource();
		UserCache userCache = new NullUserCache();
		
		ConfigurableLdapAuthenticationProviderImpl ldapAuthenticationProvider = new ConfigurableLdapAuthenticationProviderImpl();
		
		// Initialize provider since it implements InitializingBean interface
		ldapAuthenticationProvider.setLdapConfigurationService(ldapConfigurationService);
		ldapAuthenticationProvider.setMessageSource(messageSource);
		ldapAuthenticationProvider.setUserCache(userCache);
		try {
			ldapAuthenticationProvider.init();
			
		} catch (Exception e) {
			fail();
		}
		
		ldapAuthenticationProvider.setDefaultRole(Roles.LDAPUSER_NAME);		
		Authentication authRequest = new UsernamePasswordAuthenticationToken(username,password);
		// Test
		Authentication authResponse = ldapAuthenticationProvider.authenticate(authRequest);
		
		LdapUserDetails ldapUserDetails = (LdapUserDetails) authResponse.getPrincipal();
		
		// Check defaultRole assignment
		boolean defaultRoleContained = false;
		for (GrantedAuthority grantedAuthority : ldapUserDetails.getAuthorities()) {
			if (grantedAuthority.getAuthority().equals(defaultRolePrefix+Roles.LDAPUSER_NAME))
				defaultRoleContained = true;
		}
		
		String retrievedUsername = "";
		Long retrievedAuthenticationDomainId=0L;
		
		try {
			retrievedUsername = (String) ldapUserDetails.getAttributes().get(AttributeMappingKeys.USERNAME_KEY).get();
			retrievedAuthenticationDomainId= (Long) ldapUserDetails.getAttributes().get(AttributeMappingKeys.AUTHENTICATIONDOMAINID_KEY).get();
		} catch (NamingException e) {
			fail();
		}
		
		// Check domainid and username
		
		if (!retrievedUsername.equals(username) || !retrievedAuthenticationDomainId.equals(ldapServerConfiguration.getAuthenticationDomainId()) || !defaultRoleContained)
			fail();
		verify(ldapConfigurationService);
	}

	public void testSuccessfulAuthenticationViaActiveDirectoryServerWithDomainInformationWithinUsername() {
		
		// Provide valid username and password!
		String username="";
		String password="";
		if ("".equals(username) || "".equals(password))
			return;
			   
		String defaultRolePrefix="ROLE_";
		
		List<LdapServerConfiguration> ldapServerConfigurations = new Vector<LdapServerConfiguration>();
		LdapServerConfiguration ldapServerConfiguration = new LdapServerConfiguration();
		ldapServerConfiguration.setAuthenticationDomainId(42L);
		ldapServerConfiguration.setAuthenticationDomainName("exampledomain");
		ldapServerConfiguration.setAuthenticationType("DIGEST-MD5");
		ldapServerConfiguration.setEmailKey("mail");
		ldapServerConfiguration.setFirstNameKey("givenName");
		ldapServerConfiguration.setLastNameKey("sn");
		ldapServerConfiguration.setLdapServerType(LdapServerType.ACTIVE_DIRECTORY);
		ldapServerConfiguration.setPort(389);
		ldapServerConfiguration.setProviderUrl("ldap://wwusv1.uni-muenster.de");
		ldapServerConfiguration.setRoleAttributeKeys(new String[]{"memberOf"});
		ldapServerConfiguration.setRootDn("dc=uni-muenster,dc=de");
		ldapServerConfiguration.setUseConnectionPool(false);
		ldapServerConfiguration.setUseLdapContext(true);
		ldapServerConfiguration.setUserDnPatterns(new String[]{"cn={0},ou=projekt-benutzer,dc=uni-muenster,dc=de"});
		ldapServerConfiguration.setUsernameKey("cn");
		
		ldapServerConfigurations.add(ldapServerConfiguration);
		
		String modifiedUsername = SecurityDomainUtility.toUsername(ldapServerConfiguration.getAuthenticationDomainName(), username);
		
		LdapConfigurationService ldapConfigurationService = createStrictMock(LdapConfigurationService.class);
		expect(ldapConfigurationService.getEnabledLdapServerConfigurations()).andReturn(ldapServerConfigurations);
		replay(ldapConfigurationService);
		
		MessageSource messageSource = new StaticMessageSource();
		UserCache userCache = new NullUserCache();
		
		ConfigurableLdapAuthenticationProviderImpl ldapAuthenticationProvider = new ConfigurableLdapAuthenticationProviderImpl();
		
		// Initialize provider since it implements InitializingBean interface
		ldapAuthenticationProvider.setLdapConfigurationService(ldapConfigurationService);
		ldapAuthenticationProvider.setMessageSource(messageSource);
		ldapAuthenticationProvider.setUserCache(userCache);
		try {
			ldapAuthenticationProvider.init();
		} catch (Exception e) {
			fail();
		}
		
		ldapAuthenticationProvider.setDefaultRole(Roles.LDAPUSER_NAME);		
		Authentication authRequest = new UsernamePasswordAuthenticationToken(modifiedUsername,password);
		// Test
		Authentication authResponse = ldapAuthenticationProvider.authenticate(authRequest);
		
		LdapUserDetails ldapUserDetails = (LdapUserDetails) authResponse.getPrincipal();
		
		// Check defaultRole assignment
		boolean defaultRoleContained = false;
		for (GrantedAuthority grantedAuthority : ldapUserDetails.getAuthorities()) {
			if (grantedAuthority.getAuthority().equals(defaultRolePrefix+Roles.LDAPUSER_NAME))
				defaultRoleContained = true;
		}
		
		String retrievedUsername = "";
		Long retrievedAuthenticationDomainId=0L;
		
		try {
			retrievedUsername = (String) ldapUserDetails.getAttributes().get(AttributeMappingKeys.USERNAME_KEY).get();
			retrievedAuthenticationDomainId= (Long) ldapUserDetails.getAttributes().get(AttributeMappingKeys.AUTHENTICATIONDOMAINID_KEY).get();
		} catch (NamingException e) {
			fail();
		}
		
		// Check domainid and username
		
		if (!retrievedUsername.equals(username) || !retrievedAuthenticationDomainId.equals(ldapServerConfiguration.getAuthenticationDomainId()) || !defaultRoleContained)
			fail();
		verify(ldapConfigurationService);
	}	
	
	
	public void testAuthenticationViaActiveDirectoryServerWithBadCredentials() {
	
		// Provide arbitrary username and password!
		String username="tester";
		String password="protected";
		if ("".equals(username) || "".equals(password))
			return;
		
		List<LdapServerConfiguration> ldapServerConfigurations = new Vector<LdapServerConfiguration>();
		LdapServerConfiguration ldapServerConfiguration = new LdapServerConfiguration();
		ldapServerConfiguration.setAuthenticationDomainId(42L);
		ldapServerConfiguration.setAuthenticationDomainName("exampledomain");
		ldapServerConfiguration.setAuthenticationType("DIGEST-MD5");
		ldapServerConfiguration.setEmailKey("mail");
		ldapServerConfiguration.setFirstNameKey("givenName");
		ldapServerConfiguration.setLastNameKey("sn");
		ldapServerConfiguration.setLdapServerType(LdapServerType.ACTIVE_DIRECTORY);
		ldapServerConfiguration.setPort(389);
		ldapServerConfiguration.setProviderUrl("ldap://wwusv1.uni-muenster.de");
		ldapServerConfiguration.setRoleAttributeKeys(new String[]{"memberOf"});
		ldapServerConfiguration.setRootDn("dc=uni-muenster,dc=de");
		ldapServerConfiguration.setUseConnectionPool(false);
		ldapServerConfiguration.setUseLdapContext(true);
		ldapServerConfiguration.setUserDnPatterns(new String[]{"cn={0},ou=projekt-benutzer,dc=uni-muenster,dc=de"});
		ldapServerConfiguration.setUsernameKey("cn");
		
		ldapServerConfigurations.add(ldapServerConfiguration);
		
		LdapConfigurationService ldapConfigurationService = createStrictMock(LdapConfigurationService.class);
		expect(ldapConfigurationService.getEnabledLdapServerConfigurations()).andReturn(ldapServerConfigurations);
		replay(ldapConfigurationService);
		
		MessageSource messageSource = new StaticMessageSource();
		UserCache userCache = new NullUserCache();
		
		ConfigurableLdapAuthenticationProviderImpl ldapAuthenticationProvider = new ConfigurableLdapAuthenticationProviderImpl();
		
		// Initialize provider since it implements InitializingBean interface
		ldapAuthenticationProvider.setLdapConfigurationService(ldapConfigurationService);
		ldapAuthenticationProvider.setMessageSource(messageSource);
		ldapAuthenticationProvider.setUserCache(userCache);
		try {
			ldapAuthenticationProvider.init();
		} catch (javax.naming.CommunicationException e) {
			// no ldap server available
			return;
		} catch (Exception e) {			
			fail();
		}
		
		ldapAuthenticationProvider.setDefaultRole(Roles.LDAPUSER_NAME);		
		Authentication authRequest = new UsernamePasswordAuthenticationToken(username,password);
		// Test
		try {
			ldapAuthenticationProvider.authenticate(authRequest);
			fail();
		} catch (AuthenticationServiceException e) {
			if (!(e.getCause().getCause() instanceof CommunicationException)) {
				fail(e.getCause().getCause().getMessage());
			} else {
				return; // Ldap not available!
			}
		} catch (BadCredentialsException be) {
			// Success
		}		
		verify(ldapConfigurationService);
	}

	public void testAuthenticationViaActiveDirectoryServerWithNoMappingInfo() {
		
		// Provide valid username and password!
		String username="";
		String password="";
		if ("".equals(username) || "".equals(password))
			return;
		
		List<LdapServerConfiguration> ldapServerConfigurations = new Vector<LdapServerConfiguration>();
		LdapServerConfiguration ldapServerConfiguration = new LdapServerConfiguration();
		ldapServerConfiguration.setAuthenticationDomainId(42L);
		ldapServerConfiguration.setAuthenticationDomainName("exampledomain");
		ldapServerConfiguration.setAuthenticationType("DIGEST-MD5");
		ldapServerConfiguration.setLdapServerType(LdapServerType.ACTIVE_DIRECTORY);
		ldapServerConfiguration.setPort(389);
		ldapServerConfiguration.setProviderUrl("ldap://wwusv1.uni-muenster.de");
		ldapServerConfiguration.setRoleAttributeKeys(new String[]{"memberOf"});
		ldapServerConfiguration.setRootDn("dc=uni-muenster,dc=de");
		ldapServerConfiguration.setUseConnectionPool(false);
		ldapServerConfiguration.setUseLdapContext(true);
		ldapServerConfiguration.setUserDnPatterns(new String[]{"cn={0},ou=projekt-benutzer,dc=uni-muenster,dc=de"});
		
		ldapServerConfigurations.add(ldapServerConfiguration);
		
		LdapConfigurationService ldapConfigurationService = createStrictMock(LdapConfigurationService.class);
		expect(ldapConfigurationService.getEnabledLdapServerConfigurations()).andReturn(ldapServerConfigurations);
		replay(ldapConfigurationService);
		
		MessageSource messageSource = new StaticMessageSource();
		UserCache userCache = new NullUserCache();
		
		ConfigurableLdapAuthenticationProviderImpl ldapAuthenticationProvider = new ConfigurableLdapAuthenticationProviderImpl();
		
		// Initialize provider since it implements InitializingBean interface
		ldapAuthenticationProvider.setLdapConfigurationService(ldapConfigurationService);
		ldapAuthenticationProvider.setMessageSource(messageSource);
		ldapAuthenticationProvider.setUserCache(userCache);
		try {
			ldapAuthenticationProvider.init();
		} catch (Exception e) {
			fail();
		}
		
		ldapAuthenticationProvider.setDefaultRole(Roles.LDAPUSER_NAME);		
		Authentication authRequest = new UsernamePasswordAuthenticationToken(username,password);
		// Test
		try {
			ldapAuthenticationProvider.authenticate(authRequest);
			fail();
		} catch (IllegalArgumentException iae) {
			// Success
		} catch (Exception e) {
			// IllegalArgumentException expected
			fail();
		}
		verify(ldapConfigurationService);
	}
	
	//~ Tests for Non-Active Directory Configuration ================================================================================================	
/*	
	public void testSuccessfulAuthenticationViaNonActiveDirectoryServerWithoutDomainNameWithinUsername() {
		
		// Provide valid username and password!
		String username="defaultuser";
		String password="masterkey";
		if ("".equals(username) || "".equals(password)) {
			return;
		}
		
		String defaultRolePrefix="ROLE_";
		List<LdapServerConfiguration> ldapServerConfigurations = new Vector<LdapServerConfiguration>();
		LdapServerConfiguration ldapServerConfiguration = new LdapServerConfiguration();
		ldapServerConfiguration.setAuthenticationDomainId(42L);
		ldapServerConfiguration.setAuthenticationDomainName("exampledomain");
		ldapServerConfiguration.setAuthenticationType("SIMPLE");
		ldapServerConfiguration.setEmailKey("mail");
		ldapServerConfiguration.setFirstNameKey("givenName");
		ldapServerConfiguration.setLastNameKey("sn");
		ldapServerConfiguration.setLdapServerType(LdapServerType.OTHER);
		ldapServerConfiguration.setPort(10389);
		ldapServerConfiguration.setProviderUrl("ldap://localhost");
		ldapServerConfiguration.setRoleAttributeKeys(new String[]{"memberOf"});
		ldapServerConfiguration.setRootDn("dc=example,dc=com");
		ldapServerConfiguration.setUseConnectionPool(false);
		ldapServerConfiguration.setUseLdapContext(true);
		ldapServerConfiguration.setUserDnPatterns(new String[]{"uid={0},ou=myunit"});
		ldapServerConfiguration.setUsernameKey("uid");
		
		ldapServerConfigurations.add(ldapServerConfiguration);
		
		LdapConfigurationService ldapConfigurationService = createStrictMock(LdapConfigurationService.class);
		expect(ldapConfigurationService.getEnabledLdapServerConfigurations()).andReturn(ldapServerConfigurations);
		replay(ldapConfigurationService);
		
		MessageSource messageSource = new StaticMessageSource();
		UserCache userCache = new NullUserCache();
		
		ConfigurableLdapAuthenticationProviderImpl ldapAuthenticationProvider = new ConfigurableLdapAuthenticationProviderImpl();
		
		// Initialize provider since it implements InitializingBean interface
		ldapAuthenticationProvider.setLdapConfigurationService(ldapConfigurationService);
		ldapAuthenticationProvider.setMessageSource(messageSource);
		ldapAuthenticationProvider.setUserCache(userCache);
		try {
			ldapAuthenticationProvider.init();
		} catch (Exception e) {
			fail();
		}
		
		ldapAuthenticationProvider.setDefaultRole(Roles.LDAPUSER_NAME);
		ldapAuthenticationProvider.setDefaultRolePrefix(defaultRolePrefix);	
		Authentication authRequest = new UsernamePasswordAuthenticationToken(username,password);
		// Test
		Authentication authResponse = ldapAuthenticationProvider.authenticate(authRequest);
		
		LdapUserDetails ldapUserDetails = (LdapUserDetails) authResponse.getPrincipal();
		
		// Check defaultRole assignment
		boolean defaultRoleContained = false;
		for (GrantedAuthority grantedAuthority : ldapUserDetails.getAuthorities()) {
			if (grantedAuthority.getAuthority().equals(defaultRolePrefix+Roles.LDAPUSER_NAME))
				defaultRoleContained = true;
		}
		
		String retrievedUsername = "";
		Long retrievedAuthenticationDomainId=0L;
		
		try {
			retrievedUsername = (String) ldapUserDetails.getAttributes().get(AttributeMappingKeys.USERNAME_KEY).get();
			retrievedAuthenticationDomainId= (Long) ldapUserDetails.getAttributes().get(AttributeMappingKeys.AUTHENTICATIONDOMAINID_KEY).get();
		} catch (NamingException e) {			
			fail();
		}
		
		// Check domainid and username
		
		if (!retrievedUsername.equals(username) || !retrievedAuthenticationDomainId.equals(ldapServerConfiguration.getAuthenticationDomainId()) || !defaultRoleContained)
			fail();
		verify(ldapConfigurationService);
	}
*/
/*
	
	
	public void testSuccessfulAuthenticationViaNonActiveDirectoryServerWithDomainNameWithinUsername() {
		
		// Provide valid username and password!
		String username="defaultuser";
		String password="masterkey";
		if ("".equals(username) || "".equals(password))
			return;
		
		String defaultRolePrefix="ROLE_";
		List<LdapServerConfiguration> ldapServerConfigurations = new Vector<LdapServerConfiguration>();
		LdapServerConfiguration ldapServerConfiguration = new LdapServerConfiguration();
		ldapServerConfiguration.setAuthenticationDomainId(42L);
		ldapServerConfiguration.setAuthenticationDomainName("exampledomain");
		ldapServerConfiguration.setAuthenticationType("SIMPLE");
		ldapServerConfiguration.setEmailKey("mail");
		ldapServerConfiguration.setFirstNameKey("givenName");
		ldapServerConfiguration.setLastNameKey("sn");
		ldapServerConfiguration.setLdapServerType(LdapServerType.OTHER);
		ldapServerConfiguration.setPort(10389);
		ldapServerConfiguration.setProviderUrl("ldap://localhost");
		ldapServerConfiguration.setRoleAttributeKeys(new String[]{"memberOf"});
		ldapServerConfiguration.setRootDn("dc=example,dc=com");
		ldapServerConfiguration.setUseConnectionPool(false);
		ldapServerConfiguration.setUseLdapContext(true);
		ldapServerConfiguration.setUserDnPatterns(new String[]{"uid={0},ou=myunit"});
		ldapServerConfiguration.setUsernameKey("uid");
		
		ldapServerConfigurations.add(ldapServerConfiguration);
		
		String modifiedUsername = SecurityConstants.USERNAME_DOMAIN_DELIMITER+
		   						  SecurityConstants.USERNAME_DOMAIN_DELIMITER+
		   						  ldapServerConfiguration.getAuthenticationDomainName()+
		   						  SecurityConstants.USERNAME_DOMAIN_DELIMITER+
		   						  username;
		
		LdapConfigurationService ldapConfigurationService = createStrictMock(LdapConfigurationService.class);
		expect(ldapConfigurationService.getEnabledLdapServerConfigurations()).andReturn(ldapServerConfigurations);
		replay(ldapConfigurationService);
		
		MessageSource messageSource = new StaticMessageSource();
		UserCache userCache = new NullUserCache();
		
		ConfigurableLdapAuthenticationProviderImpl ldapAuthenticationProvider = new ConfigurableLdapAuthenticationProviderImpl();
		
		// Initialize provider since it implements InitializingBean interface
		ldapAuthenticationProvider.setLdapConfigurationService(ldapConfigurationService);
		ldapAuthenticationProvider.setMessageSource(messageSource);
		ldapAuthenticationProvider.setUserCache(userCache);
		try {
			ldapAuthenticationProvider.init();
		} catch (Exception e) {
			fail();
		}
		
		ldapAuthenticationProvider.setDefaultRole(Roles.LDAPUSER_NAME);
		ldapAuthenticationProvider.setDefaultRolePrefix(defaultRolePrefix);	
		Authentication authRequest = new UsernamePasswordAuthenticationToken(modifiedUsername,password);
		// Test
		Authentication authResponse = ldapAuthenticationProvider.authenticate(authRequest);
		
		LdapUserDetails ldapUserDetails = (LdapUserDetails) authResponse.getPrincipal();
		
		// Check defaultRole assignment
		boolean defaultRoleContained = false;
		for (GrantedAuthority grantedAuthority : ldapUserDetails.getAuthorities()) {
			if (grantedAuthority.getAuthority().equals(defaultRolePrefix+Roles.LDAPUSER_NAME))
				defaultRoleContained = true;
		}
		
		String retrievedUsername = "";
		Long retrievedAuthenticationDomainId=0L;
		
		try {
			retrievedUsername = (String) ldapUserDetails.getAttributes().get(AttributeMappingKeys.USERNAME_KEY).get();
			retrievedAuthenticationDomainId= (Long) ldapUserDetails.getAttributes().get(AttributeMappingKeys.AUTHENTICATIONDOMAINID_KEY).get();
		} catch (NamingException e) {			
			fail();
		}
		
		// Check domainid and username
		
		if (!retrievedUsername.equals(username) || !retrievedAuthenticationDomainId.equals(ldapServerConfiguration.getAuthenticationDomainId()) || !defaultRoleContained)
			fail();
		verify(ldapConfigurationService);
	}	

*/
/*
	public void testAuthenticationViaNonActiveDirectoryServerWithBadCredentials() {
		// Provide invalid username or password!
		String username="tester";
		String password="protected";
		if ("".equals(username) || "".equals(password))
			return;
		
		String defaultRolePrefix="ROLE_";
		List<LdapServerConfiguration> ldapServerConfigurations = new Vector<LdapServerConfiguration>();
		LdapServerConfiguration ldapServerConfiguration = new LdapServerConfiguration();
		ldapServerConfiguration.setAuthenticationDomainId(42L);
		ldapServerConfiguration.setAuthenticationDomainName("exampledomain");
		ldapServerConfiguration.setAuthenticationType("SIMPLE");
		ldapServerConfiguration.setEmailKey("mail");
		ldapServerConfiguration.setFirstNameKey("givenName");
		ldapServerConfiguration.setLastNameKey("sn");
		ldapServerConfiguration.setLdapServerType(LdapServerType.OTHER);
		ldapServerConfiguration.setPort(10389);
		ldapServerConfiguration.setProviderUrl("ldap://localhost");
		ldapServerConfiguration.setRoleAttributeKeys(new String[]{"memberOf"});
		ldapServerConfiguration.setRootDn("dc=example,dc=com");
		ldapServerConfiguration.setUseConnectionPool(false);
		ldapServerConfiguration.setUseLdapContext(true);
		ldapServerConfiguration.setUserDnPatterns(new String[]{"uid={0},ou=myunit"});
		ldapServerConfiguration.setUsernameKey("uid");
		
		ldapServerConfigurations.add(ldapServerConfiguration);
		
		LdapConfigurationService ldapConfigurationService = createStrictMock(LdapConfigurationService.class);
		expect(ldapConfigurationService.getEnabledLdapServerConfigurations()).andReturn(ldapServerConfigurations);
		replay(ldapConfigurationService);
		
		MessageSource messageSource = new StaticMessageSource();
		UserCache userCache = new NullUserCache();
		
		ConfigurableLdapAuthenticationProviderImpl ldapAuthenticationProvider = new ConfigurableLdapAuthenticationProviderImpl();
		
		// Initialize provider since it implements InitializingBean interface
		ldapAuthenticationProvider.setLdapConfigurationService(ldapConfigurationService);
		ldapAuthenticationProvider.setMessageSource(messageSource);
		ldapAuthenticationProvider.setUserCache(userCache);
		try {
			ldapAuthenticationProvider.init();
		} catch (Exception e) {			
			fail();
		}
		
		ldapAuthenticationProvider.setDefaultRole(Roles.LDAPUSER_NAME);
		ldapAuthenticationProvider.setDefaultRolePrefix(defaultRolePrefix);
		Authentication authRequest = new UsernamePasswordAuthenticationToken(username,password);
		// Test
		try {
			ldapAuthenticationProvider.authenticate(authRequest);
			fail();
		} catch (BadCredentialsException be) {
			// Success
		}		
		verify(ldapConfigurationService);
	}
*/
/*	
	public void testAuthenticationViaNonActiveDirectoryServerWithNoMappingInfo() {
		
		// Provide valid username and password!
		String username="defaultuser";
		String password="masterkey";
		if ("".equals(username) || "".equals(password))
			return;
		String defaultRolePrefix="ROLE_";
		List<LdapServerConfiguration> ldapServerConfigurations = new Vector<LdapServerConfiguration>();
		LdapServerConfiguration ldapServerConfiguration = new LdapServerConfiguration();
		ldapServerConfiguration.setAuthenticationDomainId(42L);
		ldapServerConfiguration.setAuthenticationDomainName("exampledomain");
		ldapServerConfiguration.setAuthenticationType("SIMPLE");
		ldapServerConfiguration.setLdapServerType(LdapServerType.OTHER);
		ldapServerConfiguration.setPort(10389);
		ldapServerConfiguration.setProviderUrl("ldap://localhost");
		ldapServerConfiguration.setRoleAttributeKeys(new String[]{"memberOf"});
		ldapServerConfiguration.setRootDn("dc=example,dc=com");
		ldapServerConfiguration.setUseConnectionPool(false);
		ldapServerConfiguration.setUseLdapContext(true);
		ldapServerConfiguration.setUserDnPatterns(new String[]{"uid={0},ou=myunit"});
		
		ldapServerConfigurations.add(ldapServerConfiguration);
		
		LdapConfigurationService ldapConfigurationService = createStrictMock(LdapConfigurationService.class);
		expect(ldapConfigurationService.getEnabledLdapServerConfigurations()).andReturn(ldapServerConfigurations);
		replay(ldapConfigurationService);
		
		MessageSource messageSource = new StaticMessageSource();
		UserCache userCache = new NullUserCache();
		
		ConfigurableLdapAuthenticationProviderImpl ldapAuthenticationProvider = new ConfigurableLdapAuthenticationProviderImpl();
		
		// Initialize provider since it implements InitializingBean interface
		ldapAuthenticationProvider.setLdapConfigurationService(ldapConfigurationService);
		ldapAuthenticationProvider.setMessageSource(messageSource);
		ldapAuthenticationProvider.setUserCache(userCache);
		try {
			ldapAuthenticationProvider.init();
		} catch (Exception e) {			
			fail();
		}
		
		ldapAuthenticationProvider.setDefaultRole(Roles.LDAPUSER_NAME);
		ldapAuthenticationProvider.setDefaultRolePrefix(defaultRolePrefix);
		Authentication authRequest = new UsernamePasswordAuthenticationToken(username,password);
		// Test
		try {
			ldapAuthenticationProvider.authenticate(authRequest);
			fail();
		} catch (IllegalArgumentException iae) {
			// Success
		} catch (Exception e) {
			// IllegalArgumentException expected
			fail();
		} 
		verify(ldapConfigurationService);
	}
*/	
}
	