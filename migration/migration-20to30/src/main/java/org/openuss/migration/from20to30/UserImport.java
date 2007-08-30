package org.openuss.migration.from20to30;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.openuss.migration.legacy.dao.LegacyDao;
import org.openuss.migration.legacy.domain.Assistant2;
import org.openuss.migration.legacy.domain.Assistantinformation2;
import org.openuss.migration.legacy.domain.Student2;
import org.openuss.migration.legacy.domain.Studentinformation2;
import org.openuss.security.Group;
import org.openuss.security.GroupDao;
import org.openuss.security.Roles;
import org.openuss.security.User;
import org.openuss.security.UserContact;
import org.openuss.security.UserDao;
import org.openuss.security.UserPreferences;
import org.openuss.security.UserProfile;
import org.openuss.security.acl.ObjectIdentity;
import org.openuss.security.acl.ObjectIdentityDao;

/**
 * This Service migrate data from openuss 2.0 to openuss-plexus 3.0
 * 
 * @author Ingo Dueppe
 * 
 */
public class UserImport {

	private static final Logger logger = Logger.getLogger(UserImport.class);

	/** Maps legacy ids of students or assistants to to new user id */
	private Map<String, Long> id2UserMap = new HashMap<String, Long>(36000);
	
	/** Maps existing user emails to user objects */
	private Map<String, Long> email2UserMap = new HashMap<String, Long>(36000);
	
	/** Maps user ids to consolidated student or assistants usernames*/
	private Map<Long, String> consolidatedUsernames = new HashMap<Long, String>();

	/** Maps existing usernames of objects */
	private Set<String> usernames = new HashSet<String>(36000);

	/** List of users to be imported */
	private List<User> importedUsers = new ArrayList<User>(36000);
	
	/** List of user ids */
	private List<Long> newUserIds = new ArrayList<Long>(36000);
	
	/** List of student or assistants that are invalid */
	private List<Object> invalidEmails = new ArrayList<Object>();

	/** Inactive users*/
	private List<Object> inactives = new ArrayList<Object>();
	
	/** List of renamed users */
	private List<Long> renamedUsers = new ArrayList();

	/** Referenz to legacyDao */
	private LegacyDao legacyDao;

	/** UserDao */
	private UserDao userDao;
	
	/** GroupDao */
	private GroupDao groupDao;
	
	/** ObjectIdentityDao */
	private ObjectIdentityDao objectIdentityDao;
	
	public void importUsers() {
		initializeUsers();
		loadStudents();
		loadAssistants();

		logger.debug("found users               " + importedUsers.size());
		logger.debug("found renamed users       " + renamedUsers.size());
		logger.debug("found consolidated users  " + consolidatedUsernames.size());
		logger.debug("found invalid users       " + invalidEmails.size());
		logger.debug("found inactive users      " + inactives.size());

		saveUserObjectIdentites();
		saveUserRoles();
		
		logger.info("cleaning data.");
		importedUsers.clear();
		
		logger.info("finish user import.");
		
	}

	private void saveUserRoles() {
		logger.debug("adding new users to user group");
		Group roleUser = groupDao.load(Roles.USER_ID);
		for (User user : importedUsers) {
			roleUser.addMember(user);
			user.addGroup(roleUser);
		}
		getGroupDao().update(roleUser);
	}
	
	private void initializeUsers() {
		Collection<User> existingUsers = userDao.loadAll();
		for (User user : existingUsers) {
			email2UserMap.put(user.getEmail().toLowerCase(), user.getId());
			usernames.add(user.getUsername().toLowerCase());
		}
	}

	private void loadStudents() {
		logger.info("loading students...");
		Collection<Student2> students2 = legacyDao.loadAllStudents();
		logger.info("found " + students2.size() + " students.");

		int count = 0;
		for (Student2 student2 : students2) {
			if (!ImportUtil.toBoolean(student2.getAactive())) {
				logger.debug("student not actived " + student2.getUusername() + " - " + student2.getEmailaddress());
				inactives.add(student2);
				continue;
			}
			String email = student2.getEmailaddress().toLowerCase();
			if (!GenericValidator.isEmail(email)) {
				invalidEmails.add(student2);
			} else if (email2UserMap.containsKey(email)) {
				logger.trace("email already in use " + email);
				Long userId = email2UserMap.get(email);
				id2UserMap.put(student2.getId(), userId);
				consolidatedUsernames.put(userId, student2.getUusername());
			} else {
				storeUser(email, transformStudent2User(student2), student2.getId());
			}
			process(++count);
		}
	}


	private void loadAssistants() {
		logger.info("loading assistants...");
		Collection<Assistant2> assistants2 = legacyDao.loadAllAssistants();
		logger.info("found " + assistants2.size() + " assistants.");

		int count = 0;
		for (Assistant2 assistant : assistants2) {
			if (!ImportUtil.toBoolean(assistant.getAactive())) {
				logger.debug("assistant not actived " + assistant.getUusername() + " - " + assistant.getEmailaddress());
				inactives.add(assistant);
				continue;
			}
			String email = assistant.getEmailaddress().toLowerCase();
			if (!GenericValidator.isEmail(email)) {
				invalidEmails.add(assistant);
			} else if (email2UserMap.containsKey(email)) {
				logger.debug("email already in use " + email);
				Long userId = email2UserMap.get(email);
				id2UserMap.put(assistant.getId(), userId);
				consolidatedUsernames.put(userId, assistant.getUusername());
			} else {
				storeUser(email, transformAssistant2User(assistant), assistant.getId());
			}
			process(++count);
		}
	}


	private void storeUser(String email, User user, String id) {
		checkUserName(user);
		userDao.create(user);

		usernames.add(user.getUsername().toLowerCase().trim());
		importedUsers.add(user);
		newUserIds.add(user.getId());
		email2UserMap.put(email, user.getId());
		id2UserMap.put(id, user.getId());
	}

	private void checkUserName(User user) {
		if (existingUserName(user)) {
			String oldName = user.getUsername();
			user.setUsername(user.getEmail());
			while (existingUserName(user)) {
				logger.error("found conflict...");
				user.setUsername(oldName + "-" + System.currentTimeMillis());
			}
			renamedUsers.add(user.getId());
			logger.debug("rename user " + user.getDisplayName() + "(" + user.getUsername() + ")");
		}
	}
	private User transformAssistant2User(Assistant2 assistant) {
		User user = User.Factory.newInstance();
		user.setUsername(assistant.getUusername());
		user.setPassword(assistant.getPpassword());
		user.setEmail(assistant.getEmailaddress());
		user.setEnabled(false);
		
		user.setContact(assistantToUserContact(assistant));
		user.setPreferences(assistantToUserPreferences(assistant));
		user.setProfile(assistantToUserProfile(assistant));
		return user;
	}

	/**
	 * Map assistant to UserProfile
	 * 
	 * @param assistant
	 * @return
	 */
	private UserProfile assistantToUserProfile(Assistant2 assistant) {
		UserProfile profile = UserProfile.Factory.newInstance();

		if (!assistant.getAssistantinformations().isEmpty()) {
			Assistantinformation2 info = assistant.getAssistantinformations().iterator().next();
			profile.setEmailPublic(ImportUtil.toBoolean(info.getEmail()));
			profile.setAddressPublic(ImportUtil.toBoolean(info.getAddress()));
			profile.setImagePublic(ImportUtil.toBoolean(info.getImage()));
			profile.setPortraitPublic(ImportUtil.toBoolean(info.getDescription()));
			profile.setProfilePublic(ImportUtil.toBoolean(info.getIspublic()));
			profile.setTelephonePublic(ImportUtil.toBoolean(info.getTelephone()));

			profile.setPortrait(info.getTtext());
		}

		return profile;
	}

	/**
	 * Map assistant to user preferences
	 * 
	 * @param assistant
	 * @return UserPreferences
	 */
	private UserPreferences assistantToUserPreferences(Assistant2 assistant) {
		UserPreferences preferences = UserPreferences.Factory.newInstance();
		preferences.setLocale(assistant.getLocale());
		preferences.setTheme("plexus");
		preferences.setTimezone("GMT+2");
		return preferences;
	}

	/**
	 * Map assistant to user contact
	 * 
	 * @param assistant
	 * @return UserContact
	 */
	private UserContact assistantToUserContact(Assistant2 assistant) {
		UserContact contact = UserContact.Factory.newInstance();
		contact.setFirstName(assistant.getFirstname());
		contact.setLastName(assistant.getLastname());
		contact.setTitle(assistant.getTitle());
		contact.setProfession(assistant.getFfunction());
		contact.setAddress(assistant.getAddress());
		contact.setPostcode(assistant.getPostcode());
		contact.setCity(assistant.getCity());
		contact.setCountry(assistant.getLand());
		contact.setTelephone(assistant.getTelephone());
		contact.setSmsEmail(assistant.getEmailsmsgatewayaddress());
		return contact;
	}

	private boolean existingUserName(User user) {
		return usernames.contains(user.getUsername().toLowerCase().trim());
	}

	private void saveUserObjectIdentites() {
		logger.debug("setting user object identity");
		Collection<ObjectIdentity> objIds = new ArrayList<ObjectIdentity>();
		for (User user : importedUsers) {
			objIds.add(ImportUtil.createObjectIdentity(user.getId(), null));
		}
		objectIdentityDao.create(objIds);
	}

	@SuppressWarnings( { "deprecation" })
	private User transformStudent2User(Student2 student) {
		logger.trace("create " + student.getLastname() + "(" + student.getEmailaddress() + ")");
		Studentinformation2 info = null;
		if (!student.getStudentinformations().isEmpty()) {
			info = student.getStudentinformations().iterator().next();
		}

		// User
		User user = User.Factory.newInstance();
		user.setUsername(student.getUusername().toLowerCase());
		user.setPassword(student.getPpassword());
		user.setEmail(student.getEmailaddress().toLowerCase());
		user.setEnabled(false);

		// User Contact
		user.setContact(UserContact.Factory.newInstance());
		user.getContact().setFirstName(student.getFirstname());
		user.getContact().setLastName(student.getLastname());
		user.getContact().setAddress(student.getAddress());
		user.getContact().setCity(student.getCity());
		user.getContact().setPostcode(student.getPostcode());
		user.getContact().setCountry(student.getLand());
		user.getContact().setSmsEmail(student.getEmailsmsgatewayaddress());
		user.getContact().setTelephone(student.getTelephone());

		// User Preferences
		user.getPreferences().setLocale(student.getLocale());

		user.setProfile(UserProfile.Factory.newInstance());
		// User Profile
		user.getProfile().setAgeGroup(student.getYyear());
		user.getProfile().setMatriculation(student.getPersonalid());
		user.getProfile().setStudies(student.getStudies());
		if (info != null) {
			user.getProfile().setPortrait(info.getTtext());
		}

		if (info != null) {
			user.getProfile().setAddressPublic(ImportUtil.toBoolean(info.getAddress()));
			user.getProfile().setTelephonePublic(ImportUtil.toBoolean(info.getTelephone()));
			user.getProfile().setImagePublic(ImportUtil.toBoolean(info.getImage()));
			user.getProfile().setEmailPublic(ImportUtil.toBoolean(info.getEmail()));
			user.getProfile().setPortraitPublic(ImportUtil.toBoolean(info.getDescription()));
			user.getProfile().setProfilePublic(ImportUtil.toBoolean(info.getIspublic()));
		}

		return user;
	}

	private void process(int count) {
		if (count % 500 == 0) {
			logger.trace("processed " + count + " ...");
		}
	}

	public LegacyDao getLegacyDao() {
		return legacyDao;
	}

	public void setLegacyDao(LegacyDao legacyDao) {
		this.legacyDao = legacyDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public User loadUserByLegacyId(String id) {
		if (id2UserMap.containsKey(id)) {
			return loadUser(id2UserMap.get(id));
		} else {
			return null;
		}
	}
	
	public User loadUser(Long id) {
		if (id != null) {
			return userDao.load(id);
		} else {
			return null;
		}
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public ObjectIdentityDao getObjectIdentityDao() {
		return objectIdentityDao;
	}

	public void setObjectIdentityDao(ObjectIdentityDao objectIdentityDao) {
		this.objectIdentityDao = objectIdentityDao;
	}

	public List<Long> getNewUserIds() {
		return newUserIds;
	}

}
