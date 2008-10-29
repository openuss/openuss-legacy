package org.openuss.viewtracking;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.viewtracking.TrackingService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.viewtracking.TrackingService
 */
public abstract class TrackingServiceBase
    implements org.openuss.viewtracking.TrackingService
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

    private org.openuss.viewtracking.DomainViewStateDao domainViewStateDao;

    /**
     * Sets the reference to <code>domainViewState</code>'s DAO.
     */
    public void setDomainViewStateDao(org.openuss.viewtracking.DomainViewStateDao domainViewStateDao)
    {
        this.domainViewStateDao = domainViewStateDao;
    }

    /**
     * Gets the reference to <code>domainViewState</code>'s DAO.
     */
    protected org.openuss.viewtracking.DomainViewStateDao getDomainViewStateDao()
    {
        return this.domainViewStateDao;
    }

    /**
     * @see org.openuss.viewtracking.TrackingService#setNew(java.lang.Object)
     */
    public void setNew(java.lang.Object domainObject)
    {
        try
        {
            this.handleSetNew(domainObject);
        }
        catch (Throwable th)
        {
            throw new org.openuss.viewtracking.TrackingServiceException(
                "Error performing 'org.openuss.viewtracking.TrackingService.setNew(java.lang.Object domainObject)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #setNew(java.lang.Object)}
      */
    protected abstract void handleSetNew(java.lang.Object domainObject)
        throws java.lang.Exception;

    /**
     * @see org.openuss.viewtracking.TrackingService#setRead(java.lang.Object)
     */
    public void setRead(java.lang.Object domainObject)
    {
        try
        {
            this.handleSetRead(domainObject);
        }
        catch (Throwable th)
        {
            throw new org.openuss.viewtracking.TrackingServiceException(
                "Error performing 'org.openuss.viewtracking.TrackingService.setRead(java.lang.Object domainObject)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #setRead(java.lang.Object)}
      */
    protected abstract void handleSetRead(java.lang.Object domainObject)
        throws java.lang.Exception;

    /**
     * @see org.openuss.viewtracking.TrackingService#setModified(java.lang.Object)
     */
    public void setModified(java.lang.Object domainObject)
    {
        try
        {
            this.handleSetModified(domainObject);
        }
        catch (Throwable th)
        {
            throw new org.openuss.viewtracking.TrackingServiceException(
                "Error performing 'org.openuss.viewtracking.TrackingService.setModified(java.lang.Object domainObject)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #setModified(java.lang.Object)}
      */
    protected abstract void handleSetModified(java.lang.Object domainObject)
        throws java.lang.Exception;

    /**
     * @see org.openuss.viewtracking.TrackingService#getViewState(java.lang.Object)
     */
    public org.openuss.viewtracking.ViewState getViewState(java.lang.Object domainObject)
    {
        try
        {
            return this.handleGetViewState(domainObject);
        }
        catch (Throwable th)
        {
            throw new org.openuss.viewtracking.TrackingServiceException(
                "Error performing 'org.openuss.viewtracking.TrackingService.getViewState(java.lang.Object domainObject)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getViewState(java.lang.Object)}
      */
    protected abstract org.openuss.viewtracking.ViewState handleGetViewState(java.lang.Object domainObject)
        throws java.lang.Exception;

    /**
     * @see org.openuss.viewtracking.TrackingService#setViewState(org.openuss.viewtracking.ViewState, java.lang.Object)
     */
    public void setViewState(org.openuss.viewtracking.ViewState viewState, java.lang.Object domainObject)
    {
        try
        {
            this.handleSetViewState(viewState, domainObject);
        }
        catch (Throwable th)
        {
            throw new org.openuss.viewtracking.TrackingServiceException(
                "Error performing 'org.openuss.viewtracking.TrackingService.setViewState(org.openuss.viewtracking.ViewState viewState, java.lang.Object domainObject)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #setViewState(org.openuss.viewtracking.ViewState, java.lang.Object)}
      */
    protected abstract void handleSetViewState(org.openuss.viewtracking.ViewState viewState, java.lang.Object domainObject)
        throws java.lang.Exception;

    /**
     * @see org.openuss.viewtracking.TrackingService#remove(java.lang.Object)
     */
    public void remove(java.lang.Object domainObject)
    {
        try
        {
            this.handleRemove(domainObject);
        }
        catch (Throwable th)
        {
            throw new org.openuss.viewtracking.TrackingServiceException(
                "Error performing 'org.openuss.viewtracking.TrackingService.remove(java.lang.Object domainObject)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #remove(java.lang.Object)}
      */
    protected abstract void handleRemove(java.lang.Object domainObject)
        throws java.lang.Exception;

    /**
     * @see org.openuss.viewtracking.TrackingService#getTopicViewStates(java.lang.Long, java.lang.Long)
     */
    public java.util.List getTopicViewStates(java.lang.Long domainIdentifier, java.lang.Long userId)
    {
        try
        {
            return this.handleGetTopicViewStates(domainIdentifier, userId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.viewtracking.TrackingServiceException(
                "Error performing 'org.openuss.viewtracking.TrackingService.getTopicViewStates(java.lang.Long domainIdentifier, java.lang.Long userId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getTopicViewStates(java.lang.Long, java.lang.Long)}
      */
    protected abstract java.util.List handleGetTopicViewStates(java.lang.Long domainIdentifier, java.lang.Long userId)
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