// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import java.util.ArrayList;
import java.util.List;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate AttributeMappingDao class.
 * @see org.openuss.security.ldap.AttributeMappingDao
 */
public class AttributeMappingDaoTest extends AttributeMappingDaoTestBase {
	
	private RoleAttributeKeyDao roleAttributeKeyDao;

	
	
	private TestUtility testUtility;
	
	
	public void testRoleAttributeKeyDaoInjection() {
		assertNotNull(roleAttributeKeyDao);
	}
	
	
	public void testAttributeMappingDaoCreate() {	
	
		RoleAttributeKey roleAttributeKey = RoleAttributeKey.Factory.newInstance();
		roleAttributeKey.setName(testUtility.unique("CN"));
		assertNull(roleAttributeKey.getId());
		roleAttributeKeyDao.create(roleAttributeKey);
		assertNotNull(roleAttributeKey.getId());
		
		List<RoleAttributeKey> roleAttr = new ArrayList<RoleAttributeKey>();
		roleAttr.add(roleAttributeKey);
		
		AttributeMapping myAttr = attributeMappingDao.create("email", "Hans", "groupRole", "Mustermann", "mapping test", roleAttr, "user");
		assertNotNull(myAttr.getId());
		
		List<AttributeMapping> attrList = new ArrayList<AttributeMapping>();
		attrList.add(myAttr);
					
	}


	

	public void setRoleAttributeKeyDao(RoleAttributeKeyDao roleAttributeKeyDao) {
		this.roleAttributeKeyDao = roleAttributeKeyDao;
	}

	
	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
	
}
