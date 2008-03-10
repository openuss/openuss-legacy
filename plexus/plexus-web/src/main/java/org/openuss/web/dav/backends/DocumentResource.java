package org.openuss.web.dav.backends;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FileInfo;
import org.openuss.documents.Folder;
import org.openuss.documents.FolderDao;
import org.openuss.documents.FolderEntry;
import org.openuss.documents.FolderInfo;
import org.openuss.web.Constants;
import org.openuss.web.dav.CollisionAvoidingSimpleWebDAVResource;
import org.openuss.web.dav.IOContextImpl;
import org.openuss.web.dav.WebDAVContext;
import org.openuss.web.dav.WebDAVUtils;
import org.openuss.webdav.IOContext;
import org.openuss.webdav.WebDAVConstants;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.openuss.webdav.WebDAVResourceException;
import org.openuss.webdav.WebDAVStatusCodes;
import org.springframework.web.context.WebApplicationContext;

/**
 * A WebDAV resource in the org.openuss.document system.
 */
public class DocumentResource extends CollisionAvoidingSimpleWebDAVResource {
	private Logger logger = Logger.getLogger(DocumentResource.class);
	protected DocumentService documentService;
	protected FolderDao folderDao;
	
	protected FolderEntry entry;
	protected Collection<FolderEntry> subEntriesCache = null;
	
	public DocumentResource(WebDAVContext context, WebDAVPath path, FolderEntry entry) {
		super(context, path, entry.getId());
		this.entry = entry;
		
		documentService = (DocumentService) getWAC().getBean(Constants.DOCUMENT_SERVICE, DocumentService.class);
		folderDao = (FolderDao) getWAC().getBean("folderDao", FolderDao.class);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#createCollectionImpl(java.lang.String)
	 */
	@Override
	protected WebDAVResource createCollectionImpl(String name)
			throws WebDAVResourceException {
		
		FolderInfo fi = documentService.getFolder(entry);
		
		FolderInfo newFolderInfo = new FolderInfo();
		newFolderInfo.setName(name);
		
		try {
			documentService.createFolder(newFolderInfo, fi);
		} catch (DocumentApplicationException e) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this);
		}
		
		subEntriesCache = null;
		
		FolderEntry childEntry = ((Folder) entry).getFolderEntryByName(name);
		
		return new DocumentResource(getContext(), path, childEntry);
	}

	@Override
	protected WebDAVResource createFileImpl(String name)
			throws WebDAVResourceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void deleteImpl() throws WebDAVResourceException {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#readContentImpl()
	 */
	@Override
	protected IOContext readContentImpl() throws WebDAVResourceException,
			IOException {
		if (isCollection()) {
			return readCollectionContent();
		} else {
			// this is a file
			IOContextImpl res = new IOContextImpl();
			
			FileInfo fi = documentService.getFileEntry(entry.getId(), true);
			res.setInputStream(fi.getInputStream());
			res.setContentLength(entry.getFileSize());
			Timestamp mts = WebDAVUtils.timestampToDate(entry.getModified());
			res.setModificationTime(mts);
			res.setContentType(fi.getContentType());
			
			return res;
		}
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getContentType()
	 */
	public String getContentType() {
		if (isCollection()) {
			return WebDAVConstants.MIMETYPE_DIRECTORY;
		}
		FileInfo fi = documentService.getFileEntry(entry.getId(), true);
		return fi.getContentType();
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#simpleGetProperties(java.util.Set)
	 */
	@Override
	protected Map<String, String> simpleGetProperties(Set<String> propNames) {
		Map<String,String> resMap = new TreeMap<String,String>();
		
		if ((propNames == null) || propNames.contains(WebDAVConstants.PROPERTY_DISPLAYNAME)) {
			String description = entry.getDescription();
			
			if (StringUtils.isNotEmpty(description)) {
				resMap.put(WebDAVConstants.PROPERTY_DISPLAYNAME, description);
			}
		}
		
		if ((propNames == null) || propNames.contains(WebDAVConstants.PROPERTY_GETCONTENTLENGTH)) { 
			resMap.put(WebDAVConstants.PROPERTY_GETCONTENTLENGTH, String.valueOf(entry.getFileSize()));
		}
		// FIXME date format
		/*if ((propNames == null) || propNames.contains(WebDAVConstants.PROPERTY_CREATIONDATE)) { 
			resMap.put(WebDAVConstants.PROPERTY_CREATIONDATE, WebDAVUtils.dateToRFC1123String(entry.getCreated()));
		}*/
		if ((propNames == null) || propNames.contains(WebDAVConstants.PROPERTY_GETLASTMODIFIED)) { 
			resMap.put(WebDAVConstants.PROPERTY_GETLASTMODIFIED, WebDAVUtils.dateToRFC1123String(entry.getModified()));
		}
		
		return resMap;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#writeContentImpl(org.openuss.webdav.IOContext)
	 */
	@Override
	protected void writeContentImpl(IOContext ioc)
			throws WebDAVResourceException {
		
		FileInfo fi = documentService.getFileEntry(entry.getId(), false);
		
		fi.setContentType(ioc.getContentType());
		fi.setInputStream(ioc.getInputStream());
		// TODO checkme
		try {
			documentService.saveFileEntry(fi);
		} catch (DocumentApplicationException e) {
			logger.error("");
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this);
		}
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#isCollection()
	 */
	public boolean isCollection() {
		return (entry instanceof Folder);
	}

	public boolean isReadable() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isWritable() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.CollisionAvoidingSimpleWebDAVResource#getChild(long, java.lang.String, org.openuss.webdav.WebDAVPath)
	 */
	@Override
	protected WebDAVResource getChild(long id, String sname, WebDAVPath path) {
		if (!isCollection()) {
			return null;
		}
		
		if (id != ID_NONE) {
			for (FolderEntry fe : getSubEntries()) {
				if (fe.getId() == id) {
					return new DocumentResource(getContext(), path, fe);
				}
			}
		} else {
			for (FolderEntry fe : getSubEntries()) {
				if (sname.equals(sanitizeName(getNameByFolderEntry(fe)))) {
					return new DocumentResource(getContext(), path, fe);
				}
			}
		}
			
		return null;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getRawChildNames()
	 */
	@Override
	protected Map<Long, String> getRawChildNames() {
		if (!isCollection()) {
			return null;
		}
		Map<Long,String> res = new TreeMap<Long, String>();
		
		for (FolderEntry fe : getSubEntries()) {
			res.put(fe.getId(), getNameByFolderEntry(fe));
		}
		
		return res;
	}
	
	/**
	 * @return All FileEntry objects of all the children.
	 */
	protected Collection<FolderEntry> getSubEntries() {
		if ((subEntriesCache == null) && (entry instanceof Folder)) {
			subEntriesCache = ((Folder)entry).getEntries(); 
		}
		
		return subEntriesCache;
	}
	
	/**
	 * @param fe The FolderEntry object that should be created.
	 * @return The name to choose for the object.
	 */
	public static String getNameByFolderEntry(FolderEntry fe) {
		return fe.getFileName();
	}
}
