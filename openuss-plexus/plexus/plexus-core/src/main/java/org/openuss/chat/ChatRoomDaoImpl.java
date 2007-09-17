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
		
	}

	/**
	 * @see org.openuss.chat.ChatRoomDao#toChatRoomInfo(org.openuss.chat.ChatRoom)
	 */
	public org.openuss.chat.ChatRoomInfo toChatRoomInfo(final org.openuss.chat.ChatRoom entity) {
		// @todo verify behavior of toChatRoomInfo
		return super.toChatRoomInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private org.openuss.chat.ChatRoom loadChatRoomFromChatRoomInfo(org.openuss.chat.ChatRoomInfo chatRoomInfo) {
		// @todo implement loadChatRoomFromChatRoomInfo
		throw new java.lang.UnsupportedOperationException(
				"org.openuss.chat.loadChatRoomFromChatRoomInfo(org.openuss.chat.ChatRoomInfo) not yet implemented.");

		/*
		 * A typical implementation looks like this: org.openuss.chat.ChatRoom
		 * chatRoom = this.load(chatRoomInfo.getId()); if (chatRoom == null) {
		 * chatRoom = org.openuss.chat.ChatRoom.Factory.newInstance(); } return
		 * chatRoom;
		 */
	}

	/**
	 * @see org.openuss.chat.ChatRoomDao#chatRoomInfoToEntity(org.openuss.chat.ChatRoomInfo)
	 */
	public org.openuss.chat.ChatRoom chatRoomInfoToEntity(org.openuss.chat.ChatRoomInfo chatRoomInfo) {
		// @todo verify behavior of chatRoomInfoToEntity
		org.openuss.chat.ChatRoom entity = this.loadChatRoomFromChatRoomInfo(chatRoomInfo);
		this.chatRoomInfoToEntity(chatRoomInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.chat.ChatRoomDao#chatRoomInfoToEntity(org.openuss.chat.ChatRoomInfo,
	 *      org.openuss.chat.ChatRoom)
	 */
	public void chatRoomInfoToEntity(org.openuss.chat.ChatRoomInfo sourceVO, org.openuss.chat.ChatRoom targetEntity,
			boolean copyIfNull) {
		// @todo verify behavior of chatRoomInfoToEntity
		super.chatRoomInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}