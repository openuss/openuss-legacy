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
 * JUnit Test for Spring Hibernate RoleAttributeKeyDao class.
 * @see org.openuss.security.ldap.RoleAttributeKeyDao
 */
public class RoleAttributeKeyDaoTest extends RoleAttributeKeyDaoTestBase {

	private TestUtility testUtility;
	
	private AttributeMappingDao attributeMappingDao;
	
	public void testRoleAttributeKeyDaoCreate() {
		RoleAttributeKey roleAttributeKey = RoleAttributeKey.Factory.newInstance();
		roleAttributeKey.setName(testUtility.unique("roleattribute test"));
		assertNull(roleAttributeKey.getId());
		roleAttributeKeyDao.create(roleAttributeKey);
		assertNotNull(roleAttributeKey.getId());
		setComplete();
		endTransaction();
	}

	
	public void testAddAttributeMappingToRoleAttributeKey() {
		RoleAttributeKey roleAttributeKey = RoleAttributeKey.Factory.newInstance();
		roleAttributeKey.setName(testUtility.unique("roleattribute test"));
		assertNull(roleAttributeKey.getId());
		roleAttributeKeyDao.create(roleAttributeKey);
		assertNotNull(roleAttributeKey.getId());
		
		List<RoleAttributeKey> roleAttr = new ArrayList<RoleAttributeKey>();
		roleAttr.add(roleAttributeKey);
		
		AttributeMapping myAttr = AttributeMapping.Factory.newInstance();
		myAttr.setEmailKey("email");
		myAttr.setGroupRoleAttributeKey("groupRole");
		myAttr.setLastNameKey("lastname key");
		myAttr.setFirstNameKey("firstname key");
		myAttr.setMappingName("mapping test");
		myAttr.setRoleAttributeKeys(roleAttr);
		myAttr.setUsernameKey("user");

		attributeMappingDao.create(myAttr);
			
		List<AttributeMapping> attrList = new ArrayList<AttributeMapping>();
		attrList.add(myAttr);
	}
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}


	public void setAttributeMappingDao(AttributeMappingDao attributeMappingDao) {
		this.attributeMappingDao = attributeMappingDao;
	}
	
	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext.xml", 
			"classpath*:applicationContext-beans.xml",
			"classpath*:applicationContext-lucene.xml",
			"classpath*:applicationContext-cache.xml", 
			"classpath*:applicationContext-messaging.xml",
			"classpath*:applicationContext-resources.xml",
			"classpath*:testContext.xml", 
			"classpath*:testDisableSecurity.xml", 
			"classpath*:testDataSource.xml"};
	}

	
	
}
