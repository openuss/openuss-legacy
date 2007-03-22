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
 * @version 0.6
 */
public class MultiStatusImpl implements MultiStatus {
	private final List<MultiStatusResponse> responses;
	private String description;
	
	public MultiStatusImpl() {
		responses = new ArrayList<MultiStatusResponse>();
	}
	
	public List<MultiStatusResponse> getResponses() {
		return responses;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void addResponse(MultiStatusResponse response) {
		responses.add(response);
	}

	public void toXml(Document document) {
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
