package org.openuss.web.dav;

import java.io.File;
import java.io.FileInputStream;
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

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderEntryInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.foundation.DomainObject;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.security.acl.LectureAclEntry;
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
	protected DocumentService documentService;
	
	protected final DomainObject domainObj;
	
	protected final FolderEntryInfo info;
	protected Collection<FolderEntryInfo> subEntriesCache = null;
	
	protected DocumentResource(WebDAVContext context, WebDAVPath path, DomainObject domainObj, FolderEntryInfo info) {
		super(context, path);
		this.domainObj = domainObj;
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
		DocumentService documentService = (DocumentService) context.getWAC().getBean(Constants.DOCUMENT_SERVICE, DocumentService.class);;
		
		FolderInfo fi = documentService.getFolder(domainObject);
		
		return new DocumentResource(context, path, domainObject, folderInfoToFolderEntryInfo(fi));
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
			documentService.createFolder(newFolderInfo, folderEntryInfoToFolderInfo(info));
		} catch (DocumentApplicationException e) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this, "Internal error when creating a new folder: " + e.getMessage());
		}
		
		subEntriesCache = null;
		
		return getChild(name, getPath().concat(name).asResolved());
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#createFileImpl(java.lang.String, org.openuss.webdav.IOContext)
	 */
	@Override
	protected WebDAVResource createFileImpl(String name, IOContext ioc)
			throws WebDAVResourceException {
		long len; // The size of the uploaded file
		InputStream is = null;
		File tmpf = null;
		WebDAVResource res;

		try {
			// Write to temp file to correct bug in Hibernate.createBlob().
			tmpf = writeToTmpFile(ioc.getInputStream());
			
			len = tmpf.length();
			checkFileSize(len);
				
			is = new FileInputStream(tmpf); // TODO bug
			
			// Construct fileInfo object
			FileInfo newFileInfo = createNewFileInfo(name, ioc, is, len);
			
			FolderInfo fi = folderEntryInfoToFolderInfo(info);
			documentService.createFileEntry(newFileInfo, fi);
			
			subEntriesCache = null;
			
			res = getChild(name, getPath().concat(name).asResolved());
		} catch (IOException ioe) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this, "Internal error when caching the input");
		} catch (DocumentApplicationException e) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this, "Internal error when creating a new file: " + e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// Catch silently
				}
			}
			if (tmpf != null) {
				tmpf.delete();
			}
		}
		
		return res;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#deleteImpl()
	 */
	@Override
	protected void deleteImpl() throws WebDAVResourceException {
		try {
			Collection<FolderEntryInfo> entryInfosToDelete = new ArrayList<FolderEntryInfo>(1);
			entryInfosToDelete.add(info);
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
		
		if ((!isCollection()) &&
				((propNames == null) || propNames.contains(WebDAVConstants.PROPERTY_GETCONTENTLENGTH))) {
			Integer fileSize = info.getFileSize();
			String sizeStr = (fileSize != null) ? String.valueOf(fileSize) : null;
			
			resMap.put(WebDAVConstants.PROPERTY_GETCONTENTLENGTH, sizeStr);
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
		long len; // The size of the uploaded file
		InputStream is = null;
		File tmpf = null;

		try {
			// Write to temp file to correct bug in Hibernate.createBlob().
			tmpf = writeToTmpFile(ioc.getInputStream());
				
			len = tmpf.length();
			checkFileSize(len);
				
			is = new FileInputStream(tmpf);
			fi.setInputStream(is);
			fi.setFileSize((int) len); 
			fi.setModified(new Date());
			
			documentService.saveFileEntry(fi);
		} catch (IOException ioe) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this, ioe);
		} catch (DocumentApplicationException e) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this, "Internal error when writing to a file: " + e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// Catch silently
				}
			}
			if (tmpf != null) {
				tmpf.delete();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#isCollection()
	 */
	public boolean isCollection() {
		return info.isFolder();
	}

	public boolean isReadable() {
		return AcegiUtils.hasPermission(info, new Integer[] { LectureAclEntry.READ });
	}

	public boolean isWritable() {
		return AcegiUtils.hasPermission(info, new Integer[] { LectureAclEntry.ASSIST });
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getChild(java.lang.String, org.openuss.webdav.WebDAVPath)
	 */
	@Override
	protected WebDAVResource getChild(String name, WebDAVPath path) {
		if (!isCollection()) {
			return null;
		}
		
		for (FolderEntryInfo fei : getSubEntries()) {
			if (name.equals(getNameByFolderEntryInfo(fei))) {
				return new DocumentResource(getContext(), path, domainObj, fei);
			}
		}
			
		return null;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getChildren()
	 */
	@Override
	public Set<WebDAVResource> getChildren() {
		if (!isCollection()) {
			return null;
		}
		
		Set<WebDAVResource> res = new HashSet<WebDAVResource>();
		
		for (FolderEntryInfo fei : getSubEntries()) {
			WebDAVPath nextPath = path.concat(getNameByFolderEntryInfo(fei)).asResolved();
			WebDAVResource c = new DocumentResource(getContext(), nextPath, domainObj, fei);
			
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
		for (FolderEntryInfo fei : getSubEntries()) {
			if (name.equals(getNameByFolderEntryInfo(fei))) {
				return true;
			}
		}
		
		return false; 
	}
	
	/**
	 * @return All FileEntry objects of all the children.
	 */
	@SuppressWarnings("unchecked")
	protected Collection<FolderEntryInfo> getSubEntries() {
		if ((subEntriesCache == null) && (isCollection())) {
			subEntriesCache = documentService.getFolderEntries(domainObj, folderEntryInfoToFolderInfo(info));
		}
		
		return subEntriesCache;
	}
	
	/**
	 * @param fei The FolderEntry object that should be created.
	 * @return The name to choose for the object.
	 */
	public static String getNameByFolderEntryInfo(FolderEntryInfo fei) {
		return fei.getFileName();
	}
	
	/**
	 * Creates a mockup of a FolderInfo object from a (more detailled) FolderEntryInfo one.
	 * 
	 * @param fei The info object to copy.
	 * @return fei as a FolderInfo object.
	 */
	private static FolderInfo folderEntryInfoToFolderInfo(FolderEntryInfo fei) {
		FolderInfo res = new FolderInfo();
		
		res.setCreated(fei.getCreated());
		res.setDescription(fei.getDescription());
		res.setId(fei.getId());
		res.setModified(fei.getModified());
		res.setName(fei.getName());
		res.setPath(fei.getPath());
		
		return res;
	}
	
	/**
	 * Creates a mockup of a FolderEntryInfo object from a (less detailled) FolderInfo one.
	 * 
	 * @param fi The info object to copy.
	 * @return fi as a FolderEntryInfo object.
	 */
	private static FolderEntryInfo folderInfoToFolderEntryInfo(FolderInfo fi) {
		FolderEntryInfo res = new FolderEntryInfo();
		
		res.setCreated(fi.getCreated());
		res.setDescription(fi.getDescription());
		res.setFolder(true);
		res.setId(fi.getId());
		res.setModified(fi.getModified());
		res.setName(fi.getName());
		res.setPath(fi.getPath());
		
		return res;
	}
	
	/**
	 * Writes the complete data of an input stream to a temporary file.
	 * 
	 * @param is The input stream to read.
	 * @return The temporary file.
	 * @throws IOException On writing errors.
	 */
	private static File writeToTmpFile(InputStream is) throws IOException {
		File f = null;
		FileOutputStream fos = null;
		
		try {
			f = File.createTempFile("openuss", "webdav-documentresource");
			
			fos = new FileOutputStream(f);
			IOUtils.copyLarge(is, fos);
		} finally {
			if (fos != null) {
				fos.close();
			}
		}

		return f;
	}
	
	/**
	 * @param name The name of the new file to create.
	 * @param ioc The IOContext that is used to determine all the other properties.
	 * @param is The input stream to read the data from.
	 * @param len The number of bytes available in the InputStream. This is needed for the database and ignored otherwise.
	 * @return A new dummy file info.
	 */
	private static FileInfo createNewFileInfo(String name, IOContext ioc, InputStream is, long len) {
		FileInfo res = new FileInfo();
		
		res.setFileName(name);
		res.setDescription(WebDAVPathImpl.stripExtension(name));
		res.setContentType(sanitizeContentType(ioc.getContentType()));
		
		res.setInputStream(is);
		res.setFileSize((int) len);
		
		Date now = new Date();
		res.setCreated(now);
		res.setModified(now);
		
		return res;
	}
	
	/**
	 * @param contentType The inputted content type.
	 * @return A valid content type.
	 */
	private static String sanitizeContentType(String contentType) {
		if ((contentType == null) || contentType.equals("")) {
			return WebDAVConstants.MIMETYPE_DEFAULT;
		} else {
			return contentType;
		}
	}
}
