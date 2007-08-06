package org.openuss.lecture;

import org.openuss.foundation.AbstractMockDao;

public class CourseTypeDaoMock extends AbstractMockDao<CourseType> implements CourseTypeDao {

	public Object create(int transform, String name, String shortcut, String description) {
		// TODO Auto-generated method stub
		return null;
	}

	public CourseType create(String name, String shortcut, String description) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object findByName(int transform, String queryString, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object findByName(int transform, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public CourseType findByName(String queryString, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public CourseType findByName(String name) {
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

	public CourseType findByShortcut(String queryString, String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}

	public CourseType findByShortcut(String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}

}
