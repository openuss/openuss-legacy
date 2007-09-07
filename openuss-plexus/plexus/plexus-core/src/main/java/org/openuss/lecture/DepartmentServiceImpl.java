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
 * @see org.openuss.lecture.DepartmentService
 * @author Florian Dondorf
 * @author Ron Haus
 */
public class DepartmentServiceImpl extends org.openuss.lecture.DepartmentServiceBase {

	private static final Logger logger = Logger.getLogger(DepartmentServiceImpl.class);

	/**
	 * @see org.openuss.lecture.DepartmentService#create(org.openuss.lecture.DepartmentInfo, java.lang.Long)
	 */
	protected java.lang.Long handleCreate(org.openuss.lecture.DepartmentInfo department, java.lang.Long userId)
			throws java.lang.Exception {

		logger.debug("Starting method handleCreate");

		Validate.notNull(department, "DepartmentService.handleCreate - the Department cannot be null");
		Validate.notNull(userId, "DepartmentService.handleCreate - the User must have a valid ID");

		Validate.isTrue(department.getId() == null,
				"DepartmentService.handleCreate - the Department shouldn't have an ID yet");
		Validate.isTrue(!department.isDefaultDepartment(),
				"DepartmentService.handleCreate - You cannot create a default Department!");

		// Transform ValueObject into Entity
		Department departmentEntity = this.getDepartmentDao().departmentInfoToEntity(department);

		// Create a default Membership for the University
		Membership membership = Membership.Factory.newInstance();
		departmentEntity.setMembership(membership);

		// Create the Department and add it to the University
		departmentEntity.getUniversity().add(departmentEntity);
		this.getDepartmentDao().create(departmentEntity);
		Validate.notNull(departmentEntity.getId(), "DepartmentService.handleCreate - Couldn't create Department");

		// FIXME - Kai, Indexing should not base on VOs!
		// Kai: Do not delete this!!! Set id of department VO for indexing
		department.setId(departmentEntity.getId());

		// Create default Groups for Department
		GroupItem groupItem = new GroupItem();
		groupItem.setName("DEPARTMENT_" + departmentEntity.getId() + "_ADMINS");
		groupItem.setLabel("autogroup_administrator_label");
		groupItem.setGroupType(GroupType.ADMINISTRATOR);
		Long adminsId = this.getOrganisationService().createGroup(departmentEntity.getId(), groupItem);
		Group admins = this.getGroupDao().load(adminsId);

		// Security
		this.getSecurityService().createObjectIdentity(departmentEntity, null);
		this.getSecurityService().setPermissions(admins, departmentEntity, LectureAclEntry.DEPARTMENT_ADMINISTRATION);

		// Add Owner to Members and Group of Administrators
		this.getOrganisationService().addMember(departmentEntity.getId(), userId);
		this.getOrganisationService().addUserToGroup(userId, admins.getId());

		// TODO: Fire departmentCreated event to bookmark Department to User who created it

		return departmentEntity.getId();
	}

	/**
	 * @see org.openuss.lecture.DepartmentService#update(org.openuss.lecture.DepartmentInfo)
	 */
	protected void handleUpdate(org.openuss.lecture.DepartmentInfo departmentInfo) throws java.lang.Exception {
		// TODO: Security

		Validate.notNull(departmentInfo, "DepartmentService.handleUpdate - the Department cannot be null");
		Validate.isTrue(departmentInfo.getId() != null,
				"DepartmentService.handleUpdate - the Department must have a valid ID");

		// Check changes of University
		University university = this.getUniversityDao().load(departmentInfo.getUniversityId());
		Department departmentOld = this.getDepartmentDao().load(departmentInfo.getId());
		if (!departmentOld.getUniversity().equals(university)) {
			throw new DepartmentServiceException("DepartmentService.handleUpdate - The University can not be changed.");
		}

		// Transform VO to Entity
		Department departmentEntity = this.getDepartmentDao().departmentInfoToEntity(departmentInfo);
		
		// Update department
		this.getDepartmentDao().update(departmentEntity);
	}

	/**
	 * @see org.openuss.lecture.DepartmentService#removeDepartment(java.lang.Long)
	 */
	protected void handleRemoveDepartment(java.lang.Long departmentId) throws java.lang.Exception {
		logger.debug("Starting method handleRemoveDepartment for DepartmentID " + departmentId);

		Validate.notNull(departmentId, "DepartmentService.handleRemove - the DepartmentId cannot be null");
		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department,
				"DepartmentService.handleRemoveDepartment - no Department found corresponding to the ID "
						+ departmentId);
		Validate.isTrue(department.getInstitutes().size() == 0,
				"DepartmentService.handleRemoveDepartment - the Department still contains Institutes");

		// Remove Security
		this.getSecurityService().removeAllPermissions(department);
		this.getSecurityService().removeObjectIdentity(department);

		// Clear Membership
		this.getMembershipService().clearMembership(department.getMembership());

		// Remove Department
		department.getUniversity().remove(department);
		this.getDepartmentDao().remove(department);
	}

	/**
	 * @see org.openuss.lecture.DepartmentService#findDepartment(java.lang.Long)
	 */
	protected org.openuss.lecture.DepartmentInfo handleFindDepartment(java.lang.Long departmentId)
			throws java.lang.Exception {

		Validate.notNull(departmentId, "DepartmentService.handleFindDepartment - the DepartmentId cannot be null");

		Department department = (Department) this.getDepartmentDao().load(departmentId);
		return this.getDepartmentDao().toDepartmentInfo(department);
	}

	/**
	 * @see org.openuss.lecture.DepartmentService#findDepartmentsByUniversity(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected java.util.List handleFindDepartmentsByUniversity(java.lang.Long universityId) throws java.lang.Exception {

		Validate.notNull(universityId,
				"DepartmentService.handleFindDepartmentsByUniversity - the universityId cannot be null");

		University university = this.getUniversityDao().load(universityId);

		Validate.notNull(university,
				"DepartmentService.handleFindDepartmentsByUniversity - no University found corresponding to the ID "
						+ universityId);
		/*
		 * if (university == null) { return new ArrayList(); } List departmentInfos = new ArrayList(); for (Department
		 * department : university.getDepartments()) {
		 * departmentInfos.add(this.getDepartmentDao().toDepartmentInfo(department)); }
		 */

		return this.getDepartmentDao().findByUniversity(DepartmentDao.TRANSFORM_DEPARTMENTINFO, university);
	}

	/**
	 * @see org.openuss.lecture.DepartmentService#findDepartmentsByUniversityAndEnabled(java.lang.Long,
	 *      org.openuss.lecture.University, java.lang.Boolean)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindDepartmentsByUniversityAndEnabled(Long universityId, boolean enabled) throws Exception {

		Validate.notNull(universityId,
				"DepartmentService.handleFindDepartmentsByUniversityAndEnabled - the universityId cannot be null");

		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university,
				"DepartmentService.handleFindDepartmentsByUniversityAndEnabled - no University found corresponding to the ID "
						+ universityId);

		return this.getDepartmentDao().findByUniversityAndEnabled(DepartmentDao.TRANSFORM_DEPARTMENTINFO, university,
				enabled);
	}

	/**
	 * @see org.openuss.lecture.DepartmentService#findDepartmentsByType(org.openuss.lecture DepartmentType)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindDepartmentsByType(DepartmentType type) throws Exception {

		Validate.notNull(type, "DepartmentService.handleFindDepartmentsByType - the type cannot be null");

		return this.getDepartmentDao().findByType(DepartmentDao.TRANSFORM_DEPARTMENTINFO, type);
	}

	@SuppressWarnings( { "unchecked" })
	@Override
	protected List handleFindDepartmentsByUniversityAndType(Long universityId, DepartmentType departmentType)
			throws Exception {
		Validate.notNull(universityId,
				"DepartmentService.handleFindDepartmentsByUniversityAndType - the universityId cannot be null");
		Validate.notNull(departmentType,
				"DepartmentService.handleFindDepartmentsByUniversityAndType - the departmentType cannot be null");
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university,
				"DepartmentService.handleFindDepartmentsByUniversityAndType - no University found corresponding to the ID "
						+ universityId);

		return this.getDepartmentDao().findByUniversityAndType(DepartmentDao.TRANSFORM_DEPARTMENTINFO, university,
				departmentType);
	}

	@Override
	protected void handleAcceptApplication(Long applicationId, Long userId) throws Exception {

		Validate.notNull(applicationId, "DepartmentService.handleAcceptApplication - the applicationId cannot be null");

		Application application = this.getApplicationDao().load(applicationId);
		Validate.notNull(application,
				"DepartmentService.handleAcceptApplication - no Application found corresponding to the ID "
						+ applicationId);
		Validate.isTrue(!application.isConfirmed(),
				"DepartmentService.handleAcceptApplication - the Application is already confirmed");

		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "DepartmentService.handleAcceptApplication - no User found corresponding to the ID "
				+ userId);

		Department department = application.getDepartment();
		Institute institute = application.getInstitute();
		department.add(institute);

		application.setConfirmationDate(new Date());
		application.setConfirmingUser(user);
		application.setConfirmed(true);
	}

	@Override
	protected void handleRejectApplication(Long applicationId) throws Exception {

		Validate.notNull(applicationId, "DepartmentService.handleRejectApplication - the applicationId cannot be null");

		Application application = this.getApplicationDao().load(applicationId);
		Validate.notNull(application,
				"DepartmentService.handleRejectApplication - no Application found corresponding to the ID "
						+ applicationId);
		Validate.isTrue(!application.isConfirmed(),
				"DepartmentService.handleRejectApplication - the Application is already confirmed");

		application.remove(application.getDepartment());
		application.remove(application.getInstitute());
		this.getApplicationDao().remove(application);
	}

	@SuppressWarnings( { "unchecked" })
	@Override
	protected void handleSignoffInstitute(Long instituteId) throws Exception {

		Validate.notNull(instituteId, "DepartmentService.handleSignoffInstitute - the instituteId cannot be null");

		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute,
				"DepartmentService.handleSignoffInstitute - no Institute found corresponding to the ID " + instituteId);

		Department department = institute.getDepartment();
		Validate.notNull(department,
				"DepartmentService.handleSignoffInstitute - no Department found corresponding to the Institute");

		// Remove obsolete Application
		Application application = this.getApplicationDao().findByInstituteAndDepartment(institute, department);
		Long userId = application.getApplyingUser().getId();
		if (application != null) {
			application.remove(department);
			application.remove(institute);
			this.getApplicationDao().remove(application);
		}

		// Remove Institute from old Department
		department.remove(institute);

		// Assign Institute to Standard non-official Department
		Department departmentDefault = this.getDepartmentDao().findByUniversityAndDefault(department.getUniversity(), true);
		this.getInstituteService().applyAtDepartment(instituteId, departmentDefault.getId(), userId);
	}

	@Override
	protected ApplicationInfo handleFindApplication(Long applicationId) throws Exception {

		Validate.notNull(applicationId, "DepartmentService.handleFindApplication - the applicationId cannot be null");

		return (ApplicationInfo) this.getApplicationDao().load(ApplicationDao.TRANSFORM_APPLICATIONINFO, applicationId);
	}

	/**
	 * @see org.openuss.lecture.DepartmentService#findApplicationsByDepartment(org.openuss.lecture.Department)
	 */
	@SuppressWarnings( { "unchecked" })
	public List handleFindApplicationsByDepartment(Long departmentId) throws Exception {

		Validate.notNull(departmentId, "DepartmentService.findApplicationsByDepartment - "
				+ "the departmentId cannot be null.");

		// Load Department
		Department departmentEntity = this.getDepartmentDao().load(departmentId);
		Validate.notNull(departmentEntity, "DepartmentService.findApplicationsByDepartment - "
				+ "no department can be found with teh departmentId " + departmentId);

		return this.getApplicationDao().findByDepartment(ApplicationDao.TRANSFORM_APPLICATIONINFO, departmentEntity);
	}

	@Override
	public void handleSetDepartmentStatus(Long departmentId, boolean status) {
		Validate.notNull(departmentId, "DepartmentService.setDepartmentStatus - the departmentId cannot be null.");
		Validate.notNull(status, "DepartmentService.setDepartmentStatus - status cannot be null.");

		// Load department
		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department, "DepartmentService.setDepartmentStatus - "
				+ "department cannot be found with the corresponding departmentId " + departmentId);

		// Set status
		department.setEnabled(status);
		this.update(this.getDepartmentDao().toDepartmentInfo(department));
	}

	@Override
	public boolean handleIsNoneExistingDepartmentShortcut(DepartmentInfo self, String shortcut) throws Exception {
		Department found = getDepartmentDao().findByShortcut(shortcut);
		DepartmentInfo foundInfo = null;
		if (found != null) {
			foundInfo = this.getDepartmentDao().toDepartmentInfo(found);
		}
		return isEqualOrNull(self, foundInfo);
	}

	@Override
	@SuppressWarnings( { "unchecked" })
	protected List handleFindApplicationsByDepartmentAndConfirmed(Long departmentId, boolean confirmed)
			throws Exception {
		Validate.notNull(departmentId, "DepartmentService.findOpenApplicationsByDepartment - "
				+ "the departmentId cannot be null.");

		// Load Department
		Department departmentEntity = this.getDepartmentDao().load(departmentId);
		Validate.notNull(departmentEntity, "DepartmentService.findOpenApplicationsByDepartment - "
				+ "no department can be found with the departmentId " + departmentId);
		return this.getApplicationDao().findByDepartmentAndConfirmed(ApplicationDao.TRANSFORM_APPLICATIONINFO,
				departmentEntity, confirmed);
	}

	@Override
	@SuppressWarnings( { "unchecked" })
	protected List handleFindDepartmentsByUniversityAndTypeAndEnabled(Long universityId, DepartmentType type,
			boolean enabled) throws Exception {

		Validate.notNull(type,
				"DepartmentService.handleFindDepartmentsByUniversityAndTypeAndEnabled - the type cannot be null");
		Validate
				.notNull(universityId,
						"DepartmentService.handleFindDepartmentsByUniversityAndTypeAndEnabled - the universityId cannot be null");
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university,
				"DepartmentService.handleFindDepartmentsByUniversityAndTypeAndEnabled - no University found corresponding to the ID "
						+ universityId);

		return this.getDepartmentDao().findByUniversityAndTypeAndEnabled(DepartmentDao.TRANSFORM_DEPARTMENTINFO,
				university, type, enabled);
	}

	@Override
	protected void handleRemoveCompleteDepartmentTree(Long departmentId) throws Exception {
		logger.debug("Starting method handleRemoveCompleteDepartmentTree for DepartmentID " + departmentId);

		Validate.notNull(departmentId, "DepartmentService.handleRemove - the DepartmentId cannot be null");
		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department,
				"DepartmentService.handleRemoveDepartment - no Department found corresponding to the ID "
						+ departmentId);

		if (!department.getApplications().isEmpty()) {
			// Remove applications
			List<Application> applications = new ArrayList<Application>();
			for (Application application : department.getApplications()) {
				application.remove(department);
				department.setApplications(new ArrayList<Application>());
				application.remove(application.getInstitute());
				this.getApplicationDao().remove(application);
			}
		}
		
		if (!department.getInstitutes().isEmpty()) {
			// Remove Institutes
			List<Institute> institutes = new ArrayList<Institute>();
			for (Institute institute : department.getInstitutes()) {
				institutes.add(institute);
			}
			for (Institute institute : institutes) {
				this.getInstituteService().removeCompleteInstituteTree(institute.getId());
			}
		}

		// Remove Security
		this.getSecurityService().removeAllPermissions(department);
		this.getSecurityService().removeObjectIdentity(department);

		// Clear Membership
		this.getMembershipService().clearMembership(department.getMembership());

		// Remove Department
		department.getUniversity().remove(department);
		this.getDepartmentDao().remove(department);

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

}