package org.openuss.chat;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.openuss.foundation.DomainObject;

/**
 * 
 * @author Ingo Dueppe
 * 
 */
public interface ChatRoom extends DomainObject {

	public Long getId();

	public void setId(Long id);

	public Long getDomainId();

	public void setDomainId(Long domainId);

	public String getName();

	public void setName(String name);

	public String getTopic();

	public void setTopic(String topic);

	public Date getCreated();

	public void setCreated(Date created);

	public Collection<ChatMessage> getMessages();

	public void setMessages(Collection<ChatMessage> messages);

	public Set<ChatUser> getChatUsers();

	public void setChatUsers(Set<ChatUser> chatUsers);

	public ChatUser getOwner();

	public void setOwner(ChatUser owner);

	public abstract void add(ChatMessage message);

	public abstract void add(ChatUser user);

	public abstract void remove(org.openuss.chat.ChatUser user);

}