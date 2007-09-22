package org.openuss.search;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.openuss.foundation.DomainObject;
import org.springmodules.lucene.index.support.LuceneIndexSupport;

/**
 * Domain Index Command
 * @author Ingo Dueppe
 */
public abstract class DomainIndexer extends LuceneIndexSupport {

	private static final Logger logger = Logger.getLogger(DomainIndexer.class);

	public static final String NAME = "NAME";
	public static final String DETAILS = "DETAILS";
	
	public static final String IDENTIFIER = "IDENTIFIER";
	public static final String DOMAINTYPE = "DOMAINTYPE";
	public static final String MODIFIED = "MODIFIED";
	
	public static final String CONTENT = "CONTENT";
	
	private DomainObject domainObject;
	
	/**
	 * Create a new index entry of the domain object
	 */
	public abstract void create();
	
	/**
	 * Update the index entry of the domain object
	 */
	public abstract void update();

	/**
	 * Delete the index entry of the domain object
	 */
	public void delete() {
		Validate.notNull(getDomainObject(),"Field domainObject must not be null");
		logger.debug("deleting domain object ["+getDomainObject().getId()+"] from index");
		Term term = new Term(IDENTIFIER, String.valueOf(getDomainObject().getId()));
		getLuceneIndexTemplate().deleteDocuments(term);
	}

	/**
	 * {@inheritDoc}
	 */
	public DomainObject getDomainObject() {
		return domainObject;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setDomainObject(DomainObject domainObject) {
		this.domainObject = domainObject;
	}

}
