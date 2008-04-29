package org.openuss.lecture;

import java.util.Collection;
import java.util.List;

import org.openuss.foundation.AbstractMockDao;

public class CourseDaoMock extends AbstractMockDao<Course> implements CourseDao  {

	public void courseInfoToEntity(CourseInfo sourceVO, Course targetEntity, boolean copyIfNull) {
		
	}

	public Course courseInfoToEntity(CourseInfo courseInfo) {
		return null;
	}

	public void courseInfoToEntityCollection(Collection instances) {
		
	}

	public Object create(int transform, String shortcut, AccessType accessType, String password, Boolean documents,
			Boolean discussion, Boolean newsletter, Boolean chat, Boolean freestylelearning,
			Boolean braincontest, Boolean collaboration, Boolean papersubmission, Boolean wiki, String description, boolean enabled, Boolean calendar) {
		return null;
	}

	public Course create(String shortcut, AccessType accessType, String password, Boolean documents,
			Boolean discussion, Boolean newsletter, Boolean chat, Boolean freestylelearning,
			Boolean braincontest, Boolean collaboration, Boolean papersubmission, Boolean wiki, String description, boolean enabled, Boolean calendar) {
		return null;
	}

	public List findByCourseType(CourseType courseType) {
		return null;
	}

	public List findByCourseType(int transform, CourseType courseType) {
		return null;
	}

	public List findByCourseType(int transform, String queryString, CourseType courseType) {
		return null;
	}

	public List findByCourseType(String queryString, CourseType courseType) {
		return null;
	}

	public List findByPeriod(int transform, Period period) {
		return null;
	}

	public List findByPeriod(int transform, String queryString, Period period) {
		return null;
	}

	public List findByPeriod(Period period) {
		return null;
	}

	public List findByPeriod(String queryString, Period period) {
		return null;
	}

	public Object findByShortcut(int transform, String queryString, String shortcut) {
		return null;
	}

	public Object findByShortcut(int transform, String shortcut) {
		return null;
	}

	public Course findByShortcut(String queryString, String shortcut) {
		return null;
	}

	public Course findByShortcut(String shortcut) {
		return null;
	}
	
	
	public List findByInstitute(int transform, String queryString, Institute institute) {
		return null;
	}

	public List findByInstitute(int transform, Institute institute) {
		return null;
	}

	public List findByInstitute(String queryString, Institute institute) {
		return null;
	}

	public List findByInstitute(Institute institute) {
		return null;
	}
	
	
	public List findByPeriodAndCourseType(int transform, String queryString, Period period, CourseType courseType) {
		return null;
	}

	public List findByPeriodAndCourseType(int transform, Period period, CourseType courseType) {
		return null;
	}

	public List findByPeriodAndCourseType(String queryString, Period period, CourseType courseType) {
		return null;
	}

	public List findByPeriodAndCourseType(Period period, CourseType courseType) {
		return null;
	}
	
	public List findByPeriodAndEnabled(int transform, String queryString, Period period, boolean enabled) {
		return null;
	}

	public List findByPeriodAndEnabled(int transform, Period period, boolean enabled) {
		return null;
	}

	public List findByPeriodAndEnabled(String queryString, Period period, boolean enabled) {
		return null;
	}

	public List findByPeriodAndEnabled(Period period, boolean enabled) {
		return null;
	}

	public List findByEnabled(int transform, String queryString, boolean enabled) {
		return null;
	}

	public List findByEnabled(int transform, boolean enabled) {
		return null;
	}

	public List findByEnabled(String queryString, boolean enabled) {
		return null;
	}

	public List findByEnabled(boolean enabled) {
		return null;
	}
	
	public void toCourseInfo(Course sourceEntity, CourseInfo targetVO) {
		
	}

	public CourseInfo toCourseInfo(Course entity) {
		return null;
	}

	public void toCourseInfoCollection(Collection entities) {
		
	}

}
