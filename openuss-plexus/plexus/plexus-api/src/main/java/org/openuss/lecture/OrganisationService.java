package org.openuss.lecture;

import java.util.List;

/**
 * Handles all matters that concern an Organisation - especially User and Group
 * management.
 * 
 * @author Ron Haus
 * @author Florian Dondorf
 */
public interface OrganisationService {

	/**
	 * Adds a new User to the List of Members of the Organisation.
	 * 
	 * @param userId
	 *            - The ID of the User that will be added to the Organisation as
	 *            a Member.
	 * @param organisationId
	 *            - The ID of the Organisation that the User will be added to
	 *            the List of Member.
	 * @throws IllegalArgumentException
	 *             when the User is already a Member or Aspirant.
	 */
	public void addMember(Long organisationId, Long userId);

	/**
	 * Removes a User from the List of Members of the Organisation.
	 * 
	 * @param userId
	 *            - The ID of the User that will be removed from the
	 *            Organisation as a Member.
	 * @param organisationId
	 *            - The ID of the Organisation that the User will be removed
	 *            from the List of Aspirants.
	 * @throws IllegalArgumentException
	 *             when the User has not been in the List of Members.
	 */
	public void removeMember(Long organisationId, Long userId);

	/**
	 * Adds a new User to the List of Aspirants of the Organisation.
	 * 
	 * @param userId
	 *            - The ID of the User that will be added to the Organisation as
	 *            an Aspirant.
	 * @param organisationId
	 *            - The ID of the Organisation that the User will be added to as
	 *            an Aspirant.
	 * @throws IllegalArgumentException
	 *             when the User is already a Member or Aspirant.
	 */
	public void addAspirant(Long organisationId, Long userId);

	/**
	 * acceptAspirant
	 * Removes a User from the List of Aspirants of the Organisation and adds
	 * this User to the List of Members of the Organisation.
	 * 
	 * @param userId
	 *            - The ID of the User that will be added to the Organisation as
	 *            an Aspirant.
	 * @param organisationId
	 *            - The ID of the Organisation that the User will be added to as
	 *            an Aspirant.
	 * @throws IllegalArgumentException
	 *             when the User is already a Member or has not been in the List
	 *             of Aspirant.
	 */
	public void acceptAspirant(Long organisationId, Long userId);

	/**
	 * <p>
	 * Removes a User from the List of Aspirants of the Organisation.
	 * </p>
	 * <p>
	 * 
	 * @param userId
	 *            - The ID of the User that will be added to the Organisation as
	 *            an Aspirant.
	 *            </p>
	 *            <p>
	 * @param organisationId
	 *            - The ID of the Organisation that the User will be added to as
	 *            an Aspirant.
	 *            </p>
	 *            <p>
	 * @throws IllegalArgumentException
	 *             when the User has not been in the List of Aspirant.
	 *             </p>
	 */
	public void rejectAspirant(Long organisationId, Long userId);

	/**
	 * <p>
	 * Creates a Group a new Group for the Organisation.
	 * </p>
	 * <p>
	 * 
	 * @param organisationId
	 *            - The ID of the Organisation.
	 *            </p>
	 *            <p>
	 * @param groupItem
	 *            - The Group data to be stored.
	 *            </p>
	 */
	public Long createGroup(Long organisationId, org.openuss.security.GroupItem groupItem);

	/**
	 * <p>
	 * Removes the Group from the Organisation.
	 * </p>
	 * <p>
	 * 
	 * @param organisationId
	 *            - The ID of the Organisation.
	 *            </p>
	 *            <p>
	 * @param groupId
	 *            - The ID of the Group.
	 *            </p>
	 */
	public void removeGroup(Long organisationId, Long groupId);

	/**
	 * <p>
	 * Adds a User to the Group.
	 * </p>
	 * <p>
	 * 
	 * @param userId
	 *            - The ID of the User.
	 *            </p>
	 *            <p>
	 * @param groupId
	 *            - The ID of the Group.
	 *            </p>
	 */
	public void addUserToGroup(Long userId, Long groupId);

	/**
	 * <p>
	 * Removes a User from the Group.
	 * </p>
	 * <p>
	 * 
	 * @param userId
	 *            - The ID of the User.
	 *            </p>
	 *            <p>
	 * @param groupId
	 *            - The ID of the Group.
	 *            </p>
	 */
	public void removeUserFromGroup(Long userId, Long groupId);

	/**
     * 
     */
	public void sendActivationCode(org.openuss.lecture.Organisation organisation);

	/**
	 * <p>
	 * Changes the status of an Organisation.
	 * </p>
	 * <p>
	 * 
	 * @param organisationId
	 *            - The ID of the Organisation.
	 *            </p>
	 *            <p>
	 * @param enabled
	 *            - The new status of the Organisation.
	 *            </p>
	 */
	public void setOrganisationEnabled(Long organisationId, boolean enabled);

	/**
	 * <p>
	 * Finds all Members that belong to the Organisation.
	 * </p>
	 * <p>
	 * 
	 * @param organisationId
	 *            - The ID of the Organisation.
	 *            </p>
	 *            <p>
	 * @return List of UserInfos
	 *         </p>
	 */
	public List findAllMembers(Long organisationId);

	/**
	 * <p>
	 * Finds all Aspirants that belong to the Organisation.
	 * </p>
	 * <p>
	 * 
	 * @param organisationId
	 *            - The ID of the Organisation.
	 *            </p>
	 *            <p>
	 * @return List of UserInfos
	 *         </p>
	 */
	public List findAllAspirants(Long organisationId);

	/**
	 * <p>
	 * Finds the corresponding Group.
	 * </p>
	 * <p>
	 * 
	 * @param groupId
	 *            - The ID of the Group.
	 *            </p>
	 *            <p>
	 * @return GroupItem
	 *         </p>
	 */
	public org.openuss.security.GroupItem findGroup(Long groupId);

	/**
     * 
     */
	public org.openuss.lecture.OrganisationHierarchy findDepartmentHierarchy(Long departmentId);

	/**
     * 
     */
	public org.openuss.lecture.OrganisationHierarchy findInstituteHierarchy(Long instituteId);

	/**
     * 
     */
	public org.openuss.lecture.OrganisationHierarchy findCourseHierarchy(Long courseId);

	/**
	 * <p>
	 * Finds all Groups that belong to the Organisation.
	 * </p>
	 * <p>
	 * 
	 * @param organisationId
	 *            - The ID of the Organisation.
	 *            </p>
	 *            <p>
	 * @return A list of GroupItems.
	 *         </p>
	 */
	public List findGroupsByOrganisation(Long organisationId);

}
