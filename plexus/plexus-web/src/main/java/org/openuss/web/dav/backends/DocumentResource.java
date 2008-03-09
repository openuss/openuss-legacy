package org.openuss.web.dav.backends;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.openuss.desktop.DesktopService2;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FileEntry;
import org.openuss.documents.Folder;
import org.openuss.documents.FolderEntry;
import org.openuss.documents.FolderInfo;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.InstituteService;
import org.openuss.web.Constants;
import org.openuss.web.dav.SimpleWebDAVResource;
import org.openuss.webdav.IOContext;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.openuss.webdav.WebDAVResourceException;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 */
public class DocumentResource extends SimpleWebDAVResource {
	protected DocumentService documentService;
	protected FolderEntry fe;
	
	public DocumentResource(WebApplicationContext wac, WebDAVPath path, FolderEntry fe) {
		super(wac, path);
		
		documentService = (DocumentService) getWAC().getBean("documentService", DocumentService.class);
	}
	
	@Override
	protected WebDAVResource createCollectionImpl(String name)
			throws WebDAVResourceException {
		// TODO Auto-generated method stub
		return null;
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
	
	@Override
	protected Map<Long, String> getRawChildNames() {
		
		return null;
	}

	@Override
	protected IOContext readContentImpl() throws WebDAVResourceException,
			IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<String, String> simpleGetProperties(Set<String> propNames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void writeContentImpl(IOContext ioc)
			throws WebDAVResourceException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#isCollection()
	 */
	public boolean isCollection() {
		return (fe instanceof Folder);
	}

	public boolean isReadable() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isWritable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected WebDAVResource getChild(String name, WebDAVPath path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<WebDAVResource> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasChild(String name) {
		// TODO Auto-generated method stub
		return false;
	}
}
