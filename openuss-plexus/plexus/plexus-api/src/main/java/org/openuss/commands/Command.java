package org.openuss.commands;

import java.util.Date;

import org.openuss.foundation.DomainObject;

/**
 * 
 * @author Ingo Dueppe
 * 
 */
public interface Command extends DomainObject {

	public Long getId();

	public void setId(Long id);

	public Long getDomainIdentifier();

	public void setDomainIdentifier(Long domainIdentifier);

	public String getCommand();

	public void setCommand(String command);

	public CommandState getState();

	public void setState(CommandState state);

	public String getCommandType();

	public void setCommandType(String commandType);

	public Date getStartTime();

	public void setStartTime(Date startTime);

	public Date getExecutionTime();

	public void setExecutionTime(Date executionTime);

}