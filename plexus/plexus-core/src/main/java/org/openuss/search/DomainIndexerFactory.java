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
 * @author Kai Stettner
 */
public class DomainIndexerFactory {
	private static final Logger logger = Logger.getLogger(DomainIndexerFactory.class);
	
	private Map<String, String> indexer = new HashMap<String, String>();

	public String getIndexerName(DomainObject domainObject) {
		// FIXME Soon - we don't need this anymore
		
		logger.debug("Starting method getIndexerName");
		Validate.notNull(domainObject, "Parameter domainObject must not be null.");
		//Just for debugging
		logger.debug(domainObject.getId());
		logger.debug(domainObject.getClass());
		logger.debug(domainObject.getClass().getInterfaces());
		Class[] interfaces = domainObject.getClass().getInterfaces();
		for (Class interfaceClass : interfaces) {
			logger.debug(interfaceClass.getName());
			String name = indexer.get(interfaceClass.getName());
			logger.debug(name);
			if (StringUtils.isNotBlank(name)) {
				logger.debug("In loop");
				return name;
			}
		}
		// TODO - looks ugly...
		// Maybe the domainObject is wrapped by a proxy - some trying the other way around.
		for (String name : indexer.keySet()) {
			//Just for debugging
			logger.debug(name);
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
