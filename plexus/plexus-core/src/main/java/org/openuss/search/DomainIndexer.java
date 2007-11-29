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
	
	public static final String UNIVERSITY_IDENTIFIER = "UNIVERSITY_IDENTIFIER";
	public static final String DEPARTMENT_IDENTIFIER = "DEPARTMENT_IDENTIFIER";
	public static final String INSTITUTE_IDENTIFIER = "INSTITUTE_IDENTIFIER";
	public static final String COURSE_TYPE_IDENTIFIER = "COURSE_TYPE_IDENTIFIER";
	public static final String PERIOD_IDENTIFIER = "PERIOD_IDENTIFIER";
	public static final String OFFICIAL_FLAG = "OFFICIAL_FLAG";
	public static final String RESULT_TYPE = "RESULT_TYPE";
	
	public static final int EXTENDED_SEARCH_RESULT_TYPE_UNIVERSITY = 1;
	public static final int EXTENDED_SEARCH_RESULT_TYPE_DEPARTMENT = 2;
	public static final int EXTENDED_SEARCH_RESULT_TYPE_INSTITUTION = 3;
	public static final int EXTENDED_SEARCH_RESULT_TYPE_COURSE_TYPE = 4;
	public static final int EXTENDED_SEARCH_RESULT_TYPE_COURSE = 5;

	// discussion search
	public static final String POST_TITLE = "POST_TITLE";
	public static final String POST_COURSE_IDENTIFIER = "POST_COURSE_IDENTIFIER";
	public static final String POST_IDENTIFIER = "POST_IDENTIFIER";
	public static final String POST_SUBMITTER = "POST_SUBMITTER";	
		
	
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
		logger.debug("Method delete: delete index entry for domain object");
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
