package org.openuss.webdav;


/**
 * A simple, string-based WebDAVAnswer implementation.
 */
public class SimpleWebDAVAnswer implements WebDAVAnswer {
	/**
	 * The HTTP status code of this message.
	 * @see WebDAVStatusCodes
	 */
	protected int statusCode;
	/**
	 * A human-readable message.
	 */
	protected String message;
	
	/**
	 * @param statusCode The HTTP status code of this message.
	 */
	public SimpleWebDAVAnswer(int statusCode) {
		this(statusCode, null);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param statusCode The HTTP status code of this message.
	 * @param message A human-readable message.
	 */
	public SimpleWebDAVAnswer(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
		
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVAnswer#getContentType()
	 */
	public String getContentType() {
		return WebDAVConstants.MIMETYPE_TEXT;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVAnswer#getStatusCode()
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVAnswer#getMessage()
	 */
	public String getMessage() {
		return message;
	}
}
