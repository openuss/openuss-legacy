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

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.security.Roles;
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
	protected Long handleCreate(CourseInfo courseInfo) {

		Validate.notNull(courseInfo, "CourseServiceImpl.handleCreate - courseInfo cannot be null.");
		Validate.notNull(courseInfo.getCourseTypeId(),
				"CourseServiceImpl.handleCreate - getCourseTypeId cannot be null.");
		Validate.notNull(courseInfo.getPeriodId(), "CourseServiceImpl.handleCreate - PeriodId cannot be null.");

		// Default is enabled
		courseInfo.setEnabled(true);

		// Transform VO to entity
		Course courseEntity = this.getCourseDao().courseInfoToEntity(courseInfo);
		Validate.notNull(courseEntity, "CourseServiceImpl.handleCreate - cannot transform courseInfo to entity.");
		courseEntity.setEnabled(true);

		// Add Course to CourseType and Period
		this.getCourseTypeDao().load(courseInfo.getCourseTypeId()).add(courseEntity);
		this.getPeriodDao().load(courseInfo.getPeriodId()).add(courseEntity);

		// Save Entity
		this.getCourseDao().create(courseEntity);
		Validate.notNull(courseEntity, "CourseServiceImpl.handleCreate - ID of course cannot be null.");

		// FIXME - Kai, Indexing should not base on VOs!
		// Kai: Do not delete this!!! Set id of institute VO for indexing
		courseInfo.setId(courseEntity.getId());

		// Set Security
		this.getSecurityService().createObjectIdentity(courseEntity, courseEntity.getCourseType());
		updateAccessTypePermission(courseEntity);

		return courseEntity.getId();
	}

	/**
	 * @see org.openuss.lecture.CourseService#create(org.openuss.lecture.CourseInfo)
	 */
	protected void handleRemoveCourse(Long courseId) throws Exception {

		Validate.notNull(courseId, "CourseServiceImpl.handleRemoveCourse - courseId cannot be null.");
		Course course = (Course) this.getCourseDao().load(courseId);
		Validate.notNull(course,
				"CourseServiceImpl.handleRemoveCourse - no course entity found with the corresponding courseId "
						+ courseId);

		// Remove Security
		this.getSecurityService().removeAllPermissions(course);
		this.getSecurityService().removeObjectIdentity(course);

		// Remove Course
		course.getCourseType().remove(course);
		course.getPeriod().remove(course);
		this.getCourseDao().remove(courseId);
	}

	@SuppressWarnings( { "unchecked" })
	protected List handleFindCoursesByCourseType(Long courseTypeId) {

		Validate.notNull(courseTypeId, "CourseService.findCoursesByCourseType -" + "courseTypeId cannot be null.");

		CourseType courseType = this.getCourseTypeDao().load(courseTypeId);

		return this.getCourseDao().findByCourseType(CourseDao.TRANSFORM_COURSEINFO, courseType);
	}

	protected CourseInfo handleFindCourse(Long courseId) {

		Validate.notNull(courseId, "CourseService.findCourse - courseId cannot be null.");
		return this.getCourseDao().toCourseInfo(this.getCourseDao().load(courseId));
	}

	@Override
	protected boolean handleIsNoneExistingCourseShortcut(CourseInfo self, String shortcut) {
		Course found = getCourseDao().findByShortcut(shortcut);
		CourseInfo foundInfo = null;
		if (found != null) {
			foundInfo = this.getCourseDao().toCourseInfo(found);
		}
		return isEqualOrNull(self, foundInfo);
	}

	/**
	 * @see org.openuss.lecture.CourseService#getAssistants(org.openuss.lecture.Course)
	 */
	@SuppressWarnings( { "unchecked" })
	private List<CourseMemberInfo> getAssistants(Course course) throws Exception {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, course,
				CourseMemberType.ASSISTANT);
	}

	/**
	 * @see org.openuss.lecture.CourseService#getAspirants(org.openuss.lecture.Course)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List<CourseMemberInfo> handleGetAspirants(Course course) throws Exception {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, course,
				CourseMemberType.ASPIRANT);
	}

	/**
	 * @see org.openuss.lecture.CourseService#getParticipants(org.openuss.lecture.Course)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List<CourseMemberInfo> handleGetParticipants(Course course) throws Exception {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, course,
				CourseMemberType.PARTICIPANT);
	}

	/**
	 * @see org.openuss.lecture.CourseService#addAssistant(org.openuss.lecture.Course, org.openuss.security.User)
	 * @deprecated
	 */
	@SuppressWarnings( { "unchecked" })
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
		Map<String, String> parameters = new HashMap<String, String>();
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
		Validate.notNull(courseInfo, "CourseService.updateCourse - Parameter course must not be null.");
		Validate.notNull(courseInfo.getId(),
				"CourseService.updateCourse - Parameter course must contain a valid course id.");

		// Check changes of CourseType
		CourseType courseType = this.getCourseTypeDao().load(courseInfo.getCourseTypeId());
		Course courseOld = getCourseDao().load(courseInfo.getId());
		if (!courseOld.getCourseType().equals(courseType)) {
			throw new CourseServiceException("CourseService.updateCourse - The CourseType cannot be changed.");
		}

		// Check changes of Period
		Period period = this.getPeriodDao().load(courseInfo.getPeriodId());
		if (!courseOld.getPeriod().equals(period)) {
			throw new CourseServiceException("CourseService.updateCourse - The Period cannot be changed.");
		}

		// Transform VO to Entity
		Course courseEntity = getCourseDao().courseInfoToEntity(courseInfo);

		// Update Rights
		updateAccessTypePermission(courseEntity);

		// Update Course
		getCourseDao().update(courseEntity);

	}

	@SuppressWarnings("unchecked")
	@Override
	protected List handleFindAllCoursesByInstitute(Long instituteId) throws Exception {

		Validate.notNull(instituteId, "CourseService.findAllCoursesByInstitute -" + "instituteId cannot be null.");

		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute, "CourseService.findAllCoursesByInstitute - "
				+ "no institute could be found with the instituteId " + instituteId);

		List<CourseInfo> courses = new ArrayList<CourseInfo>();
		Iterator iter = institute.getCourseTypes().iterator();
		while (iter.hasNext()) {
			CourseType courseType = (CourseType) iter.next();
			courses.addAll(this.getCourseDao().findByCourseType(CourseDao.TRANSFORM_COURSEINFO, courseType));
			/*
			 * Iterator courseIter = courseType.getCourses().iterator(); while (courseIter.hasNext()) { Course course =
			 * (Course) courseIter.next(); courses.add(this.getCourseDao().toCourseInfo(course)); }
			 */
		}

		return courses;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List handleFindCoursesByPeriodAndInstitute(Long periodId, Long instituteId) throws Exception {

		Validate.notNull(periodId, "CourseService.findCoursesByPeriodAndInstitute -" + "periodId cannot be null.");
		Validate
				.notNull(instituteId, "CourseService.findCoursesByPeriodAndInstitute -" + "instituteId cannot be null.");

		Period period = this.getPeriodDao().load(periodId);
		Validate.notNull(period, "CourseService.findCoursesByPeriodAndInstitute -"
				+ "no period found with the corresponding periodId " + periodId);

		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute, "CourseService.findCoursesByPeriodAndInstitute -"
				+ "no institute found with the corresponding instiuteId " + instituteId);

		/*
		 * List<CourseInfo> courseInfos = new ArrayList<CourseInfo>(); List<CourseType> courseTypes =
		 * institute.getCourseTypes(); for (CourseType courseType : courseTypes) {
		 * courseInfos.addAll(this.getCourseDao(). findByPeriodAndCourseType(CourseDao.TRANSFORM_COURSEINFO, period,
		 * courseType)); }
		 */

		List<Course> allCourses = this.getCourseDao().findByPeriod(period);
		List<CourseInfo> courseInfos = new ArrayList<CourseInfo>();
		Iterator iter = allCourses.iterator();
		while (iter.hasNext()) {
			Course course = (Course) iter.next();
			if (ObjectUtils.equals(course.getCourseType().getInstitute().getId(),instituteId)) {
				courseInfos.add(this.getCourseDao().toCourseInfo(course));
			}

		}

		return courseInfos;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List handleFindCoursesByActivePeriods(InstituteInfo instituteInfo) throws Exception {

		Validate.notNull(instituteInfo, "CourseService.findCoursesByPeriodAndInstitute -"
				+ "instituteInfo cannot be null.");

		// Load entity
		Institute institute = this.getInstituteDao().instituteInfoToEntity(instituteInfo);
		Validate.notNull(institute, "CourseService.findCoursesByPeriodAndInstitute -"
				+ "instituteInfo cannot be transformed to institute.");
		Validate.notNull(institute.getDepartment(), "CourseService.findCoursesByPeriodAndInstitute -"
				+ "department cannot be null.");
		Validate.notNull(institute.getDepartment().getUniversity(), "CourseService.findCoursesByPeriodAndInstitute -"
				+ "university cannot be null.");

		List<Period> periods = this.getPeriodDao().findByUniversity(institute.getDepartment().getUniversity());
		List<CourseInfo> courses = new ArrayList<CourseInfo>();
		Iterator iter = periods.iterator();
		while (iter.hasNext()) {
			Period period = (Period) iter.next();
			if (period.isActive()) {
				Iterator courseIter = period.getCourses().iterator();
				while (courseIter.hasNext()) {
					courses.add(this.getCourseDao().toCourseInfo((Course) courseIter.next()));
				}
			}
		}
		return courses;
	}

	@Override
	protected void handleSetCourseStatus(Long courseId, boolean status) {
		Validate.notNull(courseId, "CourseService.setCourseStatus -" + "courseId cannot be null.");
		Validate.notNull(status, "CourseService.setCourseStatus -" + "status cannot be null.");

		Course course = this.getCourseDao().load(courseId);
		course.setEnabled(status);
		this.getCourseDao().update(course);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected List handleFindCoursesByActivePeriodsAndEnabled(Long instituteId, boolean enabled) throws Exception {

		Validate.notNull(instituteId, "CourseService.handleFindCoursesByActivePeriodsAndEnabled -"
				+ "instituteId cannot be null.");
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute, "CourseService.handleFindCoursesByActivePeriodsAndEnabled -"
				+ "instituteInfo cannot be transformed to institute.");
		Validate.notNull(institute.getDepartment(), "CourseService.handleFindCoursesByActivePeriodsAndEnabled -"
				+ "department cannot be null.");
		Validate.notNull(institute.getDepartment().getUniversity(),
				"CourseService.handleFindCoursesByActivePeriodsAndEnabled -" + "university cannot be null.");
		
		List<Course> allCoursesOfInstitute = new ArrayList<Course>();
		for (CourseType courseType : institute.getCourseTypes()) {
			allCoursesOfInstitute.addAll(courseType.getCourses());
		}
		
		List<CourseInfo> courses = new ArrayList<CourseInfo>();
		for (Course course : allCoursesOfInstitute) {
			if (course.getPeriod().isActive() && course.isEnabled() == enabled) {
				courses.add(this.getCourseDao().toCourseInfo(course));
			}
		}
		return courses;
		
	}

	@Override
	@SuppressWarnings("unchecked")
	protected List handleFindCoursesByInstituteAndEnabled(Long instituteId, boolean enabled) throws Exception {
		Validate.notNull(instituteId, "CourseService.findAllCoursesByInstitute -" + "instituteId cannot be null.");

		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute, "CourseService.findAllCoursesByInstitute - "
				+ "no institute could be found with the instituteId " + instituteId);

		List<CourseInfo> courses = new ArrayList<CourseInfo>();
		Iterator iter = institute.getCourseTypes().iterator();
		while (iter.hasNext()) {
			CourseType courseType = (CourseType) iter.next();
			Iterator courseIter = courseType.getCourses().iterator();
			while (courseIter.hasNext()) {
				Course course = (Course) courseIter.next();
				if (course.isEnabled() == enabled) {
					courses.add(this.getCourseDao().toCourseInfo(course));
				}
			}
		}
		return courses;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected List handleFindCoursesByPeriodAndInstituteAndEnabled(Long periodId, Long instituteId, boolean enabled)
			throws Exception {

		Validate.notNull(periodId, "CourseService.handleFindCoursesByPeriodAndInstituteAndEnabled -"
				+ "periodId cannot be null.");
		Validate.notNull(instituteId, "CourseService.handleFindCoursesByPeriodAndInstituteAndEnabled -"
				+ "instituteId cannot be null.");

		Period period = this.getPeriodDao().load(periodId);
		Validate.notNull(period, "CourseService.handleFindCoursesByPeriodAndInstituteAndEnabled -"
				+ "no period found with the corresponding periodId " + periodId);

		List<Course> allCourses = this.getCourseDao().findByPeriod(period);
		List<CourseInfo> courseInfos = new ArrayList<CourseInfo>();
		Iterator iter = allCourses.iterator();
		while (iter.hasNext()) {
			Course course = (Course) iter.next();
			if ((course.isEnabled() == enabled) && (ObjectUtils.equals(course.getCourseType().getInstitute().getId() , instituteId))) {
				courseInfos.add(this.getCourseDao().toCourseInfo(course));
			}

		}

		return courseInfos;
	}

	@Override
	protected void handleRegisterListener(LectureListener listener) throws Exception {
		// TODO Auto-generated method stub

	}

	/*------------------- private methods -------------------- */

	private void updateAccessTypePermission(Course course) {
		if (course.getAccessType() != AccessType.OPEN) {
			getSecurityService().setPermissions(Roles.USER, course, LectureAclEntry.NOTHING);
		} else if (course.getAccessType() == AccessType.OPEN) {
			getSecurityService().setPermissions(Roles.USER, course, LectureAclEntry.COURSE_PARTICIPANT);
		}
	}

	/**
	 * Convenience method for isNonExisting methods.<br/> Checks whether or not the found record is equal to self
	 * entry.
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