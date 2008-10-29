package org.openuss.lecture;

import java.util.List;

/**
 * <p>
 * Handles all matters that concern an University - this includes the Period.
 * </p>
 * <p>
 * 
 * @author Ron Haus
 *         </p>
 *         <p>
 * @author Florian Dondorf
 *         </p>
 *         <p>
 * @author Malte Stockmann
 *         </p>
 */
public interface UniversityService {

	/**
	 * <p>
	 * Creates a new University. This includes the basic data as well as group
	 * and security setting. During this process also a default non-official
	 * department and a default period is created.
	 * </p>
	 * <p>
	 * The presented User will become the first Member and Administrator.
	 * </p>
	 * <p>
	 * 
	 * @param universityInfo
	 *            - The InfoObject containing the data necessary to create a new
	 *            University.
	 *            </p>
	 *            <p>
	 * @param userId
	 *            - The ID of the User that becomes the first Administrator of
	 *            the University.
	 *            </p>
	 *            <p>
	 * @return The ID of the newly created University.
	 *         </p>
	 */
	public Long createUniversity(org.openuss.lecture.UniversityInfo universityInfo, Long userId);

	/**
	 * <p>
	 * Creates a new Period.
	 * </p>
	 * <p>
	 * 
	 * @param periodInfo
	 *            - The InfoObject containing the data necessary to create a new
	 *            Period.
	 *            </p>
	 *            <p>
	 * @return The ID of the newly created Period.
	 *         </p>
	 */
	public Long createPeriod(org.openuss.lecture.PeriodInfo periodInfo);

	/**
	 * <p>
	 * Updates the basic data of the University.
	 * </p>
	 * <p>
	 * 
	 * @param universityInfo
	 *            - The InfoObject containing the data necessary to update the
	 *            University.
	 *            </p>
	 */
	public void update(org.openuss.lecture.UniversityInfo universityInfo);

	/**
	 * <p>
	 * Updates the basic data of the Period.
	 * </p>
	 * <p>
	 * 
	 * @param periodInfo
	 *            - The InfoObject containing the data necessary to update the
	 *            Period.
	 *            </p>
	 *            <p>
	 * @throws UniversityServiceException
	 *             if one tries to change the University or the type (default)
	 *             of the Period.
	 *             </p>
	 */
	public void update(org.openuss.lecture.PeriodInfo periodInfo);

	/**
	 * <p>
	 * Removes the University
	 * </p>
	 * <p>
	 * 
	 * @param universityId
	 *            - The ID of the University.
	 *            </p>
	 *            <p>
	 * @throws IllegalArgumentException
	 *             when the University still contains Departments.
	 *             </p>
	 */
	public void removeUniversity(Long universityId);

	/**
	 * <p>
	 * Removes the University including all Periods and Organisations below it.
	 * </p>
	 * <p>
	 * 
	 * @param universityId
	 *            - The ID of the University.
	 *            </p>
	 */
	public void removeCompleteUniversityTree(Long universityId);

	/**
	 * <p>
	 * Removes the Period.
	 * </p>
	 * <p>
	 * 
	 * @param periodId
	 *            - The ID of the Period.
	 *            </p>
	 *            <p>
	 * @throws IllegalArgumentException
	 *             when the Period still contains Courses.
	 *             </p>
	 */
	public void removePeriod(Long periodId);

	/**
     * 
     */
	public void removePeriodAndCourses(Long periodId);

	/**
     * 
     */
	public void setUniversityStatus(Long universityId, boolean status);

	/**
	 * <p>
	 * 
	 * @deprecated Use Boolean flag "isActive" of Period instead
	 *             </p>
	 */
	public boolean isActivePeriod(Long periodId);

	/**
	 * <p>
	 * Finds the University.
	 * </p>
	 * <p>
	 * 
	 * @param universityId
	 *            - The ID of the University
	 *            </p>
	 *            <p>
	 * @return UniversityInfo
	 *         </p>
	 */
	public org.openuss.lecture.UniversityInfo findUniversity(Long universityId);

	/**
	 * <p>
	 * Finds all Universities.
	 * </p>
	 * <p>
	 * 
	 * @return List of UniversityInfos
	 *         </p>
	 */
	public List findAllUniversities();

	/**
	 * <p>
	 * Finds all Universities with the given status.
	 * </p>
	 * <p>
	 * 
	 * @param enabled
	 *            - The status of the University.
	 *            </p>
	 *            <p>
	 * @return List of UniversityInfos
	 *         </p>
	 */
	public List findUniversitiesByEnabled(boolean enabled);

	/**
	 * <p>
	 * Finds the Period.
	 * </p>
	 * <p>
	 * 
	 * @param periodId
	 *            - The ID of the Period.
	 *            </p>
	 *            <p>
	 * @return PeriodInfo
	 *         </p>
	 */
	public org.openuss.lecture.PeriodInfo findPeriod(Long periodId);

	/**
	 * <p>
	 * Finds all Periods of the University.
	 * </p>
	 * <p>
	 * 
	 * @param universityId
	 *            - The ID of the University.
	 *            </p>
	 *            <p>
	 * @return List of PeriodInfos
	 *         </p>
	 */
	public List findPeriodsByUniversity(Long universityId);

	/**
	 * <p>
	 * Finds all Periods of the University which are active or not active.
	 * </p>
	 * <p>
	 * 
	 * @param universityId
	 *            - The ID of the University.
	 *            </p>
	 *            <p>
	 * @param active
	 *            - The status of the Period.
	 *            </p>
	 */
	public List findPeriodsByUniversityAndActivation(Long universityId, boolean active);

	/**
     * 
     */
	public List findUniversitiesByTypeAndEnabled(org.openuss.lecture.UniversityType universityType, boolean enabled);

	/**
     * 
     */
	public List findUniversitiesByMemberAndEnabled(Long userId, boolean enabled);

	/**
     * 
     */
	public List findPeriodsByUniversityWithCourses(Long universityId);

	/**
     * 
     */
	public List findPeriodsByInstituteWithCoursesOrActive(org.openuss.lecture.InstituteInfo instituteInfo);

}
