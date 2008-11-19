package org.openuss.documents;

/**
 * 
 */
public abstract class FolderBase
    extends org.openuss.documents.FolderEntryImpl
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -7199532373420674011L;

    private java.lang.Long domainIdentifier;

    /**
     * @see org.openuss.documents.Folder#getDomainIdentifier()
     */
    public java.lang.Long getDomainIdentifier()
    {
        return this.domainIdentifier;
    }

    /**
     * @see org.openuss.documents.Folder#setDomainIdentifier(java.lang.Long domainIdentifier)
     */
    public void setDomainIdentifier(java.lang.Long domainIdentifier)
    {
        this.domainIdentifier = domainIdentifier;
    }

    private java.util.List<org.openuss.documents.FolderEntry> entries = new java.util.ArrayList<org.openuss.documents.FolderEntry>();

    /**
     * 
     */
    public java.util.List<org.openuss.documents.FolderEntry> getEntries()
    {
        return this.entries;
    }

    public void setEntries(java.util.List<org.openuss.documents.FolderEntry> entries)
    {
        this.entries = entries;
    }

    /**
     * 
     */
    public abstract boolean isRoot();

    /**
     * <p>
     * Adds a folder entry to this folder.
     * </p>
     * <p>
     * @throws DocumentApplicationException - if entry not unique
     * within the folder
     * </p>
     */
    public abstract void addFolderEntry(org.openuss.documents.FolderEntry entry)
        throws org.openuss.documents.DocumentApplicationException;

    /**
     * 
     */
    public abstract void removeFolderEntry(org.openuss.documents.FolderEntry entry);

    /**
     * 
     */
    public abstract org.openuss.documents.FolderEntry getFolderEntryByName(java.lang.String name);

    /**
     * <p>
     * Checks if the entry has a unique filename within the folder.
     * </p>
     * <p>
     * @return true - if entry is unique within the folder
     * </p>
     */
    public abstract boolean canAdd(org.openuss.documents.FolderEntry entry);

    /**
     * 
     */
    public abstract void moveHere(org.openuss.documents.FolderEntry entry)
        throws org.openuss.documents.DocumentApplicationException;

    /**
     * 
     */
    public abstract java.util.List getAllSubfolders();

    /**
     * 
     */
    public abstract boolean correctName(org.openuss.documents.FolderEntry entry);

    /**
     * 
     */
    public abstract boolean correctHierarchy(org.openuss.documents.FolderEntry entry);

    /**
     * This entity does not have any identifiers
     * but since it extends the <code>org.openuss.documents.FolderEntryImpl</code> class
     * it will simply delegate the call up there.
     *
     * @see org.openuss.documents.FolderEntry#equals(Object)
     */
    public boolean equals(Object object)
    {
        return super.equals(object);
    }

    /**
     * This entity does not have any identifiers
     * but since it extends the <code>org.openuss.documents.FolderEntryImpl</code> class
     * it will simply delegate the call up there.
     *
     * @see org.openuss.documents.FolderEntry#hashCode()
     */
    public int hashCode()
    {
        return super.hashCode();
    }

}