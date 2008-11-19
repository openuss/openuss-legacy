package org.openuss.webdav;

import org.w3c.dom.Element;

/**
 * Multiple responses to a query all concerning a common resource.
 * 
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 1.0
 */
public interface MultiStatusResponse {
	/**
	 * The href of the resource this response is associated with.
	 * @return The href of the associated resource.
	 */
	public String getHref();
	
	/**
	 * Transforms the multi-status response to XML and adds it to the given {@link Node}.
	 * @param el The XML node to add the multi-status to.
	 */
	public void addToXML(Element el);
}
