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
import java.util.LinkedList;
import java.util.List;

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
		Folder parent = loadFolder(parentInfo.getId());
		
		createFolder(folder, parent);
		getFolderDao().toFolderInfo(folder, folderInfo);
	}

	@Override
	protected void handleCreateFileEntry(FolderEntryInfo info, FolderInfo parentInfo) throws Exception {
		Validate.notNull(info, "Parameter info must not be null.");
		Validate.notNull(parentInfo, "Parameter parentInfo must not be null.");
		Validate.notNull(info.getRepositoryFileId(), "Parameter info must provide a valid repository file id");
		
		Folder parent = obtainParentFolder(parentInfo);
		FileEntry entry = getFileEntryDao().folderEntryInfoToEntity(info);
		
		persistFileEntry(entry, parent);
		
		injectRepositoryFileInEntry(entry);
		getFileEntryDao().toFolderEntryInfo(entry, info);
	}

	/**
	 * Injects a refreshed repository file into a file entry object
	 * @param entry
	 */
	private void injectRepositoryFileInEntry(FileEntry entry) {
		entry.setRepositoryFile(getRepositoryService().getFile(entry.getRepositoryFile(), false));
	}

	/**
	 * Retrieve the folder of the given folderInfo value object
	 * @param parentInfo
	 * @return Folder object
	 * @throws DocumentApplicationException
	 */
	private Folder obtainParentFolder(FolderInfo parentInfo) throws DocumentApplicationException {
		Folder parent = loadFolder(parentInfo.getId());
		if (parent == null) {
			throw new DocumentApplicationException("message_parent_folder_not_found");
		}
		return parent;
	}

	@Override
	protected void handleCreateFileEntry(FileInfo fileInfo, FolderInfo parentInfo) throws Exception {
		Validate.notNull(parentInfo, "Parameter parentInfo must not be null.");
		Validate.notNull(fileInfo, "Parameter info must not be null.");
		Validate.notNull(fileInfo.getInputStream(), "Parameter info must provide an inputStream");

		Folder parent = obtainParentFolder(parentInfo);

		createFileEntryAndRepositoryFile(fileInfo, parent);
	}

	/**
	 * 
	 * @param fileInfo
	 * @param parent
	 * @throws DocumentApplicationException
	 */
	private void createFileEntryAndRepositoryFile(FileInfo fileInfo, Folder parent) throws DocumentApplicationException {
		Folder folder = createFolderPathStructure(parent, fileInfo);
		
		FileEntry entry = getFileEntryDao().fileInfoToEntity(fileInfo);
		RepositoryFile repositoryFile = entry.getRepositoryFile();
		getRepositoryService().saveFile(repositoryFile);
		persistFileEntry(entry, folder);
		 
		getFileEntryDao().toFileInfo(entry, fileInfo);
	}

	@Override
	protected void handleCreateFolderEntries(Collection fileEntries, FolderInfo parentInfo) throws Exception {
		Validate.allElementsOfType(fileEntries, FileInfo.class, "Parameter fileEntries must only contain FileEntryInfo objects.");
		Validate.notNull(parentInfo, "Parameter parentInfo must not be null.");
		
		Folder parent = obtainParentFolder(parentInfo);
		
		for (FileInfo entry : (Collection<FileInfo>)fileEntries) {
			createFileEntryAndRepositoryFile(entry, parent);
		}
	}

	@Override
	protected List handleAllFileEntries(Collection entries) throws Exception {
		Validate.allElementsOfType(entries, FolderEntryInfo.class, "Parameter entries must only contain FolderEntryInfo objects.");
		getFolderDao().folderEntryInfoToEntityCollection(entries);
		
		List files = new ArrayList();
		collectAllFileEntries(entries, files);
		
		getFileEntryDao().toFileInfoCollection(files);
		
		return files;
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
			folder = loadFolder(folderInfo.getId());
		}
		if (folder == null || folder.getId() == null) {
			throw new DocumentApplicationException("message_error_folder_not_found");
		}
		List entries = getFolderEntryDao().findByParent(FolderEntryDao.TRANSFORM_FOLDERENTRYINFO, folder);
		if (entries == null) {
			entries = new ArrayList();
		}
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
	protected void handleRemoveFolder(FolderInfo folderInfo) throws Exception {
		Validate.notNull(folderInfo, "Parameter folderInfo must not be null.");
		removeFolderEntry(loadFolderEntry(folderInfo.getId()));
	}
	
	
	@Override
	protected void handleRemoveFolderEntry(FolderEntryInfo folderEntryInfo) throws Exception {
		Validate.notNull(folderEntryInfo, "Parameter folderEntryInfo must not be null.");
		removeFolderEntry(loadFolderEntry(folderEntryInfo.getId()));
	}
	
	@Override
	protected void handleRemoveFolderEntries(Collection folderEntries) throws Exception {
		Validate.allElementsOfType(folderEntries, FolderEntryInfo.class, "Parameter folderEntries must contain only FolderEntryInfo objects.");
		for (FolderEntryInfo entry: (Collection<FolderEntryInfo>)folderEntries) {
			removeFolderEntry(entry);
		}
	}
	
	@Override
	protected void handleSaveFolder(FolderInfo folderInfo) throws Exception {
		Validate.notNull(folderInfo, "Parameter folderInfo must not be null.");
		Folder folder = getFolderDao().folderInfoToEntity(folderInfo);
		persistFolder(folder);
	}
	
	@Override
	protected void handleSaveFolderEntry(FolderEntryInfo folderEntryInfo) throws Exception {
		Validate.notNull(folderEntryInfo, "Parameter folderEntry must not be null!");
		FolderEntry entry = getFolderEntryDao().folderEntryInfoToEntity(folderEntryInfo);
		if (entry instanceof FileEntry) {
			injectRepositoryFileInEntry((FileEntry) entry);
			persistFileEntry((FileEntry) entry);
		} else if (entry instanceof Folder) {
			persistFolder((Folder) entry);
		} else {
			throw new DocumentServiceException("Unkown folder entry type!");
		}
	}

	@Override
	protected FolderEntryInfo handleGetFileEntry(FolderEntryInfo folderEntryInfo) throws Exception {
		Validate.notNull(folderEntryInfo, "Parameter folderEntry must not be null!");
		Validate.notNull(folderEntryInfo.getId(),
				"Parameter folderEntry must be an persisted entity with a valid identifier!");

		FileEntry file = getFileEntryDao().load(folderEntryInfo.getId());
		connectWithFileInputStream(file);
		return getFolderEntryDao().toFolderEntryInfo(file);
	}


	@Override
	protected FolderInfo handleGetFolder(Object domainObject) throws Exception {
		return getFolderDao().toFolderInfo(getRootFolderForDomainObject(domainObject));
	}

	private void connectWithFileInputStream(FileEntry file) {
		getRepositoryService().getInputStreamOfFile(file.getRepositoryFile());
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
		folder.setName("ROOT");
		folder.setDomainIdentifier(domainIdentifier);
		folder.setParent(null);

		getFolderDao().create(folder);
		return folder;
	}

	private FolderInfo loadFolderInfo(Long folderId) {
		Validate.notNull(folderId, "Must provide a folder id.");
		return (FolderInfo) getFolderDao().load(FolderDao.TRANSFORM_FOLDERINFO, folderId);
	}
	
	private Folder loadFolder(long id) {
		Folder folder;
		folder = getFolderDao().load(id);
		return folder;
	}
	
	private void removeFolderEntry(FolderEntry folderEntry) throws Exception {
		if (folderEntry instanceof FileEntry) {
			getFolderEntryDao().remove(folderEntry.getId());
			FileEntry fileEntry = (FileEntry) folderEntry;
			getRepositoryService().removeFile(fileEntry.getRepositoryFile());
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

	private void persistFileEntry(FileEntry file) {
		if (logger.isDebugEnabled()) {
			logger.debug("saving file entry " + file.getName());
		}
		if (file.getId() == null) {
			getFileEntryDao().create(file);
		} else {
			getFileEntryDao().update(file);
		}
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
	
	private void persistFileEntry(FileEntry entry, Folder parent) {
		parent.addFolderEntry(entry);
		persistFileEntry(entry);
		getFolderDao().update(parent);
	}
	
	private Folder createFolderPathStructure(Folder parent, FileInfo info) throws DocumentApplicationException {
		List<String> path = retrievePathOfFileName(info.getName());
		for (String folderName : path) {
			parent = createFolderByName(folderName, parent);
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
	
	private Folder createFolderByName(String folderName, Folder parent) throws DocumentApplicationException {
		logger.debug("createFolderByName(folderName=" + folderName + ", parent=" + parent + ")");

		FolderEntry folderEntry = parent.getFolderEntryByName(folderName);
		if (folderEntry == null) {
			Folder folder = Folder.Factory.newInstance();
			folder.setName(folderName);
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
	
	private void collectAllFileEntries(Collection<FolderEntry> entries, List<FileEntry> selectedFiles) {
		logger.debug("collectAllFileEntries(entries=" + entries + ", selectedFiles=" + selectedFiles + ")");
		for (FolderEntry entry : entries) {
			if (entry instanceof FileEntry) {
				FileEntry fileEntry = (FileEntry) entry;
				connectWithFileInputStream(fileEntry); 
				selectedFiles.add(fileEntry);
			} else if (entry instanceof Folder) {
				Folder folder = (Folder) entry;
				collectAllFileEntries(folder.getEntries(), selectedFiles);
			}
		}
	}

//	private void createFileEntry(FileInfo info, Folder parent) throws DocumentApplicationException {
//		logger.debug("createFileEntry(info=" + info + ", parent=" + parent + ")"); 
//
//		RepositoryFile repositoryFile = RepositoryFile.Factory.newInstance();
//		File file = new File(info.getName());
//		repositoryFile.setName(file.getName());
//		repositoryFile.setFileName(file.getName());
//		repositoryFile.setCreated(info.getCreated());
//		repositoryFile.setModified(info.getModified());
//		// bigger than int will cause trouble much early
//		repositoryFile.setFileSize((int) info.getSize());
//		repositoryFile.setDescription(info.getDescription());
//		repositoryFile.setContentType(info.getContentType());
//		repositoryFile.setInputStream(info.getInputStream());
//		
//		FileEntry fileEntry = FileEntry.Factory.newInstance();
//		fileEntry.setRepositoryFile(repositoryFile);
//		
//		persistFileEntry(fileEntry, parent);
//		getFileEntryDao().toFileInfo(fileEntry, info);
//	}


//	private static final int DRAIN_BUFFER_SIZE = 1024;
//	protected void handleCreateFolderEntryFromZip(InputStream input, Folder parent) throws Exception {
//	parent = loadFolder(parent.getId());
//	try {
//		ZipInputStream zis = new ZipInputStream(input);
//		ZipEntry entry;
//		while ((entry = zis.getNextEntry()) != null) {
//			if (!entry.isDirectory()) {
//				logger.debug("-- name: " + entry.getName());
//				try {
//					Folder folder = createFolderPathStructure(parent, entry);
//					createFileEntry(zis, entry, folder);
//				} catch (IllegalArgumentException e) {
//					logger.error(e);
//				}
//			}
//		}
//		zis.close();
//		input.close();
//	} catch (Exception e) {
//		logger.error("Can't read next entry of zip input stream", e);
//		throw new DocumentApplicationException("message_error_cannot_read_entry_in_zip_file");
//	}
//}
//
//protected InputStream handleGetZippedFolderEntries(Collection selectedEntries) throws Exception {
//	Collection<FolderEntry> entries = transformFolderEntryInfoToFolderEntry(selectedEntries);
//	List<FileEntry> selectedFiles = new ArrayList<FileEntry>();
//	collectAllFileEntries(entries, selectedFiles);
//	logger.debug("ZippedFolderEntries : - " + selectedFiles.size() + " files selected!");
//	return zippedFileContent(selectedFiles);
//}
//
//private Collection<FolderEntry> transformFolderEntryInfoToFolderEntry(Collection selectedEntries) {
//	Validate.allElementsOfType(selectedEntries, FolderEntryInfo.class,
//			"Parameter selectedEntries must contain FolderEntryInfo objects");
//	CollectionUtils.transform(selectedEntries, new Transformer() {
//		public Object transform(Object object) {
//			FolderEntryInfo info = (FolderEntryInfo) object;
//			if (info.isFolder()) {
//				return getFolder(info);
//			} else {
//				return getFileEntry(info);
//			}
//		}
//	});
//	Collection<FolderEntry> result = new ArrayList<FolderEntry>();
//	result.addAll(selectedEntries);
//	return result;
//}
//
//private InputStream zippedFileContent(final Collection<FileEntry> files) throws Exception {
//	try {
//		final PipedInputStream pis = new PipedInputStream();
//		final PipedOutputStream pot = new PipedOutputStream(pis);
//
//		new Thread(new Runnable() {
//			public void run() {
//				ZipOutputStream zos = null;
//				InputStream fis = null;
//				boolean empty = true;
//				try {
//					zos = new ZipOutputStream(pot);
//					for (FileEntry file : files) {
//						ZipEntry zipEntry = new ZipEntry(file.getAbsoluteName());
//						zipEntry.setComment(file.getDescription());
//						zipEntry.setTime(file.getModified().getTime());
//						zos.putNextEntry(zipEntry);
//						fis = file.getRepositoryFile().getInputStream();
//						drain(fis, zos);
//						fis.close();
//						zos.closeEntry();
//						empty = false;
//					}
//					if (empty) {
//						zos.putNextEntry(new ZipEntry("readme.txt"));
//						zos.write("No contents available".getBytes());
//						zos.closeEntry();
//					}
//					zos.close();
//				} catch (Exception e) {
//					logger.error("Can't pipe output to input", e);
//					try {
//						if (fis != null)
//							fis.close();
//						if (zos != null)
//							zos.close();
//						if (pis != null)
//							pis.close();
//					} catch (IOException e1) {
//						logger.error("Can't pipe output to input", e1);
//					}
//				}
//			}
//		}).start();
//		return pis;
//	} catch (Exception e) {
//		logger.error("Can't create input-stream", e);
//		return new ByteArrayInputStream(new byte[0]);
//	}
//}
//
//private void drain(InputStream input, OutputStream output) throws IOException {
//	int bytesRead = 0;
//	byte[] buffer = new byte[DRAIN_BUFFER_SIZE];
//
//	while ((bytesRead = input.read(buffer, 0, DRAIN_BUFFER_SIZE)) != -1) {
//		output.write(buffer, 0, bytesRead);
//	}
//}

}