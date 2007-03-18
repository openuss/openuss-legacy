package org.openuss;

import java.util.ArrayList;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.FacultyDao;
import org.openuss.security.Group;
import org.openuss.security.GroupDao;
import org.openuss.security.Roles;
import org.openuss.security.User;
import org.openuss.security.UserDao;

/**
 * Test Utility to generate default database structures
 * 
 * @author Ingo Dueppe
 */
public class TestUtility {

	private UserDao userDao;
	
	private GroupDao groupDao;

	private FacultyDao facultyDao;

	private User defaultUser;

	private Faculty defaultFaculty;
	
	private static long uniqueId = System.currentTimeMillis();

	public User createDefaultUserInDB() {
		defaultUser.setUsername(unique("username"));
		defaultUser.setGroups(new ArrayList());
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
	
	public void removeUser(User user) {
		userDao.remove(user);
	}

	public Faculty createPersistFacultyWithDefaultUser() {
		defaultUser.setUsername(unique("username"));
		userDao.create(defaultUser);
		defaultFaculty.setName(unique("name"));
		defaultFaculty.setShortcut(unique("shortcut"));
		facultyDao.create(defaultFaculty);
		return defaultFaculty;
	}

	public void removePersistFacultyAndDefaultUser() {
		facultyDao.remove(defaultFaculty);
		userDao.remove(defaultFaculty.getOwner());
	}

	public Faculty createdDefaultFacultyWithStoredUser() {
		defaultUser.setUsername(unique("username"));
		userDao.create(defaultUser);
		defaultFaculty.setName(unique("name"));
		defaultFaculty.setShortcut(unique("shortcut"));
		facultyDao.create(defaultFaculty);
		return defaultFaculty;
	}

	public User createSecureContext() {
		String username = unique("username");
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String password = encoder.encodePassword("password", null);

		User user = User.Factory.newInstance();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail("email");
		user.setEnabled(true);
		Group group = groupDao.load(Roles.ADMINISTRATOR);
		user.addGroup(group);
		group.addMember(user);
		userDao.create(user);
		groupDao.update(group);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
				"password");
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

	public Faculty getDefaultFaculty() {
		return defaultFaculty;
	}

	public void setDefaultFaculty(Faculty defaultFaculty) {
		this.defaultFaculty = defaultFaculty;
	}

	public FacultyDao getFacultyDao() {
		return facultyDao;
	}

	public void setFacultyDao(FacultyDao facultyDao) {
		this.facultyDao = facultyDao;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
}
