package org.openuss.chat;

import java.util.Date;
import java.util.List;

import org.openuss.foundation.DomainObject;

/**
 * 
 * @author Ingo Dueppe
 * 
 */
public interface ChatService {

	/**
	 * Create a new chat room for domain object. Current user will become the
	 * owner of the room.
	 * 
	 * @param domainObject
	 *            - contains a valid identifier
	 * @param name
	 *            - name of the domain object
	 * @param topic
	 *            - topic of the room
	 * @return ChatRoomInfo
	 */
	public ChatRoomInfo createRoom(DomainObject domain, String name, String topic);

	/**
	 * Delete a chat room. User need ACL.DELETE priviledge
	 * 
	 * @param roomId
	 *            - identifier of the chatroom
	 */
	public void deleteRoom(Long roomId) throws ChatRoomServiceException;

	/**
	 * Retrieve a list of existing rooms for the given domain object
	 * 
	 * @param domain
	 *            - DomainObject with a given identifier
	 * @return List<ChatRoomInfo>
	 */
	public List getRooms(DomainObject domain);

	/**
	 * Current user will be added as chat user to the given room.
	 * 
	 * @param roomId
	 *            - existing room identifier
	 */
	public void enterRoom(Long roomId);

	/**
	 * Current user will leave the given chat room.
	 * 
	 * @param roomId
	 *            - existing room id
	 */
	public void leaveRoom(Long roomId);

	/**
	 * Retrieve all messages of the room.
	 * 
	 * @param roomId
	 *            - existing identifier of the chat room
	 * @return List<ChatMessageInfo>
	 */
	public List getMessages(Long roomId);

	/**
	 * Retrieve online user name list of the given room
	 * 
	 * @param roomId
	 *            - existing identifier of the room
	 * @return List<String>
	 */
	public List getChatUsers(Long roomId);

	/**
	 * Retrieve a list of recent messages of the room that were posted after the
	 * given message id.
	 * 
	 * @param messageId
	 *            - identifier of a message
	 * @param roomId
	 *            - identifier of the chat room
	 * @return List<ChatMessageInfo>
	 */
	public List<ChatMessageInfo> getRecentMessages(Long roomId, Long messageId);

	/**
	 * Retrieve a list of recent messages of the room that were posted after the
	 * given point of time.
	 * 
	 * @param since
	 *            - timestamp
	 * @param roomId
	 *            - identifier of the chat room
	 * @return List<ChatMessageInfo>
	 */
	public List getRecentMessages(Long roomId, Date since);

	/**
	 * Send a new message of the current user to the chat room.
	 * 
	 * @param roomId
	 *            - existing identifier of the chat room
	 * @param test
	 *            - message text
	 */
	public void sendMessage(Long roomId, String text);

	public ChatRoomInfo getRoom(Long roomId);

	/**
	 * Retrieve the last message id
	 * 
	 * @return Long - id of the last message
	 */
	public Long getLastMessage(Long roomId);

}
