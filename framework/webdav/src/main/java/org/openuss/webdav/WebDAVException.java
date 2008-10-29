package org.openuss.webdav;

import java.util.Map;

/**
 * A WebDAV related exception that is ready to be outputted to the client.
 * Each exception contains therefore an HTTP/WebDAV status code (like 404) and (optionally) additional information
 * about the reason of the exception (the original exception).
 */
public class WebDAVException extends Exception implements WebDAVAnswer {
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 5132720312122649706L;

	protected int statusCode;

	/**
	 * Constructor.
	 * @param statusCode The status code of the exception.
	 */
	public WebDAVException(int statusCode) {
		this(statusCode, getStatusPhrase(statusCode));
	}

	/**
	 * Constructor with a text message specification. 
	 * 
	 * @param statusCode The status code of the exception.
	 * @param causeMsg A description of the exception
	 */
	public WebDAVException(int statusCode, String msg) {
		this(statusCode, msg, null);
	}
	
	/**
	 * Constructor with a text message specification. 
	 * 
	 * @param statusCode The status code of the exception.
	 * @param cause The reason for the exception.
	 */
	public WebDAVException(int statusCode, Throwable cause) {
		this(statusCode, cause.getMessage(), cause);
	}

	/**
	 * Constructor with a text message and .
	 * @param statusCode The status code of the exception.
	 * @param cause The cause of the exception.
	 */
	public WebDAVException(int statusCode, String msg, Throwable cause) {
		super(msg, cause);
		
		this.statusCode = statusCode;
	}
	
	/**
	 * Getter for the status code.
	 * @return The status code of the exception.
	 */
	public int getStatusCode() {
		return statusCode;
	}
	
	/**
	 * Shortcut to acquire the status message.
	 * 
	 * @return WebDAVStatus.getReasonPhrase(getStatusCode())
	 */
	public String getStatusPhrase() {
		return WebDAVStatusCodes.getReasonPhrase(getStatusCode());
	}
	
	/**
	 * Reset the status code of this exception
	 * 
	 * @param statusCode The new code to set.
	 * @return 
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	/**
	 * Getter for the status phrase.
	 * 
	 * @param statusCode The HTTP status code, i.e. 404.
	 * @return The status phrase of the exception, i.e. "File Not Found".
	 */
	protected static String getStatusPhrase(int statusCode) {
		return WebDAVStatusCodes.getReasonPhrase(statusCode);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVAnswer#getContentType()
	 */
	public String getContentType() {
		return WebDAVConstants.MIMETYPE_TEXT;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVAnswer#getXHeaders()
	 */
	public Map<String, String> getXHeaders() {
		return null;
	}
}
