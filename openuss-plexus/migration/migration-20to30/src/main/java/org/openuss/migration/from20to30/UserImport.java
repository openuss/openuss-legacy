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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.openuss.migration.legacy.domain.Assistant2;
import org.openuss.migration.legacy.domain.Assistantinformation2;
import org.openuss.migration.legacy.domain.Student2;
import org.openuss.migration.legacy.domain.Studentinformation2;
import org.openuss.security.Group;
import org.openuss.security.GroupDao;
import org.openuss.security.Roles;
import org.openuss.security.User;
import org.openuss.security.UserDao;
import org.openuss.security.acl.ObjectIdentityDao;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * This Service migrate data from openuss 2.0 to openuss-plexus 3.0
 * 
 * @author Ingo Dueppe
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

	/** Number of Users to be imported */
	private List<User> importedUsers = new ArrayList<User>(33000);
	
	/** Number of student or assistants that are invalid */
	private int invalidEmails = 0;

	/** UserDao */
	private UserDao userDao;
	
	/** GroupDao */
	private GroupDao groupDao;
	
	/** ObjectIdentityDao */
	private ObjectIdentityDao objectIdentityDao;
	
	private SessionFactory plexusSessionFactory;

	private int count;
	
	public void perform() {
		initializeUsers();
		loadStudents();
		loadAssistants();

		logger.debug("found users               " + importedUsers);
		logger.debug("found consolidated users  " + consolidatedUsernames.size());
		logger.debug("found invalid users       " + invalidEmails);

		// saveUserObjectIdentites();
		saveUserRoles();
		
		logger.info("cleaning data.");
		importedUsers.clear();
		logger.info("finish user import.");
	}

	private void saveUserRoles() {
		Session session = plexusSessionFactory.openSession();
		
		TransactionSynchronizationManager.bindResource(plexusSessionFactory, new SessionHolder(session));
		Transaction tx = plexusSessionFactory.getCurrentSession().beginTransaction();

		
		Group roleUser = groupDao.load(Roles.USER_ID);
		for (User user : importedUsers) {
			roleUser.addMember(user);
			user.addGroup(roleUser);
			objectIdentityDao.create(ImportUtil.createObjectIdentity(user.getId(), null));
		}
		groupDao.update(roleUser);
		
		tx.commit();
		session.close();
		TransactionSynchronizationManager.unbindResource(plexusSessionFactory);
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
		
		count = 0;
		
		Student2 student = null;
		while (results.next()) {
			// remove last student from session
			if (student != null) {
				evict(student);
			}
			// retrieve next student
			student = (Student2) results.get()[0];
			if (!ImportUtil.toBoolean(student.getAactive())) {
				logger.debug("student not actived " + student.getUusername() + " - " + student.getEmailaddress());
				continue;
			}
			String email = student.getEmailaddress().toLowerCase();
			if (!GenericValidator.isEmail(email)) {
				invalidEmails++;
				identifierDao.log("invalid email address "+email+" of student "+student.getId());
			} else if (email2UserMap.containsKey(email)) {
				logger.trace("email already in use " + email);
				Long userId = email2UserMap.get(email);
				id2UserMap.put(student.getId(), userId);
				identifierDao.insertUserId(student.getId(), userId);
				consolidatedUsernames.put(userId, student.getUusername());
				identifierDao.log("consolidate student "+student.getUusername()+"("+student.getId()+")");
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
		count = 0;
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
				invalidEmails++;
				identifierDao.log("invalid email address "+email+" of assistant "+assistant.getId());
			} else if (email2UserMap.containsKey(email)) {
				logger.debug("email already in use " + email);
				Long userId = email2UserMap.get(email);
				identifierDao.insertUserId(assistant.getId(), userId);
				identifierDao.insertConsolidated(userId, assistant.getUusername());
				id2UserMap.put(assistant.getId(), userId);
				consolidatedUsernames.put(userId, assistant.getUusername());
				identifierDao.log("consolidate assistant "+assistant.getUusername()+"("+assistant.getId()+")");
			} else {
				storeUser(email, transformAssistant2User(assistant), assistant.getId());
			}
			count++;
			process(count);
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
		
		user.setFirstName(assistant.getFirstname());
		user.setLastName(assistant.getLastname());
		user.setTitle(assistant.getTitle());
		user.setProfession(assistant.getFfunction());
		user.setAddress(assistant.getAddress());
		user.setPostcode(assistant.getPostcode());
		user.setCity(assistant.getCity());
		user.setCountry(assistant.getLand());
		user.setTelephone(assistant.getTelephone());
		user.setSmsEmail(assistant.getEmailsmsgatewayaddress());


		user.setLocale(assistant.getLocale());
		user.setTheme("plexus");
		user.setTimezone("GMT+2");
		
		if (!assistant.getAssistantinformations().isEmpty()) {
			Assistantinformation2 info = assistant.getAssistantinformations().iterator().next();
			user.setEmailPublic(ImportUtil.toBoolean(info.getEmail()));
			user.setAddressPublic(ImportUtil.toBoolean(info.getAddress()));
			user.setImagePublic(ImportUtil.toBoolean(info.getImage()));
			user.setPortraitPublic(ImportUtil.toBoolean(info.getDescription()));
			user.setProfilePublic(ImportUtil.toBoolean(info.getIspublic()));
			user.setTelephonePublic(ImportUtil.toBoolean(info.getTelephone()));

			user.setPortrait(info.getTtext());
		}
		
		return user;
	}

	private boolean existingUserName(User user) {
		return usernames.contains(user.getUsername().toLowerCase().trim());
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
		user.setFirstName(student.getFirstname());
		user.setLastName(student.getLastname());
		user.setAddress(student.getAddress());
		user.setCity(student.getCity());
		user.setPostcode(student.getPostcode());
		user.setCountry(student.getLand());
		user.setSmsEmail(student.getEmailsmsgatewayaddress());
		user.setTelephone(student.getTelephone());

		// User Preferences
		user.setLocale(student.getLocale());

		// User Profile
		user.setAgeGroup(student.getYyear());
		user.setMatriculation(student.getPersonalid());
		user.setStudies(student.getStudies());
		if (info != null) {
			user.setPortrait(info.getTtext());
		}

		if (info != null) {
			user.setAddressPublic(ImportUtil.toBoolean(info.getAddress()));
			user.setTelephonePublic(ImportUtil.toBoolean(info.getTelephone()));
			user.setImagePublic(ImportUtil.toBoolean(info.getImage()));
			user.setEmailPublic(ImportUtil.toBoolean(info.getEmail()));
			user.setPortraitPublic(ImportUtil.toBoolean(info.getDescription()));
			user.setProfilePublic(ImportUtil.toBoolean(info.getIspublic()));
		}

		return user;
	}

	private void process(int count) {
		if (count % 500 == 0) {
			logger.info("processed " + count + " ...");
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

	public void setPlexusSessionFactory(SessionFactory plexusSessionFactory) {
		this.plexusSessionFactory = plexusSessionFactory;
	}
}
