package org.openuss.course.newsletter;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.course.newsletter.CourseNewsletterService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.course.newsletter.CourseNewsletterService
 */
public abstract class CourseNewsletterServiceBase
    implements org.openuss.course.newsletter.CourseNewsletterService
{

    private org.openuss.newsletter.NewsletterService newsletterService;

    /**
     * Sets the reference to <code>newsletterService</code>.
     */
    public void setNewsletterService(org.openuss.newsletter.NewsletterService newsletterService)
    {
        this.newsletterService = newsletterService;
    }

    /**
     * Gets the reference to <code>newsletterService</code>.
     */
    protected org.openuss.newsletter.NewsletterService getNewsletterService()
    {
        return this.newsletterService;
    }

    /**
     * @see org.openuss.course.newsletter.CourseNewsletterService#sendPreview(org.openuss.lecture.CourseInfo, org.openuss.newsletter.MailDetail)
     */
    public void sendPreview(org.openuss.lecture.CourseInfo course, org.openuss.newsletter.MailDetail mail)
    {
        try
        {
            this.handleSendPreview(course, mail);
        }
        catch (Throwable th)
        {
            throw new org.openuss.course.newsletter.CourseNewsletterServiceException(
                "Error performing 'org.openuss.course.newsletter.CourseNewsletterService.sendPreview(org.openuss.lecture.CourseInfo course, org.openuss.newsletter.MailDetail mail)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #sendPreview(org.openuss.lecture.CourseInfo, org.openuss.newsletter.MailDetail)}
      */
    protected abstract void handleSendPreview(org.openuss.lecture.CourseInfo course, org.openuss.newsletter.MailDetail mail)
        throws java.lang.Exception;

    /**
     * @see org.openuss.course.newsletter.CourseNewsletterService#getMails(org.openuss.lecture.CourseInfo)
     */
    public java.util.List getMails(org.openuss.lecture.CourseInfo course)
    {
        try
        {
            return this.handleGetMails(course);
        }
        catch (Throwable th)
        {
            throw new org.openuss.course.newsletter.CourseNewsletterServiceException(
                "Error performing 'org.openuss.course.newsletter.CourseNewsletterService.getMails(org.openuss.lecture.CourseInfo course)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getMails(org.openuss.lecture.CourseInfo)}
      */
    protected abstract java.util.List handleGetMails(org.openuss.lecture.CourseInfo course)
        throws java.lang.Exception;

    /**
     * @see org.openuss.course.newsletter.CourseNewsletterService#getMail(org.openuss.newsletter.MailInfo)
     */
    public org.openuss.newsletter.MailDetail getMail(org.openuss.newsletter.MailInfo mail)
    {
        try
        {
            return this.handleGetMail(mail);
        }
        catch (Throwable th)
        {
            throw new org.openuss.course.newsletter.CourseNewsletterServiceException(
                "Error performing 'org.openuss.course.newsletter.CourseNewsletterService.getMail(org.openuss.newsletter.MailInfo mail)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getMail(org.openuss.newsletter.MailInfo)}
      */
    protected abstract org.openuss.newsletter.MailDetail handleGetMail(org.openuss.newsletter.MailInfo mail)
        throws java.lang.Exception;

    /**
     * @see org.openuss.course.newsletter.CourseNewsletterService#sendMail(org.openuss.lecture.CourseInfo, org.openuss.newsletter.MailDetail)
     */
    public void sendMail(org.openuss.lecture.CourseInfo course, org.openuss.newsletter.MailDetail mail)
    {
        try
        {
            this.handleSendMail(course, mail);
        }
        catch (Throwable th)
        {
            throw new org.openuss.course.newsletter.CourseNewsletterServiceException(
                "Error performing 'org.openuss.course.newsletter.CourseNewsletterService.sendMail(org.openuss.lecture.CourseInfo course, org.openuss.newsletter.MailDetail mail)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #sendMail(org.openuss.lecture.CourseInfo, org.openuss.newsletter.MailDetail)}
      */
    protected abstract void handleSendMail(org.openuss.lecture.CourseInfo course, org.openuss.newsletter.MailDetail mail)
        throws java.lang.Exception;

    /**
     * @see org.openuss.course.newsletter.CourseNewsletterService#subscribe(org.openuss.lecture.CourseInfo, org.openuss.security.UserInfo)
     */
    public void subscribe(org.openuss.lecture.CourseInfo course, org.openuss.security.UserInfo user)
    {
        try
        {
            this.handleSubscribe(course, user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.course.newsletter.CourseNewsletterServiceException(
                "Error performing 'org.openuss.course.newsletter.CourseNewsletterService.subscribe(org.openuss.lecture.CourseInfo course, org.openuss.security.UserInfo user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #subscribe(org.openuss.lecture.CourseInfo, org.openuss.security.UserInfo)}
      */
    protected abstract void handleSubscribe(org.openuss.lecture.CourseInfo course, org.openuss.security.UserInfo user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.course.newsletter.CourseNewsletterService#unsubscribe(org.openuss.lecture.CourseInfo, org.openuss.security.UserInfo)
     */
    public void unsubscribe(org.openuss.lecture.CourseInfo course, org.openuss.security.UserInfo user)
    {
        try
        {
            this.handleUnsubscribe(course, user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.course.newsletter.CourseNewsletterServiceException(
                "Error performing 'org.openuss.course.newsletter.CourseNewsletterService.unsubscribe(org.openuss.lecture.CourseInfo course, org.openuss.security.UserInfo user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #unsubscribe(org.openuss.lecture.CourseInfo, org.openuss.security.UserInfo)}
      */
    protected abstract void handleUnsubscribe(org.openuss.lecture.CourseInfo course, org.openuss.security.UserInfo user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.course.newsletter.CourseNewsletterService#unsubscribe(org.openuss.newsletter.SubscriberInfo)
     */
    public void unsubscribe(org.openuss.newsletter.SubscriberInfo subscriber)
    {
        try
        {
            this.handleUnsubscribe(subscriber);
        }
        catch (Throwable th)
        {
            throw new org.openuss.course.newsletter.CourseNewsletterServiceException(
                "Error performing 'org.openuss.course.newsletter.CourseNewsletterService.unsubscribe(org.openuss.newsletter.SubscriberInfo subscriber)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #unsubscribe(org.openuss.newsletter.SubscriberInfo)}
      */
    protected abstract void handleUnsubscribe(org.openuss.newsletter.SubscriberInfo subscriber)
        throws java.lang.Exception;

    /**
     * @see org.openuss.course.newsletter.CourseNewsletterService#setBlockingState(org.openuss.newsletter.SubscriberInfo)
     */
    public void setBlockingState(org.openuss.newsletter.SubscriberInfo subscriber)
    {
        try
        {
            this.handleSetBlockingState(subscriber);
        }
        catch (Throwable th)
        {
            throw new org.openuss.course.newsletter.CourseNewsletterServiceException(
                "Error performing 'org.openuss.course.newsletter.CourseNewsletterService.setBlockingState(org.openuss.newsletter.SubscriberInfo subscriber)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #setBlockingState(org.openuss.newsletter.SubscriberInfo)}
      */
    protected abstract void handleSetBlockingState(org.openuss.newsletter.SubscriberInfo subscriber)
        throws java.lang.Exception;

    /**
     * @see org.openuss.course.newsletter.CourseNewsletterService#getSubscribers(org.openuss.lecture.CourseInfo)
     */
    public java.util.List getSubscribers(org.openuss.lecture.CourseInfo course)
    {
        try
        {
            return this.handleGetSubscribers(course);
        }
        catch (Throwable th)
        {
            throw new org.openuss.course.newsletter.CourseNewsletterServiceException(
                "Error performing 'org.openuss.course.newsletter.CourseNewsletterService.getSubscribers(org.openuss.lecture.CourseInfo course)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getSubscribers(org.openuss.lecture.CourseInfo)}
      */
    protected abstract java.util.List handleGetSubscribers(org.openuss.lecture.CourseInfo course)
        throws java.lang.Exception;

    /**
     * @see org.openuss.course.newsletter.CourseNewsletterService#saveMail(org.openuss.lecture.CourseInfo, org.openuss.newsletter.MailDetail)
     */
    public void saveMail(org.openuss.lecture.CourseInfo course, org.openuss.newsletter.MailDetail mail)
    {
        try
        {
            this.handleSaveMail(course, mail);
        }
        catch (Throwable th)
        {
            throw new org.openuss.course.newsletter.CourseNewsletterServiceException(
                "Error performing 'org.openuss.course.newsletter.CourseNewsletterService.saveMail(org.openuss.lecture.CourseInfo course, org.openuss.newsletter.MailDetail mail)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #saveMail(org.openuss.lecture.CourseInfo, org.openuss.newsletter.MailDetail)}
      */
    protected abstract void handleSaveMail(org.openuss.lecture.CourseInfo course, org.openuss.newsletter.MailDetail mail)
        throws java.lang.Exception;

    /**
     * @see org.openuss.course.newsletter.CourseNewsletterService#deleteMail(org.openuss.lecture.CourseInfo, org.openuss.newsletter.MailDetail)
     */
    public void deleteMail(org.openuss.lecture.CourseInfo course, org.openuss.newsletter.MailDetail mail)
    {
        try
        {
            this.handleDeleteMail(course, mail);
        }
        catch (Throwable th)
        {
            throw new org.openuss.course.newsletter.CourseNewsletterServiceException(
                "Error performing 'org.openuss.course.newsletter.CourseNewsletterService.deleteMail(org.openuss.lecture.CourseInfo course, org.openuss.newsletter.MailDetail mail)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #deleteMail(org.openuss.lecture.CourseInfo, org.openuss.newsletter.MailDetail)}
      */
    protected abstract void handleDeleteMail(org.openuss.lecture.CourseInfo course, org.openuss.newsletter.MailDetail mail)
        throws java.lang.Exception;

    /**
     * @see org.openuss.course.newsletter.CourseNewsletterService#addNewsletter(org.openuss.lecture.CourseInfo)
     */
    public void addNewsletter(org.openuss.lecture.CourseInfo course)
    {
        try
        {
            this.handleAddNewsletter(course);
        }
        catch (Throwable th)
        {
            throw new org.openuss.course.newsletter.CourseNewsletterServiceException(
                "Error performing 'org.openuss.course.newsletter.CourseNewsletterService.addNewsletter(org.openuss.lecture.CourseInfo course)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addNewsletter(org.openuss.lecture.CourseInfo)}
      */
    protected abstract void handleAddNewsletter(org.openuss.lecture.CourseInfo course)
        throws java.lang.Exception;

    /**
     * @see org.openuss.course.newsletter.CourseNewsletterService#getNewsletter(org.openuss.lecture.CourseInfo)
     */
    public org.openuss.newsletter.NewsletterInfo getNewsletter(org.openuss.lecture.CourseInfo course)
    {
        try
        {
            return this.handleGetNewsletter(course);
        }
        catch (Throwable th)
        {
            throw new org.openuss.course.newsletter.CourseNewsletterServiceException(
                "Error performing 'org.openuss.course.newsletter.CourseNewsletterService.getNewsletter(org.openuss.lecture.CourseInfo course)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getNewsletter(org.openuss.lecture.CourseInfo)}
      */
    protected abstract org.openuss.newsletter.NewsletterInfo handleGetNewsletter(org.openuss.lecture.CourseInfo course)
        throws java.lang.Exception;

    /**
     * @see org.openuss.course.newsletter.CourseNewsletterService#updateNewsletter(org.openuss.newsletter.NewsletterInfo)
     */
    public void updateNewsletter(org.openuss.newsletter.NewsletterInfo newsletter)
    {
        try
        {
            this.handleUpdateNewsletter(newsletter);
        }
        catch (Throwable th)
        {
            throw new org.openuss.course.newsletter.CourseNewsletterServiceException(
                "Error performing 'org.openuss.course.newsletter.CourseNewsletterService.updateNewsletter(org.openuss.newsletter.NewsletterInfo newsletter)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #updateNewsletter(org.openuss.newsletter.NewsletterInfo)}
      */
    protected abstract void handleUpdateNewsletter(org.openuss.newsletter.NewsletterInfo newsletter)
        throws java.lang.Exception;

    /**
     * @see org.openuss.course.newsletter.CourseNewsletterService#updateMail(org.openuss.lecture.CourseInfo, org.openuss.newsletter.MailDetail)
     */
    public void updateMail(org.openuss.lecture.CourseInfo course, org.openuss.newsletter.MailDetail mail)
    {
        try
        {
            this.handleUpdateMail(course, mail);
        }
        catch (Throwable th)
        {
            throw new org.openuss.course.newsletter.CourseNewsletterServiceException(
                "Error performing 'org.openuss.course.newsletter.CourseNewsletterService.updateMail(org.openuss.lecture.CourseInfo course, org.openuss.newsletter.MailDetail mail)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #updateMail(org.openuss.lecture.CourseInfo, org.openuss.newsletter.MailDetail)}
      */
    protected abstract void handleUpdateMail(org.openuss.lecture.CourseInfo course, org.openuss.newsletter.MailDetail mail)
        throws java.lang.Exception;

    /**
     * @see org.openuss.course.newsletter.CourseNewsletterService#exportSubscribers(org.openuss.lecture.CourseInfo)
     */
    public java.lang.String exportSubscribers(org.openuss.lecture.CourseInfo course)
    {
        try
        {
            return this.handleExportSubscribers(course);
        }
        catch (Throwable th)
        {
            throw new org.openuss.course.newsletter.CourseNewsletterServiceException(
                "Error performing 'org.openuss.course.newsletter.CourseNewsletterService.exportSubscribers(org.openuss.lecture.CourseInfo course)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #exportSubscribers(org.openuss.lecture.CourseInfo)}
      */
    protected abstract java.lang.String handleExportSubscribers(org.openuss.lecture.CourseInfo course)
        throws java.lang.Exception;

    /**
     * @see org.openuss.course.newsletter.CourseNewsletterService#cancelSending(org.openuss.newsletter.MailInfo)
     */
    public void cancelSending(org.openuss.newsletter.MailInfo mail)
    {
        try
        {
            this.handleCancelSending(mail);
        }
        catch (Throwable th)
        {
            throw new org.openuss.course.newsletter.CourseNewsletterServiceException(
                "Error performing 'org.openuss.course.newsletter.CourseNewsletterService.cancelSending(org.openuss.newsletter.MailInfo mail)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #cancelSending(org.openuss.newsletter.MailInfo)}
      */
    protected abstract void handleCancelSending(org.openuss.newsletter.MailInfo mail)
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