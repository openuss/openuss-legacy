package org.openuss.security;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.security.MembershipService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.security.MembershipService
 */
public abstract class MembershipServiceBase
    implements org.openuss.security.MembershipService
{

    private org.openuss.security.SecurityService securityService;

    /**
     * Sets the reference to <code>securityService</code>.
     */
    public void setSecurityService(org.openuss.security.SecurityService securityService)
    {
        this.securityService = securityService;
    }

    /**
     * Gets the reference to <code>securityService</code>.
     */
    protected org.openuss.security.SecurityService getSecurityService()
    {
        return this.securityService;
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

    private org.openuss.security.MembershipDao membershipDao;

    /**
     * Sets the reference to <code>membership</code>'s DAO.
     */
    public void setMembershipDao(org.openuss.security.MembershipDao membershipDao)
    {
        this.membershipDao = membershipDao;
    }

    /**
     * Gets the reference to <code>membership</code>'s DAO.
     */
    protected org.openuss.security.MembershipDao getMembershipDao()
    {
        return this.membershipDao;
    }

    /**
     * @see org.openuss.security.MembershipService#addMember(org.openuss.security.Membership, org.openuss.security.User)
     */
    public void addMember(org.openuss.security.Membership membership, org.openuss.security.User user)
    {
        try
        {
            this.handleAddMember(membership, user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.MembershipServiceException(
                "Error performing 'org.openuss.security.MembershipService.addMember(org.openuss.security.Membership membership, org.openuss.security.User user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addMember(org.openuss.security.Membership, org.openuss.security.User)}
      */
    protected abstract void handleAddMember(org.openuss.security.Membership membership, org.openuss.security.User user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.MembershipService#removeMember(org.openuss.security.Membership, org.openuss.security.User)
     */
    public void removeMember(org.openuss.security.Membership membership, org.openuss.security.User user)
    {
        try
        {
            this.handleRemoveMember(membership, user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.MembershipServiceException(
                "Error performing 'org.openuss.security.MembershipService.removeMember(org.openuss.security.Membership membership, org.openuss.security.User user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeMember(org.openuss.security.Membership, org.openuss.security.User)}
      */
    protected abstract void handleRemoveMember(org.openuss.security.Membership membership, org.openuss.security.User user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.MembershipService#addAspirant(org.openuss.security.Membership, org.openuss.security.User)
     */
    public void addAspirant(org.openuss.security.Membership membership, org.openuss.security.User user)
    {
        try
        {
            this.handleAddAspirant(membership, user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.MembershipServiceException(
                "Error performing 'org.openuss.security.MembershipService.addAspirant(org.openuss.security.Membership membership, org.openuss.security.User user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addAspirant(org.openuss.security.Membership, org.openuss.security.User)}
      */
    protected abstract void handleAddAspirant(org.openuss.security.Membership membership, org.openuss.security.User user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.MembershipService#acceptAspirant(org.openuss.security.Membership, org.openuss.security.User)
     */
    public void acceptAspirant(org.openuss.security.Membership membership, org.openuss.security.User user)
    {
        try
        {
            this.handleAcceptAspirant(membership, user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.MembershipServiceException(
                "Error performing 'org.openuss.security.MembershipService.acceptAspirant(org.openuss.security.Membership membership, org.openuss.security.User user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #acceptAspirant(org.openuss.security.Membership, org.openuss.security.User)}
      */
    protected abstract void handleAcceptAspirant(org.openuss.security.Membership membership, org.openuss.security.User user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.MembershipService#rejectAspirant(org.openuss.security.Membership, org.openuss.security.User)
     */
    public void rejectAspirant(org.openuss.security.Membership membership, org.openuss.security.User user)
    {
        try
        {
            this.handleRejectAspirant(membership, user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.MembershipServiceException(
                "Error performing 'org.openuss.security.MembershipService.rejectAspirant(org.openuss.security.Membership membership, org.openuss.security.User user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #rejectAspirant(org.openuss.security.Membership, org.openuss.security.User)}
      */
    protected abstract void handleRejectAspirant(org.openuss.security.Membership membership, org.openuss.security.User user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.MembershipService#createGroup(org.openuss.security.Membership, org.openuss.security.Group)
     */
    public org.openuss.security.Group createGroup(org.openuss.security.Membership membership, org.openuss.security.Group group)
    {
        try
        {
            return this.handleCreateGroup(membership, group);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.MembershipServiceException(
                "Error performing 'org.openuss.security.MembershipService.createGroup(org.openuss.security.Membership membership, org.openuss.security.Group group)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createGroup(org.openuss.security.Membership, org.openuss.security.Group)}
      */
    protected abstract org.openuss.security.Group handleCreateGroup(org.openuss.security.Membership membership, org.openuss.security.Group group)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.MembershipService#removeGroup(org.openuss.security.Membership, org.openuss.security.Group)
     */
    public void removeGroup(org.openuss.security.Membership membership, org.openuss.security.Group group)
    {
        try
        {
            this.handleRemoveGroup(membership, group);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.MembershipServiceException(
                "Error performing 'org.openuss.security.MembershipService.removeGroup(org.openuss.security.Membership membership, org.openuss.security.Group group)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeGroup(org.openuss.security.Membership, org.openuss.security.Group)}
      */
    protected abstract void handleRemoveGroup(org.openuss.security.Membership membership, org.openuss.security.Group group)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.MembershipService#clearMembership(org.openuss.security.Membership)
     */
    public void clearMembership(org.openuss.security.Membership membership)
    {
        try
        {
            this.handleClearMembership(membership);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.MembershipServiceException(
                "Error performing 'org.openuss.security.MembershipService.clearMembership(org.openuss.security.Membership membership)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #clearMembership(org.openuss.security.Membership)}
      */
    protected abstract void handleClearMembership(org.openuss.security.Membership membership)
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