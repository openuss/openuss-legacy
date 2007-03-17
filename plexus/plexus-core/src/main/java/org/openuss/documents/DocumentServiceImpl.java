// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.openuss.framework.utilities.DomainObjectUtility;

/**
 * @see org.openuss.documents.DocumentsService
 */
public class DocumentServiceImpl extends org.openuss.documents.DocumentServiceBase {

	@Override
	protected void handleAddFileEntry(FileEntry file, Folder parent) throws Exception {
		Validate.notNull(file, "Parameter file must not be null!");
		Validate.notNull(parent, "Parameter parent must not be null!");
		
		parent = getFolderDao().load(parent.getId());
		parent.addFolderEntry(file);
		
		getFolderDao().update(parent);
	}

	@Override
	protected void handleAddFolderEntry(Folder folder, Folder parent) throws Exception {
		Validate.notNull(folder, "Parameter folder must not be null!");
		Validate.notNull(parent, "Parameter parent must not be null, root folder can only be created according with an domain object!");
		
		parent = getFolderDao().load(parent.getId());
		parent.addFolderEntry(folder);
		
		getFolderDao().create(folder);
		getFolderDao().update(parent);
	}

	@Override
	protected FileEntry handleGetFileEntry(FolderEntryInfo folderEntry) throws Exception {
		Validate.notNull(folderEntry, "Parameter folderEntry must not be null!");
		Validate.notNull(folderEntry.getId(), "Parameter folderEntry must be an persisted entity with a valid identifier!");

		return getFileEntryDao().load(folderEntry.getId());
	}

	@Override
	protected Folder handleGetFolder(FolderEntryInfo folderEntry) throws Exception {
		Validate.notNull(folderEntry, "Parameter folderEntry must not be null!");
		Validate.notNull(folderEntry.getId(), "Parameter folderEntry must be an persisted entity with a valid identifier!");
		return getFolderDao().load(folderEntry.getId());
	}

	@Override
	protected List handleGetFolderPath(Folder folder) throws Exception {
		Validate.notNull(folder, "Parameter folder must not be null!");
		List folderPath = new LinkedList();
		folder = getFolderDao().load(folder.getId());
		while (folder != null) {
			folderPath.add(0,folder);
			folder = folder.getParent();
		}
		return folderPath;
	}

	@Override
	protected void handleRemoveFileEntry(FolderEntryInfo folderEntry) throws Exception {
		getFolderEntryDao().remove(folderEntry.getId());
	}

	@Override
	protected void handleSaveFolderEntry(FolderEntry folderEntry) throws Exception {
		Validate.notNull(folderEntry, "Parameter folderEntry must not be null!");
		Validate.notNull(folderEntry.getId(), "Parameter folderEntry must be an persisted entity with a valid identifier!");
		getFolderEntryDao().update(folderEntry);
	}

	@Override
	protected List handleGetFolderEntries(Object domainObject, Folder folder) throws Exception {
		if (folder == null) {
			folder = getRootFolderForDomainObject(domainObject);
		}
		if (folder == null) {
			throw new DocumentServiceException("message_error_folder_not_found");
		}
		List entries = getFolderEntryDao().findByParent(FolderEntryDao.TRANSFORM_FOLDERENTRYINFO, folder);
		if (entries == null) {
			entries = new ArrayList();
		}
		return entries;
	}

	@Override
	protected Folder handleGetFolder(Object domainObject, FolderEntryInfo folderEntry) throws Exception {
		if (folderEntry == null) {
			return getRootFolderForDomainObject(domainObject);
		} else {
			return getFolderDao().load(folderEntry.getId());
		}
	}

	private Folder getRootFolderForDomainObject(Object domainObject) throws IllegalAccessException, InvocationTargetException {
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


}