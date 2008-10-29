package org.openuss.documents;

import java.util.List;

import org.openuss.foundation.DomainObject;

/**
 * @author Ingo Dueppe
 */
public interface Folder extends FolderEntry, DomainObject {

	/**
	 * Defines the id of domain object this folder is associated to. I. e. the
	 * id of an course or of a institute.
	 */
	public Long getDomainIdentifier();

	public void setDomainIdentifier(Long domainIdentifier);

	public List<FolderEntry> getEntries();

	public void setEntries(List<FolderEntry> entries);

	public abstract boolean isRoot();

	/**
	 * Adds a folder entry to this folder.
	 * 
	 * @throws DocumentApplicationException
	 *             - if entry not unique within the folder
	 */
	public abstract void addFolderEntry(FolderEntry entry)
			throws DocumentApplicationException;

	public void removeFolderEntry(FolderEntry entry);

	public FolderEntry getFolderEntryByName(String name);

	/**
	 * Checks if the entry has a unique filename within the folder.
	 * 
	 * @return true - if entry is unique within the folder
	 */
	public abstract boolean canAdd(FolderEntry entry);

	public abstract void moveHere(FolderEntry entry)
			throws DocumentApplicationException;

	public abstract List getAllSubfolders();

	public abstract boolean correctName(FolderEntry entry);

	public abstract boolean correctHierarchy(FolderEntry entry);

}