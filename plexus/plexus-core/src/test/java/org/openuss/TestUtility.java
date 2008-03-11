package org.openuss;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.openuss.calendar.Appointment;
import org.openuss.calendar.AppointmentDao;
import org.openuss.calendar.AppointmentInfo;
import org.openuss.calendar.AppointmentType;
import org.openuss.calendar.AppointmentTypeDao;
import org.openuss.calendar.RecurrenceType;
import org.openuss.calendar.SerialAppointment;
import org.openuss.calendar.SerialAppointmentDao;
import org.openuss.groups.GroupAccessType;
import org.openuss.groups.UserGroup;
import org.openuss.groups.UserGroupDao;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.Application;
import org.openuss.lecture.ApplicationDao;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseDao;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.CourseTypeDao;
import org.openuss.lecture.Department;
import org.openuss.lecture.DepartmentDao;
import org.openuss.lecture.DepartmentType;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteDao;
import org.openuss.lecture.OrganisationService;
import org.openuss.lecture.Period;
import org.openuss.lecture.PeriodDao;
import org.openuss.lecture.University;
import org.openuss.lecture.UniversityDao;
import org.openuss.lecture.UniversityType;
import org.openuss.security.Group;
import org.openuss.security.GroupDao;
import org.openuss.security.GroupItem;
import org.openuss.security.GroupType;
import org.openuss.security.Membership;
import org.openuss.security.MembershipDao;
import org.openuss.security.Roles;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserContact;
import org.openuss.security.UserDao;
import org.openuss.security.UserImpl;
import org.openuss.security.UserPreferences;
import org.openuss.security.UserProfile;
import org.openuss.security.acl.LectureAclEntry;

/**
 * Test Utility to generate default database structures
 * 
 * @author Ingo Dueppe
 * @author Ron Haus
 * @author Florian Dondorf
 * @author Lutz D. Kramer
 */
public class TestUtility {

	private UserDao userDao;

	private GroupDao groupDao;

	private MembershipDao membershipDao;

	private InstituteDao instituteDao;

	private UniversityDao universityDao;

	private DepartmentDao departmentDao;

	private CourseTypeDao courseTypeDao;

	private CourseDao courseDao;

	private ApplicationDao applicationDao;

	private PeriodDao periodDao;

	private UserGroupDao userGroupDao;

	private SecurityService securityService;

	private OrganisationService organisationService;

	private User defaultUser;

	private Institute defaultInstitute;
	
	private AppointmentTypeDao appointmentTypeDao;
	
	private AppointmentDao appointmentDao;
	
	private SerialAppointmentDao serialAppointmentDao;
	
	private org.openuss.calendar.CalendarDao calendarDao;

	private static long uniqueId = System.currentTimeMillis();
	


	/**
	 * @deprecated As of OpenUSS 3.0 RC1, replaced by
	 *             <code>TestUtility.createUniqueUserInDB()</code>.
	 */
	public User createDefaultUserInDB() {
		defaultUser.setUsername(unique("username"));
		defaultUser.setGroups(new ArrayList<Group>());
		defaultUser.setContact(UserContact.Factory.newInstance());
		defaultUser.setPreferences(UserPreferences.Factory.newInstance());
		defaultUser.setProfile(UserProfile.Factory.newInstance());
		userDao.create(defaultUser);
		return defaultUser;
	}

	/**
	 * @deprecated As of OpenUSS 3.0 RC1, replaced by
	 *             <code>TestUtility.createUniqueUserInDB()</code>.
	 */
	public User createUserInDB() {
		User user = createDefaultUser();
		userDao.create(user);
		return user;
	}

	/**
	 * @deprecated As of OpenUSS 3.0 RC1
	 */
	public void removeUser() {
		removeUser(defaultUser);
	}

	/**
	 * @deprecated As of OpenUSS 3.0 RC1
	 */
	public void updateUser(User user) {
		userDao.update(user);
	}

	/**
	 * @deprecated As of OpenUSS 3.0 RC1
	 */
	public void removeUser(User user) {
		user = userDao.load(user.getId());
		userDao.remove(user);
	}

	/**
	 * @deprecated As of OpenUSS 3.0 RC1, replaced by
	 *             <code>TestUtility.createUniqueInstituteInDB()</code>.
	 */
	public Institute createPersistInstituteWithDefaultUser() {
		defaultUser.setUsername(unique("username"));
		defaultUser.setContact(UserContact.Factory.newInstance());
		defaultUser.setFirstName("firstName");
		defaultUser.setLastName("lastName");
		defaultUser.setTitle("title");
		defaultUser.setEmail(unique("email"));
		defaultUser.setLocale("de_DE");
		defaultUser.setPreferences(UserPreferences.Factory.newInstance());

		userDao.create(defaultUser);
		defaultInstitute.setName(unique("name"));
		defaultInstitute.setShortcut(unique("shortcut"));
		defaultInstitute.setEmail(unique("email"));
		defaultInstitute.setLocale("de_DE");
		defaultInstitute.setOwnerName("owner name");
		defaultInstitute.setMembership(Membership.Factory.newInstance());
		instituteDao.create(defaultInstitute);
		return defaultInstitute;
	}

	public User createUniqueUserInDB() {
		// Create a unique User
		UserPreferences userPreferences = UserPreferences.Factory.newInstance();
		userPreferences.setLocale("de");
		userPreferences.setTheme("plexus");
		userPreferences.setTimezone(TimeZone.getDefault().getID());

		UserContact userContact = UserContact.Factory.newInstance();
		userContact.setFirstName("Unique");
		userContact.setLastName("User");
		userContact.setAddress("Leonardo Campus 5");
		userContact.setCity("M�nster");
		userContact.setCountry("Germany");
		userContact.setPostcode("48149");

		User user = User.Factory.newInstance();
		user.setUsername(unique("username"));
		user.setPassword("masterkey");
		user.setEmail(unique("openuss") + "@e-learning.uni-muenster.de");
		user.setEnabled(true);
		user.setAccountExpired(false);
		user.setCredentialsExpired(false);
		user.setAccountLocked(false);

		user.setPreferences(userPreferences);
		user.setContact(userContact);
		user.setGroups(new ArrayList<Group>());

		userDao.create(user);

		return user;
	}

	public Membership createUniqueMembershipInDB() {
		// Create a unique User
		User user = this.createUniqueUserInDB();

		// Create a unique Membership
		Membership membership = Membership.Factory.newInstance();
		membership.getMembers().add(user);
		Group group = Group.Factory.newInstance();
		group.setName(unique("MEMBERSHIP_TestGroup"));
		group.setLabel(unique("autogroup_testgroup_label"));
		group.setGroupType(GroupType.ADMINISTRATOR);
		group.addMember(user);
		membership.getGroups().add(group);

		membershipDao.create(membership);

		return membership;
	}

	public University createUniqueUniversityInDB() {
		// Create a User
		User user = this.createUniqueUserInDB();

		// Create a unique University
		University university = University.Factory.newInstance();
		university.setName(unique("University"));
		university.setShortcut(unique("Uni"));
		university.setDescription("A unique University");
		university.setOwnerName("Administrator");
		university.setUniversityType(UniversityType.UNIVERSITY);
		university.setMembership(Membership.Factory.newInstance());
		university.setEnabled(true);
		university.setAddress("Leo 18");
		university.setCity("M�nster");
		university.setCountry("Germany");
		university.setEmail("openuss@uni-muenster.de");
		university.setLocale("de");
		university.setPostcode("48149");
		university.setTelefax("0251-telefax");
		university.setTelephone("0251-telephone");
		university.setTheme("plexus");
		university.setWebsite("www.openuss.de");

		// Add a default NONOFFICIAL Department
		Department department = Department.Factory.newInstance();
		department.setName("Standard Department");
		department.setDefaultDepartment(true);
		department.setDepartmentType(DepartmentType.NONOFFICIAL);
		department.setShortcut(unique("StandDepart"));
		department.setDescription("Dies ist das Standard Department.");
		department.setOwnerName(university.getOwnerName());
		department.setEnabled(true);
		department.setMembership(Membership.Factory.newInstance());
		department.setAddress(university.getAddress());
		department.setCity(university.getCity());
		department.setCountry(university.getCountry());
		department.setEmail(university.getEmail());
		department.setLocale(university.getLocale());
		department.setPostcode(university.getPostcode());
		department.setTelefax(university.getTelefax());
		department.setTelephone(university.getTelephone());
		department.setTheme(university.getTheme());
		department.setWebsite(university.getWebsite());
		university.add(department);

		// Create a default Period
		Period period = Period.Factory.newInstance();
		period.setName("Standard Period");
		period.setDefaultPeriod(true);
		period.setDescription("Dies ist die Standard Period.");
		period.setStartdate(new Date(0)); // 1. January 1970, 00:00:00 GMT
		Calendar cal = new GregorianCalendar();
		cal.set(2050, 11, 31);// 31. December 2050, 00:00:00 GMT
		period.setEnddate(new Date(cal.getTimeInMillis()));
		university.add(period);

		// periodDao.create(period);

		// Create the University
		this.getUniversityDao().create(university);
		this.getDepartmentDao().create(department);
		this.getPeriodDao().create(period);

		// Create default Groups for the University and it's Department
		GroupItem groupItemUni = new GroupItem();
		groupItemUni.setName("UNIVERSITY_" + university.getId() + "_ADMINS");
		groupItemUni.setLabel("autogroup_administrator_label");
		groupItemUni.setGroupType(GroupType.ADMINISTRATOR);
		Long adminsUniId = this.getOrganisationService().createGroup(
				university.getId(), groupItemUni);
		Group adminsUni = this.getGroupDao().load(adminsUniId);

		GroupItem groupItemDepart = new GroupItem();
		groupItemDepart.setName("DEPARTMENT_" + department.getId() + "_ADMINS");
		groupItemDepart.setLabel("autogroup_administrator_label");
		groupItemDepart.setGroupType(GroupType.ADMINISTRATOR);
		Long adminsDepartId = this.getOrganisationService().createGroup(
				department.getId(), groupItemDepart);
		Group adminsDepart = this.getGroupDao().load(adminsDepartId);

		// Set ObjectIdentity for Security
		this.getSecurityService().createObjectIdentity(university, null);
		this.getSecurityService().createObjectIdentity(department, university);
		this.getSecurityService().createObjectIdentity(period, university);

		// Set ACL permissions
		this.getSecurityService().setPermissions(adminsUni, university,
				LectureAclEntry.UNIVERSITY_ADMINISTRATION);
		this.getSecurityService().setPermissions(adminsDepart, department,
				LectureAclEntry.DEPARTMENT_ADMINISTRATION);

		// Add Owner to Members and Group of Administrators
		this.getOrganisationService().addMember(university.getId(),
				user.getId());
		this.getOrganisationService().addMember(department.getId(),
				user.getId());
		this.getOrganisationService().addUserToGroup(user.getId(),
				adminsUni.getId());
		this.getOrganisationService().addUserToGroup(user.getId(),
				adminsDepart.getId());

		return university;
	}

	public University createUniqueEmptyUniversityInDB() {
		// Create a User
		User user = this.createUniqueUserInDB();

		// Create a unique University
		University university = University.Factory.newInstance();
		university.setName(unique("University"));
		university.setShortcut(unique("Uni"));
		university.setDescription("A unique University");
		university.setOwnerName("Administrator");
		university.setUniversityType(UniversityType.UNIVERSITY);
		university.setMembership(Membership.Factory.newInstance());
		university.setEnabled(true);
		university.setAddress("Leo 18");
		university.setCity("M�nster");
		university.setCountry("Germany");
		university.setEmail("openuss@uni-muenster.de");
		university.setLocale("de");
		university.setPostcode("48149");
		university.setTelefax("0251-telefax");
		university.setTelephone("0251-telephone");
		university.setTheme("plexus");
		university.setWebsite("www.openuss.de");

		// Create the University
		this.getUniversityDao().create(university);

		// Create default Groups for the University
		GroupItem groupItemUni = new GroupItem();
		groupItemUni.setName("UNIVERSITY_" + university.getId() + "_ADMINS");
		groupItemUni.setLabel("autogroup_administrator_label");
		groupItemUni.setGroupType(GroupType.ADMINISTRATOR);
		Long adminsUniId = this.getOrganisationService().createGroup(
				university.getId(), groupItemUni);
		Group adminsUni = this.getGroupDao().load(adminsUniId);

		// Set ObjectIdentity for Security
		this.getSecurityService().createObjectIdentity(university, null);

		// Set ACL permissions
		this.getSecurityService().setPermissions(adminsUni, university,
				LectureAclEntry.UNIVERSITY_ADMINISTRATION);

		// Add Owner to Members and Group of Administrators
		this.getOrganisationService().addMember(university.getId(),
				user.getId());
		this.getOrganisationService().addUserToGroup(user.getId(),
				adminsUni.getId());

		return university;
	}

	public Department createUniqueDepartmentInDB() {
		// Create a User
		User user = this.createUniqueUserInDB();

		// Create a unique University
		University university = this.createUniqueUniversityInDB();

		// Create a unique Department
		Department department = Department.Factory.newInstance();
		department.setName(unique("Department"));
		department.setDepartmentType(DepartmentType.NONOFFICIAL);
		department.setShortcut(unique("Dep"));
		department.setDescription("A unique Department");
		department.setOwnerName("Administrator");
		department.setEnabled(true);
		department.setDefaultDepartment(false);
		department.setMembership(Membership.Factory.newInstance());
		department.setAddress("Leo 18");
		department.setCity("M�nster");
		department.setCountry("Germany");
		department.setEmail("openuss@uni-muenster.de");
		department.setLocale("de");
		department.setPostcode("48149");
		department.setTelefax("0251-telefax");
		department.setTelephone("0251-telephone");
		department.setTheme("plexus");
		department.setWebsite("www.openuss.de");

		university.add(department);

		departmentDao.create(department);

		// Create default Groups for Department
		GroupItem groupItem = new GroupItem();
		groupItem.setName("DEPARTMENT_" + department.getId() + "_ADMINS");
		groupItem.setLabel("autogroup_administrator_label");
		groupItem.setGroupType(GroupType.ADMINISTRATOR);
		Long adminsId = this.getOrganisationService().createGroup(
				department.getId(), groupItem);
		Group admins = this.getGroupDao().load(adminsId);

		// Security
		this.getSecurityService().createObjectIdentity(department, university);
		this.getSecurityService().setPermissions(admins, department,
				LectureAclEntry.DEPARTMENT_ADMINISTRATION);

		// Add Owner to Members and Group of Administrators
		this.getOrganisationService().addMember(department.getId(),
				user.getId());
		this.getOrganisationService().addUserToGroup(user.getId(),
				admins.getId());

		return department;
	}

	public Institute createUniqueInstituteInDB() {
		// Create a User
		User user = this.createUniqueUserInDB();

		// Create a unique Department
		Department department = this.createUniqueDepartmentInDB();

		// Create a unique Institute
		Institute institute = Institute.Factory.newInstance();
		institute.setName(unique("Institute"));
		institute.setShortcut(unique("Inst"));
		institute.setDescription("A unique Insitute");
		institute.setOwnerName("Administrator");
		institute.setEnabled(true);
		institute.setMembership(Membership.Factory.newInstance());
		institute.setAddress("Leo 18");
		institute.setCity("M�nster");
		institute.setCountry("Germany");
		institute.setEmail("openuss@uni-muenster.de");
		institute.setLocale("de");
		institute.setPostcode("48149");
		institute.setTelefax("0251-telefax");
		institute.setTelephone("0251-telephone");
		institute.setTheme("plexus");
		institute.setWebsite("www.openuss.de");

		department.add(institute);
		institute.setDepartment(department);

		instituteDao.create(institute);

		// Create Application
		Application application = Application.Factory.newInstance();
		application.setApplicationDate(new Date());
		application.setApplyingUser(user);
		application.setConfirmationDate(new Date());
		application.setConfirmed(true);
		application.setConfirmingUser(user);
		application.setDescription("Automatically created Application");
		application.add(institute);
		application.add(department);
		this.getApplicationDao().create(application);

		// Create default Groups for Institute
		GroupItem admins = new GroupItem();
		admins.setName("INSTITUTE_" + institute.getId() + "_ADMINS");
		admins.setLabel("autogroup_administrator_label");
		admins.setGroupType(GroupType.ADMINISTRATOR);
		Long adminsId = this.getOrganisationService().createGroup(
				institute.getId(), admins);
		Group adminsGroup = this.getGroupDao().load(adminsId);

		GroupItem assistants = new GroupItem();
		assistants.setName("INSTITUTE_" + institute.getId() + "_ASSISTANTS");
		assistants.setLabel("autogroup_assistant_label");
		assistants.setGroupType(GroupType.ASSISTANT);
		Long assistantsId = this.getOrganisationService().createGroup(
				institute.getId(), assistants);
		Group assistantsGroup = this.getGroupDao().load(assistantsId);

		GroupItem tutors = new GroupItem();
		tutors.setName("INSTITUTE_" + institute.getId() + "_TUTORS");
		tutors.setLabel("autogroup_tutor_label");
		tutors.setGroupType(GroupType.TUTOR);
		Long tutorsId = this.getOrganisationService().createGroup(
				institute.getId(), tutors);
		Group tutorsGroup = this.getGroupDao().load(tutorsId);

		// Security
		this.getSecurityService().createObjectIdentity(institute, null);
		this.getSecurityService().setPermissions(adminsGroup, institute,
				LectureAclEntry.INSTITUTE_ADMINISTRATION);
		this.getSecurityService().setPermissions(assistantsGroup, institute,
				LectureAclEntry.INSTITUTE_ASSIST);
		this.getSecurityService().setPermissions(tutorsGroup, institute,
				LectureAclEntry.INSTITUTE_TUTOR);

		// Add Owner to Members and the group of Administrators
		this.getOrganisationService()
				.addMember(institute.getId(), user.getId());
		this.getOrganisationService().addUserToGroup(user.getId(),
				adminsGroup.getId());

		return institute;
	}

	public CourseType createUniqueCourseTypeInDB() {
		// Create a unique Institute
		Institute institute = this.createUniqueInstituteInDB();

		// Create a unique CourseType
		CourseType courseType = CourseType.Factory.newInstance();
		courseType.setName(unique("CourseType"));
		courseType.setShortcut(unique("CT"));
		courseType.setDescription("A unique CourseType");
		courseType.setInstitute(institute);
		institute.add(courseType);

		courseTypeDao.create(courseType);

		this.getSecurityService().createObjectIdentity(courseType, institute);

		return courseType;
	}

	public Course createUniqueCourseInDB() {

		// Create a unique CourseType and Period
		CourseType courseType = this.createUniqueCourseTypeInDB();
		Period period = this.createUniquePeriodInDB(courseType.getInstitute()
				.getDepartment().getUniversity());

		// Create a unique CourseType
		Course course = Course.Factory.newInstance();
		course.setShortcut(unique("cou"));
		course.setDescription("A unique Course");
		course.setAccessType(AccessType.APPLICATION);
		course.setDocuments(false);
		course.setDiscussion(false);
		course.setNewsletter(false);
		course.setChat(false);
		course.setWiki(false);
		course.setFreestylelearning(false);
		course.setBraincontest(false);
		period.add(course);
		courseType.add(course);

		courseDao.create(course);
		this.getSecurityService().createObjectIdentity(course, courseType);

		return course;
	}

	public Period createUniquePeriodInDB(University university) {

		// Create Startdate
		Calendar cal = new GregorianCalendar();
		cal.set(2007, 3, 1);
		Date startdate = new Date(cal.getTimeInMillis());

		// Create Enddate
		cal = new GregorianCalendar();
		cal.set(2008, 8, 31);
		Date enddate = new Date(cal.getTimeInMillis());

		// Create a unique Period
		Period period = Period.Factory.newInstance();
		period.setName(unique("Period"));
		period.setDescription("A unique Period");
		period.setCourses(new ArrayList<Course>());
		period.setStartdate(startdate);
		period.setEnddate(enddate);
		period.setDefaultPeriod(false);
		university.add(period);

		periodDao.create(period);
		this.getSecurityService().createObjectIdentity(period, university);

		return period;
	}

	public Period createUniquePeriodInDB() {
		// Create University
		University university = this.createUniqueUniversityInDB();

		// Create Startdate
		Calendar cal = new GregorianCalendar();
		cal.set(2007, 3, 1);
		Date startdate = new Date(cal.getTimeInMillis());

		// Create Enddate
		cal = new GregorianCalendar();
		cal.set(2008, 8, 31);
		Date enddate = new Date(cal.getTimeInMillis());

		// Create a unique Period
		Period period = Period.Factory.newInstance();
		period.setName(unique("Period"));
		period.setDescription("A unique Period");
		period.setCourses(new ArrayList<Course>());
		period.setStartdate(startdate);
		period.setEnddate(enddate);
		period.setDefaultPeriod(false);
		university.add(period);

		periodDao.create(period);
		this.getSecurityService().createObjectIdentity(period, university);

		return period;
	}

	public Period createUniqueInactivePeriodInDB() {
		// Create a unique University
		University university = this.createUniqueUniversityInDB();

		// Create Startdate
		Calendar cal = new GregorianCalendar();
		cal.set(2005, 3, 1);
		Date startdate = new Date(cal.getTimeInMillis());

		// Create Enddate
		cal = new GregorianCalendar();
		cal.set(2006, 8, 31);
		Date enddate = new Date(cal.getTimeInMillis());

		// Create a unique Period
		Period period = Period.Factory.newInstance();
		period.setName(unique("Period"));
		period.setDescription("A unique Period");
		period.setCourses(new ArrayList<Course>());
		period.setStartdate(startdate);
		period.setEnddate(enddate);
		period.setDefaultPeriod(false);
		university.add(period);

		periodDao.create(period);

		return period;
	}

	/**
	 * @deprecated
	 */
	public Application createUniqueUnconfirmedApplicationInDB() {
		// Create a complete Application
		Application application = Application.Factory.newInstance();
		application.setApplicationDate(new Date());
		Department department = this.createUniqueDepartmentInDB();
		department.setDepartmentType(DepartmentType.OFFICIAL);
		application.add(department);
		application.add(this.createUniqueInstituteInDB());
		application.setConfirmed(false);
		application.setDescription("A unique Application");
		application.setApplyingUser(this.createUniqueUserInDB());
		application.setConfirmingUser(this.createUniqueUserInDB());
		application.setApplicationDate(new Date());
		application.setConfirmationDate(new Date());
		applicationDao.create(application);

		return application;
	}

	public void removePersistInstituteAndDefaultUser() {
		instituteDao.remove(defaultInstitute);
		// userDao.remove(defaultInstitute.getOwner());
	}

	/**
	 * @deprecated As of OpenUSS 3.0 RC1, replaced by
	 *             <code>TestUtility.createUniqueInstituteInDB()</code>.
	 */
	public Institute createdDefaultInstituteWithStoredUser() {
		defaultUser.setUsername(unique("username"));
		userDao.create(defaultUser);
		defaultInstitute.setName(unique("name"));
		defaultInstitute.setShortcut(unique("shortcut"));
		instituteDao.create(defaultInstitute);
		return defaultInstitute;
	}

	public User createAnonymousSecureContext() {
		return createSecureContext(Roles.ANONYMOUS_ID);
	}

	public User createUserSecureContext() {
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
		authentication = new UsernamePasswordAuthenticationToken(user,
				"[Protected]", ((UserImpl) user).getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);
		return user;
	}

	public void destroySecureContext() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	/**
	 * @deprecated As of OpenUSS 3.0 RC1, replaced by
	 *             <code>TestUtility.createUniqueUserInDB()</code>.
	 */
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

	public UserGroup createUniqueUserGroupInDB() {

		// Create a unique GroupType
		UserGroup userGroup = UserGroup.Factory.newInstance();
		userGroup.setName("UserGroup");
		userGroup.setShortcut(unique("group"));
		userGroup.setCreator(createUniqueUserInDB());
		Group mod = Group.Factory.newInstance();
		mod.setName(unique("moderator"));
		mod.setGroupType(GroupType.MODERATOR);
		getGroupDao().create(mod);
		userGroup.setModeratorsGroup(mod);
		Group mem = Group.Factory.newInstance();
		mem.setName(unique("member"));
		mem.setGroupType(GroupType.MEMBER);
		getGroupDao().create(mem);
		userGroup.setMembersGroup(mem);
		userGroup.setMembership(Membership.Factory.newInstance());
		userGroup.setAccessType(GroupAccessType.OPEN);
		userGroup.setForum(true);
		userGroup.setNewsletter(true);
		userGroup.setChat(false);
		userGroup.setDescription("A UserGroup");
		userGroup.setDocuments(true);
		userGroup.setCalendar(true);
		this.getUserGroupDao().create(userGroup);
		this.getSecurityService().createObjectIdentity(userGroup, null);

		return userGroup;
	}

	public static synchronized long unique() {
		return ++uniqueId;
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

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public void setCourseTypeDao(CourseTypeDao courseTypeDao) {
		this.courseTypeDao = courseTypeDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public void setPeriodDao(PeriodDao periodDao) {
		this.periodDao = periodDao;
	}

	public CourseTypeDao getCourseTypeDao() {
		return courseTypeDao;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public ApplicationDao getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}

	public PeriodDao getPeriodDao() {
		return periodDao;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public OrganisationService getOrganisationService() {
		return organisationService;
	}

	public void setOrganisationService(OrganisationService organisationService) {
		this.organisationService = organisationService;
	}

	public void setUserGroupDao(UserGroupDao userGroupDao) {
		this.userGroupDao = userGroupDao;
	}

	public UserGroupDao getUserGroupDao() {
		return userGroupDao;
	}

	/***************************** Calendar *************************/
	
	public AppointmentType createAppointmentTypeInDB(String name) {
		AppointmentType appType = AppointmentType.Factory.newInstance();
		appType.setName(name);
		appointmentTypeDao.create(appType);
		return appType;
	}
	
	public AppointmentInfo getTestAppointmentInfo(AppointmentType appType) {
		
		Date start = new Date();
		Date end = new Date();

		AppointmentInfo appInfo = new AppointmentInfo();
		appInfo.setSubject("Subject");
		appInfo.setDescription("Description");
		appInfo.setLocation("Location");
		appInfo.setStarttime(new Timestamp(start.getTime()));
		appInfo.setEndtime(new Timestamp(end.getTime()));
		appInfo.setAppointmentTypeInfo(appointmentTypeDao
				.toAppointmentTypeInfo(appType));

		// TODO isSerial setzen beim Erzeugen eines Termins
		appInfo.setSerial(false);

		return appInfo;
	}
	
	public void setAppointmentTypeDao(AppointmentTypeDao appointmentTypeDao) {
		this.appointmentTypeDao = appointmentTypeDao;
	}

	public void createUniqueAppointmentForCalendarInDB(org.openuss.calendar.CalendarInfo calInfo) {
//		Appointment app = Appointment.Factory.newInstance();
//		app.setAppointmentType(appType);
//		app.setDescription("description");
//		app.setLocation("location");
//		app.setSubject(unique("subject"));
//		app.setSerial(false);
		AppointmentType appType = this.createAppointmentTypeInDB("standard");
		Random rnd = new Random();
		Long randomLong = rnd.nextLong();
		Timestamp start = new Timestamp(randomLong);
		Timestamp end = new Timestamp(randomLong + (60*60*60)); // 1 h later
		System.out.println("startzeitpunkt: " + start.toGMTString());
		System.out.println("endzeitpunkt: " + end.toGMTString());
//		app.setStarttime(start);
//		app.setEndtime(end);
		org.openuss.calendar.Calendar cal = calendarDao.load(calInfo.getId());
		Appointment app = appointmentDao.create(appType, "description", end, "location", false, cal, start, unique("subject"),true);
		
		cal.addAppointment(app);
		calendarDao.update(cal);
	}
	
	public void createUniqueSeriallAppForCalendarInDB(org.openuss.calendar.CalendarInfo calInfo) {
		org.openuss.calendar.Calendar cal = calendarDao.load(calInfo.getId());
		AppointmentType appType = this.createAppointmentTypeInDB("standard");
		Random rnd = new Random();
		Long randomLong = rnd.nextLong();
		System.out.println("lomng: " + randomLong);
		Timestamp start = new Timestamp(randomLong);
		Timestamp end = new Timestamp(randomLong + (60*60*60)); // 1 h later
		Timestamp recurEnd = new Timestamp(randomLong + (60*60*60*24*60)); // 2 months later
		SerialAppointment serialAppointment = serialAppointmentDao.create(
				appType,
				"description",
				end,
				"location",
				recurEnd,
				1,
				RecurrenceType.weekly, true, cal,
				start,
				unique("subject"), true);

		cal.addSerialAppointment(serialAppointment);
		
		// make changes persistent for the source calendar
		getCalendarDao().update(cal);

		// make changes persistent for the subscribed calendars
		Set<org.openuss.calendar.Calendar> subscribedCals = cal.getSubscribedCalendars();
		if (!subscribedCals.isEmpty()) {
			for (org.openuss.calendar.Calendar subscribedCal : subscribedCals) {
				getCalendarDao().update(subscribedCal);
			}
		}
	}

	public AppointmentDao getAppointmentDao() {
		return appointmentDao;
	}

	public void setAppointmentDao(AppointmentDao appointmentDao) {
		this.appointmentDao = appointmentDao;
	}

	public org.openuss.calendar.CalendarDao getCalendarDao() {
		return calendarDao;
	}

	public void setCalendarDao(org.openuss.calendar.CalendarDao calendarDao) {
		this.calendarDao = calendarDao;
	}

	public AppointmentTypeDao getAppointmentTypeDao() {
		return appointmentTypeDao;
	}

	public SerialAppointmentDao getSerialAppointmentDao() {
		return serialAppointmentDao;
	}

	public void setSerialAppointmentDao(SerialAppointmentDao serialAppointmentDao) {
		this.serialAppointmentDao = serialAppointmentDao;
	}


}
