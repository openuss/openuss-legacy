// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * JUnit Test for Spring Hibernate LdapConfigurationService class.
 * @see org.openuss.security.ldap.LdapConfigurationService
 */
public class LdapConfigurationServiceIntegrationTest extends LdapConfigurationServiceIntegrationTestBase {
	
	private AuthenticationDomainDao authenticationDomainDao;
	private RoleAttributeKeyDao roleAttributeKeyDao;
	private RoleAttributeKeySetDao roleAttributeKeySetDao;
	private UserDnPatternDao userDnPatternDao;
	private UserDnPatternSetDao userDnPatternSetDao;
	private LdapServerDao ldapServerDao;
	private AttributeMappingDao attributeMappingDao;
	
	
	public void testAuthenticationDomainDaoInjection() {
		assertNotNull(authenticationDomainDao);
	}
	
	public void testRoleAttributeKeyDaoInjection() {
		assertNotNull(roleAttributeKeyDao);
	}
	
	public void testRoleAttributeKeySetDaoInjection() {
		assertNotNull(roleAttributeKeySetDao);
	}
	
	public void testUserDnPatternDaoInjection() {
		assertNotNull(userDnPatternDao);
	}
	
	public void testUserDnPatternSetDaoInjection() {
		assertNotNull(userDnPatternSetDao);
	}
	
	public void testLdapServerDaoInjection() {
		assertNotNull(ldapServerDao);
	}
	
	public void testAttributeMappingDaoInjection() {
		assertNotNull(attributeMappingDao);
	}
	
	
	public void testAllDaoCreate() {	
	
//		create role attribute object
		RoleAttributeKey roleAttributeKey = RoleAttributeKey.Factory.newInstance();
		roleAttributeKey.setRoleAttributeKey("CN");		
		List<RoleAttributeKey> roleAttributeKeys = new ArrayList<RoleAttributeKey>();
		roleAttributeKeys.add(roleAttributeKey);
		
		RoleAttributeKeySet roleAttributeKeySet = RoleAttributeKeySet.Factory.newInstance();
		roleAttributeKeySet.setName("role attribute key set test");
		roleAttributeKeySet.setRoleAttributeKeys(roleAttributeKeys);
		
		assertNull(roleAttributeKeySet.getId());
		roleAttributeKeySetDao.create(roleAttributeKeySet);
		assertNotNull(roleAttributeKeySet.getId());
		
//		create user dn pattern object
		UserDnPattern userDnPattern = UserDnPattern.Factory.newInstance();
		userDnPattern.setUserDnPattern("memberOf");
		List<UserDnPattern> userDnPatterns = new ArrayList<UserDnPattern>();
		userDnPatterns.add(userDnPattern);		
		
//		create user dn pattern set object
		UserDnPatternSet userDnPatternSet = UserDnPatternSet.Factory.newInstance();
		userDnPatternSet.setName("WWU user dn pattern");
		userDnPatternSet.setUserDnPatterns(userDnPatterns);
		
		assertNull(userDnPatternSet.getId());
		userDnPatternSetDao.create(userDnPatternSet);
		assertNotNull(userDnPatternSet.getId());		
		
//		create attribute mapping object
		AttributeMapping attributeMapping = AttributeMapping.Factory.newInstance();		
		attributeMapping.setMappingName(testUtility.unique("Mapping Test"));
		attributeMapping.setUsernameKey("CN");
		attributeMapping.setFirstNameKey("givenName");
		attributeMapping.setLastNameKey("SN");
		attributeMapping.setEmailKey("mail");
		attributeMapping.setGroupRoleAttributeKey("CN");		
//		attributeMapping.setAuthenticationDomains(authenticationDomains);
		attributeMapping.setRoleAttributeKeySet(roleAttributeKeySet);
		
		assertNull(attributeMapping.getId());
		attributeMappingDao.create(attributeMapping);
		assertNotNull(attributeMapping.getId());
		
//		create authentication domain object
		AuthenticationDomain authenticationDomain = AuthenticationDomain.Factory.newInstance();
		authenticationDomain.setName(testUtility.unique("WWU"));
		authenticationDomain.setDescription("WWU Domain");
		authenticationDomain.setAttributeMapping(attributeMapping);
		Set<AuthenticationDomain> authenticationDomains = new HashSet<AuthenticationDomain>();
		authenticationDomains.add(authenticationDomain);
		
		assertNull(authenticationDomain.getId());
		authenticationDomainDao.create(authenticationDomain);
		assertNotNull(authenticationDomain.getId());
		
//		create ldap server object
		LdapServer ldapServer = LdapServer.Factory.newInstance();
		ldapServer.setProviderUrl("ldap://wwusv1.uni-muenster.de");
		ldapServer.setPort(389);
		ldapServer.setRootDn("dc=uni-muenster,dc=de");
		ldapServer.setDescription("LDAP Server WWU");
		ldapServer.setEnabled(true);
		ldapServer.setLdapServerType(LdapServerType.ACTIVE_DIRECTORY);
		ldapServer.setAuthenticationType("DIGEST-MD5");
		ldapServer.setUseConnectionPool(false);
		ldapServer.setManagerDn("admin");
		ldapServer.setManagerPassword("hidden");
		ldapServer.setUseLdapContext(true);
		ldapServer.setUserDnPatternSet(userDnPatternSet);
		ldapServer.setAuthenticationDomain(authenticationDomain);		
		
		assertNull(ldapServer.getId());
		ldapServerDao.create(ldapServer);
		assertNotNull(ldapServer.getId());
		
		setComplete();
		endTransaction();
		
		startNewTransaction();
		ldapServerDao.remove(ldapServer);
		setComplete();
		endTransaction();
				
	}


	
	
	public void setAuthenticationDomainDao(AuthenticationDomainDao authenticationDomainDao) {
		this.authenticationDomainDao = authenticationDomainDao;
	}


	public void setRoleAttributeKeyDao(RoleAttributeKeyDao roleAttributeKeyDao) {
		this.roleAttributeKeyDao = roleAttributeKeyDao;
	}


	public void setRoleAttributeKeySetDao(
			RoleAttributeKeySetDao roleAttributeKeySetDao) {
		this.roleAttributeKeySetDao = roleAttributeKeySetDao;
	}


	public void setUserDnPatternDao(UserDnPatternDao userDnPatternDao) {
		this.userDnPatternDao = userDnPatternDao;
	}


	public void setUserDPatternSetDao(UserDnPatternSetDao userDnPatternSetDao) {
		this.userDnPatternSetDao = userDnPatternSetDao;
	}

	public void setLdapServerDao(LdapServerDao ldapServerDao) {
		this.ldapServerDao = ldapServerDao;
	}

	public void setAttributeMappingDao(AttributeMappingDao attributeMappingDao) {
		this.attributeMappingDao = attributeMappingDao;
	}
	
	
	
}