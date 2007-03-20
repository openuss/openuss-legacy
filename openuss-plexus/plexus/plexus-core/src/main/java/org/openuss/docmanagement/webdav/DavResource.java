package org.openuss.docmanagement.webdav;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.util.Text;
import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavResourceLocator;
import org.apache.jackrabbit.webdav.io.InputContext;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.openuss.docmanagement.DocConstants;
import org.openuss.docmanagement.DocRights;

/**
 * @author David Ullrich
 * @version 0.6
 */
public abstract class DavResource {
	protected final DavResourceFactory factory;
	protected final DavResourceLocator locator;
	protected Node representedNode;
	protected final Session session;
	
	protected DavResource(DavResourceFactory factory, Session session, DavResourceLocator locator, Node representedNode) {
		this.factory = factory;
		this.session = session;
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
	
	public DavResourceCollection getCollection() {
		DavResourceCollection parent = null;
		
		if (!locator.isRootLocation()) {
			String parentPath = Text.getRelativeParent(getLocator().getRepositoryPath(), 1);
			if (parentPath.length() == 0) {
				parentPath = "/";
			}
			DavResourceLocator parentLocator = getLocator().getFactory().createResourceLocator(getLocator().getPrefix(), null, parentPath);
			parent = (DavResourceCollection)getFactory().createResource(session, parentLocator, true);
		}
		
		return parent;
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
	public abstract void exportContent(ExportContext context) throws DavException;
	
	/**
	 * @param context
	 * @throws IOException
	 */
	public abstract boolean importContent(ImportContext context) throws DavException;
	
	protected void exportProperties(ExportContext context, Node node) throws IOException {
		try {
			// set creation time property of export context
			// the property jcr:created can be found on the export root; navigate up for files
			// test, if not a collection, node has a parent and the property is set in the repository
			
			// TODO überarbeiten

			// set content length property of export context
			long length = -1;
			// test, if node contains data
			if (node.hasProperty(JcrConstants.JCR_DATA)) {
				// get length of data and export value to context
				Property property = node.getProperty(JcrConstants.JCR_DATA);
				length = property.getLength();
				context.setContentLength(length);
			}
			
			// set content type property of export context
			String mimeType = null;
			String encoding = null;
			// test, if property jcr:mimetype is set in repository
			if (node.hasProperty(JcrConstants.JCR_MIMETYPE)) {
				mimeType = node.getProperty(JcrConstants.JCR_MIMETYPE).getString();
			}
			// test, if property jcr:encoding is set in repository
			if (node.hasProperty(JcrConstants.JCR_ENCODING)) {
				encoding = node.getProperty(JcrConstants.JCR_ENCODING).getString();
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
			if (node.hasProperty(JcrConstants.JCR_LASTMODIFIED)) {
				modificationTime = node.getProperty(JcrConstants.JCR_LASTMODIFIED).getLong();
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
			Node contentNode;
			
			if (node.hasNode(JcrConstants.JCR_CONTENT)) {
				contentNode = node.getNode(JcrConstants.JCR_CONTENT);

				// test, if node contains data. if not -> do nothing
				if (contentNode.hasProperty(JcrConstants.JCR_DATA)) {
					// node contains data -> copy to output stream of context
					Property property = contentNode.getProperty(JcrConstants.JCR_DATA);
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
			}
		} catch (RepositoryException ex) {
			// rethrow RepositoryException as IOException
			throw new IOException(ex.getMessage());
		}
	}
	
	protected abstract boolean importData(ImportContext context, Node node) throws DavException;
	
	protected abstract boolean importProperties(ImportContext context, Node node) throws DavException;
	
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
			
//			if (locator.isRootLocation()) {
//				// TODO abonnierte Elemente anzeigen
//			} else {
				// Kindknoten anzeigen
				NodeIterator nodeIterator = representedNode.getNodes();
				Node node;
				DavResourceLocator locator;
				while (nodeIterator.hasNext()) {
					node = nodeIterator.nextNode();
					if (!itemFilter.isFilteredItem(node)) {
						locator = getLocator().getFactory().createResourceLocator(getLocator().getPrefix(), null, node.getPath());
						members.add(getFactory().createResource(representedNode.getSession(), locator, false));
					}
				}
//			}
		} catch (RepositoryException ex) {
			// rethrow RepositoryException as DavException
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR);
		}
		
		return members.toArray(new DavResource[0]);
	}
	
	public MultiStatusResponse getProperties(List<String> properties, boolean namesOnly) {
		MultiStatusResponse response = new MultiStatusResponse(locator.getHref(isCollection()), null);
		
		boolean spoolAllProperties = ((properties == null) || (properties.size() == 0));
		
		try {
			// creationdate
			if (spoolAllProperties || properties.contains(DavConstants.PROPERTY_CREATIONDATE)) {
				if (representedNode.hasProperty(JcrConstants.JCR_CREATED)) {
					if (namesOnly) {
						response.addProperty(HttpStatus.SC_OK, DavConstants.PROPERTY_CREATIONDATE, null);
					} else {
						response.addProperty(HttpStatus.SC_OK, DavConstants.PROPERTY_CREATIONDATE, representedNode.getProperty(JcrConstants.JCR_CREATED).getString());
					}
				}
			}
			
			// displayname
			if (spoolAllProperties || properties.contains(DavConstants.PROPERTY_DISPLAYNAME)) {
				if (namesOnly) {
					response.addProperty(HttpStatus.SC_OK, DavConstants.PROPERTY_DISPLAYNAME, null);
				} else {
					response.addProperty(HttpStatus.SC_OK, DavConstants.PROPERTY_DISPLAYNAME, representedNode.getName());
				}
			}
			
			// resourcetype
			if (spoolAllProperties || properties.contains(DavConstants.PROPERTY_RESOURCETYPE)) {
				if (namesOnly || !isCollection()) {
					response.addProperty(HttpStatus.SC_OK, DavConstants.PROPERTY_RESOURCETYPE, null);
				} else {
					QName collectionName = DocumentHelper.createQName(DavConstants.XML_COLLECTION, MultiStatus.getDefaultNamespace());
					Element collectionElement = DocumentHelper.createElement(collectionName);
					response.addProperty(HttpStatus.SC_OK, null, DavConstants.PROPERTY_RESOURCETYPE, collectionElement);
				}
			}
			
			// TODO weitere properties ausgeben
		} catch (RepositoryException ex) {
			
		}
		
		return response;
	}
	
	public void addMember(DavResource resource, InputContext context) throws DavException {
		if (!exists()) {
			throw new DavException(HttpStatus.SC_CONFLICT);
		}
		
		// TODO filtern?
		
		ImportContext importContext = null;
		
		if (context != null) {
			importContext = new ImportContext(context, Text.getName(resource.getLocator().getRepositoryPath()));
		}

		if (!resource.importContent(importContext)) {
			throw new DavException(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
		}
		
		try {
			// HÄ?
			representedNode.save();
		} catch (RepositoryException ex) {
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}
}
