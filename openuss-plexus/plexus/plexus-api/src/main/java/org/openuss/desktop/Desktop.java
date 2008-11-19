package org.openuss.desktop;

import java.util.List;

import org.openuss.foundation.DomainObject;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.Department;
import org.openuss.lecture.Institute;
import org.openuss.lecture.University;
import org.openuss.security.User;

/**
 * 
 * @author Ingo Dueppe
 * 
 */
public interface Desktop extends DomainObject {

	public Long getId();

	public void setId(Long id);

	public User getUser();

	public void setUser(User user);

	public List<Institute> getInstitutes();

	public void setInstitutes(List<Institute> institutes);

	public List<Course> getCourses();

	public void setCourses(List<Course> courses);

	public List<CourseType> getCourseTypes();

	public void setCourseTypes(List<CourseType> courseTypes);

	public List<University> getUniversities();

	public void setUniversities(List<University> universities);

	public List<Department> getDepartments();

	public void setDepartments(List<Department> departments);

	public void linkInstitute(Institute institute);

	public void unlinkInstitute(Institute institute);

	public void linkCourseType(CourseType courseType);

	public void unlinkCourseType(CourseType courseType);

	public void linkCourse(Course course);

	public void unlinkCourse(org.openuss.lecture.Course course);

}