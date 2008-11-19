package org.openuss.documents;

import java.util.Date;

/**
 * The size of the folder entry in a human-readable format with kilobytes,
 * megabytes or gigabytes.
 */
public interface FolderEntry extends org.openuss.foundation.DomainObject {

	public Long getId();

	public void setId(Long id);

	public String getName();

	public void setName(String name);

	/**
	 * The point of time the file has been created.
	 */
	public Date getCreated();

	public void setCreated(Date created);

	public String getDescription();

	public void setDescription(String description);

	public org.openuss.documents.Folder getParent();

	public void setParent(org.openuss.documents.Folder parent);

	/**
	 * @return String name of the file with full path
	 */
	public abstract String getAbsoluteName();

	public abstract String getFileName();

	public abstract String getExtension();

	public abstract Date getModified();

	public abstract Integer getFileSize();

	public abstract String getPath();

	public abstract String getSizeAsString();

	public abstract boolean isReleased();

	public abstract Date releaseDate();

}