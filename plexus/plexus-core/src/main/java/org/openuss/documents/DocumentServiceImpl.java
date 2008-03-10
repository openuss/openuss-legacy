// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.foundation.DomainObject;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @see org.openuss.documents.DocumentsService
 */
public class DocumentServiceImpl extends org.openuss.documents.DocumentServiceBase {

	private static final Logger logger = Logger.getLogger(DocumentServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	protected List<FileInfo> handleAllFileEntries(Collection entries) throws Exception {
		Validate.allElementsOfType(entries, FolderEntryInfo.class,
				"Parameter entries must only contain FolderEntryInfo objects.");
		Collection entities = new ArrayList(entries);
		getFolderEntryDao().folderEntryInfoToEntityCollection(entities);

		List files = new ArrayList();
		collectAllFileEntries(entities, files);
		filterEntriesByPermission(entries);

		getFileEntryDao().toFileInfoCollection(files);

		return files;
	}

	@Override
	protected void handleCreateFolder(FolderInfo folderInfo, FolderInfo parentInfo) throws Exception {
		Validate.notNull(folderInfo, "Parameter folder must not be null!");
		Validate.notNull(parentInfo, "Parameter parent must not be null!");

		Folder folder = getFolderDao().folderInfoToEntity(folderInfo);
		Folder parent = getFolderDao().load(parentInfo.getId());

		createFolder(folder, parent);
		getFolderDao().toFolderInfo(folder, folderInfo);
	}

	@Override
	protected void handleCreateFileEntries(Collection fileEntries, FolderInfo parentInfo) throws Exception {
		Validate.allElementsOfType(fileEntries, FileInfo.class,
				"Parameter fileEntries must only contain FileEntryInfo objects.");
		Validate.notNull(parentInfo, "Parameter parentInfo must not be null.");

		Folder parent = obtainParentFolder(parentInfo);

		for (FileInfo entry : (Collection<FileInfo>) fileEntries) {
			createFileEntryWithContent(entry, parent);
		}
	}

	@Override
	protected void handleCreateFileEntry(FileInfo fileInfo, FolderInfo parentInfo) throws Exception {
		Validate.notNull(parentInfo, "Parameter parentInfo must not be null.");
		Validate.notNull(fileInfo, "Parameter info must not be null.");
		Validate.notNull(fileInfo.getInputStream(), "Parameter info must provide an inputStream");

		Folder parent = obtainParentFolder(parentInfo);

		createFileEntryWithContent(fileInfo, parent);
	}

	@Override
	protected List<?> handleGetFileEntries(DomainObject domainObject) throws Exception {
		Validate.notNull(domainObject, "Parameter domainObject must not be null!");
		Folder root = getRootFolderForDomainObject(domainObject);
		List<?> entries = new ArrayList<FolderEntry>(root.getEntries());

		CollectionUtils.filter(entries, new Predicate() {
			public boolean evaluate(Object object) {
				return (object instanceof FileEntry);
			}
		});
		filterEntriesByPermission(entries);
		getFileEntryDao().toFileInfoCollection(entries);
		return entries;
	}

	@Override
	protected FileInfo handleGetFileEntry(Long fileId, boolean withInputStream) throws Exception {
		Validate.notNull(fileId, "Parameter fileId must not be null!");

		FileInfo fileInfo = (FileInfo) getFileEntryDao().load(FileEntryDao.TRANSFORM_FILEINFO, fileId);
		if (fileInfo != null && withInputStream) {
			fileInfo.setInputStream(getRepositoryService().loadContent(fileInfo.getId()));
		}
		return fileInfo;
	}

	@Override
	protected FolderInfo handleGetFolder(DomainObject domainObject) throws Exception {
		return getFolderDao().toFolderInfo(getRootFolderForDomainObject(domainObject));
	}

	@Override
	protected FolderInfo handleGetFolder(FolderInfo folder) throws Exception {
		Validate.notNull(folder, "Parameter folder must not be null!");
		return loadFolderInfo(folder.getId());
	}

	@Override
	protected FolderInfo handleGetFolder(FolderEntryInfo folder) throws Exception {
		Validate.notNull(folder, "Parameter folder must not be null!");
		return loadFolderInfo(folder.getId());
	}

	@Override
	protected List<FolderEntryInfo> handleGetFolderEntries(DomainObject domainObject, FolderInfo folderInfo) throws Exception {
		Validate.notNull(domainObject, "Parameter DomainObject must not be null!");
		Folder folder = retrieveFolderOfOwner(domainObject, folderInfo);
		List<FolderEntryInfo> entries = getFolderEntryDao().findByParent(FolderEntryDao.TRANSFORM_FOLDERENTRYINFO, folder);
		filterEntriesByPermission(entries);
		return entries;
	}
	
	private boolean isFolderOfDomainObject(DomainObject domainObject, Folder folder) {
		Validate.notNull(folder);
		while (folder.getParent() != null) {
			folder = folder.getParent();
		}
		return ObjectUtils.equals(folder.getDomainIdentifier(), domainObject.getId());
	}

	@Override
	protected List handleGetFolderPath(FolderInfo folderInfo) throws Exception {
		Validate.notNull(folderInfo, "Parameter folder must not be null!");
		Validate.notNull(folderInfo.getId(), "Paremter folder must contain a primary key.");

		List folderPath = new LinkedList();
		Folder folder = getFolderDao().load(folderInfo.getId());
		while (folder != null) {
			folderPath.add(0, folder);
			folder = folder.getParent();
		}
		getFolderDao().toFolderInfoCollection(folderPath);
		return folderPath;
	}

	@Override
	protected void handleRemoveFolderEntry(Long folderEntryId) throws Exception {
		Validate.notNull(folderEntryId, "Parameter folderEntryId must not be null.");
		removeFolderEntry(loadFolderEntry(folderEntryId));
	}

	@Override
	protected void handleRemoveFolderEntries(Collection folderEntries) throws Exception {
		Validate.allElementsOfType(folderEntries, FolderEntryInfo.class,
				"Parameter folderEntries must contain only FolderEntryInfo objects.");
		for (FolderEntryInfo entry : (Collection<FolderEntryInfo>) folderEntries) {
			removeFolderEntry(entry.getId());
		}
	}

	@Override
	protected void handleRemoveFileEntries(Collection entries) throws Exception {
		Validate.allElementsOfType(entries, FileInfo.class);
		for (FileInfo entry : (Collection<FileInfo>) entries) {
			removeFolderEntry(entry.getId());
		}
	}

	@Override
	protected void handleSaveFolder(FolderInfo folderInfo) throws Exception {
		Validate.notNull(folderInfo, "Parameter folderInfo must not be null.");
		
		Folder folder = getFolderDao().folderInfoToEntity(folderInfo);
		persistFolder(folder);
	}

	@Override
	protected void handleSaveFileEntry(FileInfo fileInfo) throws Exception {
		Validate.notNull(fileInfo, "Parameter fileInfo must not be null!");
		Validate.notNull(fileInfo.getId(), "Parameter fileInfo must contain an id.");
		FileEntry entry = getFileEntryDao().fileInfoToEntity(fileInfo);
		entry.setModified(new Date());
		persistFileEntry(entry);
		if (fileInfo.getInputStream() != null) {
			getRepositoryService().saveContent(fileInfo.getId(), fileInfo.getInputStream());
		}
	}

	private Folder createFolderByName(String folderName, Folder parent, Date created)
			throws DocumentApplicationException {
		logger.debug("createFolderByName(folderName=" + folderName + ", parent=" + parent + ")");

		FolderEntry folderEntry = parent.getFolderEntryByName(folderName);
		if (folderEntry == null) {
			Folder folder = Folder.Factory.newInstance();
			folder.setName(folderName);
			folder.setCreated(created);
			createFolder(folder, parent);
			return folder;
		} else if (folderEntry instanceof Folder) {
			return (Folder) folderEntry;
		} else if (folderEntry instanceof FileEntry) {
			throw new DocumentApplicationException("error_message_zip_file_produce_naming_conflicts");
		} else {
			throw new DocumentServiceException("error_message_unkown_folder_entry_type");
		}
	}

	private void createFileEntryWithContent(FileInfo fileInfo, Folder parent) throws DocumentApplicationException {
		Folder folder = createFolderPathStructure(parent, fileInfo);
		FileEntry entry = getFileEntryDao().fileInfoToEntity(fileInfo);

		persistFileEntry(entry, folder);
		getRepositoryService().saveContent(entry.getId(), fileInfo.getInputStream());

		defineSecurityPermission(entry, folder);

		getFileEntryDao().toFileInfo(entry, fileInfo);
	}

	private void createFolder(Folder folder, Folder parent) throws DocumentApplicationException {
		parent.addFolderEntry(folder);
		persistFolder(folder);
		getFolderDao().update(parent);
		defineSecurityPermission(folder, parent);
	}

	private Folder createFolderPathStructure(Folder parent, FileInfo info) throws DocumentApplicationException {
		Validate.notNull(info.getFileName(), "Parameter fileInfo must contain a filename.");
		List<String> path = retrievePathOfFileName(info.getFileName());
		for (String folderName : path) {
			parent = createFolderByName(folderName, parent, info.getCreated());
		}
		return parent;
	}

	/**
	 * Creates a new root folder for the domain object
	 * 
	 * @param domainIdentifier
	 * @return Folder - new root folder
	 */
	private Folder createRootFolderForDomainObject(Long domainIdentifier) {
		Validate.notNull(domainIdentifier, "DomainIdentifier must not be null.");
		logger.debug("Creating new root folder for domain object " + domainIdentifier);

		Folder folder = Folder.Factory.newInstance();
		folder.setName("");
		folder.setParent(null);
		folder.setCreated(new Date());
		folder.setDomainIdentifier(domainIdentifier);

		getFolderDao().create(folder);

		defineSecurityPermission(folder, domainIdentifier);

		return folder;
	}

	private void defineSecurityPermission(Object object, Object parent) {
		getSecurityService().createObjectIdentity(object, parent);
	}

	/**
	 * Filter not released files if user doesn't have Assist permission
	 * 
	 * @param domainObject
	 * @param contests
	 */
	private void filterEntriesByPermission(Collection entries) {
		// FIXME should be implemented as aspect
		if (entries != null && entries.size() > 0) {
			Object object = entries.iterator().next();
			if (!AcegiUtils.hasPermission(object, new Integer[] { LectureAclEntry.ASSIST })) {
				CollectionUtils.filter(entries, new Predicate() {
					public boolean evaluate(Object object) {
						boolean eval = false;
						if (object instanceof FolderEntry) {
							eval = ((FolderEntry) object).isReleased();
						} else if (object instanceof FileInfo) {
							eval = ((FileInfo) object).isReleased();
						} else if (object instanceof FolderEntryInfo) {
							eval = ((FolderEntryInfo) object).isReleased();
						} 
						if (!eval) {
							logger.trace("------------------------------------> removing file "+object);
						}
						
						return eval;
					}
				});
			}
		}
	}

	private Folder getRootFolderForDomainObject(DomainObject domainObject) throws IllegalAccessException,
			InvocationTargetException {
		Validate.notNull(domainObject, "domain_object must not be null!");
		Validate.notNull(domainObject.getId(), "domain object must provide an id!");

		Folder folder = getFolderDao().findByDomainIdentifier(domainObject.getId());
		if (folder == null) {
			folder = createRootFolderForDomainObject(domainObject.getId());
		}
		return folder;
	}

	private void persistFileEntry(FileEntry entry, Folder parent) throws DocumentApplicationException {
		parent.addFolderEntry(entry);
		persistFileEntry(entry);
		getFolderDao().update(parent);
	}

	private void persistFileEntry(FileEntry file) throws DocumentApplicationException {
		logger.debug("saving file entry " + file.getName());
		if (file.getCreated() == null) {
			file.setCreated(new Date());
		}
		
		// check if file with same name exists
		if (!canRename(file, file.getParent())) {
			throw new DocumentApplicationException("documents_folder_not_a_unique_filename");
		}

		if (file.getId() == null) {
			getFileEntryDao().create(file);
		} else {
			getFileEntryDao().update(file);
		}
	}

	private void persistFolder(Folder folder) throws DocumentApplicationException {
		Validate.notNull(folder, "Parameter folder must not be null!");
		Validate.notNull(folder, "Parameter folder must have a parent folder!");
		if (logger.isDebugEnabled()) {
			logger.debug("persisting folder " + folder.getName());
		}
		
		// check if folder with same name exists
		if (!canRename(folder, folder.getParent())) {
			throw new DocumentApplicationException("documents_folder_not_a_unique_filename");
		}
		
		if (folder.getId() == null) {
			getFolderDao().create(folder);
		} else {
			getFolderDao().update(folder);
		}
	}

	private void collectAllFileEntries(Collection<FolderEntry> entries, List<FileEntry> selectedFiles) {
		logger.debug("collectAllFileEntries(entries=" + entries + ", selectedFiles=" + selectedFiles + ")");
		for (FolderEntry entry : entries) {
			if (entry instanceof FileEntry) {
				FileEntry fileEntry = (FileEntry) entry;
				selectedFiles.add(fileEntry);
			} else if (entry instanceof Folder) {
				Folder folder = (Folder) entry;
				collectAllFileEntries(folder.getEntries(), selectedFiles);
			}
		}
	}

	private FolderInfo loadFolderInfo(Long folderId) {
		Validate.notNull(folderId, "Must provide a folder id.");
		return (FolderInfo) getFolderDao().load(FolderDao.TRANSFORM_FOLDERINFO, folderId);
	}

	private FolderEntry loadFolderEntry(Long id) {
		Validate.notNull(id, "Must provide a folder entry id.");
		FolderEntry entry = getFolderEntryDao().load(id);
		return entry;
	}

	private Folder obtainParentFolder(FolderInfo parentInfo) throws DocumentApplicationException {
		Folder parent = getFolderDao().load(parentInfo.getId());
		if (parent == null) {
			throw new DocumentApplicationException("message_parent_folder_not_found");
		}
		return parent;
	}

	private List<String> retrievePathOfFileName(String fileName) {
		List<String> path = new ArrayList<String>();
		if (StringUtils.isNotBlank(fileName)) {
			if (fileName.startsWith("/")) {
				fileName = fileName.substring(1);
			}
			File file = new File(fileName);
			while ((file = file.getParentFile()) != null) {
				path.add(0, file.getName());
			}
		}
		return path;
	}

	private void removeFolderEntry(FolderEntry folderEntry) throws Exception {
		if (folderEntry instanceof FileEntry) {
			getFolderEntryDao().remove(folderEntry.getId());
			getRepositoryService().removeContent(folderEntry.getId());
			getSecurityService().removeObjectIdentity(folderEntry);
		} else if (folderEntry instanceof Folder) {
			Folder folder = (Folder) folderEntry;
			for (FolderEntry entry : folder.getEntries()) {
				removeFolderEntry(entry);
			}
			getFolderEntryDao().remove(folder);
			getSecurityService().removeObjectIdentity(folderEntry);
		}
	}

	@Override
	protected void handleDiffSave(DomainObject domainObject, List files) throws Exception {
		Validate.notNull(domainObject, "Parameter domainObject must not be null");
		if (files == null) {
			files = new ArrayList<FileInfo>();
		}

		List<FileInfo> savedAttachments = getFileEntries(domainObject);
		Collection<FileInfo> removedAttachments = CollectionUtils.subtract(savedAttachments, files);
		removeFileEntries(removedAttachments);

		FolderInfo folder = getFolder(domainObject);
		for (FileInfo file : (List<FileInfo>) files) {
			if (file.getId() == null) {
				createFileEntry(file, folder);
			}
		}

	}

	@Override
	protected void handleRemove(DomainObject domainObject) throws Exception {
		Validate.notNull(domainObject, "Parameter domainObject must not be null.");
		Folder root = getRootFolderForDomainObject(domainObject);
		removeFolderEntry(root.getId());
	}

	@Override
	protected FolderInfo handleGetFolder(DomainObject owner, FolderInfo folderInfo) throws Exception {
		Validate.notNull(owner, "Parameter DomainObject must not be null!");
		Folder folder = retrieveFolderOfOwner(owner, folderInfo);
		return getFolderDao().toFolderInfo(folder);
	}

	/**
	 * This method verifies that the returned folder belongs to the domain object. If the specified folder
	 * is not a subfolder of the domain object this method will return the root folder of the domain object.
	 * @param owner
	 * @param folderInfo
	 * @return folder entity instance
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private Folder retrieveFolderOfOwner(DomainObject owner, FolderInfo folderInfo) throws IllegalAccessException, InvocationTargetException {
		Folder folder = null;
		if (folderInfo != null && folderInfo.getId() != null) {
			folder = getFolderDao().load(folderInfo.getId());
		}
		if (folder == null || !isFolderOfDomainObject(owner, folder)) {
			folder = getRootFolderForDomainObject(owner);
		}
		return folder;
	}

	@Override
	protected boolean handleIsFolderOfDomainObject(FolderInfo folderInfo, DomainObject domainObject) throws Exception {
		Folder folder = getFolderDao().load(folderInfo.getId());
		return isFolderOfDomainObject(domainObject, folder);
	}

	private boolean canRename(FolderEntry folderEntry, Folder folder) {
		for (FolderEntry entry : folder.getEntries()) {
			if (StringUtils.equalsIgnoreCase(folderEntry.getFileName(), entry.getFileName()) && !ObjectUtils.equals(entry, folderEntry)) {
				return false; // not valid
			}
		}
		return true; // vaild
	}
}
