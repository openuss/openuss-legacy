package org.openuss.lecture;

import org.openuss.security.User;

/**
 * Builds faculty, periods, courseType, and course structures.
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
	
	public LectureBuilder addCourseType() {
		CourseType courseType = CourseType.Factory.newInstance();
		courseType.setName(unique());
		courseType.setShortcut(unique());
		courseType.setDescription("description");
		faculty.add(courseType);
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
	
	public LectureBuilder addCourse() {
		return addCourse(0, 0);
	}
	
	public LectureBuilder addCourse(int indexCourseType, int indexPeriod ) {
		CourseType courseType = faculty.getCourseTypes().get(indexCourseType);
		Period period = faculty.getPeriods().get(indexPeriod);
		addCourse(faculty, courseType, period);
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
	
	public Course getCourse() {
		return getCourse(0);
	}
	
	public Course getCourse(int index) {
		return faculty.getCourses().get(index);
	}
	
	public LectureBuilder remove() {
		if (faculty.getId() != null)
			facultyDao.remove(faculty);
		return this;
	}
	
	private String unique() {
		return ""+(System.currentTimeMillis()+code++);
	}
	
	public CourseType addCourseType(Faculty faculty) {
		CourseType courseType = LectureFactory.createCourseType();
		courseType.setFaculty(faculty);
		faculty.add(courseType);
		return courseType;
	}
	
	public Period addPeriod(Faculty faculty) {
		Period period = LectureFactory.createPeriod();
		period.setFaculty(faculty);
		faculty.add(period);
		return period;
	}
	
	public Course addCourse(Faculty faculty, CourseType courseType, Period period) {
		Course course = LectureFactory.createCourse();
		courseType.add(course);
		course.setCourseType(courseType);
		course.setShortcut(unique());
		period.add(course);
		course.setPeriod(period);
		faculty.add(course);
		course.setFaculty(faculty);
		return course;
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
