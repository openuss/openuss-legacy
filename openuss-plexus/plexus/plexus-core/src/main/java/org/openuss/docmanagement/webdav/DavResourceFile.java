package org.openuss.docmanagement.webdav;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.JcrConstants;
import org.openuss.docmanagement.DocConstants;
import org.openuss.docmanagement.DocRights;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.5
 */
public class DavResourceFile extends DavResourceBase {
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
	 * @see org.openuss.docmanagement.webdav.DavResourceBase#addMember(org.openuss.docmanagement.webdav.DavResource, org.openuss.docmanagement.webdav.ImportContext)
	 */
	@Override
	public void addMember(DavResource resource, ImportContext context) throws DavException {
		// TODO Sicherheitsabfrage implementieren
		super.addMember(resource, context);
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
		try {
			Node contentNode;

			// snoop for content
			if (representedNode.hasNode(JcrConstants.JCR_CONTENT)) {
				contentNode = representedNode.getNode(JcrConstants.JCR_CONTENT);

				// test, if node contains data. if not -> do nothing
				if (contentNode.hasProperty(JcrConstants.JCR_DATA)) {
					// node contains data -> copy to output stream of context
					Property property = contentNode.getProperty(JcrConstants.JCR_DATA);
					InputStream inputStream = property.getStream();
					OutputStream outputStream = context.getOutputStream();
					
					try {
						byte[] buffer = new byte[8192];
						int read;
						// copy while input stream contains data
						while ((read = inputStream.read(buffer)) >= 0) {
							outputStream.write(buffer, 0, read);
						}
					} finally {
						// close input stream
						inputStream.close();
					}
				}
			}
		} catch (IOException ex) {
			// exception while operation with streams
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		} catch (RepositoryException ex) {
			// rethrow RepositoryException as DavException
			throw new DavException(HttpStatus.SC_SERVICE_UNAVAILABLE, ex.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#exportProperties(org.openuss.docmanagement.webdav.ExportContext)
	 */
	@Override
	protected void exportProperties(ExportContext context) throws DavException {
        try {
        	// get jcr:content node
        	Node contentNode = representedNode.getNode(JcrConstants.JCR_CONTENT);
        	
            long length = -1;
            
            // lookup length of data, if property jcr:data is present
            if (contentNode.hasProperty(JcrConstants.JCR_DATA)) {
                Property dataProperty = contentNode.getProperty(JcrConstants.JCR_DATA);
                length = dataProperty.getLength();
                context.setContentLength(length);
            }

            // request mime-type and encoding
            String mimeType = null;
            String encoding = null;
            if (contentNode.hasProperty(JcrConstants.JCR_MIMETYPE)) {
                mimeType = contentNode.getProperty(JcrConstants.JCR_MIMETYPE).getString();
            }
            if (contentNode.hasProperty(JcrConstants.JCR_ENCODING)) {
                encoding = contentNode.getProperty(JcrConstants.JCR_ENCODING).getString();
                // ignore empty encodings
                if (encoding.length() == 0) {
                    encoding = null;
                }
            }
            context.setContentType(mimeType, encoding);

            // time of last modification
            context.setModificationTime(getLastModified());

            // create entity tag for files containing data
            if (length >= 0) {
                String etag = "\"" + length + "-" + getLastModified() + "\"";
                context.setETag(etag);
            }
        } catch (RepositoryException ex) {
            // should never occur
            throw new DavException(HttpStatus.SC_SERVICE_UNAVAILABLE, ex.getMessage());
        }
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResourceBase#getProperties(java.util.List, boolean)
	 */
	@Override
	public MultiStatusResponse getProperties(List<String> properties, boolean namesOnly) throws DavException {
		// DavResourceBase returns a property response
		PropertyResponse response = (PropertyResponse)super.getProperties(properties, namesOnly);
		
		// property doc:owner
		// TODO
		
		// property distribution time
		// TODO
		
		// doc:viewed property is not required for webdav-access -> ignore
		
		return response;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#importData(org.openuss.docmanagement.webdav.ImportContext)
	 */
	@Override
	protected boolean importData(ImportContext context) throws DavException {
		boolean success = true;
		
		// get input stream
		InputStream inputStream = context.getInputStream();
		
		// set property jcr:data of node jcr:content with data from stream
		if (inputStream != null) {
			try {
				Node contentNode;
				
				// snoop for already existing jcr:content node
				if (representedNode.hasNode(DocConstants.JCR_CONTENT)) {
					contentNode = representedNode.getNode(DocConstants.JCR_CONTENT);
				} else {
					contentNode = representedNode.addNode(DocConstants.JCR_CONTENT, DocConstants.NT_RESOURCE);
				}
				
				contentNode.setProperty(JcrConstants.JCR_DATA, inputStream);
			} catch (RepositoryException ex) {
				// data could not be set
				success = false;
			} finally {
				// close input stream
				try {
					inputStream.close();
				} catch (IOException ex) {
					// ignore
				}
			}
		}
		
		return success;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#importProperties(org.openuss.docmanagement.webdav.ImportContext)
	 */
	@Override
	protected boolean importProperties(ImportContext context) throws DavException {
		boolean success = true;
		
		// set mime-type property of node
		try {
			representedNode.setProperty(JcrConstants.JCR_MIMETYPE, context.getMimeType());
		} catch (RepositoryException ex) {
			// ignore
		}
		
		// set encoding property of node
		try {
			representedNode.setProperty(JcrConstants.JCR_ENCODING, context.getEncoding());
		} catch (RepositoryException ex) {
			// ignore
		}

		// set visibility property of node
		try {
			// FIXME set adequate visibility
			representedNode.getParent().setProperty(DocConstants.PROPERTY_VISIBILITY, (DocRights.READ_ALL|DocRights.EDIT_ASSIST));
		} catch (RepositoryException ex) {
			// property mandatory, cannot ignore exception
			success = false;
		}

		// set distribution time property of node
		try {
			representedNode.getParent().setProperty(DocConstants.PROPERTY_DISTRIBUTIONTIME, Calendar.getInstance());
		} catch (RepositoryException ex) {
			// property mandatory, cannot ignore exception
			success = false;
		}
		
		// set message property of node
		try {
			representedNode.getParent().setProperty(DocConstants.PROPERTY_MESSAGE, context.getSystemId());
		} catch (RepositoryException ex) {
			// property mandatory, cannot ignore exception
			success = false;
		}
		
		// set last modified property of node
		try {
			Calendar modificationTime = Calendar.getInstance();
			if (context.getModificationTime() != -1) {
				modificationTime.setTimeInMillis(context.getModificationTime());
			} else {
				modificationTime.setTime(new Date());
			}
			representedNode.setProperty(JcrConstants.JCR_LASTMODIFIED, modificationTime);
		} catch (RepositoryException ex) {
			// ignore
		}

		return success;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#isCollection()
	 */
	public boolean isCollection() {
		return false;
	}
}
