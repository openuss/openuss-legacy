// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.commands;

import java.util.Date;

import org.apache.commons.lang.Validate;
import org.openuss.foundation.DomainObject;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

/**
 * @author Ingo Dueppe
 * @see org.openuss.commands.CommandService
 */
public class CommandServiceImpl extends CommandServiceBase {

	/**
	 * @see org.openuss.commands.CommandService#doClusterEachCommand(org.openuss.foundation.DomainObject,
	 *      java.lang.String, java.lang.String)
	 */
	protected void handleCreateEachCommand(final DomainObject domainObject, final String commandName, final String commandType) {
		createCommand(domainObject, commandName, commandType, CommandState.EACH, new Date());
	}

	/**
	 * @throws SchedulerException 
	 * @see org.openuss.commands.CommandService#doClusterOnceCommand(org.openuss.foundation.DomainObject,
	 *      java.lang.String, java.lang.String)
	 */
	protected Long handleCreateOnceCommand(final DomainObject domainObject, final String commandName, final Date startTime, final String commandType) throws SchedulerException {
		Long commandId = createCommand(domainObject, commandName, commandType, CommandState.ONCE, startTime);

		Trigger trigger = new SimpleTrigger("Command-"+commandId, Scheduler.DEFAULT_GROUP, startTime);
		trigger.setJobName("cluster_command");
		JobDataMap jdm = new JobDataMap();
		jdm.put("commandId", commandId);
		trigger.setJobDataMap(jdm);
		getClusterScheduler().scheduleJob(trigger);
		
		return commandId;
	}

	private Long createCommand(final DomainObject domainObject, final String commandName, final String commandType, final CommandState state, final Date startTime) {
		Validate.notNull(domainObject,"DomainObject must not be null.");
		Validate.notNull(domainObject.getId(), "DomainObject must provide an id");
		Validate.notEmpty(commandName, "CommandName must not be null.");
		Validate.notNull(state, "State must not be null");

		Command command = Command.Factory.newInstance();
		command.setDomainIdentifier(domainObject.getId());
		command.setCommand(commandName);
		if (startTime == null) {
			command.setStartTime(null);
		} else {
			command.setStartTime(startTime);
		}
		command.setCommandType(commandType);
		command.setState(state);
		getCommandDao().create(command);
		return command.getId();
	}

	@Override
	protected void handleDeleteCommand(final Long commandId) {
		getCommandDao().remove(commandId);
	}

	@Override
	protected void handleSetStartTime(final Long commandId, final Date startTime) {
		Command command = getCommandDao().load(commandId);
		command.setStartTime(startTime);
		getCommandDao().update(command);
	}
}