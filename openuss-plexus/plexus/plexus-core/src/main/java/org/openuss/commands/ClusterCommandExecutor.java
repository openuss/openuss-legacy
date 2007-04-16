package org.openuss.commands;

import java.util.Collection;

import org.openuss.foundation.DefaultDomainObject;
import org.openuss.system.SystemService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Cluster Comand Executor
 * @author Ingo Dueppe
 */
public class ClusterCommandExecutor implements ApplicationContextAware{

	private ApplicationContext applicationContext;
	
	private CommandDao commandDao;
	
	private LastProcessedCommandDao lastProcessedCommandDao;
	
	private SystemService systemService;
	
	/**
	 * process all pending commands
	 */
	public void processCommands() {
		Collection<Command> commands = loadNextCommands();
		for (Command command : commands) {
			if (CommandState.EACH.equals(command.getCommandType())) {
				DomainCommand domainCommand = createCommand(command);
				domainCommand.execute();
			}
		}
	}
	
	private Collection<Command> loadNextCommands() {
		LastProcessedCommand last = lastProcessedCommandDao.load(systemService.getInstanceIdentity());
		
		if (last != null || last.getLast() != null) {
			return commandDao.loadAll();
		} else {
			return commandDao.findAllEventsAfter(last.getLast().getId());
		}
	}
	
	
	private DomainCommand createCommand(Command command) {
		DomainCommand domainCommand = (DomainCommand) applicationContext.getBean(command.getCommand());
		
		domainCommand.setDomainObject(new DefaultDomainObject(command.getDomainIdentifier()));
		domainCommand.setCommandTime(command.getCommandTime());
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
