// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.apache.commons.lang.Validate;
import org.openuss.security.Membership;

/**
 * @see org.openuss.lecture.Department
 * @author Ron Haus
 * @author Florian Dondorf
 */
public class DepartmentDaoImpl extends DepartmentDaoBase {
	/**
	 * @see org.openuss.lecture.DepartmentDao#toDepartmentInfo(org.openuss.lecture.Department,
	 *      org.openuss.lecture.DepartmentInfo)
	 */
	public void toDepartmentInfo(Department sourceEntity, DepartmentInfo targetVO) {
		super.toDepartmentInfo(sourceEntity, targetVO);
		if (sourceEntity.getUniversity() != null) {
			targetVO.setUniversityId(sourceEntity.getUniversity().getId());
		}		
	}

	/**
	 * @see org.openuss.lecture.DepartmentDao#toDepartmentInfo(org.openuss.lecture.Department)
	 */
	public DepartmentInfo toDepartmentInfo(final Department entity) {
		return super.toDepartmentInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private Department loadDepartmentFromDepartmentInfo(DepartmentInfo departmentInfo) {
		Department department = Department.Factory.newInstance();
		department.setMembership(Membership.Factory.newInstance());
		if (departmentInfo.getId() != null) {
			department = this.load(departmentInfo.getId());
		}
		return department;
	}

	/**
	 * @see org.openuss.lecture.DepartmentDao#departmentInfoToEntity(org.openuss.lecture.DepartmentInfo)
	 */
	public Department departmentInfoToEntity(DepartmentInfo departmentInfo) {
		Validate.notNull(departmentInfo.getUniversityId(), "The UniversityId cannot be null");
		Department entity = this.loadDepartmentFromDepartmentInfo(departmentInfo);
		this.departmentInfoToEntity(departmentInfo, entity, true);
		entity.setUniversity(this.getUniversityDao().load(departmentInfo.getUniversityId()));

		return entity;
	}

	/**
	 * @see org.openuss.lecture.DepartmentDao#departmentInfoToEntity(org.openuss.lecture.DepartmentInfo,
	 *      org.openuss.lecture.Department)
	 */
	public void departmentInfoToEntity(DepartmentInfo sourceVO,	Department targetEntity, boolean copyIfNull) {
		super.departmentInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}