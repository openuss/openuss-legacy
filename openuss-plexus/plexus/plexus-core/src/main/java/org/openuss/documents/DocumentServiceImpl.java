// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.framework.utilities.DomainObjectUtility;
import org.openuss.repository.RepositoryFile;

/**
 * @see org.openuss.documents.DocumentsService
 */
public class DocumentServiceImpl extends org.openuss.documents.DocumentServiceBase {

	private static final Logger logger = Logger.getLogger(DocumentServiceImpl.class);

	private static final int DRAIN_BUFFER_SIZE = 1024;

	@Override
	protected void handleCreateFileEntry(FileEntry file, Folder parent) throws Exception {
		Validate.notNull(file, "Parameter file must not be null!");
		Validate.notNull(parent, "Parameter parent must not be null!");

		parent = getFolderDao().load(parent.getId());
		parent.addFolderEntry(file);

		persistFileEntry(file);

		getFolderDao().update(parent);
	}

	private void persistFileEntry(FileEntry file) {
		Validate.notNull(file, "Parameter file must not be null!");
		Validate.notNull(file.getParent(), "Parameter file must have a parent folder!");
		if (logger.isDebugEnabled()) {
			logger.debug("saving file entry " + file.getName());
		}
		getRepositoryService().saveFile(file.getRepositoryFile());

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

	@Override
	protected void handleCreateFolderEntry(Folder folder, Folder parent) throws Exception {
		Validate.notNull(folder, "Parameter folder must not be null!");
		Validate.notNull(parent, "Parameter parent must not be null!");

		parent = getFolderDao().load(parent.getId());
		parent.addFolderEntry(folder);

		persistFolder(folder);

		getFolderDao().update(parent);
	}

	@Override
	protected FileEntry handleGetFileEntry(FolderEntryInfo folderEntry) throws Exception {
		Validate.notNull(folderEntry, "Parameter folderEntry must not be null!");
		Validate.notNull(folderEntry.getId(),
				"Parameter folderEntry must be an persisted entity with a valid identifier!");

		FileEntry file = getFileEntryDao().load(folderEntry.getId());
		connectWithFileInputStream(file);
		return file;
	}

	private void connectWithFileInputStream(FileEntry file) {
		getRepositoryService().getInputStreamOfFile(file.getRepositoryFile());
	}

	@Override
	protected Folder handleGetFolder(FolderEntryInfo folderEntry) throws Exception {
		Validate.notNull(folderEntry, "Parameter folderEntry must not be null!");
		Validate.notNull(folderEntry.getId(),
				"Parameter folderEntry must be an persisted entity with a valid identifier!");
		return getFolderDao().load(folderEntry.getId());
	}

	@Override
	protected List handleGetFolderPath(Folder folder) throws Exception {
		Validate.notNull(folder, "Parameter folder must not be null!");
		Validate.notNull(folder.getId(), "Paremter folder must contain a primary key.");
		
		List folderPath = new LinkedList();
		folder = getFolderDao().load(folder.getId());
		while (folder != null) {
			folderPath.add(0, folder);
			folder = folder.getParent();
		}
		return folderPath;
	}

	@Override
	protected void handleRemoveFolderEntry(FolderEntryInfo folderEntryInfo) throws Exception {
		Validate.notNull(folderEntryInfo, "Parameter folderEntryInfo must not be null.");
		Validate.notNull(folderEntryInfo.getId(), "Parameter folderEntryInfo must contain an id.");

		FolderEntry folderEntry = getFolderEntryDao().load(folderEntryInfo.getId());
		removeFolderEntry(folderEntry);
	}

	@Override
	protected void handleRemoveFolderEntry(FolderEntry folderEntry) throws Exception {
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

	@Override
	protected void handleRemoveFolderEntries(Collection folderEntries) throws Exception {
		Validate.notNull(folderEntries, "Parameter folderEntries must not be null!");
		for (Object object : folderEntries) {
			if (object instanceof FolderEntry) {
				removeFolderEntry((FolderEntry) object);
			} else if (object instanceof FolderEntryInfo) {
				removeFolderEntry((FolderEntryInfo) object);
			}
		}
	}

	@Override
	protected void handleSaveFolderEntry(FolderEntry folderEntry) throws Exception {
		Validate.notNull(folderEntry, "Parameter folderEntry must not be null!");
		Validate.notNull(folderEntry.getId(),
				"Parameter folderEntry must be an persisted entity with a valid identifier!");

		if (folderEntry instanceof FileEntry) {
			persistFileEntry((FileEntry) folderEntry);
		} else if (folderEntry instanceof Folder) {
			persistFolder((Folder) folderEntry);
		} else {
			throw new DocumentServiceException("Unkown folder entry type!");
		}
	}

	@Override
	protected List handleGetFolderEntries(Object domainObject, Folder folder) throws Exception {
		if (folder == null || folder.getId() == null) {
			folder = getRootFolderForDomainObject(domainObject);
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
	protected Folder handleGetFolder(Object domainObject, FolderEntry folderEntry) throws IllegalAccessException,
			InvocationTargetException {
		if (folderEntry == null) {
			return getRootFolderForDomainObject(domainObject);
		} else {
			return getFolderDao().load(folderEntry.getId());
		}
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
		Folder folder;
		folder = Folder.Factory.newInstance();
		folder.setName("ROOT_FOLDER");
		folder.setDomainIdentifier(domainIdentifier);
		folder.setParent(null);

		getFolderDao().create(folder);
		return folder;
	}

	@Override
	protected void handleCreateFolderEntryFromZip(InputStream input, Folder parent) throws Exception {
		parent = getFolder(null, parent);
		try {
			ZipInputStream zis = new ZipInputStream(input);
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				if (!entry.isDirectory()) {
					logger.debug("-- name: " + entry.getName());
					try {
						Folder folder = createFolderPathStructure(parent, entry);
						createFileEntry(zis, entry, folder);
					} catch (IllegalArgumentException e) {
						logger.error(e);
					}
				}
			}
			zis.close();
			input.close();
		} catch (Exception e) {
			logger.error("Can't read next entry of zip input stream", e);
			throw new DocumentApplicationException("message_error_cannot_read_entry_in_zip_file");
		}
	}

	private void createFileEntry(InputStream inputStream, ZipEntry entry, Folder folder)
			throws DocumentApplicationException {
		RepositoryFile repositoryFile = RepositoryFile.Factory.newInstance();
		File file = new File(entry.getName());
		repositoryFile.setName(file.getName());
		repositoryFile.setFileName(file.getName());
		repositoryFile.setCreated(new Date(entry.getTime()));
		repositoryFile.setModified(new Date(entry.getTime()));
		// bigger than int will cause trouble much early
		repositoryFile.setFileSize((int) entry.getSize()); 
		repositoryFile.setDescription(entry.getComment());
		repositoryFile.setContentType("application/octet-stream");
		repositoryFile.setInputStream(inputStream);

		FileEntry fileEntry = FileEntry.Factory.newInstance();
		fileEntry.setRepositoryFile(repositoryFile);
		createFileEntry(fileEntry, folder);
	}

	private Folder createFolderPathStructure(Folder parent, ZipEntry entry) throws DocumentApplicationException {
		List<String> path = retrievePathByFileName(entry.getName());
		for (String folderName : path) {
			parent = createFolderByName(folderName, parent);
		}
		return parent;
	}

	private List<String> retrievePathByFileName(String fileName) {
		List<String> path = new ArrayList<String>();
		File file = new File(fileName);
		while ((file = file.getParentFile()) != null) {
			path.add(0, file.getName());
		}
		return path;
	}

	private Folder createFolderByName(String folderName, Folder parent) throws DocumentApplicationException {
		FolderEntry folderEntry = parent.getFolderEntryByName(folderName);
		if (folderEntry == null) {
			Folder folder = Folder.Factory.newInstance();
			folder.setName(folderName);
			createFolderEntry(folder, parent);
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
	protected InputStream handleGetZippedFolderEntries(Collection selectedEntries) throws Exception {
		Collection<FolderEntry> entries = transformFolderEntryInfoToFolderEntry(selectedEntries);
		List<FileEntry> selectedFiles = new ArrayList<FileEntry>();
		collectAllFileEntries(entries, selectedFiles);
		logger.debug("ZippedFolderEntries : - "+selectedFiles.size()+" files selected!");
		return zippedFileContent(selectedFiles);
	}

	private void collectAllFileEntries(Collection<FolderEntry> entries, List<FileEntry> selectedFiles) {
		for (FolderEntry entry : entries) {
			if (entry instanceof FileEntry) {
				FileEntry fileEntry = (FileEntry) entry;
				connectWithFileInputStream(fileEntry);
				selectedFiles.add(fileEntry);
			} else if (entry instanceof Folder){
				Folder folder = (Folder) entry;
				collectAllFileEntries(folder.getEntries(),selectedFiles);
			}
		}
	}

	private Collection<FolderEntry> transformFolderEntryInfoToFolderEntry(Collection selectedEntries) {
		Validate.allElementsOfType(selectedEntries, FolderEntryInfo.class,"Parameter selectedEntries must contain FolderEntryInfo objects");
		CollectionUtils.transform(selectedEntries, new Transformer(){
			public Object transform(Object object) {
				FolderEntryInfo info = (FolderEntryInfo) object;
				if (info.isFolder()) {
					return getFolder(info);
				} else {
					return getFileEntry(info);
				}
			}}
		);
		Collection<FolderEntry> result = new ArrayList<FolderEntry>();
		result.addAll(selectedEntries);
		return result;
	}

	protected InputStream zippedFileContent(final Collection<FileEntry> files) throws Exception {
		try {
			final PipedInputStream pis = new PipedInputStream();
			final PipedOutputStream pot = new PipedOutputStream(pis);

			new Thread(new Runnable() {
				public void run() {
					ZipOutputStream zos = null;
					InputStream fis = null;
					boolean empty = true;
					try {
						zos = new ZipOutputStream(pot);
						for (FileEntry file : files) {
							ZipEntry zipEntry = new ZipEntry(file.getAbsoluteName());
							zipEntry.setComment(file.getDescription());
							zipEntry.setTime(file.getModified().getTime());
							zos.putNextEntry(zipEntry);
							fis = file.getRepositoryFile().getInputStream();
							drain(fis, zos);
							fis.close();
							zos.closeEntry();
							empty = false;
						}
						if (empty) {
							zos.putNextEntry(new ZipEntry("readme.txt"));
							zos.write("No contents available".getBytes());
							zos.closeEntry();
						}
						zos.close();
					} catch (Exception e) {
						logger.error("Can't pipe output to input", e);
						try {
							if (fis != null)
								fis.close();
							if (zos != null)
								zos.close();
							if (pis != null)
								pis.close();
						} catch (IOException e1) {
							logger.error("Can't pipe output to input", e1);
						}
					}
				}
			}).start();
			return pis;
		} catch (Exception e) {
			logger.error("Can't create input-stream", e);
			return new ByteArrayInputStream(new byte[0]);
		}
	}

	private void drain(InputStream input, OutputStream output) throws IOException {
		int bytesRead = 0;
		byte[] buffer = new byte[DRAIN_BUFFER_SIZE];

		while ((bytesRead = input.read(buffer, 0, DRAIN_BUFFER_SIZE)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
	}

}