package org.openuss.commands;

import java.util.Date;

import org.openuss.foundation.DomainObject;

/**
 * DomainCommand 
 * @author Ingo Dueppe
 *
 */
public interface DomainCommand {

	/**
	 * Execute the command
	 */
	public void execute() throws Exception;
	
	/**
	 * Retrieve the associated domain object
	 * @return DomainObject instance
	 */
	public abstract DomainObject getDomainObject();

	/**
	 * Define the associated domain object.
	 * @param domainObject
	 */
	public abstract void setDomainObject(DomainObject domainObject);
	
	/**
	 * Getting the subtype of the command
	 * @return String commandType
	 */
	public abstract String getCommandType();
	
	/**
	 * Setting the subtype of the command
	 * @param commandType
	 */
	public abstract void setCommandType(String commandType);
	
	
	/**
	 * Getting the start time
	 * @return Date - earliest point of time to execute the command
	 */
	public abstract Date getStartTime();
	
	/**
	 * Setting the start time. 
	 * @param startTime - earliest point of time to execute the command
	 */
	public abstract void setStartTime(Date startTime);
	
}