// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.chat;
/**
 * @see org.openuss.chat.ChatMessage
 */
public class ChatMessageDaoImpl
    extends org.openuss.chat.ChatMessageDaoBase
{
    /**
     * @see org.openuss.chat.ChatMessageDao#toChatMessageInfo(org.openuss.chat.ChatMessage, org.openuss.chat.ChatMessageInfo)
     */
    public void toChatMessageInfo(
        org.openuss.chat.ChatMessage sourceEntity,
        org.openuss.chat.ChatMessageInfo targetVO)
    {
        // @todo verify behavior of toChatMessageInfo
        super.toChatMessageInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.chat.ChatMessageDao#toChatMessageInfo(org.openuss.chat.ChatMessage)
     */
    public org.openuss.chat.ChatMessageInfo toChatMessageInfo(final org.openuss.chat.ChatMessage entity)
    {
        // @todo verify behavior of toChatMessageInfo
        return super.toChatMessageInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.chat.ChatMessage loadChatMessageFromChatMessageInfo(org.openuss.chat.ChatMessageInfo chatMessageInfo)
    {
        if (chatMessageInfo==null || chatMessageInfo.getId()==null) {
        	return ChatMessage.Factory.newInstance();
        }
    	org.openuss.chat.ChatMessage chatMessage = this.load(chatMessageInfo.getId());
        if (chatMessage == null)
        {
            chatMessage = org.openuss.chat.ChatMessage.Factory.newInstance();
        }
        return chatMessage;
    }

    
    /**
     * @see org.openuss.chat.ChatMessageDao#chatMessageInfoToEntity(org.openuss.chat.ChatMessageInfo)
     */
    public org.openuss.chat.ChatMessage chatMessageInfoToEntity(org.openuss.chat.ChatMessageInfo chatMessageInfo)
    {
        // @todo verify behavior of chatMessageInfoToEntity
        org.openuss.chat.ChatMessage entity = this.loadChatMessageFromChatMessageInfo(chatMessageInfo);
        this.chatMessageInfoToEntity(chatMessageInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.chat.ChatMessageDao#chatMessageInfoToEntity(org.openuss.chat.ChatMessageInfo, org.openuss.chat.ChatMessage)
     */
    public void chatMessageInfoToEntity(
        org.openuss.chat.ChatMessageInfo sourceVO,
        org.openuss.chat.ChatMessage targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of chatMessageInfoToEntity
        super.chatMessageInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}