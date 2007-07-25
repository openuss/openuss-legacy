// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;
/**
 * @see org.openuss.lecture.University
 */
public class UniversityDaoImpl
    extends org.openuss.lecture.UniversityDaoBase
{
    /**
     * @see org.openuss.lecture.UniversityDao#toUniversityInfo(org.openuss.lecture.University, org.openuss.lecture.UniversityInfo)
     */
    public void toUniversityInfo(
        org.openuss.lecture.University sourceEntity,
        org.openuss.lecture.UniversityInfo targetVO)
    {
        // @todo verify behavior of toUniversityInfo
        super.toUniversityInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.lecture.UniversityDao#toUniversityInfo(org.openuss.lecture.University)
     */
    public org.openuss.lecture.UniversityInfo toUniversityInfo(final org.openuss.lecture.University entity)
    {
        // @todo verify behavior of toUniversityInfo
        return super.toUniversityInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.lecture.University loadUniversityFromUniversityInfo(org.openuss.lecture.UniversityInfo universityInfo)
    {
        // @todo implement loadUniversityFromUniversityInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.lecture.loadUniversityFromUniversityInfo(org.openuss.lecture.UniversityInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.lecture.University university = this.load(universityInfo.getId());
        if (university == null)
        {
            university = org.openuss.lecture.University.Factory.newInstance();
        }
        return university;
        */
    }

    
    /**
     * @see org.openuss.lecture.UniversityDao#universityInfoToEntity(org.openuss.lecture.UniversityInfo)
     */
    public org.openuss.lecture.University universityInfoToEntity(org.openuss.lecture.UniversityInfo universityInfo)
    {
        // @todo verify behavior of universityInfoToEntity
        org.openuss.lecture.University entity = this.loadUniversityFromUniversityInfo(universityInfo);
        this.universityInfoToEntity(universityInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.lecture.UniversityDao#universityInfoToEntity(org.openuss.lecture.UniversityInfo, org.openuss.lecture.University)
     */
    public void universityInfoToEntity(
        org.openuss.lecture.UniversityInfo sourceVO,
        org.openuss.lecture.University targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of universityInfoToEntity
        super.universityInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}