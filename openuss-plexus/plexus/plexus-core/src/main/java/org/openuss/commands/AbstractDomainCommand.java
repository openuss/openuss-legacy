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
		return startTime != null? new Date(startTime.getTime()):null;
	}

	public void setStartTime(Date commandTime) {
		this.startTime = commandTime != null ? new Date(commandTime.getTime()):null;
	}

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public DomainObject getDomainObject() {
		return domainObject;
	}

	public void setDomainObject(DomainObject domainObject) {
		this.domainObject = domainObject;
	}

}
