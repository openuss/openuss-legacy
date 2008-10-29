package org.openuss.messaging;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * @author Ingo Dueppe
 */
public interface MessageJob extends org.openuss.foundation.DomainObject {

	public Long getId();

	public void setId(Long id);

	public Date getCreated();

	public void setCreated(Date created);

	public boolean isSendAsSms();

	public void setSendAsSms(boolean sendAsSms);

	public org.openuss.messaging.JobState getState();

	public void setState(org.openuss.messaging.JobState state);

	public Set<org.openuss.messaging.Recipient> getRecipients();

	public void setRecipients(Set<org.openuss.messaging.Recipient> recipients);

	public org.openuss.messaging.Message getMessage();

	public void setMessage(org.openuss.messaging.Message message);

	/**
	 * Number of mails that are already send
	 */
	public int getSend();

	/**
	 * Number of mails that produces an error during sending
	 */
	public int getError();

	public int getToSend();

	public void addRecipient(org.openuss.security.User user);

	public void addRecipients(Collection users);

	public void addRecipient(String email, String locale);

}