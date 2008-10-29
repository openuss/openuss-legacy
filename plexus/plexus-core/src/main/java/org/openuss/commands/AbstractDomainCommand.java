package org.openuss.commands;

import java.util.Date;

import org.openuss.foundation.DomainObject;

/**
 * Default implementation of DomainCommand
 * @author Ingo Dueppe
 * 
 */
public abstract class AbstractDomainCommand implements DomainCommand {

	private Date startTime;
	
	private DomainObject domainObject;
	private String commandType;

	public Date getStartTime() {
		return startTime == null? null : new Date(startTime.getTime());
	}

	public void setStartTime(final Date commandTime) {
		this.startTime = (commandTime == null) ? null : new Date(commandTime.getTime());
	}

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(final String commandType) {
		this.commandType = commandType;
	}

	public DomainObject getDomainObject() {
		return domainObject;
	}

	public void setDomainObject(final DomainObject domainObject) {
		this.domainObject = domainObject;
	}

}
