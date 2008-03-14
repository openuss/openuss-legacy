// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;
/**
 * @see org.openuss.seminarpool.SeminarCondition
 * @author Stefan Thiemann
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
        super.toSeminarConditionInfo(sourceEntity, targetVO);
        if(sourceEntity.getSeminarpool() != null && sourceEntity.getSeminarpool().getId() != null){
        	targetVO.setSeminarpoolId(sourceEntity.getSeminarpool().getId());
        }
    }


    /**
     * @see org.openuss.seminarpool.SeminarConditionDao#toSeminarConditionInfo(org.openuss.seminarpool.SeminarCondition)
     */
    public org.openuss.seminarpool.SeminarConditionInfo toSeminarConditionInfo(final org.openuss.seminarpool.SeminarCondition entity)
    {
    	if (entity != null) { 
    		SeminarConditionInfo targetVO = new SeminarConditionInfo();
			toSeminarConditionInfo(entity, targetVO);
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
    private org.openuss.seminarpool.SeminarCondition loadSeminarConditionFromSeminarConditionInfo(org.openuss.seminarpool.SeminarConditionInfo seminarConditionInfo)
    {
        SeminarCondition seminarCondition = null;
    	if(seminarConditionInfo != null && seminarConditionInfo.getId() != null){
        	seminarCondition =  this.load(seminarConditionInfo.getId());
        }
        if(seminarCondition == null){
        	seminarCondition = SeminarCondition.Factory.newInstance();
        }
        return seminarCondition;
    }

    
    /**
     * @see org.openuss.seminarpool.SeminarConditionDao#seminarConditionInfoToEntity(org.openuss.seminarpool.SeminarConditionInfo)
     */
    public org.openuss.seminarpool.SeminarCondition seminarConditionInfoToEntity(org.openuss.seminarpool.SeminarConditionInfo seminarConditionInfo)
    {
        org.openuss.seminarpool.SeminarCondition entity = this.loadSeminarConditionFromSeminarConditionInfo(seminarConditionInfo);
        this.seminarConditionInfoToEntity(seminarConditionInfo, entity, true);
        if(seminarConditionInfo.getSeminarpoolId() != null){
        	Seminarpool seminarpool = getSeminarpoolDao().load(seminarConditionInfo.getSeminarpoolId());
        	entity.setSeminarpool(seminarpool);
        }
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
        super.seminarConditionInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}