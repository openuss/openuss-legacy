// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.registration;

import org.openuss.desktop.DesktopDao;
import org.openuss.security.Group;
import org.openuss.security.GroupDao;
import org.openuss.security.User;
import org.openuss.security.UserContactDao;
import org.openuss.security.UserContactInfo;
import org.openuss.security.UserDao;
import org.openuss.security.UserInfo;
import org.openuss.security.UserInfoDetails;
import org.openuss.security.UserPreferencesDao;
import org.openuss.security.UserPreferencesInfo;

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
	private ActivationCodeDao activationCodeDao;
	
	public void testInjection() {
		assertNotNull(userDao);
		assertNotNull(userContactDao);
		assertNotNull(userPreferencesDao);
		assertNotNull(groupDao);
		assertNotNull(desktopDao);
	}
	
	public UserInfo createUserInfo() {
		UserInfo userInfo = new UserInfoDetails();
		userInfo.setPassword("masterkey");
		userInfo.setEmail(testUtility.unique("openuss@openuss.org"));
		userInfo.setUsername(testUtility.unique("username"));
		
		UserPreferencesInfo preferences = new UserPreferencesInfo();
		preferences.setLocale("xyz");
		preferences.setTimezone("Europa/Berlin+1");
		userInfo.setPreferences(preferences);
		
		UserContactInfo contact = new UserContactInfo();
		contact.setAddress("Address");
		contact.setPostcode("postcode");
		contact.setCity("city");
		contact.setFirstName("firstName");
		contact.setLastName("lastName");
		userInfo.setContact(contact);
		
		return userInfo;
	}
	
	public void testUserRegistration() throws RegistrationException {
		UserInfo userInfo = createUserInfo();
		registrationService.registrateUser(userInfo);
		
		assertNotNull(userInfo.getId());
		assertEquals(false, userInfo.isEnabled());
		
		setComplete();
		endTransaction();
		startNewTransaction();
		
		String code = registrationService.generateActivationCode(userInfo);
		
		// activate user
		registrationService.activateUserByCode(code);
		
		user = getUserDao().load(userInfo.getId());
		assertEquals(true, user.isEnabled());
		
		// try to activate again
		try {
			registrationService.activateUserByCode(code);
			//success
		} catch (RegistrationCodeNotFoundException ex) {
			fail("RegistrationCodeNotFoundException expected!");
		}
		
		//clean up
		activationCodeDao.remove(activationCodeDao.loadAll());
		user = userDao.load(user.getId());
		for (Group group: user.getGroups()) {
			group = groupDao.load(group.getId());
			group.removeMember(user);
			groupDao.update(group);
		}

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
	
	public void setUserContactDao(UserContactDao userContactDao) {
		this.userContactDao = userContactDao;
	}

	public DesktopDao getDesktopDao() {
		return desktopDao;
	}

	public void setDesktopDao(DesktopDao desktopDao) {
		this.desktopDao = desktopDao;
	}

	public ActivationCodeDao getActivationCodeDao() {
		return activationCodeDao;
	}

	public void setActivationCodeDao(ActivationCodeDao activationCodeDao) {
		this.activationCodeDao = activationCodeDao;
	}
}