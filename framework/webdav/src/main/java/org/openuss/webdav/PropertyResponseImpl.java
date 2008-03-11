package org.openuss.webdav;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An element of a MultiStatus object detailling the answer of a PROPFIND or PROPPATCH.
 */
public class PropertyResponseImpl implements PropertyResponse {
	/**
	 * The (absolute or relative) path to the resource that is respresented in this response. 
	 */
	protected String href;
	/**
	 * Debugging / informational data.
	 */
	protected String description;
	/**
	 * Mapping of error codes to the collection of corresponding messages and their values
	 */
	protected final Map<Integer, Collection<PropertyResponseNode>> propstats = new HashMap<Integer, Collection<PropertyResponseNode>>();
	
	/**
	 * @param href The href of the resource this response is associated with.
	 */
	public PropertyResponseImpl(String href) {
		this(href, null);
	}
	
	/**
	 * Constructor.
	 * @param href The href of the resource this response is associated with.
	 * @param responseDescription The description of the resource. 
	 */
	public PropertyResponseImpl(String href, String responseDescription) {
		this.href = href;
		this.description = responseDescription;
	}
	
	
	/**
	 * Creates a property with the given name in the DAV-Namespace, the given text
	 * as the body and associates it with the given status code.
	 * @param statusCode The status code to associate the property with.
	 * @param name The name of the property.
	 * @param value The text as content of the property.
	 */
	public void addSimpleProperty(int statusCode, String name, String value) {
		addSimpleProperty(statusCode, null, name, value);
	}
	
	/**
	 * Creates a property with the given namespace:name, the given text as the body
	 * and associates it with the given status code.
	 * @param statusCode The status code to associate the property with.
	 * @param namespaceURI The namespace of the property.
	 * @param name The name of the property.
	 * @param value The text as content of the property.
	 */
	public void addSimpleProperty(int statusCode, String namespaceURI, String name, String value) {
		// check parameter
		SimplePropertyResponseNode prn = new SimplePropertyResponseNode(namespaceURI, name, value);
		
		// add element to hash map
		addProperty(statusCode, prn);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.MultiStatusResponse#getHref()
	 */
	public String getHref() {
		return href;
	}

	/**
	 * Adds the WebDAV XML representation of this property response to the specified parent node.
	 * 
	 * @param element The parent node
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
		
		// iterate through propstat elements and append each
		for (Integer statusInteger : propstats.keySet()) {
			Collection<PropertyResponseNode> prns = propstats.get(statusInteger);
			Element propstatEl = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_PROPSTAT);
			responseEl.appendChild(propstatEl);
		
			// Status code
			Element statusEl = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_STATUS);
			statusEl.setTextContent(WebDAVStatusCodes.getStatusLine(statusInteger.intValue()));
			propstatEl.appendChild(statusEl);
			
			// Properties
			Element propEl = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_PROP);
			propstatEl.appendChild(propEl);
			
			for (PropertyResponseNode prn : prns) {
				prn.addToXml(propEl);
			}
		}
		
		// append description, if set
		if (description != null) {
			Element descEl = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_RESPONSEDESCRIPTION);
			descEl.setTextContent(description);
			responseEl.appendChild(descEl);
		}
	}
	
	/**
	 * Creates a new PropertyResponse out of a WebDAVResourceException.
	 * 
	 * @param wre The exception, containing information about the source.
	 * @return A new PropertyResponse object.
	 */
	public static PropertyResponseImpl createFromHrefException(WebDAVHrefException wre) {
		return new PropertyResponseImpl(wre.getHref(), wre.getMessage());
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.PropertyResponse#addProperty(int, org.openuss.webdav.PropertyResponseNode)
	 */
	public void addProperty(int statusCode, PropertyResponseNode prn) {
		Collection<PropertyResponseNode> c;
		
		c = propstats.get(statusCode);
		if (c == null) {
			c = new ArrayList<PropertyResponseNode>(1);
			propstats.put(statusCode, c);
		}
		
		c.add(prn);
	}
}
