// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @see org.openuss.documents.Folder
 * @author ingo dueppe
 */
public class FolderImpl extends org.openuss.documents.FolderBase implements org.openuss.documents.Folder {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 8286059784837095960L;
	
	public FolderImpl() {
		setName("");
		setCreated(new Date());
	}

	/**
	 * @see org.openuss.documents.Folder#addFolderEntry(org.openuss.documents.FolderEntry)
	 */
	public void addFolderEntry(org.openuss.documents.FolderEntry entry) {
		if (getEntries() != null && entry != null) {
			getEntries().add(entry);
			entry.setParent(this);
		}
	}

	/**
	 * @see org.openuss.documents.Folder#isRoot()
	 */
	public boolean isRoot() {
		return getParent() == null;
	}

	@Override
	public boolean isValidSubfolderName(String name) {
		for (FolderEntry entry : getEntries()) {
			if (StringUtils.equalsIgnoreCase(name, entry.getName())) {
				return false; // not valid
			}
		}
		return true; // vaild
	}
	
	@Override
	public FolderEntry getFolderEntryByName(String name) {
		for (FolderEntry entry : getEntries()) {
			if (StringUtils.equalsIgnoreCase(name, entry.getName())) {
				return entry;
			}
		}
		return null;
	}

	@Override
	public void removeFolderEntry(FolderEntry entry) {
		if (getEntries() != null && entry != null) {
			getEntries().remove(entry);
			if (entry.getParent() == this) {
				entry.setParent(null);
			}
		}	
	}
	
	@Override
	public Date getModified() {
		Date modified = getCreated();
		// TODO ATTENTION - Don't know what the performance this will be! - it's recursivly
		for (FolderEntry entry : getEntries()) {
			if (modified.before(entry.getModified())) {
				modified = entry.getModified();
			}
		}
		return modified;
	}

	@Override
	public String getExtension() {
		return "folder";
	}

	@Override
	public Integer getSize() {
		int size = 0;
		// TODO ATTENTION - Don't know what the performance this will be! - it's recursivly
		for (FolderEntry entry : getEntries()) {
			size += entry.getSize();
		}
		return size;
	}
}