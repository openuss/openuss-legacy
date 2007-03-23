// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

import java.util.Date;

import org.apache.commons.io.FileUtils;

/**
 * @see org.openuss.documents.FolderEntry
 */
public class FolderEntryImpl extends org.openuss.documents.FolderEntryBase implements org.openuss.documents.FolderEntry {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 9108164927807060019L;

	@Override
	public Date getCreated() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public String getExtension() {
		return null;
	}

	@Override
	public Date getModified() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Integer getSize() {
		return null;
	}

	@Override
	public String getPath() {
		if (getParent() == null) {
			return "";
		} else {
			return getParent().getPath()+"/"+getParent().getName();
		}
	}
	
	@Override
	public String getAbsoluteName() {
		return getPath()+"/"+getName();
	}

	@Override
	public String getSizeAsString() {
		Integer size = getSize();
		if (size != null) {
			return FileUtils.byteCountToDisplaySize(size);
		} else {
			return "";
		}
	}

	@Override
	public String getFileName() {
		return getName();
	}
}