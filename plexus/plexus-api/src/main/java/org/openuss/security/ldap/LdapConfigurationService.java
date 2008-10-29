package org.openuss.security.ldap;

import java.util.List;

/**
 * 
 * @author Ingo Dueppe
 * 
 */
public interface LdapConfigurationService {

	public List getEnabledLdapServerConfigurations();

	public LdapServerInfo createLdapServer(LdapServerInfo ldapServer);

	public void deleteLdapServer(LdapServerInfo ldapServer);

	public void saveLdapServer(LdapServerInfo ldapServer);

	public List getLdapServersByDomain(AuthenticationDomainInfo domain);

	public List getAllLdapServers();

	public AuthenticationDomainInfo createDomain(AuthenticationDomainInfo domain);

	public void deleteDomain(AuthenticationDomainInfo domain);

	public void saveDomain(AuthenticationDomainInfo domain);

	public List getAllDomains();

	public void addServerToDomain(LdapServerInfo server, AuthenticationDomainInfo domain);

	public void removeServerFromDomain(LdapServerInfo server, AuthenticationDomainInfo domian);

	public AttributeMappingInfo createAttributeMapping(AttributeMappingInfo attributeMapping);

	public void deleteAttributeMapping(AttributeMappingInfo attributeMapping);

	public void saveAttributeMapping(AttributeMappingInfo attributeMapping);

	public List getAllAttributeMappings();

	public RoleAttributeKeyInfo createRoleAttributeKey(RoleAttributeKeyInfo roleAttributeKey);

	public void deleteRoleAttributeKey(RoleAttributeKeyInfo roleAttributeKey);

	public void saveRoleAttributeKey(RoleAttributeKeyInfo roleAttributeKey);

	public void removeRoleAttributeKeyFromAttributeMapping(RoleAttributeKeyInfo roleAttributeKeyInfo, AttributeMappingInfo attributeMappingInfo);

	public void addRoleAttributeKeyToAttributeMapping(RoleAttributeKeyInfo roleAttributeKeyinfo, AttributeMappingInfo attributeMappingInfo);

	public List getAllRoleAttributeKeys();

	public List getAllRoleAttributeKeysByMapping(AttributeMappingInfo attributeMappingInfo);

	public void removeDomainFromAttributeMapping(AuthenticationDomainInfo domain, AttributeMappingInfo mapping);

	public void addDomainToAttributeMapping(AuthenticationDomainInfo domain, AttributeMappingInfo mapping);

	public UserDnPatternInfo createUserDnPattern(UserDnPatternInfo userDnPattern);

	public void saveUserDnPattern(UserDnPatternInfo userDnPattern);

	public void deleteUserDnPattern(UserDnPatternInfo userDnPattern);

	public void addUserDnPatternToLdapServer(UserDnPatternInfo userDnPatternInfo, LdapServerInfo ldapServerInfo);

	public void removeUserDnPatternFromLdapServer(UserDnPatternInfo userDnPatternInfo, LdapServerInfo ldapServerInfo);

	public List getAllUserDnPatterns();

	public List getAllUserDnPatternsByLdapServer(LdapServerInfo ldapServerInfo);

	public AuthenticationDomainInfo getDomainById(Long authDomainId);

	public LdapServerInfo getLdapServerById(Long id);

	public RoleAttributeKeyInfo getRoleAttributeKeyById(Long id);

	public AttributeMappingInfo getAttributeMappingById(Long id);

	public UserDnPatternInfo getUserDnPatternById(Long id);

	public boolean isValidRoleAttributeKey(RoleAttributeKeyInfo roleAttributeKeyInfo);

	public boolean isValidUserDnPattern(UserDnPatternInfo userDnPattern);

	public boolean isValidAttributeMappingName(AttributeMappingInfo attributeMappingInfo);

	public boolean isValidAuthenticationDomainName(AuthenticationDomainInfo authenticationDomainInfo);

	public boolean isValidURL(String[] schemes, String url);

}
