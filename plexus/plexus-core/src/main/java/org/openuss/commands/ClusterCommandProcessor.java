package org.openuss.commands;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.openuss.foundation.DefaultDomainObject;
import org.openuss.system.SystemService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Cluster Comand Executor
 * 
 * @author Ingo Dueppe
 */
public class ClusterCommandProcessor implements ApplicationContextAware {

	private static final Logger logger = Logger.getLogger(ClusterCommandProcessor.class);

	private ApplicationContext applicationContext;

	private CommandDao commandDao;

	private LastProcessedCommandDao lastProcessedCommandDao;

	private SystemService systemService;

	/**
	 * process all pending each node commands
	 */
	public void processEachCommands() {
		logger.info("starting processing each commands");
		Collection<Command> commands = loadNextEachCommands();
		for (Command command : commands) {
			processEachCommand(command);
			updateLastProcessedCommand(command);
		}
		logger.info("finished processing each commands");
	}
	
	/**
	 * process next pending once node command
	 */
	public void processOnceCommand() {
		logger.info("starting processing next once command");
		Command command = loadNextOnceCommand();
		if (command != null) {
			try {
				createCommand(command).execute();
				command.setState(CommandState.DONE);
			} catch (Throwable th) {
				logExecutionError(command, th);
				command.setState(CommandState.ERROR);
			} finally {
				command.setExecutionTime(new Date());
				commandDao.update(command);
			}
		}
		logger.info("finished processing next once command");
	}
	
	private Command loadNextOnceCommand() {
		List<Command> commands = commandDao.findAllOnceCommands();
		if (!commands.isEmpty()) {
			return commands.get(0);
		} else {
			return null;
		}
	}

	private void processEachCommand(Command command) {
		try {
			createCommand(command).execute();
		} catch (Throwable th) {
			logExecutionError(command, th);
			command.setState(CommandState.ERROR);
			commandDao.update(command);
		}
	}

	private void updateLastProcessedCommand(Command command) {
		LastProcessedCommand last = lastProcessedCommandDao.load(systemService.getInstanceIdentity());
		if (last == null || last.getId() == null) {
			last = LastProcessedCommand.Factory.newInstance();
			last.setId(systemService.getInstanceIdentity());
			last.setLast(command);
			lastProcessedCommandDao.create(last);
		} else {
			last.setLast(command);
			lastProcessedCommandDao.update(last);
		}
	}

	private void logExecutionError(Command command, Throwable th) {
		StringBuilder msg = new StringBuilder();
		msg.append("\nCommand throw exception on execution:");
		msg.append("\n\t id = "+command.getId());
		msg.append("\n\t DomainIdentifier = "+command.getDomainIdentifier());
		msg.append("\n\t Command = "+command.getCommand());
		msg.append("\n\t CommandType = "+command.getCommandType());
		msg.append("\n\t StartTime = "+command.getStartTime());
		msg.append("\n\t ExecutionTime = "+command.getExecutionTime());
		msg.append("\n\t State = "+command.getState());
		
		logger.error(msg.toString(), th);
	}

	private Collection<Command> loadNextEachCommands() {
		LastProcessedCommand last = lastProcessedCommandDao.load(systemService.getInstanceIdentity());

		if (last == null || last.getLast() == null) {
			return commandDao.findAllEachCommandsAfter(0L);
		} else {
			return commandDao.findAllEachCommandsAfter(last.getLast().getId());
		}
	}

	private DomainCommand createCommand(Command command) {
		DomainCommand domainCommand = (DomainCommand) applicationContext.getBean(command.getCommand());

		domainCommand.setDomainObject(new DefaultDomainObject(command.getDomainIdentifier()));
		domainCommand.setStartTime(command.getStartTime());
		domainCommand.setCommandType(command.getCommandType());
		return domainCommand;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public CommandDao getCommandDao() {
		return commandDao;
	}

	public void setCommandDao(CommandDao commandDao) {
		this.commandDao = commandDao;
	}

	public LastProcessedCommandDao getLastProcessedCommandDao() {
		return lastProcessedCommandDao;
	}

	public void setLastProcessedCommandDao(LastProcessedCommandDao lastProcessedCommandDao) {
		this.lastProcessedCommandDao = lastProcessedCommandDao;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

}
