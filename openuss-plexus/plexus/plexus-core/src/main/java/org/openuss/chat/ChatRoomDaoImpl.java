// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.chat;

/**
 * @see org.openuss.chat.ChatRoom
 */
public class ChatRoomDaoImpl extends ChatRoomDaoBase {

	/**
	 * @see org.openuss.chat.ChatRoomDao#toChatRoomInfo(org.openuss.chat.ChatRoom,
	 *      org.openuss.chat.ChatRoomInfo)
	 */
	public void toChatRoomInfo(ChatRoom sourceEntity, ChatRoomInfo targetVO) {
		super.toChatRoomInfo(sourceEntity, targetVO);
		targetVO.setOnlineUsers(sourceEntity.getChatUsers().size());
		targetVO.setMessages(sourceEntity.getMessages().size());
	}

	/**
	 * @see org.openuss.chat.ChatRoomDao#toChatRoomInfo(org.openuss.chat.ChatRoom)
	 */
	public ChatRoomInfo toChatRoomInfo(final ChatRoom entity) {
		return super.toChatRoomInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private ChatRoom loadChatRoomFromChatRoomInfo(ChatRoomInfo chatRoomInfo) {
		ChatRoom chatRoom = this.load(chatRoomInfo.getId());
		if (chatRoom == null) {
			chatRoom = new ChatRoomImpl();
		}
		return chatRoom;

	}

	/**
	 * @see org.openuss.chat.ChatRoomDao#chatRoomInfoToEntity(org.openuss.chat.ChatRoomInfo)
	 */
	public ChatRoom chatRoomInfoToEntity(ChatRoomInfo chatRoomInfo) {
		ChatRoom entity = this.loadChatRoomFromChatRoomInfo(chatRoomInfo);
		this.chatRoomInfoToEntity(chatRoomInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.chat.ChatRoomDao#chatRoomInfoToEntity(org.openuss.chat.ChatRoomInfo,
	 *      org.openuss.chat.ChatRoom)
	 */
	public void chatRoomInfoToEntity(ChatRoomInfo sourceVO, ChatRoom targetEntity, boolean copyIfNull) {
		super.chatRoomInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}
}