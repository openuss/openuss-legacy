// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

/**
 * @see org.openuss.lecture.Department
 * @author Ron Haus, Florian Dondorf
 */
public class DepartmentDaoImpl extends org.openuss.lecture.DepartmentDaoBase {
	/**
	 * @see org.openuss.lecture.DepartmentDao#toDepartmentInfo(org.openuss.lecture.Department,
	 *      org.openuss.lecture.DepartmentInfo)
	 */
	public void toDepartmentInfo(org.openuss.lecture.Department sourceEntity,
			org.openuss.lecture.DepartmentInfo targetVO) {
		super.toDepartmentInfo(sourceEntity, targetVO);
		if (sourceEntity.getUniversity() != null) {
			targetVO.setUniversityId(sourceEntity.getUniversity().getId());
		}
	}

	/**
	 * @see org.openuss.lecture.DepartmentDao#toDepartmentInfo(org.openuss.lecture.Department)
	 */
	public org.openuss.lecture.DepartmentInfo toDepartmentInfo(final org.openuss.lecture.Department entity) {
		// @todo verify behavior of toDepartmentInfo
		return super.toDepartmentInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object from the object store. If no such
	 * entity object exists in the object store, a new, blank entity is created
	 */
	private org.openuss.lecture.Department loadDepartmentFromDepartmentInfo(
			org.openuss.lecture.DepartmentInfo departmentInfo) {
		org.openuss.lecture.Department department = org.openuss.lecture.Department.Factory.newInstance();
		department.setMembership(org.openuss.security.Membership.Factory.newInstance());
		if (departmentInfo.getId() != null) {
			department = this.load(departmentInfo.getId());
		}
		return department;
	}

	/**
	 * @see org.openuss.lecture.DepartmentDao#departmentInfoToEntity(org.openuss.lecture.DepartmentInfo)
	 */
	public org.openuss.lecture.Department departmentInfoToEntity(org.openuss.lecture.DepartmentInfo departmentInfo) {
		// @todo verify behavior of departmentInfoToEntity
		if (departmentInfo.getUniversityId() == null) {
			throw new IllegalArgumentException(
					"DepartmentDaoImpl.departmentInfoToEntity - the UniversityId cannot be null");
		}
		org.openuss.lecture.Department entity = this.loadDepartmentFromDepartmentInfo(departmentInfo);
		this.departmentInfoToEntity(departmentInfo, entity, true);
		entity.setUniversity(this.getUniversityDao().load(departmentInfo.getUniversityId()));

		return entity;
	}

	/**
	 * @see org.openuss.lecture.DepartmentDao#departmentInfoToEntity(org.openuss.lecture.DepartmentInfo,
	 *      org.openuss.lecture.Department)
	 */
	public void departmentInfoToEntity(org.openuss.lecture.DepartmentInfo sourceVO,
			org.openuss.lecture.Department targetEntity, boolean copyIfNull) {
		// @todo verify behavior of departmentInfoToEntity
		super.departmentInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}