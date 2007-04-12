// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.registration;

import java.util.TimeZone;

import org.openuss.TestUtility;
import org.openuss.desktop.DesktopDao;
import org.openuss.registration.RegistrationCodeNotFoundException;
import org.openuss.registration.RegistrationException;
import org.openuss.registration.RegistrationServiceIntegrationTestBase;
import org.openuss.security.Group;
import org.openuss.security.GroupDao;
import org.openuss.security.User;
import org.openuss.security.UserContact;
import org.openuss.security.UserContactDao;
import org.openuss.security.UserDao;
import org.openuss.security.UserPreferences;
import org.openuss.security.UserPreferencesDao;

/**
 * JUnit Test for Spring Hibernate RegistrationService class.
 * @see org.openuss.registration.RegistrationService
 */
public class RegistrationServiceIntegrationTest extends RegistrationServiceIntegrationTestBase {
	
	private DesktopDao desktopDao;
	private UserDao userDao;
	private UserPreferencesDao userPreferencesDao;
	private UserContactDao userContactDao;
	private GroupDao groupDao;
	private User user;
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
		user = testUtility.createDefaultUser();
		// create preferences
		UserPreferences preferences = UserPreferences.Factory.newInstance();
		preferences.setLocale("de");
		preferences.setTheme("plexus");
		preferences.setTimezone(TimeZone.getDefault().getID());
		user.setPreferences(preferences);
		// create person
		UserContact contact = UserContact.Factory.newInstance();
		contact.setFirstName("firstName");
		contact.setLastName("lastName");
		contact.setAddress("address");
		user.setContact(contact);
	}

	public void testInjection() {
		assertNotNull(userDao);
		assertNotNull(userContactDao);
		assertNotNull(userPreferencesDao);
		assertNotNull(groupDao);
		assertNotNull(desktopDao);
	}
	
	public void testUserRegistration() throws RegistrationException {
		registrationService.registrateUser(user, true);
		
		assertNotNull(user.getId());
		assertNotNull(user.getContact());
		assertNotNull(user.getContact().getId());
		assertNotNull(user.getPreferences());
		assertNotNull(user.getPreferences().getId());
		assertEquals(false, user.isEnabled());
		
		setComplete();
		endTransaction();
		startNewTransaction();
		
		String code = registrationService.generateActivationCode(user);
		
		// activate user
		registrationService.activateUserByCode(code);
		
		user = getUserDao().load(user.getId());
		assertEquals(true, user.isEnabled());
		
		// try to activate again
		try {
			registrationService.activateUserByCode(code);
			fail("RegistrationCodeNotFoundException expected!");
		} catch (RegistrationCodeNotFoundException ex) {
			 //success
		}
		
		//clean up
		user = userDao.load(user.getId());
		for (Group group: user.getGroups()) {
			group = groupDao.load(group.getId());
			group.removeMember(user);
			groupDao.update(group);
		}

		
//		Desktop will be on demand at the first request
//		Desktop desktop = desktopDao.findByUser(user);
//		desktopDao.remove(desktop);
		
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

	public UserPreferencesDao getUserPreferencesDao() {
		return userPreferencesDao;
	}

	public void setUserPreferencesDao(UserPreferencesDao userPreferencesDao) {
		this.userPreferencesDao = userPreferencesDao;
	}

	public UserContactDao getUserContactDao() {
		return userContactDao;
	}

	public void setPersonDao(UserContactDao personDao) {
		this.userContactDao = personDao;
	}

	public GroupDao getRoleDao() {
		return groupDao;
	}

	public void setRoleDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	@Override
	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext.xml",
			"classpath*:applicationContext-localDataSource.xml",
			"classpath*:applicationContext-beans.xml",
			"classpath*:applicationContext-service.xml",
			"classpath*:applicationContext-entities.xml",
			"classpath*:applicationContext-cache.xml",
			"classpath*:applicationContext-tests.xml",
			"classpath*:beanRefFactory",
			"classpath*:testSecurity.xml",
			"classpath*:testDataSource.xml"};
	}

	public void setUserContactDao(UserContactDao userContactDao) {
		this.userContactDao = userContactDao;
	}

	public DesktopDao getDesktopDao() {
		return desktopDao;
	}

	public void setDesktopDao(DesktopDao desktopDao) {
		this.desktopDao = desktopDao;
	}
}