package org.openuss.docmanagement.webdav;

import java.io.IOException;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavResourceLocator;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class DavResourceLink extends DavResource {
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

	@Override
	public void exportContent(ExportContext context) throws DavException {
		if (!canExport(context)) {
			// TODO exception message
		}
		
		// TODO
		
		context.informCompleted(true);
	}

	@Override
	public boolean importContent(ImportContext context) throws DavException {
		if (!canImport(context)) {
			// TODO exception message
		}
		
		// TODO
		
		context.informCompleted(true);
		
		return false;
	}

	@Override
	protected boolean importData(ImportContext context, Node node) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}
}
