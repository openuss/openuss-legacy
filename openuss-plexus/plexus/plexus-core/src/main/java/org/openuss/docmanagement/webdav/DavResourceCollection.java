package org.openuss.docmanagement.webdav;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class DavResourceCollection extends DavResource {

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#isCollection()
	 */
	@Override
	public boolean isCollection() {
		return true;
	}
}
