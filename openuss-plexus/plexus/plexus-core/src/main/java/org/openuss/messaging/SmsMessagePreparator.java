/**
 * 
 */
package org.openuss.messaging;


import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * SmsPreparator for sms messages
 * 
 * @author Ingo Dueppe
 */
public class SmsMessagePreparator extends MessagePreparator {

	private Message message;

	public void prepare(MimeMessage mimeMessage) throws Exception {
		MimeMessageHelper msg = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		msg.setFrom(fromAddress, message.getSenderName());
		msg.setTo(recipient.getSms());
		msg.setSubject(message.getSubject());
		msg.setText(message.getSubject());
		if (sendDate != null) {
			msg.setSentDate(sendDate);
		}
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Message getMessage() {
		return message;
	}


}