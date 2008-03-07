// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.smartcardio.ATR;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate RoleAttributeKeyDao class.
 * @see org.openuss.security.ldap.RoleAttributeKeyDao
 */
public class RoleAttributeKeyDaoTest extends RoleAttributeKeyDaoTestBase {
	/**
	 * Logger for this class
	 */	
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
		
		AttributeMapping myAttr = attributeMappingDao.create("email", "Hans", "groupRole", "Mustermann", "mapping test", roleAttr, "user");
			
		List<AttributeMapping> attrList = new ArrayList<AttributeMapping>();
		attrList.add(myAttr);
			
//		roleAttributeKey.setAttributeMappings(attrList);		
//		roleAttributeKeyDao.update(roleAttributeKey);
//		
//		setComplete();
//		endTransaction();
//		
//		startNewTransaction();
		
//		roleAttributeKeyDao.remove(roleAttributeKey.getId());
//		attributeMappingDao.remove(myAttr);
//		
//		setComplete();
//		endTransaction();
//		
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
	
	
	
	
}
