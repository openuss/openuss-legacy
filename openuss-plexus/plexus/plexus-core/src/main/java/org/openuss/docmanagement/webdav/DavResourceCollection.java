package org.openuss.docmanagement.webdav;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavResourceLocator;
import org.openuss.docmanagement.DocConstants;
import org.openuss.docmanagement.DocRights;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class DavResourceCollection extends DavResource {
	public DavResourceCollection(DavResourceFactory factory, Session session, DavResourceLocator locator, Node representedNode) {
		super(factory, session, locator, representedNode);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#isCollection()
	 */
	@Override
	public boolean isCollection() {
		return true;
	}

	@Override
	public void exportContent(ExportContext context) throws DavException {
		if (!canExport(context)) {
			// TODO exception message
			throw new DavException(HttpStatus.SC_BAD_REQUEST);
		}
		
		try {
			exportProperties(context, representedNode);

			if (context.hasStream()) {
				// TODO collection entries ausgeben
			}
		} catch (IOException ex) {
			// TODO
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}
		
		context.informCompleted(true);
	}

	@Override
	public boolean importContent(ImportContext context) throws DavException {
		if (!canImport(context)) {
			// TODO exception message
		}
		
		try {
			Node rootNode = session.getRootNode();
			representedNode = rootNode.addNode(getLocator().getRepositoryPath().substring(1), DocConstants.NT_FOLDER);
			
			importProperties(null, representedNode);
		} catch (RepositoryException ex) {
			
		} catch (IOException ex) {
			
		}
		
		return true;
	}

	@Override
	protected boolean importData(ImportContext context, Node node) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected boolean importProperties(ImportContext context, Node node) throws IOException {
		// HACK
		try {
			node.setProperty(DocConstants.PROPERTY_VISIBILITY, (DocRights.READ_ALL|DocRights.EDIT_ASSIST));
		} catch (RepositoryException ex) {
			
		}
		try {
			node.setProperty(DocConstants.PROPERTY_MESSAGE, node.getName());
		} catch (RepositoryException ex) {
			
		}
		return true;
	}
}
