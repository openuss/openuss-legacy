// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.commands;

import java.util.Date;
import java.util.List;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate CommandDao class.
 * @see org.openuss.commands.CommandDao
 */
public class CommandDaoTest extends CommandDaoTestBase {
	
	private TestUtility testUtility;
	
	public void testCommandDaoCreate() {
		createCommandInDB(testUtility.unique(), "commandname", "commandType", CommandState.ONCE);
	}
	
	public void testFindAllCommandsAfter() {
		Command command1 = createCommandInDB(testUtility.unique(), "commandName", "commanType", CommandState.EACH);
		Command command2 = createCommandInDB(testUtility.unique(), "commandName", "commanType", CommandState.EACH);
		Command command3 = createCommandInDB(testUtility.unique(), "commandName", "commanType", CommandState.EACH);
		Command command4 = createCommandInDB(testUtility.unique(), "commandName", "commanType", CommandState.EACH);
		Command command5 = createCommandInDB(testUtility.unique(), "commandName", "commanType", CommandState.EACH);
		
		commit();
		
		List<Command> commands = commandDao.findAllEventsAfter(command2.getId());
		
		assertEquals(3, commands.size());
		
		assertFalse(commands.contains(command1));
		assertFalse(commands.contains(command2));
		assertTrue(commands.contains(command3));
		assertTrue(commands.contains(command4));
		assertTrue(commands.contains(command5));
		
		
		command1 = createCommandInDB(testUtility.unique(), "commandName", "commanType", CommandState.EACH);
		command2 = createCommandInDB(testUtility.unique(), "commandName", "commanType", CommandState.EACH);
		command3 = createCommandInDB(testUtility.unique(), "commandName", "commanType", CommandState.EACH);
		command4 = createCommandInDB(testUtility.unique(), "commandName", "commanType", CommandState.EACH);
		command5 = createCommandInDB(testUtility.unique(), "commandName", "commanType", CommandState.EACH);

		commit();
		
		commands = commandDao.findAllEventsAfter(command2.getId());
		
		assertEquals(3, commands.size());
		
		assertFalse(commands.contains(command1));
		assertFalse(commands.contains(command2));
		assertTrue(commands.contains(command3));
		assertTrue(commands.contains(command4));
		assertTrue(commands.contains(command5));
		
	}
	
	private void commit() {
		setComplete();
		endTransaction();
		startNewTransaction();
	}

	private Command createCommandInDB(Long domainIdentifier, String commandName, String commandType, CommandState state) {
		Command command = new CommandImpl();
		command.setDomainIdentifier(domainIdentifier);
		command.setCommand(commandName);
		command.setCommandType(commandType);
		command.setCommandTime(new Date());
		command.setState(state);
		assertNull(command.getId());
		commandDao.create(command);
		assertNotNull(command.getId());
		return command;
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
}