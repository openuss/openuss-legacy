package org.openuss.docmanagement.webdav;

import java.net.URL;

import org.openuss.docmanagement.webdav.DefaultIOManagerImpl;
import org.openuss.docmanagement.webdav.DefaultItemFilterImpl;
import org.openuss.docmanagement.webdav.IOManager;
import org.openuss.docmanagement.webdav.ItemFilter;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.5
 */
public class DavResourceConfiguration {
	private ItemFilter itemFilter;
	
	public void parse(URL configurationURL) {
		// TODO angegebene Konfigurationsdatei parsen und IOManager und ItemFilter entsprechend setzen
	}
	
	/**
	 * Getter for the {@link ItemFilter}.
	 * @return The ItemFilter.
	 */
	public ItemFilter getItemFilter() {
		if (itemFilter == null) {
			itemFilter = new DefaultItemFilterImpl();
		}
		return itemFilter;
	}
}
