package org.openuss.docmanagement.webdav;

import javax.jcr.Item;

public interface ItemFilter {
	public boolean isFilteredItem(Item item);
}
