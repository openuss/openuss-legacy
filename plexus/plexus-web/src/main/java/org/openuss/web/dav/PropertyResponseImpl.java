package org.openuss.web.dav;

import org.openuss.webdav.PropertyResponseNode;
import org.openuss.webdav.WebDAVConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * A node in a property response that consists solely of a name and optionally a value.
 * @see PropertyResponseImpl
 */
class SimplePropertyResponseNode implements PropertyResponseNode {
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

	public SimplePropertyResponseNode(String name, String value) {
		this(null, name, value);
	}

	public SimplePropertyResponseNode(String namespaceURI, String name,
			String value) {
		super();
		this.namespaceURI = namespaceURI;
		this.name = name;
		this.value = value;
	}

	public String getNamespaceURI() {
		return namespaceURI == null ? WebDAVConstants.NAMESPACE_WEBDAV_URI : namespaceURI;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
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
	public void addToXml(Element el) {
		Document doc = el.getOwnerDocument();
		
		Element res = doc.createElementNS(getNamespaceURI(), getName());
		
		if (hasValue()) {
			Node textNode = doc.createTextNode(getValue());
			
			res.appendChild(textNode);
		}
		
		el.appendChild(res);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.PropertyResponseNode#getPropName()
	 */
	public String getPropName() {
		return getName();
	}
}
