package org.openuss.docmanagement.webdav;

import java.net.URL;

import org.openuss.docmanagement.webdav.DefaultItemFilterImpl;
import org.openuss.docmanagement.webdav.ItemFilter;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.6
 */
public class DavResourceConfiguration {
	private ItemFilter itemFilter;
	
	/**
	 * Parses the XML file indentified by URL as configuration file.
	 * @param configurationURL The URL identifying the configuration file.
	 */
	public void parse(URL configurationURL) {
		// TODO angegebene Konfigurationsdatei parsen und ItemFilter entsprechend setzen
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
