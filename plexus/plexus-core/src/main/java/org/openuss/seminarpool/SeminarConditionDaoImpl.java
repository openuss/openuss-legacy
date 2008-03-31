// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;
/**
 * @see org.openuss.seminarpool.SeminarCondition
 */
public class SeminarConditionDaoImpl
    extends org.openuss.seminarpool.SeminarConditionDaoBase
{
    /**
     * @see org.openuss.seminarpool.SeminarConditionDao#toSeminarConditionInfo(org.openuss.seminarpool.SeminarCondition, org.openuss.seminarpool.SeminarConditionInfo)
     */
    public void toSeminarConditionInfo(
        org.openuss.seminarpool.SeminarCondition sourceEntity,
        org.openuss.seminarpool.SeminarConditionInfo targetVO)
    {
        // @todo verify behavior of toSeminarConditionInfo
        super.toSeminarConditionInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.seminarpool.SeminarConditionDao#toSeminarConditionInfo(org.openuss.seminarpool.SeminarCondition)
     */
    public org.openuss.seminarpool.SeminarConditionInfo toSeminarConditionInfo(final org.openuss.seminarpool.SeminarCondition entity)
    {
        // @todo verify behavior of toSeminarConditionInfo
        return super.toSeminarConditionInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.seminarpool.SeminarCondition loadSeminarConditionFromSeminarConditionInfo(org.openuss.seminarpool.SeminarConditionInfo seminarConditionInfo)
    {
        // @todo implement loadSeminarConditionFromSeminarConditionInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.loadSeminarConditionFromSeminarConditionInfo(org.openuss.seminarpool.SeminarConditionInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.seminarpool.SeminarCondition seminarCondition = this.load(seminarConditionInfo.getId());
        if (seminarCondition == null)
        {
            seminarCondition = org.openuss.seminarpool.SeminarCondition.Factory.newInstance();
        }
        return seminarCondition;
        */
    }

    
    /**
     * @see org.openuss.seminarpool.SeminarConditionDao#seminarConditionInfoToEntity(org.openuss.seminarpool.SeminarConditionInfo)
     */
    public org.openuss.seminarpool.SeminarCondition seminarConditionInfoToEntity(org.openuss.seminarpool.SeminarConditionInfo seminarConditionInfo)
    {
        // @todo verify behavior of seminarConditionInfoToEntity
        org.openuss.seminarpool.SeminarCondition entity = this.loadSeminarConditionFromSeminarConditionInfo(seminarConditionInfo);
        this.seminarConditionInfoToEntity(seminarConditionInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.seminarpool.SeminarConditionDao#seminarConditionInfoToEntity(org.openuss.seminarpool.SeminarConditionInfo, org.openuss.seminarpool.SeminarCondition)
     */
    public void seminarConditionInfoToEntity(
        org.openuss.seminarpool.SeminarConditionInfo sourceVO,
        org.openuss.seminarpool.SeminarCondition targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of seminarConditionInfoToEntity
        super.seminarConditionInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}