package org.openuss.docmanagement.webdav;

import javax.jcr.Item;

/**
 * @author David Ullrich
 * @version 0.8
 */
public interface ItemFilter {
	/**
	 * Returns true, if the given item should be filtered.
	 * @param item The item to be tested.
	 * @return true if given item should be filtered.
	 */
	public boolean isFilteredItem(Item item);
}
