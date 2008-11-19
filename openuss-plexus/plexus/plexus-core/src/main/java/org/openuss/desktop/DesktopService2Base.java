package org.openuss.desktop;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.desktop.DesktopService2</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.desktop.DesktopService2
 */
public abstract class DesktopService2Base
    implements org.openuss.desktop.DesktopService2
{

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

    private org.openuss.desktop.DesktopDao desktopDao;

    /**
     * Sets the reference to <code>desktop</code>'s DAO.
     */
    public void setDesktopDao(org.openuss.desktop.DesktopDao desktopDao)
    {
        this.desktopDao = desktopDao;
    }

    /**
     * Gets the reference to <code>desktop</code>'s DAO.
     */
    protected org.openuss.desktop.DesktopDao getDesktopDao()
    {
        return this.desktopDao;
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
     * @see org.openuss.desktop.DesktopService2#createDesktop(java.lang.Long)
     */
    public java.lang.Long createDesktop(java.lang.Long userId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            return this.handleCreateDesktop(userId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.createDesktop(java.lang.Long userId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createDesktop(java.lang.Long)}
      */
    protected abstract java.lang.Long handleCreateDesktop(java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#updateDesktop(org.openuss.desktop.DesktopInfo)
     */
    public void updateDesktop(org.openuss.desktop.DesktopInfo desktop)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            this.handleUpdateDesktop(desktop);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.updateDesktop(org.openuss.desktop.DesktopInfo desktop)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #updateDesktop(org.openuss.desktop.DesktopInfo)}
      */
    protected abstract void handleUpdateDesktop(org.openuss.desktop.DesktopInfo desktop)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#linkUniversity(java.lang.Long, java.lang.Long)
     */
    public void linkUniversity(java.lang.Long desktopId, java.lang.Long universityId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            this.handleLinkUniversity(desktopId, universityId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.linkUniversity(java.lang.Long desktopId, java.lang.Long universityId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #linkUniversity(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleLinkUniversity(java.lang.Long desktopId, java.lang.Long universityId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#linkDepartment(java.lang.Long, java.lang.Long)
     */
    public void linkDepartment(java.lang.Long desktopId, java.lang.Long departmentId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            this.handleLinkDepartment(desktopId, departmentId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.linkDepartment(java.lang.Long desktopId, java.lang.Long departmentId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #linkDepartment(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleLinkDepartment(java.lang.Long desktopId, java.lang.Long departmentId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#linkInstitute(java.lang.Long, java.lang.Long)
     */
    public void linkInstitute(java.lang.Long desktopId, java.lang.Long instituteId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            this.handleLinkInstitute(desktopId, instituteId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.linkInstitute(java.lang.Long desktopId, java.lang.Long instituteId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #linkInstitute(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleLinkInstitute(java.lang.Long desktopId, java.lang.Long instituteId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#linkCourseType(java.lang.Long, java.lang.Long)
     */
    public void linkCourseType(java.lang.Long desktopId, java.lang.Long courseTypeId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            this.handleLinkCourseType(desktopId, courseTypeId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.linkCourseType(java.lang.Long desktopId, java.lang.Long courseTypeId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #linkCourseType(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleLinkCourseType(java.lang.Long desktopId, java.lang.Long courseTypeId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#linkCourse(java.lang.Long, java.lang.Long)
     */
    public void linkCourse(java.lang.Long desktopId, java.lang.Long courseId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            this.handleLinkCourse(desktopId, courseId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.linkCourse(java.lang.Long desktopId, java.lang.Long courseId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #linkCourse(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleLinkCourse(java.lang.Long desktopId, java.lang.Long courseId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#unlinkUniversity(java.lang.Long, java.lang.Long)
     */
    public void unlinkUniversity(java.lang.Long desktopId, java.lang.Long universityId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            this.handleUnlinkUniversity(desktopId, universityId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.unlinkUniversity(java.lang.Long desktopId, java.lang.Long universityId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #unlinkUniversity(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleUnlinkUniversity(java.lang.Long desktopId, java.lang.Long universityId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#unlinkDepartment(java.lang.Long, java.lang.Long)
     */
    public void unlinkDepartment(java.lang.Long desktopId, java.lang.Long departmentId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            this.handleUnlinkDepartment(desktopId, departmentId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.unlinkDepartment(java.lang.Long desktopId, java.lang.Long departmentId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #unlinkDepartment(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleUnlinkDepartment(java.lang.Long desktopId, java.lang.Long departmentId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#unlinkInstitute(java.lang.Long, java.lang.Long)
     */
    public void unlinkInstitute(java.lang.Long desktopId, java.lang.Long instituteId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            this.handleUnlinkInstitute(desktopId, instituteId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.unlinkInstitute(java.lang.Long desktopId, java.lang.Long instituteId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #unlinkInstitute(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleUnlinkInstitute(java.lang.Long desktopId, java.lang.Long instituteId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#unlinkCourseType(java.lang.Long, java.lang.Long)
     */
    public void unlinkCourseType(java.lang.Long desktopId, java.lang.Long courseTypeId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            this.handleUnlinkCourseType(desktopId, courseTypeId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.unlinkCourseType(java.lang.Long desktopId, java.lang.Long courseTypeId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #unlinkCourseType(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleUnlinkCourseType(java.lang.Long desktopId, java.lang.Long courseTypeId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#unlinkCourse(java.lang.Long, java.lang.Long)
     */
    public void unlinkCourse(java.lang.Long desktopId, java.lang.Long courseId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            this.handleUnlinkCourse(desktopId, courseId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.unlinkCourse(java.lang.Long desktopId, java.lang.Long courseId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #unlinkCourse(java.lang.Long, java.lang.Long)}
      */
    protected abstract void handleUnlinkCourse(java.lang.Long desktopId, java.lang.Long courseId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#unlinkAllFromUniversity(java.lang.Long)
     */
    public void unlinkAllFromUniversity(java.lang.Long universityId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            this.handleUnlinkAllFromUniversity(universityId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.unlinkAllFromUniversity(java.lang.Long universityId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #unlinkAllFromUniversity(java.lang.Long)}
      */
    protected abstract void handleUnlinkAllFromUniversity(java.lang.Long universityId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#unlinkAllFromDepartment(java.lang.Long)
     */
    public void unlinkAllFromDepartment(java.lang.Long departmentId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            this.handleUnlinkAllFromDepartment(departmentId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.unlinkAllFromDepartment(java.lang.Long departmentId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #unlinkAllFromDepartment(java.lang.Long)}
      */
    protected abstract void handleUnlinkAllFromDepartment(java.lang.Long departmentId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#unlinkAllFromInstitute(java.lang.Long)
     */
    public void unlinkAllFromInstitute(java.lang.Long instituteId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            this.handleUnlinkAllFromInstitute(instituteId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.unlinkAllFromInstitute(java.lang.Long instituteId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #unlinkAllFromInstitute(java.lang.Long)}
      */
    protected abstract void handleUnlinkAllFromInstitute(java.lang.Long instituteId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#unlinkAllFromCourseType(java.lang.Long)
     */
    public void unlinkAllFromCourseType(java.lang.Long courseTypeId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            this.handleUnlinkAllFromCourseType(courseTypeId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.unlinkAllFromCourseType(java.lang.Long courseTypeId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #unlinkAllFromCourseType(java.lang.Long)}
      */
    protected abstract void handleUnlinkAllFromCourseType(java.lang.Long courseTypeId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#unlinkAllFromCourse(java.lang.Long)
     */
    public void unlinkAllFromCourse(java.lang.Long courseId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            this.handleUnlinkAllFromCourse(courseId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.unlinkAllFromCourse(java.lang.Long courseId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #unlinkAllFromCourse(java.lang.Long)}
      */
    protected abstract void handleUnlinkAllFromCourse(java.lang.Long courseId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#findLinkedDepartmentsByUserAndUniversity(java.lang.Long, java.lang.Long)
     */
    public java.util.List findLinkedDepartmentsByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            return this.handleFindLinkedDepartmentsByUserAndUniversity(userId, universityId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.findLinkedDepartmentsByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findLinkedDepartmentsByUserAndUniversity(java.lang.Long, java.lang.Long)}
      */
    protected abstract java.util.List handleFindLinkedDepartmentsByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#findAdditionalDepartmentsByUserAndUniversity(java.lang.Long, java.lang.Long)
     */
    public java.util.List findAdditionalDepartmentsByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            return this.handleFindAdditionalDepartmentsByUserAndUniversity(userId, universityId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.findAdditionalDepartmentsByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findAdditionalDepartmentsByUserAndUniversity(java.lang.Long, java.lang.Long)}
      */
    protected abstract java.util.List handleFindAdditionalDepartmentsByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#findLinkedInstitutesByUserAndUniversity(java.lang.Long, java.lang.Long)
     */
    public java.util.List findLinkedInstitutesByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            return this.handleFindLinkedInstitutesByUserAndUniversity(userId, universityId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.findLinkedInstitutesByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findLinkedInstitutesByUserAndUniversity(java.lang.Long, java.lang.Long)}
      */
    protected abstract java.util.List handleFindLinkedInstitutesByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#findAdditionalInstitutesByUserAndUniversity(java.lang.Long, java.lang.Long)
     */
    public java.util.List findAdditionalInstitutesByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            return this.handleFindAdditionalInstitutesByUserAndUniversity(userId, universityId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.findAdditionalInstitutesByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findAdditionalInstitutesByUserAndUniversity(java.lang.Long, java.lang.Long)}
      */
    protected abstract java.util.List handleFindAdditionalInstitutesByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#findLinkedCoursesByUserAndUniversity(java.lang.Long, java.lang.Long)
     */
    public java.util.List findLinkedCoursesByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            return this.handleFindLinkedCoursesByUserAndUniversity(userId, universityId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.findLinkedCoursesByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findLinkedCoursesByUserAndUniversity(java.lang.Long, java.lang.Long)}
      */
    protected abstract java.util.List handleFindLinkedCoursesByUserAndUniversity(java.lang.Long userId, java.lang.Long universityId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#findDesktop(java.lang.Long)
     */
    public org.openuss.desktop.DesktopInfo findDesktop(java.lang.Long desktopId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            return this.handleFindDesktop(desktopId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.findDesktop(java.lang.Long desktopId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findDesktop(java.lang.Long)}
      */
    protected abstract org.openuss.desktop.DesktopInfo handleFindDesktop(java.lang.Long desktopId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#findDesktopByUser(java.lang.Long)
     */
    public org.openuss.desktop.DesktopInfo findDesktopByUser(java.lang.Long userId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            return this.handleFindDesktopByUser(userId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.findDesktopByUser(java.lang.Long userId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findDesktopByUser(java.lang.Long)}
      */
    protected abstract org.openuss.desktop.DesktopInfo handleFindDesktopByUser(java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopService2#getMyUniInfo(java.lang.Long)
     */
    public java.util.Map getMyUniInfo(java.lang.Long userId)
        throws org.openuss.desktop.DesktopException
    {
        try
        {
            return this.handleGetMyUniInfo(userId);
        }
        catch (org.openuss.desktop.DesktopException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.desktop.DesktopService2Exception(
                "Error performing 'org.openuss.desktop.DesktopService2.getMyUniInfo(java.lang.Long userId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getMyUniInfo(java.lang.Long)}
      */
    protected abstract java.util.Map handleGetMyUniInfo(java.lang.Long userId)
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