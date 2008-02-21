// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;
/**
 * @see org.openuss.seminarpool.SeminarUserConditionValue
 */
public class SeminarUserConditionValueDaoImpl
    extends org.openuss.seminarpool.SeminarUserConditionValueDaoBase
{
    /**
     * @see org.openuss.seminarpool.SeminarUserConditionValueDao#toSeminarUserConditionValueInfo(org.openuss.seminarpool.SeminarUserConditionValue, org.openuss.seminarpool.SeminarUserConditionValueInfo)
     */
    public void toSeminarUserConditionValueInfo(
        org.openuss.seminarpool.SeminarUserConditionValue sourceEntity,
        org.openuss.seminarpool.SeminarUserConditionValueInfo targetVO)
    {
        // @todo verify behavior of toSeminarUserConditionValueInfo
        super.toSeminarUserConditionValueInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.seminarpool.SeminarUserConditionValueDao#toSeminarUserConditionValueInfo(org.openuss.seminarpool.SeminarUserConditionValue)
     */
    public org.openuss.seminarpool.SeminarUserConditionValueInfo toSeminarUserConditionValueInfo(final org.openuss.seminarpool.SeminarUserConditionValue entity)
    {
        // @todo verify behavior of toSeminarUserConditionValueInfo
        return super.toSeminarUserConditionValueInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.seminarpool.SeminarUserConditionValue loadSeminarUserConditionValueFromSeminarUserConditionValueInfo(org.openuss.seminarpool.SeminarUserConditionValueInfo seminarUserConditionValueInfo)
    {
        // @todo implement loadSeminarUserConditionValueFromSeminarUserConditionValueInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.loadSeminarUserConditionValueFromSeminarUserConditionValueInfo(org.openuss.seminarpool.SeminarUserConditionValueInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.seminarpool.SeminarUserConditionValue seminarUserConditionValue = this.load(seminarUserConditionValueInfo.getId());
        if (seminarUserConditionValue == null)
        {
            seminarUserConditionValue = org.openuss.seminarpool.SeminarUserConditionValue.Factory.newInstance();
        }
        return seminarUserConditionValue;
        */
    }

    
    /**
     * @see org.openuss.seminarpool.SeminarUserConditionValueDao#seminarUserConditionValueInfoToEntity(org.openuss.seminarpool.SeminarUserConditionValueInfo)
     */
    public org.openuss.seminarpool.SeminarUserConditionValue seminarUserConditionValueInfoToEntity(org.openuss.seminarpool.SeminarUserConditionValueInfo seminarUserConditionValueInfo)
    {
        // @todo verify behavior of seminarUserConditionValueInfoToEntity
        org.openuss.seminarpool.SeminarUserConditionValue entity = this.loadSeminarUserConditionValueFromSeminarUserConditionValueInfo(seminarUserConditionValueInfo);
        this.seminarUserConditionValueInfoToEntity(seminarUserConditionValueInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.seminarpool.SeminarUserConditionValueDao#seminarUserConditionValueInfoToEntity(org.openuss.seminarpool.SeminarUserConditionValueInfo, org.openuss.seminarpool.SeminarUserConditionValue)
     */
    public void seminarUserConditionValueInfoToEntity(
        org.openuss.seminarpool.SeminarUserConditionValueInfo sourceVO,
        org.openuss.seminarpool.SeminarUserConditionValue targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of seminarUserConditionValueInfoToEntity
        super.seminarUserConditionValueInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}