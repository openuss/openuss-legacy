// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

/**
 * @see org.openuss.lecture.CourseMember
 */
public class CourseMemberDaoImpl extends org.openuss.lecture.CourseMemberDaoBase {

	/**
	 * @see org.openuss.lecture.CourseMemberDao#toCourseMemberInfo(org.openuss.lecture.CourseMember,
	 *      org.openuss.lecture.CourseMemberInfo)
	 */
	public void toCourseMemberInfo(CourseMember sourceEntity, CourseMemberInfo targetVO) {
		targetVO.setId(sourceEntity.getId());
		targetVO.setUserId(sourceEntity.getUser().getId());
		targetVO.setCourseId(sourceEntity.getCourse().getId());
		targetVO.setUsername(sourceEntity.getUser().getUsername());
		targetVO.setFirstName(sourceEntity.getUser().getFirstName());
		targetVO.setLastName(sourceEntity.getUser().getLastName());
		targetVO.setTitle(sourceEntity.getUser().getTitle());
		targetVO.setEmail(sourceEntity.getUser().getEmail());
		targetVO.setMemberType(sourceEntity.getMemberType());
	}

	/**
	 * @see org.openuss.lecture.CourseMemberDao#toCourseMemberInfo(org.openuss.lecture.CourseMember)
	 */
	public org.openuss.lecture.CourseMemberInfo toCourseMemberInfo(
			final org.openuss.lecture.CourseMember entity) {

		return super.toCourseMemberInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private org.openuss.lecture.CourseMember loadCourseMemberFromCourseMemberInfo(
			org.openuss.lecture.CourseMemberInfo courseMemberInfo) {
		org.openuss.lecture.CourseMember courseMember = null;
		if (courseMemberInfo.getId() != null) { 
			courseMember = this.load(courseMemberInfo.getId());
		} else {
			courseMember = org.openuss.lecture.CourseMember.Factory.newInstance();
		}
		return courseMember;
	}


	/**
	 * @see org.openuss.lecture.CourseMemberDao#courseMemberInfoToEntity(org.openuss.lecture.CourseMemberInfo)
	 */
	public org.openuss.lecture.CourseMember courseMemberInfoToEntity(
			org.openuss.lecture.CourseMemberInfo courseMemberInfo) {
		org.openuss.lecture.CourseMember entity = this
				.loadCourseMemberFromCourseMemberInfo(courseMemberInfo);
		this.courseMemberInfoToEntity(courseMemberInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.lecture.CourseMemberDao#courseMemberInfoToEntity(org.openuss.lecture.CourseMemberInfo,
	 *      org.openuss.lecture.CourseMember)
	 */
	public void courseMemberInfoToEntity(org.openuss.lecture.CourseMemberInfo sourceVO,
			org.openuss.lecture.CourseMember targetEntity, boolean copyIfNull) {

		super.courseMemberInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}


}