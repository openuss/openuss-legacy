// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.commands;

import java.util.Date;

import org.apache.commons.lang.Validate;
import org.openuss.foundation.DomainObject;

/**
 * @author Ingo Dueppe
 * @see org.openuss.commands.CommandService
 */
public class CommandServiceImpl extends CommandServiceBase {

	/**
	 * @see org.openuss.commands.CommandService#doClusterEachCommand(org.openuss.foundation.DomainObject,
	 *      java.lang.String, java.lang.String)
	 */
	protected void handleDoClusterEachCommand(DomainObject domainObject,String commandName, String commandType) throws Exception {
		createCommand(domainObject, commandName, commandType, CommandState.EACH);
	}

	/**
	 * @see org.openuss.commands.CommandService#doClusterOnceCommand(org.openuss.foundation.DomainObject,
	 *      java.lang.String, java.lang.String)
	 */
	protected void handleDoClusterOnceCommand(DomainObject domainObject, String commandName, String commandType) throws java.lang.Exception {
		createCommand(domainObject, commandName, commandType, CommandState.ONCE);
	}

	private void createCommand(DomainObject domainObject, String commandName, String commandType, CommandState state) {
		Validate.notNull(domainObject,"DomainObject must not be null.");
		Validate.notNull(domainObject.getId(), "DomainObject must provide an id");
		Validate.notEmpty(commandName, "CommandName must not be null.");
		Validate.notNull(state, "State must not be null");
		
		Command command = Command.Factory.newInstance();
		command.setDomainIdentifier(domainObject.getId());
		command.setCommand(commandName);
		command.setCommandTime(new Date());
		command.setCommandType(commandType);
		command.setState(state);
		getCommandDao().create(command);
	}
}