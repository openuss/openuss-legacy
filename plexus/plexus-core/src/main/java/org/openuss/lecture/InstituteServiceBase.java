package org.openuss.lecture;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.lecture.InstituteService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.lecture.InstituteService
 */
public abstract class InstituteServiceBase
    implements org.openuss.lecture.InstituteService
{

    private org.openuss.lecture.OrganisationService organisationService;

    /**
     * Sets the reference to <code>organisationService</code>.
     */
    public void setOrganisationService(org.openuss.lecture.OrganisationService organisationService)
    {
        this.organisationService = organisationService;
    }

    /**
     * Gets the reference to <code>organisationService</code>.
     */
    protected org.openuss.lecture.OrganisationService getOrganisationService()
    {
        return this.organisationService;
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

    private org.openuss.lecture.CourseTypeService courseTypeService;

    /**
     * Sets the reference to <code>courseTypeService</code>.
     */
    public void setCourseTypeService(org.openuss.lecture.CourseTypeService courseTypeService)
    {
        this.courseTypeService = courseTypeService;
    }

    /**
     * Gets the reference to <code>courseTypeService</code>.
     */
    protected org.openuss.lecture.CourseTypeService getCourseTypeService()
    {
        return this.courseTypeService;
    }

    private org.openuss.events.DomainEventPublisher eventPublisher;

    /**
     * Sets the reference to <code>eventPublisher</code>.
     */
    public void setEventPublisher(org.openuss.events.DomainEventPublisher eventPublisher)
    {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Gets the reference to <code>eventPublisher</code>.
     */
    protected org.openuss.events.DomainEventPublisher getEventPublisher()
    {
        return this.eventPublisher;
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

    private org.openuss.lecture.ApplicationDao applicationDao;

    /**
     * Sets the reference to <code>application</code>'s DAO.
     */
    public void setApplicationDao(org.openuss.lecture.ApplicationDao applicationDao)
    {
        this.applicationDao = applicationDao;
    }

    /**
     * Gets the reference to <code>application</code>'s DAO.
     */
    protected org.openuss.lecture.ApplicationDao getApplicationDao()
    {
        return this.applicationDao;
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
     * @see org.openuss.lecture.InstituteService#create(org.openuss.lecture.InstituteInfo, java.lang.Long)
     */
    public java.lang.Long create(org.openuss.lecture.InstituteInfo instituteInfo, java.lang.Long userId)
    {
        try
        {
            return this.handleCreate(instituteInfo, userId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.InstituteServiceException(
                "Error performing 'org.openuss.lecture.InstituteService.create(org.openuss.lecture.InstituteInfo instituteInfo, java.lang.Long userId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #create(org.openuss.lecture.InstituteInfo, java.lang.Long)}
      */
    protected abstract java.lang.Long handleCreate(org.openuss.lecture.InstituteInfo instituteInfo, java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.InstituteService#update(org.openuss.lecture.InstituteInfo)
     */
    public void update(org.openuss.lecture.InstituteInfo instituteInfo)
    {
        try
        {
            this.handleUpdate(instituteInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.InstituteServiceException(
                "Error performing 'org.openuss.lecture.InstituteService.update(org.openuss.lecture.InstituteInfo instituteInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #update(org.openuss.lecture.InstituteInfo)}
      */
    protected abstract void handleUpdate(org.openuss.lecture.InstituteInfo instituteInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.InstituteService#removeInstitute(java.lang.Long)
     */
    public void removeInstitute(java.lang.Long instituteId)
    {
        try
        {
            this.handleRemoveInstitute(instituteId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.InstituteServiceException(
                "Error performing 'org.openuss.lecture.InstituteService.removeInstitute(java.lang.Long instituteId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeInstitute(java.lang.Long)}
      */
    protected abstract void handleRemoveInstitute(java.lang.Long instituteId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.InstituteService#applyAtDepartment(org.openuss.lecture.ApplicationInfo)
     */
    public java.lang.Long applyAtDepartment(org.openuss.lecture.ApplicationInfo applicationInfo)
    {
        try
        {
            return this.handleApplyAtDepartment(applicationInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.InstituteServiceException(
                "Error performing 'org.openuss.lecture.InstituteService.applyAtDepartment(org.openuss.lecture.ApplicationInfo applicationInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #applyAtDepartment(org.openuss.lecture.ApplicationInfo)}
      */
    protected abstract java.lang.Long handleApplyAtDepartment(org.openuss.lecture.ApplicationInfo applicationInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.InstituteService#applyAtDepartment(java.lang.Long, java.lang.Long, java.lang.Long)
     */
    public java.lang.Long applyAtDepartment(java.lang.Long instituteId, java.lang.Long departmentId, java.lang.Long userId)
    {
        try
        {
            return this.handleApplyAtDepartment(instituteId, departmentId, userId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.InstituteServiceException(
                "Error performing 'org.openuss.lecture.InstituteService.applyAtDepartment(java.lang.Long instituteId, java.lang.Long departmentId, java.lang.Long userId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #applyAtDepartment(java.lang.Long, java.lang.Long, java.lang.Long)}
      */
    protected abstract java.lang.Long handleApplyAtDepartment(java.lang.Long instituteId, java.lang.Long departmentId, java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.InstituteService#setInstituteStatus(java.lang.Long, boolean)
     */
    public void setInstituteStatus(java.lang.Long instituteId, boolean status)
    {
        try
        {
            this.handleSetInstituteStatus(instituteId, status);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.InstituteServiceException(
                "Error performing 'org.openuss.lecture.InstituteService.setInstituteStatus(java.lang.Long instituteId, boolean status)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #setInstituteStatus(java.lang.Long, boolean)}
      */
    protected abstract void handleSetInstituteStatus(java.lang.Long instituteId, boolean status)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.InstituteService#removeCompleteInstituteTree(java.lang.Long)
     */
    public void removeCompleteInstituteTree(java.lang.Long instituteId)
    {
        try
        {
            this.handleRemoveCompleteInstituteTree(instituteId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.InstituteServiceException(
                "Error performing 'org.openuss.lecture.InstituteService.removeCompleteInstituteTree(java.lang.Long instituteId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeCompleteInstituteTree(java.lang.Long)}
      */
    protected abstract void handleRemoveCompleteInstituteTree(java.lang.Long instituteId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.InstituteService#setGroupOfMember(org.openuss.lecture.InstituteMember, java.lang.Long)
     */
    public void setGroupOfMember(org.openuss.lecture.InstituteMember member, java.lang.Long instituteId)
    {
        try
        {
            this.handleSetGroupOfMember(member, instituteId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.InstituteServiceException(
                "Error performing 'org.openuss.lecture.InstituteService.setGroupOfMember(org.openuss.lecture.InstituteMember member, java.lang.Long instituteId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #setGroupOfMember(org.openuss.lecture.InstituteMember, java.lang.Long)}
      */
    protected abstract void handleSetGroupOfMember(org.openuss.lecture.InstituteMember member, java.lang.Long instituteId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.InstituteService#resendActivationCode(org.openuss.lecture.InstituteInfo, java.lang.Long)
     */
    public void resendActivationCode(org.openuss.lecture.InstituteInfo instituteInfo, java.lang.Long userId)
    {
        try
        {
            this.handleResendActivationCode(instituteInfo, userId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.InstituteServiceException(
                "Error performing 'org.openuss.lecture.InstituteService.resendActivationCode(org.openuss.lecture.InstituteInfo instituteInfo, java.lang.Long userId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #resendActivationCode(org.openuss.lecture.InstituteInfo, java.lang.Long)}
      */
    protected abstract void handleResendActivationCode(org.openuss.lecture.InstituteInfo instituteInfo, java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.InstituteService#getInstituteSecurity(java.lang.Long)
     */
    public org.openuss.lecture.InstituteSecurity getInstituteSecurity(java.lang.Long instituteId)
    {
        try
        {
            return this.handleGetInstituteSecurity(instituteId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.InstituteServiceException(
                "Error performing 'org.openuss.lecture.InstituteService.getInstituteSecurity(java.lang.Long instituteId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getInstituteSecurity(java.lang.Long)}
      */
    protected abstract org.openuss.lecture.InstituteSecurity handleGetInstituteSecurity(java.lang.Long instituteId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.InstituteService#findAllInstitutes(boolean)
     */
    public java.util.List findAllInstitutes(boolean enabledOnly)
    {
        try
        {
            return this.handleFindAllInstitutes(enabledOnly);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.InstituteServiceException(
                "Error performing 'org.openuss.lecture.InstituteService.findAllInstitutes(boolean enabledOnly)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findAllInstitutes(boolean)}
      */
    protected abstract java.util.List handleFindAllInstitutes(boolean enabledOnly)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.InstituteService#findApplicationsByInstitute(java.lang.Long)
     */
    public java.util.List findApplicationsByInstitute(java.lang.Long instituteId)
    {
        try
        {
            return this.handleFindApplicationsByInstitute(instituteId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.InstituteServiceException(
                "Error performing 'org.openuss.lecture.InstituteService.findApplicationsByInstitute(java.lang.Long instituteId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findApplicationsByInstitute(java.lang.Long)}
      */
    protected abstract java.util.List handleFindApplicationsByInstitute(java.lang.Long instituteId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.InstituteService#findApplicationByInstituteAndConfirmed(java.lang.Long, boolean)
     */
    public org.openuss.lecture.ApplicationInfo findApplicationByInstituteAndConfirmed(java.lang.Long instituteId, boolean confirmed)
    {
        try
        {
            return this.handleFindApplicationByInstituteAndConfirmed(instituteId, confirmed);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.InstituteServiceException(
                "Error performing 'org.openuss.lecture.InstituteService.findApplicationByInstituteAndConfirmed(java.lang.Long instituteId, boolean confirmed)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findApplicationByInstituteAndConfirmed(java.lang.Long, boolean)}
      */
    protected abstract org.openuss.lecture.ApplicationInfo handleFindApplicationByInstituteAndConfirmed(java.lang.Long instituteId, boolean confirmed)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.InstituteService#findInstitute(java.lang.Long)
     */
    public org.openuss.lecture.InstituteInfo findInstitute(java.lang.Long instituteId)
    {
        try
        {
            return this.handleFindInstitute(instituteId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.InstituteServiceException(
                "Error performing 'org.openuss.lecture.InstituteService.findInstitute(java.lang.Long instituteId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findInstitute(java.lang.Long)}
      */
    protected abstract org.openuss.lecture.InstituteInfo handleFindInstitute(java.lang.Long instituteId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.InstituteService#findInstitutesByDepartment(java.lang.Long)
     */
    public java.util.List findInstitutesByDepartment(java.lang.Long departmentId)
    {
        try
        {
            return this.handleFindInstitutesByDepartment(departmentId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.InstituteServiceException(
                "Error performing 'org.openuss.lecture.InstituteService.findInstitutesByDepartment(java.lang.Long departmentId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findInstitutesByDepartment(java.lang.Long)}
      */
    protected abstract java.util.List handleFindInstitutesByDepartment(java.lang.Long departmentId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.InstituteService#findInstitutesByDepartmentAndEnabled(java.lang.Long, boolean)
     */
    public java.util.List findInstitutesByDepartmentAndEnabled(java.lang.Long departmentId, boolean enabled)
    {
        try
        {
            return this.handleFindInstitutesByDepartmentAndEnabled(departmentId, enabled);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.InstituteServiceException(
                "Error performing 'org.openuss.lecture.InstituteService.findInstitutesByDepartmentAndEnabled(java.lang.Long departmentId, boolean enabled)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findInstitutesByDepartmentAndEnabled(java.lang.Long, boolean)}
      */
    protected abstract java.util.List handleFindInstitutesByDepartmentAndEnabled(java.lang.Long departmentId, boolean enabled)
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