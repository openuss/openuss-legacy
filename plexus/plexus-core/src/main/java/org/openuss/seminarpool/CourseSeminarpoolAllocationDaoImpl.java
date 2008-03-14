// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import org.openuss.lecture.Course;

/**
 * @see org.openuss.seminarpool.CourseSeminarpoolAllocation
 * @author Stefan Thiemann
 */
public class CourseSeminarpoolAllocationDaoImpl
    extends org.openuss.seminarpool.CourseSeminarpoolAllocationDaoBase
{
    /**
     * @see org.openuss.seminarpool.CourseSeminarpoolAllocationDao#toCourseSeminarpoolAllocationInfo(org.openuss.seminarpool.CourseSeminarpoolAllocation, org.openuss.seminarpool.CourseSeminarpoolAllocationInfo)
     */
    public void toCourseSeminarpoolAllocationInfo(
        org.openuss.seminarpool.CourseSeminarpoolAllocation sourceEntity,
        org.openuss.seminarpool.CourseSeminarpoolAllocationInfo targetVO)
    {
        super.toCourseSeminarpoolAllocationInfo(sourceEntity, targetVO);
        if(sourceEntity.getSeminarpool() != null && sourceEntity.getSeminarpool().getId() != null){
        	targetVO.setSeminarpoolId(sourceEntity.getSeminarpool().getId());
        }
        if(sourceEntity.getCourse() != null && sourceEntity.getCourse().getId() != null){
        	targetVO.setCourseId(sourceEntity.getCourse().getId());
        	targetVO.setCourseName(this.getCourseDao().load(sourceEntity.getCourse().getId()).getName());
        	targetVO.setCourseShortcut(this.getCourseDao().load(sourceEntity.getCourse().getId()).getShortcut());
        } 
        targetVO.setNumberOfRegistrations(Long.valueOf(sourceEntity.getSeminarPriority().size()));
        
    }


    /**
     * @see org.openuss.seminarpool.CourseSeminarpoolAllocationDao#toCourseSeminarpoolAllocationInfo(org.openuss.seminarpool.CourseSeminarpoolAllocation)
     */
    public org.openuss.seminarpool.CourseSeminarpoolAllocationInfo toCourseSeminarpoolAllocationInfo(final org.openuss.seminarpool.CourseSeminarpoolAllocation entity)
    {
    	if (entity != null) { 
    		CourseSeminarpoolAllocationInfo targetVO = new CourseSeminarpoolAllocationInfo();
			toCourseSeminarpoolAllocationInfo(entity, targetVO);
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
    private org.openuss.seminarpool.CourseSeminarpoolAllocation loadCourseSeminarpoolAllocationFromCourseSeminarpoolAllocationInfo(org.openuss.seminarpool.CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo)
    {
    	CourseSeminarpoolAllocation courseSeminarpoolAllocation = CourseSeminarpoolAllocation.Factory.newInstance();
    	if(courseSeminarpoolAllocationInfo != null && courseSeminarpoolAllocationInfo.getId() != null){
    		courseSeminarpoolAllocation = this.load(courseSeminarpoolAllocationInfo.getId());
    	}
    	return courseSeminarpoolAllocation;
    }

    
    /**
     * @see org.openuss.seminarpool.CourseSeminarpoolAllocationDao#courseSeminarpoolAllocationInfoToEntity(org.openuss.seminarpool.CourseSeminarpoolAllocationInfo)
     */
    public org.openuss.seminarpool.CourseSeminarpoolAllocation courseSeminarpoolAllocationInfoToEntity(org.openuss.seminarpool.CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo)
    {
        org.openuss.seminarpool.CourseSeminarpoolAllocation entity = this.loadCourseSeminarpoolAllocationFromCourseSeminarpoolAllocationInfo(courseSeminarpoolAllocationInfo);
        this.courseSeminarpoolAllocationInfoToEntity(courseSeminarpoolAllocationInfo, entity, true);
        if(courseSeminarpoolAllocationInfo.getSeminarpoolId() != null){
        	Seminarpool seminarpool = this.getSeminarpoolDao().load(courseSeminarpoolAllocationInfo.getSeminarpoolId());
        	entity.setSeminarpool(seminarpool);
        }
        if(courseSeminarpoolAllocationInfo.getCourseId() != null){
        	Course course = this.getCourseDao().load(courseSeminarpoolAllocationInfo.getCourseId());
        	entity.setCourse(course);
        }
        return entity;
    }


    /**
     * @see org.openuss.seminarpool.CourseSeminarpoolAllocationDao#courseSeminarpoolAllocationInfoToEntity(org.openuss.seminarpool.CourseSeminarpoolAllocationInfo, org.openuss.seminarpool.CourseSeminarpoolAllocation)
     */
    public void courseSeminarpoolAllocationInfoToEntity(
        org.openuss.seminarpool.CourseSeminarpoolAllocationInfo sourceVO,
        org.openuss.seminarpool.CourseSeminarpoolAllocation targetEntity,
        boolean copyIfNull)
    {
        super.courseSeminarpoolAllocationInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

    /**
     * @see org.openuss.seminarpool.CourseSeminarpoolAllocationDao#toSeminarPlaceAllocationInfo(org.openuss.seminarpool.CourseSeminarpoolAllocation, org.openuss.seminarpool.SeminarPlaceAllocationInfo)
     */
    public void toSeminarPlaceAllocationInfo(
        org.openuss.seminarpool.CourseSeminarpoolAllocation sourceEntity,
        org.openuss.seminarpool.SeminarPlaceAllocationInfo targetVO)
    {
        super.toSeminarPlaceAllocationInfo(sourceEntity, targetVO);
        if(sourceEntity.getSeminarpool() != null && sourceEntity.getSeminarpool().getId() != null){
        	targetVO.setSeminarpoolId(sourceEntity.getSeminarpool().getId());
        }
        if(sourceEntity.getCourse() != null && sourceEntity.getCourse().getId() != null){
        	targetVO.setCourseId(sourceEntity.getCourse().getId());
        } 
    }


    /**
     * @see org.openuss.seminarpool.CourseSeminarpoolAllocationDao#toSeminarPlaceAllocationInfo(org.openuss.seminarpool.CourseSeminarpoolAllocation)
     */
    public org.openuss.seminarpool.SeminarPlaceAllocationInfo toSeminarPlaceAllocationInfo(final org.openuss.seminarpool.CourseSeminarpoolAllocation entity)
    {
    	if (entity != null) { 
    		SeminarPlaceAllocationInfo targetVO = new SeminarPlaceAllocationInfo();
			toSeminarPlaceAllocationInfo(entity, targetVO);
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
    private org.openuss.seminarpool.CourseSeminarpoolAllocation loadCourseSeminarpoolAllocationFromSeminarPlaceAllocationInfo(org.openuss.seminarpool.SeminarPlaceAllocationInfo seminarPlaceAllocationInfo)
    {
        // @todo implement loadCourseSeminarpoolAllocationFromSeminarPlaceAllocationInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.loadCourseSeminarpoolAllocationFromSeminarPlaceAllocationInfo(org.openuss.seminarpool.SeminarPlaceAllocationInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.seminarpool.CourseSeminarpoolAllocation courseSeminarpoolAllocation = this.load(seminarPlaceAllocationInfo.getId());
        if (courseSeminarpoolAllocation == null)
        {
            courseSeminarpoolAllocation = org.openuss.seminarpool.CourseSeminarpoolAllocation.Factory.newInstance();
        }
        return courseSeminarpoolAllocation;
        */
    }

    
    /**
     * @see org.openuss.seminarpool.CourseSeminarpoolAllocationDao#seminarPlaceAllocationInfoToEntity(org.openuss.seminarpool.SeminarPlaceAllocationInfo)
     */
    public org.openuss.seminarpool.CourseSeminarpoolAllocation seminarPlaceAllocationInfoToEntity(org.openuss.seminarpool.SeminarPlaceAllocationInfo seminarPlaceAllocationInfo)
    {
        // @todo verify behavior of seminarPlaceAllocationInfoToEntity
        org.openuss.seminarpool.CourseSeminarpoolAllocation entity = this.loadCourseSeminarpoolAllocationFromSeminarPlaceAllocationInfo(seminarPlaceAllocationInfo);
        this.seminarPlaceAllocationInfoToEntity(seminarPlaceAllocationInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.seminarpool.CourseSeminarpoolAllocationDao#seminarPlaceAllocationInfoToEntity(org.openuss.seminarpool.SeminarPlaceAllocationInfo, org.openuss.seminarpool.CourseSeminarpoolAllocation)
     */
    public void seminarPlaceAllocationInfoToEntity(
        org.openuss.seminarpool.SeminarPlaceAllocationInfo sourceVO,
        org.openuss.seminarpool.CourseSeminarpoolAllocation targetEntity,
        boolean copyIfNull)
    {
        super.seminarPlaceAllocationInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}