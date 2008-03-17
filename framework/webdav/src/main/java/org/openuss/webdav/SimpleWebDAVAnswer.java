package org.openuss.webdav;

import java.util.Map;


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
	 * Additional HTTP headers
	 */
	protected Map<String,String> headers;
	
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
	
	/**
	 * Constructor.
	 * 
	 * @param statusCode The HTTP status code of this message.
	 * @param message A human-readable message.
	 * @param headers Additional HTTP headers to set
	 */
	public SimpleWebDAVAnswer(int statusCode, String message, Map<String,String> headers) {
		this.statusCode = statusCode;
		this.message = message;
		this.headers = headers;
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

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVAnswer#getHeaderValues()
	 */
	public Map<String, String> getXHeaders() {
		return headers;
	}
}
