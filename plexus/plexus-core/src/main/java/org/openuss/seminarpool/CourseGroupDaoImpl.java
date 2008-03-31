// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;
/**
 * @see org.openuss.seminarpool.CourseGroup
 */
public class CourseGroupDaoImpl
    extends org.openuss.seminarpool.CourseGroupDaoBase
{
    /**
     * @see org.openuss.seminarpool.CourseGroupDao#toCourseGroupInfo(org.openuss.seminarpool.CourseGroup, org.openuss.seminarpool.CourseGroupInfo)
     */
    public void toCourseGroupInfo(
        org.openuss.seminarpool.CourseGroup sourceEntity,
        org.openuss.seminarpool.CourseGroupInfo targetVO)
    {
        // @todo verify behavior of toCourseGroupInfo
        super.toCourseGroupInfo(sourceEntity, targetVO);
        // WARNING! No conversion for targetVO.courseSchedule (can't convert sourceEntity.getCourseSchedule():org.openuss.seminarpool.CourseSchedule to org.openuss.seminarpool.CourseScheduleInfo
    }


    /**
     * @see org.openuss.seminarpool.CourseGroupDao#toCourseGroupInfo(org.openuss.seminarpool.CourseGroup)
     */
    public org.openuss.seminarpool.CourseGroupInfo toCourseGroupInfo(final org.openuss.seminarpool.CourseGroup entity)
    {
        // @todo verify behavior of toCourseGroupInfo
        return super.toCourseGroupInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.seminarpool.CourseGroup loadCourseGroupFromCourseGroupInfo(org.openuss.seminarpool.CourseGroupInfo courseGroupInfo)
    {
        // @todo implement loadCourseGroupFromCourseGroupInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.loadCourseGroupFromCourseGroupInfo(org.openuss.seminarpool.CourseGroupInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.seminarpool.CourseGroup courseGroup = this.load(courseGroupInfo.getId());
        if (courseGroup == null)
        {
            courseGroup = org.openuss.seminarpool.CourseGroup.Factory.newInstance();
        }
        return courseGroup;
        */
    }

    
    /**
     * @see org.openuss.seminarpool.CourseGroupDao#courseGroupInfoToEntity(org.openuss.seminarpool.CourseGroupInfo)
     */
    public org.openuss.seminarpool.CourseGroup courseGroupInfoToEntity(org.openuss.seminarpool.CourseGroupInfo courseGroupInfo)
    {
        // @todo verify behavior of courseGroupInfoToEntity
        org.openuss.seminarpool.CourseGroup entity = this.loadCourseGroupFromCourseGroupInfo(courseGroupInfo);
        this.courseGroupInfoToEntity(courseGroupInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.seminarpool.CourseGroupDao#courseGroupInfoToEntity(org.openuss.seminarpool.CourseGroupInfo, org.openuss.seminarpool.CourseGroup)
     */
    public void courseGroupInfoToEntity(
        org.openuss.seminarpool.CourseGroupInfo sourceVO,
        org.openuss.seminarpool.CourseGroup targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of courseGroupInfoToEntity
        super.courseGroupInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}