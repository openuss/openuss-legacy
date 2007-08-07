package org.openuss.lecture;

import java.util.Collection;

import org.openuss.foundation.AbstractMockDao;

public class CourseTypeDaoMock extends AbstractMockDao<CourseType> implements CourseTypeDao {

	public void courseTypeInfoToEntity(CourseTypeInfo sourceVO, CourseType targetEntity, boolean copyIfNull) {
		// TODO Auto-generated method stub
		
	}

	public CourseType courseTypeInfoToEntity(CourseTypeInfo courseTypeInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public void courseTypeInfoToEntityCollection(Collection instances) {
		// TODO Auto-generated method stub
		
	}

	public void toCourseTypeInfo(CourseType sourceEntity, CourseTypeInfo targetVO) {
		// TODO Auto-generated method stub
		
	}

	public CourseTypeInfo toCourseTypeInfo(CourseType entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public void toCourseTypeInfoCollection(Collection entities) {
		// TODO Auto-generated method stub
		
	}

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
