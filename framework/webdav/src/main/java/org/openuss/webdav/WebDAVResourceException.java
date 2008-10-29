package org.openuss.webdav;

/**
 * An error on a specific resource.
 */
public class WebDAVResourceException extends WebDAVHrefException {
	/**
	 * Version ID for serialization.
	 */
	private static final long serialVersionUID = -8692450036462387399L;
	/**
	 * The resource that was worked on when the exception was raised.
	 */
	protected transient WebDAVResource resource;
	
	/**
	 * @param statusCode The WebDAV status code as defined in WebDAVStatusCodes. 
	 * @param res The resource that was worked on when the exception was raised.
	 */
	public WebDAVResourceException(int statusCode, WebDAVResource res) {
		this(statusCode, res, (String) null);
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
	 * @param statusCode The WebDAV status code as defined in WebDAVStatusCodes. 
	 * @param res The resource that was worked on when the exception was raised.
	 * @param cause The causing exception
	 */
	public WebDAVResourceException(int statusCode, WebDAVResource res, Throwable cause) {
		super(statusCode, cause.getMessage(), cause);
		
		resource = res;
	}
	
	/**
	 * @return The ressource the exception occured on.
	 */
	public WebDAVResource getResource() {
		return resource;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVHrefException#getPath()
	 */
	@Override
	public WebDAVPath getPath() {
		return getResource().getPath();
	}
}
