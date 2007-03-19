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
	public void toFolderEntryInfo(FolderEntry sourceEntity,	FolderEntryInfo targetVO) {
		super.toFolderEntryInfo(sourceEntity, targetVO);
		targetVO.setName(sourceEntity.getName());
		targetVO.setDescription(sourceEntity.getDescription());
		targetVO.setExtension(sourceEntity.getExtension());
		targetVO.setSize(sourceEntity.getSize());
		targetVO.setCreated(sourceEntity.getCreated());
		targetVO.setModified(sourceEntity.getModified());
		targetVO.setFolder(sourceEntity instanceof Folder);
		
		targetVO.setExtension(sourceEntity.getExtension());
		if (sourceEntity instanceof FileEntry) {
			FileEntry fileEntry = (FileEntry) sourceEntity;
			targetVO.setRepositoryFileId(fileEntry.getId());
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
	private org.openuss.documents.FolderEntry loadFolderEntryFromFolderEntryInfo(
			org.openuss.documents.FolderEntryInfo folderEntryInfo) {
		// @todo implement loadFolderEntryFromFolderEntryInfo
		throw new java.lang.UnsupportedOperationException(
				"org.openuss.documents.loadFolderEntryFromFolderEntryInfo(org.openuss.documents.FolderEntryInfo) not yet implemented.");

		/*
		 * A typical implementation looks like this:
		 * org.openuss.documents.FolderEntry folderEntry =
		 * this.load(folderEntryInfo.getId()); if (folderEntry == null) {
		 * folderEntry =
		 * org.openuss.documents.FolderEntry.Factory.newInstance(); } return
		 * folderEntry;
		 */
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