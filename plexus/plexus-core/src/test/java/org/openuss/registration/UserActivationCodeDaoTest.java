// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.registration;

import java.sql.Timestamp;

import org.openuss.TestUtility;
import org.openuss.security.User;
import org.openuss.security.UserDao;


/**
 * JUnit Test for Spring Hibernate UserActivationCodeDao class.
 * @see org.openuss.registration.UserActivationCodeDao
 */
public class UserActivationCodeDaoTest extends UserActivationCodeDaoTestBase {
	private static final String CODE = "ABCD012345678901234567890123456789012345678901234567890123456789";
	private UserDao userDao;
	private UserActivationCode registrationCode;

	private User user;

	private TestUtility testUtility;

	public void testUserDaoInjection() {
		assertNotNull(userDao);
	}
	
	public void testUserActivationCodeDaoCreate() {
		UserActivationCode userActivationCode = new UserActivationCodeImpl();
		userActivationCode.setCode("code");
		userActivationCode.setUser(testUtility.createUniqueUserInDB());
		userActivationCode.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		assertNull(userActivationCode.getId());
		userActivationCodeDao.create(userActivationCode);
		assertNotNull(userActivationCode.getId());
	}
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
		user = testUtility.createUniqueUser();
		assertNull(user.getId());

		registrationCode = UserActivationCode.Factory.newInstance();
		registrationCode.setCode(CODE);
		registrationCode.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		registrationCode.setUser(user);
		assertNull(registrationCode.getId());
	}
	

	public void testRegistrationCodeDaoCreate() {
		userDao.create(user);
		assertNotNull(user.getId());
		userActivationCodeDao.create(registrationCode);
		assertNotNull(registrationCode.getId());
		setComplete();
		endTransaction();

		UserActivationCode loaded = userActivationCodeDao.load(registrationCode.getId());
		assertNotNull(loaded);
		userActivationCodeDao.remove(loaded);
		loaded = userActivationCodeDao.load(registrationCode.getId());
		assertNull(loaded);

		// cleanup
		userDao.remove(user);
		setComplete();
		endTransaction();
	}

	
	public void testFindByCode() {
		userDao.create(user);
		assertNotNull(user.getId());
		userActivationCodeDao.create(registrationCode);
		assertNotNull(registrationCode.getId());

		setComplete();
		endTransaction();

		UserActivationCode loaded = userActivationCodeDao.findByCode(CODE);
		assertEquals(registrationCode.getId(), loaded.getId());

		// cleanup
		userActivationCodeDao.remove(registrationCode);
		userDao.remove(user);

		setComplete();
		endTransaction();

	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}