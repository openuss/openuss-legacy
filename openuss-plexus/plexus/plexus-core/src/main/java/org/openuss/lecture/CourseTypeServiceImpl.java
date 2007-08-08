// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * @see org.openuss.lecture.CourseTypeService
 * @author Florian Dondorf
 */
public class CourseTypeServiceImpl
    extends org.openuss.lecture.CourseTypeServiceBase
{

	@Override
	protected Long handleCreate(CourseTypeInfo courseTypeInfo) throws Exception {
		
		Validate.notNull(courseTypeInfo, "CourseTypeServiceImpl.handleCreate - courseTypeInfo must not be null.");
		
		//Transform ValueObject to Entity
		CourseType courseType = this.getCourseTypeDao().courseTypeInfoToEntity(courseTypeInfo);
		Validate.notNull(courseType, "CourseTypeServiceImpl.handleCreate - could not transform courseTypeInfo to entity.");
		
		return this.getCourseTypeDao().create(courseType).getId();
	}

	@Override
	protected CourseTypeInfo handleFindCourseType(Long courseTypeId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List handleFindCourseTypesByInstitute(Long instituteId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleRemoveCourseType(Long courseTypeId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleUpdate(CourseTypeInfo courseTypeInfo) throws Exception {
		// TODO Auto-generated method stub
		
	}

}