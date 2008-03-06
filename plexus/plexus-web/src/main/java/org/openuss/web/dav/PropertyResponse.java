package org.openuss.web.dav;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.openuss.webdav.MultiStatusResponse;
import org.openuss.webdav.WebDAVConstants;
import org.openuss.webdav.WebDAVHrefException;
import org.openuss.webdav.WebDAVStatusCodes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * An element of a MultiStatus object detailling the answer of a PROPFIND or PROPPATCH.
 */
public class PropertyResponse implements MultiStatusResponse {
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
	public PropertyResponse(String href) {
		this(href, null);
	}
	
	/**
	 * Constructor.
	 * @param href The href of the resource this response is associated with.
	 * @param responseDescription The description of the resource. 
	 */
	public PropertyResponse(String href, String responseDescription) {
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
	public void addProperty(int statusCode, String name, String value) {
		addProperty(statusCode, null, name, value);
	}
	
	/**
	 * Creates a property with the given namespace:name, the given text as the body
	 * and associates it with the given status code.
	 * @param statusCode The status code to associate the property with.
	 * @param namespaceURI The namespace of the property.
	 * @param name The name of the property.
	 * @param value The text as content of the property.
	 */
	public void addProperty(int statusCode, String namespaceURI, String name, String value) {
		// check parameter
		if ((name == null) || (name.length() == 0)) {
			throw new IllegalArgumentException("The parameter name must not be null or empty.");
		}
		
		PropertyResponseNode prn = new PropertyResponseNode(namespaceURI, name, value);
		
		// add element to hash map
		addProperty(statusCode, prn);
	}
	
	/**
	 * Internal helper function to add a response with a given status code.
	 * 
	 * @param statusCode The status code.
	 * @param prn The response.
	 */
	protected void addProperty(int statusCode, PropertyResponseNode prn) {
		Collection<PropertyResponseNode> c;
		
		c = propstats.get(statusCode);
		if (c == null) {
			c = new ArrayList<PropertyResponseNode>(1);
			propstats.put(statusCode, c);
		}
		
		c.add(prn);
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
		Element responseEl = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV_URI, WebDAVConstants.XML_RESPONSE);
		el.appendChild(responseEl);
		
		// append href
		Element hrefElement = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV_URI, WebDAVConstants.XML_HREF);
		hrefElement.setTextContent(getHref());
		responseEl.appendChild(hrefElement);
		
		// iterate through propstat elements and append each
		for (Integer statusInteger : propstats.keySet()) {
			Collection<PropertyResponseNode> prns = propstats.get(statusInteger);
			Element propstatEl = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV_URI, WebDAVConstants.XML_PROPSTAT);
			
			// Status code
			Element statusEl = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV_URI, WebDAVConstants.XML_STATUS);
			statusEl.setTextContent(WebDAVStatusCodes.getStatusLine(statusInteger.intValue()));
			propstatEl.appendChild(statusEl);
			
			// Properties
			for (PropertyResponseNode prn : prns) {
				propstatEl.appendChild(prn.toXMLNode(doc));
			}
			
			responseEl.appendChild(propstatEl);
		}
		
		// append description, if set
		if (description != null) {
			Element descEl = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV_URI, WebDAVConstants.XML_RESPONSEDESCRIPTION);
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
	public static PropertyResponse createFromHrefException(WebDAVHrefException wre) {
		return new PropertyResponse(wre.getHref(), wre.getMessage());
	}
	
	/**
	 * A node in a property response.
	 * We can't use normal XML documents here since we don't have a document.
	 */
	protected static class PropertyResponseNode {
		/**
		 * URI of the namespace. null for no/default namespace.
		 */
		protected String namespaceURI;
		/**
		 * The local name of the node.
		 */
		protected String name;
		/**
		 * The value of the property response.
		 */
		protected String value;

		public PropertyResponseNode(String name, String value) {
			this(null, name, value);
		}

		public PropertyResponseNode(String namespaceURI, String name,
				String value) {
			super();
			this.namespaceURI = namespaceURI;
			this.name = name;
			this.value = value;
		}

		public String getNamespaceURI() {
			return namespaceURI == null ? WebDAVConstants.NAMESPACE_WEBDAV_URI : namespaceURI;
		}

		public void setNamespaceURI(String namespaceURI) {
			this.namespaceURI = namespaceURI;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * @return true iff this node should have a value element.
		 */
		public boolean hasValue() {
			return value != null;
		}
		
		/**
		 * Create an XML node out of this object.  
		 * 
		 * @param doc The document that the node shall belong to.
		 * @return The created node.
		 */
		public Node toXMLNode(Document doc) {
			Element res = doc.createElementNS(getNamespaceURI(), getName());
			
			if (hasValue()) {
				Node textNode = doc.createTextNode(getValue());
				
				res.appendChild(textNode);
			}
			
			return res;
		}
	}
}
