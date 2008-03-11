// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * JUnit Test for Spring Hibernate LdapConfigurationService class.
 * @see org.openuss.security.ldap.LdapConfigurationService
 * @author Damian Kemner
 * @author Jürgen de Braaf
 */
public class LdapConfigurationServiceIntegrationTest extends LdapConfigurationServiceIntegrationTestBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LdapConfigurationServiceIntegrationTest.class);
	
	private AuthenticationDomainDao authenticationDomainDao;
	private RoleAttributeKeyDao roleAttributeKeyDao;
	private UserDnPatternDao userDnPatternDao;
	private LdapServerDao ldapServerDao;
	private AttributeMappingDao attributeMappingDao;
	private LdapConfigurationService service;
	
	
	public void testAuthenticationDomainDaoInjection() {
		assertNotNull(authenticationDomainDao);
	}
	
	public void testRoleAttributeKeyDaoInjection() {
		assertNotNull(roleAttributeKeyDao);
	}
	
	public void testUserDnPatternDaoInjection() {
		assertNotNull(userDnPatternDao);
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
	
	private UserDnPatternInfo createUserDnPatternInfoDummy() {
		UserDnPatternInfo userDnPattern = new UserDnPatternInfo();
		userDnPattern.setName(testUtility.unique("memberOf"));
		
		return userDnPattern;
	}
	
	private AuthenticationDomainInfo createAuthenticationDomainInfoDummy(AttributeMappingInfo mapping) {
		assertNotNull(mapping.getId());
		
		AuthenticationDomainInfo domain = new AuthenticationDomainInfo();
		domain.setName(testUtility.unique("WWU Muenster"));
		domain.setDescription("Test");
		domain.setAttributeMappingId(mapping.getId());
		
		return domain;
	}
	
	
	private LdapServerInfo createLdapServerInfoDummy(AuthenticationDomainInfo domain, UserDnPatternInfo userDnPattern, boolean isEnabled) {
		assertNotNull(domain.getId());
		assertNotNull(userDnPattern.getId());
		
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
		ldapServer.setEnabled(isEnabled);
		
		List<Long> userDnPatternIds = new ArrayList<Long>();
		userDnPatternIds.add(userDnPattern.getId());
		ldapServer.setUserDnPatternIds(userDnPatternIds);
		
		return ldapServer;
	}
	
	private RoleAttributeKeyInfo createRoleAttributeKeyInfoDummy() {
		RoleAttributeKeyInfo key = new RoleAttributeKeyInfo();
		key.setName(testUtility.unique("CN"));
		
		return key;
	}
	
	private AttributeMappingInfo createAttributeMappingInfoDummy(RoleAttributeKeyInfo roleAttributeKey) {
		assertNotNull(roleAttributeKey.getId());
		
		AttributeMappingInfo mapping = new AttributeMappingInfo();
		mapping.setMappingName(testUtility.unique("Mapping Test"));
		mapping.setEmailKey("mail");
		mapping.setFirstNameKey("givenName");
		mapping.setGroupRoleAttributeKey("CN");
		mapping.setLastNameKey("SN");
		mapping.setUsernameKey("CN");
		
		List<Long> roleAttributeKeyIds = new ArrayList<Long>();
		roleAttributeKeyIds.add(roleAttributeKey.getId());
		mapping.setRoleAttributeKeyIds(roleAttributeKeyIds);
		
		return mapping;
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
		
		patternInfo = service.createUserDnPattern(patternInfo);		
		assertNotNull(patternInfo.getId());
	}
	
	
	
	/*
	 * Tests creation and manipulation of AuthenticationDomain/Info objects
	 */
	public void testCreateDomain() {
		service = getLdapConfigurationService();
		
//		create RoleAttributeKeyInfo object
		RoleAttributeKeyInfo roleAttrKeyInfo = createRoleAttributeKeyInfoDummy();
		assertNull(roleAttrKeyInfo.getId());
		roleAttrKeyInfo = service.createRoleAttributeKey(roleAttrKeyInfo);
		assertNotNull(roleAttrKeyInfo.getId());
		
//		create AttributeMappingInfo object
		AttributeMappingInfo attrMappingInfo = createAttributeMappingInfoDummy(roleAttrKeyInfo);
		assertNull(attrMappingInfo.getId());
		attrMappingInfo = service.createAttributeMapping(attrMappingInfo);
		assertNotNull(attrMappingInfo.getId());
		assertTrue(0 < attrMappingInfo.getRoleAttributeKeyIds().size());
		
//		create AuthenticationDomainInfo object
		AuthenticationDomainInfo domainInfo = createAuthenticationDomainInfoDummy(attrMappingInfo);
		assertNull(domainInfo.getId());
		domainInfo = service.createDomain(domainInfo);
		assertNotNull(domainInfo.getId());	
		assertNotNull(domainInfo.getAttributeMappingId());
		
		// set new values in order to test exception
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
		domainInfo.setName(testUtility.unique("Test domain"));
		assertNotNull(domainInfo.getName());
		
		// try again
		try {
		      service.saveDomain(domainInfo);
		    } catch (LdapConfigurationServiceException expected) {
		    	fail("Should NOT have raised an LdapConfigurationServiceException: Name of new authentication domain must not be empty!");
		    }
		    
		List<AuthenticationDomainInfo> all = service.getAllDomains(); 
		int x = all.size();
		service.deleteDomain(domainInfo);
		all = service.getAllDomains();
		assertTrue(x > all.size());
		
	}
	

	
	
	/*
	 * Tests role attribute key and attribute mapping integration
	 */
	public void testRoleAttributeKey() {
		service = getLdapConfigurationService();
//		commit();
		
		// create a role attribute key
		RoleAttributeKeyInfo key1 = createRoleAttributeKeyInfoDummy();
		assertNull(key1.getId());
		key1 = service.createRoleAttributeKey(key1);
		assertNotNull(key1.getId());
		
		flush();
		
		// create another role attribute key
		RoleAttributeKeyInfo key2 = createRoleAttributeKeyInfoDummy();
		assertNull(key2.getId());
		key2 = service.createRoleAttributeKey(key2);
		assertNotNull(key2.getId());
		
		flush();
		
		// test
		List<RoleAttributeKeyInfo> allRoleAttributeKeys = service.getAllRoleAttributeKeys();
		int allRoleAttributeKeysCount = allRoleAttributeKeys.size();
		assertTrue(2 == allRoleAttributeKeysCount);
		
		AttributeMappingInfo map1 = createAttributeMappingInfoDummy(key1);
		assertNull(map1.getId());
		map1 = service.createAttributeMapping(map1);
		assertNotNull(map1.getId());
		
		flush();
		
		List<AttributeMappingInfo> allAttributeMappings = service.getAllAttributeMappings();
		
		int allAttributeMappingsCount = allAttributeMappings.size();
		assertTrue(1 == allAttributeMappingsCount);
		
		List<RoleAttributeKeyInfo> allRoleAttributeKeysMap1 = service.getAllRoleAttributeKeysByMapping(map1);
		int allRoleAttributeKeysMap1Count = allRoleAttributeKeysMap1.size();
		assertTrue(1 == allRoleAttributeKeysMap1Count);
		
		service.addRoleAttributeKeyToAttributeMapping(key2, map1);
		
		flush();
		
		allRoleAttributeKeysMap1 = service.getAllRoleAttributeKeysByMapping(map1);
		allRoleAttributeKeysMap1Count = allRoleAttributeKeysMap1.size();
		assertTrue(2 == allRoleAttributeKeysMap1Count);
		
		
		// this should do NOTHING
		service.addRoleAttributeKeyToAttributeMapping(key2, map1);
		
		flush();
		
		allRoleAttributeKeysMap1 = service.getAllRoleAttributeKeysByMapping(map1);
		allRoleAttributeKeysMap1Count = allRoleAttributeKeysMap1.size();
		assertTrue(2 == allRoleAttributeKeysMap1Count);
		
		// remove role attribute key from mapping
		service.removeRoleAttributeKeyFromAttributeMapping(key2, map1);
		
		flush();
		
		allRoleAttributeKeysMap1 = service.getAllRoleAttributeKeysByMapping(map1);
		allRoleAttributeKeysMap1Count = allRoleAttributeKeysMap1.size();		
		assertTrue(1 == allRoleAttributeKeysMap1Count);
		allRoleAttributeKeys = service.getAllRoleAttributeKeys();
		allRoleAttributeKeysCount = allRoleAttributeKeys.size();
		assertTrue(2 == allRoleAttributeKeysCount);			
			
		logger.debug("********1 RoleAttributeKeys:");
		logger.debug(allRoleAttributeKeys);
		logger.debug("RoleAttributeKeys (Mapping1):");
		logger.debug(allRoleAttributeKeysMap1);	
		
		
		assertTrue(1 == service.getAllAttributeMappings().size());
		
		
		// remove another role attribute key from mapping
		service.removeRoleAttributeKeyFromAttributeMapping(key1, map1);
		
		flush();
		
		allRoleAttributeKeysMap1 = service.getAllRoleAttributeKeysByMapping(map1);
		allRoleAttributeKeysMap1Count = allRoleAttributeKeysMap1.size();
		assertTrue(0 == allRoleAttributeKeysMap1Count);
		allRoleAttributeKeys = service.getAllRoleAttributeKeys();
		allRoleAttributeKeysCount = allRoleAttributeKeys.size();
			
		logger.debug("********2 RoleAttributeKeys:");
		logger.debug(allRoleAttributeKeys);
		logger.debug("RoleAttributeKeys (Mapping1):");
		logger.debug(allRoleAttributeKeysMap1);
			
		assertTrue(2 == allRoleAttributeKeysCount);
		assertTrue(0 == service.getAllAttributeMappings().size());
		
		logger.debug("********3 RoleAttributeKeys:");
		logger.debug(allRoleAttributeKeys);
		logger.debug("RoleAttributeKeys (Mapping1):");
		logger.debug(allRoleAttributeKeysMap1);
		
		logger.debug("********4 allAttributeMappings:");
		logger.debug(service.getAllAttributeMappings().size());
		
//		zero attribute mappings, because deleted when role attribute key relationship has been killed
		assertTrue(0 == service.getAllAttributeMappings().size());
		

		
	}
	
	/*
	 * tests validators
	 */
	public void testIsValid() {
		service = getLdapConfigurationService();
//		commit();
		
		RoleAttributeKeyInfo key1 = service.createRoleAttributeKey(createRoleAttributeKeyInfoDummy());
//		key1.setName("key1");
//		service.saveRoleAttributeKey(key1);
		assertTrue(false == service.isValidRoleAttributeKey(key1));
		
		UserDnPatternInfo pattern1 = service.createUserDnPattern(createUserDnPatternInfoDummy());
//		pattern1.setName("pattern1");
//		service.saveUserDnPattern(pattern1);
		assertTrue(false == service.isValidUserDnPattern(pattern1));
		
		AttributeMappingInfo mapping1 = service.createAttributeMapping(createAttributeMappingInfoDummy(key1));
		service.isValidAttributeMappingName(mapping1);
		
		
	}
	
	
	/*
	 * test getById methods
	 */
	public void testGetter() {
		service = getLdapConfigurationService();
		commit();
		
		RoleAttributeKeyInfo key1 = service.createRoleAttributeKey(createRoleAttributeKeyInfoDummy());
		key1.setName("Key 1");
		service.saveRoleAttributeKey(key1);
		
		RoleAttributeKeyInfo key2 = service.createRoleAttributeKey(createRoleAttributeKeyInfoDummy());
		key2.setName("Key 2");
		service.saveRoleAttributeKey(key2);
		
		AttributeMappingInfo map1 = service.createAttributeMapping(createAttributeMappingInfoDummy(key1));
		map1.setMappingName("Mapping 1");
		service.saveAttributeMapping(map1);
		
		AttributeMappingInfo map2 = service.createAttributeMapping(createAttributeMappingInfoDummy(key2));
		map2.setMappingName("Mapping 2");
		service.saveAttributeMapping(map2);
		
		AuthenticationDomainInfo domain1 = service.createDomain(createAuthenticationDomainInfoDummy(map1));
		domain1.setName("Domain 1");
		service.saveDomain(domain1);
		
		AuthenticationDomainInfo domain2 = service.createDomain(createAuthenticationDomainInfoDummy(map2));
		domain2.setName("Domain 2");
		service.saveDomain(domain2);
		
		UserDnPatternInfo pattern1 = service.createUserDnPattern(createUserDnPatternInfoDummy());
		pattern1.setName("Pattern 1");
		service.saveUserDnPattern(pattern1);
		
		UserDnPatternInfo pattern2 = service.createUserDnPattern(createUserDnPatternInfoDummy());
		pattern2.setName("Pattern 2");
		service.saveUserDnPattern(pattern2);
		
		LdapServerInfo server1 = service.createLdapServer(createLdapServerInfoDummy(domain1, pattern1, true));
		server1.setDescription("Server 1");
		service.saveLdapServer(server1);
		
		LdapServerInfo server2 = service.createLdapServer(createLdapServerInfoDummy(domain2, pattern2, true));
		server2.setDescription("Server 2");
		service.saveLdapServer(server2);
		
		
		List<RoleAttributeKeyInfo> roleAttributeKeys = service.getAllRoleAttributeKeys();
		assertTrue(2 == roleAttributeKeys.size());
		for (RoleAttributeKeyInfo roleAttributeKey : roleAttributeKeys) {
			assertTrue(1 == roleAttributeKey.getAttributeMappingIds().size());
		}
		assertTrue(map1.getId() == roleAttributeKeys.get(0).getAttributeMappingIds().get(0));
		assertTrue(map2.getId() == roleAttributeKeys.get(1).getAttributeMappingIds().get(0));

		
		
		RoleAttributeKeyInfo findKey = service.getRoleAttributeKeyById(key1.getId());
		assertTrue("Key 1" == findKey.getName());
		findKey = service.getRoleAttributeKeyById(key2.getId());
		assertTrue("Key 2" == findKey.getName());
		
		AttributeMappingInfo findMap = service.getAttributeMappingById(map1.getId());
		assertTrue("Mapping 1" == findMap.getMappingName());
		findMap = service.getAttributeMappingById(map2.getId());
		assertTrue("Mapping 2" == findMap.getMappingName());
		
		AuthenticationDomainInfo findDomain = service.getDomainById(domain1.getId());
		assertTrue("Domain 1" == findDomain.getName());
		findDomain = service.getDomainById(domain2.getId());
		assertTrue("Domain 2" == findDomain.getName());
		
		UserDnPatternInfo findPattern = service.getUserDnPatternById(pattern1.getId());
		assertTrue("Pattern 1" == findPattern.getName());
		findPattern = service.getUserDnPatternById(pattern2.getId());
		assertTrue("Pattern 2" == findPattern.getName());
		
		LdapServerInfo findServer = service.getLdapServerById(server1.getId());
		assertTrue("Server 1" == findServer.getDescription());
		findServer = service.getLdapServerById(server2.getId());
		assertTrue("Server 2" == findServer.getDescription());
		
		
	}
	
	/*
	 * Tests alls
	 */
	public void testAllIntegration() {
//		TODO has to be implemented
		
		service = getLdapConfigurationService();
		
//		AuthenticationDomainInfo domain = service.createDomain(createAuthenticationDomainInfoDummy());
//		AuthenticationDomainInfo domain2 = service.createDomain(createAuthenticationDomainInfoDummy());
//		AuthenticationDomainInfo domain3 = service.createDomain(createAuthenticationDomainInfoDummy());
//		
//		UserDnPatternInfo pattern = service.createUserDnPattern(createUserDnPatternInfoDummy());
//		UserDnPatternInfo pattern2 = service.createUserDnPattern(createUserDnPatternInfoDummy());
//		
//		LdapServerInfo server = service.createLdapServer(createLdapServerInfoDummy(domain));
//		LdapServerInfo server2 = service.createLdapServer(createLdapServerInfoDummy(domain2));
//		LdapServerInfo server3 = service.createLdapServer(createLdapServerInfoDummy(domain));
//		
//		List<AuthenticationDomainInfo> allDomains = service.getAllDomains();
//		List<LdapServerInfo> allServers = service.getAllLdapServers(); 
//		List<LdapServerInfo> allServers1 = service.getLdapServersByDomain(domain);
//		List<LdapServerInfo> allServers2 = service.getLdapServersByDomain(domain2);
//		List<LdapServerInfo> allServers3 = service.getLdapServersByDomain(domain3);
//		
//		
//		assertTrue(3 == allDomains.size());
//		assertTrue(3 == allServers.size());
//		assertTrue(2 == allServers1.size());
//		assertTrue(1 == allServers2.size());
//		assertTrue(0 == allServers3.size());
//		
//		
//		service.deleteDomain(domain); 
//		
//		allDomains = service.getAllDomains();
//		allServers = service.getAllLdapServers(); 
//		allServers1 = service.getLdapServersByDomain(domain);
//		allServers2 = service.getLdapServersByDomain(domain2);
//		allServers3 = service.getLdapServersByDomain(domain3);
//		
//		assertTrue(2 == allDomains.size());
//		assertTrue(1 == allServers.size());
//		assertTrue(0 == allServers1.size());
//		assertTrue(1 == allServers2.size());
//		assertTrue(0 == allServers3.size());
//		
//		service.deleteDomain(domain3);
//		
//		allDomains = service.getAllDomains();
//		allServers = service.getAllLdapServers(); 
//		allServers1 = service.getLdapServersByDomain(domain);
//		allServers2 = service.getLdapServersByDomain(domain2);
//		allServers3 = service.getLdapServersByDomain(domain3);
//		
//		assertTrue(1 == allDomains.size());
//		assertTrue(1 == allServers.size());
//		assertTrue(0 == allServers1.size());
//		assertTrue(1 == allServers2.size());
//		assertTrue(0 == allServers3.size());
//		
//		allDomains = service.getAllDomains();
//		allServers = service.getAllLdapServers(); 
//		allServers1 = service.getLdapServersByDomain(domain);
//		allServers2 = service.getLdapServersByDomain(domain2);
//		allServers3 = service.getLdapServersByDomain(domain3);
//		
//		assertTrue(1 == allDomains.size());
//		assertTrue(1 == allServers.size());
//		assertTrue(0 == allServers1.size());
//		assertTrue(1 == allServers2.size());
//		assertTrue(0 == allServers3.size());
//		
//		
//		allDomains = service.getAllDomains();
//		allServers = service.getAllLdapServers(); 
//		allServers1 = service.getLdapServersByDomain(domain);
//		allServers2 = service.getLdapServersByDomain(domain2);
//		allServers3 = service.getLdapServersByDomain(domain3);
//		
//		assertTrue(1 == allDomains.size());
//		assertTrue(0 == allServers.size());
//		assertTrue(0 == allServers1.size());
//		assertTrue(0 == allServers2.size());
//		assertTrue(0 == allServers3.size());
	}
	
	/*
	 * Tests getAllRoleAttributeKeys
	 */
	public void testGetAllRoleAttributeKeys() {
		commit();
		service = getLdapConfigurationService();
		
		List<RoleAttributeKeyInfo> all = service.getAllRoleAttributeKeys();
		
//		first delete all existing role attributes
		for (Iterator<RoleAttributeKeyInfo> iterator = all.iterator(); iterator.hasNext();) {
			RoleAttributeKeyInfo key = iterator.next();
			service.deleteRoleAttributeKey(key);
		}
		
//		create two role attributes
		RoleAttributeKeyInfo key1 = service.createRoleAttributeKey(createRoleAttributeKeyInfoDummy());
		assertNotNull(key1.getId());
		
		RoleAttributeKeyInfo key2 = service.createRoleAttributeKey(createRoleAttributeKeyInfoDummy());
		assertNotNull(key2.getId());
		
//		check for number two	
		all = service.getAllRoleAttributeKeys();
		assertTrue(2 == all.size());
	}
	
	
	
	/*
	 * Tests creation and manipulation of LdapServer/Info objects
	 */
	public void testCreateLdapServer() {
		
		service = getLdapConfigurationService();
		
		// create UserDnPattern
		UserDnPatternInfo patternInfo = createUserDnPatternInfoDummy();
		assertNotNull(patternInfo);
		assertNull(patternInfo.getId());
		
		patternInfo = service.createUserDnPattern(patternInfo);		
		assertNotNull(patternInfo.getId());
		
//		****************** create domain for server ******************************
		
//		create RoleAttributeKeyInfo object
		RoleAttributeKeyInfo roleAttrKeyInfo = createRoleAttributeKeyInfoDummy();
		assertNull(roleAttrKeyInfo.getId());
		roleAttrKeyInfo = service.createRoleAttributeKey(roleAttrKeyInfo);
		assertNotNull(roleAttrKeyInfo.getId());
		
//		create AttributeMappingInfo object
		AttributeMappingInfo attrMappingInfo = createAttributeMappingInfoDummy(roleAttrKeyInfo);
		assertNull(attrMappingInfo.getId());
		attrMappingInfo = service.createAttributeMapping(attrMappingInfo);
		assertNotNull(attrMappingInfo.getId());
		assertTrue(0 < attrMappingInfo.getRoleAttributeKeyIds().size());
		
//		create AuthenticationDomainInfo object
		AuthenticationDomainInfo domainInfo = createAuthenticationDomainInfoDummy(attrMappingInfo);
		assertNull(domainInfo.getId());
		domainInfo = service.createDomain(domainInfo);
		assertNotNull(domainInfo.getId());	
		assertNotNull(domainInfo.getAttributeMappingId());
		
//		*********************** create LdapServerInfo object ********************
		
		LdapServerInfo server = createLdapServerInfoDummy(domainInfo, patternInfo, true);
		assertNotNull(server);
		assertNull(server.getId());
		
		// save ldap info object to DB
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
		
		// set new value
		server.setProviderUrl("ldap://valid.ldap.com");
		server.setPort(389);
		
		// test exception
		try {
			service.saveLdapServer(server);
		} catch (LdapConfigurationServiceException expected) {
			fail("Should NOT have raised any LdapConfigurationServiceException");
		}
		
//		check delete method
		List<LdapServerInfo> all = service.getAllLdapServers();
		int x = all.size();		
		service.deleteLdapServer(server);
		all = service.getAllLdapServers();
		assertTrue(x > all.size());
		
	}
	
	
	/**
	 * Test get all enabled LDAP server configurations
	 */
	public void testGetEnabledLdapServerConfigurations() {
		service = getLdapConfigurationService();
		
		UserDnPatternInfo userDnPattern = createUserDnPatternInfoDummy();
		service.createUserDnPattern(userDnPattern);
		assertNotNull(userDnPattern.getId());
		
		RoleAttributeKeyInfo roleAttributeKey = createRoleAttributeKeyInfoDummy();
		service.createRoleAttributeKey(roleAttributeKey);
		assertNotNull(roleAttributeKey.getId());
		
		AttributeMappingInfo mapping = createAttributeMappingInfoDummy(roleAttributeKey);
		service.createAttributeMapping(mapping);
		assertNotNull(mapping.getId());
		
		AuthenticationDomainInfo domain = createAuthenticationDomainInfoDummy(mapping);
		service.createDomain(domain);
		assertNotNull(domain.getId());
		
		assertNotNull(service.createLdapServer(createLdapServerInfoDummy(domain, userDnPattern, true)).getId());		
		assertNotNull(service.createLdapServer(createLdapServerInfoDummy(domain, userDnPattern, true)).getId());		
		assertNotNull(service.createLdapServer(createLdapServerInfoDummy(domain, userDnPattern, false)).getId());		
		
		assertTrue(2 == service.getEnabledLdapServerConfigurations().size());		
		
	}
	
	
	/*
	 * Tests getAll-methods of LdapServer objects
	 */
	public void testGetAllLdapServers() {
		
		service = getLdapConfigurationService();
		
		UserDnPatternInfo userDnPattern = createUserDnPatternInfoDummy();
		service.createUserDnPattern(userDnPattern);
		assertNotNull(userDnPattern.getId());
		
		RoleAttributeKeyInfo roleAttributeKey = createRoleAttributeKeyInfoDummy();
		service.createRoleAttributeKey(roleAttributeKey);
		assertNotNull(roleAttributeKey.getId());
		
		AttributeMappingInfo mapping = createAttributeMappingInfoDummy(roleAttributeKey);
		service.createAttributeMapping(mapping);
		assertNotNull(mapping.getId());
		
		AuthenticationDomainInfo domain = createAuthenticationDomainInfoDummy(mapping);
		service.createDomain(domain);
		assertNotNull(domain.getId());
		
		assertNotNull(service.createLdapServer(createLdapServerInfoDummy(domain, userDnPattern, true)).getId());		
		assertNotNull(service.createLdapServer(createLdapServerInfoDummy(domain, userDnPattern, true)).getId());		
		assertNotNull(service.createLdapServer(createLdapServerInfoDummy(domain, userDnPattern, false)).getId());
		
		List<LdapServerInfo> servers = service.getAllLdapServers();
		int size = servers.size();
		assertTrue(0 < size);
		
		
		List<AuthenticationDomainInfo> domains = service.getAllDomains();
		int d = domains.size();
		assertTrue(0 < d);
		
//		assertTrue(server1.getAuthenticationDomainId() == domain1.getId());
//		List<Long> ids = new ArrayList<Long>();
//		ids.add(server1.getId());
//		ids.add(server2.getId());
//		List<Long> ids2 = domain1.getLdapServerIds();
//		assertNotNull(ids2);
//		//assertTrue(domain1.getLdapServerIds().equals(ids));
		
//		List<LdapServerInfo> serversByDomain1 = service.getLdapServersByDomain(domain1);
//		int d1 = serversByDomain1.size();
//		System.out.println(d1);
//		assertTrue(2 == d1);
//		
//		List<LdapServerInfo> serversByDomain2 = service.getLdapServersByDomain(domain2);
//		int d2 = serversByDomain2.size();
//		System.out.println(d2);
//		assertTrue(1 == d2);
	}
	
	
	/*
	 * Tests creation and manipulation of RoleAttributeKey/Info objects
	 */
	public void testCreateRoleAttributeKey() {
		service = getLdapConfigurationService();
		
		// create value object
		RoleAttributeKeyInfo key = createRoleAttributeKeyInfoDummy();
		assertNotNull(key);
		assertNull(key.getId());
		
		// save value object to DB
		key = service.createRoleAttributeKey(key);
		assertNotNull(key);
		assertNotNull(key.getId());
		
		// set new value
		key.setName(null);
		try {
			service.saveRoleAttributeKey(key);
			fail("Should have raised an LdapConfigurationServiceException: new attribute key must not be empty!");
		} catch (LdapConfigurationServiceException expected) {
		}
		
	}
	
	
	
	
	/*
	 * Tests creation and manipulation of AttributeMapping/Info objects
	 */
	public void testCreateAttributeMapping() {
		service = getLdapConfigurationService();		
		RoleAttributeKeyInfo key = createRoleAttributeKeyInfoDummy();
		key = service.createRoleAttributeKey(key);		
		AttributeMappingInfo mapping = createAttributeMappingInfoDummy(key);
		assertNotNull(mapping);
		assertNull(mapping.getId());		
		mapping = service.createAttributeMapping(mapping);
		assertNotNull(mapping);
		assertNotNull(mapping.getId());
	}
	
	
	
	
//	public void testLdapConfigurationService() {
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
//		
//	}
//	
//	public void testAllDaoCreate() {	
//	
////		create role attribute object
//		RoleAttributeKey roleAttributeKey = RoleAttributeKey.Factory.newInstance();
//		roleAttributeKey.setName("CN");		
//		
//		assertNull(roleAttributeKey.getId());
//		roleAttributeKeyDao.create(roleAttributeKey);
//		assertNotNull(roleAttributeKey.getId());
//		
////		create user dn pattern object
//		UserDnPattern userDnPattern = UserDnPattern.Factory.newInstance();
//		userDnPattern.setName("memberOf");
//	
//		assertNull(userDnPattern.getId());
//		userDnPatternDao.create(userDnPattern);
//		assertNotNull(userDnPattern.getId());		
//		
////		create attribute mapping object
//		AttributeMapping attributeMapping = AttributeMapping.Factory.newInstance();		
//		attributeMapping.setMappingName(testUtility.unique("Mapping Test"));
//		attributeMapping.setUsernameKey("CN");
//		attributeMapping.setFirstNameKey("givenName");
//		attributeMapping.setLastNameKey("SN");
//		attributeMapping.setEmailKey("mail");
//		attributeMapping.setGroupRoleAttributeKey("CN");		
////		attributeMapping.setAuthenticationDomains(authenticationDomains);
//		
//		List<RoleAttributeKey> roleAttributeKeys = new ArrayList<RoleAttributeKey>();
//		roleAttributeKeys.add(roleAttributeKey);
//		attributeMapping.setRoleAttributeKeys(roleAttributeKeys);
//		
//		assertNull(attributeMapping.getId());
//		attributeMappingDao.create(attributeMapping);
//		assertNotNull(attributeMapping.getId());
//		
////		create authentication domain object
//		AuthenticationDomain authenticationDomain = AuthenticationDomain.Factory.newInstance();
//		authenticationDomain.setName(testUtility.unique("WWU"));
//		authenticationDomain.setDescription("WWU Domain");
//		authenticationDomain.setAttributeMapping(attributeMapping);
////		Set<AuthenticationDomain> authenticationDomains = new HashSet<AuthenticationDomain>();
////		authenticationDomains.add(authenticationDomain);
//		
//		assertNull(authenticationDomain.getId());
//		authenticationDomainDao.create(authenticationDomain);
//		assertNotNull(authenticationDomain.getId());
//		
////		create LDAP server object
//		LdapServer ldapServer = LdapServer.Factory.newInstance();
//		ldapServer.setProviderUrl("ldap://wwusv1.uni-muenster.de");
//		ldapServer.setPort(389);
//		ldapServer.setRootDn("dc=uni-muenster,dc=de");
//		ldapServer.setDescription("LDAP Server WWU");
//		ldapServer.setEnabled(true);
//		ldapServer.setLdapServerType(LdapServerType.ACTIVE_DIRECTORY);
//		ldapServer.setAuthenticationType("DIGEST-MD5");
//		ldapServer.setUseConnectionPool(false);
//		ldapServer.setManagerDn("admin");
//		ldapServer.setManagerPassword("hidden");
//		ldapServer.setUseLdapContext(true);
//		List<UserDnPattern> userDnPatterns = new ArrayList<UserDnPattern>();
//		userDnPatterns.add(userDnPattern);
//		ldapServer.setUserDnPatterns(userDnPatterns);		
//		ldapServer.setAuthenticationDomain(authenticationDomain);		
//		
//		assertNull(ldapServer.getId());
//		ldapServerDao.create(ldapServer);
//		assertNotNull(ldapServer.getId());
//
//		ldapServerDao.remove(ldapServer);
//		assertNull(ldapServer.getId());
//	}


	
	
	public void setAuthenticationDomainDao(AuthenticationDomainDao authenticationDomainDao) {
		this.authenticationDomainDao = authenticationDomainDao;
	}


	public void setRoleAttributeKeyDao(RoleAttributeKeyDao roleAttributeKeyDao) {
		this.roleAttributeKeyDao = roleAttributeKeyDao;
	}


	public void setUserDnPatternDao(UserDnPatternDao userDnPatternDao) {
		this.userDnPatternDao = userDnPatternDao;
	}

	public void setLdapServerDao(LdapServerDao ldapServerDao) {
		this.ldapServerDao = ldapServerDao;
	}

	public void setAttributeMappingDao(AttributeMappingDao attributeMappingDao) {
		this.attributeMappingDao = attributeMappingDao;
	}

	public void setService(LdapConfigurationService service) {
		this.service = service;
	}
	
	
	
	
	
}