package org.openuss.messaging;

import java.util.Date;

import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * 
 * @author Ingo Dueppe
 */
public abstract class MessagePreparator implements MimeMessagePreparator {

	protected Recipient recipient;
	protected Date sendDate;
	protected String fromAddress;
	

	public Recipient getRecipient() {
		return recipient;
	}

	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

}
