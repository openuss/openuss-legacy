package org.openuss.docmanagement.webdav;

import org.dom4j.Element;
import org.dom4j.Namespace;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 1.0
 */
public interface PropertyResponse extends MultiStatusResponse {
	/**
	 * Creates a property with the given name in the DAV-Namespace, the given text
	 * as the body and associates it with the given status code.
	 * @param statusCode The status code to associate the property with.
	 * @param name The name of the property.
	 * @param value The text as content of the property.
	 */
	public void addProperty(int statusCode, String name, String value);
	
	/**
	 * Creates a property with the given namespace:name, the given text as the body
	 * and associates it with the given status code.
	 * @param statusCode The status code to associate the property with.
	 * @param namespace The namespace of the property.
	 * @param name The name of the property.
	 * @param value The text as content of the property.
	 */
	public void addProperty(int statusCode, Namespace namespace, String name, String value);
	
	/**
	 * Creates a property with the given namespace:name, the given inner element as the body
	 * and associates it with the given status code.
	 * @param statusCode The status code to associate the property with.
	 * @param namespace The namespace of the property.
	 * @param name The name of the property.
	 * @param innerElement The inner element of the property.
	 */
	public void addProperty(int statusCode, Namespace namespace, String name, Element innerElement);
	
	/**
	 * Adds an element associated with the given status code.
	 * @param statusCode The status code to associate the element with.
	 * @param element The element to add.
	 */
	public void addProperty(int statusCode, Element element);
}
