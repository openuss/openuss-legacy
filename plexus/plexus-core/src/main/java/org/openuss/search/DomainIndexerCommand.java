package org.openuss.search;

import org.openuss.foundation.DomainObject;


/**
 * The DomainIndexerCommand will be called by the IndexerJob to index a given domain object. 
 * @author Ingo Dueppe
 */
public interface DomainIndexerCommand {
	
	public static final String IDENTIFIER = "IDENTIFIER";
	public static final String DOMAINTYPE = "DOMAINTYPE";
	public static final String MODIFIED = "MODIFIED";
	public static final String CONTENT = "CONTENT";
	
	/**
	 * Retrieve the associated domain object
	 * @return DomainObject instance
	 */
	public abstract DomainObject getDomainObject();

	/**
	 * Define the associated domain object.
	 * @param domainObject
	 */
	public abstract void setDomainObject(DomainObject domainObject);

	/**
	 * Create a new index entry for the domain object 
	 */
	public abstract void create();

	/**
	 * Update the index entry of domain object
	 */
	public abstract void update();

	/**
	 * Delete the index entry of the domain object
	 */
	public abstract void delete();

}