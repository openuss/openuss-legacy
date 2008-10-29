package org.openuss.messaging;

import java.util.Date;

import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * 
 * @author Ingo Dueppe
 */
public abstract class MessagePreparator implements InitializingBean, MimeMessagePreparator {

	protected Recipient recipient;
	protected Date sendDate;
	protected String fromAddress;

	private SystemService systemService;

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public void afterPropertiesSet() throws Exception {
		if (systemService != null) {
			fromAddress = systemService.getProperty(SystemProperties.MAIL_FROM_ADDRESS).getValue();
		}
	}

	public Recipient getRecipient() {
		return recipient;
	}

	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}

	public Date getSendDate() {
		return sendDate == null ? null : new Date(sendDate.getTime());
	}

	public void setSendDate(Date sendDate) {
		if (sendDate != null) {
			this.sendDate = new Date(sendDate.getTime());
		}
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

}
