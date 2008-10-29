package org.openuss.lecture;

import java.util.List;

/**
 * Handles all matters that concern an Department - this includes the
 * Application.
 * 
 * @author Ron Haus
 * @author Florian Dondorf
 */
public interface DepartmentService {

	/**
	 * creates a new department
	 * 
	 * @param departmentInfo
	 *            - The info object which should be created.
	 * @param userId
	 *            - the userId of the creating user.
	 * @return the departmentId
	 */
	public Long create(DepartmentInfo departmentInfo, Long userId);

	/**
	 * updates a department
	 * 
	 * @param departmentInfo
	 *            - The info object which should be updated.
	 */
	public void update(DepartmentInfo departmentInfo);

	/**
	 * removes a department
	 * 
	 * @param departmentId
	 *            - The id of the department which should be removed.
	 */
	public void removeDepartment(Long departmentId);

	public void removeCompleteDepartmentTree(Long departmentId);

	/**
	 * accepts the given application
	 * 
	 * @param applicationId
	 *            the application which is accepted.
	 * @param userId
	 *            - the user who confirms the application.
	 */
	public void acceptApplication(Long applicationId, Long userId);

	/**
	 * rejects the given application
	 * 
	 * @param applicationId
	 *            the application which is rejected.
	 */
	public void rejectApplication(Long applicationId);

	/**
	 * Signs off the given institute.
	 * 
	 * @param instituteId
	 *            - the id of the institute which is signed off.
	 */
	public void signoffInstitute(Long instituteId);

	/**
	 * sets the status of a department from enabled to disabled and the other
	 * way around.
	 * 
	 * @param departmentId
	 *            - the department for which the status should be set.
	 * @param status
	 *            - enabled or disabled
	 */
	public void setDepartmentStatus(Long departmentId, boolean status);

	/**
	 * finds a department by id.
	 * 
	 * @param departmentId
	 *            - The id of the department.
	 * @return departmentInfo The found departmentInfo.
	 */
	public DepartmentInfo findDepartment(Long departmentId);

	/**
	 * finds the departments of a given university.
	 * 
	 * @param universityId
	 *            - the id of the university.
	 * @return a collection of universityInfos.
	 */
	public List findDepartmentsByUniversity(Long universityId);

	/**
	 * Finds departments by a given university which are either enabled or
	 * disabled.
	 * 
	 * @param universityId
	 *            - the id of the university.
	 * @param enabled
	 *            - sets wheather enabled or disabled departments should be
	 *            found.
	 * @return a collection of found departmentInfos
	 */
	public List findDepartmentsByUniversityAndEnabled(Long universityId, boolean enabled);

	/**
	 * <p>
	 * finds a list of departments by a given university and a given
	 * departmentType.
	 * </p>
	 * <p>
	 * 
	 * @param universityId
	 *            - the id of the given university.
	 *            </p>
	 *            <p>
	 * @param type
	 *            - the departmentType.
	 *            </p>
	 *            <p>
	 * @return a collection of departmentInfos.
	 *         </p>
	 */
	public List findDepartmentsByUniversityAndType(Long universityId, DepartmentType type);

	/**
     * 
     */
	public List findDepartmentsByUniversityAndTypeAndEnabled(Long universityId,
			DepartmentType type, boolean enabled);

	/**
	 * <p>
	 * finds an application with the given applicationId.
	 * </p>
	 * <p>
	 * 
	 * @param applicationId
	 *            - the id of the application which should be found.
	 *            </p>
	 *            <p>
	 * @return the found applicationInfo object.
	 *         </p>
	 */
	public ApplicationInfo findApplication(Long applicationId);

	/**
	 * <p>
	 * finds a list of departments by a given departmentType.
	 * </p>
	 * <p>
	 * 
	 * @param departmentType
	 *            - the departmentType.
	 *            </p>
	 *            <p>
	 * @return a collection of departmentInfos.
	 *         </p>
	 */
	public List findDepartmentsByType(DepartmentType type);

	/**
	 * <p>
	 * finds a list of applications by a given department.
	 * </p>
	 * <p>
	 * 
	 * @param departmentId
	 *            - the id of department which applications should be found.
	 * @return a collection of appliactionInfos.
	 */
	public List findApplicationsByDepartment(Long departmentId);

	/**
	 * finds a list of aplications by a given department and the confirmed
	 * value.
	 * 
	 * @param departmentId
	 *            - the id of the department for which the applications should
	 *            be found.
	 * @param confirmed
	 *            - sets the value to find either confirmed or unconfirmed
	 *            applications.
	 * @return a collection of applicationInfos.
	 */
	public List findApplicationsByDepartmentAndConfirmed(Long departmentId, boolean confirmed);

}
