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
import org.openuss.lecture.events.InstituteUpdatedEvent;
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
 * @author Ingo Dueppe
 */
public class DepartmentServiceImpl extends DepartmentServiceBase {

	private static final Logger logger = Logger.getLogger(DepartmentServiceImpl.class);

	/**
	 * @see org.openuss.lecture.DepartmentService#create(org.openuss.lecture.DepartmentInfo,
	 *      java.lang.Long)
	 */
	protected Long handleCreate(DepartmentInfo department, Long userId) throws Exception {
		logger.debug("Starting method handleCreate");

		Validate.notNull(department, "The Department cannot be null");
		Validate.notNull(userId, "The User must have a valid ID");

		Validate.isTrue(department.getId() == null, "The Department shouldn't have an ID yet");
		Validate.isTrue(!department.isDefaultDepartment(), "You cannot create a default Department!");

		// Transform ValueObject into Entity
		Department departmentEntity = this.getDepartmentDao().departmentInfoToEntity(department);

		// Create a default Membership for the University
		Membership membership = Membership.Factory.newInstance();
		departmentEntity.setMembership(membership);

		// Create the Department and add it to the University
		departmentEntity.getUniversity().add(departmentEntity);
		this.getDepartmentDao().create(departmentEntity);
		Validate.notNull(departmentEntity.getId(), "DepartmentService.handleCreate - Couldn't create Department");

		// FIXME AOP2Events - Kai, Indexing should not base on VOs!
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

		return departmentEntity.getId();
	}

	/**
	 * @see org.openuss.lecture.DepartmentService#update(org.openuss.lecture.DepartmentInfo)
	 */
	protected void handleUpdate(DepartmentInfo departmentInfo) throws Exception {
		Validate.notNull(departmentInfo, "The Department cannot be null");
		Validate.isTrue(departmentInfo.getId() != null, "The Department must have a valid ID");

		// Check changes of University
		University university = this.getUniversityDao().load(departmentInfo.getUniversityId());
		Department departmentOld = this.getDepartmentDao().load(departmentInfo.getId());
		if (!departmentOld.getUniversity().equals(university)) {
			throw new DepartmentServiceException("The University can not be changed.");
		}

		// Transform VO to Entity
		Department departmentEntity = this.getDepartmentDao().departmentInfoToEntity(departmentInfo);

		// Update department
		this.getDepartmentDao().update(departmentEntity);
	}

	/**
	 * @see org.openuss.lecture.DepartmentService#removeDepartment(java.lang.Long)
	 */
	protected void handleRemoveDepartment(Long departmentId) throws Exception {
		logger.debug("Starting method handleRemoveDepartment for DepartmentID " + departmentId);

		Validate.notNull(departmentId, "The DepartmentId cannot be null");
		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department, "No Department found corresponding to the ID " + departmentId);
		Validate.isTrue(department.getInstitutes().size() == 0, "The Department still contains Institutes");

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
	protected DepartmentInfo handleFindDepartment(Long departmentId) throws Exception {
		Validate.notNull(departmentId, "The DepartmentId cannot be null");
		return (DepartmentInfo) getDepartmentDao().load(DepartmentDao.TRANSFORM_DEPARTMENTINFO, departmentId);
	}

	/**
	 * @see org.openuss.lecture.DepartmentService#findDepartmentsByUniversity(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindDepartmentsByUniversity(Long universityId) throws Exception {
		Validate.notNull(universityId, "The universityId cannot be null");
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university, "No University found corresponding to the ID " + universityId);
		/*
		 * if (university == null) { return new ArrayList(); } List
		 * departmentInfos = new ArrayList(); for (Department department :
		 * university.getDepartments()) {
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
		Validate.notNull(universityId, "The universityId cannot be null");
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university, "No University found corresponding to the ID " + universityId);
		return getDepartmentDao().findByUniversityAndEnabled(DepartmentDao.TRANSFORM_DEPARTMENTINFO, university,
				enabled);
	}

	/**
	 * @see org.openuss.lecture.DepartmentService#findDepartmentsByType(org.openuss.lecture
	 *      DepartmentType)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindDepartmentsByType(DepartmentType type) throws Exception {
		Validate.notNull(type, "The type cannot be null");
		return this.getDepartmentDao().findByType(DepartmentDao.TRANSFORM_DEPARTMENTINFO, type);
	}

	@SuppressWarnings( { "unchecked" })
	@Override
	protected List handleFindDepartmentsByUniversityAndType(Long universityId, DepartmentType departmentType)
			throws Exception {
		Validate.notNull(universityId, "DepartmentThe universityId cannot be null");
		Validate.notNull(departmentType, "The departmentType cannot be null");
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university, "No University found corresponding to the ID " + universityId);
		return this.getDepartmentDao().findByUniversityAndType(DepartmentDao.TRANSFORM_DEPARTMENTINFO, university,
				departmentType);
	}

	@Override
	protected void handleAcceptApplication(Long applicationId, Long userId) throws Exception {
		Validate.notNull(applicationId, "The applicationId cannot be null");
		Application application = this.getApplicationDao().load(applicationId);
		Validate.notNull(application, "No Application found corresponding to the ID " + applicationId);
		Validate.isTrue(!application.isConfirmed(), "The Application is already confirmed");

		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "No User found corresponding to the ID " + userId);

		Department department = application.getDepartment();
		Institute institute = application.getInstitute();
		
		verifyCoursePeriodsAssoziations(department, institute);

		// Add Institute to Department
		department.add(institute);
		getInstituteDao().update(institute);
		
		// Delete all old Applications of the Institute (should actually only be max 2)
		List<Application> applicationsOld = new ArrayList<Application>();
		for (Application applicationOld : institute.getApplications()) {
			if (!applicationId.equals(applicationOld.getId())) {
				applicationsOld.add(applicationOld);
			}
		}
		for (Application applicationOld : applicationsOld) {
			try {
				applicationOld.remove(applicationOld.getInstitute());
				applicationOld.remove(applicationOld.getDepartment());
				this.getApplicationDao().remove(applicationOld);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		// Mark Application as confirmed
		application.setConfirmationDate(new Date());
		application.setConfirmingUser(user);
		application.setConfirmed(true);
		
		getEventPublisher().publishEvent(new InstituteUpdatedEvent(institute));
	}

	/**
	 * Checks if the institute changed the university. If so then the preexisting 
	 * courses will be moved in the default period of the university.
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
	
	private boolean hasUniversityChanged(Department department, Institute institute) {
		return institute.getDepartment() != null
				&& !institute.getDepartment().getUniversity().equals(department.getUniversity());
	}

	@Override
	protected void handleRejectApplication(Long applicationId) throws Exception {

		Validate.notNull(applicationId, "The applicationId cannot be null");

		Application application = this.getApplicationDao().load(applicationId);
		Validate.notNull(application, "No Application found corresponding to the ID " + applicationId);
		Validate.isTrue(!application.isConfirmed(), "The Application is already confirmed");

		application.remove(application.getDepartment());
		application.remove(application.getInstitute());
		this.getApplicationDao().remove(application);
	}

	@SuppressWarnings( { "unchecked" })
	@Override
	protected void handleSignoffInstitute(Long instituteId) throws Exception {
		Validate.notNull(instituteId, "The instituteId cannot be null");
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute, "No Institute found corresponding to the ID " + instituteId);

		Department department = institute.getDepartment();
		Validate.notNull(department, "No Department found corresponding to the Institute");

		// Remove obsolete Application
		Application application = this.getApplicationDao().findByInstituteAndDepartment(institute, department);
		if (application == null) {
			return;
		}
		application.remove(department);
		application.remove(institute);
		this.getApplicationDao().remove(application);
		Long userId = application.getApplyingUser().getId();

		// Remove Institute from old Department
		department.remove(institute);

		// Assign Institute to Standard non-official Department
		Department departmentDefault = this.getDepartmentDao().findByUniversityAndDefault(department.getUniversity(),
				true);
		this.getInstituteService().applyAtDepartment(instituteId, departmentDefault.getId(), userId);
	}

	@Override
	protected ApplicationInfo handleFindApplication(Long applicationId) throws Exception {

		Validate.notNull(applicationId, "The applicationId cannot be null");

		return (ApplicationInfo) this.getApplicationDao().load(ApplicationDao.TRANSFORM_APPLICATIONINFO, applicationId);
	}

	/**
	 * @see org.openuss.lecture.DepartmentService#findApplicationsByDepartment(org.openuss.lecture.Department)
	 */
	@SuppressWarnings( { "unchecked" })
	public List handleFindApplicationsByDepartment(Long departmentId) throws Exception {

		Validate.notNull(departmentId, "Tthe departmentId cannot be null.");

		// Load Department
		Department departmentEntity = this.getDepartmentDao().load(departmentId);
		Validate.notNull(departmentEntity, "No department can be found with teh departmentId " + departmentId);

		return this.getApplicationDao().findByDepartment(ApplicationDao.TRANSFORM_APPLICATIONINFO, departmentEntity);
	}

	@Override
	public void handleSetDepartmentStatus(Long departmentId, boolean status) {
		Validate.notNull(departmentId, "The departmentId cannot be null.");
		Validate.notNull(status, "Status cannot be null.");

		// Load department
		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department, "Department cannot be found with the corresponding departmentId " + departmentId);

		// Only allow department to be enabled when the super-ordinate
		// university is enabled
		if (status) {
			Validate.isTrue(department.getUniversity().isEnabled(),	"The department cannot be enabled because the associated university is disabled.");
		}

		// Set status
		department.setEnabled(status);
		this.update(this.getDepartmentDao().toDepartmentInfo(department));

		// Set subordinate organisations to "disabled" if the department was
		// just disabled
		if (!status) {
			for (Institute institute : department.getInstitutes()) {
				institute.setEnabled(false);
				this.getInstituteDao().update(institute);
				for (CourseType courseType : institute.getCourseTypes()) {
					for (Course course : courseType.getCourses()) {
						course.setEnabled(false);
						this.getCourseDao().update(course);
					}
				}
			}
		}
	}

	@Override
	@SuppressWarnings( { "unchecked" })
	protected List handleFindApplicationsByDepartmentAndConfirmed(Long departmentId, boolean confirmed)	throws Exception {
		Validate.notNull(departmentId, "The departmentId cannot be null.");

		// Load Department
		Department departmentEntity = this.getDepartmentDao().load(departmentId);
		Validate.notNull(departmentEntity, "No department can be found with the departmentId " + departmentId);
		return this.getApplicationDao().findByDepartmentAndConfirmed(ApplicationDao.TRANSFORM_APPLICATIONINFO,	departmentEntity, confirmed);
	}

	@Override
	@SuppressWarnings( { "unchecked" })
	protected List handleFindDepartmentsByUniversityAndTypeAndEnabled(Long universityId, DepartmentType type, boolean enabled) throws Exception {
		Validate.notNull(type, "The type cannot be null");
		Validate.notNull(universityId, "The universityId cannot be null");
		University university = getUniversityDao().load(universityId);
		Validate.notNull(university, "No University found corresponding to the ID "	+ universityId);
		return getDepartmentDao().findByUniversityAndTypeAndEnabled(DepartmentDao.TRANSFORM_DEPARTMENTINFO,	university, type, enabled);
	}

	@Override
	protected void handleRemoveCompleteDepartmentTree(Long departmentId) throws Exception {
		logger.debug("Starting method handleRemoveCompleteDepartmentTree for DepartmentID " + departmentId);

		Validate.notNull(departmentId, "The DepartmentId cannot be null");
		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department, "No Department found corresponding to the ID "	+ departmentId);

		if (!department.getApplications().isEmpty()) {
			// Remove applications
			List<Application> applications = new ArrayList<Application>();
			for (Application application : department.getApplications()) {
				applications.add(application);
			}
			for (Application application : applications) {
				application.remove(department);
				application.remove(application.getInstitute());
				this.getApplicationDao().remove(application);
			}
			// department.setApplications(new ArrayList<Application>());
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

}