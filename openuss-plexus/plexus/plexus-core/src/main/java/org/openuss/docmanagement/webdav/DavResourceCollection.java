package org.openuss.docmanagement.webdav;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.openuss.docmanagement.DocConstants;
import org.openuss.docmanagement.DocRights;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.8
 */
public class DavResourceCollection extends DavResourceBase {
	/**
	 * Constructor.
	 * @param factory The resource factory.
	 * @param session The session with the repository.
	 * @param locator The locator identifying this resource.
	 * @param representedNode The node from the repository or null.
	 */
	DavResourceCollection(DavResourceFactory factory, Session session, DavResourceLocator locator, Node representedNode) {
		super(factory, session, locator, representedNode);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#copyDataFrom(org.openuss.docmanagement.webdav.DavResource)
	 */
	@Override
	protected boolean copyDataFrom(DavResource source) throws DavException {
		// check, if source is a file
		if (!(source instanceof DavResourceCollection)) {
			throw new DavException(HttpStatus.SC_METHOD_FAILURE, "The source resource is not a file.");
		}
		
		// collections do not contain data
		return true;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#copyPropertiesFrom(org.openuss.docmanagement.webdav.DavResource)
	 */
	@Override
	protected boolean copyPropertiesFrom(DavResource source) throws DavException {
		DavResourceCollection castedSource = (DavResourceCollection)source;
		
		try {
			// copy mandatory properties from source
			representedNode.setProperty(DocConstants.PROPERTY_VISIBILITY, source.getVisibility());
			representedNode.setProperty(DocConstants.PROPERTY_MESSAGE, source.getDisplayName());
			
			// copy deadline, if set at source
			if (castedSource.getDeadline() < Long.MAX_VALUE) {
				representedNode.setProperty(DocConstants.PROPERTY_DEADLINE, castedSource.getDeadline());
			}
		} catch (RepositoryException ex) {
			// undefined repository exception occurred -> rethrow as DavException
			throw new DavException(HttpStatus.SC_SERVICE_UNAVAILABLE);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#exportData(org.openuss.docmanagement.webdav.ExportContext)
	 */
	@Override
	protected void exportData(ExportContext context) throws DavException {
		if (context.hasStream()) {
			// create stream writer for output
			OutputStreamWriter writer = new OutputStreamWriter(context.getOutputStream());
			
			try {
				// write html file containing a listing of the resource
				writer.write("<html><head><title>");
				writer.write("Content of collection " + getLocator().getHref(isCollection()));
				writer.write("</title></head></body>");
				writer.write("<table>");
				
				// show up-link, if not root
				if (!getLocator().isRootLocation()) {
					writer.write("<tr>");
					writer.write("<td><img src=\"\"></td>");
					writer.write("<td><a href=\"" + getCollection().getLocator().getHref(true) + "\">..</a></td>");
					writer.write("</tr>");
				}
				
				// iterate through members and display link for each of them
				Iterator<DavResource> iterator = getMembers().iterator();
				DavResource member;
				while (iterator.hasNext()) {
					member = iterator.next();
					writer.write("<tr>");
					writer.write("<td><img src=\"\"></td>");
					writer.write("<td><a href=\"" + member.getLocator().getHref(member.isCollection()) + "\">" + member.getLocator().getName() + "</a></td>");
					writer.write("</tr>");
				}
				
				writer.write("</table>");
				writer.write("</body></html>");
				writer.close();
			} catch (IOException ex) {
				// rethrow as DavException
				throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#exportProperties(org.openuss.docmanagement.webdav.ExportContext)
	 */
	@Override
	protected void exportProperties(ExportContext context) throws DavException {
		// set properties for html export of collection members
		context.setContentType("text/html", "UTF-8");
		context.setETag("");
		context.setModificationTime(getLastModified());
	}
	
	/**
	 * Returns the deadline for imports.
	 * @return The deadline for imports or Long.MAX_VALUE if no deadline is set.
	 * @throws DavException
	 */
	public long getDeadline() throws DavException {
		if (!exists()) {
			throw new DavException(HttpStatus.SC_NOT_FOUND);
		}
		
		try {
			// return property value, if present
			if (representedNode.hasProperty(DocConstants.PROPERTY_DEADLINE)) {
				return representedNode.getProperty(DocConstants.PROPERTY_DEADLINE).getLong();
			}
			return Long.MAX_VALUE;
		} catch (RepositoryException ex) {
			throw new DavException(HttpStatus.SC_SERVICE_UNAVAILABLE);
		}
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#importData(org.openuss.docmanagement.webdav.ImportContext)
	 */
	@Override
	protected boolean importData(ImportContext context) throws DavException {
		// collections cannot contain raw data
		return true;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#importProperties(org.openuss.docmanagement.webdav.ImportContext)
	 */
	@Override
	protected boolean importProperties(ImportContext context) throws DavException {
		boolean success = true;
		
		try {
			// FIXME set adequate visibility
			representedNode.setProperty(DocConstants.PROPERTY_VISIBILITY, (DocRights.READ_ALL|DocRights.EDIT_ASSIST));
		} catch (RepositoryException ex) {
			// error occurred while setting mandatory property
			success = false;
		}
		
		try {
			// set name of property as message
			representedNode.setProperty(DocConstants.PROPERTY_MESSAGE, representedNode.getName());
		} catch (RepositoryException ex) {
			// error occurred while setting mandatory property
			success = false;
		}
		
		return success;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavResource#isCollection()
	 */
	public boolean isCollection() {
		return true;
	}
}
