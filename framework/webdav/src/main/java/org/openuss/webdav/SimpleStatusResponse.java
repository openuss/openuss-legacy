package org.openuss.webdav;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SimpleStatusResponse implements MultiStatusStatusResponse{
	
	/**
	 * The (absolute or relative) path to the resource that is represented in this response. 
	 */
	protected String href;
	
	/**
	 * The status code
	 */
	protected int statusCode;
	
	/**
	 * Constructor.
	 * @param href The href of the resource this response is associated with.
	 * @param responseDescription The description of the resource. 
	 */
	public SimpleStatusResponse(String href, int statusCode) {
		this.href = href;
		this.statusCode = statusCode;
	}
	
	/**
	 * Constructor with a WebDAVPath objecet.
	 * 
	 * @param path The path to store.
	 * @param statusCode The HTTP status code of this response 
	 */
	public SimpleStatusResponse(WebDAVPath path, int statusCode) {
		this(path.toClientString(), statusCode);
	}
	
	/**
	 * Constructor with a WebDAVResource objecet.
	 * 
	 * @param resource The described resource
	 * @param statusCode The HTTP status code of this response 
	 */
	public SimpleStatusResponse(WebDAVResource res, int statusCode) {
		this(res.getPath(), statusCode);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.MultiStatusResponse#addToXML(org.w3c.dom.Element)
	 */
	public void addToXML(Element el) {
		Document doc = el.getOwnerDocument();
		
		// create response element
		Element responseEl = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_RESPONSE);
		el.appendChild(responseEl);
		
		// append href
		Element hrefElement = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_HREF);
		hrefElement.setTextContent(getHref());
		responseEl.appendChild(hrefElement);
		
		// Status code
		Element statusEl = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_STATUS);
		statusEl.setTextContent(WebDAVStatusCodes.getStatusLine(statusCode));
		responseEl.appendChild(statusEl);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.MultiStatusResponse#getHref()
	 */
	public String getHref() {
		return href;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.MultiStatusStatusResponse#getStatusCode()
	 */
	public int getStatusCode() {
		return statusCode;
	}

}
