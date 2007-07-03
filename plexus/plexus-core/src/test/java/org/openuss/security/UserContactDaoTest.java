// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import org.openuss.TestUtility;

/**
 * JUnit Test for Spring Hibernate PersonDao class.
 * @see org.openuss.security.PersonDao
 */
public class UserContactDaoTest extends UserContactDaoTestBase {
	
	private UserDao userDao;
	private UserContact contact;
	
	private TestUtility testUtility;
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void testInjectionUserDao() {
		assertNotNull(userDao);
	}
	
	@Override
	protected void onSetUpBeforeTransaction() {
		contact = UserContact.Factory.newInstance();
		contact.setFirstName("firstName");
		contact.setLastName("lastName");
		contact.setTitle("title");
		contact.setAddress("address");
		contact.setCity("city");
		contact.setPostcode("12359");
		contact.setCountry("Germany");
		contact.setProfession("profession");
		contact.setTelephone("+49 999 99999999");
	}

	public void testPersonDaoCreate() {
		assertNull(contact.getId());
		userContactDao.create(contact);
		assertNotNull(contact.getId());
		setComplete();
		endTransaction();
	}
	
	public void testUserContactAssociation() {
		User user = testUtility.createDefaultUser();
		user.setContact(contact);
		assertNull(user.getId());
		assertNull(contact.getId());
		
		userContactDao.create(contact);
		userDao.create(user);
		
		setComplete();
		endTransaction();
		startNewTransaction();
		
		assertNotNull(user.getId());
		assertNotNull(contact.getId());
		
		User testUser = userDao.load(user.getId());

		assertEquals(user.getId(), testUser.getId());
		
		// remove created record after test
		userDao.remove(user.getId());
		setComplete();
	}

	
	public void testCreateRemoveLoad() {
		assertNull(contact.getId());
		userContactDao.create(contact);
		assertNotNull(contact.getId());
		setComplete();
		endTransaction();
		UserContact loaded = userContactDao.load(contact.getId());
		assertEquals(contact.getId(), loaded.getId());
		userContactDao.remove(loaded);
		loaded = userContactDao.load(contact.getId());
		assertNull(loaded);
		// clean up
		setComplete();
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

}