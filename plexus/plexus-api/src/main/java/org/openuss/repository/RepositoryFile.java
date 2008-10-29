package org.openuss.repository;

import java.util.Date;

/**
 * Represents the
 */
public interface RepositoryFile extends org.openuss.foundation.DomainObject {

	public Long getId();

	public void setId(Long id);

	/**
	 * The point of time the file has been modified.
	 */
	public Date getModified();

	public void setModified(Date modified);

	public java.sql.Blob getContent();

	public void setContent(java.sql.Blob content);

	/**
	 * Counts the number of downloads of each file.
	 */
	public Long getDownloads();

	public void setDownloads(Long downloads);

	/**
	 * Gets InputStream to the content of the file.
	 */
	public abstract java.io.InputStream getInputStream();

	/**
	 * Sets the input stream to the file content.
	 */
	public abstract void setInputStream(java.io.InputStream InputStream);

	public abstract void increaseDownloads();

}