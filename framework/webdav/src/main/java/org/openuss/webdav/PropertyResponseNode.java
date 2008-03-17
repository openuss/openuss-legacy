package org.openuss.webdav;

import org.w3c.dom.Element;

/**
 * The part of a response to a PROPFIND that details one requested property.
 */
public interface PropertyResponseNode {
	/**
	 * Add the XML representation of this node to the specified element.
	 * 
	 * @param el The element to append the XML representationto.
	 */
	public void addToXml(Element el);
	
	/**
	 * @return The namespace URI of the property
	 */
	public String getNamespaceURI();
	
	/**
	 * @return The name of the property
	 */
	public String getPropName();
}
