// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.chat;

import java.util.ArrayList;
import java.util.List;

import org.openuss.security.User;

/**
 * @see org.openuss.chat.ChatService
 */
public class ChatServiceImpl
    extends org.openuss.chat.ChatServiceBase
{

	private ChatRoom generateRoom(Long domainId){
		ChatRoom room = ChatRoom.Factory.newInstance();
		room.setDomainId(domainId);
		//TODO check if roomId is needed 
		room.setRoomId(domainId);
		room.setDomainName(" ");
		room.setCurrentTopic(" ");		
		return room;
	}
	
    /**
     * @see org.openuss.chat.ChatService#getMessages(java.lang.Long, java.lang.Long)
     */
    protected java.util.List handleGetMessages(java.lang.Long domainId, java.lang.Long last)
        throws java.lang.Exception
    {
    	//FIXME use last and cleanuptime
    	List messages = new ArrayList<ChatMessageInfo>();
    	ChatRoom room = getRoomEntity(domainId);
    	if (room.getMessages() == null) {
    		return messages;
    	}
    	List<?> messageList = room.getMessages();
    	getChatMessageDao().toChatMessageInfoCollection(messageList);
    	update();
    	return (List<ChatMessageInfo>)messageList;
    }

    /**
     * @see org.openuss.chat.ChatService#sendMessage(org.openuss.chat.ChatMessageInfo, java.lang.Long)
     */
    protected void handleSendMessage(org.openuss.chat.ChatMessageInfo message, java.lang.Long domainId)
        throws java.lang.Exception
    {
    	ChatRoom room = getRoomEntity(domainId);
    	List<ChatMessage> messages = room.getMessages();
    	if (messages == null){
    		messages = new ArrayList<ChatMessage>();
    	}
    	ChatMessage messageEntity = getChatMessageDao().chatMessageInfoToEntity(message);
    	ChatUser sender = getChatUserDao().findChatUserByUsername(getSecurityService().getCurrentUser().getUsername());
    	if (sender == null){
    		sender = ChatUser.Factory.newInstance();
    		sender.setLastActive(message.getTime().getTime());
    		sender.setUsername(getSecurityService().getCurrentUser().getUsername());
    		sender.setDisplayName(getSecurityService().getCurrentUser().getDisplayName());
    		getChatUserDao().create(sender);
    		if (!room.getChatUsers().contains(sender)){
    			List<ChatUser> users = room.getChatUsers();
    			users.add(sender);    			
    			room.setChatUsers(users);
    			getChatRoomDao().update(room);
    		}
    	}
    	messageEntity.setSender(sender);
    	messageEntity.setRoom(room);
    	getChatMessageDao().create(messageEntity);
    	messages.add(messageEntity);
    	room.setMessages(messages);
    	getChatRoomDao().update(room);    	
    }

    /**
     * @see org.openuss.chat.ChatService#getChatUsers(java.lang.Long)
     */
    protected java.util.List handleGetChatUsers(java.lang.Long domainId)
        throws java.lang.Exception
    {
    	ChatRoom room = getRoomEntity(domainId);
    	List<ChatUser> users = room.getChatUsers();
    	List<ChatUserInfo> usersVO = new ArrayList<ChatUserInfo>();
    	if (users == null){
    		return new ArrayList<ChatUserInfo>();
    	}
    	for (ChatUser chatUser : users) {
    		usersVO.add(getChatUserDao().toChatUserInfo(chatUser));
    	}
    	return usersVO;
    }

	private ChatRoom getRoomEntity(java.lang.Long domainId) {
		ChatRoom room = getChatRoomDao().findChatRoomById(domainId);
    	if (room==null) {
    		room = generateRoom(domainId);
    		getChatRoomDao().create(room);
    	}
		return room;
	}

	private ChatUser getUser(){		
		User current = getSecurityService().getCurrentUser();
		ChatUser user = getChatUserDao().findChatUserByUsername(current.getUsername());
    	if (user == null){
    		user = ChatUser.Factory.newInstance();
    		user.setLastActive(System.currentTimeMillis());
    		user.setUsername(current.getUsername());
    		user.setDisplayName(current.getDisplayName());
    		getChatUserDao().create(user);
    	}
    	return user;
	}
	
    /**
     * @see org.openuss.chat.ChatService#login(java.lang.Long)
     */
    protected void handleLogin(java.lang.Long domainId)
        throws java.lang.Exception
    {
    	ChatRoom room = getRoomEntity(domainId);
    	List<ChatUser> users = room.getChatUsers();
    	ChatUser user = getUser();
    	if (!users.contains(user)){
    		users.add(user);
    		room.setChatUsers(users);
    		getChatRoomDao().update(room);
    	}
    	update();
    }

    /**
     * @see org.openuss.chat.ChatService#logout(java.lang.Long)
     */
    protected void handleLogout(java.lang.Long domainId)
        throws java.lang.Exception
    {
    	ChatRoom room = getRoomEntity(domainId);
    	ChatUser user = getUser();
    	List<ChatUser> users = room.getChatUsers();
    	if (users.contains(user)){
    		users.remove(user);
    		room.setChatUsers(users);
    		getChatRoomDao().update(room);
    	}
    	user.setLastActive(System.currentTimeMillis());
    	getChatUserDao().update(user);
    }

    /**
     * @see org.openuss.chat.ChatService#setRoom(org.openuss.chat.ChatRoomInfo)
     */
    protected void handleSetRoom(org.openuss.chat.ChatRoomInfo room)
        throws java.lang.Exception
    {
    	getChatRoomDao().chatRoomInfoToEntity(room);
    }

	@Override
	protected void handleUpdate() throws Exception {
//		ChatUser user = getUser();
//		user.setLastActive(System.currentTimeMillis());
//		getChatUserDao().update(user);
	}

	@Override
	protected void handleSendSystemMessage(ChatMessageInfo message, Long domainId)
			throws Exception {
//		ChatRoom room = getRoomEntity(domainId);
//		ChatMessage messageEntity = getChatMessageDao().chatMessageInfoToEntity(message);
//		messageEntity.setSender(room.getSYSTEMUSER());
//		messageEntity.setRoom(room);
//		getChatMessageDao().create(messageEntity);
	}

	@Override
	protected ChatRoomInfo handleGetRoom(Long domainId) throws Exception {
		return getChatRoomDao().toChatRoomInfo(getRoomEntity(domainId));
	}

}