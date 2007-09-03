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
import org.hibernate.ScrollableResults;
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
public class UserImport extends DefaultImport {

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
	
	/** List of student or assistants that are invalid */
	private List<Object> invalidEmails = new ArrayList<Object>();

	/** UserDao */
	private UserDao userDao;
	
	/** GroupDao */
	private GroupDao groupDao;
	
	/** ObjectIdentityDao */
	private ObjectIdentityDao objectIdentityDao;
	
	public void perform() {
		initializeUsers();
		loadStudents();
		loadAssistants();

		logger.debug("found users               " + importedUsers.size());
		logger.debug("found consolidated users  " + consolidatedUsernames.size());
		logger.debug("found invalid users       " + invalidEmails.size());

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
		groupDao.update(roleUser);
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
		ScrollableResults results = legacyDao.loadAllStudents();
		
		int count = 0;
		
		Student2 student = null;
		while (results.next()) {
			// remove last student from session
			evict(student); 
			// retrieve next student
			student = (Student2) results.get()[0];
			if (!ImportUtil.toBoolean(student.getAactive())) {
				logger.debug("student not actived " + student.getUusername() + " - " + student.getEmailaddress());
				continue;
			}
			String email = student.getEmailaddress().toLowerCase();
			if (!GenericValidator.isEmail(email)) {
				invalidEmails.add(student);
			} else if (email2UserMap.containsKey(email)) {
				logger.trace("email already in use " + email);
				Long userId = email2UserMap.get(email);
				id2UserMap.put(student.getId(), userId);
				identifierDao.insertUserId(student.getId(), userId);
				consolidatedUsernames.put(userId, student.getUusername());
			} else {
				storeUser(email, transformStudent2User(student), student.getId());
			}
			process(++count);
		}
		results.close();
	}


	private void loadAssistants() {
		logger.info("loading assistants...");
		ScrollableResults results = legacyDao.loadAllAssistants();
		Assistant2 assistant = null;
		int count = 0;
		while(results.next()) {
			// remove last assistant from session
			evict(assistant);
			// retrieve next assistant;
			assistant = (Assistant2) results.get()[0];
			if (!ImportUtil.toBoolean(assistant.getAactive())) {
				logger.debug("assistant not actived " + assistant.getUusername() + " - " + assistant.getEmailaddress());
				continue;
			}
			String email = assistant.getEmailaddress().toLowerCase();
			if (!GenericValidator.isEmail(email)) {
				invalidEmails.add(assistant);
			} else if (email2UserMap.containsKey(email)) {
				logger.debug("email already in use " + email);
				Long userId = email2UserMap.get(email);
				identifierDao.insertUserId(assistant.getId(), userId);
				identifierDao.insertConsolidated(userId, assistant.getUusername());
				id2UserMap.put(assistant.getId(), userId);
				consolidatedUsernames.put(userId, assistant.getUusername());
			} else {
				storeUser(email, transformAssistant2User(assistant), assistant.getId());
			}
			process(++count);
		}
		results.close();
	}


	private void storeUser(String email, User user, String legacyId) {
		checkUserName(user, legacyId);
		userDao.create(user);
		
		identifierDao.insertUserId(legacyId, user.getId());
		
		usernames.add(user.getUsername().toLowerCase().trim());
		importedUsers.add(user);
		email2UserMap.put(email, user.getId());
		id2UserMap.put(legacyId, user.getId());
	}

	private void checkUserName(User user, String legacyId) {
		if (existingUserName(user)) {
			String oldName = user.getUsername();
			user.setUsername(user.getEmail());
			while (existingUserName(user)) {
				logger.error("found conflict...");
				user.setUsername(oldName + "-" + System.currentTimeMillis());
			}
			identifierDao.insertRenamed(legacyId, oldName);
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

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public User loadUserByLegacyId(String legacyId) {
		Long id = identifierDao.getUserId(legacyId);
		if (id != null) {
			return loadUser(id);
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
	
	public List<Long> loadAllNewUserIds() {
		return identifierDao.loadAllNewUserIds();
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public void setObjectIdentityDao(ObjectIdentityDao objectIdentityDao) {
		this.objectIdentityDao = objectIdentityDao;
	}

}
