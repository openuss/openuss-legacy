// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @see org.openuss.lecture.EnrollmentService
 */
public class EnrollmentServiceImpl extends org.openuss.lecture.EnrollmentServiceBase {

	/**
	 * @see org.openuss.lecture.EnrollmentService#getAssistants(org.openuss.lecture.Enrollment)
	 */
	protected java.util.List handleGetAssistants(org.openuss.lecture.Enrollment enrollment) throws java.lang.Exception {
		return getEnrollmentMemberDao().findByType(EnrollmentMemberDao.TRANSFORM_ENROLLMENTMEMBERINFO, enrollment,
				EnrollmentMemberType.ASSISTANT);
	}

	/**
	 * @see org.openuss.lecture.EnrollmentService#getAspirants(org.openuss.lecture.Enrollment)
	 */
	protected java.util.List handleGetAspirants(org.openuss.lecture.Enrollment enrollment) throws java.lang.Exception {
		return getEnrollmentMemberDao().findByType(EnrollmentMemberDao.TRANSFORM_ENROLLMENTMEMBERINFO, enrollment,
				EnrollmentMemberType.ASPIRANT);
	}

	/**
	 * @see org.openuss.lecture.EnrollmentService#getParticipants(org.openuss.lecture.Enrollment)
	 */
	protected java.util.List handleGetParticipants(org.openuss.lecture.Enrollment enrollment)
			throws java.lang.Exception {
		return getEnrollmentMemberDao().findByType(EnrollmentMemberDao.TRANSFORM_ENROLLMENTMEMBERINFO, enrollment,
				EnrollmentMemberType.PARTICIPANT);
	}

	/**
	 * @see org.openuss.lecture.EnrollmentService#addAssistant(org.openuss.lecture.Enrollment,
	 *      org.openuss.security.User)
	 */
	protected void handleAddAssistant(org.openuss.lecture.Enrollment enrollment, org.openuss.security.User user)
			throws java.lang.Exception {
		EnrollmentMember assistant = createEnrollmentMember(enrollment, user);
		assistant.setMemberType(EnrollmentMemberType.ASSISTANT);
		getEnrollmentMemberDao().create(assistant);
	}

	/**
	 * @see org.openuss.lecture.EnrollmentService#addAspirant(org.openuss.lecture.Enrollment,
	 *      org.openuss.security.User)
	 */
	protected void handleAddAspirant(org.openuss.lecture.Enrollment enrollment, org.openuss.security.User user)
			throws java.lang.Exception {
		EnrollmentMember aspirant = createEnrollmentMember(enrollment, user);
		aspirant.setMemberType(EnrollmentMemberType.ASPIRANT);
		getEnrollmentMemberDao().create(aspirant);
	}

	private EnrollmentMember createEnrollmentMember(org.openuss.lecture.Enrollment enrollment,
			org.openuss.security.User user) {
		EnrollmentMember aspirant = EnrollmentMember.Factory.newInstance();
		aspirant.setEnrollment(enrollment);
		aspirant.setUser(user);
		return aspirant;
	}

	/**
	 * @see org.openuss.lecture.EnrollmentService#addParticipant(org.openuss.lecture.Enrollment,
	 *      org.openuss.security.User)
	 */
	protected void handleAddParticipant(org.openuss.lecture.Enrollment enrollment, org.openuss.security.User user)
			throws java.lang.Exception {
		EnrollmentMember participant = createEnrollmentMember(enrollment, user);
		persistParticipantWithPermissions(participant);
	}

	@Override
	protected void handleAcceptAspirant(Long memberId) throws Exception {
		EnrollmentMember member = getEnrollmentMemberDao().load(memberId);
		if (member.getMemberType() == EnrollmentMemberType.ASPIRANT) {
			persistParticipantWithPermissions(member);
		}
	}

	private void persistParticipantWithPermissions(EnrollmentMember participant) {
		participant.setMemberType(EnrollmentMemberType.PARTICIPANT);
		getSecurityService().setPermissions(participant.getUser(), participant.getEnrollment(),
				LectureAclEntry.ENROLLMENT_PARTICIPANT);

		if (participant.getId() == null) {
			getEnrollmentMemberDao().create(participant);
		} else {
			getEnrollmentMemberDao().update(participant);
		}
	}

	@Override
	protected void handleRemoveMember(Long memberId) throws Exception {
		EnrollmentMember member = getEnrollmentMemberDao().load(memberId);
		if (member != null) {
			getSecurityService().removePermission(member.getUser(), member.getEnrollment());
			getEnrollmentMemberDao().remove(member);
		}
	}

	@Override
	protected void handleApplyUser(Enrollment enrollment, User user) throws Exception {
		Enrollment original = getEnrollmentDao().load(enrollment.getId());
		if (original.getAccessType() == AccessType.APPLICATION) {
			addAspirant(enrollment, user);
		} else {
			throw new EnrollmentServiceException("message_error_enrollment_accesstype_is_not_application");
		}
	}

	@Override
	protected void handleApplyUserByPassword(String password, Enrollment enrollment, User user) throws Exception {
		Enrollment original = getEnrollmentDao().load(enrollment.getId());
		if (original.getAccessType() == AccessType.PASSWORD && original.isPasswordCorrect(password)) {
			addParticipant(enrollment, user);
		} else {
			throw new EnrollmentServiceException("message_error_password_is_not_correct");
		}
	}

	@Override
	protected void handleRejectAspirant(Long memberId) throws Exception {
		removeMember(memberId);
	}

}