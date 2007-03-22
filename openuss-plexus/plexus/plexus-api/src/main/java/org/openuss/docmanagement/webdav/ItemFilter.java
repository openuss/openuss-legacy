package org.openuss.docmanagement.webdav;

import javax.jcr.Item;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public interface ItemFilter {
	/**
	 * Returns true, if the given {@link Item} should be filtered.
	 * @param item The item to be tested.
	 * @return true, if given item should be filtered.
	 */
	public boolean isFilteredItem(Item item);
}
