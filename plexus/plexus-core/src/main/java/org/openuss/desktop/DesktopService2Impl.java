// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.desktop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.Department;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.Period;
import org.openuss.lecture.University;
import org.openuss.security.User;

/**
 * @see org.openuss.desktop.DesktopService2
 * @author Ron Haus
 * @author Florian Dondorf
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
		Validate.notNull(user, "DesktopService2.handleFindDesktopByUser - No user found corresponding to the userId "
				+ userId);

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

		logger.debug("Starting method handleCreateDesktop");

		Validate.notNull(userId, "DesktopService2.handleCreateDesktop - userId cannot be null!");

		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "DesktopService2.handleCreateDesktop - No user found corresponding to the userId "
				+ userId);

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
	 * @see org.openuss.desktop.DesktopService2#linkUniversity(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected void handleLinkUniversity(java.lang.Long desktopId, java.lang.Long universityId)
			throws java.lang.Exception {

		Validate.notNull(desktopId, "DesktopService2.handleLinkUniversity - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate.notNull(desktop,
				"DesktopService2.handleLinkUniversity - No Desktop found corresponding to the desktopId " + desktopId);

		Validate.notNull(universityId, "DesktopService2.handleLinkUniversity - universityId cannot be null!");
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university,
				"DesktopService2.handleLinkUniversity - No University found corresponding to the universityId "
						+ universityId);

		if (!desktop.getUniversities().contains(university)) {
			desktop.getUniversities().add(university);
		}
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#linkDepartment(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected void handleLinkDepartment(java.lang.Long desktopId, java.lang.Long departmentId)
			throws java.lang.Exception {

		Validate.notNull(desktopId, "DesktopService2.handleLinkDepartment - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate.notNull(desktop,
				"DesktopService2.handleLinkDepartment - No Desktop found corresponding to the desktopId " + desktopId);

		Validate.notNull(departmentId, "DesktopService2.handleLinkDepartment - departmentId cannot be null!");
		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department,
				"DesktopService2.handleLinkDepartment - No Department found corresponding to the departmentId "
						+ departmentId);

		if (!desktop.getDepartments().contains(department)) {
			desktop.getDepartments().add(department);
		}
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#linkInstitute(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected void handleLinkInstitute(java.lang.Long desktopId, java.lang.Long instituteId) throws java.lang.Exception {

		Validate.notNull(desktopId, "DesktopService2.handleLinkInstitute - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate.notNull(desktop,
				"DesktopService2.handleLinkInstitute - No Desktop found corresponding to the desktopId " + desktopId);

		Validate.notNull(instituteId, "DesktopService2.handleLinkInstitute - instituteId cannot be null!");
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute,
				"DesktopService2.handleLinkInstitute - No Institute found corresponding to the instituteId "
						+ instituteId);

		if (!desktop.getInstitutes().contains(institute)) {
			desktop.getInstitutes().add(institute);
		}
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#linkCourseType(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected void handleLinkCourseType(java.lang.Long desktopId, java.lang.Long courseTypeId)
			throws java.lang.Exception {

		Validate.notNull(desktopId, "DesktopService2.handleLinkCourseType - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate.notNull(desktop,
				"DesktopService2.handleLinkCourseType - No Desktop found corresponding to the desktopId " + desktopId);

		Validate.notNull(courseTypeId, "DesktopService2.handleLinkCourseType - courseTypeId cannot be null!");
		CourseType courseType = this.getCourseTypeDao().load(courseTypeId);
		Validate.notNull(courseType,
				"DesktopService2.handleLinkCourseType - No CourseType found corresponding to the courseTypeId "
						+ courseTypeId);

		if (!desktop.getCourseTypes().contains(courseType)) {
			desktop.getCourseTypes().add(courseType);
		}
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#linkCourse(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected void handleLinkCourse(java.lang.Long desktopId, java.lang.Long courseId) throws java.lang.Exception {

		Validate.notNull(desktopId, "DesktopService2.handleLinkCourse - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate.notNull(desktop, "DesktopService2.handleLinkCourse - No Desktop found corresponding to the desktopId "
				+ desktopId);

		Validate.notNull(courseId, "DesktopService2.handleLinkCourse - courseId cannot be null!");
		Course course = this.getCourseDao().load(courseId);
		Validate.notNull(course, "DesktopService2.handleLinkCourse - No Course found corresponding to the courseId "
				+ courseId);

		if (!desktop.getCourses().contains(course)) {
			desktop.getCourses().add(course);
		}
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#unlinkUniversity(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected void handleUnlinkUniversity(java.lang.Long desktopId, java.lang.Long universityId)
			throws java.lang.Exception {

		Validate.notNull(desktopId, "DesktopService2.handleUnlinkUniversity - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate
				.notNull(desktop,
						"DesktopService2.handleUnlinkUniversity - No Desktop found corresponding to the desktopId "
								+ desktopId);

		Validate.notNull(universityId, "DesktopService2.handleUnlinkUniversity - universityId cannot be null!");
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university,
				"DesktopService2.handleUnlinkUniversity - No University found corresponding to the universityId "
						+ universityId);

		desktop.getUniversities().remove(university);
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#unlinkDepartment(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected void handleUnlinkDepartment(java.lang.Long desktopId, java.lang.Long departmentId)
			throws java.lang.Exception {

		Validate.notNull(desktopId, "DesktopService2.handleUnlinkDepartment - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate
				.notNull(desktop,
						"DesktopService2.handleUnlinkDepartment - No Desktop found corresponding to the desktopId "
								+ desktopId);

		Validate.notNull(departmentId, "DesktopService2.handleUnlinkDepartment - departmentId cannot be null!");
		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department,
				"DesktopService2.handleUnlinkDepartment - No Department found corresponding to the departmentId "
						+ departmentId);

		desktop.getDepartments().remove(department);

	}

	/**
	 * @see org.openuss.desktop.DesktopService2#unlinkInstitute(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected void handleUnlinkInstitute(java.lang.Long desktopId, java.lang.Long instituteId)
			throws java.lang.Exception {

		Validate.notNull(desktopId, "DesktopService2.handleUnlinkInstitute - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate.notNull(desktop,
				"DesktopService2.handleUnlinkInstitute - No Desktop found corresponding to the desktopId " + desktopId);

		Validate.notNull(instituteId, "DesktopService2.handleUnlinkInstitute - instituteId cannot be null!");
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute,
				"DesktopService2.handleUnlinkInstitute - No Institute found corresponding to the instituteId "
						+ instituteId);

		desktop.getInstitutes().remove(institute);
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#unlinkCourseType(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected void handleUnlinkCourseType(java.lang.Long desktopId, java.lang.Long courseTypeId)
			throws java.lang.Exception {

		Validate.notNull(desktopId, "DesktopService2.handleUnlinkCourseType - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate
				.notNull(desktop,
						"DesktopService2.handleUnlinkCourseType - No Desktop found corresponding to the desktopId "
								+ desktopId);

		Validate.notNull(courseTypeId, "DesktopService2.handleUnlinkCourseType - courseTypeId cannot be null!");
		CourseType courseType = this.getCourseTypeDao().load(courseTypeId);
		Validate.notNull(courseType,
				"DesktopService2.handleUnlinkCourseType - No CourseType found corresponding to the courseTypeId "
						+ courseTypeId);

		desktop.getCourseTypes().remove(courseType);
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#unlinkCourse(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected void handleUnlinkCourse(java.lang.Long desktopId, java.lang.Long courseId) throws java.lang.Exception {

		Validate.notNull(desktopId, "DesktopService2.handleUnlinkCourse - desktopId cannot be null!");
		Desktop desktop = this.getDesktopDao().load(desktopId);
		Validate.notNull(desktop,
				"DesktopService2.handleUnlinkCourse - No Desktop found corresponding to the desktopId " + desktopId);

		Validate.notNull(courseId, "DesktopService2.handleUnlinkCourse - courseId cannot be null!");
		Course course = this.getCourseDao().load(courseId);
		Validate.notNull(course, "DesktopService2.handleUnlinkCourse - No Course found corresponding to the courseId "
				+ courseId);

		desktop.getCourses().remove(course);
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#unlinkAllFromUniversity(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected void handleUnlinkAllFromUniversity(java.lang.Long universityId) throws java.lang.Exception {

		Validate.notNull(universityId, "DesktopService2.handleUnlinkAllFromUniversity - universityId cannot be null!");
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university,
				"DesktopService2.handleUnlinkAllFromUniversity - No University found corresponding to the universityId "
						+ universityId);

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
		Validate.notNull(department,
				"DesktopService2.handleUnlinkAllFromDepartment - No Department found corresponding to the departmentId "
						+ departmentId);

		Collection<Desktop> desktops = getDesktopDao().findByDepartment(department);
		for (Desktop desktop : desktops) {
			desktop.getDepartments().remove(department);
		}
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#unlinkAllFromInstitue(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected void handleUnlinkAllFromInstitute(java.lang.Long instituteId) throws java.lang.Exception {

		Validate.notNull(instituteId, "DesktopService2.handleUnlinkAllFromInstitue - instituteId cannot be null!");
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute,
				"DesktopService2.handleUnlinkAllFromInstitue - No Institute found corresponding to the instituteId "
						+ instituteId);

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
		Validate.notNull(courseType,
				"DesktopService2.handleUnlinkAllFromCourseType - No CourseType found corresponding to the courseTypeId "
						+ courseTypeId);

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
		Validate
				.notNull(course,
						"DesktopService2.handleUnlinkAllFromCourse - No Course found corresponding to the courseId "
								+ courseId);

		Collection<Desktop> desktops = getDesktopDao().findByCourse(course);
		for (Desktop desktop : desktops) {
			desktop.getCourses().remove(course);
		}
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#handleFindLinkedDepartmentsByUserAndUniversity(java.lang.Long,
	 *      java.lang.Long universityId)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindLinkedDepartmentsByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)
			throws java.lang.Exception {

		Validate.notNull(userId,
				"DesktopService2.handleFindLinkedDEpartmentsByUserAndUniversity - userId cannot be null!");
		Validate.notNull(universityId,
				"DesktopService2.handleFindLinkedDEpartmentsByUserAndUniversity - universityId cannot be null!");

		// Get linked departments of user for given university
		DesktopInfo desktopInfo = this.findDesktopByUser(userId);
		List<DepartmentInfo> linkedDepartments = new ArrayList<DepartmentInfo>();
		Iterator iter = desktopInfo.getDepartmentInfos().iterator();
		while (iter.hasNext()) {
			DepartmentInfo departmentInfo = (DepartmentInfo) iter.next();
			if (departmentInfo.getUniversityId().equals(universityId)) {
				linkedDepartments.add(departmentInfo);
			}
		}

		return linkedDepartments;
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#handleFindAdditionalDepartmentsByUserAndUniversity(java.lang.Long,
	 *      java.lang.Long universityId)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindAdditionalDepartmentsByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)
			throws java.lang.Exception {

		Validate.notNull(userId,
				"DesktopService2.handleFindAdditionalDepartmentsByUserAndUniversity - userId cannot be null!");
		Validate.notNull(universityId,
				"DesktopService2.handleFindAdditionalDepartmentsByUserAndUniversity - universityId cannot be null!");

		DesktopInfo desktopInfo = this.findDesktopByUser(userId);

		// Get additional institutes of university
		List<InstituteInfo> additionalInstitutes = this.findAdditionalInstitutesByUserAndUniversity(userId,
				universityId);

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
			if (!desktopInfo.getDepartmentInfos().contains(departmentInfo)
					&& universityId.equals(departmentInfo.getUniversityId())) {
				additionalDepartments.add(departmentInfo);
			}
		}

		return additionalDepartments;
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#handleFindLinkedInstitutesByUserAndUniversity(java.lang.Long,
	 *      java.lang.Long universityId)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindLinkedInstitutesByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)
			throws java.lang.Exception {

		Validate.notNull(userId,
				"DesktopService2.handleFindLinkedInstitutesByUserAndUniversity - userId cannot be null!");
		Validate.notNull(universityId,
				"DesktopService2.handleFindLinkedInstitutesByUserAndUniversity - universityId cannot be null!");

		// Get linked institutes of user for given university
		DesktopInfo desktopInfo = this.findDesktopByUser(userId);
		List<InstituteInfo> linkedInstitutes = new ArrayList<InstituteInfo>();
		Iterator iter = desktopInfo.getInstituteInfos().iterator();
		while (iter.hasNext()) {
			InstituteInfo instituteInfo = (InstituteInfo) iter.next();
			Institute institute = this.getInstituteDao().load(instituteInfo.getId());
			if (institute.getDepartment().getUniversity().getId().equals(universityId)) {
				linkedInstitutes.add(instituteInfo);
			}
		}

		return linkedInstitutes;
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#handleFindAdditionalInstitutesByUserAndUniversity(java.lang.Long,
	 *      java.lang.Long universityId)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindAdditionalInstitutesByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)
			throws java.lang.Exception {

		Validate.notNull(userId,
				"DesktopService2.handleFindAdditionalInstitutesByUserAndUniversity - userId cannot be null!");
		Validate.notNull(universityId,
				"DesktopService2.handleFindAdditionalInstitutesByUserAndUniversity - universityId cannot be null!");

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
			if (!linkedInstitutes.contains(instituteInfo)
					&& universityId.equals(institute.getDepartment().getUniversity().getId())) {
				additionalInstitutes.add(instituteInfo);
			}
		}

		return additionalInstitutes;
	}

	/**
	 * @see org.openuss.desktop.DesktopService2#handleFindCoursesByUserAndUniversity(java.lang.Long,
	 *      java.lang.Long universityId)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindLinkedCoursesByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)
			throws java.lang.Exception {

		Validate.notNull(userId, "DesktopService2.handleFindCoursesByUserAndUniversity - userId cannot be null!");
		Validate.notNull(universityId,
				"DesktopService2.handleFindCoursesByUserAndUniversity - universityId cannot be null!");

		User user = this.getUserDao().load(userId);
		Validate.notNull(user,
				"DesktopService2.handleFindCoursesByUserAndUniversity - No user found corresponding to the userId "
						+ userId);

		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university,
				"DesktopService2.handleFindCoursesByUserAndUniversity - No university found corresponding to the universityId "
						+ universityId);

		DesktopInfo desktopInfo = this.findDesktopByUser(userId);
		List<CourseInfo> linkedCourses = new ArrayList<CourseInfo>();

		Iterator iter = desktopInfo.getCourseInfos().iterator();
		while (iter.hasNext()) {
			CourseInfo courseInfo = (CourseInfo) iter.next();
			Course course = this.getCourseDao().courseInfoToEntity(courseInfo);
			if (course.getCourseType().getInstitute().getDepartment().getUniversity().getId().equals(universityId)) {
				linkedCourses.add(courseInfo);
			}
		}

		return linkedCourses;
	}

	protected boolean handleIsUniversityBookmarked(Long universityId, Long userId) {

		Validate
				.notNull(universityId, "DesktopService2.handleIsUniversityBookmarked - the universityId cannot be null");
		Validate.notNull(userId, "DesktopService2.handleIsUniversityBookmarked - the userId cannot be null");

		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university, "DesktopService2.handleIsUniversityBookmarked - "
				+ "no university found with the universityId " + universityId);

		User user = this.getUserDao().load(userId);
		Validate
				.notNull(user, "DesktopService2.handleIsUniversityBookmarked - no user found with the userId " + userId);

		// Desktop desktop =
		// this.getDesktopDao().findByUniversityAndUser(university, user);
		Desktop desktop = this.getDesktopDao().findByUser(user);
		if (desktop != null) {
			if (desktop.getUniversities().contains(university)) {
				return true;
			}
		}
		return false;

	}

	protected boolean handleIsDepartmentBookmarked(Long departmentId, Long userId) {

		Validate
				.notNull(departmentId, "DesktopService2.handleIsUniversityBookmarked - the departmentId cannot be null");
		Validate.notNull(userId, "DesktopService2.handleIsUniversityBookmarked - the userId cannot be null");

		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department, "DesktopService2.handleIsUniversityBookmarked - "
				+ "no department found with the departmentId " + departmentId);

		User user = this.getUserDao().load(userId);
		Validate
				.notNull(user, "DesktopService2.handleIsUniversityBookmarked - no user found with the userId " + userId);

		// Desktop desktop =
		// this.getDesktopDao().findByUniversityAndUser(university, user);
		Desktop desktop = this.getDesktopDao().findByUser(user);
		if (desktop != null) {
			if (desktop.getDepartments().contains(department)) {
				return true;
			}
		}
		return false;
	}

	protected boolean handleIsInstituteBookmarked(Long instituteId, Long userId) {

		Validate.notNull(instituteId, "DesktopService2.handleIsUniversityBookmarked - the instituteId cannot be null");
		Validate.notNull(userId, "DesktopService2.handleIsUniversityBookmarked - the userId cannot be null");

		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute, "DesktopService2.handleIsUniversityBookmarked - "
				+ "no institute found with the instituteId " + instituteId);

		User user = this.getUserDao().load(userId);
		Validate
				.notNull(user, "DesktopService2.handleIsUniversityBookmarked - no user found with the userId " + userId);

		// Desktop desktop =
		// this.getDesktopDao().findByUniversityAndUser(university, user);
		Desktop desktop = this.getDesktopDao().findByUser(user);
		if (desktop != null) {
			if (desktop.getInstitutes().contains(institute)) {
				return true;
			}
		}
		return false;
	}

	protected boolean handleIsCourseBookmarked(Long courseId, Long userId) {

		Validate.notNull(courseId, "DesktopService2.handleIsUniversityBookmarked - the instituteId cannot be null");
		Validate.notNull(userId, "DesktopService2.handleIsUniversityBookmarked - the userId cannot be null");

		Course course = this.getCourseDao().load(courseId);
		Validate.notNull(course, "DesktopService2.handleIsUniversityBookmarked - "
				+ "no course found with the courseId " + courseId);

		User user = this.getUserDao().load(userId);
		Validate
				.notNull(user, "DesktopService2.handleIsUniversityBookmarked - no user found with the userId " + userId);

		// Desktop desktop =
		// this.getDesktopDao().findByUniversityAndUser(university, user);
		Desktop desktop = this.getDesktopDao().findByUser(user);
		if (desktop != null) {
			if (desktop.getCourses().contains(course)) {
				return true;
			}
		}
		return false;
	}

	protected Map handleGetMyUniInfo(Long userId) {
		Validate.notNull(userId, "DesktopService2.handleFindDesktopByUser - userId cannot be null!");

		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "DesktopService2.handleFindDesktopByUser - No user found corresponding to the userId "
				+ userId);

		Desktop desktop = getDesktopDao().findByUser(user);

		if (desktop == null) {
			// create new desktop
			logger.debug("DesktopService2.handleFindDesktopByUser - desktop doesn't exist for user, create new one");
			desktop = new DesktopImpl();
			desktop.setUser(user);
			this.getDesktopDao().create(desktop);
		}

		MyUniDataSet myUniDataSet = new MyUniDataSet(desktop);
		myUniDataSet.loadData();
		return myUniDataSet.getMyUniInfo();
	}

	public static class MyUniDataSet {
		private Desktop desktop;
		private Map<Long, UniversityDataSet> uniDataSets;

		public MyUniDataSet(Desktop desktop) {
			this.desktop = desktop;
		}

		public void setDesktop(Desktop desktop) {
			this.desktop = desktop;
		}

		/*
		 * Fills the MyUni data structure
		 */
		public void loadData() {
			if (desktop == null)
				return;

			// Create a new hash map to hold the data for each university
			uniDataSets = new HashMap<Long, UniversityDataSet>();

			// Get the bookmarks from the user's desktop
			List<Course> courseBookmarks = desktop.getCourses();
			List<Institute> instituteBookmarks = desktop.getInstitutes();
			List<Department> departmentBookmarks = desktop.getDepartments();

			if (courseBookmarks != null) {
				// Process each course bookmark
				Iterator<Course> courseIterator = courseBookmarks.iterator();
				while (courseIterator.hasNext()) {
					Course course = (Course) courseIterator.next();
					processCourse(course);
				}
			}

			if (instituteBookmarks != null) {
				// Process each institute bookmark
				Iterator<Institute> instituteIterator = instituteBookmarks.iterator();
				while (instituteIterator.hasNext()) {
					Institute institute = (Institute) instituteIterator.next();
					processInstituteBookmark(institute);
				}
			}

			if (departmentBookmarks != null) {
				// Process each department bookmark
				Iterator<Department> departmentIterator = departmentBookmarks.iterator();
				while (departmentIterator.hasNext()) {
					Department department = (Department) departmentIterator.next();
					processDepartmentBookmark(department);
				}
			}
		}

		/*
		 * Returns a map of special info objects that hold the information to be
		 * displayed by the MyUni page, one for each university
		 */
		public Map<Long, MyUniInfo> getMyUniInfo() {
			// Create a new hash map of type MyUniInfo
			Map<Long, MyUniInfo> myUniInfo = new HashMap<Long, MyUniInfo>();

			// Return empty map if there is no data
			if (uniDataSets == null)
				return myUniInfo;

			MyUniUniversityInfo currentUniversityInfo;

			// Convert each university data set to info object and add it to the
			// map
			for (UniversityDataSet uniDataSet : uniDataSets.values()) {
				currentUniversityInfo = uniDataSet.getUnversity();
				if (currentUniversityInfo != null) {
					myUniInfo.put(currentUniversityInfo.getId(), uniDataSet.toInfo());
				}
			}

			return myUniInfo;
		}

		private Long processDepartment(Department department) {
			// Process the department as not bookmarked
			return processDepartment(department, false);
		}

		private Long processDepartmentBookmark(Department department) {
			// Process the department as bookmarked
			return processDepartment(department, true);
		}

		/*
		 * Adds a department to its corresponding university data set. If there
		 * is no university data set for the department's university a new one
		 * is created.
		 */
		private Long processDepartment(Department department, boolean bookmarked) {
			if (department == null)
				return null;

			University university = department.getUniversity();
			if (university == null)
				return null;

			Long universityID = university.getId();
			if (universityID == null)
				return null;

			// Create a new data set for the university if it does not exist yet
			assert uniDataSets != null;
			if (!uniDataSets.containsKey(universityID)) {
				UniversityDataSet universityDataSet = new UniversityDataSet(university);
				uniDataSets.put(universityID, universityDataSet);
			}

			// Add the department to the university data set
			uniDataSets.get(universityID).addDepartment(department, bookmarked);

			// Return the university id
			return universityID;
		}

		private Long processInstitute(Institute institute, boolean hasCurrentCourse) {
			// Process the institute as not bookmarked
			return processInstitute(institute, hasCurrentCourse, false);
		}

		private Long processInstituteBookmark(Institute institute) {
			// Process the institute as bookmarked
			// Argument for 'hasCurrentCourse' is false
			// to make sure the counting of current courses
			// for the institute is not increased by the bookmark
			return processInstitute(institute, false, true);
		}

		/*
		 * Processes the institute's department and adds the institute to the
		 * corresponding university data set @return the id of the institute's
		 * university
		 */
		private Long processInstitute(Institute institute, boolean hasCurrentCourse, boolean bookmarked) {
			if (institute == null)
				return null;

			// Process the department of the institute
			Department department = institute.getDepartment();
			if (department == null)
				return null;

			// Process the corresponding department and get the university id
			Long universityID = processDepartment(department);
			if (universityID == null)
				return null;

			// Get the corresponding university data set
			assert uniDataSets != null;
			UniversityDataSet currentDataSet = uniDataSets.get(universityID);
			assert currentDataSet != null;

			// Add the institute to the university data set
			currentDataSet.addInstitute(institute, hasCurrentCourse, bookmarked);

			// Return the university id
			return universityID;

		}

		/*
		 * Processes the course's institute and adds the course to the
		 * corresponding university data set @return the course's university id
		 */
		private Long processCourse(Course course) {
			if (course == null)
				return null;

			Period coursePeriod = course.getPeriod();
			boolean isCurrent;

			// Determine if the course is in a current period
			if (coursePeriod == null)
				isCurrent = false;
			else
				isCurrent = coursePeriod.isActive();

			// Get the course's c ourse type
			CourseType courseType = course.getCourseType();
			if (courseType == null)
				return null;

			// Get the course's institute
			Institute institute = courseType.getInstitute();
			if (institute == null)
				return null;

			// Process the corresponding institute
			// We are skipping the course type because course types don't show
			// up on the MyUni page
			Long universityID = processInstitute(institute, isCurrent);
			if (universityID == null)
				return null;

			// Get the corresponding university data set
			assert uniDataSets != null;
			UniversityDataSet currentDataSet = uniDataSets.get(universityID);
			assert currentDataSet != null;

			// Add the course to the university data set
			currentDataSet.addCourse(course, isCurrent);

			return universityID;

		}

		// Test data not working any longer because of entity objects in
		// add*-Methods in UniversityDataSet
		/*
		 * public void loadTestData() { logger.debug("Loading MyUni test data");
		 * 
		 * MyUniUniversityInfo uniInfo; MyUniDepartmentInfo departmentInfo;
		 * MyUniCourseInfo courseInfo; UniversityDataSet uniDataSet;
		 * 
		 *  // Create Uni 1 and Subitems uniInfo = new MyUniUniversityInfo();
		 * uniInfo.setId(1L); uniInfo.setName("Uni M�nster"); uniDataSet = new
		 * UniversityDataSet(uniInfo);
		 * 
		 * departmentInfo = new MyUniDepartmentInfo(); departmentInfo.setId(1L);
		 * departmentInfo.setName("Fachbereich 4");
		 * uniDataSet.addDepartment(departmentInfo);
		 * 
		 * departmentInfo = new MyUniDepartmentInfo(); departmentInfo.setId(2L);
		 * departmentInfo.setName("Fachbereich 5");
		 * uniDataSet.addDepartment(departmentInfo);
		 * 
		 * departmentInfo = new MyUniDepartmentInfo(); departmentInfo.setId(3L);
		 * departmentInfo.setName("Fachbereich 6");
		 * uniDataSet.addDepartment(departmentInfo);
		 * 
		 * courseInfo = new MyUniCourseInfo(); courseInfo.setId(1L);
		 * courseInfo.setName("KLR"); uniDataSet.addCourse(courseInfo, true);
		 * 
		 * courseInfo = new MyUniCourseInfo(); courseInfo.setId(2L);
		 * courseInfo.setName("BWL1"); uniDataSet.addCourse(courseInfo, true);
		 * 
		 * courseInfo = new MyUniCourseInfo(); courseInfo.setId(3L);
		 * courseInfo.setName("BWL2"); uniDataSet.addCourse(courseInfo, false);
		 * 
		 * courseInfo = new MyUniCourseInfo(); courseInfo.setId(4L);
		 * courseInfo.setName("BWL3"); uniDataSet.addCourse(courseInfo, false);
		 * 
		 * uniDataSets.put(1L, uniDataSet);
		 *  // Create Uni 2 and subitems uniInfo = new MyUniUniversityInfo();
		 * uniInfo.setId(2L); uniInfo.setName("Uni Bonn"); uniDataSet = new
		 * UniversityDataSet(uniInfo);
		 * 
		 * 
		 * departmentInfo = new MyUniDepartmentInfo(); departmentInfo.setId(4L);
		 * departmentInfo.setName("Fachbereich 4");
		 * uniDataSet.addDepartment(departmentInfo);
		 * 
		 * departmentInfo = new MyUniDepartmentInfo(); departmentInfo.setId(5L);
		 * departmentInfo.setName("Fachbereich 8");
		 * uniDataSet.addDepartment(departmentInfo);
		 * 
		 * 
		 * courseInfo = new MyUniCourseInfo(); courseInfo.setId(1L);
		 * courseInfo.setName("Kosten- und Leistungsrechnung");
		 * uniDataSet.addCourse(courseInfo, true);
		 * 
		 * courseInfo = new MyUniCourseInfo(); courseInfo.setId(2L);
		 * courseInfo.setName("Informatik 1"); uniDataSet.addCourse(courseInfo,
		 * true);
		 * 
		 * courseInfo = new MyUniCourseInfo(); courseInfo.setId(3L);
		 * courseInfo.setName("Informatik 2"); uniDataSet.addCourse(courseInfo,
		 * true);
		 * 
		 * courseInfo = new MyUniCourseInfo(); courseInfo.setId(4L);
		 * courseInfo.setName("Unternehmensgr�ndung M�rkte und Branchen");
		 * uniDataSet.addCourse(courseInfo, false);
		 * 
		 * uniDataSets.put(2L, uniDataSet);
		 *  // Create Uni 3 and subitems uniInfo = new UniversityInfo();
		 * uniInfo.setId(3L); uniInfo.setName("Uni K�ln"); uniDataSet = new
		 * UniversityDataSet(uniInfo);
		 * 
		 * departmentInfo = new MyUniDepartmentInfo(); departmentInfo.setId(6L);
		 * departmentInfo.setName("Fachbereich 1");
		 * departmentInfo.setUniversityId(3L);
		 * uniDataSet.addDepartment(departmentInfo);
		 * 
		 * departmentInfo = new MyUniDepartmentInfo(); departmentInfo.setId(7L);
		 * departmentInfo.setName("Fachbereich 7");
		 * departmentInfo.setUniversityId(3L);
		 * uniDataSet.addDepartment(departmentInfo);
		 * 
		 * departmentInfo = new MyUniDepartmentInfo(); departmentInfo.setId(8L);
		 * departmentInfo.setName("Fachbereich 8");
		 * departmentInfo.setUniversityId(8L);
		 * uniDataSet.addDepartment(departmentInfo);
		 * 
		 * 
		 * courseInfo = new MyUniCourseInfo(); courseInfo.setId(1L);
		 * courseInfo.setName("Einf�hrung in die WI");
		 * uniDataSet.addCourse(courseInfo, true);
		 * 
		 * courseInfo = new MyUniCourseInfo(); courseInfo.setId(2L);
		 * courseInfo.setName("Datenbanken"); uniDataSet.addCourse(courseInfo,
		 * false);
		 * 
		 * courseInfo = new MyUniCourseInfo(); courseInfo.setId(3L);
		 * courseInfo.setName("Einf�hrung in die Java Framework-Theorie");
		 * uniDataSet.addCourse(courseInfo, false);
		 * 
		 * courseInfo = new MyUniCourseInfo(); courseInfo.setId(4L);
		 * courseInfo.setName("OpenUSS Projektseminar");
		 * uniDataSet.addCourse(courseInfo, false);
		 * 
		 * 
		 * uniDataSets.put(3L, uniDataSet); }
		 */

		/*
		 * Holds all relevant information that is displayed on the MyUni page
		 * for one university
		 */
		private static class UniversityDataSet {
			Map<Long, MyUniCourseInfo> currentCourses;
			Map<Long, MyUniCourseInfo> pastCourses;
			Map<Long, MyUniInstituteInfo> currentInstitutes;
			Map<Long, MyUniInstituteInfo> pastInstitutes;
			Map<Long, MyUniDepartmentInfo> departments;
			Map<Long, Integer> instituteCurrentCoursesCount;
			MyUniUniversityInfo university;

			public UniversityDataSet(University university) {
				currentCourses = new HashMap<Long, MyUniCourseInfo>();
				pastCourses = new HashMap<Long, MyUniCourseInfo>();
				currentInstitutes = new HashMap<Long, MyUniInstituteInfo>();
				pastInstitutes = new HashMap<Long, MyUniInstituteInfo>();
				departments = new HashMap<Long, MyUniDepartmentInfo>();
				instituteCurrentCoursesCount = new HashMap<Long, Integer>();

				this.university = universityEntityToInfo(university);
			}

			/*
			 * Converts the UniversityDataSet object to MyUniInfo object
			 */
			public MyUniInfo toInfo() {
				MyUniInfo newInfo = new MyUniInfo();

				// Set the number of current courses for each current institute
				MyUniInstituteInfo currentInstituteInfo;
				Integer numberOfCurrentCourses;
				Iterator<MyUniInstituteInfo> iterator = currentInstitutes.values().iterator();
				while (iterator.hasNext()) {
					currentInstituteInfo = iterator.next();
					numberOfCurrentCourses = instituteCurrentCoursesCount.get(currentInstituteInfo.getId());

					if (numberOfCurrentCourses != null)
						currentInstituteInfo.setNumberOfCurrentCourses(numberOfCurrentCourses);
				}

				// Copy the data to the info object
				newInfo.setCurrentCourses(currentCourses.values());
				newInfo.setPastCourses(pastCourses.values());
				newInfo.setCurrentInstitutes(currentInstitutes.values());
				newInfo.setPastInstitutes(pastInstitutes.values());
				newInfo.setDepartments(departments.values());
				newInfo.setMyUniUniversityInfo(university);

				return newInfo;
			}

			public MyUniUniversityInfo getUnversity() {
				return university;
			}

			/*
			 * Adds a department to the data set and ensures proper handling of
			 * the department's bookmark flag
			 */
			public void addDepartment(Department department, boolean bookmarked) {
				// Convert entity to info object
				MyUniDepartmentInfo departmentInfo = departmentEntityToInfo(department);

				if (departmentInfo != null) {
					Long departmentId = departmentInfo.getId();

					if (departments.containsKey(departmentId)) {
						// The department has been added before
						// If this department is a bookmark
						// get the former department object from the departments
						// hash
						// and set the bookmark flag to true,
						// regardless of the former value
						if (bookmarked == true) {
							departmentInfo = departments.get(departmentId);
							departmentInfo.setBookmarked(true);
						}
					} else {
						// The department is added to the set for the first time
						departmentInfo.setBookmarked(bookmarked);
						departments.put(departmentId, departmentInfo);
					}
				}
			}

			/*
			 * Adds an institute to the data set and ensures proper handling of
			 * the institutes current/past state and bookmark flag
			 */
			public void addInstitute(Institute institute, boolean isCurrent, boolean bookmarked) {
				// Convert entity to info object
				MyUniInstituteInfo instituteInfo = instituteEntityToInfo(institute);

				if (instituteInfo != null) {
					Long instituteId = instituteInfo.getId();

					if (isCurrent == true || bookmarked == true) {
						// The institute is either current or bookmark
						// so it should appear in the visible list

						// Remove institute from the list of past institutes
						if (pastInstitutes.containsKey(instituteId))
							pastInstitutes.remove(instituteId);

						// Check if institute is already in the list of current
						// institutes
						if (currentInstitutes.containsKey(instituteId)) {
							// Institute is already in the list

							// If bookmarked,
							// get the former institute info object from the map
							// and set the bookmark flag to true,
							// regardless of its former value
							// (If not, leave the flag as it is,
							// because it might have been marked
							// as bookmarked in another iteration)
							if (bookmarked == true) {
								// Get the old InstituteInfo from the list of
								// current institutes
								instituteInfo = currentInstitutes.get(instituteId);
								instituteInfo.setBookmarked(true);
							}
						} else {
							// Institute is not in the list
							// If bookmarked, set the flag
							if (bookmarked == true)
								instituteInfo.setBookmarked(true);

							// Add institute to the list
							currentInstitutes.put(instituteId, instituteInfo);
						}
					} else // institute is not current and not bookmarked
					{
						// Add institute to the list of past institutes
						// only if it is not already contained
						// in the list of current institutes
						if (!currentInstitutes.containsKey(instituteId))
							pastInstitutes.put(instituteId, instituteInfo);
					}
				}
			}

			/*
			 * Adds a course to the data set and ensures proper handling of the
			 * courses current/past state
			 */
			public void addCourse(Course course, boolean isCurrent) {
				// Convert entity to info object
				MyUniCourseInfo courseInfo = courseEntityToInfo(course);

				if (courseInfo != null) {
					Long id = courseInfo.getId();

					if (isCurrent == true) {
						// The course is current so it should
						// appear only in the list of current courses

						// Remove the course from the list of past courses
						if (pastCourses.containsKey(id))
							pastCourses.remove(id);

						if (!currentCourses.containsKey(id)) {
							// The course has not been added to the list of
							// current courses yet
							// Add it to the list
							currentCourses.put(id, courseInfo);

							// Increase the counter of the current courses for
							// the corresponding institute
							CourseType courseType = course.getCourseType();
							if (courseType != null) {
								Institute institute = courseType.getInstitute();
								if (institute != null) {
									Long instituteId = institute.getId();
									if (instituteId != null) {
										Integer courseCount;
										courseCount = instituteCurrentCoursesCount.get(instituteId);

										if (courseCount == null)
											courseCount = 0;

										courseCount++;
										instituteCurrentCoursesCount.put(instituteId, courseCount);
									}
								}
							}
						}
					} else // (isCurrent is false)
					{
						// Add course to the list of past courses
						// only if it is not already contained
						// in the list of current courses
						if (!currentCourses.containsKey(id))
							pastCourses.put(id, courseInfo);
					}
				}
			}

			private MyUniUniversityInfo universityEntityToInfo(University university) {
				if (university != null) {
					Long uniId = university.getId();

					if (uniId == null)
						return null;

					MyUniUniversityInfo uniInfo = new MyUniUniversityInfo();
					uniInfo.setId(university.getId());
					uniInfo.setName(university.getName());
					return uniInfo;
				} else {
					return null;
				}
			}

			private MyUniDepartmentInfo departmentEntityToInfo(Department department) {
				if (department != null) {
					Long departmentId = department.getId();

					if (departmentId == null)
						return null;

					MyUniDepartmentInfo departmentInfo = new MyUniDepartmentInfo();
					departmentInfo.setId(departmentId);
					departmentInfo.setName(department.getName());
					departmentInfo.setBookmarked(false);
					return departmentInfo;
				} else {
					return null;
				}
			}

			private MyUniInstituteInfo instituteEntityToInfo(Institute institute) {
				if (institute != null) {
					Long instituteId = institute.getId();

					if (instituteId == null)
						return null;

					MyUniInstituteInfo instituteInfo = new MyUniInstituteInfo();
					instituteInfo.setId(instituteId);
					instituteInfo.setName(institute.getName());
					instituteInfo.setBookmarked(false);
					instituteInfo.setNumberOfCurrentCourses(0);

					return instituteInfo;
				} else {
					return null;
				}
			}

			private MyUniCourseInfo courseEntityToInfo(Course course) {
				if (course != null) {
					Long courseId = course.getId();

					if (courseId == null)
						return null;

					MyUniCourseInfo courseInfo = new MyUniCourseInfo();
					courseInfo.setId(courseId);
					courseInfo.setName(course.getName());

					Period coursePeriod = course.getPeriod();

					if (coursePeriod != null) {
						courseInfo.setPeriod(coursePeriod.getName());
						courseInfo.setPeriodId(coursePeriod.getId());
					}

					return courseInfo;
				} else {
					return null;
				}
			}
		}
	}
}