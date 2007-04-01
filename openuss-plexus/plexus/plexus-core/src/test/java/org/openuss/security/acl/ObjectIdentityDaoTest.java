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
		ObjectIdentity objectIdentity = ObjectIdentity.Factory.newInstance();
		long id = testUtility.unique();
		objectIdentity.setId(id);
		objectIdentity.setParent(null);
		objectIdentityDao.create(objectIdentity);

		commitAndNewTransaction();
		
		// load objectIdentity from db
		ObjectIdentity oid = objectIdentityDao.load(id);
		assertNotNull(oid);
		assertEquals(oid, objectIdentity);
		
		// remove objectIdentity from db
		objectIdentityDao.remove(oid);
		
		commitAndNewTransaction();
		
		ObjectIdentity noid = objectIdentityDao.load(id);
		assertNull(noid);
		
	}

	private void commitAndNewTransaction() {
		// commit and start new transaction
		setComplete();
		endTransaction();
		startNewTransaction();
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
}