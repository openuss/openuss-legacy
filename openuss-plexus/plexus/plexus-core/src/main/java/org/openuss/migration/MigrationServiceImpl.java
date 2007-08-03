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
import org.openuss.lecture.Course;
import org.openuss.lecture.Institute;
import org.openuss.lecture.Period;
import org.openuss.lecture.CourseType;
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

	private static final Logger logger = Logger.getLogger(MigrationServiceImpl.class);
	
	private Map<String, User> users = new HashMap<String, User>();
	private Map<String, Desktop> desktops = new HashMap<String, Desktop>();
	private Map<String, Institute> institutes = new HashMap<String, Institute>();
	private Map<String, Course> courses = new HashMap<String, Course>();
	private Map<String, CourseType> courseTypes = new HashMap<String, CourseType>();
	private Map<Institute, ObjectIdentity> instituteObjIds = new HashMap<Institute, ObjectIdentity>();
	
	/**
	 * @see org.openuss.migration.MigrationService#performMigration()
	 */
	protected synchronized void handlePerformMigration() throws java.lang.Exception {
		final LegacyDao legacyDao = getLegacyDao();
		
		logger.debug("loading legacy data");
		Collection<Assistant2> assistants2 = legacyDao.loadAllAssistants();
		logger.debug("load "+assistants2.size()+" legacy assistants entries.");
		Collection<Faculty2> faculties2 = legacyDao.loadAllInstitutes();
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
		Group roleUser = getGroupDao().load(Roles.USER_ID);
		for (User user : users.values()) {
			roleUser.addMember(user);
			user.addGroup(roleUser);
		}
		getGroupDao().update(roleUser);
		
		logger.debug("setting user object identity");
		Collection<ObjectIdentity> objIds = new ArrayList<ObjectIdentity>();
		for (User user : users.values()) {
			objIds.add(createObjectIdentity(user.getId(), null));
		}
		getObjectIdentityDao().create(objIds);
		
		logger.debug("finished to add assistants to group");
		
		
	}

	/**
	 * Transformation of Faculties
	 */
	private void migrateFacultiesToFaculties(Collection<Faculty2> faculties2) {
		logger.debug("migrate faculties");
		logger.debug("starting to transform faculties");
		for (Faculty2 faculty2 : faculties2) {
			Institute institute = faculty2ToInstitute(faculty2);
			// define owner 
			User owner = users.get(faculty2.getAssistant().getId());
			institute.getMembership().getMembers().add(owner);
			
			institutes.put(faculty2.getId(),institute);
		}
		logger.debug("finished to transform institute structure");
		logger.debug("starting to persist faculties");
		getInstituteDao().create(institutes.values());
		logger.debug("finishing to persist faculties");
	}

	/**
	 *  The faculty groups must inherit the grant priviledges from institute.
	 *  Therefor each group must be defined as a subelement of the facutly.
	 */
	private void createGroupsGrantPermissionsOfFaculties() {
		logger.debug("creating institute group grant permissions.");
		List<ObjectIdentity> groupObjIds = new ArrayList<ObjectIdentity>(); 
		for(Institute institute : institutes.values()) {
			ObjectIdentity instituteObjId = instituteObjIds.get(institute);
			/*for(Group group : institute.getGroups()) {
				groupObjIds.add(createObjectIdentity(group.getId(), instituteObjId));
			}*/
		}
		logger.debug("persisting institute group grant permissions.");
		getObjectIdentityDao().create(groupObjIds);
	}

	/**
	 * 
	 * @param assistants2
	 */
	private void createDesktopLinksForAssistants(Collection<Assistant2> assistants2) {
		logger.debug("creating assistant desktop links for courses and institutes");
		for (Assistant2 assistant: assistants2) {
			Desktop desktop = desktops.get(assistant.getId());
			if (desktop != null) {
				for (Assistantenrollment2 assistEnrollment: assistant.getAssistantenrollments()) {
					Course course = courses.get(assistEnrollment.getEnrollment().getId());
					if (course != null) {
						desktop.getCourses().add(course);
					}
				}
				for (Assistantfaculty2 facultyLink: assistant.getAssistantinstitutes()) {
					Institute institute = institutes.get(facultyLink.getFaculty().getId());
					if (institute != null) {
						desktop.getInstitutes().add(institute);
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
	 * @param institutes
	 */
	private void createGroupsAndPermissionsForFaculties(Collection<Faculty2> faculties2) {
		logger.debug("starting to create institute groups");
		Collection<ObjectIdentity> objIds = new ArrayList<ObjectIdentity>();
		Collection<Group> groups = new ArrayList<Group>();
		for (Faculty2 faculty2 : faculties2) {
			Institute institute = institutes.get(faculty2.getId());
			Group groupAdmins = createGroup(institute.getId(), GroupType.ADMINISTRATOR);
			Group groupAssistants = createGroup(institute.getId(), GroupType.ASSISTANT);
			Group groupTutors = createGroup(institute.getId(), GroupType.TUTOR);
			
			buildInstituteSecurity(objIds, groups, institute, groupAdmins, groupAssistants, groupTutors);
			buildInstituteMembers(faculty2, institute, groupAdmins, groupAssistants);
		}
		logger.debug("starting to persist institutes, groups, objectidenties and permissions");
		getGroupDao().create(groups);
		getInstituteDao().update(institutes.values());
		getObjectIdentityDao().create(objIds);
	}

	private void buildInstituteMembers(Faculty2 faculty2, Institute institute, Group groupAdmins, Group groupAssistants) {
		// analyse institute members and aspirants
		for(Assistantfaculty2 assistant: faculty2.getAssistantinstitutes()) {
			User user = users.get(assistant.getAssistant().getId());
			if (user != null) {
				// aspirant or member?
				if (toBoolean(assistant.getAactive())) {
					/*if (!institute.getOwner().equals(user)) {
						institute.getMembers().add(user);
					}*/
					// is admin or owner?
					if (toBoolean(assistant.getIsadmin()) || faculty2.getAssistant().getId().equals(assistant.getAssistant().getId())) {
						groupAdmins.addMember(user);
						user.addGroup(groupAdmins);
					}
					groupAssistants.addMember(user);
					user.addGroup(groupAssistants);
				} else {
					institute.getMembership().getAspirants().add(user);
				}
			} else {
				logger.error("user not found "+assistant.getAssistant().getId());
			}
		}
	}

	/**
	 * Builds the Faculty ACL security. This depends on two steps:
	 * <ol>
	 * 		<li>Define a domain object identity structure for each institute and its courses</li>
	 * 		<li>Define the group permissions to the institute, that will be inherited to the courses</li>
	 * </ol> 
	 * 
	 * 
	 * @param objIds
	 * @param groups
	 * @param institute
	 * @param groupAdmins
	 * @param groupAssistants
	 * @param groupTutors
	 */
	private void buildInstituteSecurity(Collection<ObjectIdentity> objIds, Collection<Group> groups, Institute institute, Group groupAdmins, Group groupAssistants, Group groupTutors) {
		institute.getMembership().getGroups().add(groupAdmins);
		institute.getMembership().getGroups().add(groupAssistants);
		institute.getMembership().getGroups().add(groupTutors);
		
		groups.addAll(institute.getMembership().getGroups());
		
		// create object identity structure institute <-(parent)---- course
		ObjectIdentity instituteObjId = createObjectIdentity(institute.getId(), null);
		objIds.add(instituteObjId);
		
		// cache institute <--> objId dependency
		instituteObjIds.put(institute, instituteObjId);
		
		/*for (Course course: institute.getCourses()) {
			objIds.add(createObjectIdentity(course.getId(),instituteObjId));
		}*/
		// create group permissions
		instituteObjId.addPermission(Permission.Factory.newInstance(LectureAclEntry.INSTITUTE_ADMINISTRATION, instituteObjId, groupAdmins ));
		instituteObjId.addPermission(Permission.Factory.newInstance(LectureAclEntry.INSTITUTE_ASSIST, instituteObjId, groupAssistants ));
		instituteObjId.addPermission(Permission.Factory.newInstance(LectureAclEntry.INSTITUTE_TUTOR, instituteObjId, groupTutors ));
	}
	
	private ObjectIdentity createObjectIdentity(Long id, ObjectIdentity parent) {
		ObjectIdentity objId = ObjectIdentity.Factory.newInstance();
		objId.setId(id);
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
	 * Map legacy institute to new institute
	 * @param faculty2
	 * @return object of institute
	 */
	private Institute faculty2ToInstitute(Faculty2 faculty2) {
		Institute institute = Institute.Factory.newInstance();
		institute.setName(faculty2.getName());
		institute.setShortcut(faculty2.getId());
		institute.setDescription(faculty2.getRemark());
		institute.setWebsite(faculty2.getWebsite());
		institute.setLocale(faculty2.getLocale());
		institute.setOwnerName(faculty2.getOwner());
		institute.setEnabled(toBoolean(faculty2.getAactive()));

		Map<String, CourseType> courseTypes = transformInstituteSubjects(faculty2, institute);
		
		transformInstituteSemesters(faculty2, institute, courseTypes);
		
		return institute;
	}

	/**
	 * Transform institute subject structure to new subjects
	 * @param faculty2
	 * @param institute
	 * @return Map<old uid, Subject> 
	 */
	private Map<String, CourseType> transformInstituteSubjects(Faculty2 faculty2, Institute institute) {
		// migrating subjects
		Collection<Subject2> subjects2 = faculty2.getSubjects();
		for (Subject2 subject2 : subjects2) {
			CourseType courseType = subject2ToCourseType(subject2);
			courseTypes.put(subject2.getId(), courseType);
			institute.add(courseType);
			courseType.setInstitute(institute);
		}
		return courseTypes;
	}

	/**
	 * 
	 * @param faculty2
	 * @param institute
	 * @param courseTypes
	 */
	private void transformInstituteSemesters(Faculty2 faculty2, Institute institute, Map<String, CourseType> subjects) {
		//TODO: Implement me correctly!
		/*
		// migrating semesters
		Collection<Semester2> semesters2 = faculty2.getSemesters();
		for (Semester2 semester2 : semesters2) {
			Period period = semester2ToPeriod(semester2, subjects);
			institute.add(period);
			period.setInstitute(institute);
			for (Course course: period.getCourses()) {
				institute.add(course);
				course.setInstitute(institute);
			}
		}
		*/
	}
	
	
	/**
	 * Map legacy semester to new period
	 * @param semester
	 * @return object of period
	 */
	private Period semester2ToPeriod(Semester2 semester, Map<String, CourseType> subjects) {
		Period period = Period.Factory.newInstance();
		period.setName(semester.getName());
		period.setDescription(semester.getRemark());
		
		Collection<Enrollment2> courses2 = semester.getEnrollments();
		for (Enrollment2 enrollment2 : courses2) {
			Course course = enrollment2ToCourse(enrollment2);
			course.setPeriod(period);
			period.add(course);
			courses.put(enrollment2.getId(), course);
			CourseType subject = subjects.get(enrollment2.getSubject().getId());
			subject.add(course);
			course.setCourseType(subject);
		}
		
		return period;
	}
	
	/**
	 * Map legacy subject to new subject
	 * @param subject2
	 * @return object of subject
	 */
	private CourseType subject2ToCourseType(Subject2 subject2) {
		CourseType courseType = CourseType.Factory.newInstance();
		courseType.setName(subject2.getName());
		courseType.setShortcut(subject2.getId());
		courseType.setDescription(subject2.getRemark());

		return courseType;
	}
	
	private Course enrollment2ToCourse(Enrollment2 enrollment2) {
		Course course = Course.Factory.newInstance();
		// TODO check if the shortcut can be generated from the institute and subject name instead of the previous guid
		course.setShortcut(enrollment2.getId());
		course.setDescription(enrollment2.getSubject().getRemark());

		course.setBraincontest(toBoolean(enrollment2.getQuiz()));
		course.setChat(toBoolean(enrollment2.getChat()));
		course.setDiscussion(toBoolean(enrollment2.getDiscussion()));
		course.setDocuments(toBoolean(enrollment2.getLecturematerials()));
		course.setNewsletter(toBoolean(enrollment2.getMailinglist()));
		course.setFreestylelearning(toBoolean(enrollment2.getFslinstall()));
		course.setWiki(false);
		return course;
	}
	
	/**
	 * @param c
	 * @return true if character == '1'
	 */
	private boolean toBoolean(Character c) {
		return c != null && c == '1';
	}
	
}