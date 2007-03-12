package org.openuss.docmanagement;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.log4j.Logger;

public class ResourceFactory {
	private final Logger logger = Logger.getLogger(ResourceFactory.class);
	
	public ResourceDao createResource(Session session, String resourcePath) {
		ResourceDao resource = null;
		
		// return null, if session or path is null or path is empty
		if ((session == null) || (resourcePath == null) || (resourcePath.length() == 0)) {
			return null;
		}
		
		try {
			Item representedItem = session.getItem(resourcePath);
			// return null, if item is null or not a node
			if ((representedItem == null) || !representedItem.isNode()) {
				return null;
			}
			
			Node node = (Node)representedItem;
			if (node.isNodeType("nt:file")) {
				logger.debug("File found.");
				resource = new FileDao(node);
			} else if (node.isNodeType("nt:folder")) {
				logger.debug("Folder found.");
				// TODO genauer differenzieren
				resource = new FolderDao(node);
			} else {
				logger.debug("Unsupported node type found: " + node.getPrimaryNodeType().toString());
			}
		} catch (RepositoryException ex) {
			logger.debug("Repository exception occurred.");
			logger.debug("Exception: " + ex.getMessage());
		}
		return resource;
	}
}
