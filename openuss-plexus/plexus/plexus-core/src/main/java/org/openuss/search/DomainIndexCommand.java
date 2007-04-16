package org.openuss.search;

import org.apache.log4j.Logger;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.lucene.index.Term;
import org.openuss.commands.DomainCommand;
import org.openuss.foundation.DomainObject;
import org.springmodules.lucene.index.support.LuceneIndexSupport;

/**
 * Domain Index Command
 * @author Ingo Dueppe
 */
public abstract class DomainIndexCommand extends LuceneIndexSupport implements DomainCommand {

	private static final Logger logger = Logger.getLogger(DomainIndexCommand.class);
	
	public static final String IDENTIFIER = "IDENTIFIER";
	public static final String DOMAINTYPE = "DOMAINTYPE";
	public static final String MODIFIED = "MODIFIED";
	public static final String CONTENT = "CONTENT";
	
	private DomainObject domainObject;
	private String commandType;
	private Date commandTime;
	
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
		Term term = new Term(IDENTIFIER, String.valueOf(getDomainObject().getId()));
		getLuceneIndexTemplate().deleteDocuments(term);
	}

	/**
	 * {@inheritDoc}
	 */
	public final void execute() { 
		if (StringUtils.equals("CREATE",commandType)) {
			this.create();
		} else if (StringUtils.equals("UPDATE",commandType)) {
			this.update();
		} else if (StringUtils.equals("DELETE",commandType)) {
			this.delete();
		} else {
			logger.error("unkown command type "+commandType);
		}
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

	/**
	 * {@inheritDoc}
	 */
	public Date getCommandTime() {
		return commandTime;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCommandTime(Date commandTime) {
		this.commandTime = commandTime;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getCommandType() {
		return commandType;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}
}
