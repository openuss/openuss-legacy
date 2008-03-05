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
 * JUnit Test for Spring Hibernate RoleAttributeKeySetDao class.
 * @see org.openuss.security.ldap.RoleAttributeKeySetDao
 */
public class RoleAttributeKeySetDaoTest extends RoleAttributeKeySetDaoTestBase {
	
	private TestUtility testUtility;
	
	public void testRoleAttributeKeySetDaoCreate() {
		
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
	}
	

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

}
