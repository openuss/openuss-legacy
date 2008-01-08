// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import java.util.TimeZone;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate UserPreferencesDao class.
 * @see org.openuss.security.UserPreferencesDao
 */
public class UserPreferencesDaoTest extends UserPreferencesDaoTestBase {
	
	private UserDao userDao;
	
	private TestUtility testUtility;
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public void testUserDaoInjection() {
		assertNotNull(userDao);
	}
	
	public void testUserPreferencesDaoCreate() {
		UserPreferences userPreferences = new UserPreferencesImpl();
		userPreferences.setLocale("de");
		userPreferences.setTheme("plexus");
		userPreferences.setTimezone(TimeZone.getDefault().getID());
		assertNull(userPreferences.getId());
		userPreferencesDao.create(userPreferences);
		assertNotNull(userPreferences.getId());
	}
	
	@SuppressWarnings("deprecation")
	public void testUserPreferencesAssociation() {
		UserPreferences preferences = UserPreferences.Factory.newInstance("de","plexus",TimeZone.getDefault().getID());
		User user = testUtility.createDefaultUser();

		assertNull(user.getId());
		assertNull(preferences.getId());
		
		assertEquals("de", preferences.getLocale());
		assertEquals("plexus", preferences.getTheme());
		
		user.setPreferences(preferences);
		assertEquals(preferences, user.getPreferences());
		
		userPreferencesDao.create(preferences);
		userDao.create(user);
		
		assertNotNull(user.getId());
		assertNotNull(preferences.getId());
		
		User testUser = userDao.load(user.getId());
		assertEquals(preferences.getId(), testUser.getPreferences().getId());
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
}