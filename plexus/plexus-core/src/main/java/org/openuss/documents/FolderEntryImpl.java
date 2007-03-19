// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

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
			String path = getParent().getPath();
			if (StringUtils.isNotBlank(path)) {
				return getParent().getPath()+"/"+getName();
			} else {
				return getName();
			}
		}
	}
	
	@Override
	public String getAbsoluteName() {
		String path = getPath();
		if (StringUtils.isNotBlank(path)) {
			return path + "/" + getName();
		} else {
			return getName();
		}
	}
}