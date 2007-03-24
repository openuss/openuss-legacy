package org.openuss.docmanagement;

import org.apache.log4j.Logger;

public class MailSendingImpl implements MailSending{

	public static final Logger logger = Logger.getLogger(MailSendingImpl.class);
	
	public void triggerMails(Long id, boolean all) {
		if (all){
			logger.debug("Mails for Faculty/Enrollment: "+ id.toString()+" send to all users");
			return;
		}
		logger.debug("Mails for Faculty/Enrollment: "+ id.toString()+" send to assistants");
	}
	
}