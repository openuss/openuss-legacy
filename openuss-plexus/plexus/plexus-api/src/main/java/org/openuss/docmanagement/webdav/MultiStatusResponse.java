package org.openuss.docmanagement.webdav;

import org.dom4j.Element;

/**
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
	 * Transforms the multi-status response to XML and adds it to the given {@link Element}.
	 * @param element The element to add the multi-status to.
	 */
	public void toXml(Element element);
}
