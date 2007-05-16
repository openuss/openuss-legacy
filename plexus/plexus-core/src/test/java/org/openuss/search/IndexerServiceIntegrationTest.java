// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.search;

import java.util.Collection;
import java.util.Iterator;

import org.openuss.commands.Command;
import org.openuss.commands.CommandDao;
import org.openuss.foundation.DomainObject;
import org.openuss.lecture.Faculty;

/**
 * JUnit Test for Spring Hibernate IndexerService class.
 * @see org.openuss.search.IndexerService
 */
public class IndexerServiceIntegrationTest extends IndexerServiceIntegrationTestBase {
	
	private CommandDao commandDao;
	
	public void testCreateIndex() throws IndexerApplicationException {
		DomainObject domain = createDomainObject();
		indexerService.createIndex(domain);
		check("facultyIndexer", "CREATE", domain);
	}

	private DomainObject createDomainObject() {
		Faculty faculty = Faculty.Factory.newInstance();
		faculty.setId(testUtility.unique());
		return faculty;
	}

	public void testUpdateIndex() throws IndexerApplicationException {
		DomainObject domain = createDomainObject();
		indexerService.updateIndex(domain);
		check("facultyIndexer", "UPDATE", domain);
	}

	public void testDeleteIndex() throws IndexerApplicationException {
		DomainObject domain = createDomainObject();
		indexerService.deleteIndex(domain);
		check("facultyIndexer", "DELETE", domain);
	}

	private void check(String command, String commandType, DomainObject domain) {
		Collection<Command> commands = commandDao.loadAll();
		assertFalse(commands.isEmpty());
		Command cmd = last(commands);
		assertEquals(domain.getId(), cmd.getDomainIdentifier());
		assertEquals(command, cmd.getCommand());
		assertEquals(commandType, cmd.getCommandType());
	}

	private Command last(Collection<Command> commands) {
		Command command = null;
		Iterator iter = commands.iterator();
		while (iter.hasNext()) {
			command = (Command) iter.next();
		}
		return command;
	}


	public CommandDao getCommandDao() {
		return commandDao;
	}

	public void setCommandDao(CommandDao commandDao) {
		this.commandDao = commandDao;
	}
}