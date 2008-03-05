// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import java.util.ArrayList;
import java.util.List;


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
	
//		create role attribute object
		RoleAttributeKey roleAttributeKey = RoleAttributeKey.Factory.newInstance();
		roleAttributeKey.setName("CN");		
		List<RoleAttributeKey> roleAttributeKeys = new ArrayList<RoleAttributeKey>();
		roleAttributeKeys.add(roleAttributeKey);
		
		RoleAttributeKeySet roleAttributeKeySet = RoleAttributeKeySet.Factory.newInstance();
		roleAttributeKeySet.setName("role attribute key set test");
		roleAttributeKeySet.setRoleAttributeKeys(roleAttributeKeys);
		
		assertNull(roleAttributeKeySet.getId());
		roleAttributeKeySetDao.create(roleAttributeKeySet);
		assertNotNull(roleAttributeKeySet.getId());
	
		
//		create attribute mapping object
		AttributeMapping attributeMapping = AttributeMapping.Factory.newInstance();		
		attributeMapping.setMappingName("Mapping Test");
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
