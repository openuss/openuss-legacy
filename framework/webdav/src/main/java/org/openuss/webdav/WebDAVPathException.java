package org.openuss.webdav;

/**
 * An exception concerning a specific path.
 */
public class WebDAVPathException extends WebDAVHrefException {
	/**
	 * Version id for serialization.
	 */
	private static final long serialVersionUID = -9082712076364331251L;

	protected WebDAVPath path;
	
	public WebDAVPathException(int statusCode, WebDAVPath path, String msg, Throwable cause) {
		super(statusCode, msg, cause);
		
		setPath(path);
	}

	public WebDAVPathException(int statusCode, WebDAVPath path, String msg) {
		this(statusCode, path, msg, null);
	}

	public WebDAVPathException(int statusCode, WebDAVPath path, Throwable cause) {
		this(statusCode, path, cause.getMessage(), cause);
	}

	public WebDAVPathException(int statusCode, WebDAVPath path) {
		this(statusCode, path, "Error when working on " + path.getCompleteString() + ". Error code: " + statusCode + " " + WebDAVStatusCodes.getReasonPhrase(statusCode));
	}
	
	/**
	 * Internal setter for the path that saves the full path.
	 */
	protected void setPath(WebDAVPath path) {
		this.path = path.asResolved();
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVHrefException#getPath()
	 */
	@Override
	public WebDAVPath getPath() {
		return path;
	}
}
