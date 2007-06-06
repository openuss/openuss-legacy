package org.openuss.mailinglist;

import org.apache.log4j.Logger;


import org.openuss.messaging.JobState;
import org.openuss.messaging.MessageJob;

/**
 * This aspect will observe the messaging service state and set the mail   
 * @author Ingo Dueppe
 */
public class MailingStatusAspect {
	
	private static final Logger logger = Logger.getLogger(MailingStatusAspect.class);
	
	private MailDao mailDao;

	/**
	 * Set mail state to <code>SEND</code> if the message job state is <code>DONE</code> 
	 * @param message MessageJob
	 */
	public void updateMailingStatus(MessageJob message) {
		if (message.getState() == JobState.DONE) { 
			logger.debug("setting mail state to SEND");
			Mail mail = mailDao.findMailByMessageId(message.getId());
			mail.setStatus(MailingStatus.SEND);
			mailDao.update(mail);
		}
	}

	public MailDao getMailDao() {
		return mailDao;
	}

	public void setMailDao(MailDao mailDao) {
		this.mailDao = mailDao;
	}

}
