package org.openuss.commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

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
		List<Command> commands = new ArrayList<Command>(store.values());
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

	private static Boolean lock = Boolean.FALSE;
	
	public boolean obtainLock(Command command) {
		if (!lock) {
			synchronized(lock) {
				lock = true;
				return lock;
			}
		}
		return false;
	}

	public void releaseLock(Command command) {
		synchronized(lock) {
			lock = false;
		}
	}

	public List findAllEachCommandsAfter(int transform, Long commandId) {
		return findAllEachCommandsAfter(commandId);
	}

	public List findAllEachCommandsAfter(int transform, String queryString, Long commandId) {
		return findAllEachCommandsAfter(commandId);
	}

}
