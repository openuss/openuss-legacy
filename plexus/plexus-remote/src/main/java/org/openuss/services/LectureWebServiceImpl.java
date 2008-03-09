package org.openuss.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.desktop.DesktopService2Exception;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.CourseMemberType;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.CourseServiceException;
import org.openuss.lecture.CourseTypeInfo;
import org.openuss.lecture.CourseTypeService;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.lecture.PeriodInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
import org.openuss.services.model.CourseBean;
import org.openuss.services.model.InstituteBean;
import org.openuss.services.model.Role;
import org.openuss.services.model.UserBean;

/**
 * This class communicates between the external web service interface an the
 * internal api-
 * 
 * @author Ingo Dueppe
 * 
 */
public class LectureWebServiceImpl implements LectureWebService {
	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(LectureWebServiceImpl.class);

	private SecurityService securityService;

	private CourseService courseService;
	private CourseTypeService courseTypeService;
	private InstituteService instituteService;
	private UniversityService universityService;
	private DepartmentService departmentService;

	@Override
	public Long createUser(final UserBean user) throws LectureLogicException {
		Validate.notNull(user, "User must not be null");
		UserInfo userInfo = new UserInfo();
		copyPropertiesFromUserBeanToUserInfo(user, userInfo);
		userInfo.setEnabled(true);
		userInfo = securityService.createUser(userInfo);
		return userInfo.getId();
	}

	@Override
	public boolean updateUser(final UserBean user) throws LectureLogicException {
		Validate.notNull(user, "Parameter user must not be null!");
		UserInfo userInfo = securityService.getUser(user.getId());
		copyPropertiesFromUserBeanToUserInfo(user, userInfo, false);
		securityService.saveUser(userInfo);
		return true;
	}

	@Override
	public boolean deleteUser(final long userId) throws LectureLogicException {
		return false;
	}

	@Override
	public Long findUser(final String username) throws LectureLogicException {
		UserInfo user = securityService.getUserByName(username);
		return (user == null) ? null : user.getId();
	}

	@Override
	public Long createCourse(final CourseBean course) throws LectureLogicException {
		Validate.notNull(course, "Parameter course must not be null!");

		PeriodInfo period = findBestFittingPeriod(course);

		CourseTypeInfo courseType = new CourseTypeInfo();
		courseType.setName(course.getName());
		courseType.setShortcut(course.getShortcut());
		courseType.setInstituteId(course.getInstituteId());

		courseType.setId(courseTypeService.create(courseType));

		CourseInfo courseInfo = new CourseInfo();
		courseInfo.setPeriodId(period.getId());
		courseInfo.setCourseTypeId(courseType.getId());

		courseInfo.setAccessType(AccessType.APPLICATION);
		courseInfo.setShortcut(course.getShortcut());
		courseInfo.setDescription(course.getDescription());

		return courseService.create(courseInfo);
	}

	@Override
	public boolean deleteCourse(final long courseId) throws LectureLogicException {
		try {
			courseService.removeCourse(courseId);
			return true;
		} catch (DesktopService2Exception ex) {
			logger.debug(ex);
		} catch (CourseServiceException ex) {
			logger.debug(ex);
		}
		return false;
	}

	@Override
	public boolean updateCourse(final CourseBean course) throws LectureLogicException {
		Validate.notNull(course, "Parameter course must not be null.");
		Validate.notNull(course.getId(), "Parameter course.id must not be null");

		CourseInfo courseInfo = courseService.findCourse(course.getId());

		if (StringUtils.isNotBlank(course.getShortcut())) {
			courseInfo.setShortcut(course.getShortcut());
		}

		if (!ObjectUtils.equals(course.getInstituteId(), courseInfo.getInstituteId())) {
			throw new LectureLogicException("Cannot change institute of course!");
		}

		course.setInstituteId(courseInfo.getInstituteId());
		PeriodInfo period = findBestFittingPeriod(course);
		courseInfo.setPeriodId(period.getId());
		courseService.updateCourse(courseInfo);

		CourseTypeInfo courseTypeInfo = courseTypeService.findCourseType(courseInfo.getCourseTypeId());
		if (StringUtils.isNotBlank(course.getName())) {
			courseTypeInfo.setName(course.getName());
		}
		if (StringUtils.isNotBlank(course.getShortcut())) {
			courseTypeInfo.setShortcut(course.getShortcut());
		}
		courseTypeInfo.setDescription(course.getDescription());

		courseTypeService.update(courseTypeInfo);

		return true;
	}

	@Override
	public CourseBean getCourse(final long courseId) throws LectureLogicException {
		Validate.notNull(courseId, "Parameter courseId must not be null.");

		CourseInfo courseInfo = courseService.getCourseInfo(courseId);

		CourseBean course = null;
		if (courseInfo != null) {
			course = new CourseBean();
			course.setId(courseInfo.getId());
			course.setName(courseInfo.getName());
			course.setShortcut(courseInfo.getShortcut());
			course.setDescription(courseInfo.getDescription());
		}

		return course;
	}

	@Override
	public boolean assignCourseMember(final long courseId, final long userId, final Role role)
			throws LectureLogicException {
		Validate.notNull(role, "Parameter role must not be null.");
		UserInfo user = securityService.getUser(userId);
		Validate.notNull(user, "User not found.");
		CourseInfo course = courseService.getCourseInfo(courseId);
		Validate.notNull(course, "Course not found.");

		if (role == Role.ASSISTANT) {
			courseService.addAssistant(course, user);
		} else {
			courseService.addParticipant(course, user);
		}

		return true;
	}

	@Override
	public boolean removeCourseMember(final long courseId, final long userId) throws LectureLogicException {
		UserInfo user = securityService.getUser(userId);
		Validate.notNull(user, "User not found.");
		CourseInfo course = courseService.getCourseInfo(courseId);
		Validate.notNull(course, "Course not found.");

		CourseMemberInfo member = courseService.getMemberInfo(course, user);
		if (member != null) {
			courseService.removeMember(member.getId());
		}
		return member != null;
	}

	@Override
	public Role isCourseMember(final long courseId, final long userId) throws LectureLogicException {
		UserInfo user = securityService.getUser(userId);
		CourseInfo course = courseService.getCourseInfo(courseId);

		CourseMemberInfo member = courseService.getMemberInfo(course, user);

		Role result;

		if (member == null) {
			result = Role.NONE;
		} else if (member.getMemberType() == CourseMemberType.ASSISTANT) {
			result = Role.ASSISTANT;
		} else {
			result = Role.PARTICIPANT;
		}
		return result;
	}

	@Override
	public boolean updateInstitute(final InstituteBean institute) throws LectureLogicException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Long createInstitute(final InstituteBean institute) throws LectureLogicException {

		InstituteInfo info = new InstituteInfo();
		copyProperties(institute, info);

		return instituteService.create(info, institute.getOwnerId());
	}

	@Override
	public InstituteBean getInstitute(final long instituteId) throws LectureLogicException {
		InstituteInfo source = instituteService.findInstitute(instituteId);
		if (source == null) {
			throw new LectureLogicException("Institute (" + instituteId + ") does not exists.");
		}
		InstituteBean target = new InstituteBean();
		copyProperties(source, target);
		return target;
	}

	@Override
	public List<InstituteBean> listInstitute(final long departmentId) throws LectureLogicException {
		List<InstituteInfo> institutes = instituteService.findInstitutesByDepartment(departmentId);
		List<InstituteBean> beans = new ArrayList(institutes.size());
		for (InstituteInfo institute : institutes) {
			InstituteBean bean = new InstituteBean();
			try {
				copyProperties(institute, bean);
				beans.add(bean);
			} catch (LectureLogicException e) {
				logger.error(e);
			}
		}
		return beans;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(final SecurityService securityService) {
		this.securityService = securityService;
	}

	public CourseTypeService getCourseTypeService() {
		return courseTypeService;
	}

	public void setCourseTypeService(final CourseTypeService courseTypeService) {
		this.courseTypeService = courseTypeService;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(final CourseService courseService) {
		this.courseService = courseService;
	}

	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(final InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public UniversityService getUniversityService() {
		return universityService;
	}

	public void setUniversityService(final UniversityService universityService) {
		this.universityService = universityService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	// --------------------- Private Methods ------------- //
	private boolean containPeriodCourseTimeFrame(final CourseBean course, final PeriodInfo period) {
		return (!period.getStartdate().after(course.getStartDate()) && !period.getEnddate().before(course.getEndDate()));
	}

	private void copyProperties(final Object source, final Object target) throws LectureLogicException {
		try {
			BeanUtils.copyProperties(target, source);
		} catch (IllegalAccessException e) {
			logger.error(e);
			throw new LectureLogicException(e);
		} catch (InvocationTargetException e) {
			logger.error(e);
			throw new LectureLogicException(e);
		}
	}

	private void copyPropertiesFromUserBeanToUserInfo(final UserBean user, final UserInfo userInfo) {
		copyPropertiesFromUserBeanToUserInfo(user, userInfo, false);
	}

	private void copyPropertiesFromUserBeanToUserInfo(final UserBean user, final UserInfo userInfo,
			final boolean copyIfNull) { // NOPMD by idueppe on 09.03.08 00:09
		if (copyIfNull || user.getUsername() != null) {
			userInfo.setUsername(user.getUsername());
		}
		if (copyIfNull || user.getEmail() != null) {
			userInfo.setEmail(user.getEmail());
		}
		if (copyIfNull || user.getPassword() != null) {
			userInfo.setPassword(user.getPassword());
		}
		if (copyIfNull || user.getFirstName() != null) {
			userInfo.setFirstName(user.getFirstName());
		}
		if (copyIfNull || user.getLastName() != null) {
			userInfo.setLastName(user.getLastName());
		}
		if (copyIfNull || user.getAddress() != null) {
			userInfo.setAddress(user.getAddress());
		}
		if (copyIfNull || user.getPostCode() != null) {
			userInfo.setPostcode(user.getPostCode());
		}
		if (copyIfNull || user.getCity() != null) {
			userInfo.setCity(user.getCity());
		}
		if (copyIfNull || user.getMatriculation() != null) {
			userInfo.setMatriculation(user.getMatriculation());
		}
		if (copyIfNull || user.getLocale() != null) {
			userInfo.setLocale(user.getLocale());
		}
		if (copyIfNull || user.getTitle() != null) {
			userInfo.setTitle(user.getTitle());
		}
	}

	private boolean isFoundBiggerThenPeriod(final PeriodInfo found, final PeriodInfo period) {
		return found == null
				|| (found.getStartdate().before(period.getStartdate()) && found.getEnddate().after(period.getEnddate()));
	}

	private PeriodInfo findBestFittingPeriod(final CourseBean course) throws LectureLogicException {
		List<PeriodInfo> periods = findPeriodsByCourse(course);

		checkCourseEndDate(course);

		// Select first entry
		PeriodInfo found = null;
		for (PeriodInfo period : periods) {
			if (containPeriodCourseTimeFrame(course, period) && isFoundBiggerThenPeriod(found, period)) {
				found = period;
			}
		}
		if (found == null) {
			throw new LectureLogicException("Now fitting period found, check the start and end date values.");
		}

		return found;
	}

	/**
	 * Check if the course enddate is not null and after startdate
	 * 
	 * @param course
	 */
	private void checkCourseEndDate(final CourseBean course) {
		Validate.notNull(course.getStartDate(), "Parameter course must contain a valid start date.");

		if (course.getEndDate() == null || course.getStartDate().before(course.getEndDate())) {
			course.setEndDate(course.getStartDate());
		}
	}

	/**
	 * Lookups the periods of the university to that the given course belongs
	 * too.
	 * 
	 * @param course
	 * @return List<PeriodInfo>
	 */
	private List<PeriodInfo> findPeriodsByCourse(final CourseBean course) {
		InstituteInfo institute = instituteService.findInstitute(course.getInstituteId());
		Validate.notNull(institute, "Couldn't find an institute with the id " + course.getInstituteId());
		DepartmentInfo department = departmentService.findDepartment(institute.getDepartmentId());
		List<PeriodInfo> periods = universityService.findPeriodsByUniversity(department.getUniversityId());
		Validate.notEmpty(periods, "Couldn't find any periods for the course, check institute id's.");
		return periods;
	}

}
