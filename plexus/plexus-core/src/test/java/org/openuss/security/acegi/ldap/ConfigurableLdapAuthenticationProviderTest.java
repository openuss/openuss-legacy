package org.openuss.security.acegi.ldap;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.List;
import java.util.Vector;

import javax.naming.NamingException;

import junit.framework.TestCase;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.dao.UserCache;
import org.acegisecurity.providers.dao.cache.NullUserCache;
import org.acegisecurity.userdetails.ldap.LdapUserDetails;
import org.openuss.security.AttributeMappingKeys;
import org.openuss.security.Roles;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.security.ldap.LdapServerType;
import org.springframework.context.MessageSource;
import org.springframework.context.support.StaticMessageSource;

public class ConfigurableLdapAuthenticationProviderTest extends TestCase {

	public void testAuthenticationViaActiveDirectoryServer() {
		List<LdapServerConfiguration> ldapServerConfigurations = new Vector<LdapServerConfiguration>();
		LdapServerConfiguration ldapServerConfiguration = new LdapServerConfiguration();
		ldapServerConfiguration.setAuthenticationDomainId(42L);
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
			ldapAuthenticationProvider.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		ldapAuthenticationProvider.setDefaultRole(Roles.LDAPUSER_NAME);
		String username = "p_schu07";
		Authentication authRequest = new UsernamePasswordAuthenticationToken(username,"");
		// Test
		Authentication authResponse = ldapAuthenticationProvider.authenticate(authRequest);
		
		LdapUserDetails ldapUserDetails = (LdapUserDetails) authResponse.getPrincipal();
		
		// Check defaultRole assignment
		boolean defaultRoleContained = false;
		for (GrantedAuthority grantedAuthority : ldapUserDetails.getAuthorities()) {
			if (grantedAuthority.getAuthority().equals(Roles.LDAPUSER_NAME))
				defaultRoleContained = true;
		}
		
		String retrievedUsername = "";
		Long retrievedAuthenticationDomainId=0L;
		
		try {
			retrievedUsername = (String) ldapUserDetails.getAttributes().get(AttributeMappingKeys.USERNAME_KEY).get();
			retrievedAuthenticationDomainId= (Long) ldapUserDetails.getAttributes().get(AttributeMappingKeys.AUTHENTICATIONDOMAINID_KEY).get();
		} catch (NamingException e) {
			e.printStackTrace();
			fail();
		}
		
		// Check domainid and username
		
		if (!retrievedUsername.equals(username) || !retrievedAuthenticationDomainId.equals(ldapServerConfiguration.getAuthenticationDomainId()) || !defaultRoleContained)
			fail();
		verify(ldapConfigurationService);
	}
}
