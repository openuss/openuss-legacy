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
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.framework.utilities.DomainObjectUtility;
import org.openuss.repository.RepositoryFile;

/**
 * @see org.openuss.documents.DocumentsService
 */
public class DocumentServiceImpl extends org.openuss.documents.DocumentServiceBase {

	private static final Logger logger = Logger.getLogger(DocumentServiceImpl.class);

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
	protected void handleCreateFileEntry(FileInfo fileInfo, FolderInfo parentInfo) throws Exception {
		Validate.notNull(parentInfo, "Parameter parentInfo must not be null.");
		Validate.notNull(fileInfo, "Parameter info must not be null.");
		Validate.notNull(fileInfo.getInputStream(), "Parameter info must provide an inputStream");
		
		Folder parent = obtainParentFolder(parentInfo);
		
		createFileEntryAndRepositoryFile(fileInfo, parent);
	}
	
	private Folder obtainParentFolder(FolderInfo parentInfo) throws DocumentApplicationException {
		Folder parent = getFolderDao().load(parentInfo.getId());
		if (parent == null) {
			throw new DocumentApplicationException("message_parent_folder_not_found");
		}
		return parent;
	}
	
	private void createFileEntryAndRepositoryFile(FileInfo fileInfo, Folder parent) throws DocumentApplicationException {
		Folder folder = createFolderPathStructure(parent, fileInfo);
		FileEntry entry = getFileEntryDao().fileInfoToEntity(fileInfo);
		
		persistFileEntry(entry, folder);
		
		getRepositoryService().saveContent(entry.getId(), fileInfo.getInputStream());

		getFileEntryDao().toFileInfo(entry, fileInfo);
	}
	
	private void persistFileEntry(FileEntry entry, Folder parent) {
		parent.addFolderEntry(entry);
		persistFileEntry(entry);
		getFolderDao().update(parent);
	}
	
	private void persistFileEntry(FileEntry file) {
		logger.debug("saving file entry " + file.getName());
		if (file.getCreated() == null) {
			file.setCreated(new Date());
		}
		
		if (file.getId() == null) {
			getFileEntryDao().create(file);
		} else {
			getFileEntryDao().update(file);
		}
	}

	@Override
	protected List handleAllFileEntries(Collection entries) throws Exception {
		Validate.allElementsOfType(entries, FolderEntryInfo.class, "Parameter entries must only contain FolderEntryInfo objects.");
		Collection entities = new ArrayList(entries);
		getFolderEntryDao().folderEntryInfoToEntityCollection(entities);

		List files = new ArrayList();
		collectAllFileEntries(entities, files);

		getFileEntryDao().toFileInfoCollection(files);

		return files;
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
	protected List handleGetFolderEntries(Object domainObject, FolderInfo folderInfo) throws Exception {
		Folder folder;
		if (folderInfo == null || folderInfo.getId() == null) {
			folder = getRootFolderForDomainObject(domainObject);
		} else {
			folder = getFolderDao().load(folderInfo.getId());
		}
		if (folder == null || folder.getId() == null) {
			throw new DocumentApplicationException("message_error_folder_not_found");
		}
		List entries = getFolderEntryDao().findByParent(FolderEntryDao.TRANSFORM_FOLDERENTRYINFO, folder);
		return entries;
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
		getFileEntryDao().update(entry);
		if (fileInfo.getInputStream() != null) {
			getRepositoryService().saveContent(fileInfo.getId(), fileInfo.getInputStream());
		}
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
	protected FolderInfo handleGetFolder(Object domainObject) throws Exception {
		return getFolderDao().toFolderInfo(getRootFolderForDomainObject(domainObject));
	}

	private Folder getRootFolderForDomainObject(Object domainObject) throws IllegalAccessException,
			InvocationTargetException {
		Validate.notNull(domainObject, "domain_object must not be null!");
		Long domainIdentifier = DomainObjectUtility.identifierFromObject(domainObject);
		if (domainIdentifier == null) {
			throw new DocumentServiceException("no_domain_object_identifier_found");
		}
		Folder folder = getFolderDao().findByDomainIdentifier(domainIdentifier);
		if (folder == null) {
			folder = createRootFolderForDomainObject(domainIdentifier);
		}
		return folder;
	}

	private Folder createRootFolderForDomainObject(Long domainIdentifier) {
		Validate.notNull(domainIdentifier, "DomainIdentifier must not be null.");
		logger.debug("Creating new root folder for domain object " + domainIdentifier);

		Folder folder = Folder.Factory.newInstance();
		folder.setName("");
		folder.setParent(null);
		folder.setCreated(new Date());
		folder.setDomainIdentifier(domainIdentifier);

		getFolderDao().create(folder);
		return folder;
	}

	private FolderInfo loadFolderInfo(Long folderId) {
		Validate.notNull(folderId, "Must provide a folder id.");
		return (FolderInfo) getFolderDao().load(FolderDao.TRANSFORM_FOLDERINFO, folderId);
	}

	private void removeFolderEntry(FolderEntry folderEntry) throws Exception {
		if (folderEntry instanceof FileEntry) {
			getFolderEntryDao().remove(folderEntry.getId());
			getRepositoryService().removeContent(folderEntry.getId());
		} else if (folderEntry instanceof Folder) {
			Folder folder = (Folder) folderEntry;
			for (FolderEntry entry : folder.getEntries()) {
				removeFolderEntry(entry);
			}
			getFolderEntryDao().remove(folder);
		}
	}

	private FolderEntry loadFolderEntry(Long id) {
		Validate.notNull(id, "Must provide a folder entry id.");
		FolderEntry entry = getFolderEntryDao().load(id);
		return entry;
	}


	private void persistFolder(Folder folder) {
		Validate.notNull(folder, "Parameter folder must not be null!");
		Validate.notNull(folder, "Parameter folder must have a parent folder!");
		if (logger.isDebugEnabled()) {
			logger.debug("persisting folder " + folder.getName());
		}
		if (folder.getId() == null) {
			getFolderDao().create(folder);
		} else {
			getFolderDao().update(folder);
		}
	}

	private void createFolder(Folder folder, Folder parent) {
		parent.addFolderEntry(folder);

		persistFolder(folder);

		getFolderDao().update(parent);
	}

	private Folder createFolderPathStructure(Folder parent, FileInfo info) throws DocumentApplicationException {
		List<String> path = retrievePathOfFileName(info.getFileName());
		for (String folderName : path) {
			parent = createFolderByName(folderName, parent, info.getCreated());
		}
		return parent;
	}

	private List<String> retrievePathOfFileName(String fileName) {
		if (fileName.startsWith("/")) {
			fileName = fileName.substring(1);
		}
		List<String> path = new ArrayList<String>();
		File file = new File(fileName);
		while ((file = file.getParentFile()) != null) {
			path.add(0, file.getName());
		}
		return path;
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

	@Override
	protected List handleGetFileEntries(Object domainObject) throws Exception {
		Folder root = getRootFolderForDomainObject(domainObject);
		List entries = root.getEntries();
		CollectionUtils.filter(entries, new Predicate() {
			public boolean evaluate(Object object) {
				return (object instanceof FileEntry);
			}
		});
		return entries;
	}

	@Override
	protected void handleCreateFileEntries(Collection fileEntries, FolderInfo parentInfo) throws Exception {
		Validate.allElementsOfType(fileEntries, FileInfo.class,	"Parameter fileEntries must only contain FileEntryInfo objects.");
		Validate.notNull(parentInfo, "Parameter parentInfo must not be null.");

		Folder parent = obtainParentFolder(parentInfo);

		for (FileInfo entry : (Collection<FileInfo>) fileEntries) {
			createFileEntryAndRepositoryFile(entry, parent);
		}
	}
}