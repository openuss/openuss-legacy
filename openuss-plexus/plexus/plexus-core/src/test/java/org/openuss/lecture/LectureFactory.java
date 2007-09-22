package org.openuss.lecture;


/**
 * Factory to create lecture structures
 * @author Ingo Dueppe
 */
public class LectureFactory {
	
	public static Institute createInstitute() {
		Institute institute = Institute.Factory.newInstance();
		institute.setName(unique("test-institute"));
		institute.setOwnername("institute owner");
		institute.setShortcut(unique("shortcut"));
		institute.setEmail("email@email.com");
		institute.setAddress("Leonardo-Campus 3");
		institute.setPostcode("48149");
		institute.setCity("Münster");
		institute.setLocale("de");
		institute.setTheme("plexus");
		institute.setWebsite("www.openuss.org");
		return institute;
	}
	
	public static Period createPeriod() {
		Period period = Period.Factory.newInstance();
		period.setName("test-period");
		period.setDescription("description");
		return period;
	}
	
	public static CourseType createCourseType() {
		CourseType courseType = CourseType.Factory.newInstance();
		courseType.setName(unique("test-courseType"));
		courseType.setShortcut(unique("test-shortcut"));
		courseType.setDescription("description");
		return courseType;
	}
	
	public static Course createCourse() {
		Course course = Course.Factory.newInstance();
		course.setShortcut(unique("course-shortcut"));
		course.setAccessType(AccessType.CLOSED);
		return course;
	}
	
	
	private static String unique(String name) {
		return name+" - "+unique();
	}
	
	static long unique = System.currentTimeMillis()/2;
	
	private static synchronized long unique() {
		return unique++;
	}
	

}
