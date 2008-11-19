// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.chat;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

/**
 * @see org.openuss.chat.ChatMessage
 * @author Ingo Düppe
 */
public class ChatMessageDaoImpl extends ChatMessageDaoBase {

	@Override
	protected Long handleLastMessageId(final Long roomId) throws Exception {
		final String hqlSelect = "select max(m.id) from org.openuss.chat.ChatMessage as m where m.room.id = :roomId";
		return (Long) getHibernateTemplate().execute(new org.springframework.orm.hibernate3.HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session session) throws HibernateException {
				List<?> results = session.createQuery(hqlSelect).setLong("roomId", roomId).list();
				Long msgId;
				if (results.size() > 0) {
					msgId = (Long) results.get(0);
				} else {
					msgId = 0L;
				}
				logger.debug("last "+msgId+" records.");
				return msgId;
			}
		}, true);
		
	}

	/**
	 * @see org.openuss.chat.ChatMessageDao#findByRoomAndAfter(int,
	 *      java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<?> findByRoomAfter(final int transform, final Long roomId, final Long messageId) {
		return findByRoomAfter(
				transform,
				"from org.openuss.chat.ChatMessage as chatMessage where chatMessage.room.id = ? and chatMessage.id > ?",
				roomId, messageId);
	}

	/**
	 * @see org.openuss.chat.ChatMessageDao#findByRoom(int, java.lang.Long)
	 */
	@Override
	public List<?> findByRoom(final int transform, final Long roomId) {
		return findByRoom(transform,
				"from org.openuss.chat.ChatMessage as chatMessage where chatMessage.room.id = ? order by id", roomId);
	}

	@Override
	public List<?> findByRoomSince(int transform, Long roomId, Date since) {
		return findByRoomSince(
				transform,
				"from org.openuss.chat.ChatMessage as chatMessage where chatMessage.room.id = ? and chatMessage.time > ?",
				roomId, since);
	}

	/**
	 * @see org.openuss.chat.ChatMessageDao#toChatMessageInfo(org.openuss.chat.ChatMessage,
	 *      org.openuss.chat.ChatMessageInfo)
	 */
	public void toChatMessageInfo(ChatMessage sourceEntity, ChatMessageInfo targetVO) {
		super.toChatMessageInfo(sourceEntity, targetVO);
		targetVO.setDisplayName(sourceEntity.getSender().getDisplayName());
	}

	/**
	 * @see org.openuss.chat.ChatMessageDao#toChatMessageInfo(org.openuss.chat.ChatMessage)
	 */
	public ChatMessageInfo toChatMessageInfo(final ChatMessage entity) {
		return super.toChatMessageInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private ChatMessage loadChatMessageFromChatMessageInfo(ChatMessageInfo chatMessageInfo) {
		ChatMessage chatMessage = load(chatMessageInfo.getId());
		if (chatMessage == null) {
			chatMessage = new ChatMessageImpl();
		}
		return chatMessage;
	}

	/**
	 * @see org.openuss.chat.ChatMessageDao#chatMessageInfoToEntity(org.openuss.chat.ChatMessageInfo)
	 */
	public ChatMessage chatMessageInfoToEntity(ChatMessageInfo chatMessageInfo) {
		ChatMessage entity = this.loadChatMessageFromChatMessageInfo(chatMessageInfo);
		this.chatMessageInfoToEntity(chatMessageInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.chat.ChatMessageDao#chatMessageInfoToEntity(org.openuss.chat.ChatMessageInfo,
	 *      org.openuss.chat.ChatMessage)
	 */
	public void chatMessageInfoToEntity(ChatMessageInfo sourceVO, ChatMessage targetEntity, boolean copyIfNull) {
		super.chatMessageInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}
}