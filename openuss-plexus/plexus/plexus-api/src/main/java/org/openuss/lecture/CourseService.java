package org.openuss.lecture;

import java.util.List;

/**
 * Handles all matters that concern a Course.
 * 
 * @author Ron Haus
 * @author Florian Dondorf
 * @author Ingo Dueppe
 */
public interface CourseService {

	/**
	 * remove member from enrollment membership.
	 */
	public void removeMember(CourseMemberInfo member);

	/**
	 * Accept aspirant as member.
	 */
	public void acceptAspirant(CourseMemberInfo member);

	/**
	 * Reject aspirant for membership.
	 */
	public void rejectAspirant(CourseMemberInfo member);

	public List getAspirants(CourseInfo course);

	public void addAssistant(CourseInfo course, org.openuss.security.UserInfo user);

	public void addAspirant(CourseInfo course, org.openuss.security.UserInfo user);

	public void addParticipant(CourseInfo course, org.openuss.security.UserInfo user);

	/**
	 * Apply user for course as aspirant or participants. <br/>
	 * The result of the methode depends on the <code>AccessType</code> of the
	 * course:
	 * <li> <code>open / anonymous</code> - The user will become a participant of
	 * the course.</li>
	 * <li> <code> application </code> - The user will become an asipirant of the
	 * course and the assistant will recieve an notification</li>
	 * <li> <code> password </code> - If the password is correct the user become
	 * a participant of the course otherwise an exception is thrown.</li>
	 * <li> <code> closed </code> - An exception is throw.</li></ol>
	 * 
	 * @param courseId
	 *            - Identifier of the course
	 * @param userId
	 *            - Identifier of the aspirant / participant
	 * @param password
	 *            - Optional password for the course
	 * @throws CourseServiceApplicationException
	 *             - if user can&apost add as participant or aspirant to the
	 *             course.
	 */
	public void applyUser(CourseInfo courseInfo, org.openuss.security.UserInfo userInfo,
			String password);

	/**
	 * Apply user for course as aspirant or participants. <br/>
	 * The result of the methode depends on the <code>AccessType</code> of the
	 * course:
	 * <li> <code>open / anonymous</code> - The user will become a participant of
	 * the course.</li>
	 * <li> <code> application </code> - The user will become an asipirant of the
	 * course and the assistant will recieve an notification</li>
	 * <li> <code> closed </code> - An exception is throw.
	 * 
	 * @param courseId
	 *            - Identifier of the course
	 * @param userId
	 *            - Identifier of the aspirant / participant
	 * @throws CourseServiceApplicationException
	 *             - if user can't add as participant or aspirant to the course.
	 */
	public void applyUser(CourseInfo courseInfo, org.openuss.security.UserInfo userInfo);

	/**
	 * <p>
	 * Remove all aspirants for course info
	 * </p>
	 */
	public void removeAspirants(CourseInfo course);

	public void updateCourse(CourseInfo course);

	public Long create(CourseInfo courseInfo);

	public void removeCourse(Long courseId);

	public void setCourseStatus(Long courseId, boolean status);

	public boolean isNoneExistingCourseShortcut(CourseInfo self, String shortcut);

	public List getAssistants(CourseInfo course);

	public List getParticipants(CourseInfo course);

	public CourseMemberInfo getMemberInfo(CourseInfo course,
			org.openuss.security.UserInfo user);

	public CourseInfo findCourse(Long courseId);

	/**
	 * Retrieve a list of course according to the course type.
	 * 
	 * @param courseTypeId
	 *            - identifier of the course type
	 *            <p>
	 * @return List<Course>
	 *         </p>
	 */
	public List findCoursesByCourseType(Long courseTypeId);

	public List findCoursesByPeriodAndInstitute(Long periodId, Long instituteId);

	public List findCoursesByActivePeriods(InstituteInfo instituteInfo);

	public List findCoursesByInstituteAndEnabled(Long instituteId, boolean enabled);

	public List findCoursesByPeriodAndInstituteAndEnabled(Long periodId, Long instituteId, boolean enabled);

	public List findCoursesByActivePeriodsAndEnabled(Long instituteId, boolean enabled);

	public List findAllCoursesByInstitute(Long instituteId);

	/**
	 * finds all courses by the departsment which is given by the domainId
	 * 
	 * @param departmentId
	 *            : Id of the department
	 * @param onlyActive
	 *            : only the active courses are selected
	 * @param onlyEnabled
	 *            : only the enabled courses are selected
	 * @result List of CourseInfo
	 */
	public List findAllCoursesByDepartment(Long departmentId, Boolean onlyActive, Boolean onlyEnabled);

	/**
	 * finds all courses by the departsment and a specific period
	 * 
	 * @param departmentId
	 *            : Id of the department
	 * @param periodId
	 *            : only the courses of the given period are selected
	 * @param onlyEnabled
	 *            : only the enabled courses are selected
	 * @result List of CourseInfo
	 */
	public List findAllCoursesByDepartmentAndPeriod(Long departmentId, Long periodId, Boolean onlyEnabled);

	public void removeAssistant(CourseMemberInfo assistant);

}
