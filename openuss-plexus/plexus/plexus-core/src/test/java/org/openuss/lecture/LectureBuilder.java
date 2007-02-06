package org.openuss.lecture;

import org.openuss.security.User;

/**
 * Builds faculty, periods, subject, and enrollment structures.
 *  
 * @author Ingo Dueppe
 */
public class LectureBuilder {
	
	private User owner;
	
	private Faculty faculty;
	
	private FacultyDao facultyDao;
	
	private static int code = 0;
	
	public LectureBuilder createFaculty(User owner) {
		faculty = Faculty.Factory.newInstance();
		faculty.setName(unique());
		faculty.setOwnername("faculty owner");
		faculty.setShortcut(unique());
		faculty.setEmail("email@email.com");
		faculty.setAddress("Leonardo-Campus 3");
		faculty.setPostcode("48149");
		faculty.setCity("Münster");
		faculty.setLocale("de");
		faculty.setTheme("plexus");
		faculty.setWebsite("www.openuss.org");
		faculty.setOwner(owner);
		return this;
	}
	
	public LectureBuilder addSubject() {
		Subject subject = Subject.Factory.newInstance();
		subject.setName(unique());
		subject.setShortcut(unique());
		subject.setDescription("description");
		faculty.add(subject);
		return this;
	}
	
	public LectureBuilder addPeriod() {
		Period period = Period.Factory.newInstance();
		period.setName("test-period");
		period.setDescription("description");
		faculty.add(period);
		period.setFaculty(faculty);
		return this;
	}
	
	public LectureBuilder addEnrollment() {
		return addEnrollment(0, 0);
	}
	
	public LectureBuilder addEnrollment(int indexSubject, int indexPeriod ) {
		Subject subject = faculty.getSubjects().get(indexSubject);
		Period period = faculty.getPeriods().get(indexPeriod);
		addEnrollment(faculty, subject, period);
		return this;
	}
	
	public LectureBuilder persist() {
		if (faculty.getId() == null) {
			facultyDao.create(faculty);
		} else {
			facultyDao.update(faculty);
		}
		return this;
	}
	
	public Enrollment getEnrollment() {
		return getEnrollment(0);
	}
	
	public Enrollment getEnrollment(int index) {
		return faculty.getEnrollments().get(index);
	}
	
	public LectureBuilder remove() {
		if (faculty.getId() != null)
			facultyDao.remove(faculty);
		return this;
	}
	
	private String unique() {
		return ""+(System.currentTimeMillis()+code++);
	}
	
	public Subject addSubject(Faculty faculty) {
		Subject subject = LectureFactory.createSubject();
		subject.setFaculty(faculty);
		faculty.add(subject);
		return subject;
	}
	
	public Period addPeriod(Faculty faculty) {
		Period period = LectureFactory.createPeriod();
		period.setFaculty(faculty);
		faculty.add(period);
		return period;
	}
	
	public Enrollment addEnrollment(Faculty faculty, Subject subject, Period period) {
		Enrollment enrollment = LectureFactory.createEnrollment();
		subject.add(enrollment);
		enrollment.setSubject(subject);
		enrollment.setShortcut(unique());
		period.add(enrollment);
		enrollment.setPeriod(period);
		faculty.add(enrollment);
		enrollment.setFaculty(faculty);
		return enrollment;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public FacultyDao getFacultyDao() {
		return facultyDao;
	}

	public void setFacultyDao(FacultyDao facultyDao) {
		this.facultyDao = facultyDao;
	}
}
