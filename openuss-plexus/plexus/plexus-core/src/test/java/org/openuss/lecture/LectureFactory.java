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
		faculty.setCity("Münster");
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
	
	public static Subject createSubject() {
		Subject subject = Subject.Factory.newInstance();
		subject.setName(unique("test-subject"));
		subject.setShortcut(unique("test-shortcut"));
		subject.setDescription("description");
		return subject;
	}
	
	public static Enrollment createEnrollment() {
		Enrollment enrollment = Enrollment.Factory.newInstance();
		enrollment.setShortcut(unique("enrollment-shortcut"));
		enrollment.setAccessType(AccessType.CLOSED);
		return enrollment;
	}
	
	
	private static String unique(String name) {
		return name+" - "+unique();
	}
	
	static long unique = System.currentTimeMillis()/2;
	
	private static synchronized long unique() {
		return unique++;
	}
	

}
