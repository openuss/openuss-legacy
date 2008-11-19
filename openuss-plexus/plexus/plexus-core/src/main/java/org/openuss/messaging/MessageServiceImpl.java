// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.messaging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.openuss.commands.CommandApplicationService;
import org.openuss.security.User;
import org.openuss.security.UserInfo;

/**
 * @see org.openuss.messaging.MessageService
 */
public class MessageServiceImpl extends MessageServiceBase {

	/**
	 * @see org.openuss.messaging.MessageService#sendMessage(java.lang.String,
	 *      java.lang.String, boolean, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Long handleSendMessage(String sender, String subject, String text, boolean sms, List recipients) throws Exception {
		Validate.notEmpty(subject, "Parameter subject must not be empty");
		validateRecipients(recipients);
		
		TextMessage message = new TextMessageImpl();
		message.setSenderName(sender);
		message.setSubject(subject);
		message.setText(text);
		
		MessageJob job = createMessageJob(sms, recipients, message);
		
		return job.getId();
	}

	private MessageJob createMessageJob(boolean sms, List<User> recipients, Message message) throws CommandApplicationService {
		MessageJob job = new MessageJobImpl();
		job.setState(JobState.INQUEUE);
		job.setSendAsSms(sms);
		job.addRecipients(recipients);
		job.setMessage(message);
		
		getMessageJobDao().create(job);
		getCommandService().createOnceCommand(job, "messageSendingCommmand", new Date(), null);
		return job;
	}

	/**
	 * @see org.openuss.messaging.MessageService#sendMessage(java.lang.String,
	 *      java.lang.String, java.util.Map, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	protected Long handleSendMessage(String sender, String subject, String templateName, Map parameters, List recipients) throws Exception {
		Validate.notEmpty(subject, "Parameter subject must not be empty");
		Validate.notEmpty(templateName,"Parameter templateName must not be empty");

		validateRecipients(recipients);
		
		TemplateMessage message = createMessage(sender, subject, templateName, parameters);

		MessageJob job = createMessageJob(false, recipients, message);
		
		return job.getId();
	}

	private TemplateMessage createMessage(String sender, String subject, String templateName, Map<String, Object> parameters) {
		TemplateMessage message = new TemplateMessageImpl();
		message.setSenderName(sender);
		message.setSubject(subject);
		message.setTemplate(templateName);
		message.addParameters(parameters);
		return message;
	}

	private void validateRecipients(List<User> recipients) {
		Validate.notNull(recipients,"Parameter recipient must not be empty");
		Validate.allElementsOfType(recipients, org.openuss.security.User.class,"Parameter recipient must only contain User objects.");
	}

	@Override
	protected JobInfo handleGetJobState(Long messageId) throws Exception {
		Validate.notNull(messageId, "Parameter messageId must not be null.");
		return (JobInfo) getMessageJobDao().load(MessageJobDao.TRANSFORM_JOBINFO, messageId );
	}

	@Override
	protected Long handleSendMessage(String senderName, String subject, String text, boolean sms, UserInfo recipient) throws Exception {
		return handleSendMessage(senderName, subject, text, sms, wrapRecipient(recipient));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Long handleSendMessage(String senderName, String subject, String templateName, Map parameters, UserInfo recipient) throws Exception {
		return handleSendMessage(senderName, subject, templateName, parameters, wrapRecipient(recipient));
	}

	private List<User> wrapRecipient(UserInfo recipient) {
		List<User> recipients = new ArrayList<User>();
		recipients.add(getSecurityService().getUserObject(recipient.getId()));
		return recipients;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Long handleSendMessage(String sender, String subject, String templateName, Map parameters, String email, String locale) throws Exception {
		Validate.notEmpty(sender, "Parameter sender must not be empty.");
		Validate.notEmpty(subject, "Parameter subject must not be empty.");
		Validate.notEmpty(templateName,"Parameter templateName must not be empty.");
		Validate.notEmpty(email, "Parameter email must not be empty.");
		Validate.notEmpty(locale, "Parameter locale must not be empty.");

		TemplateMessage message = createMessage(sender, subject, templateName, parameters);
		
		MessageJob job = new MessageJobImpl();
		job.setState(JobState.INQUEUE);
		job.addRecipient(email, locale);
		job.setMessage(message);
		
		getMessageJobDao().create(job);
		getCommandService().createOnceCommand(job, "messageSendingCommmand", new Date(), null);
		
		return job.getId();
	}

}