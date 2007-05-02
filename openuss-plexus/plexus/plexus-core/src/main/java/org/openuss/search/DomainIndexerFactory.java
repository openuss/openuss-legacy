package org.openuss.search;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.openuss.foundation.DomainObject;

/**
 * Factory to manage DomainIndexerCommands and associated DomainObjects
 * @author Ingo Dueppe
 */
public class DomainIndexerFactory {
	private static final Logger logger = Logger.getLogger(DomainIndexerFactory.class);
	
	private Map<String, String> indexer = new HashMap<String, String>();

	public String getIndexerName(DomainObject domainObject) {
		Validate.notNull(domainObject, "Parameter domainObject must not be null.");
		Class[] interfaces = domainObject.getClass().getInterfaces();
		for (Class interfaceClass : interfaces) {
			String name = indexer.get(interfaceClass.getName());
			if (StringUtils.isNotBlank(name)) {
				return name;
			}
		}
		// TODO - looks urgly...
		// Maybe the domainObject is wrapped by a proxy - some trying the other way around.
		for (String name : indexer.keySet()) {
			try {
				if (Class.forName(name).isAssignableFrom(domainObject.getClass())) {
					return name;
				}
			} catch (ClassNotFoundException e) {
				logger.error(e);
			}
		}
		throw new IllegalStateException("No DomainIndexer found for "+domainObject);
	}
	
	public Map<String, String> getIndexer() {
		return indexer;
	}

	public void setIndexer(Map<String, String> indexer) {
		this.indexer = indexer;
	}

}
