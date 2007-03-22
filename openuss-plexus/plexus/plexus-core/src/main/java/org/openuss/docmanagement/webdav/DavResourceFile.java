package org.openuss.docmanagement.webdav;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavResourceLocator;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.5
 */
public class DavResourceFile extends DavResource {
	/**
	 * Constructor.
	 * @param factory The resource factory.
	 * @param session The session with the repository.
	 * @param locator The locator identifying this resource.
	 * @param representedNode The node from the repository or null.
	 */
	DavResourceFile(DavResourceFactory factory, Session session, DavResourceLocator locator, Node representedNode) {
		super(factory, session, locator, representedNode);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#copyDataFrom(org.openuss.docmanagement.webdav.DavResource)
	 */
	@Override
	protected boolean copyDataFrom(DavResource source) throws DavException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#copyPropertiesFrom(org.openuss.docmanagement.webdav.DavResource)
	 */
	@Override
	protected boolean copyPropertiesFrom(DavResource source) throws DavException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#exportData(org.openuss.docmanagement.webdav.ExportContext)
	 */
	@Override
	protected void exportData(ExportContext context) throws DavException {
		// TODO review
//		try {
//			Node contentNode;
//			
//			if (representedNode.hasNode(JcrConstants.JCR_CONTENT)) {
//				contentNode = representedNode.getNode(JcrConstants.JCR_CONTENT);
//
//				// test, if node contains data. if not -> do nothing
//				if (contentNode.hasProperty(JcrConstants.JCR_DATA)) {
//					// node contains data -> copy to output stream of context
//					Property property = contentNode.getProperty(JcrConstants.JCR_DATA);
//					InputStream inStream = property.getStream();
//					OutputStream outStream = context.getOutputStream();
//					try {
//						// TODO buffer-Größe in Konstante auslagern
//						byte[] buffer = new byte[8192];
//						int read;
//						// copy while input stream contains data
//						while ((read = inStream.read(buffer)) >= 0) {
//							outStream.write(buffer, 0, read);
//						}
//					} finally {
//						// close input stream
//						inStream.close();
//					}
//				}
//			}
//		} catch (IOException ex) {
//			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
//		} catch (RepositoryException ex) {
//			// rethrow RepositoryException as IOException
//			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
//		}
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#exportProperties(org.openuss.docmanagement.webdav.ExportContext)
	 */
	@Override
	protected void exportProperties(ExportContext context) throws DavException {
		// TODO
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#importContent(org.openuss.docmanagement.webdav.ImportContext)
	 */
	@Override
	public boolean importContent(ImportContext context) throws DavException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#importData(org.openuss.docmanagement.webdav.ImportContext)
	 */
	@Override
	protected boolean importData(ImportContext context) throws DavException {
//		TODO
//		InputStream inputStream = context.getInputStream();
//		if (inputStream != null) {
//			try {
//				node.setProperty(JcrConstants.JCR_DATA, inputStream);
//			} catch (RepositoryException ex) {
//				// TODO
//			} finally {
//				try {
//					inputStream.close();
//				} catch (IOException ex) {
//					
//				}
//			}
//		}
//		return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#importProperties(org.openuss.docmanagement.webdav.ImportContext)
	 */
	@Override
	protected boolean importProperties(ImportContext context) throws DavException {
//		TODO
//		try {
//			// if mimetype of context is null -> remove the property
//			node.setProperty(JcrConstants.JCR_MIMETYPE, context.getMimeType());
//		} catch (RepositoryException ex) {
//			// ignore
//		}
//		try {
//			// if encoding of context is null -> remove the property
//			node.setProperty(JcrConstants.JCR_ENCODING, context.getEncoding());
//		} catch (RepositoryException ex) {
//			// ignore
//		}
//		
//		try {
//			node.getParent().setProperty(DocConstants.PROPERTY_VISIBILITY, (DocRights.READ_ALL|DocRights.EDIT_ASSIST));
//		} catch (RepositoryException ex) {
//			
//		}
//		try {
//			node.getParent().setProperty(DocConstants.PROPERTY_DISTRIBUTIONTIME, Calendar.getInstance());
//		} catch (RepositoryException ex) {
//			
//		}
//		try {
//			node.getParent().setProperty(DocConstants.PROPERTY_MESSAGE, context.getSystemId());
//		} catch (RepositoryException ex) {
//			
//		}
//		
//		try {
//			Calendar modificationTime = Calendar.getInstance();
//			if (context.getModificationTime() != -1) {
//				modificationTime.setTimeInMillis(context.getModificationTime());
//			} else {
//				modificationTime.setTime(new Date());
//			}
//			node.setProperty(JcrConstants.JCR_LASTMODIFIED, modificationTime);
//		} catch (RepositoryException ex) {
//			// ignore
//		}
//		return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#isCollection()
	 */
	@Override
	public boolean isCollection() {
		return false;
	}
}
