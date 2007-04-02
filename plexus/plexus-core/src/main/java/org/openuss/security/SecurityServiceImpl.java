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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.security.acegi.acl.EntityObjectIdentity;
import org.openuss.security.acl.ObjectIdentity;
import org.openuss.security.acl.Permission;

/**
 * @see org.openuss.security.SecurityService
 */
public class SecurityServiceImpl extends org.openuss.security.SecurityServiceBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SecurityServiceImpl.class);
	
	private static final String GROUP_PREFIX = "GROUP_";
	private static final String ROLE_PREFIX = "ROLE_";

	@Override
	protected Collection handleGetAllUsers() throws Exception{
		return getUserDao().loadAll();
	}
	
	@Override
	protected User handleGetUser(Long userId) throws Exception {
		return getUserDao().load(userId);
	}
	
	@Override
	protected User handleGetUserByName(String name) throws Exception {
		return getUserDao().findUserByUsername(name.toLowerCase());
	}
	
	@Override
	protected User handleGetUserByEmail(String email) throws Exception{
		return getUserDao().findUserByEmail(email);
	}
	
	@Override
	protected User handleCreateUser(User user) throws Exception {
		if (user.getId() != null) {
			throw new IllegalArgumentException("user must not have an identifier!");
		}
		if (!isValidUserName(null, user.getUsername())) {
			throw new SecurityServiceException("Invalid username.");
		}
		if (StringUtils.isBlank(user.getPassword())) {
			throw new SecurityServiceException("Password must not be empty");
		}
		if (StringUtils.isBlank(user.getEmail())) {
			throw new SecurityServiceException("Email must be defined");
		}
		if (user.getProfile() == null) {
			user.setProfile(UserProfile.Factory.newInstance());
		}
		
		user = getUserDao().create(user);
		
		Group roleAdmin = getGroupDao().load(Roles.ADMINISTRATOR_ID);
		if (roleAdmin.getMembers().size() == 0) {
			logger.info("User "+user.getUsername()+" is the first user and achieve administrator role!");
			handleAddAuthorityToGroup(user, roleAdmin);
		}
		
		// define object identity
		createObjectIdentity(user, null);
		
		return user;
	}

	@Override
	protected void handleSaveUser(User user) throws Exception {
		if (StringUtils.isBlank(user.getPassword())) {
			// no password so inject the persistent one
			String password = getUserDao().getPassword(user.getId());
			user.setPassword(password);
		}
		getUserDao().update(user);
	}
	
	@Override
	protected void handleSaveUserProfile(UserProfile profile) throws Exception {
		if (profile.getId() == null) {
			getUserProfileDao().create(profile);
		} else {
			getUserProfileDao().update(profile);
		}
	}

	@Override
	protected void handleSaveUserContact(UserContact contact) throws Exception {
		if (contact.getId() == null) {
			getUserContactDao().create(contact);
		} else {
			getUserContactDao().update(contact);
		}
		
	}
	
	@Override
	protected void handleSaveUserPreferences(UserPreferences preferences) throws Exception {
		if (preferences.getId() == null) {
			getUserPreferencesDao().create(preferences);
		} else {
			getUserPreferencesDao().update(preferences);
		}
	}

	@Override
	protected void handleRemoveUser(User user) throws Exception {
		getUserDao().remove(user.getId());
	}


	@Override
	protected void handleAddAuthorityToGroup(Authority authority, Group group) throws Exception {
		if (authority == null) {
			throw new IllegalArgumentException("Authority must not be null!");
		}
		if (authority.getId() == null) {
			throw new IllegalArgumentException("Authority must be persistent with a valid id!");
		}
		if (authority.equals(group)) {
			throw new SecurityServiceException("Authority cannot be a member of itselfs.");
		}
		
		// refresh group object
		final GroupDao groupDao = getGroupDao();
		group = groupDao.load(group.getId());
		
		if (group == null) {
			throw new SecurityServiceException("Group cannot be found!");
		}

		// check inheritance constraints
		List grantedGroups = group.getGrantedGroups();
		if (grantedGroups.contains(authority) ) {
			throw new SecurityServiceException("Circular dependencies between authorities is not supported");
		}
		
		authority.addGroup(group);
		group.addMember(authority);
		groupDao.update(group);
		
		removeAuthorityFromUserCache(authority);
	}

	private void removeAuthorityFromUserCache(Authority authority) {
		if (logger.isDebugEnabled()) {
			logger.debug("removing authority "+authority.getName()+" from cache!");
		}
		getUserCache().removeUserFromCache(authority.getName());
	}

	@Override
	protected void handleRemoveAuthorityFromGroup(Authority authority, Group group) throws Exception {
		if (authority == null) {
			throw new IllegalArgumentException("Authority must not be null!");
		}
		if (group == null) {
			throw new IllegalArgumentException("Group must not be null");
		}
		if (authority.getId() == null) {
			throw new IllegalArgumentException("Authority must be persistent with a valid id!");
		}
		
		// refresh group 
		final GroupDao groupDao = getGroupDao();
		group = groupDao.load(group.getId());
		
		if (group == null) {
			throw new IllegalStateException("Group does not exist!");  
		}
		
		authority.removeGroup(group);
		group.removeMember(authority);
		
		groupDao.update(group);
		
		removeAuthorityFromUserCache(authority);
	}

	@Override
	protected boolean handleIsValidUserName(User self, String userName) throws Exception {
		// username must not start with GROUP_ or ROLE_
		if (userName.startsWith(GROUP_PREFIX) || userName.startsWith(ROLE_PREFIX)) {
			return false;
		}
		User user = getUserDao().findUserByUsername(userName);
		if (self == null || user == null) {
			return user == null;
		} else {
			return self.equals(user);
		}
	}

	@Override
	protected User handleIsNonExistingEmailAddress(User self, String email) throws Exception {
		User found = getUserDao().findUserByEmail(email);
		if (self == null || found == null) {
			return found;
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
			group.setName( GROUP_PREFIX + name);
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
				throw new SecurityServiceException("Object Identity to Object "+parent+" does not exist!");
			}
		}
		
		// analyse object if it is an entity object with an id
		EntityObjectIdentity entityOI = new EntityObjectIdentity(object);
		
		// define ObjectIdentity entity
		ObjectIdentity objectIdentity = ObjectIdentity.Factory.newInstance();
		objectIdentity.setId(entityOI.getIdentifier());
		// These fields are not needed in openuss
		//		objectIdentity.setObjectIdentityClass(entityOI.getClassname());
		//		objectIdentity.setAclClass(entityOI.getClass().getName());
		objectIdentity.setParent(parentObjectIdentity);

		getObjectIdentityDao().create(objectIdentity);
		
		return objectIdentity;
	}

	@Override
	protected void handleRemoveObjectIdentity(Object object) throws Exception {
		ObjectIdentity oi = getObjectIdentity(object);
		getObjectIdentityDao().remove(oi);
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
		
		removeAuthorityFromUserCache(authority);
	}

	@Override
	protected Collection handleGetAllGroups() throws Exception {
		GroupDao groupDao = getGroupDao();
		return groupDao.loadAll(GroupDao.TRANSFORM_GROUPITEM);
	}

	@Override
	protected List handleGetUsers(UserCriteria criteria) throws Exception {
		UserDao userDao = getUserDao();
		List users = userDao.findUsersByCriteria(UserDao.TRANSFORM_USERINFO, criteria);
		return users;
	}

	@Override
	protected void handleSetLoginTime(User user) throws Exception {
		User usr = getUserDao().load(user.getId());
		if (usr == null) {
			logger.error("couldn't find user with id "+user.getId());
			throw new SecurityServiceException("Couldn't find user with id "+user.getId());
		}
		usr.setLastLogin(new Date());
		getUserDao().update(usr);
	}

	@Override
	protected void handleRemovePermission(Authority authority, Object object) throws Exception {
		Permission permission = getPermissions(authority, object);
		if (permission != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("removing permission for authority "+authority+" for "+object);
			}
			getPermissionDao().remove(permission);
		} else {
			logger.debug("Permission entity for authority "+authority+" for "+object+" not found!");
		}
	}

	private ObjectIdentity getObjectIdentity(Object object) throws IllegalAccessException, InvocationTargetException {
		
		ObjectIdentity objectIdentity = getObjectIdentityDao().load(new EntityObjectIdentity(object).getIdentifier());
		if (objectIdentity == null) {
			throw new SecurityServiceException("ObjectIdentity doesn't exist for object "+object);
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


}
