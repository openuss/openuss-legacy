// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.search;

import org.apache.commons.lang.Validate;
import org.openuss.commands.CommandApplicationService;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;

/**
 * @author Ingo Dueppe
 * @see org.openuss.search.IndexerService
 */
public class IndexerServiceImpl extends IndexerServiceBase {

	private DomainIndexerFactory indexerFactory;
	
	@Override
	protected void handleCreateIndex(DomainObject domainObject) throws Exception {
		createIndexEvent(domainObject, "CREATE");
	}

	@Override
	protected void handleDeleteIndex(DomainObject domainObject) throws Exception {
		createIndexEvent(domainObject, "DELETE");
		
	}

	@Override
	protected void handleUpdateIndex(DomainObject domainObject) throws Exception {
		createIndexEvent(domainObject, "UPDATE");
	}
	
	private void createIndexEvent(DomainObject domainObject, String commandType) throws IndexerApplicationException {
		Validate.notNull(domainObject, "Parameter domainObject must not be null.");
		Validate.notNull(domainObject.getId(), "Parameter domainObject.getId() must not be null.");
		
		try {
			String commandName = indexerFactory.getIndexerName(domainObject);
			getCommandService().createEachCommand(domainObject, commandName, commandType);
		} catch (CommandApplicationService e) {
			throw new IndexerApplicationException(e);
		}
	}
	
	protected void handleRecreate() throws Exception {
		try {
			getCommandService().createEachCommand(new DefaultDomainObject(1L), "recreateIndexerCommand", "UPDATE");
		} catch (CommandApplicationService e) {
			throw new IndexerApplicationException(e);
		}
	}

	public DomainIndexerFactory getIndexerFactory() {
		return indexerFactory;
	}

	public void setIndexerFactory(DomainIndexerFactory indexerFactory) {
		this.indexerFactory = indexerFactory;
	}
	

}