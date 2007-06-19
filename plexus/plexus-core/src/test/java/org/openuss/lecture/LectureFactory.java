package org.openuss.lecture;


/**
 * Factory to create lecture structures
 * @author Ingo Dueppe
 */
public class LectureFactory {
	
	public static Faculty createFaculty() {
		Faculty faculty = Faculty.Factory.newInstance();
		faculty.setName(unique("test-faculty"));
		faculty.setOwnername("faculty owner");
		faculty.setShortcut(unique("shortcut"));
		faculty.setEmail("email@email.com");
		faculty.setAddress("Leonardo-Campus 3");
		faculty.setPostcode("48149");
		faculty.setCity("M�nster");
		faculty.setLocale("de");
		faculty.setTheme("plexus");
		faculty.setWebsite("www.openuss.org");
		return faculty;
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
