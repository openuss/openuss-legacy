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
        super.toSeminarPrioritiesInfo(sourceEntity, targetVO);
        if (sourceEntity.getCourseSeminarPoolAllocation() != null){
        	targetVO.setCourseId(sourceEntity.getCourseSeminarPoolAllocation().getId());
        }
        if ( sourceEntity.getSeminarUserRegistration() != null ) {
        	targetVO.setSeminarUserRegistrationId(sourceEntity.getSeminarUserRegistration().getId());
        }
    }


    /**
     * @see org.openuss.seminarpool.SeminarPriorityDao#toSeminarPrioritiesInfo(org.openuss.seminarpool.SeminarPriority)
     */
    public SeminarPrioritiesInfo toSeminarPrioritiesInfo(final SeminarPriority entity)
    {
    	if ( entity != null ){
    		SeminarPrioritiesInfo infoVO = new SeminarPrioritiesInfo();
    		toSeminarPrioritiesInfo(entity, infoVO);
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
    private SeminarPriority loadSeminarPriorityFromSeminarPrioritiesInfo(SeminarPrioritiesInfo seminarPrioritiesInfo)
    {

    	SeminarPriority seminarPriority = null;
        if ( seminarPrioritiesInfo != null && seminarPrioritiesInfo.getId() != null ) {
        	seminarPriority = this.load(seminarPrioritiesInfo.getId());
        }
        if ( seminarPriority == null){
        	seminarPriority = SeminarPriority.Factory.newInstance();
        }
        return seminarPriority;

    }

    
    /**
     * @see org.openuss.seminarpool.SeminarPriorityDao#seminarPrioritiesInfoToEntity(org.openuss.seminarpool.SeminarPrioritiesInfo)
     */
    public SeminarPriority seminarPrioritiesInfoToEntity(SeminarPrioritiesInfo seminarPrioritiesInfo)
    {
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
        super.seminarPrioritiesInfoToEntity(sourceVO, targetEntity, copyIfNull);
        if (sourceVO.getCourseId() != null ) {
        	targetEntity.setCourseSeminarPoolAllocation(getCourseSeminarpoolAllocationDao().load(sourceVO.getCourseId()));
        }
        if (sourceVO.getSeminarUserRegistrationId() != null ) {
        	targetEntity.setSeminarUserRegistration(getSeminarUserRegistrationDao().load(sourceVO.getSeminarUserRegistrationId()));
        }
    }


	public SeminarPriority seminarPriorityDetailInfoToEntity(
			SeminarPriorityDetailInfo seminarPriorityDetailInfo) {
		throw new UnsupportedOperationException();
	}

}