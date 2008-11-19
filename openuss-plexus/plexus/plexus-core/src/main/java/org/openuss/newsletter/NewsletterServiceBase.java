package org.openuss.newsletter;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.newsletter.NewsletterService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.newsletter.NewsletterService
 */
public abstract class NewsletterServiceBase
    implements org.openuss.newsletter.NewsletterService
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

    private org.openuss.commands.CommandService commandService;

    /**
     * Sets the reference to <code>commandService</code>.
     */
    public void setCommandService(org.openuss.commands.CommandService commandService)
    {
        this.commandService = commandService;
    }

    /**
     * Gets the reference to <code>commandService</code>.
     */
    protected org.openuss.commands.CommandService getCommandService()
    {
        return this.commandService;
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

    private org.openuss.newsletter.NewsletterDao newsletterDao;

    /**
     * Sets the reference to <code>newsletter</code>'s DAO.
     */
    public void setNewsletterDao(org.openuss.newsletter.NewsletterDao newsletterDao)
    {
        this.newsletterDao = newsletterDao;
    }

    /**
     * Gets the reference to <code>newsletter</code>'s DAO.
     */
    protected org.openuss.newsletter.NewsletterDao getNewsletterDao()
    {
        return this.newsletterDao;
    }

    private org.openuss.newsletter.SubscriberDao subscriberDao;

    /**
     * Sets the reference to <code>subscriber</code>'s DAO.
     */
    public void setSubscriberDao(org.openuss.newsletter.SubscriberDao subscriberDao)
    {
        this.subscriberDao = subscriberDao;
    }

    /**
     * Gets the reference to <code>subscriber</code>'s DAO.
     */
    protected org.openuss.newsletter.SubscriberDao getSubscriberDao()
    {
        return this.subscriberDao;
    }

    private org.openuss.newsletter.MailDao mailDao;

    /**
     * Sets the reference to <code>mail</code>'s DAO.
     */
    public void setMailDao(org.openuss.newsletter.MailDao mailDao)
    {
        this.mailDao = mailDao;
    }

    /**
     * Gets the reference to <code>mail</code>'s DAO.
     */
    protected org.openuss.newsletter.MailDao getMailDao()
    {
        return this.mailDao;
    }

    /**
     * @see org.openuss.newsletter.NewsletterService#subscribe(org.openuss.newsletter.NewsletterInfo, org.openuss.security.UserInfo)
     */
    public void subscribe(org.openuss.newsletter.NewsletterInfo newsletter, org.openuss.security.UserInfo user)
    {
        try
        {
            this.handleSubscribe(newsletter, user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.subscribe(org.openuss.newsletter.NewsletterInfo newsletter, org.openuss.security.UserInfo user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #subscribe(org.openuss.newsletter.NewsletterInfo, org.openuss.security.UserInfo)}
      */
    protected abstract void handleSubscribe(org.openuss.newsletter.NewsletterInfo newsletter, org.openuss.security.UserInfo user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#unsubscribe(org.openuss.newsletter.NewsletterInfo, org.openuss.security.UserInfo)
     */
    public void unsubscribe(org.openuss.newsletter.NewsletterInfo newsletter, org.openuss.security.UserInfo user)
    {
        try
        {
            this.handleUnsubscribe(newsletter, user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.unsubscribe(org.openuss.newsletter.NewsletterInfo newsletter, org.openuss.security.UserInfo user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #unsubscribe(org.openuss.newsletter.NewsletterInfo, org.openuss.security.UserInfo)}
      */
    protected abstract void handleUnsubscribe(org.openuss.newsletter.NewsletterInfo newsletter, org.openuss.security.UserInfo user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#unsubscribe(org.openuss.newsletter.SubscriberInfo)
     */
    public void unsubscribe(org.openuss.newsletter.SubscriberInfo subscriber)
    {
        try
        {
            this.handleUnsubscribe(subscriber);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.unsubscribe(org.openuss.newsletter.SubscriberInfo subscriber)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #unsubscribe(org.openuss.newsletter.SubscriberInfo)}
      */
    protected abstract void handleUnsubscribe(org.openuss.newsletter.SubscriberInfo subscriber)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#setBlockingState(org.openuss.newsletter.SubscriberInfo)
     */
    public void setBlockingState(org.openuss.newsletter.SubscriberInfo subscriber)
    {
        try
        {
            this.handleSetBlockingState(subscriber);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.setBlockingState(org.openuss.newsletter.SubscriberInfo subscriber)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #setBlockingState(org.openuss.newsletter.SubscriberInfo)}
      */
    protected abstract void handleSetBlockingState(org.openuss.newsletter.SubscriberInfo subscriber)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#saveMail(org.openuss.newsletter.NewsletterInfo, org.openuss.newsletter.MailDetail)
     */
    public void saveMail(org.openuss.newsletter.NewsletterInfo newsletter, org.openuss.newsletter.MailDetail mail)
    {
        try
        {
            this.handleSaveMail(newsletter, mail);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.saveMail(org.openuss.newsletter.NewsletterInfo newsletter, org.openuss.newsletter.MailDetail mail)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #saveMail(org.openuss.newsletter.NewsletterInfo, org.openuss.newsletter.MailDetail)}
      */
    protected abstract void handleSaveMail(org.openuss.newsletter.NewsletterInfo newsletter, org.openuss.newsletter.MailDetail mail)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#deleteMail(org.openuss.newsletter.NewsletterInfo, org.openuss.newsletter.MailDetail)
     */
    public void deleteMail(org.openuss.newsletter.NewsletterInfo newsletter, org.openuss.newsletter.MailDetail mail)
        throws org.openuss.newsletter.NewsletterApplicationException
    {
        try
        {
            this.handleDeleteMail(newsletter, mail);
        }
        catch (org.openuss.newsletter.NewsletterApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.deleteMail(org.openuss.newsletter.NewsletterInfo newsletter, org.openuss.newsletter.MailDetail mail)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #deleteMail(org.openuss.newsletter.NewsletterInfo, org.openuss.newsletter.MailDetail)}
      */
    protected abstract void handleDeleteMail(org.openuss.newsletter.NewsletterInfo newsletter, org.openuss.newsletter.MailDetail mail)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#sendPreview(org.openuss.newsletter.NewsletterInfo, org.openuss.newsletter.MailDetail)
     */
    public void sendPreview(org.openuss.newsletter.NewsletterInfo newsletter, org.openuss.newsletter.MailDetail mail)
    {
        try
        {
            this.handleSendPreview(newsletter, mail);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.sendPreview(org.openuss.newsletter.NewsletterInfo newsletter, org.openuss.newsletter.MailDetail mail)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #sendPreview(org.openuss.newsletter.NewsletterInfo, org.openuss.newsletter.MailDetail)}
      */
    protected abstract void handleSendPreview(org.openuss.newsletter.NewsletterInfo newsletter, org.openuss.newsletter.MailDetail mail)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#sendMail(org.openuss.newsletter.NewsletterInfo, org.openuss.newsletter.MailDetail)
     */
    public void sendMail(org.openuss.newsletter.NewsletterInfo newsletter, org.openuss.newsletter.MailDetail mail)
    {
        try
        {
            this.handleSendMail(newsletter, mail);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.sendMail(org.openuss.newsletter.NewsletterInfo newsletter, org.openuss.newsletter.MailDetail mail)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #sendMail(org.openuss.newsletter.NewsletterInfo, org.openuss.newsletter.MailDetail)}
      */
    protected abstract void handleSendMail(org.openuss.newsletter.NewsletterInfo newsletter, org.openuss.newsletter.MailDetail mail)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#addNewsletter(org.openuss.foundation.DomainObject, java.lang.String)
     */
    public void addNewsletter(org.openuss.foundation.DomainObject domainObject, java.lang.String name)
    {
        try
        {
            this.handleAddNewsletter(domainObject, name);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.addNewsletter(org.openuss.foundation.DomainObject domainObject, java.lang.String name)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addNewsletter(org.openuss.foundation.DomainObject, java.lang.String)}
      */
    protected abstract void handleAddNewsletter(org.openuss.foundation.DomainObject domainObject, java.lang.String name)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#updateNewsletter(org.openuss.newsletter.NewsletterInfo)
     */
    public void updateNewsletter(org.openuss.newsletter.NewsletterInfo newsletter)
    {
        try
        {
            this.handleUpdateNewsletter(newsletter);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.updateNewsletter(org.openuss.newsletter.NewsletterInfo newsletter)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #updateNewsletter(org.openuss.newsletter.NewsletterInfo)}
      */
    protected abstract void handleUpdateNewsletter(org.openuss.newsletter.NewsletterInfo newsletter)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#updateMail(org.openuss.foundation.DomainObject, org.openuss.newsletter.MailDetail)
     */
    public void updateMail(org.openuss.foundation.DomainObject domainObject, org.openuss.newsletter.MailDetail mail)
    {
        try
        {
            this.handleUpdateMail(domainObject, mail);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.updateMail(org.openuss.foundation.DomainObject domainObject, org.openuss.newsletter.MailDetail mail)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #updateMail(org.openuss.foundation.DomainObject, org.openuss.newsletter.MailDetail)}
      */
    protected abstract void handleUpdateMail(org.openuss.foundation.DomainObject domainObject, org.openuss.newsletter.MailDetail mail)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#exportSubscribers(org.openuss.newsletter.NewsletterInfo)
     */
    public java.lang.String exportSubscribers(org.openuss.newsletter.NewsletterInfo newsletter)
    {
        try
        {
            return this.handleExportSubscribers(newsletter);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.exportSubscribers(org.openuss.newsletter.NewsletterInfo newsletter)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #exportSubscribers(org.openuss.newsletter.NewsletterInfo)}
      */
    protected abstract java.lang.String handleExportSubscribers(org.openuss.newsletter.NewsletterInfo newsletter)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#cancelSending(org.openuss.newsletter.MailInfo)
     */
    public void cancelSending(org.openuss.newsletter.MailInfo mail)
    {
        try
        {
            this.handleCancelSending(mail);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.cancelSending(org.openuss.newsletter.MailInfo mail)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #cancelSending(org.openuss.newsletter.MailInfo)}
      */
    protected abstract void handleCancelSending(org.openuss.newsletter.MailInfo mail)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#markAsSend(org.openuss.newsletter.MailInfo)
     */
    public void markAsSend(org.openuss.newsletter.MailInfo mail)
    {
        try
        {
            this.handleMarkAsSend(mail);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.markAsSend(org.openuss.newsletter.MailInfo mail)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #markAsSend(org.openuss.newsletter.MailInfo)}
      */
    protected abstract void handleMarkAsSend(org.openuss.newsletter.MailInfo mail)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#getSubscribers(org.openuss.newsletter.NewsletterInfo)
     */
    public java.util.List getSubscribers(org.openuss.newsletter.NewsletterInfo newsletter)
    {
        try
        {
            return this.handleGetSubscribers(newsletter);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.getSubscribers(org.openuss.newsletter.NewsletterInfo newsletter)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getSubscribers(org.openuss.newsletter.NewsletterInfo)}
      */
    protected abstract java.util.List handleGetSubscribers(org.openuss.newsletter.NewsletterInfo newsletter)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#getMails(org.openuss.newsletter.NewsletterInfo, boolean)
     */
    public java.util.List getMails(org.openuss.newsletter.NewsletterInfo newsletter, boolean withDeleted)
    {
        try
        {
            return this.handleGetMails(newsletter, withDeleted);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.getMails(org.openuss.newsletter.NewsletterInfo newsletter, boolean withDeleted)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getMails(org.openuss.newsletter.NewsletterInfo, boolean)}
      */
    protected abstract java.util.List handleGetMails(org.openuss.newsletter.NewsletterInfo newsletter, boolean withDeleted)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#getMail(org.openuss.newsletter.MailInfo)
     */
    public org.openuss.newsletter.MailDetail getMail(org.openuss.newsletter.MailInfo mail)
    {
        try
        {
            return this.handleGetMail(mail);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.getMail(org.openuss.newsletter.MailInfo mail)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getMail(org.openuss.newsletter.MailInfo)}
      */
    protected abstract org.openuss.newsletter.MailDetail handleGetMail(org.openuss.newsletter.MailInfo mail)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#getNewsletter(org.openuss.foundation.DomainObject)
     */
    public org.openuss.newsletter.NewsletterInfo getNewsletter(org.openuss.foundation.DomainObject domainObject)
    {
        try
        {
            return this.handleGetNewsletter(domainObject);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.getNewsletter(org.openuss.foundation.DomainObject domainObject)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getNewsletter(org.openuss.foundation.DomainObject)}
      */
    protected abstract org.openuss.newsletter.NewsletterInfo handleGetNewsletter(org.openuss.foundation.DomainObject domainObject)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#getNewsletter(org.openuss.newsletter.MailDetail)
     */
    public org.openuss.newsletter.NewsletterInfo getNewsletter(org.openuss.newsletter.MailDetail mail)
    {
        try
        {
            return this.handleGetNewsletter(mail);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.getNewsletter(org.openuss.newsletter.MailDetail mail)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getNewsletter(org.openuss.newsletter.MailDetail)}
      */
    protected abstract org.openuss.newsletter.NewsletterInfo handleGetNewsletter(org.openuss.newsletter.MailDetail mail)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.NewsletterService#removeAllSubscriptions(org.openuss.security.User)
     */
    public void removeAllSubscriptions(org.openuss.security.User user)
    {
        try
        {
            this.handleRemoveAllSubscriptions(user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.newsletter.NewsletterServiceException(
                "Error performing 'org.openuss.newsletter.NewsletterService.removeAllSubscriptions(org.openuss.security.User user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeAllSubscriptions(org.openuss.security.User)}
      */
    protected abstract void handleRemoveAllSubscriptions(org.openuss.security.User user)
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