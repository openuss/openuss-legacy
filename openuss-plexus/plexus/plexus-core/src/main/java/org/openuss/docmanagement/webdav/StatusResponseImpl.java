package org.openuss.docmanagement.webdav;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public class StatusResponseImpl implements StatusResponse {
	private final String href;
	private final String description;
	private int statusCode;
	
	/**
	 * Constructor.
	 * @param href The href of the resource this response is associated with.
	 * @param statusCode The status code of the response.
	 * @param responseDescription The description of the resource.
	 */
	StatusResponseImpl(String href, int statusCode, String responseDescription) {
		this.href = href;
		this.statusCode = statusCode;
		this.description = responseDescription;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.MultiStatusResponse#getHref()
	 */
	public String getHref() {
		return href;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.MultiStatusResponse#toXml(org.dom4j.Element)
	 */
	public void toXml(Element element) {
		// check parameter
		if (element == null) {
			throw new IllegalArgumentException("The parameter element must not be null.");
		}

		// create response element
		QName rootName = DocumentHelper.createQName(DavConstants.XML_RESPONSE, DavConstants.XML_DAV_NAMESPACE);
		Element rootElement = element.addElement(rootName);
		
		// append href
		QName hrefName = DocumentHelper.createQName(DavConstants.XML_HREF, DavConstants.XML_DAV_NAMESPACE);
		Element hrefElement = rootElement.addElement(hrefName);
		hrefElement.addText(getHref());
		
		// append status
		QName statusName = DocumentHelper.createQName(DavConstants.XML_STATUS, DavConstants.XML_DAV_NAMESPACE);
		Element statusElement = rootElement.addElement(statusName);
		statusElement.addText(HttpStatus.getStatusLine(statusCode));
		
		// append description, if set
		if (description != null) {
			QName descriptionName = DocumentHelper.createQName(DavConstants.XML_RESPONSEDESCRIPTION, DavConstants.XML_DAV_NAMESPACE);
			Element descriptionElement = rootElement.addElement(descriptionName);
			descriptionElement.addText(description);
		}
	}
}
