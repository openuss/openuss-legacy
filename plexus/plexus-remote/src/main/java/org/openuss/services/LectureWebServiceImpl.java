package org.openuss.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
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
	public Long createUser(UserBean user) throws LectureLogicException {
		try {
			Validate.notNull(user, "User must not be null");
			UserInfo userInfo = new UserInfo();
			copyPropertiesFromUserBeanToUserInfo(user, userInfo);
			userInfo.setEnabled(true);
			userInfo = securityService.createUser(userInfo);
			return userInfo.getId();
		} catch (RuntimeException ex) {
			throw new LectureLogicException(ex.getMessage(), ex);
		}
	}

	@Override
	public boolean updateUser(UserBean user) throws LectureLogicException {
		try {
			Validate.notNull(user, "Parameter user must not be null!");
			UserInfo userInfo = securityService.getUser(user.getId());
			copyPropertiesFromUserBeanToUserInfo(user, userInfo, false);
			securityService.saveUser(userInfo);
			return true;
		} catch (IllegalArgumentException ex) {
			throw new LectureLogicException(ex.getMessage(), ex);
		}

	}

	@Override
	public boolean deleteUser(long userId) throws LectureLogicException {
		return false;
	}

	@Override
	public Long findUser(String username) throws LectureLogicException {
		try {
			UserInfo user = securityService.getUserByName(username);
			if (user != null) {
				return user.getId();
			} else {
				return null;
			}
		} catch (IllegalArgumentException ex) {
			throw new LectureLogicException(ex.getMessage(), ex);
		}
	}

	@Override
	public Long createCourse(CourseBean course) throws LectureLogicException {
		try {
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

			Long courseId = courseService.create(courseInfo);

			return courseId;
		} catch (IllegalArgumentException ex) {
			throw new LectureLogicException(ex.getMessage(), ex);
		}
	}

	@Override
	public boolean deleteCourse(long courseId) throws LectureLogicException {
		try {
			courseService.removeCourse(courseId);
			return true;
		} catch (CourseServiceException ex) {
			logger.info(ex);
			return false;
		}
	}

	@Override
	public boolean updateCourse(CourseBean course) throws LectureLogicException {
		try {
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
		} catch (IllegalArgumentException ex) {
			throw new LectureLogicException(ex.getMessage(), ex);
		}
	}

	@Override
	public CourseBean getCourse(long courseId) throws LectureLogicException {
		try {
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
		} catch (IllegalArgumentException ex) {
			throw new LectureLogicException(ex.getMessage(), ex);
		}
	}

	@Override
	public boolean assignCourseMember(long courseId, long userId, Role role) throws LectureLogicException {
		try {
			Validate.notNull(role, "Parameter role must not be null.");
			UserInfo user = securityService.getUser(userId);
			CourseInfo course = courseService.getCourseInfo(courseId);

			if (role == Role.ASSISTANT) {
				courseService.addAssistant(course, user);
			} else {
				courseService.addParticipant(course, user);
			}

			return true;
		} catch (IllegalArgumentException ex) {
			throw new LectureLogicException(ex.getMessage(), ex);
		}
	}

	@Override
	public boolean removeCourseMember(long courseId, long userId) throws LectureLogicException {
		try {
			UserInfo user = securityService.getUser(userId);
			CourseInfo course = courseService.getCourseInfo(courseId);

			CourseMemberInfo member = courseService.getMemberInfo(course, user);
			if (member != null) {
				courseService.removeMember(member.getId());
			} else {
				return false;
			}
			return true;
		} catch (IllegalArgumentException ex) {
			throw new LectureLogicException(ex.getMessage(), ex);
		}
	}

	@Override
	public Role isCourseMember(long courseId, long userId) throws LectureLogicException {
		try {
			UserInfo user = securityService.getUser(userId);
			CourseInfo course = courseService.getCourseInfo(courseId);

			CourseMemberInfo member = courseService.getMemberInfo(course, user);

			if (member == null) {
				return Role.NONE;
			} else if (member.getMemberType() == CourseMemberType.ASSISTANT) {
				return Role.ASSISTANT;
			} else {
				return Role.PARTICIPANT;
			}
		} catch (IllegalArgumentException ex) {
			throw new LectureLogicException(ex.getMessage(), ex);
		}

	}

	@Override
	public boolean updateInstitute(InstituteBean institute) throws LectureLogicException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Long createInstitute(InstituteBean institute) throws LectureLogicException {
		
		InstituteInfo info = new InstituteInfo();
		copyProperties(institute, info);
		
		Long id = instituteService.create(info, institute.getOwnerId());
		return id;
	}

	@Override
	public InstituteBean getInstitute(long instituteId) throws LectureLogicException {
		InstituteInfo source = instituteService.findInstitute(instituteId);
		if (source == null) {
			throw new LectureLogicException("Institute ("+instituteId+") does not exists.");
		}
		InstituteBean target = new InstituteBean();
		copyProperties(source, target);
		return target;
	}

	@Override
	public List<InstituteBean> listInstitute(long departmentId) throws LectureLogicException {
		List<InstituteInfo> institutes = instituteService.findInstitutesByDepartment(departmentId);
		List<InstituteBean> beans = new ArrayList(institutes.size());
		for(InstituteInfo institute: institutes) {
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

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public CourseTypeService getCourseTypeService() {
		return courseTypeService;
	}

	public void setCourseTypeService(CourseTypeService courseTypeService) {
		this.courseTypeService = courseTypeService;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public UniversityService getUniversityService() {
		return universityService;
	}

	public void setUniversityService(UniversityService universityService) {
		this.universityService = universityService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	// --------------------- Private Methods ------------- //
	private boolean containPeriodCourseTimeFrame(CourseBean course, PeriodInfo period) {
		return (!period.getStartdate().after(course.getStartDate()) && !period.getEnddate().before(course.getEndDate()));
	}

	private void copyProperties(Object source, Object target) throws LectureLogicException {
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

	private void copyPropertiesFromUserBeanToUserInfo(UserBean user, UserInfo userInfo) {
		copyPropertiesFromUserBeanToUserInfo(user, userInfo, false);
	}

	private void copyPropertiesFromUserBeanToUserInfo(UserBean user, UserInfo userInfo, boolean copyIfNull) {
		if (copyIfNull || user.getUsername() != null)
			userInfo.setUsername(user.getUsername());
		if (copyIfNull || user.getEmail() != null)
			userInfo.setEmail(user.getEmail());
		if (copyIfNull || user.getPassword() != null)
			userInfo.setPassword(user.getPassword());
		if (copyIfNull || user.getFirstName() != null)
			userInfo.setFirstName(user.getFirstName());
		if (copyIfNull || user.getLastName() != null)
			userInfo.setLastName(user.getLastName());
		if (copyIfNull || user.getAddress() != null)
			userInfo.setAddress(user.getAddress());
		if (copyIfNull || user.getPostCode() != null)
			userInfo.setPostcode(user.getPostCode());
		if (copyIfNull || user.getCity() != null)
			userInfo.setCity(user.getCity());
		if (copyIfNull || user.getMatriculation() != null)
			userInfo.setMatriculation(user.getMatriculation());
		if (copyIfNull || user.getLocale() != null)
			userInfo.setLocale(user.getLocale());
		if (copyIfNull || user.getTitle() != null)
			userInfo.setTitle(user.getTitle());
	}

	private boolean isFoundBiggerThenPeriod(PeriodInfo found, PeriodInfo period) {
		return found == null
				|| (found.getStartdate().before(period.getStartdate()) && found.getEnddate().after(period.getEnddate()));
	}

	private PeriodInfo findBestFittingPeriod(CourseBean course) throws LectureLogicException {
		InstituteInfo institute = instituteService.findInstitute(course.getInstituteId());
		Validate.notNull(institute, "Couldn't find an institute with the id " + course.getInstituteId());
		DepartmentInfo department = departmentService.findDepartment(institute.getDepartmentId());
		List<PeriodInfo> periods = universityService.findPeriodsByUniversity(department.getUniversityId());
		Validate.notEmpty(periods, "Couldn't find any periods for the course, check institute id's.");

		if (course.getEndDate() == null) {
			course.setEndDate(course.getStartDate());
		}

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



}