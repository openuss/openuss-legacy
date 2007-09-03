// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.security.Authority;
import org.openuss.security.Group;
import org.openuss.security.GroupItem;
import org.openuss.security.GroupType;
import org.openuss.security.Membership;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @see org.openuss.lecture.InstituteService
 * @author Ron Haus
 * @author Florian Dondorf
 */
public class InstituteServiceImpl extends org.openuss.lecture.InstituteServiceBase {

	private static final Logger logger = Logger.getLogger(InstituteServiceImpl.class);

	/**
	 * @see org.openuss.lecture.InstituteService#create(org.openuss.lecture.InstituteInfo)
	 */
	protected java.lang.Long handleCreate(InstituteInfo instituteInfo, Long userId) throws java.lang.Exception {

		logger.debug("Starting method handleCreate");

		Validate.notNull(instituteInfo, "InstituteService.handleCreate - the Institute cannot be null");
		Validate.notNull(userId, "InstituteService.handleCreate - the User must have a valid ID");
		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "InstituteService.handleCreate - no valid User found corresponding to the ID " + userId);
		Validate.isTrue(instituteInfo.getId() == null,
				"InstituteService.handleCreate - the Institute shouldn't have an ID yet");
		Validate.isTrue(instituteInfo.getDepartmentId() != null,
				"InstituteService.handleCreate - the DepartmentID cannot be null");

		// Transform ValueObject into Entity
		Institute instituteEntity = this.getInstituteDao().instituteInfoToEntity(instituteInfo);
		Department department = instituteEntity.getDepartment();
		Validate.notNull(department, "InstituteService.handleCreate - "
				+ "no valid Department found corresponding to the ID " + instituteInfo.getDepartmentId());

		// Create a default Membership for the University
		Membership membership = Membership.Factory.newInstance();
		instituteEntity.setMembership(membership);

		// Create the Institute
		department.add(instituteEntity);
		this.getInstituteDao().create(instituteEntity);
		Validate.notNull(instituteEntity.getId(), "InstituteService.handleCreate - Couldn't create Institute");

		// FIXME - Kai, Indexing should not base on VOs!
		// Kai: Do not delete this!!! Set id of institute VO for indexing
		instituteInfo.setId(instituteEntity.getId());

		// Create Application for OFFICIAL Departments
		if (department.getDepartmentType().equals(DepartmentType.OFFICIAL)) {
			Application application = Application.Factory.newInstance();
			application.setApplicationDate(new Date());
			application.setDepartment(department);
			application.setInstitute(instituteEntity);
			application.setConfirmed(true);
			application.setApplyingUser(user);
			application.setConfirmingUser(user);

			application.add(department);
			application.add(instituteEntity);
			this.getDepartmentDao().update(department);
		}

		// Create default Groups for Institute
		GroupItem admins = new GroupItem();
		admins.setName("INSTITUTE_" + instituteEntity.getId() + "_ADMINS");
		admins.setLabel("autogroup_administrator_label");
		admins.setGroupType(GroupType.ADMINISTRATOR);
		Long adminsId = this.getOrganisationService().createGroup(instituteEntity.getId(), admins);
		Group adminsGroup = this.getGroupDao().load(adminsId);

		GroupItem assistants = new GroupItem();
		assistants.setName("INSTITUTE_" + instituteEntity.getId() + "_ASSISTANTS");
		assistants.setLabel("autogroup_assistant_label");
		assistants.setGroupType(GroupType.ASSISTANT);
		Long assistantsId = this.getOrganisationService().createGroup(instituteEntity.getId(), assistants);
		Group assistantsGroup = this.getGroupDao().load(assistantsId);

		GroupItem tutors = new GroupItem();
		tutors.setName("INSTITUTE_" + instituteEntity.getId() + "_TUTORS");
		tutors.setLabel("autogroup_tutor_label");
		tutors.setGroupType(GroupType.TUTOR);
		Long tutorsId = this.getOrganisationService().createGroup(instituteEntity.getId(), tutors);
		Group tutorsGroup = this.getGroupDao().load(tutorsId);

		// Security
		this.getSecurityService().createObjectIdentity(instituteEntity, null);
		this.getSecurityService()
				.setPermissions(adminsGroup, instituteEntity, LectureAclEntry.INSTITUTE_ADMINISTRATION);
		this.getSecurityService().setPermissions(assistantsGroup, instituteEntity, LectureAclEntry.INSTITUTE_ASSIST);
		this.getSecurityService().setPermissions(tutorsGroup, instituteEntity, LectureAclEntry.INSTITUTE_TUTOR);

		// Add Owner to Members and the group of Administrators
		this.getOrganisationService().addMember(instituteEntity.getId(), userId);
		this.getOrganisationService().addUserToGroup(userId, adminsGroup.getId());

		// TODO: Fire createdInstitute event to bookmark the Institute for the User who created it

		return instituteEntity.getId();
	}

	/**
	 * @see org.openuss.lecture.InstituteService#update(org.openuss.lecture.InstituteInfo)
	 */
	protected void handleUpdate(org.openuss.lecture.InstituteInfo instituteInfo) throws java.lang.Exception {

		logger.debug("Starting method handleUpdate");

		Validate.notNull(instituteInfo, "InstituteService.handleUpdate - the Institute cannot be null");
		Validate.notNull(instituteInfo.getId(), "InstituteService.handleUpdate - the Institute must have a valid ID");

		// Check changes of department
		Institute instituteEntity = this.getInstituteDao().load(instituteInfo.getId());
		Department department = this.getDepartmentDao().load(instituteInfo.getDepartmentId());
		if (!instituteEntity.getDepartment().equals(department)) {
			throw new InstituteServiceException("The department can not be changed. You have to apply first.");
		}

		// Transform ValueObject into Entity
		Institute institute = this.getInstituteDao().instituteInfoToEntity(instituteInfo);

		// Update Entity
		this.getInstituteDao().update(institute);
	}

	/**
	 * @see org.openuss.lecture.InstituteService#removeInstitute(java.lang.Long)
	 */
	protected void handleRemoveInstitute(java.lang.Long instituteId) throws java.lang.Exception {
		logger.debug("Starting method handleRemoveInstitute for InstituteID " + instituteId);

		Validate.notNull(instituteId, "InstituteService.handleRemoveInstitute - the InstituteID cannot be null");
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute,
				"InstituteService.handleRemoveInstitute - no Institute found to the corresponding ID " + instituteId);
		Validate.isTrue(institute.getCourseTypes().isEmpty(),
				"InstituteService.handleRemoveInstitute - the Institute still contains CourseTypes");

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
	protected org.openuss.lecture.InstituteInfo handleFindInstitute(java.lang.Long instituteId)
			throws java.lang.Exception {

		Validate.notNull(instituteId, "InstituteService.handleFindInstitute - the instituteId cannot be null");

		Institute institute = (Institute) this.getInstituteDao().load(instituteId);
		Validate.notNull(institute,
				"InstituteService.handleFindInstitute - no Institute found corresponding to the ID " + instituteId);

		return this.getInstituteDao().toInstituteInfo(institute);
	}

	/**
	 * @see org.openuss.lecture.InstituteService#findInstitutesByDepartment(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected java.util.List handleFindInstitutesByDepartment(java.lang.Long departmentId) throws java.lang.Exception {

		Validate.notNull(departmentId,
				"InstituteService.handleFindInstitutesByDepartment - the departmentId cannot be null");

		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department,
				"InstituteService.handleFindInstitutesByDepartment - no Department found corresponding to the ID "
						+ departmentId);

		List instituteInfos = new ArrayList();
		for (Institute institute : department.getInstitutes()) {
			instituteInfos.add(this.getInstituteDao().toInstituteInfo(institute));
		}

		return instituteInfos;
	}

	/**
	 * @see org.openuss.lecture.InstituteService#findInstitutesByDepartmentAndEnabled(java.lang.Long, java.lang.Boolean)
	 */
	@SuppressWarnings( { "unchecked" })
	protected java.util.List handleFindInstitutesByDepartmentAndEnabled(java.lang.Long departmentId, boolean enabled)
			throws java.lang.Exception {

		Validate.notNull(departmentId,
				"InstituteService.handleFindInstitutesByDepartmentAndEnabled - the departmentId cannot be null");

		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department,
				"InstituteService.handleFindDepartmentsByUniversityAndEnabled - no University found corresponding to the ID "
						+ departmentId);

		return this.getInstituteDao().findByDepartmentAndEnabled(InstituteDao.TRANSFORM_INSTITUTEINFO, department,
				enabled);
	}

	/**
	 * @see org.openuss.lecture.InstituteService#findInstitutesByEnabled(java.lang.Boolean)
	 */
	@SuppressWarnings( { "unchecked" })
	@Override
	protected List handleFindInstitutesByEnabled(boolean enabledOnly) throws Exception {

		Validate.notNull(enabledOnly,
				"InstituteServiceImpl.handleFindInstitutesByEnabled - enabledOnly cannot be null.");

		return this.getInstituteDao().findByEnabled(enabledOnly);
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

		Validate.notNull(applicationInfo, "InstituteService.applyAtDepartment - the applicationInfo cannot be null.");
		Validate.isTrue(applicationInfo.getConfirmationDate() == null,
				"InstituteService.applyAtDepartment - you cannot set the Confirmation Date yet.");

		Validate.isTrue(!applicationInfo.isConfirmed(),
				"InstituteService.applyAtDepartment - you Application cannot be confirmed yet.");

		Validate.isTrue(applicationInfo.getConfirmingUserInfo() == null,
				"InstituteService.applyAtDepartment - you cannot set the confirming User yet.");

		if ((applicationInfo.getApplicationDate() == null)) {
			applicationInfo.setApplicationDate(new Date());
		} else {
			Validate.isTrue(!applicationInfo.getApplicationDate().after(new Date()),
					"InstituteService.applyAtDepartment - Application Date is in the Future.");
		}

		// Transform VO to entity
		Application application = this.getApplicationDao().applicationInfoToEntity(applicationInfo);
		Validate.notNull(application, "InstituteService.applyAtDepartment - cannot transform value object to entity");
		Validate.isTrue(application.getDepartment().getDepartmentType().equals(DepartmentType.OFFICIAL),
				"InstituteService.applyAtDepartment - an Application is only necessary for official Departments");
		Validate.isTrue(application.getInstitute().getApplication() == null,
				"InstituteService.applyAtDepartment - the Institue already has an application, remove/signoff before");

		application.add(application.getDepartment());
		application.add(application.getInstitute());
		this.getApplicationDao().create(application);

		applicationInfo.setId(application.getId());

		return application.getId();
	}

	@Override
	protected void handleRemoveUnconfirmedApplication(Long applicationId) throws Exception {

		Validate.notNull(applicationId,
				"DepartmentService.handleRemoveUnconfirmedApplication - the applicationId cannot be null");

		Application application = this.getApplicationDao().load(applicationId);
		Validate.notNull(application,
				"DepartmentService.handleRemoveUnconfirmedApplication - no Application found corresponding to the ID "
						+ applicationId);
		Validate.isTrue(!application.isConfirmed(),
				"DepartmentService.handleRemoveUnconfirmedApplication - the Application is already confirmed");

		application.remove(application.getDepartment());
		application.remove(application.getInstitute());
		this.getApplicationDao().remove(application);
	}

	@Override
	public void handleSetInstituteStatus(Long instituteId, boolean status) {
		Validate.notNull(instituteId, "InstituteService.setInstituteStatus - the instituteId cannot be null.");
		Validate.notNull(status, "InstituteService.setInstituteStatus - status cannot be null.");

		// Load institute
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute,
				"InstiuteService.setInstituteStatus - instiute cannot be found with the corresponding instituteId "
						+ instituteId);

		// Set status
		institute.setEnabled(status);
		this.update(this.getInstituteDao().toInstituteInfo(institute));
	}

	@Override
	public boolean handleIsNoneExistingInstituteShortcut(InstituteInfo self, String shortcut) throws Exception {

		Institute found = getInstituteDao().findByShortcut(shortcut);
		InstituteInfo foundInfo = null;
		if (found != null) {
			foundInfo = this.getInstituteDao().toInstituteInfo(found);
		}
		return isEqualOrNull(self, foundInfo);
	}

	@Override
	protected ApplicationInfo handleFindApplicationByInstitute(Long instituteId) throws Exception {
		Validate.notNull(instituteId, "InstituteService.findApplicationByInstitute - the instituteId cannot be null.");

		// Load institute
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute,
				"InstiuteService.findApplicationByInstitute - instiute cannot be found with the corresponding instituteId "
						+ instituteId);

		if (institute.getApplication() == null) {
			return null;
		} else {
			return this.getApplicationDao().toApplicationInfo(institute.getApplication());
		}
	}

	@Override
	protected void handleRemoveCompleteInstituteTree(Long instituteId) throws Exception {
		logger.debug("Starting method handleRemoveCompleteInstituteTree for InstituteID " + instituteId);

		Validate.notNull(instituteId,
				"InstituteService.handleRemoveCompleteInstituteTree - the InstituteID cannot be null");
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute,
				"InstituteService.handleRemoveCompleteInstituteTree - no Institute found to the corresponding ID "
						+ instituteId);

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

		// Remove Security
		this.getSecurityService().removeAllPermissions(institute);
		this.getSecurityService().removeObjectIdentity(institute);

		// Clear Membership
		this.getMembershipService().clearMembership(institute.getMembership());

		// Remove Institute
		institute.getDepartment().remove(institute);
		this.getInstituteDao().remove(institute);

	}

	public void handleSetGroupOfMember(InstituteMember member, Long instituteId) throws Exception {
		logger.debug("Setting groups of member");
		Institute institute = getInstituteDao().load(instituteId);

		User user = this.getUserDao().load(member.getId());
		Validate.notNull(user, "InstituteServiceImpl.handleSetGroupOfMember -" + "no user found with the userId "
				+ member.getId());

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

	/*------------------- private methods -------------------- */

	/**
	 * Convenience method for isNonExisting methods.<br/> Checks whether or not the found record is equal to self
	 * entry.
	 * <ul>
	 * <li>self == null AND found == null => <b>true</b></li>
	 * <li>self == null AND found <> null => <b>false</b></li>
	 * <li>self <> null AND found == null => <b>true</b></li>
	 * <li>self <> null AND found <> null AND self == found => <b>true</b></li>
	 * <li>self <> null AND found <> null AND self <> found => <b>false</b></li>
	 * </ul>
	 * 
	 * @param self
	 *            current record
	 * @param found
	 *            in database
	 * @return true or false
	 */
	private boolean isEqualOrNull(Object self, Object found) {
		if (self == null || found == null) {
			return found == null;
		} else {
			return self.equals(found);
		}
	}

	/*------------------- private methods -------------------- */

	// TODO: Add Set of listeners
	// TODO: Method unregisterListener
	// TODO: Method fireRemovingInstitute (Institute institute)
	// TODO: Method fireCreatedInstitute (Institute institute)
}