package org.openuss.desktop;

import java.util.List;
import java.util.Map;

/**
 * @author Ingo Dueppe
 */
public interface DesktopService2 {

	public Long createDesktop(Long userId) throws DesktopException;

	public void updateDesktop(DesktopInfo desktop) throws DesktopException;

	public void linkUniversity(Long desktopId, Long universityId) throws DesktopException;

	public void linkDepartment(Long desktopId, Long departmentId) throws DesktopException;

	public void linkInstitute(Long desktopId, Long instituteId) throws DesktopException;

	/**
	 * @deprecated It's no longer intended to link CourseTypes
	 */
	@Deprecated
	public void linkCourseType(Long desktopId, Long courseTypeId) throws DesktopException;

	public void linkCourse(Long desktopId, Long courseId) throws DesktopException;

	public void unlinkUniversity(Long desktopId, Long universityId) throws DesktopException;

	public void unlinkDepartment(Long desktopId, Long departmentId) throws DesktopException;

	public void unlinkInstitute(Long desktopId, Long instituteId) throws DesktopException;

	/**
	 * @deprecated CourseTypes should not have been linked
	 */
	@Deprecated
	public void unlinkCourseType(Long desktopId, Long courseTypeId) throws DesktopException;

	public void unlinkCourse(Long desktopId, Long courseId) throws DesktopException;

	public void unlinkAllFromUniversity(Long universityId) throws DesktopException;

	public void unlinkAllFromDepartment(Long departmentId) throws DesktopException;

	public void unlinkAllFromInstitute(Long instituteId) throws DesktopException;

	/**
	 * @deprecated CourseTypes should not have been linked
	 */
	@Deprecated
	public void unlinkAllFromCourseType(Long courseTypeId) throws DesktopException;

	public void unlinkAllFromCourse(Long courseId) throws DesktopException;

	public List findLinkedDepartmentsByUserAndUniversity(Long userId, Long universityId) throws DesktopException;

	public List findAdditionalDepartmentsByUserAndUniversity(Long userId, Long universityId) throws DesktopException;

	public List findLinkedInstitutesByUserAndUniversity(Long userId, Long universityId) throws DesktopException;

	public List findAdditionalInstitutesByUserAndUniversity(Long userId, Long universityId) throws DesktopException;

	public List findLinkedCoursesByUserAndUniversity(Long userId, Long universityId) throws DesktopException;

	public DesktopInfo findDesktop(Long desktopId) throws DesktopException;

	public DesktopInfo findDesktopByUser(Long userId) throws DesktopException;

	public Map getMyUniInfo(Long userId) throws DesktopException;

}
