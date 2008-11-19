package org.openuss.commands;

import java.util.Date;

import org.openuss.foundation.DomainObject;

/**
 * ClusterCommandService will asynchronesly execute cluster commands. There
 * exists two types of commands. Commands that will be execute on each server
 * node of the cluster and commands that will be executed only once on one of
 * the server nodes.
 * 
 * @author Ingo Dueppe
 */
public interface CommandService {

	/**
	 * Execute a command that will be executed on each node within the cluster.
	 * 
	 * @Param domainObject - optional instance of the domain object
	 * @Param command - bean name of the command to be executed
	 * @Param commandType - optional parameter for the command
	 */
	public void createEachCommand(DomainObject domainObject, String commandName, String commandType)
			throws CommandApplicationService;

	/**
	 * Execute a command that will only be executed by one of the server nodes.
	 * 
	 * @Param domainObject - instance of a domainObject containing an identifier
	 * @Param commandName - name of a command bean
	 * @Param startTime - earliest point of time to execute the command
	 * @Param commandType - optional parameter for additional information
	 * @Return identifier of the command
	 */
	public Long createOnceCommand(DomainObject domainObject, String commandName, Date startTime, String commandType)
			throws CommandApplicationService;

	/**
	 * Change the startTime of the given command
	 * 
	 * @Param commandId - identifier of the command
	 * @Param startTime - new earliest point of time to execute the command
	 */
	public void setStartTime(Long commandId, Date startTime) throws CommandApplicationService;

	/**
	 * remove the given command
	 * 
	 * @Param commandId - identifier of the command
	 */
	public void deleteCommand(Long commandId) throws CommandApplicationService;

}
