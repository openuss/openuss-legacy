// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

import java.util.Date;

/**
 * @see org.openuss.documents.Folder
 * @author ingo dueppe
 */
public class FolderDaoImpl extends org.openuss.documents.FolderDaoBase {
	/**
	 * @see org.openuss.documents.FolderDao#toFolderInfo(org.openuss.documents.Folder,
	 *      org.openuss.documents.FolderInfo)
	 */
	public void toFolderInfo(org.openuss.documents.Folder sourceEntity, org.openuss.documents.FolderInfo targetVO) {
		super.toFolderInfo(sourceEntity, targetVO);
		targetVO.setModified(sourceEntity.getModified());
		targetVO.setPath(sourceEntity.getPath());
		targetVO.setRoot(sourceEntity.isRoot());
	}

	/**
	 * @see org.openuss.documents.FolderDao#toFolderInfo(org.openuss.documents.Folder)
	 */
	public org.openuss.documents.FolderInfo toFolderInfo(final org.openuss.documents.Folder entity) {
		return super.toFolderInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private org.openuss.documents.Folder loadFolderFromFolderInfo(org.openuss.documents.FolderInfo folderInfo) {
		Folder folder = null;
		if (folderInfo.getId() != null) {
			folder = this.load(folderInfo.getId());
		}
		if (folder == null) {
			folder = Folder.Factory.newInstance();
		}
		return folder;
	}

	/**
	 * @see org.openuss.documents.FolderDao#folderInfoToEntity(org.openuss.documents.FolderInfo)
	 */
	public Folder folderInfoToEntity(FolderInfo folderInfo) {
		org.openuss.documents.Folder entity = this.loadFolderFromFolderInfo(folderInfo);
		this.folderInfoToEntity(folderInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.documents.FolderDao#folderInfoToEntity(org.openuss.documents.FolderInfo,
	 *      org.openuss.documents.Folder)
	 */
	public void folderInfoToEntity(FolderInfo sourceVO, Folder targetEntity, boolean copyIfNull) {
		super.folderInfoToEntity(sourceVO, targetEntity, copyIfNull);
		if (targetEntity.getCreated() == null) {
			targetEntity.setCreated(new Date());
		}
	}

	/**
	 * @see org.openuss.documents.FolderDao#toFolderEntryInfo(org.openuss.documents.Folder,
	 *      org.openuss.documents.FolderEntryInfo)
	 */
	public void toFolderEntryInfo(Folder sourceEntity, FolderEntryInfo targetVO) {
		super.toFolderEntryInfo(sourceEntity, targetVO);
		targetVO.setExtension(sourceEntity.getExtension());
		targetVO.setModified(sourceEntity.getModified());
	}

	/**
	 * @see org.openuss.documents.FolderDao#toFolderEntryInfo(org.openuss.documents.Folder)
	 */
	public FolderEntryInfo toFolderEntryInfo(final Folder entity) {
		return super.toFolderEntryInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private Folder loadFolderFromFolderEntryInfo(FolderEntryInfo folderEntryInfo) {
		Folder folder = null;
		if (folderEntryInfo.getId() != null) {
			folder = this.load(folderEntryInfo.getId());
		}
		if (folder == null) {
			folder = Folder.Factory.newInstance();
		}
		return folder;
	}

	/**
	 * @see org.openuss.documents.FolderDao#folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo)
	 */
	public Folder folderEntryInfoToEntity(FolderEntryInfo folderEntryInfo) {
		Folder entity = this.loadFolderFromFolderEntryInfo(folderEntryInfo);
		this.folderEntryInfoToEntity(folderEntryInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.documents.FolderDao#folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo,
	 *      org.openuss.documents.Folder)
	 */
	public void folderEntryInfoToEntity(FolderEntryInfo sourceVO, Folder targetEntity, boolean copyIfNull) {
		super.folderEntryInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}