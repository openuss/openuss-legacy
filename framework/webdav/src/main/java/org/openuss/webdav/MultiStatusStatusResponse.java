package org.openuss.webdav;

/**
 * A MultiStatusResponse that contains an HTTP status.
 */
public interface MultiStatusStatusResponse extends MultiStatusResponse {
	/**
	 * @return The HTTP status code
	 */
	public int getStatusCode();
}
