// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.openuss.foundation.DomainObject;
import org.openuss.security.UserInfo;

/**
 * @see org.openuss.chat.ChatService
 */
public class ChatServiceImpl extends ChatServiceBase {

	private static final String CHAT_USER_LEAVE_MESSAGE = "chatservice_user_leave_message";
	
	private static final String CHAT_USER_ENTER_MESSAGE = "chatservice_user_enter_message";
	
	private static final String CHAT_ROOM_CANNOT_DELETE_ERROR = "chatservice_room_contains_user_cannot_deleted_message";

	/**
	 * @see org.openuss.chat.ChatService#createRoom(org.openuss.foundation.DomainObject,
	 *      java.lang.String, java.lang.String)
	 */
	protected ChatRoomInfo handleCreateRoom(DomainObject domain, String name, String topic) throws Exception {
		ChatRoom chatRoom = new ChatRoomImpl();
		chatRoom.setDomainId(domain.getId());
		chatRoom.setName(name);
		chatRoom.setTopic(topic);
		chatRoom.setCreated(new Date());
		chatRoom.setOwner(retrieveCurrentChatUser());
		
		getChatRoomDao().create(chatRoom);
		
		return getChatRoomDao().toChatRoomInfo(chatRoom);
	}
	
	private ChatUser retrieveCurrentChatUser() {
		UserInfo user = getSecurityService().getCurrentUser();
		if (user == null || user.getId()==null) {
			throw new IllegalStateException("No user is logined.");
		}
		ChatUser chatUser = getChatUserDao().load(user.getId());
		if (chatUser == null) {
			chatUser = new ChatUserImpl();
			chatUser.setId(user.getId());
			chatUser.setDisplayName(user.getDisplayName());
			chatUser.setEmail(user.getEmail());
			getChatUserDao().create(chatUser);
		} 
		return chatUser;
	}

	/**
	 * @see org.openuss.chat.ChatService#deleteRoom(java.lang.Long)
	 */
	protected void handleDeleteRoom(Long roomId) throws Exception {
		Validate.notNull(roomId, "Parameter roomId must not be null!");
		ChatRoom room = getChatRoomDao().load(roomId);
		if (!room.getChatUsers().isEmpty()) {
			throw new ChatRoomServiceException(CHAT_ROOM_CANNOT_DELETE_ERROR);
		} else {
			getChatRoomDao().remove(roomId);
		}
	}

	/**
	 * @see org.openuss.chat.ChatService#getRooms(org.openuss.foundation.DomainObject)
	 */
	@SuppressWarnings("unchecked")
	protected List<ChatRoomInfo> handleGetRooms(DomainObject domain) throws Exception {
		Validate.notNull(domain, "Parameter domain must not be null!");
		Validate.notNull(domain.getId(), "Parameter domain must contain an valid identifier!");
		return (List<ChatRoomInfo>) getChatRoomDao().findChatRoomByDomainId(ChatRoomDao.TRANSFORM_CHATROOMINFO, domain.getId());
	}

	/**
	 * @see org.openuss.chat.ChatService#enterRoom(java.lang.Long)
	 */
	protected void handleEnterRoom(Long roomId) throws java.lang.Exception {
		Validate.notNull(roomId, "Parameter roomId must not be null!");
		ChatUser user = retrieveCurrentChatUser();
		ChatRoom room = getChatRoomDao().load(roomId);
		room.getChatUsers().add(user);

		room.add(createChatMessage(CHAT_USER_ENTER_MESSAGE, true, room, user));
		getChatRoomDao().update(room);
	}

	private ChatMessage createChatMessage(String msg, boolean i18n, ChatRoom room, ChatUser user) {
		ChatMessage chatMessage = new ChatMessageImpl();
		chatMessage.setText(msg);
		chatMessage.setTime(new Date());
		chatMessage.setI18n(i18n);
		chatMessage.setRoom(room);
		chatMessage.setSender(user);
		return chatMessage;
	}

	/**
	 * @see org.openuss.chat.ChatService#leaveRoom(java.lang.Long)
	 */
	protected void handleLeaveRoom(Long roomId) throws java.lang.Exception {
		Validate.notNull(roomId, "Parameter roomId must not be null!");
		ChatUser user = retrieveCurrentChatUser();
		ChatRoom room = getChatRoomDao().load(roomId);
		room.getChatUsers().remove(user);
		
		room.add(createChatMessage(CHAT_USER_LEAVE_MESSAGE, true , room, user));
		
		getChatRoomDao().update(room);
	}

	/**
	 * @see org.openuss.chat.ChatService#getMessages(java.lang.Long)
	 */
	protected List<?> handleGetMessages(Long roomId) throws Exception {
		Validate.notNull(roomId, "Parameter roomId must not be null!");
		return getChatMessageDao().findByRoom(ChatMessageDao.TRANSFORM_CHATMESSAGEINFO,roomId);
	}

	/**
	 * @see org.openuss.chat.ChatService#getChatUsers(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	protected List<?> handleGetChatUsers(Long roomId) throws Exception {
		Validate.notNull(roomId, "Parameter roomId must not be null!");
		ChatRoom room = getChatRoomDao().load(roomId);
		List<?> users = new ArrayList(room.getChatUsers());
		getChatUserDao().toChatUserInfoCollection(users);
		return users;
	}

	/**
	 * @see org.openuss.chat.ChatService#getRecentMessages(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected List<?> handleGetRecentMessages(Long roomId, Long messageId)	throws Exception {
		Validate.notNull(roomId, "Parameter roomId must not be null!");
		Validate.notNull(messageId, "Parameter messageId must not be null!");
		return getChatMessageDao().findByRoomAfter(ChatMessageDao.TRANSFORM_CHATMESSAGEINFO,roomId,messageId);
	}

	@Override
	protected List<?> handleGetRecentMessages(Long roomId, Date since) throws Exception {
		Validate.notNull(roomId, "Parameter roomId must not be null");
		Validate.notNull(since, "Parameter since must not be null");
		return getChatMessageDao().findByRoomSince(ChatMessageDao.TRANSFORM_CHATMESSAGEINFO, roomId, since);
	}

	/**
	 * @see org.openuss.chat.ChatService#sendMessage(java.lang.Long,
	 *      java.lang.String)
	 */
	protected void handleSendMessage(Long roomId, String text) throws Exception {
		Validate.notNull(roomId, "Parameter roomId must not be null!");
		Validate.notNull(text, "Parameter text must not be null!");
		
		ChatRoom room = getChatRoomDao().load(roomId);
		ChatUser user = retrieveCurrentChatUser();
		
		ChatMessage message = new ChatMessageImpl();
		message.setSender(user);
		message.setText(text);
		message.setTime(new Date());
		room.add(message);
		
		getChatMessageDao().create(message);
	}

	@Override
	protected ChatRoomInfo handleGetRoom(Long roomId) throws Exception {
		Validate.notNull(roomId, "Parameter roomId must not be null");
		return (ChatRoomInfo) getChatRoomDao().load(ChatRoomDao.TRANSFORM_CHATROOMINFO, roomId);
	}

	@Override
	protected Long handleGetLastMessage(Long roomId) throws Exception {
		Validate.notNull(roomId, "Parameter roomId must not be null");
		return getChatMessageDao().lastMessageId(roomId);
	}


}