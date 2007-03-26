package org.openuss.docmanagement.webdav;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public class MultiStatusImpl implements MultiStatus {
	private final List<MultiStatusResponse> responses = new ArrayList<MultiStatusResponse>();
	private String description;
		
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.MultiStatus#addResponse(org.openuss.docmanagement.webdav.MultiStatusResponse)
	 */
	public void addResponse(MultiStatusResponse response) {
		responses.add(response);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.MultiStatus#createPropertyResponse(java.lang.String, java.lang.String)
	 */
	public PropertyResponse createPropertyResponse(String href, String description) {
		// create new instance of PropertyResponse and add to responses
		PropertyResponse response = new PropertyResponseImpl(href, description);
		responses.add(response);
		return response;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.MultiStatus#createStatusResponse(java.lang.String, int, java.lang.String)
	 */
	public StatusResponse createStatusResponse(String href, int statusCode, String description) {
		// create new instance of StatusResponse and add to responses
		StatusResponse response = new StatusResponseImpl(href, statusCode, description);
		responses.add(response);
		return response;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.MultiStatus#getDescription()
	 */
	public String getDescription() {
		return description;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.MultiStatus#getResponses()
	 */
	public List<MultiStatusResponse> getResponses() {
		return responses;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.MultiStatus#setDescription(java.lang.String)
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.MultiStatus#toXml(org.dom4j.Document)
	 */
	public void toXml(Document document) {
		// check parameter
		if (document == null) {
			throw new IllegalArgumentException("The parameter document must not be null.");
		}
		
		// create root element
		QName rootName = DocumentHelper.createQName(DavConstants.XML_MULTISTATUS, DavConstants.XML_DAV_NAMESPACE);
		Element rootElement = document.addElement(rootName);

		// iterate over responses and append to rootElement
		Iterator<MultiStatusResponse> iterator = getResponses().iterator();
		MultiStatusResponse response;
		while (iterator.hasNext()) {
			response = iterator.next();
			response.toXml(rootElement);
		}
		
		// append description, if set
		if (description != null) {
			QName descriptionName = DocumentHelper.createQName(DavConstants.XML_RESPONSEDESCRIPTION, DavConstants.XML_DAV_NAMESPACE);
			Element descriptionElement = rootElement.addElement(descriptionName);
			descriptionElement.addText(description);
		}
	}
}
