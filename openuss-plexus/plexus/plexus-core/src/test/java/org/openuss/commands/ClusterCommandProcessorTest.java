package org.openuss.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * Testing the CommandClusterHandler
 * 
 * @author Ingo Dueppe
 */
public class ClusterCommandProcessorTest extends AbstractDependencyInjectionSpringContextTests {

	private ClusterCommandProcessor commandProcessor;

	private MockCommandDao mockCommandDao;

	private MockCommand mockCommand;

	private MockLastProcessedCommandDao mockLastProcessedCommandDao;

	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		mockCommand.reset();
		mockCommandDao.reset();
		mockLastProcessedCommandDao.reset();
	}

	public void testEachCommandExecution() {
		List<Command> commands = createCommands(10, CommandState.EACH);
		mockCommandDao.create(commands);
		commandProcessor.processNodeCommands();
		assertEquals(commands.size(), mockCommand.getExecutionCount());

		checkStateForAll(commands, CommandState.EACH);
		assertEquals(commands.get(commands.size()-1),lastProcessedCommand());

		commandProcessor.processNodeCommands();
		assertEquals(commands.size(), mockCommand.getExecutionCount());
	}

	public void testEachCommandExecutionWithError() {
		List<Command> commands = createCommands(10, CommandState.EACH);
		mockCommandDao.create(commands);
		mockCommand.setThrowException(true);
		commandProcessor.processNodeCommands();
		assertEquals(commands.size(), mockCommand.getExecutionCount());

		checkStateForAll(commands, CommandState.ERROR);
		assertEquals(commands.get(commands.size()-1),lastProcessedCommand());
		commandProcessor.processNodeCommands();
		assertEquals(commands.size(), mockCommand.getExecutionCount());
	}

	public void testOnceCommandExecution() {
		List<Command> commands = createCommands(10, CommandState.ONCE);
		mockCommandDao.create(commands);
		commandProcessor.processCommand(commands.get(0).getId());
		assertEquals(1, mockCommand.getExecutionCount());
		Command commandFirst = commands.get(0);
		assertEquals(CommandState.DONE, commandFirst.getState());
		Command commandSecond = commands.get(1);
		assertEquals(CommandState.ONCE, commandSecond.getState());
		
		for(int i=0; i < 10; i++) {
			commandProcessor.processCommand(commands.get(i).getId());
		}
		checkStateForAll(commands, CommandState.DONE);
	}

	private Command lastProcessedCommand() {
		Command last = mockLastProcessedCommandDao.load(commandProcessor.getSystemService().getInstanceIdentity()).getLast();
		return last;
	}

	private void checkStateForAll(List<Command> commands, final CommandState state) {
		CollectionUtils.forAllDo(commands, new Closure() {
			public void execute(Object input) {
				assertEquals(state, ((Command)input).getState());
			}
		});
	}


	private List<Command> createCommands(int count, CommandState state) {
		List<Command> commands = new ArrayList<Command>();
		for (int i = 0; i < count; i++) {
			commands.add(createCommand(state));
		}
		return commands;
	}

	private Command createCommand(CommandState state) {
		Command command = new CommandImpl();
		command.setState(state);
		command.setCommand("mockCommand");
		command.setCommandType("update");
		command.setDomainIdentifier(130L);
		return command;
	}

	public ClusterCommandProcessor getCommandProcessor() {
		return commandProcessor;
	}

	public void setCommandProcessor(ClusterCommandProcessor commandProcessor) {
		this.commandProcessor = commandProcessor;
	}

	protected String[] getConfigLocations() {
		return new String[] { "classpath*:testCommands.xml" };
	}

	public MockCommandDao getMockCommandDao() {
		return mockCommandDao;
	}

	public void setMockCommandDao(MockCommandDao mockCommandDao) {
		this.mockCommandDao = mockCommandDao;
	}

	public MockLastProcessedCommandDao getMockLastProcessedCommandDao() {
		return mockLastProcessedCommandDao;
	}

	public void setMockLastProcessedCommandDao(MockLastProcessedCommandDao mockLastProcessedCommandDao) {
		this.mockLastProcessedCommandDao = mockLastProcessedCommandDao;
	}

	public void setMockCommand(MockCommand mockCommand) {
		this.mockCommand = mockCommand;
	}

	public MockCommand getEachCommand() {
		return mockCommand;
	}

	public void setEachCommand(MockCommand eachCommand) {
		this.mockCommand = eachCommand;
	}

}
