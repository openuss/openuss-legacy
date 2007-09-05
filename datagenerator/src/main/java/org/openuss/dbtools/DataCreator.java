package org.openuss.dbtools;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openuss.TestUtility;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.ApplicationInfo;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.CourseTypeInfo;
import org.openuss.lecture.CourseTypeService;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.DepartmentType;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.lecture.PeriodInfo;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.lecture.UniversityType;
import org.openuss.news.NewsCategory;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.news.PublisherType;
import org.openuss.security.Group;
import org.openuss.security.GroupDao;
import org.openuss.security.Roles;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserContact;
import org.openuss.security.UserDao;
import org.openuss.security.UserImpl;
import org.openuss.security.UserInfo;
import org.openuss.security.UserPreferences;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * This class contains methods to put some (non-random) data into the database. It always expects a clean database!
 * 
 * @version 1.1.0-SNAPSHOT
 * 
 * @author Ron Haus
 * 
 */

public class DataCreator {

	private SessionFactory sessionFactory;
	private Session session;

	private UserDao userDao;
	private GroupDao groupDao;
	private UniversityService universityService;
	private DepartmentService departmentService;
	private InstituteService instituteService;
	private CourseTypeService courseTypeService;
	private CourseService courseService;
	private NewsService newsService;
	private SecurityService securityService;

	private static long uniqueId = System.currentTimeMillis();

	// private static Logger logger = Logger.getRootLogger();

	public DataCreator() {
		// Setup
		ApplicationContext appContext = new ClassPathXmlApplicationContext(getConfigLocations());

		sessionFactory = (SessionFactory) appContext.getBean("sessionFactory");
		session = SessionFactoryUtils.getSession(sessionFactory, true);
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));

		userDao = (UserDao) appContext.getBean("userDao");
		groupDao = (GroupDao) appContext.getBean("groupDao");
		universityService = (UniversityService) appContext.getBean("universityService");
		departmentService = (DepartmentService) appContext.getBean("departmentService");
		instituteService = (InstituteService) appContext.getBean("instituteService");
		courseTypeService = (CourseTypeService) appContext.getBean("courseTypeService");
		courseService = (CourseService) appContext.getBean("courseService");
		newsService = (NewsService) appContext.getBean("newsService");
		securityService = (SecurityService) appContext.getBean("securityService");
	}

	protected String[] getConfigLocations() {
		return new String[] { "classpath*:applicationContext.xml", "classpath*:applicationContext-beans.xml",
				"classpath*:applicationContext-lucene.xml", "classpath*:applicationContext-cache.xml",
				"classpath*:applicationContext-messaging.xml", "classpath*:applicationContext-resources.xml",
				"classpath*:applicationContext-aop.xml", "classpath*:testContext.xml", "classpath*:testSecurity.xml",
				"classpath*:applicationContext-localDataSource.xml" };
	}

	private long unique() {
		synchronized (TestUtility.class) {
			return ++uniqueId;
		}
	}

	private String unique(String str) {
		return str + "-" + unique();
	}

	public User createUniqueUsers() {
		// Create a unique User
		UserPreferences userPreferences = UserPreferences.Factory.newInstance();
		userPreferences.setLocale("de");
		userPreferences.setTheme("plexus");
		userPreferences.setTimezone(TimeZone.getDefault().getID());

		UserContact userContact = UserContact.Factory.newInstance();
		userContact.setFirstName("Frank");
		userContact.setLastName(unique("Miller"));
		userContact.setAddress("Leonardo Campus 5");
		userContact.setCity("Münster");
		userContact.setCountry("Germany");
		userContact.setPostcode("48149");

		User user = User.Factory.newInstance();
		user.setUsername(unique("user"));
		user.setPassword("feelfree");
		user.setEmail(unique("openuss") + "@openuss-plexus.com");
		user.setEnabled(true);
		user.setAccountExpired(false);
		user.setCredentialsExpired(false);
		user.setAccountLocked(false);

		user.setPreferences(userPreferences);
		user.setContact(userContact);
		user.setGroups(new ArrayList<Group>());
		
		user = userDao.create(user);
		
		Group roleUser = groupDao.load(Roles.USER_ID);
		securityService.addAuthorityToGroup(user, roleUser);

		session.flush();
		return user;
	}

	public Long createUniqueUniversity(Long userId, UniversityType universityType) {

		// Create a unique University
		UniversityInfo universityInfo = new UniversityInfo();
		universityInfo.setName(unique("University "));
		universityInfo.setShortcut(unique("Uni "));
		universityInfo.setDescription("This is a unique University");
		universityInfo.setOwnerName("Administrator");
		universityInfo.setUniversityType(universityType);
		universityInfo.setEnabled(true);
		universityInfo.setAddress("Leo 18");
		universityInfo.setCity("Münster");
		universityInfo.setCountry("Germany");
		universityInfo.setEmail("openuss@uni-muenster.de");
		universityInfo.setLocale("de");
		universityInfo.setPostcode("48149");
		universityInfo.setTelefax("0251-telefax");
		universityInfo.setTelephone("0251-telephone");
		universityInfo.setTheme("plexus");
		universityInfo.setWebsite("www.openuss.de");

		Long universityId = universityService.createUniversity(universityInfo, userId);

		return universityId;
	}

	public Long createUniquePeriod(Date startdate, Date enddate, Long universityId) {
		
		//Create Period
		PeriodInfo periodInfo = new PeriodInfo();
		periodInfo.setUniversityId(universityId);
		periodInfo.setName(unique("Period "));
		periodInfo.setDescription("A new Period for this Organisation");
		periodInfo.setStartdate(startdate);
		periodInfo.setEnddate(enddate);
		
		Long periodId = universityService.createPeriod(periodInfo);
		
		return periodId;
	}
	
	public Long createUniqueDepartment(Long userId, Long universityId, DepartmentType departmentType) {

		// Create a unique Department
		DepartmentInfo departmentInfo = new DepartmentInfo();
		departmentInfo.setName(unique("Department "));
		departmentInfo.setShortcut(unique("Dep "));
		departmentInfo.setDescription("A unique Department");
		departmentInfo.setOwnerName("Administrator");
		departmentInfo.setEnabled(true);
		departmentInfo.setAddress("Leo 18");
		departmentInfo.setCity("Münster");
		departmentInfo.setCountry("Germany");
		departmentInfo.setEmail("openuss@uni-muenster.de");
		departmentInfo.setLocale("de");
		departmentInfo.setPostcode("48149");
		departmentInfo.setTelefax("0251-telefax");
		departmentInfo.setTelephone("0251-telephone");
		departmentInfo.setTheme("plexus");
		departmentInfo.setWebsite("www.openuss.de");
		departmentInfo.setDepartmentType(departmentType);
		departmentInfo.setUniversityId(universityId);
		
		Long departmentId = departmentService.create(departmentInfo, userId);

		return departmentId;
	}
	
	public Long createUniqueInstitute(Long userId, Long departmentId) {

		// Create a unique Institute
		InstituteInfo instituteInfo = new InstituteInfo();
		instituteInfo.setName(unique("Institute "));
		instituteInfo.setShortcut(unique("Inst "));
		instituteInfo.setDescription("A unique Insitute");
		instituteInfo.setOwnerName("Administrator");
		instituteInfo.setEnabled(true);
		instituteInfo.setAddress("Leo 18");
		instituteInfo.setCity("Münster");
		instituteInfo.setCountry("Germany");
		instituteInfo.setEmail("openuss@uni-muenster.de");
		instituteInfo.setLocale("de");
		instituteInfo.setPostcode("48149");
		instituteInfo.setTelefax("0251-telefax");
		instituteInfo.setTelephone("0251-telephone");
		instituteInfo.setTheme("plexus");
		instituteInfo.setWebsite("www.openuss.de");
		instituteInfo.setDepartmentId(departmentId);

		Long instituteId = instituteService.create(instituteInfo, userId);

		return instituteId;
	}
	
	public Long applyInstituteAtDepartment(DepartmentInfo departmentInfo, InstituteInfo instituteInfo, UserInfo userInfo) {
		
		//Create Application
		ApplicationInfo applicationInfo = new ApplicationInfo();
		applicationInfo.setDepartmentInfo(departmentInfo);
		applicationInfo.setInstituteInfo(instituteInfo);
		applicationInfo.setApplyingUserInfo(userInfo);
		
		Long applicationId = instituteService.applyAtDepartment(applicationInfo);

		return applicationId;
	}
	
	public void acceptApplication(Long applicationId, Long userId) {
		
		departmentService.acceptApplication(applicationId, userId);
	}
	


	public Long createUniqueCourseType(Long instituteId) {
		
		// Create a unique CourseType
		CourseTypeInfo courseTypeInfo = new CourseTypeInfo();
		courseTypeInfo.setName(unique("CourseType "));
		courseTypeInfo.setShortcut(unique("CT "));
		courseTypeInfo.setDescription("A unique CourseType");
		courseTypeInfo.setInstituteId(instituteId);

		Long courseTypeId = courseTypeService.create(courseTypeInfo);

		return courseTypeId;
	}

	public Long createUniqueCourse(Long periodId, Long courseTypeId) {

		// Create a unique CourseType
		CourseInfo courseInfo = new CourseInfo();
		courseInfo.setShortcut(unique("Course "));
		courseInfo.setDescription("A unique Course");
		courseInfo.setAccessType(AccessType.OPEN);
		courseInfo.setDocuments(false);
		courseInfo.setDiscussion(false);
		courseInfo.setNewsletter(false);
		courseInfo.setChat(false);
		courseInfo.setWiki(false);
		courseInfo.setFreestylelearning(false);
		courseInfo.setBraincontest(false);
		courseInfo.setCourseTypeId(courseTypeId);
		courseInfo.setPeriodId(periodId);
		
		Long courseId = courseService.create(courseInfo);

		return courseId;
	}
	
	public void createNews(String title, String text) {
		NewsItemInfo item = new NewsItemInfo();
		item.setAuthor("Administrator");
		item.setCategory(NewsCategory.GLOBAL);
		item.setExpired(false);
		item.setExpireDate(new GregorianCalendar(2008, 7, 1).getTime());
		item.setPublishDate(new Date());
		item.setPublisherIdentifier(-10L);
		item.setPublisherName("Adminsitrator");
		item.setPublisherType(PublisherType.COURSE);
		item.setReleased(true);
		item.setText(text);
		item.setTitle(title);
		
		newsService.saveNewsItem(item);
	}
	
	public List findPeriodsByUniversity(Long universityId) {
		return universityService.findPeriodsByUniversity(universityId);
	}
	public InstituteInfo findInstitute(Long instituteId) {
		return instituteService.findInstitute(instituteId);
	}
	public DepartmentInfo findDepartment(Long departmentId) {
		return departmentService.findDepartment(departmentId);
	}
	public List findDepartmentsByUniversityAndType(Long universityId, DepartmentType departmentType) {
		return departmentService.findDepartmentsByUniversityAndType(universityId, departmentType);
	}
	public UserInfo findUser(Long userId) {
		User user = securityService.getUser(userId);
		return userDao.toUserInfo(user);
	}
	
	public User createAdminSecureContext() {
		return createSecureContext(Roles.ADMINISTRATOR_ID);
	}

	private User createSecureContext(Long roleId) {
		User user = User.Factory.newInstance();
		user.setUsername(unique("username"));
		user.setPassword("feelfree");
		user.setEmail("email@email.com");
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
	
	public void destroySecureContext() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}
}