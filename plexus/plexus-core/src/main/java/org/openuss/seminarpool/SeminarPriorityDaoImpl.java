// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;
/**
 * @see org.openuss.seminarpool.SeminarPriority
 */
public class SeminarPriorityDaoImpl
    extends org.openuss.seminarpool.SeminarPriorityDaoBase
{
    /**
     * @see org.openuss.seminarpool.SeminarPriorityDao#toSeminarPrioritiesInfo(org.openuss.seminarpool.SeminarPriority, org.openuss.seminarpool.SeminarPrioritiesInfo)
     */
    public void toSeminarPrioritiesInfo(
        org.openuss.seminarpool.SeminarPriority sourceEntity,
        org.openuss.seminarpool.SeminarPrioritiesInfo targetVO)
    {
        // @todo verify behavior of toSeminarPrioritiesInfo
        super.toSeminarPrioritiesInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.seminarpool.SeminarPriorityDao#toSeminarPrioritiesInfo(org.openuss.seminarpool.SeminarPriority)
     */
    public org.openuss.seminarpool.SeminarPrioritiesInfo toSeminarPrioritiesInfo(final org.openuss.seminarpool.SeminarPriority entity)
    {
        // @todo verify behavior of toSeminarPrioritiesInfo
        return super.toSeminarPrioritiesInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.seminarpool.SeminarPriority loadSeminarPriorityFromSeminarPrioritiesInfo(org.openuss.seminarpool.SeminarPrioritiesInfo seminarPrioritiesInfo)
    {
        // @todo implement loadSeminarPriorityFromSeminarPrioritiesInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.loadSeminarPriorityFromSeminarPrioritiesInfo(org.openuss.seminarpool.SeminarPrioritiesInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.seminarpool.SeminarPriority seminarPriority = this.load(seminarPrioritiesInfo.getId());
        if (seminarPriority == null)
        {
            seminarPriority = org.openuss.seminarpool.SeminarPriority.Factory.newInstance();
        }
        return seminarPriority;
        */
    }

    
    /**
     * @see org.openuss.seminarpool.SeminarPriorityDao#seminarPrioritiesInfoToEntity(org.openuss.seminarpool.SeminarPrioritiesInfo)
     */
    public org.openuss.seminarpool.SeminarPriority seminarPrioritiesInfoToEntity(org.openuss.seminarpool.SeminarPrioritiesInfo seminarPrioritiesInfo)
    {
        // @todo verify behavior of seminarPrioritiesInfoToEntity
        org.openuss.seminarpool.SeminarPriority entity = this.loadSeminarPriorityFromSeminarPrioritiesInfo(seminarPrioritiesInfo);
        this.seminarPrioritiesInfoToEntity(seminarPrioritiesInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.seminarpool.SeminarPriorityDao#seminarPrioritiesInfoToEntity(org.openuss.seminarpool.SeminarPrioritiesInfo, org.openuss.seminarpool.SeminarPriority)
     */
    public void seminarPrioritiesInfoToEntity(
        org.openuss.seminarpool.SeminarPrioritiesInfo sourceVO,
        org.openuss.seminarpool.SeminarPriority targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of seminarPrioritiesInfoToEntity
        super.seminarPrioritiesInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}