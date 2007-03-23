// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

import java.io.File;

import org.openuss.repository.RepositoryFile;

/**
 * @see org.openuss.documents.FileEntry author ingo dueppe
 * @author ingo dueppe
 */
public class FileEntryDaoImpl extends org.openuss.documents.FileEntryDaoBase {
	/**
	 * @see org.openuss.documents.FileEntryDao#toFolderEntryInfo(org.openuss.documents.FileEntry,
	 *      org.openuss.documents.FolderEntryInfo)
	 */
	public void toFolderEntryInfo(FileEntry source, FolderEntryInfo target) {
		super.toFolderEntryInfo(source, target);
		target.setName(source.getName());
		target.setDescription(source.getDescription());
		target.setCreated(source.getCreated());
		target.setModified(source.getModified());
		target.setExtension(source.getExtension());
		target.setPath(source.getPath());
		target.setRepositoryFileId(source.getRepositoryFile().getId());
		target.setSize(source.getSize());
		target.setSizeAsString(source.getSizeAsString());
		target.setFolder(false);
		target.setReleased(source.isReleased());
		target.setReleaseDate(source.getCreated());
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private FileEntry loadFileEntryFromFolderEntryInfo(FolderEntryInfo folderEntryInfo) {
		FileEntry entry = null;
		if (folderEntryInfo.getId() != null) {
			entry = this.load(folderEntryInfo.getId());
		}
		if (entry == null) {
			entry = FileEntry.Factory.newInstance();
		}
		return entry;
	}

	/**
	 * @see org.openuss.documents.FileEntryDao#folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo)
	 */
	public FileEntry folderEntryInfoToEntity(FolderEntryInfo folderEntryInfo) {
		FileEntry entity = this.loadFileEntryFromFolderEntryInfo(folderEntryInfo);
		this.folderEntryInfoToEntity(folderEntryInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.documents.FileEntryDao#folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo,
	 *      org.openuss.documents.FileEntry)
	 */
	public void folderEntryInfoToEntity(FolderEntryInfo sourceVO, FileEntry targetEntity, boolean copyIfNull) {
		super.folderEntryInfoToEntity(sourceVO, targetEntity, copyIfNull);

		RepositoryFile file = targetEntity.getRepositoryFile();
		if (file != null) {
			file.setName(sourceVO.getName());
			file.setFileName(sourceVO.getName());
			file.setFileSize(sourceVO.getSize());
			file.setDescription(sourceVO.getDescription());
			file.setCreated(sourceVO.getCreated());
			file.setModified(sourceVO.getModified());
		} else {
			file = RepositoryFile.Factory.newInstance();
			file.setId(sourceVO.getRepositoryFileId());
			targetEntity.setRepositoryFile(file);
		}
	}

	public FileEntry fileInfoToEntity(FileInfo fileInfo) {
		FileEntry entity = this.loadFileEntryFromFileInfo(fileInfo);
		this.fileInfoToEntity(fileInfo, entity, true);
		return entity;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private FileEntry loadFileEntryFromFileInfo(FileInfo fileInfo) {
		FileEntry entry = null;
		if (fileInfo.getId() != null) {
			entry = this.load(fileInfo.getId());
		}
		if (entry == null) {
			entry = FileEntry.Factory.newInstance();
			entry.setRepositoryFile(RepositoryFile.Factory.newInstance());
		}
		return entry;
	}

	/**
	 * @see org.openuss.documents.FileEntryDao#fileInfoToEntity(org.openuss.documents.FileInfo,
	 *      org.openuss.documents.FileEntry)
	 */
	public void fileInfoToEntity(FileInfo source, FileEntry target, boolean copyIfNull) {
		if (target.getRepositoryFile() == null) {
			target.setRepositoryFile(RepositoryFile.Factory.newInstance());
		}
		RepositoryFile file = target.getRepositoryFile();

		if (copyIfNull || source.getName() != null) {
			file.setName(extractName(source.getName()));
		}
		if (copyIfNull || source.getFileName() != null) {
			file.setFileName(extractFileName(source.getName()));
		}
		if (copyIfNull || source.getCreated() != null) {
			file.setCreated(source.getCreated());
		}
		if (copyIfNull || source.getModified() != null) {
			file.setModified(source.getModified());
		}
		if (copyIfNull || source.getSize() != null) {
			file.setFileSize(source.getSize());
		}
		if (copyIfNull || source.getContentType() != null) {
			file.setContentType(source.getContentType());
		}
		if (copyIfNull || source.getInputStream() != null) {
			file.setInputStream(source.getInputStream());
		}
		if (copyIfNull || source.getDescription() != null) {
			file.setDescription(source.getDescription());
		}
	}

	private String extractFileName(String name) {
		File file = new File(name);
		return file.getName();
	}

	private String extractName(String name) {
		File file = new File(name);
		return file.getName();
	}

	/**
	 * @see org.openuss.documents.FileEntryDao#toFileInfo(org.openuss.documents.FileEntry,
	 *      org.openuss.documents.FileInfo)
	 */
	public void toFileInfo(FileEntry source, FileInfo target) {
		target.setId(source.getId());
		target.setName(source.getName());
		target.setFileName(source.getFileName());
		target.setDescription(source.getDescription());
		target.setContentType(source.getContentType());
		target.setCreated(source.getCreated());
		target.setModified(source.getModified());
		target.setSize(source.getSize());
		if (source.getRepositoryFile() != null) {
			target.setInputStream(source.getRepositoryFile().getInputStream());
		}
	}

}