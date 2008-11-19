package org.openuss.lecture;

import java.util.List;

import org.openuss.foundation.DomainObject;

/**
 * Institute
 * 
 * @author Ingo Dueppe
 */
public interface Institute extends Organisation, DomainObject {

	public Department getDepartment();

	public void setDepartment(Department department);

	public List<CourseType> getCourseTypes();

	public void setCourseTypes(List<CourseType> courseTypes);

	public List<Application> getApplications();

	public void setApplications(List<Application> applications);

	public abstract void add(CourseType courseType);

	public abstract void remove(CourseType courseType);

	/**
	 * @Deprecated Use UniversityService.findActiveCoursesByUniversity instead
	 */
	@Deprecated
	public abstract List getActiveCourses();

	public abstract List getAllCourses();

}