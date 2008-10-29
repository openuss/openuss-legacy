package org.openuss.repository;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.repository.RepositoryService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.repository.RepositoryService
 */
public abstract class RepositoryServiceBase
    implements org.openuss.repository.RepositoryService
{

    private org.openuss.repository.RepositoryFileDao repositoryFileDao;

    /**
     * Sets the reference to <code>repositoryFile</code>'s DAO.
     */
    public void setRepositoryFileDao(org.openuss.repository.RepositoryFileDao repositoryFileDao)
    {
        this.repositoryFileDao = repositoryFileDao;
    }

    /**
     * Gets the reference to <code>repositoryFile</code>'s DAO.
     */
    protected org.openuss.repository.RepositoryFileDao getRepositoryFileDao()
    {
        return this.repositoryFileDao;
    }

    /**
     * @see org.openuss.repository.RepositoryService#saveContent(java.lang.Long, java.io.InputStream)
     */
    public void saveContent(java.lang.Long fileId, java.io.InputStream content)
    {
        try
        {
            this.handleSaveContent(fileId, content);
        }
        catch (Throwable th)
        {
            throw new org.openuss.repository.RepositoryServiceException(
                "Error performing 'org.openuss.repository.RepositoryService.saveContent(java.lang.Long fileId, java.io.InputStream content)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #saveContent(java.lang.Long, java.io.InputStream)}
      */
    protected abstract void handleSaveContent(java.lang.Long fileId, java.io.InputStream content)
        throws java.lang.Exception;

    /**
     * @see org.openuss.repository.RepositoryService#loadContent(java.lang.Long)
     */
    public java.io.InputStream loadContent(java.lang.Long fileId)
    {
        try
        {
            return this.handleLoadContent(fileId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.repository.RepositoryServiceException(
                "Error performing 'org.openuss.repository.RepositoryService.loadContent(java.lang.Long fileId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #loadContent(java.lang.Long)}
      */
    protected abstract java.io.InputStream handleLoadContent(java.lang.Long fileId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.repository.RepositoryService#removeContent(java.lang.Long)
     */
    public void removeContent(java.lang.Long fileId)
    {
        try
        {
            this.handleRemoveContent(fileId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.repository.RepositoryServiceException(
                "Error performing 'org.openuss.repository.RepositoryService.removeContent(java.lang.Long fileId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeContent(java.lang.Long)}
      */
    protected abstract void handleRemoveContent(java.lang.Long fileId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.repository.RepositoryService#setRepositoryLocation(java.lang.String)
     */
    public void setRepositoryLocation(java.lang.String path)
    {
        try
        {
            this.handleSetRepositoryLocation(path);
        }
        catch (Throwable th)
        {
            throw new org.openuss.repository.RepositoryServiceException(
                "Error performing 'org.openuss.repository.RepositoryService.setRepositoryLocation(java.lang.String path)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #setRepositoryLocation(java.lang.String)}
      */
    protected abstract void handleSetRepositoryLocation(java.lang.String path)
        throws java.lang.Exception;

    /**
     * @see org.openuss.repository.RepositoryService#getRepositoryLocation()
     */
    public java.lang.String getRepositoryLocation()
    {
        try
        {
            return this.handleGetRepositoryLocation();
        }
        catch (Throwable th)
        {
            throw new org.openuss.repository.RepositoryServiceException(
                "Error performing 'org.openuss.repository.RepositoryService.getRepositoryLocation()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getRepositoryLocation()}
      */
    protected abstract java.lang.String handleGetRepositoryLocation()
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