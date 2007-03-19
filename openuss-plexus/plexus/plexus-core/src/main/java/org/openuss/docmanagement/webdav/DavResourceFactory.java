package org.openuss.docmanagement.webdav;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.webdav.DavResourceLocator;
import org.apache.log4j.Logger;
import org.openuss.docmanagement.DocConstants;

public class DavResourceFactory {
	private final Logger logger = Logger.getLogger(DavResourceFactory.class);
	
	private final DavResourceConfiguration configuration;
	
	public DavResourceFactory(DavResourceConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public DavResource createResource(Session session, DavResourceLocator locator) {
		DavResource resource = null;
		
		try {
			if (session.itemExists(locator.getRepositoryPath())) {
				Item representedItem = session.getItem(locator.getRepositoryPath());

				if (representedItem.isNode()) {
					Node representedNode = (Node)representedItem;
					if (representedNode.isNodeType(DocConstants.NT_FOLDER)) {
						resource = new DavResourceCollection(this, session, locator, representedNode);
					} else {
						resource = new DavResourceFile(this, session, locator, representedNode);
					}
				}
				
				// TODO kein Node. Kann das vorkommen?
			} else {
				resource = new DavResourceFile(this, session, locator, null);
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
