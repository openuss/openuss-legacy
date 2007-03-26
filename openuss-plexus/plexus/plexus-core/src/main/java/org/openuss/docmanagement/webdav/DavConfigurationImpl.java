package org.openuss.docmanagement.webdav;

import java.io.InputStream;
import java.net.URL;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.openuss.docmanagement.webdav.DefaultItemFilterImpl;
import org.openuss.docmanagement.webdav.ItemFilter;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public class DavConfigurationImpl implements DavConfiguration {
	private final Logger logger = Logger.getLogger(DavConfigurationImpl.class);
	
	private ItemFilter itemFilter;
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavConfiguration#parse(java.net.URL)
	 */
	public void parse(URL configurationURL) {
		try {
			InputStream inputStream = configurationURL.openStream();
			
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			
			// look for root element <configuration>
			Element rootElement = document.getRootElement();
			if (rootElement.getName().equals("configuration")) {
				// look for element <itemfilter>
				Element itemFilterElement = rootElement.element("itemfilter");
				
				if (itemFilterElement != null) {
					// look for element <class>
					Element classElement = itemFilterElement.element("class");

					if (classElement != null) {
						// look for attribute name
						Attribute nameAttribute = classElement.attribute("name");
						
						// create instance of given class an set as item filter
						if (nameAttribute != null) {
							Class itemFilterClass = Class.forName(nameAttribute.getText());
							itemFilter = (ItemFilter)itemFilterClass.newInstance();
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Exception occurred.");
			logger.error("Exception: " + ex.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavConfiguration#getItemFilter()
	 */
	public ItemFilter getItemFilter() {
		// return instance of DefaultItemFilterImpl, if no ItemFilter-Definition is set
		if (itemFilter == null) {
			itemFilter = new DefaultItemFilterImpl();
		}
		return itemFilter;
	}
}
