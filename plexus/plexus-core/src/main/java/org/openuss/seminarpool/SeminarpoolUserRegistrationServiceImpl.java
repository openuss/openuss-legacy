// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.openuss.desktop.DesktopInfo;
import org.openuss.newsletter.MailDetail;
import org.openuss.newsletter.NewsletterInfo;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;

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
			 Collection conditionValue)
			throws Exception {
		Validate.notNull(userRegistrationInfo, "handleEditUserRegistration UserRegistrationInfo cannot be null");
		Validate.notNull(userRegistrationInfo.getSeminarPriorityList(), "handleEditUserRegistration SeminarPriorities cannot be null");
		SeminarUserRegistration targetEntity = 	getSeminarUserRegistrationDao().seminarUserRegistrationInfoToEntity(userRegistrationInfo);
		if ( conditionValue != null ) {
			for (SeminarUserConditionValueInfo seminarUserConditionInfo : (Collection<SeminarUserConditionValueInfo>)conditionValue){
				SeminarUserConditionValue conditionValueEntity = getSeminarUserConditionValueDao().seminarUserConditionValueInfoToEntity(seminarUserConditionInfo);
				targetEntity.addUserCondition(conditionValueEntity);
				conditionValueEntity.setSeminarUserRegistration(targetEntity);
				
			}
		}
		return getSeminarUserRegistrationDao().create(targetEntity).getId();
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
		List<SeminarUserConditionValueInfo> list = new ArrayList<SeminarUserConditionValueInfo>();
		for ( SeminarUserConditionValue conditionValue : userRegistration.getSeminarUserConditionValue()) {
			list.add(getSeminarUserConditionValueDao().toSeminarUserConditionValueInfo(conditionValue));
		}
		return list;
	}

	@Override
	protected void handleEditUserRegistration(
			SeminarUserRegistrationInfo seminarUserRegistrationInfo,
			Collection userConditions) throws Exception {
		Validate.notNull(seminarUserRegistrationInfo, "handleEditUserRegistration UserRegistrationInfo cannot be null");
		Validate.notNull(seminarUserRegistrationInfo.getSeminarPriorityList(), "handleEditUserRegistration SeminarPriorities cannot be null");
		SeminarUserRegistration targetEntity = 	getSeminarUserRegistrationDao().load(seminarUserRegistrationInfo.getId());
		for (SeminarPriority priority : targetEntity.getSeminarPriority()){
			priority.getCourseSeminarPoolAllocation().getSeminarPriority().remove(priority);
			priority.setSeminarUserRegistration(null);
		}
		targetEntity.getSeminarPriority().clear();
		targetEntity = 	getSeminarUserRegistrationDao().seminarUserRegistrationInfoToEntity(seminarUserRegistrationInfo);
		if ( userConditions != null ) {
			Collection<SeminarUserConditionValue> userConditionValueEntityList = new ArrayList<SeminarUserConditionValue>();
			for (SeminarUserConditionValueInfo seminarUserConditionInfo : (Collection<SeminarUserConditionValueInfo>)userConditions){
				SeminarUserConditionValue conditionValueEntity = getSeminarUserConditionValueDao().seminarUserConditionValueInfoToEntity(seminarUserConditionInfo);
				userConditionValueEntityList.add(conditionValueEntity);
			}
			targetEntity.setSeminarUserConditionValue(userConditionValueEntityList);
		}
		getSeminarUserRegistrationDao().update(targetEntity);
	}

	@Override
	protected void handleUnregisterUserFromSeminar(
			SeminarUserRegistrationInfo userRegistrationInfo) throws Exception {
		Validate.notNull(userRegistrationInfo, "handleUnregisterUserFromSeminar ==> userRegistrationInfo cannot be null");
		Validate.notNull(userRegistrationInfo.getSeminarpoolId(), "handleUnregisterUserFromSeminar ==> userRegistrationInfo.getSeminarpoolId() cannot be null");		
		Seminarpool seminarpoolEntity = getSeminarpoolDao().load(userRegistrationInfo.getSeminarpoolId());
		seminarpoolEntity.removeRegistration(getSeminarUserRegistrationDao().load(userRegistrationInfo.getId()));
		this.getSeminarUserRegistrationDao().remove(userRegistrationInfo.getId());
	}

	@Override
	protected SeminarUserRegistrationInfo handleFindSeminarUserRegistrationById(Long id)
			throws Exception {
		Validate.notNull(id, "handleFindSeminarUserRegistrationById ==> id cannot be null");
		SeminarUserRegistration seminarUserRegistrationEntity = getSeminarUserRegistrationDao().load(id);
		return getSeminarUserRegistrationDao().toSeminarUserRegistrationInfo(seminarUserRegistrationEntity);
	}

	@Override
	protected SeminarUserRegistrationInfo handleFindSeminarUserRegistrationByUserAndSeminarpool(
			Long userId, Long seminarpoolId) throws Exception {
		SeminarUserRegistrationInfo sem = null;
		if(userId != null && seminarpoolId != null){
			SeminarUserRegistration semEntity = getSeminarUserRegistrationDao().findByUserAndSeminarpool(this.getUserDao().load(userId), this.getSeminarpoolDao().load(seminarpoolId));
			sem = this.getSeminarUserRegistrationDao().toSeminarUserRegistrationInfo(semEntity);
		}
		if(sem == null){
			sem = new SeminarUserRegistrationInfo();
		}
		return sem;
	}

	@Override
	protected List handleGetDetailUserRegistrationById(
			Long seminarUserRegistrationId) throws Exception {
		Validate.notNull(seminarUserRegistrationId, "handleFindSeminarUserRegistrationById ==> seminarUserRegistrationId cannot be null");
		SeminarUserRegistration userRegistration = getSeminarUserRegistrationDao().load(seminarUserRegistrationId);
		List<SeminarPriorityDetailInfo> infoList = new ArrayList<SeminarPriorityDetailInfo>();
		for( SeminarPriority entity : userRegistration.getSeminarPriority()){
			infoList.add(mapSeminarPriorityEntityToDetailInfo(entity));
		}		
		return infoList;
	}
	
	private SeminarPriorityDetailInfo mapSeminarPriorityEntityToDetailInfo(
			SeminarPriority entity) {
		SeminarPriorityDetailInfo detailInfo = new SeminarPriorityDetailInfo();
		detailInfo.setCourseAllocationId(entity.getCourseSeminarPoolAllocation().getId());
		detailInfo.setId(entity.getId());
		detailInfo.setSeminarUserRegistrationId(entity.getSeminarUserRegistration().getId());
		detailInfo.setUserId(entity.getSeminarUserRegistration().getUser().getId());
		detailInfo.setUserFirstName(entity.getSeminarUserRegistration().getUser().getFirstName());
		detailInfo.setUserLastName(entity.getSeminarUserRegistration().getUser().getLastName());
		detailInfo.setPriority(entity.getPriority());
		detailInfo.setCourseName(entity.getCourseSeminarPoolAllocation().getCourse().getName());
		return detailInfo;
	}

	@Override
	protected boolean handleApplyUserByPassword(String password, Long userId,
			Long seminarpoolId) throws Exception {
		Validate.notNull(userId, "handleApplyUserByPassword ==> userId cannot be null");
		Validate.notNull(seminarpoolId, "handleApplyUserByPassword ==> seminarpoolId cannot be null");
		User user = getUserDao().load(userId);
		Seminarpool seminarpool = getSeminarpoolDao().load(seminarpoolId);
		if ( seminarpool.isPasswordCorrect(password) ) {
			getSecurityService().setPermissions(user, seminarpool,
					LectureAclEntry.COURSE_PARTICIPANT);
			return true;
		}
		return false;

	}

	@Override
	protected void handleInformParticipantsByMail(Long seminarpoolId)
			throws Exception {
		String assignedcourses="";
		Validate.notNull(seminarpoolId, "handleInformParticipantsByMail ==> seminarpoolId connot be null");
		Seminarpool seminarpool = getSeminarpoolDao().load(seminarpoolId);
		Map<String, String> parameters = new HashMap<String, String>();
		for(SeminarUserRegistration sur : seminarpool.getSeminarUserRegistration()){
		parameters.put("seminarpoolname", "" + seminarpool.getName() + "(" + seminarpool.getShortcut() + ")");
		List<SeminarPlaceAllocationInfo> courseList = this.getSeminarpoolAdministrationService().getAllocationsByUserAndSeminarpool(sur.getUser().getId(), seminarpoolId);
		for(SeminarPlaceAllocationInfo spai : courseList){
			assignedcourses += spai.getCourseName()+"<br />";
		}
		parameters.put("courses", assignedcourses);
		getMessageService().sendMessage(seminarpool.getName() + "(" + seminarpool.getShortcut() + ")",
				"seminarpool.application.subject", "seminarpoolapplication", parameters, sur.getUser());
		}
	}

	@Override
	protected void handleSetBookmarksOnMyUniPage(Long seminarpoolId)
			throws Exception {
		Validate.notNull(seminarpoolId, "handleSetBookmarksOnMyUniPage ==> seminarpoolId connot be null");
		Seminarpool seminarpool = getSeminarpoolDao().load(seminarpoolId);
		for(SeminarUserRegistration sur : seminarpool.getSeminarUserRegistration()){
			DesktopInfo desktopInfo = getDesktopService2().findDesktopByUser(sur.getUser().getId()); 
			List<SeminarPlaceAllocationInfo> courseList = this.getSeminarpoolAdministrationService().getAllocationsByUserAndSeminarpool(sur.getUser().getId(), seminarpoolId);
			for(SeminarPlaceAllocationInfo spai : courseList){
				this.getDesktopService2().linkCourse(desktopInfo.getId(), spai.getCourseId());
			}
		}
	}

}