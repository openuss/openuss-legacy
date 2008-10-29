package org.openuss.paperSubmission;

/**
 * Spring Service base class for <code>org.openuss.paperSubmission.PaperSubmissionService</code>,
 * provides access to all services and entities referenced by this service.
 *
 * @see org.openuss.paperSubmission.PaperSubmissionService
 */
public abstract class PaperSubmissionServiceBase
    implements org.openuss.paperSubmission.PaperSubmissionService
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

    private org.openuss.documents.DocumentService documentService;

    /**
     * Sets the reference to <code>documentService</code>.
     */
    public void setDocumentService(org.openuss.documents.DocumentService documentService)
    {
        this.documentService = documentService;
    }

    /**
     * Gets the reference to <code>documentService</code>.
     */
    protected org.openuss.documents.DocumentService getDocumentService()
    {
        return this.documentService;
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

    private org.openuss.paperSubmission.ExamDao examDao;

    /**
     * Sets the reference to <code>exam</code>'s DAO.
     */
    public void setExamDao(org.openuss.paperSubmission.ExamDao examDao)
    {
        this.examDao = examDao;
    }

    /**
     * Gets the reference to <code>exam</code>'s DAO.
     */
    protected org.openuss.paperSubmission.ExamDao getExamDao()
    {
        return this.examDao;
    }

    private org.openuss.paperSubmission.PaperSubmissionDao paperSubmissionDao;

    /**
     * Sets the reference to <code>paperSubmission</code>'s DAO.
     */
    public void setPaperSubmissionDao(org.openuss.paperSubmission.PaperSubmissionDao paperSubmissionDao)
    {
        this.paperSubmissionDao = paperSubmissionDao;
    }

    /**
     * Gets the reference to <code>paperSubmission</code>'s DAO.
     */
    protected org.openuss.paperSubmission.PaperSubmissionDao getPaperSubmissionDao()
    {
        return this.paperSubmissionDao;
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

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#createExam(org.openuss.paperSubmission.ExamInfo)
     */
    public void createExam(org.openuss.paperSubmission.ExamInfo examInfo)
    {
        try
        {
            this.handleCreateExam(examInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.paperSubmission.PaperSubmissionServiceException(
                "Error performing 'org.openuss.paperSubmission.PaperSubmissionService.createExam(org.openuss.paperSubmission.ExamInfo examInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createExam(org.openuss.paperSubmission.ExamInfo)}
      */
    protected abstract void handleCreateExam(org.openuss.paperSubmission.ExamInfo examInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#updateExam(org.openuss.paperSubmission.ExamInfo)
     */
    public void updateExam(org.openuss.paperSubmission.ExamInfo examInfo)
    {
        try
        {
            this.handleUpdateExam(examInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.paperSubmission.PaperSubmissionServiceException(
                "Error performing 'org.openuss.paperSubmission.PaperSubmissionService.updateExam(org.openuss.paperSubmission.ExamInfo examInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #updateExam(org.openuss.paperSubmission.ExamInfo)}
      */
    protected abstract void handleUpdateExam(org.openuss.paperSubmission.ExamInfo examInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#removeExam(java.lang.Long)
     */
    public void removeExam(java.lang.Long examId)
    {
        try
        {
            this.handleRemoveExam(examId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.paperSubmission.PaperSubmissionServiceException(
                "Error performing 'org.openuss.paperSubmission.PaperSubmissionService.removeExam(java.lang.Long examId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeExam(java.lang.Long)}
      */
    protected abstract void handleRemoveExam(java.lang.Long examId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#createPaperSubmission(org.openuss.paperSubmission.PaperSubmissionInfo)
     */
    public void createPaperSubmission(org.openuss.paperSubmission.PaperSubmissionInfo paperSubmissionInfo)
    {
        try
        {
            this.handleCreatePaperSubmission(paperSubmissionInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.paperSubmission.PaperSubmissionServiceException(
                "Error performing 'org.openuss.paperSubmission.PaperSubmissionService.createPaperSubmission(org.openuss.paperSubmission.PaperSubmissionInfo paperSubmissionInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createPaperSubmission(org.openuss.paperSubmission.PaperSubmissionInfo)}
      */
    protected abstract void handleCreatePaperSubmission(org.openuss.paperSubmission.PaperSubmissionInfo paperSubmissionInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#updatePaperSubmission(org.openuss.paperSubmission.PaperSubmissionInfo, boolean)
     */
    public org.openuss.paperSubmission.PaperSubmissionInfo updatePaperSubmission(org.openuss.paperSubmission.PaperSubmissionInfo paperSubmissionInfo, boolean changeDeliverDate)
    {
        try
        {
            return this.handleUpdatePaperSubmission(paperSubmissionInfo, changeDeliverDate);
        }
        catch (Throwable th)
        {
            throw new org.openuss.paperSubmission.PaperSubmissionServiceException(
                "Error performing 'org.openuss.paperSubmission.PaperSubmissionService.updatePaperSubmission(org.openuss.paperSubmission.PaperSubmissionInfo paperSubmissionInfo, boolean changeDeliverDate)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #updatePaperSubmission(org.openuss.paperSubmission.PaperSubmissionInfo, boolean)}
      */
    protected abstract org.openuss.paperSubmission.PaperSubmissionInfo handleUpdatePaperSubmission(org.openuss.paperSubmission.PaperSubmissionInfo paperSubmissionInfo, boolean changeDeliverDate)
        throws java.lang.Exception;

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#findExamsByDomainId(java.lang.Long)
     */
    public java.util.List findExamsByDomainId(java.lang.Long domainId)
    {
        try
        {
            return this.handleFindExamsByDomainId(domainId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.paperSubmission.PaperSubmissionServiceException(
                "Error performing 'org.openuss.paperSubmission.PaperSubmissionService.findExamsByDomainId(java.lang.Long domainId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findExamsByDomainId(java.lang.Long)}
      */
    protected abstract java.util.List handleFindExamsByDomainId(java.lang.Long domainId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#findActiveExamsByDomainId(java.lang.Long)
     */
    public java.util.List findActiveExamsByDomainId(java.lang.Long domainId)
    {
        try
        {
            return this.handleFindActiveExamsByDomainId(domainId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.paperSubmission.PaperSubmissionServiceException(
                "Error performing 'org.openuss.paperSubmission.PaperSubmissionService.findActiveExamsByDomainId(java.lang.Long domainId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findActiveExamsByDomainId(java.lang.Long)}
      */
    protected abstract java.util.List handleFindActiveExamsByDomainId(java.lang.Long domainId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#findInactiveExamsByDomainId(java.lang.Long)
     */
    public java.util.List findInactiveExamsByDomainId(java.lang.Long domainId)
    {
        try
        {
            return this.handleFindInactiveExamsByDomainId(domainId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.paperSubmission.PaperSubmissionServiceException(
                "Error performing 'org.openuss.paperSubmission.PaperSubmissionService.findInactiveExamsByDomainId(java.lang.Long domainId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findInactiveExamsByDomainId(java.lang.Long)}
      */
    protected abstract java.util.List handleFindInactiveExamsByDomainId(java.lang.Long domainId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#findPaperSubmissionsByExam(java.lang.Long)
     */
    public java.util.List findPaperSubmissionsByExam(java.lang.Long examId)
    {
        try
        {
            return this.handleFindPaperSubmissionsByExam(examId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.paperSubmission.PaperSubmissionServiceException(
                "Error performing 'org.openuss.paperSubmission.PaperSubmissionService.findPaperSubmissionsByExam(java.lang.Long examId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findPaperSubmissionsByExam(java.lang.Long)}
      */
    protected abstract java.util.List handleFindPaperSubmissionsByExam(java.lang.Long examId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#findInTimePaperSubmissionsByExam(java.lang.Long)
     */
    public java.util.List findInTimePaperSubmissionsByExam(java.lang.Long examId)
    {
        try
        {
            return this.handleFindInTimePaperSubmissionsByExam(examId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.paperSubmission.PaperSubmissionServiceException(
                "Error performing 'org.openuss.paperSubmission.PaperSubmissionService.findInTimePaperSubmissionsByExam(java.lang.Long examId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findInTimePaperSubmissionsByExam(java.lang.Long)}
      */
    protected abstract java.util.List handleFindInTimePaperSubmissionsByExam(java.lang.Long examId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#getExam(java.lang.Long)
     */
    public org.openuss.paperSubmission.ExamInfo getExam(java.lang.Long examId)
    {
        try
        {
            return this.handleGetExam(examId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.paperSubmission.PaperSubmissionServiceException(
                "Error performing 'org.openuss.paperSubmission.PaperSubmissionService.getExam(java.lang.Long examId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getExam(java.lang.Long)}
      */
    protected abstract org.openuss.paperSubmission.ExamInfo handleGetExam(java.lang.Long examId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#getPaperSubmission(java.lang.Long)
     */
    public org.openuss.paperSubmission.PaperSubmissionInfo getPaperSubmission(java.lang.Long paperSubmissionId)
    {
        try
        {
            return this.handleGetPaperSubmission(paperSubmissionId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.paperSubmission.PaperSubmissionServiceException(
                "Error performing 'org.openuss.paperSubmission.PaperSubmissionService.getPaperSubmission(java.lang.Long paperSubmissionId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getPaperSubmission(java.lang.Long)}
      */
    protected abstract org.openuss.paperSubmission.PaperSubmissionInfo handleGetPaperSubmission(java.lang.Long paperSubmissionId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#findPaperSubmissionsByExamAndUser(java.lang.Long, java.lang.Long)
     */
    public java.util.List findPaperSubmissionsByExamAndUser(java.lang.Long examId, java.lang.Long userId)
    {
        try
        {
            return this.handleFindPaperSubmissionsByExamAndUser(examId, userId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.paperSubmission.PaperSubmissionServiceException(
                "Error performing 'org.openuss.paperSubmission.PaperSubmissionService.findPaperSubmissionsByExamAndUser(java.lang.Long examId, java.lang.Long userId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #findPaperSubmissionsByExamAndUser(java.lang.Long, java.lang.Long)}
      */
    protected abstract java.util.List handleFindPaperSubmissionsByExamAndUser(java.lang.Long examId, java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#getPaperSubmissionFiles(java.util.Collection)
     */
    public java.util.List getPaperSubmissionFiles(java.util.Collection submissions)
    {
        try
        {
            return this.handleGetPaperSubmissionFiles(submissions);
        }
        catch (Throwable th)
        {
            throw new org.openuss.paperSubmission.PaperSubmissionServiceException(
                "Error performing 'org.openuss.paperSubmission.PaperSubmissionService.getPaperSubmissionFiles(java.util.Collection submissions)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getPaperSubmissionFiles(java.util.Collection)}
      */
    protected abstract java.util.List handleGetPaperSubmissionFiles(java.util.Collection submissions)
        throws java.lang.Exception;

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#getMembersAsPaperSubmissionsByExam(java.lang.Long)
     */
    public java.util.List getMembersAsPaperSubmissionsByExam(java.lang.Long examId)
    {
        try
        {
            return this.handleGetMembersAsPaperSubmissionsByExam(examId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.paperSubmission.PaperSubmissionServiceException(
                "Error performing 'org.openuss.paperSubmission.PaperSubmissionService.getMembersAsPaperSubmissionsByExam(java.lang.Long examId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getMembersAsPaperSubmissionsByExam(java.lang.Long)}
      */
    protected abstract java.util.List handleGetMembersAsPaperSubmissionsByExam(java.lang.Long examId)
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