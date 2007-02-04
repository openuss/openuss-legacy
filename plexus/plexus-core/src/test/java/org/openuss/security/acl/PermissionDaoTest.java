// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.acl;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate PermissionDao class.
 * @see org.openuss.security.acl.PermissionDao
 */
public class PermissionDaoTest extends PermissionDaoTestBase {
	
	private ObjectIdentityDao objectIdentityDao;
	
	private TestUtility testUtility;
	

	public void testPermissionDaoCreate() {
		ObjectIdentity objectIdentity = ObjectIdentity.Factory.newInstance();
		objectIdentity.setObjectIdentity(4711L);
		objectIdentity.setParent(null);
		assertNull(objectIdentity.getId());
		objectIdentityDao.create(objectIdentity);
		assertNotNull(objectIdentity.getId());
		
		Permission permission = Permission.Factory.newInstance();
		permission.setMask(2);
		permission.setRecipient(testUtility.createDefaultUserInDB());
		permission.setAclObjectIdentity(objectIdentity);
		objectIdentity.addPermission(permission);
		assertNull(permission.getId());
		permissionDao.create(permission);
		assertNotNull(permission.getId());
	}

	public ObjectIdentityDao getObjectIdentityDao() {
		return objectIdentityDao;
	}

	public void setObjectIdentityDao(ObjectIdentityDao objectIdentityDao) {
		this.objectIdentityDao = objectIdentityDao;
	}
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

}