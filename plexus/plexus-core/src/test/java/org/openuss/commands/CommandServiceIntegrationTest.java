// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.commands;

import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;

/**
 * JUnit Test for Spring Hibernate CommandService class.
 * @see org.openuss.commands.CommandService
 */
public class CommandServiceIntegrationTest extends CommandServiceIntegrationTestBase {
	
	public void testDoClusterOnceCommand() throws CommandApplicationService {
		DomainObject domainObject = new DefaultDomainObject(testUtility.unique());
		
//		TestCommandDao commandDao = new TestCommandDao(); 
//		injectCommandDao(commandDao);
		
		commandService.doClusterOnceCommand(domainObject, "commandName", "commandType");
		
//		Command command = commandDao.getCommand();
//		assertEquals(CommandState.ONCE, command.getState());
//		assertEquals("commandName", command.getCommand());
//		assertEquals("commandType", command.getCommandType());
//		assertEquals(domainObject.getId(), command.getDomainIdentifier());
	}

	public void testDoClusterEachCommand() throws CommandApplicationService {
		DomainObject domainObject = new DefaultDomainObject(testUtility.unique());
		
//		TestCommandDao commandDao = new TestCommandDao(); 
//		injectCommandDao(commandDao);
		
		commandService.doClusterOnceCommand(domainObject, "commandName", "commandType");
		
//		Command command = commandDao.getCommand();
//		assertEquals(CommandState.EACH, command.getState());
//		assertEquals("commandName", command.getCommand());
//		assertEquals("commandType", command.getCommandType());
//		assertEquals(domainObject.getId(), command.getDomainIdentifier());
	}
	
	public class DummyCommandDao extends CommandDaoImpl {
		Command command;
		
		@Override
		public Command create(Command command) {
			this.command = command;
			command.setId(testUtility.unique());
			return command;
		}
		
		public Command getCommand() {
			return command;
		}
		
		public void setCommand(Command command) {
			this.command = command;
		}
	}
	
}