// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

/**
 * @see org.openuss.lecture.EnrollmentMember
 */
public class EnrollmentMemberDaoImpl extends org.openuss.lecture.EnrollmentMemberDaoBase {

	/**
	 * @see org.openuss.lecture.EnrollmentMemberDao#toEnrollmentMemberInfo(org.openuss.lecture.EnrollmentMember,
	 *      org.openuss.lecture.EnrollmentMemberInfo)
	 */
	public void toEnrollmentMemberInfo(EnrollmentMember sourceEntity, EnrollmentMemberInfo targetVO) {
		targetVO.setId(sourceEntity.getId());
		targetVO.setUserId(sourceEntity.getUser().getId());
		targetVO.setEnrollmentId(sourceEntity.getEnrollment().getId());
		targetVO.setUsername(sourceEntity.getUser().getUsername());
		targetVO.setFirstName(sourceEntity.getUser().getFirstName());
		targetVO.setLastName(sourceEntity.getUser().getLastName());
		targetVO.setEmail(sourceEntity.getUser().getEmail());
		targetVO.setMemberType(sourceEntity.getMemberType());
	}

	/**
	 * @see org.openuss.lecture.EnrollmentMemberDao#toEnrollmentMemberInfo(org.openuss.lecture.EnrollmentMember)
	 */
	public org.openuss.lecture.EnrollmentMemberInfo toEnrollmentMemberInfo(
			final org.openuss.lecture.EnrollmentMember entity) {

		return super.toEnrollmentMemberInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private org.openuss.lecture.EnrollmentMember loadEnrollmentMemberFromEnrollmentMemberInfo(
			org.openuss.lecture.EnrollmentMemberInfo enrollmentMemberInfo) {

		org.openuss.lecture.EnrollmentMember enrollmentMember = this.load(enrollmentMemberInfo.getId());
		if (enrollmentMember == null) {
			enrollmentMember = org.openuss.lecture.EnrollmentMember.Factory.newInstance();
		}
		return enrollmentMember;
	}

	/**
	 * @see org.openuss.lecture.EnrollmentMemberDao#enrollmentMemberInfoToEntity(org.openuss.lecture.EnrollmentMemberInfo)
	 */
	public org.openuss.lecture.EnrollmentMember enrollmentMemberInfoToEntity(
			org.openuss.lecture.EnrollmentMemberInfo enrollmentMemberInfo) {
		org.openuss.lecture.EnrollmentMember entity = this
				.loadEnrollmentMemberFromEnrollmentMemberInfo(enrollmentMemberInfo);
		this.enrollmentMemberInfoToEntity(enrollmentMemberInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.lecture.EnrollmentMemberDao#enrollmentMemberInfoToEntity(org.openuss.lecture.EnrollmentMemberInfo,
	 *      org.openuss.lecture.EnrollmentMember)
	 */
	public void enrollmentMemberInfoToEntity(org.openuss.lecture.EnrollmentMemberInfo sourceVO,
			org.openuss.lecture.EnrollmentMember targetEntity, boolean copyIfNull) {

		super.enrollmentMemberInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}


}