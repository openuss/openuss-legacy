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
 * @version 0.6
 */
public class DavResourceFactory {
	private final Logger logger = Logger.getLogger(DavResourceFactory.class);
	
	private final DavResourceConfiguration configuration;
	
	/**
	 * Constructor.
	 * @param configuration The configuration object containing reference to the ItemFilter.
	 */
	public DavResourceFactory(DavResourceConfiguration configuration) {
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
		
		try {
			if (session.itemExists(locator.getRepositoryPath())) {
				Item representedItem = session.getItem(locator.getRepositoryPath());

				if (representedItem.isNode()) {
					Node representedNode = (Node)representedItem;
					// HACK
					if (locator.isRootLocation() || (representedNode.isNodeType(DocConstants.DOC_FOLDER))) {
						resource = new DavResourceCollection(this, session, locator, representedNode);
					} else {
						resource = new DavResourceFile(this, session, locator, representedNode);
					}
				}
				
				// TODO kein Node. Kann das vorkommen?
			} else {
				if (isCollection) {
					resource = new DavResourceCollection(this, session, locator, null);
				} else {
					resource = new DavResourceFile(this, session, locator, null);
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
	
	public DavResourceConfiguration getConfiguration() {
		return configuration;
	}
}
