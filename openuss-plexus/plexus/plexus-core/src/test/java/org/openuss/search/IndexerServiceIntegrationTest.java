// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.search;

import java.util.Collection;
import java.util.Iterator;

import org.openuss.TestUtility;
import org.openuss.commands.Command;
import org.openuss.commands.CommandDao;
import org.openuss.foundation.DomainObject;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteImpl;

/**
 * JUnit Test for Spring Hibernate IndexerService class.
 * @see org.openuss.search.IndexerService
 */
public class IndexerServiceIntegrationTest extends IndexerServiceIntegrationTestBase {
	
	private CommandDao commandDao;
	
	public void testCreateIndex() throws IndexerApplicationException {
		DomainObject domain = createDomainObject();
		indexerService.createIndex(domain);
		check("instituteIndexerCommand", "CREATE", domain);
	}

	private DomainObject createDomainObject() {
		Institute institute = new InstituteImpl();
		institute.setId(TestUtility.unique());
		return institute;
	}

	public void testUpdateIndex() throws IndexerApplicationException {
		DomainObject domain = createDomainObject();
		indexerService.updateIndex(domain);
		check("instituteIndexerCommand", "UPDATE", domain);
	}

	public void testDeleteIndex() throws IndexerApplicationException {
		DomainObject domain = createDomainObject();
		indexerService.deleteIndex(domain);
		check("instituteIndexerCommand", "DELETE", domain);
	}

	private void check(String command, String commandType, DomainObject domain) {
		Collection<Command> commands = commandDao.loadAll();
		assertFalse("command empty", commands.isEmpty());
		Command cmd = last(commands, domain.getId());
		assertEquals("domainIdentifier", domain.getId(), cmd.getDomainIdentifier());
		assertEquals("command", command, cmd.getCommand());
		assertEquals("commandType", commandType, cmd.getCommandType());
	}

	private Command last(Collection<Command> commands, Long domainId) {
		Command command = null;
		Iterator<Command> iter = commands.iterator();
		do {
			command = iter.next();
		} while (!command.getDomainIdentifier().equals(domainId) && iter.hasNext() );
		return command;
	}


	public CommandDao getCommandDao() {
		return commandDao;
	}

	public void setCommandDao(CommandDao commandDao) {
		this.commandDao = commandDao;
	}
}