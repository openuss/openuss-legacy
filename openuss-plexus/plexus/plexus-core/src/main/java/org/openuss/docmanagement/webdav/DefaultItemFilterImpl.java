package org.openuss.docmanagement.webdav;

import javax.jcr.Item;
import javax.jcr.RepositoryException;

import org.apache.log4j.Logger;

/**
 * Very simple implementation of ItemFilter {@link ItemFilter}.
 * Only Items in namespace jcr and rep are filtered.
 * 
 * TODO 0.9 extract namespaces to configuration file
 * TODO 1.0 successful unit tests
 * @author David Ullrich
 * @version 0.8
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
			logger.debug("Repository exception occurred.");
			logger.debug("Exception: " + ex.getMessage());
			// ignore exception
		}

		return false;
	}
}
