package org.openuss.security.ldap;

import java.util.List;
import java.util.Vector;

import org.openuss.security.acegi.ldap.LdapServerConfiguration;

public class LdapConfigurationServiceDummy implements LdapConfigurationService {

	
	public List getEnabledLdapServerConfigurations() {
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

		return ldapServerConfigurations;
	}

	public void addAttributeMappingToRoleAttributeKeySet(
			AttributeMappingInfo mapping, RoleAttributeKeySetInfo keySet) {
		// TODO Auto-generated method stub
		
	}

	public void addDomainToAttributeMapping(AuthenticationDomainInfo domain,
			AttributeMappingInfo mapping) {
		// TODO Auto-generated method stub
		
	}

	public void addRoleAttributeKeyToSet(RoleAttributeKeyInfo key,
			RoleAttributeKeySetInfo keySet) {
		// TODO Auto-generated method stub
		
	}

	public void addServerToDomain(LdapServerInfo server,
			AuthenticationDomainInfo domain) {
		// TODO Auto-generated method stub
		
	}

	public void addServerToUserDnPatternSet(LdapServerInfo server,
			UserDnPatternSetInfo userDnPatternSet) {
		// TODO Auto-generated method stub
		
	}

	public void addUserDnPatternToSet(UserDnPatternSetInfo userDnPattern,
			UserDnPatternSetInfo userDnPatternSet) {
		// TODO Auto-generated method stub
		
	}

	public AttributeMapping createAttributeMapping(
			AttributeMappingInfo attributeMapping) {
		// TODO Auto-generated method stub
		return null;
	}

	public AuthenticationDomain createDomain(AuthenticationDomainInfo domain) {
		// TODO Auto-generated method stub
		return null;
	}

	public RoleAttributeKey createRoleAttributeKey(
			RoleAttributeKeyInfo roleAttributeKey) {
		// TODO Auto-generated method stub
		return null;
	}

	public RoleAttributeKeySet createRoleAttributeKeySet(
			RoleAttributeKeySetInfo roleAttributeKeySet) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserDnPattern createUserDnPattern(UserDnPatternInfo userDnPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserDnPatternSet createUserDnPatternSet(
			UserDnPatternSetInfo userDnPatternSet) {
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

	public void deleteRoleAttributeKeySet(
			RoleAttributeKeySetInfo roleAttributeKeySet) {
		// TODO Auto-generated method stub
		
	}

	public void deleteUserDnPattern(UserDnPatternInfo userDnPattern) {
		// TODO Auto-generated method stub
		
	}

	public void deleteUserDnPatternSet(UserDnPatternSetInfo userDnPatternSet) {
		// TODO Auto-generated method stub
		
	}

	public List getAllAttributeKeySets() {
		// TODO Auto-generated method stub
		return null;
	}

	public List getAllAttributeKeysBySet(
			RoleAttributeKeySetInfo roleAttributeKeySet) {
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

	public List getLdapServersByDomain(AuthenticationDomainInfo domain) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValidURL(String url) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeAttributeMappingFromRoleAttributeKeySet(
			AttributeMappingInfo mapping, RoleAttributeKeySetInfo keySet) {
		// TODO Auto-generated method stub
		
	}

	public void removeDomainFromAttributeMapping(
			AuthenticationDomainInfo domain, AttributeMappingInfo mapping) {
		// TODO Auto-generated method stub
		
	}

	public void removeRoleAttributeKeyFromSet(RoleAttributeKeyInfo key,
			RoleAttributeKeySetInfo keySet) {
		// TODO Auto-generated method stub
		
	}

	public void removeServerFromDomain(LdapServerInfo server,
			AuthenticationDomainInfo domian) {
		// TODO Auto-generated method stub
		
	}

	public void removeServerFromUserDnPatternSet(LdapServerInfo server,
			UserDnPatternSetInfo userDnPatternSet) {
		// TODO Auto-generated method stub
		
	}

	public void removeUserDnPatternFromSet(UserDnPatternInfo userDnPattern,
			UserDnPatternSetInfo userDnPatternSet) {
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

	public void saveRoleAttributeKeySet(
			RoleAttributeKeySetInfo roleAttributeKeySet) {
		// TODO Auto-generated method stub
		
	}

	public void saveUserDnPattern(UserDnPatternInfo userDnPattern) {
		// TODO Auto-generated method stub
		
	}

	public void saveUserDnPatternSet(UserDnPatternSetInfo userDnPatternSet) {
		// TODO Auto-generated method stub
		
	}

	public LdapServer createLdapServer(LdapServerInfo ldapServer) {
		// TODO Auto-generated method stub
		LdapServer ldapServer2 = new LdapServerImpl();
		return ldapServer2;
	}

}
