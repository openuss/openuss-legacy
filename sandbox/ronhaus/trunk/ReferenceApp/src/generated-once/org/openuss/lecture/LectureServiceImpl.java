package org.openuss.lecture;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openuss.registration.Assistant;
import org.openuss.registration.Student;

/**
 * 
 */
public class LectureServiceImpl extends LectureServiceBase {

	public void enrollStudentInCourse(Student student, Course course) {
		if (course.getMaxParticipants() > course.getParticipants().size()) {
			course.getParticipants().add(student);
			this.getCourseDao().update(course);
		}
	}

	public Set<org.openuss.lecture.Course> getFullCourses() {
		List<Course> courses = this.getCourseDao().readAll();
		Set<Course> fullCourses = new HashSet<Course>();
		for (Course course : courses) {
			if (course.getMaxParticipants() <= course.getParticipants().size()) {
				fullCourses.add(course);
			}
		}
		return fullCourses;
	}

	public Long persist(Student student) {
		return new Long(this.getStudentDao().create(student).toString());
	}

	public Long persist(Assistant assistant) {

		return new Long(this.getAssistantDao().create(assistant).toString());
	}

	public Long persist(Course course) {

		return new Long(this.getCourseDao().create(course).toString());
	}

	public Student readStudent(Long mnr) {
		List<Student> students = this.getStudentDao().readAll();
		for (Student student : students) {
			if (student.getMnr().compareTo(mnr) == 0) {
				return student;
			}
		}
		return null;
	}

	public Assistant readAssistant(Long id) {
		return this.getAssistantDao().read(id);
	}

	public Course readCourse(Long id) {
		return this.getCourseDao().read(id);
	}
	
	public void testTransactionManager(Student student) {
		Logger logger = Logger.getRootLogger();
		logger.info("Jetzt geht's los - hier kommt die Exception!");
		this.getStudentDao().create(student);
		throw new UnsupportedOperationException();
	}

}
