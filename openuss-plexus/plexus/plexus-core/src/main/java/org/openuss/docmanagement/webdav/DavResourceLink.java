package org.openuss.docmanagement.webdav;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class DavResourceLink extends DavResource {

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#isCollection()
	 */
	@Override
	public boolean isCollection() {
		// TODO abh�ngig vom Ziel des Links
		return false;
	}

}
