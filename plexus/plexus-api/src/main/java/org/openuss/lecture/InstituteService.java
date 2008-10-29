package org.openuss.lecture;

import java.util.List;

/**
 * Handles all matters that concern an Institute - this includes the
 * Application.
 * 
 * @author Ron Haus
 * @author Florian Dondorf
 */
public interface InstituteService {

	/**
	 * Creates a new Institute. By that it has to be assigned to a Department.
	 * 
	 * @param instituteInfo
	 *            - The InfoObject containing the data necessary to create a new
	 *            Institute.
	 * @param userId
	 *            - The ID of the User that becomes the first Administrator of
	 *            the Institute.
	 * @return The ID of the newly created Institute.
	 */
	public Long create(InstituteInfo instituteInfo, Long userId);

	public void update(InstituteInfo instituteInfo);

	public void removeInstitute(Long instituteId);

	/**
	 * @deprecated
	 */
	public Long applyAtDepartment(ApplicationInfo applicationInfo);

	/**
	 * Applies an Institute at a Department. Is the given Department a
	 * non-official one, the Application will be confirmed, otherwise the
	 * Institute will be assigned to the default Department of the University
	 * and a new open Application for the offical Department is created.
	 * 
	 * @param instituteId
	 *            - The ID of the Institute.
	 * @param departmentId
	 *            - The ID of the Department.
	 * @param userId
	 *            - The ID of the applying User.
	 */
	public Long applyAtDepartment(Long instituteId, Long departmentId, Long userId);

	public void setInstituteStatus(Long instituteId, boolean status);

	public void removeCompleteInstituteTree(Long instituteId);

	public void setGroupOfMember(InstituteMember member, Long instituteId);

	public void resendActivationCode(InstituteInfo instituteInfo, Long userId);

	public InstituteSecurity getInstituteSecurity(Long instituteId);

	/**
	 * Retrieve all institutes.
	 * 
	 * @param enabledOnly
	 *            - filter all disabled insitutes out
	 * @return List<InstituteInfo> - list of institute info objects
	 */
	public List findAllInstitutes(boolean enabledOnly);

	public List findApplicationsByInstitute(Long instituteId);

	public ApplicationInfo findApplicationByInstituteAndConfirmed(Long instituteId, boolean confirmed);

	public InstituteInfo findInstitute(Long instituteId);

	public List findInstitutesByDepartment(Long departmentId);

	public List findInstitutesByDepartmentAndEnabled(Long departmentId, boolean enabled);

}
