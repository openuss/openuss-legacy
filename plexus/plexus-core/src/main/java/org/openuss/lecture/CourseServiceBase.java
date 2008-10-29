package org.openuss.lecture;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.lecture.CourseService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.lecture.CourseService
 */
public abstract class CourseServiceBase
    implements org.openuss.lecture.CourseService
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

    private org.openuss.system.SystemService systemService;

    /**
     * Sets the reference to <code>systemService</code>.
     */
    public void setSystemService(org.openuss.system.SystemService systemService)
    {
        this.systemService = systemService;
    }

    /**
     * Gets the reference to <code>systemService</code>.
     */
    protected org.openuss.system.SystemService getSystemService()
    {
        return this.systemService;
    }

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

    private org.openuss.discussion.DiscussionService discussionService;

    /**
     * Sets the reference to <code>discussionService</code>.
     */
    public void setDiscussionService(org.openuss.discussion.DiscussionService discussionService)
    {
        this.discussionService = discussionService;
    }

    /**
     * Gets the reference to <code>discussionService</code>.
     */
    protected org.openuss.discussion.DiscussionService getDiscussionService()
    {
        return this.discussionService;
    }

    private org.openuss.course.newsletter.CourseNewsletterService courseNewsletterService;

    /**
     * Sets the reference to <code>courseNewsletterService</code>.
     */
    public void setCourseNewsletterService(org.openuss.course.newsletter.CourseNewsletterService courseNewsletterService)
    {
        this.courseNewsletterService = courseNewsletterService;
    }

    /**
     * Gets the reference to <code>courseNewsletterService</code>.
     */
    protected org.openuss.course.newsletter.CourseNewsletterService getCourseNewsletterService()
    {
        return this.courseNewsletterService;
    }

    private org.openuss.desktop.DesktopService2 desktopService2;

    /**
     * Sets the reference to <code>desktopService2</code>.
     */
    public void setDesktopService2(org.openuss.desktop.DesktopService2 desktopService2)
    {
        this.desktopService2 = desktopService2;
    }

    /**
     * Gets the reference to <code>desktopService2</code>.
     */
    protected org.openuss.desktop.DesktopService2 getDesktopService2()
    {
        return this.desktopService2;
    }

    private org.openuss.lecture.CourseMemberDao courseMemberDao;

    /**
     * Sets the reference to <code>courseMember</code>'s DAO.
     */
    public void setCourseMemberDao(org.openuss.lecture.CourseMemberDao courseMemberDao)
    {
        this.courseMemberDao = courseMemberDao;
    }

    /**
     * Gets the reference to <code>courseMember</code>'s DAO.
     */
    protected org.openuss.lecture.CourseMemberDao getCourseMemberDao()
    {
        return this.courseMemberDao;
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

    /**
     * @see org.openuss.lecture.CourseService#removeMember(org.openuss.lecture.CourseMemberInfo)
     */
    public void removeMember(org.openuss.lecture.CourseMemberInfo member)
    {
        try
        {
            this.handleRemoveMember(member);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.removeMember(org.openuss.lecture.CourseMemberInfo member)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeMember(org.openuss.lecture.CourseMemberInfo)}
      */
    protected abstract void handleRemoveMember(org.openuss.lecture.CourseMemberInfo member)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#acceptAspirant(org.openuss.lecture.CourseMemberInfo)
     */
    public void acceptAspirant(org.openuss.lecture.CourseMemberInfo member)
    {
        try
        {
            this.handleAcceptAspirant(member);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.acceptAspirant(org.openuss.lecture.CourseMemberInfo member)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #acceptAspirant(org.openuss.lecture.CourseMemberInfo)}
      */
    protected abstract void handleAcceptAspirant(org.openuss.lecture.CourseMemberInfo member)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#rejectAspirant(org.openuss.lecture.CourseMemberInfo)
     */
    public void rejectAspirant(org.openuss.lecture.CourseMemberInfo member)
    {
        try
        {
            this.handleRejectAspirant(member);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.rejectAspirant(org.openuss.lecture.CourseMemberInfo member)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #rejectAspirant(org.openuss.lecture.CourseMemberInfo)}
      */
    protected abstract void handleRejectAspirant(org.openuss.lecture.CourseMemberInfo member)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#getAspirants(org.openuss.lecture.CourseInfo)
     */
    public java.util.List getAspirants(org.openuss.lecture.CourseInfo course)
    {
        try
        {
            return this.handleGetAspirants(course);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.getAspirants(org.openuss.lecture.CourseInfo course)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getAspirants(org.openuss.lecture.CourseInfo)}
      */
    protected abstract java.util.List handleGetAspirants(org.openuss.lecture.CourseInfo course)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#addAssistant(org.openuss.lecture.CourseInfo, org.openuss.security.UserInfo)
     */
    public void addAssistant(org.openuss.lecture.CourseInfo course, org.openuss.security.UserInfo user)
    {
        try
        {
            this.handleAddAssistant(course, user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.addAssistant(org.openuss.lecture.CourseInfo course, org.openuss.security.UserInfo user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addAssistant(org.openuss.lecture.CourseInfo, org.openuss.security.UserInfo)}
      */
    protected abstract void handleAddAssistant(org.openuss.lecture.CourseInfo course, org.openuss.security.UserInfo user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#addAspirant(org.openuss.lecture.CourseInfo, org.openuss.security.UserInfo)
     */
    public void addAspirant(org.openuss.lecture.CourseInfo course, org.openuss.security.UserInfo user)
    {
        try
        {
            this.handleAddAspirant(course, user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.addAspirant(org.openuss.lecture.CourseInfo course, org.openuss.security.UserInfo user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addAspirant(org.openuss.lecture.CourseInfo, org.openuss.security.UserInfo)}
      */
    protected abstract void handleAddAspirant(org.openuss.lecture.CourseInfo course, org.openuss.security.UserInfo user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#addParticipant(org.openuss.lecture.CourseInfo, org.openuss.security.UserInfo)
     */
    public void addParticipant(org.openuss.lecture.CourseInfo course, org.openuss.security.UserInfo user)
    {
        try
        {
            this.handleAddParticipant(course, user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.addParticipant(org.openuss.lecture.CourseInfo course, org.openuss.security.UserInfo user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addParticipant(org.openuss.lecture.CourseInfo, org.openuss.security.UserInfo)}
      */
    protected abstract void handleAddParticipant(org.openuss.lecture.CourseInfo course, org.openuss.security.UserInfo user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#applyUser(org.openuss.lecture.CourseInfo, org.openuss.security.UserInfo, java.lang.String)
     */
    public void applyUser(org.openuss.lecture.CourseInfo courseInfo, org.openuss.security.UserInfo userInfo, java.lang.String password)
    {
        try
        {
            this.handleApplyUser(courseInfo, userInfo, password);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.applyUser(org.openuss.lecture.CourseInfo courseInfo, org.openuss.security.UserInfo userInfo, java.lang.String password)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #applyUser(org.openuss.lecture.CourseInfo, org.openuss.security.UserInfo, java.lang.String)}
      */
    protected abstract void handleApplyUser(org.openuss.lecture.CourseInfo courseInfo, org.openuss.security.UserInfo userInfo, java.lang.String password)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#applyUser(org.openuss.lecture.CourseInfo, org.openuss.security.UserInfo)
     */
    public void applyUser(org.openuss.lecture.CourseInfo courseInfo, org.openuss.security.UserInfo userInfo)
    {
        try
        {
            this.handleApplyUser(courseInfo, userInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.applyUser(org.openuss.lecture.CourseInfo courseInfo, org.openuss.security.UserInfo userInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #applyUser(org.openuss.lecture.CourseInfo, org.openuss.security.UserInfo)}
      */
    protected abstract void handleApplyUser(org.openuss.lecture.CourseInfo courseInfo, org.openuss.security.UserInfo userInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#removeAspirants(org.openuss.lecture.CourseInfo)
     */
    public void removeAspirants(org.openuss.lecture.CourseInfo course)
    {
        try
        {
            this.handleRemoveAspirants(course);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.removeAspirants(org.openuss.lecture.CourseInfo course)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeAspirants(org.openuss.lecture.CourseInfo)}
      */
    protected abstract void handleRemoveAspirants(org.openuss.lecture.CourseInfo course)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#updateCourse(org.openuss.lecture.CourseInfo)
     */
    public void updateCourse(org.openuss.lecture.CourseInfo course)
    {
        try
        {
            this.handleUpdateCourse(course);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.updateCourse(org.openuss.lecture.CourseInfo course)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #updateCourse(org.openuss.lecture.CourseInfo)}
      */
    protected abstract void handleUpdateCourse(org.openuss.lecture.CourseInfo course)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#create(org.openuss.lecture.CourseInfo)
     */
    public java.lang.Long create(org.openuss.lecture.CourseInfo courseInfo)
    {
        try
        {
            return this.handleCreate(courseInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.create(org.openuss.lecture.CourseInfo courseInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #create(org.openuss.lecture.CourseInfo)}
      */
    protected abstract java.lang.Long handleCreate(org.openuss.lecture.CourseInfo courseInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#removeCourse(java.lang.Long)
     */
    public void removeCourse(java.lang.Long courseId)
    {
        try
        {
            this.handleRemoveCourse(courseId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.removeCourse(java.lang.Long courseId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeCourse(java.lang.Long)}
      */
    protected abstract void handleRemoveCourse(java.lang.Long courseId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#setCourseStatus(java.lang.Long, boolean)
     */
    public void setCourseStatus(java.lang.Long courseId, boolean status)
    {
        try
        {
            this.handleSetCourseStatus(courseId, status);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.setCourseStatus(java.lang.Long courseId, boolean status)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #setCourseStatus(java.lang.Long, boolean)}
      */
    protected abstract void handleSetCourseStatus(java.lang.Long courseId, boolean status)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#isNoneExistingCourseShortcut(org.openuss.lecture.CourseInfo, java.lang.String)
     */
    public boolean isNoneExistingCourseShortcut(org.openuss.lecture.CourseInfo self, java.lang.String shortcut)
    {
        try
        {
            return this.handleIsNoneExistingCourseShortcut(self, shortcut);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.isNoneExistingCourseShortcut(org.openuss.lecture.CourseInfo self, java.lang.String shortcut)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #isNoneExistingCourseShortcut(org.openuss.lecture.CourseInfo, java.lang.String)}
      */
    protected abstract boolean handleIsNoneExistingCourseShortcut(org.openuss.lecture.CourseInfo self, java.lang.String shortcut)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#getAssistants(org.openuss.lecture.CourseInfo)
     */
    public java.util.List getAssistants(org.openuss.lecture.CourseInfo course)
    {
        try
        {
            return this.handleGetAssistants(course);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.getAssistants(org.openuss.lecture.CourseInfo course)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getAssistants(org.openuss.lecture.CourseInfo)}
      */
    protected abstract java.util.List handleGetAssistants(org.openuss.lecture.CourseInfo course)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#getParticipants(org.openuss.lecture.CourseInfo)
     */
    public java.util.List getParticipants(org.openuss.lecture.CourseInfo course)
    {
        try
        {
            return this.handleGetParticipants(course);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.getParticipants(org.openuss.lecture.CourseInfo course)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getParticipants(org.openuss.lecture.CourseInfo)}
      */
    protected abstract java.util.List handleGetParticipants(org.openuss.lecture.CourseInfo course)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#getMemberInfo(org.openuss.lecture.CourseInfo, org.openuss.security.UserInfo)
     */
    public org.openuss.lecture.CourseMemberInfo getMemberInfo(org.openuss.lecture.CourseInfo course, org.openuss.security.UserInfo user)
    {
        try
        {
            return this.handleGetMemberInfo(course, user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.getMemberInfo(org.openuss.lecture.CourseInfo course, org.openuss.security.UserInfo user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getMemberInfo(org.openuss.lecture.CourseInfo, org.openuss.security.UserInfo)}
      */
    protected abstract org.openuss.lecture.CourseMemberInfo handleGetMemberInfo(org.openuss.lecture.CourseInfo course, org.openuss.security.UserInfo user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#findCourse(java.lang.Long)
     */
    public org.openuss.lecture.CourseInfo findCourse(java.lang.Long courseId)
    {
        try
        {
            return this.handleFindCourse(courseId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.findCourse(java.lang.Long courseId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findCourse(java.lang.Long)}
      */
    protected abstract org.openuss.lecture.CourseInfo handleFindCourse(java.lang.Long courseId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#findCoursesByCourseType(java.lang.Long)
     */
    public java.util.List findCoursesByCourseType(java.lang.Long courseTypeId)
    {
        try
        {
            return this.handleFindCoursesByCourseType(courseTypeId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.findCoursesByCourseType(java.lang.Long courseTypeId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findCoursesByCourseType(java.lang.Long)}
      */
    protected abstract java.util.List handleFindCoursesByCourseType(java.lang.Long courseTypeId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#findCoursesByPeriodAndInstitute(java.lang.Long, java.lang.Long)
     */
    public java.util.List findCoursesByPeriodAndInstitute(java.lang.Long periodId, java.lang.Long instituteId)
    {
        try
        {
            return this.handleFindCoursesByPeriodAndInstitute(periodId, instituteId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.findCoursesByPeriodAndInstitute(java.lang.Long periodId, java.lang.Long instituteId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findCoursesByPeriodAndInstitute(java.lang.Long, java.lang.Long)}
      */
    protected abstract java.util.List handleFindCoursesByPeriodAndInstitute(java.lang.Long periodId, java.lang.Long instituteId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#findCoursesByActivePeriods(org.openuss.lecture.InstituteInfo)
     */
    public java.util.List findCoursesByActivePeriods(org.openuss.lecture.InstituteInfo instituteInfo)
    {
        try
        {
            return this.handleFindCoursesByActivePeriods(instituteInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.findCoursesByActivePeriods(org.openuss.lecture.InstituteInfo instituteInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findCoursesByActivePeriods(org.openuss.lecture.InstituteInfo)}
      */
    protected abstract java.util.List handleFindCoursesByActivePeriods(org.openuss.lecture.InstituteInfo instituteInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#findCoursesByInstituteAndEnabled(java.lang.Long, boolean)
     */
    public java.util.List findCoursesByInstituteAndEnabled(java.lang.Long instituteId, boolean enabled)
    {
        try
        {
            return this.handleFindCoursesByInstituteAndEnabled(instituteId, enabled);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.findCoursesByInstituteAndEnabled(java.lang.Long instituteId, boolean enabled)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findCoursesByInstituteAndEnabled(java.lang.Long, boolean)}
      */
    protected abstract java.util.List handleFindCoursesByInstituteAndEnabled(java.lang.Long instituteId, boolean enabled)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#findCoursesByPeriodAndInstituteAndEnabled(java.lang.Long, java.lang.Long, boolean)
     */
    public java.util.List findCoursesByPeriodAndInstituteAndEnabled(java.lang.Long periodId, java.lang.Long instituteId, boolean enabled)
    {
        try
        {
            return this.handleFindCoursesByPeriodAndInstituteAndEnabled(periodId, instituteId, enabled);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.findCoursesByPeriodAndInstituteAndEnabled(java.lang.Long periodId, java.lang.Long instituteId, boolean enabled)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findCoursesByPeriodAndInstituteAndEnabled(java.lang.Long, java.lang.Long, boolean)}
      */
    protected abstract java.util.List handleFindCoursesByPeriodAndInstituteAndEnabled(java.lang.Long periodId, java.lang.Long instituteId, boolean enabled)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#findCoursesByActivePeriodsAndEnabled(java.lang.Long, boolean)
     */
    public java.util.List findCoursesByActivePeriodsAndEnabled(java.lang.Long instituteId, boolean enabled)
    {
        try
        {
            return this.handleFindCoursesByActivePeriodsAndEnabled(instituteId, enabled);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.findCoursesByActivePeriodsAndEnabled(java.lang.Long instituteId, boolean enabled)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findCoursesByActivePeriodsAndEnabled(java.lang.Long, boolean)}
      */
    protected abstract java.util.List handleFindCoursesByActivePeriodsAndEnabled(java.lang.Long instituteId, boolean enabled)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#findAllCoursesByInstitute(java.lang.Long)
     */
    public java.util.List findAllCoursesByInstitute(java.lang.Long instituteId)
    {
        try
        {
            return this.handleFindAllCoursesByInstitute(instituteId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.findAllCoursesByInstitute(java.lang.Long instituteId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findAllCoursesByInstitute(java.lang.Long)}
      */
    protected abstract java.util.List handleFindAllCoursesByInstitute(java.lang.Long instituteId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#findAllCoursesByDepartment(java.lang.Long, java.lang.Boolean, java.lang.Boolean)
     */
    public java.util.List findAllCoursesByDepartment(java.lang.Long departmentId, java.lang.Boolean onlyActive, java.lang.Boolean onlyEnabled)
    {
        try
        {
            return this.handleFindAllCoursesByDepartment(departmentId, onlyActive, onlyEnabled);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.findAllCoursesByDepartment(java.lang.Long departmentId, java.lang.Boolean onlyActive, java.lang.Boolean onlyEnabled)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findAllCoursesByDepartment(java.lang.Long, java.lang.Boolean, java.lang.Boolean)}
      */
    protected abstract java.util.List handleFindAllCoursesByDepartment(java.lang.Long departmentId, java.lang.Boolean onlyActive, java.lang.Boolean onlyEnabled)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#findAllCoursesByDepartmentAndPeriod(java.lang.Long, java.lang.Long, java.lang.Boolean)
     */
    public java.util.List findAllCoursesByDepartmentAndPeriod(java.lang.Long departmentId, java.lang.Long periodId, java.lang.Boolean onlyEnabled)
    {
        try
        {
            return this.handleFindAllCoursesByDepartmentAndPeriod(departmentId, periodId, onlyEnabled);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.findAllCoursesByDepartmentAndPeriod(java.lang.Long departmentId, java.lang.Long periodId, java.lang.Boolean onlyEnabled)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findAllCoursesByDepartmentAndPeriod(java.lang.Long, java.lang.Long, java.lang.Boolean)}
      */
    protected abstract java.util.List handleFindAllCoursesByDepartmentAndPeriod(java.lang.Long departmentId, java.lang.Long periodId, java.lang.Boolean onlyEnabled)
        throws java.lang.Exception;

    /**
     * @see org.openuss.lecture.CourseService#removeAssistant(org.openuss.lecture.CourseMemberInfo)
     */
    public void removeAssistant(org.openuss.lecture.CourseMemberInfo assistant)
    {
        try
        {
            this.handleRemoveAssistant(assistant);
        }
        catch (Throwable th)
        {
            throw new org.openuss.lecture.CourseServiceException(
                "Error performing 'org.openuss.lecture.CourseService.removeAssistant(org.openuss.lecture.CourseMemberInfo assistant)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeAssistant(org.openuss.lecture.CourseMemberInfo)}
      */
    protected abstract void handleRemoveAssistant(org.openuss.lecture.CourseMemberInfo assistant)
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