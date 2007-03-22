package org.openuss.docmanagement.webdav;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.jcr.AccessDeniedException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.lock.LockException;

import org.apache.jackrabbit.util.Text;
import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavResourceLocator;
import org.apache.jackrabbit.webdav.io.InputContext;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.openuss.docmanagement.DocConstants;

/**
 * TODO: Sicherheitsabfragen und Filter
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.8
 */
public abstract class DavResource {
	protected final DavResourceFactory factory;
	protected final DavResourceLocator locator;
	protected Node representedNode;
	protected final Session session;
	
	/**
	 * Constructor.
	 * @param factory The resource factory.
	 * @param session The session with the repository.
	 * @param locator The locator identifying this resource.
	 * @param representedNode The node from the repository or null.
	 */
	protected DavResource(DavResourceFactory factory, Session session, DavResourceLocator locator, Node representedNode) {
		this.factory = factory;
		this.session = session;
		this.locator = locator;
		this.representedNode = representedNode;
	}
	
	/**
	 * Returns, whether this resource is a representation of an existing object.
	 * @return True, if this resource is a representation of an existing object.
	 */
	public boolean exists() {
		return (representedNode != null);
	}
	
	/**
	 * Returns resource type.
	 * @return True, if the resource is a collection.
	 */
	public abstract boolean isCollection();
	
	/**
	 * Getter for the {@link DavResourceFactory} used to create this resource.
	 * @return The resource factory.
	 */
	public DavResourceFactory getFactory() {
		return factory;
	}
	
	/**
	 * Getter for the {@link DavResourceLocator} identifying this resource.
	 * @return The resource locator.
	 */
	public DavResourceLocator getLocator() {
		return locator;
	}
	
	/**
	 * Returns the parent collection containing this resource or null.
	 * @return The parent collection or null.
	 */
	public DavResourceCollection getCollection() {
		DavResourceCollection parent = null;
		
		// root element has no parent
		if (!locator.isRootLocation()) {
			// get path to parent
			String parentPath = Text.getRelativeParent(getLocator().getRepositoryPath(), 1);

			// root element is locatable via path '/'
			if (parentPath.length() == 0) {
				parentPath = "/";
			}
			
			// create instance of locator and resource
			DavResourceLocator parentLocator = getLocator().getFactory().createResourceLocator(getLocator().getPrefix(), null, parentPath);
			parent = (DavResourceCollection)getFactory().createResource(session, parentLocator, true);
		}
		
		return parent;
	}
	
	/**
	 * Exports the content of the resource to the given {@link ExportContext}.
	 * @param context The export context.
	 * @throws DavException
	 */
	public void exportContent(ExportContext context) throws DavException {
		// check parameters
		if (context == null) {
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Export context must not be null.");
		}
		
		// only physically present collections can be exported
		if (!exists()) {
			throw new DavException(HttpStatus.SC_NOT_FOUND, "The resource could not be found in the repository.");
		}

		// export properties to context
		exportProperties(context);

		// generate a body for context
		if (context.hasStream()) {
			exportData(context);
		}

		// mark transaction as completed
		context.informCompleted(true);
	}
	
	/**
	 * Imports the content from the given {@link ImportContext}.
	 * @param context The import context.
	 * @return 
	 * @throws DavException
	 */
	public boolean importContent(ImportContext context) throws DavException {
		// check parameters
		if (context == null) {
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Import context must not be null.");
		}

		boolean success = false;
		
		try {
			// get reference to root node in repository
			Node rootNode = session.getRootNode();

			// create node in repository with relative path and with type doc:folder
			representedNode = rootNode.addNode(getLocator().getRepositoryPath().substring(1), DocConstants.DOC_FOLDER);
			
			// import data and properties
			success = importData(context) && importProperties(context);
		} catch (RepositoryException ex) {
			// exception occurred while accessing repository -> rethrow as DavException
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}
		
		return success;
	}
	
	/**
	 * Exports properties to the given {@link ExportContext}.
	 * @param context The export context.
	 * @throws DavException
	 */
	protected abstract void exportProperties(ExportContext context) throws DavException;
	
	/**
	 * Exports data to the given {@link ExportContext}.
	 * @param context The export context.
	 * @throws DavException
	 */
	protected abstract void exportData(ExportContext context) throws DavException;
	
	/**
	 * Imports data from given {@link ImportContext}.
	 * @param context The import context.
	 * @return True, if import was successful.
	 * @throws DavException
	 */
	protected abstract boolean importData(ImportContext context) throws DavException;
	
	/**
	 * Imports properties from {@link ImportContext}.
	 * @param context The import context.
	 * @return True, if import was successful.
	 * @throws DavException
	 */
	protected abstract boolean importProperties(ImportContext context) throws DavException;
	
	/**
	 * Returns the name to display to user.
	 * @return The display name.
	 * @throws DavException
	 */
	public String getDisplayName() throws DavException {
		if (!exists()) {
			throw new DavException(HttpStatus.SC_NOT_FOUND);
		}
		
		try {
			// lookup message property
			String name = representedNode.getProperty(DocConstants.PROPERTY_MESSAGE).getString();
			
			// return property name, if message is not set
			if (name.length() == 0) {
				return representedNode.getName();
			}
			return name;
		} catch (RepositoryException ex) {
			throw new DavException(HttpStatus.SC_SERVICE_UNAVAILABLE);
		}
	}
	
	/**
	 * Returns the date and time of creation.
	 * @return The creation date and time.
	 * @throws DavException
	 */
	public String getCreationDate() throws DavException {
		if (!exists()) {
			throw new DavException(HttpStatus.SC_NOT_FOUND);
		}
		
		try {
			// return property value, if present; root has no jcr:created-property
			if (representedNode.hasProperty(DocConstants.JCR_CREATED)) {
				return representedNode.getProperty(DocConstants.JCR_CREATED).getString();
			}
			return System.currentTimeMillis() + "";
		} catch (RepositoryException ex) {
			throw new DavException(HttpStatus.SC_SERVICE_UNAVAILABLE);
		}
	}
	
	/**
	 * Returns a filtered list of {@link DavResource} containing the descendents.
	 * @return The list of descendent resources.
	 * @throws DavException
	 */
	public List<DavResource> getMembers() throws DavException {
		if (!exists()) {
			throw new DavException(HttpStatus.SC_NOT_FOUND);
		}
		
		List<DavResource> members = new LinkedList<DavResource>();
		
		try {
			// create reference to the item filter
			ItemFilter itemFilter = getFactory().getConfiguration().getItemFilter();
			
			// iterate through child nodes in repository
			NodeIterator nodeIterator = representedNode.getNodes();
			Node node;
			DavResourceLocator locator;
			while (nodeIterator.hasNext()) {
				node = nodeIterator.nextNode();

				// create locator and instance of DavResource, if item is not to be filtered
				if (!itemFilter.isFilteredItem(node)) {
					locator = getLocator().getFactory().createResourceLocator(getLocator().getPrefix(), null, node.getPath());
					members.add(getFactory().createResource(session, locator, false));
				}
			}
		} catch (RepositoryException ex) {
			// rethrow RepositoryException as DavException
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR);
		}
		
		return members;
	}
	
	/**
	 * Returns response containing the requested properties.
	 * @param properties The names of the requested properties or null.
	 * @param namesOnly True, if only the names are requested.
	 * @return The response containing the properties.
	 * @throws DavException
	 */
	public MultiStatusResponse getProperties(List<String> properties, boolean namesOnly) throws DavException {
		// create the response
		MultiStatusResponse response = new MultiStatusResponse(locator.getHref(isCollection()), null);
		
		// check, if all properties are requested
		boolean spoolAllProperties = ((properties == null) || (properties.size() == 0));
		
		// property creationdate
		if (spoolAllProperties || properties.contains(DavConstants.PROPERTY_CREATIONDATE)) {
			String propertyValue = null;
			if (!namesOnly) {
				propertyValue = getCreationDate();
			}
			response.addProperty(HttpStatus.SC_OK, DavConstants.PROPERTY_CREATIONDATE, propertyValue);
		}

		// property displayname
		if (spoolAllProperties || properties.contains(DavConstants.PROPERTY_DISPLAYNAME)) {
			String propertyValue = null;
			if (!namesOnly) {
				propertyValue = getDisplayName();
			}
			response.addProperty(HttpStatus.SC_OK, DavConstants.PROPERTY_DISPLAYNAME, propertyValue);
		}

		// property resourcetype
		if (spoolAllProperties || properties.contains(DavConstants.PROPERTY_RESOURCETYPE)) {
			if (namesOnly || !isCollection()) {
				response.addProperty(HttpStatus.SC_OK, DavConstants.PROPERTY_RESOURCETYPE, null);
			} else {
				QName collectionName = DocumentHelper.createQName(DavConstants.XML_COLLECTION, MultiStatus.getDefaultNamespace());
				Element collectionElement = DocumentHelper.createElement(collectionName);
				response.addProperty(HttpStatus.SC_OK, null, DavConstants.PROPERTY_RESOURCETYPE, collectionElement);
			}
		}

		return response;
	}
	
	/**
	 * Adds a member to this resource.
	 * @param resource
	 * @param context
	 * @throws DavException
	 */
	public void addMember(DavResource resource, InputContext context) throws DavException {
		// return status code 409 (CONFLICT), if this resource is not physically present
		if (!exists()) {
			throw new DavException(HttpStatus.SC_CONFLICT);
		}
		
		ImportContext importContext = null;
		try {
			// create instance of ImportContext
			if (context != null) {
				importContext = new ImportContext(context, Text.getName(resource.getLocator().getRepositoryPath()));
			}
			
			// try to import content
			if (!resource.importContent(importContext)) {
				// import failed
				throw new DavException(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
			}
			
			// persist pending changes.
			representedNode.save();
		} catch (IOException ex) {
			// error while creating import context
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		} catch (RepositoryException ex) {
			// error while persisting changes
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}
	
	/**
	 * Copies data and properties from source.
	 * @param source The source of the data and the properties.
	 * @param recursive True, if members of source should be copied.
	 * @return The multi-status containing error informations.
	 * @throws DavException
	 */
	public MultiStatus copyFrom(DavResource source, boolean recursive) throws DavException {
		if ((source == null) || !source.exists()) {
			throw new DavException(HttpStatus.SC_NOT_FOUND, "Source resource not found.");
		}
		
		// prepare multi-status for errors
		MultiStatus multistatus = new MultiStatus();

		// copy this resource
		boolean success = copyDataFrom(source) && copyPropertiesFrom(source);

		// remove copied data and properties, if not successful
		if (success) {
			// go deeper in hierarchy, if copy was successful
			if (recursive) {
				// iterate through members and call copy method recursively
				List<DavResource> members = source.getMembers();
				Iterator<DavResource> iterator = members.iterator();
				DavResource member;
				String destinationPath;
				DavResourceLocator destinationLocator;
				DavResource destinationMember;
				while (iterator.hasNext()) {
					member = iterator.next();
					
					// build path by replacing source with destination path
					destinationPath = member.getLocator().getRepositoryPath().replaceFirst(source.getLocator().getRepositoryPath(), getLocator().getRepositoryPath());
					
					// get locator and instance of resource
					destinationLocator = getLocator().getFactory().createResourceLocator(getLocator().getPrefix(), null, destinationPath);
					destinationMember = getFactory().createResource(session, destinationLocator, member.isCollection());
					
					// recursive call of method without rethrow
					destinationMember.copyFrom(member, multistatus, true);
				}
			}
		} else {
			remove();
		}
		
		return multistatus;
	}
	
	/**
	 * Helper method, which recursively copies data and properties from source.
	 * @param source The source of the data and the properties.
	 * @param multistatus The multi-status to fill with error informations.
	 * @param recursive True, if members of source should be copied.
	 * @throws DavException
	 */
	protected void copyFrom(DavResource source, MultiStatus multistatus, boolean recursive) throws DavException {
		// copy to this resource
		boolean success = copyDataFrom(source, multistatus) && copyPropertiesFrom(source, multistatus);

		// remove copied data and properties, if not successful
		if (success) {
			// go deeper in hierarchy, if copy was successful
			if (recursive) {
				// iterate through members and call copy method recursively
				List<DavResource> members = source.getMembers();
				Iterator<DavResource> iterator = members.iterator();
				DavResource member;
				String destinationPath;
				DavResourceLocator destinationLocator;
				DavResource destinationMember;
				while (iterator.hasNext()) {
					member = iterator.next();
					
					// build path by replacing source with destination path
					destinationPath = member.getLocator().getRepositoryPath().replaceFirst(source.getLocator().getRepositoryPath(), getLocator().getRepositoryPath());
					
					// get locator and instance of resource
					destinationLocator = getLocator().getFactory().createResourceLocator(getLocator().getPrefix(), null, destinationPath);
					destinationMember = getFactory().createResource(session, destinationLocator, member.isCollection());
					
					// recursive call of method with multi-status
					destinationMember.copyFrom(member, multistatus, true);
				}
			}
		} else {
			// copy operation not successful -> remove copied data and properties
			remove(multistatus);
		}
	}
	
	/**
	 * Helper method, which copies data from source.
	 * @param source The source of the properties.
	 * @return True, if copy operation was successful.
	 * @throws DavException
	 */
	protected abstract boolean copyDataFrom(DavResource source) throws DavException;
	
	/**
	 * Helper method, which copies data from source and reports errors to multi-status.
	 * @param source The source of the properties.
	 * @param multistatus The multi-status to fill with error informations.
	 * @return True, if copy operation was successful.
	 * @throws DavException
	 */
	protected boolean copyDataFrom(DavResource source, MultiStatus multistatus) throws DavException {
		try {
			// delegate to other method
			copyDataFrom(source);
		} catch (DavException ex) {
			// add status codes other than 500 (Internal Server Error) too multi-status
			if (ex.getErrorCode() != HttpStatus.SC_INTERNAL_SERVER_ERROR) {
				multistatus.addResponse(new MultiStatusResponse(getLocator().getHref(isCollection()), ex.getErrorCode(), null));
			} else {
				throw ex;
			}
		}
		
		return true;
	}

	/**
	 * Helper method, which copies properties from source.
	 * @param source The source of the properties.
	 * @return True, if copy operation was successful.
	 * @throws DavException
	 */
	protected abstract boolean copyPropertiesFrom(DavResource source) throws DavException;

	/**
	 * Helper method, which copies properties from source and reports errors to multi-status.
	 * @param source The source of the properties.
	 * @param multistatus The multi-status to fill with error informations.
	 * @return True, if copy operation was successful.
	 * @throws DavException
	 */
	protected boolean copyPropertiesFrom(DavResource source, MultiStatus multistatus) throws DavException {
		try {
			// delegate to other method
			copyPropertiesFrom(source);
		} catch (DavException ex) {
			// add status codes other than 500 (Internal Server Error) too multi-status
			if (ex.getErrorCode() != HttpStatus.SC_INTERNAL_SERVER_ERROR) {
				multistatus.addResponse(new MultiStatusResponse(getLocator().getHref(isCollection()), ex.getErrorCode(), null));
			} else {
				throw ex;
			}
		}
		
		return true;
	}
	
	public int getVisibility() throws DavException {
		if (!exists()) {
			throw new DavException(HttpStatus.SC_NOT_FOUND);
		}
		
		try {
			// lookup visibility
			return (int)representedNode.getProperty(DocConstants.PROPERTY_VISIBILITY).getLong();
		} catch (RepositoryException ex) {
			throw new DavException(HttpStatus.SC_SERVICE_UNAVAILABLE);
		}
	}
	
	/**
	 * Removes the resource and returns multi-status, if any error occurred.
	 * @return The multi-status containing error informations.
	 * @throws DavException
	 */
	public MultiStatus remove() throws DavException {
		if (!exists()) {
			throw new DavException(HttpStatus.SC_NOT_FOUND, "Resource not found: " + getLocator().getResourcePath());
		}

		// prepare multi-status for errors
		MultiStatus multistatus = new MultiStatus();
		
		try {
			// iterate through members and call remove method recursively
			List<DavResource> members = getMembers();
			Iterator<DavResource> iterator = members.iterator();
			DavResource member;
			while (iterator.hasNext()) {
				member = iterator.next();
				member.remove(multistatus);
			}
			
			// all members removed; remove this, if multistatus has no responses
			if (multistatus.getResponses().size() == 0) {
				representedNode.remove();
				representedNode = null;
			}
		} catch (AccessDeniedException ex) {
			// access on node denied
			throw new DavException(HttpStatus.SC_FORBIDDEN, "Deleting the resource is not allowed.");
		} catch (LockException ex) {
			// resource is locked
			throw new DavException(HttpStatus.SC_LOCKED, "The resource is locked.");
		} catch (RepositoryException ex) {
			// undefined exception occurred -> rethrow as DavException
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}
		
		return multistatus;
	}
	
	/**
	 * Helper method which recursively tries to remove members and the resource itself.
	 * @param multistatus The multi-status to fill with error responses.
	 * @throws DavException
	 */
	protected void remove(MultiStatus multistatus) throws DavException {
		try {
			// iterate through members and call remove method recursively
			List<DavResource> members = getMembers();
			Iterator<DavResource> iterator = members.iterator();
			DavResource member;
			while (iterator.hasNext()) {
				member = iterator.next();
				member.remove(multistatus);
			}
			
			// all members removed; remove this, if multistatus has no responses
			if (multistatus.getResponses().size() == 0) {
				representedNode.remove();
				representedNode = null;
			}
		} catch (AccessDeniedException ex) {
			// access on node denied
			multistatus.addResponse(new MultiStatusResponse(getLocator().getHref(isCollection()), HttpStatus.SC_FORBIDDEN, null));
		} catch (LockException ex) {
			// resource is locked
			multistatus.addResponse(new MultiStatusResponse(getLocator().getHref(isCollection()), HttpStatus.SC_LOCKED, null));
		} catch (RepositoryException ex) {
			// undefined exception occurred -> rethrow as DavException
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}
}
