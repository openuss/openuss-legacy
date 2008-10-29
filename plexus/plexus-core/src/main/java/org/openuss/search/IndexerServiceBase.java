package org.openuss.search;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.search.IndexerService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.search.IndexerService
 */
public abstract class IndexerServiceBase
    implements org.openuss.search.IndexerService
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

    /**
     * @see org.openuss.search.IndexerService#createIndex(org.openuss.foundation.DomainObject)
     */
    public void createIndex(org.openuss.foundation.DomainObject domainObject)
        throws org.openuss.search.IndexerApplicationException
    {
        try
        {
            this.handleCreateIndex(domainObject);
        }
        catch (org.openuss.search.IndexerApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.search.IndexerServiceException(
                "Error performing 'org.openuss.search.IndexerService.createIndex(org.openuss.foundation.DomainObject domainObject)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createIndex(org.openuss.foundation.DomainObject)}
      */
    protected abstract void handleCreateIndex(org.openuss.foundation.DomainObject domainObject)
        throws java.lang.Exception;

    /**
     * @see org.openuss.search.IndexerService#updateIndex(org.openuss.foundation.DomainObject)
     */
    public void updateIndex(org.openuss.foundation.DomainObject domainObject)
        throws org.openuss.search.IndexerApplicationException
    {
        try
        {
            this.handleUpdateIndex(domainObject);
        }
        catch (org.openuss.search.IndexerApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.search.IndexerServiceException(
                "Error performing 'org.openuss.search.IndexerService.updateIndex(org.openuss.foundation.DomainObject domainObject)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #updateIndex(org.openuss.foundation.DomainObject)}
      */
    protected abstract void handleUpdateIndex(org.openuss.foundation.DomainObject domainObject)
        throws java.lang.Exception;

    /**
     * @see org.openuss.search.IndexerService#deleteIndex(org.openuss.foundation.DomainObject)
     */
    public void deleteIndex(org.openuss.foundation.DomainObject domainObject)
        throws org.openuss.search.IndexerApplicationException
    {
        try
        {
            this.handleDeleteIndex(domainObject);
        }
        catch (org.openuss.search.IndexerApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.search.IndexerServiceException(
                "Error performing 'org.openuss.search.IndexerService.deleteIndex(org.openuss.foundation.DomainObject domainObject)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #deleteIndex(org.openuss.foundation.DomainObject)}
      */
    protected abstract void handleDeleteIndex(org.openuss.foundation.DomainObject domainObject)
        throws java.lang.Exception;

    /**
     * @see org.openuss.search.IndexerService#recreate()
     */
    public void recreate()
        throws org.openuss.search.IndexerApplicationException
    {
        try
        {
            this.handleRecreate();
        }
        catch (org.openuss.search.IndexerApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.search.IndexerServiceException(
                "Error performing 'org.openuss.search.IndexerService.recreate()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #recreate()}
      */
    protected abstract void handleRecreate()
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