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
		if (course.getAccessType() == null) {
			course.setAccessType(AccessType.CLOSED);
		}
		course.setDocuments(true);
		course.setNewsletter(true);
		course.setDiscussion(true);

		// Add Course to CourseType and Period
		this.getCourseTypeDao().load(courseInfo.getCourseTypeId()).add(course);
		this.getPeriodDao().load(courseInfo.getPeriodId()).add(course);

		// Save Entity
		this.getCourseDao().create(course);
		Validate.notNull(course, "Id of course cannot be null.");

		// FIXME AOP2Events - Kai, Indexing should not base on VOs!
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

		getSecurityService().setPermissions(participantsGroup, course, LectureAclEntry.COURSE_PARTICIPANT);
		updateAccessTypePermission(course);

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

	@SuppressWarnings("unchecked")
	@Override
	protected List<CourseMemberInfo> handleGetParticipants(CourseInfo course) throws Exception {
		return getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO,
				getCourseDao().courseInfoToEntity(course), CourseMemberType.PARTICIPANT);
	}

	@Override
	protected CourseMemberInfo handleGetMemberInfo(CourseInfo courseInfo, UserInfo userInfo) throws Exception {
		CourseMemberPK pk = createMemberPk(retrieveCourse(courseInfo), retrieveUser(userInfo));
		return (CourseMemberInfo) getCourseMemberDao().load(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO,	pk);
	}

	@Override
	protected void handleAcceptAspirant(CourseMemberInfo memberInfo) throws Exception {
		CourseMemberPK pk = memberInfoToPK(memberInfo);
		
		CourseMember member = getCourseMemberDao().load(pk);
		if (member.getMemberType() == CourseMemberType.ASPIRANT) {
			member.setMemberType(CourseMemberType.PARTICIPANT);
			getCourseMemberDao().update(member);
			defineParticipantsPermission(member);
		
			Map<String, String> parameters = new HashMap<String, String>();
			
			Course course = courseOfPk(member.getCourseMemberPk());
			User user = userOfPk(member.getCourseMemberPk());
			UserInfo userInfo = new UserInfo();
			userInfo.setId(user.getId());
			
			parameters.put("coursename", "" + course.getName() + "(" + course.getShortcut() + ")");
			getMessageService().sendMessage(course.getName() + "(" + course.getShortcut() + ")",
					"course.application.subject", "courseapplicationapply", parameters, userInfo);
		}
	}

	/**
	 * Workaround for Hibernate composite limitation
	 * @param memberPk
	 * @return Course object
	 */
	private Course courseOfPk(CourseMemberPK memberPk) {
		return getCourseDao().load(memberPk.getCourse().getId());
	}
	
	/**
	 * Workaround for Hibernate composite limitation
	 * @param memberPk
	 * @return User Object
	 */
	private User userOfPk(CourseMemberPK memberPk) {
		return getSecurityService().getUserObject(memberPk.getUser().getId());
	}

	@Override
	protected void handleRemoveMember(CourseMemberInfo memberInfo) throws Exception {
		CourseMember member = getCourseMemberDao().load(memberInfoToPK(memberInfo));
		if (member != null && (member.getMemberType()==CourseMemberType.PARTICIPANT || member.getMemberType()==CourseMemberType.ASPIRANT)) {
			Course course = courseOfPk(member.getCourseMemberPk());
			User user = userOfPk(member.getCourseMemberPk());
			getSecurityService().removeAuthorityFromGroup(user, getParticipantsGroup(course));
			getCourseMemberDao().remove(member);

			// FIXME AOP2Events - Architecture Break - do not use dependencies from lecture/course to course/modules like discussion or newsletter, use events instead 
			getCourseNewsletterService().unsubscribe(getCourseDao().toCourseInfo(course), getSecurityService().getUser(user.getId()));
			getDiscussionService().removeForumWatch(getDiscussionService().getForum(getCourseDao().toCourseInfo(course)));
			
		}		
		getDesktopService2().unlinkCourse(getDesktopService2().findDesktopByUser(memberInfo.getUserId()).getId(), memberInfo.getCourseId());
		
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleRemoveAspirants(CourseInfo courseInfo) throws Exception {
		Course course = retrieveCourse(courseInfo);
		List<CourseMember> members = getCourseMemberDao().findByType(course, CourseMemberType.ASPIRANT);
		getCourseMemberDao().remove(members);
	}

	@Override
	protected void handleRejectAspirant(CourseMemberInfo memberInfo) throws Exception {
		CourseMember member = getCourseMemberDao().load(memberInfoToPK(memberInfo));
		if (member != null) {
			removeMember(memberInfo);
			Map<String, String> parameters = new HashMap<String, String>();
			
			Course course = courseOfPk(member.getCourseMemberPk());
			User user = userOfPk(member.getCourseMemberPk());
			UserInfo userInfo = new UserInfo();
			userInfo.setId(user.getId());
			
			parameters.put("coursename", "" + course.getName() + "(" + course.getShortcut() + ")");
			getMessageService().sendMessage(course.getName() + "(" + course.getShortcut() + ")",
					"course.application.subject", "courseapplicationreject", parameters, userInfo);
		}
	}

	@Override
	protected void handleApplyUser(CourseInfo courseInfo, UserInfo userInfo) throws Exception {
		handleApplyUser(courseInfo, userInfo, null);
	}

	@Override
	protected void handleApplyUser(CourseInfo courseInfo, UserInfo userInfo, String password) throws Exception {
		Course course = retrieveCourse(courseInfo);
		User user = retrieveUser(userInfo);
	
		if (course.getAccessType() == AccessType.OPEN) {
			addCourseMember(course, user, CourseMemberType.PARTICIPANT);
		} else if (course.getAccessType() == AccessType.PASSWORD ) {
			if (course.getAccessType() == AccessType.PASSWORD && course.isPasswordCorrect(password)) {
				addCourseMember(course, user, CourseMemberType.PARTICIPANT);
			} else {
				throw new CourseApplicationException("message_error_password_is_not_correct");
			}
		} else if (course.getAccessType() == AccessType.APPLICATION) {
			addCourseMember(course, user, CourseMemberType.ASPIRANT);
			sendApplicationNotificationToAssistants(course);
		} else {
			throw new CourseApplicationException("message_error_course_accesstype_is_not_application");
		}
	}

	@Override
	protected void handleAddAspirant(CourseInfo courseInfo, UserInfo userInfo) throws Exception {
		addCourseMember(retrieveCourse(courseInfo), retrieveUser(userInfo), CourseMemberType.ASPIRANT);		
	}

	@Override
	protected void handleAddAssistant(CourseInfo courseInfo, UserInfo userInfo) throws Exception {
		addCourseMember(retrieveCourse(courseInfo), retrieveUser(userInfo), CourseMemberType.ASSISTANT);
	}

	@Override
	protected void handleAddParticipant(CourseInfo courseInfo, UserInfo userInfo) throws Exception {
		addCourseMember(retrieveCourse(courseInfo), retrieveUser(userInfo), CourseMemberType.PARTICIPANT);
	}
	
	/**
	 * Create a course member and define needed permissions.
	 * @param course - Course entity
	 * @param user - User entity 
	 * @param type - CourseMemberType
	 * @return CourseMember entity
	 */
	private CourseMember addCourseMember(Course course, User user, CourseMemberType type) {
		CourseMember member = persistCourseMember(course, user, type);
		if (CourseMemberType.ASSISTANT == type) {
			CourseMemberPK pk = member.getCourseMemberPk();
			getSecurityService().setPermissions(userOfPk(pk), courseOfPk(pk), LectureAclEntry.INSTITUTE_TUTOR);
		} else if (CourseMemberType.PARTICIPANT == type) {
			defineParticipantsPermission(member);
		}
		return member;
	}

	/**
	 * Create a CourseMemberPK from Course and User entities
	 * @param course
	 * @param user
	 * @return CourseMemberPK
	 */
	private CourseMemberPK createMemberPk(Course course, User user) {
		CourseMemberPK pk = new CourseMemberPK();
		pk.setCourse(course);
		pk.setUser(user);
		return pk;
	}

	/**
	 * Define the permissions for a new participant.
	 * @param participant - CourseMember entity
	 */
	private void defineParticipantsPermission(CourseMember participant) {
		CourseMemberPK pk = participant.getCourseMemberPk();
		getSecurityService().addAuthorityToGroup(userOfPk(pk), getParticipantsGroup(courseOfPk(pk)));
		getSecurityService().saveUser(userOfPk(participant.getCourseMemberPk()));
	}

	/**
	 * Retrieve Group of Participants
	 * @param course - ntity of course
	 * @return Group if group exists or null
	 */
	private Group getParticipantsGroup(Course course) {
		Set<Group> groups = course.getGroups();
		for (Group group : groups) {
			if (group.getName().contains("PARTICIPANTS")) {
				return group;
			}
		}
		return null;
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

	/**
	 * Transform MemberInfo into CourseMemberPK
	 * @param memberInfo
	 * @return CourseMemberPK
	 */
	private CourseMemberPK memberInfoToPK(CourseMemberInfo memberInfo) {
		CourseMemberPK pk = new CourseMemberPK();
		Course course = new CourseImpl();
		course.setId(memberInfo.getCourseId());
		pk.setCourse(course);
		pk.setUser(User.Factory.newInstanceByIdentifier(memberInfo.getUserId()));
		return pk;
	}

	/**
	 * This method persist the course member based on user, course, and type.
	 * If the user is already a member of the course,  the membership will be updated. 
	 * Otherwise a new membership entity will be created.
	 * 
	 * @param user - User entity
	 * @param course - Course entity
	 * @param memberType - type of membership
	 * @return CourseMember 
	 */
	private CourseMember persistCourseMember(Course course, User user, CourseMemberType memberType) {
		CourseMember member = getCourseMemberDao().load(createMemberPk(course,user));
		if (member == null) {
			member = new CourseMemberImpl();
			member.setCourseMemberPk(createMemberPk(course,user));
			member.setMemberType(memberType);
			getCourseMemberDao().create(member);
		} else {
			member.setMemberType(memberType);
			getCourseMemberDao().update(member);
		}
		return member;
	}

	/**
	 * Convert CourseInfo into course entity.
	 * @param courseInfo
	 * @return Course 
	 */
	private Course retrieveCourse(CourseInfo courseInfo) {
		Validate.notNull(courseInfo, "Parameter courseInfo must not be null");
		Validate.notNull(courseInfo.getId(), "Parameter courseInfo must contain a valid id");
		Course course = getCourseDao().load(courseInfo.getId());
		if (course == null) {
			throw new CourseServiceException("course_not_found");
		}
		return course;
	}

	/**
	 * Convert UserInfo into user entity.
	 * @param userInfo
	 * @return User
	 */
	private User retrieveUser(UserInfo userInfo) {
		Validate.notNull(userInfo, "Parameter userInfo must not be null");
		Validate.notNull(userInfo.getId(), "Parameter userInfo must contain a valid id");
		User user = getSecurityService().getUserObject(userInfo);
		if (user == null) {
			throw new CourseServiceException("user_not_found");
		}
		return user;
	}


	@SuppressWarnings("unchecked")
	private void sendApplicationNotificationToAssistants(Course course) {
		List<CourseMemberInfo> assistants = getCourseMemberDao().findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, course, CourseMemberType.ASSISTANT);
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


	/**
	 * Update access type permission of course. After changing the access type of a course, 
	 * this method will update the permission settings of the course.  
	 * @param course - entity
	 */
	private void updateAccessTypePermission(Course course) {
		logger.debug("changing course " + course.getName() + " (" + course.getId() + ") to " + course.getAccessType());
		if (course.getAccessType() == AccessType.ANONYMOUS) {
			getSecurityService().setPermissions(Roles.ANONYMOUS, course, LectureAclEntry.READ);
		} else {
			getSecurityService().setPermissions(Roles.ANONYMOUS, course, LectureAclEntry.NOTHING);
		}
	}

	@Override
	protected void handleRemoveAssistant(CourseMemberInfo assistant) throws Exception {
		CourseMember member = getCourseMemberDao().load(memberInfoToPK(assistant));
		if (member != null && member.getMemberType()==CourseMemberType.ASSISTANT) {
			Course course = courseOfPk(member.getCourseMemberPk());
			User user = userOfPk(member.getCourseMemberPk());

			getSecurityService().removeAuthorityFromGroup(user, getParticipantsGroup(course));
			getCourseMemberDao().remove(member);
		}
		
	}

}