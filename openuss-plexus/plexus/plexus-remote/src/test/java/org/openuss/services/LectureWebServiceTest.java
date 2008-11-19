package org.openuss.services;

import java.util.Date;

import org.openuss.services.model.CourseBean;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class LectureWebServiceTest extends AbstractDependencyInjectionSpringContextTests {
	
	private LectureWebService lectureClient;
	
	public void testGetCourse() throws LectureLogicException  {
		CourseBean course = lectureClient.getCourse(1L);

		assertNotNull(course);
		assertEquals("name", course.getName());
		assertEquals("shortcut", course.getShortcut());
		
		try {
			lectureClient.getCourse(-1000L);
			fail();
		} catch (LectureLogicException ex) {
			assertNotNull(ex.getMessage());
		}
	}
	
	public void testCreateCourse() throws LectureLogicException {
		CourseBean course = new CourseBean();
		course.setShortcut("Shortcut");
		course.setStartDate(new Date());
		course.setEndDate(new Date());
		
		long courseId = lectureClient.createCourse(course);
		assertTrue(1 < courseId);
		course = lectureClient.getCourse(courseId);
		assertNotNull(course);
	}
		
	
	protected String[] getConfigLocations() {
		setAutowireMode(AUTOWIRE_BY_NAME);
		return new String[] { "classpath*:test-webservice.xml"};
	}

	public LectureWebService getLectureClient() {
		return lectureClient;
	}

	public void setLectureClient(LectureWebService lectureClient) {
		this.lectureClient = lectureClient;
	}

}
