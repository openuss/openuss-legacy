// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;
/**
 * @see org.openuss.seminarpool.Seminarpool
 * @author Stefan Thiemann
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
        super.toSeminarpoolInfo(sourceEntity, targetVO);
        targetVO.setUniversityId(sourceEntity.getUniversity().getId());
    }


    /**
     * @see org.openuss.seminarpool.SeminarpoolDao#toSeminarpoolInfo(org.openuss.seminarpool.Seminarpool)
     */
    public org.openuss.seminarpool.SeminarpoolInfo toSeminarpoolInfo(final org.openuss.seminarpool.Seminarpool entity)
    {
	if ( entity != null ) {
	    SeminarpoolInfo targetInfo =  super.toSeminarpoolInfo(entity);
            targetInfo.setUniversityId(entity.getUniversity().getId());
            return targetInfo;
	}
	return null;
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.seminarpool.Seminarpool loadSeminarpoolFromSeminarpoolInfo(org.openuss.seminarpool.SeminarpoolInfo seminarpoolInfo)
    {
    	Seminarpool seminarpool = null;
        if(seminarpoolInfo != null && seminarpoolInfo.getId() != null){
        	seminarpool = this.load(seminarpoolInfo.getId());
        }
        if(seminarpool == null){
        	seminarpool = Seminarpool.Factory.newInstance();
        }
        return seminarpool;
    }

    
    /**
     * @see org.openuss.seminarpool.SeminarpoolDao#seminarpoolInfoToEntity(org.openuss.seminarpool.SeminarpoolInfo)
     */
    public org.openuss.seminarpool.Seminarpool seminarpoolInfoToEntity(org.openuss.seminarpool.SeminarpoolInfo seminarpoolInfo)
    {
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
        super.seminarpoolInfoToEntity(sourceVO, targetEntity, copyIfNull);
        targetEntity.setUniversity(getUniversityDao().load(sourceVO.getUniversityId()));
    }

}