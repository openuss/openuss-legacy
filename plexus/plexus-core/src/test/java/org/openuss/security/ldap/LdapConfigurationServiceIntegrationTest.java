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
	
	/*
	 * *************************************************************************
	 * 		ValueObjects
	 * *************************************************************************
	 */
	
	private AuthenticationDomainInfo createAuthenticationDomainInfoDummy() {
		AuthenticationDomainInfo domain = new AuthenticationDomainInfo();
		domain.setName("ZIV Uni Muenster");
		domain.setDescription("Zentrale Benutzerkennung von Mitarbeitern und Studierenden der Universitaet Muenster.");
		
		return domain;
	}
	
	private UserDnPatternInfo createUserDnPatternInfoDummy() {
		UserDnPatternInfo userDnPattern = new UserDnPatternInfo();
		userDnPattern.setName("memberOf");
		
		return userDnPattern;
	}
	
	private UserDnPatternSetInfo createUserDnPatternSetInfoDummy(UserDnPatternInfo userDnPattern) {
		List<Long> userDnPatterns = new ArrayList<Long>();
		userDnPatterns.add(userDnPattern.getId());		
		
		UserDnPatternSetInfo userDnPatternSet = new UserDnPatternSetInfo();
		userDnPatternSet.setName("WWU user dn pattern");
		userDnPatternSet.setUserDnPatternIds(userDnPatterns);
		
		return userDnPatternSet;
	}
	
	private LdapServerInfo createLdapServerInfoDummy(AuthenticationDomainInfo domain, UserDnPatternSetInfo patternSet) {
		LdapServerInfo ldapServer = new LdapServerInfo();
		ldapServer.setProviderUrl("ldap://wwusv1.uni-muenster.de");
		ldapServer.setPort(389);
		ldapServer.setRootDn("dc=uni-muenster,dc=de");
		ldapServer.setDescription("LDAP Server WWU");
		ldapServer.setLdapServerType(LdapServerType.ACTIVE_DIRECTORY);
		ldapServer.setAuthenticationType("DIGEST-MD5");
		ldapServer.setUseConnectionPool(false);
		ldapServer.setManagerDn("admin");
		ldapServer.setManagerPassword("hidden");
		ldapServer.setUseLdapContext(true);
		ldapServer.setAuthenticationDomainId(domain.getId());
		ldapServer.setUserDnPatternSetId(patternSet.getId());
		
		return ldapServer;
	}
	
	/*
	 * *************************************************************************
	 * *************************************************************************
	 */
	
	
	/*
	 * Tests creation and manipulation of UserDnPattern/Info objects
	 */
	public void testCreateUserDnPattern() {
		service = getLdapConfigurationService();
		
		UserDnPatternInfo patternInfo = createUserDnPatternInfoDummy();
		assertNotNull(patternInfo);
		assertNull(patternInfo.getId());
		assertTrue("memberOf" == patternInfo.getName());
		
		patternInfo = service.createUserDnPattern(patternInfo);		
		assertNotNull(patternInfo.getId());
	}
	
	
	/*
	 * Tests creation and manipulation of UserDnPatternSet/Info objects
	 */
	public void testCreateUserDnPatternSet() {
		service = getLdapConfigurationService();
		
		UserDnPatternInfo patternInfo = createUserDnPatternInfoDummy();		
		patternInfo = service.createUserDnPattern(patternInfo);
		
		UserDnPatternSetInfo patternSet = createUserDnPatternSetInfoDummy(patternInfo);
		assertNotNull(patternInfo.getId());
		
		assertNotNull(patternSet);
		assertNull(patternSet.getId());
		
		patternSet = service.createUserDnPatternSet(patternSet);		
		assertNotNull(patternSet.getId());
	}
	
	
	
	/*
	 * Tests creation and manipulation of AuthenticationDomain/Info objects
	 */
	public void testCreateDomain() {
		service = getLdapConfigurationService();
		
		// create info object
		AuthenticationDomainInfo domainInfo = createAuthenticationDomainInfoDummy();
		assertNull(domainInfo.getId());
		
		// save info object to DB
		domainInfo = service.createDomain(domainInfo);
		assertNotNull(domainInfo);
		
		// test values
		assertTrue("ZIV Uni Muenster" == domainInfo.getName());
		assertTrue("Zentrale Benutzerkennung von Mitarbeitern und Studierenden der Universitaet Muenster." == domainInfo.getDescription());
		assertNull(domainInfo.getAttributeMappingId());
		
		// set new values
		domainInfo.setName(null);
		domainInfo.setDescription("Test description");
		assertNull(domainInfo.getName());
		
		// test exception
		try {
		      service.saveDomain(domainInfo);
		      fail("Should have raised an LdapConfigurationServiceException: Name of new authentication domain must not be empty!");
		    } catch (LdapConfigurationServiceException expected) {
		    }
		    
		// set new values
		domainInfo.setName("Test domain");
		assertNotNull(domainInfo.getName());
		
		// try again
		try {
		      service.saveDomain(domainInfo);
		    } catch (LdapConfigurationServiceException expected) {
		    	fail("Should NOT have raised an LdapConfigurationServiceException: Name of new authentication domain must not be empty!");
		    }
	}
	
	
	
	/*
	 * Tests creation and manipulation of LdapServer/Info objects
	 */
	public void testCreateLdapServer() {
		service = getLdapConfigurationService();
		
		// already tested 
		UserDnPatternInfo pattern = createUserDnPatternInfoDummy();
		pattern = service.createUserDnPattern(pattern);
		
		UserDnPatternSetInfo patternSet = createUserDnPatternSetInfoDummy(pattern);
		patternSet = service.createUserDnPatternSet(patternSet);
		
		AuthenticationDomainInfo domain = createAuthenticationDomainInfoDummy();
		domain = service.createDomain(domain);
		// ---
		
		LdapServerInfo server = createLdapServerInfoDummy(domain, patternSet);
		assertNotNull(server);
		assertNull(server.getId());
		

		// set values
		server.setProviderUrl("ldap://wwusv1.uni-muenster.de");
		server.setPort(389);
		server.setRootDn("dc=uni-muenster,dc=de");
		server.setDescription("LDAP Server WWU");
		server.setAuthenticationType("DIGEST-MD5");
		server.setUseConnectionPool(false);
		server.setManagerDn("admin");
		server.setManagerPassword("hidden");
		server.setUseLdapContext(true);
		server.setAuthenticationDomainId(domain.getId());
		server.setUserDnPatternSetId(patternSet.getId());
		
		// test values
		assertTrue("ldap://wwusv1.uni-muenster.de" == server.getProviderUrl());
		assertTrue(389 == server.getPort());
		assertTrue("dc=uni-muenster,dc=de" == server.getRootDn());
		assertTrue("LDAP Server WWU" == server.getDescription());
		assertTrue("DIGEST-MD5" == server.getAuthenticationType());
		assertTrue(false == server.getUseConnectionPool());
		assertTrue("admin" == server.getManagerDn());
		assertTrue("hidden" == server.getManagerPassword());
		assertTrue(true == server.getUseLdapContext());
		assertNotNull(server.getAuthenticationDomainId());
		assertNotNull(server.getUserDnPatternSetId());
		
		// save info object to DB
		server = service.createLdapServer(server);
		assertNotNull(server.getId());

		// set new value
		server.setProviderUrl("http://www.invalid-url.com");
		
		// test exception
		try {
			service.saveLdapServer(server);
			fail("Should have raised an LdapConfigurationServiceException: URL must be a valid ldap-url!");
		} catch (LdapConfigurationServiceException expected) {
		}
		
		// set new value
		server.setProviderUrl(null);
		
		// test exception
		try {
			service.saveLdapServer(server);
			fail("Should have raised an LdapConfigurationServiceException: URL must not be empty!");
		} catch (LdapConfigurationServiceException expected) {
		}
		
		// set new value
		server.setProviderUrl("ldap://12345678");
		
		// test exception
		try {
			service.saveLdapServer(server);
			fail("Should have raised an LdapConfigurationServiceException: URL must be a valid ldap-url!");
		} catch (LdapConfigurationServiceException expected) {
		}
		
		// set new value
		server.setProviderUrl("ldap://valid.ldap.com");
		server.setPort(-2);
		
		// test exception
		try {
			service.saveLdapServer(server);
			fail("Should have raised an LdapConfigurationServiceException: port must not be negative!");
		} catch (LdapConfigurationServiceException expected) {
		}
			
		
	}
	
	public void testLdapConfigurationService() {
//		LdapConfigurationService service = getLdapConfigurationService();
//
//		
//		// create user dn pattern object
//		UserDnPatternInfo userDnPattern = new UserDnPatternInfo();
//		userDnPattern.setUserDnPattern("memberOf");
//		List<UserDnPatternInfo> userDnPatterns = new ArrayList<UserDnPatternInfo>();
//		userDnPatterns.add(userDnPattern);		
//		
//		// create user dn pattern set object
//		UserDnPatternSetInfo userDnPatternSet = new UserDnPatternSetInfo();
//		userDnPatternSet.setName("WWU user dn pattern");
//		userDnPatternSet.setUserDNPatternIds(userDnPatterns);
//		
//		UserDnPattern pattern = service.createUserDnPattern(userDnPattern);
//		UserDnPatternSet patternSet = service.createUserDnPatternSet(userDnPatternSet);
//		
//		service.saveUserDnPattern(userDnPattern);
//		service.saveUserDnPatternSet(userDnPatternSet);
//		
//		
//		
//		
//		// create role attribute key object
//		RoleAttributeKeyInfo roleAttributeKey = new RoleAttributeKeyInfo();
//		roleAttributeKey.setRoleAttributeKey("CN");
//		List<RoleAttributeKeyInfo> roleAttributeKeys = new ArrayList<RoleAttributeKeyInfo>();
//		roleAttributeKeys.add(roleAttributeKey);
//		
//		// create role attribute key set object
//		RoleAttributeKeySetInfo roleAttributeKeySet = new RoleAttributeKeySetInfo();
//		roleAttributeKeySet.setName("role attribute key set test");
//		roleAttributeKeySet.setRoleAttributeKeyIds(roleAttributeKeys);
////		
////		RoleAttributeKey key = service.createRoleAttributeKey(roleAttributeKey);
////		RoleAttributeKeySet keySet = service.createRoleAttributeKeySet(roleAttributeKeySet);
////		
////		service.saveRoleAttributeKey(roleAttributeKey);
////		service.saveRoleAttributeKeySet(roleAttributeKeySet);
//		
//		
//		
//		
//		
//		// create attribute mapping object
//		AttributeMappingInfo attributeMapping = new AttributeMappingInfo();		
//		attributeMapping.setMappingName(testUtility.unique("Mapping Test"));
//		attributeMapping.setUsernameKey("CN");
//		attributeMapping.setFirstNameKey("givenName");
//		attributeMapping.setLastNameKey("SN");
//		attributeMapping.setEmailKey("mail");
//		attributeMapping.setGroupRoleAttributeKey("CN");
//		attributeMapping.setRoleAttributeKeySetId(roleAttributeKeySet.getId());
//		
//
//
//		// create domain
//		AuthenticationDomainInfo domain = new AuthenticationDomainInfo();
//		domain.setName("ZIV Uni Muenster");
//		domain.setDescription("Zentrale Benutzerkennung von Mitarbeitern und Studierenden der Universitaet Muenster.");
//		domain.setAttributeMappingId(attributeMapping.getId());
//		
//		// create another domain
//		AuthenticationDomainInfo domain2 = new AuthenticationDomainInfo();
//		domain2.setName("Uni Bielefeld");
//		domain2.setDescription("Zentrale Benutzerkennung von Mitarbeitern und Studierenden der Universitaet Bielefeld.");
//		domain2.setAttributeMappingId(attributeMapping.getId());
//		
//		// create another domain
//		AuthenticationDomainInfo domain3 = new AuthenticationDomainInfo();
//		domain3.setName("ZIV Uni Muenster");
//		domain3.setDescription("Zentrale Benutzerkennung von Mitarbeitern und Studierenden der Universitaet Muenster.");
//		domain3.setAttributeMappingId(attributeMapping.getId());
//		
//		AuthenticationDomain authDomain = service.createDomain(domain);
//		AuthenticationDomain authDomain2 = service.createDomain(domain2);
//		AuthenticationDomain authDomain3 = service.createDomain(domain3);
		
//		service.saveDomain(domain);
//		service.saveDomain(domain2);
//		service.saveDomain(domain3);
		
		
		
//		List<AuthenticationDomainInfo> domains = new ArrayList<AuthenticationDomainInfo>();
//		domains.add(domain);
//		domains.add(domain2);
//		domains.add(domain3);
//		attributeMapping.setAuthenticationDomainIds(domains);
//		
//		
//		
//		// create ldap server object
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
//		ldapServer.setUserDnPatternSetId(userDnPatternSet.getId());
//		ldapServer.setAuthenticationDomainId(domain.getId());
//		
//		LdapServer server = service.createLdapServer(ldapServer);
//		service.saveLdapServer(ldapServer);
		
		
//		// create another ldap server object
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
//		ldapServer2.setUserDnPatternSetId(userDnPatternSet.getId());
//		ldapServer2.setAuthenticationDomainId(domain2.getId());
//		
//		
//		// create another ldap server object
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
//		ldapServer3.setUserDnPatternSetId(userDnPatternSet.getId());
//		ldapServer3.setAuthenticationDomainId(domain3.getId());
		
				
//		AttributeMapping mapping = service.createAttributeMapping(attributeMapping);
//		

		

//		LdapServer server2 = service.createLdapServer(ldapServer2);
//		LdapServer server3 = service.createLdapServer(ldapServer3);
//		
//		service.saveDomain(domain);
//		service.saveDomain(domain2);
//		service.saveDomain(domain3);
//		
//		service.saveLdapServer(ldapServer2);
//		service.saveLdapServer(ldapServer3);
//		
//		service.addServerToDomain(ldapServer, domain3);
//		service.addServerToDomain(ldapServer2, domain);
//		service.removeServerFromDomain(ldapServer, domain);
		
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
		userDnPattern.setName("memberOf");
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