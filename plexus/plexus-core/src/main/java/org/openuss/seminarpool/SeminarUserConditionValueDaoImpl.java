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
        super.toSeminarUserConditionValueInfo(sourceEntity, targetVO);
        if ( sourceEntity.getSeminarCondition() != null ) {
        	SeminarCondition condition = sourceEntity.getSeminarCondition(); 
        	targetVO.setSeminarConditionId(condition.getId());
        	targetVO.setSeminarConditionDescription(condition.getConditionDescription());
        	targetVO.setSeminarConditionFieldDescription(condition.getFieldDescription());
        	targetVO.setSeminarConditionType(condition.getFieldType());
        }
        if ( sourceEntity.getSeminarUserRegistration() != null ) {
        	targetVO.setSeminarUserRegistrationId(sourceEntity.getSeminarUserRegistration().getId());
        }
		if ( Boolean.FALSE.toString().equals(sourceEntity.getConditionValue()) ){
			targetVO.setConditionValueBoolean(Boolean.FALSE);
		} else if ( Boolean.TRUE.toString().equals(sourceEntity.getConditionValue())){
			targetVO.setConditionValueBoolean(Boolean.TRUE);
		}
    }


    /**
     * @see org.openuss.seminarpool.SeminarUserConditionValueDao#toSeminarUserConditionValueInfo(org.openuss.seminarpool.SeminarUserConditionValue)
     */
    public org.openuss.seminarpool.SeminarUserConditionValueInfo toSeminarUserConditionValueInfo(final org.openuss.seminarpool.SeminarUserConditionValue entity)
    {
    	if ( entity != null ){
    		SeminarUserConditionValueInfo infoVO = new SeminarUserConditionValueInfo();
    		toSeminarUserConditionValueInfo(entity, infoVO);
    		return infoVO;
    	} else {
    		return null; 
    	}
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.seminarpool.SeminarUserConditionValue loadSeminarUserConditionValueFromSeminarUserConditionValueInfo(SeminarUserConditionValueInfo seminarUserConditionValueInfo)
    {
    	SeminarUserConditionValue seminarUserConditionValue = null;
        if ( seminarUserConditionValueInfo != null && seminarUserConditionValueInfo.getId() != null ) {
        	seminarUserConditionValue = this.load(seminarUserConditionValueInfo.getId());
        }
        if ( seminarUserConditionValue == null){
        	seminarUserConditionValue = SeminarUserConditionValue.Factory.newInstance();
        }
        return seminarUserConditionValue;
    }

    
    /**
     * @see org.openuss.seminarpool.SeminarUserConditionValueDao#seminarUserConditionValueInfoToEntity(org.openuss.seminarpool.SeminarUserConditionValueInfo)
     */
    public org.openuss.seminarpool.SeminarUserConditionValue seminarUserConditionValueInfoToEntity(org.openuss.seminarpool.SeminarUserConditionValueInfo seminarUserConditionValueInfo)
    {
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
        super.seminarUserConditionValueInfoToEntity(sourceVO, targetEntity, copyIfNull);
        if ( sourceVO.getSeminarConditionId() != null ) {
        	targetEntity.setSeminarCondition(getSeminarConditionDao().load(sourceVO.getSeminarConditionId()));
        }
        if ( sourceVO.getSeminarUserRegistrationId() != null ){
        	targetEntity.setSeminarUserRegistration(getSeminarUserRegistrationDao().load(sourceVO.getSeminarUserRegistrationId()));
        }
        if ( sourceVO.getConditionValueBoolean() != null ){
        	targetEntity.setConditionValue(sourceVO.getConditionValueBoolean().toString());
        }
    }

}