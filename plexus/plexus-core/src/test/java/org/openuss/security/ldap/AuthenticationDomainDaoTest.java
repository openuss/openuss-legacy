// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import java.util.ArrayList;
import java.util.List;


/**
 * JUnit Test for Spring Hibernate AuthenticationDomainDao class.
 * @see org.openuss.security.ldap.AuthenticationDomainDao
 */
public class AuthenticationDomainDaoTest extends AuthenticationDomainDaoTestBase {
	
	private AttributeMappingDao attributeMappingDao;
	private RoleAttributeKeyDao roleAttributeKeyDao;
	
	public void testAuthenticationDomainDaoCreate() {
		AuthenticationDomain authenticationDomain = new AuthenticationDomainImpl();
		authenticationDomain.setName("OpenuSS ");
		authenticationDomain.setDescription("OpenUSS Domain ");
		assertNull(authenticationDomain.getId());
		
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
		roleAttributeKey.setName("test1");
		
		List<RoleAttributeKey> roleAttributeKeys = new ArrayList<RoleAttributeKey>();
		roleAttributeKeys.add(roleAttributeKey);
		
		attributeMapping.setRoleAttributeKeys(roleAttributeKeys);
		authenticationDomain.setAttributeMapping(attributeMapping);
		
		roleAttributeKeyDao.create(roleAttributeKeys);
		attributeMappingDao.create(attributeMapping);

		authenticationDomainDao.create(authenticationDomain);
				
		assertNotNull(authenticationDomain.getId());
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
}
