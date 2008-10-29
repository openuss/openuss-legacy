package org.openuss.documents;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.documents.DocumentService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.documents.DocumentService
 */
public abstract class DocumentServiceBase
    implements org.openuss.documents.DocumentService
{

    private org.openuss.repository.RepositoryService repositoryService;

    /**
     * Sets the reference to <code>repositoryService</code>.
     */
    public void setRepositoryService(org.openuss.repository.RepositoryService repositoryService)
    {
        this.repositoryService = repositoryService;
    }

    /**
     * Gets the reference to <code>repositoryService</code>.
     */
    protected org.openuss.repository.RepositoryService getRepositoryService()
    {
        return this.repositoryService;
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

    private org.openuss.documents.FolderEntryDao folderEntryDao;

    /**
     * Sets the reference to <code>folderEntry</code>'s DAO.
     */
    public void setFolderEntryDao(org.openuss.documents.FolderEntryDao folderEntryDao)
    {
        this.folderEntryDao = folderEntryDao;
    }

    /**
     * Gets the reference to <code>folderEntry</code>'s DAO.
     */
    protected org.openuss.documents.FolderEntryDao getFolderEntryDao()
    {
        return this.folderEntryDao;
    }

    private org.openuss.documents.FolderDao folderDao;

    /**
     * Sets the reference to <code>folder</code>'s DAO.
     */
    public void setFolderDao(org.openuss.documents.FolderDao folderDao)
    {
        this.folderDao = folderDao;
    }

    /**
     * Gets the reference to <code>folder</code>'s DAO.
     */
    protected org.openuss.documents.FolderDao getFolderDao()
    {
        return this.folderDao;
    }

    private org.openuss.documents.FileEntryDao fileEntryDao;

    /**
     * Sets the reference to <code>fileEntry</code>'s DAO.
     */
    public void setFileEntryDao(org.openuss.documents.FileEntryDao fileEntryDao)
    {
        this.fileEntryDao = fileEntryDao;
    }

    /**
     * Gets the reference to <code>fileEntry</code>'s DAO.
     */
    protected org.openuss.documents.FileEntryDao getFileEntryDao()
    {
        return this.fileEntryDao;
    }

    /**
     * @see org.openuss.documents.DocumentService#allFileEntries(java.util.Collection)
     */
    public java.util.List allFileEntries(java.util.Collection entries)
    {
        try
        {
            return this.handleAllFileEntries(entries);
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.allFileEntries(java.util.Collection entries)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #allFileEntries(java.util.Collection)}
      */
    protected abstract java.util.List handleAllFileEntries(java.util.Collection entries)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#getFolder(org.openuss.foundation.DomainObject, org.openuss.documents.FolderInfo)
     */
    public org.openuss.documents.FolderInfo getFolder(org.openuss.foundation.DomainObject owner, org.openuss.documents.FolderInfo folderInfo)
    {
        try
        {
            return this.handleGetFolder(owner, folderInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.getFolder(org.openuss.foundation.DomainObject owner, org.openuss.documents.FolderInfo folderInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getFolder(org.openuss.foundation.DomainObject, org.openuss.documents.FolderInfo)}
      */
    protected abstract org.openuss.documents.FolderInfo handleGetFolder(org.openuss.foundation.DomainObject owner, org.openuss.documents.FolderInfo folderInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#getFolder(org.openuss.foundation.DomainObject)
     */
    public org.openuss.documents.FolderInfo getFolder(org.openuss.foundation.DomainObject domainObject)
    {
        try
        {
            return this.handleGetFolder(domainObject);
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.getFolder(org.openuss.foundation.DomainObject domainObject)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getFolder(org.openuss.foundation.DomainObject)}
      */
    protected abstract org.openuss.documents.FolderInfo handleGetFolder(org.openuss.foundation.DomainObject domainObject)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#getFolder(org.openuss.documents.FolderInfo)
     */
    public org.openuss.documents.FolderInfo getFolder(org.openuss.documents.FolderInfo folder)
    {
        try
        {
            return this.handleGetFolder(folder);
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.getFolder(org.openuss.documents.FolderInfo folder)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getFolder(org.openuss.documents.FolderInfo)}
      */
    protected abstract org.openuss.documents.FolderInfo handleGetFolder(org.openuss.documents.FolderInfo folder)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#getFolder(org.openuss.documents.FolderEntryInfo)
     */
    public org.openuss.documents.FolderInfo getFolder(org.openuss.documents.FolderEntryInfo folder)
    {
        try
        {
            return this.handleGetFolder(folder);
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.getFolder(org.openuss.documents.FolderEntryInfo folder)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getFolder(org.openuss.documents.FolderEntryInfo)}
      */
    protected abstract org.openuss.documents.FolderInfo handleGetFolder(org.openuss.documents.FolderEntryInfo folder)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#getFolderEntries(org.openuss.foundation.DomainObject, org.openuss.documents.FolderInfo)
     */
    public java.util.List getFolderEntries(org.openuss.foundation.DomainObject domainObject, org.openuss.documents.FolderInfo folder)
    {
        try
        {
            return this.handleGetFolderEntries(domainObject, folder);
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.getFolderEntries(org.openuss.foundation.DomainObject domainObject, org.openuss.documents.FolderInfo folder)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getFolderEntries(org.openuss.foundation.DomainObject, org.openuss.documents.FolderInfo)}
      */
    protected abstract java.util.List handleGetFolderEntries(org.openuss.foundation.DomainObject domainObject, org.openuss.documents.FolderInfo folder)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#getFolderPath(org.openuss.documents.FolderInfo)
     */
    public java.util.List getFolderPath(org.openuss.documents.FolderInfo folder)
    {
        try
        {
            return this.handleGetFolderPath(folder);
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.getFolderPath(org.openuss.documents.FolderInfo folder)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getFolderPath(org.openuss.documents.FolderInfo)}
      */
    protected abstract java.util.List handleGetFolderPath(org.openuss.documents.FolderInfo folder)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#getFileEntry(java.lang.Long, boolean)
     */
    public org.openuss.documents.FileInfo getFileEntry(java.lang.Long fileId, boolean withInputStream)
    {
        try
        {
            return this.handleGetFileEntry(fileId, withInputStream);
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.getFileEntry(java.lang.Long fileId, boolean withInputStream)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getFileEntry(java.lang.Long, boolean)}
      */
    protected abstract org.openuss.documents.FileInfo handleGetFileEntry(java.lang.Long fileId, boolean withInputStream)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#getFileEntries(org.openuss.foundation.DomainObject)
     */
    public java.util.List getFileEntries(org.openuss.foundation.DomainObject domainObject)
    {
        try
        {
            return this.handleGetFileEntries(domainObject);
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.getFileEntries(org.openuss.foundation.DomainObject domainObject)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getFileEntries(org.openuss.foundation.DomainObject)}
      */
    protected abstract java.util.List handleGetFileEntries(org.openuss.foundation.DomainObject domainObject)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#isFolderOfDomainObject(org.openuss.documents.FolderInfo, org.openuss.foundation.DomainObject)
     */
    public boolean isFolderOfDomainObject(org.openuss.documents.FolderInfo folderInfo, org.openuss.foundation.DomainObject domainObject)
    {
        try
        {
            return this.handleIsFolderOfDomainObject(folderInfo, domainObject);
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.isFolderOfDomainObject(org.openuss.documents.FolderInfo folderInfo, org.openuss.foundation.DomainObject domainObject)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #isFolderOfDomainObject(org.openuss.documents.FolderInfo, org.openuss.foundation.DomainObject)}
      */
    protected abstract boolean handleIsFolderOfDomainObject(org.openuss.documents.FolderInfo folderInfo, org.openuss.foundation.DomainObject domainObject)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#getParentFolder(org.openuss.documents.FileInfo)
     */
    public org.openuss.documents.FolderInfo getParentFolder(org.openuss.documents.FileInfo file)
    {
        try
        {
            return this.handleGetParentFolder(file);
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.getParentFolder(org.openuss.documents.FileInfo file)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getParentFolder(org.openuss.documents.FileInfo)}
      */
    protected abstract org.openuss.documents.FolderInfo handleGetParentFolder(org.openuss.documents.FileInfo file)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#getAllSubfolders(org.openuss.foundation.DomainObject)
     */
    public java.util.List getAllSubfolders(org.openuss.foundation.DomainObject domainObject)
    {
        try
        {
            return this.handleGetAllSubfolders(domainObject);
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.getAllSubfolders(org.openuss.foundation.DomainObject domainObject)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getAllSubfolders(org.openuss.foundation.DomainObject)}
      */
    protected abstract java.util.List handleGetAllSubfolders(org.openuss.foundation.DomainObject domainObject)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#createFileEntries(java.util.Collection, org.openuss.documents.FolderInfo)
     */
    public void createFileEntries(java.util.Collection fileEntries, org.openuss.documents.FolderInfo parentInfo)
        throws org.openuss.documents.DocumentApplicationException
    {
        try
        {
            this.handleCreateFileEntries(fileEntries, parentInfo);
        }
        catch (org.openuss.documents.DocumentApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.createFileEntries(java.util.Collection fileEntries, org.openuss.documents.FolderInfo parentInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createFileEntries(java.util.Collection, org.openuss.documents.FolderInfo)}
      */
    protected abstract void handleCreateFileEntries(java.util.Collection fileEntries, org.openuss.documents.FolderInfo parentInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#createFolder(org.openuss.documents.FolderInfo, org.openuss.documents.FolderInfo)
     */
    public void createFolder(org.openuss.documents.FolderInfo folder, org.openuss.documents.FolderInfo parent)
        throws org.openuss.documents.DocumentApplicationException
    {
        try
        {
            this.handleCreateFolder(folder, parent);
        }
        catch (org.openuss.documents.DocumentApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.createFolder(org.openuss.documents.FolderInfo folder, org.openuss.documents.FolderInfo parent)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createFolder(org.openuss.documents.FolderInfo, org.openuss.documents.FolderInfo)}
      */
    protected abstract void handleCreateFolder(org.openuss.documents.FolderInfo folder, org.openuss.documents.FolderInfo parent)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#createFileEntry(org.openuss.documents.FileInfo, org.openuss.documents.FolderInfo)
     */
    public void createFileEntry(org.openuss.documents.FileInfo fileInfo, org.openuss.documents.FolderInfo parent)
        throws org.openuss.documents.DocumentApplicationException
    {
        try
        {
            this.handleCreateFileEntry(fileInfo, parent);
        }
        catch (org.openuss.documents.DocumentApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.createFileEntry(org.openuss.documents.FileInfo fileInfo, org.openuss.documents.FolderInfo parent)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createFileEntry(org.openuss.documents.FileInfo, org.openuss.documents.FolderInfo)}
      */
    protected abstract void handleCreateFileEntry(org.openuss.documents.FileInfo fileInfo, org.openuss.documents.FolderInfo parent)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#diffSave(org.openuss.foundation.DomainObject, java.util.List)
     */
    public void diffSave(org.openuss.foundation.DomainObject domainObject, java.util.List fileInfos)
    {
        try
        {
            this.handleDiffSave(domainObject, fileInfos);
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.diffSave(org.openuss.foundation.DomainObject domainObject, java.util.List fileInfos)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #diffSave(org.openuss.foundation.DomainObject, java.util.List)}
      */
    protected abstract void handleDiffSave(org.openuss.foundation.DomainObject domainObject, java.util.List fileInfos)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#moveFolderEntries(org.openuss.foundation.DomainObject, org.openuss.documents.FolderInfo, java.util.List)
     */
    public void moveFolderEntries(org.openuss.foundation.DomainObject domainObject, org.openuss.documents.FolderInfo target, java.util.List chosenObjects)
        throws org.openuss.documents.DocumentApplicationException
    {
        try
        {
            this.handleMoveFolderEntries(domainObject, target, chosenObjects);
        }
        catch (org.openuss.documents.DocumentApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.moveFolderEntries(org.openuss.foundation.DomainObject domainObject, org.openuss.documents.FolderInfo target, java.util.List chosenObjects)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #moveFolderEntries(org.openuss.foundation.DomainObject, org.openuss.documents.FolderInfo, java.util.List)}
      */
    protected abstract void handleMoveFolderEntries(org.openuss.foundation.DomainObject domainObject, org.openuss.documents.FolderInfo target, java.util.List chosenObjects)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#removeFolderEntry(java.lang.Long)
     */
    public void removeFolderEntry(java.lang.Long folderEntryId)
        throws org.openuss.documents.DocumentApplicationException
    {
        try
        {
            this.handleRemoveFolderEntry(folderEntryId);
        }
        catch (org.openuss.documents.DocumentApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.removeFolderEntry(java.lang.Long folderEntryId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeFolderEntry(java.lang.Long)}
      */
    protected abstract void handleRemoveFolderEntry(java.lang.Long folderEntryId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#removeFileEntries(java.util.Collection)
     */
    public void removeFileEntries(java.util.Collection entries)
        throws org.openuss.documents.DocumentApplicationException
    {
        try
        {
            this.handleRemoveFileEntries(entries);
        }
        catch (org.openuss.documents.DocumentApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.removeFileEntries(java.util.Collection entries)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeFileEntries(java.util.Collection)}
      */
    protected abstract void handleRemoveFileEntries(java.util.Collection entries)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#removeFolderEntries(java.util.Collection)
     */
    public void removeFolderEntries(java.util.Collection folderEntries)
        throws org.openuss.documents.DocumentApplicationException
    {
        try
        {
            this.handleRemoveFolderEntries(folderEntries);
        }
        catch (org.openuss.documents.DocumentApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.removeFolderEntries(java.util.Collection folderEntries)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeFolderEntries(java.util.Collection)}
      */
    protected abstract void handleRemoveFolderEntries(java.util.Collection folderEntries)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#remove(org.openuss.foundation.DomainObject)
     */
    public void remove(org.openuss.foundation.DomainObject domainObject)
    {
        try
        {
            this.handleRemove(domainObject);
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.remove(org.openuss.foundation.DomainObject domainObject)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #remove(org.openuss.foundation.DomainObject)}
      */
    protected abstract void handleRemove(org.openuss.foundation.DomainObject domainObject)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#saveFolder(org.openuss.documents.FolderInfo)
     */
    public void saveFolder(org.openuss.documents.FolderInfo folder)
        throws org.openuss.documents.DocumentApplicationException
    {
        try
        {
            this.handleSaveFolder(folder);
        }
        catch (org.openuss.documents.DocumentApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.saveFolder(org.openuss.documents.FolderInfo folder)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #saveFolder(org.openuss.documents.FolderInfo)}
      */
    protected abstract void handleSaveFolder(org.openuss.documents.FolderInfo folder)
        throws java.lang.Exception;

    /**
     * @see org.openuss.documents.DocumentService#saveFileEntry(org.openuss.documents.FileInfo)
     */
    public void saveFileEntry(org.openuss.documents.FileInfo fileInfo)
        throws org.openuss.documents.DocumentApplicationException
    {
        try
        {
            this.handleSaveFileEntry(fileInfo);
        }
        catch (org.openuss.documents.DocumentApplicationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.documents.DocumentServiceException(
                "Error performing 'org.openuss.documents.DocumentService.saveFileEntry(org.openuss.documents.FileInfo fileInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #saveFileEntry(org.openuss.documents.FileInfo)}
      */
    protected abstract void handleSaveFileEntry(org.openuss.documents.FileInfo fileInfo)
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