package org.openuss.docmanagement.webdav;

import org.dom4j.Element;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.5
 */
public interface MultiStatusResponse {
	public String getHref();
	
	public void toXml(Element element);
}
