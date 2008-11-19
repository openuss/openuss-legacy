package org.openuss.documents;

import java.util.Collection;
import java.util.List;

import org.openuss.foundation.DomainObject;

/**
 * DocumentService is the central service for file / folder hierarchie
 * management
 * 
 * @author Ingo Dueppe
 */
public interface DocumentService {

	/**
	 * Collects recursively all file entries of the selected entries as a flat
	 * list. Selected folders will be scanned to retrieve the containing files
	 * 
	 * @param List
	 *            of FileEntryInfo objects
	 */
	public List allFileEntries(Collection entries);

	/**
	 * Retrieve a fresh folder info object. If folder is null the root folder of
	 * domain object will be returned. If folder is not <code>null</code> the
	 * specified folder will be returned if it belongs to the domain object. If
	 * the defined folder isn't the root or a subfolder of the domain object the
	 * method will return the root folder of the domain object.
	 */
	public FolderInfo getFolder(DomainObject owner, FolderInfo folderInfo);

	/**
	 * Retrieve a root foldeInfo according to a domainObject.
	 * 
	 * @param domainObject
	 *            - object with id property
	 */
	public FolderInfo getFolder(DomainObject domainObject);

	public FolderInfo getFolder(FolderInfo folder);

	public FolderInfo getFolder(FolderEntryInfo folder);

	/**
	 * Returns the folderentries of the requested folder. if the folder
	 * parameter is null it will return the root folder of the domain object
	 * associated folders.
	 * 
	 * @Param object with a getId() method
	 * @Param optional parameter
	 */
	public List getFolderEntries(DomainObject domainObject, FolderInfo folder);

	/**
	 * The folder ancestor list beginning at the root folder.
	 * 
	 * @return List<FolderInfo>
	 */
	public List getFolderPath(FolderInfo folder);

	/**
	 * Retrieve a FileInfo object for the given FolderEntryInfo file. If the
	 * fileinfo object contains an open InputStream to the file content can be
	 * defined with the second parameter.
	 * 
	 * @param folderEntryInfo
	 *            object of the file entry
	 * @param withInputStream
	 *            if true the fileinfo object will contain an open input stream
	 * @return FileInfo object
	 */
	public FileInfo getFileEntry(Long fileId, boolean withInputStream);

	/**
	 * A list of all file entries in the root folder of the domain object.
	 * 
	 * @return List of FileInfo objects
	 */
	public List getFileEntries(DomainObject domainObject);

	public boolean isFolderOfDomainObject(FolderInfo folderInfo, DomainObject domainObject);

	public FolderInfo getParentFolder(FileInfo file);

	public List getAllSubfolders(DomainObject domainObject);

	/**
	 * Creates folder and file entries from a collection containing
	 * folderEntries
	 */
	public void createFileEntries(Collection fileEntries, FolderInfo parentInfo) throws DocumentApplicationException;

	/**
	 * Creates a new sub folder under the given parent folder
	 * 
	 * @param folder
	 *            the new subfolder
	 * @param parent
	 *            the folder that will contain the new subfolder
	 */
	public void createFolder(FolderInfo folder, FolderInfo parent) throws DocumentApplicationException;

	public void createFileEntry(FileInfo fileInfo, FolderInfo parent) throws DocumentApplicationException;

	/**
	 * This method will save only the difference of the files list to the
	 * current root folder. All existing files within the root folder that are
	 * not part of the files list will be deleted. All files within the files
	 * list that doesn't exist within the root folder will be saved. After this
	 * operation the folder just contain the files discribed in the list.
	 * 
	 * @param domainObject
	 * @param files
	 *            - List of FileInfo objects
	 */
	public void diffSave(DomainObject domainObject, List fileInfos);

	public void moveFolderEntries(DomainObject domainObject, FolderInfo target, List chosenObjects)
			throws DocumentApplicationException;

	public void removeFolderEntry(Long folderEntryId) throws DocumentApplicationException;

	/**
	 * Removing a list of file entries.
	 * 
	 * @param entries
	 *            - collection of FileInfo objects
	 */
	public void removeFileEntries(Collection entries) throws DocumentApplicationException;

	public void removeFolderEntries(Collection folderEntries) throws DocumentApplicationException;

	public void remove(DomainObject domainObject);

	public void saveFolder(FolderInfo folder) throws DocumentApplicationException;

	public void saveFileEntry(FileInfo fileInfo) throws DocumentApplicationException;

}
