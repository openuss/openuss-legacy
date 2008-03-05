// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

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
	
	public void testRoleAttributeKeyDaoCreate() {
		RoleAttributeKey roleAttributeKey = RoleAttributeKey.Factory.newInstance();
		roleAttributeKey.setName(testUtility.unique("roleattribute test"));
		assertNull(roleAttributeKey.getId());
		roleAttributeKeyDao.create(roleAttributeKey);
		assertNotNull(roleAttributeKey.getId());
		setComplete();
		endTransaction();
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
	
}
