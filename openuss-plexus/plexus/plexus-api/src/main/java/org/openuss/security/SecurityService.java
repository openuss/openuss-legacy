package org.openuss.security;

import java.util.Collection;
import java.util.List;

/**
 * 
 */
public interface SecurityService {

	/**
     * 
     */
	public Collection getAllUsers();

	/**
     * 
     */
	public Collection getAllGroups();

	/**
	 * <p>
	 * 
	 * @return List<UserDetail>
	 *         </p>
	 */
	public List getUsers(org.openuss.security.UserCriteria criteria);

	/**
     * 
     */
	public org.openuss.security.UserInfo getUser(Long userId);

	/**
     * 
     */
	public org.openuss.security.UserInfo getUserByName(String name);

	/**
     * 
     */
	public org.openuss.security.UserInfo createUser(org.openuss.security.UserInfo user);

	/**
	 * <p>
	 * Change password of current the user.
	 * </p>
	 */
	public void changePassword(String password);

	/**
     * 
     */
	public void saveUser(org.openuss.security.UserInfo user);

	/**
	 * <p>
	 * this is executed by the user, the user is set inactive to make it
	 * possible to reactivate, after a specified time user can be removed
	 * permanently by removeUserPermanently - method through an administrator
	 * </p>
	 */
	public void removeUser(org.openuss.security.UserInfo user);

	/**
	 * <p>
	 * Assigns the defined authority to the user. The user object must be an
	 * already persistent object.
	 * </p>
	 * <p>
	 * 
	 * @param user
	 *            </p>
	 *            <p>
	 * @param authorityID
	 *            </p>
	 */
	public void addAuthorityToGroup(org.openuss.security.Authority authority, org.openuss.security.Group group);

	/**
     * 
     */
	public void removeAuthorityFromGroup(org.openuss.security.Authority authority, org.openuss.security.Group group);

	/**
	 * <p>
	 * Checks whether or not the given username is valid. This means that it
	 * must no start with <code>GROUP_</code> or <code>ROLE_</code> and that the
	 * username doesn't already exists within the database. If the self
	 * parameter is not null the method will ignore the self entry within the
	 * database otherwise updating of a user would always lead into a user
	 * already exist error.
	 * </p>
	 */
	public boolean isValidUserName(org.openuss.security.UserInfo self, String userName);

	/**
	 * <p>
	 * Checks whether or not a user exists with the given email address. If the
	 * self parameter is not null the self entry will be ignored. If the email
	 * address doesn't exist the method return null if it exists it will return
	 * the user.
	 * </p>
	 */
	public org.openuss.security.UserInfo isNonExistingEmailAddress(org.openuss.security.UserInfo self, String email);

	/**
     * 
     */
	public org.openuss.security.Group getGroupByName(String name);

	/**
     * 
     */
	public org.openuss.security.Group getGroup(org.openuss.security.Group group);

	/**
     * 
     */
	public org.openuss.security.Group createGroup(String name, String label, String password,
			org.openuss.security.GroupType groupType);

	/**
     * 
     */
	public void removeGroup(org.openuss.security.Group group);

	/**
     * 
     */
	public void saveGroup(org.openuss.security.Group group);

	/**
	 * <p>
	 * Creates a new object identity.
	 * </p>
	 * <p>
	 * 
	 * @Param object instance to generate the object identity
	 *        </p>
	 *        <p>
	 * @Param optional parent object that inherit the permissions
	 *        </p>
	 */
	public org.openuss.security.acl.ObjectIdentity createObjectIdentity(Object object, Object parent);

	/**
     * 
     */
	public void removeObjectIdentity(Object object);

	/**
	 * <p>
	 * Removes the permission entry from the system
	 * </p>
	 */
	public void removePermission(org.openuss.security.Authority authority, Object object);

	/**
	 * <p>
	 * Removes all available Permissions from an Object.
	 * </p>
	 * <p>
	 * 
	 * @param object
	 *            Object to be freed from all Permissions
	 *            </p>
	 *            <p>
	 * @author Ron Haus
	 *         </p>
	 */
	public void removeAllPermissions(Object object);

	/**
	 * <p>
	 * Defines the permission for a user or a authority to a domain object.
	 * </p>
	 * <p>
	 * 
	 * @param authority
	 *            who gets
	 *            </p>
	 *            <p>
	 * @param mask
	 *            the permission bit mask
	 *            </p>
	 *            <p>
	 * @param object
	 *            on which domain object
	 *            </p>
	 */
	public void setPermissions(org.openuss.security.Authority authority, Object object, Integer mask);

	/**
     * 
     */
	public org.openuss.security.acl.Permission getPermissions(org.openuss.security.Authority authority, Object object);

	/**
	 * <p>
	 * Sets the lastLogin time of the user to the current server time
	 * </p>
	 */
	public void setLoginTime(org.openuss.security.UserInfo user);

	/**
     * 
     */
	public org.openuss.security.UserInfo getUserByEmail(String email);

	/**
     * 
     */
	public org.openuss.security.UserInfo getCurrentUser();

	/**
     * 
     */
	public boolean hasPermission(Object domainObject, Integer[] permissions);

	/**
     * 
     */
	public org.openuss.security.User getUserObject(org.openuss.security.UserInfo user);

	/**
     * 
     */
	public org.openuss.security.User getUserObject(Long userId);

	/**
     * 
     */
	public void saveUser(org.openuss.security.User user);

	/**
     * 
     */
	public String[] getGrantedAuthorities(org.openuss.security.UserInfo userInfo);

	/**
     * 
     */
	public void removeUserPermanently(org.openuss.security.UserInfo user);

	/**
     * 
     */
	public void removePersonalInformation(org.openuss.security.User user);

}
