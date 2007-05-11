package org.openuss.dbtools;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContextImpl;
import org.acegisecurity.providers.TestingAuthenticationToken;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopService;
import org.openuss.discussion.DiscussionService;
import org.openuss.discussion.ForumInfo;
import org.openuss.discussion.PostInfo;
import org.openuss.discussion.TopicInfo;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.FacultyDao;
import org.openuss.lecture.LectureService;
import org.openuss.lecture.Period;
import org.openuss.lecture.Subject;
import org.openuss.registration.RegistrationException;
import org.openuss.registration.RegistrationService;
import org.openuss.security.User;
import org.openuss.security.UserContact;
import org.openuss.security.UserImpl;
import org.openuss.security.UserPreferences;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * This class contains methods to put some (non-random) data into the database.
 * It always expects a clean database!
 * 
 * @version	1.0.0-SNAPSHOT
 * 
 * @author Ron Haus
 * 
 */

public class DataCreator {

	private RegistrationService registrationService;

	private LectureService lectureService;

	private DesktopService desktopService;

	private DiscussionService discussionService;

	private FacultyDao facultyDao;

	SessionFactory sessionFactory;

	private static Logger logger = Logger.getRootLogger();

	public DataCreator() {
		// Setup
		ApplicationContext appContext = new ClassPathXmlApplicationContext(getConfigLocations());

		sessionFactory = (SessionFactory) appContext.getBean("sessionFactory");
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));

		registrationService = (RegistrationService) appContext.getBean("registrationService");
		lectureService = (LectureService) appContext.getBean("lectureService");
		desktopService = (DesktopService) appContext.getBean("desktopService");
		discussionService = (DiscussionService) appContext.getBean("discussionService");

		facultyDao = (FacultyDao) appContext.getBean("facultyDao");
	}

	private String[] getConfigLocations() {
		return new String[] { "classpath*:applicationContext.xml", "classpath*:applicationContext-localDataSource.xml",
				"classpath*:applicationContext-beans.xml", "classpath*:applicationContext-tests.xml",
				"classpath*:applicationContext-cache.xml", "classpath*:beanRefFactory", "classpath*:testSecurity.xml" };
	}

	private static void createSecureContext(String roleName) {
		TestingAuthenticationToken authentication = new TestingAuthenticationToken("principal", "credentials",
				new GrantedAuthority[] { new GrantedAuthorityImpl(roleName) });
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private static void destroySecureContext() {
		SecurityContextHolder.setContext(new SecurityContextImpl());
	}

	/**
	 * 
	 * Creates (non-random) users, faculties, subject, periods and enrollments
	 * in the database for testing purposes. It will create max 1 faculty for
	 * each user.
	 * 
	 * @param userNumber -
	 *            Total number of users that will be created.
	 * @param facultyNumber -
	 *            Total number of faculties that will be created for the
	 *            first $facultyNumber of users. Therefore, needs to be smaller
	 *            that &userNumber.
	 * @param periodNumber -
	 *            Total number of tperiods that will be created for each
	 *            faculty.
	 * @param subjectNumber -
	 *            Total number of subjects that will be created for each
	 *            faculty.
	 * @param subjectPeriodNumber -
	 *            Total number of subjects that will be put into each period.
	 * @throws IllegalArgumentException
	 *             if the number of faculties is greater than the number of
	 *             users
	 */
	public void createBasicData(int userNumber, int facultyNumber, int periodNumber, int subjectNumber,
			int subjectPeriodNumber) throws Exception {

		if (facultyNumber > userNumber) {
			throw new IllegalArgumentException("You need to create more users than faculties.");
		}

		createSecureContext("ROLE_ADMIN");

		/*
		 * Create Administrator Account - needs to be the first user!
		 */
		User admin = User.Factory.newInstance();
		admin.setUsername("admin");
		admin.setPassword("masterkey");
		admin.setEmail("openuss@e-learning.uni-muenster.de");
		admin.setEnabled(true);
		admin.setAccountExpired(false);
		admin.setCredentialsExpired(false);
		admin.setAccountLocked(false);
		// create preferences
		UserPreferences adminPreferences = UserPreferences.Factory.newInstance();
		adminPreferences.setLocale("de");
		adminPreferences.setTheme("plexus");
		adminPreferences.setTimezone(TimeZone.getDefault().getID());
		admin.setPreferences(adminPreferences);
		// create person
		UserContact adminContact = UserContact.Factory.newInstance();
		adminContact.setFirstName("Ingo");
		adminContact.setLastName("Düppe");
		adminContact.setAddress("Leonardo Campus 5");
		adminContact.setCity("Münster");
		adminContact.setCountry("Germany");
		adminContact.setPostcode("48149");
		admin.setContact(adminContact);

		try {

			registrationService.registrateUser(admin, false);
			String adminCode = registrationService.generateActivationCode(admin);
			registrationService.activateUserByCode(adminCode);

			logger.info("Admin \"" + admin.getUsername() + "\" has been registered and activated.");
		} catch (RegistrationException e) {
			e.printStackTrace();
		}

		/*
		 * Create User Accounts
		 */
		User user = null;
		UserPreferences preferences = null;
		UserContact contact = null;
		String code = null;
		int j = 1;
		Period period = null;
		Subject subject = null;
		Desktop desktop = null;
		Enrollment enrollment = null;
		for (int i = 1; i < (userNumber + 1); i++) {
			user = User.Factory.newInstance();
			user.setUsername("student" + i);
			user.setPassword("feelfree");
			user.setEmail("student" + i + "@email.com");
			user.setEnabled(true);
			user.setAccountExpired(false);
			user.setCredentialsExpired(false);
			user.setAccountLocked(false);
			// create preferences
			preferences = UserPreferences.Factory.newInstance();
			preferences.setLocale("de");
			preferences.setTheme("plexus");
			preferences.setTimezone(TimeZone.getDefault().getID());
			user.setPreferences(preferences);
			// create person
			contact = UserContact.Factory.newInstance();
			contact.setFirstName("Student" + i);
			contact.setLastName("Stallone");
			contact.setAddress("Leonardo Campus 5");
			contact.setCity("Münster");
			contact.setCountry("Germany");
			contact.setPostcode("48149");
			user.setContact(contact);

			try {
				registrationService.registrateUser(user, false);
				code = registrationService.generateActivationCode(user);
				registrationService.activateUserByCode(code);
				logger.info("User \"" + user.getUsername() + "\" has been registered and activated.");
			} catch (Exception e) {
				e.printStackTrace();
			}

			/*
			 * Create faculties inclusive periods and subjects
			 */
			if (j < (facultyNumber + 1)) {
				Faculty faculty = Faculty.Factory.newInstance();
				faculty.setName("Institution " + j);
				faculty.setShortcut("inst" + j);
				faculty.setAddress("Leonardo Campus 5");
				faculty.setCity("Münster");
				faculty.setDescription("A wonderfull faculty");
				faculty.setEmail("inst" + j + "@email.com");
				faculty.setLocale("de");
				faculty.setOwner(user);
				faculty.setOwnername(user.getFirstName() + " " + user.getLastName());
				faculty.setPostcode("48149");
				faculty.setTheme("plexus");

				// periods
				for (int m = 1; m < (periodNumber + 1); m++) {
					period = Period.Factory.newInstance();
					period.setName("S" + m);
					period.setDescription("Semester " + m);
					period.setFaculty(faculty);
					faculty.getPeriods().add(period);
					if (m == 1) {
						faculty.setActivePeriod(period);
					}
				}

				// subjects
				for (int n = 1; n < (subjectNumber + 1); n++) {
					subject = Subject.Factory.newInstance();
					subject.setName("Subject " + j + "." + n);
					subject.setShortcut("S" + j + "." + n);
					subject.setDescription("A wonderfull subject");
					subject.setFaculty(faculty);
					faculty.getSubjects().add(subject);
				}

				try {
					// save faculty
					lectureService.createFaculty(faculty);
					logger.info("Faculty \"" + faculty.getName() + "\" for Owner \"" + faculty.getOwnername()
							+ "\" with " + subjectNumber + " subject(s) and " + periodNumber
							+ " period(s) has been created.");

					// save desktop
					desktop = desktopService.createDesktop(user);
					desktopService.linkFaculty(desktop, faculty);

					// create enrollment between period and subject, open it and
					// put it on the desktop
					List<Period> l1 = faculty.getPeriods();
					List<Subject> l2 = faculty.getSubjects();
					int x = 0;
					int y = 0;
					for (x = 0; x < l1.size(); x++) {
						for (y = 0; y < l2.size(); y++) {
							// max n subjects a period
							if (y >= subjectPeriodNumber) {
								break;
							}
							enrollment = lectureService.createEnrollment(l2.get(y).getId(), l1.get(x).getId());
							enrollment.setAccessType(AccessType.OPEN);
							enrollment.setDescription("A wonderfull enrollment");
							lectureService.persist(enrollment);
							desktopService.linkEnrollment(desktop, enrollment);

						}
					}
					logger
							.info((x * y) + " enrollment(s) for faculty \"" + faculty.getName()
									+ "\" have been created.");

				} catch (Exception e) {
					e.printStackTrace();
				}

				j++;
			}

		}

		destroySecureContext();
	}

	/**
	 * 
	 * Creates (non-random) topics and posts. Everything created by the owner of the faculty.
	 * 
	 * @param topicNumber -
	 *            Total number of topics that will be created for each
	 *            forum.
	 * @param postNumber -
	 *            Total number of posts that will be put into each topic.
	 */
	public void createForumData(int topicNumber, int postNumber) {

		logger.info("Creating Forum data...");

		createSecureContext("ROLE_ADMIN");

		// Load Faculties
		List faculties = facultyDao.loadAllEnabled();
		logger.info("Number of Faculties found: " + faculties.size());

		// Create Forums, Topics
		ForumInfo forumInfo = null;
		ForumInfo loadedForum = null;
		Faculty faculty = null;
		List<Enrollment> enrollments = null;
		User owner = null;
		PostInfo post = null;
		TopicInfo topic = null;

		Iterator itFac = faculties.iterator();
		for (int i = 0; i < faculties.size(); i++) {
			faculty = (Faculty) itFac.next();
			enrollments = faculty.getEnrollments();

			logger.info("Creating one Forum for each of the " + enrollments.size() + " Enrollment(s) of the Faculty "
					+ faculty.getName() + "...");
			for (Enrollment enrollment : enrollments) {
				forumInfo = new ForumInfo();
				forumInfo.setDomainIdentifier(enrollment.getId());
				forumInfo.setReadOnly(false);
				discussionService.addForum(forumInfo);
			}

			logger.info("Creating " + topicNumber + " Topics with " + postNumber + " Posts for each Forum...");
			owner = faculty.getOwner();
			// Create secureContext for the Owner
			final UsernamePasswordAuthenticationToken authentication;
			authentication = new UsernamePasswordAuthenticationToken(owner, "[Protected]", ((UserImpl) owner)
					.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);

			for (Enrollment enrollment : enrollments) {
				logger.info("Starting with the Forum of Enrollment "+enrollment.getName()+"...");
				loadedForum = discussionService.getForum(enrollment);
				for (int j = 1; j < (topicNumber + 1); j++) {
					post = new PostInfo();
					post.setCreated(new Date(System.currentTimeMillis()));
					post.setLastModification(post.getCreated());
					post.setSubmitter(owner.getUsername());
					post.setSubmitterId(owner.getId());
					post.setText("Feel free to add some postings to this topic!");
					post.setTitle("This is topic number " + j);
					post.setIp("192.168.0.1");

					discussionService.createTopic(post, loadedForum);
				}

				List topics = discussionService.getTopics(loadedForum);
				Iterator itTop = topics.iterator();
				for (int n = 0; n < topics.size(); n++) {
					topic = (TopicInfo) itTop.next();

					for (int m = 0; m < postNumber; m++) {
						post = new PostInfo();
						post.setCreated(new Date(System.currentTimeMillis()));
						post.setLastModification(post.getCreated());
						post.setSubmitter(owner.getUsername());
						post.setSubmitterId(owner.getId());
						post.setText("This is another posting!");
						post.setTitle("This is posting number " + (m+1));
						post.setIp("192.168.0.1");

						discussionService.addPost(post, topic);
					}
				}
			}
		}

		destroySecureContext();

	}
}
