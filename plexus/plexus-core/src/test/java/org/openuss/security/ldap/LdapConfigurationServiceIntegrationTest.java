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
 * @author Damian Kemner
 * @author Jürgen de Braaf
 */
public class LdapConfigurationServiceIntegrationTest extends LdapConfigurationServiceIntegrationTestBase {
	
	private AuthenticationDomainDao authenticationDomainDao;
	private RoleAttributeKeyDao roleAttributeKeyDao;
	private RoleAttributeKeySetDao roleAttributeKeySetDao;
	private UserDnPatternDao userDnPatternDao;
	private UserDnPatternSetDao userDnPatternSetDao;
	private LdapServerDao ldapServerDao;
	private AttributeMappingDao attributeMappingDao;
	private LdapConfigurationService service;
	
	
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
	
	public void testLdapConfigurationService() {
		service = new LdapConfigurationServiceImpl();
		
////		create user dn pattern object
//		UserDnPatternInfo userDnPattern = new UserDnPatternInfo();
//		userDnPattern.setUserDnPattern("memberOf");
//		List<UserDnPatternInfo> userDnPatterns = new ArrayList<UserDnPatternInfo>();
//		userDnPatterns.add(userDnPattern);		
//		
////		create user dn pattern set object
//		UserDnPatternSetInfo userDnPatternSet = new UserDnPatternSetInfo();
//		userDnPatternSet.setName("WWU user dn pattern");
//		
//		
////		create ldap server object
//		LdapServerInfo ldapServer = new LdapServerInfo();
//		ldapServer.setProviderUrl("ldap://wwusv1.uni-muenster.de");
//		ldapServer.setPort(389);
//		ldapServer.setRootDn("dc=uni-muenster,dc=de");
//		ldapServer.setDescription("LDAP Server WWU");
//		ldapServer.setLdapServerType(LdapServerType.ACTIVE_DIRECTORY);
//		ldapServer.setAuthenticationType("DIGEST-MD5");
//		ldapServer.setUseConnectionPool(false);
//		ldapServer.setManagerDn("admin");
//		ldapServer.setManagerPassword("hidden");
//		ldapServer.setUseLdapContext(true);
//		
//		
////		create another ldap server object
//		LdapServerInfo ldapServer2 = new LdapServerInfo();
//		ldapServer2.setProviderUrl("ldap://ldap.uni-bielefeld.de");
//		ldapServer2.setPort(389);
//		ldapServer2.setRootDn("dc=uni-bielefeld,dc=de");
//		ldapServer2.setDescription("LDAP Server UB");
//		ldapServer2.setLdapServerType(LdapServerType.ACTIVE_DIRECTORY);
//		ldapServer2.setAuthenticationType("DIGEST-MD5");
//		ldapServer2.setUseConnectionPool(false);
//		ldapServer2.setManagerDn("admin");
//		ldapServer2.setManagerPassword("hidden");
//		ldapServer2.setUseLdapContext(true);
////		ldapServer2.setUserDnPatternSet(null);
////		ldapServer2.setAuthenticationDomain(null);
//		
//		
////		create another ldap server object
//		LdapServerInfo ldapServer3 = new LdapServerInfo();
//		ldapServer3.setProviderUrl("falsch://ldap.uni-bielefeld.de");
//		ldapServer3.setPort(389);
//		ldapServer3.setRootDn("dc=uni-bielefeld,dc=de");
//		ldapServer3.setDescription("LDAP Server UB");
//		ldapServer3.setLdapServerType(LdapServerType.ACTIVE_DIRECTORY);
//		ldapServer3.setAuthenticationType("DIGEST-MD5");
//		ldapServer3.setUseConnectionPool(false);
//		ldapServer3.setManagerDn("admin");
//		ldapServer3.setManagerPassword("hidden");
//		ldapServer3.setUseLdapContext(true);
////		ldapServer3.setUserDnPatternSet(null);
////		ldapServer3.setAuthenticationDomain(null);
//		
//		
//		
//// create domain
//		AuthenticationDomainInfo domain = new AuthenticationDomainInfo();
//		domain.setName("ZIV Uni Muenster");
//		domain.setDescription("Zentrale Benutzerkennung von Mitarbeitern und Studierenden der Universitaet Muenster.");
//		
//// create another domain
//		AuthenticationDomainInfo domain2 = new AuthenticationDomainInfo();
//		domain2.setName("Uni Bielefeld");
//		domain2.setDescription("Zentrale Benutzerkennung von Mitarbeitern und Studierenden der Universitaet Bielefeld.");
//		
//// create another domain
//		AuthenticationDomainInfo domain3 = new AuthenticationDomainInfo();
//		// name empty
//		// domain3.setName("ZIV Uni Muenster");
//		domain3.setDescription("Zentrale Benutzerkennung von Mitarbeitern und Studierenden der Universitaet Muenster.");
//		
//						
//		
//		// ldapServer1
//		service.saveLdapServer(ldapServer);
//		// ldapServer1 --> domain1
//		service.saveDomain(domain);
//		service.addServerToDomain(ldapServer, domain);
//		
//		// ldapServer2
//		service.saveLdapServer(ldapServer2);
//		// ldapServer2 --> domain1
//		service.addServerToDomain(ldapServer2, domain);
//		
//		
//		
		
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