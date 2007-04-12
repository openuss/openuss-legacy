// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.search;

import java.util.Date;

import org.apache.commons.lang.Validate;
import org.openuss.foundation.DomainObject;

/**
 * @author Ingo Dueppe
 * @see org.openuss.search.IndexerService
 */
public class IndexerServiceImpl extends org.openuss.search.IndexerServiceBase {

	@Override
	protected void handleCreateIndex(DomainObject domainObject, String command) throws Exception {
		createIndexEvent(domainObject, command, IndexEventType.CREATE);
	}

	@Override
	protected void handleDeleteIndex(DomainObject domainObject, String command) throws Exception {
		createIndexEvent(domainObject, command, IndexEventType.DELETE);
		
	}

	@Override
	protected void handleUpdateIndex(DomainObject domainObject, String command) throws Exception {
		createIndexEvent(domainObject, command, IndexEventType.UPDATE);
	}
	
	private void createIndexEvent(DomainObject domainObject, String command, IndexEventType type) {
		Validate.notNull(domainObject, "Parameter domainObject must not be null.");
		Validate.notNull(domainObject.getId(), "Parameter domainObject.getId() must not be null.");
		Validate.notEmpty(command, "Parameter domainObject.getId() must not be null.");
		
		IndexEvent event = IndexEvent.Factory.newInstance();
		event.setCommandName(command);
		event.setDomainIdentifier(domainObject.getId());
		event.setEventType(type);
		event.setEventTime(new Date());
			
		getIndexEventDao().create(event);
	}

}