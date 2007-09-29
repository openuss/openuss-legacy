// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

/**
 * @see org.openuss.lecture.Course
 */
public class CourseDaoImpl extends org.openuss.lecture.CourseDaoBase {
	/**
	 * @see org.openuss.lecture.CourseDao#toCourseInfo(org.openuss.lecture.Course,
	 *      org.openuss.lecture.CourseInfo)
	 */
	public void toCourseInfo(Course sourceEntity, CourseInfo targetVO) {
		super.toCourseInfo(sourceEntity, targetVO);
		targetVO.setName(sourceEntity.getName());
		targetVO.setDescription(sourceEntity.getDescription());
		targetVO.setShortcut(sourceEntity.getShortcut());
		targetVO.setPassword(sourceEntity.getPassword());
		targetVO.setAccessType(sourceEntity.getAccessType());
		targetVO.setPeriodId(sourceEntity.getPeriod().getId());
		targetVO.setPeriodName(sourceEntity.getPeriod().getName());
		targetVO.setCourseTypeDescription(sourceEntity.getCourseType().getDescription());
		targetVO.setCourseTypeId(sourceEntity.getCourseType().getId());
		
		if (sourceEntity.getCourseType() != null) {
			Institute institute = sourceEntity.getCourseType().getInstitute();
			if (institute != null) {
				targetVO.setInstituteId(institute.getId());
				targetVO.setInstituteName(institute.getName());
			}
		}
	}

	/**
	 * @see org.openuss.lecture.CourseDao#toCourseInfo(org.openuss.lecture.Course)
	 */
	public CourseInfo toCourseInfo(final Course entity) {
		if (entity != null) { 
			CourseInfo targetVO = new CourseInfo();
			toCourseInfo(entity, targetVO);
			return targetVO;
		} else {
			return null;
		}
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private Course loadCourseFromCourseInfo(CourseInfo courseInfo) {
		
		Course course = Course.Factory.newInstance();
		if (courseInfo.getId() != null) {
			course = this.load(courseInfo.getId());
		}
		return course;
	}

	/**
	 * @see org.openuss.lecture.CourseDao#courseInfoToEntity(org.openuss.lecture.CourseInfo)
	 */
	public Course courseInfoToEntity(CourseInfo courseInfo) {
		Course entity = this.loadCourseFromCourseInfo(courseInfo);
		this.courseInfoToEntity(courseInfo, entity, true);
		if (courseInfo.getCourseTypeId() != null) {
			CourseType courseType = this.getCourseTypeDao().load(courseInfo.getCourseTypeId());
			entity.setCourseType(courseType);
		}
		
		if (courseInfo.getPeriodId() != null) {
			Period period = this.getPeriodDao().load(courseInfo.getPeriodId());
			entity.setPeriod(period);
		}
		return entity;
	}

	/**
	 * @see org.openuss.lecture.CourseDao#courseInfoToEntity(org.openuss.lecture.CourseInfo,
	 *      org.openuss.lecture.Course)
	 */
	public void courseInfoToEntity(CourseInfo sourceVO, Course targetEntity, boolean copyIfNull) {
		super.courseInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}