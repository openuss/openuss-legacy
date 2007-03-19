package org.openuss.docmanagement.webdav;

import java.io.IOException;
import java.io.InputStream;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavResourceLocator;
import org.openuss.docmanagement.DocConstants;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class DavResourceFile extends DavResource {
	public DavResourceFile(DavResourceFactory factory, Session session, DavResourceLocator locator, Node representedNode) {
		super(factory, session, locator, representedNode);		
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#isCollection()
	 */
	@Override
	public boolean isCollection() {
		return false;
	}

	@Override
	public void exportContent(ExportContext context) throws DavException {
		if (!canExport(context)) {
			throw new DavException(HttpStatus.SC_NOT_FOUND);
		}
		
		try {
			exportProperties(context, representedNode);

			if (context.hasStream()) {
				exportData(context, representedNode);
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
			// TODO exception
		}
		
		boolean success = false;
		
		try {
			Node contentNode;
			
			if (exists()) {
				contentNode = representedNode.getNode(JcrConstants.JCR_CONTENT); 
			} else {
				Node rootNode = session.getRootNode();
				representedNode = rootNode.addNode(getLocator().getRepositoryPath().substring(1), DocConstants.NT_FILE);
				contentNode = representedNode.addNode(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE);
			}
			
			success = importData(context, contentNode) && importProperties(context, contentNode);
		} catch (RepositoryException ex) {
			success = false;
			// TODO
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		} catch (IOException ex) {
			success = false;
			// TODO
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		} finally {
			if (!success && (representedNode != null)) {
				try {
					representedNode.refresh(false);
				} catch (RepositoryException ex) {
					// TODO
					throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				}
			}
		}
		
		context.informCompleted(success);
		
		return success;
	}

	@Override
	protected boolean importData(ImportContext context, Node node) throws IOException {
		InputStream inputStream = context.getInputStream();
		if (inputStream != null) {
			try {
				node.setProperty(JcrConstants.JCR_DATA, inputStream);
			} catch (RepositoryException ex) {
				// TODO
			} finally {
				inputStream.close();
			}
		}
		return true;
	}
}
