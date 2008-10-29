package org.openuss.chat;

import java.util.Date;

import org.openuss.foundation.DomainObject;

/**
 * @author Ingo Dueppe
 */
public interface ChatMessage extends DomainObject {

	public Long getId();

	public void setId(Long id);

	/**
	 * Message text
	 */
	public String getText();

	public void setText(String text);

	/**
	 * Timestamp of the message
	 */
	public Date getTime();

	public void setTime(Date time);

	public boolean isI18n();

	public void setI18n(boolean i18n);

	public ChatRoom getRoom();

	public void setRoom(ChatRoom room);

	public ChatUser getSender();

	public void setSender(ChatUser sender);

}