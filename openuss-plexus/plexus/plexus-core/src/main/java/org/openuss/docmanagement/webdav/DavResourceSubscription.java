package org.openuss.docmanagement.webdav;

import java.util.LinkedList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.jackrabbit.util.Text;
import org.openuss.docmanagement.DocConstants;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.6
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
	 * @see org.openuss.docmanagement.webdav.DavResource#exists()
	 */
	@Override
	public boolean exists() {
		// TODO
		return true;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#getCreationDate()
	 */
	@Override
	public String getCreationDate() throws DavException {
		// TODO
		return System.currentTimeMillis() + "";
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#getDisplayName()
	 */
	@Override
	public String getDisplayName() throws DavException {
		// TODO auf Anzeigename des Enrollments umbiegen
		return Text.getName(getLocator().getRepositoryPath());
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#getMembers()
	 */
	@Override
	public List<DavResource> getMembers() throws DavException {
		List<DavResource> members = new LinkedList<DavResource>();
		
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
}
