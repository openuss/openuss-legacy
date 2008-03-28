// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.List;

import org.openuss.security.User;

/**
 * @see org.openuss.lecture.CourseMember
 * 
 * @author Ingo Düppe
 */
public class CourseMemberDaoImpl extends CourseMemberDaoBase {

	/**
	 * @see org.openuss.lecture.CourseMemberDao#toCourseMemberInfo(org.openuss.lecture.CourseMember,
	 *      org.openuss.lecture.CourseMemberInfo)
	 */
	public void toCourseMemberInfo(CourseMember sourceEntity, CourseMemberInfo targetVO) {
		targetVO.setUserId(sourceEntity.getCourseMemberPk().getUser().getId());
		targetVO.setCourseId(sourceEntity.getCourseMemberPk().getCourse().getId());
		targetVO.setUsername(sourceEntity.getCourseMemberPk().getUser().getUsername());
		targetVO.setFirstName(sourceEntity.getCourseMemberPk().getUser().getFirstName());
		targetVO.setLastName(sourceEntity.getCourseMemberPk().getUser().getLastName());
		targetVO.setTitle(sourceEntity.getCourseMemberPk().getUser().getTitle());
		targetVO.setEmail(sourceEntity.getCourseMemberPk().getUser().getEmail());
		targetVO.setMemberType(sourceEntity.getMemberType());
	}

	/**
	 * @see org.openuss.lecture.CourseMemberDao#toCourseMemberInfo(org.openuss.lecture.CourseMember)
	 */
	public CourseMemberInfo toCourseMemberInfo(final CourseMember entity) {
		return super.toCourseMemberInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private CourseMember loadCourseMemberFromCourseMemberInfo(CourseMemberInfo courseMemberInfo) {
		CourseMemberPK pk = new CourseMemberPK();
		User user = User.Factory.newInstance();
		user.setId(courseMemberInfo.getUserId());
		pk.setUser(user);
		Course course = Course.Factory.newInstance();
		course.setId(courseMemberInfo.getCourseId());
		pk.setCourse(course);
		
		CourseMember courseMember = this.load(pk);
		if (courseMember == null) {
			courseMember = CourseMember.Factory.newInstance();
		}
		return courseMember;
	}

	/**
	 * @see org.openuss.lecture.CourseMemberDao#courseMemberInfoToEntity(org.openuss.lecture.CourseMemberInfo)
	 */
	public CourseMember courseMemberInfoToEntity(CourseMemberInfo courseMemberInfo) {
		CourseMember entity = this.loadCourseMemberFromCourseMemberInfo(courseMemberInfo);
		this.courseMemberInfoToEntity(courseMemberInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.lecture.CourseMemberDao#courseMemberInfoToEntity(org.openuss.lecture.CourseMemberInfo,
	 *      org.openuss.lecture.CourseMember)
	 */
	public void courseMemberInfoToEntity(CourseMemberInfo sourceVO, CourseMember targetEntity, boolean copyIfNull) {
		super.courseMemberInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

	/**
	 * @see org.openuss.lecture.CourseMemberDao#findByUser(int,
	 *      org.openuss.security.User)
	 */
	@Override
	public List findByUser(final int transform, final User user) {
		return this.findByUser(transform,
				"from org.openuss.lecture.CourseMember as courseMember where courseMember.courseMemberPk.user = ?",
				user);
	}

	/**
	 * @see org.openuss.lecture.CourseMemberDao#findByCourse(int,
	 *      org.openuss.lecture.Course)
	 */
	public List findByCourse(final int transform, final Course course) {
		return this.findByCourse(transform,	
				"from org.openuss.lecture.CourseMember as courseMember where courseMember.courseMemberPk.course = ?", course);
	}

	/**
	 * @see org.openuss.lecture.CourseMemberDao#findByType(int,
	 *      org.openuss.lecture.Course, org.openuss.lecture.CourseMemberType)
	 */
	public List findByType(final int transform, final Course course, final CourseMemberType memberType) {
		return this
				.findByType(
						transform,
						"from org.openuss.lecture.CourseMember as courseMember where courseMember.courseMemberPk.course = ? and courseMember.memberType = ?",
						course, memberType);
	}

}