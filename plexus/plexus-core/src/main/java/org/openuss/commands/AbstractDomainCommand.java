package org.openuss.commands;

import java.util.Date;

import org.openuss.foundation.DomainObject;

/**
 * 
 * @author Ingo Dueppe
 * 
 */
public abstract class AbstractDomainCommand implements DomainCommand {

	private Date commandTime;
	private DomainObject domainObject;
	private String commandType;

	public Date getCommandTime() {
		return commandTime;
	}

	public void setCommandTime(Date commandTime) {
		this.commandTime = commandTime;
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
