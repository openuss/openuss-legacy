// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

/**
 * @see org.openuss.seminarpool.CourseSchedule
 * @author Stefan Thiemann
 */
public class CourseScheduleDaoImpl
    extends org.openuss.seminarpool.CourseScheduleDaoBase
{
    /**
     * @see org.openuss.seminarpool.CourseScheduleDao#toCourseScheduleInfo(org.openuss.seminarpool.CourseSchedule, org.openuss.seminarpool.CourseScheduleInfo)
     */
    public void toCourseScheduleInfo(
        org.openuss.seminarpool.CourseSchedule sourceEntity,
        org.openuss.seminarpool.CourseScheduleInfo targetVO)
    {
        super.toCourseScheduleInfo(sourceEntity, targetVO);
        if (sourceEntity.getCourseGroup() != null) {
        	targetVO.setCourseGroupId(sourceEntity.getCourseGroup().getId());
        }
    }


    /**
     * @see org.openuss.seminarpool.CourseScheduleDao#toCourseScheduleInfo(org.openuss.seminarpool.CourseSchedule)
     */
    public org.openuss.seminarpool.CourseScheduleInfo toCourseScheduleInfo(final org.openuss.seminarpool.CourseSchedule entity)
    {
    	if (entity != null) { 
    		CourseScheduleInfo targetVO = new CourseScheduleInfo();
			toCourseScheduleInfo(entity, targetVO);
			return targetVO;
		} else {
			return null;
		}
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.seminarpool.CourseSchedule loadCourseScheduleFromCourseScheduleInfo(org.openuss.seminarpool.CourseScheduleInfo courseScheduleInfo)
    {
        CourseSchedule courseSchedule = null;
        if(courseScheduleInfo != null && courseScheduleInfo.getId() != null){
        	courseSchedule = this.load(courseScheduleInfo.getId());
        }
        if(courseSchedule == null){
        	courseSchedule = CourseSchedule.Factory.newInstance();
        }
        return courseSchedule;
    }

    
    /**
     * @see org.openuss.seminarpool.CourseScheduleDao#courseScheduleInfoToEntity(org.openuss.seminarpool.CourseScheduleInfo)
     */
    public org.openuss.seminarpool.CourseSchedule courseScheduleInfoToEntity(org.openuss.seminarpool.CourseScheduleInfo courseScheduleInfo)
    {
        org.openuss.seminarpool.CourseSchedule entity = this.loadCourseScheduleFromCourseScheduleInfo(courseScheduleInfo);
        this.courseScheduleInfoToEntity(courseScheduleInfo, entity, true);
        
        if(courseScheduleInfo.getCourseGroupId() != null){
        	CourseGroup courseGroup = this.getCourseGroupDao().load(courseScheduleInfo.getCourseGroupId());
        	entity.setCourseGroup(courseGroup);
        }
        return entity;
    }


    /**
     * @see org.openuss.seminarpool.CourseScheduleDao#courseScheduleInfoToEntity(org.openuss.seminarpool.CourseScheduleInfo, org.openuss.seminarpool.CourseSchedule)
     */
    public void courseScheduleInfoToEntity(
        org.openuss.seminarpool.CourseScheduleInfo sourceVO,
        org.openuss.seminarpool.CourseSchedule targetEntity,
        boolean copyIfNull)
    {
        super.courseScheduleInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}