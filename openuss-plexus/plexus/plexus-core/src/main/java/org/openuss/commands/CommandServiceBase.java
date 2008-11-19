package org.openuss.commands;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.commands.CommandService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.commands.CommandService
 */
public abstract class CommandServiceBase
    implements org.openuss.commands.CommandService
{

    private org.quartz.Scheduler clusterScheduler;

    /**
     * Sets the reference to <code>clusterScheduler</code>.
     */
    public void setClusterScheduler(org.quartz.Scheduler clusterScheduler)
    {
        this.clusterScheduler = clusterScheduler;
    }

    /**
     * Gets the reference to <code>clusterScheduler</code>.
     */
    protected org.quartz.Scheduler getClusterScheduler()
    {
        return this.clusterScheduler;
    }

    private org.openuss.commands.CommandDao commandDao;

    /**
     * Sets the reference to <code>command</code>'s DAO.
     */
    public void setCommandDao(org.openuss.commands.CommandDao commandDao)
    {
        this.commandDao = commandDao;
    }

    /**
     * Gets the reference to <code>command</code>'s DAO.
     */
    protected org.openuss.commands.CommandDao getCommandDao()
    {
        return this.commandDao;
    }

    /**
     * @see org.openuss.commands.CommandService#createEachCommand(org.openuss.foundation.DomainObject, java.lang.String, java.lang.String)
     */
    public void createEachCommand(org.openuss.foundation.DomainObject domainObject, java.lang.String commandName, java.lang.String commandType)
        throws org.openuss.commands.CommandApplicationService
    {
        try
        {
            this.handleCreateEachCommand(domainObject, commandName, commandType);
        }
        catch (org.openuss.commands.CommandApplicationService ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.commands.CommandServiceException(
                "Error performing 'org.openuss.commands.CommandService.createEachCommand(org.openuss.foundation.DomainObject domainObject, java.lang.String commandName, java.lang.String commandType)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createEachCommand(org.openuss.foundation.DomainObject, java.lang.String, java.lang.String)}
      */
    protected abstract void handleCreateEachCommand(org.openuss.foundation.DomainObject domainObject, java.lang.String commandName, java.lang.String commandType)
        throws java.lang.Exception;

    /**
     * @see org.openuss.commands.CommandService#createOnceCommand(org.openuss.foundation.DomainObject, java.lang.String, java.util.Date, java.lang.String)
     */
    public java.lang.Long createOnceCommand(org.openuss.foundation.DomainObject domainObject, java.lang.String commandName, java.util.Date startTime, java.lang.String commandType)
        throws org.openuss.commands.CommandApplicationService
    {
        try
        {
            return this.handleCreateOnceCommand(domainObject, commandName, startTime, commandType);
        }
        catch (org.openuss.commands.CommandApplicationService ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.commands.CommandServiceException(
                "Error performing 'org.openuss.commands.CommandService.createOnceCommand(org.openuss.foundation.DomainObject domainObject, java.lang.String commandName, java.util.Date startTime, java.lang.String commandType)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createOnceCommand(org.openuss.foundation.DomainObject, java.lang.String, java.util.Date, java.lang.String)}
      */
    protected abstract java.lang.Long handleCreateOnceCommand(org.openuss.foundation.DomainObject domainObject, java.lang.String commandName, java.util.Date startTime, java.lang.String commandType)
        throws java.lang.Exception;

    /**
     * @see org.openuss.commands.CommandService#setStartTime(java.lang.Long, java.util.Date)
     */
    public void setStartTime(java.lang.Long commandId, java.util.Date startTime)
        throws org.openuss.commands.CommandApplicationService
    {
        try
        {
            this.handleSetStartTime(commandId, startTime);
        }
        catch (org.openuss.commands.CommandApplicationService ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.commands.CommandServiceException(
                "Error performing 'org.openuss.commands.CommandService.setStartTime(java.lang.Long commandId, java.util.Date startTime)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #setStartTime(java.lang.Long, java.util.Date)}
      */
    protected abstract void handleSetStartTime(java.lang.Long commandId, java.util.Date startTime)
        throws java.lang.Exception;

    /**
     * @see org.openuss.commands.CommandService#deleteCommand(java.lang.Long)
     */
    public void deleteCommand(java.lang.Long commandId)
        throws org.openuss.commands.CommandApplicationService
    {
        try
        {
            this.handleDeleteCommand(commandId);
        }
        catch (org.openuss.commands.CommandApplicationService ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.commands.CommandServiceException(
                "Error performing 'org.openuss.commands.CommandService.deleteCommand(java.lang.Long commandId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #deleteCommand(java.lang.Long)}
      */
    protected abstract void handleDeleteCommand(java.lang.Long commandId)
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