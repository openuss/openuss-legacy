package org.openuss.web.dav.backends;

import java.io.IOException;


import org.apache.shale.tiger.managed.Property;
import org.openuss.lecture.OrganisationService;
import org.openuss.web.dav.SimpleWebDAVResource;
import org.openuss.webdav.IOContext;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.openuss.webdav.WebDAVResourceException;
import org.openuss.webdav.WebDAVStatusCodes;

/**
 * A resource representing any organisation, i.e. university.
 */
public abstract class AbstractOrganisationResource extends SimpleWebDAVResource {
	/**
	 * The virtual ID of the root organisation.
	 */
	protected static long ID_ROOT = -1000;

	@Property(value = "#{organisationService}")
	protected OrganisationService organisationService;

	
	/**
	 * @param path The path of this object.
	 * @param id The id of this object.
	 */
	protected AbstractOrganisationResource(WebDAVPath path, long id) {
		super(path, id);
		
		if (hasRealId()) {
			// TODO implement
		}
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#isRealId()
	 */
	protected boolean hasRealId() {
		return super.hasRealId() && (id != ID_ROOT);
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#createCollectionImpl()
	 */
	@Override
	protected void createCollectionImpl() throws WebDAVResourceException {
		// should never be called because mayCreateCollection() returns false.
		throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this, "Creation of organisations via WebDAV is not implemented");
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#deleteImpl()
	 */
	@Override
	protected void deleteImpl() throws WebDAVResourceException {
		// should never be called because isDeletable() returns false.
		throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this, "Deletion of organisations via WebDAV is not implemented");
	}

	@Override
	public boolean isReadable() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#isWritable()
	 */
	@Override
	public boolean isWritable() {
		// Creation of organisations is not implemented, creation of files makes no sense
		return false;
	}

	@Override
	protected IOContext readContentImpl() throws WebDAVResourceException,
			IOException {
		// TODO auto-implement
		return null;
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
		return exists(); // All existing organisations are collections 
	}
}
