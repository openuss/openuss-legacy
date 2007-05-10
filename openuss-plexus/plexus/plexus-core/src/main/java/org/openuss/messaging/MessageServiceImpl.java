// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.messaging;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;

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
		
		TextMessage message = TextMessage.Factory.newInstance();
		message.setSenderName(sender);
		message.setSubject(subject);
		message.setText(text);
		
		MessageJob job = MessageJob.Factory.newInstance();
		job.setState(JobState.INQUEUE);
		job.setSendAsSms(sms);
		job.addRecipients(recipients);
		job.setMessage(message);
		
		getMessageJobDao().create(job);
		
		return job.getId();
	}

	/**
	 * @see org.openuss.messaging.MessageService#sendMessage(java.lang.String,
	 *      java.lang.String, java.util.Map, java.util.List)
	 */
	protected Long handleSendMessage(String sender, String subject, String templateName, Map parameters, List recipients) throws Exception {
		Validate.notEmpty(subject, "Parameter subject must not be empty");
		Validate.notEmpty(templateName,"Parameter templateName must not be empty");

		validateRecipients(recipients);
		
		TemplateMessage message = TemplateMessage.Factory.newInstance();
		message.setSenderName(sender);
		message.setSubject(subject);
		message.setTemplate(templateName);
		message.addParameters(parameters);

		MessageJob job = MessageJob.Factory.newInstance();
		job.setState(JobState.INQUEUE);
		job.setSendAsSms(false);
		job.addRecipients(recipients);
		job.setMessage(message);
		getMessageJobDao().create(job);
		
		return job.getId();
	}

	private void validateRecipients(List recipients) {
		Validate.notNull(recipients,"Parameter recipient must not be empty");
		Validate.allElementsOfType(recipients, org.openuss.security.User.class,"Parameter recipient must only contain User objects.");
	}

	@Override
	protected JobInfo handleGetJobState(Long messageId) throws Exception {
		Validate.notNull(messageId, "Parameter messageId must not be null.");
		return (JobInfo) getMessageJobDao().load(MessageJobDao.TRANSFORM_JOBINFO, messageId );
	}

}