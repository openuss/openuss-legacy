// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.openuss.lecture.Course;
import org.openuss.security.Group;
import org.openuss.security.GroupItem;
import org.openuss.security.GroupType;
import org.openuss.security.Membership;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @see org.openuss.seminarpool.SeminarpoolAdministrationService
 */
public class SeminarpoolAdministrationServiceImpl
    extends org.openuss.seminarpool.SeminarpoolAdministrationServiceBase
{

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#createSeminarpool(org.openuss.seminarpool.SeminarpoolInfo, java.lang.Long)
     */
    protected java.lang.Long handleCreateSeminarpool(org.openuss.seminarpool.SeminarpoolInfo seminarpoolInfo, java.lang.Long userId)
        throws java.lang.Exception
    {
		Validate.notNull(seminarpoolInfo, "The Seminarpool cannot be null");
		Validate.notNull(userId, "The User must have a valid ID");
		User user = getSecurityService().getUser(userId);
		Validate.notNull(user, "No valid User found corresponding to the ID " + userId);
		Validate.isTrue(seminarpoolInfo.getId() == null, "The Seminarpool shouldn't have an ID yet");

		seminarpoolInfo.setId(null);

		// Transform ValueObject into Entity
		Seminarpool seminarpoolEntity = this.getSeminarpoolDao().seminarpoolInfoToEntity(seminarpoolInfo);

		// Create a default Membership for the Institute
		Membership membership = Membership.Factory.newInstance();
		seminarpoolEntity.setMembership(membership);

		// Create the Institute
		getSeminarpoolDao().create(seminarpoolEntity);
		Validate.notNull(seminarpoolEntity.getId(), "SeminarpoolDao.handleCreate - Couldn't create Seminarpool");

		seminarpoolInfo.setId(seminarpoolEntity.getId());

		// Create default Groups for Institute
		GroupItem admins = new GroupItem();
		admins.setName("SEMINARPOOL_" + seminarpoolEntity.getId() + "_ADMINS");
		admins.setLabel("autogroup_administrator_label");
		admins.setGroupType(GroupType.ADMINISTRATOR);
		Long adminsId = this.getOrganisationService().createGroup(seminarpoolEntity.getId(), admins);
		Group adminsGroup = this.getGroupDao().load(adminsId);

//		GroupItem assistants = new GroupItem();
//		assistants.setName("SEMINARPOOL_" + seminarpoolEntity.getId() + "_ASSISTANTS");
//		assistants.setLabel("autogroup_assistant_label");
//		assistants.setGroupType(GroupType.ASSISTANT);
//		Long assistantsId = this.getOrganisationService().createGroup(seminarpoolEntity.getId(), assistants);
//		Group assistantsGroup = this.getGroupDao().load(assistantsId);
//
//		GroupItem tutors = new GroupItem();
//		tutors.setName("SEMINARPOOL_" + seminarpoolEntity.getId() + "_TUTORS");
//		tutors.setLabel("autogroup_tutor_label");
//		tutors.setGroupType(GroupType.TUTOR);
//		Long tutorsId = this.getOrganisationService().createGroup(seminarpoolEntity.getId(), tutors);
//		Group tutorsGroup = this.getGroupDao().load(tutorsId);

		// Security
		getSecurityService().createObjectIdentity(seminarpoolEntity, null);
		getSecurityService().setPermissions(adminsGroup, seminarpoolEntity, LectureAclEntry.INSTITUTE_ADMINISTRATION);
//		getSecurityService().setPermissions(assistantsGroup, seminarpoolEntity, LectureAclEntry.INSTITUTE_ASSIST);
//		getSecurityService().setPermissions(tutorsGroup, seminarpoolEntity, LectureAclEntry.INSTITUTE_TUTOR);

		// Add Owner to Members and the group of Administrators
		getOrganisationService().addMember(seminarpoolEntity.getId(), userId);
		getOrganisationService().addUserToGroup(userId, adminsGroup.getId());
		return seminarpoolEntity.getId();
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#updateSeminarpool(org.openuss.seminarpool.SeminarpoolInfo)
     */
    protected void handleUpdateSeminarpool(org.openuss.seminarpool.SeminarpoolInfo seminarpoolInfo)
        throws java.lang.Exception
    {
    	Validate.notNull(seminarpoolInfo, "handleUpdateSeminarpool ==> seminarpoolInfo cannot be null");
    	Validate.notNull(seminarpoolInfo.getId(), "handleUpdateSeminarpool ==> seminarpoolInfo.getID() cannot be null");
    	Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarpoolInfo.getId());
    	getSeminarpoolDao().seminarpoolInfoToEntity(seminarpoolInfo, seminarpoolEntity, false);
    	getSeminarpoolDao().update(seminarpoolEntity);
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#removeSeminarpool(java.lang.Long)
     */
    protected void handleRemoveSeminarpool(java.lang.Long seminarpoolId)
        throws java.lang.Exception
    {
    	Validate.notNull(seminarpoolId, "handleRemoveSeminarpool ==> seminarpoolId cannot be null");
    	getSeminarpoolDao().remove(seminarpoolId);
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#addSeminarpoolAdmin(java.lang.Long, java.lang.Long)
     */
    protected void handleAddSeminarpoolAdmin(java.lang.Long userId, java.lang.Long seminarpoolId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleAddSeminarpoolAdmin(java.lang.Long userId, java.lang.Long seminarpoolId)
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.SeminarpoolAdministrationService.handleAddSeminarpoolAdmin(java.lang.Long userId, java.lang.Long seminarpoolId) Not implemented!");
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#removeSeminarpoolAdmin(java.lang.Long, java.lang.Long)
     */
    protected void handleRemoveSeminarpoolAdmin(java.lang.Long userId, java.lang.Long seminarpoolId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleRemoveSeminarpoolAdmin(java.lang.Long userId, java.lang.Long seminarpoolId)
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.SeminarpoolAdministrationService.handleRemoveSeminarpoolAdmin(java.lang.Long userId, java.lang.Long seminarpoolId) Not implemented!");
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#addSeminar(java.lang.Long, java.lang.Long, java.lang.Integer)
     */
    protected Long handleAddSeminar(CourseSeminarpoolAllocationInfo seminarpoolAllocation,
			Collection courseGroups)
        throws java.lang.Exception
    {
    	Validate.notNull(seminarpoolAllocation, "handleAddSeminar ==> seminarpoolAllocation cannot be null");
    	Validate.notNull(seminarpoolAllocation.getId(), "handleAddSeminar ==> seminarpoolAllocation.getId() cannot be null");    	
    	Validate.notNull(courseGroups, "handleAddSeminar ==> courseGroups cannot be null");
    	Validate.isTrue( courseGroups.size() >= 1, "handleAddSeminar ==> courseGroups cannot be null");
    	Seminarpool seminarpool = getSeminarpoolDao().load(seminarpoolAllocation.getId());
    	Validate.notNull(seminarpool, "handleAddSeminar ==> seminarpool cannot be null");
    	Course course = getCourseDao().load(seminarpoolAllocation.getCourseId());
    	Validate.notNull(course, "handleAddSeminar ==> course cannot be null");
    	CourseSeminarpoolAllocation courseAllocation = CourseSeminarpoolAllocation.Factory.newInstance();
    	courseAllocation.setSeminarpool(seminarpool);
    	courseAllocation.setCourse(course);
    	seminarpool.addCourseAllocation(courseAllocation);
    	Set<CourseGroup> set = new HashSet<CourseGroup>();
    	for (CourseGroupInfo groupInfo : (Collection<CourseGroupInfo>) courseGroups){
    		CourseGroup courseGroupEntity = getCourseGroupDao().courseGroupInfoToEntity(groupInfo);
    		courseGroupEntity.setCourseSeminarpoolAllocation(courseAllocation);
    		set.add(courseGroupEntity);    		
    	}
    	courseAllocation.setCourseGroup(set);
        return getCourseSeminarpoolAllocationDao().create(courseAllocation).getId();    
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#removeSeminar(java.lang.Long, java.lang.Long)
     */
    protected void handleRemoveSeminar(CourseSeminarpoolAllocationInfo courseSeminarpoolAllocation)
        throws java.lang.Exception
    {
    	Validate.notNull(courseSeminarpoolAllocation, "handleRemoveSeminar ==> courseSeminarpoolAllocation cannot be null");
    	Validate.notNull(courseSeminarpoolAllocation.getId(), "handleRemoveSeminar ==> courseSeminarpoolAllocation.getId() cannot be null");
    	CourseSeminarpoolAllocation courseAllocationEntity = getCourseSeminarpoolAllocationDao().load(courseSeminarpoolAllocation.getId());
    	Validate.notNull(courseAllocationEntity, "handleRemoveSeminar ==> Cannot load CourseSeminarpoolAllocation");
    	Seminarpool seminarpoolEntity = courseAllocationEntity.getSeminarpool();
    	seminarpoolEntity.removeCourseAllocation(courseAllocationEntity);
    	getCourseSeminarpoolAllocationDao().remove(courseAllocationEntity);
    	
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#findSeminarpool(java.lang.Long)
     */
    protected org.openuss.seminarpool.SeminarpoolInfo handleFindSeminarpool(java.lang.Long seminarpoolId)
        throws java.lang.Exception
    {
    	Validate.notNull(seminarpoolId, "handleFindSeminarpool ==> seminarpoolId cannot be null");
    	Seminarpool seminapoolEntity = getSeminarpoolDao().load(seminarpoolId);
    	return getSeminarpoolDao().toSeminarpoolInfo(seminapoolEntity);	
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#findSeminarpoolsByUniversity(java.lang.Long)
     */
    protected java.util.List handleFindSeminarpoolsByUniversity(java.lang.Long universityId)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleFindSeminarpoolsByUniversity(java.lang.Long universityId)
        return null;
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#findSeminarpoolsByDepartment(java.lang.Long)
     */
    protected java.util.List handleFindSeminarpoolsByDepartment(java.lang.Long departmentId)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleFindSeminarpoolsByDepartment(java.lang.Long departmentId)
        return null;
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#findSeminarpoolsByInstitute(java.lang.Long)
     */
    protected java.util.List handleFindSeminarpoolsByInstitute(java.lang.Long instituteId)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleFindSeminarpoolsByInstitute(java.lang.Long instituteId)
        return null;
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#findSeminarpoolByDescription(java.lang.String)
     */
    protected java.util.List handleFindSeminarpoolByDescription(java.lang.String description)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleFindSeminarpoolByDescription(java.lang.String description)
        return null;
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#findCoursesInSeminarpool(java.lang.Long)
     */
    protected java.util.List handleFindCoursesInSeminarpool(java.lang.Long seminarpoolId)
        throws java.lang.Exception
    {
    	Validate.notNull(seminarpoolId, "handleFindCoursesInSeminarpool ==> seminarpoolId cannot be null");
    	Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarpoolId);
    	Validate.notNull(seminarpoolEntity, "handleFindCoursesInSeminarpool ==> Cannot load Seminarpool");
    	Set<CourseSeminarpoolAllocation> courseAllocations = seminarpoolEntity.getCourseSeminarpoolAllocation();
    	List<CourseSeminarpoolAllocationInfo> courseAllocationList = new ArrayList<CourseSeminarpoolAllocationInfo>();
    	for (CourseSeminarpoolAllocation courseAllocation : courseAllocations){
    		courseAllocationList.add(getCourseSeminarpoolAllocationDao().toCourseSeminarpoolAllocationInfo(courseAllocation));
    	}    	
        return courseAllocationList;
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#getSeminarpool(java.lang.Long)
     */
    protected org.openuss.seminarpool.SeminarpoolInfo handleGetSeminarpool(java.lang.Long seminarpoolId)
        throws java.lang.Exception
    {
    	Validate.notNull(seminarpoolId, "handleFindCoursesInSeminarpool ==> seminarpoolId cannot be null");
    	Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarpoolId);
    	Validate.notNull(seminarpoolEntity, "handleFindCoursesInSeminarpool ==> Cannot load Seminarpool");
    	return getSeminarpoolDao().toSeminarpoolInfo(getSeminarpoolDao().load(seminarpoolId));
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#getAllSeminarpools()
     */
    protected java.util.List handleGetAllSeminarpools()
        throws java.lang.Exception
    {
    	Collection<Seminarpool> seminarpools = getSeminarpoolDao().loadAll();
    	List<SeminarpoolInfo> seminarpoolInfoList = new ArrayList<SeminarpoolInfo>();
    	for (Seminarpool seminarpool : seminarpools){
    		seminarpoolInfoList.add(getSeminarpoolDao().toSeminarpoolInfo(seminarpool));
    	}
        return seminarpoolInfoList;
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#getRegistrations(java.lang.Long)
     */
    protected java.util.List handleGetRegistrations(java.lang.Long seminarpoolId)
        throws java.lang.Exception
    {
    	Validate.notNull(seminarpoolId, "handleFindCoursesInSeminarpool ==> seminarpoolId cannot be null");
    	Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarpoolId);
    	Validate.notNull(seminarpoolEntity, "handleFindCoursesInSeminarpool ==> Cannot load Seminarpool");
    	Set<SeminarUserRegistration> userRegistrations = seminarpoolEntity.getSeminarUserRegistration();
    	List<SeminarUserRegistrationInfo> userRegistrationsInfoList = new ArrayList<SeminarUserRegistrationInfo>();
    	for (SeminarUserRegistration userRegistration : userRegistrations){
    		List<SeminarPrioritiesInfo> seminarPriorityInfoList = new ArrayList<SeminarPrioritiesInfo>();
    		for (SeminarPriority seminarPriority : userRegistration.getSeminarPriority()){
    			seminarPriorityInfoList.add(getSeminarPriorityDao().toSeminarPrioritiesInfo(seminarPriority));
    		}    		
    		SeminarUserRegistrationInfo seminarUserRegistrationInfo  = getSeminarUserRegistrationDao().toSeminarUserRegistrationInfo(userRegistration);
    		seminarUserRegistrationInfo.setSeminarPriorityList(seminarPriorityInfoList);
    		userRegistrationsInfoList.add(seminarUserRegistrationInfo);
    		
    	}
    	return userRegistrationsInfoList;
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#getRegistrationsByCourse(java.lang.Long, java.lang.Long)
     */
    protected java.util.List handleGetRegistrationsByCourse(java.lang.Long seminarpoolId, java.lang.Long searchCourseId)
        throws java.lang.Exception
    {
    	Validate.notNull(searchCourseId, "handleGetRegistrationsByCourse ==> courseId cannot be null");
    	Validate.notNull(seminarpoolId, "handleGetRegistrationsByCourse ==> seminarpoolId cannot be null");
    	Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarpoolId);
    	Validate.notNull(seminarpoolEntity, "handleGetRegistrationsByCourse ==> Cannot load Seminarpool");
    	Set<SeminarUserRegistration> userRegistrations = seminarpoolEntity.getSeminarUserRegistration();
    	List<SeminarUserRegistrationInfo> userRegistrationsInfoList = new ArrayList<SeminarUserRegistrationInfo>();
    	for (SeminarUserRegistration userRegistration : userRegistrations){
    		List<SeminarPrioritiesInfo> seminarPriorityInfoList = new ArrayList<SeminarPrioritiesInfo>();
    		Long courseId = null;
    		for (SeminarPriority seminarPriority : userRegistration.getSeminarPriority()){
    			courseId = seminarPriority.getCourseSeminarPoolAllocation().getCourse().getId();
    			// map and add SeminarPriority if courseId == searchCourseId
    			if (courseId.equals(searchCourseId)){
    				seminarPriorityInfoList.add(getSeminarPriorityDao().toSeminarPrioritiesInfo(seminarPriority));
    			}
    		}
			// map and add SeminarUserRegistration if courseId == searchCourseId
    		if (courseId != null && courseId.equals(searchCourseId)){
        		SeminarUserRegistrationInfo seminarUserRegistrationInfo  = getSeminarUserRegistrationDao().toSeminarUserRegistrationInfo(userRegistration);
        		seminarUserRegistrationInfo.setSeminarPriorityList(seminarPriorityInfoList);
        		userRegistrationsInfoList.add(seminarUserRegistrationInfo);    			
    		}
    	}
    	return userRegistrationsInfoList;
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#getAllocations(java.lang.Long)
     */
    protected java.util.List handleGetAllocations(java.lang.Long seminarpoolId)
        throws java.lang.Exception
    {
    	Validate.notNull(seminarpoolId, "handleGetAllocations ==> seminarpoolId cannot be null");
    	Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarpoolId);
    	Validate.notNull(seminarpoolEntity, "handleGetAllocations ==> Cannot load Seminarpool");
    	return mapSeminarPlaceAllocation(getCourseSeminarpoolAllocationDao().findSeminarPlaceAllocationBySeminarpool(seminarpoolEntity), seminarpoolId, null);
    }
    
    private List<SeminarPlaceAllocationInfo> mapSeminarPlaceAllocation(List<CourseSeminarpoolAllocation> courseAllocationSet, Long seminarpoolId, Long courseId){
    	List<SeminarPlaceAllocationInfo> placeAllocationList = new ArrayList<SeminarPlaceAllocationInfo>(); 
    	for (CourseSeminarpoolAllocation courseAllocation : courseAllocationSet){
    		Course course = courseAllocation.getCourse();
    		if ( courseId != null){
    			if (!courseId.equals(course.getId())){
    				continue;
    			}
    		}    			
    		Set<CourseGroup> courseGroups = courseAllocation.getCourseGroup();
    		for (CourseGroup courseGroup : courseGroups){    			
    			Set<User> users = courseGroup.getUser();
    			for (User user : users) {
    				SeminarPlaceAllocationInfo placeAllocationInfo = new SeminarPlaceAllocationInfo();
    				placeAllocationInfo.setCourseId(course.getId());
    				placeAllocationInfo.setCourseName(course.getName());
    				placeAllocationInfo.setGroupId(courseGroup.getId());
    				placeAllocationInfo.setSeminarpoolId(seminarpoolId);
    				placeAllocationInfo.setUserId(user.getId());
    				placeAllocationInfo.setUserName(user.getDisplayName());
    				placeAllocationList.add(placeAllocationInfo);
    			}
    		}
		
    	}
    	return placeAllocationList;
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#getAllocationsByCourse(java.lang.Long, java.lang.Long)
     */
    protected List handleGetAllocationsByCourse(java.lang.Long courseId, java.lang.Long seminarpoolId)
        throws java.lang.Exception
    {
    	Validate.notNull(seminarpoolId, "handleGetAllocationsByCourse ==> seminarpoolId cannot be null");
    	Validate.notNull(courseId, "handleGetAllocationsByCourse ==> courseId cannot be null");
    	Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarpoolId);
    	Validate.notNull(seminarpoolEntity, "handleGetAllocations ==> Cannot load Seminarpool");
    	Course courseEntity = getCourseDao().load(courseId);    	
    	return mapSeminarPlaceAllocation(getCourseSeminarpoolAllocationDao().findSeminarpoolAllocationsBySeminarpoolAndCourse(courseEntity, seminarpoolEntity), seminarpoolId, courseId);
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#generateAllocation(java.lang.Long)
     */
    protected void handleGenerateAllocation(java.lang.Long seminarpoolId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleGenerateAllocation(java.lang.Long seminarpoolId)
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.SeminarpoolAdministrationService.handleGenerateAllocation(java.lang.Long seminarpoolId) Not implemented!");
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#moveUser(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
     */
    protected void handleMoveUser(java.lang.Long seminarpoolId, java.lang.Long userId, java.lang.Long oldCourseId, java.lang.Long newCourseId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleMoveUser(java.lang.Long seminarpoolId, java.lang.Long userId, java.lang.Long oldCourseId, java.lang.Long newCourseId)
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.SeminarpoolAdministrationService.handleMoveUser(java.lang.Long seminarpoolId, java.lang.Long userId, java.lang.Long oldCourseId, java.lang.Long newCourseId) Not implemented!");
    }


    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#addUserToAllocation(java.lang.Long, java.lang.Long, java.lang.Long)
     */
    protected void handleAddUserToAllocation(java.lang.Long seminarpoolId, java.lang.Long userId, java.lang.Long courseId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleAddUserToAllocation(java.lang.Long seminarpoolId, java.lang.Long userId, java.lang.Long courseId)
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.SeminarpoolAdministrationService.handleAddUserToAllocation(java.lang.Long seminarpoolId, java.lang.Long userId, java.lang.Long courseId) Not implemented!");
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#removeUserFromAllocation(java.lang.Long, java.lang.Long, java.lang.Long)
     */
    protected void handleRemoveUserFromAllocation(java.lang.Long seminarpoolId, java.lang.Long userId, java.lang.Long courseId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleRemoveUserFromAllocation(java.lang.Long seminarpoolId, java.lang.Long userId, java.lang.Long courseId)
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.SeminarpoolAdministrationService.handleRemoveUserFromAllocation(java.lang.Long seminarpoolId, java.lang.Long userId, java.lang.Long courseId) Not implemented!");
    }

    /**
     * @see org.openuss.seminarpool.SeminarpoolAdministrationService#confirmAllocation(java.lang.Long)
     */
    protected void handleConfirmAllocation(java.lang.Long seminarpoolId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleConfirmAllocation(java.lang.Long seminarpoolId)
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.SeminarpoolAdministrationService.handleConfirmAllocation(java.lang.Long seminarpoolId) Not implemented!");
    }

	@Override
	protected Long handleAddConditionToSeminarpool(
			SeminarConditionInfo seminarCondition) throws Exception {
		Validate.notNull(seminarCondition, "handleAddConditionToSeminarpool ==> seminarCondition cannot be null");
		Validate.notNull(seminarCondition.getSeminarpoolId(), "handleAddConditionToSeminarpool.getSeminarpoolId() ==> SeminarpoolId cannot be null");
		Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarCondition.getSeminarpoolId());
		Validate.notNull(seminarpoolEntity, "handleAddConditionToSeminarpool ==> Cannot load Seminarpool");
		SeminarCondition seminarConditionEntity = getSeminarConditionDao().seminarConditionInfoToEntity(seminarCondition);
		seminarpoolEntity.addCondition(seminarConditionEntity);
		return seminarConditionEntity.getId();
	}

	@Override
	protected void handleRemoveConditionFromSeminarpool(
			SeminarConditionInfo seminarCondition) throws Exception {
		Validate.notNull(seminarCondition, "handleAddConditionToSeminarpool ==> seminarCondition cannot be null");
		Validate.notNull(seminarCondition.getSeminarpoolId(), "handleAddConditionToSeminarpool.getSeminarpoolId() ==> SeminarpoolId cannot be null");
		Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarCondition.getSeminarpoolId());
		Validate.notNull(seminarpoolEntity, "handleAddConditionToSeminarpool ==> Cannot load Seminarpool");
		SeminarCondition seminarConditionEntity = getSeminarConditionDao().seminarConditionInfoToEntity(seminarCondition);
		seminarpoolEntity.removeCondition(seminarConditionEntity);		
	}


}