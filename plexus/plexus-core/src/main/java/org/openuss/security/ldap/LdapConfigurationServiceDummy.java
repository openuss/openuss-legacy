package org.openuss.security.ldap;

import java.util.List;
import java.util.Vector;

import org.openuss.security.acegi.ldap.LdapServerConfiguration;

public class LdapConfigurationServiceDummy implements LdapConfigurationService {

	
	public List getEnabledLdapServerConfigurations() {
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
		
		ldapServerConfiguration = new LdapServerConfiguration();
		ldapServerConfiguration.setAuthenticationDomainId(42L);
		ldapServerConfiguration.setAuthenticationDomainName("defaultdomain");
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
		
		return ldapServerConfigurations;
	}

	public void addDomainToAttributeMapping(AuthenticationDomainInfo domain,
			AttributeMappingInfo mapping) {
		// TODO Auto-generated method stub
		
	}

	public void addRoleAttributeKeyToAttributeMapping(
			RoleAttributeKeyInfo roleAttributeKeyinfo,
			AttributeMappingInfo attributeMappingInfo) {
		// TODO Auto-generated method stub
		
	}

	public void addServerToDomain(LdapServerInfo server,
			AuthenticationDomainInfo domain) {
		// TODO Auto-generated method stub
		
	}

	public void addUserDnPatternToLdapServer(
			UserDnPatternInfo userDnPatternInfo, LdapServerInfo ldapServerInfo) {
		// TODO Auto-generated method stub
		
	}

	public AttributeMappingInfo createAttributeMapping(
			AttributeMappingInfo attributeMapping) {
		// TODO Auto-generated method stub
		return null;
	}

	public AuthenticationDomainInfo createDomain(AuthenticationDomainInfo domain) {
		// TODO Auto-generated method stub
		return null;
	}

	public LdapServerInfo createLdapServer(LdapServerInfo ldapServer) {
		// TODO Auto-generated method stub
		return null;
	}

	public RoleAttributeKeyInfo createRoleAttributeKey(
			RoleAttributeKeyInfo roleAttributeKey) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserDnPatternInfo createUserDnPattern(UserDnPatternInfo userDnPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteAttributeMapping(AttributeMappingInfo attributeMapping) {
		// TODO Auto-generated method stub
		
	}

	public void deleteDomain(AuthenticationDomainInfo domain) {
		// TODO Auto-generated method stub
		
	}

	public void deleteLdapServer(LdapServerInfo ldapServer) {
		// TODO Auto-generated method stub
		
	}

	public void deleteRoleAttributeKey(RoleAttributeKeyInfo roleAttributeKey) {
		// TODO Auto-generated method stub
		
	}

	public void deleteUserDnPattern(UserDnPatternInfo userDnPattern) {
		// TODO Auto-generated method stub
		
	}

	public List getAllAttributeMappings() {
		// TODO Auto-generated method stub
		return null;
	}

	public List getAllDomains() {
		// TODO Auto-generated method stub
		return null;
	}

	public List getAllLdapServers() {
		// TODO Auto-generated method stub
		return null;
	}

	public List getAllRoleAttributeKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	public List getAllRoleAttributeKeysByMapping(
			AttributeMappingInfo attributeMappingInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getAllUserDnPatterns() {
		// TODO Auto-generated method stub
		return null;
	}

	public List getAllUserDnPatternsByLdapServer(LdapServerInfo ldapServerInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public AuthenticationDomainInfo getDomainById(Long authDomainId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getLdapServersByDomain(AuthenticationDomainInfo domain) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValidURL(String url) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeDomainFromAttributeMapping(
			AuthenticationDomainInfo domain, AttributeMappingInfo mapping) {
		// TODO Auto-generated method stub
		
	}

	public void removeRoleAttributeKeyFromAttributeMapping(
			RoleAttributeKeyInfo roleAttributeKeyInfo,
			AttributeMappingInfo attributeMappingInfo) {
		// TODO Auto-generated method stub
		
	}

	public void removeServerFromDomain(LdapServerInfo server,
			AuthenticationDomainInfo domian) {
		// TODO Auto-generated method stub
		
	}

	public void removeUserDnPatternFromLdapServer(
			UserDnPatternInfo userDnPatternInfo, LdapServerInfo ldapServerInfo) {
		// TODO Auto-generated method stub
		
	}

	public void saveAttributeMapping(AttributeMappingInfo attributeMapping) {
		// TODO Auto-generated method stub
		
	}

	public void saveDomain(AuthenticationDomainInfo domain) {
		// TODO Auto-generated method stub
		
	}

	public void saveLdapServer(LdapServerInfo ldapServer) {
		// TODO Auto-generated method stub
		
	}

	public void saveRoleAttributeKey(RoleAttributeKeyInfo roleAttributeKey) {
		// TODO Auto-generated method stub
		
	}

	public void saveUserDnPattern(UserDnPatternInfo userDnPattern) {
		// TODO Auto-generated method stub
		
	}




	public void addRoleAttributeKeyToAttributeMapping(
			RoleAttributeKeyInfo roleAttributeKeyinfo,
			AttributeMappingInfo attributeMappingInfo) {
		// TODO Auto-generated method stub
		
	}



	public void addUserDnPatternToLdapServer(
			UserDnPatternInfo userDnPatternInfo, LdapServerInfo ldapServerInfo) {
		// TODO Auto-generated method stub
		
	}



	public List getAllRoleAttributeKeysByMapping(
			AttributeMappingInfo attributeMappingInfo) {
		// TODO Auto-generated method stub
		return null;
	}



	public List getAllUserDnPatterns() {
		// TODO Auto-generated method stub
		return null;
	}



	public List getAllUserDnPatternsByLdapServer(
			UserDnPatternInfo userDnPatternInfo, LdapServerInfo ldapServerInfo) {
		// TODO Auto-generated method stub
		return null;
	}



	public void removeRoleAttributeKeyFromAttributeMapping(
			RoleAttributeKeyInfo roleAttributeKeyInfo,
			AttributeMappingInfo attributeMappingInfo) {
		// TODO Auto-generated method stub
		
	}



	public void removeUserDnPatternFromLdapServer(
			UserDnPatternInfo userDnPatternInfo, LdapServerInfo ldapServerInfo) {
		// TODO Auto-generated method stub
		
	}
	
	

	
	
	
}
