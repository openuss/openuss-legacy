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
		createCommandInDB(TestUtility.unique(), "commandname", "commandType", CommandState.ONCE);
	}
	
	public void testCommandDaoUpdate() {
		Command command = createCommandInDB(TestUtility.unique(), "commandname", "commandType", CommandState.EACH);
		commit();
		command = commandDao.load(command.getId());
		command.setCommand("new command");
		commit();
		command = commandDao.load(command.getId());
		assertEquals("new command",command.getCommand());
	}
	
	
	public void testFindAllOnceCommands() {
		markAllAsDone();
		
		Command[] command = new Command[5];
		fillCommandArray(command, CommandState.ONCE);
		
		command[4].setStartTime(new Date(System.currentTimeMillis()+10000L));
		
		commit();
		
		List<Command> commands = commandDao.findAllOnceCommands();
		
		assertEquals(4, commands.size());
		assertTrue(commands.contains(command[0]));
		assertTrue(commands.contains(command[1]));
		assertTrue(commands.contains(command[2]));
		assertTrue(commands.contains(command[3]));
		assertFalse(commands.contains(command[4]));
	
		markAllAsDone();

		commands = commandDao.findAllOnceCommands();
		assertEquals(0, commands.size());
	}

	private void markAllAsDone() {
		// check previous created commands and mark them as done
		List<Command> commands = commandDao.findAllOnceCommands();
		for (Command command : commands) {
			assertEquals(CommandState.ONCE, command.getState());
			command.setState(CommandState.DONE);
		}
		commit();
	}

	public void testFindAllEachCommandsAfter() {
		Command[] command = new Command[5];
		fillCommandArray(command, CommandState.EACH);
		
		commit();
		
		List<Command> commands = commandDao.findAllEachCommandsAfter(command[1].getId());
		
		assertEquals(3, commands.size());
		
		assertFalse(commands.contains(command[0]));
		assertFalse(commands.contains(command[1]));
		assertTrue(commands.contains(command[2]));
		assertTrue(commands.contains(command[3]));
		assertTrue(commands.contains(command[4]));
		
		
		fillCommandArray(command, CommandState.EACH);

		commit();
		
		commands = commandDao.findAllEachCommandsAfter(command[1].getId());
		
		assertEquals(3, commands.size());
		
		assertFalse(commands.contains(command[0]));
		assertFalse(commands.contains(command[1]));
		assertTrue(commands.contains(command[2]));
		assertTrue(commands.contains(command[3]));
		assertTrue(commands.contains(command[4]));
	}

	private void fillCommandArray(Command[] command, CommandState state) {
		for (int i = 0; i < command.length; i++) {
			command[i] = createCommandInDB(TestUtility.unique(), "commandName", "commanType", state);
		}
	}
	
	private Command createCommandInDB(Long domainIdentifier, String commandName, String commandType, CommandState state) {
		Command command = new CommandImpl();
		command.setDomainIdentifier(domainIdentifier);
		command.setCommand(commandName);
		command.setCommandType(commandType);
		command.setStartTime(new Date());
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