// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate UserProfileDao class.
 * @see org.openuss.security.UserProfileDao
 */
public class UserProfileDaoTest extends UserProfileDaoTestBase {
	
	private UserProfile userProfile;
	private User user;
	private UserDao userDao;
	
	private TestUtility testUtility;

	@Override
	protected void onSetUpBeforeTransaction() throws Exception {
		super.onSetUpBeforeTransaction();
		// create empty user profile
		userProfile = new UserProfileImpl();
		userProfile.setImageFileId(null);
		userProfile.setTelephonePublic(true);
		userProfile.setEmailPublic(true);
		userProfile.setAddressPublic(true);
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		user = testUtility.createDefaultUser();
		userDao.create(user);
	}
	
	public void testUserProfileDaoCreate() {
		assertNull(userProfile.getId());
		userProfileDao.create(userProfile);
		assertNotNull(userProfile.getId());
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