/**
 * 
 */
package org.openuss.docmanagement.webdav;

import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.5
 */
public class PropertyResponseImpl implements PropertyResponse {
	private final String href;
	private final String description;
		
	private final HashMap<Integer, Element> propstats;
	
	PropertyResponseImpl(String href, String responseDescription) {
		this.href = href;
		this.description = responseDescription;
		
		propstats = new HashMap<Integer, Element>();
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.PropertyResponse#addProperty(int, java.lang.String, java.lang.String)
	 */
	public void addProperty(int statusCode, String name, String value) {
		addProperty(statusCode, null, name, value);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.PropertyResponse#addProperty(int, org.dom4j.Namespace, java.lang.String, java.lang.String)
	 */
	public void addProperty(int statusCode, Namespace namespace, String name, String value) {
		if (namespace == null) {
			namespace = DavConstants.XML_DAV_NAMESPACE;
		}
		
		QName propertyName = DocumentHelper.createQName(name, namespace);
		Element propertyElement = DocumentHelper.createElement(propertyName);
		
		if (value != null) {
			propertyElement.addText(value);
		}
		
		addProperty(statusCode, propertyElement);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.PropertyResponse#addProperty(int, org.dom4j.Namespace, java.lang.String, org.dom4j.Element)
	 */
	public void addProperty(int statusCode, Namespace namespace, String name, Element innerElement) {
		if (namespace == null) {
			namespace = DavConstants.XML_DAV_NAMESPACE;
		}
		
		QName propertyName = DocumentHelper.createQName(name, namespace);
		Element propertyElement = DocumentHelper.createElement(propertyName);
		propertyElement.add(innerElement);
		
		addProperty(statusCode, propertyElement);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.PropertyResponse#addProperty(int, org.dom4j.Element)
	 */
	public void addProperty(int statusCode, Element element) {
		Element propElement = getPropElement(statusCode);
		
		propElement.add(element);
	}
	
	private Element getPropElement(int statusCode) {
		Integer statusInteger = new Integer(statusCode);
		
		Element propstatElement = propstats.get(statusInteger);
		
		if (propstatElement == null) {
			QName propstatName = DocumentHelper.createQName(DavConstants.XML_PROPSTAT, DavConstants.XML_DAV_NAMESPACE);
			propstatElement = DocumentHelper.createElement(propstatName);
			
			QName propName = DocumentHelper.createQName(DavConstants.XML_PROP, DavConstants.XML_DAV_NAMESPACE);
			propstatElement.addElement(propName);
			
			propstats.put(statusInteger, propstatElement);
		}
		
		return propstatElement.element(DavConstants.XML_PROP);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.MultiStatusResponse#getHref()
	 */
	public String getHref() {
		return href;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.MultiStatusResponse#toXml(org.dom4j.Element)
	 */
	public void toXml(Element element) {
		QName rootName = DocumentHelper.createQName(DavConstants.XML_RESPONSE, DavConstants.XML_DAV_NAMESPACE);
		Element rootElement = element.addElement(rootName);
		
		// append href
		QName hrefName = DocumentHelper.createQName(DavConstants.XML_HREF, DavConstants.XML_DAV_NAMESPACE);
		Element hrefElement = rootElement.addElement(hrefName);
		hrefElement.addText(getHref());

		Iterator<Integer> iterator = propstats.keySet().iterator();
		Integer statusInteger;

		while (iterator.hasNext()) {
			statusInteger = iterator.next();
			Element propstatElement = propstats.get(statusInteger);
			// add status line
			QName statusName = DocumentHelper.createQName(DavConstants.XML_STATUS, DavConstants.XML_DAV_NAMESPACE);
			Element statusElement = propstatElement.addElement(statusName);
			statusElement.addText(HttpStatus.getStatusLine(statusInteger.intValue()));
			// add to response element
			rootElement.add(propstatElement);
		}
		
		// append description, if set
		if (description != null) {
			QName descriptionName = DocumentHelper.createQName(DavConstants.XML_RESPONSEDESCRIPTION, DavConstants.XML_DAV_NAMESPACE);
			Element descriptionElement = rootElement.addElement(descriptionName);
			descriptionElement.addText(description);
		}
	}
}
