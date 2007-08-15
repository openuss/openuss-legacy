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
import org.openuss.security.Group;
import org.openuss.security.GroupItem;
import org.openuss.security.GroupType;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @see org.openuss.lecture.DepartmentService
 * @author Florian Dondorf, Ron Haus
 */
public class DepartmentServiceImpl extends org.openuss.lecture.DepartmentServiceBase {

	/**
	 * @see org.openuss.lecture.DepartmentService#create(org.openuss.lecture.DepartmentInfo, java.lang.Long)
	 */
	protected java.lang.Long handleCreate(org.openuss.lecture.DepartmentInfo department, java.lang.Long userId)
			throws java.lang.Exception {

		Validate.notNull(department, "DepartmentService.handleCreate - the Department cannot be null");
		Validate.notNull(userId, "DepartmentService.handleCreate - the User must have a valid ID");

		Validate.isTrue(department.getId() == null,
				"DepartmentService.handleCreate - the Department shouldn't have an ID yet");

		// Create Department
		Department departmentEntity = this.getDepartmentDao().departmentInfoToEntity(department);
		departmentEntity.getUniversity().getDepartments().add(departmentEntity);
		this.getDepartmentDao().create(departmentEntity);

		Validate.notNull(departmentEntity.getId(), "DepartmentService.handleCreate - Couldn't create Department");
		
		// Do not delete this!!! Set id of department VO for indexing
		department.setId(departmentEntity.getId());

		// Create Groups for Department
		GroupItem groupItem = new GroupItem();
		groupItem.setName("DEPARTMENT_" + departmentEntity.getId() + "_ADMINS");
		groupItem.setLabel("autogroup_administrator_label");
		groupItem.setGroupType(GroupType.ADMINISTRATOR);
		Group admins = this.getOrganisationService().createGroup(departmentEntity.getId(), groupItem);

		// TODO: Security
		// Create Object Identity
		this.getSecurityService().createObjectIdentity(departmentEntity, null);
		
		// Add permissions -> ACL Entry for each group
		this.getSecurityService().setPermissions(admins, departmentEntity, LectureAclEntry.DEPARTMENT_ADMINISTRATION);
		
		// Add Owner to Members and Group of Administrators
		this.getOrganisationService().addMember(departmentEntity.getId(), userId);
		this.getOrganisationService().addUserToGroup(userId, admins.getId());

		//TODO: Fire departmentCreated event to bookmark department to 
		
		return departmentEntity.getId();

	}

	/**
	 * @see org.openuss.lecture.DepartmentService#update(org.openuss.lecture.DepartmentInfo)
	 */
	protected void handleUpdate(org.openuss.lecture.DepartmentInfo department) throws java.lang.Exception {
		// TODO: Security

		Validate.notNull(department, "DepartmentService.handleUpdate - the Department cannot be null");
		Validate.isTrue(department.getId() != null,
				"DepartmentService.handleUpdate - the Department must have a valid ID");

		// Transform departmentInfo to departmentEntity
		Department departmentEntity = this.getDepartmentDao().departmentInfoToEntity(department);

		// Update department
		this.getDepartmentDao().update(departmentEntity);
	}

	/**
	 * @see org.openuss.lecture.DepartmentService#removeDepartment(java.lang.Long)
	 */
	protected void handleRemoveDepartment(java.lang.Long departmentId) throws java.lang.Exception {
		// TODO: Security

		Validate.notNull(departmentId, "DepartmentService.handleRemove - the DepartmentId cannot be null");

		// Remove department
		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department,
				"DepartmentService.handleRemoveDepartment - no Department found corresponding to the ID "
						+ departmentId);

		department.getMembership().getGroups().clear(); // due to problems of cascade
		
		//TODO: fireRemovedDepartment event to delete all bookmarks and open applications
		//		existing institutes have no longer an association to a department and are set to an open department
		
		
		this.getDepartmentDao().remove(department);

	}

	/**
	 * @see org.openuss.lecture.DepartmentService#findDepartment(java.lang.Long)
	 */
	protected org.openuss.lecture.DepartmentInfo handleFindDepartment(java.lang.Long departmentId)
			throws java.lang.Exception {

		Validate.notNull(departmentId, "DepartmentService.handleFindDepartment - the DepartmentId cannot be null");

		Department department = (Department) this.getDepartmentDao().load(departmentId);
		Validate.notNull(department,
				"DepartmentService.handleFindDepartment - no Department found corresponding to the ID " + departmentId);

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
		/*
		Validate.notNull(university,
				"DepartmentService.handleFindDepartmentsByUniversity - no University found corresponding to the ID "
						+ universityId);
		*/
		if (university == null) {
			return new ArrayList();
		}
		List departmentInfos = new ArrayList();
		for (Department department : university.getDepartments()) {
			departmentInfos.add(this.getDepartmentDao().toDepartmentInfo(department));
		}

		return departmentInfos;
	}

	/**
	 * @see org.openuss.lecture.DepartmentService#findDepartmentsByUniversityAndEnabled(java.lang.Long,
	 *      org.openuss.lecture.University, java.lang.Boolean)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindDepartmentsByUniversityAndEnabled(Long universityId, Boolean enabled) throws Exception {

		Validate.notNull(universityId,
				"DepartmentService.handleFindDepartmentsByUniversityAndEnabled - the universityId cannot be null");

		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university,
				"DepartmentService.handleFindDepartmentsByUniversityAndEnabled - no University found corresponding to the ID "
						+ universityId);

		return this.getDepartmentDao().findByUniversityAndEnabled(DepartmentDao.TRANSFORM_DEPARTMENTINFO, university,
				enabled);
	}

	@Override
	protected void handleAcceptApplication(Long applicationId, Long userId) throws Exception {

		Validate.notNull(applicationId, "DepartmentService.handleAcceptApplication - the applicationId cannot be null");

		Application application = this.getApplicationDao().load(applicationId);
		Validate.notNull(application,
				"DepartmentService.handleAcceptApplication - no Application found corresponding to the ID "+applicationId);
		Validate.isTrue(!application.getConfirmed(),
				"DepartmentService.handleAcceptApplication - the Application is already confirmed");
		
		User user = this.getUserDao().load(userId);
		Validate.notNull(user,
		"DepartmentService.handleAcceptApplication - no User found corresponding to the ID "+userId);
		
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
				"DepartmentService.handleRejectApplication - no Application found corresponding to the ID "+applicationId);
		Validate.isTrue(!application.getConfirmed(),
				"DepartmentService.handleRejectApplication - the Application is already confirmed");
		
		this.getApplicationDao().remove(application);
	}

	@Override
	protected void handleSignoffInstitute(Long instituteId) throws Exception {
		
		Validate.notNull(instituteId, "DepartmentService.handleSignoffInstitute - the instituteId cannot be null");

		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute,
				"DepartmentService.handleSignoffInstitute - no Institute found corresponding to the ID "+instituteId);
		
		Department department = institute.getDepartment();
		Validate.notNull(department,
				"DepartmentService.handleSignoffInstitute - no Department found corresponding to the Institute");
		
		this.getApplicationDao().remove(institute.getApplication());
		department.remove(institute);

	}

	@Override
	protected ApplicationInfo handleFindApplication(Long applicationId) throws Exception {

		Validate.notNull(applicationId, "DepartmentService.handleFindApplication - the applicationId cannot be null");

		return (ApplicationInfo) this.getApplicationDao().load(ApplicationDao.TRANSFORM_APPLICATIONINFO, applicationId);
	}
	
	/*------------------- private methods -------------------- */
	
	// TODO: Add Set of listeners
	
	// TODO: Method unregisterListener
	
	// TODO: Method fireRemovingDepartment (Department department)
	
	// TODO: Method fireCreatedDepartment (Department department)

}