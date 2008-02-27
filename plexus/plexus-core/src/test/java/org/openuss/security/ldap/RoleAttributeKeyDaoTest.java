// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;


/**
 * JUnit Test for Spring Hibernate RoleAttributeKeyDao class.
 * @see org.openuss.security.ldap.RoleAttributeKeyDao
 */
public class RoleAttributeKeyDaoTest extends RoleAttributeKeyDaoTestBase {
	
	public void testRoleAttributeKeyDaoCreate() {
		RoleAttributeKey roleAttributeKey = RoleAttributeKey.Factory.newInstance();
		roleAttributeKey.setRoleAttributeKey("roleattribute test");
		assertNull(roleAttributeKey.getId());
		roleAttributeKeyDao.create(roleAttributeKey);
		assertNotNull(roleAttributeKey.getId());
	}
}
