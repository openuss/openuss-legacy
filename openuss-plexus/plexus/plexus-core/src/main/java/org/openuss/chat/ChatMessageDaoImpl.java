// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.chat;

import java.util.List;

/**
 * @see org.openuss.chat.ChatMessage
 */
public class ChatMessageDaoImpl extends ChatMessageDaoBase {

	/**
	 * @see org.openuss.chat.ChatMessageDao#findByRoomAndAfter(int,
	 *      java.lang.Long, java.lang.Long)
	 */
	@Override
	public List findByRoomAndAfter(final int transform, final Long roomId, final Long after) {
		return this
				.findByRoomAndAfter(
						transform,
						"from org.openuss.chat.ChatMessage as chatMessage where chatMessage.room.id = ? and chatMessage.id > ?",
						roomId, after);
	}

	/**
	 * @see org.openuss.chat.ChatMessageDao#findByRoom(int, java.lang.Long)
	 */
	public List findByRoom(final int transform, final Long roomId) {
		return this.findByRoom(transform,
				"from org.openuss.chat.ChatMessage as chatMessage where chatMessage.room.id = ? order by id", roomId);
	}

	/**
	 * @see org.openuss.chat.ChatMessageDao#toChatMessageInfo(org.openuss.chat.ChatMessage,
	 *      org.openuss.chat.ChatMessageInfo)
	 */
	public void toChatMessageInfo(ChatMessage sourceEntity, ChatMessageInfo targetVO) {
		// @todo verify behavior of toChatMessageInfo
		super.toChatMessageInfo(sourceEntity, targetVO);
	}

	/**
	 * @see org.openuss.chat.ChatMessageDao#toChatMessageInfo(org.openuss.chat.ChatMessage)
	 */
	public ChatMessageInfo toChatMessageInfo(final ChatMessage entity) {
		// @todo verify behavior of toChatMessageInfo
		return super.toChatMessageInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private ChatMessage loadChatMessageFromChatMessageInfo(ChatMessageInfo chatMessageInfo) {
		// @todo implement loadChatMessageFromChatMessageInfo
		throw new java.lang.UnsupportedOperationException(
				"org.openuss.chat.loadChatMessageFromChatMessageInfo(org.openuss.chat.ChatMessageInfo) not yet implemented.");

		/*
		 * A typical implementation looks like this:
		 * org.openuss.chat.ChatMessage chatMessage =
		 * this.load(chatMessageInfo.getId()); if (chatMessage == null) {
		 * chatMessage = org.openuss.chat.ChatMessage.Factory.newInstance(); }
		 * return chatMessage;
		 */
	}

	/**
	 * @see org.openuss.chat.ChatMessageDao#chatMessageInfoToEntity(org.openuss.chat.ChatMessageInfo)
	 */
	public ChatMessage chatMessageInfoToEntity(ChatMessageInfo chatMessageInfo) {
		// @todo verify behavior of chatMessageInfoToEntity
		ChatMessage entity = this.loadChatMessageFromChatMessageInfo(chatMessageInfo);
		this.chatMessageInfoToEntity(chatMessageInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.chat.ChatMessageDao#chatMessageInfoToEntity(org.openuss.chat.ChatMessageInfo,
	 *      org.openuss.chat.ChatMessage)
	 */
	public void chatMessageInfoToEntity(ChatMessageInfo sourceVO, ChatMessage targetEntity, boolean copyIfNull) {
		// @todo verify behavior of chatMessageInfoToEntity
		super.chatMessageInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}