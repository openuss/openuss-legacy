// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;
/**
 * @see org.openuss.seminarpool.CourseSeminarpoolAllocation
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
        // @todo verify behavior of toCourseSeminarpoolAllocationInfo
        super.toCourseSeminarpoolAllocationInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.seminarpool.CourseSeminarpoolAllocationDao#toCourseSeminarpoolAllocationInfo(org.openuss.seminarpool.CourseSeminarpoolAllocation)
     */
    public org.openuss.seminarpool.CourseSeminarpoolAllocationInfo toCourseSeminarpoolAllocationInfo(final org.openuss.seminarpool.CourseSeminarpoolAllocation entity)
    {
        // @todo verify behavior of toCourseSeminarpoolAllocationInfo
        return super.toCourseSeminarpoolAllocationInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.seminarpool.CourseSeminarpoolAllocation loadCourseSeminarpoolAllocationFromCourseSeminarpoolAllocationInfo(org.openuss.seminarpool.CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo)
    {
        // @todo implement loadCourseSeminarpoolAllocationFromCourseSeminarpoolAllocationInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.loadCourseSeminarpoolAllocationFromCourseSeminarpoolAllocationInfo(org.openuss.seminarpool.CourseSeminarpoolAllocationInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.seminarpool.CourseSeminarpoolAllocation courseSeminarpoolAllocation = this.load(courseSeminarpoolAllocationInfo.getId());
        if (courseSeminarpoolAllocation == null)
        {
            courseSeminarpoolAllocation = org.openuss.seminarpool.CourseSeminarpoolAllocation.Factory.newInstance();
        }
        return courseSeminarpoolAllocation;
        */
    }

    
    /**
     * @see org.openuss.seminarpool.CourseSeminarpoolAllocationDao#courseSeminarpoolAllocationInfoToEntity(org.openuss.seminarpool.CourseSeminarpoolAllocationInfo)
     */
    public org.openuss.seminarpool.CourseSeminarpoolAllocation courseSeminarpoolAllocationInfoToEntity(org.openuss.seminarpool.CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo)
    {
        // @todo verify behavior of courseSeminarpoolAllocationInfoToEntity
        org.openuss.seminarpool.CourseSeminarpoolAllocation entity = this.loadCourseSeminarpoolAllocationFromCourseSeminarpoolAllocationInfo(courseSeminarpoolAllocationInfo);
        this.courseSeminarpoolAllocationInfoToEntity(courseSeminarpoolAllocationInfo, entity, true);
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
        // @todo verify behavior of courseSeminarpoolAllocationInfoToEntity
        super.courseSeminarpoolAllocationInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

    /**
     * @see org.openuss.seminarpool.CourseSeminarpoolAllocationDao#toSeminarPlaceAllocationInfo(org.openuss.seminarpool.CourseSeminarpoolAllocation, org.openuss.seminarpool.SeminarPlaceAllocationInfo)
     */
    public void toSeminarPlaceAllocationInfo(
        org.openuss.seminarpool.CourseSeminarpoolAllocation sourceEntity,
        org.openuss.seminarpool.SeminarPlaceAllocationInfo targetVO)
    {
        // @todo verify behavior of toSeminarPlaceAllocationInfo
        super.toSeminarPlaceAllocationInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.seminarpool.CourseSeminarpoolAllocationDao#toSeminarPlaceAllocationInfo(org.openuss.seminarpool.CourseSeminarpoolAllocation)
     */
    public org.openuss.seminarpool.SeminarPlaceAllocationInfo toSeminarPlaceAllocationInfo(final org.openuss.seminarpool.CourseSeminarpoolAllocation entity)
    {
        // @todo verify behavior of toSeminarPlaceAllocationInfo
        return super.toSeminarPlaceAllocationInfo(entity);
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
        // @todo verify behavior of seminarPlaceAllocationInfoToEntity
        super.seminarPlaceAllocationInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}