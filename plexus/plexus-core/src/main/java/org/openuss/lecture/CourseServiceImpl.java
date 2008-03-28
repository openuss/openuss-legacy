// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.lecture.events.CourseCreatedEvent;
import org.openuss.lecture.events.CourseRemoveEvent;
import org.openuss.lecture.events.CourseUpdateEvent;
import org.openuss.security.Group;
import org.openuss.security.GroupType;
import org.openuss.security.Roles;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.system.SystemProperties;

/**
 * @see org.openuss.lecture.CourseService
 */
public class CourseServiceImpl extends CourseServiceBase {

	private static final Logger logger = Logger.getLogger(CourseServiceImpl.class);

	/**
	 * @see org.openuss.lecture.CourseService#create(org.openuss.lecture.CourseInfo)
	 */
	protected Long handleCreate(CourseInfo courseInfo) {

		Validate.notNull(courseInfo, "CourseInfo cannot be null.");
		Validate.notNull(courseInfo.getCourseTypeId(), "GetCourseTypeId cannot be null.");
		Validate.notNull(courseInfo.getPeriodId(), "PeriodId cannot be null.");

	
		// Transform VO to entity
		Course course = this.getCourseDao().courseInfoToEntity(courseInfo);
		Validate.notNull(course, "Cannot transform courseInfo to entity.");
		course.setEnabled(true);
		
		// Define default values
		course.setAccessType(AccessType.CLOSED);
		course.setDocuments(true);
		course.setNewsletter(true);
		course.setDiscussion(true);

		// Add Course to CourseType and Period
		this.getCourseTypeDao().load(courseInfo.getCourseTypeId()).add(course);
		this.getPeriodDao().load(courseInfo.getPeriodId()).add(course);

		// Save Entity
		this.getCourseDao().create(course);
		Validate.notNull(course, "Id of course cannot be null.");

		// FIXME - Kai, Indexing should not base on VOs!
		// Kai: Do not delete this!!! Set id of institute VO for indexing
		// Update input parameter for aspects to get the right domain objects.
		courseInfo.setId(course.getId());

		// Set Security
		this.getSecurityService().createObjectIdentity(course, course.getCourseType());

		// Create default Group for Course
		Group participantsGroup = getSecurityService().createGroup("COURSE_" + course.getId() + "_PARTICIPANTS",
				"autogroup_participant_label", null, GroupType.PARTICIPANT);
		Set<Group> groups = course.getGroups();
		if (groups == null) {
			groups = new HashSet<Group>();
		}
		groups.add(participantsGroup);
		course.setGroups(groups);
		getCourseDao().update(course);

		defineCourseSecuritySettings(course, participantsGroup);

		getEventPublisher().publishEvent(new CourseCreatedEvent(course));

		return course.getId();
	}

	/**
	 * @see org.openuss.lecture.CourseService#create(org.openuss.lecture.CourseInfo)
	 */
	@SuppressWarnings("unchecked")
	protected void handleRemoveCourse(Long courseId) throws Exception {
		Validate.notNull(courseId, "CourseId cannot be null.");
		Course course = (Course) this.getCourseDao().load(courseId);

		if (course == null) {
			throw new CourseServiceException("No course entity found with the corresponding courseId " + courseId);
		}

		getEventPublisher().publishEvent(new CourseRemoveEvent(course));

		// Remove Security
		this.getSecurityService().removeAllPermissions(course);
		this.getSecurityService().removeObjectIdentity(course);

		// Remove security group
		Set<Group> courseGroups = course.getGroups();
		for (Group courseGroup : courseGroups) {
			getSecurityService().removeGroup(courseGroup);
		}

		// Remove CourseMembers
		List<CourseMember> courseMembers = getCourseMemberDao().findByCourse(course);
		if (!(courseMembers == null || courseMembers.size() == 0)) {
			getCourseMemberDao().remove(courseMembers);
		}

		// Remove Course
		course.getCourseType().remove(course);
		course.getPeriod().remove(course);
		this.getCourseDao().remove(courseId);
	}

	@Override
	protected void handleUpdateCourse(CourseInfo courseInfo) throws Exception {
		logger.debug("Starting method handleUpdateCourse");
		Validate.notNull(courseInfo, "Parameter course must not be null.");
		Validate.notNull(courseInfo.getId(), "Parameter course must contain a valid course id.");

		//check changes of access type
		CourseInfo courseOld = findCourse(courseInfo.getId());
		if (courseOld.getAccessType() == AccessType.APPLICATION && courseInfo.getAccessType() != AccessType.APPLICATION) {
			removeAspirants(courseOld);
		}

		
		// Check changes of CourseType
		CourseType courseType = this.getCourseTypeDao().load(courseInfo.getCourseTypeId());
		if (!courseOld.getCourseTypeId().equals(courseType.getId())) {
			throw new CourseServiceException("The CourseType cannot be changed.");
		}

		// Transform VO to Entity
		Course course = getCourseDao().courseInfoToEntity(courseInfo);
		// Update Rights
		updateAccessTypePermission(course);
		// Update Course
		getCourseDao().update(course);

		getEventPublisher().publishEvent(new CourseUpdateEvent(course));
	}

	@Override
	protected void handleSetCourseStatus(Long courseId, boolean status) throws CourseApplicationException {
		Validate.notNull(courseId, "CourseId cannot be null.");
		Validate.notNull(status, "Status cannot be null.");

		Course course = this.getCourseDao().load(courseId);
		if (course == null) {
			throw new CourseApplicationException("Course " + courseId + " not found!");
		}
		course.setEnabled(status);
		this.getCourseDao().update(course);

		getEventPublisher().publishEvent(new CourseUpdateEvent(course));
	}

	protected CourseInfo handleFindCourse(Long courseId) {
		Validate.notNull(courseId, "CourseId cannot be null.");
		return (CourseInfo) this.getCourseDao().load(CourseDao.TRANSFORM_COURSEINFO, courseId);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List handleFindAllCoursesByInstitute(Long instituteId) throws Exception {
		Validate.notNull(instituteId, "InstituteId cannot be null.");
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute, "No institute could be found with the instituteId " + instituteId);

		List<CourseInfo> courses = new ArrayList<CourseInfo>();
		Iterator iter = institute.getCourseTypes().iterator();
		while (iter.hasNext()) {
			CourseType courseType = (CourseType) iter.next();
			courses.addAll(this.getCourseDao().findByCourseType(CourseDao.TRANSFORM_COURSEINFO, courseType));
			/*
			 * Iterator courseIter = courseType.getCourses().iterator(); while
			 * (courseIter.hasNext()) { Course course = (Course)
			 * courseIter.next();
			 * courses.add(this.getCourseDao().toCourseInfo(course)); }
			 */
		}

		return courses;
	}

	@SuppressWarnings( { "unchecked" })
	protected List handleFindCoursesByCourseType(Long courseTypeId) {
		Validate.notNull(courseTypeId, "CourseTypeId cannot be null.");
		CourseType courseType = this.getCourseTypeDao().load(courseTypeId);
		return this.getCourseDao().findByCourseType(CourseDao.TRANSFORM_COURSEINFO, courseType);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List handleFindCoursesByPeriodAndInstitute(Long periodId, Long instituteId) throws Exception {
		Validate.notNull(periodId, "PeriodId cannot be null.");
		Validate.notNull(instituteId, "InstituteId cannot be null.");

		Period period = this.getPeriodDao().load(periodId);
		Validate.notNull(period, "No period found with the corresponding periodId " + periodId);

		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute, "No institute found with the corresponding instiuteId " + instituteId);

		/*
		 * List<CourseInfo> courseInfos = new ArrayList<CourseInfo>(); List<CourseType>
		 * courseTypes = institute.getCourseTypes(); for (CourseType courseType :
		 * courseTypes) { courseInfos.addAll(this.getCourseDao().
		 * findByPeriodAndCourseType(CourseDao.TRANSFORM_COURSEINFO, period,
		 * courseType)); }
		 */

		List<Course> allCourses = this.getCourseDao().findByPeriod(period);
		List<CourseInfo> courseInfos = new ArrayList<CourseInfo>();
		Iterator iter = allCourses.iterator();
		while (iter.hasNext()) {
			Course course = (Course) iter.next();
			if (ObjectUtils.equals(course.getCourseType().getInstitute().getId(), instituteId)) {
				courseInfos.add(this.getCourseDao().toCourseInfo(course));
			}
		}

		return courseInfos;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List handleFindCoursesByActivePeriods(InstituteInfo instituteInfo) throws Exception {
		Validate.notNull(instituteInfo, "InstituteInfo cannot be null.");

		// Load entity
		Institute institute = this.getInstituteDao().instituteInfoToEntity(instituteInfo);
		Validate.notNull(institute, "InstituteInfo cannot be transformed to institute.");
		Validate.notNull(institute.getDepartment(), "Department cannot be null.");
		Validate.notNull(institute.getDepartment().getUniversity(), "University cannot be null.");

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
	@SuppressWarnings("unchecked")
	protected List handleFindCoursesByActivePeriodsAndEnabled(Long instituteId, boolean enabled) throws Exception {
		Validate.notNull(instituteId, "InstituteId cannot be null.");
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute, "InstituteInfo cannot be transformed to institute.");
		Validate.notNull(institute.getDepartment(), "Department cannot be null.");
		Validate.notNull(institute.getDepartment().getUniversity(), "University cannot be null.");

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
		Validate.notNull(instituteId, "InstituteId cannot be null.");

		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute, "No institute could be found with the instituteId " + instituteId);

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
		Validate.notNull(periodId, "PeriodId cannot be null.");
		Validate.notNull(instituteId, "InstituteId cannot be null.");

		Period period = this.getPeriodDao().load(periodId);
		Validate.notNull(period, "No period found with the corresponding periodId " + periodId);

		List<Course> allCourses = this.getCourseDao().findByPeriod(period);
		List<CourseInfo> courseInfos = new ArrayList<CourseInfo>();
		Iterator iter = allCourses.iterator();
		while (iter.hasNext()) {
			Course course = (Course) iter.next();
			if ((course.isEnabled() == enabled)
					&& (ObjectUtils.equals(course.getCourseType().getInstitute().getId(), instituteId))) {
				courseInfos.add(this.getCourseDao().toCourseInfo(course));
			}
		}

		return courseInfos;
	}

	@Override
	protected boolean handleIsNoneExistingCourseShortcut(CourseInfo self, String shortcut) {
		logger.error("IsNoneExistingCourseShortcut is called!");
		Course found = getCourseDao().findByShortcut(shortcut);
		CourseInfo foundInfo = null;
		if (found != null) {
			foundInfo = this.getCourseDao().toCourseInfo(found);
		}
		return isEqualOrNull(self, foundInfo);
	}

	/**
	 * @see org.openuss.lecture.CourseService#getAspirants(org.openuss.lecture.Course)
	 */
	@SuppressWarnings("unchecked")
	protected List<CourseMemberInfo> handleGetAspirants(final Course course) {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, course,
				CourseMemberType.ASPIRANT);
	}

	/**
	 * @see org.openuss.lecture.CourseService#getParticipants(org.openuss.lecture.Course)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List<CourseMemberInfo> handleGetParticipants(Course course) {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, course,
				CourseMemberType.PARTICIPANT);
	}

	@Override
	protected void handleAcceptAspirant(CourseMemberInfo memberInfo) throws Exception {
		CourseMemberPK pk = memberInfoToPK(memberInfo);
		
		
		CourseMember member = getCourseMemberDao().load(pk);
		if (member.getMemberType() == CourseMemberType.ASPIRANT) {
			persistParticipantWithPermissions(member);
		}
		Map<String, String> parameters = new HashMap<String, String>();
		
		Course course = member.getCourseMemberPk().getCourse();
		User user = member.getCourseMemberPk().getUser();
		
		parameters.put("coursename", "" + course.getName() + "(" + course.getShortcut() + ")");
		getMessageService().sendMessage(course.getName() + "(" + course.getShortcut() + ")",
				"course.application.subject", "courseapplicationapply", parameters,
				getSecurityService().getUser(user.getId()));
	}

	private CourseMemberPK memberInfoToPK(CourseMemberInfo memberInfo) {
		CourseMemberPK pk = new CourseMemberPK();
		pk.setCourse(Course.Factory.newInstance(memberInfo.getCourseId()));
		pk.setUser(User.Factory.newInstance(memberInfo.getUserId()));
		return pk;
	}

	@Override
	protected void handleRemoveMember(CourseMemberInfo memberInfo) throws Exception {
		CourseMember member = getCourseMemberDao().load(memberInfoToPK(memberInfo));
		if (member != null) {
			Course course = member.getCourseMemberPk().getCourse();
			User user = member.getCourseMemberPk().getUser();

			getSecurityService().removeAuthorityFromGroup(user, getParticipantsGroup(course));
			getCourseMemberDao().remove(member);
		}
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
				getCourseMemberDao().remove(member);
			}
		}
	}

	@Override
	protected void handleRejectAspirant(CourseMemberInfo memberInfo) throws Exception {
		CourseMember member = getCourseMemberDao().load(memberInfoToPK(memberInfo));
		if (member != null) {
			removeMember(memberInfo);
			Map<String, String> parameters = new HashMap<String, String>();
			
			Course course = member.getCourseMemberPk().getCourse();
			User user = member.getCourseMemberPk().getUser();
			
			parameters.put("coursename", "" + course.getName() + "(" + course.getShortcut() + ")");
			getMessageService().sendMessage(course.getName() + "(" + course.getShortcut() + ")",
					"course.application.subject", "courseapplicationreject", parameters,
					getSecurityService().getUser(user.getId()));
		}
	}

	@Override
	protected void handleAddAspirant(CourseInfo course, UserInfo user) throws Exception {
		CourseMember aspirant = retrieveCourseMember(getCourseDao().courseInfoToEntity(course), getSecurityService()
				.getUserObject(user));
		aspirant.setMemberType(CourseMemberType.ASPIRANT);
		getCourseMemberDao().create(aspirant);
	}

	@Override
	protected void handleAddAssistant(CourseInfo courseInfo, UserInfo userInfo) throws Exception {
		Validate.notNull(courseInfo, "Parameter course must not be null");
		Validate.notNull(userInfo, "Parameter user must not be null");
		User user = getSecurityService().getUserObject(userInfo);
		Course course = getCourseDao().load(courseInfo.getId());

		if (course == null || user == null) {
			throw new CourseServiceException("course_user_not_found");
		}
		
		CourseMember assistant = retrieveCourseMember(course, user);
		assistant.setMemberType(CourseMemberType.ASSISTANT);
		
		getCourseMemberDao().create(assistant);
		
		course = assistant.getCourseMemberPk().getCourse();
		user = assistant.getCourseMemberPk().getUser();
		
		getSecurityService().setPermissions(user, course, LectureAclEntry.INSTITUTE_TUTOR);
	}

	@Override
	protected void handleAddParticipant(CourseInfo course, UserInfo user) throws Exception {
		CourseMember participant = retrieveCourseMember(getCourseDao().courseInfoToEntity(course), getSecurityService()
				.getUserObject(user));
		persistParticipantWithPermissions(participant);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleApplyUser(Long courseId, Long userId) throws Exception {
		handleApplyUser(courseId, userId, null);
	}

	@Override
	protected void handleApplyUser(Long courseId, Long userId, String password) throws Exception {
		Validate.notNull(courseId, "Parameter courseId must not be null");
		Validate.notNull(userId, "Parameter userId must not be null");
		
		Course course = getCourseDao().load(courseId);
		User user = getSecurityService().getUserObject(userId);
		
		if (course == null || user == null) {
			throw new CourseServiceException("Course not found with id "+courseId);
		}
		
		if (course.getAccessType() == AccessType.OPEN) {
			addParticipant(course, user);
		} else if (course.getAccessType() == AccessType.PASSWORD ) {
			if (course.getAccessType() == AccessType.PASSWORD && course.isPasswordCorrect(password)) {
				addParticipant(course, user);
			} else {
				throw new CourseApplicationException("message_error_password_is_not_correct");
			}
		} else if (course.getAccessType() == AccessType.APPLICATION) {
			sendApplicationNotificationToAssistants(course);
			addAspirant(course, user);
		} else {
			throw new CourseApplicationException("message_error_course_accesstype_is_not_application");
		}

	}

	private void sendApplicationNotificationToAssistants(Course course) {
		List<CourseMemberInfo> assistants = getAssistants(course);
		List<User> recipients = new ArrayList<User>();
		if (assistants != null && !assistants.isEmpty()) {
			for (CourseMemberInfo member : assistants) {
				recipients.add(getSecurityService().getUserObject(member.getUserId()));
			}
			String link = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue();
			link += "/views/secured/course/courseaspirants.faces?course=" + course.getId();
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("coursename", course.getName() + "(" + course.getShortcut() + ")");
			parameters.put("courseapplicantlink", link);
			getMessageService().sendMessage(course.getName(), "course.application.subject", "courseapplication", parameters, recipients);
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
	protected CourseMemberInfo handleGetMemberInfo(CourseInfo courseInfo, UserInfo userInfo) throws Exception {
		Course course = getCourseDao().courseInfoToEntity(courseInfo);
		User user = getSecurityService().getUserObject(userInfo);
		
		CourseMemberPK pk = createPK(course, user);
		
		
		return (CourseMemberInfo) getCourseMemberDao().load(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO,	pk);
	}

	private CourseMemberPK createPK(Course course, User user) {
		CourseMemberPK pk = new CourseMemberPK();
		pk.setCourse(course);
		pk.setUser(user);
		return pk;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<CourseMemberInfo> handleGetParticipants(CourseInfo course) throws Exception {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO,
				getCourseDao().courseInfoToEntity(course), CourseMemberType.PARTICIPANT);
	}

	/**
	 * Add aspirant to course
	 */
	private void addAspirant(Course course, User user) throws Exception {
		CourseMember aspirant = retrieveCourseMember(course, user);
		aspirant.setMemberType(CourseMemberType.ASPIRANT);
		persistCourseMember(aspirant);
	}

	/**
	 * Add participant to course
	 */
	private void addParticipant(Course course, User user) throws Exception {
		CourseMember participant = retrieveCourseMember(course, user);
		persistParticipantWithPermissions(participant);
	}

	private void defineCourseSecuritySettings(Course courseEntity, Group participantsGroup) {
		getSecurityService().setPermissions(participantsGroup, courseEntity, LectureAclEntry.COURSE_PARTICIPANT);
		updateAccessTypePermission(courseEntity);
	}

	/**
	 * @see org.openuss.lecture.CourseService#getAssistants(org.openuss.lecture.Course)
	 */
	@SuppressWarnings("unchecked")
	private List<CourseMemberInfo> getAssistants(final Course course) {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, course, CourseMemberType.ASSISTANT);
	}

	private Group getParticipantsGroup(Course course) {
		Set<Group> groups = course.getGroups();
		for (Group group : groups) {
			if (group.getName().contains("PARTICIPANTS")) {
				return group;
			}
		}
		return null;
	}

	private void persistCourseMember(CourseMember member) {
		if (member.getCourseMemberPk() == null) {
			getCourseMemberDao().create(member);
		} else {
			getCourseMemberDao().update(member);
		}
	}

	private void persistParticipantWithPermissions(CourseMember participant) {
		participant.setMemberType(CourseMemberType.PARTICIPANT);
		persistCourseMember(participant);
		getSecurityService().addAuthorityToGroup(participant.getCourseMemberPk().getUser(), getParticipantsGroup(participant.getCourseMemberPk().getCourse()));
		getSecurityService().saveUser(participant.getCourseMemberPk().getUser());
	}

	private CourseMember retrieveCourseMember(Course course, User user) {
		CourseMember member = getCourseMemberDao().load(createPK(course,user));
		if (member == null) {
			member = CourseMember.Factory.newInstance();
			course = getCourseDao().load(course.getId());
			UserInfo userInfo = new UserInfo();
			userInfo.setId(user.getId());
			user = getSecurityService().getUserObject(userInfo);
			member.setCourseMemberPk(createPK(course,user));
		}
		return member;
	}

	private void updateAccessTypePermission(Course course) {
		logger.debug("changing course " + course.getName() + " (" + course.getId() + ") to " + course.getAccessType());
		Group group = getParticipantsGroup(course);
		if (course.getAccessType() == AccessType.ANONYMOUS) {
			getSecurityService().setPermissions(Roles.ANONYMOUS, course, LectureAclEntry.READ);
		} else {
			getSecurityService().setPermissions(Roles.ANONYMOUS, course, LectureAclEntry.NOTHING);
		}

		if (course.getAccessType() == AccessType.OPEN || course.getAccessType() == AccessType.ANONYMOUS) {
			getSecurityService().addAuthorityToGroup(Roles.USER, group);
		} else {
			getSecurityService().removeAuthorityFromGroup(Roles.USER, group);
		}
	}

	/**
	 * Convenience method for isNonExisting methods.<br/> Checks whether or not
	 * the found record is equal to self entry.
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

	@SuppressWarnings("unchecked")
	@Override
	protected List handleFindAllCoursesByDepartment(Long departmentId,
			Boolean onlyActive, Boolean onlyEnabled) throws Exception {
		Validate.notNull(departmentId, "departmentId cannot be null.");
		Validate.notNull(onlyActive, "onlyActive cannot be null");
		Validate.notNull(onlyEnabled, "onlyEnabled cannot be null.");
		final Department department = getDepartmentDao().load(departmentId);
		Validate.notNull(department, "No department could be found with the departmentId " + departmentId);

		final List<CourseInfo> courseList = new ArrayList<CourseInfo>();
		final List<InstituteInfo> instituteInfos = new ArrayList<InstituteInfo>();
		for (Institute institute : department.getInstitutes()) {
			instituteInfos.add(this.getInstituteDao().toInstituteInfo(institute));
		}
		
		if(onlyActive){
			for(InstituteInfo institute : instituteInfos) {
				courseList.addAll(this.findCoursesByActivePeriodsAndEnabled(institute.getId(), onlyEnabled));
			}
		}else{
			for(InstituteInfo institute : instituteInfos) {
				courseList.addAll(this.findCoursesByInstituteAndEnabled(institute.getId(), onlyEnabled));
			}
		}
		return courseList;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List handleFindAllCoursesByDepartmentAndPeriod(Long departmentId,
			Long periodId, Boolean onlyEnabled) throws Exception {
		Validate.notNull(onlyEnabled, "enabled cannot be null.");
		Validate.notNull(periodId, "periodId cannot be null.");
		Validate.notNull(departmentId, "departmentId cannot be null.");
		final Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department, "No department could be found with the departmentId " + departmentId);

		final List<CourseInfo> courseList = new ArrayList<CourseInfo>();
		final List<InstituteInfo> instituteInfos = new ArrayList<InstituteInfo>();
		for (Institute institute : department.getInstitutes()) {
			instituteInfos.add(this.getInstituteDao().toInstituteInfo(institute));
		}
		
		for(InstituteInfo institute : instituteInfos){	
			courseList.addAll(this.findCoursesByPeriodAndInstituteAndEnabled(periodId, institute.getId(), onlyEnabled));
		}
		
		return courseList;
	}
}