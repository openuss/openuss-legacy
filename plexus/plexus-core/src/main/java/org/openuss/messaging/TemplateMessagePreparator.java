package org.openuss.messaging;

import java.util.Locale;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * 
 * @author Ingo Dueppe
 */
public class TemplateMessagePreparator extends MessagePreparator implements MessageSourceAware {

	private MessageSource messageSource;

	private TemplateMessage templateMessage;

	private VelocityEngine velocityEngine;
	
	private static final String TEMPLATE_PREFIX = "templates/emails/";
	private static final String TEMPLATE_SUFFIX = ".vsl";
	private static final String LOCALE_SEPARATOR = "_";

	public void prepare(MimeMessage mimeMessage) throws Exception {
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		message.setTo(recipient.getEmail());
		message.setSubject(localizedSubject());
		message.setFrom(fromAddress, localeSenderName());

		String text = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, 
				determineMostFittingTemplate(localizedTemplate()),
				templateMessage.getParameterMap());
		message.setText(text, true);

		if (sendDate != null) {
			message.setSentDate(sendDate);
		}
	}
	
	private String localeSenderName() {
		return messageSource.getMessage(templateMessage.getSenderName(), null, new Locale(recipient.getLocale()));
	}
	
	private String determineMostFittingTemplate(String template){
		if (velocityEngine.templateExists(template)) return template;
		while (!velocityEngine.templateExists(template))
			template = template.substring(0,template.lastIndexOf(LOCALE_SEPARATOR.charAt(0)))+TEMPLATE_SUFFIX;
		return template;
	}
	
	private String localizedSubject() {
		return messageSource.getMessage(
			templateMessage.getSubject(),
			null,
			templateMessage.getSubject(),
			new Locale(recipient.getLocale())
		);
	}

	private String localizedTemplate() {
		if (StringUtils.isNotBlank(recipient.getLocale())) {
			return TEMPLATE_PREFIX + templateMessage.getTemplate() + LOCALE_SEPARATOR + recipient.getLocale() + TEMPLATE_SUFFIX;
		} else {
			return TEMPLATE_PREFIX + templateMessage.getTemplate() + TEMPLATE_SUFFIX;
		}
	}

	public TemplateMessage getTemplateMessage() {
		return templateMessage;
	}

	public void setTemplateMessage(TemplateMessage templateMessage) {
		this.templateMessage = templateMessage;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
