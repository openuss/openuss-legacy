// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.security.acegi.acl.EntityObjectIdentity;
import org.openuss.security.acl.ObjectIdentity;
import org.openuss.security.acl.Permission;

/**
 * @see org.openuss.security.SecurityService
 * @author Ron Haus
 * @author Ingo Dueppe
 */
public class SecurityServiceImpl extends SecurityServiceBase {

	private static final Logger logger = Logger.getLogger(SecurityServiceImpl.class);

	private static final String GROUP_PREFIX = "GROUP_";
	private static final String ROLE_PREFIX = "ROLE_";

	@Override
	protected Collection handleGetAllUsers() throws Exception {
		return getUserDao().loadAll(UserDao.TRANSFORM_USERINFO);
	}

	@Override
	protected UserInfo handleGetUser(Long userId) throws Exception {
		return (UserInfo) getUserDao().load(UserDao.TRANSFORM_USERINFO, userId);
	}

	@Override
	protected UserInfo handleGetUserByName(String name) throws Exception {
		return (UserInfo) getUserDao().findUserByUsername(UserDao.TRANSFORM_USERINFO, name.toLowerCase());
	}

	@Override
	protected UserInfo handleGetUserByEmail(String email) throws Exception {
		return (UserInfo) getUserDao().findUserByEmail(UserDao.TRANSFORM_USERINFO, email);
	}

	@Override
	protected UserInfo handleCreateUser(UserInfo userInfo) throws Exception {
		Validate.isTrue(userInfo.getId() == null, "User must not have an identifier!");
		
		validateUserInfoForRegistration(userInfo);

		User user = getUserDao().userInfoToEntity(userInfo);
		getUserDao().create(user);
		encodePassword(user);
		getUserDao().update(user);
		getUserDao().toUserInfo(user, userInfo);

		// Define object identity security
		createObjectIdentity(user, null);

		return userInfo;
	}

	private void validateUserInfoForRegistration(UserInfo userInfo) {
		if (!isValidUserName(null, userInfo.getUsername())) {
			throw new SecurityServiceException("Invalid username.");
		}
		if (StringUtils.isBlank(userInfo.getPassword())) {
			throw new SecurityServiceException("Password must not be empty");
		}
		if (StringUtils.isBlank(userInfo.getEmail())) {
			throw new SecurityServiceException("Email must be defined");
		}
		if (isNonExistingEmailAddress(userInfo, userInfo.getEmail()) != null) {
			throw new SecurityServiceException("Email adress already in use (shold not occur -> validator bypassed?) " + userInfo.getEmail());
		}
	}

	@Override
	protected void handleChangePassword(String password) throws Exception {
		Validate.notEmpty(password, "Password must not be empty");
		Validate.isTrue(password.length() > 5, "Password must be longer then 5 characters");
		User user = getUserDao().load(getCurrentUser().getId());
		user.setPassword(password);
		encodePassword(user);
		getUserDao().update(user);
		updateSecurityContext(user);
	}

	private void encodePassword(User user) {
		logger.info("encode user password");
		Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
		passwordEncoder.setEncodeHashAsBase64(true);
		user.setPassword(passwordEncoder.encodePassword(user.getPassword(), user.getId()));
	}

	@Override
	protected void handleSaveUser(User user) throws Exception {
		// Do not change the user password by this method use changeUserPassword
		// instead
		user.setPassword(getUserDao().getPassword(user.getId()));
		getUserDao().update(user);
	}

	@Override
	protected void handleRemoveUser(UserInfo user) throws Exception {
		getUserDao().remove(user.getId());
	}

	@Override
	protected void handleAddAuthorityToGroup(Authority authority, Group group) throws Exception {
		Validate.notNull(authority, "Authority must not be null");
		Validate.notNull(authority.getId(), " Authority must provide a valid id.");
		Validate.notNull(group, "Group must not be null");
		Validate.isTrue(!authority.equals(group), "Authority cannot become a member of itself");

		group = forceGroupLoad(group);
		authority = forceAuthorityLoad(authority);

		checkInheritanceConstraints(authority, group);

		authority.addGroup(group);
		group.addMember(authority);
		getGroupDao().update(group);

		updateSecurityContext(authority);

		removeAuthorityFromUserCache(authority);
	}

	private void updateSecurityContext(Authority authority) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext != null && authority instanceof UserImpl) {
			Authentication auth = securityContext.getAuthentication();
			if (auth != null && ObjectUtils.equals(auth.getPrincipal(), authority)) {
				logger.debug("refresing current user security context.");
				final UsernamePasswordAuthenticationToken authentication;
				authentication = new UsernamePasswordAuthenticationToken(((UserImpl) authority).getName(), "[Protected]",
						((UserImpl) authority).getAuthorities());
				securityContext.setAuthentication(authentication);
			}
		}
		
		//TODO check if UserContext of another user can updated
//		if (authority instanceof UserImpl) {
//			UserInfo userInfo = getUserDao().toUserInfo((User)authority);
//			getUserCache().removeUserFromCache(userInfo.getUsername());
//			getUserCache().putUserInCache(new UserInfoDetailsAdapter(userInfo,((UserImpl) authority).getGrantedAuthorities()));
//		}
	}

	private Authority forceAuthorityLoad(Authority authority) {
		authority = getAuthorityDao().load(authority.getId());
		if (authority == null) {
			throw new SecurityServiceException("Authority not found");
		}
		return authority;
	}

	private Group forceGroupLoad(Group group) {
		group = getGroupDao().load(group.getId());
		if (group == null) {
			throw new SecurityServiceException("Group not found!");
		}
		return group;
	}

	private void checkInheritanceConstraints(Authority authority, Group group) {
		List grantedGroups = group.getGrantedGroups();
		if (grantedGroups.contains(authority)) {
			throw new SecurityServiceException("Circular dependencies between authorities is not supported");
		}
	}

	private void removeAuthorityFromUserCache(Authority authority) {
		if (logger.isDebugEnabled()) {
			logger.debug("removing authority " + authority.getName() + " from cache!");
		}
		if (getUserCache() != null) {
			getUserCache().removeUserFromCache(authority.getName());
		}
	}

	@Override
	protected void handleRemoveAuthorityFromGroup(Authority authority, Group group) throws Exception {
		Validate.notNull(authority, "Authority must not be null.");
		Validate.notNull(authority.getId(), "Authority mut provide a valid id.");
		Validate.notNull(group, "Group must not be null.");

		group = forceGroupLoad(group);
		authority = forceAuthorityLoad(authority);

		authority.removeGroup(group);
		group.removeMember(authority);
		getGroupDao().update(group);

		updateSecurityContext(authority);

		removeAuthorityFromUserCache(authority);
	}

	@Override
	protected boolean handleIsValidUserName(UserInfo self, String userName) throws Exception {
		// username must not start with GROUP_ or ROLE_
		if (userName == null || userName.startsWith(GROUP_PREFIX) || userName.startsWith(ROLE_PREFIX)) {
			return false;
		}
		UserInfo userInfo = (UserInfo) getUserDao().findUserByUsername(UserDao.TRANSFORM_USERINFO, userName);
		if (self == null || userInfo == null) {
			return userInfo == null;
		} else {
			return self.equals(userInfo);
		}
	}

	@Override
	protected UserInfo handleIsNonExistingEmailAddress(UserInfo self, String email) throws Exception {
		UserInfo found = (UserInfo) getUserDao().findUserByEmail(UserDao.TRANSFORM_USERINFO, email);
		if (self == null || found == null) {
			return null;
		} else {
			return (self.equals(found)) ? null : found;
		}
	}

	@Override
	protected Group handleGetGroupByName(String name) {
		return getGroupDao().findGroupByName(name);
	}

	@Override
	protected Group handleGetGroup(Group group) {
		return getGroupDao().load(group.getId());
	}

	@Override
	protected Group handleCreateGroup(String name, String label, String password, GroupType groupType) throws Exception {
		if (!name.startsWith(GROUP_PREFIX) && !name.startsWith(ROLE_PREFIX)) {
			name = GROUP_PREFIX + name;
		}
		Group group = Group.Factory.newInstance();
		group.setName(name);
		group.setLabel(label);
		group.setPassword(password);
		group.setGroupType(groupType);
		getGroupDao().create(group);
		return group;
	}

	@Override
	protected void handleRemoveGroup(Group group) throws Exception {
		getGroupDao().remove(group);
	}

	@Override
	protected void handleSaveGroup(Group group) throws Exception {
		String name = group.getName();
		if (!name.startsWith(GROUP_PREFIX) && !name.startsWith(ROLE_PREFIX)) {
			group.setName(GROUP_PREFIX + name);
		}

		getGroupDao().update(group);
	}

	@Override
	protected ObjectIdentity handleCreateObjectIdentity(Object object, Object parent) throws Exception {
		// check parent object
		ObjectIdentity parentObjectIdentity = null;
		if (parent != null) {
			EntityObjectIdentity parentOI = new EntityObjectIdentity(parent);
			parentObjectIdentity = getObjectIdentityDao().load(parentOI.getIdentifier());

			if (parentObjectIdentity == null) {
				throw new SecurityServiceException("Object Identity to Object " + parent + " does not exist!");
			}
		}

		// analyse object if it is an entity object with an id
		EntityObjectIdentity entityOI = new EntityObjectIdentity(object);

		// define ObjectIdentity entity
		ObjectIdentity objectIdentity = ObjectIdentity.Factory.newInstance();
		objectIdentity.setId(entityOI.getIdentifier());
		objectIdentity.setParent(parentObjectIdentity);

		getObjectIdentityDao().create(objectIdentity);

		return objectIdentity;
	}

	@Override
	protected void handleRemoveObjectIdentity(Object object) throws Exception {
		ObjectIdentity oi = getObjectIdentity(object);
		getObjectIdentityDao().remove(oi);
		removeAclsFromCache(object);
	}

	@Override
	protected void handleSetPermissions(Authority authority, Object object, Integer mask) throws Exception {
		ObjectIdentity objectIdentity = getObjectIdentity(object);

		Permission permission = getPermissionDao().findPermission(objectIdentity, authority);
		if (permission == null) {
			// permission does not exist, create a new one
			permission = Permission.Factory.newInstance(mask, objectIdentity, authority);
			getPermissionDao().create(permission);
		} else {
			permission.setMask(mask);
			getPermissionDao().update(permission);
		}

		removeAclsFromCache(object);
		removeAuthorityFromUserCache(authority);
	}

	private void removeAclsFromCache(Object object) throws IllegalAccessException, InvocationTargetException {
		if (getAclCache() != null) {
			logger.debug("removing acls from cache for " + object);
			getAclCache().removeEntriesFromCache(new EntityObjectIdentity(object));
		}
	}

	@Override
	protected Collection handleGetAllGroups() throws Exception {
		GroupDao groupDao = getGroupDao();
		return groupDao.loadAll(GroupDao.TRANSFORM_GROUPITEM);
	}

	@Override
	protected List handleGetUsers(UserCriteria criteria) throws Exception {
		return getUserDao().findUsersByCriteria(UserDao.TRANSFORM_USERINFO, criteria);
	}

	@Override
	protected void handleSetLoginTime(UserInfo user) throws Exception {
		User usr = getUserDao().load(user.getId());
		if (usr == null) {
			logger.error("couldn't find user with id " + user.getId());
			throw new SecurityServiceException("Couldn't find user with id " + user.getId());
		}
		usr.setLastLogin(new Date());
		getUserDao().update(usr);
	}

	@Override
	protected void handleRemovePermission(Authority authority, Object object) throws Exception {
		Permission permission = getPermissions(authority, object);
		if (permission != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("removing permission for authority " + authority + " for " + object);
			}
			ObjectIdentity objectIdentity = permission.getAclObjectIdentity();
			permission.setRecipient(null);
			objectIdentity.removePermission(permission);
			getObjectIdentityDao().update(objectIdentity);
			getPermissionDao().remove(permission);
		} else {
			logger.debug("Permission entity for authority " + authority + " for " + object + " not found!");
		}
	}

	/**
	 * @see org.openuss.lecture.SecurityService#removeAllPermissions(Object)
	 */
	@SuppressWarnings( { "unchecked" })
	protected void handleRemoveAllPermissions(Object object) throws Exception {
		ObjectIdentity objectIdentity = getObjectIdentity(object);
		List<Permission> permissions = getPermissionDao().findPermissions(objectIdentity);

		for (Permission permission : permissions) {
			ObjectIdentity aclObjectIdentity = permission.getAclObjectIdentity();
			permission.setRecipient(null);
			aclObjectIdentity.removePermission(permission);
			getObjectIdentityDao().update(aclObjectIdentity);
			getPermissionDao().remove(permission);
		}
	}

	private ObjectIdentity getObjectIdentity(Object object) throws IllegalAccessException, InvocationTargetException {

		ObjectIdentity objectIdentity = getObjectIdentityDao().load(new EntityObjectIdentity(object).getIdentifier());
		if (objectIdentity == null) {
			throw new SecurityServiceException("ObjectIdentity doesn't exist for object " + object);
		}
		return objectIdentity;
	}

	@Override
	protected Permission handleGetPermissions(Authority authority, Object object) throws Exception {
		Validate.notNull(authority, "Parameter authority must not be null.");
		Validate.notNull(object, "Parameter object must not be null.");
		ObjectIdentity objectIdentity = getObjectIdentity(object);
		return getPermissionDao().findPermission(objectIdentity, authority);
	}

	@Override
	protected UserInfo handleGetCurrentUser() throws Exception {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null || context.getAuthentication() == null) {
			return null;
		}
		String name = context.getAuthentication().getName();
		return getUserByName(name);
	}

	@Override
	protected boolean handleHasPermission(Object domainObject, Integer[] permissions) throws Exception {
		// FIXME make SecurityService independent from AcegiUtils
		return AcegiUtils.hasPermission(domainObject, permissions);
	}

	@Override
	protected User handleGetUserObject(UserInfo user) throws Exception {
		if (user == null || user.getId() == null) {
			return null;
		}
		return getUserDao().load(user.getId());
	}

	@Override
	protected User handleGetUserObject(Long userId) throws Exception {
		return getUserDao().load(userId);
	}

	@Override
	protected void handleSaveUser(UserInfo user) throws Exception {
		if (user.getId() != null) {
			getUserDao().update(getUserDao().userInfoToEntity(user));
		} else if (user.getId() == null) {
			createUser(user);
		}

	}

	@Override
	protected String[] handleGetGrantedAuthorities(UserInfo userInfo) throws Exception {
		Validate.notNull(userInfo, "Parameter userInfo must not be null.");
		User user = getUserDao().load(userInfo.getId());
		if (user != null) {
			return ((UserImpl) user).getGrantedAuthorities(); 
		} else {
			return new String[0];
		}
	}

}
