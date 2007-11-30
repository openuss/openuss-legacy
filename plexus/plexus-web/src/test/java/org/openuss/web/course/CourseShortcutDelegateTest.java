package org.openuss.web.course;

import org.acegisecurity.context.SecurityContextHolder;
import org.hibernate.SessionFactory;
import org.openuss.TestUtility;
import org.openuss.course.newsletter.CourseNewsletterService;
import org.openuss.desktop.DesktopInfo;
import org.openuss.desktop.DesktopService2;
import org.openuss.discussion.DiscussionService;
import org.openuss.discussion.ForumInfo;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.newsletter.NewsletterInfo;
import org.openuss.security.User;
import org.openuss.security.UserProfile;
import org.openuss.test.foundation.DummyFacesContext;
import org.openuss.test.foundation.DummySecurityContext;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * This class tests the {@link CourseShortCutDelegate}.
 */
public class CourseShortcutDelegateTest extends AbstractTransactionalDataSourceSpringContextTests {
	protected CourseNewsletterService courseNewsletterService;
	protected DiscussionService discussionService;
	protected DesktopService2 desktopService2;
	protected CourseService courseService;
	protected TestUtility testUtility;
	protected SessionFactory sessionFactory;
	protected CourseShortcutDelegate courseShortcutDelegate;
	
	/**
	 * Test various shortcut functions.
	 * 
	 * @throws Exception On internal errors.
	 */
	public void testCourseShortcutDelegate() throws Exception {
		User user = testUtility.createUniqueUserInDB();
		Course course = testUtility.createUniqueCourseInDB();
		long courseId = course.getId();
		long userId = user.getId();
		DesktopInfo desktopInfo = desktopService2.findDesktopByUser(userId);
		CourseInfo courseInfo = courseService.getCourseInfo(courseId);
		NewsletterInfo newsletterInfo; // Each test will reaquire this to force updating
		sessionFactory.getCurrentSession().flush();
		ForumInfo forum = discussionService.getForum(courseInfo);
		assertTrue(forum != null);
		assertTrue(courseShortcutDelegate != null);
		
		// Fake web environment
		courseShortcutDelegate.setFacesContext(new DummyFacesContext());
		SecurityContextHolder.setContext(DummySecurityContext.create(user));
		
		UserProfile up = user.getProfile();
		
		// No automatic abonnement selected
		up.setNewsletterSelected(false);
		up.setDiscussionSelected(false);
		assertTrue(! up.isNewsletterSelected());
		assertTrue(! up.isDiscussionSelected());
		
		newsletterInfo = courseNewsletterService.getNewsletter(courseInfo);
		assertTrue(! desktopService2.isCourseBookmarked(courseId, userId));
		assertTrue(! newsletterInfo.isSubscribed());
		assertTrue(! discussionService.watchesForum(forum));
		
		courseShortcutDelegate.shortcutCourse(desktopService2, desktopInfo, courseInfo, user);
		
		newsletterInfo = courseNewsletterService.getNewsletter(courseInfo);
		assertTrue(desktopService2.isCourseBookmarked(courseId, userId));
		assertTrue(! newsletterInfo.isSubscribed());
		assertTrue(! discussionService.watchesForum(forum));
		
		// Test manual subscriptions
		courseNewsletterService.subscribe(courseInfo, user);
		discussionService.addForumWatch(forum);
		
		newsletterInfo = courseNewsletterService.getNewsletter(courseInfo);
		assertTrue(desktopService2.isCourseBookmarked(courseId, userId));
		assertTrue(newsletterInfo.isSubscribed());
		assertTrue(discussionService.watchesForum(forum));
		
		courseShortcutDelegate.removeCourseShortcut(desktopService2, desktopInfo, courseInfo, user);
		
		newsletterInfo = courseNewsletterService.getNewsletter(courseInfo);
		assertTrue(! desktopService2.isCourseBookmarked(courseId, userId));
		assertTrue(! newsletterInfo.isSubscribed());
		assertTrue(! discussionService.watchesForum(forum));
		
		// Both selected
		up.setNewsletterSelected(true);
		up.setDiscussionSelected(true);
		assertTrue(up.isNewsletterSelected());
		assertTrue(up.isDiscussionSelected());
		
		newsletterInfo = courseNewsletterService.getNewsletter(courseInfo);
		assertTrue(! desktopService2.isCourseBookmarked(courseId, userId));
		assertTrue(! newsletterInfo.isSubscribed());
		assertTrue(! discussionService.watchesForum(forum));
		
		courseShortcutDelegate.shortcutCourse(desktopService2, desktopInfo, courseInfo, user);
		
		newsletterInfo = courseNewsletterService.getNewsletter(courseInfo);
		assertTrue(desktopService2.isCourseBookmarked(courseId, userId));
		assertTrue(newsletterInfo.isSubscribed());
		assertTrue(discussionService.watchesForum(forum));
		
		// Test manual unsubscriptions
		courseNewsletterService.unsubscribe(courseInfo, user);
		discussionService.removeForumWatch(forum);
		
		newsletterInfo = courseNewsletterService.getNewsletter(courseInfo);
		assertTrue(desktopService2.isCourseBookmarked(courseId, userId));
		assertTrue(! newsletterInfo.isSubscribed());
		assertTrue(! discussionService.watchesForum(forum));
		
		courseShortcutDelegate.removeCourseShortcut(desktopService2, desktopInfo, courseInfo, user);
		
		newsletterInfo = courseNewsletterService.getNewsletter(courseInfo);
		assertTrue(! desktopService2.isCourseBookmarked(courseId, userId));
		assertTrue(! newsletterInfo.isSubscribed());
		assertTrue(! discussionService.watchesForum(forum));
		
		// Forum selected, changing in between
		up.setNewsletterSelected(false);
		up.setDiscussionSelected(true);
		assertTrue(! up.isNewsletterSelected());
		assertTrue(up.isDiscussionSelected());
		
		newsletterInfo = courseNewsletterService.getNewsletter(courseInfo);
		assertTrue(! desktopService2.isCourseBookmarked(courseId, userId));
		assertTrue(! newsletterInfo.isSubscribed());
		assertTrue(! discussionService.watchesForum(forum));
		
		courseShortcutDelegate.shortcutCourse(desktopService2, desktopInfo, courseInfo, user);
		
		newsletterInfo = courseNewsletterService.getNewsletter(courseInfo);
		assertTrue(desktopService2.isCourseBookmarked(courseId, userId));
		assertTrue(! newsletterInfo.isSubscribed());
		assertTrue(discussionService.watchesForum(forum));
		
		up.setNewsletterSelected(true);
		up.setDiscussionSelected(false);
		assertTrue(up.isNewsletterSelected());
		assertTrue(! up.isDiscussionSelected());
		
		courseShortcutDelegate.removeCourseShortcut(desktopService2, desktopInfo, courseInfo, user);
		
		newsletterInfo = courseNewsletterService.getNewsletter(courseInfo);
		assertTrue(! desktopService2.isCourseBookmarked(courseId, userId));
		assertTrue(! newsletterInfo.isSubscribed());
		assertTrue(! discussionService.watchesForum(forum));
		
		courseShortcutDelegate.shortcutCourse(desktopService2, desktopInfo, courseInfo, user);
		
		newsletterInfo = courseNewsletterService.getNewsletter(courseInfo);
		assertTrue(desktopService2.isCourseBookmarked(courseId, userId));
		assertTrue(newsletterInfo.isSubscribed());
		assertTrue(! discussionService.watchesForum(forum));
		
		courseShortcutDelegate.removeCourseShortcut(desktopService2, desktopInfo, courseInfo, user);
		
		newsletterInfo = courseNewsletterService.getNewsletter(courseInfo);
		assertTrue(! desktopService2.isCourseBookmarked(courseId, userId));
		assertTrue(! newsletterInfo.isSubscribed());
		assertTrue(! discussionService.watchesForum(forum));
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setDesktopService2(DesktopService2 desktopService2) {
		this.desktopService2 = desktopService2;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}
	
	public void setCourseShortcutDelegate(CourseShortcutDelegate courseShortcutDelegate) {
		this.courseShortcutDelegate = courseShortcutDelegate; 
	}

	protected String[] getConfigLocations() {
		return new String[] { 
				"classpath*:applicationContext.xml", 
				"classpath*:applicationContext-beans.xml",
				"classpath*:applicationContext-lucene.xml", 
				"classpath*:applicationContext-cache.xml",
				"classpath*:applicationContext-messaging.xml", 
				"classpath*:applicationContext-resources.xml",
				"classpath*:applicationContext-aop.xml", 
				"classpath*:testContext.xml", 
				"classpath*:testSecurity.xml",
				"classpath*:testDatasource.xml"};
	}

	public void setNewsletterService(CourseNewsletterService newsletterService) {
		this.courseNewsletterService = newsletterService;
	}

	public void setDiscussionService(DiscussionService discussionService) {
		this.discussionService = discussionService;
	}
}
