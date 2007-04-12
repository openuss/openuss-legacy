// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.registration;

import java.sql.Timestamp;

import org.openuss.TestUtility;
import org.openuss.registration.ActivationCode;
import org.openuss.registration.ActivationCodeDaoTestBase;
import org.openuss.security.User;
import org.openuss.security.UserDao;


/**
 * JUnit Test for Spring Hibernate ActivationCodeDao class.
 * @see org.openuss.registration.ActivationCodeDao
 */
public class ActivationCodeDaoTest extends ActivationCodeDaoTestBase {
	
	private static final String CODE = "ABCD012345678901234567890123456789012345678901234567890123456789";
	
	private UserDao userDao;
	private ActivationCode registrationCode;

	private User user;
	
	private TestUtility testUtility;

	public void testUserDaoInjection() {
		assertNotNull(userDao);
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
		user = testUtility.createDefaultUser();
		assertNull(user.getId());
		
		registrationCode = ActivationCode.Factory.newInstance();
		registrationCode.setCode(CODE);
		registrationCode.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		registrationCode.setUser(user);		
		assertNull(registrationCode.getId());
	}
	
	public void testRegistrationCodeDaoCreate() {
		userDao.create(user);
		assertNotNull(user.getId());
		activationCodeDao.create(registrationCode);
		assertNotNull(registrationCode.getId());
		setComplete();
		endTransaction();

		ActivationCode loaded = activationCodeDao.load(registrationCode.getId());
		assertNotNull(loaded);
		activationCodeDao.remove(loaded);
		loaded = activationCodeDao.load(registrationCode.getId());
		assertNull(loaded);
		
		// cleanup
		userDao.remove(user);
		setComplete();
		endTransaction();
	}
	
	
	public void testFindByCode() {
		userDao.create(user);
		assertNotNull(user.getId());
		activationCodeDao.create(registrationCode);
		assertNotNull(registrationCode.getId());

		setComplete();
		endTransaction();
		
		ActivationCode loaded = activationCodeDao.findByCode(CODE);
		assertEquals(registrationCode.getId(), loaded.getId());

		// cleanup
		activationCodeDao.remove(registrationCode);
		userDao.remove(user);

		setComplete();
		endTransaction();

	}

	public UserDao getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
}