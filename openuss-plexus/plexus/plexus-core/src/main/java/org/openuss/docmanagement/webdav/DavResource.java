package org.openuss.docmanagement.webdav;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;

import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavResourceLocator;
import org.openuss.docmanagement.DocConstants;

/**
 * @author David Ullrich
 * @version 0.5
 */
public abstract class DavResource {
	protected final DavResourceFactory factory;
	protected final DavResourceLocator locator;
	protected final Node representedNode;
	
	protected DavResource(DavResourceFactory factory, DavResourceLocator locator, Node representedNode) {
		this.factory = factory;
		this.locator = locator;
		this.representedNode = representedNode;
	}
	
	public boolean exists() {
		return (representedNode != null);
	}
	
	/**
	 * @return
	 */
	public abstract boolean isCollection();
	
	public DavResourceFactory getFactory() {
		return factory;
	}
	
	public DavResourceLocator getLocator() {
		return locator;
	}
	
	/**
	 * @param context
	 * @return
	 */
	public boolean canExport(ExportContext context) {
		// export impossible, if context is null or already completed
		if ((context == null) || (context.isCompleted())) {
			return false;
		}
		
		// export impossible, if new resource
		if (!exists()) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param context
	 * @return
	 */
	public boolean canImport(ImportContext context) {
		// import impossible, if context is null or already completed
		if ((context == null) || (context.isCompleted())) {
			return false;
		}
		
		// TODO Node überprüfen

		if (context.getSystemId() == null) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param context
	 * @throws IOException
	 */
	public abstract void exportContent(ExportContext context) throws IOException;
	
	/**
	 * @param context
	 * @throws IOException
	 */
	public abstract void importContent(ImportContext context) throws IOException;
	
	protected void exportProperties(ExportContext context, Node node) throws IOException {
		try {
			// set creation time property of export context
			// the property jcr:created can be found on the export root; navigate up for files
			// test, if not a collection, node has a parent and the property is set in the repository
			
			// TODO überarbeiten
			if (!isCollection() && (node.getDepth() > 0) && node.getParent().hasProperty(DocConstants.JCR_CREATED)) {
				// export value to context
				context.setCreationTime(node.getParent().getProperty(DocConstants.JCR_CREATED).getValue().getLong());
			}

			// set content length property of export context
			long length = -1;
			// test, if node contains data
			if (node.hasProperty(DocConstants.JCR_DATA)) {
				// get length of data and export value to context
				Property property = node.getProperty(DocConstants.JCR_DATA);
				length = property.getLength();
				context.setContentLength(length);
			}
			
			// set content type property of export context
			String mimeType = null;
			String encoding = null;
			// test, if property jcr:mimetype is set in repository
			if (node.hasProperty(DocConstants.JCR_MIMETYPE)) {
				mimeType = node.getProperty(DocConstants.JCR_MIMETYPE).getString();
			}
			// test, if property jcr:encoding is set in repository
			if (node.hasProperty(DocConstants.JCR_ENCODING)) {
				encoding = node.getProperty(DocConstants.JCR_ENCODING).getString();
				// test, if encoding has a valid value; null IS a valid value
				if ((encoding != null) && (encoding.length() == 0)) {
					encoding = null;
				}
			}
			// export values to context
			context.setContentType(mimeType, encoding);
			
			// set modification time property of export context
			long modificationTime = -1;
			// test, if property is set in repository; in case of doubt: current system time
			if (node.hasProperty(DocConstants.JCR_LASTMODIFIED)) {
				modificationTime = node.getProperty(DocConstants.JCR_LASTMODIFIED).getLong();
				context.setModificationTime(modificationTime);
			} else {
				context.setModificationTime(System.currentTimeMillis());
			}
			
			// set entity tag property of export context
			if ((length >= 0) && (modificationTime >= 0)) {
				context.setETag("\"" + length + "-" + modificationTime + "\"");
			}
		} catch (RepositoryException ex) {
			// rethrow RepositoryException as IOException
			throw new IOException(ex.getMessage());
		}
	}
	
	protected void exportData(ExportContext context, Node node) throws IOException {
		try {
			// test, if node contains data. if not -> do nothing
			if (node.hasProperty(DocConstants.JCR_DATA)) {
				// node contains data -> copy to output stream of context
				Property property = node.getProperty(DocConstants.JCR_DATA);
				InputStream inStream = property.getStream();
				OutputStream outStream = context.getOutputStream();
				try {
					// TODO buffer-Größe in Konstante auslagern
					byte[] buffer = new byte[8192];
					int read;
					// copy while input stream contains data
					while ((read = inStream.read(buffer)) >= 0) {
						outStream.write(buffer, 0, read);
					}
				} finally {
					// close input stream
					inStream.close();
				}
			}
		} catch (RepositoryException ex) {
			// rethrow RepositoryException as IOException
			throw new IOException(ex.getMessage());
		}
	}
	
	protected boolean importData(ImportContext context, Node node) throws IOException {
		// TODO
		return false;
	}
	
	protected boolean importProperties(ImportContext context, Node node) throws IOException {
		// TODO
		return false;
	}
	
	public String getDisplayName() throws DavException {
		if (!exists()) {
			throw new DavException(HttpStatus.SC_NOT_FOUND);
		}
		
		try {
			return representedNode.getName();
		} catch (RepositoryException ex) {
			throw new DavException(HttpStatus.SC_SERVICE_UNAVAILABLE);
		}
	}
	
	public String getCreationDate() throws DavException {
		if (!exists()) {
			throw new DavException(HttpStatus.SC_NOT_FOUND);
		}
		
		try {
			// TODO kann das Property fehlen?
			return representedNode.getProperty(DocConstants.JCR_CREATED).getString();
		} catch (RepositoryException ex) {
			throw new DavException(HttpStatus.SC_SERVICE_UNAVAILABLE);
		}
	}
	
	public DavResource[] getMembers() throws DavException {
		// TODO prüfen, ob der Fehler berechtigt ist
		if (!exists()) {
			throw new DavException(HttpStatus.SC_NOT_FOUND);
		}
		
		List<DavResource> members = new ArrayList<DavResource>();
		
		try {
			ItemFilter itemFilter = getFactory().getConfiguration().getItemFilter();
			
			if (locator.isRootLocation()) {
				// TODO abonnierte Elemente anzeigen
			} else {
				// Kindknoten anzeigen
				NodeIterator nodeIterator = representedNode.getNodes();
				Node node;
				DavResourceLocator locator;
				while (nodeIterator.hasNext()) {
					node = nodeIterator.nextNode();
					if (!itemFilter.isFilteredItem(node)) {
						locator = getLocator().getFactory().createResourceLocator(getLocator().getPrefix(), null, node.getPath());
						members.add(getFactory().createResource(representedNode.getSession(), locator));
					}
				}
			}
		} catch (RepositoryException ex) {
			// rethrow RepositoryException as DavException
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR);
		}
		
		return members.toArray(new DavResource[0]);
	}
	
	public MultiStatusResponse getProperties(String[] properties) {
		MultiStatusResponse response = new MultiStatusResponse(locator.getHref(isCollection()), null);
		
		if (properties.length == 0) {
			// reply ALL properties
		} else {
			// reply only requested properties
		}
		
		// HACK
		try {
			ItemFilter itemFilter = getFactory().getConfiguration().getItemFilter();

			PropertyIterator propertyIterator = representedNode.getProperties();
			Property property;
			while (propertyIterator.hasNext()) {
				property = propertyIterator.nextProperty();
				if (!itemFilter.isFilteredItem(property)) {
					response.addProperty(HttpStatus.SC_OK, property.getName(), property.getValue().getString());
				}
			}
		} catch (RepositoryException ex) {
			
		}
		
		return response;
	}
}
