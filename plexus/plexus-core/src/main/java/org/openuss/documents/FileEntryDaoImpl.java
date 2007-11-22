// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;


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
		target.setFileSize(source.getFileSize());
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
		if (copyIfNull || sourceVO.getFileName() != null) {
			targetEntity.setFileName(sourceVO.getFileName());
		}
		if (copyIfNull || sourceVO.getFileSize() != null) {
			targetEntity.setFileSize(sourceVO.getFileSize());
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
			// entry.setRepositoryFile(RepositoryFile.Factory.newInstance());
		}
		return entry;
	}

	/**
	 * @see org.openuss.documents.FileEntryDao#fileInfoToEntity(org.openuss.documents.FileInfo,
	 *      org.openuss.documents.FileEntry)
	 */
	public void fileInfoToEntity(FileInfo source, FileEntry target, boolean copyIfNull) {
		super.fileInfoToEntity(source, target, copyIfNull);
		if (copyIfNull || source.getId() != null) {
			target.setId(source.getId());
		}
		if (copyIfNull || source.getName() != null) {
			target.setFileName(source.getName());
		}
		if (copyIfNull || source.getFileName() != null) {
			target.setFileName(source.getFileName());
		}
		if (copyIfNull || source.getContentType() != null) {
			target.setContentType(source.getContentType());
		}
		if (copyIfNull || source.getCreated() != null) {
			target.setCreated(source.getCreated());
		}
		if (copyIfNull || source.getModified() != null) {
			target.setModified(source.getModified());
		}
		if (copyIfNull || source.getDescription() != null) {
			target.setDescription(source.getDescription());
		}
		if (copyIfNull || source.getFileSize() != null) {
			target.setFileSize(source.getFileSize());
		}
	}

	/**
	 * @see org.openuss.documents.FileEntryDao#toFileInfo(org.openuss.documents.FileEntry,
	 *      org.openuss.documents.FileInfo)
	 */
	public void toFileInfo(FileEntry source, FileInfo target) {
		target.setId(source.getId());
		target.setName(source.getName());
		target.setFileName(source.getFileName());
		target.setAbsoluteName(source.getAbsoluteName());
		target.setPath(source.getPath());
		target.setDescription(source.getDescription());
		target.setContentType(source.getContentType());
		target.setCreated(source.getCreated());
		target.setModified(source.getModified());
		target.setFileSize(source.getFileSize());
		target.setExtension(source.getExtension());
		target.setReleased(source.isReleased());
	}

}