package org.openuss.web.dav;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.acegisecurity.acl.AclManager;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FileInfo;
import org.openuss.documents.Folder;
import org.openuss.documents.FolderDao;
import org.openuss.documents.FolderEntry;
import org.openuss.documents.FolderEntryDao;
import org.openuss.documents.FolderEntryInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.foundation.DomainObject;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.web.Constants;
import org.openuss.webdav.IOContext;
import org.openuss.webdav.IOContextImpl;
import org.openuss.webdav.SimpleWebDAVResource;
import org.openuss.webdav.WebDAVConstants;
import org.openuss.webdav.WebDAVContext;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVPathImpl;
import org.openuss.webdav.WebDAVResource;
import org.openuss.webdav.WebDAVResourceException;
import org.openuss.webdav.WebDAVStatusCodes;
import org.openuss.webdav.WebDAVUtils;

/**
 * A WebDAV resource in the org.openuss.document system.
 */
public class DocumentResource extends SimpleWebDAVResource {
	private Logger logger = Logger.getLogger(DocumentResource.class);
	
	protected DocumentService documentService;
	
	protected DomainObject domainObj;
	
	protected FolderEntryInfo info;
	protected Collection<FolderEntryInfo> subEntriesCache = null;
	
	protected DocumentResource(WebDAVContext context, WebDAVPath path, DomainObject domainObj, FolderEntryInfo info) {
		super(context, path);
		this.info = info;
		
		documentService = (DocumentService) getWAC().getBean(Constants.DOCUMENT_SERVICE, DocumentService.class);
	}
	
	/**
	 * @param context The context of the current query.
	 * @param path The path of this object.
	 * @param domainObject The domain object (course etc.) whose associated documents should be represented.
	 * @return The DocumentResource associated to the given domain object.
	 */
	public static DocumentResource createByDomainObject(WebDAVContext context, WebDAVPath path, DomainObject domainObject) {
		DocumentService documentService = (DocumentService) getWAC().getBean(Constants.DOCUMENT_SERVICE, DocumentService.class);;
		
		FolderInfo fi = documentService.getFolder(domainObject);
		
		
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#createCollectionImpl(java.lang.String)
	 */
	@Override
	protected WebDAVResource createCollectionImpl(String name)
			throws WebDAVResourceException {
		
		FolderInfo newFolderInfo = new FolderInfo();
		newFolderInfo.setName(name);
		newFolderInfo.setDescription(name);
		newFolderInfo.setCreated(new Date());
		
		try {
			documentService.createFolder(newFolderInfo, info);
		} catch (DocumentApplicationException e) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this, "Internal error when creating a new folder: " + e.getMessage());
		}
		
		subEntriesCache = null;
		
		FolderEntry childEntry = ((Folder) info).getFolderEntryByName(name);
		
		return new DocumentResource(getContext(), path, childEntry);
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#createFileImpl(java.lang.String, org.openuss.webdav.IOContext)
	 */
	@Override
	protected WebDAVResource createFileImpl(String name, IOContext ioc)
			throws WebDAVResourceException {
		FolderInfo folderInfo = folderDao.toFolderInfo((Folder)info);
		
		// The final input stream
		InputStream is;

		// Write to temp file
		FileOutputStream fos;
		try {
			File tmpf = File.createTempFile("openuss", "webdav-documentresource");
			
			fos = new FileOutputStream(tmpf);
			IOUtils.copyLarge(ioc.getInputStream(), fos);
			fos.close();
		} catch (IOException ioe) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this, ioe);
		}
		*/

		
		// TODO Ask Ingo why Hibernate queries available()
		is = ioc.getInputStream();
		FileInfo newFileInfo = new FileInfo();
		newFileInfo.setFileName(name);
		newFileInfo.setDescription(WebDAVPathImpl.stripExtension(name));
		newFileInfo.setContentType("text/plain"); // TODO ioc.getContentType()
		//newFileInfo.setFileSize((int) ioc.getContentLength());
		int t = 0;
		try {
			t = is.available();
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		newFileInfo.setFileSize(t);
		newFileInfo.setCreated(new Date());
		newFileInfo.setModified(new Date());
		newFileInfo.setInputStream(is);
		
		try {
			documentService.createFileEntry(newFileInfo, folderInfo);
		} catch (DocumentApplicationException e) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this, "Internal error when creating a new folder: " + e.getMessage());
		}
		
		subEntriesCache = null;
		
		FolderEntry childEntry = ((Folder) info).getFolderEntryByName(name);
		
		return new DocumentResource(getContext(), path, childEntry);
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#deleteImpl()
	 */
	@Override
	protected void deleteImpl() throws WebDAVResourceException {
		try {
			FolderEntryInfo fei = folderEntryDao.toFolderEntryInfo(info);
			
			Collection<FolderEntryInfo> entryInfosToDelete = new ArrayList<FolderEntryInfo>(1);
			entryInfosToDelete.add(fei);
			documentService.removeFolderEntries(entryInfosToDelete);
		} catch (DocumentApplicationException e) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this);
		}
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
			
			FileInfo fi = documentService.getFileEntry(info.getId(), true);
			res.setInputStream(fi.getInputStream());
			res.setContentLength(info.getFileSize());
			Timestamp mts = WebDAVUtils.timestampToDate(info.getModified());
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
		FileInfo fi = documentService.getFileEntry(info.getId(), true);
		return fi.getContentType();
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#simpleGetProperties(java.util.Set)
	 */
	@Override
	protected Map<String, String> simpleGetProperties(Set<String> propNames) {
		Map<String,String> resMap = new TreeMap<String,String>();
		
		if ((propNames == null) || propNames.contains(WebDAVConstants.PROPERTY_DISPLAYNAME)) {
			String description = info.getDescription();
			
			if (StringUtils.isNotEmpty(description)) {
				resMap.put(WebDAVConstants.PROPERTY_DISPLAYNAME, description);
			}
		}
		
		if ((propNames == null) || propNames.contains(WebDAVConstants.PROPERTY_GETCONTENTLENGTH)) { 
			resMap.put(WebDAVConstants.PROPERTY_GETCONTENTLENGTH, String.valueOf(info.getFileSize()));
		}
		if ((propNames == null) || propNames.contains(WebDAVConstants.PROPERTY_CREATIONDATE)) { 
			resMap.put(WebDAVConstants.PROPERTY_CREATIONDATE, WebDAVUtils.dateToInternetString(info.getCreated()));
		}
		if ((propNames == null) || propNames.contains(WebDAVConstants.PROPERTY_GETLASTMODIFIED)) { 
			resMap.put(WebDAVConstants.PROPERTY_GETLASTMODIFIED, WebDAVUtils.dateToRFC1123String(info.getModified()));
		}
		
		return resMap;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#writeContentImpl(org.openuss.webdav.IOContext)
	 */
	@Override
	protected void writeContentImpl(IOContext ioc)
			throws WebDAVResourceException {
		
		FileInfo fi = documentService.getFileEntry(info.getId(), false);
		
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
		return (info instanceof Folder);
	}

	public boolean isReadable() {
		/*if (isCollection()) {
			FolderInfo folderI = documentService.getF(entry.getId(), false);
		} else {
			FileInfo fileI = documentService.getFileEntry(entry.getId(), false);
		}
		return AcegiUtils.hasPermission(fi, new Integer[] { LectureAclEntry.READ });*/
		return true;
	}

	public boolean isWritable() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getChild(java.lang.String, org.openuss.webdav.WebDAVPath)
	 */
	@Override
	protected WebDAVResource getChild(String name, WebDAVPath path) {
		if (!isCollection()) {
			return null;
		}
		
		for (FolderEntry fe : getSubEntries()) {
			if (name.equals(getNameByFolderEntry(fe))) {
				return new DocumentResource(getContext(), path, fe);
			}
		}
			
		return null;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getChildren()
	 */
	@Override
	public Set<WebDAVResource> getChildren() {
		Set<WebDAVResource> res = new HashSet<WebDAVResource>();
		
		for (FolderEntry fe : getSubEntries()) {
			WebDAVPath nextPath = path.concat(getNameByFolderEntry(fe)).asResolved();
			WebDAVResource c = new DocumentResource(getContext(), nextPath, fe);
			
			if (!c.isReadable()) {
				continue;
			}
			
			res.add(c);
		}
		
		return res;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#hasChild(java.lang.String)
	 */
	public boolean hasChild(String name) {
		if (!isCollection()) {
			return false;
		}
		
		FolderEntry newEntry = FolderEntry.Factory.newInstance();
		newEntry.setName(name);
		newEntry.setDescription(name);
		
		return (!((Folder) info).canAdd(newEntry));
	}
	
	/**
	 * @return All FileEntry objects of all the children.
	 */
	protected Collection<FolderEntry> getSubEntries() {
		if ((subEntriesCache == null) && (info instanceof Folder)) {
			documentService.getFolderEntries(domainObject, info);
			subEntriesCache = info).getEntries(); 
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
