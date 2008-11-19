package org.openuss.chat;

import org.apache.commons.lang.Validate;

/**
 * @see org.openuss.chat.ChatRoom
 */
public class ChatRoomImpl extends ChatRoomBase implements ChatRoom {
	
	/** The serial version UID of this class. Needed for serialization. */
	private static final long serialVersionUID = -8060974264066747959L;

	@Override
	public void add(ChatMessage message) {
		Validate.notNull(message, "Parameter message must not be null!");
		getMessages().add(message);
		message.setRoom(this);
	}

	@Override
	public void add(ChatUser user) {
		getChatUsers().add(user);
	}

	@Override
	public void remove(ChatUser user) {
		getChatUsers().remove(user);
	}

}