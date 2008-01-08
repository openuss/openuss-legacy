// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

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
	public String getFileName() {
		File file = new File(super.getFileName());
		return file.getName();
	}
	
	@Override
	public String getExtension() {
		String fileName = getFileName();
		if (fileName != null) {
			int index = fileName.lastIndexOf('.');
			return fileName.substring(index+1);
		} else {
			return "";
		}
	}

	@Override
	public String getName() {
		String name = super.getName();
		if (StringUtils.isBlank(name)) {
			name = getFileName();
			if (name != null) {
				int index = name.lastIndexOf('.');
				if (index > 0) {
					name = name.substring(0, index);
				}
			}
		}
		return name;
	}
	
	@Override
	public boolean isReleased() {
		return !(new Date().before(getCreated()));
	}

	@Override
	public Date releaseDate() {
		return getCreated();
	}

	@Override
	public Date getModified() {
		Date modified = super.getModified();
		if (modified == null || modified.before(getCreated())) {
			modified = getCreated();
		}
		return modified;
	}

}