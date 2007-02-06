// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.acl;


/**
 * JUnit Test for Spring Hibernate ObjectIdentityDao class.
 * @see org.openuss.security.acl.ObjectIdentityDao
 */
public class ObjectIdentityDaoTest extends ObjectIdentityDaoTestBase {
	
	public void testObjectIdentityDaoCreate() {
		ObjectIdentity objectIdentity = ObjectIdentity.Factory.newInstance();
//		objectIdentity.setAclClass("org.openuss.security.acl.EntityObjectIdentity");
		objectIdentity.setObjectIdentity(4711L);
//		objectIdentity.setObjectIdentityClass("org.openuss.test.dummy.4711");
		objectIdentity.setParent(null);
		assertNull(objectIdentity.getId());
		objectIdentityDao.create(objectIdentity);
		assertNotNull(objectIdentity.getId());

		commitAndNewTransaction();
		
		// load objectIdentity from db
		ObjectIdentity oid = objectIdentityDao.load(objectIdentity.getId());
		assertNotNull(oid);
		assertEquals(oid, objectIdentity);
		
		// remove objectIdentity from db
		objectIdentityDao.remove(oid);
		
		commitAndNewTransaction();
		
		ObjectIdentity noid = objectIdentityDao.load(oid.getId());
		assertNull(noid);
		
	}

	private void commitAndNewTransaction() {
		// commit and start new transaction
		setComplete();
		endTransaction();
		startNewTransaction();
	}
}