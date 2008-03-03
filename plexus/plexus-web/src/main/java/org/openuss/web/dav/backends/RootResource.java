package org.openuss.web.dav.backends;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.openuss.webdav.IOContext;
import org.openuss.webdav.MultiStatusResponse;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.openuss.webdav.WebDAVResourceException;
import org.w3c.dom.Document;

public class RootResource implements WebDAVResource{

	public void createCollection() throws WebDAVResourceException {
		// TODO Auto-generated method stub
		
	}

	public void delete() throws WebDAVResourceException {
		// TODO Auto-generated method stub
		
	}

	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	public Set<WebDAVResource> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	public WebDAVPath getPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public MultiStatusResponse getProperties(Document req)
			throws WebDAVResourceException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isCollection() {
		// TODO Auto-generated method stub
		return false;
	}

	public IOContext readContent() throws WebDAVResourceException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public WebDAVResource resolvePath(List<String> path)
			throws WebDAVResourceException {
		// TODO Auto-generated method stub
		return null;
	}

	public MultiStatusResponse updateProperties(Document req)
			throws WebDAVResourceException {
		// TODO Auto-generated method stub
		return null;
	}

	public void writeContent(IOContext ioc) throws WebDAVResourceException,
			IOException {
		// TODO Auto-generated method stub
		
	}

	public WebDAVResource resolvePath(WebDAVPath path)
			throws WebDAVResourceException {
		// TODO Auto-generated method stub
		return null;
	}

	
}

	