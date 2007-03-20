package org.openuss.docmanagement.webdav;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.7
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
			DavResource[] members = resource.getMembers();
			for (int i = 0, j = members.length; i < j; i++) {
				// call method with adjusted depth-value, if not infinity
				if (depth == DavConstants.DEPTH_INFINITY) {
					addResponse(multistatus, members[i], properties, namesOnly, depth);
				} else {
					addResponse(multistatus, members[i], properties, namesOnly, 0);
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
	 * @throws DavException
	 */
	public void copyResource(DavResourceLocator source, DavResourceLocator target) throws DavException {
		// check parameters
		if ((source == null) || (target == null)) {
			logger.error("Source or target locator is null.");
			throw new DavException(HttpStatus.SC_METHOD_FAILURE, "Source and target locator must not be null.");
		}
		// TODO Methode COPY implementieren
		throw new DavException(HttpStatus.SC_NOT_IMPLEMENTED, "COPY not implemented.");
	}
	
	/**
	 * Moves a repository object to a different location.
	 * @param source The locator identifying the source repository object.
	 * @param target The locator identifying the target for the copy operation.
	 * @throws DavException
	 */
	public void moveResource(DavResourceLocator source, DavResourceLocator target) throws DavException {
		// check parameters
		if ((source == null) || (target == null)) {
			logger.error("Source or target locator is null.");
			throw new DavException(HttpStatus.SC_METHOD_FAILURE, "Source and target locator must not be null.");
		}
		// TODO Methode MOVE implementieren
		throw new DavException(HttpStatus.SC_NOT_IMPLEMENTED, "MOVE not implemented.");
	}
	
	/**
	 * Deletes a repository object.
	 * @param locator The locator identifying the repository object to delete.
	 * @throws DavException
	 */
	public void deleteResource(DavResourceLocator locator) throws DavException {
		// check parameters
		if (locator == null) {
			logger.error("Locator is null.");
			throw new DavException(HttpStatus.SC_METHOD_FAILURE, "Locator must not be null.");
		}
		// TODO Methode DELETE implementieren
		throw new DavException(HttpStatus.SC_NOT_IMPLEMENTED, "DELETE not implemented.");
	}
	
	/**
	 * Getter for instance of {@link DavResourceFactory}.
	 * @return The resource factory.
	 */
	public DavResourceFactory getResourceFactory() {
		if (resourceFactory == null) {
			resourceFactory = new DavResourceFactory(configuration);
		}
		return resourceFactory;
	}
}
