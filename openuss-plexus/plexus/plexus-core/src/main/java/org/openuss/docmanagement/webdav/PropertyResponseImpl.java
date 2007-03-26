package org.openuss.docmanagement.webdav;

import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public class PropertyResponseImpl implements PropertyResponse {
	private final String href;
	private final String description;
		
	private final HashMap<Integer, Element> propstats = new HashMap<Integer, Element>();;
	
	/**
	 * Constructor.
	 * @param href The href of the resource this response is associated with.
	 * @param responseDescription The description of the resource. 
	 */
	PropertyResponseImpl(String href, String responseDescription) {
		this.href = href;
		this.description = responseDescription;
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
		// check parameter
		if ((name == null) || (name.length() == 0)) {
			throw new IllegalArgumentException("The parameter name must not be null or empty.");
		}

		// get reference to default namespace if parameter is null
		if (namespace == null) {
			namespace = DavConstants.XML_DAV_NAMESPACE;
		}
		
		// create qualified name and element for property
		QName propertyName = DocumentHelper.createQName(name, namespace);
		Element propertyElement = DocumentHelper.createElement(propertyName);

		// set value as text, if not null
		if (value != null) {
			propertyElement.addText(value);
		}
		
		// add element to hash map
		addProperty(statusCode, propertyElement);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.PropertyResponse#addProperty(int, org.dom4j.Namespace, java.lang.String, org.dom4j.Element)
	 */
	public void addProperty(int statusCode, Namespace namespace, String name, Element innerElement) {
		// check parameter
		if ((name == null) || (name.length() == 0)) {
			throw new IllegalArgumentException("The parameter name must not be null or empty.");
		}
		
		// get reference to default namespace if parameter is null
		if (namespace == null) {
			namespace = DavConstants.XML_DAV_NAMESPACE;
		}
		
		// create qualified name and element for property
		QName propertyName = DocumentHelper.createQName(name, namespace);
		Element propertyElement = DocumentHelper.createElement(propertyName);
		
		// add inner element
		propertyElement.add(innerElement);
		
		// add element to hash map
		addProperty(statusCode, propertyElement);
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.PropertyResponse#addProperty(int, org.dom4j.Element)
	 */
	public void addProperty(int statusCode, Element element) {
		// get reference to element from hash map
		Element propElement = getPropElement(statusCode);
		
		// add given element to prop element
		propElement.add(element);
	}
	
	/**
	 * Returns the prop element from the propstat element associated with the given status code.
	 * @param statusCode The status code of the prop
	 * @return
	 */
	private Element getPropElement(int statusCode) {
		// hash map requires non-primitive types as key
		Integer statusInteger = new Integer(statusCode);
		
		// request propstat element from hash map
		Element propstatElement = propstats.get(statusInteger);
		
		// create propstat element, if not in hash map
		if (propstatElement == null) {
			// create qualified name and element for propstat element
			QName propstatName = DocumentHelper.createQName(DavConstants.XML_PROPSTAT, DavConstants.XML_DAV_NAMESPACE);
			propstatElement = DocumentHelper.createElement(propstatName);
			
			// create qualified name and element for prop element
			QName propName = DocumentHelper.createQName(DavConstants.XML_PROP, DavConstants.XML_DAV_NAMESPACE);
			propstatElement.addElement(propName);
			
			// add to hash map
			propstats.put(statusInteger, propstatElement);
		}
		
		// return reference to prop element from propstat element
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
		// check parameter
		if (element == null) {
			throw new IllegalArgumentException("The parameter element must not be null.");
		}
		
		// create response element
		QName rootName = DocumentHelper.createQName(DavConstants.XML_RESPONSE, DavConstants.XML_DAV_NAMESPACE);
		Element rootElement = element.addElement(rootName);
		
		// append href
		QName hrefName = DocumentHelper.createQName(DavConstants.XML_HREF, DavConstants.XML_DAV_NAMESPACE);
		Element hrefElement = rootElement.addElement(hrefName);
		hrefElement.addText(getHref());

		// iterate through propstat elements and append
		Iterator<Integer> iterator = propstats.keySet().iterator();
		Integer statusInteger;
		while (iterator.hasNext()) {
			statusInteger = iterator.next();
			Element propstatElement = propstats.get(statusInteger);

			// create and status line and append
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
