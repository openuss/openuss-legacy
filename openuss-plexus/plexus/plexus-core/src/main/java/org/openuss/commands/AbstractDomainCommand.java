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
		return startTime;
	}

	public void setStartTime(Date commandTime) {
		this.startTime = commandTime;
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
