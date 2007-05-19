package org.openuss.framework.mail;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Class for sending e-mail messages 
 * 
 * @author Ingo Dueppe
 * @deprecated
 */
public class MailEngine {

	private static final Logger logger = Logger.getLogger(MailEngine.class);
	
	private static final String TEMPLATE_PREFIX = "templates/emails/";
	private static final String TEMPLATE_SUFFIX = ".vsl";
	private static final String LOCALE_SEPARATOR = "_";
	
    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;
    
    private String defaultFrom;

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    public JavaMailSender getMailSender() {
    	return mailSender;
    }
    
    public MimeMessage createMimeMessage() {
    	return mailSender.createMimeMessage();
    }

    /**
     * Send a simple message with pre-populated values.
     * @param msg
     */
    public void send(MimeMessage msg) {
        try {
            mailSender.send(msg);
        } catch (MailException ex) {
        	logger.error(ex);
        }
    }
    
    /**
     * Convenience method for sending messages with attachments.
     * 
     * @param emailAddresses
     * @param resource
     * @param bodyText
     * @param subject
     * @param attachmentName
     * @throws MessagingException
     * @author Ben Gill
     */
    public void sendMessage(String[] emailAddresses,
    						ClassPathResource resource, String bodyText,
                            String subject, String attachmentName)
    throws MessagingException {
        MimeMessage message =
            ((JavaMailSenderImpl) mailSender).createMimeMessage();

        // use the true flag to indicate you need a multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(emailAddresses);
        helper.setText(bodyText);
        helper.setSubject(subject);

        helper.addAttachment(attachmentName, resource);

        ((JavaMailSenderImpl) mailSender).send(message);
    }

    /**
     *<p>
     * Send a localized message based on an email template.
     * </p>
     * <p>
     * This method will add the default prefix of <code>templates/emails/</code> and the default suffix of <code>.vsl</code>.
     * <p/>
     * For Instance, a template name like <code>password</code> will be transformed to 
     * <code>templates/emails/password.vsl</code>
     * Send a message based on an email template
     * @param message an mimemessage
     * @param templateName
     * @param model
     * @throws MessagingException 
     */
    public void sendMessage(MimeMessage message, String templateName, Map model) throws MessagingException {
        String text = null;
        try {
        	templateName = TEMPLATE_PREFIX + templateName + TEMPLATE_SUFFIX;
            text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, model);
            MimeMessageHelper helper = new MimeMessageHelper(message ,false);
            helper.setText(text, true);
            if (message.getFrom() == null)
            	helper.setFrom(defaultFrom);
        } catch (VelocityException e) {
            logger.error(e);
            throw new MessagingException("Exception during template rendering.",e);
        }
        send(message);
    }
    
    /**
     * <p>
     * Send a localized message based on an email template.
     * </p>
     * <p>
     * This method will add the default prefix of <code>templates/emails/</code> and the default suffix of <code>.vsl</code>.
     * <p/>
     * For Instance, a template name like <code>password</code> will be transformed with a german locale to 
     * <code>templates/emails/password_de.vsl</code>
     * @param message
     * @param templateName without path and suffix 
     * @param model
     * @param locale
     * @throws MessagingException
     */
    public void sendMessage(MimeMessage message, String templateName, Map model, String locale) throws MessagingException {
    	if (locale != null) {
	    	// check if wether a localized version of the template exists or not 
	    	final ClassLoader cl = Thread.currentThread().getContextClassLoader();
	    	String templateUrl = TEMPLATE_PREFIX + templateName + LOCALE_SEPARATOR + locale + TEMPLATE_SUFFIX;
	    	if (cl.getResource(templateUrl) != null) {
	    		templateName = templateName + LOCALE_SEPARATOR + locale;
	    	}
    	}
   		sendMessage(message, templateName, model);
    }
    

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	/**
	 * Access the default from address.
	 * @return
	 */
	public String getDefaultFrom() {
		return defaultFrom;
	}

	/**
	 * Defines the default from adress
	 * @param defaultFrom
	 */
	public void setDefaultFrom(String defaultFrom) {
		this.defaultFrom = defaultFrom;
	}
}
