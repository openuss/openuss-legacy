// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * @see org.openuss.lecture.CourseTypeService
 * @author Florian Dondorf
 */
public class CourseTypeServiceImpl
    extends org.openuss.lecture.CourseTypeServiceBase
{

	private static final Logger logger = Logger.getLogger(CourseTypeServiceImpl.class);
	
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

		Validate.notNull(courseTypeId, "CourseTypeServiceImpl.handleRemoveCourseType - the courseTypeId cannot be null.");
		
		CourseType courseType = this.getCourseTypeDao().load(courseTypeId);
		Validate.notNull(courseType, "CourseTypeServiceImpl.handleRemoveCourseType - cannot fin CourseType to the corresponding courseTypeId "+courseTypeId);
		
		// fire course delete
		for (Course course : courseType.getCourses()) {
			fireRemovingCourse(course);
		}
		fireRemovingCourseType(courseType);
		this.getCourseTypeDao().remove(courseTypeId);
		
	}

	@Override
	protected void handleUpdate(CourseTypeInfo courseTypeInfo) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean handleIsNoneExistingCourseTypeShortcut(CourseTypeInfo self, String shortcut) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean handleIsNoneExistingCourseTypeName(CourseTypeInfo self, String name) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	
	/*------------------- private methods -------------------- */

	private Set<LectureListener> listeners;

	protected void handleUnregisterListener(LectureListener listener) throws Exception {
		if (listeners != null) {
			listeners.remove(listener);
		}
	}

	// FIXME use event handler instead

	private void fireRemovingCourse(Course course) throws LectureException {
		if (listeners != null) {
			logger.debug("fire removing course event");
			for (LectureListener listener : listeners) {
				listener.removingCourse(course);
			}
		}
	}

	private void fireRemovingCourseType(CourseType courseType) throws LectureException {
		if (listeners != null) {
			logger.debug("fire removing courseType event");
			for (LectureListener listener : listeners) {
				listener.removingCourseType(courseType);
			}
		}
	}

	private void fireRemovingInstitute(Institute institute) throws LectureException {
		if (listeners != null) {
			logger.debug("fire removing institute event");
			for (LectureListener listener : listeners) {
				listener.removingInstitute(institute);
			}
		}
	}

	private void fireCreatedInstitute(Institute institute) throws LectureException {
		if (listeners != null) {
			logger.debug("fire created institute event");
			for (LectureListener listener : listeners) {
				listener.createdInstitute(institute);
			}
		}
	}

}