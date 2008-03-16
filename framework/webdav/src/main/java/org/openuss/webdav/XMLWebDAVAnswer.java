package org.openuss.webdav;

import org.w3c.dom.Document;

public class XMLWebDAVAnswer implements WebDAVAnswer {
	/**
	 * The HTTP status code
	 */
	protected int statusCode;
	/**
	 * The represented document
	 */
	protected Document doc;
	
	public XMLWebDAVAnswer(int statusCode, Document doc) {
		this.statusCode = statusCode;
		this.doc = doc;
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

}
