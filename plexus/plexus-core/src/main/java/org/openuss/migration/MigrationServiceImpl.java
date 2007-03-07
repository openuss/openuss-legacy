// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.migration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openuss.desktop.Desktop;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.Period;
import org.openuss.lecture.Subject;
import org.openuss.migration.legacy.dao.LegacyDao;
import org.openuss.migration.legacy.domain.Assistant2;
import org.openuss.migration.legacy.domain.Assistantenrollment2;
import org.openuss.migration.legacy.domain.Assistantfaculty2;
import org.openuss.migration.legacy.domain.Assistantinformation2;
import org.openuss.migration.legacy.domain.Enrollment2;
import org.openuss.migration.legacy.domain.Faculty2;
import org.openuss.migration.legacy.domain.Semester2;
import org.openuss.migration.legacy.domain.Subject2;
import org.openuss.security.Group;
import org.openuss.security.GroupType;
import org.openuss.security.Roles;
import org.openuss.security.User;
import org.openuss.security.UserContact;
import org.openuss.security.UserPreferences;
import org.openuss.security.UserProfile;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.security.acl.ObjectIdentity;
import org.openuss.security.acl.Permission;

/**
 * @see org.openuss.migration.MigrationService
 */
public class MigrationServiceImpl extends org.openuss.migration.MigrationServiceBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MigrationServiceImpl.class);
	
	private Map<String, User> users = new HashMap<String, User>();
	private Map<String, Desktop> desktops = new HashMap<String, Desktop>();
	private Map<String, Faculty> faculties = new HashMap<String, Faculty>();
	private Map<String, Enrollment> enrollments = new HashMap<String, Enrollment>();
	private Map<String, Subject> subjects = new HashMap<String, Subject>();
	private Map<Faculty, ObjectIdentity> facultyObjIds = new HashMap<Faculty, ObjectIdentity>();
	
	/**
	 * @see org.openuss.migration.MigrationService#performMigration()
	 */
	protected synchronized void handlePerformMigration() throws java.lang.Exception {
		final LegacyDao legacyDao = getLegacyDao();
		
		logger.debug("loading legacy data");
		Collection<Assistant2> assistants2 = legacyDao.loadAllAssistants();
		logger.debug("load "+assistants2.size()+" legacy assistants entries.");
		Collection<Faculty2> faculties2 = legacyDao.loadAllFaculties();
		logger.debug("load "+faculties2.size()+" legacy faculties entries.");
		
		logger.debug("migrating legacy data");
		migrateAssistantsToUsers(assistants2);
		createDesktopsForUsers();
		migrateFacultiesToFaculties(faculties2);
		createGroupsAndPermissionsForFaculties(faculties2);
		createGroupsGrantPermissionsOfFaculties();
		createDesktopLinksForAssistants(assistants2);
	}

	/**
	 *  The faculty groups must inherit the grant priviledges from faculty.
	 *  Therefor each group must be defined as a subelement of the facutly.
	 */
	private void createGroupsGrantPermissionsOfFaculties() {
		logger.debug("creating faculty group grant permissions.");
		List<ObjectIdentity> groupObjIds = new ArrayList<ObjectIdentity>(); 
		for(Faculty faculty : faculties.values()) {
			ObjectIdentity facultyObjId = facultyObjIds.get(faculty);
			for(Group group : faculty.getGroups()) {
				groupObjIds.add(createObjectIdentity(group.getId(), facultyObjId));
			}
		}
		logger.debug("persisting faculty group grant permissions.");
		getObjectIdentityDao().create(groupObjIds);
	}

	/**
	 * 
	 * @param assistants2
	 */
	private void createDesktopLinksForAssistants(Collection<Assistant2> assistants2) {
		logger.debug("creating assistant desktop links for enrollments and faculties");
		for (Assistant2 assistant: assistants2) {
			Desktop desktop = desktops.get(assistant.getId());
			if (desktop != null) {
				for (Assistantenrollment2 assistEnrollment: assistant.getAssistantenrollments()) {
					Enrollment enrollment = enrollments.get(assistEnrollment.getEnrollment().getId());
					if (enrollment != null) {
						desktop.getEnrollments().add(enrollment);
					}
				}
				for (Assistantfaculty2 facultyLink: assistant.getAssistantfaculties()) {
					Faculty faculty = faculties.get(facultyLink.getFaculty().getId());
					if (faculty != null) {
						desktop.getFaculties().add(faculty);
					}
				}
			}
		}
		logger.debug("persisting desktops");
		getDesktopDao().update(desktops.values());
	}

	/**
	 * Creates for each user in users a new desktop object
	 */
	private void createDesktopsForUsers() {
		logger.debug("creating assistant desktops");
		for(Map.Entry<String, User> entry: users.entrySet()) {
			Desktop desktop = Desktop.Factory.newInstance();
			desktop.setUser(entry.getValue());
			desktops.put(entry.getKey(), desktop);
		}
		logger.debug("persisting assistant desktops");
		getDesktopDao().create(desktops.values());
	}

	/**
	 * 
	 * @param faculties
	 */
	private void createGroupsAndPermissionsForFaculties(Collection<Faculty2> faculties2) {
		logger.debug("starting to create faculty groups");
		Collection<ObjectIdentity> objIds = new ArrayList<ObjectIdentity>();
		Collection<Group> groups = new ArrayList<Group>();
		for (Faculty2 faculty2 : faculties2) {
			Faculty faculty = faculties.get(faculty2.getId());
			Group groupAdmins = createGroup(faculty.getId(), GroupType.ADMINISTRATOR);
			Group groupAssistants = createGroup(faculty.getId(), GroupType.ASSISTANT);
			Group groupTutors = createGroup(faculty.getId(), GroupType.TUTOR);
			
			buildFacultySecurity(objIds, groups, faculty, groupAdmins, groupAssistants, groupTutors);
			buildFacultyMembers(faculty2, faculty, groupAdmins, groupAssistants);
		}
		logger.debug("starting to persist faculties, groups, objectidenties and permissions");
		getGroupDao().create(groups);
		getFacultyDao().update(faculties.values());
		getObjectIdentityDao().create(objIds);
	}

	private void buildFacultyMembers(Faculty2 faculty2, Faculty faculty, Group groupAdmins, Group groupAssistants) {
		// analyse faculty members and aspirants
		for(Assistantfaculty2 assistant: faculty2.getAssistantfaculties()) {
			User user = users.get(assistant.getAssistant().getId());
			if (user != null) {
				// aspirant or member?
				if (toBoolean(assistant.getAactive())) {
					if (!faculty.getOwner().equals(user)) {
						faculty.getMembers().add(user);
					}
					// is admin or owner?
					if (toBoolean(assistant.getIsadmin()) || faculty2.getAssistant().getId().equals(assistant.getAssistant().getId())) {
						groupAdmins.addMember(user);
						user.addGroup(groupAdmins);
					}
					groupAssistants.addMember(user);
					user.addGroup(groupAssistants);
				} else {
					faculty.getAspirants().add(user);
				}
			} else {
				logger.error("user not found "+assistant.getAssistant().getId());
			}
		}
	}

	/**
	 * Builds the Faculty ACL security. This depends on two steps:
	 * <ol>
	 * 		<li>Define a domain object identity structure for each faculty and its enrollments</li>
	 * 		<li>Define the group permissions to the faculty, that will be inherited to the enrollments</li>
	 * </ol> 
	 * 
	 * 
	 * @param objIds
	 * @param groups
	 * @param faculty
	 * @param groupAdmins
	 * @param groupAssistants
	 * @param groupTutors
	 */
	private void buildFacultySecurity(Collection<ObjectIdentity> objIds, Collection<Group> groups, Faculty faculty, Group groupAdmins, Group groupAssistants, Group groupTutors) {
		faculty.getGroups().add(groupAdmins);
		faculty.getGroups().add(groupAssistants);
		faculty.getGroups().add(groupTutors);
		
		groups.addAll(faculty.getGroups());
		
		// create object identity structure faculty <-(parent)---- enrollment
		ObjectIdentity facultyObjId = createObjectIdentity(faculty.getId(), null);
		objIds.add(facultyObjId);
		
		// cache faculty <--> objId dependency
		facultyObjIds.put(faculty, facultyObjId);
		
		for (Enrollment enrollment: faculty.getEnrollments()) {
			objIds.add(createObjectIdentity(enrollment.getId(),facultyObjId));
		}
		// create group permissions
		facultyObjId.addPermission(Permission.Factory.newInstance(LectureAclEntry.FACULTY_ADMINISTRATION, facultyObjId, groupAdmins ));
		facultyObjId.addPermission(Permission.Factory.newInstance(LectureAclEntry.FACULTY_ASSIST, facultyObjId, groupAssistants ));
		facultyObjId.addPermission(Permission.Factory.newInstance(LectureAclEntry.FACULTY_TUTOR, facultyObjId, groupTutors ));
	}
	
	private ObjectIdentity createObjectIdentity(Long id, ObjectIdentity parent) {
		ObjectIdentity objId = ObjectIdentity.Factory.newInstance();
		objId.setObjectIdentity(id);
		objId.setParent(parent);
		return objId;
	}
	
	/**
	 * 
	 * @param id
	 * @param groupType
	 * @return
	 */
	private Group createGroup(Long id, GroupType groupType) {
		String name = null;
		String label = null;
		if (groupType == GroupType.ADMINISTRATOR) {
			name = "GROUP_FACULTY_"+id+"_ADMINS";
			label = "Administrators";
		} else if (groupType == GroupType.ASSISTANT) {
			name = "GROUP_FACULTY_"+id+"_ASSISTANTS";
			label = "Assistants";
		} else if (groupType == GroupType.TUTOR) {
			name = "GROUP_FACULTY_"+id+"_TUTORS";
			label = "Tutors";
		}
		Group group = Group.Factory.newInstance();
		group.setName(name);
		group.setLabel(label);
		group.setPassword(null);
		group.setGroupType(groupType);
		return group;
	}

	/**
	 * Loads assistants information.
	 * Creates user, profile, preferences, contact, and role informations for each legacy assistant.
	 */
	private void migrateAssistantsToUsers(Collection<Assistant2> assistants2) {
		logger.debug("migrating assistants to users");
		logger.debug("transforming assistants ");
		for (Assistant2 assistant : assistants2) {
			users.put(assistant.getId(), assistantToUser(assistant));
		}
		logger.debug("starting to persist "+users.size()+" users");
		getUserDao().create(users.values());

		logger.debug("starting to add users to group");
		Group roleUser = getGroupDao().load(Roles.USER);
		Group roleAssist = getGroupDao().load(Roles.ASSISTANT);
		for (User user : users.values()) {
			roleUser.addMember(user);
			roleAssist.addMember(user);
			user.addGroup(roleUser);
			user.addGroup(roleAssist);
		}
		getGroupDao().update(roleUser);
		getGroupDao().update(roleAssist);
		logger.debug("finished to add assistants to group");
	}
	
	
	/**
	 * Map legacy assistant object to new user object.
	 * Creates an User with contact, preferences, and profile.
	 * @param assistant
	 * @return user object
	 */
	private User assistantToUser(Assistant2 assistant) {
		User user = User.Factory.newInstance();
		user.setUsername(assistant.getUusername());
		user.setPassword(assistant.getPpassword());
		user.setEmail(assistant.getEmailaddress());
		user.setEnabled(toBoolean(assistant.getAactive()));
		user.setContact(assistantToUserContact(assistant));
		user.setPreferences(assistantToUserPreferences(assistant));
		user.setProfile(assistantToUserProfile(assistant));
		return user;
	}
	
	/**
	 * Map assistant to UserProfile
	 * @param assistant
	 * @return
	 */
	private UserProfile assistantToUserProfile(Assistant2 assistant) {
		UserProfile profile = UserProfile.Factory.newInstance();

		if (!assistant.getAssistantinformations().isEmpty()) {
			Assistantinformation2 info = assistant.getAssistantinformations().iterator().next();
			profile.setEmailPublic(toBoolean(info.getEmail()));
			profile.setAddressPublic(toBoolean(info.getAddress()));
			profile.setImagePublic(toBoolean(info.getImage()));
			profile.setPortraitPublic(toBoolean(info.getDescription()));
			profile.setProfilePublic(toBoolean(info.getIspublic()));
			profile.setTelephonePublic(toBoolean(info.getTelephone()));
			
			profile.setPortrait(info.getTtext());
		}
		
		return profile;
	}

	/**
	 * Map assistant to user preferences
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
	
	/**
	 * Transformation of Faculties
	 */
	private void migrateFacultiesToFaculties(Collection<Faculty2> faculties2) {
		logger.debug("migrate faculties");
		logger.debug("starting to transform faculties");
		for (Faculty2 faculty2 : faculties2) {
			Faculty faculty = faculty2ToFaculty(faculty2);
			// define owner 
			User owner = users.get(faculty2.getAssistant().getId());
			faculty.setOwner(owner);
			faculty.getMembers().add(owner);
			
			faculties.put(faculty2.getId(),faculty);
		}
		logger.debug("finished to transform faculty structure");
		logger.debug("starting to persist faculties");
		getFacultyDao().create(faculties.values());
		logger.debug("finishing to persist faculties");
	}
	
	/**
	 * Map legacy faculty to new faculty
	 * @param faculty2
	 * @return object of faculty
	 */
	private Faculty faculty2ToFaculty(Faculty2 faculty2) {
		Faculty faculty = Faculty.Factory.newInstance();
		faculty.setName(faculty2.getName());
		faculty.setShortcut(faculty2.getId());
		faculty.setDescription(faculty2.getRemark());
		faculty.setWebsite(faculty2.getWebsite());
		faculty.setLocale(faculty2.getLocale());
		faculty.setOwnername(faculty2.getOwner());
		faculty.setEnabled(toBoolean(faculty2.getAactive()));

		Map<String, Subject> subjects = transformFacultySubjects(faculty2, faculty);
		
		transformFacultySemesters(faculty2, faculty, subjects);
		
		return faculty;
	}

	/**
	 * Transform faculty subject structure to new subjects
	 * @param faculty2
	 * @param faculty
	 * @return Map<old uid, Subject> 
	 */
	private Map<String, Subject> transformFacultySubjects(Faculty2 faculty2, Faculty faculty) {
		// migrating subjects
		Collection<Subject2> subjects2 = faculty2.getSubjects();
		for (Subject2 subject2 : subjects2) {
			Subject subject = subject2ToSubject(subject2);
			subjects.put(subject2.getId(), subject);
			faculty.add(subject);
			subject.setFaculty(faculty);
		}
		return subjects;
	}

	/**
	 * 
	 * @param faculty2
	 * @param faculty
	 * @param subjects
	 */
	private void transformFacultySemesters(Faculty2 faculty2, Faculty faculty, Map<String, Subject> subjects) {
		// migrating semesters
		Collection<Semester2> semesters2 = faculty2.getSemesters();
		for (Semester2 semester2 : semesters2) {
			Period period = semester2ToPeriod(semester2, subjects);
			faculty.add(period);
			period.setFaculty(faculty);
			for (Enrollment enrollment: period.getEnrollments()) {
				faculty.add(enrollment);
				enrollment.setFaculty(faculty);
			}
		}
	}
	
	
	/**
	 * Map legacy semester to new period
	 * @param semester
	 * @return object of period
	 */
	private Period semester2ToPeriod(Semester2 semester, Map<String, Subject> subjects) {
		Period period = Period.Factory.newInstance();
		period.setName(semester.getName());
		period.setDescription(semester.getRemark());
		
		Collection<Enrollment2> enrollments2 = semester.getEnrollments();
		for (Enrollment2 enrollment2 : enrollments2) {
			Enrollment enrollment = enrollment2ToEnrollment(enrollment2);
			enrollment.setPeriod(period);
			period.add(enrollment);
			enrollments.put(enrollment2.getId(), enrollment);
			Subject subject = subjects.get(enrollment2.getSubject().getId());
			subject.add(enrollment);
			enrollment.setSubject(subject);
		}
		
		return period;
	}
	
	/**
	 * Map legacy subject to new subject
	 * @param subject2
	 * @return object of subject
	 */
	private Subject subject2ToSubject(Subject2 subject2) {
		Subject subject = Subject.Factory.newInstance();
		subject.setName(subject2.getName());
		subject.setShortcut(subject2.getId());
		subject.setDescription(subject2.getRemark());

		return subject;
	}
	
	private Enrollment enrollment2ToEnrollment(Enrollment2 enrollment2) {
		Enrollment enrollment = Enrollment.Factory.newInstance();
		enrollment.setShortcut(enrollment2.getId());
		enrollment.setBraincontest(toBoolean(enrollment2.getQuiz()));
		enrollment.setChat(toBoolean(enrollment2.getChat()));
		enrollment.setDiscussion(toBoolean(enrollment2.getDiscussion()));
		enrollment.setDocuments(toBoolean(enrollment2.getLecturematerials()));
		enrollment.setMailinglist(toBoolean(enrollment2.getMailinglist()));
		enrollment.setFreestylelearning(toBoolean(enrollment2.getFslinstall()));
		enrollment.setWiki(false);
		// TODO insert description
		enrollment.setDescription("");
		return enrollment;
	}
	
	/**
	 * @param c
	 * @return true if character == '1'
	 */
	private boolean toBoolean(Character c) {
		return c != null && c == '1';
	}
	
}