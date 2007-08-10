// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Iterator;
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
		
		Validate.notNull(courseTypeId, "CourseTypeServiceImpl.handleFindCourseType - the courseTypeId cannot be null.");
		
		//Load CourseType entity
		CourseType courseType = this.getCourseTypeDao().load(courseTypeId);
		Validate.notNull(courseType, "CourseTypeServiceImpl.handleFindCourseType - can not find courseType with the corresponfing id "+courseTypeId);
		
		return this.getCourseTypeDao().toCourseTypeInfo(courseType);
	}

	/**
	 * @see org.openuss.lecture.CourseTypeService#findCourseTypesByInstitute(java.lang.Long)
	 */
	@Override
	@SuppressWarnings( { "unchecked" })
	protected List handleFindCourseTypesByInstitute(Long instituteId) throws Exception {
		
		Validate.notNull(instituteId, "CourseTypeServiceImpl.handleFindCourseTypesByInstitutes - the instituteId cannot be null.");
		
		//Load Institute
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute, "CourseTypeServiceImpl.handleFindCourseTypesByInstitutes - can not find institute with corresponding id "+instituteId);
		
		List courseTypeInfos = new ArrayList();
		for (CourseType courseType : institute.getCourseTypes()) {
			courseTypeInfos.add(this.getCourseTypeDao().toCourseTypeInfo(courseType));
		}

		return courseTypeInfos;
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