// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;


/**
 * JUnit Test for Spring Hibernate RoleAttributeKeySetDao class.
 * @see org.openuss.security.ldap.RoleAttributeKeySetDao
 */
public class RoleAttributeKeySetDaoTest extends RoleAttributeKeySetDaoTestBase {
	
	public void testRoleAttributeKeySetDaoCreate() {
		RoleAttributeKeySet roleAttributeKeySet = RoleAttributeKeySet.Factory.newInstance();
		roleAttributeKeySet.setName("role attribute test");
		assertNull(roleAttributeKeySet.getId());
		roleAttributeKeySetDao.create(roleAttributeKeySet);
		assertNotNull(roleAttributeKeySet.getId());
	}
}
