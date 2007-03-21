package org.openuss.docmanagement.webdav;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavResourceLocator;
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
	public DavResourceRoot(DavResourceFactory factory, Session session, DavResourceLocator locator, Node representedNode) {
		super(factory, session, locator, representedNode);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#getMembers()
	 */
	@Override
	public List<DavResource> getMembers() throws DavException {
		List<DavResource> members = new LinkedList<DavResource>();
		
		// display virtual folders for each subscribed enrollment
		Iterator<Enrollment> iterator = getFactory().getService().getSubscribedEnrollments().iterator();
		Enrollment enrollment;
		DavResourceLocator locator;
		while (iterator.hasNext()) {
			enrollment = iterator.next();
			locator = getLocator().getFactory().createResourceLocator(getLocator().getPrefix(), null, "/" + enrollment.getId().toString());
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
	public void remove() throws DavException {
		// throw exception with status code 403 (FORBIDDEN) independent of user rights
		throw new DavException(HttpStatus.SC_FORBIDDEN, "Root cannot be deleted.");
	}
}
