package org.openuss.webdav;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

/**
 * A WebDAV answer containing a complete XML document.
 */
public class XMLWebDAVAnswer implements WebDAVAnswer {
	/**
	 * The HTTP status code
	 */
	protected int statusCode;
	/**
	 * The represented document
	 */
	protected Document doc;
	/**
	 * The map of additional header values
	 */
	protected Map<String,String> headers;
	
	public XMLWebDAVAnswer(int statusCode, Document doc) {
		this(statusCode, doc, null);
	}

	public XMLWebDAVAnswer(int statusCode, Document doc, Map<String,String> xHeaders) {
		this.statusCode = statusCode;
		this.doc = doc;
		this.headers = xHeaders;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVAnswer#getContentType()
	 */
	public String getContentType() {
		return WebDAVConstants.MIMETYPE_XML;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVAnswer#getMessage()
	 */
	public String getMessage() {
		return WebDAVUtils.documentToString(doc);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVAnswer#getStatusCode()
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVAnswer#getXHeaders()
	 */
	public Map<String, String> getXHeaders() {
		return headers;
	}
	
	/**
	 * @param key The key of the header property
	 * @param value The header value
	 */
	public void addHeader(String key, String value) {
		if (headers == null) {
			headers = new HashMap<String, String>();
		}
		
		headers.put(key, value);
	}
}
