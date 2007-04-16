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
	public void execute();
	
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
	 * Getting the command time
	 * @return Date - point of time when the command was fired
	 */
	public abstract Date getCommandTime();
	
	/**
	 * Setting the command time. 
	 * @param commandTime - point of time when the command was original fired
	 */
	public abstract void setCommandTime(Date commandTime);
	
}