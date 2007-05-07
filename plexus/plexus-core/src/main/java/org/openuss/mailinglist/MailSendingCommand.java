package org.openuss.mailinglist;

import java.util.Date;

import org.openuss.commands.DomainCommand;
import org.openuss.foundation.DomainObject;

public class MailSendingCommand implements DomainCommand{

	public MailingListService mailingListService;
	
	private Date commandTime;
	
	private String commandType;
	
	private DomainObject domainObject;
	
	public void execute() throws Exception {
		// TODO implement me
	}

	public Date getStartTime() {		
		return commandTime;
	}

	public String getCommandType() {
		return commandType;
	}

	public DomainObject getDomainObject() {
		return domainObject;
	}

	public void setStartTime(Date commandTime) {
		this.commandTime = commandTime;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType; 
	}

	public void setDomainObject(DomainObject domainObject) {
		this.domainObject = domainObject;
	}

	public MailingListService getMailingListService() {
		return mailingListService;
	}

	public void setMailingListService(MailingListService mailingListService) {
		this.mailingListService = mailingListService;
	}
	
}