package org.openuss.docmanagement.webdav;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import org.openuss.docmanagement.DocConstants;


/**
 * Default implementation of interface IOHandler
 * @author David Ullrich
 * @version 0.5
 */
public class DefaultIOHandlerImpl implements IOHandler {
	// private field to store reference to hosting IOManager
	private IOManager manager;
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOHandler#canExport(org.openuss.docmanagement.webdav.ExportContext, boolean)
	 */
	public boolean canExport(ExportContext context, boolean isCollection) {
//		// export impossible, if context null or already completed
//		if (context == null || context.isCompleted()) {
//			return false;
//		}
//		
//		// get the root item for the export operation
//		Item rootItem = context.getExportRoot();
//		
//		// export impossible, if root item null
//		if (rootItem == null) {
//			return false;
//		}
//		
//		// a representation of a file has to have a child node of type jcr:content
//		if (!isCollection) {
//			// only nodes can have child nodes -> test, if it is a node
//			if (rootItem.isNode()) {
//				try {
//					Node node = (Node)rootItem;
//					// test, if child node of required type exists
//					return node.hasNode(DocConstants.JCR_CONTENT);
//				} catch (RepositoryException ex) {
//					// hasNode throwed an exception -> do nothing
//				}
//			}
//			
//			// root item is no node or exception occurred -> export impossible
//			return false;
//		}
//		
//		// a collection can be exported
//		return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOHandler#canExport(org.openuss.docmanagement.webdav.ExportContext, org.apache.jackrabbit.webdav.DavResource)
	 */
	public boolean canExport(ExportContext context, DavResource resource) {
		// export impossible, if resource is null
		if (resource == null) {
			return false;
		}
		
		// redirect to other method
		return canExport(context, resource.isCollection());
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOHandler#canImport(org.openuss.docmanagement.webdav.ImportContext, boolean)
	 */
	public boolean canImport(ImportContext context, boolean isCollection) {
//		// export impossible, if context null or already completed
//		if (context == null || context.isCompleted()) {
//			return false;
//		}
//		
//		// get the root item for the import operation
//		Item rootItem = context.getImportRoot();
//		
//		// import possible, if the root item exists and is a node and the context has a system id
//		return (rootItem != null) && rootItem.isNode() && (context.getSystemId() != null);
		return false;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOHandler#canImport(org.openuss.docmanagement.webdav.ImportContext, org.apache.jackrabbit.webdav.DavResource)
	 */
	public boolean canImport(ImportContext context, DavResource resource) {
		// import impossible, if resource is null
		if (resource == null) {
			return false;
		}

		// redirect to other method
		return canImport(context, resource.isCollection());
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOHandler#exportContent(org.openuss.docmanagement.webdav.ExportContext, boolean)
	 */
	public boolean exportContent(ExportContext context, boolean isCollection) throws IOException {
//		if (!canExport(context, isCollection)) {
//			throw new IOException();
//		}
//		
//		try {
//			// get node for export root; context is checked in canExport
//			Node node = (Node)context.getExportRoot();
//			
//			// export the actual content of the file, if not a collection
//			if (!isCollection) {
//				// get child node of type jcr:content; existence checked in canExport
//				node = node.getNode(DocConstants.JCR_CONTENT);
//			}
//			
//			// export properties
//			exportProperties(context, isCollection, node);
//			
//			// export content, if context has a stream
//			if (context.hasStream()) {
//				if (isCollection) {
//					// node is a collection -> export collection entries
//					exportCollectionEntries(context, node);
//				} else {
//					// node is a file -> export actual data
//					exportData(context, node);
//				}
//			}
//			
//			// everything is exported
//			return true;
//		} catch (RepositoryException ex) {
//			// throw IOException for RepositoryException
//			throw new IOException(ex.getMessage());
//		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOHandler#exportContent(org.openuss.docmanagement.webdav.ExportContext, org.apache.jackrabbit.webdav.DavResource)
	 */
	public boolean exportContent(ExportContext context, DavResource resource) throws IOException {
		// test, if export is possible
		if (!canExport(context, resource)) {
			throw new IOException();
		}
		
		// redirect to other method; resource is checked in canExport
		return exportContent(context, resource.isCollection());
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOHandler#getIOManager()
	 */
	public IOManager getIOManager() {
		// return content of private field
		return manager;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOHandler#getName()
	 */
	public String getName() {
		// return the name of the class as name for IOHandler
		return getClass().getName();
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOHandler#importContent(org.openuss.docmanagement.webdav.ImportContext, boolean)
	 */
	public boolean importContent(ImportContext context, boolean isCollection) throws IOException {
//		// variable for import status
//		boolean success = false;
//		
//		try {
//			// get node for import operation
//			Node node = getContentNode(context, isCollection);
//			// import data first, then properties
//			success = importData(context, isCollection, node) && importProperties(context, isCollection, node); 
//		} catch (RepositoryException ex) {
//			// mark import as unsuccessful
//			success = false;
//			// throw IOException for RepositoryException
//			throw new IOException(ex.getMessage());
//		} finally {
//			// check, if import was successful
//			if (!success) {
//				// import failed -> rollback
//				try {
//					context.getImportRoot().refresh(false);
//				} catch (RepositoryException ex) {
//					// throw IOException for RepositoryException
//					throw new IOException(ex.getMessage());
//				}
//			}
//		}
//		
//		// return status of import operation
//		return success;
		return false;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOHandler#importContent(org.openuss.docmanagement.webdav.ImportContext, org.apache.jackrabbit.webdav.DavResource)
	 */
	public boolean importContent(ImportContext context, DavResource resource) throws IOException {
		// test, if import is possible
		if (!canImport(context, resource)) {
			throw new IOException();
		}
		
		// redirect to other method; resource is checked in canImport
		return importContent(context, resource.isCollection());
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.IOHandler#setIOManager(org.openuss.docmanagement.webdav.IOManager)
	 */
	public void setIOManager(IOManager manager) {
		// store parameter in private field
		this.manager = manager;
	}
	

	/**
	 * @param context
	 * @param isCollection
	 * @param contentNode
	 * @throws IOException
	 */
	private void exportProperties(ExportContext context, boolean isCollection, Node node) throws IOException {
		try {
			// set creation time property of export context
			// the property jcr:created can be found on the export root; navigate up for files
			// test, if not a collection, node has a parent and the property is set in the repository 
			if (!isCollection && (node.getDepth() > 0) && node.getParent().hasProperty(DocConstants.JCR_CREATED)) {
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
			// throw IOException for RepositoryException
			throw new IOException(ex.getMessage());
		}
	}
	
	/**
	 * @param context
	 * @param node
	 * @throws IOException
	 * @throws RepositoryException
	 */
	private void exportData(ExportContext context, Node node) throws IOException {
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
			// throw IOException for RepositoryException
			throw new IOException(ex.getMessage());
		}
	}

	/**
	 * @param context
	 * @param node
	 * @throws IOException
	 */
	private void exportCollectionEntries(ExportContext context, Node node) throws IOException {
		// TODO Inhalt der Collection ausgeben -> als Webseite formatieren ?
		// TODO wenn root-Node, dann abonnierte.. hier?
	}

	/**
	 * @param context
	 * @param isCollection
	 * @return
	 * @throws RepositoryException
	 */
	private Node getContentNode(ImportContext context, boolean isCollection) throws RepositoryException {
		// TODO den entsprechenden Knoten aus dem Repository fischen
		//       - prüfen, ob schon existent, sonst neu anlegen
		//       - Collections sind Knoten selbst; Files enthalten Kindknoten jcr:content
		return null;
	}

	/**
	 * @param context
	 * @param isCollection
	 * @param node
	 * @return
	 * @throws IOException
	 */
	private boolean importData(ImportContext context, boolean isCollection, Node node) throws RepositoryException, IOException {
		// get input stream from context
		InputStream inStream = context.getInputStream();
		// test, if input stream exists
		if (inStream != null) {
			 // collections must not contain data
			if (isCollection) {
				return false;
			}

			try {
				// TODO bei Versionierung Knoten aus- und einchecken

				// store data in property jcr:data
				node.setProperty(DocConstants.JCR_DATA, inStream);
			} finally {
				// close input stream
				inStream.close();
			}
		}
		return true;
	}

	/**
	 * @param context
	 * @param isCollection
	 * @param node
	 * @return
	 */
	private boolean importProperties(ImportContext context, boolean isCollection, Node node) {
		// TODO MIME-Type in Abhängigkeit vom Objekttyp setzen
		try {
			// if mimetype of context is null -> remove the property
			node.setProperty(DocConstants.JCR_MIMETYPE, context.getMimeType());
		} catch (RepositoryException ex) {
			// ignore
		}
		try {
			// if encoding of context is null -> remove the property
			node.setProperty(DocConstants.JCR_ENCODING, context.getEncoding());
		} catch (RepositoryException ex) {
			// ignore
		}
		// TODO nochmal semantisch überprüfen
		try {
			Calendar modificationTime = Calendar.getInstance();
			if (context.getModificationTime() != -1) {
				modificationTime.setTimeInMillis(context.getModificationTime());
			} else {
				modificationTime.setTime(new Date());
			}
			node.setProperty(DocConstants.JCR_LASTMODIFIED, modificationTime);
		} catch (RepositoryException ex) {
			// ignore
		}
		return true;
	}
}
