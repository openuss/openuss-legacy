// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.openuss.security.GroupItem;
import org.openuss.security.GroupType;
import org.openuss.security.User;

/**
 * @see org.openuss.lecture.DepartmentService
 * @author Florian Dondorf, Ron Haus
 */
public class DepartmentServiceImpl extends org.openuss.lecture.DepartmentServiceBase {

	/**
	 * @see org.openuss.lecture.DepartmentService#create(org.openuss.lecture.DepartmentInfo, java.lang.Long)
	 */
	protected java.lang.Long handleCreate(org.openuss.lecture.DepartmentInfo department, java.lang.Long ownerId)
			throws java.lang.Exception {
		// TODO: Security

		Validate.notNull(department, "DepartmentService.handleCreate - the Department cannot be null");
		Validate.notNull(ownerId, "DepartmentService.handleCreate - the Owner must have a valid ID");

		Validate.isTrue(department.getId() == null,
				"DepartmentService.handleCreate - the Department shouldn't have an ID yet");

		// Create Department
		Department departmentEntity = this.getDepartmentDao().departmentInfoToEntity(department);
		this.getDepartmentDao().create(departmentEntity);
		departmentEntity.getUniversity().getDepartments().add(departmentEntity);
		
		Validate.notNull(departmentEntity.getId(), "DepartmentService.handleCreate - Couldn't create Department");

		// Create Groups for Department
		GroupItem groupItem = new GroupItem();
		groupItem.setName("DEPARTMENT_" + department.getId() + "_ADMINS");
		groupItem.setLabel("autogroup_administrator_label");
		groupItem.setGroupType(GroupType.ADMINISTRATOR);
		Long groupId = this.getOrganisationService().createGroup(departmentEntity.getId(), groupItem);

		// Add Owner to Members and Group of Administrators
		this.getOrganisationService().addMember(departmentEntity.getId(), ownerId);
		this.getOrganisationService().addUserToGroup(ownerId, groupId);

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
		department.getMembership().getGroups().clear(); //due to problems of cascade
		this.getDepartmentDao().remove(department);

	}

    /**
     * @see org.openuss.lecture.DepartmentService#findDepartment(java.lang.Long)
     */
    protected org.openuss.lecture.DepartmentInfo handleFindDepartment(java.lang.Long departmentId)
        throws java.lang.Exception
    {
    	// TODO: Security
    	
    	Validate.notNull(departmentId, "DepartmentService.handleFindDepartment - the DepartmentId cannot be null");
    	
    	Department department = (Department) this.getDepartmentDao().load(departmentId);
    	return this.getDepartmentDao().toDepartmentInfo(department); 
    }

	/**
	 * @see org.openuss.lecture.DepartmentService#findDepartmentsByUniversity(java.lang.Long)
	 */
	protected java.util.List handleFindDepartmentsByUniversity(java.lang.Long universityId) throws java.lang.Exception {
		
		// TODO: Security
    	
    	Validate.notNull(universityId, "DepartmentService.handleFindDepartmentsByUniversity - the universityId cannot be null");
    	
    	University university = this.getUniversityDao().load(universityId);
    	
		return university.getDepartments();
	}

}