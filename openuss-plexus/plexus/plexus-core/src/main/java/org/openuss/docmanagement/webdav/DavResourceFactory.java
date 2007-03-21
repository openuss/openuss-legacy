package org.openuss.docmanagement.webdav;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.webdav.DavResourceLocator;
import org.apache.log4j.Logger;
import org.openuss.docmanagement.DocConstants;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.7
 */
public class DavResourceFactory {
	private final Logger logger = Logger.getLogger(DavResourceFactory.class);
	
	private final DavService service;
	private final DavResourceConfiguration configuration;
	
	/**
	 * Constructor.
	 * @param configuration The configuration object containing reference to the ItemFilter.
	 */
	public DavResourceFactory(DavService service, DavResourceConfiguration configuration) {
		this.service = service;
		this.configuration = configuration;
	}
	
	/**
	 * Creates an instance of {@link DavResource} identified by given locator.
	 * @param session The repository session which will be used for repository operations.
	 * @param locator The locator identifying the resource to create.
	 * @param isCollection True, if a new collection should be created. Ignored, if resource already present.
	 * @return The DavResource.
	 */
	public DavResource createResource(Session session, DavResourceLocator locator, boolean isCollection) {
		DavResource resource = null;
		
		logger.debug("Repository resource requested: " + locator.getRepositoryPath());
		
		try {
			if (session.itemExists(locator.getRepositoryPath())) {
				Item representedItem = session.getItem(locator.getRepositoryPath());

				if (representedItem.isNode()) {
					Node representedNode = (Node)representedItem;

					// creates adequate instance for node type
					if (locator.isRootLocation()) {
						resource = new DavResourceRoot(this, session, locator, representedNode);
					} else {
						resource = createResource(locator, representedNode);
					}
				} else {
					// not a node. should not occur -> ignore
					logger.debug("Non-node item requested. " + locator.getRepositoryPath());
				}
			} else {

				if (isSubscriptionCollection(locator.getRepositoryPath())) {
					resource = new DavResourceSubscription(this, session, locator, null);
				} else {

					if (isCollection) {
						resource = new DavResourceCollection(this, session, locator, null);
					} else {
						resource = new DavResourceFile(this, session, locator, null);
					}
				}
			}
		} catch (PathNotFoundException ex) {
			logger.debug("Path not found exception occurred.");
			logger.debug("Exception: " + ex.getMessage());
		} catch (RepositoryException ex) {
			logger.debug("Repository exception occurred.");
			logger.debug("Exception: " + ex.getMessage());
		}
		
		return resource;
	}
	
	/**
	 * Creates an adequate instance of DavResource descendants for node.
	 * @param locator The locator identifying the resource.
	 * @param node The node.
	 * @return The adequate instance or null.
	 * @throws RepositoryException
	 */
	private DavResource createResource(DavResourceLocator locator, Node node) throws RepositoryException {
		// examine node type
		if (node.isNodeType(DocConstants.DOC_FOLDER)) {
			// return instance of DavResourceCollection
			return new DavResourceCollection(this, node.getSession(), locator, node);
		} else if (node.isNodeType(DocConstants.DOC_FILE)) {
			// return instance of DavResourceFile
			return new DavResourceFile(this, node.getSession(), locator, node);
		} else if (node.isNodeType(DocConstants.DOC_LINK)) {
			// lookup link an create recursive call
			return createResource(locator, node.getProperty(DocConstants.PROPERTY_REFERENCE).getNode());
		}
		
		// unknown node types. should not occur -> ignore
		logger.debug("Unknown node type found. " + locator.getRepositoryPath());
		return null;
	}
	
	/**
	 * Returns, if requested resource is a subscribed enrollment.
	 * @param path The path of the resource.
	 * @return True, if path identifies a subscribed enrollment.
	 */
	private boolean isSubscriptionCollection(String path) {
		if (path == null) {
			return false;
		}
		
		// not a subscription, if path starts with /distribution, /examarea or /workingplace
		if (path.startsWith("/" + DocConstants.DISTRIBUTION) || path.startsWith("/" + DocConstants.EXAMAREA) || path.startsWith("/" + DocConstants.WORKINGPLACE)) {
			return false;
		}
		
		// not a subscription, if path is too long
		if (path.split("/").length > 2) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Getter for the {@link DavService}.
	 * @return The DavService.
	 */
	public DavService getService() {
		return service;
	}
	
	/**
	 * Getter for the {@link DavResourceConfiguration}.
	 * @return The DavResourceConfiguration.
	 */
	public DavResourceConfiguration getConfiguration() {
		return configuration;
	}
}
