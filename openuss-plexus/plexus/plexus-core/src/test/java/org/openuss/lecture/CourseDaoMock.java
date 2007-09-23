package org.openuss.lecture;

import java.util.Collection;
import java.util.List;

import org.openuss.foundation.AbstractMockDao;

public class CourseDaoMock extends AbstractMockDao<Course> implements CourseDao  {

	public void courseInfoToEntity(CourseInfo sourceVO, Course targetEntity, boolean copyIfNull) {
		// TODO Auto-generated method stub
		
	}

	public Course courseInfoToEntity(CourseInfo courseInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public void courseInfoToEntityCollection(Collection instances) {
		// TODO Auto-generated method stub
		
	}

	public Object create(int transform, String shortcut, AccessType accessType, String password, Boolean documents,
			Boolean discussion, Boolean newsletter, Boolean chat, Boolean wiki, Boolean freestylelearning,
			Boolean braincontest, String description, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public Course create(String shortcut, AccessType accessType, String password, Boolean documents,
			Boolean discussion, Boolean newsletter, Boolean chat, Boolean wiki, Boolean freestylelearning,
			Boolean braincontest, String description, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByCourseType(CourseType courseType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByCourseType(int transform, CourseType courseType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByCourseType(int transform, String queryString, CourseType courseType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByCourseType(String queryString, CourseType courseType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByPeriod(int transform, Period period) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByPeriod(int transform, String queryString, Period period) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByPeriod(Period period) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByPeriod(String queryString, Period period) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object findByShortcut(int transform, String queryString, String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object findByShortcut(int transform, String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}

	public Course findByShortcut(String queryString, String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}

	public Course findByShortcut(String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public List findByInstitute(int transform, String queryString, Institute institute) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByInstitute(int transform, Institute institute) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByInstitute(String queryString, Institute institute) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByInstitute(Institute institute) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public List findByPeriodAndCourseType(int transform, String queryString, Period period, CourseType courseType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByPeriodAndCourseType(int transform, Period period, CourseType courseType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByPeriodAndCourseType(String queryString, Period period, CourseType courseType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByPeriodAndCourseType(Period period, CourseType courseType) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List findByPeriodAndEnabled(int transform, String queryString, Period period, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByPeriodAndEnabled(int transform, Period period, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByPeriodAndEnabled(String queryString, Period period, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByPeriodAndEnabled(Period period, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByEnabled(int transform, String queryString, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByEnabled(int transform, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByEnabled(String queryString, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void toCourseInfo(Course sourceEntity, CourseInfo targetVO) {
		// TODO Auto-generated method stub
		
	}

	public CourseInfo toCourseInfo(Course entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public void toCourseInfoCollection(Collection entities) {
		// TODO Auto-generated method stub
		
	}

}
