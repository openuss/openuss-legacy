package org.openuss;

import java.util.ArrayList;
import java.util.TimeZone;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteDao;
import org.openuss.lecture.University;
import org.openuss.lecture.UniversityDao;
import org.openuss.security.Group;
import org.openuss.security.GroupDao;
import org.openuss.security.GroupType;
import org.openuss.security.Membership;
import org.openuss.security.MembershipDao;
import org.openuss.security.Roles;
import org.openuss.security.User;
import org.openuss.security.UserContact;
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
	
	private MembershipDao membershipDao;

	private InstituteDao instituteDao;
	
	private UniversityDao universityDao;

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
	
	public University createDefaultUniversityWithDefaultUser() {
		// Create a unique User
		UserPreferences userPreferences = UserPreferences.Factory.newInstance();
		userPreferences.setLocale("de");
		userPreferences.setTheme("plexus");
		userPreferences.setTimezone(TimeZone.getDefault().getID());
		
		UserContact userContact = UserContact.Factory.newInstance();
		userContact.setFirstName("Unique");
		userContact.setLastName("User");
		userContact.setAddress("Leonardo Campus 5");
		userContact.setCity("Münster");
		userContact.setCountry("Germany");
		userContact.setPostcode("48149");
		
		User user = User.Factory.newInstance();
		user.setUsername(unique("username"));
		user.setPassword("masterkey");
		user.setEmail(unique("openuss")+"@e-learning.uni-muenster.de");
		user.setEnabled(true);
		user.setAccountExpired(false);
		user.setCredentialsExpired(false);
		user.setAccountLocked(false);
		
		user.setPreferences(userPreferences);
		user.setContact(userContact);
		user.setGroups(new ArrayList<Group>());
		
		userDao.create(user);
		
		// Create a unique Membership
		Membership membership = Membership.Factory.newInstance();
		membership.getMembers().add(user);
		Group group = Group.Factory.newInstance(unique("Group"), GroupType.ADMINISTRATOR);
		group.addMember(user);
		membership.getGroups().add(group);
		
		membershipDao.create(membership);
		
		// Create a unique University
		University university = University.Factory.newInstance();
		university.setName(unique("University"));
		university.setShortcut(unique("Uni"));
		university.setDescription("A unique University");
		university.setMembership(membership);
		
		universityDao.create(university);
		
		return university;
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
	
	public MembershipDao getMembershipDao() {
		return membershipDao;
	}

	public void setMembershipDao(MembershipDao membershipDao) {
		this.membershipDao = membershipDao;
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

	public UniversityDao getUniversityDao() {
		return universityDao;
	}

	public void setUniversityDao(UniversityDao universityDao) {
		this.universityDao = universityDao;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
}
