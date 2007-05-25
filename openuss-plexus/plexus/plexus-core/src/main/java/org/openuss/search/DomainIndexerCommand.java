package org.openuss.search;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openuss.commands.AbstractDomainCommand;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextException;

/**
 * Domain Index Command
 * @author Ingo Dueppe
 */
public class DomainIndexerCommand extends AbstractDomainCommand implements InitializingBean{

	private static final Logger logger = Logger.getLogger(DomainIndexerCommand.class);
	
	private DomainIndexer domainIndexer;
	
	/**
	 * {@inheritDoc}
	 */
	public final void execute() {
		domainIndexer.setDomainObject(getDomainObject());
		if (StringUtils.equals("CREATE",getCommandType())) {
			domainIndexer.create();
		} else if (StringUtils.equals("UPDATE",getCommandType())) {
			domainIndexer.update();
		} else if (StringUtils.equals("DELETE",getCommandType())) {
			domainIndexer.delete();
		} else {
			logger.error("unkown command type "+getCommandType());
		}
	}

	public DomainIndexer getDomainIndexer() {
		return domainIndexer;
	}

	public void setDomainIndexer(DomainIndexer domainIndexer) {
		this.domainIndexer = domainIndexer;
	}

	public void afterPropertiesSet() throws Exception {
		if (domainIndexer == null)
			throw new ApplicationContextException("DomainIndexerCommand must be associated with a domainIndexer bean!");
	}
	
}
