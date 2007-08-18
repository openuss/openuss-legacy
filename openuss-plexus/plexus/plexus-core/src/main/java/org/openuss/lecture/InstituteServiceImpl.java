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
import org.openuss.security.Group;
import org.openuss.security.GroupItem;
import org.openuss.security.GroupType;
import org.openuss.security.Membership;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @see org.openuss.lecture.InstituteService
 * @author Ron Haus
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
		Validate.notNull(user, "InstituteService.handleCreate - no valid User found corresponding to the ID "+userId);
		Validate.isTrue(instituteInfo.getId() == null,
				"InstituteService.handleCreate - the Institute shouldn't have an ID yet");
		Validate.isTrue(instituteInfo.getDepartmentId() != null,
				"InstituteService.handleCreate - the DepartmentID cannot be null");

		
		
		// Transform ValueObject into Entity
		Institute instituteEntity = this.getInstituteDao().instituteInfoToEntity(instituteInfo);
		Department department = instituteEntity.getDepartment();
		Validate.notNull(department, "InstituteService.handleCreate - no valid Department found corresponding to the ID "+instituteInfo.getDepartmentId());

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

		//Create Application for OFFICIAL Departments
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
		}
		
		// Create default Groups for Institute
		GroupItem admins = new GroupItem();
		admins.setName("INSTITUTE_" + instituteEntity.getId() + "_ADMINS");
		admins.setLabel("autogroup_administrator_label");
		admins.setGroupType(GroupType.ADMINISTRATOR);
		Group adminsGroup = this.getOrganisationService().createGroup(instituteEntity.getId(), admins);

		GroupItem assistants = new GroupItem();
		assistants.setName("INSTITUTE_" + instituteEntity.getId() + "_ASSISTANTS");
		assistants.setLabel("autogroup_assistant_label");
		assistants.setGroupType(GroupType.ASSISTANT);
		Group assistantsGroup = this.getOrganisationService().createGroup(instituteEntity.getId(), assistants);

		GroupItem tutors = new GroupItem();
		tutors.setName("INSTITUTE_" + instituteEntity.getId() + "_TUTORS");
		tutors.setLabel("autogroup_tutor_label");
		tutors.setGroupType(GroupType.TUTOR);
		Group tutorsGroup = this.getOrganisationService().createGroup(instituteEntity.getId(), tutors);

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

		// Transform ValueObject into Entity
		Institute institute = this.getInstituteDao().instituteInfoToEntity(instituteInfo);

		// Update Entity
		this.getInstituteDao().update(institute);
	}

	/**
	 * @see org.openuss.lecture.InstituteService#removeInstitute(java.lang.Long)
	 */
	protected void handleRemoveInstitute(java.lang.Long instituteId) throws java.lang.Exception {

		logger.debug("Starting method handleRemoveInstitute");

		Validate.notNull(instituteId, "InstituteService.handleRemoveInstitute - the InstituteID cannot be null");

		// Get Institute entity
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute,
				"InstituteService.handleRemoveInstitute - no Institute found to the corresponding ID " + instituteId);

		// TODO: Fire removedInstitute event to delete all bookmarks
		// Fire removedCourseTypes event to delete all bookmarks
		// Fire removedCourses event to delete all bookmarks

		this.getInstituteDao().remove(instituteId);

		// fire events
		/*
		 * for (Course course : institute.getCourses()) { fireRemovingCourse(course); }
		 * 
		 * for (CourseType courseType : institute.getCourseTypes()) { fireRemovingCourseType(courseType); }
		 * 
		 * fireRemovingInstitute(institute);
		 */
		// TODO InstituteCodes need to be removed before
		// getRegistrationService().removeInstituteCodes(institute);
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
	protected java.util.List handleFindInstitutesByDepartmentAndEnabled(java.lang.Long departmentId,
			java.lang.Boolean enabled) throws java.lang.Exception {

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
	protected List handleFindInstitutesByEnabled(Boolean enabledOnly) throws Exception {

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
		if (applicationInfo.getConfirmed() == null) {
			applicationInfo.setConfirmed(false);
		} else {
			Validate.isTrue(!applicationInfo.getConfirmed(),
					"InstituteService.applyAtDepartment - you Application cannot be confirmed yet.");
		}
		Validate.isTrue(applicationInfo.getConfirmingUserId() == null,
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

		this.getApplicationDao().create(application);

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
		Validate.isTrue(!application.getConfirmed(),
				"DepartmentService.handleRemoveUnconfirmedApplication - the Application is already confirmed");

		application.getDepartment().getApplications().remove(application);
		application.getInstitute().setApplication(null);
		this.getApplicationDao().remove(application);

	}

	@Override
	public boolean handleIsNoneExistingInstituteShortcut(InstituteInfo self, String shortcut) throws Exception {
		Institute found = getInstituteDao().findByShortcut(shortcut);
		return isEqualOrNull(self, found);
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