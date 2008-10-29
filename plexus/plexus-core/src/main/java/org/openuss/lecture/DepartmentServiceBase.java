package org.openuss.lecture;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.lecture.DepartmentService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.lecture.DepartmentService
 */
public abstract class DepartmentServiceBase
    implements org.openuss.lecture.DepartmentService
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

    private org.openuss.lecture.InstituteService instituteService;

    /**
     * Sets the reference to <code>instituteService</code>.
     */
    public void setInstituteService(org.openuss.lecture.InstituteService instituteService)
    {
        this.instituteService = instituteService;
    }

    /**
     * Gets the reference to <code>instituteService</code>.
     */
    protected org.openuss.lecture.InstituteService getInstituteService()
    {
        return this.instituteService;
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

    /**
     * @see org.openuss.lecture.DepartmentService#create(org.openuss.lecture.DepartmentInfo, java.lang.Long)
     */
    public java.lang.Long create(org.openuss.lecture.DepartmentInfo departmentInfo, java.lang.Long userId)
    {
        try
        {
            return this.handleCreate(departmentInfo, userId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.DepartmentServiceException(
                "Error performing 'org.openuss.lecture.DepartmentService.create(org.openuss.lecture.DepartmentInfo departmentInfo, java.lang.Long userId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #create(org.openuss.lecture.DepartmentInfo, java.lang.Long)}
      */
    protected abstract java.lang.Long handleCreate(org.openuss.lecture.DepartmentInfo departmentInfo, java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.DepartmentService#update(org.openuss.lecture.DepartmentInfo)
     */
    public void update(org.openuss.lecture.DepartmentInfo departmentInfo)
    {
        try
        {
            this.handleUpdate(departmentInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.DepartmentServiceException(
                "Error performing 'org.openuss.lecture.DepartmentService.update(org.openuss.lecture.DepartmentInfo departmentInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #update(org.openuss.lecture.DepartmentInfo)}
      */
    protected abstract void handleUpdate(org.openuss.lecture.DepartmentInfo departmentInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.DepartmentService#removeDepartment(java.lang.Long)
     */
    public void removeDepartment(java.lang.Long departmentId)
    {
        try
        {
            this.handleRemoveDepartment(departmentId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.DepartmentServiceException(
                "Error performing 'org.openuss.lecture.DepartmentService.removeDepartment(java.lang.Long departmentId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeDepartment(java.lang.Long)}
      */
    protected abstract void handleRemoveDepartment(java.lang.Long departmentId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.DepartmentService#removeCompleteDepartmentTree(java.lang.Long)
     */
    public void removeCompleteDepartmentTree(java.lang.Long departmentId)
    {
        try
        {
            this.handleRemoveCompleteDepartmentTree(departmentId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.DepartmentServiceException(
                "Error performing 'org.openuss.lecture.DepartmentService.removeCompleteDepartmentTree(java.lang.Long departmentId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeCompleteDepartmentTree(java.lang.Long)}
      */
    protected abstract void handleRemoveCompleteDepartmentTree(java.lang.Long departmentId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.DepartmentService#acceptApplication(java.lang.Long, java.lang.Long)
     */
    public void acceptApplication(java.lang.Long applicationId, java.lang.Long userId)
    {
        try
        {
            this.handleAcceptApplication(applicationId, userId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.DepartmentServiceException(
                "Error performing 'org.openuss.lecture.DepartmentService.acceptApplication(java.lang.Long applicationId, java.lang.Long userId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #acceptApplication(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleAcceptApplication(java.lang.Long applicationId, java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.DepartmentService#rejectApplication(java.lang.Long)
     */
    public void rejectApplication(java.lang.Long applicationId)
    {
        try
        {
            this.handleRejectApplication(applicationId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.DepartmentServiceException(
                "Error performing 'org.openuss.lecture.DepartmentService.rejectApplication(java.lang.Long applicationId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #rejectApplication(java.lang.Long)}
      */
    protected abstract void handleRejectApplication(java.lang.Long applicationId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.DepartmentService#signoffInstitute(java.lang.Long)
     */
    public void signoffInstitute(java.lang.Long instituteId)
    {
        try
        {
            this.handleSignoffInstitute(instituteId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.DepartmentServiceException(
                "Error performing 'org.openuss.lecture.DepartmentService.signoffInstitute(java.lang.Long instituteId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #signoffInstitute(java.lang.Long)}
      */
    protected abstract void handleSignoffInstitute(java.lang.Long instituteId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.DepartmentService#setDepartmentStatus(java.lang.Long, boolean)
     */
    public void setDepartmentStatus(java.lang.Long departmentId, boolean status)
    {
        try
        {
            this.handleSetDepartmentStatus(departmentId, status);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.DepartmentServiceException(
                "Error performing 'org.openuss.lecture.DepartmentService.setDepartmentStatus(java.lang.Long departmentId, boolean status)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #setDepartmentStatus(java.lang.Long, boolean)}
      */
    protected abstract void handleSetDepartmentStatus(java.lang.Long departmentId, boolean status)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.DepartmentService#findDepartment(java.lang.Long)
     */
    public org.openuss.lecture.DepartmentInfo findDepartment(java.lang.Long departmentId)
    {
        try
        {
            return this.handleFindDepartment(departmentId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.DepartmentServiceException(
                "Error performing 'org.openuss.lecture.DepartmentService.findDepartment(java.lang.Long departmentId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findDepartment(java.lang.Long)}
      */
    protected abstract org.openuss.lecture.DepartmentInfo handleFindDepartment(java.lang.Long departmentId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.DepartmentService#findDepartmentsByUniversity(java.lang.Long)
     */
    public java.util.List findDepartmentsByUniversity(java.lang.Long universityId)
    {
        try
        {
            return this.handleFindDepartmentsByUniversity(universityId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.DepartmentServiceException(
                "Error performing 'org.openuss.lecture.DepartmentService.findDepartmentsByUniversity(java.lang.Long universityId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findDepartmentsByUniversity(java.lang.Long)}
      */
    protected abstract java.util.List handleFindDepartmentsByUniversity(java.lang.Long universityId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.DepartmentService#findDepartmentsByUniversityAndEnabled(java.lang.Long, boolean)
     */
    public java.util.List findDepartmentsByUniversityAndEnabled(java.lang.Long universityId, boolean enabled)
    {
        try
        {
            return this.handleFindDepartmentsByUniversityAndEnabled(universityId, enabled);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.DepartmentServiceException(
                "Error performing 'org.openuss.lecture.DepartmentService.findDepartmentsByUniversityAndEnabled(java.lang.Long universityId, boolean enabled)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findDepartmentsByUniversityAndEnabled(java.lang.Long, boolean)}
      */
    protected abstract java.util.List handleFindDepartmentsByUniversityAndEnabled(java.lang.Long universityId, boolean enabled)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.DepartmentService#findDepartmentsByUniversityAndType(java.lang.Long, org.openuss.lecture.DepartmentType)
     */
    public java.util.List findDepartmentsByUniversityAndType(java.lang.Long universityId, org.openuss.lecture.DepartmentType type)
    {
        try
        {
            return this.handleFindDepartmentsByUniversityAndType(universityId, type);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.DepartmentServiceException(
                "Error performing 'org.openuss.lecture.DepartmentService.findDepartmentsByUniversityAndType(java.lang.Long universityId, org.openuss.lecture.DepartmentType type)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findDepartmentsByUniversityAndType(java.lang.Long, org.openuss.lecture.DepartmentType)}
      */
    protected abstract java.util.List handleFindDepartmentsByUniversityAndType(java.lang.Long universityId, org.openuss.lecture.DepartmentType type)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.DepartmentService#findDepartmentsByUniversityAndTypeAndEnabled(java.lang.Long, org.openuss.lecture.DepartmentType, boolean)
     */
    public java.util.List findDepartmentsByUniversityAndTypeAndEnabled(java.lang.Long universityId, org.openuss.lecture.DepartmentType type, boolean enabled)
    {
        try
        {
            return this.handleFindDepartmentsByUniversityAndTypeAndEnabled(universityId, type, enabled);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.DepartmentServiceException(
                "Error performing 'org.openuss.lecture.DepartmentService.findDepartmentsByUniversityAndTypeAndEnabled(java.lang.Long universityId, org.openuss.lecture.DepartmentType type, boolean enabled)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findDepartmentsByUniversityAndTypeAndEnabled(java.lang.Long, org.openuss.lecture.DepartmentType, boolean)}
      */
    protected abstract java.util.List handleFindDepartmentsByUniversityAndTypeAndEnabled(java.lang.Long universityId, org.openuss.lecture.DepartmentType type, boolean enabled)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.DepartmentService#findApplication(java.lang.Long)
     */
    public org.openuss.lecture.ApplicationInfo findApplication(java.lang.Long applicationId)
    {
        try
        {
            return this.handleFindApplication(applicationId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.DepartmentServiceException(
                "Error performing 'org.openuss.lecture.DepartmentService.findApplication(java.lang.Long applicationId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findApplication(java.lang.Long)}
      */
    protected abstract org.openuss.lecture.ApplicationInfo handleFindApplication(java.lang.Long applicationId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.DepartmentService#findDepartmentsByType(org.openuss.lecture.DepartmentType)
     */
    public java.util.List findDepartmentsByType(org.openuss.lecture.DepartmentType type)
    {
        try
        {
            return this.handleFindDepartmentsByType(type);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.DepartmentServiceException(
                "Error performing 'org.openuss.lecture.DepartmentService.findDepartmentsByType(org.openuss.lecture.DepartmentType type)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findDepartmentsByType(org.openuss.lecture.DepartmentType)}
      */
    protected abstract java.util.List handleFindDepartmentsByType(org.openuss.lecture.DepartmentType type)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.DepartmentService#findApplicationsByDepartment(java.lang.Long)
     */
    public java.util.List findApplicationsByDepartment(java.lang.Long departmentId)
    {
        try
        {
            return this.handleFindApplicationsByDepartment(departmentId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.DepartmentServiceException(
                "Error performing 'org.openuss.lecture.DepartmentService.findApplicationsByDepartment(java.lang.Long departmentId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findApplicationsByDepartment(java.lang.Long)}
      */
    protected abstract java.util.List handleFindApplicationsByDepartment(java.lang.Long departmentId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.DepartmentService#findApplicationsByDepartmentAndConfirmed(java.lang.Long, boolean)
     */
    public java.util.List findApplicationsByDepartmentAndConfirmed(java.lang.Long departmentId, boolean confirmed)
    {
        try
        {
            return this.handleFindApplicationsByDepartmentAndConfirmed(departmentId, confirmed);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.DepartmentServiceException(
                "Error performing 'org.openuss.lecture.DepartmentService.findApplicationsByDepartmentAndConfirmed(java.lang.Long departmentId, boolean confirmed)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findApplicationsByDepartmentAndConfirmed(java.lang.Long, boolean)}
      */
    protected abstract java.util.List handleFindApplicationsByDepartmentAndConfirmed(java.lang.Long departmentId, boolean confirmed)
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