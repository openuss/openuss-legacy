// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.acl;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate ObjectIdentityDao class.
 * @see org.openuss.security.acl.ObjectIdentityDao
 */
public class ObjectIdentityDaoTest extends ObjectIdentityDaoTestBase {
	
	private TestUtility testUtility;
	
	public void testObjectIdentityDaoCreate() {
		ObjectIdentity objectIdentity = createObjectIdentity(null);
		objectIdentityDao.create(objectIdentity);

		flush();
		
		// load objectIdentity from db
		ObjectIdentity oid = objectIdentityDao.load(objectIdentity.getId());
		assertNotNull(oid);
		assertEquals(oid, objectIdentity);
		
		// remove objectIdentity from db
		objectIdentityDao.remove(oid);
		
		flush();
		
		ObjectIdentity noid = objectIdentityDao.load(objectIdentity.getId());
		assertNull(noid);
	}

	public void testCascadingDelete() {
		ObjectIdentity root = createObjectIdentity(null);
		objectIdentityDao.create(root);
		ObjectIdentity level1 = createObjectIdentity(root);
		objectIdentityDao.create(level1);
		ObjectIdentity level2 = createObjectIdentity(level1);
		objectIdentityDao.create(level2);
		ObjectIdentity level3 = createObjectIdentity(level2);
		objectIdentityDao.create(level3);
		
		commit();
		
		ObjectIdentity loaded = objectIdentityDao.load(level3.getId());
		assertEquals(level3, loaded);
		assertEquals(level2, loaded.getParent());
		assertEquals(level1, loaded.getParent().getParent());
		assertEquals(root, loaded.getParent().getParent().getParent());
		assertTrue(ObjectIdentityImpl.SYSTEM_OBJECT_IDENTITY == loaded.getParent().getParent().getParent().getParent().getId());
		
		objectIdentityDao.remove(root.getId());
		
		commit();
		
		assertNull(objectIdentityDao.load(level3.getId()));
		
	}

	private ObjectIdentity createObjectIdentity(ObjectIdentity parent) {
		ObjectIdentity objectIdentity = ObjectIdentity.Factory.newInstance();
		objectIdentity.setId(TestUtility.unique());
		objectIdentity.setParent(parent);
		return objectIdentity;
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
}