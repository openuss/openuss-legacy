package org.openuss.search;

import org.apache.commons.lang.Validate;
import org.apache.lucene.index.Term;
import org.openuss.foundation.DomainObject;
import org.springmodules.lucene.index.support.LuceneIndexSupport;

/**
 * Abstract Domain Index Command
 * @author Ingo Dueppe
 */
public abstract class AbstractDomainIndexCommand extends LuceneIndexSupport implements DomainIndexerCommand {
	
	private DomainObject domainObject;
	
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

	public void delete() {
		Validate.notNull(getDomainObject(),"Field domainObject must not be null");
		Term term = new Term(IDENTIFIER, String.valueOf(getDomainObject().getId()));
		getLuceneIndexTemplate().deleteDocuments(term);
	}

}
