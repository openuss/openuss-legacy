// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.openuss.security.User;

/**
 * @see org.openuss.seminarpool.SeminarpoolUserRegistrationService
 */
public class SeminarpoolUserRegistrationServiceImpl
    extends org.openuss.seminarpool.SeminarpoolUserRegistrationServiceBase
{

    /**
     * @see org.openuss.seminarpool.SeminarpoolUserRegistrationService#removeUserRegistration(java.lang.Long)
     */
    protected void handleRemoveUserRegistration(java.lang.Long userRegistrationId)
        throws java.lang.Exception
    {
    	Validate.notNull(userRegistrationId, "handleRemoveUserRegistration userRegistrationId cannot be null");
    	getSeminarUserRegistrationDao().remove(userRegistrationId);
    }

	@Override
	protected Long handleRegisterUserForSeminarpool(
			SeminarUserRegistrationInfo userRegistrationInfo,
			Collection seminarPriorities, Collection conditionValue)
			throws Exception {

		return getSeminarUserRegistrationDao().create(mapSeminarUserRegistrationInfoToEntity(userRegistrationInfo, conditionValue, seminarPriorities)).getId();
	}





	@Override
	protected List handleFindConditionValuesByUserAndSeminarpool(Long userId,
			Long seminarpoolId) throws Exception {
		Validate.notNull(userId, "SeminarpoolUserRegistrationService.handleFindConditionValuesByUserAndSeminarpool ==> userId cannot be Null");
		Validate.notNull(seminarpoolId, "SeminarpoolUserRegistrationService.handleFindConditionValuesByUserAndSeminarpool ==> seminarpoolId cannot be Null");
		Seminarpool seminarpool = getSeminarpoolDao().load(seminarpoolId);
		User user = getUserDao().load(userId);
		SeminarUserRegistration seminarUserRegistration = getSeminarUserRegistrationDao().findByUserAndSeminarpool(user, seminarpool);
		return findConditionValueBySeminarUserRegistration(seminarUserRegistration);
	}





	@Override
	protected List handleFindUserConditionValuesBySeminarUserRegistration(
			SeminarUserRegistrationInfo seminarUserRegistration)
			throws Exception {
		Validate.notNull(seminarUserRegistration , "SeminarpoolUserRegistrationService.handleFindUserConditionValuesBySeminarUserRegistration ==> seminarUserRegistration cannot be Null");
		SeminarUserRegistration userRegistration = getSeminarUserRegistrationDao().load(seminarUserRegistration.getId());
		return findConditionValueBySeminarUserRegistration(userRegistration);
	}
	
	private List<SeminarUserConditionValueInfo> findConditionValueBySeminarUserRegistration(SeminarUserRegistration userRegistration) throws Exception {
		Set<SeminarUserConditionValue> set = userRegistration.getSeminarUserConditionValue();
		Iterator<SeminarUserConditionValue> iter = set.iterator();
		List<SeminarUserConditionValueInfo> list = new ArrayList<SeminarUserConditionValueInfo>();
		while( iter.hasNext() ){
			SeminarUserConditionValueInfo conditionValueInfo = new SeminarUserConditionValueInfo();
			getSeminarUserConditionValueDao().toSeminarUserConditionValueInfo(iter.next(), conditionValueInfo);
			list.add(conditionValueInfo);
		}
		return list;
	}

	@Override
	protected void handleEditUserRegistration(
			SeminarUserRegistrationInfo seminarUserRegistrationInfo,
			Collection userConditions, Collection priorities) throws Exception {

		getSeminarUserRegistrationDao().update(mapSeminarUserRegistrationInfoToEntity(seminarUserRegistrationInfo, userConditions, priorities));
	}
	
	private SeminarUserRegistration mapSeminarUserRegistrationInfoToEntity(
			SeminarUserRegistrationInfo seminarUserRegistrationInfo,
			Collection userConditions, Collection priorities) throws Exception{
		Validate.notNull(seminarUserRegistrationInfo, "handleEditUserRegistration UserRegistrationInfo cannot be null");
		Validate.notNull(priorities, "handleEditUserRegistration SeminarPriorities cannot be null");
		SeminarUserRegistration targetEntity = SeminarUserRegistration.Factory.newInstance();
		getSeminarUserRegistrationDao().seminarUserRegistrationInfoToEntity(seminarUserRegistrationInfo, targetEntity, true);
		if ( priorities != null ){
			Iterator iter = priorities.iterator();
			while( iter.hasNext()){
				SeminarPriority seminarPriorityEntity = SeminarPriority.Factory.newInstance();
				getSeminarPriorityDao().seminarPrioritiesInfoToEntity((SeminarPrioritiesInfo)iter.next(), seminarPriorityEntity, true);
				targetEntity.addPriority(seminarPriorityEntity);
				seminarPriorityEntity.setSeminarUserRegistration(targetEntity);
			}
			
		}
		if ( userConditions != null ) {
			Iterator iter = userConditions.iterator();
			while ( iter.hasNext() ){
				SeminarUserConditionValue conditionValueEntity = SeminarUserConditionValue.Factory.newInstance();
				getSeminarUserConditionValueDao().seminarUserConditionValueInfoToEntity((SeminarUserConditionValueInfo)iter.next(), conditionValueEntity, true);
				targetEntity.addUserCondition(conditionValueEntity);
				conditionValueEntity.setSeminarUserRegistration(targetEntity);
			}
		}
		return targetEntity;
	}

}