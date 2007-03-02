package org.openuss.docmanagement.webdav;

import javax.jcr.Item;
import javax.jcr.RepositoryException;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class DefaultItemFilterImpl implements ItemFilter {
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.ItemFilter#isFilteredItem(javax.jcr.Item)
	 */
	public boolean isFilteredItem(Item item) {
		// test parameter
		if (item == null) {
			return true;
		}
		
		try {
			String name = item.getName();
			if (name.startsWith("jcr:") || name.startsWith("rep:")) {
				return true;
			}
		} catch (RepositoryException ex) {
			// ignore
		}

		return false;
	}
}
