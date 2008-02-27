package org.openuss.services;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.CourseService;
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

	private SecurityService securityService;

	private CourseService courseService;
	private CourseTypeService courseTypeService;
	private InstituteService instituteService;
	private UniversityService universityService;
	private DepartmentService departmentService;

	@Override
	public Long createUser(UserBean user) {
		Validate.notNull(user, "User must not be null");
		UserInfo userInfo = new UserInfo();
		copyPropertiesFromUserBeanToUserInfo(user, userInfo);
		userInfo.setEnabled(true);
		userInfo = securityService.createUser(userInfo);
		return userInfo.getId();
	}

	@Override
	public Long findUser(String username) {
		UserInfo user = securityService.getUserByName(username);
		if (user != null) {
			return user.getId();
		} else {
			return null;
		}
	}

	@Override
	public boolean updateUser(UserBean user) throws LectureLogicException {
		Validate.notNull(user, "Parameter user must not be null!");
		UserInfo userInfo = securityService.getUser(user.getId());
		copyPropertiesFromUserBeanToUserInfo(user, userInfo, false);
		securityService.saveUser(userInfo);
		return true;
	}

	@Override
	public boolean deleteUser(long userId) throws LectureLogicException {
		return false;
	}

	@Override
	public Long createCourse(CourseBean course) throws LectureLogicException {
		Validate.notNull(course, "Parameter course must not be null!");
		InstituteInfo institute = instituteService.findInstitute(course.getInstituteId());
		Validate.notNull(institute, "Couldn't find an institute with the id " + course.getInstituteId());

		PeriodInfo found = findBestFittingPeriod(course, institute);
		
		CourseTypeInfo courseType = new CourseTypeInfo();
		courseType.setName(course.getName());
		courseType.setShortcut(course.getShortcut());
		courseType.setInstituteId(institute.getId());
		
		courseType.setId(courseTypeService.create(courseType));
		
		CourseInfo courseInfo = new CourseInfo();
		courseInfo.setPeriodId(found.getId());
		courseInfo.setCourseTypeId(courseType.getId());

		courseInfo.setAccessType(AccessType.APPLICATION);
		courseInfo.setShortcut(course.getShortcut());
		courseInfo.setDescription(course.getDescription());
		
		Long courseId = courseService.create(courseInfo);
		
		return courseId;
	}
	
	@Override
	public CourseBean getCourse(long courseId) throws LectureLogicException {
		Validate.notNull(courseId, "Parameter courseId must not be null.");
		
		CourseInfo courseInfo = courseService.getCourseInfo(courseId);
		
		CourseBean course = new CourseBean();
		
		course.setId(courseInfo.getId());
		course.setName(courseInfo.getName());
		course.setShortcut(courseInfo.getShortcut());
		course.setDescription(courseInfo.getDescription());
		
		return course;
	}

	private PeriodInfo findBestFittingPeriod(CourseBean course, InstituteInfo institute) {
		DepartmentInfo department = departmentService.findDepartment(institute.getDepartmentId());
		List<PeriodInfo> periods = universityService.findPeriodsByUniversity(department.getUniversityId());
		Validate.notEmpty(periods, "Couldn't find any periods for the course, check institute id's.");

		if (course.getEndDate() == null) {
			course.setEndDate(course.getStartDate());
		}
		
		// Select first entry
		PeriodInfo found = periods.get(0);
		for (PeriodInfo period : periods) {
			if (containPeriodCourseTimeFrame(course, period) && isFoundBiggerThenPeriod(found, period)) {
				found = period;
			}
		}
		return found;
	}

	private boolean isFoundBiggerThenPeriod(PeriodInfo found, PeriodInfo period) {
		return (found.getStartdate().before(period.getStartdate()) && found.getEnddate().after(period.getEnddate()));
	}

	private boolean containPeriodCourseTimeFrame(CourseBean course, PeriodInfo period) {
		return (!period.getStartdate().after(course.getStartDate()) && !period.getEnddate()
				.before(course.getEndDate()));
	}

	@Override
	public boolean assignCourseMember(long courseId, long userId, Role role) throws LectureLogicException {
		Validate.notNull(role, "Parameter role must not be null.");
		UserInfo user = securityService.getUser(userId);
		CourseInfo course = courseService.getCourseInfo(courseId);
		
		if (role == Role.ASSISTANT) {
			courseService.addAssistant(course, user);
		} else {
			courseService.addParticipant(course, user);
		}
		
		return true;
	}

	@Override
	public boolean removeCourseMember(long courseId, long userId) throws LectureLogicException {
		UserInfo user = securityService.getUser(userId);
		CourseInfo course = courseService.getCourseInfo(courseId);
		
		CourseMemberInfo member = courseService.getMemberInfo(course, user);
		courseService.removeMember(member.getId());
		
		return true;
	}

	@Override
	public Role isCourseMember(long courseId, long userId) throws LectureLogicException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateCourse(CourseBean course) throws LectureLogicException {
		// TODO Auto-generated method stub
		return false;
	}


	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	@Override
	public Long createInstitute(InstituteBean institute) throws LectureLogicException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstituteBean getInstitute(long instituteId) throws LectureLogicException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateInstitute(InstituteBean institute) throws LectureLogicException {
		// TODO Auto-generated method stub
		return false;
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

}
