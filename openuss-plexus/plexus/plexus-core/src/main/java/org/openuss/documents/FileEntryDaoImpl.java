// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

/**
 * @see org.openuss.documents.FileEntry
 * author ingo dueppe
 */
public class FileEntryDaoImpl extends org.openuss.documents.FileEntryDaoBase {
	/**
	 * @see org.openuss.documents.FileEntryDao#toFolderEntryInfo(org.openuss.documents.FileEntry,
	 *      org.openuss.documents.FolderEntryInfo)
	 */
	public void toFolderEntryInfo(org.openuss.documents.FileEntry sourceEntity,
			org.openuss.documents.FolderEntryInfo targetVO) {
		// @todo verify behavior of toFolderEntryInfo
		super.toFolderEntryInfo(sourceEntity, targetVO);
	}

	/**
	 * @see org.openuss.documents.FileEntryDao#toFolderEntryInfo(org.openuss.documents.FileEntry)
	 */
	public org.openuss.documents.FolderEntryInfo toFolderEntryInfo(final org.openuss.documents.FileEntry entity) {
		// @todo verify behavior of toFolderEntryInfo
		return super.toFolderEntryInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private org.openuss.documents.FileEntry loadFileEntryFromFolderEntryInfo(
			org.openuss.documents.FolderEntryInfo folderEntryInfo) {
		// @todo implement loadFileEntryFromFolderEntryInfo
		throw new java.lang.UnsupportedOperationException(
				"org.openuss.documents.loadFileEntryFromFolderEntryInfo(org.openuss.documents.FolderEntryInfo) not yet implemented.");

		/*
		 * A typical implementation looks like this:
		 * org.openuss.documents.FileEntry fileEntry =
		 * this.load(folderEntryInfo.getId()); if (fileEntry == null) {
		 * fileEntry = org.openuss.documents.FileEntry.Factory.newInstance(); }
		 * return fileEntry;
		 */
	}

	/**
	 * @see org.openuss.documents.FileEntryDao#folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo)
	 */
	public org.openuss.documents.FileEntry folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo folderEntryInfo) {
		// @todo verify behavior of folderEntryInfoToEntity
		org.openuss.documents.FileEntry entity = this.loadFileEntryFromFolderEntryInfo(folderEntryInfo);
		this.folderEntryInfoToEntity(folderEntryInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.documents.FileEntryDao#folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo,
	 *      org.openuss.documents.FileEntry)
	 */
	public void folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo sourceVO,
			org.openuss.documents.FileEntry targetEntity, boolean copyIfNull) {
		// @todo verify behavior of folderEntryInfoToEntity
		super.folderEntryInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}