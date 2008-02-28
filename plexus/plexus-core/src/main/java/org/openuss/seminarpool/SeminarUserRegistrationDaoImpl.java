// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;




/**
 * @see org.openuss.seminarpool.SeminarUserRegistration
 */
public class SeminarUserRegistrationDaoImpl
    extends org.openuss.seminarpool.SeminarUserRegistrationDaoBase
{
    /**
     * @see org.openuss.seminarpool.SeminarUserRegistrationDao#toSeminarUserRegistrationInfo(org.openuss.seminarpool.SeminarUserRegistration, org.openuss.seminarpool.SeminarUserRegistrationInfo)
     */
    public void toSeminarUserRegistrationInfo(
        org.openuss.seminarpool.SeminarUserRegistration sourceEntity,
        org.openuss.seminarpool.SeminarUserRegistrationInfo targetVO)
    {
        super.toSeminarUserRegistrationInfo(sourceEntity, targetVO);
        targetVO.setSeminarpoolId(sourceEntity.getSeminarpool().getId());
        targetVO.setUserId(sourceEntity.getUser().getId());
        if (sourceEntity.getSeminarPriority() != null && sourceEntity.getSeminarPriority().size() > 0){
        	List<SeminarPrioritiesInfo> seminarPriorityInfoList = new ArrayList<SeminarPrioritiesInfo>();
        	for (SeminarPriority seminarPriorityEntity : sourceEntity.getSeminarPriority()){
        		getSeminarPriorityDao().toSeminarPrioritiesInfo(seminarPriorityEntity);
        		seminarPriorityInfoList.add(getSeminarPriorityDao().toSeminarPrioritiesInfo(seminarPriorityEntity));        		
        	}
        	targetVO.setSeminarPriorityList(seminarPriorityInfoList);
        }
    }
    
    /**
     * @see org.openuss.seminarpool.SeminarUserRegistrationDao#toSeminarUserRegistrationInfo(org.openuss.seminarpool.SeminarUserRegistration)
     */
    public org.openuss.seminarpool.SeminarUserRegistrationInfo toSeminarUserRegistrationInfo(final org.openuss.seminarpool.SeminarUserRegistration entity)
    {
        if(entity != null){
        	SeminarUserRegistrationInfo info = new SeminarUserRegistrationInfo();
        	toSeminarUserRegistrationInfo(entity, info);
        	return info;
        } else {
        	return null;
        }
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private SeminarUserRegistration loadSeminarUserRegistrationFromSeminarUserRegistrationInfo(SeminarUserRegistrationInfo seminarUserRegistrationInfo)
    {
        SeminarUserRegistration seminarUserRegistration = null;
        if ( seminarUserRegistrationInfo != null && seminarUserRegistrationInfo.getId() != null ) {
        	seminarUserRegistration = this.load(seminarUserRegistrationInfo.getId());
        }
        if ( seminarUserRegistration == null){
        	seminarUserRegistration = SeminarUserRegistration.Factory.newInstance();
        }
        
        return seminarUserRegistration;     
    }

    
    /**
     * @see org.openuss.seminarpool.SeminarUserRegistrationDao#seminarUserRegistrationInfoToEntity(org.openuss.seminarpool.SeminarUserRegistrationInfo)
     */
    public SeminarUserRegistration seminarUserRegistrationInfoToEntity(SeminarUserRegistrationInfo seminarUserRegistrationInfo)
    {
        org.openuss.seminarpool.SeminarUserRegistration entity = this.loadSeminarUserRegistrationFromSeminarUserRegistrationInfo(seminarUserRegistrationInfo);
        this.seminarUserRegistrationInfoToEntity(seminarUserRegistrationInfo, entity, true);        
        return entity;
    }


    /**
     * @see org.openuss.seminarpool.SeminarUserRegistrationDao#seminarUserRegistrationInfoToEntity(org.openuss.seminarpool.SeminarUserRegistrationInfo, org.openuss.seminarpool.SeminarUserRegistration)
     */
    public void seminarUserRegistrationInfoToEntity(
        org.openuss.seminarpool.SeminarUserRegistrationInfo sourceVO,
        org.openuss.seminarpool.SeminarUserRegistration targetEntity,
        boolean copyIfNull)
    {
        super.seminarUserRegistrationInfoToEntity(sourceVO, targetEntity, copyIfNull);
        if ( sourceVO.getSeminarpoolId() != null ) {
        	targetEntity.setSeminarpool(getSeminarpoolDao().load(sourceVO.getSeminarpoolId()));
        }
        if ( sourceVO.getUserId() != null ){
        	targetEntity.setUser(getUserDao().load(sourceVO.getUserId()));
        }
		if ( sourceVO.getSeminarPriorityList() != null ){
			for ( SeminarPrioritiesInfo seminarPriorityInfo : (Collection<SeminarPrioritiesInfo>)sourceVO.getSeminarPriorityList()){
				SeminarPriority seminarPriorityEntity = getSeminarPriorityDao().seminarPrioritiesInfoToEntity(seminarPriorityInfo);
				targetEntity.addPriority(seminarPriorityEntity);
				seminarPriorityEntity.setSeminarUserRegistration(targetEntity);				
			}
		}
        
    }
}