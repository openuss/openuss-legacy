package org.openuss.docmanagement.webdav;

import javax.jcr.Item;

/**
 * @author David Ullrich
 * @version 0.8
 */
public interface ItemFilter {
	/**
	 * @param item
	 * @return
	 */
	public boolean isFilteredItem(Item item);
}
