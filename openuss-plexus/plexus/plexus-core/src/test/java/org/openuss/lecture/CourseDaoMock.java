package org.openuss.lecture;

import java.util.Collection;

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

	public Course create(Course course) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object create(int transform, Course course) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object create(int transform, String shortcut, AccessType accessType, String password, boolean documents, boolean discussion, boolean newsletter, boolean chat, boolean wiki, boolean freestylelearning, boolean braincontest, String description) {
		// TODO Auto-generated method stub
		return null;
	}

	public Course create(String shortcut, AccessType accessType, String password, boolean documents, boolean discussion, boolean newsletter, boolean chat, boolean wiki, boolean freestylelearning, boolean braincontest, String description) {
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

	public void remove(Course course) {
		// TODO Auto-generated method stub
		
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

	public void update(Course course) {
		// TODO Auto-generated method stub
		
	}

	public Course create(String shortcut, AccessType accessType,
			String password, Boolean documents, Boolean discussion,
			Boolean newsletter, Boolean chat, Boolean wiki,
			Boolean freestylelearning, Boolean braincontest, String description) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object create(int transform, String shortcut, AccessType accessType,
			String password, Boolean documents, Boolean discussion,
			Boolean newsletter, Boolean chat, Boolean wiki,
			Boolean freestylelearning, Boolean braincontest, String description) {
		// TODO Auto-generated method stub
		return null;
	}

}
