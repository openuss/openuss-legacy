package org.openuss.lecture;


/**
 * Factory to create lecture structures
 * @author Ingo Dueppe
 */
public class LectureFactory {
	
	public static Institute createInstitute() {
		Institute institute = new InstituteImpl();
		institute.setName(unique("test-institute"));
		institute.setOwnerName("institute owner");
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
		Period period = new PeriodImpl();
		period.setName("test-period");
		period.setDescription("description");
		return period;
	}
	
	public static CourseType createCourseType() {
		CourseType courseType = new CourseTypeImpl();
		courseType.setName(unique("test-courseType"));
		courseType.setShortcut(unique("test-shortcut"));
		courseType.setDescription("description");
		return courseType;
	}
	
	public static Course createCourse() {
		Course course = new CourseImpl();
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
