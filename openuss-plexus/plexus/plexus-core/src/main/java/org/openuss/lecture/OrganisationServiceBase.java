package org.openuss.lecture;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.lecture.OrganisationService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.lecture.OrganisationService
 */
public abstract class OrganisationServiceBase
    implements org.openuss.lecture.OrganisationService
{

    private org.openuss.security.MembershipService membershipService;

    /**
     * Sets the reference to <code>membershipService</code>.
     */
    public void setMembershipService(org.openuss.security.MembershipService membershipService)
    {
        this.membershipService = membershipService;
    }

    /**
     * Gets the reference to <code>membershipService</code>.
     */
    protected org.openuss.security.MembershipService getMembershipService()
    {
        return this.membershipService;
    }

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

    private org.openuss.registration.RegistrationService registrationService;

    /**
     * Sets the reference to <code>registrationService</code>.
     */
    public void setRegistrationService(org.openuss.registration.RegistrationService registrationService)
    {
        this.registrationService = registrationService;
    }

    /**
     * Gets the reference to <code>registrationService</code>.
     */
    protected org.openuss.registration.RegistrationService getRegistrationService()
    {
        return this.registrationService;
    }

    private org.openuss.messaging.MessageService messageService;

    /**
     * Sets the reference to <code>messageService</code>.
     */
    public void setMessageService(org.openuss.messaging.MessageService messageService)
    {
        this.messageService = messageService;
    }

    /**
     * Gets the reference to <code>messageService</code>.
     */
    protected org.openuss.messaging.MessageService getMessageService()
    {
        return this.messageService;
    }

    private org.openuss.lecture.OrganisationDao organisationDao;

    /**
     * Sets the reference to <code>organisation</code>'s DAO.
     */
    public void setOrganisationDao(org.openuss.lecture.OrganisationDao organisationDao)
    {
        this.organisationDao = organisationDao;
    }

    /**
     * Gets the reference to <code>organisation</code>'s DAO.
     */
    protected org.openuss.lecture.OrganisationDao getOrganisationDao()
    {
        return this.organisationDao;
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

    private org.openuss.lecture.DepartmentDao departmentDao;

    /**
     * Sets the reference to <code>department</code>'s DAO.
     */
    public void setDepartmentDao(org.openuss.lecture.DepartmentDao departmentDao)
    {
        this.departmentDao = departmentDao;
    }

    /**
     * Gets the reference to <code>department</code>'s DAO.
     */
    protected org.openuss.lecture.DepartmentDao getDepartmentDao()
    {
        return this.departmentDao;
    }

    private org.openuss.lecture.InstituteDao instituteDao;

    /**
     * Sets the reference to <code>institute</code>'s DAO.
     */
    public void setInstituteDao(org.openuss.lecture.InstituteDao instituteDao)
    {
        this.instituteDao = instituteDao;
    }

    /**
     * Gets the reference to <code>institute</code>'s DAO.
     */
    protected org.openuss.lecture.InstituteDao getInstituteDao()
    {
        return this.instituteDao;
    }

    private org.openuss.lecture.CourseDao courseDao;

    /**
     * Sets the reference to <code>course</code>'s DAO.
     */
    public void setCourseDao(org.openuss.lecture.CourseDao courseDao)
    {
        this.courseDao = courseDao;
    }

    /**
     * Gets the reference to <code>course</code>'s DAO.
     */
    protected org.openuss.lecture.CourseDao getCourseDao()
    {
        return this.courseDao;
    }

    private org.openuss.lecture.UniversityDao universityDao;

    /**
     * Sets the reference to <code>university</code>'s DAO.
     */
    public void setUniversityDao(org.openuss.lecture.UniversityDao universityDao)
    {
        this.universityDao = universityDao;
    }

    /**
     * Gets the reference to <code>university</code>'s DAO.
     */
    protected org.openuss.lecture.UniversityDao getUniversityDao()
    {
        return this.universityDao;
    }

    /**
     * @see org.openuss.lecture.OrganisationService#addMember(java.lang.Long, java.lang.Long)
     */
    public void addMember(java.lang.Long organisationId, java.lang.Long userId)
    {
        try
        {
            this.handleAddMember(organisationId, userId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.OrganisationServiceException(
                "Error performing 'org.openuss.lecture.OrganisationService.addMember(java.lang.Long organisationId, java.lang.Long userId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addMember(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleAddMember(java.lang.Long organisationId, java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.OrganisationService#removeMember(java.lang.Long, java.lang.Long)
     */
    public void removeMember(java.lang.Long organisationId, java.lang.Long userId)
    {
        try
        {
            this.handleRemoveMember(organisationId, userId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.OrganisationServiceException(
                "Error performing 'org.openuss.lecture.OrganisationService.removeMember(java.lang.Long organisationId, java.lang.Long userId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeMember(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleRemoveMember(java.lang.Long organisationId, java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.OrganisationService#addAspirant(java.lang.Long, java.lang.Long)
     */
    public void addAspirant(java.lang.Long organisationId, java.lang.Long userId)
    {
        try
        {
            this.handleAddAspirant(organisationId, userId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.OrganisationServiceException(
                "Error performing 'org.openuss.lecture.OrganisationService.addAspirant(java.lang.Long organisationId, java.lang.Long userId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addAspirant(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleAddAspirant(java.lang.Long organisationId, java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.OrganisationService#acceptAspirant(java.lang.Long, java.lang.Long)
     */
    public void acceptAspirant(java.lang.Long organisationId, java.lang.Long userId)
    {
        try
        {
            this.handleAcceptAspirant(organisationId, userId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.OrganisationServiceException(
                "Error performing 'org.openuss.lecture.OrganisationService.acceptAspirant(java.lang.Long organisationId, java.lang.Long userId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #acceptAspirant(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleAcceptAspirant(java.lang.Long organisationId, java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.OrganisationService#rejectAspirant(java.lang.Long, java.lang.Long)
     */
    public void rejectAspirant(java.lang.Long organisationId, java.lang.Long userId)
    {
        try
        {
            this.handleRejectAspirant(organisationId, userId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.OrganisationServiceException(
                "Error performing 'org.openuss.lecture.OrganisationService.rejectAspirant(java.lang.Long organisationId, java.lang.Long userId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #rejectAspirant(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleRejectAspirant(java.lang.Long organisationId, java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.OrganisationService#createGroup(java.lang.Long, org.openuss.security.GroupItem)
     */
    public java.lang.Long createGroup(java.lang.Long organisationId, org.openuss.security.GroupItem groupItem)
    {
        try
        {
            return this.handleCreateGroup(organisationId, groupItem);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.OrganisationServiceException(
                "Error performing 'org.openuss.lecture.OrganisationService.createGroup(java.lang.Long organisationId, org.openuss.security.GroupItem groupItem)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createGroup(java.lang.Long, org.openuss.security.GroupItem)}
      */
    protected abstract java.lang.Long handleCreateGroup(java.lang.Long organisationId, org.openuss.security.GroupItem groupItem)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.OrganisationService#removeGroup(java.lang.Long, java.lang.Long)
     */
    public void removeGroup(java.lang.Long organisationId, java.lang.Long groupId)
    {
        try
        {
            this.handleRemoveGroup(organisationId, groupId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.OrganisationServiceException(
                "Error performing 'org.openuss.lecture.OrganisationService.removeGroup(java.lang.Long organisationId, java.lang.Long groupId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeGroup(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleRemoveGroup(java.lang.Long organisationId, java.lang.Long groupId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.OrganisationService#addUserToGroup(java.lang.Long, java.lang.Long)
     */
    public void addUserToGroup(java.lang.Long userId, java.lang.Long groupId)
    {
        try
        {
            this.handleAddUserToGroup(userId, groupId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.OrganisationServiceException(
                "Error performing 'org.openuss.lecture.OrganisationService.addUserToGroup(java.lang.Long userId, java.lang.Long groupId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addUserToGroup(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleAddUserToGroup(java.lang.Long userId, java.lang.Long groupId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.OrganisationService#removeUserFromGroup(java.lang.Long, java.lang.Long)
     */
    public void removeUserFromGroup(java.lang.Long userId, java.lang.Long groupId)
    {
        try
        {
            this.handleRemoveUserFromGroup(userId, groupId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.OrganisationServiceException(
                "Error performing 'org.openuss.lecture.OrganisationService.removeUserFromGroup(java.lang.Long userId, java.lang.Long groupId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeUserFromGroup(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleRemoveUserFromGroup(java.lang.Long userId, java.lang.Long groupId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.OrganisationService#sendActivationCode(org.openuss.lecture.Organisation)
     */
    public void sendActivationCode(org.openuss.lecture.Organisation organisation)
    {
        try
        {
            this.handleSendActivationCode(organisation);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.OrganisationServiceException(
                "Error performing 'org.openuss.lecture.OrganisationService.sendActivationCode(org.openuss.lecture.Organisation organisation)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #sendActivationCode(org.openuss.lecture.Organisation)}
      */
    protected abstract void handleSendActivationCode(org.openuss.lecture.Organisation organisation)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.OrganisationService#setOrganisationEnabled(java.lang.Long, boolean)
     */
    public void setOrganisationEnabled(java.lang.Long organisationId, boolean enabled)
    {
        try
        {
            this.handleSetOrganisationEnabled(organisationId, enabled);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.OrganisationServiceException(
                "Error performing 'org.openuss.lecture.OrganisationService.setOrganisationEnabled(java.lang.Long organisationId, boolean enabled)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #setOrganisationEnabled(java.lang.Long, boolean)}
      */
    protected abstract void handleSetOrganisationEnabled(java.lang.Long organisationId, boolean enabled)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.OrganisationService#findAllMembers(java.lang.Long)
     */
    public java.util.List findAllMembers(java.lang.Long organisationId)
    {
        try
        {
            return this.handleFindAllMembers(organisationId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.OrganisationServiceException(
                "Error performing 'org.openuss.lecture.OrganisationService.findAllMembers(java.lang.Long organisationId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findAllMembers(java.lang.Long)}
      */
    protected abstract java.util.List handleFindAllMembers(java.lang.Long organisationId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.OrganisationService#findAllAspirants(java.lang.Long)
     */
    public java.util.List findAllAspirants(java.lang.Long organisationId)
    {
        try
        {
            return this.handleFindAllAspirants(organisationId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.OrganisationServiceException(
                "Error performing 'org.openuss.lecture.OrganisationService.findAllAspirants(java.lang.Long organisationId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findAllAspirants(java.lang.Long)}
      */
    protected abstract java.util.List handleFindAllAspirants(java.lang.Long organisationId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.OrganisationService#findGroup(java.lang.Long)
     */
    public org.openuss.security.GroupItem findGroup(java.lang.Long groupId)
    {
        try
        {
            return this.handleFindGroup(groupId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.OrganisationServiceException(
                "Error performing 'org.openuss.lecture.OrganisationService.findGroup(java.lang.Long groupId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findGroup(java.lang.Long)}
      */
    protected abstract org.openuss.security.GroupItem handleFindGroup(java.lang.Long groupId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.OrganisationService#findDepartmentHierarchy(java.lang.Long)
     */
    public org.openuss.lecture.OrganisationHierarchy findDepartmentHierarchy(java.lang.Long departmentId)
    {
        try
        {
            return this.handleFindDepartmentHierarchy(departmentId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.OrganisationServiceException(
                "Error performing 'org.openuss.lecture.OrganisationService.findDepartmentHierarchy(java.lang.Long departmentId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findDepartmentHierarchy(java.lang.Long)}
      */
    protected abstract org.openuss.lecture.OrganisationHierarchy handleFindDepartmentHierarchy(java.lang.Long departmentId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.OrganisationService#findInstituteHierarchy(java.lang.Long)
     */
    public org.openuss.lecture.OrganisationHierarchy findInstituteHierarchy(java.lang.Long instituteId)
    {
        try
        {
            return this.handleFindInstituteHierarchy(instituteId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.OrganisationServiceException(
                "Error performing 'org.openuss.lecture.OrganisationService.findInstituteHierarchy(java.lang.Long instituteId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findInstituteHierarchy(java.lang.Long)}
      */
    protected abstract org.openuss.lecture.OrganisationHierarchy handleFindInstituteHierarchy(java.lang.Long instituteId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.OrganisationService#findCourseHierarchy(java.lang.Long)
     */
    public org.openuss.lecture.OrganisationHierarchy findCourseHierarchy(java.lang.Long courseId)
    {
        try
        {
            return this.handleFindCourseHierarchy(courseId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.OrganisationServiceException(
                "Error performing 'org.openuss.lecture.OrganisationService.findCourseHierarchy(java.lang.Long courseId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findCourseHierarchy(java.lang.Long)}
      */
    protected abstract org.openuss.lecture.OrganisationHierarchy handleFindCourseHierarchy(java.lang.Long courseId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.OrganisationService#findGroupsByOrganisation(java.lang.Long)
     */
    public java.util.List findGroupsByOrganisation(java.lang.Long organisationId)
    {
        try
        {
            return this.handleFindGroupsByOrganisation(organisationId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.OrganisationServiceException(
                "Error performing 'org.openuss.lecture.OrganisationService.findGroupsByOrganisation(java.lang.Long organisationId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findGroupsByOrganisation(java.lang.Long)}
      */
    protected abstract java.util.List handleFindGroupsByOrganisation(java.lang.Long organisationId)
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