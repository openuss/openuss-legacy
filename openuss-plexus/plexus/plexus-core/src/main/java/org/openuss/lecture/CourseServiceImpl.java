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

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.system.SystemProperties;

/**
 * @see org.openuss.lecture.CourseService
 */
public class CourseServiceImpl extends org.openuss.lecture.CourseServiceBase {
	
	private static final Logger logger = Logger.getLogger(CourseServiceImpl.class);

	/**
	 * @see org.openuss.lecture.CourseService#create(org.openuss.lecture.CourseInfo)
	 */
	public Long handleCreate(CourseInfo courseInfo) {
		
		Validate.notNull(courseInfo, "CourseServiceImpl.handleCreate - courseInfo cannot be null.");
		Validate.notNull(courseInfo.getCourseTypeId(), "CourseServiceImpl.handleCreate - getCourseTypeId cannot be null.");
		Validate.notNull(courseInfo.getPeriodId(), "CourseServiceImpl.handleCreate - PeriodId cannot be null.");
		
		// Transform VO to entity
		Course courseEntity = this.getCourseDao().courseInfoToEntity(courseInfo);
		Validate.notNull(courseEntity, "CourseServiceImpl.handleCreate - cannot transform courseInfo to entity.");
		
		// Add Course to CourseType and Period
		this.getCourseTypeDao().load (courseInfo.getCourseTypeId()).add(courseEntity);
		this.getPeriodDao().load (courseInfo.getPeriodId()).add(courseEntity);
		
		// Save Entity
		this.getCourseDao().create(courseEntity);
		Validate.notNull(courseEntity, "CourseServiceImpl.handleCreate - ID of course cannot be null.");
		
		// FIXME - Kai, Indexing should not base on VOs!
		// Kai: Do not delete this!!! Set id of institute VO for indexing
		courseInfo.setId(courseEntity.getId());
		
		//TODO: Security
		//FIXME: Shouldn't this be CourseType? And we don't want that!
		Institute institute = courseEntity.getCourseType().getInstitute();
		this.getSecurityService().createObjectIdentity(courseEntity, institute);
		
		return courseEntity.getId();
	}
	
	/**
	 * @see org.openuss.lecture.CourseService#create(org.openuss.lecture.CourseInfo)
	 */
	@Override
	public void handleRemoveCourse (Long courseId) throws Exception {
		
		// TODO: Security
		
		Validate.notNull(courseId, "CourseServiceImpl.handleRemoveCourse - courseId cannot be null.");
		Course course = (Course) this.getCourseDao().load(courseId);
		Validate.notNull(course, "CourseServiceImpl.handleRemoveCourse - no course entity found with the corresponding courseId "+courseId);
		
		// TODO: Fire removedCourse event
		//fireRemovingCourse(course);
		
		// Remove course
		this.getCourseDao().remove(courseId);
	}
	
	public List handleFindCoursesByCourseType (Long courseTypeId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public CourseInfo handleFindCourse (Long courseId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean handleIsNoneExistingCourseShortcut (CourseInfo self, String shortcut) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * @see org.openuss.lecture.CourseService#getAssistants(org.openuss.lecture.Course)
	 */
	private List<CourseMemberInfo> getAssistants(Course course) throws Exception {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, course,
				CourseMemberType.ASSISTANT);
	}

	/**
	 * @see org.openuss.lecture.CourseService#getAspirants(org.openuss.lecture.Course)
	 */
	protected List<CourseMemberInfo> handleGetAspirants(Course course) throws Exception {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, course,
				CourseMemberType.ASPIRANT);
	}

	/**
	 * @see org.openuss.lecture.CourseService#getParticipants(org.openuss.lecture.Course)
	 */
	protected List<CourseMemberInfo> handleGetParticipants(Course course) throws Exception {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, course,
				CourseMemberType.PARTICIPANT);
	}

	/**
	 * @see org.openuss.lecture.CourseService#addAssistant(org.openuss.lecture.Course,
	 *      org.openuss.security.User)
	 * @deprecated
	 */
	protected void handleAddAssistant(Course course, User user) throws Exception {
		CourseMember assistant = retrieveCourseMember(course, user);
		assistant.setMemberType(CourseMemberType.ASSISTANT);
		persistCourseMember(assistant);
	}

	private void persistCourseMember(CourseMember member) {
		if (member.getId() == null) {
			getCourseMemberDao().create(member);
		} else {
			getCourseMemberDao().update(member);
		}
	}

	/**
	 * Add aspirant to course
	 */
	private void addAspirant(Course course, User user) throws Exception {
		CourseMember aspirant = retrieveCourseMember(course, user);
		aspirant.setMemberType(CourseMemberType.ASPIRANT);
		persistCourseMember(aspirant);
	}

	private CourseMember retrieveCourseMember(Course course, User user) {
		CourseMember member = getCourseMemberDao().findByUserAndCourse(user, course);
		if (member == null) {
			member = CourseMember.Factory.newInstance();
			course = getCourseDao().load(course.getId());
			user = getSecurityService().getUser(user.getId());
			member.setCourse(course);
			member.setUser(user);
		}
		return member;
	}

	/**
	 * Add participant to course
	 */
	private void addParticipant(Course course, User user) throws Exception {
		CourseMember participant = retrieveCourseMember(course, user);
		persistParticipantWithPermissions(participant);
	}

	@Override
	protected void handleAcceptAspirant(Long memberId) throws Exception {
		CourseMember member = getCourseMemberDao().load(memberId);
		if (member.getMemberType() == CourseMemberType.ASPIRANT) {
			persistParticipantWithPermissions(member);
		}
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("coursename", "" + member.getCourse().getName() + "(" + member.getCourse().getShortcut() + ")");
		getMessageService().sendMessage(member.getCourse().getName() + "(" + member.getCourse().getShortcut() + ")",
				"course.application.subject", "courseapplicationapply", parameters, member.getUser());
	}

	private void persistParticipantWithPermissions(CourseMember participant) {
		participant.setMemberType(CourseMemberType.PARTICIPANT);
		getSecurityService().setPermissions(participant.getUser(), participant.getCourse(),
				LectureAclEntry.COURSE_PARTICIPANT);
		persistCourseMember(participant);
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
	protected void handleRejectAspirant(Long memberId) throws Exception {
		CourseMember member = getCourseMemberDao().load(memberId);
		removeMember(memberId);
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("coursename", "" + member.getCourse().getName() + "(" + member.getCourse().getShortcut() + ")");
		getMessageService().sendMessage(member.getCourse().getName() + "(" + member.getCourse().getShortcut() + ")",
				"course.application.subject", "courseapplicationreject", parameters, member.getUser());
	}

	@Override
	protected void handleAddAspirant(CourseInfo course, User user) throws Exception {
		CourseMember aspirant = retrieveCourseMember(getCourseDao().courseInfoToEntity(course), user);
		aspirant.setMemberType(CourseMemberType.ASPIRANT);
		getCourseMemberDao().create(aspirant);
	}

	@Override
	protected void handleAddAssistant(CourseInfo course, User user) throws Exception {
		CourseMember assistant = retrieveCourseMember(getCourseDao().courseInfoToEntity(course), user);
		assistant.setMemberType(CourseMemberType.ASSISTANT);
		getCourseMemberDao().create(assistant);
	}

	@Override
	protected void handleAddParticipant(CourseInfo course, User user) throws Exception {
		CourseMember participant = retrieveCourseMember(getCourseDao().courseInfoToEntity(course), user);
		persistParticipantWithPermissions(participant);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleApplyUser(CourseInfo courseInfo, User user) throws Exception {
		Validate.notNull(user, "Parameter user must not be null.");
		Validate.notNull(courseInfo, "Parameter courseInfo must not be null.");
		Validate.notNull(courseInfo.getId(), "Parameter courseInfo.id must not be null.");
		Course course = getCourseDao().load(courseInfo.getId());
		if (course.getAccessType() == AccessType.APPLICATION) {
			List<CourseMemberInfo> assistants = getAssistants(course);
			List<User> recipients = new ArrayList<User>();
			if (assistants != null && assistants.size() != 0) {
				for (CourseMemberInfo member : assistants) {
					recipients.add(getSecurityService().getUser(member.getUserId()));
				}
				// FIXME - link should be configured from outside the core
				// component
				String link = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()
						+ "views/secured/course/courseaspirants.faces?course=" + course.getId();
				Map<String, String> parameters = new HashMap<String, String>();
				parameters.put("coursename", course.getName() + "(" + course.getShortcut() + ")");
				parameters.put("courseapplicantlink", link);
				getMessageService().sendMessage(course.getName(), "course.application.subject", "courseapplication",
						parameters, recipients);
			}
			addAspirant(course, user);
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

	@SuppressWarnings("unchecked")
	@Override
	protected List<CourseMemberInfo> handleGetAspirants(CourseInfo course) throws Exception {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO,
				getCourseDao().courseInfoToEntity(course), CourseMemberType.ASPIRANT);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<CourseMemberInfo> handleGetAssistants(CourseInfo course) throws Exception {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO,
				getCourseDao().courseInfoToEntity(course), CourseMemberType.ASSISTANT);
	}

	@Override
	protected CourseInfo handleGetCourseInfo(Long courseId) throws Exception {
		Validate.notNull(courseId, "Parameter courseId must not be null!");
		return (CourseInfo) getCourseDao().load(CourseDao.TRANSFORM_COURSEINFO, courseId);
	}

	@Override
	protected CourseMemberInfo handleGetMemberInfo(CourseInfo course, User user) throws Exception {
		return (CourseMemberInfo) getCourseMemberDao().findByUserAndCourse(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO,
				user, getCourseDao().courseInfoToEntity(course));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<CourseMemberInfo> handleGetParticipants(CourseInfo course) throws Exception {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO,
				getCourseDao().courseInfoToEntity(course), CourseMemberType.PARTICIPANT);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleRemoveAspirants(CourseInfo course) throws Exception {
		Course courseDao = getCourseDao().load(course.getId());
		List<CourseMember> members = getCourseMemberDao().findByCourse(courseDao);
		Iterator<CourseMember> i = members.iterator();
		CourseMember member;
		while (i.hasNext()) {
			member = i.next();
			if (member.getMemberType() == CourseMemberType.ASPIRANT) {
				getCourseMemberDao().remove(member.getId());
			}
		}
	}

	@Override
	protected void handleUpdateCourse(CourseInfo courseInfo) throws Exception {
		logger.debug("Starting method handleUpdateCourse");
		Validate.notNull(courseInfo, "Parameter course must not be null.");
		Validate.notNull(courseInfo.getId(), "Parameter course must contain a valid course id.");
		Course course = getCourseDao().courseInfoToEntity(courseInfo);
		getCourseDao().update(course);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List handleFindCoursesByPeriodAndInstitute (Long periodId, Long instituteId) throws Exception {
		// TODO: Implement this method.
		return null;
	}
	
	@Override
	protected void handleRegisterListener(LectureListener listener) throws Exception {
		//TODO: Implement this method.
	}
	
	
	/*------------------- private methods -------------------- */
	
	// TODO: Add Set of listeners
	
	// TODO: Method unregisterListener
	
	// TODO: Method fireRemovingCourseType (CourseType courseType)
	
	/**
	 * Convenience method for isNonExisting methods.<br/> Checks whether or not the found record is equal to self entry.
	 * <ul>
	 * <li>self == null AND found == null => <b>true</b></li>
	 * <li>self == null AND found <> null => <b>false</b></li>
	 * <li>self <> null AND found == null => <b>true</b></li>
	 * <li>self <> null AND found <> null AND self == found => <b>true</b></li>
	 * <li>self <> null AND found <> null AND self <> found => <b>false</b></li>
	 * </ul>
	 * 
	 * @param self
	 *            current record
	 * @param found
	 *            in database
	 * @return true or false
	 */
	private boolean isEqualOrNull(Object self, Object found) {
		if (self == null || found == null) {
			return found == null;
		} else {
			return self.equals(found);
		}
	}

}