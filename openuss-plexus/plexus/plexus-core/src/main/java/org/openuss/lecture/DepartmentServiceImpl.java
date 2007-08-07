// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.openuss.security.GroupItem;
import org.openuss.security.GroupType;

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
		// TODO: Security

		Validate.notNull(department, "DepartmentService.handleCreate - the Department cannot be null");
		Validate.notNull(userId, "DepartmentService.handleCreate - the User must have a valid ID");

		Validate.isTrue(department.getId() == null,
				"DepartmentService.handleCreate - the Department shouldn't have an ID yet");

		// Create Department
		Department departmentEntity = this.getDepartmentDao().departmentInfoToEntity(department);
		departmentEntity.getUniversity().getDepartments().add(departmentEntity);
		this.getDepartmentDao().create(departmentEntity);

		Validate.notNull(departmentEntity.getId(), "DepartmentService.handleCreate - Couldn't create Department");

		// Create Groups for Department
		GroupItem groupItem = new GroupItem();
		groupItem.setName("DEPARTMENT_" + department.getId() + "_ADMINS");
		groupItem.setLabel("autogroup_administrator_label");
		groupItem.setGroupType(GroupType.ADMINISTRATOR);
		Long groupId = this.getOrganisationService().createGroup(departmentEntity.getId(), groupItem);

		// Add Owner to Members and Group of Administrators
		this.getOrganisationService().addMember(departmentEntity.getId(), userId);
		this.getOrganisationService().addUserToGroup(userId, groupId);

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
		Validate.notNull(university,
				"DepartmentService.handleFindDepartmentsByUniversity - no University found corresponding to the ID "
						+ universityId);
		
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

}