// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @see org.openuss.documents.Folder
 * @author ingo dueppe
 */
public class FolderImpl extends org.openuss.documents.FolderBase implements org.openuss.documents.Folder {

	private static final long serialVersionUID = 8286059784837095960L;
	
	/**
	 * @see org.openuss.documents.Folder#addFolderEntry(org.openuss.documents.FolderEntry)
	 */
	public void addFolderEntry(org.openuss.documents.FolderEntry entry) throws DocumentApplicationException {
		if (!canAdd(entry)){
			//TODO Throw different Exceptions for Wrong Name and Destroyed Hierarchy
			throw new DocumentApplicationException("documents_folder_not_a_unique_filename");
		}
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
	public boolean canAdd(FolderEntry folderEntry) {
		return correctName(folderEntry) && correctHierarchy(folderEntry);
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
	public Integer getFileSize() {
		int size = 0;
		// TODO ATTENTION - Don't know what the performance this will be! - it's recursivly
		for (FolderEntry entry : getEntries()) {
			size += entry.getFileSize();
		}
		return size;
	}

	@Override
	public boolean correctHierarchy(FolderEntry entry) {
		// TODO Auto-generated method stub
		// TODO Implement check for correct Hierarchy after moving!
		if(this.equals(entry) && entry instanceof Folder){
			return false; //Hierarchy would get destroyed.
		}
		return true; //Hierarchy stays correct
	}

	@Override
	public void moveHere(FolderEntry entry) throws DocumentApplicationException {
		System.out.println("Vater: " + entry.getId());
		entry.getParent().removeFolderEntry(entry);
		this.addFolderEntry(entry);
	}

	@Override
	public boolean correctName(FolderEntry entry) {
		for (FolderEntry folderEntry : getEntries()) {
			if (StringUtils.equalsIgnoreCase(entry.getFileName(), folderEntry.getFileName()) && !ObjectUtils.equals(folderEntry, entry)) {
				return false; // not valid
			}
		}
		return true; // valid
	}

}