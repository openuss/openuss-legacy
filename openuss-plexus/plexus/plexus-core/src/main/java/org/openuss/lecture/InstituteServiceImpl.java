// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.lecture.events.InstituteCreatedEvent;
import org.openuss.lecture.events.InstituteRemoveEvent;
import org.openuss.lecture.events.InstituteUpdatedEvent;
import org.openuss.security.Group;
import org.openuss.security.GroupItem;
import org.openuss.security.GroupType;
import org.openuss.security.Membership;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @see org.openuss.lecture.InstituteService
 * @author Ron Haus
 * @author Florian Dondorf
 * @author Ingo Düppe
 */
public class InstituteServiceImpl extends InstituteServiceBase {

	private static final Logger logger = Logger.getLogger(InstituteServiceImpl.class);

	/**
	 * @see org.openuss.lecture.InstituteService#create(org.openuss.lecture.InstituteInfo)
	 */
	protected Long handleCreate(InstituteInfo instituteInfo, Long userId) throws Exception {
		logger.debug("Starting method handleCreate");

		Validate.notNull(instituteInfo, "The Institute cannot be null");
		Validate.notNull(userId, "The User must have a valid ID");
		UserInfo user = getSecurityService().getUser(userId);
		Validate.notNull(user, "No valid User found corresponding to the ID " + userId);
		Validate.isTrue(instituteInfo.getId() == null, "The Institute shouldn't have an ID yet");

		// Ignore DepartmentID - one has to call applyAtDepartment right after
		instituteInfo.setDepartmentId(null);

		// Transform ValueObject into Entity
		Institute institute = this.getInstituteDao().instituteInfoToEntity(instituteInfo);

		// Create a default Membership for the Institute
		Membership membership = Membership.Factory.newInstance();
		institute.setMembership(membership);

		// Create the Institute
		this.getInstituteDao().create(institute);

		// FIXME AOP2Events - Kai, Indexing should not base on VOs!
		// Kai: Do not delete this!!! Set id of institute VO for indexing
		instituteInfo.setId(institute.getId());

		// Create default Groups for Institute
		GroupItem admins = new GroupItem();
		admins.setName("INSTITUTE_" + institute.getId() + "_ADMINS");
		admins.setLabel("autogroup_administrator_label");
		admins.setGroupType(GroupType.ADMINISTRATOR);
		Long adminsId = this.getOrganisationService().createGroup(institute.getId(), admins);
		Group adminsGroup = this.getGroupDao().load(adminsId);

		GroupItem assistants = new GroupItem();
		assistants.setName("INSTITUTE_" + institute.getId() + "_ASSISTANTS");
		assistants.setLabel("autogroup_assistant_label");
		assistants.setGroupType(GroupType.ASSISTANT);
		Long assistantsId = this.getOrganisationService().createGroup(institute.getId(), assistants);
		Group assistantsGroup = this.getGroupDao().load(assistantsId);

		GroupItem tutors = new GroupItem();
		tutors.setName("INSTITUTE_" + institute.getId() + "_TUTORS");
		tutors.setLabel("autogroup_tutor_label");
		tutors.setGroupType(GroupType.TUTOR);
		Long tutorsId = this.getOrganisationService().createGroup(institute.getId(), tutors);
		Group tutorsGroup = this.getGroupDao().load(tutorsId);

		// Security
		getSecurityService().createObjectIdentity(institute, null);
		getSecurityService().setPermissions(adminsGroup, institute, LectureAclEntry.INSTITUTE_ADMINISTRATION);
		getSecurityService().setPermissions(assistantsGroup, institute, LectureAclEntry.INSTITUTE_ASSIST);
		getSecurityService().setPermissions(tutorsGroup, institute, LectureAclEntry.INSTITUTE_TUTOR);

		// Add Owner to Members and the group of Administrators
		getOrganisationService().addMember(institute.getId(), userId);
		getOrganisationService().addUserToGroup(userId, adminsGroup.getId());

		// FIXME Create Application
		// handleApplyAtDepartment(instituteInfo.getId(), departmentId, userId);
		
		getEventPublisher().publishEvent(new InstituteCreatedEvent(institute));
		
		return institute.getId();
	}

	/**
	 * @see org.openuss.lecture.InstituteService#update(org.openuss.lecture.InstituteInfo)
	 */
	protected void handleUpdate(InstituteInfo instituteInfo) throws Exception {
		logger.debug("Starting method handleUpdate");
		Validate.notNull(instituteInfo, "InstituteService.handleUpdate - the Institute cannot be null");
		Validate.notNull(instituteInfo.getId(), "InstituteService.handleUpdate - the Institute must have a valid ID");

		// Check changes of Department
		Department department = getDepartmentDao().load(instituteInfo.getDepartmentId());
		Institute instituteOld = getInstituteDao().load(instituteInfo.getId());
		if (!instituteOld.getDepartment().equals(department)) {
			logger.debug("The department can not be changed. You have to apply first.");
			instituteInfo.setDepartmentId(instituteOld.getDepartment().getId());
		}

		// Transform ValueObject into Entity
		Institute institute = this.getInstituteDao().instituteInfoToEntity(instituteInfo);

		// Update Entity
		this.getInstituteDao().update(institute);
		
		getEventPublisher().publishEvent(new InstituteUpdatedEvent(institute));
	}

	/**
	 * @see org.openuss.lecture.InstituteService#removeInstitute(java.lang.Long)
	 */
	protected void handleRemoveInstitute(Long instituteId) throws Exception {
		logger.debug("Starting method handleRemoveInstitute for InstituteID " + instituteId);

		Validate.notNull(instituteId, "The InstituteID cannot be null");
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute, "No Institute found to the corresponding ID " + instituteId);
		Validate.isTrue(institute.getCourseTypes().isEmpty(), "The Institute still contains CourseTypes");
		
		getEventPublisher().publishEvent(new InstituteRemoveEvent(institute));

		// Remove Security
		this.getSecurityService().removeAllPermissions(institute);
		this.getSecurityService().removeObjectIdentity(institute);

		// Clear Membership
		this.getMembershipService().clearMembership(institute.getMembership());

		// Remove Institute
		institute.getDepartment().remove(institute);
		this.getInstituteDao().remove(institute);
	}

	/**
	 * @see org.openuss.lecture.InstituteService#findInstitute(java.lang.Long)
	 */
	protected InstituteInfo handleFindInstitute(Long instituteId) throws Exception {
		Validate.notNull(instituteId, "The instituteId cannot be null");
		return (InstituteInfo) getInstituteDao().load(InstituteDao.TRANSFORM_INSTITUTEINFO, instituteId);
	}

	/**
	 * @see org.openuss.lecture.InstituteService#findInstitutesByDepartment(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindInstitutesByDepartment(Long departmentId) throws Exception {
		Validate.notNull(departmentId, "The departmentId cannot be null");

		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department, "No Department found corresponding to the ID " + departmentId);

		List instituteInfos = new ArrayList();
		for (Institute institute : department.getInstitutes()) {
			instituteInfos.add(this.getInstituteDao().toInstituteInfo(institute));
		}

		return instituteInfos;
	}

	/**
	 * @see org.openuss.lecture.InstituteService#findInstitutesByDepartmentAndEnabled(java.lang.Long,
	 *      java.lang.Boolean)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindInstitutesByDepartmentAndEnabled(Long departmentId, boolean enabled) throws Exception {
		Validate.notNull(departmentId, "The departmentId cannot be null");

		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department, "No University found corresponding to the ID " + departmentId);

		return getInstituteDao().findByDepartmentAndEnabled(InstituteDao.TRANSFORM_INSTITUTEINFO, department, enabled);
	}

	/**
	 * @see org.openuss.lecture.InstituteService#findInstitutesByEnabled(java.lang.Boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<InstituteInfo> handleFindAllInstitutes(boolean enabledOnly) throws Exception {
		if (enabledOnly) {
			return getInstituteDao().findByEnabled(InstituteDao.TRANSFORM_INSTITUTEINFO, enabledOnly);
		} else {
			return new ArrayList<InstituteInfo>(getInstituteDao().loadAll(InstituteDao.TRANSFORM_INSTITUTEINFO));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected InstituteSecurity handleGetInstituteSecurity(Long instituteId) throws Exception {
		return (InstituteSecurity) getInstituteDao().load(InstituteDao.TRANSFORM_INSTITUTESECURITY, instituteId);
	}

	@Override
	protected Long handleApplyAtDepartment(ApplicationInfo applicationInfo) throws Exception {
		Validate.notNull(applicationInfo, "The applicationInfo cannot be null.");
		return handleApplyAtDepartment(applicationInfo.getInstituteInfo().getId(), applicationInfo.getDepartmentInfo()
				.getId(), applicationInfo.getApplyingUserInfo().getId());
	}

	@Override
	protected Long handleApplyAtDepartment(Long instituteId, Long departmentId, Long userId) throws Exception {
		logger.debug("handleApplication started");

		Validate.notNull(instituteId, "The InstituteID cannot be null.");
		Validate.notNull(departmentId, "The DepartmentID cannot be null.");
		Validate.notNull(userId, "The UserID cannot be null.");
		Institute institute = getInstituteDao().load(instituteId);
		Validate.notNull(institute, "No Institute found corresponding to the InstituteID " + instituteId);

		deleteOldNotConfirmedApplications(institute);
		Department department = loadDepartment(departmentId);
		User user = loadUser(userId);

		if (department.getDepartmentType() == DepartmentType.NONOFFICIAL) {
			deleteAllConfirmedApplications(institute);

			Application application = createDefaultApplication(institute, department, user, true);
			// Add Institute to Department
			verifyCoursePeriodsAssoziations(department, institute);
			department.add(institute);
			return application.getId();

		} else {
			// Create not-confirmed Application for official Department
			Application application = createDefaultApplication(institute, department, user, false);

			if (institute.getDepartment() == null) {
				// Add Institute to default Department of University including a
				// confirmed Application
				University university = department.getUniversity();
				Department departmentDefault = this.getDepartmentDao().findByUniversityAndDefault(university, true);
				createDefaultApplication(institute, departmentDefault, user, true);
				departmentDefault.add(institute);
			}

			return application.getId();
		}
	}

	@Override
	protected void handleSetInstituteStatus(Long instituteId, boolean status) {
		Validate.notNull(instituteId, "InstituteService.setInstituteStatus - the instituteId cannot be null.");
		Validate.notNull(status, "InstituteService.setInstituteStatus - status cannot be null.");
	
		// Load institute
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute,	"Instiute cannot be found with the corresponding instituteId "	+ instituteId);
	
		// Only allow institute to be enabled when the super-ordinate department is enabled
		if (status) {
			Validate.isTrue(institute.getDepartment().isEnabled(), "The institute cannot be enabled because the associated department is disabled.");
		}
	
		// Set status
		institute.setEnabled(status);
		this.update(this.getInstituteDao().toInstituteInfo(institute));
	
		// Set subordinate courses to "disabled" if the institution was just
		// disabled
		if (!status) {
			this.getInstituteDao().update(institute);
			for (CourseType courseType : institute.getCourseTypes()) {
				for (Course course : courseType.getCourses()) {
					course.setEnabled(false);
					this.getCourseDao().update(course);
				}
			}
		}
	}

	@Override
	protected List<ApplicationInfo> handleFindApplicationsByInstitute(Long instituteId) throws Exception {
		Validate.notNull(instituteId, "InstituteService.findApplicationByInstitute - the instituteId cannot be null.");
	
		// Load institute
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute,
				"InstiuteService.findApplicationByInstitute - instiute cannot be found with the corresponding instituteId "
						+ instituteId);
	
		List<ApplicationInfo> applicationInfos = new ArrayList<ApplicationInfo>();
		for (Application application : institute.getApplications()) {
			applicationInfos.add(this.getApplicationDao().toApplicationInfo(application));
		}
	
		return applicationInfos;
	}

	@Override
	protected ApplicationInfo handleFindApplicationByInstituteAndConfirmed(Long instituteId, boolean confirmed)
			throws Exception {
		Validate.notNull(instituteId,
				"InstituteService.findApplicationByInstituteAndConfirmed - the instituteId cannot be null.");
		Validate.notNull(confirmed,
				"InstituteService.findApplicationByInstituteAndConfirmed - the confirmed cannot be null.");
	
		// Load institute
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute,
				"InstiuteService.findApplicationByInstituteAndConfirmed - instiute cannot be found with the corresponding instituteId "
						+ instituteId);
	
		for (Application application : institute.getApplications()) {
			if (application.isConfirmed() == confirmed) {
				return this.getApplicationDao().toApplicationInfo(application);
			}
		}
	
		return null;
	}

	@Override
	protected void handleRemoveCompleteInstituteTree(Long instituteId) throws Exception {
		logger.debug("Starting method handleRemoveCompleteInstituteTree for InstituteID " + instituteId);
	
		Validate.notNull(instituteId, "The InstituteID cannot be null");
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute, "No Institute found to the corresponding ID " + instituteId);
		
		getEventPublisher().publishEvent(new InstituteRemoveEvent(institute));
	
		if (!institute.getCourseTypes().isEmpty()) {
			// Remove CourseTypes
			List<CourseType> courseTypes = new ArrayList<CourseType>();
			for (CourseType courseType : institute.getCourseTypes()) {
				courseTypes.add(courseType);
			}
			for (CourseType courseType : courseTypes) {
				this.getCourseTypeService().removeCourseType(courseType.getId());
			}
		}
	
		// TODO: Remove Applications
	
		// Remove Security
		this.getSecurityService().removeAllPermissions(institute);
		this.getSecurityService().removeObjectIdentity(institute);
	
		// Clear Membership
		this.getMembershipService().clearMembership(institute.getMembership());
	
		// Remove Institute
		institute.getDepartment().remove(institute);
		this.getInstituteDao().remove(institute);
	
	}

	protected void handleSetGroupOfMember(InstituteMember member, Long instituteId) throws Exception {
		logger.debug("Setting groups of member");
		Institute institute = getInstituteDao().load(instituteId);
	
		User user = this.getUserDao().load(member.getId());
		Validate.notNull(user, "No user found with the userId "	+ member.getId());
	
		if (!institute.getMembership().getMembers().contains(user)) {
			throw new LectureException("User is not a member of the institute!");
		}
	
		// cache group ids
		final List<Long> groupIds = new ArrayList<Long>();
		for (InstituteGroup group : member.getGroups()) {
			groupIds.add(group.getId());
		}
	
		// remove and add user to the new groups
		final SecurityService securityService = getSecurityService();
		for (Group group : institute.getMembership().getGroups()) {
			if (group.getMembers().contains(user) && !(groupIds.contains(group.getId()))) {
				securityService.removeAuthorityFromGroup(user, group);
			} else if (!group.getMembers().contains(user) && (groupIds.contains(group.getId()))) {
				securityService.addAuthorityToGroup(user, group);
			}
		}
	}

	// FIXME AOP2Events - Get rid of this method
	protected void handleResendActivationCode(InstituteInfo instituteInfo, Long userId) {
		Validate.notNull(instituteInfo, "InstituteInfo cannot be null.");
		Validate.notNull(instituteInfo.getId(), "id cannot be null.");
		Institute institute = this.getInstituteDao().load(instituteInfo.getId());
		Validate.notNull(institute, "No institute found with the instiuteId " + instituteInfo.getId());
		// Do not delete this method although it seems that it does nothing.
		// When this stub is called an aspect starts to send an email.
	}

	private Application createDefaultApplication(Institute institute, Department department, User user,
			boolean confirmed) {
		// Create confirmed Application for non-official Department
		Application application = new ApplicationImpl();
		application.setApplicationDate(new Date());
		application.setApplyingUser(user);
		if (confirmed) {
			application.setConfirmationDate(new Date());
			application.setConfirmed(true);
			application.setConfirmingUser(user);
		} else {
			application.setConfirmationDate(null);
			application.setConfirmed(false);
			application.setConfirmingUser(null);
		}
		application.setDescription("Automatically created Application");
		application.add(institute);
		application.add(department);
		this.getApplicationDao().create(application);
		return application;
	}

	private void deleteAllConfirmedApplications(Institute institute) {
		// Delete all confirmed Applications of the Institute (should actually
		// only be max 1)
		List<Application> applicationsOldConfirmed = new ArrayList<Application>();
		for (Application applicationOld : institute.getApplications()) {
			if (applicationOld.isConfirmed()) {
				applicationsOldConfirmed.add(applicationOld);
			}
		}
		for (Application applicationOld : applicationsOldConfirmed) {
			try {
				applicationOld.remove(applicationOld.getInstitute());
				applicationOld.remove(applicationOld.getDepartment());
				this.getApplicationDao().remove(applicationOld);
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	private void deleteOldNotConfirmedApplications(Institute institute) {
		// Delete all not-confirmed Applications of the Institute (should
		// actually only be max 1)
		List<Application> applicationsOldNotConfirmed = new ArrayList<Application>();
		for (Application application : institute.getApplications()) {
			if (!application.isConfirmed()) {
				applicationsOldNotConfirmed.add(application);
			}
		}
		for (Application application : applicationsOldNotConfirmed) {
			try {
				application.remove(application.getInstitute());
				application.remove(application.getDepartment());
				this.getApplicationDao().remove(application);
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	private boolean hasUniversityChanged(Department department, Institute institute) {
		return institute.getDepartment() != null
				&& !institute.getDepartment().getUniversity().equals(department.getUniversity());
	}

	private Department loadDepartment(Long departmentId) {
		// Load Department
		Department department = getDepartmentDao().load(departmentId);
		Validate.notNull(department, " No Department found corresponding to the DepartmentID " + departmentId);
		return department;
	}

	private User loadUser(Long userId) {
		// Load User
		User user = getSecurityService().getUserObject(userId);
		Validate.notNull(user, " No User found corresponding to the UserID " + userId);
		return user;
	}

	/**
	 * Checks if the institute changed the university. If so then the
	 * preexisting courses will be moved in the default period of the
	 * university.
	 * 
	 * @param department
	 * @param institute
	 */
	private void verifyCoursePeriodsAssoziations(Department department, Institute institute) {
		assert department != null;
		assert institute != null;
		
		if (hasUniversityChanged(department, institute)) {
			University newUniversity = department.getUniversity();
			University oldUniversity = institute.getDepartment().getUniversity();
			
			Map<Period, Period> periodMapping = PeriodMapping.generate(oldUniversity.getPeriods(), newUniversity.getPeriods());
			
			for (CourseType courseType : institute.getCourseTypes()) {
				for (Course course : courseType.getCourses()) {
					course.setPeriod(periodMapping.get(course.getPeriod()));
				}
			}
			getInstituteDao().update(institute);
		}
	}
}