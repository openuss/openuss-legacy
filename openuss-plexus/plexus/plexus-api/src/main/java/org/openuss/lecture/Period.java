package org.openuss.lecture;

import java.util.Date;
import java.util.List;

import org.openuss.foundation.DomainObject;

/**
 * A period is a arbitary time frame like a semester, or trimester.
 * @author Ingo Dueppe
 */
public interface Period extends DomainObject {

	public Long getId();

	public void setId(Long id);

	public String getName();

	public void setName(String name);

	public String getDescription();

	public void setDescription(String description);

	public Date getStartdate();

	public void setStartdate(Date startdate);

	public Date getEnddate();

	public void setEnddate(Date enddate);

	public boolean isDefaultPeriod();

	public void setDefaultPeriod(boolean defaultPeriod);

	public List<org.openuss.lecture.Course> getCourses();

	public void setCourses(List<Course> courses);

	public University getUniversity();

	public void setUniversity(University university);

	public abstract void add(Course course);

	public abstract void remove(Course course);

	public abstract boolean isActive();

}