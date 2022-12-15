package org.openuss.webdav;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Implements {@link MultiStatusAnswer}
 */
public class MultiStatusAnswerImpl implements MultiStatusAnswer  {
	/**
	 * The list of individual responses in this object.
	 * Each element corresponds to a &lt;response&gt; element in the final outputted document. 
	 */
	protected List<MultiStatusResponse> responses;
	/**
	 * Additional inform(ation)al data. null for no information. 
	 */
	protected String description;
	protected Map<String,String> headerValues;
	
	public MultiStatusAnswerImpl() {
		this(null);
	}
	
	public MultiStatusAnswerImpl(Map<String,String> headerValues) {
		responses = new ArrayList<MultiStatusResponse>();
		description = null;
		this.headerValues = headerValues;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.MultiStatusAnswer#addResponse(org.openuss.webdav.MultiStatusResponse)
	 */
	public void addResponse(MultiStatusResponse response) {
		responses.add(response);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.MultiStatusAnswer#getResponses()
	 */
	public List<MultiStatusResponse> getResponses() {
		return responses;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.MultiStatusAnswer#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.MultiStatusAnswer#setDescription(java.lang.String)
	 */
	public void setDescription(String description) {
		this.description = description;
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
		Document doc = WebDAVUtils.newDocument();
		
		Element rootNode = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_MULTISTATUS);
		doc.appendChild(rootNode);
		
		for (MultiStatusResponse response : getResponses()) {
			response.addToXML(rootNode);
		}
		
		// append description, if set
		if (description != null) {
			Node descNode = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_RESPONSEDESCRIPTION);
			Node descNodeText = doc.createTextNode(description);
			descNode.appendChild(descNodeText);
			rootNode.appendChild(descNode);
		}
		
		return WebDAVUtils.documentToString(doc);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVAnswer#getStatusCode()
	 */
	public int getStatusCode() {
		return WebDAVStatusCodes.SC_MULTI_STATUS;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.MultiStatusAnswer#getResponseCount()
	 */
	public int getResponseCount() {
		return responses.size();
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVAnswer#getHeaderValues()
	 */
	public Map<String, String> getXHeaders() {
		return headerValues;
	}
	
	/**
	 * Adds a new HTTP header value
	 * 
	 * @param key The name of the header value
	 * @param value The header value, unsanitized
	 */
	public void setHeaderValue(String key, String value) {
		if (headerValues == null) {
			headerValues = new HashMap<String,String>();
		}
		
		headerValues.put(key, value);
	}
}