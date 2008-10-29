// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;
import org.springframework.beans.support.PropertyComparator;

/**
 * JUnit Test for Spring Hibernate CommandService class.
 * @see org.openuss.commands.CommandService
 */
public class CommandServiceIntegrationTest extends CommandServiceIntegrationTestBase {
	
	private CommandDao commandDao;
	
	public void testDoClusterOnceCommand() throws CommandApplicationService {
		DomainObject domainObject = new DefaultDomainObject(TestUtility.unique());
		commandService.createOnceCommand(domainObject, "commandName", new Date(), "commandType");
		checkCommand(domainObject, CommandState.ONCE);
	}
	
	public void testDoClusterEachCommand() throws CommandApplicationService {
		DomainObject domainObject = new DefaultDomainObject(TestUtility.unique());
		commandService.createEachCommand(domainObject, "commandName", "commandType");
		checkCommand(domainObject, CommandState.EACH);
	}

	private void checkCommand(DomainObject domainObject, CommandState state) {
		Command command = lastCommand();
		assertNotNull(command);
		assertEquals(state, command.getState());
		assertEquals("commandName", command.getCommand());
		assertEquals("commandType", command.getCommandType());
		assertEquals(domainObject.getId(), command.getDomainIdentifier());
	}

	private Command lastCommand() {
		Collection<Command> commands = commandDao.loadAll();
		assertFalse(commands.isEmpty());
		List<Command> commandList = new ArrayList<Command>(commands);
		Collections.sort(commandList, new PropertyComparator("id", true, false));
		return commandList.get(0);
	}

	public CommandDao getCommandDao() {
		return commandDao;
	}

	public void setCommandDao(CommandDao commandDao) {
		this.commandDao = commandDao;
	}
	
}