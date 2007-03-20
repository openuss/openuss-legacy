package org.openuss.docmanagement.webdav;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavResourceLocator;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.6
 */
public class DavResourceLink extends DavResource {
	/**
	 * Constructor.
	 * @param factory
	 * @param session
	 * @param locator
	 * @param representedNode
	 */
	public DavResourceLink(DavResourceFactory factory, Session session, DavResourceLocator locator, Node representedNode) {
		super(factory, session, locator, representedNode);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#isCollection()
	 */
	@Override
	public boolean isCollection() {
		// TODO abhängig vom Ziel des Links
		return false;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#exportContent(org.openuss.docmanagement.webdav.ExportContext)
	 */
	@Override
	public void exportContent(ExportContext context) throws DavException {
		if (!canExport(context)) {
			// TODO exception message
		}
		
		// TODO
		
		context.informCompleted(true);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#importContent(org.openuss.docmanagement.webdav.ImportContext)
	 */
	@Override
	public boolean importContent(ImportContext context) throws DavException {
		if (!canImport(context)) {
			// TODO exception message
		}
		
		// TODO
		
		context.informCompleted(true);
		
		return false;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#importData(org.openuss.docmanagement.webdav.ImportContext, javax.jcr.Node)
	 */
	@Override
	protected boolean importData(ImportContext context, Node node) throws DavException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#importProperties(org.openuss.docmanagement.webdav.ImportContext, javax.jcr.Node)
	 */
	@Override
	protected boolean importProperties(ImportContext context, Node node) throws DavException {
		// TODO Auto-generated method stub
		return false;
	}
}
