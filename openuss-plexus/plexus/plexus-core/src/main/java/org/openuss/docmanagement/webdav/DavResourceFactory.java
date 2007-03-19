package org.openuss.docmanagement.webdav;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.webdav.DavResourceLocator;
import org.apache.log4j.Logger;

public class DavResourceFactory {
	private final Logger logger = Logger.getLogger(DavResourceFactory.class);
	
	private final DavResourceConfiguration configuration;
	
	public DavResourceFactory(DavResourceConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public DavResource createResource(Session session, DavResourceLocator locator) {
		DavResource resource = null;
		
		try {
			Item representedItem = session.getItem(locator.getRepositoryPath());
			
			if ((representedItem != null) && (representedItem.isNode())) {
				resource = new DavResourceCollection(this, locator, (Node)representedItem);
			} else {
				resource = new DavResourceCollection(this, locator, null);
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
