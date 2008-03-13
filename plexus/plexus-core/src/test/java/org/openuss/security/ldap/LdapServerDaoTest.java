// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import java.util.ArrayList;
import java.util.List;


/**
 * JUnit Test for Spring Hibernate LdapServerDao class.
 * @see org.openuss.security.ldap.LdapServerDao
 */
public class LdapServerDaoTest extends LdapServerDaoTestBase {
	private AuthenticationDomainDao authenticationDomainDao;
	private UserDnPatternDao userDnPatternDao;
	private AttributeMappingDao attributeMappingDao;
	private RoleAttributeKeyDao roleAttributeKeyDao;
	
	public AuthenticationDomainDao getAuthenticationDomainDao() {
		return authenticationDomainDao;
	}

	public void setAuthenticationDomainDao(
			AuthenticationDomainDao authenticationDomainDao) {
		this.authenticationDomainDao = authenticationDomainDao;
	}

	public UserDnPatternDao getUserDnPatternDao() {
		return userDnPatternDao;
	}

	public void setUserDnPatternDao(UserDnPatternDao userDnPatternDao) {
		this.userDnPatternDao = userDnPatternDao;
	}

	public AttributeMappingDao getAttributeMappingDao() {
		return attributeMappingDao;
	}

	public void setAttributeMappingDao(AttributeMappingDao attributeMappingDao) {
		this.attributeMappingDao = attributeMappingDao;
	}

	public RoleAttributeKeyDao getRoleAttributeKeyDao() {
		return roleAttributeKeyDao;
	}

	public void setRoleAttributeKeyDao(RoleAttributeKeyDao roleAttributeKeyDao) {
		this.roleAttributeKeyDao = roleAttributeKeyDao;
	}

	public void testLdapServerDaoCreate() {
		LdapServer ldapServer = LdapServer.Factory.newInstance();
		
		// basic attributes
		ldapServer.setProviderUrl(" ");
		ldapServer.setPort(389);
		ldapServer.setRootDn("DC=uni-muenster,DC=de");
		ldapServer.setAuthenticationType("DIGEST-MD5");
		ldapServer.setManagerDn("manger_user");
		ldapServer.setManagerPassword("manager_pswd");
		ldapServer.setUseConnectionPool(false);
		ldapServer.setUseLdapContext(false);
		ldapServer.setDescription("WWU Ldap Server");
		ldapServer.setLdapServerType(LdapServerType.ACTIVE_DIRECTORY);
		ldapServer.setEnabled(true);
		
		// relationships
		
		// domain
		AuthenticationDomain authenticationDomain = AuthenticationDomain.Factory.newInstance();
		authenticationDomain.setName("Test");
		authenticationDomain.setDescription("test");
		ldapServer.setAuthenticationDomain(authenticationDomain);
		
		// user dn patterns
		UserDnPattern userDnPattern = UserDnPattern.Factory.newInstance();
		UserDnPattern userDnPattern2 = UserDnPattern.Factory.newInstance();
		userDnPattern.setName("test");
		userDnPattern2.setName("CN");
		
		List<UserDnPattern> userDnPatterns = new ArrayList<UserDnPattern>();
		userDnPatterns.add(userDnPattern);
		userDnPatterns.add(userDnPattern2);
		
		
		// attribute mapping
		AttributeMapping attributeMapping = AttributeMapping.Factory.newInstance();
		attributeMapping.setMappingName("test");
		attributeMapping.setEmailKey("test");
		attributeMapping.setFirstNameKey("test");
		attributeMapping.setGroupRoleAttributeKey("test");
		attributeMapping.setLastNameKey("test");
		attributeMapping.setUsernameKey("test");
		
		// role attribute keys
		RoleAttributeKey roleAttributeKey = RoleAttributeKey.Factory.newInstance();
		RoleAttributeKey roleAttributeKey2 = RoleAttributeKey.Factory.newInstance();
		roleAttributeKey.setName("test1");
		roleAttributeKey2.setName("test2");
		
		List<RoleAttributeKey> roleAttributeKeys = new ArrayList<RoleAttributeKey>();
		roleAttributeKeys.add(roleAttributeKey);
		roleAttributeKeys.add(roleAttributeKey2);
		
		
		attributeMapping.setRoleAttributeKeys(roleAttributeKeys);
		authenticationDomain.setAttributeMapping(attributeMapping);
		ldapServer.setAuthenticationDomain(authenticationDomain);
		ldapServer.setUserDnPatterns(userDnPatterns);
		
		
		
		// tests
		assertNull(ldapServer.getId());
		assertNull(authenticationDomain.getId());
		assertNull(attributeMapping.getId());
		


		
		attributeMapping = attributeMappingDao.create(attributeMapping);
		authenticationDomain = authenticationDomainDao.create(authenticationDomain);
		


		
		ldapServer = ldapServerDao.create(ldapServer);
		
		assertNotNull(ldapServer.getId());
		assertNotNull(authenticationDomain.getId());
		assertNotNull(attributeMapping.getId());
	}
	

}
