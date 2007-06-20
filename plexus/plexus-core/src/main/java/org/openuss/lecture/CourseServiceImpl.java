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
 * @see org.openuss.lecture.CourseService
 */
public class CourseServiceImpl extends org.openuss.lecture.CourseServiceBase {

	/**
	 * @see org.openuss.lecture.CourseService#getAssistants(org.openuss.lecture.Course)
	 */
	protected java.util.List handleGetAssistants(org.openuss.lecture.Course course) throws java.lang.Exception {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, course,
				CourseMemberType.ASSISTANT);
	}

	/**
	 * @see org.openuss.lecture.CourseService#getAspirants(org.openuss.lecture.Course)
	 */
	protected java.util.List handleGetAspirants(org.openuss.lecture.Course course) throws java.lang.Exception {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, course,
				CourseMemberType.ASPIRANT);
	}

	/**
	 * @see org.openuss.lecture.CourseService#getParticipants(org.openuss.lecture.Course)
	 */
	protected java.util.List handleGetParticipants(org.openuss.lecture.Course course)
			throws java.lang.Exception {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, course,
				CourseMemberType.PARTICIPANT);
	}

	/**
	 * @see org.openuss.lecture.CourseService#addAssistant(org.openuss.lecture.Course,
	 *      org.openuss.security.User)
	 */
	protected void handleAddAssistant(org.openuss.lecture.Course course, org.openuss.security.User user)
			throws java.lang.Exception {
		CourseMember assistant = createCourseMember(course, user);
		assistant.setMemberType(CourseMemberType.ASSISTANT);
		getCourseMemberDao().create(assistant);
	}

	/**
	 * @see org.openuss.lecture.CourseService#addAspirant(org.openuss.lecture.Course,
	 *      org.openuss.security.User)
	 */
	protected void handleAddAspirant(org.openuss.lecture.Course course, org.openuss.security.User user)
			throws java.lang.Exception {
		CourseMember aspirant = createCourseMember(course, user);
		aspirant.setMemberType(CourseMemberType.ASPIRANT);
		getCourseMemberDao().create(aspirant);
	}

	private CourseMember createCourseMember(org.openuss.lecture.Course course,
			org.openuss.security.User user) {
		course = getCourseDao().load(course.getId());
		user = getSecurityService().getUser(user.getId());
		CourseMember aspirant = CourseMember.Factory.newInstance();
		aspirant.setCourse(course);
		aspirant.setUser(user);
		return aspirant;
	}

	/**
	 * @see org.openuss.lecture.CourseService#addParticipant(org.openuss.lecture.Course,
	 *      org.openuss.security.User)
	 */
	protected void handleAddParticipant(org.openuss.lecture.Course course, org.openuss.security.User user)
			throws java.lang.Exception {
		CourseMember participant = createCourseMember(course, user);
		persistParticipantWithPermissions(participant);
	}

	@Override
	protected void handleAcceptAspirant(Long memberId) throws Exception {
		CourseMember member = getCourseMemberDao().load(memberId);
		if (member.getMemberType() == CourseMemberType.ASPIRANT) {
			persistParticipantWithPermissions(member);
		}
		Map parameters = new HashMap();
		parameters.put("coursename", ""+member.getCourse().getName()+"("+member.getCourse().getShortcut()+")");
		getMessageService().sendMessage(member.getCourse().getName()+"("+member.getCourse().getShortcut()+")", 
				"course.application.subject", "courseapplicationapply", parameters, 
				member.getUser());
	}

	private void persistParticipantWithPermissions(CourseMember participant) {
		participant.setMemberType(CourseMemberType.PARTICIPANT);
		getSecurityService().setPermissions(participant.getUser(), participant.getCourse(),
				LectureAclEntry.COURSE_PARTICIPANT);

		if (participant.getId() == null) {
			getCourseMemberDao().create(participant);
		} else {
			getCourseMemberDao().update(participant);
		}
	}

	@Override
	protected void handleRemoveMember(Long memberId) throws Exception {
		CourseMember member = getCourseMemberDao().load(memberId);
		if (member != null) {
			getSecurityService().removePermission(member.getUser(), member.getCourse());
			getCourseMemberDao().remove(member);
		}
	}

	@Override
	protected void handleApplyUser(Course course, User user) throws Exception {
		Course originalCourse = getCourseDao().load(course.getId());
		if (originalCourse.getAccessType() == AccessType.APPLICATION) {
			// TODO send email to all assistants
			addAspirant(originalCourse, user);
		} else {
			throw new CourseApplicationException("message_error_course_accesstype_is_not_application");
		}
	}

	@Override
	protected void handleApplyUserByPassword(String password, Course course, User user) throws Exception {
		Course originalCourse = getCourseDao().load(course.getId());
		if (originalCourse.getAccessType() == AccessType.PASSWORD && originalCourse.isPasswordCorrect(password)) {
			addParticipant(originalCourse, user);
		} else {
			throw new CourseApplicationException("message_error_password_is_not_correct");
		}
	}

	@Override
	protected void handleRejectAspirant(Long memberId) throws Exception {
		CourseMember member = getCourseMemberDao().load(memberId);
		removeMember(memberId);
		Map parameters = new HashMap();
		parameters.put("coursename", ""+member.getCourse().getName()+"("+member.getCourse().getShortcut()+")");
		getMessageService().sendMessage(member.getCourse().getName()+"("+member.getCourse().getShortcut()+")", 
				"course.application.subject", "courseapplicationreject", parameters, 
				member.getUser());		
	}

	@Override
	protected Course handleGetCourse(Course course) throws Exception {
		if (course == null) {
			return null;
		} else {
			return getCourseDao().load(course.getId());
		}
	}

	@Override
	protected CourseMemberInfo handleGetMemberInfo(Course course, User user) throws Exception {
		return (CourseMemberInfo) getCourseMemberDao().findByUserAndCourse(
				CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, user, course);
	}

	@Override
	protected void handleAddAspirant(CourseInfo course, User user) throws Exception {
		CourseMember aspirant = createCourseMember(getCourseDao().courseInfoToEntity(course), user);
		aspirant.setMemberType(CourseMemberType.ASPIRANT);
		getCourseMemberDao().create(aspirant);
	}

	@Override
	protected void handleAddAssistant(CourseInfo course, User user) throws Exception {
		CourseMember assistant = createCourseMember(getCourseDao().courseInfoToEntity(course), user);
		assistant.setMemberType(CourseMemberType.ASSISTANT);
		getCourseMemberDao().create(assistant);
	}

	@Override
	protected void handleAddParticipant(CourseInfo course, User user) throws Exception {
		CourseMember participant = createCourseMember(getCourseDao().courseInfoToEntity(course), user);
		persistParticipantWithPermissions(participant);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleApplyUser(CourseInfo course, User user) throws Exception {
		Course originalCourse = getCourseDao().courseInfoToEntity(course);
		if (originalCourse.getAccessType() == AccessType.APPLICATION) {
			List<CourseMemberInfo> assistants = getAssistants(course);
			List<User> recipients = new ArrayList();
			if (assistants!=null&&assistants.size()!=0){
				Iterator i = assistants.iterator();
				while (i.hasNext()){
					recipients.add(getSecurityService().getUser(((CourseMemberInfo)i.next()).getUserId()));					
				}
				String link = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+"views/secured/course/courseaspirants.faces?course="+course.getId(); 
				Map parameters = new HashMap();
				parameters.put("coursename", course.getName()+"("+course.getShortcut()+")");
				parameters.put("courseapplicantlink", link);			
				getMessageService().sendMessage(course.getName(), "course.application.subject", "courseapplication", parameters, recipients);
			}
			addAspirant(originalCourse, user);
		} else {
			throw new CourseApplicationException("message_error_course_accesstype_is_not_application");
		}
	}

	@Override
	protected void handleApplyUserByPassword(String password, CourseInfo course, User user) throws Exception {
		Course originalCourse = getCourseDao().courseInfoToEntity(course);
		if (originalCourse.getAccessType() == AccessType.PASSWORD && originalCourse.isPasswordCorrect(password)) {
			addParticipant(originalCourse, user);
		} else {
			throw new CourseApplicationException("message_error_password_is_not_correct");
		}
	}

	@Override
	protected List handleGetAspirants(CourseInfo course) throws Exception {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, getCourseDao().courseInfoToEntity(course),
				CourseMemberType.ASPIRANT);
	}

	@Override
	protected List handleGetAssistants(CourseInfo course) throws Exception {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, getCourseDao().courseInfoToEntity(course),
				CourseMemberType.ASSISTANT);
	}

	@Override
	protected CourseInfo handleGetCourseInfo(Course course) throws Exception {
		if ((course==null)||(course.getId()==null)) return null;
		course = getCourseDao().load(course.getId());
		if (course==null) return null;
		return getCourseDao().toCourseInfo(course);
	}

	@Override
	protected CourseMemberInfo handleGetMemberInfo(CourseInfo course, User user) throws Exception {
		return (CourseMemberInfo) getCourseMemberDao().findByUserAndCourse(
				CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, user, getCourseDao().courseInfoToEntity(course));
	}

	@Override
	protected List handleGetParticipants(CourseInfo course) throws Exception {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, getCourseDao().courseInfoToEntity(course),
				CourseMemberType.PARTICIPANT);
	}

	@Override
	protected void handleRemoveAspirants(CourseInfo course) throws Exception {
		Course courseDao = getCourseDao().load(course.getId()); 
		List<CourseMember> members = getCourseMemberDao().findByCourse(courseDao);
		Iterator i = members.iterator();
		CourseMember member;
		while (i.hasNext()){
			member = (CourseMember) i.next();
			if (member.getMemberType()== CourseMemberType.ASPIRANT){
				getCourseMemberDao().remove(member.getId()); 
			}
		}
	}

}