package org.openuss.lecture;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.lecture.CourseTypeService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.lecture.CourseTypeService
 */
public abstract class CourseTypeServiceBase
    implements org.openuss.lecture.CourseTypeService
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

    private org.openuss.lecture.CourseService courseService;

    /**
     * Sets the reference to <code>courseService</code>.
     */
    public void setCourseService(org.openuss.lecture.CourseService courseService)
    {
        this.courseService = courseService;
    }

    /**
     * Gets the reference to <code>courseService</code>.
     */
    protected org.openuss.lecture.CourseService getCourseService()
    {
        return this.courseService;
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

    /**
     * @see org.openuss.lecture.CourseTypeService#create(org.openuss.lecture.CourseTypeInfo)
     */
    public java.lang.Long create(org.openuss.lecture.CourseTypeInfo courseTypeInfo)
    {
        try
        {
            return this.handleCreate(courseTypeInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseTypeServiceException(
                "Error performing 'org.openuss.lecture.CourseTypeService.create(org.openuss.lecture.CourseTypeInfo courseTypeInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #create(org.openuss.lecture.CourseTypeInfo)}
      */
    protected abstract java.lang.Long handleCreate(org.openuss.lecture.CourseTypeInfo courseTypeInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseTypeService#update(org.openuss.lecture.CourseTypeInfo)
     */
    public void update(org.openuss.lecture.CourseTypeInfo courseTypeInfo)
    {
        try
        {
            this.handleUpdate(courseTypeInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseTypeServiceException(
                "Error performing 'org.openuss.lecture.CourseTypeService.update(org.openuss.lecture.CourseTypeInfo courseTypeInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #update(org.openuss.lecture.CourseTypeInfo)}
      */
    protected abstract void handleUpdate(org.openuss.lecture.CourseTypeInfo courseTypeInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseTypeService#removeCourseType(java.lang.Long)
     */
    public void removeCourseType(java.lang.Long courseTypeId)
    {
        try
        {
            this.handleRemoveCourseType(courseTypeId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseTypeServiceException(
                "Error performing 'org.openuss.lecture.CourseTypeService.removeCourseType(java.lang.Long courseTypeId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeCourseType(java.lang.Long)}
      */
    protected abstract void handleRemoveCourseType(java.lang.Long courseTypeId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseTypeService#findCourseType(java.lang.Long)
     */
    public org.openuss.lecture.CourseTypeInfo findCourseType(java.lang.Long courseTypeId)
    {
        try
        {
            return this.handleFindCourseType(courseTypeId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseTypeServiceException(
                "Error performing 'org.openuss.lecture.CourseTypeService.findCourseType(java.lang.Long courseTypeId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findCourseType(java.lang.Long)}
      */
    protected abstract org.openuss.lecture.CourseTypeInfo handleFindCourseType(java.lang.Long courseTypeId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseTypeService#findCourseTypesByInstitute(java.lang.Long)
     */
    public java.util.List findCourseTypesByInstitute(java.lang.Long instituteId)
    {
        try
        {
            return this.handleFindCourseTypesByInstitute(instituteId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseTypeServiceException(
                "Error performing 'org.openuss.lecture.CourseTypeService.findCourseTypesByInstitute(java.lang.Long instituteId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findCourseTypesByInstitute(java.lang.Long)}
      */
    protected abstract java.util.List handleFindCourseTypesByInstitute(java.lang.Long instituteId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseTypeService#isNoneExistingCourseTypeName(org.openuss.lecture.CourseTypeInfo, java.lang.String)
     */
    public boolean isNoneExistingCourseTypeName(org.openuss.lecture.CourseTypeInfo self, java.lang.String name)
    {
        try
        {
            return this.handleIsNoneExistingCourseTypeName(self, name);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseTypeServiceException(
                "Error performing 'org.openuss.lecture.CourseTypeService.isNoneExistingCourseTypeName(org.openuss.lecture.CourseTypeInfo self, java.lang.String name)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #isNoneExistingCourseTypeName(org.openuss.lecture.CourseTypeInfo, java.lang.String)}
      */
    protected abstract boolean handleIsNoneExistingCourseTypeName(org.openuss.lecture.CourseTypeInfo self, java.lang.String name)
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