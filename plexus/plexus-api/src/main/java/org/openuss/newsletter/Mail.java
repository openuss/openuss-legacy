package org.openuss.newsletter;

import java.util.Date;

import org.openuss.foundation.DomainObject;

/**
 * @author Ingo Dueppe 
 */
public interface Mail extends DomainObject {

	public Long getId();

	public void setId(Long id);

	public org.openuss.newsletter.MailingStatus getStatus();

	public void setStatus(MailingStatus status);

	public String getSubject();

	public void setSubject(String subject);

	public String getText();

	public void setText(String text);

	public boolean isSms();

	public void setSms(boolean sms);

	public Date getSendDate();

	public void setSendDate(Date sendDate);

	/**
	 * The id of the crone job of scheduled mailings. This id is needed to
	 * cancel mailings.
	 */
	public Long getCommandId();

	public void setCommandId(Long commandId);

	public Long getMessageId();

	public void setMessageId(Long messageId);

	public org.openuss.newsletter.Newsletter getNewsletter();

	public void setNewsletter(org.openuss.newsletter.Newsletter newsletter);

}