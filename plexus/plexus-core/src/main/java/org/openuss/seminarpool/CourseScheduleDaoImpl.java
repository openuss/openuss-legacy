// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;
/**
 * @see org.openuss.seminarpool.CourseSchedule
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
        // @todo verify behavior of toCourseScheduleInfo
        super.toCourseScheduleInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.seminarpool.CourseScheduleDao#toCourseScheduleInfo(org.openuss.seminarpool.CourseSchedule)
     */
    public org.openuss.seminarpool.CourseScheduleInfo toCourseScheduleInfo(final org.openuss.seminarpool.CourseSchedule entity)
    {
        // @todo verify behavior of toCourseScheduleInfo
        return super.toCourseScheduleInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.seminarpool.CourseSchedule loadCourseScheduleFromCourseScheduleInfo(org.openuss.seminarpool.CourseScheduleInfo courseScheduleInfo)
    {
        // @todo implement loadCourseScheduleFromCourseScheduleInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.loadCourseScheduleFromCourseScheduleInfo(org.openuss.seminarpool.CourseScheduleInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.seminarpool.CourseSchedule courseSchedule = this.load(courseScheduleInfo.getId());
        if (courseSchedule == null)
        {
            courseSchedule = org.openuss.seminarpool.CourseSchedule.Factory.newInstance();
        }
        return courseSchedule;
        */
    }

    
    /**
     * @see org.openuss.seminarpool.CourseScheduleDao#courseScheduleInfoToEntity(org.openuss.seminarpool.CourseScheduleInfo)
     */
    public org.openuss.seminarpool.CourseSchedule courseScheduleInfoToEntity(org.openuss.seminarpool.CourseScheduleInfo courseScheduleInfo)
    {
        // @todo verify behavior of courseScheduleInfoToEntity
        org.openuss.seminarpool.CourseSchedule entity = this.loadCourseScheduleFromCourseScheduleInfo(courseScheduleInfo);
        this.courseScheduleInfoToEntity(courseScheduleInfo, entity, true);
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
        // @todo verify behavior of courseScheduleInfoToEntity
        super.courseScheduleInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}