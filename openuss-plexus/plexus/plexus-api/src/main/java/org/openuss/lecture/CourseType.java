package org.openuss.lecture;

import java.util.List;

import org.openuss.foundation.DomainObject;

/**
 * The courseType
 */
public interface CourseType extends DomainObject {

	public Long getId();

	public void setId(Long id);

	public String getName();

	public void setName(String name);

	public String getShortcut();

	public void setShortcut(String shortcut);

	public String getDescription();

	public void setDescription(String description);

	public List<Course> getCourses();

	public void setCourses(List<Course> courses);

	public Institute getInstitute();

	public void setInstitute(Institute institute);

	public abstract void add(Course course);

	public abstract void remove(Course course);

}