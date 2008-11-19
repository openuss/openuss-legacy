package org.openuss.lecture;

import java.util.List;

/**
 * Handles all matters that concern a CourseType.
 * 
 * @author Ron Haus
 * @author Florian Dondorf
 */
public interface CourseTypeService {

	public Long create(CourseTypeInfo courseTypeInfo);

	public void update(CourseTypeInfo courseTypeInfo);

	public void removeCourseType(Long courseTypeId);

	public CourseTypeInfo findCourseType(Long courseTypeId);

	public List findCourseTypesByInstitute(Long instituteId);

	public boolean isNoneExistingCourseTypeName(CourseTypeInfo self, String name);

}
