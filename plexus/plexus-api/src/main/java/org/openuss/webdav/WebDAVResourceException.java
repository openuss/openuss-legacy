package org.openuss.webdav;

/**
 * An error on a specific resource.
 */
public class WebDAVResourceException extends WebDAVException {
	/**
	 * Version ID for serialization.
	 */
	private static final long serialVersionUID = -8692450036462387399L;
	/**
	 * The resource that was worked on when the exception was raised.
	 */
	protected WebDAVResource resource;
	
	/**
	 * @param statusCode The WebDAV status code as defined in WebDAVStatusCodes. 
	 * @param res The resource that was worked on when the exception was raised.
	 */
	public WebDAVResourceException(int statusCode, WebDAVResource res) {
		this(statusCode, res, null);
	}
	
	/**
	 * @param statusCode The WebDAV status code as defined in WebDAVStatusCodes. 
	 * @param res The resource that was worked on when the exception was raised.
	 * @param message A human-readable message.
	 */
	public WebDAVResourceException(int statusCode, WebDAVResource res, String message) {
		this(statusCode, res, message, null);
	}
	
	/**
	 * @param statusCode The WebDAV status code as defined in WebDAVStatusCodes. 
	 * @param res The resource that was worked on when the exception was raised.
	 * @param message A human-readable message.
	 * @param cause The causing exception
	 */
	public WebDAVResourceException(int statusCode, WebDAVResource res, String message, Throwable cause) {
		super(statusCode, message, cause);
		
		resource = res;
	}
	
	/**
	 * @return The ressource the exception occured on.
	 */
	public WebDAVResource getResource() {
		return resource;
	}
	
	/**
	 * @return A string describing the path of the resource that can be presented to the client.
	 */
	public String getHref() {
		return getResource().getPath().toClientString();
	}
}
