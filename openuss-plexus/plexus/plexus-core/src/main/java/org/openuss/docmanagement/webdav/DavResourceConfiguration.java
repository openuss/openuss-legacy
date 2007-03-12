package org.openuss.docmanagement.webdav;

import java.net.URL;

import org.openuss.docmanagement.webdav.DefaultIOManagerImpl;
import org.openuss.docmanagement.webdav.DefaultItemFilterImpl;
import org.openuss.docmanagement.webdav.IOManager;
import org.openuss.docmanagement.webdav.ItemFilter;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class DavResourceConfiguration {
	private IOManager ioManager;
	private ItemFilter itemFilter;
	
	/**
	 * @param configurationURL
	 */
	public void parse(URL configurationURL) {
		// TODO angegebene Konfigurationsdatei parsen und IOManager und ItemFilter entsprechend setzen
	}
	
	/**
	 * @return
	 */
	public IOManager getIOManager() {
		if (ioManager == null) {
			ioManager = new DefaultIOManagerImpl();
		}
		return ioManager;
	}
	
	/**
	 * @return
	 */
	public ItemFilter getItemFilter() {
		if (itemFilter == null) {
			itemFilter = new DefaultItemFilterImpl();
		}
		return itemFilter;
	}
}
