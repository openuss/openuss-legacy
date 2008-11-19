package org.openuss.security;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.security.SecurityService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.security.SecurityService
 */
public abstract class SecurityServiceBase
    implements org.openuss.security.SecurityService
{

    private org.acegisecurity.providers.dao.UserCache userCache;

    /**
     * Sets the reference to <code>userCache</code>.
     */
    public void setUserCache(org.acegisecurity.providers.dao.UserCache userCache)
    {
        this.userCache = userCache;
    }

    /**
     * Gets the reference to <code>userCache</code>.
     */
    protected org.acegisecurity.providers.dao.UserCache getUserCache()
    {
        return this.userCache;
    }

    private org.acegisecurity.acl.basic.cache.EhCacheBasedAclEntryCache aclCache;

    /**
     * Sets the reference to <code>aclCache</code>.
     */
    public void setAclCache(org.acegisecurity.acl.basic.cache.EhCacheBasedAclEntryCache aclCache)
    {
        this.aclCache = aclCache;
    }

    /**
     * Gets the reference to <code>aclCache</code>.
     */
    protected org.acegisecurity.acl.basic.cache.EhCacheBasedAclEntryCache getAclCache()
    {
        return this.aclCache;
    }

    private org.openuss.commands.CommandService commandService;

    /**
     * Sets the reference to <code>commandService</code>.
     */
    public void setCommandService(org.openuss.commands.CommandService commandService)
    {
        this.commandService = commandService;
    }

    /**
     * Gets the reference to <code>commandService</code>.
     */
    protected org.openuss.commands.CommandService getCommandService()
    {
        return this.commandService;
    }

    private org.openuss.security.AuthorityDao authorityDao;

    /**
     * Sets the reference to <code>authority</code>'s DAO.
     */
    public void setAuthorityDao(org.openuss.security.AuthorityDao authorityDao)
    {
        this.authorityDao = authorityDao;
    }

    /**
     * Gets the reference to <code>authority</code>'s DAO.
     */
    protected org.openuss.security.AuthorityDao getAuthorityDao()
    {
        return this.authorityDao;
    }

    private org.openuss.security.GroupDao groupDao;

    /**
     * Sets the reference to <code>group</code>'s DAO.
     */
    public void setGroupDao(org.openuss.security.GroupDao groupDao)
    {
        this.groupDao = groupDao;
    }

    /**
     * Gets the reference to <code>group</code>'s DAO.
     */
    protected org.openuss.security.GroupDao getGroupDao()
    {
        return this.groupDao;
    }

    private org.openuss.security.UserDao userDao;

    /**
     * Sets the reference to <code>user</code>'s DAO.
     */
    public void setUserDao(org.openuss.security.UserDao userDao)
    {
        this.userDao = userDao;
    }

    /**
     * Gets the reference to <code>user</code>'s DAO.
     */
    protected org.openuss.security.UserDao getUserDao()
    {
        return this.userDao;
    }

    private org.openuss.security.acl.PermissionDao permissionDao;

    /**
     * Sets the reference to <code>permission</code>'s DAO.
     */
    public void setPermissionDao(org.openuss.security.acl.PermissionDao permissionDao)
    {
        this.permissionDao = permissionDao;
    }

    /**
     * Gets the reference to <code>permission</code>'s DAO.
     */
    protected org.openuss.security.acl.PermissionDao getPermissionDao()
    {
        return this.permissionDao;
    }

    private org.openuss.security.acl.ObjectIdentityDao objectIdentityDao;

    /**
     * Sets the reference to <code>objectIdentity</code>'s DAO.
     */
    public void setObjectIdentityDao(org.openuss.security.acl.ObjectIdentityDao objectIdentityDao)
    {
        this.objectIdentityDao = objectIdentityDao;
    }

    /**
     * Gets the reference to <code>objectIdentity</code>'s DAO.
     */
    protected org.openuss.security.acl.ObjectIdentityDao getObjectIdentityDao()
    {
        return this.objectIdentityDao;
    }

    /**
     * @see org.openuss.security.SecurityService#getAllUsers()
     */
    public java.util.Collection getAllUsers()
    {
        try
        {
            return this.handleGetAllUsers();
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.getAllUsers()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getAllUsers()}
      */
    protected abstract java.util.Collection handleGetAllUsers()
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#getAllGroups()
     */
    public java.util.Collection getAllGroups()
    {
        try
        {
            return this.handleGetAllGroups();
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.getAllGroups()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getAllGroups()}
      */
    protected abstract java.util.Collection handleGetAllGroups()
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#getUsers(org.openuss.security.UserCriteria)
     */
    public java.util.List getUsers(org.openuss.security.UserCriteria criteria)
    {
        try
        {
            return this.handleGetUsers(criteria);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.getUsers(org.openuss.security.UserCriteria criteria)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getUsers(org.openuss.security.UserCriteria)}
      */
    protected abstract java.util.List handleGetUsers(org.openuss.security.UserCriteria criteria)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#getUser(java.lang.Long)
     */
    public org.openuss.security.UserInfo getUser(java.lang.Long userId)
    {
        try
        {
            return this.handleGetUser(userId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.getUser(java.lang.Long userId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getUser(java.lang.Long)}
      */
    protected abstract org.openuss.security.UserInfo handleGetUser(java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#getUserByName(java.lang.String)
     */
    public org.openuss.security.UserInfo getUserByName(java.lang.String name)
    {
        try
        {
            return this.handleGetUserByName(name);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.getUserByName(java.lang.String name)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getUserByName(java.lang.String)}
      */
    protected abstract org.openuss.security.UserInfo handleGetUserByName(java.lang.String name)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#createUser(org.openuss.security.UserInfo)
     */
    public org.openuss.security.UserInfo createUser(org.openuss.security.UserInfo user)
    {
        try
        {
            return this.handleCreateUser(user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.createUser(org.openuss.security.UserInfo user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createUser(org.openuss.security.UserInfo)}
      */
    protected abstract org.openuss.security.UserInfo handleCreateUser(org.openuss.security.UserInfo user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#changePassword(java.lang.String)
     */
    public void changePassword(java.lang.String password)
    {
        try
        {
            this.handleChangePassword(password);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.changePassword(java.lang.String password)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #changePassword(java.lang.String)}
      */
    protected abstract void handleChangePassword(java.lang.String password)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#saveUser(org.openuss.security.UserInfo)
     */
    public void saveUser(org.openuss.security.UserInfo user)
    {
        try
        {
            this.handleSaveUser(user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.saveUser(org.openuss.security.UserInfo user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #saveUser(org.openuss.security.UserInfo)}
      */
    protected abstract void handleSaveUser(org.openuss.security.UserInfo user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#removeUser(org.openuss.security.UserInfo)
     */
    public void removeUser(org.openuss.security.UserInfo user)
    {
        try
        {
            this.handleRemoveUser(user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.removeUser(org.openuss.security.UserInfo user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeUser(org.openuss.security.UserInfo)}
      */
    protected abstract void handleRemoveUser(org.openuss.security.UserInfo user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#addAuthorityToGroup(org.openuss.security.Authority, org.openuss.security.Group)
     */
    public void addAuthorityToGroup(org.openuss.security.Authority authority, org.openuss.security.Group group)
    {
        try
        {
            this.handleAddAuthorityToGroup(authority, group);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.addAuthorityToGroup(org.openuss.security.Authority authority, org.openuss.security.Group group)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addAuthorityToGroup(org.openuss.security.Authority, org.openuss.security.Group)}
      */
    protected abstract void handleAddAuthorityToGroup(org.openuss.security.Authority authority, org.openuss.security.Group group)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#removeAuthorityFromGroup(org.openuss.security.Authority, org.openuss.security.Group)
     */
    public void removeAuthorityFromGroup(org.openuss.security.Authority authority, org.openuss.security.Group group)
    {
        try
        {
            this.handleRemoveAuthorityFromGroup(authority, group);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.removeAuthorityFromGroup(org.openuss.security.Authority authority, org.openuss.security.Group group)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeAuthorityFromGroup(org.openuss.security.Authority, org.openuss.security.Group)}
      */
    protected abstract void handleRemoveAuthorityFromGroup(org.openuss.security.Authority authority, org.openuss.security.Group group)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#isValidUserName(org.openuss.security.UserInfo, java.lang.String)
     */
    public boolean isValidUserName(org.openuss.security.UserInfo self, java.lang.String userName)
    {
        try
        {
            return this.handleIsValidUserName(self, userName);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.isValidUserName(org.openuss.security.UserInfo self, java.lang.String userName)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #isValidUserName(org.openuss.security.UserInfo, java.lang.String)}
      */
    protected abstract boolean handleIsValidUserName(org.openuss.security.UserInfo self, java.lang.String userName)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#isNonExistingEmailAddress(org.openuss.security.UserInfo, java.lang.String)
     */
    public org.openuss.security.UserInfo isNonExistingEmailAddress(org.openuss.security.UserInfo self, java.lang.String email)
    {
        try
        {
            return this.handleIsNonExistingEmailAddress(self, email);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.isNonExistingEmailAddress(org.openuss.security.UserInfo self, java.lang.String email)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #isNonExistingEmailAddress(org.openuss.security.UserInfo, java.lang.String)}
      */
    protected abstract org.openuss.security.UserInfo handleIsNonExistingEmailAddress(org.openuss.security.UserInfo self, java.lang.String email)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#getGroupByName(java.lang.String)
     */
    public org.openuss.security.Group getGroupByName(java.lang.String name)
    {
        try
        {
            return this.handleGetGroupByName(name);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.getGroupByName(java.lang.String name)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getGroupByName(java.lang.String)}
      */
    protected abstract org.openuss.security.Group handleGetGroupByName(java.lang.String name)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#getGroup(org.openuss.security.Group)
     */
    public org.openuss.security.Group getGroup(org.openuss.security.Group group)
    {
        try
        {
            return this.handleGetGroup(group);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.getGroup(org.openuss.security.Group group)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getGroup(org.openuss.security.Group)}
      */
    protected abstract org.openuss.security.Group handleGetGroup(org.openuss.security.Group group)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#createGroup(java.lang.String, java.lang.String, java.lang.String, org.openuss.security.GroupType)
     */
    public org.openuss.security.Group createGroup(java.lang.String name, java.lang.String label, java.lang.String password, org.openuss.security.GroupType groupType)
    {
        try
        {
            return this.handleCreateGroup(name, label, password, groupType);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.createGroup(java.lang.String name, java.lang.String label, java.lang.String password, org.openuss.security.GroupType groupType)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createGroup(java.lang.String, java.lang.String, java.lang.String, org.openuss.security.GroupType)}
      */
    protected abstract org.openuss.security.Group handleCreateGroup(java.lang.String name, java.lang.String label, java.lang.String password, org.openuss.security.GroupType groupType)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#removeGroup(org.openuss.security.Group)
     */
    public void removeGroup(org.openuss.security.Group group)
    {
        try
        {
            this.handleRemoveGroup(group);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.removeGroup(org.openuss.security.Group group)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeGroup(org.openuss.security.Group)}
      */
    protected abstract void handleRemoveGroup(org.openuss.security.Group group)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#saveGroup(org.openuss.security.Group)
     */
    public void saveGroup(org.openuss.security.Group group)
    {
        try
        {
            this.handleSaveGroup(group);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.saveGroup(org.openuss.security.Group group)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #saveGroup(org.openuss.security.Group)}
      */
    protected abstract void handleSaveGroup(org.openuss.security.Group group)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#createObjectIdentity(java.lang.Object, java.lang.Object)
     */
    public org.openuss.security.acl.ObjectIdentity createObjectIdentity(java.lang.Object object, java.lang.Object parent)
    {
        try
        {
            return this.handleCreateObjectIdentity(object, parent);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.createObjectIdentity(java.lang.Object object, java.lang.Object parent)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createObjectIdentity(java.lang.Object, java.lang.Object)}
      */
    protected abstract org.openuss.security.acl.ObjectIdentity handleCreateObjectIdentity(java.lang.Object object, java.lang.Object parent)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#removeObjectIdentity(java.lang.Object)
     */
    public void removeObjectIdentity(java.lang.Object object)
    {
        try
        {
            this.handleRemoveObjectIdentity(object);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.removeObjectIdentity(java.lang.Object object)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeObjectIdentity(java.lang.Object)}
      */
    protected abstract void handleRemoveObjectIdentity(java.lang.Object object)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#removePermission(org.openuss.security.Authority, java.lang.Object)
     */
    public void removePermission(org.openuss.security.Authority authority, java.lang.Object object)
    {
        try
        {
            this.handleRemovePermission(authority, object);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.removePermission(org.openuss.security.Authority authority, java.lang.Object object)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removePermission(org.openuss.security.Authority, java.lang.Object)}
      */
    protected abstract void handleRemovePermission(org.openuss.security.Authority authority, java.lang.Object object)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#removeAllPermissions(java.lang.Object)
     */
    public void removeAllPermissions(java.lang.Object object)
    {
        try
        {
            this.handleRemoveAllPermissions(object);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.removeAllPermissions(java.lang.Object object)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeAllPermissions(java.lang.Object)}
      */
    protected abstract void handleRemoveAllPermissions(java.lang.Object object)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#setPermissions(org.openuss.security.Authority, java.lang.Object, java.lang.Integer)
     */
    public void setPermissions(org.openuss.security.Authority authority, java.lang.Object object, java.lang.Integer mask)
    {
        try
        {
            this.handleSetPermissions(authority, object, mask);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.setPermissions(org.openuss.security.Authority authority, java.lang.Object object, java.lang.Integer mask)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #setPermissions(org.openuss.security.Authority, java.lang.Object, java.lang.Integer)}
      */
    protected abstract void handleSetPermissions(org.openuss.security.Authority authority, java.lang.Object object, java.lang.Integer mask)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#getPermissions(org.openuss.security.Authority, java.lang.Object)
     */
    public org.openuss.security.acl.Permission getPermissions(org.openuss.security.Authority authority, java.lang.Object object)
    {
        try
        {
            return this.handleGetPermissions(authority, object);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.getPermissions(org.openuss.security.Authority authority, java.lang.Object object)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getPermissions(org.openuss.security.Authority, java.lang.Object)}
      */
    protected abstract org.openuss.security.acl.Permission handleGetPermissions(org.openuss.security.Authority authority, java.lang.Object object)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#setLoginTime(org.openuss.security.UserInfo)
     */
    public void setLoginTime(org.openuss.security.UserInfo user)
    {
        try
        {
            this.handleSetLoginTime(user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.setLoginTime(org.openuss.security.UserInfo user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #setLoginTime(org.openuss.security.UserInfo)}
      */
    protected abstract void handleSetLoginTime(org.openuss.security.UserInfo user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#getUserByEmail(java.lang.String)
     */
    public org.openuss.security.UserInfo getUserByEmail(java.lang.String email)
    {
        try
        {
            return this.handleGetUserByEmail(email);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.getUserByEmail(java.lang.String email)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getUserByEmail(java.lang.String)}
      */
    protected abstract org.openuss.security.UserInfo handleGetUserByEmail(java.lang.String email)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#getCurrentUser()
     */
    public org.openuss.security.UserInfo getCurrentUser()
    {
        try
        {
            return this.handleGetCurrentUser();
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.getCurrentUser()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getCurrentUser()}
      */
    protected abstract org.openuss.security.UserInfo handleGetCurrentUser()
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#hasPermission(java.lang.Object, java.lang.Integer[])
     */
    public boolean hasPermission(java.lang.Object domainObject, java.lang.Integer[] permissions)
    {
        try
        {
            return this.handleHasPermission(domainObject, permissions);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.hasPermission(java.lang.Object domainObject, java.lang.Integer[] permissions)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #hasPermission(java.lang.Object, java.lang.Integer[])}
      */
    protected abstract boolean handleHasPermission(java.lang.Object domainObject, java.lang.Integer[] permissions)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#getUserObject(org.openuss.security.UserInfo)
     */
    public org.openuss.security.User getUserObject(org.openuss.security.UserInfo user)
    {
        try
        {
            return this.handleGetUserObject(user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.getUserObject(org.openuss.security.UserInfo user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getUserObject(org.openuss.security.UserInfo)}
      */
    protected abstract org.openuss.security.User handleGetUserObject(org.openuss.security.UserInfo user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#getUserObject(java.lang.Long)
     */
    public org.openuss.security.User getUserObject(java.lang.Long userId)
    {
        try
        {
            return this.handleGetUserObject(userId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.getUserObject(java.lang.Long userId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getUserObject(java.lang.Long)}
      */
    protected abstract org.openuss.security.User handleGetUserObject(java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#saveUser(org.openuss.security.User)
     */
    public void saveUser(org.openuss.security.User user)
    {
        try
        {
            this.handleSaveUser(user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.saveUser(org.openuss.security.User user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #saveUser(org.openuss.security.User)}
      */
    protected abstract void handleSaveUser(org.openuss.security.User user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#getGrantedAuthorities(org.openuss.security.UserInfo)
     */
    public java.lang.String[] getGrantedAuthorities(org.openuss.security.UserInfo userInfo)
    {
        try
        {
            return this.handleGetGrantedAuthorities(userInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.getGrantedAuthorities(org.openuss.security.UserInfo userInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getGrantedAuthorities(org.openuss.security.UserInfo)}
      */
    protected abstract java.lang.String[] handleGetGrantedAuthorities(org.openuss.security.UserInfo userInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#removeUserPermanently(org.openuss.security.UserInfo)
     */
    public void removeUserPermanently(org.openuss.security.UserInfo user)
    {
        try
        {
            this.handleRemoveUserPermanently(user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.removeUserPermanently(org.openuss.security.UserInfo user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeUserPermanently(org.openuss.security.UserInfo)}
      */
    protected abstract void handleRemoveUserPermanently(org.openuss.security.UserInfo user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.SecurityService#removePersonalInformation(org.openuss.security.User)
     */
    public void removePersonalInformation(org.openuss.security.User user)
    {
        try
        {
            this.handleRemovePersonalInformation(user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.SecurityServiceException(
                "Error performing 'org.openuss.security.SecurityService.removePersonalInformation(org.openuss.security.User user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removePersonalInformation(org.openuss.security.User)}
      */
    protected abstract void handleRemovePersonalInformation(org.openuss.security.User user)
        throws java.lang.Exception;

    /**
     * Gets the current <code>principal</code> if one has been set,
     * otherwise returns <code>null</code>.
     *
     * @return the current principal
     */
    protected java.security.Principal getPrincipal()
    {
        return org.andromda.spring.PrincipalStore.get();
    }
}