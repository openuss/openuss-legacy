package org.openuss.webdav;

/**
 * Any WebDAVException concerning a specific resource.
 *
 */
public abstract class WebDAVHrefException extends WebDAVException {

	public WebDAVHrefException(int statusCode, String msg, Throwable cause) {
		super(statusCode, msg, cause);
	}


	/**
	 * @return The path that was resolved when the exception occured.
	 */
	public abstract WebDAVPath getPath();
	
	/**
	 * @return A string describing the path of the resource that can be presented to the client.
	 */
	public String getHref() {
		return getPath().toClientString();
	}
}
