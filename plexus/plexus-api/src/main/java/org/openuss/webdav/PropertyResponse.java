package org.openuss.webdav;

/**
 * An element of a MultiStatus object detailling the answer of a PROPFIND or PROPPATCH.
 */
public interface PropertyResponse extends MultiStatusResponse {
	/**
	 * Adds a new found / not found property
	 * 
	 * @param statusCode The HTTP status code of the property  
	 * @param prn The property response node to add
	 */
	public void addProperty(int statusCode, PropertyResponseNode prn);
}
