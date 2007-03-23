// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

/**
 * @see org.openuss.documents.FolderEntry
 * @author ingo dueppe
 */
public class FolderEntryDaoImpl extends org.openuss.documents.FolderEntryDaoBase {
	/**
	 * @see org.openuss.documents.FolderEntryDao#toFolderEntryInfo(org.openuss.documents.FolderEntry,
	 *      org.openuss.documents.FolderEntryInfo)
	 */
	public void toFolderEntryInfo(FolderEntry sourceEntity, FolderEntryInfo targetVO) {
		super.toFolderEntryInfo(sourceEntity, targetVO);
		targetVO.setName(sourceEntity.getName());
		targetVO.setPath(sourceEntity.getPath());
		targetVO.setDescription(sourceEntity.getDescription());
		targetVO.setExtension(sourceEntity.getExtension());
		targetVO.setSize(sourceEntity.getSize());
		targetVO.setCreated(sourceEntity.getCreated());
		targetVO.setModified(sourceEntity.getModified());
		targetVO.setFolder(sourceEntity instanceof Folder);
		targetVO.setSizeAsString(sourceEntity.getSizeAsString());
		targetVO.setExtension(sourceEntity.getExtension());

		targetVO.setReleaseDate(sourceEntity.getCreated());
		if (sourceEntity instanceof FileEntry) {
			FileEntry fileEntry = (FileEntry) sourceEntity;
			targetVO.setRepositoryFileId(fileEntry.getRepositoryFile().getId());
			targetVO.setReleased(fileEntry.isReleased());
		} else {
			targetVO.setReleased(true);
		}
	}

	/**
	 * @see org.openuss.documents.FolderEntryDao#toFolderEntryInfo(org.openuss.documents.FolderEntry)
	 */
	public org.openuss.documents.FolderEntryInfo toFolderEntryInfo(final org.openuss.documents.FolderEntry entity) {
		return super.toFolderEntryInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private FolderEntry loadFolderEntryFromFolderEntryInfo(FolderEntryInfo folderEntryInfo) {
		FolderEntry entry = null;
		if (folderEntryInfo.getId() != null) {
			entry = this.load(folderEntryInfo.getId());
		}
		if (entry == null)
			if (folderEntryInfo.isFolder()) {
				return Folder.Factory.newInstance();
			} else {
				return FileEntry.Factory.newInstance();
		}
		return entry;
	}

	/**
	 * @see org.openuss.documents.FolderEntryDao#folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo)
	 */
	public org.openuss.documents.FolderEntry folderEntryInfoToEntity(
			org.openuss.documents.FolderEntryInfo folderEntryInfo) {
		// @todo verify behavior of folderEntryInfoToEntity
		org.openuss.documents.FolderEntry entity = this.loadFolderEntryFromFolderEntryInfo(folderEntryInfo);
		this.folderEntryInfoToEntity(folderEntryInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.documents.FolderEntryDao#folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo,
	 *      org.openuss.documents.FolderEntry)
	 */
	public void folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo sourceVO,
			org.openuss.documents.FolderEntry targetEntity, boolean copyIfNull) {
		// @todo verify behavior of folderEntryInfoToEntity
		super.folderEntryInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}