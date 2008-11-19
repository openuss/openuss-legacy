package org.openuss.webdav;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * A property response node that may contain arbitrary XML nodes.
 */
public class XMLPropertyResponseNode implements PropertyResponseNode {
	protected Node node;

	/**
	 * @param node The property node to add.
	 */
	public XMLPropertyResponseNode(Node node) {
		this.node = node;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.PropertyResponseNode#addToXml(org.w3c.dom.Element)
	 */
	public void addToXml(Element el) {
		Document doc = el.getOwnerDocument();
		
		Node n = doc.importNode(node, true);
		el.appendChild(n);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.PropertyResponseNode#getNamespaceURI()
	 */
	public String getNamespaceURI() {
		return node.getNamespaceURI();
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.PropertyResponseNode#getPropName()
	 */
	public String getPropName() {
		return node.getLocalName();
	}
	
	
}
