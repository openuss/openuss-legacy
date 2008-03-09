package org.openuss.web.dav.backends;

import java.io.IOException;

import org.openuss.web.dav.CollisionAvoidingSimpleWebDAVResource;
import org.openuss.web.dav.SimpleWebDAVResource;
import org.openuss.webdav.IOContext;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.openuss.webdav.WebDAVResourceException;
import org.openuss.webdav.WebDAVStatusCodes;
import org.springframework.web.context.WebApplicationContext;

/**
 * A resource representing any organisation, i.e. university.
 */
public abstract class AbstractOrganisationResource extends CollisionAvoidingSimpleWebDAVResource {
	/**
	 * The virtual ID of the root organisation.
	 */
	protected static long ID_ROOT = -1000;

	/**
	 * @param wac The bean factory
	 * @param path The path of this object.
	 * @param id The id of this object.
	 */
	protected AbstractOrganisationResource(WebApplicationContext wac, WebDAVPath path, long id) {
		super(wac, path, id);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#createCollectionImpl(java.lang.String)
	 */
	@Override
	protected WebDAVResource createCollectionImpl(String name) throws WebDAVResourceException {
		// should never be called because isWritable() returns false.
		throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this, "Creation of organisations via WebDAV is not implemented");
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#createFileImpl(java.lang.String)
	 */
	@Override
	protected WebDAVResource createFileImpl(String name)
			throws WebDAVResourceException {
		// should never be called because isWritable() returns false.
		throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this, "Creation of files in the organization tree via WebDAV is not implemented");
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#deleteImpl()
	 */
	@Override
	protected void deleteImpl() throws WebDAVResourceException {
		// should never be called because isDeletable() returns false.
		throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this, "Deletion of organisations via WebDAV is not implemented");
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#isWritable()
	 */
	public boolean isWritable() {
		// Creation of organisations is not implemented, creation of files makes no sense
		return false;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#readContentImpl()
	 */
	@Override
	protected IOContext readContentImpl() throws WebDAVResourceException,
			IOException {
		return readCollectionContent();
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#writeContentImpl(org.openuss.webdav.IOContext)
	 */
	@Override
	protected void writeContentImpl(IOContext ioc)
			throws WebDAVResourceException {
		// should never be called because isWritable() returns false.
		throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this, "Writing to organisations via WebDAV is not implemented");
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#isCollection()
	 */
	public boolean isCollection() {
		return true; // All organisations are collections 
	}
}
