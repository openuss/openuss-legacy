// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.chat;

/**
 * @see org.openuss.chat.ChatRoom
 */
public class ChatRoomDaoImpl
    extends org.openuss.chat.ChatRoomDaoBase
{
    /**
     * @see org.openuss.chat.ChatRoomDao#toChatRoomInfo(org.openuss.chat.ChatRoom, org.openuss.chat.ChatRoomInfo)
     */
    public void toChatRoomInfo(
        org.openuss.chat.ChatRoom sourceEntity,
        org.openuss.chat.ChatRoomInfo targetVO)
    {
        // @todo verify behavior of toChatRoomInfo
        super.toChatRoomInfo(sourceEntity, targetVO);
        long lastMsgStamp=0;
        for (ChatMessage chatMessage : sourceEntity.getMessages()) {
			if (chatMessage.getTime().getTime()>lastMsgStamp) lastMsgStamp=chatMessage.getTime().getTime();
		}
        targetVO.setLastMsgStamp(lastMsgStamp);
    }


    /**
     * @see org.openuss.chat.ChatRoomDao#toChatRoomInfo(org.openuss.chat.ChatRoom)
     */
    public org.openuss.chat.ChatRoomInfo toChatRoomInfo(final org.openuss.chat.ChatRoom entity)
    {
        return super.toChatRoomInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.chat.ChatRoom loadChatRoomFromChatRoomInfo(org.openuss.chat.ChatRoomInfo chatRoomInfo)
    {
    	if (chatRoomInfo==null||chatRoomInfo.getId()==null) {
    		return ChatRoom.Factory.newInstance();
    	}
        org.openuss.chat.ChatRoom chatRoom = this.load(chatRoomInfo.getId());
        if (chatRoom == null)
        {
            chatRoom = org.openuss.chat.ChatRoom.Factory.newInstance();
        }
        return chatRoom;
    }

    
    /**
     * @see org.openuss.chat.ChatRoomDao#chatRoomInfoToEntity(org.openuss.chat.ChatRoomInfo)
     */
    public org.openuss.chat.ChatRoom chatRoomInfoToEntity(org.openuss.chat.ChatRoomInfo chatRoomInfo)
    {
        org.openuss.chat.ChatRoom entity = this.loadChatRoomFromChatRoomInfo(chatRoomInfo);
        this.chatRoomInfoToEntity(chatRoomInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.chat.ChatRoomDao#chatRoomInfoToEntity(org.openuss.chat.ChatRoomInfo, org.openuss.chat.ChatRoom)
     */
    public void chatRoomInfoToEntity(
        org.openuss.chat.ChatRoomInfo sourceVO,
        org.openuss.chat.ChatRoom targetEntity,
        boolean copyIfNull)
    {
        super.chatRoomInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}