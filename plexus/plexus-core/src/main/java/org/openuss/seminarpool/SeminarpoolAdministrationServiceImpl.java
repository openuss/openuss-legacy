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

import org.apache.commons.lang.Validate;
import org.openuss.desktop.Desktop;
import org.openuss.lecture.Course;
import org.openuss.lecture.University;
import org.openuss.security.Group;
import org.openuss.security.GroupItem;
import org.openuss.security.GroupType;
import org.openuss.security.Membership;
import org.openuss.security.Roles;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @see org.openuss.seminarpool.SeminarpoolAdministrationService
 */
public class SeminarpoolAdministrationServiceImpl extends
		org.openuss.seminarpool.SeminarpoolAdministrationServiceBase {

	/**
	 * @see org.openuss.seminarpool.SeminarpoolAdministrationService#createSeminarpool(org.openuss.seminarpool.SeminarpoolInfo,
	 *      java.lang.Long)
	 */
	protected java.lang.Long handleCreateSeminarpool(
			org.openuss.seminarpool.SeminarpoolInfo seminarpoolInfo,
			java.lang.Long userId) throws java.lang.Exception {
		Validate.notNull(seminarpoolInfo, "The Seminarpool cannot be null");
		Validate.notNull(userId, "The User must have a valid ID");
		User user = getSecurityService().getUser(userId);
		Validate.notNull(user, "No valid User found corresponding to the ID "
				+ userId);
		Validate.isTrue(seminarpoolInfo.getId() == null,
				"The Seminarpool shouldn't have an ID yet");
		seminarpoolInfo.setId(null);
		// Transform ValueObject into Entity
		Seminarpool seminarpoolEntity = this.getSeminarpoolDao()
				.seminarpoolInfoToEntity(seminarpoolInfo);

		// Create a default Membership for the Seminarpool
		Membership membership = Membership.Factory.newInstance();
		seminarpoolEntity.setMembership(membership);

		// Create the Seminarpool
		getSeminarpoolDao().create(seminarpoolEntity);
		Validate.notNull(seminarpoolEntity.getId(),
				"SeminarpoolDao.handleCreate - Couldn't create Seminarpool");
		seminarpoolInfo.setId(seminarpoolEntity.getId());
		// Create default Groups for Institute
		GroupItem admins = new GroupItem();
		admins.setName("SEMINARPOOL_" + seminarpoolEntity.getId() + "_ADMINS");
		admins.setLabel("autogroup_administrator_label");
		admins.setGroupType(GroupType.ADMINISTRATOR);
		Group group = this.getGroupDao().groupItemToEntity(admins);
		group = this.getMembershipService().createGroup(
				seminarpoolEntity.getMembership(), group);

		Validate
				.notNull(group.getId(),
						"MembershipService.handleCreateGroup - Group couldn't be created");
		// Security
		getSecurityService().createObjectIdentity(seminarpoolEntity, null);
		getSecurityService().setPermissions(group, seminarpoolEntity,
				LectureAclEntry.OGCRUD);

		group.addMember(user);
		// Add Owner to Members and the group of Administrators
		getMembershipService().addMember(membership, user);
		getSecurityService().addAuthorityToGroup(user, group);
		if (seminarpoolInfo.getAccessType().equals(SeminarpoolAccessType.OPEN)) {
			getSecurityService().setPermissions(Roles.USER, seminarpoolEntity,
					LectureAclEntry.COURSE_PARTICIPANT);
		}
		return seminarpoolEntity.getId();
	}

	/**
	 * @see org.openuss.seminarpool.SeminarpoolAdministrationService#updateSeminarpool(org.openuss.seminarpool.SeminarpoolInfo)
	 */
	protected void handleUpdateSeminarpool(
			org.openuss.seminarpool.SeminarpoolInfo seminarpoolInfo)
			throws java.lang.Exception {
		Validate.notNull(seminarpoolInfo,
				"handleUpdateSeminarpool ==> seminarpoolInfo cannot be null");
		Validate
				.notNull(seminarpoolInfo.getId(),
						"handleUpdateSeminarpool ==> seminarpoolInfo.getID() cannot be null");
		Seminarpool seminarpoolEntity = getSeminarpoolDao().load(
				seminarpoolInfo.getId());
		getSeminarpoolDao().seminarpoolInfoToEntity(seminarpoolInfo,
				seminarpoolEntity, false);
		getSeminarpoolDao().update(seminarpoolEntity);
	}

	/**
	 * @see org.openuss.seminarpool.SeminarpoolAdministrationService#removeSeminarpool(java.lang.Long)
	 */
	protected void handleRemoveSeminarpool(java.lang.Long seminarpoolId)
			throws java.lang.Exception {
		Validate.notNull(seminarpoolId,
				"handleRemoveSeminarpool ==> seminarpoolId cannot be null");
		Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarpoolId);
		// Remove Security
		getSecurityService().removeAllPermissions(seminarpoolEntity);
		getSecurityService().removeObjectIdentity(seminarpoolEntity);

		// Clear Membership
		getMembershipService().clearMembership(
				seminarpoolEntity.getMembership());
		deleteAllAllocationsFromSeminarpool(seminarpoolEntity);
		getSeminarpoolDao().remove(seminarpoolId);
	}

	    private void deleteAllAllocationsFromSeminarpool(Seminarpool seminarpool){
	        for (CourseSeminarpoolAllocation courseSeminarpoolAllocation : seminarpool.getCourseSeminarpoolAllocation()) {
	        for (CourseGroup courseGroup : courseSeminarpoolAllocation.getCourseGroup()){
	            for (User user : courseGroup.getUser()) {
	            user.getCourseGroup().remove(courseGroup);
	            }
	            courseGroup.getUser().clear();
	        }
	        }
	    }
	
	/**
	 * @see org.openuss.seminarpool.SeminarpoolAdministrationService#addSeminarpoolAdmin(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected void handleAddSeminarpoolAdmin(java.lang.Long userId,
			java.lang.Long seminarpoolId) throws java.lang.Exception {
		Seminarpool seminarpool = getSeminarpoolDao().load(seminarpoolId);
		Validate.notNull(seminarpool, "No Seminarpool found corresponding to the ID " + seminarpoolId);
		User user = getUserDao().load(userId);
		Validate.notNull(user, "No User found corresponding to the ID " + user);
		this.getMembershipService().addMember(seminarpool.getMembership(), user);
		for (Group group : seminarpool.getMembership().getGroups()) {
			getSecurityService().addAuthorityToGroup(user, group);
		}
		Desktop desktop = getDesktopDao().findByUser(user);
		getDesktopService2().linkSeminarpool(desktop.getId(), seminarpool.getId());

	}

	/**
	 * @see org.openuss.seminarpool.SeminarpoolAdministrationService#removeSeminarpoolAdmin(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected void handleRemoveSeminarpoolAdmin(java.lang.Long userId,
			java.lang.Long seminarpoolId) throws java.lang.Exception {
		Seminarpool seminarpool = this.getSeminarpoolDao().load(seminarpoolId);
		Validate
				.notNull(seminarpool,
						"No Seminarpool found corresponding to the ID "
								+ seminarpoolId);

		Validate.isTrue(seminarpool.getMembership().getMembers().size() > 1,
				"You cannot remove the last Member!");

		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "No User found corresponding to the ID "
				+ userId);

		this.getMembershipService().removeMember(seminarpool.getMembership(),
				user);
		for (Group group : seminarpool.getMembership().getGroups()) {
			getSecurityService().removeAuthorityFromGroup(user, group);
		}
	}

	/**
	 * @see org.openuss.seminarpool.SeminarpoolAdministrationService#addSeminar(java.lang.Long,
	 *      java.lang.Long, java.lang.Integer)
	 */
	protected Long handleAddSeminar(
			CourseSeminarpoolAllocationInfo seminarpoolAllocation,
			Collection courseGroups) throws java.lang.Exception {
		Validate.notNull(seminarpoolAllocation,
				"handleAddSeminar ==> seminarpoolAllocation cannot be null");
		Validate.notNull(courseGroups,
				"handleAddSeminar ==> courseGroups cannot be null");
		Validate.isTrue(courseGroups.size() >= 1,
				"handleAddSeminar ==> courseGroups cannot be null");
		Seminarpool seminarpool = getSeminarpoolDao().load(
				seminarpoolAllocation.getSeminarpoolId());
		Validate.notNull(seminarpool,
				"handleAddSeminar ==> seminarpool cannot be null");
		Course course = getCourseDao()
				.load(seminarpoolAllocation.getCourseId());
		Validate.notNull(course, "handleAddSeminar ==> course cannot be null");
		CourseSeminarpoolAllocation courseAllocation = CourseSeminarpoolAllocation.Factory
				.newInstance();
		courseAllocation.setSeminarpool(seminarpool);
		courseAllocation.setCourse(course);

		Collection<CourseGroup> set = new HashSet<CourseGroup>();
		for (CourseGroupInfo groupInfo : (Collection<CourseGroupInfo>) courseGroups) {
			CourseGroup courseGroupEntity = getCourseGroupDao()
					.courseGroupInfoToEntity(groupInfo);
			courseGroupEntity.setCourseSeminarpoolAllocation(courseAllocation);
			set.add(courseGroupEntity);
			// this.getCourseGroupDao().create(courseGroupEntity);
		}
		courseAllocation.setCourseGroup(set);
		Long courseAllocationId = getCourseSeminarpoolAllocationDao().create(
				courseAllocation).getId();
		// Set Security
		this.getSecurityService()
				.createObjectIdentity(courseAllocation, course);
		for (User member : seminarpool.getMembership().getMembers()) {
			getSecurityService().setPermissions(member, courseAllocation,
					LectureAclEntry.OGCRUD);
			getSecurityService().setPermissions(member, seminarpool,
					LectureAclEntry.RU);
		}
		for (User member : course.getCourseType().getInstitute()
				.getMembership().getMembers()) {
			getSecurityService().setPermissions(member, seminarpool,
					LectureAclEntry.RU);
		}

		seminarpool.addCourseAllocation(courseAllocation);

		return courseAllocationId;
	}

	/**
	 * @see org.openuss.seminarpool.SeminarpoolAdministrationService#removeSeminar(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected void handleRemoveSeminar(
			CourseSeminarpoolAllocationInfo courseSeminarpoolAllocation)
			throws java.lang.Exception {
		Validate
				.notNull(courseSeminarpoolAllocation,
						"handleRemoveSeminar ==> courseSeminarpoolAllocation cannot be null");
		Validate
				.notNull(courseSeminarpoolAllocation.getId(),
						"handleRemoveSeminar ==> courseSeminarpoolAllocation.getId() cannot be null");
		CourseSeminarpoolAllocation courseAllocationEntity = getCourseSeminarpoolAllocationDao()
				.load(courseSeminarpoolAllocation.getId());
		Validate
				.notNull(courseAllocationEntity,
						"handleRemoveSeminar ==> Cannot load CourseSeminarpoolAllocation");
		Seminarpool seminarpoolEntity = courseAllocationEntity.getSeminarpool();
		seminarpoolEntity.removeCourseAllocation(courseAllocationEntity);
		getCourseSeminarpoolAllocationDao().remove(courseAllocationEntity);

	}

	/**
	 * @see org.openuss.seminarpool.SeminarpoolAdministrationService#findSeminarpool(java.lang.Long)
	 */
	protected org.openuss.seminarpool.SeminarpoolInfo handleFindSeminarpool(
			java.lang.Long seminarpoolId) throws java.lang.Exception {
		Validate.notNull(seminarpoolId,
				"handleFindSeminarpool ==> seminarpoolId cannot be null");
		Seminarpool seminapoolEntity = getSeminarpoolDao().load(seminarpoolId);
		return getSeminarpoolDao().toSeminarpoolInfo(seminapoolEntity);
	}

	/**
	 * @see org.openuss.seminarpool.SeminarpoolAdministrationService#findSeminarpoolsByUniversity(java.lang.Long)
	 */
	protected java.util.List handleFindSeminarpoolsByUniversity(
			java.lang.Long universityId) throws java.lang.Exception {
		Validate
				.notNull(universityId,
						"handleFindSeminarpoolsByUniversity ==> universityId cannot be null");
		University university = getUniversityDao().load(universityId);

		List<Seminarpool> seminarpools = getSeminarpoolDao().findByUniversity(
				university);
		List<SeminarpoolInfo> seminarpoolInfoList = new ArrayList<SeminarpoolInfo>();
		for (Seminarpool seminarpool : seminarpools) {
			seminarpoolInfoList.add(getSeminarpoolDao().toSeminarpoolInfo(
					seminarpool));
		}
		return seminarpoolInfoList;
	}

	/**
	 * @see org.openuss.seminarpool.SeminarpoolAdministrationService#findCoursesInSeminarpool(java.lang.Long)
	 */
	protected java.util.List handleFindCoursesInSeminarpool(
			java.lang.Long seminarpoolId) throws java.lang.Exception {
		Validate
				.notNull(seminarpoolId,
						"handleFindCoursesInSeminarpool ==> seminarpoolId cannot be null");
		Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarpoolId);
		// SeminarpoolInfo seminarpoolInfo =
		// getSeminarpoolDao().toSeminarpoolInfo(seminarpoolEntity);
		Validate.notNull(seminarpoolEntity,
				"handleFindCoursesInSeminarpool ==> Cannot load Seminarpool");
		Collection<CourseSeminarpoolAllocation> courseAllocations = seminarpoolEntity
				.getCourseSeminarpoolAllocation();
		List<CourseSeminarpoolAllocationInfo> courseAllocationList = new ArrayList<CourseSeminarpoolAllocationInfo>();
		for (CourseSeminarpoolAllocation courseAllocation : courseAllocations) {
			// only if the course is accepted or if the user is the owner the
			// course or is seminarpool admin
			if (getSecurityService().hasPermission(courseAllocation,
					new Integer[] { LectureAclEntry.GCRUD })
					|| getSecurityService().hasPermission(seminarpoolEntity,
							new Integer[] { LectureAclEntry.GCRUD })) {
				courseAllocationList.add(getCourseSeminarpoolAllocationDao()
						.toCourseSeminarpoolAllocationInfo(courseAllocation));
			} else if (courseAllocation.isAccepted()) {
				courseAllocationList.add(getCourseSeminarpoolAllocationDao()
						.toCourseSeminarpoolAllocationInfo(courseAllocation));
			}
		}
		return courseAllocationList;
	}

	/**
	 * @see org.openuss.seminarpool.SeminarpoolAdministrationService#getSeminarpool(java.lang.Long)
	 */
	protected org.openuss.seminarpool.SeminarpoolInfo handleGetSeminarpool(
			java.lang.Long seminarpoolId) throws java.lang.Exception {
		Validate
				.notNull(seminarpoolId,
						"handleFindCoursesInSeminarpool ==> seminarpoolId cannot be null");
		Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarpoolId);
		Validate.notNull(seminarpoolEntity,
				"handleFindCoursesInSeminarpool ==> Cannot load Seminarpool");
		return getSeminarpoolDao().toSeminarpoolInfo(
				getSeminarpoolDao().load(seminarpoolId));
	}

	/**
	 * @see org.openuss.seminarpool.SeminarpoolAdministrationService#getAllSeminarpools()
	 */
	protected java.util.List handleGetAllSeminarpools()
			throws java.lang.Exception {
		Collection<Seminarpool> seminarpools = getSeminarpoolDao().loadAll();
		List<SeminarpoolInfo> seminarpoolInfoList = new ArrayList<SeminarpoolInfo>();
		for (Seminarpool seminarpool : seminarpools) {
			seminarpoolInfoList.add(getSeminarpoolDao().toSeminarpoolInfo(
					seminarpool));
		}
		return seminarpoolInfoList;
	}

	/**
	 * @see org.openuss.seminarpool.SeminarpoolAdministrationService#getRegistrations(java.lang.Long)
	 */
	protected java.util.List handleGetRegistrations(java.lang.Long seminarpoolId)
			throws java.lang.Exception {
		Validate
				.notNull(seminarpoolId,
						"handleFindCoursesInSeminarpool ==> seminarpoolId cannot be null");
		Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarpoolId);
		Validate.notNull(seminarpoolEntity,
				"handleFindCoursesInSeminarpool ==> Cannot load Seminarpool");
		Collection<SeminarUserRegistration> userRegistrations = seminarpoolEntity
				.getSeminarUserRegistration();
		List<SeminarUserRegistrationInfo> userRegistrationsInfoList = new ArrayList<SeminarUserRegistrationInfo>();
		for (SeminarUserRegistration userRegistration : userRegistrations) {
			List<SeminarPrioritiesInfo> seminarPriorityInfoList = new ArrayList<SeminarPrioritiesInfo>();
			for (SeminarPriority seminarPriority : userRegistration
					.getSeminarPriority()) {
				seminarPriorityInfoList.add(getSeminarPriorityDao()
						.toSeminarPrioritiesInfo(seminarPriority));
			}
			SeminarUserRegistrationInfo seminarUserRegistrationInfo = getSeminarUserRegistrationDao()
					.toSeminarUserRegistrationInfo(userRegistration);
			seminarUserRegistrationInfo
					.setSeminarPriorityList(seminarPriorityInfoList);
			userRegistrationsInfoList.add(seminarUserRegistrationInfo);

		}
		return userRegistrationsInfoList;
	}

	/**
	 * @see org.openuss.seminarpool.SeminarpoolAdministrationService#getRegistrationsByCourse(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected java.util.List handleGetRegistrationsByCourse(
			java.lang.Long seminarpoolId, java.lang.Long searchCourseId)
			throws java.lang.Exception {
		Validate.notNull(searchCourseId,
				"handleGetRegistrationsByCourse ==> courseId cannot be null");
		Validate
				.notNull(seminarpoolId,
						"handleGetRegistrationsByCourse ==> seminarpoolId cannot be null");
		Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarpoolId);
		Validate.notNull(seminarpoolEntity,
				"handleGetRegistrationsByCourse ==> Cannot load Seminarpool");
		Collection<SeminarUserRegistration> userRegistrations = seminarpoolEntity
				.getSeminarUserRegistration();
		List<SeminarUserRegistrationInfo> userRegistrationsInfoList = new ArrayList<SeminarUserRegistrationInfo>();
		for (SeminarUserRegistration userRegistration : userRegistrations) {
			List<SeminarPrioritiesInfo> seminarPriorityInfoList = new ArrayList<SeminarPrioritiesInfo>();
			Long courseId = null;
			for (SeminarPriority seminarPriority : userRegistration
					.getSeminarPriority()) {
				courseId = seminarPriority.getCourseSeminarPoolAllocation()
						.getCourse().getId();
				// map and add SeminarPriority if courseId == searchCourseId
				if (courseId.equals(searchCourseId)) {
					seminarPriorityInfoList.add(getSeminarPriorityDao()
							.toSeminarPrioritiesInfo(seminarPriority));
				}
			}
			// map and add SeminarUserRegistration if courseId == searchCourseId
			if (courseId != null && courseId.equals(searchCourseId)) {
				SeminarUserRegistrationInfo seminarUserRegistrationInfo = getSeminarUserRegistrationDao()
						.toSeminarUserRegistrationInfo(userRegistration);
				seminarUserRegistrationInfo
						.setSeminarPriorityList(seminarPriorityInfoList);
				userRegistrationsInfoList.add(seminarUserRegistrationInfo);
			}
		}
		return userRegistrationsInfoList;
	}

	/**
	 * @see org.openuss.seminarpool.SeminarpoolAdministrationService#getAllocations(java.lang.Long)
	 */
	protected java.util.List handleGetAllocations(java.lang.Long seminarpoolId)
			throws java.lang.Exception {
		Validate.notNull(seminarpoolId,
				"handleGetAllocations ==> seminarpoolId cannot be null");
		Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarpoolId);
		Validate.notNull(seminarpoolEntity,
				"handleGetAllocations ==> Cannot load Seminarpool");
		return mapSeminarPlaceAllocation(getCourseSeminarpoolAllocationDao()
				.findSeminarPlaceAllocationBySeminarpool(seminarpoolEntity),
				seminarpoolId, null);
	}

	private List<SeminarPlaceAllocationInfo> mapSeminarPlaceAllocation(
			List<CourseSeminarpoolAllocation> courseAllocationSet,
			Long seminarpoolId, Long courseId) {
		List<SeminarPlaceAllocationInfo> placeAllocationList = new ArrayList<SeminarPlaceAllocationInfo>();
		for (CourseSeminarpoolAllocation courseAllocation : courseAllocationSet) {
			Course course = courseAllocation.getCourse();
			if (courseId != null) {
				if (!courseId.equals(course.getId())) {
					continue;
				}
			}
			Collection<CourseGroup> courseGroups = courseAllocation
					.getCourseGroup();
			for (CourseGroup courseGroup : courseGroups) {
				Collection<User> users = courseGroup.getUser();
				for (User user : users) {
					SeminarPlaceAllocationInfo placeAllocationInfo = new SeminarPlaceAllocationInfo();
					placeAllocationInfo.setCourseId(course.getId());
					placeAllocationInfo.setCourseName(course.getName());
					placeAllocationInfo.setGroupId(courseGroup.getId());
					placeAllocationInfo.setGroupName(courseGroup.getName());
					placeAllocationInfo.setSeminarpoolId(seminarpoolId);
					placeAllocationInfo.setUserId(user.getId());
					placeAllocationInfo.setFirstName(user.getFirstName());
					placeAllocationInfo.setLastName(user.getLastName());
					placeAllocationList.add(placeAllocationInfo);
				}
			}

		}
		return placeAllocationList;
	}

	/**
	 * @see org.openuss.seminarpool.SeminarpoolAdministrationService#getAllocationsByCourse(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected List handleGetAllocationsByCourse(java.lang.Long courseId,
			java.lang.Long seminarpoolId) throws java.lang.Exception {
		Validate
				.notNull(seminarpoolId,
						"handleGetAllocationsByCourse ==> seminarpoolId cannot be null");
		Validate.notNull(courseId,
				"handleGetAllocationsByCourse ==> courseId cannot be null");
		Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarpoolId);
		Validate.notNull(seminarpoolEntity,
				"handleGetAllocations ==> Cannot load Seminarpool");
		Course courseEntity = getCourseDao().load(courseId);
		return mapSeminarPlaceAllocation(getCourseSeminarpoolAllocationDao()
				.findSeminarpoolAllocationsBySeminarpoolAndCourse(courseEntity,
						seminarpoolEntity), seminarpoolId, courseId);
	}

	@Override
	protected Long handleAddConditionToSeminarpool(
			SeminarConditionInfo seminarConditionInfo) throws Exception {
		Validate
				.notNull(seminarConditionInfo,
						"handleAddConditionToSeminarpool ==> seminarCondition cannot be null");
		Validate
				.notNull(
						seminarConditionInfo.getSeminarpoolId(),
						"handleAddConditionToSeminarpool.getSeminarpoolId() ==> SeminarpoolId cannot be null");
		Seminarpool seminarpoolEntity = getSeminarpoolDao().load(
				seminarConditionInfo.getSeminarpoolId());
		Validate.notNull(seminarpoolEntity,
				"handleAddConditionToSeminarpool ==> Cannot load Seminarpool");
		SeminarCondition seminarConditionEntity = getSeminarConditionDao()
				.seminarConditionInfoToEntity(seminarConditionInfo);
		getSeminarConditionDao().create(seminarConditionEntity);
		seminarpoolEntity.addCondition(seminarConditionEntity);
		return seminarConditionEntity.getId();
	}

	@Override
	protected void handleRemoveConditionFromSeminarpool(
			SeminarConditionInfo seminarConditionInfo) throws Exception {
		Validate
				.notNull(seminarConditionInfo,
						"handleAddConditionToSeminarpool ==> seminarCondition cannot be null");
		Validate
				.notNull(
						seminarConditionInfo.getSeminarpoolId(),
						"handleAddConditionToSeminarpool.getSeminarpoolId() ==> SeminarpoolId cannot be null");
		Seminarpool seminarpoolEntity = getSeminarpoolDao().load(
				seminarConditionInfo.getSeminarpoolId());
		Validate.notNull(seminarpoolEntity,
				"handleAddConditionToSeminarpool ==> Cannot load Seminarpool");
		SeminarCondition seminarConditionEntity = getSeminarConditionDao()
				.load(seminarConditionInfo.getId());
		seminarpoolEntity.removeCondition(seminarConditionEntity);
	}

	@Override
	protected List handleFindSeminarpoolByInsituteAndStatus(Long instituteId,
			SeminarpoolStatus seminarpoolStatus) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List handleFindSeminarpoolsByDepartmentAndStatus(
			Long departmentId, SeminarpoolStatus seminarpoolStatus)
			throws Exception {

		Validate
				.notNull(departmentId,
						"handleFindSeminarpoolsByDepartmentAndStatus ==> departmentId cannot be null");

		Long universityId = this.getDepartmentService().findDepartment(departmentId).getUniversityId();
		
		List<SeminarpoolInfo> seminarpoolInfoList = this.findSeminarpoolsByUniversityAndStatus(universityId, seminarpoolStatus);
		List<SeminarpoolInfo> seminarpoolInfoList2 = new ArrayList<SeminarpoolInfo>();
		for (SeminarpoolInfo seminarpoolInfo : seminarpoolInfoList) {
			List<CourseSeminarpoolAllocationInfo> csaiList = this.findCoursesInSeminarpool(seminarpoolInfo.getId());
			boolean courseInDepartment = false;
			for(CourseSeminarpoolAllocationInfo csai : csaiList){
				this.getCourseService().findCourse(csai.getCourseId());
				if(this.getInstituteService().findInstitute(this.getCourseService().findCourse(csai.getCourseId()).getInstituteId()).getDepartmentId().equals(departmentId)){
					courseInDepartment=true;
				}
			}
			if(courseInDepartment){
				seminarpoolInfoList2.add(seminarpoolInfo);
			}
		}
		return seminarpoolInfoList2;
	}

	@Override
	protected List handleFindSeminarpoolsByUniversityAndStatus(
			Long universityId, SeminarpoolStatus seminarpoolStatus)
			throws Exception {
		Validate
				.notNull(universityId,
						"handleFindSeminarpoolsByUniversityAndStatus ==> universityId cannot be null");
		University university = getUniversityDao().load(universityId);

		List<Seminarpool> seminarpools = getSeminarpoolDao()
				.findByUniversityAndStatus(university, seminarpoolStatus);
		List<SeminarpoolInfo> seminarpoolInfoList = new ArrayList<SeminarpoolInfo>();
		for (Seminarpool seminarpool : seminarpools) {
			seminarpoolInfoList.add(getSeminarpoolDao().toSeminarpoolInfo(
					seminarpool));
		}
		return seminarpoolInfoList;
	}

	@Override
	protected List handleFindSeminarpoolAdministratorsBySeminarpool(
			Long seminarpoolId) throws Exception {
		Validate
				.notNull(
						seminarpoolId,
						"handleFindSeminarpoolAdministratorsBySeminarpool ==> seminarpoolId cannot be null");
		Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarpoolId);
		Validate
				.notNull(
						seminarpoolEntity,
						"handleFindSeminarpoolAdministratorsBySeminarpool ==> seminarpool cannot loaded");
		Membership membership = seminarpoolEntity.getMembership();
		List<User> userList = membership.getMembers();
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		for (User user : userList) {
			userInfoList.add(getUserDao().toUserInfo(user));
		}
		return userInfoList;
	}

	@Override
	protected SeminarConditionInfo handleFindConditionById(Long conditionId)
			throws Exception {
		Validate.notNull(conditionId,
				"handleFindConditionById ==> conditionId cannot be null");
		SeminarCondition seminarCondition = getSeminarConditionDao().load(
				conditionId);
		Validate
				.notNull(seminarCondition,
						"handleFindConditionById ==> SeminarCondition cannot be loaded");
		SeminarConditionInfo seminarCondtionInfo = getSeminarConditionDao()
				.toSeminarConditionInfo(seminarCondition);
		return seminarCondtionInfo;
	}

	protected void handleActivateSeminar(
			CourseSeminarpoolAllocationInfo courseSeminarpoolAllocation)
			throws Exception {
		Validate
				.notNull(courseSeminarpoolAllocation,
						"handleActivateSeminar ==> courseSeminarpoolAllocation cannot be null");
		Validate
				.notNull(courseSeminarpoolAllocation.getId(),
						"handleActivateSeminar ==> courseSeminarpoolAllocation.getId() cannot be null");
		CourseSeminarpoolAllocation courseAllocationEntity = getCourseSeminarpoolAllocationDao()
				.load(courseSeminarpoolAllocation.getId());
		Validate
				.notNull(courseAllocationEntity,
						"handleActivateSeminar ==> Cannot load CourseSeminarpoolAllocation");
		courseAllocationEntity.setAccepted(true);
		getCourseSeminarpoolAllocationDao().update(courseAllocationEntity);
	}

	@Override
	protected List handleFindConditionBySeminarpool(Long seminarpoolId)
			throws Exception {
		Validate
				.notNull(seminarpoolId,
						"handleFindConditionBySeminarpool ==> seminarpoolId cannot be null");
		Seminarpool seminarpool = getSeminarpoolDao().load(seminarpoolId);
		Validate.notNull(seminarpool,
				"handleFindConditionById ==> seminarpool cannot be loaded");
		Collection<SeminarCondition> seminarConditionList = seminarpool
				.getSeminarCondition();
		List<SeminarConditionInfo> seminarConditionInfoList = new ArrayList<SeminarConditionInfo>();
		for (SeminarCondition seminarCondition : seminarConditionList) {
			seminarConditionInfoList.add(getSeminarConditionDao()
					.toSeminarConditionInfo(seminarCondition));
		}
		return seminarConditionInfoList;
	}

	@Override
	protected List handleFindCourseGroupsByCourseAllocationId(
			Long courseAllocationId) throws Exception {
		Validate
				.notNull(
						courseAllocationId,
						"handleFindCourseGroupsByCourseAllocationId ==> courseAllocationId cannot be null");
		CourseSeminarpoolAllocation courseAllocation = getCourseSeminarpoolAllocationDao()
				.load(courseAllocationId);
		Validate
				.notNull(
						courseAllocation,
						"handleFindCourseGroupsByCourseAllocationId ==> courseAllocation cannot be loaded");
		Collection<CourseGroup> courseGroupEntities = courseAllocation
				.getCourseGroup();
		List<CourseGroupInfo> courseGroupInfos = new ArrayList<CourseGroupInfo>();
		for (CourseGroup courseGroup : courseGroupEntities) {
			courseGroupInfos.add(getCourseGroupDao().toCourseGroupInfo(
					courseGroup));
		}
		return courseGroupInfos;
	}

	@Override
	protected Long handleAddCourseGroup(CourseGroupInfo courseGroup)
			throws Exception {
		Validate.notNull(courseGroup,
				"handleAddCourseGroup ==> courseGroup cannot be null");
		Validate
				.notNull(courseGroup.getCourseSeminarpoolAllocationId(),
						"handleAddCourseGroup ==> getCourseSeminarpoolAllocationId cannot be null");
		CourseGroup entity = getCourseGroupDao().courseGroupInfoToEntity(
				courseGroup);
		CourseSeminarpoolAllocation courseSeminarpoolAllocationEntity = getCourseSeminarpoolAllocationDao()
				.load(courseGroup.getCourseSeminarpoolAllocationId());
		courseSeminarpoolAllocationEntity.addCourseGroup(entity);
		getCourseGroupDao().create(entity);
		getCourseSeminarpoolAllocationDao().update(
				courseSeminarpoolAllocationEntity);
		return entity.getId();
	}

	@Override
	protected Long handleAddCourseSchedule(CourseScheduleInfo courseSchedule)
			throws Exception {
		Validate.notNull(courseSchedule,
				"handleAddCourseGroup ==> courseGroup cannot be null");
		Validate
				.notNull(courseSchedule.getCourseGroupId(),
						"handleAddCourseGroup ==> getCourseSeminarpoolAllocationId cannot be null");
		CourseGroup courseGroupEntity = getCourseGroupDao().load(
				courseSchedule.getCourseGroupId());
		CourseSchedule courseScheduleEntity = getCourseScheduleDao()
				.courseScheduleInfoToEntity(courseSchedule);
		courseGroupEntity.addCourseSchedule(courseScheduleEntity);
		courseGroupEntity.setIsTimeSet(Boolean.TRUE);
		getCourseGroupDao().update(courseGroupEntity);
		return courseScheduleEntity.getId();
	}

	@Override
	protected void handleRemoveCourseGroup(CourseGroupInfo courseGroupInfo)
			throws Exception {
		Validate.notNull(courseGroupInfo,
				"CourseGroupInfo ==> courseGroupInfo cannot be null");
		Validate
				.notNull(courseGroupInfo.getCourseSeminarpoolAllocationId(),
						"CourseGroupInfo ==> getCourseSeminarpoolAllocationId cannot be null");
		CourseGroup entity = getCourseGroupDao().load(courseGroupInfo.getId());
		CourseSeminarpoolAllocation courseSeminarpoolAllocationEntity = getCourseSeminarpoolAllocationDao()
				.load(courseGroupInfo.getCourseSeminarpoolAllocationId());
		courseSeminarpoolAllocationEntity.removeCourseGroup(entity);

	}

	@Override
	protected void handleRemoveCourseSchedule(CourseScheduleInfo courseSchedule)
			throws Exception {
		Validate.notNull(courseSchedule,
				"handleRemoveCourseSchedule ==> courseGroup cannot be null");
		Validate
				.notNull(
						courseSchedule.getCourseGroupId(),
						"handleRemoveCourseSchedule ==> getCourseSeminarpoolAllocationId cannot be null");
		CourseGroup courseGroupEntity = getCourseGroupDao().load(
				courseSchedule.getCourseGroupId());
		CourseSchedule courseScheduleEntity = getCourseScheduleDao()
				.courseScheduleInfoToEntity(courseSchedule);
		courseGroupEntity.removeCourseGroup(courseScheduleEntity);
		if (courseGroupEntity.getCourseSchedule() != null
				&& courseGroupEntity.getCourseSchedule().size() == 0) {
			courseGroupEntity.setIsTimeSet(Boolean.FALSE);
		}
		getCourseGroupDao().update(courseGroupEntity);
	}

	@Override
	protected List handleGetRegistrationsAsUserInfo(Long seminarpoolId)
			throws Exception {
		Validate
				.notNull(seminarpoolId,
						"handleGetRegistrationsAsUserInfo ==> seminarpoolId cannot be null");
		Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarpoolId);
		Validate.notNull(seminarpoolEntity,
				"handleGetRegistrationsAsUserInfo ==> Cannot load Seminarpool");
		Collection<SeminarUserRegistration> userRegistrations = seminarpoolEntity
				.getSeminarUserRegistration();
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		for (SeminarUserRegistration userRegistration : userRegistrations) {
			userInfoList.add(getUserDao()
					.toUserInfo(userRegistration.getUser()));
		}
		return userInfoList;
	}

	@Override
	protected List handleGetDetailCourseregistrationsById(
			Long courseAllocationId) throws Exception {
		Validate
				.notNull(courseAllocationId,
						"handleGetDetailCourseregistrationsById ==> courseAllocationId cannot be null");
		CourseSeminarpoolAllocation courseAllocation = getCourseSeminarpoolAllocationDao()
				.load(courseAllocationId);
		Validate
				.notNull(courseAllocation,
						"handleGetDetailCourseregistrationsById ==> courseAllocation cannot be loaded");
		List<SeminarPriorityDetailInfo> infoList = new ArrayList<SeminarPriorityDetailInfo>();
		for (SeminarPriority entity : courseAllocation.getSeminarPriority()) {
			infoList.add(mapSeminarPriorityEntityToDetailInfo(entity));
		}
		return infoList;
	}

	private SeminarPriorityDetailInfo mapSeminarPriorityEntityToDetailInfo(
			SeminarPriority entity) {
		SeminarPriorityDetailInfo detailInfo = new SeminarPriorityDetailInfo();
		detailInfo.setCourseAllocationId(entity
				.getCourseSeminarPoolAllocation().getId());
		detailInfo.setId(entity.getId());
		detailInfo.setSeminarUserRegistrationId(entity
				.getSeminarUserRegistration().getId());
		detailInfo.setUserId(entity.getSeminarUserRegistration().getUser()
				.getId());
		detailInfo.setUserFirstName(entity.getSeminarUserRegistration()
				.getUser().getFirstName());
		detailInfo.setUserLastName(entity.getSeminarUserRegistration()
				.getUser().getLastName());
		detailInfo.setPriority(entity.getPriority());
		detailInfo.setCourseName(entity.getCourseSeminarPoolAllocation()
				.getCourse().getName());
		return detailInfo;
	}

	@Override
	protected void handleRemoveSeminarPriorityById(Long priorityId)
			throws Exception {
		Validate
				.notNull(priorityId,
						"handleRemoveSeminarPriorityById ==> priorityId cannot be null");
		SeminarPriority priorityEntity = getSeminarPriorityDao().load(
				priorityId);
		SeminarUserRegistration userRegistrationEntity = priorityEntity
				.getSeminarUserRegistration();
		if (userRegistrationEntity.getSeminarPriority().size() > 1) {
			userRegistrationEntity.removePriority(priorityEntity);
		} else {
			getSeminarpoolUserRegistrationService().removeUserRegistration(
					priorityEntity.getSeminarUserRegistration().getId());
		}
	}

	@Override
	protected List handleGetAllocationsByUserAndSeminarpool(Long userId,
			Long seminarpoolId) throws Exception {
		Validate
				.notNull(userId,
						"handleGetAllocationsByUserAndSeminarpool ==> userId cannot be null");
		Validate
				.notNull(seminarpoolId,
						"handleGetAllocationsByUserAndSeminarpool ==> seminarpoolId cannot be null");
		User user = getUserDao().load(userId);
		Validate
				.notNull(user,
						"handleGetAllocationsByUserAndSeminarpool ==> user cannot be loaded");
		List<SeminarPlaceAllocationInfo> allocationList = new ArrayList<SeminarPlaceAllocationInfo>();
		for (CourseGroup courseGroup : user.getCourseGroup()) {
			if (courseGroup.getCourseSeminarpoolAllocation().getSeminarpool()
					.getId().equals(seminarpoolId)) {
				SeminarPlaceAllocationInfo info = new SeminarPlaceAllocationInfo();
				info.setCourseId(courseGroup.getCourseSeminarpoolAllocation()
						.getCourse().getId());
				info.setCourseName(courseGroup.getCourseSeminarpoolAllocation()
						.getCourse().getName());
				info.setFirstName(user.getFirstName());
				info.setLastName(user.getLastName());
				info.setSeminarpoolId(courseGroup
						.getCourseSeminarpoolAllocation().getSeminarpool()
						.getId());
				info.setUserId(user.getId());
				info.setGroupName(courseGroup.getName());
				allocationList.add(info);
			}
		}
		return allocationList;
	}

	@Override
	protected List handleGetAllSeminarpoolAdmins(Long seminarpoolId)
			throws Exception {
		Validate
				.notNull(seminarpoolId,
						"handleGetAllocationsByUserAndSeminarpool ==> seminarpoolId cannot be null");
		Seminarpool seminarpool = getSeminarpoolDao().load(seminarpoolId);
		Validate
				.notNull(seminarpoolId,
						"handleGetAllocationsByUserAndSeminarpool ==> seminarpool cannot be loaded");
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		for (User user : seminarpool.getMembership().getMembers()) {
			userInfoList.add(getUserDao().toUserInfo(user));
		}
		return userInfoList;
	}

	@Override
	protected void handleDeactivateSeminar(
			CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo)
			throws Exception {
		Validate
				.notNull(courseSeminarpoolAllocationInfo,
						"handleDeactivateSeminar ==> courseSeminarpoolAllocation cannot be null");
		Validate
				.notNull(
						courseSeminarpoolAllocationInfo.getId(),
						"handleDeactivateSeminar ==> courseSeminarpoolAllocation.getId() cannot be null");
		CourseSeminarpoolAllocation courseAllocationEntity = getCourseSeminarpoolAllocationDao()
				.load(courseSeminarpoolAllocationInfo.getId());
		Validate
				.notNull(courseAllocationEntity,
						"handleDeactivateSeminar ==> Cannot load CourseSeminarpoolAllocation");
		courseAllocationEntity.setAccepted(false);
		getCourseSeminarpoolAllocationDao().update(courseAllocationEntity);

	}

	@Override
	protected List handleFindAcceptedCoursesInSeminarpool(Long seminarpoolId)
			throws Exception {
		Validate
				.notNull(seminarpoolId,
						"handleFindCoursesInSeminarpool ==> seminarpoolId cannot be null");
		Seminarpool seminarpoolEntity = getSeminarpoolDao().load(seminarpoolId);
		// SeminarpoolInfo seminarpoolInfo =
		// getSeminarpoolDao().toSeminarpoolInfo(seminarpoolEntity);
		Validate.notNull(seminarpoolEntity,
				"handleFindCoursesInSeminarpool ==> Cannot load Seminarpool");
		Collection<CourseSeminarpoolAllocation> courseAllocations = seminarpoolEntity
				.getCourseSeminarpoolAllocation();
		List<CourseSeminarpoolAllocationInfo> courseAllocationList = new ArrayList<CourseSeminarpoolAllocationInfo>();
		for (CourseSeminarpoolAllocation courseAllocation : courseAllocations) {
			// only if the course is accepted
			if (courseAllocation.isAccepted()) {
				courseAllocationList.add(getCourseSeminarpoolAllocationDao()
						.toCourseSeminarpoolAllocationInfo(courseAllocation));
			}
		}
		return courseAllocationList;
	}

	@Override
	protected boolean handleAddUserToAllocation(Long userId, Long courseGroupId)
			throws Exception {
		Validate.notNull(userId,
				"handleRemoveUserFromAllocation ==> userId cannot be null");
		Validate
				.notNull(courseGroupId,
						"handleRemoveUserFromAllocation ==> courseGroupId cannot be null");
		User user = getUserDao().load(userId);
		CourseGroup courseGroup = getCourseGroupDao().load(courseGroupId);
		Validate.notNull(user,
				"handleRemoveUserFromAllocation ==> user cannot be loaded");
		Validate
				.notNull(courseGroup,
						"handleRemoveUserFromAllocation ==> courseGroup cannot be loaded");
		if (!courseGroup.getUser().contains(user)) {
			courseGroup.addUser(user);
			getCourseGroupDao().update(courseGroup);
			getUserDao().update(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void handleConfirmAllocation(Long seminarpoolId) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected List handleFindSeminarpoolByDescription(String description)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List handleFindSeminarpoolsByDepartment(Long departmentId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List handleFindSeminarpoolsByInstitute(Long instituteId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleMoveUser(Long seminarpoolId, Long userId,
			Long oldCourseId, Long newCourseId) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void handleRemoveUserFromAllocation(Long userId,
			Long courseGroupId) throws Exception {
		Validate.notNull(userId,
				"handleRemoveUserFromAllocation ==> userId cannot be null");
		Validate
				.notNull(courseGroupId,
						"handleRemoveUserFromAllocation ==> courseGroupId cannot be null");
		User user = getUserDao().load(userId);
		CourseGroup courseGroup = getCourseGroupDao().load(courseGroupId);
		Validate.notNull(user,
				"handleRemoveUserFromAllocation ==> user cannot be loaded");
		Validate
				.notNull(courseGroup,
						"handleRemoveUserFromAllocation ==> courseGroup cannot be loaded");
		courseGroup.removeUser(user);
		getCourseGroupDao().update(courseGroup);
		getUserDao().update(user);
	}
}