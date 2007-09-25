package org.openuss.lecture;

import java.util.Collection;

import org.openuss.foundation.AbstractMockDao;

public class CourseTypeDaoMock extends AbstractMockDao<CourseType> implements CourseTypeDao {

	public void courseTypeInfoToEntity(CourseTypeInfo sourceVO, CourseType targetEntity, boolean copyIfNull) {
	}

	public CourseType courseTypeInfoToEntity(CourseTypeInfo courseTypeInfo) {
		return null;
	}

	public void courseTypeInfoToEntityCollection(Collection instances) {
	}

	public void toCourseTypeInfo(CourseType sourceEntity, CourseTypeInfo targetVO) {
	}

	public CourseTypeInfo toCourseTypeInfo(CourseType entity) {
		return null;
	}

	public void toCourseTypeInfoCollection(Collection entities) {
		
	}

	public Object create(int transform, String name, String shortcut, String description) {
		return null;
	}

	public CourseType create(String name, String shortcut, String description) {
		return null;
	}

	public Object findByName(int transform, String queryString, String name) {
		return null;
	}

	public Object findByName(int transform, String name) {
		return null;
	}

	public CourseType findByName(String queryString, String name) {
		return null;
	}

	public CourseType findByName(String name) {
		return null;
	}

	public Object findByShortcut(int transform, String queryString, String shortcut) {
		return null;
	}

	public Object findByShortcut(int transform, String shortcut) {
		return null;
	}

	public CourseType findByShortcut(String queryString, String shortcut) {
		return null;
	}

	public CourseType findByShortcut(String shortcut) {
		return null;
	}

}
