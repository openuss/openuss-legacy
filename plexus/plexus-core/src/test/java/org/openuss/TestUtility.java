package org.openuss;

import java.util.ArrayList;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteDao;
import org.openuss.security.Group;
import org.openuss.security.GroupDao;
import org.openuss.security.Roles;
import org.openuss.security.User;
import org.openuss.security.UserDao;
import org.openuss.security.UserImpl;
import org.openuss.security.UserPreferences;

/**
 * Test Utility to generate default database structures
 * 
 * @author Ingo Dueppe
 */
public class TestUtility {

	private UserDao userDao;
	
	private GroupDao groupDao;

	private InstituteDao instituteDao;

	private User defaultUser;

	private Institute defaultInstitute;
	
	private static long uniqueId = System.currentTimeMillis();

	public User createDefaultUserInDB() {
		defaultUser.setUsername(unique("username"));
		defaultUser.setGroups(new ArrayList<Group>());
		defaultUser.setPreferences(UserPreferences.Factory.newInstance());
		userDao.create(defaultUser);
		return defaultUser;
	}
	
	public User createUserInDB() {
		User user = createDefaultUser();
		userDao.create(user);
		return user;
	}

	public void removeUser() {
		removeUser(defaultUser);
	}
	
	public void updateUser(User user) {
		userDao.update(user);
	}
	
	public void removeUser(User user) {
		user = userDao.load(user.getId());
		userDao.remove(user);
	}

	public Institute createPersistInstituteWithDefaultUser() {
		defaultUser.setUsername(unique("username"));
		defaultUser.setPreferences(UserPreferences.Factory.newInstance());
		userDao.create(defaultUser);
		defaultInstitute.setName(unique("name"));
		defaultInstitute.setShortcut(unique("shortcut"));
		instituteDao.create(defaultInstitute);
		return defaultInstitute;
	}

	public void removePersistInstituteAndDefaultUser() {
		instituteDao.remove(defaultInstitute);
		userDao.remove(defaultInstitute.getOwner());
	}

	public Institute createdDefaultInstituteWithStoredUser() {
		defaultUser.setUsername(unique("username"));
		userDao.create(defaultUser);
		defaultInstitute.setName(unique("name"));
		defaultInstitute.setShortcut(unique("shortcut"));
		instituteDao.create(defaultInstitute);
		return defaultInstitute;
	}

	public User createSecureContext() {
		return createSecureContext(Roles.USER_ID);
	} 
	
	public User createAdminSecureContext() {
		return createSecureContext(Roles.ADMINISTRATOR_ID);
	}

	private User createSecureContext(Long roleId) {
		String username = unique("username");
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String password = encoder.encodePassword("password", null);

		User user = User.Factory.newInstance();
		user.setUsername(username);
		user.setPassword(password);
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setTitle("title");
		user.setEmail("email");
		user.setEnabled(true);
		Group group = groupDao.load(roleId);
		user.addGroup(group);
		group.addMember(user);
		userDao.create(user);
		groupDao.update(group);

		final UsernamePasswordAuthenticationToken authentication;
		authentication = new UsernamePasswordAuthenticationToken(user, "[Protected]", ((UserImpl) user).getAuthorities());

		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return user;
	} 
	
	
	public User createDefaultUser() {
		User user = User.Factory.newInstance();
		user.setUsername(unique("username"));
		user.setPassword("password");
		user.setEmail(unique("email"));
		user.setEnabled(true);
		user.setAccountExpired(true);
		user.setCredentialsExpired(true);
		user.setAccountLocked(true);
		return user;
	}

	public long unique() {
		synchronized (TestUtility.class) {
			return ++uniqueId;
		}
	}
	
	public String unique(String str) {
		return str + "-" + unique();
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public User getDefaultUser() {
		return defaultUser;
	}

	public void setDefaultUser(User defaultUser) {
		this.defaultUser = defaultUser;
	}

	public Institute getDefaultInstitute() {
		return defaultInstitute;
	}

	public void setDefaultInstitute(Institute defaultInstitute) {
		this.defaultInstitute = defaultInstitute;
	}

	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
}
