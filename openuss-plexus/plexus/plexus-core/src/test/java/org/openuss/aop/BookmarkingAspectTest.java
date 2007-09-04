package org.openuss.aop;

import org.hibernate.SessionFactory;
import org.openuss.TestUtility;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.desktop.DesktopService2;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.lecture.UniversityType;
import org.openuss.security.User;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public class BookmarkingAspectTest extends AbstractTransactionalDataSourceSpringContextTests {

	protected SessionFactory sessionFactory;
	private DesktopService2 desktopService2;
	private TestUtility testUtility;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public DesktopService2 getDesktopService2() {
		return desktopService2;
	}

	public void setDesktopService2(DesktopService2 desktopService2) {
		this.desktopService2 = desktopService2;
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	protected String[] getConfigLocations() {
		return new String[] { "classpath*:applicationContext.xml", "classpath*:applicationContext-beans.xml",
				"classpath*:applicationContext-lucene.xml", "classpath*:applicationContext-cache.xml",
				"classpath*:applicationContext-messaging.xml", "classpath*:applicationContext-resources.xml",
				"classpath*:applicationContext-aop.xml", "classpath*:testContext.xml", "classpath*:testSecurity.xml",
				"classpath*:testDataSource.xml" };
	}

	public void testUniversityBookmarkingAspect() {

		// Create a User
		User user = testUtility.createUniqueUserInDB();
		sessionFactory.getCurrentSession().flush();
		DesktopInfo desktopInfo = null;
		try {
			desktopInfo = desktopService2.findDesktopByUser(user.getId());
		} catch (DesktopException e) {
			e.printStackTrace();
		}
		assertTrue(desktopInfo.getUniversityInfos().isEmpty());

		UniversityService universityService = (UniversityService) this.applicationContext.getBean("universityService");
		UniversityInfo universityInfo = new UniversityInfo();
		universityInfo.setName(testUtility.unique("testUniversity"));
		universityInfo.setShortcut(testUtility.unique("testU"));
		universityInfo.setOwnerName("Administrator");
		universityInfo.setEnabled(true);
		universityInfo.setUniversityType(UniversityType.UNIVERSITY);
		testUtility.createAdminSecureContext();
		Long universityId = universityService.createUniversity(universityInfo, user.getId());
		sessionFactory.getCurrentSession().flush();
		testUtility.destroySecureContext();

		try {
			desktopInfo = desktopService2.findDesktopByUser(user.getId());
		} catch (DesktopException e) {
			e.printStackTrace();
		}
		assertTrue(!desktopInfo.getUniversityInfos().isEmpty());

		universityService.removeUniversity(universityId);

		try {
			desktopInfo = desktopService2.findDesktopByUser(user.getId());
		} catch (DesktopException e) {
			e.printStackTrace();
		}
		assertTrue(desktopInfo.getUniversityInfos().isEmpty());

	}

	public void departmentBookmarkingAspectTest() {

	}

	public void instituteBookmarkingAspectTest() {

	}

	public void courseTypeBookmarkingAspectTest() {

	}

	public void testCourseBookmarkingAspect() {

		// Create a User and CourseType
		User user = testUtility.createUniqueUserInDB();
		Course course = testUtility.createUniqueCourseInDB();
		sessionFactory.getCurrentSession().flush();

		DesktopInfo desktopInfo = null;
		try {
			desktopInfo = desktopService2.findDesktopByUser(user.getId());
			assertTrue(desktopInfo.getCourseInfos().isEmpty());
		} catch (DesktopException e) {
			e.printStackTrace();
		}

		// Bookmark a Course
		try {
			this.getDesktopService2().linkCourse(desktopInfo.getId(), course.getId());
			desktopInfo = desktopService2.findDesktopByUser(user.getId());
			assertTrue(desktopInfo.getCourseInfos().size() == 1);
		} catch (DesktopException e) {
			e.printStackTrace();
		}

		// Remove Course
		CourseService courseService = (CourseService) this.applicationContext.getBean("courseService");
		courseService.removeCourse(course.getId());

		sessionFactory.getCurrentSession().flush();
		
		// Test
		try {
			desktopInfo = desktopService2.findDesktopByUser(user.getId());
			assertTrue(desktopInfo.getCourseInfos().isEmpty());
		} catch (DesktopException e) {
			e.printStackTrace();
		}
	}
}
