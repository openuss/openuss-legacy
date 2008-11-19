package org.openuss.messaging;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.messaging.MessageService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.messaging.MessageService
 */
public abstract class MessageServiceBase
    implements org.openuss.messaging.MessageService
{

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

    private org.openuss.messaging.MessageJobDao messageJobDao;

    /**
     * Sets the reference to <code>messageJob</code>'s DAO.
     */
    public void setMessageJobDao(org.openuss.messaging.MessageJobDao messageJobDao)
    {
        this.messageJobDao = messageJobDao;
    }

    /**
     * Gets the reference to <code>messageJob</code>'s DAO.
     */
    protected org.openuss.messaging.MessageJobDao getMessageJobDao()
    {
        return this.messageJobDao;
    }

    /**
     * @see org.openuss.messaging.MessageService#sendMessage(java.lang.String, java.lang.String, java.lang.String, boolean, java.util.List)
     */
    public java.lang.Long sendMessage(java.lang.String sender, java.lang.String subject, java.lang.String text, boolean sms, java.util.List recipients)
    {
        try
        {
            return this.handleSendMessage(sender, subject, text, sms, recipients);
        }
        catch (Throwable th)
        {
            throw new org.openuss.messaging.MessageServiceException(
                "Error performing 'org.openuss.messaging.MessageService.sendMessage(java.lang.String sender, java.lang.String subject, java.lang.String text, boolean sms, java.util.List recipients)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #sendMessage(java.lang.String, java.lang.String, java.lang.String, boolean, java.util.List)}
      */
    protected abstract java.lang.Long handleSendMessage(java.lang.String sender, java.lang.String subject, java.lang.String text, boolean sms, java.util.List recipients)
        throws java.lang.Exception;

    /**
     * @see org.openuss.messaging.MessageService#sendMessage(java.lang.String, java.lang.String, java.lang.String, boolean, org.openuss.security.UserInfo)
     */
    public java.lang.Long sendMessage(java.lang.String sender, java.lang.String subject, java.lang.String text, boolean sms, org.openuss.security.UserInfo recipient)
    {
        try
        {
            return this.handleSendMessage(sender, subject, text, sms, recipient);
        }
        catch (Throwable th)
        {
            throw new org.openuss.messaging.MessageServiceException(
                "Error performing 'org.openuss.messaging.MessageService.sendMessage(java.lang.String sender, java.lang.String subject, java.lang.String text, boolean sms, org.openuss.security.UserInfo recipient)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #sendMessage(java.lang.String, java.lang.String, java.lang.String, boolean, org.openuss.security.UserInfo)}
      */
    protected abstract java.lang.Long handleSendMessage(java.lang.String sender, java.lang.String subject, java.lang.String text, boolean sms, org.openuss.security.UserInfo recipient)
        throws java.lang.Exception;

    /**
     * @see org.openuss.messaging.MessageService#sendMessage(java.lang.String, java.lang.String, java.lang.String, java.util.Map, java.util.List)
     */
    public java.lang.Long sendMessage(java.lang.String sender, java.lang.String subject, java.lang.String templateName, java.util.Map parameters, java.util.List recipients)
    {
        try
        {
            return this.handleSendMessage(sender, subject, templateName, parameters, recipients);
        }
        catch (Throwable th)
        {
            throw new org.openuss.messaging.MessageServiceException(
                "Error performing 'org.openuss.messaging.MessageService.sendMessage(java.lang.String sender, java.lang.String subject, java.lang.String templateName, java.util.Map parameters, java.util.List recipients)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #sendMessage(java.lang.String, java.lang.String, java.lang.String, java.util.Map, java.util.List)}
      */
    protected abstract java.lang.Long handleSendMessage(java.lang.String sender, java.lang.String subject, java.lang.String templateName, java.util.Map parameters, java.util.List recipients)
        throws java.lang.Exception;

    /**
     * @see org.openuss.messaging.MessageService#sendMessage(java.lang.String, java.lang.String, java.lang.String, java.util.Map, org.openuss.security.UserInfo)
     */
    public java.lang.Long sendMessage(java.lang.String sender, java.lang.String subject, java.lang.String templateName, java.util.Map paramters, org.openuss.security.UserInfo recipient)
    {
        try
        {
            return this.handleSendMessage(sender, subject, templateName, paramters, recipient);
        }
        catch (Throwable th)
        {
            throw new org.openuss.messaging.MessageServiceException(
                "Error performing 'org.openuss.messaging.MessageService.sendMessage(java.lang.String sender, java.lang.String subject, java.lang.String templateName, java.util.Map paramters, org.openuss.security.UserInfo recipient)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #sendMessage(java.lang.String, java.lang.String, java.lang.String, java.util.Map, org.openuss.security.UserInfo)}
      */
    protected abstract java.lang.Long handleSendMessage(java.lang.String sender, java.lang.String subject, java.lang.String templateName, java.util.Map paramters, org.openuss.security.UserInfo recipient)
        throws java.lang.Exception;

    /**
     * @see org.openuss.messaging.MessageService#sendMessage(java.lang.String, java.lang.String, java.lang.String, java.util.Map, java.lang.String, java.lang.String)
     */
    public java.lang.Long sendMessage(java.lang.String sender, java.lang.String subject, java.lang.String templateName, java.util.Map parameters, java.lang.String email, java.lang.String locale)
    {
        try
        {
            return this.handleSendMessage(sender, subject, templateName, parameters, email, locale);
        }
        catch (Throwable th)
        {
            throw new org.openuss.messaging.MessageServiceException(
                "Error performing 'org.openuss.messaging.MessageService.sendMessage(java.lang.String sender, java.lang.String subject, java.lang.String templateName, java.util.Map parameters, java.lang.String email, java.lang.String locale)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #sendMessage(java.lang.String, java.lang.String, java.lang.String, java.util.Map, java.lang.String, java.lang.String)}
      */
    protected abstract java.lang.Long handleSendMessage(java.lang.String sender, java.lang.String subject, java.lang.String templateName, java.util.Map parameters, java.lang.String email, java.lang.String locale)
        throws java.lang.Exception;

    /**
     * @see org.openuss.messaging.MessageService#getJobState(java.lang.Long)
     */
    public org.openuss.messaging.JobInfo getJobState(java.lang.Long messageId)
    {
        try
        {
            return this.handleGetJobState(messageId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.messaging.MessageServiceException(
                "Error performing 'org.openuss.messaging.MessageService.getJobState(java.lang.Long messageId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getJobState(java.lang.Long)}
      */
    protected abstract org.openuss.messaging.JobInfo handleGetJobState(java.lang.Long messageId)
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