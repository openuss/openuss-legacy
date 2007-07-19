// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.openuss.security.User;

/**
 * @see org.openuss.lecture.DepartmentService
 * @author Florian Dondorf
 */
public class DepartmentServiceImpl
    extends org.openuss.lecture.DepartmentServiceBase
{

    /**
     * @see org.openuss.lecture.DepartmentService#create(org.openuss.lecture.DepartmentInfo, java.lang.Long)
     */
    protected java.lang.Long handleCreate(org.openuss.lecture.DepartmentInfo department, java.lang.Long ownerId)
        throws java.lang.Exception
    {
       	if (department == null) {
       		throw new IllegalArgumentException("DepartmentService.handleCreate - the department cannot be null");
       	}
       	
       	if (ownerId == null) {
       		throw new IllegalArgumentException("DepartmentService.handleCreate - the owner must have a valid ID");
       	}
       	
       	User owner = this.getUserDao().load(ownerId);
       	if (owner == null) {
       		throw new IllegalArgumentException("DepartmentService.handleCreate - no user found to the id "+ownerId);
       	}
       	
       	if (department.getId() != null) {
       		throw new IllegalArgumentException("DepartmentService.handleCreate - department must have a valid Id");
       	}
       	
       	Department departmentEntity = Department.Factory.newInstance();
       	departmentEntity.setName(department.getName());
       	departmentEntity.setShortcut(department.getShortcut());
       	departmentEntity.setDescription(department.getDescription());
       	departmentEntity.setDepartmentType(DepartmentType.fromInteger(department.getDepartmentType()));
       	departmentEntity.setUniversity(this.getUniversityDao().load(department.getUniversityId()));
       	
       	departmentEntity.setOwner(owner);
       	
       	this.getDepartmentDao().create(departmentEntity).getId();
       	
       	return departmentEntity.getId();
    }

    /**
     * @see org.openuss.lecture.DepartmentService#update(org.openuss.lecture.DepartmentInfo)
     */
    protected void handleUpdate(org.openuss.lecture.DepartmentInfo department)
        throws java.lang.Exception
    {
        // @todo implement protected void handleUpdate(org.openuss.lecture.DepartmentInfo department)
        throw new java.lang.UnsupportedOperationException("org.openuss.lecture.DepartmentService.handleUpdate(org.openuss.lecture.DepartmentInfo department) Not implemented!");
    }

    /**
     * @see org.openuss.lecture.DepartmentService#removeDepartment(java.lang.Long)
     */
    protected void handleRemoveDepartment(java.lang.Long departmentId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleRemoveDepartment(java.lang.Long departmentId)
        throw new java.lang.UnsupportedOperationException("org.openuss.lecture.DepartmentService.handleRemoveDepartment(java.lang.Long departmentId) Not implemented!");
    }

    /**
     * @see org.openuss.lecture.DepartmentService#findDepartment(java.lang.Long)
     */
    protected org.openuss.lecture.DepartmentInfo handleFindDepartment(java.lang.Long departmentId)
        throws java.lang.Exception
    {
        // @todo implement protected org.openuss.lecture.DepartmentInfo handleFindDepartment(java.lang.Long departmentId)
        return null;
    }

    /**
     * @see org.openuss.lecture.DepartmentService#findDepartmentsByUniversity(java.lang.Long)
     */
    protected java.util.List handleFindDepartmentsByUniversity(java.lang.Long universityId)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleFindDepartmentsByUniversity(java.lang.Long universityId)
        return null;
    }

    /**
     * @see org.openuss.lecture.DepartmentService#acceptApplication(java.lang.Long)
     */
    protected void handleAcceptApplication(java.lang.Long applicationId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleAcceptApplication(java.lang.Long applicationId)
        throw new java.lang.UnsupportedOperationException("org.openuss.lecture.DepartmentService.handleAcceptApplication(java.lang.Long applicationId) Not implemented!");
    }

    /**
     * @see org.openuss.lecture.DepartmentService#rejectApplication(java.lang.Long)
     */
    protected void handleRejectApplication(java.lang.Long applicationId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleRejectApplication(java.lang.Long applicationId)
        throw new java.lang.UnsupportedOperationException("org.openuss.lecture.DepartmentService.handleRejectApplication(java.lang.Long applicationId) Not implemented!");
    }

    /**
     * @see org.openuss.lecture.DepartmentService#findByUserAndUniversity(java.lang.Long, java.lang.Long)
     */
    protected java.util.List handleFindByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleFindByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)
        return null;
    }

}