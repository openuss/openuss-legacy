// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;
/**
 * @see org.openuss.documents.Folder
 * @author ingo dueppe
 */
public class FolderDaoImpl
    extends org.openuss.documents.FolderDaoBase
{
    /**
     * @see org.openuss.documents.FolderDao#toFolderInfo(org.openuss.documents.Folder, org.openuss.documents.FolderInfo)
     */
    public void toFolderInfo(
        org.openuss.documents.Folder sourceEntity,
        org.openuss.documents.FolderInfo targetVO)
    {
        // @todo verify behavior of toFolderInfo
        super.toFolderInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.documents.FolderDao#toFolderInfo(org.openuss.documents.Folder)
     */
    public org.openuss.documents.FolderInfo toFolderInfo(final org.openuss.documents.Folder entity)
    {
        // @todo verify behavior of toFolderInfo
        return super.toFolderInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.documents.Folder loadFolderFromFolderInfo(org.openuss.documents.FolderInfo folderInfo)
    {
        // @todo implement loadFolderFromFolderInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.documents.loadFolderFromFolderInfo(org.openuss.documents.FolderInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.documents.Folder folder = this.load(folderInfo.getId());
        if (folder == null)
        {
            folder = org.openuss.documents.Folder.Factory.newInstance();
        }
        return folder;
        */
    }

    
    /**
     * @see org.openuss.documents.FolderDao#folderInfoToEntity(org.openuss.documents.FolderInfo)
     */
    public org.openuss.documents.Folder folderInfoToEntity(org.openuss.documents.FolderInfo folderInfo)
    {
        // @todo verify behavior of folderInfoToEntity
        org.openuss.documents.Folder entity = this.loadFolderFromFolderInfo(folderInfo);
        this.folderInfoToEntity(folderInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.documents.FolderDao#folderInfoToEntity(org.openuss.documents.FolderInfo, org.openuss.documents.Folder)
     */
    public void folderInfoToEntity(
        org.openuss.documents.FolderInfo sourceVO,
        org.openuss.documents.Folder targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of folderInfoToEntity
        super.folderInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

    /**
     * @see org.openuss.documents.FolderDao#toFolderEntryInfo(org.openuss.documents.Folder, org.openuss.documents.FolderEntryInfo)
     */
    public void toFolderEntryInfo(
        org.openuss.documents.Folder sourceEntity,
        org.openuss.documents.FolderEntryInfo targetVO)
    {
        // @todo verify behavior of toFolderEntryInfo
        super.toFolderEntryInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.documents.FolderDao#toFolderEntryInfo(org.openuss.documents.Folder)
     */
    public org.openuss.documents.FolderEntryInfo toFolderEntryInfo(final org.openuss.documents.Folder entity)
    {
        // @todo verify behavior of toFolderEntryInfo
        return super.toFolderEntryInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.documents.Folder loadFolderFromFolderEntryInfo(org.openuss.documents.FolderEntryInfo folderEntryInfo)
    {
        // @todo implement loadFolderFromFolderEntryInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.documents.loadFolderFromFolderEntryInfo(org.openuss.documents.FolderEntryInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.documents.Folder folder = this.load(folderEntryInfo.getId());
        if (folder == null)
        {
            folder = org.openuss.documents.Folder.Factory.newInstance();
        }
        return folder;
        */
    }

    
    /**
     * @see org.openuss.documents.FolderDao#folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo)
     */
    public org.openuss.documents.Folder folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo folderEntryInfo)
    {
        // @todo verify behavior of folderEntryInfoToEntity
        org.openuss.documents.Folder entity = this.loadFolderFromFolderEntryInfo(folderEntryInfo);
        this.folderEntryInfoToEntity(folderEntryInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.documents.FolderDao#folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo, org.openuss.documents.Folder)
     */
    public void folderEntryInfoToEntity(
        org.openuss.documents.FolderEntryInfo sourceVO,
        org.openuss.documents.Folder targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of folderEntryInfoToEntity
        super.folderEntryInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}