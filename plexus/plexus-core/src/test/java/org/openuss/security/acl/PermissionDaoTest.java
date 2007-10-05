// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.acl;

import org.openuss.TestUtility;
import org.openuss.security.User;


/**
 * JUnit Test for Spring Hibernate PermissionDao class.
 * @see org.openuss.security.acl.PermissionDao
 */
public class PermissionDaoTest extends PermissionDaoTestBase {
	
	private ObjectIdentityDao objectIdentityDao;
	
	private TestUtility testUtility;
	

	public void testPermissionDaoCreate() {
		User user = testUtility.createUniqueUserInDB();
		ObjectIdentity objectIdentity = createAndTestObjectIdentityInDB();
		createAndTestPermission(user, objectIdentity);
	}

	public void testFindPermission() {
		User user = testUtility.createUniqueUserInDB();
		ObjectIdentity objectIdentity = createAndTestObjectIdentityInDB();
		Permission permission = createAndTestPermission(user, objectIdentity);
		
		flush();
		
		Permission found = permissionDao.findPermission(objectIdentity, user);
		assertNotNull(found);
		assertEquals(permission, found);
		assertEquals(objectIdentity, permission.getAclObjectIdentity());
	}

	private Permission createAndTestPermission(User user, ObjectIdentity objectIdentity) {
		Permission permission = Permission.Factory.newInstance();
		permission.setMask(2);
		permission.setRecipient(user);
		permission.setAclObjectIdentity(objectIdentity);
		objectIdentity.addPermission(permission);
		assertNull(permission.getId());
		permissionDao.create(permission);
		assertNotNull(permission.getId());
		return permission;
	}

	private ObjectIdentity createAndTestObjectIdentityInDB() {
		ObjectIdentity objectIdentity = ObjectIdentity.Factory.newInstance();
		objectIdentity.setId(TestUtility.unique());
		objectIdentity.setParent(null);
		objectIdentityDao.create(objectIdentity);
		return objectIdentity;
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