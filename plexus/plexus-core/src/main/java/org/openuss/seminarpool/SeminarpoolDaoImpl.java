// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;
/**
 * @see org.openuss.seminarpool.Seminarpool
 */
public class SeminarpoolDaoImpl
    extends org.openuss.seminarpool.SeminarpoolDaoBase
{
    /**
     * @see org.openuss.seminarpool.SeminarpoolDao#toSeminarpoolInfo(org.openuss.seminarpool.Seminarpool, org.openuss.seminarpool.SeminarpoolInfo)
     */
    public void toSeminarpoolInfo(
        org.openuss.seminarpool.Seminarpool sourceEntity,
        org.openuss.seminarpool.SeminarpoolInfo targetVO)
    {
        // @todo verify behavior of toSeminarpoolInfo
        super.toSeminarpoolInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.seminarpool.SeminarpoolDao#toSeminarpoolInfo(org.openuss.seminarpool.Seminarpool)
     */
    public org.openuss.seminarpool.SeminarpoolInfo toSeminarpoolInfo(final org.openuss.seminarpool.Seminarpool entity)
    {
        // @todo verify behavior of toSeminarpoolInfo
        return super.toSeminarpoolInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.seminarpool.Seminarpool loadSeminarpoolFromSeminarpoolInfo(org.openuss.seminarpool.SeminarpoolInfo seminarpoolInfo)
    {
        // @todo implement loadSeminarpoolFromSeminarpoolInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.loadSeminarpoolFromSeminarpoolInfo(org.openuss.seminarpool.SeminarpoolInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.seminarpool.Seminarpool seminarpool = this.load(seminarpoolInfo.getId());
        if (seminarpool == null)
        {
            seminarpool = org.openuss.seminarpool.Seminarpool.Factory.newInstance();
        }
        return seminarpool;
        */
    }

    
    /**
     * @see org.openuss.seminarpool.SeminarpoolDao#seminarpoolInfoToEntity(org.openuss.seminarpool.SeminarpoolInfo)
     */
    public org.openuss.seminarpool.Seminarpool seminarpoolInfoToEntity(org.openuss.seminarpool.SeminarpoolInfo seminarpoolInfo)
    {
        // @todo verify behavior of seminarpoolInfoToEntity
        org.openuss.seminarpool.Seminarpool entity = this.loadSeminarpoolFromSeminarpoolInfo(seminarpoolInfo);
        this.seminarpoolInfoToEntity(seminarpoolInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.seminarpool.SeminarpoolDao#seminarpoolInfoToEntity(org.openuss.seminarpool.SeminarpoolInfo, org.openuss.seminarpool.Seminarpool)
     */
    public void seminarpoolInfoToEntity(
        org.openuss.seminarpool.SeminarpoolInfo sourceVO,
        org.openuss.seminarpool.Seminarpool targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of seminarpoolInfoToEntity
        super.seminarpoolInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}