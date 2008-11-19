package org.openuss.lecture;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.lecture.UniversityService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.lecture.UniversityService
 */
public abstract class UniversityServiceBase
    implements org.openuss.lecture.UniversityService
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

    private org.openuss.lecture.DepartmentService departmentService;

    /**
     * Sets the reference to <code>departmentService</code>.
     */
    public void setDepartmentService(org.openuss.lecture.DepartmentService departmentService)
    {
        this.departmentService = departmentService;
    }

    /**
     * Gets the reference to <code>departmentService</code>.
     */
    protected org.openuss.lecture.DepartmentService getDepartmentService()
    {
        return this.departmentService;
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

    private org.openuss.lecture.PeriodDao periodDao;

    /**
     * Sets the reference to <code>period</code>'s DAO.
     */
    public void setPeriodDao(org.openuss.lecture.PeriodDao periodDao)
    {
        this.periodDao = periodDao;
    }

    /**
     * Gets the reference to <code>period</code>'s DAO.
     */
    protected org.openuss.lecture.PeriodDao getPeriodDao()
    {
        return this.periodDao;
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

    private org.openuss.lecture.CourseTypeDao courseTypeDao;

    /**
     * Sets the reference to <code>courseType</code>'s DAO.
     */
    public void setCourseTypeDao(org.openuss.lecture.CourseTypeDao courseTypeDao)
    {
        this.courseTypeDao = courseTypeDao;
    }

    /**
     * Gets the reference to <code>courseType</code>'s DAO.
     */
    protected org.openuss.lecture.CourseTypeDao getCourseTypeDao()
    {
        return this.courseTypeDao;
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
     * @see org.openuss.lecture.UniversityService#createUniversity(org.openuss.lecture.UniversityInfo, java.lang.Long)
     */
    public java.lang.Long createUniversity(org.openuss.lecture.UniversityInfo universityInfo, java.lang.Long userId)
    {
        try
        {
            return this.handleCreateUniversity(universityInfo, userId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.createUniversity(org.openuss.lecture.UniversityInfo universityInfo, java.lang.Long userId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createUniversity(org.openuss.lecture.UniversityInfo, java.lang.Long)}
      */
    protected abstract java.lang.Long handleCreateUniversity(org.openuss.lecture.UniversityInfo universityInfo, java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#createPeriod(org.openuss.lecture.PeriodInfo)
     */
    public java.lang.Long createPeriod(org.openuss.lecture.PeriodInfo periodInfo)
    {
        try
        {
            return this.handleCreatePeriod(periodInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.createPeriod(org.openuss.lecture.PeriodInfo periodInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createPeriod(org.openuss.lecture.PeriodInfo)}
      */
    protected abstract java.lang.Long handleCreatePeriod(org.openuss.lecture.PeriodInfo periodInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#update(org.openuss.lecture.UniversityInfo)
     */
    public void update(org.openuss.lecture.UniversityInfo universityInfo)
    {
        try
        {
            this.handleUpdate(universityInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.update(org.openuss.lecture.UniversityInfo universityInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #update(org.openuss.lecture.UniversityInfo)}
      */
    protected abstract void handleUpdate(org.openuss.lecture.UniversityInfo universityInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#update(org.openuss.lecture.PeriodInfo)
     */
    public void update(org.openuss.lecture.PeriodInfo periodInfo)
    {
        try
        {
            this.handleUpdate(periodInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.update(org.openuss.lecture.PeriodInfo periodInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #update(org.openuss.lecture.PeriodInfo)}
      */
    protected abstract void handleUpdate(org.openuss.lecture.PeriodInfo periodInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#removeUniversity(java.lang.Long)
     */
    public void removeUniversity(java.lang.Long universityId)
    {
        try
        {
            this.handleRemoveUniversity(universityId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.removeUniversity(java.lang.Long universityId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeUniversity(java.lang.Long)}
      */
    protected abstract void handleRemoveUniversity(java.lang.Long universityId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#removeCompleteUniversityTree(java.lang.Long)
     */
    public void removeCompleteUniversityTree(java.lang.Long universityId)
    {
        try
        {
            this.handleRemoveCompleteUniversityTree(universityId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.removeCompleteUniversityTree(java.lang.Long universityId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeCompleteUniversityTree(java.lang.Long)}
      */
    protected abstract void handleRemoveCompleteUniversityTree(java.lang.Long universityId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#removePeriod(java.lang.Long)
     */
    public void removePeriod(java.lang.Long periodId)
    {
        try
        {
            this.handleRemovePeriod(periodId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.removePeriod(java.lang.Long periodId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removePeriod(java.lang.Long)}
      */
    protected abstract void handleRemovePeriod(java.lang.Long periodId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#removePeriodAndCourses(java.lang.Long)
     */
    public void removePeriodAndCourses(java.lang.Long periodId)
    {
        try
        {
            this.handleRemovePeriodAndCourses(periodId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.removePeriodAndCourses(java.lang.Long periodId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removePeriodAndCourses(java.lang.Long)}
      */
    protected abstract void handleRemovePeriodAndCourses(java.lang.Long periodId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#setUniversityStatus(java.lang.Long, boolean)
     */
    public void setUniversityStatus(java.lang.Long universityId, boolean status)
    {
        try
        {
            this.handleSetUniversityStatus(universityId, status);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.setUniversityStatus(java.lang.Long universityId, boolean status)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #setUniversityStatus(java.lang.Long, boolean)}
      */
    protected abstract void handleSetUniversityStatus(java.lang.Long universityId, boolean status)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#isActivePeriod(java.lang.Long)
     */
    public boolean isActivePeriod(java.lang.Long periodId)
    {
        try
        {
            return this.handleIsActivePeriod(periodId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.isActivePeriod(java.lang.Long periodId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #isActivePeriod(java.lang.Long)}
      */
    protected abstract boolean handleIsActivePeriod(java.lang.Long periodId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#findUniversity(java.lang.Long)
     */
    public org.openuss.lecture.UniversityInfo findUniversity(java.lang.Long universityId)
    {
        try
        {
            return this.handleFindUniversity(universityId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.findUniversity(java.lang.Long universityId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findUniversity(java.lang.Long)}
      */
    protected abstract org.openuss.lecture.UniversityInfo handleFindUniversity(java.lang.Long universityId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#findAllUniversities()
     */
    public java.util.List findAllUniversities()
    {
        try
        {
            return this.handleFindAllUniversities();
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.findAllUniversities()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findAllUniversities()}
      */
    protected abstract java.util.List handleFindAllUniversities()
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#findUniversitiesByEnabled(boolean)
     */
    public java.util.List findUniversitiesByEnabled(boolean enabled)
    {
        try
        {
            return this.handleFindUniversitiesByEnabled(enabled);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.findUniversitiesByEnabled(boolean enabled)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findUniversitiesByEnabled(boolean)}
      */
    protected abstract java.util.List handleFindUniversitiesByEnabled(boolean enabled)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#findPeriod(java.lang.Long)
     */
    public org.openuss.lecture.PeriodInfo findPeriod(java.lang.Long periodId)
    {
        try
        {
            return this.handleFindPeriod(periodId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.findPeriod(java.lang.Long periodId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findPeriod(java.lang.Long)}
      */
    protected abstract org.openuss.lecture.PeriodInfo handleFindPeriod(java.lang.Long periodId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#findPeriodsByUniversity(java.lang.Long)
     */
    public java.util.List findPeriodsByUniversity(java.lang.Long universityId)
    {
        try
        {
            return this.handleFindPeriodsByUniversity(universityId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.findPeriodsByUniversity(java.lang.Long universityId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findPeriodsByUniversity(java.lang.Long)}
      */
    protected abstract java.util.List handleFindPeriodsByUniversity(java.lang.Long universityId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#findPeriodsByUniversityAndActivation(java.lang.Long, boolean)
     */
    public java.util.List findPeriodsByUniversityAndActivation(java.lang.Long universityId, boolean active)
    {
        try
        {
            return this.handleFindPeriodsByUniversityAndActivation(universityId, active);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.findPeriodsByUniversityAndActivation(java.lang.Long universityId, boolean active)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findPeriodsByUniversityAndActivation(java.lang.Long, boolean)}
      */
    protected abstract java.util.List handleFindPeriodsByUniversityAndActivation(java.lang.Long universityId, boolean active)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#findUniversitiesByTypeAndEnabled(org.openuss.lecture.UniversityType, boolean)
     */
    public java.util.List findUniversitiesByTypeAndEnabled(org.openuss.lecture.UniversityType universityType, boolean enabled)
    {
        try
        {
            return this.handleFindUniversitiesByTypeAndEnabled(universityType, enabled);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.findUniversitiesByTypeAndEnabled(org.openuss.lecture.UniversityType universityType, boolean enabled)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findUniversitiesByTypeAndEnabled(org.openuss.lecture.UniversityType, boolean)}
      */
    protected abstract java.util.List handleFindUniversitiesByTypeAndEnabled(org.openuss.lecture.UniversityType universityType, boolean enabled)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#findUniversitiesByMemberAndEnabled(java.lang.Long, boolean)
     */
    public java.util.List findUniversitiesByMemberAndEnabled(java.lang.Long userId, boolean enabled)
    {
        try
        {
            return this.handleFindUniversitiesByMemberAndEnabled(userId, enabled);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.findUniversitiesByMemberAndEnabled(java.lang.Long userId, boolean enabled)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findUniversitiesByMemberAndEnabled(java.lang.Long, boolean)}
      */
    protected abstract java.util.List handleFindUniversitiesByMemberAndEnabled(java.lang.Long userId, boolean enabled)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#findPeriodsByUniversityWithCourses(java.lang.Long)
     */
    public java.util.List findPeriodsByUniversityWithCourses(java.lang.Long universityId)
    {
        try
        {
            return this.handleFindPeriodsByUniversityWithCourses(universityId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.findPeriodsByUniversityWithCourses(java.lang.Long universityId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findPeriodsByUniversityWithCourses(java.lang.Long)}
      */
    protected abstract java.util.List handleFindPeriodsByUniversityWithCourses(java.lang.Long universityId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.UniversityService#findPeriodsByInstituteWithCoursesOrActive(org.openuss.lecture.InstituteInfo)
     */
    public java.util.List findPeriodsByInstituteWithCoursesOrActive(org.openuss.lecture.InstituteInfo instituteInfo)
    {
        try
        {
            return this.handleFindPeriodsByInstituteWithCoursesOrActive(instituteInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.UniversityServiceException(
                "Error performing 'org.openuss.lecture.UniversityService.findPeriodsByInstituteWithCoursesOrActive(org.openuss.lecture.InstituteInfo instituteInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findPeriodsByInstituteWithCoursesOrActive(org.openuss.lecture.InstituteInfo)}
      */
    protected abstract java.util.List handleFindPeriodsByInstituteWithCoursesOrActive(org.openuss.lecture.InstituteInfo instituteInfo)
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