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

	public List findAllEachCommandsAfter(final Long commandId) {
		List<Command> commands = (List<Command>) loadAll();
		CollectionUtils.filter(commands, new Predicate() {
			public boolean evaluate(Object object) {
				if (object instanceof Command) {
					Command command = (Command) object;
					return command.getId() > commandId && command.getState() == CommandState.EACH;
				}
				return false;
			}
		});
		return commands;
	}

	public List findAllEachCommandsAfter(String queryString, Long commandId) {
		return findAllEachCommandsAfter(commandId);
	}

	public List findAllEachCommandsAfter(int transform, Long commandId) {
		return findAllEachCommandsAfter(commandId);
	}

	public List findAllEachCommandsAfter(int transform, String queryString, Long commandId) {
		return findAllEachCommandsAfter(commandId);
	}

	public List findAllOnceCommands() {
		List<Command> commands = (List<Command>) loadAll();
		CollectionUtils.filter(commands, new Predicate() {
			public boolean evaluate(Object object) {
				if (object instanceof Command) {
					Command command = (Command) object;
					return  command.getState() == CommandState.ONCE;
				}
				return false;
			}
		});
		return commands;
	}

	public List findAllOnceCommands(String queryString) {
		return findAllOnceCommands();
	}

	public List findAllOnceCommands(int transform) {
		return findAllOnceCommands();
	}

	public List findAllOnceCommands(int transform, String queryString) {
		return findAllOnceCommands();
	}

}
