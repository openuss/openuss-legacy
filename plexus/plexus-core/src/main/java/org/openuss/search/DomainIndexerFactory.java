package org.openuss.search;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.openuss.foundation.DomainObject;

/**
 * Factory to manage DomainIndexerCommands and associated DomainObjects
 * @author Ingo Dueppe
 */
public class DomainIndexerFactory {
	
	private Map<String, String> indexer = new HashMap<String, String>();

	public String getIndexerName(DomainObject domainObject) {
		Validate.notNull(domainObject, "Parameter domainObject must not be null.");
		return indexer.get(domainObject.getClass().getName());
	}
	
	public Map<String, String> getIndexer() {
		return indexer;
	}

	public void setIndexer(Map<String, String> indexer) {
		this.indexer = indexer;
	}

}
