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
import org.openuss.security.UserDao;
import org.openuss.security.UserInfo;

/**
 * JUnit Test for Spring Hibernate RegistrationService class.
 * @see org.openuss.registration.RegistrationService
 */
public class RegistrationServiceIntegrationTest extends RegistrationServiceIntegrationTestBase {
	
	private DesktopDao desktopDao;
	private UserDao userDao;
	private GroupDao groupDao;
	private User user;
	private ActivationCodeDao activationCodeDao;
	
	public void testInjection() {
		assertNotNull(userDao);
		assertNotNull(groupDao);
		assertNotNull(desktopDao);
	}
	
	public UserInfo createUserInfo() {
		UserInfo userInfo = new UserInfo();
		userInfo.setPassword("masterkey");
		userInfo.setEmail(testUtility.unique("openuss@openuss.org"));
		userInfo.setUsername(testUtility.unique("username"));
		
		userInfo.setLocale("xyz");
		userInfo.setTimezone("Europa/Berlin+1");
		
		userInfo.setAddress("Address");
		userInfo.setPostcode("postcode");
		userInfo.setCity("city");
		userInfo.setFirstName("firstName");
		userInfo.setLastName("lastName");
		
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
	
	public void testRemoveUserRegistrationCodes() throws RegistrationException{
		//FIXME not working test
//		UserInfo userInfo = createUserInfo();
//		registrationService.registrateUser(userInfo);
//		
//		assertNotNull(userInfo.getId());
//		assertEquals(false, userInfo.isEnabled());
//		
//		setComplete();
//		endTransaction();
//		startNewTransaction();
//		
//		String code = registrationService.generateActivationCode(userInfo);
//		
//		flush();
//		
//		registrationService.removeUserRegistrationCodes(user);
//		
//		flush();
//		
//		int test = 0;
//		try{
//			registrationService.activateUserByCode(code);
//		} catch (RegistrationCodeNotFoundException rcnfe){
//			test = 1;
//		}
//		assertEquals(0, test);
//		
//		
//		//clean up
//		activationCodeDao.remove(activationCodeDao.loadAll());
//		user = userDao.load(user.getId());
//		for (Group group: user.getGroups()) {
//			group = groupDao.load(group.getId());
//			group.removeMember(user);
//			groupDao.update(group);
//		}
//
//		userDao.remove(user);
//		
//		setComplete();
//		endTransaction();
	}
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public GroupDao getRoleDao() {
		return groupDao;
	}

	public void setRoleDao(GroupDao groupDao) {
		this.groupDao = groupDao;
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