package org.openuss.docmanagement.webdav;

import java.net.URL;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.6
 */
public interface DavConfiguration {
	/**
	 * Parses the XML file indentified by URL as configuration file.
	 * @param configurationURL The URL identifying the configuration file.
	 */
	public void parse(URL configurationURL);
	
	/**
	 * Getter for the {@link ItemFilter}.
	 * @return The ItemFilter.
	 */
	public ItemFilter getItemFilter();
}
