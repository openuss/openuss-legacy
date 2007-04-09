package org.openuss.web.chat;

/**
 * Message for the AJAX chat introduced in the Portlets Project of Herbie
 * @author Daniel Schulz
 * @author Pavel Saratchev
 * @author Ingo Düppe
 */
public class Message{
	
	/**
	 * sender
	 */
	private ChatUser sender;

	/**
	 * message body
	 */
	private String content;

	/**
	 * time of creation
	 */
	private long creationTime;
	
	/**
	 * constructor
	 */
	public Message(ChatUser sender, String content) {
		this.sender = sender;
		this.content = content;
		creationTime = System.currentTimeMillis();
	}

	/**
	 * @return sender
	 */
	public ChatUser getSender() {
		return sender;
	}

	/**
	 * @return message body
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * @return time of creation in milliseconds
	 */
	public long getCreationTime() {
		return creationTime;
	}
	
}
