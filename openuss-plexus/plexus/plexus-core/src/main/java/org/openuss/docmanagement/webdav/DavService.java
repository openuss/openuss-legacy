package org.openuss.docmanagement.webdav;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavResourceLocator;
import org.apache.jackrabbit.webdav.io.InputContext;
import org.apache.jackrabbit.webdav.io.OutputContext;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.openuss.lecture.Enrollment;

/**
 * TODO: RFC compliance for spool, add sicherstellen
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.8
 */
public class DavService {
	private final Logger logger = Logger.getLogger(DavService.class);
	
	private final DavResourceConfiguration configuration;
	private Session session;
	private List<Enrollment> subscribedEnrollments;
	private DavResourceFactory resourceFactory;
	
	/**
	 * Constructor.
	 * @param configuration An instance of DavResourceConfiguration {@link ItemFilter}.
	 */
	public DavService(DavResourceConfiguration configuration) {
		this.configuration = configuration;
		logger.debug("Init done.");
	}
	
	/**
	 * Getter for the {@link Session}.
	 * @return
	 */
	public Session getSession() {
		return session;
	}
	
	/**
	 * Setter for the {@link Session}.
	 * @param session The repository session to set.
	 */
	public void setSession(Session session) {
		this.session = session;
	}

	/**
	 * Getter for the list of subscribed {@link Enrollment}s.
	 * @return The subscribed Enrollments.
	 */
	public List<Enrollment> getSubscribedEnrollments() {
		return subscribedEnrollments;
	}

	/**
	 * Setter for the list of subscribed {@link Enrollment}s.
	 * @param subscribedEnrollments
	 */
	public void setSubscribedEnrollments(List<Enrollment> subscribedEnrollments) {
		this.subscribedEnrollments = subscribedEnrollments;
	}

	/**
	 * Spools the {@link DavResource} identified by the given {@link DavResourceLocator} to the given {@link OutputContext}.
	 * @param context The target context to which the resource should be spooled.
	 * @param locator The locator identifying the resource to spool.
	 * @throws DavException
	 */
	public void spoolResource(OutputContext context, DavResourceLocator locator) throws DavException {
		// check parameters
		if ((context == null) || (locator == null)) {
			logger.error("Context or locator is null. Cannot spool resource.");
			throw new DavException(HttpStatus.SC_METHOD_FAILURE, "Context and locator must not be null.");
		}

		// create instance of DavResource
		DavResource resource = getResourceFactory().createResource(getSession(), locator, false);
		try {
			// create export context and export content
			ExportContext exportContext = new ExportContext(context);
			resource.exportContent(exportContext);
		} catch (IOException ex) {
			logger.error("IO exception occurred.");
			logger.error("Exception: " + ex.getMessage());
			// rethrow IOException as DavException
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}
	
	/**
	 * Adds an member identified by the given {@link DavResourceLocator} and imports required data from given {@link InputContext}.
	 * @param context The source context from which the data for the resource should be imported.
	 * @param locator The locator identifying the resource to add.
	 * @throws DavException
	 */
	public void addMember(InputContext context, DavResourceLocator locator) throws DavException {
		// check parameters
		if ((context == null) || (locator == null)) {
			logger.error("Context or locator is null. Cannot add member.");
			throw new DavException(HttpStatus.SC_METHOD_FAILURE, "Context and locator must not be null.");
		}
		
		// create instance of DavResource and import content
		DavResource resource = getResourceFactory().createResource(getSession(), locator, false);
		resource.getCollection().addMember(resource, context);
	}
	
	/**
	 * Retrieves properties from a resource and returns an instance of {@link MultiStatus} as demanded by RFC2518.
	 * @param requestDocument The XML document containing data for the PROPFIND method.
	 * @param locator The locator identifying the root of the request.
	 * @param depth The recursion depth.
	 * @return The response Multistatus containing requested property informations. 
	 * @throws DavException
	 */
	public MultiStatus getProperties(Document requestDocument, DavResourceLocator locator, int depth) throws DavException {
		// check parameters
		if (locator == null) {
			logger.error("Locator is null.");
			throw new DavException(HttpStatus.SC_METHOD_FAILURE, "Locator must not be null.");
		}
		
		// create instance of DavResource 
		DavResource resource = getResourceFactory().createResource(getSession(), locator, false);
		
		// check existance of resource and signal a 404 to the client, if necessary
		if (!resource.exists()) {
			logger.debug("Resource not found in repository.");
			throw new DavException(HttpStatus.SC_NOT_FOUND, "Resource not found in repository.");
		}
		
		MultiStatus multistatus = new MultiStatus();
		
		// examine request and add response to multi-status
		List<String> requestedProperties = getRequestedProperties(requestDocument);
		boolean namesOnly = isNamesOnlyRequest(requestDocument);
		addResponse(multistatus, resource, requestedProperties, namesOnly, depth);
		
		return multistatus;
	}
	
	/**
	 * Extracts the names of the requested properties from the request body as described in RFC2518.
	 * Can be null, which is equivalent to a request for all properties.
	 * @param requestDocument The XML document containing data for the PROPFIND method.
	 * @return The list of names of requested properties or null, if all properties are requested.
	 * @throws DavException
	 */
	private List<String> getRequestedProperties(Document requestDocument) throws DavException {
		List<String> propertyNames = null;
		
		// an empty document is equivalent to request-all-properties
		if (requestDocument != null) {
			Element propfindElement = requestDocument.getRootElement();
			// root element must not be null, if document is not null
			if ((propfindElement == null) || (!propfindElement.getName().equals(DavConstants.XML_PROPFIND))) {
				logger.debug("Ill-formed XML or not a propfind root element.");
				throw new DavException(HttpStatus.SC_BAD_REQUEST, "Request body must be well-formed XML with a propfind root element.");
			}

			// (prop|allprop|propname) element required
			Element allPropElement = propfindElement.element(DavConstants.XML_ALLPROP);
			Element propNameElement = propfindElement.element(DavConstants.XML_PROPNAME);
			Element propElement = propfindElement.element(DavConstants.XML_PROP);
			
			boolean canEvaluate;
			if (propElement != null) {
				// prop element found -> no other element allowed
				canEvaluate = ((allPropElement == null) && (propNameElement == null));
			} else {
				// prop missing -> one and only one of allprop or propname required
				canEvaluate = ((allPropElement != null) ^ (propNameElement != null));
			}
			
			// throw exception, if more than one element was found
			if (!canEvaluate) {
				throw new DavException(HttpStatus.SC_BAD_REQUEST, "One and only one of elements allprop, propname or prop is valid.");
			}
			
			// iterate through property names and add them to the list, if prop element was found
			if ((propElement != null) && (propElement.hasContent())) {
				propertyNames = new ArrayList<String>();
				Iterator elementIterator = propElement.elementIterator();
				Element element;
				while (elementIterator.hasNext()) {
					element = (Element)elementIterator.next();
					propertyNames.add(element.getName());
				}
			}
		}
		
		return propertyNames;
	}
	
	/**
	 * Returns true, if only the names of the properties are requested.
	 * @param requestDocument The XML document from the request body to be examined.
	 * @return True, if only the names of the properties are requested.
	 * @throws DavException 
	 */
	private boolean isNamesOnlyRequest(Document requestDocument) throws DavException {
		// an empty document is equivalent to request-all-properties with values
		if (requestDocument != null) {
			Element propfindElement = requestDocument.getRootElement();
			// root element must not be null, if document is not null
			if ((propfindElement == null) || (!propfindElement.getName().equals(DavConstants.XML_PROPFIND))) {
				logger.debug("Ill-formed XML or not a propfind root element.");
				throw new DavException(HttpStatus.SC_BAD_REQUEST, "Request body must be well-formed XML with a propfind root element.");
			}

			// (prop|allprop|propname) element required
			Element allPropElement = propfindElement.element(DavConstants.XML_ALLPROP);
			Element propNameElement = propfindElement.element(DavConstants.XML_PROPNAME);
			Element propElement = propfindElement.element(DavConstants.XML_PROP);
			
			boolean canEvaluate;
			if (propElement != null) {
				// prop element found -> no other element allowed
				canEvaluate = ((allPropElement == null) && (propNameElement == null));
			} else {
				// prop missing -> one and only one of allprop or propname required
				canEvaluate = ((allPropElement != null) ^ (propNameElement != null));
			}
			
			// throw exception, if more than one element was found
			if (!canEvaluate) {
				throw new DavException(HttpStatus.SC_BAD_REQUEST, "One and only one of elements allprop, propname or prop is valid.");
			}
			
			return (propNameElement != null);
		}
		return false;
	}
	
	/**
	 * Recursively add response data for a given {@link DavResource} to given {@link MultiStatus}. 
	 * @param multistatus The multi-status containing responses.
	 * @param resource The resource to request properties from.
	 * @param properties The names of the requested properties.
	 * @param namesOnly Indicates whether the property values can be ignored.
	 * @param depth The recursion depth.
	 * @throws DavException
	 */
	private void addResponse(MultiStatus multistatus, DavResource resource, List<String> properties, boolean namesOnly, int depth) throws DavException {
		// get response for requested properties and add it to multistatus
		multistatus.addResponse(resource.getProperties(properties, namesOnly));
		
		// recursive processing, if depth > 0
		if (depth > 0) {
			// iterate through members
			List<DavResource> members = resource.getMembers();
			Iterator<DavResource> iterator = members.iterator();
			DavResource member;
			while (iterator.hasNext()) {
				member = iterator.next();
				if (depth == DavConstants.DEPTH_INFINITY) {
					addResponse(multistatus, member, properties, namesOnly, depth);
				} else {
					addResponse(multistatus, member, properties, namesOnly, 0);
				}
			}
		}
	}
	
	// TODO setProperties
	
	/**
	 * Creates a collection identified by the given instance of {@link DavResourceLocator}.
	 * @param locator The locator identifying the collection to create.
	 * @throws DavException
	 */
	public void createCollection(DavResourceLocator locator) throws DavException {
		// check parameters
		if (locator == null) {
			logger.error("Locator is null.");
			throw new DavException(HttpStatus.SC_METHOD_FAILURE, "Locator must not be null.");
		}
		
		// create resource and add to parent collection
		DavResource resource = getResourceFactory().createResource(session, locator, true);
		resource.getCollection().addMember(resource, null);
	}
	
	/**
	 * Creates a copy of a repository object.
	 * @param source The locator identifying the source repository object.
	 * @param target The locator identifying the target for the copy operation.
	 * @param overwriteAllowed
	 * @param recursive
	 * @return The multi-status containing error information or null.
	 * @throws DavException 
	 */
	public MultiStatus copyResource(DavResourceLocator source, DavResourceLocator destination, boolean overwriteAllowed, boolean recursive) throws DavException {
		return copyResource(source, destination, overwriteAllowed, recursive, true);
	}
	
	/**
	 * Creates a copy of a repository object.
	 * @param source The locator identifying the source repository object.
	 * @param target The locator identifying the target for the copy operation.
	 * @param overwriteAllowed True, if destination resource may be non-null.
	 * @param recursive True, if copy operation should be performed for whole subtree.
	 * @param autoCommit True, if pending changes should be persisted instantly.
	 * @return The multi-status containing error informations or null.
	 * @throws DavException
	 */
	private MultiStatus copyResource(DavResourceLocator source, DavResourceLocator destination, boolean overwriteAllowed, boolean recursive, boolean autoCommit) throws DavException {
		// check parameters
		if ((source == null) || (destination == null)) {
			logger.error("Source or destination locator is null.");
			throw new DavException(HttpStatus.SC_METHOD_FAILURE, "Source and destination locator must not be null.");
		}
		
		// send status code 403 (FORBIDDEN), if source and target are the same
		if (source.getRepositoryPath().equals(destination.getRepositoryPath())) {
			throw new DavException(HttpStatus.SC_FORBIDDEN, "The source and target must not be the same.");
		}

		// create instance of destination resource and check precondition
		DavResource destinationResource = getResourceFactory().createResource(getSession(), destination, true);
		boolean overwriteNeeded = destinationResource.exists();
		if (overwriteNeeded && !overwriteAllowed) {
			throw new DavException(HttpStatus.SC_PRECONDITION_FAILED, "Overwrite header was set to T and destination is non-null.");
		}
		
		// create instance of source resource
		DavResource sourceResource = getResourceFactory().createResource(getSession(), source, true);
		
		// copy resource
		MultiStatus errorStatus = destinationResource.copyFrom(sourceResource, recursive);
		
		// persist pending changes
		try {
			// persist pending changes, if autoCommit is set
			if (autoCommit) {
				getSession().save();
			}
		} catch (RepositoryException ex) {
			// error occurred while persisting pending changes
			logger.error("Repository exception occurred.");
			logger.error("Exception: " + ex.getMessage());
			// rethrow as DavException 
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}
		
		// send adequate success status code to client
		if (errorStatus == null) {
			if (overwriteNeeded) {
				throw new DavException(HttpStatus.SC_NO_CONTENT, "The source resource was successfully copied to a pre-existing destination resource.");
			}
			throw new DavException(HttpStatus.SC_CREATED, "The source resource was successfully copied.");
		}

		return errorStatus;
	}
	
	/**
	 * Moves a repository object to a different location.
	 * @param source The locator identifying the source repository object.
	 * @param target The locator identifying the target for the copy operation.
	 * @param overwriteAllowed True, if destination resource may be non-null.
	 * @return The multi-status containing error informations or null.
	 * @throws DavException
	 */
	public MultiStatus moveResource(DavResourceLocator source, DavResourceLocator destination, boolean overwriteAllowed) throws DavException {
		boolean overwriteNeeded = false;
		MultiStatus errorStatus = null;
		
		try {
			// create copy of resource prior to removing the source
			errorStatus = copyResource(source, destination, overwriteAllowed, true, false);
		} catch (DavException ex) {
			// examine exception from copy operation
			if (ex.getErrorCode() == HttpStatus.SC_CREATED) {
				// copy successful without overwrite
				overwriteNeeded = false;
			} else if (ex.getErrorCode() == HttpStatus.SC_NO_CONTENT) {
				// copy successful with overwrite
				overwriteNeeded = true;
			} else {
				// undefined exception -> rethrow
				throw ex;
			}
		}
		
		// delete resource if copy operation was successful
		if (errorStatus == null) {
			errorStatus = deleteResource(source, false);
		}
		
		// persist changes, if both operations were successful
		try {
			getSession().save();
		} catch (RepositoryException ex) {
			// error occurred while persisting pending changes
			logger.error("Repository exception occurred.");
			logger.error("Exception: " + ex.getMessage());
			// rethrow as DavException 
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		if (errorStatus == null) {

			// send adequate success status code to client
			if (overwriteNeeded) {
				throw new DavException(HttpStatus.SC_NO_CONTENT, "The source resource was successfully copied to a pre-existing destination resource.");
			}
			throw new DavException(HttpStatus.SC_CREATED, "The source resource was successfully copied.");
		}
		
		return errorStatus;
	}
	
	/**
	 * Deletes a resource identified by the {@link DavResourceLocator}.
	 * @param locator The locator identifying the repository object to delete.
	 * @return The multi-status containing error informations or null.
	 * @throws DavException
	 */
	public MultiStatus deleteResource(DavResourceLocator locator) throws DavException {
		return deleteResource(locator, true);
	}
	
	/**
	 * Deletes a resource identified by the {@link DavResourceLocator}.
	 * @param locator The locator identifying the repository object to delete.
	 * @param autoCommit True, if pending changes should be persisted instantly.
	 * @return The multi-status containing error informations or null.
	 * @throws DavException
	 */
	private MultiStatus deleteResource(DavResourceLocator locator, boolean autoCommit) throws DavException {
		// check parameters
		if (locator == null) {
			logger.error("Locator is null.");
			throw new DavException(HttpStatus.SC_METHOD_FAILURE, "Locator must not be null.");
		}
		
		// create instance of DavResource
		DavResource resource = getResourceFactory().createResource(getSession(), locator, false);
		
		// remove resource
		MultiStatus errorStatus = resource.remove();
		// removal successful? -> persist pending changes
		if (errorStatus.getResponses().size() == 0) {
			try {
				// persist pending changes, if autoCommit is set
				if (autoCommit) {
					getSession().save();
				}
				
				// kill multi-status if transaction was successful
				errorStatus = null;
			} catch (RepositoryException ex) {
				// error occurred while persisting pending changes
				logger.error("Repository exception occurred.");
				logger.error("Exception: " + ex.getMessage());
				// rethrow as DavException
				throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			}
		}

		return errorStatus;
	}
	
	/**
	 * Getter for instance of {@link DavResourceFactory}.
	 * @return The resource factory.
	 */
	public DavResourceFactory getResourceFactory() {
		if (resourceFactory == null) {
			resourceFactory = new DavResourceFactory(this, configuration);
		}
		return resourceFactory;
	}
}
