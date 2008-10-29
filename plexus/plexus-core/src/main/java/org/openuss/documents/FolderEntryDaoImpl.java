// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

import org.apache.commons.lang.StringUtils;

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
		targetVO.setFileName(sourceEntity.getFileName());
		targetVO.setPath(sourceEntity.getPath());
		targetVO.setDescription(sourceEntity.getDescription());
		targetVO.setExtension(sourceEntity.getExtension());
		targetVO.setFileSize(sourceEntity.getFileSize());
		targetVO.setCreated(sourceEntity.getCreated());
		targetVO.setModified(sourceEntity.getModified());
		targetVO.setFolder(sourceEntity instanceof Folder);
		targetVO.setSizeAsString(sourceEntity.getSizeAsString());
		targetVO.setExtension(sourceEntity.getExtension());
		targetVO.setReleased(sourceEntity.isReleased());
		targetVO.setReleaseDate(sourceEntity.getCreated());
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
				return new FolderImpl();
			} else {
				return new FileEntryImpl();
		}
		return entry;
	}

	private String shortenInMiddle(String str, int maxLength){
		if (str==null){
			return null;
		}
		if (str.length()<maxLength){
			return str;
		}
		int halfLength = maxLength / 2;
		String beginning = "";
		String ending = "";
		ending = str.substring(str.length()-halfLength);
		beginning = str.substring(0, str.length()-halfLength);
		return StringUtils.abbreviate(beginning, maxLength-halfLength)+ending;
	}
	
	/**
	 * @see org.openuss.documents.FolderEntryDao#folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo)
	 */
	public org.openuss.documents.FolderEntry folderEntryInfoToEntity(
			org.openuss.documents.FolderEntryInfo folderEntryInfo) {
		// @todo verify behavior of folderEntryInfoToEntity
		org.openuss.documents.FolderEntry entity = this.loadFolderEntryFromFolderEntryInfo(folderEntryInfo);
		folderEntryInfo.setName(shortenInMiddle(folderEntryInfo.getName(), 255));
		folderEntryInfo.setDescription(shortenInMiddle(folderEntryInfo.getDescription(), 1000));
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
		sourceVO.setName(shortenInMiddle(sourceVO.getName(), 255));
		sourceVO.setDescription(shortenInMiddle(sourceVO.getDescription(), 1000));
		super.folderEntryInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}