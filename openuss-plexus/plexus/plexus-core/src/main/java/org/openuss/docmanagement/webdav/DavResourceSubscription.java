package org.openuss.docmanagement.webdav;

import java.util.LinkedList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.jackrabbit.util.Text;
import org.openuss.docmanagement.DocConstants;
import org.openuss.docmanagement.DocRights;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.8
 */
public class DavResourceSubscription extends DavResourceCollection {
	/**
	 * Constructor.
	 * @param factory The resource factory.
	 * @param session The session with the repository.
	 * @param locator The locator identifying this resource.
	 * @param representedNode The node from the repository or null.
	 */
	DavResourceSubscription(DavResourceFactory factory, Session session, DavResourceLocator locator, Node representedNode) {
		super(factory, session, locator, representedNode);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResourceBase#addMember(org.openuss.docmanagement.webdav.DavResource, org.openuss.docmanagement.webdav.ImportContext)
	 */
	@Override
	public void addMember(DavResource resource, ImportContext context) throws DavException {
		// adding members to subscription folder is not allowed
		throw new DavException(HttpStatus.SC_FORBIDDEN);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResourceBase#copyFrom(org.openuss.docmanagement.webdav.DavResource, boolean)
	 */
	@Override
	public MultiStatus copyFrom(DavResource source, boolean recursive) throws DavException {
		// adding members to subscription folder is not allowed
		throw new DavException(HttpStatus.SC_FORBIDDEN);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#exists()
	 */
	@Override
	public boolean exists() {
		// TODO testen, was passiert, wenn das nicht gepatcht wird
		return true;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#getCreationDate()
	 */
	@Override
	public String getCreationDate() throws DavException {
		// avoid 404 (Not Found)
		return System.currentTimeMillis() + "";
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#getDisplayName()
	 */
	@Override
	public String getDisplayName() throws DavException {
		// avoid 404 (Not Found)
		return Text.getName(getLocator().getRepositoryPath());
	}

	@Override
	public long getLastModified() throws DavException {
		// avoid 404 (Not Found)
		return System.currentTimeMillis();
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#getMembers()
	 */
	@Override
	public List<DavResource> getMembers() throws DavException {
		List<DavResource> members = new LinkedList<DavResource>();
		
		// create virtual folders. mapping will result in real folders.
		
		// FIXME replace enrollment name with enrollment id from list of subscribed enrollments 
		
		// add link to distribution folders
		String distributionPath = getLocator().getRepositoryPath() + "/" + DocConstants.DISTRIBUTION;
		DavResourceLocator distributionLocator = getLocator().getFactory().createResourceLocator(getLocator().getPrefix(), null, distributionPath);
		DavResource distributionResource = getFactory().createResource(session, distributionLocator, true);
		if (distributionResource.exists()) {
			members.add(distributionResource);
		}
		
		// look for workingplace
		String workingPlacePath = getLocator().getRepositoryPath() + "/" + DocConstants.WORKINGPLACE;
		DavResourceLocator workingPlaceLocator = getLocator().getFactory().createResourceLocator(getLocator().getPrefix(), null, workingPlacePath);
		DavResource workingPlaceResource = getFactory().createResource(session, workingPlaceLocator, true);
		if (workingPlaceResource.exists()) {
			members.add(workingPlaceResource);
		}
		
		// look for examarea
		String examAreaPath = getLocator().getRepositoryPath() + "/" + DocConstants.EXAMAREA;
		DavResourceLocator examAreaLocator = getLocator().getFactory().createResourceLocator(getLocator().getPrefix(), null, examAreaPath);
		DavResource examAreaResource = getFactory().createResource(session, examAreaLocator, true);
		if (examAreaResource.exists()) {
			members.add(examAreaResource);
		}
		
		return members;
	}

	@Override
	public int getVisibility() throws DavException {
		// avoid 404 (Not Found)
		return (DocRights.READ_ALL);
	}
}
