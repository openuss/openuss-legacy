package org.openuss.mailinglist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openuss.commands.AbstractDomainCommand;
import org.openuss.commands.DomainCommand;
import org.openuss.messaging.MessageService;
import org.openuss.security.User;

public class MailSendingCommand extends AbstractDomainCommand implements DomainCommand{

	private MailDao mailDao;
	private MessageService messageService;
	
	public void execute() throws Exception {
		Mail mail = mailDao.load(getDomainObject().getId());
		if (mail.getStatus() == MailingStatus.PLANNED){
			Set<Subscriber> recipients = mail.getMailingList().getSubscribers();
			Iterator i = recipients.iterator();
			List<User> users = new ArrayList<User>();
			while (i.hasNext()){
				users.add(((Subscriber)i.next()).getUser());
			}
			Long messageId = getMessageService().sendMessage(mail.getMailingList().getName(), mail.getSubject(), mail.getText(), mail.isSms(), users);
			mail.setStatus(MailingStatus.INQUEUE);
			mail.setMessageId(messageId);
			getMailDao().update(mail);
		}
	}

	public MailDao getMailDao() {
		return mailDao;
	}


	public void setMailDao(MailDao mailDao) {
		this.mailDao = mailDao;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}


	
}