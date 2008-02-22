// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import org.openuss.lecture.CourseInfo;

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
        super.toCourseGroupInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.seminarpool.CourseGroupDao#toCourseGroupInfo(org.openuss.seminarpool.CourseGroup)
     */
    public org.openuss.seminarpool.CourseGroupInfo toCourseGroupInfo(final org.openuss.seminarpool.CourseGroup entity)
    {
    	if (entity != null) { 
    		CourseGroupInfo targetVO = new CourseGroupInfo();
			toCourseGroupInfo(entity, targetVO);
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
    private org.openuss.seminarpool.CourseGroup loadCourseGroupFromCourseGroupInfo(org.openuss.seminarpool.CourseGroupInfo courseGroupInfo)
    {
        CourseGroup courseGroup = null;
        if(courseGroupInfo != null && courseGroupInfo.getId() != null){
        	courseGroup = this.load(courseGroupInfo.getId());
        }
        if(courseGroup == null){
        	courseGroup = CourseGroup.Factory.newInstance();
        }
        return courseGroup;
    }

    
    /**
     * @see org.openuss.seminarpool.CourseGroupDao#courseGroupInfoToEntity(org.openuss.seminarpool.CourseGroupInfo)
     */
    public org.openuss.seminarpool.CourseGroup courseGroupInfoToEntity(org.openuss.seminarpool.CourseGroupInfo courseGroupInfo)
    {
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
        super.courseGroupInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}