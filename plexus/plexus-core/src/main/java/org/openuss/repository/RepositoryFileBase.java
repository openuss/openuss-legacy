package org.openuss.repository;

/**
 * Represents the
 */
public abstract class RepositoryFileBase implements RepositoryFile, java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -8786908614262595476L;

	private java.lang.Long id;

	/**
	 * @see org.openuss.repository.RepositoryFile#getId()
	 */
	public java.lang.Long getId() {
		return this.id;
	}

	/**
	 * @see org.openuss.repository.RepositoryFile#setId(java.lang.Long id)
	 */
	public void setId(java.lang.Long id) {
		this.id = id;
	}

	private java.util.Date modified;

	/**
	 * @see org.openuss.repository.RepositoryFile#getModified()
	 */
	public java.util.Date getModified() {
		return this.modified;
	}

	/**
	 * @see org.openuss.repository.RepositoryFile#setModified(java.util.Date
	 *      modified)
	 */
	public void setModified(java.util.Date modified) {
		this.modified = modified;
	}

	private java.sql.Blob content;

	/**
	 * @see org.openuss.repository.RepositoryFile#getContent()
	 */
	public java.sql.Blob getContent() {
		return this.content;
	}

	/**
	 * @see org.openuss.repository.RepositoryFile#setContent(java.sql.Blob
	 *      content)
	 */
	public void setContent(java.sql.Blob content) {
		this.content = content;
	}

	private java.lang.Long downloads = java.lang.Long.valueOf(0);

	/**
	 * @see org.openuss.repository.RepositoryFile#getDownloads()
	 */
	public java.lang.Long getDownloads() {
		return this.downloads;
	}

	/**
	 * @see org.openuss.repository.RepositoryFile#setDownloads(java.lang.Long
	 *      downloads)
	 */
	public void setDownloads(java.lang.Long downloads) {
		this.downloads = downloads;
	}

	/**
	 * Gets InputStream to the content of the file.
	 */
	public abstract java.io.InputStream getInputStream();

	/**
	 * Sets the input stream to the file content.
	 */
	public abstract void setInputStream(java.io.InputStream InputStream);

	/**
     * 
     */
	public abstract void increaseDownloads();

	/**
	 * Returns <code>true</code> if the argument is an RepositoryFile instance
	 * and all identifiers for this entity equal the identifiers of the argument
	 * entity. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof RepositoryFile)) {
			return false;
		}
		final RepositoryFile that = (RepositoryFile) object;
		if (this.id == null || that.getId() == null || !this.id.equals(that.getId())) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a hash code based on this entity's identifiers.
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode = 29 * hashCode + (id == null ? 0 : id.hashCode());

		return hashCode;
	}

}