package org.openuss;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseDao;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.CourseTypeDao;
import org.openuss.lecture.Department;
import org.openuss.lecture.DepartmentDao;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteDao;
import org.openuss.lecture.Period;
import org.openuss.lecture.PeriodDao;
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
	
	private DepartmentDao departmentDao;
	
	private CourseTypeDao courseTypeDao;
	
	private CourseDao courseDao;
	
	private PeriodDao periodDao;

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
		user.setEmail(unique("openuss")+"@e-learning.uni-muenster.de");
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
		// Create a unique University
		University university = University.Factory.newInstance();
		university.setName(unique("University"));
		university.setShortcut(unique("Uni"));
		university.setDescription("A unique University");
		university.setMembership(this.createUniqueMembershipInDB());
		
		universityDao.create(university);
		
		return university;
	}
	
	public Department createUniqueDepartmentInDB() {
		// Create a unique University
		University university = this.createUniqueUniversityInDB();
		
		// Create a unique Department
		Department department =  Department.Factory.newInstance();
		department.setName(unique("Department"));
		department.setShortcut(unique("Dep"));
		department.setDescription("A unique Department");
		department.setMembership(this.createUniqueMembershipInDB());
		
		university.getDepartments().add(department);
		department.setUniversity(university);
		
		departmentDao.create(department);
		
		return department;		
	}
	
	public CourseType createUniqueCourseTypeInDB() {

		// Create a unique CourseType
		CourseType courseType =  CourseType.Factory.newInstance();
		courseType.setName(unique("CourseType"));
		courseType.setShortcut(unique("CT"));
		courseType.setDescription("A unique CourseType");
		courseType.setInstitute(this.createdDefaultInstituteWithStoredUser());
		
		courseTypeDao.create(courseType);
		
		return courseType;		
	}
	
	public Course createUniqueCourseInDB() {
		
		// Create a unique CourseType
		Course course =  Course.Factory.newInstance();
		course.setShortcut(unique("cou"));
		course.setDescription("A unique Course");
		course.setInstitute(this.createdDefaultInstituteWithStoredUser());
		course.setAccessType(AccessType.OPEN);
		course.setDocuments(false);
		course.setDiscussion(false);
		course.setNewsletter(false);
		course.setChat(false);
		course.setWiki(false);
		course.setFreestylelearning(false);
		course.setBraincontest(false);
		course.setInstitute(this.createdDefaultInstituteWithStoredUser());
		course.setCourseType(this.createUniqueCourseTypeInDB());
		
		courseDao.create(course);
		
		return course;		
	}
	
	public Period createUniquePeriodInDB () {
		
		//Create Startdate
		Calendar cal = new GregorianCalendar();
		cal.set(2007, 3, 1);
		Date startdate = new Date(cal.getTimeInMillis());
		
		//Create Enddate
		cal = new GregorianCalendar();
		cal.set(2008, 8, 31);
		Date enddate = new Date(cal.getTimeInMillis());
		
		//Create a unique Period
		Period period = Period.Factory.newInstance();
		period.setName(unique("Period"));
		period.setUniversity(this.createUniqueUniversityInDB());
		period.setDescription("A unique Period");
		period.setCourses(new ArrayList<Course>());
		period.setStartdate(startdate);
		period.setEnddate(enddate);
		
		period.getUniversity().getPeriods().add(period);
		
		periodDao.create(period);
		
		return period;
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

	public PeriodDao getPeriodDao() {
		return periodDao;
	}
	
	
}
