package org.openuss.messaging;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.Address;
import javax.mail.SendFailedException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.openuss.commands.AbstractDomainCommand;
import org.openuss.commands.CommandApplicationService;
import org.openuss.commands.CommandService;
import org.openuss.commands.DomainCommand;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * MessageSendingCommand
 * 
 * @author Ingo Dueppe
 */
public class MessageSendingCommand extends AbstractDomainCommand implements DomainCommand, ApplicationContextAware {

	private static final Logger logger = Logger.getLogger(MessageSendingCommand.class);

	private MessageJobDao messageJobDao;
	
	private JavaMailSender mailSender;
	
	private CommandService commandService;

	private ApplicationContext applicationContext;
	
	public void execute() throws Exception {
		if (getDomainObject() != null) {
			MessageJob job = messageJobDao.load(getDomainObject().getId());
			if (job != null && job.getState() == JobState.INQUEUE) {
				processMessageJob(job);
			}
		}
	}

	private void processMessageJob(MessageJob job) {
		List<MessagePreparator> messages = new ArrayList<MessagePreparator>();
		for (Recipient recipient : job.getRecipients()) {
			if (job.getMessage() instanceof TextMessage) {
				TextMessagePreparator preparator = (TextMessagePreparator) applicationContext.getBean("textMessagePreparator");
				preparator.setTextMessage((TextMessage) job.getMessage());
				preparator.setRecipient(recipient);
				messages.add(preparator);
			} else if (job.getMessage() instanceof TemplateMessage) {
				TemplateMessagePreparator preparator = (TemplateMessagePreparator) applicationContext.getBean("templateMessagePreparator");
				preparator.setTemplateMessage((TemplateMessage) job.getMessage());
				preparator.setRecipient(recipient);
				messages.add(preparator);
			}
			if (job.isSendAsSms() && recipient.hasSmsNotification()) {
				SmsMessagePreparator preparator = (SmsMessagePreparator) applicationContext.getBean("smsMessagePreparator");
				preparator.setMessage(job.getMessage());
				preparator.setRecipient(recipient);
				messages.add(preparator);
			}
		}
		try {
			mailSender.send((MimeMessagePreparator[]) messages.toArray(new MimeMessagePreparator[messages.size()]));
			updateRecipientState(job, null);
			job.setState(JobState.DONE);
		} catch (MailSendException mse) {
			if (mse.getCause() != null && mse.getCause().getCause() instanceof ConnectException) {
				defineRetryCommand(job, mse);
				throw mse;
			} else {
				logger.error(mse.getCause());
				if (mse.getCause() == null) {
					// some recipients cause an error but the job is done
					updateRecipientState(job, extractInvalidEMails(mse));
					job.setState(JobState.DONE);
				} else {
					job.setState(JobState.ERROR);
					throw mse;
				}
			}
		} finally {
			messageJobDao.update(job);
		}
	}

	/**
	 * A mail server connection error occurred, setting retry command.
	 * @param job
	 * @param mse
	 */
	private void defineRetryCommand(MessageJob job, MailSendException mse) {
		logger.info("setting retry command for job "+job.getId());
		logger.debug("mail sending error", mse);
		try {
			getCommandService().createOnceCommand(job, "messageSendingCommmand", DateUtils.addMinutes(new Date(), 1), "retry...");
		} catch (CommandApplicationService e) {
			logger.error(e);
		}
	}

	/**
	 * Updates the recipient state to send or if the recipient email address is
	 * in the invalid address set.
	 * 
	 * @param job -
	 *            message job instance
	 * @param invalidAddresses -
	 *            set of invalid email addresses or null
	 */
	private void updateRecipientState(MessageJob job, Set<String> invalidAddresses) {
		for (Recipient recipient : job.getRecipients()) {
			if (invalidAddresses != null && invalidAddresses.contains(recipient.getEmail())) {
				logger.debug("email of recipient " + recipient.getEmail() + " is invalid");
				recipient.setState(SendState.ERROR);
			} else {
				logger.debug("email to recipient " + recipient.getEmail() + " is send");
				recipient.setState(SendState.SEND);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Set<String> extractInvalidEMails(MailSendException mse) {
		Set<String> invalidAddresses = new HashSet<String>();
		for (SendFailedException sfe : (Collection<SendFailedException>) mse.getFailedMessages().values()) {
			logger.error(sfe);
			if (!ArrayUtils.isEmpty(sfe.getInvalidAddresses())) {
				for (Address address : sfe.getInvalidAddresses()) {
					invalidAddresses.add(address.toString());
				}
			}
		}
		return invalidAddresses;
	}

	public MessageJobDao getMessageJobDao() {
		return messageJobDao;
	}

	public void setMessageJobDao(MessageJobDao messageJobDao) {
		this.messageJobDao = messageJobDao;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public CommandService getCommandService() {
		return commandService;
	}

	public void setCommandService(CommandService commandService) {
		this.commandService = commandService;
	}
}
