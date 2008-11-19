package org.openuss.chat;

import java.io.Serializable;
import java.util.Date;

public class ChatRoomInfo implements Serializable {

	private static final long serialVersionUID = 253312577247635521L;

	public ChatRoomInfo() {
		this.id = null;
		this.domainId = null;
		this.name = null;
		this.topic = null;
		this.onlineUsers = 0;
		this.messages = 0;
		this.created = null;
	}

	public ChatRoomInfo(Long id, Long domainId, String name, String topic, int onlineUsers, int messages, Date created) {
		this.id = id;
		this.domainId = domainId;
		this.name = name;
		this.topic = topic;
		this.onlineUsers = onlineUsers;
		this.messages = messages;
		this.created = created;
	}

	/**
	 * Copies constructor from other ChatRoomInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public ChatRoomInfo(ChatRoomInfo otherBean) {
		this(otherBean.getId(), otherBean.getDomainId(), otherBean.getName(), otherBean.getTopic(), otherBean
				.getOnlineUsers(), otherBean.getMessages(), otherBean.getCreated());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(ChatRoomInfo otherBean) {
		this.setId(otherBean.getId());
		this.setDomainId(otherBean.getDomainId());
		this.setName(otherBean.getName());
		this.setTopic(otherBean.getTopic());
		this.setOnlineUsers(otherBean.getOnlineUsers());
		this.setMessages(otherBean.getMessages());
		this.setCreated(otherBean.getCreated());
	}

	private Long id;

	/**
     * 
     */
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long domainId;

	/**
     * 
     */
	public Long getDomainId() {
		return this.domainId;
	}

	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

	private String name;

	/**
     * 
     */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String topic;

	/**
     * 
     */
	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	private int onlineUsers;

	/**
     * 
     */
	public int getOnlineUsers() {
		return this.onlineUsers;
	}

	public void setOnlineUsers(int onlineUsers) {
		this.onlineUsers = onlineUsers;
	}

	private int messages;

	/**
     * 
     */
	public int getMessages() {
		return this.messages;
	}

	public void setMessages(int messages) {
		this.messages = messages;
	}

	private Date created;

	/**
     * 
     */
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * Returns <code>true</code> if the argument is an ChatRoomInfo instance and
	 * all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof ChatRoomInfo)) {
			return false;
		}
		final ChatRoomInfo that = (ChatRoomInfo) object;
		if (this.id == null || that.getId() == null || !this.id.equals(that.getId())) {
			return false;
		}

		return true;
	}

	/**
	 * Returns a hash code based on this entity's identifiers.
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode = 29 * hashCode + (id == null ? 0 : id.hashCode());

		return hashCode;
	}

}