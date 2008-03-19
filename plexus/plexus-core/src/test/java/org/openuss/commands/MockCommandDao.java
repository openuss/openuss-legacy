package org.openuss.commands;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.openuss.foundation.AbstractMockDao;

/**
 * Mockup Command Dao
 * 
 * @author Ingo Dueppe
 */
public class MockCommandDao extends AbstractMockDao<Command> implements CommandDao {

	public Command create(Long domainIdentifier, String command, String commandType, Date commandTime,
			CommandState state) {
		return null;
	}

	public Object create(int transform, Long domainIdentifier, String command, String commandType, Date commandTime,
			CommandState state) {
		return null;
	}

	public List<Command> findAllEachCommandsAfter(final Long commandId) {
		List<Command> commands = (List<Command>) loadAll();
		CollectionUtils.filter(commands, new EachCommandFilterPredicate(commandId));
		return commands;
	}

	public List<Command> findAllEachCommandsAfter(String queryString, Long commandId) {
		return findAllEachCommandsAfter(commandId);
	}

	public List<?> findAllEachCommandsAfter(int transform, Long commandId) {
		return findAllEachCommandsAfter(commandId);
	}

	public List<?> findAllEachCommandsAfter(int transform, String queryString, Long commandId) {
		return findAllEachCommandsAfter(commandId);
	}

	public List<Command> findAllOnceCommands() {
		List<Command> commands = (List<Command>) loadAll();
		CollectionUtils.filter(commands, new OnceCommandFilterPredicate());
		return commands;
	}

	public List<Command> findAllOnceCommands(String queryString) {
		return findAllOnceCommands();
	}

	public List<Command> findAllOnceCommands(int transform) {
		return findAllOnceCommands();
	}

	public List<?> findAllOnceCommands(int transform, String queryString) {
		return findAllOnceCommands();
	}

	public Command create(Long domainIdentifier, String command, CommandState state, String commandType, Date startTime, Date executionTime) {
		return create(Command.Factory.newInstance(domainIdentifier, command, state, commandType, startTime, executionTime));
	}

	public Object create(int transform, Long domainIdentifier, String command, CommandState state, String commandType, Date startTime, Date executionTime) {
		return create(Command.Factory.newInstance(domainIdentifier, command, state, commandType, startTime, executionTime));
	}

	private static final class EachCommandFilterPredicate implements Predicate {
		private final Long commandId;

		private EachCommandFilterPredicate(Long commandId) {
			this.commandId = commandId;
		}

		public boolean evaluate(Object object) {
			if (object instanceof Command) {
				Command command = (Command) object;
				return command.getId() > commandId && command.getState() == CommandState.EACH;
			}
			return false;
		}
	}

	private static final class OnceCommandFilterPredicate implements Predicate {
		public boolean evaluate(Object object) {
			if (object instanceof Command) {
				Command command = (Command) object;
				return  command.getState() == CommandState.ONCE;
			}
			return false;
		}
	}

}
