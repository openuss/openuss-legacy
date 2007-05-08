// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.messaging;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.openuss.security.User;

/**
 * @see org.openuss.messaging.MessageService
 */
public class MessageServiceImpl extends MessageServiceBase {

	/**
	 * @see org.openuss.messaging.MessageService#sendMessage(java.lang.String,
	 *      java.lang.String, boolean, java.util.List)
	 */
	protected Long handleSendMessage(String sender, String subject, String text, boolean sms, List recipients) throws Exception {
		Validate.notEmpty(subject, "Parameter subject must not be empty");
		validateRecipients(recipients);
		// @todo implement protected org.openuss.messaging.MessageJob
		// handleSendMessage(java.lang.String subject, java.lang.String text,
		// boolean sms, java.util.List recipients)
		return null;
	}

	/**
	 * @see org.openuss.messaging.MessageService#sendMessage(java.lang.String,
	 *      java.lang.String, java.util.Map, java.util.List)
	 */
	protected Long handleSendMessage(String sender, String subject, String templateName, Map parameters, List recipients) throws Exception {
		Validate.notEmpty(subject, "Parameter subject must not be empty");
		Validate.notEmpty(templateName,"Parameter templateName must not be empty");
		validateRecipients(recipients);
		
		MessageJob job = MessageJob.Factory.newInstance();
		job.setSendAsSms(false);
		job.addRecipients(recipients);
		
		Message message = TemplateMessage.Factory.newInstance();
		message.setSenderName(sender);
		
		return null;
	}

	private void validateRecipients(List recipients) {
		Validate.notNull(recipients,"Parameter recipient must not be empty");
		Validate.allElementsOfType(recipients, org.openuss.security.User.class,"Parameter recipient must only contain User objects.");
	}

	@Override
	protected JobState handleGetJobState(Long messageId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}