/**
 * 
 */
package org.openuss.docmanagement.webdav;

import org.dom4j.Element;
import org.dom4j.Namespace;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.5
 */
public interface PropertyResponse extends MultiStatusResponse {
	
	public void addProperty(int statusCode, String name, String value);
	
	public void addProperty(int statusCode, Namespace namespace, String name, String value);
	
	public void addProperty(int statusCode, Namespace namespace, String name, Element innerElement);
	
	public void addProperty(int statusCode, Element element);

}
