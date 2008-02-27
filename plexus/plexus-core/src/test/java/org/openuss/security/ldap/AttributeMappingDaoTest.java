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
 * JUnit Test for Spring Hibernate AttributeMappingDao class.
 * @see org.openuss.security.ldap.AttributeMappingDao
 */
public class AttributeMappingDaoTest extends AttributeMappingDaoTestBase {
	
	private AuthenticationDomainDao authenticationDomainDao;
	private RoleAttributeKeyDao roleAttributeKeyDao;
	private RoleAttributeKeySetDao roleAttributeKeySetDao;
	private UserDnPatternDao userDnPatternDao;
	private UserDnPatternSetDao userDnPatternSetDao;
	private LdapServerDao ldapServerDao;
	
	
	
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
	
	
public void testAttributeMappingDaoCreate() {		
		
//		create ldap server object
		LdapServer ldapServer = LdapServer.Factory.newInstance();
		ldapServer.setProviderUrl("ldap://wwusv1.uni-muenster.de");
		ldapServer.setPort(389);
		ldapServer.setRootDn("dc=uni-muenster,dc=de");
		ldapServer.setDescription("LDAP Server WWU");
		ldapServer.setEnabled(true);
		ldapServer.setLdapServerType(LdapServerType.ACTIVE_DIRECTORY);
		ldapServer.setUseConnectionPool(false);
		ldapServer.setManagerDn("admin");
		ldapServer.setManagerPassword("hidden");
		ldapServer.setUseLdapContext(true);
		Set<LdapServer> ldapServers = new HashSet<LdapServer>();
		ldapServers.add(ldapServer);
	
//		create user dn pattern object
		UserDnPattern userDnPattern = UserDnPattern.Factory.newInstance("memberOf");		
		List<UserDnPattern> userDnPatterns = new ArrayList<UserDnPattern>();
		userDnPatterns.add(userDnPattern);		
		
//		create user dn pattern set object
		UserDnPatternSet userDnPatternSet = UserDnPatternSet.Factory.newInstance();
		userDnPatternSet.setName("WWU user dn pattern");
		userDnPatternSet.setUserDnPatterns(userDnPatterns);
		userDnPatternSet.setLdapServers(ldapServers);		

		ldapServer.setUserDnPatternSet(userDnPatternSet);
		
//		create role attribute key object
		RoleAttributeKey roleAttributeKey = RoleAttributeKey.Factory.newInstance("CN");
		List<RoleAttributeKey> roleAttributeKeys = new ArrayList<RoleAttributeKey>();
		roleAttributeKeys.add(roleAttributeKey);
		
//		create role attribute key set object
		RoleAttributeKeySet roleAttributeKeySet = RoleAttributeKeySet.Factory.newInstance();
		roleAttributeKeySet.setName("WWU role set");
		roleAttributeKeySet.setRoleAttributeKeys(roleAttributeKeys);
		
//		create authentication domain object
		AuthenticationDomain authenticationDomain = AuthenticationDomain.Factory.newInstance();
		authenticationDomain.setName("WWU");
		authenticationDomain.setDescription("WWU Domain");
		
		authenticationDomain.setLdapServers(ldapServers);
		Set<AuthenticationDomain> authenticationDomains = new HashSet<AuthenticationDomain>();
		authenticationDomains.add(authenticationDomain);
		
		ldapServer.setAuthenticationDomain(authenticationDomain);
		
//		create attribute mapping object
		AttributeMapping attributeMapping = AttributeMapping.Factory.newInstance();		
		attributeMapping.setMappingName("Mapping Test");
		attributeMapping.setUsernameKey("CN");
		attributeMapping.setFirstNameKey("givenName");
		attributeMapping.setLastNameKey("SN");
		attributeMapping.setEmailKey("mail");
		attributeMapping.setGroupRoleAttributeKey("CN");		
		attributeMapping.setAuthenticationDomains(authenticationDomains);
		
		List<AttributeMapping> attributeMappings = new ArrayList<AttributeMapping>();
		attributeMappings.add(attributeMapping);
		
		roleAttributeKeySet.setAttributeMappings(attributeMappings);		
		authenticationDomain.setAttributeMapping(attributeMapping);
		
		assertNull(attributeMapping.getId());
		attributeMappingDao.create(attributeMapping);
		assertNotNull(attributeMapping.getId());		
		setComplete();
		endTransaction();
		
		startNewTransaction();
		attributeMappingDao.remove(attributeMapping);
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
	
	
	
}
