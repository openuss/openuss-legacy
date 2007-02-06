package org.openuss.framework.mail;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * @author Ingo Dueppe 
 **/
public class MailIntegrationTest extends AbstractDependencyInjectionSpringContextTests {	

	private static final String PASSWORD_TEMPLATE = "password";
	private static final String VERIFICATION_TEMPLATE = "verification";
	private MailEngine mailEngine;
	private MimeMessage message;
	private MimeMessageHelper helper;
	
	private MimeMessageHelper mimeMessageHelper;
	

	private Map model;
	
	
	public void testInjection() {
		assertNotNull(mailEngine);
	}
	
	@Override
	protected void onSetUp() throws Exception {
		message = mailEngine.createMimeMessage();
		helper = new MimeMessageHelper(message, false);
		helper.setTo("plexus@openuss-plexus.com");
		helper.setFrom("no-reply@openuss.org");
		model = new HashMap();
		model.put("username", "idueppe");
		model.put("link","http://teamopenuss.uni-muenster.de");
	}

	public void testSendSimpleMessage() {
		try {
			helper.setSubject("Simple plain text message");
			helper.setText("This is a simple plain text message",false);
			mailEngine.send(message);
		} catch (MessagingException e) {
			fail();
		}
	}
	
	public void testSendLocalizedPasswordMessage() {
		try {
			helper.setSubject("OpenUSS Kennwort Assistent");
			mailEngine.sendMessage(message, PASSWORD_TEMPLATE, model, "de");
		} catch (MessagingException e) {
			fail();
		}
	}

	public void testSendNotExistingLocalizedPasswordMessage() {
		try {
			helper.setSubject("OpenUSS Kennwort Assistent (BODY SHOULD BE IN ENGLISH)");
			mailEngine.sendMessage(message, PASSWORD_TEMPLATE, model, "DOESNOTEXIST");
		} catch (MessagingException e) {
			fail();
		}
	}

	public void testSendLocalizedRegistrationMessage() {
		try {
			helper.setSubject("OpenUSS Kennwort Assistent");
			mailEngine.sendMessage(message, VERIFICATION_TEMPLATE , model, "de");
		} catch (MessagingException e) {
			fail();
		}
	}
	
	public void testSendNotExistingLocalizedRegistrationMessage() {
		try {
			helper.setSubject("OpenUSS Kennwort Assistent (BODY SHOULD BE IN ENGLISH)");
			mailEngine.sendMessage(message, VERIFICATION_TEMPLATE, model, "DOESNOTEXIST");
		} catch (MessagingException e) {
			fail();
		}
	}
	
	public void testMimeMessage() {
		assertNotNull(mimeMessageHelper);
		assertNotNull(mimeMessageHelper.getMimeMessage());
		try {
			mimeMessageHelper.setTo("plexus@openuss-plexus.com");
			mimeMessageHelper.setSubject("testcase mime message");
			mailEngine.sendMessage(mimeMessageHelper.getMimeMessage(), VERIFICATION_TEMPLATE, model, "de");
		} catch (MessagingException e) {
			fail();
		}
	}
	
	
	public MailEngine getMailEngine() {
		return mailEngine;
	}
	
	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	protected String[] getConfigLocations() {
		return new String[] { 
				"classpath*:applicationContext-services.xml"
		};
	}

	public MimeMessageHelper getMimeMessageHelper() {
		return mimeMessageHelper;
	}

	public void setMimeMessageHelper(MimeMessageHelper mimeMessageHelper) {
		this.mimeMessageHelper = mimeMessageHelper;
	}

}