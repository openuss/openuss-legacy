package org.openuss.docmanagement.webdav;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.jackrabbit.webdav.DavConstants;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

/**
 * @author David Ullrich
 * @version 0.6
 */
public class MultiStatusResponse {
	// TODO propstats als Typ auslagern
	
	private String href;
	private String description;
	private int statusCode;
	private boolean statusOnly;
	
	private final Namespace defaultNamespace = DocumentHelper.createNamespace("D", "DAV:"); 
	
	private final HashMap<Integer, Element> propstats;
	
	public MultiStatusResponse(String href, String responseDescription) {
		this(href, responseDescription, false);
	}
	
	public MultiStatusResponse(String href, int statusCode, String responseDescription) {
		this(href, responseDescription, true);
		this.statusCode = statusCode;
	}
	
	private MultiStatusResponse(String href, String responseDescription, boolean statusOnly) {
		this.href = href;
		this.description = responseDescription;
		this.statusOnly = statusOnly;
		
		propstats = new HashMap<Integer, Element>();
	}
	
	public Namespace getDefaultNamespace() {
		return defaultNamespace;
	}
	
	public String getHref() {
		return href;
	}
	
	public void addProperty(int statusCode, String name, String value) {
		addProperty(statusCode, null, name, value);
	}
	
	public void addProperty(int statusCode, Namespace namespace, String name, String value) {
		if (namespace == null) {
			namespace = getDefaultNamespace();
		}
		
		QName propertyName = DocumentHelper.createQName(name, namespace);
		Element propertyElement = DocumentHelper.createElement(propertyName);
		propertyElement.addText(value);
		
		addProperty(statusCode, propertyElement);
	}
	
	public void addProperty(int statusCode, Namespace namespace, String name, Element innerElement) {
		if (namespace == null) {
			namespace = getDefaultNamespace();
		}
		
		QName propertyName = DocumentHelper.createQName(name, namespace);
		Element propertyElement = DocumentHelper.createElement(propertyName);
		propertyElement.add(innerElement);
		
		addProperty(statusCode, propertyElement);
	}
	
	public void addProperty(int statusCode, Element element) {
		Element propElement = getPropElement(statusCode);
		
		propElement.add(element);
	}
	
	private Element getPropElement(int statusCode) {
		Integer statusInteger = new Integer(statusCode);
		
		Element propstatElement = propstats.get(statusInteger);
		
		if (propstatElement == null) {
			QName propstatName = DocumentHelper.createQName(DavConstants.XML_PROPSTAT, getDefaultNamespace());
			propstatElement = DocumentHelper.createElement(propstatName);
			
			QName propName = DocumentHelper.createQName(DavConstants.XML_PROP, getDefaultNamespace());
			propstatElement.addElement(propName);
			
			propstats.put(statusInteger, propstatElement);
		}
		
		return propstatElement.element(DavConstants.XML_PROP);
	}
	
	public void toXml(Element element, Namespace namespace) {
		QName rootName = DocumentHelper.createQName(DavConstants.XML_RESPONSE, namespace);
		Element rootElement = element.addElement(rootName);
		
		// append href
		QName hrefName = DocumentHelper.createQName(DavConstants.XML_HREF, namespace);
		Element hrefElement = rootElement.addElement(hrefName);
		hrefElement.addText(getHref());
		
		if (!statusOnly) {
			Iterator<Integer> iterator = propstats.keySet().iterator();
			Integer statusInteger;
			
			while (iterator.hasNext()) {
				statusInteger = iterator.next();
				Element propstatElement = propstats.get(statusInteger);
				// add status line
				QName statusName = DocumentHelper.createQName(DavConstants.XML_STATUS, namespace);
				Element statusElement = propstatElement.addElement(statusName);
				statusElement.addText(HttpStatus.getStatusLine(statusInteger.intValue()));
				// add to response element
				rootElement.add(propstatElement);
			}
		} else {
			// TYPE_STATUS
			QName statusName = DocumentHelper.createQName(DavConstants.XML_STATUS, namespace);
			Element statusElement = rootElement.addElement(statusName);
			statusElement.addText(HttpStatus.getStatusLine(statusCode));
		}
		
		// append description, if set
		if (description != null) {
			QName descriptionName = DocumentHelper.createQName(DavConstants.XML_RESPONSEDESCRIPTION, namespace);
			Element descriptionElement = rootElement.addElement(descriptionName);
			descriptionElement.addText(description);
		}
	}
}
