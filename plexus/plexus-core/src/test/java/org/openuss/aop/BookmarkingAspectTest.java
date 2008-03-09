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
	
	private UniversityService universityService;
	
	private TestUtility testUtility;

	public void testUniversityBookmarkingAspect() {

		// Create a User
		User user = testUtility.createUniqueUserInDB();
		sessionFactory.getCurrentSession().flush();
		DesktopInfo desktopInfo = null;
		try {
			desktopInfo = desktopService2.findDesktopByUser(user.getId());
		} catch (DesktopException e) {
			logger.error(e);
			fail();
		}
		assertTrue(desktopInfo.getUniversityInfos().isEmpty());

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

		testUtility.createAdminSecureContext();
		universityService.removeCompleteUniversityTree(universityId);

		try {
			desktopInfo = desktopService2.findDesktopByUser(user.getId());
		} catch (DesktopException e) {
			e.printStackTrace();
		}
		assertTrue(desktopInfo.getUniversityInfos().isEmpty());

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
			logger.error(e);
			fail();
		}

		// Bookmark a Course
		try {
			desktopService2.linkCourse(desktopInfo.getId(), course.getId());
			desktopInfo = desktopService2.findDesktopByUser(user.getId());
			assertTrue(desktopInfo.getCourseInfos().size() == 1);
		} catch (DesktopException e) {
			logger.error(e);
			fail();
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
			logger.error(e);
			fail();
		}
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

	protected String[] getConfigLocations() {
		return new String[] { 
				"classpath*:applicationContext.xml", 
				"classpath*:applicationContext-beans.xml",
				"classpath*:applicationContext-lucene.xml", 
				"classpath*:applicationContext-cache.xml",
				"classpath*:applicationContext-messaging.xml", 
				"classpath*:applicationContext-resources.xml",
				"classpath*:applicationContext-aop.xml", 
				"classpath*:applicationContext-events.xml",
				"classpath*:testContext.xml", 
				"classpath*:testSecurity.xml",
				"classpath*:testDataSource.xml" };
	}



	public void setUniversityService(UniversityService universityService) {
		this.universityService = universityService;
	}
}
