// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.system.SystemProperties;

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
		enrollment = getEnrollmentDao().load(enrollment.getId());
		user = getSecurityService().getUser(user.getId());
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
		Map parameters = new HashMap();
		parameters.put("enrollmentname", ""+member.getEnrollment().getName()+"("+member.getEnrollment().getShortcut()+")");
		getMessageService().sendMessage(member.getEnrollment().getName()+"("+member.getEnrollment().getShortcut()+")", 
				"enrollment.application.subject", "enrollmentapplicationapply", parameters, 
				member.getUser());
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
		Enrollment originalEnrollment = getEnrollmentDao().load(enrollment.getId());
		if (originalEnrollment.getAccessType() == AccessType.APPLICATION) {
			// TODO send email to all assistants
			addAspirant(originalEnrollment, user);
		} else {
			throw new EnrollmentApplicationException("message_error_enrollment_accesstype_is_not_application");
		}
	}

	@Override
	protected void handleApplyUserByPassword(String password, Enrollment enrollment, User user) throws Exception {
		Enrollment originalEnrollment = getEnrollmentDao().load(enrollment.getId());
		if (originalEnrollment.getAccessType() == AccessType.PASSWORD && originalEnrollment.isPasswordCorrect(password)) {
			addParticipant(originalEnrollment, user);
		} else {
			throw new EnrollmentApplicationException("message_error_password_is_not_correct");
		}
	}

	@Override
	protected void handleRejectAspirant(Long memberId) throws Exception {
		EnrollmentMember member = getEnrollmentMemberDao().load(memberId);
		removeMember(memberId);
		Map parameters = new HashMap();
		parameters.put("enrollmentname", ""+member.getEnrollment().getName()+"("+member.getEnrollment().getShortcut()+")");
		getMessageService().sendMessage(member.getEnrollment().getName()+"("+member.getEnrollment().getShortcut()+")", 
				"enrollment.application.subject", "enrollmentapplicationreject", parameters, 
				member.getUser());		
	}

	@Override
	protected Enrollment handleGetEnrollment(Enrollment enrollment) throws Exception {
		if (enrollment == null) {
			return null;
		} else {
			return getEnrollmentDao().load(enrollment.getId());
		}
	}

	@Override
	protected EnrollmentMemberInfo handleGetMemberInfo(Enrollment enrollment, User user) throws Exception {
		return (EnrollmentMemberInfo) getEnrollmentMemberDao().findByUserAndEnrollment(
				EnrollmentMemberDao.TRANSFORM_ENROLLMENTMEMBERINFO, user, enrollment);
	}

	@Override
	protected void handleAddAspirant(EnrollmentInfo enrollment, User user) throws Exception {
		EnrollmentMember aspirant = createEnrollmentMember(getEnrollmentDao().enrollmentInfoToEntity(enrollment), user);
		aspirant.setMemberType(EnrollmentMemberType.ASPIRANT);
		getEnrollmentMemberDao().create(aspirant);
	}

	@Override
	protected void handleAddAssistant(EnrollmentInfo enrollment, User user) throws Exception {
		EnrollmentMember assistant = createEnrollmentMember(getEnrollmentDao().enrollmentInfoToEntity(enrollment), user);
		assistant.setMemberType(EnrollmentMemberType.ASSISTANT);
		getEnrollmentMemberDao().create(assistant);
	}

	@Override
	protected void handleAddParticipant(EnrollmentInfo enrollment, User user) throws Exception {
		EnrollmentMember participant = createEnrollmentMember(getEnrollmentDao().enrollmentInfoToEntity(enrollment), user);
		persistParticipantWithPermissions(participant);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleApplyUser(EnrollmentInfo enrollment, User user) throws Exception {
		Enrollment originalEnrollment = getEnrollmentDao().enrollmentInfoToEntity(enrollment);
		if (originalEnrollment.getAccessType() == AccessType.APPLICATION) {
			List<EnrollmentMemberInfo> assistants = getAssistants(enrollment);
			List<User> recipients = new ArrayList();
			if (assistants!=null&&assistants.size()!=0){
				Iterator i = assistants.iterator();
				while (i.hasNext()){
					recipients.add(getSecurityService().getUser(((EnrollmentMemberInfo)i.next()).getUserId()));					
				}
				String link = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+"views/secured/enrollment/enrollmentaspirants.faces?enrollment="+enrollment.getId(); 
				Map parameters = new HashMap();
				parameters.put("enrollmentname", enrollment.getName()+"("+enrollment.getShortcut()+")");
				parameters.put("enrollmentapplicantlink", link);			
				getMessageService().sendMessage(enrollment.getName(), "enrollment.application.subject", "enrollmentapplication", parameters, recipients);
			}
			addAspirant(originalEnrollment, user);
		} else {
			throw new EnrollmentApplicationException("message_error_enrollment_accesstype_is_not_application");
		}
	}

	@Override
	protected void handleApplyUserByPassword(String password, EnrollmentInfo enrollment, User user) throws Exception {
		Enrollment originalEnrollment = getEnrollmentDao().enrollmentInfoToEntity(enrollment);
		if (originalEnrollment.getAccessType() == AccessType.PASSWORD && originalEnrollment.isPasswordCorrect(password)) {
			addParticipant(originalEnrollment, user);
		} else {
			throw new EnrollmentApplicationException("message_error_password_is_not_correct");
		}
	}

	@Override
	protected List handleGetAspirants(EnrollmentInfo enrollment) throws Exception {
		return getEnrollmentMemberDao().findByType(EnrollmentMemberDao.TRANSFORM_ENROLLMENTMEMBERINFO, getEnrollmentDao().enrollmentInfoToEntity(enrollment),
				EnrollmentMemberType.ASPIRANT);
	}

	@Override
	protected List handleGetAssistants(EnrollmentInfo enrollment) throws Exception {
		return getEnrollmentMemberDao().findByType(EnrollmentMemberDao.TRANSFORM_ENROLLMENTMEMBERINFO, getEnrollmentDao().enrollmentInfoToEntity(enrollment),
				EnrollmentMemberType.ASSISTANT);
	}

	@Override
	protected EnrollmentInfo handleGetEnrollmentInfo(Enrollment enrollment) throws Exception {
		if ((enrollment==null)||(enrollment.getId()==null)) return null;
		enrollment = getEnrollmentDao().load(enrollment.getId());
		if (enrollment==null) return null;
		return getEnrollmentDao().toEnrollmentInfo(enrollment);
	}

	@Override
	protected EnrollmentMemberInfo handleGetMemberInfo(EnrollmentInfo enrollment, User user) throws Exception {
		return (EnrollmentMemberInfo) getEnrollmentMemberDao().findByUserAndEnrollment(
				EnrollmentMemberDao.TRANSFORM_ENROLLMENTMEMBERINFO, user, getEnrollmentDao().enrollmentInfoToEntity(enrollment));
	}

	@Override
	protected List handleGetParticipants(EnrollmentInfo enrollment) throws Exception {
		return getEnrollmentMemberDao().findByType(EnrollmentMemberDao.TRANSFORM_ENROLLMENTMEMBERINFO, getEnrollmentDao().enrollmentInfoToEntity(enrollment),
				EnrollmentMemberType.PARTICIPANT);
	}

	@Override
	protected void handleRemoveAspirants(EnrollmentInfo enrollment) throws Exception {
		Enrollment enrollmentDao = getEnrollmentDao().load(enrollment.getId()); 
		List<EnrollmentMember> members = getEnrollmentMemberDao().findByEnrollment(enrollmentDao);
		Iterator i = members.iterator();
		EnrollmentMember member;
		while (i.hasNext()){
			member = (EnrollmentMember) i.next();
			if (member.getMemberType()== EnrollmentMemberType.ASPIRANT){
				getEnrollmentMemberDao().remove(member.getId()); 
			}
		}
	}

}