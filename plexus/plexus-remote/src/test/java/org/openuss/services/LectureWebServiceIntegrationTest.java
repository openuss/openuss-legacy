package org.openuss.services;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.openuss.services.model.CourseBean;
import org.openuss.services.model.UserBean;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class LectureWebServiceIntegrationTest extends AbstractDependencyInjectionSpringContextTests {
	private static final long OPENUSS_DEFAULT_INSTITUTE = 15L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LectureWebServiceIntegrationTest.class);
	
	private LectureWebService lectureClient;
	
	public void testCreateUser() throws LectureLogicException {
		UserBean userBean = createDefaultUser();
		
		assertNull(lectureClient.findUser(userBean.getUsername()));
		
		Long userId = lectureClient.createUser(userBean);
		assertNotNull(userId);
		assertEquals(userId, lectureClient.findUser(userBean.getUsername()));
		try {
			lectureClient.createUser(userBean);
			fail();
		} catch (LectureLogicException ex) {
			logger.info(ex.getMessage());
		}
	}
	
	public void testUpdateUser() throws LectureLogicException {
		UserBean user = createDefaultUser();
		user.setId(lectureClient.createUser(user));
		
		user.setUsername(unique("username"));
		lectureClient.updateUser(user);
		
		assertEquals(user.getId(), lectureClient.findUser(user.getUsername()));
		
	}
	
	public void testCreateCourse() throws LectureLogicException {
		CourseBean course = new CourseBean();
		
		course.setName("Name of the Course");
		course.setShortcut("Shortcut of the course");
		course.setDescription("Beschreibung");
		course.setInstituteId(OPENUSS_DEFAULT_INSTITUTE);
		course.setStartDate(DateUtils.addMonths(new Date(), 2));
		course.setEndDate(DateUtils.addMonths(new Date(), 5));
		
		Long courseId = lectureClient.createCourse(course);
		assertNotNull(courseId);
	}

	private UserBean createDefaultUser() {
		UserBean userBean = new UserBean();
		userBean.setUsername(unique("username"));
		userBean.setPassword("password");
		userBean.setEmail(unique("research.assistant@ercis.uni-muenster.de"));
		userBean.setFirstName("Assistant");
		userBean.setLastName("Research");
		userBean.setAddress("Leonardo Campus 3");
		userBean.setPostCode("48149");
		userBean.setCity("Muenster");
		userBean.setTitle("Dr.");
		userBean.setLocale("de");
		return userBean;
	}
	
	protected String[] getConfigLocations() {
		setAutowireMode(AUTOWIRE_BY_NAME);
		return new String[] { 
			"classpath*:applicationContext.xml", 
//			"classpath*:applicationContext-beans.xml",
//			"classpath*:applicationContext-lucene.xml",
			"classpath*:applicationContext-cache.xml", 
//			"classpath*:applicationContext-messaging.xml",
//			"classpath*:applicationContext-resources.xml",
//			"classpath*:applicationContext-aop.xml",
			"classpath*:applicationContext-webservice.xml",
			"classpath*:testIntegrated-webservice.xml",
//			"classpath*:testContext.xml", 
			"classpath*:testSecurity.xml",
			"classpath*:testDisableSecurity.xml", 
			"classpath*:testDataSource.xml"};
	}
	
	
	public LectureWebService getLectureClient() {
		return lectureClient;
	}

	public void setLectureClient(LectureWebService lectureClient) {
		this.lectureClient = lectureClient;
	}
	
	private static volatile long uniqueId = System.currentTimeMillis();
	
	public static synchronized long unique() {
		return ++uniqueId;
	}

	public String unique(String str) {
		return str + "-" + unique();
	}

}
