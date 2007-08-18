// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.HashSet;
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
		
		// Transform ValueObject to Entity
		CourseType courseTypeEntity = this.getCourseTypeDao().courseTypeInfoToEntity(courseTypeInfo);
		Validate.notNull(courseTypeEntity, "CourseTypeServiceImpl.handleCreate - could not transform courseTypeInfo to entity.");
		
		// Save entity
		courseTypeEntity.getInstitute().add(courseTypeEntity);
		this.getCourseTypeDao().create(courseTypeEntity);
		Validate.notNull(courseTypeEntity.getId(), "CourseTypeServiceImpl.handleCreate - id of courseType cannot be null.");
		
		// Do not delete this!!! Set id of courseType VO for indexing.
		courseTypeInfo.setId(courseTypeEntity.getId());
		
		return courseTypeEntity.getId();
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
		
		// TODO: Fire removedCourse event to delete all bookmarks
		//		 Fire removedCourseType event to delete all bookmarks
		/*
		for (Course course : courseType.getCourses()) {
			fireRemovingCourse(course);
		}
		fireRemovingCourseType(courseType);
		*/
		
		this.getCourseTypeDao().remove(courseTypeId);
		
	}

	@Override
	protected void handleUpdate(CourseTypeInfo courseTypeInfo) throws Exception {

		Validate.notNull(courseTypeInfo, "CourseTypeServiceImpl.handleUpdate - courseTypeInfo cannot be null.");
		Validate.notNull(courseTypeInfo.getId(), "CourseTypeServiceImpl.handleUpdate - the courseTypeInfoId cannot be null.");
		
		//Transform VO to entity
		CourseType courseType = this.getCourseTypeDao().courseTypeInfoToEntity(courseTypeInfo);
		
		//TODO: Should it be able to set new institute of courseType???
		this.getCourseTypeDao().update(courseType);
		
	}
	
	@Override
	public boolean handleIsNoneExistingCourseTypeShortcut(CourseTypeInfo self, String shortcut) throws Exception {
		CourseType found = this.getCourseTypeDao().findByShortcut(shortcut);
		CourseTypeInfo foundInfo = null;
		if (found != null) {
			foundInfo = this.getCourseTypeDao().toCourseTypeInfo(found);
		}
		return isEqualOrNull(self, foundInfo);
	}
	
	@Override
	public boolean handleIsNoneExistingCourseTypeName(CourseTypeInfo self, String name) throws Exception {
		CourseType found = getCourseTypeDao().findByName(name);
		CourseTypeInfo foundInfo = null;
		if (found != null) {
			foundInfo = this.getCourseTypeDao().toCourseTypeInfo(found);
		}
		return isEqualOrNull(self, foundInfo);
	}
	
	@Override
	public void handleRegisterListener (LectureListener listener) throws Exception {
		// TODO: Implement this method.
	}
	
	/*------------------- private methods -------------------- */

	// TODO: Add Set of listeners
	
	// TODO: Method unregisterListener
	
	// TODO: Method fireRemovingCourseType (CourseType courseType)
	
	/**
	 * Convenience method for isNonExisting methods.<br/> Checks whether or not the found record is equal to self entry.
	 * <ul>
	 * <li>self == null AND found == null => <b>true</b></li>
	 * <li>self == null AND found <> null => <b>false</b></li>
	 * <li>self <> null AND found == null => <b>true</b></li>
	 * <li>self <> null AND found <> null AND self == found => <b>true</b></li>
	 * <li>self <> null AND found <> null AND self <> found => <b>false</b></li>
	 * </ul>
	 * 
	 * @param self
	 *            current record
	 * @param found
	 *            in database
	 * @return true or false
	 */
	private boolean isEqualOrNull(Object self, Object found) {
		if (self == null || found == null) {
			return found == null;
		} else {
			return self.equals(found);
		}
	}

}