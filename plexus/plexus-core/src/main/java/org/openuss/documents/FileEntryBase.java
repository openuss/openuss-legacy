package org.openuss.documents;

/**
 * 
 */
public abstract class FileEntryBase
    extends org.openuss.documents.FolderEntryImpl
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -6678923714822612007L;

    private java.lang.String contentType;

    /**
     * @see org.openuss.documents.FileEntry#getContentType()
     */
    public java.lang.String getContentType()
    {
        return this.contentType;
    }

    /**
     * @see org.openuss.documents.FileEntry#setContentType(java.lang.String contentType)
     */
    public void setContentType(java.lang.String contentType)
    {
        this.contentType = contentType;
    }

    private java.lang.String fileName;

    /**
     * @see org.openuss.documents.FileEntry#getFileName()
     */
    public java.lang.String getFileName()
    {
        return this.fileName;
    }

    /**
     * @see org.openuss.documents.FileEntry#setFileName(java.lang.String fileName)
     */
    public void setFileName(java.lang.String fileName)
    {
        this.fileName = fileName;
    }

    private java.lang.Integer fileSize;

    /**
     * @see org.openuss.documents.FileEntry#getFileSize()
     */
    public java.lang.Integer getFileSize()
    {
        return this.fileSize;
    }

    /**
     * @see org.openuss.documents.FileEntry#setFileSize(java.lang.Integer fileSize)
     */
    public void setFileSize(java.lang.Integer fileSize)
    {
        this.fileSize = fileSize;
    }

    private java.util.Date modified;

    /**
     * @see org.openuss.documents.FileEntry#getModified()
     */
    public java.util.Date getModified()
    {
        return this.modified;
    }

    /**
     * @see org.openuss.documents.FileEntry#setModified(java.util.Date modified)
     */
    public void setModified(java.util.Date modified)
    {
        this.modified = modified;
    }

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