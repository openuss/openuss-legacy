package org.openuss.docmanagement.webdav;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Session;

import org.openuss.lecture.Enrollment;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.5
 */
public class DavResourceRoot extends DavResourceCollection {
	/**
	 * @param factory
	 * @param session
	 * @param locator
	 * @param representedNode
	 */
	DavResourceRoot(DavResourceFactory factory, Session session, DavResourceLocator locator, Node representedNode) {
		super(factory, session, locator, representedNode);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResourceBase#addMember(org.openuss.docmanagement.webdav.DavResource, org.openuss.docmanagement.webdav.ImportContext)
	 */
	@Override
	public void addMember(DavResource resource, ImportContext context) throws DavException {
		// adding members to root is not allowed
		throw new DavException(HttpStatus.SC_FORBIDDEN);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResourceBase#copyFrom(org.openuss.docmanagement.webdav.DavResource, boolean)
	 */
	@Override
	public MultiStatus copyFrom(DavResource source, boolean recursive) throws DavException {
		// adding members to root is not allowed
		throw new DavException(HttpStatus.SC_FORBIDDEN);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#getMembers()
	 */
	@Override
	public List<DavResource> getMembers() throws DavException {
		List<DavResource> members = new LinkedList<DavResource>();
		
		// display virtual folders for each subscribed enrollment
		Iterator<Enrollment> iterator = getFactory().getDavService().getSubscribedEnrollments().iterator();
		Enrollment enrollment;
		DavResourceLocator locator;
		while (iterator.hasNext()) {
			enrollment = iterator.next();
			// FIXME auf Anzeigename des Enrollments umbiegen
			locator = getLocator().getFactory().createResourceLocator(getLocator().getPrefix(), null, "/"  + enrollment.getId().toString());
			members.add(getFactory().createResource(session, locator, true));
		}
		
		// add normal members
		members.addAll(super.getMembers());

		return members;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#remove()
	 */
	@Override
	public MultiStatus remove() throws DavException {
		// throw exception with status code 403 (FORBIDDEN) independent of user rights
		throw new DavException(HttpStatus.SC_FORBIDDEN, "Root cannot be deleted.");
	}
}
