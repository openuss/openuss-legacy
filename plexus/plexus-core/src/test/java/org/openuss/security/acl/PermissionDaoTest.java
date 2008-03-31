// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.acl;

import java.util.List;

import org.openuss.TestUtility;
import org.openuss.security.User;
import org.openuss.security.acegi.acl.AclPermissionAdapter;


/**
 * JUnit Test for Spring Hibernate PermissionDao class.
 * @see org.openuss.security.acl.PermissionDao
 */
public class PermissionDaoTest extends PermissionDaoTestBase {
	
	private ObjectIdentityDao objectIdentityDao;
	
	private TestUtility testUtility;
	

	public void testPermissionDaoCreate() {
		User user = testUtility.createUniqueUserInDB();
		ObjectIdentity objectIdentity = createAndTestObjectIdentityInDB(null);
		createAndTestPermission(user, objectIdentity);
	}

	public void testFindPermission() {
		User user = testUtility.createUniqueUserInDB();
		ObjectIdentity objectIdentity = createAndTestObjectIdentityInDB(null);
		Permission permission = createAndTestPermission(user, objectIdentity);
		
		flush();
		
		Permission found = permissionDao.findPermission(objectIdentity, user);
		assertNotNull(found);
		assertEquals(permission, found);
		assertEquals(objectIdentity, permission.getPermissionPk().getAclObjectIdentity());
	}
	
	public void testFindPermissionWithRecipient() {
		User user = testUtility.createUniqueUserInDB();
		ObjectIdentity grandgrandParent = createAndTestObjectIdentityInDB(null);
		ObjectIdentity grandParent = createAndTestObjectIdentityInDB(grandgrandParent);
		ObjectIdentity parent = createAndTestObjectIdentityInDB(grandParent);
		ObjectIdentity objectIdentity = createAndTestObjectIdentityInDB(parent);
		
		Permission permission = createAndTestPermission(user, objectIdentity);
		
		flush();
		
		List<AclPermissionAdapter> list = permissionDao.findPermissionsWithRecipient(objectIdentity);
		assertNotNull(list);
		assertTrue(list.size() == 1);
		assertTrue(list.get(0).getMask() == permission.getMask());
		
	}

	private Permission createAndTestPermission(User user, ObjectIdentity objectIdentity) {
		Permission permission = Permission.Factory.newInstance();
		permission.setPermissionPk(new PermissionPK());
		permission.setMask(2);
		permission.getPermissionPk().setRecipient(user);
		permission.getPermissionPk().setAclObjectIdentity(objectIdentity);
		objectIdentity.addPermission(permission);
		permissionDao.create(permission);
		return permission;
	}

	private ObjectIdentity createAndTestObjectIdentityInDB(ObjectIdentity parent) {
		ObjectIdentity objectIdentity = ObjectIdentity.Factory.newInstance();
		objectIdentity.setId(TestUtility.unique());
		objectIdentity.setParent(parent);
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