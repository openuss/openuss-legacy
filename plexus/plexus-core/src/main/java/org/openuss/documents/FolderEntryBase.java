package org.openuss.documents;

/**
 * <p>
 * The size of the folder entry in a human-readable format with
 * kilobytes, megabytes or gigabytes.
 * </p>
 */
public abstract class FolderEntryBase
    implements FolderEntry, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -35034567844998677L;

    private java.lang.Long id;

    /**
     * @see org.openuss.documents.FolderEntry#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.documents.FolderEntry#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.String name;

    /**
     * @see org.openuss.documents.FolderEntry#getName()
     */
    public java.lang.String getName()
    {
        return this.name;
    }

    /**
     * @see org.openuss.documents.FolderEntry#setName(java.lang.String name)
     */
    public void setName(java.lang.String name)
    {
        this.name = name;
    }

    private java.util.Date created;

    /**
     * @see org.openuss.documents.FolderEntry#getCreated()
     */
    public java.util.Date getCreated()
    {
        return this.created;
    }

    /**
     * @see org.openuss.documents.FolderEntry#setCreated(java.util.Date created)
     */
    public void setCreated(java.util.Date created)
    {
        this.created = created;
    }

    private java.lang.String description;

    /**
     * @see org.openuss.documents.FolderEntry#getDescription()
     */
    public java.lang.String getDescription()
    {
        return this.description;
    }

    /**
     * @see org.openuss.documents.FolderEntry#setDescription(java.lang.String description)
     */
    public void setDescription(java.lang.String description)
    {
        this.description = description;
    }

    private org.openuss.documents.Folder parent;

    /**
     * 
     */
    public org.openuss.documents.Folder getParent()
    {
        return this.parent;
    }

    public void setParent(org.openuss.documents.Folder parent)
    {
        this.parent = parent;
    }

    /**
     * <p>
     * @return String name of the file with full path
     * </p>
     */
    public abstract java.lang.String getAbsoluteName();

    /**
     * 
     */
    public abstract java.lang.String getFileName();

    /**
     * 
     */
    public abstract java.lang.String getExtension();

    /**
     * 
     */
    public abstract java.util.Date getModified();

    /**
     * 
     */
    public abstract java.lang.Integer getFileSize();

    /**
     * 
     */
    public abstract java.lang.String getPath();

    /**
     * 
     */
    public abstract java.lang.String getSizeAsString();

    /**
     * 
     */
    public abstract boolean isReleased();

    /**
     * 
     */
    public abstract java.util.Date releaseDate();

    /**
     * Returns <code>true</code> if the argument is an FolderEntry instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof FolderEntry))
        {
            return false;
        }
        final FolderEntry that = (FolderEntry)object;
        if (this.id == null || that.getId() == null || !this.id.equals(that.getId()))
        {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code based on this entity's identifiers.
     */
    public int hashCode()
    {
        int hashCode = 0;
        hashCode = 29 * hashCode + (id == null ? 0 : id.hashCode());

        return hashCode;
    }

}