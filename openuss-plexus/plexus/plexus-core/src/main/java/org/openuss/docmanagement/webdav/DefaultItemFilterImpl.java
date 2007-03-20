package org.openuss.docmanagement.webdav;

import javax.jcr.Item;
import javax.jcr.RepositoryException;

import org.apache.log4j.Logger;

/**
 * Very simple implementation of {@link ItemFilter}.
 * Only {@link Item}s in namespaces jcr and rep are filtered.
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public class DefaultItemFilterImpl implements ItemFilter {
	private final Logger logger = Logger.getLogger(DefaultItemFilterImpl.class);
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.ItemFilter#isFilteredItem(javax.jcr.Item)
	 */
	public boolean isFilteredItem(Item item) {
		// only valid items should be sent to output -> filter null values
		if (item == null) {
			return true;
		}
		
		try {
			// get name of item and look for filtered namespaces
			String name = item.getName();
			if (name.startsWith("jcr:") || name.startsWith("rep:")) {
				// filtered namespace found -> filter item
				return true;
			}
		} catch (RepositoryException ex) {
			logger.error("Repository exception occurred.");
			logger.error("Exception: " + ex.getMessage());
			// ignore exception
		}

		return false;
	}
}
