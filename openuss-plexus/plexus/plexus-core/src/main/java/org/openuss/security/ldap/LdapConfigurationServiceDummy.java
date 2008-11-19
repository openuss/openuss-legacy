package org.openuss.security.ldap;

import java.util.List;
import java.util.Vector;

import org.openuss.security.acegi.ldap.LdapServerConfiguration;

public class LdapConfigurationServiceDummy implements LdapConfigurationService {

	public List<LdapServerConfiguration> getEnabledLdapServerConfigurations() {
		List<LdapServerConfiguration> ldapServerConfigurations = new Vector<LdapServerConfiguration>();
		LdapServerConfiguration ldapServerConfiguration = new LdapServerConfiguration();
		ldapServerConfiguration.setAuthenticationDomainId(42L);
		ldapServerConfiguration.setAuthenticationDomainName("uni-muenster");
		ldapServerConfiguration.setAuthenticationType("DIGEST-MD5");
		ldapServerConfiguration.setEmailKey("mail");
		ldapServerConfiguration.setFirstNameKey("givenName");
		ldapServerConfiguration.setLastNameKey("sn");
		ldapServerConfiguration.setLdapServerType(LdapServerType.ACTIVE_DIRECTORY);
		ldapServerConfiguration.setPort(389);
		ldapServerConfiguration.setProviderUrl("ldap://wwusv1.uni-muenster.de");
		ldapServerConfiguration.setRoleAttributeKeys(new String[] { "memberOf" });
		ldapServerConfiguration.setRootDn("dc=uni-muenster,dc=de");
		ldapServerConfiguration.setUseConnectionPool(false);
		ldapServerConfiguration.setUseLdapContext(true);
		ldapServerConfiguration.setUserDnPatterns(new String[] { "cn={0},ou=projekt-benutzer,dc=uni-muenster,dc=de" });
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
		ldapServerConfiguration.setRoleAttributeKeys(new String[] { "memberOf" });
		ldapServerConfiguration.setRootDn("dc=example,dc=com");
		ldapServerConfiguration.setUseConnectionPool(false);
		ldapServerConfiguration.setUseLdapContext(true);
		ldapServerConfiguration.setUserDnPatterns(new String[] { "uid={0},ou=myunit" });
		ldapServerConfiguration.setUsernameKey("uid");

		ldapServerConfigurations.add(ldapServerConfiguration);

		return ldapServerConfigurations;
	}

	public void addDomainToAttributeMapping(AuthenticationDomainInfo domain, AttributeMappingInfo mapping) {
	}

	public void addRoleAttributeKeyToAttributeMapping(RoleAttributeKeyInfo roleAttributeKeyinfo,
			AttributeMappingInfo attributeMappingInfo) {
	}

	public void addServerToDomain(LdapServerInfo server, AuthenticationDomainInfo domain) {
	}

	public void addUserDnPatternToLdapServer(UserDnPatternInfo userDnPatternInfo, LdapServerInfo ldapServerInfo) {
	}

	public AttributeMappingInfo createAttributeMapping(AttributeMappingInfo attributeMapping) {
		return null;
	}

	public AuthenticationDomainInfo createDomain(AuthenticationDomainInfo domain) {
		return null;
	}

	public LdapServerInfo createLdapServer(LdapServerInfo ldapServer) {
		return null;
	}

	public RoleAttributeKeyInfo createRoleAttributeKey(RoleAttributeKeyInfo roleAttributeKey) {
		return null;
	}

	public UserDnPatternInfo createUserDnPattern(UserDnPatternInfo userDnPattern) {
		return null;
	}

	public void deleteAttributeMapping(AttributeMappingInfo attributeMapping) {
	}

	public void deleteDomain(AuthenticationDomainInfo domain) {
	}

	public void deleteLdapServer(LdapServerInfo ldapServer) {
	}

	public void deleteRoleAttributeKey(RoleAttributeKeyInfo roleAttributeKey) {
	}

	public void deleteUserDnPattern(UserDnPatternInfo userDnPattern) {
	}

	public List<AttributeMapping> getAllAttributeMappings() {
		return null;
	}

	public List<?> getAllDomains() {
		return null;
	}

	public List<LdapServer> getAllLdapServers() {
		return null;
	}

	public List<RoleAttributeKey> getAllRoleAttributeKeys() {
		return null;
	}

	public List<RoleAttributeKey> getAllRoleAttributeKeysByMapping(AttributeMappingInfo attributeMappingInfo) {
		return null;
	}

	public List<UserDnPattern> getAllUserDnPatterns() {
		return null;
	}

	public List<UserDnPattern> getAllUserDnPatternsByLdapServer(LdapServerInfo ldapServerInfo) {
		return null;
	}

	public AuthenticationDomainInfo getDomainById(Long authDomainId) {
		return null;
	}

	public List<LdapServer> getLdapServersByDomain(AuthenticationDomainInfo domain) {
		return null;
	}

	public boolean isValidURL(String url) {
		return false;
	}

	public void removeDomainFromAttributeMapping(AuthenticationDomainInfo domain, AttributeMappingInfo mapping) {
	}

	public void removeRoleAttributeKeyFromAttributeMapping(RoleAttributeKeyInfo roleAttributeKeyInfo,
			AttributeMappingInfo attributeMappingInfo) {

	}

	public void removeServerFromDomain(LdapServerInfo server, AuthenticationDomainInfo domian) {

	}

	public void saveAttributeMapping(AttributeMappingInfo attributeMapping) {
	}

	public void saveDomain(AuthenticationDomainInfo domain) {
	}

	public void saveLdapServer(LdapServerInfo ldapServer) {
	}

	public void saveRoleAttributeKey(RoleAttributeKeyInfo roleAttributeKey) {
	}

	public void saveUserDnPattern(UserDnPatternInfo userDnPattern) {
	}

	public List<UserDnPattern> getAllUserDnPatternsByLdapServer(UserDnPatternInfo userDnPatternInfo, LdapServerInfo ldapServerInfo) {
		return null;
	}

	public void removeUserDnPatternFromLdapServer(UserDnPatternInfo userDnPatternInfo, LdapServerInfo ldapServerInfo) {

	}

	public AttributeMappingInfo getAttributeMappingById(Long id) {
		return null;
	}

	public LdapServerInfo getLdapServerById(Long id) {
		return null;
	}

	public RoleAttributeKeyInfo getRoleAttributeKeyById(Long id) {
		return null;
	}

	public UserDnPatternInfo getUserDnPatternById(Long id) {
		return null;
	}

	public boolean isValidRoleAttributeKey(RoleAttributeKeyInfo roleAttributeKey) {
		return false;
	}

	public boolean isValidUserDnPattern(UserDnPatternInfo userDnPattern) {
		return false;
	}

	public boolean isValidAttributeMappingName(AttributeMappingInfo attributeMappingInfo) {
		return false;
	}

	public boolean isValidAuthenticationDomainName(AuthenticationDomainInfo authenticationDomainInfo) {
		return false;
	}

	public boolean isValidURL(String[] schemes, String url) {
		return false;
	}

}
