package org.openuss.docmanagement.webdav;

import java.net.URL;

import org.openuss.docmanagement.webdav.DefaultItemFilterImpl;
import org.openuss.docmanagement.webdav.ItemFilter;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.6
 */
public class DavConfigurationImpl implements DavConfiguration {
	private ItemFilter itemFilter;
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavConfiguration#parse(java.net.URL)
	 */
	public void parse(URL configurationURL) {
		// TODO angegebene Konfigurationsdatei parsen und ItemFilter entsprechend setzen
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavConfiguration#getItemFilter()
	 */
	public ItemFilter getItemFilter() {
		// return instance of DefaultItemFilterImpl, if no ItemFilter-Definition is set
		if (itemFilter == null) {
			itemFilter = new DefaultItemFilterImpl();
		}
		return itemFilter;
	}
}
