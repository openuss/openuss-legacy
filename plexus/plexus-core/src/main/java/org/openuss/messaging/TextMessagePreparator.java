/**
 * 
 */
package org.openuss.messaging;


import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * MessagePreparator for text messages
 * 
 * @author Ingo Dueppe
 */
public class TextMessagePreparator extends MessagePreparator {

	private TextMessage textMessage;

	public void prepare(MimeMessage mimeMessage) throws Exception {
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		message.setFrom(fromAddress, textMessage.getSenderName());
		message.setTo(recipient.getEmail());
		message.setSubject(textMessage.getSubject());
		message.setText(textMessage.getText(), true);
		if (sendDate != null) {
			message.setSentDate(sendDate);
		}
	}

	public TextMessage getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(TextMessage textMessage) {
		this.textMessage = textMessage;
	}

}