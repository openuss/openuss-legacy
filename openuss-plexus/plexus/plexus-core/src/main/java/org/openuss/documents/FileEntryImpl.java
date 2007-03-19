// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

import java.util.Date;

/**
 * @see org.openuss.documents.FileEntry
 * author ingo dueppe
 */
public class FileEntryImpl extends org.openuss.documents.FileEntryBase implements org.openuss.documents.FileEntry {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 3992343940899917381L;

	@Override
	public Date getCreated() {
		return (getRepositoryFile() == null) ? null : getRepositoryFile().getCreated();
	}

	@Override
	public String getDescription() {
		return (getRepositoryFile() == null) ? null : getRepositoryFile().getDescription();
	}

	@Override
	public String getExtension() {
		return (getRepositoryFile() == null) ? null : getRepositoryFile().getExtension();
	}

	@Override
	public Date getModified() {
		return (getRepositoryFile() == null) ? null : getRepositoryFile().getModified();
	}

	@Override
	public String getName() {
		return (getRepositoryFile() == null) ? null : getRepositoryFile().getName();
	}

	@Override
	public Integer getSize() { 
		return (getRepositoryFile() == null) ? null : getRepositoryFile().getFileSize();
	}

	@Override
	public boolean isReleased() {
		return new Date().after(getCreated());
	}

	@Override
	public Date releaseDate() {
		return getCreated();
	}
}