package org.openuss.web.dav.backends;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.openuss.web.dav.SimpleWebDAVResource;
import org.openuss.webdav.IOContext;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.openuss.webdav.WebDAVResourceException;

public class RootResource extends SimpleWebDAVResource {

	protected RootResource(WebDAVPath path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Map<Long, String> getRawChildNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isReadable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isWritable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Map<String, String> simpleGetProperties(Set<String> propNames) {
		// TODO Auto-generated method stub
		return null;
	}

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

	public boolean isCollection() {
		// TODO Auto-generated method stub
		return false;
	}

	public IOContext readContent() throws WebDAVResourceException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public void writeContent(IOContext ioc) throws WebDAVResourceException,
			IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected WebDAVResource getChild(long id, String name, WebDAVPath path)
			throws WebDAVResourceException {
		// TODO Auto-generated method stub
		return null;
	}
	
}

	