// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.desktop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.Department;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.University;
import org.openuss.security.User;

/**
 * @see org.openuss.desktop.DesktopService2
 * @author Ron Haus, Florian Dondorf
 */
public class DesktopService2Impl extends org.openuss.desktop.DesktopService2Base {

	private static final Logger logger = Logger.getLogger(DesktopServiceImpl.class);
	
	/**
	 * @see org.openuss.desktop.DesktopService2#findDesktop(java.lang.Long)
	 */
	protected org.openuss.desktop.DesktopInfo handleFindDesktop(java.lang.Long desktopId) throws java.lang.Exception {
		
		Validate.notNull(desktopId, "DesktopService2.handleFindDesktop - desktopId cannot be null!");
		
		return (DesktopInfo) this.getDesktopDao().load(DesktopDao.TRANSFORM_DESKTOPINFO, desktopId);
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#findDesktopByUser(java.lang.Long)
	 */
	protected org.openuss.desktop.DesktopInfo handleFindDesktopByUser(java.lang.Long userId) throws java.lang.Exception {
		
		Validate.notNull(userId, "DesktopService2.handleFindDesktopByUser - userId cannot be null!");
		
		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "DesktopService2.handleFindDesktopByUser - No user found corresponding to the userId "+userId);
		
		Desktop desktop = getDesktopDao().findByUser(user);
	
		if (desktop == null) {
			// create new desktop
			logger.debug("DesktopService2.handleFindDesktopByUser - desktop doesn't exist for user, create new one");
			desktop = new DesktopImpl();
			desktop.setUser(user);
			this.getDesktopDao().create(desktop);
		}
		return this.getDesktopDao().toDesktopInfo(desktop);
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#createDesktop(java.lang.Long)
	 */
	protected java.lang.Long handleCreateDesktop(java.lang.Long userId) throws java.lang.Exception {
		
		Validate.notNull(userId, "DesktopService2.handleCreateDesktop - userId cannot be null!");
		
		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "DesktopService2.handleCreateDesktop - No user found corresponding to the userId "+userId);
		
		Desktop desktop = new DesktopImpl();
		desktop.setUser(user);
		return this.getDesktopDao().create(desktop).getId();
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#updateDesktop(org.openuss.desktop.DesktopInfo)
	 */
	protected void handleUpdateDesktop(org.openuss.desktop.DesktopInfo desktop) throws java.lang.Exception {
		
		Validate.notNull(desktop, "DesktopService2.handleUpdateDesktop - desktop cannot be null!");
		
		Desktop desktopEntity = this.getDesktopDao().desktopInfoToEntity(desktop);
			
		this.getDesktopDao().update(desktopEntity);		
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#linkUniversity(java.lang.Long, java.lang.Long)
	 */
	protected void handleLinkUniversity(java.lang.Long desktopId, java.lang.Long universityId)
			throws java.lang.Exception {
		
		Validate.notNull(desktopId, "DesktopService2.handleLinkUniversity - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate.notNull(desktop, "DesktopService2.handleLinkUniversity - No Desktop found corresponding to the desktopId "+desktopId);
		
		Validate.notNull(universityId, "DesktopService2.handleLinkUniversity - universityId cannot be null!");
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university, "DesktopService2.handleLinkUniversity - No University found corresponding to the universityId "+universityId);
		
		if (!desktop.getUniversities().contains(university)) {
			desktop.getUniversities().add(university);
		}
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#linkDepartment(java.lang.Long, java.lang.Long)
	 */
	protected void handleLinkDepartment(java.lang.Long desktopId, java.lang.Long departmentId)
			throws java.lang.Exception {
		
		Validate.notNull(desktopId, "DesktopService2.handleLinkDepartment - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate.notNull(desktop, "DesktopService2.handleLinkDepartment - No Desktop found corresponding to the desktopId "+desktopId);
		
		Validate.notNull(departmentId, "DesktopService2.handleLinkDepartment - departmentId cannot be null!");
		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department, "DesktopService2.handleLinkDepartment - No Department found corresponding to the departmentId "+departmentId);
		
		if (!desktop.getDepartments().contains(department)) {
			desktop.getDepartments().add(department);
		}
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#linkInstitute(java.lang.Long, java.lang.Long)
	 */
	protected void handleLinkInstitute(java.lang.Long desktopId, java.lang.Long instituteId) throws java.lang.Exception {
		
		Validate.notNull(desktopId, "DesktopService2.handleLinkInstitute - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate.notNull(desktop, "DesktopService2.handleLinkInstitute - No Desktop found corresponding to the desktopId "+desktopId);
		
		Validate.notNull(instituteId, "DesktopService2.handleLinkInstitute - instituteId cannot be null!");
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute, "DesktopService2.handleLinkInstitute - No Institute found corresponding to the instituteId "+instituteId);
		
		if (!desktop.getInstitutes().contains(institute)) {
			desktop.getInstitutes().add(institute);
		}
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#linkCourseType(java.lang.Long, java.lang.Long)
	 */
	protected void handleLinkCourseType(java.lang.Long desktopId, java.lang.Long courseTypeId)
			throws java.lang.Exception {
		
		Validate.notNull(desktopId, "DesktopService2.handleLinkCourseType - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate.notNull(desktop, "DesktopService2.handleLinkCourseType - No Desktop found corresponding to the desktopId "+desktopId);
		
		Validate.notNull(courseTypeId, "DesktopService2.handleLinkCourseType - courseTypeId cannot be null!");
		CourseType courseType = this.getCourseTypeDao().load(courseTypeId);
		Validate.notNull(courseType, "DesktopService2.handleLinkCourseType - No CourseType found corresponding to the courseTypeId "+courseTypeId);
		
		if (!desktop.getCourseTypes().contains(courseType)) {
			desktop.getCourseTypes().add(courseType);
		}
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#linkCourse(java.lang.Long, java.lang.Long)
	 */
	protected void handleLinkCourse(java.lang.Long desktopId, java.lang.Long courseId) throws java.lang.Exception {
		
		Validate.notNull(desktopId, "DesktopService2.handleLinkCourse - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate.notNull(desktop, "DesktopService2.handleLinkCourse - No Desktop found corresponding to the desktopId "+desktopId);
		
		Validate.notNull(courseId, "DesktopService2.handleLinkCourse - courseId cannot be null!");
		Course course = this.getCourseDao().load(courseId);
		Validate.notNull(course, "DesktopService2.handleLinkCourse - No Course found corresponding to the courseId "+courseId);
		
		if (!desktop.getCourses().contains(course)) {
			desktop.getCourses().add(course);
		}
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#unlinkUniversity(java.lang.Long, java.lang.Long)
	 */
	protected void handleUnlinkUniversity(java.lang.Long desktopId, java.lang.Long universityId)
			throws java.lang.Exception {

		Validate.notNull(desktopId, "DesktopService2.handleUnlinkUniversity - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate.notNull(desktop, "DesktopService2.handleUnlinkUniversity - No Desktop found corresponding to the desktopId "+desktopId);
		
		Validate.notNull(universityId, "DesktopService2.handleUnlinkUniversity - universityId cannot be null!");
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university, "DesktopService2.handleUnlinkUniversity - No University found corresponding to the universityId "+universityId);
		
		desktop.getUniversities().remove(university);
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#unlinkDepartment(java.lang.Long, java.lang.Long)
	 */
	protected void handleUnlinkDepartment(java.lang.Long desktopId, java.lang.Long departmentId)
			throws java.lang.Exception {
		
		Validate.notNull(desktopId, "DesktopService2.handleUnlinkDepartment - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate.notNull(desktop, "DesktopService2.handleUnlinkDepartment - No Desktop found corresponding to the desktopId "+desktopId);
		
		Validate.notNull(departmentId, "DesktopService2.handleUnlinkDepartment - departmentId cannot be null!");
		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department, "DesktopService2.handleUnlinkDepartment - No Department found corresponding to the departmentId "+departmentId);
		
		desktop.getDepartments().remove(department);
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#unlinkInstitute(java.lang.Long, java.lang.Long)
	 */
	protected void handleUnlinkInstitute(java.lang.Long desktopId, java.lang.Long instituteId)
			throws java.lang.Exception {
		
		Validate.notNull(desktopId, "DesktopService2.handleUnlinkInstitute - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate.notNull(desktop, "DesktopService2.handleUnlinkInstitute - No Desktop found corresponding to the desktopId "+desktopId);
		
		Validate.notNull(instituteId, "DesktopService2.handleUnlinkInstitute - instituteId cannot be null!");
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute, "DesktopService2.handleUnlinkInstitute - No Institute found corresponding to the instituteId "+instituteId);
		
		desktop.getInstitutes().remove(institute);
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#unlinkCourseType(java.lang.Long, java.lang.Long)
	 */
	protected void handleUnlinkCourseType(java.lang.Long desktopId, java.lang.Long courseTypeId)
			throws java.lang.Exception {
		
		Validate.notNull(desktopId, "DesktopService2.handleUnlinkCourseType - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate.notNull(desktop, "DesktopService2.handleUnlinkCourseType - No Desktop found corresponding to the desktopId "+desktopId);
		
		Validate.notNull(courseTypeId, "DesktopService2.handleUnlinkCourseType - courseTypeId cannot be null!");
		CourseType courseType = this.getCourseTypeDao().load(courseTypeId);
		Validate.notNull(courseType, "DesktopService2.handleUnlinkCourseType - No CourseType found corresponding to the courseTypeId "+courseTypeId);
		
		desktop.getCourseTypes().remove(courseType);
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#unlinkCourse(java.lang.Long, java.lang.Long)
	 */
	protected void handleUnlinkCourse(java.lang.Long desktopId, java.lang.Long courseId) throws java.lang.Exception {
		
		Validate.notNull(desktopId, "DesktopService2.handleUnlinkCourse - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate.notNull(desktop, "DesktopService2.handleUnlinkCourse - No Desktop found corresponding to the desktopId "+desktopId);
		
		Validate.notNull(courseId, "DesktopService2.handleUnlinkCourse - courseId cannot be null!");
		Course course = this.getCourseDao().load(courseId);
		Validate.notNull(course, "DesktopService2.handleUnlinkCourse - No Course found corresponding to the courseId "+courseId);
		
		desktop.getCourses().remove(course);
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#unlinkAllFromUniversity(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected void handleUnlinkAllFromUniversity(java.lang.Long universityId) throws java.lang.Exception {
		
		Validate.notNull(universityId, "DesktopService2.handleUnlinkAllFromUniversity - universityId cannot be null!");
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university, "DesktopService2.handleUnlinkAllFromUniversity - No University found corresponding to the universityId "+universityId);
		
		Collection<Desktop> desktops = getDesktopDao().findByUniversity(university);
		for (Desktop desktop : desktops) {
			desktop.getUniversities().remove(university);
		}
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#unlinkAllFromDepartment(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected void handleUnlinkAllFromDepartment(java.lang.Long departmentId) throws java.lang.Exception {
		
		Validate.notNull(departmentId, "DesktopService2.handleUnlinkAllFromDepartment - departmentId cannot be null!");
		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department, "DesktopService2.handleUnlinkAllFromDepartment - No Department found corresponding to the departmentId "+departmentId);
		
		Collection<Desktop> desktops = getDesktopDao().findByDepartment(department);
		for (Desktop desktop : desktops) {
			desktop.getDepartments().remove(department);
		}
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#unlinkAllFromInstitue(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected void handleUnlinkAllFromInstitue(java.lang.Long instituteId) throws java.lang.Exception {
		
		Validate.notNull(instituteId, "DesktopService2.handleUnlinkAllFromInstitue - instituteId cannot be null!");
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute, "DesktopService2.handleUnlinkAllFromInstitue - No Institute found corresponding to the instituteId "+instituteId);
		
		Collection<Desktop> desktops = getDesktopDao().findByInstitute(institute);
		for (Desktop desktop : desktops) {
			desktop.getInstitutes().remove(institute);
		}
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#unlinkAllFromCourseType(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected void handleUnlinkAllFromCourseType(java.lang.Long courseTypeId) throws java.lang.Exception {
		
		Validate.notNull(courseTypeId, "DesktopService2.handleUnlinkAllFromCourseType - courseTypeId cannot be null!");
		CourseType courseType = this.getCourseTypeDao().load(courseTypeId);
		Validate.notNull(courseType, "DesktopService2.handleUnlinkAllFromCourseType - No CourseType found corresponding to the courseTypeId "+courseTypeId);
		
		Collection<Desktop> desktops = getDesktopDao().findByCourseType(courseType);
		for (Desktop desktop : desktops) {
			desktop.getCourseTypes().remove(courseType);
		}
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#unlinkAllFromCourse(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected void handleUnlinkAllFromCourse(java.lang.Long courseId) throws java.lang.Exception {
		
		Validate.notNull(courseId, "DesktopService2.handleUnlinkAllFromCourse - courseId cannot be null!");
		Course course = this.getCourseDao().load(courseId);
		Validate.notNull(course, "DesktopService2.handleUnlinkAllFromCourse - No Course found corresponding to the courseId "+courseId);
		
		Collection<Desktop> desktops = getDesktopDao().findByCourse(course);
		for (Desktop desktop : desktops) {
			desktop.getCourses().remove(course);
		}
	}
	
	/**
	 * @see org.openuss.desktop.DesktopService2#handleFindLinkedDepartmentsByUserAndUniversity(java.lang.Long, java.lang.Long universityId)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindLinkedDepartmentsByUserAndUniversity(java.lang.Long userId, 
			java.lang.Long universityId) throws java.lang.Exception {
		
		Validate.notNull(userId, "DesktopService2.handleFindLinkedDEpartmentsByUserAndUniversity - userId cannot be null!");
		Validate.notNull(universityId, "DesktopService2.handleFindLinkedDEpartmentsByUserAndUniversity - universityId cannot be null!");
		
		// Get linked departments of user for given university
		DesktopInfo desktopInfo = this.findDesktopByUser(userId);
		List<DepartmentInfo> linkedDepartments = new ArrayList<DepartmentInfo>();
		Iterator iter = desktopInfo.getDepartmentInfos().iterator();
		while (iter.hasNext()) {
			DepartmentInfo departmentInfo = (DepartmentInfo) iter.next();
			if (departmentInfo.getUniversityId() == universityId) {
				linkedDepartments.add(departmentInfo);
			}
		}
		
		return linkedDepartments;
	}
	
	/**
	 * @see org.openuss.desktop.DesktopService2#handleFindAdditionalDepartmentsByUserAndUniversity(java.lang.Long, java.lang.Long universityId)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindAdditionalDepartmentsByUserAndUniversity(java.lang.Long userId, 
			java.lang.Long universityId) throws java.lang.Exception {
		
		Validate.notNull(userId, "DesktopService2.handleFindAdditionalDepartmentsByUserAndUniversity - userId cannot be null!");
		Validate.notNull(universityId, "DesktopService2.handleFindAdditionalDepartmentsByUserAndUniversity - universityId cannot be null!");
		
		DesktopInfo desktopInfo = this.findDesktopByUser(userId);
		
		// Get additional institutes of university
		List<InstituteInfo> additionalInstitutes = this.findAdditionalInstitutesByUserAndUniversity(userId, universityId);
		
		// Generate list of all institutes
		List<InstituteInfo> allInstitutes = new ArrayList<InstituteInfo>();
		allInstitutes.addAll(additionalInstitutes);
		allInstitutes.addAll(desktopInfo.getInstituteInfos());
		
		// Get additional departments
		List<DepartmentInfo> additionalDepartments = new ArrayList<DepartmentInfo>();
		Iterator iter = allInstitutes.iterator();
		while (iter.hasNext()) {
			InstituteInfo instituteInfo = (InstituteInfo) iter.next();
			Institute institute = this.getInstituteDao().load(instituteInfo.getId());
			DepartmentInfo departmentInfo = this.getDepartmentDao().toDepartmentInfo(institute.getDepartment());
			if (!desktopInfo.getDepartmentInfos().contains(departmentInfo) && departmentInfo.getUniversityId() == universityId) {
				additionalDepartments.add(departmentInfo);
			}
		}
		
		return additionalDepartments;
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#handleFindLinkedInstitutesByUserAndUniversity(java.lang.Long, java.lang.Long universityId)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindLinkedInstitutesByUserAndUniversity(java.lang.Long userId,
			java.lang.Long universityId) throws java.lang.Exception {
		
		Validate.notNull(userId, "DesktopService2.handleFindLinkedInstitutesByUserAndUniversity - userId cannot be null!");
		Validate.notNull(universityId, "DesktopService2.handleFindLinkedInstitutesByUserAndUniversity - universityId cannot be null!");
		
		// Get linked institutes of user for given university
		DesktopInfo desktopInfo = this.findDesktopByUser(userId);
		List<InstituteInfo> linkedInstitutes = new ArrayList<InstituteInfo>();
		Iterator iter = desktopInfo.getInstituteInfos().iterator();
		while (iter.hasNext()) {
			InstituteInfo instituteInfo = (InstituteInfo) iter.next();
			Institute institute = this.getInstituteDao().load(instituteInfo.getId());
			if (institute.getDepartment().getUniversity().getId() == universityId) {
				linkedInstitutes.add(instituteInfo);
			}
		}
		
		return linkedInstitutes;
	}
	
	/**
	 * @see org.openuss.desktop.DesktopService2#handleFindAdditionalInstitutesByUserAndUniversity(java.lang.Long, java.lang.Long universityId)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindAdditionalInstitutesByUserAndUniversity(java.lang.Long userId,
			java.lang.Long universityId) throws java.lang.Exception {
		
		Validate.notNull(userId, "DesktopService2.handleFindAdditionalInstitutesByUserAndUniversity - userId cannot be null!");
		Validate.notNull(universityId, "DesktopService2.handleFindAdditionalInstitutesByUserAndUniversity - universityId cannot be null!");
		
		// Get Courses
		DesktopInfo desktopInfo = this.findDesktopByUser(userId);
		List<InstituteInfo> additionalInstitutes = new ArrayList<InstituteInfo>();
		List<InstituteInfo> linkedInstitutes = desktopInfo.getInstituteInfos();
		Iterator iter = desktopInfo.getCourseInfos().iterator();
		while (iter.hasNext()) {
			CourseInfo courseInfo = (CourseInfo) iter.next();
			Course course = this.getCourseDao().load(courseInfo.getId());
			Institute institute = course.getCourseType().getInstitute();
			InstituteInfo instituteInfo = this.getInstituteDao().toInstituteInfo(institute);
			if (!linkedInstitutes.contains(instituteInfo) && 
					institute.getDepartment().getUniversity().getId() == universityId) {
				additionalInstitutes.add(instituteInfo);
			}
		}
		
		return additionalInstitutes;
	}
	
	/**
	 * @see org.openuss.desktop.DesktopService2#handleFindCoursesByUserAndUniversity(java.lang.Long, java.lang.Long universityId)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindLinkedCoursesByUserAndUniversity(java.lang.Long userId,
			java.lang.Long universityId) throws java.lang.Exception {
		
		Validate.notNull(userId, "DesktopService2.handleFindCoursesByUserAndUniversity - userId cannot be null!");
		Validate.notNull(universityId, "DesktopService2.handleFindCoursesByUserAndUniversity - universityId cannot be null!");
		
		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "DesktopService2.handleFindCoursesByUserAndUniversity - No user found corresponding to the userId "+userId);
		
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university, "DesktopService2.handleFindCoursesByUserAndUniversity - No university found corresponding to the universityId "+universityId);
		
		DesktopInfo desktopInfo = this.findDesktopByUser(userId);
		List<CourseInfo> linkedCourses = new ArrayList<CourseInfo>();
		
		Iterator iter = desktopInfo.getCourseInfos().iterator();
		while (iter.hasNext()) {
			CourseInfo courseInfo = (CourseInfo) iter.next();
			Course course = this.getCourseDao().courseInfoToEntity(courseInfo);
			if (course.getCourseType().getInstitute().getDepartment().getUniversity().getId() == universityId) {
				linkedCourses.add(courseInfo);
			}
		}
		
		return linkedCourses;
	}
}