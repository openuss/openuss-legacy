package org.openuss.braincontest;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.braincontest.BrainContestService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.braincontest.BrainContestService
 */
public abstract class BrainContestServiceBase
    implements org.openuss.braincontest.BrainContestService
{

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

    private org.openuss.braincontest.BrainContestDao brainContestDao;

    /**
     * Sets the reference to <code>brainContest</code>'s DAO.
     */
    public void setBrainContestDao(org.openuss.braincontest.BrainContestDao brainContestDao)
    {
        this.brainContestDao = brainContestDao;
    }

    /**
     * Gets the reference to <code>brainContest</code>'s DAO.
     */
    protected org.openuss.braincontest.BrainContestDao getBrainContestDao()
    {
        return this.brainContestDao;
    }

    private org.openuss.braincontest.AnswerDao answerDao;

    /**
     * Sets the reference to <code>answer</code>'s DAO.
     */
    public void setAnswerDao(org.openuss.braincontest.AnswerDao answerDao)
    {
        this.answerDao = answerDao;
    }

    /**
     * Gets the reference to <code>answer</code>'s DAO.
     */
    protected org.openuss.braincontest.AnswerDao getAnswerDao()
    {
        return this.answerDao;
    }

    /**
     * @see org.openuss.braincontest.BrainContestService#getContests(org.openuss.foundation.DomainObject)
     */
    public java.util.List getContests(org.openuss.foundation.DomainObject domainObject)
    {
        try
        {
            return this.handleGetContests(domainObject);
        }
        catch (Throwable th)
        {
            throw new org.openuss.braincontest.BrainContestServiceException(
                "Error performing 'org.openuss.braincontest.BrainContestService.getContests(org.openuss.foundation.DomainObject domainObject)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getContests(org.openuss.foundation.DomainObject)}
      */
    protected abstract java.util.List handleGetContests(org.openuss.foundation.DomainObject domainObject)
        throws java.lang.Exception;

    /**
     * @see org.openuss.braincontest.BrainContestService#getContest(org.openuss.braincontest.BrainContestInfo)
     */
    public org.openuss.braincontest.BrainContestInfo getContest(org.openuss.braincontest.BrainContestInfo contest)
    {
        try
        {
            return this.handleGetContest(contest);
        }
        catch (Throwable th)
        {
            throw new org.openuss.braincontest.BrainContestServiceException(
                "Error performing 'org.openuss.braincontest.BrainContestService.getContest(org.openuss.braincontest.BrainContestInfo contest)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getContest(org.openuss.braincontest.BrainContestInfo)}
      */
    protected abstract org.openuss.braincontest.BrainContestInfo handleGetContest(org.openuss.braincontest.BrainContestInfo contest)
        throws java.lang.Exception;

    /**
     * @see org.openuss.braincontest.BrainContestService#createContest(org.openuss.braincontest.BrainContestInfo)
     */
    public void createContest(org.openuss.braincontest.BrainContestInfo contest)
        throws org.openuss.braincontest.BrainContestApplicationException
    {
        try
        {
            this.handleCreateContest(contest);
        }
        catch (org.openuss.braincontest.BrainContestApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.braincontest.BrainContestServiceException(
                "Error performing 'org.openuss.braincontest.BrainContestService.createContest(org.openuss.braincontest.BrainContestInfo contest)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createContest(org.openuss.braincontest.BrainContestInfo)}
      */
    protected abstract void handleCreateContest(org.openuss.braincontest.BrainContestInfo contest)
        throws java.lang.Exception;

    /**
     * @see org.openuss.braincontest.BrainContestService#saveContest(org.openuss.braincontest.BrainContestInfo)
     */
    public void saveContest(org.openuss.braincontest.BrainContestInfo contest)
        throws org.openuss.braincontest.BrainContestApplicationException
    {
        try
        {
            this.handleSaveContest(contest);
        }
        catch (org.openuss.braincontest.BrainContestApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.braincontest.BrainContestServiceException(
                "Error performing 'org.openuss.braincontest.BrainContestService.saveContest(org.openuss.braincontest.BrainContestInfo contest)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #saveContest(org.openuss.braincontest.BrainContestInfo)}
      */
    protected abstract void handleSaveContest(org.openuss.braincontest.BrainContestInfo contest)
        throws java.lang.Exception;

    /**
     * @see org.openuss.braincontest.BrainContestService#getAttachments(org.openuss.braincontest.BrainContestInfo)
     */
    public java.util.List getAttachments(org.openuss.braincontest.BrainContestInfo contest)
    {
        try
        {
            return this.handleGetAttachments(contest);
        }
        catch (Throwable th)
        {
            throw new org.openuss.braincontest.BrainContestServiceException(
                "Error performing 'org.openuss.braincontest.BrainContestService.getAttachments(org.openuss.braincontest.BrainContestInfo contest)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getAttachments(org.openuss.braincontest.BrainContestInfo)}
      */
    protected abstract java.util.List handleGetAttachments(org.openuss.braincontest.BrainContestInfo contest)
        throws java.lang.Exception;

    /**
     * @see org.openuss.braincontest.BrainContestService#addAttachment(org.openuss.braincontest.BrainContestInfo, org.openuss.documents.FileInfo)
     */
    public void addAttachment(org.openuss.braincontest.BrainContestInfo contest, org.openuss.documents.FileInfo fileInfo)
        throws org.openuss.braincontest.BrainContestApplicationException
    {
        try
        {
            this.handleAddAttachment(contest, fileInfo);
        }
        catch (org.openuss.braincontest.BrainContestApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.braincontest.BrainContestServiceException(
                "Error performing 'org.openuss.braincontest.BrainContestService.addAttachment(org.openuss.braincontest.BrainContestInfo contest, org.openuss.documents.FileInfo fileInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addAttachment(org.openuss.braincontest.BrainContestInfo, org.openuss.documents.FileInfo)}
      */
    protected abstract void handleAddAttachment(org.openuss.braincontest.BrainContestInfo contest, org.openuss.documents.FileInfo fileInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.braincontest.BrainContestService#answer(java.lang.String, org.openuss.security.UserInfo, org.openuss.braincontest.BrainContestInfo, boolean)
     */
    public boolean answer(java.lang.String answer, org.openuss.security.UserInfo user, org.openuss.braincontest.BrainContestInfo contest, boolean topList)
        throws org.openuss.braincontest.BrainContestApplicationException
    {
        try
        {
            return this.handleAnswer(answer, user, contest, topList);
        }
        catch (org.openuss.braincontest.BrainContestApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.braincontest.BrainContestServiceException(
                "Error performing 'org.openuss.braincontest.BrainContestService.answer(java.lang.String answer, org.openuss.security.UserInfo user, org.openuss.braincontest.BrainContestInfo contest, boolean topList)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #answer(java.lang.String, org.openuss.security.UserInfo, org.openuss.braincontest.BrainContestInfo, boolean)}
      */
    protected abstract boolean handleAnswer(java.lang.String answer, org.openuss.security.UserInfo user, org.openuss.braincontest.BrainContestInfo contest, boolean topList)
        throws java.lang.Exception;

    /**
     * @see org.openuss.braincontest.BrainContestService#removeAttachment(org.openuss.braincontest.BrainContestInfo, org.openuss.documents.FileInfo)
     */
    public void removeAttachment(org.openuss.braincontest.BrainContestInfo contest, org.openuss.documents.FileInfo fileInfo)
        throws org.openuss.braincontest.BrainContestApplicationException
    {
        try
        {
            this.handleRemoveAttachment(contest, fileInfo);
        }
        catch (org.openuss.braincontest.BrainContestApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.braincontest.BrainContestServiceException(
                "Error performing 'org.openuss.braincontest.BrainContestService.removeAttachment(org.openuss.braincontest.BrainContestInfo contest, org.openuss.documents.FileInfo fileInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeAttachment(org.openuss.braincontest.BrainContestInfo, org.openuss.documents.FileInfo)}
      */
    protected abstract void handleRemoveAttachment(org.openuss.braincontest.BrainContestInfo contest, org.openuss.documents.FileInfo fileInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.braincontest.BrainContestService#getAnswers(org.openuss.braincontest.BrainContestInfo)
     */
    public java.util.List getAnswers(org.openuss.braincontest.BrainContestInfo contest)
    {
        try
        {
            return this.handleGetAnswers(contest);
        }
        catch (Throwable th)
        {
            throw new org.openuss.braincontest.BrainContestServiceException(
                "Error performing 'org.openuss.braincontest.BrainContestService.getAnswers(org.openuss.braincontest.BrainContestInfo contest)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getAnswers(org.openuss.braincontest.BrainContestInfo)}
      */
    protected abstract java.util.List handleGetAnswers(org.openuss.braincontest.BrainContestInfo contest)
        throws java.lang.Exception;

    /**
     * @see org.openuss.braincontest.BrainContestService#removeContest(org.openuss.braincontest.BrainContestInfo)
     */
    public void removeContest(org.openuss.braincontest.BrainContestInfo contest)
        throws org.openuss.braincontest.BrainContestApplicationException
    {
        try
        {
            this.handleRemoveContest(contest);
        }
        catch (org.openuss.braincontest.BrainContestApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.braincontest.BrainContestServiceException(
                "Error performing 'org.openuss.braincontest.BrainContestService.removeContest(org.openuss.braincontest.BrainContestInfo contest)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeContest(org.openuss.braincontest.BrainContestInfo)}
      */
    protected abstract void handleRemoveContest(org.openuss.braincontest.BrainContestInfo contest)
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