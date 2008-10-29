// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.chat;
/**
 * @see org.openuss.chat.ChatUser
 */
public class ChatUserDaoImpl
    extends org.openuss.chat.ChatUserDaoBase
{
    /**
     * @see org.openuss.chat.ChatUserDao#toChatUserInfo(org.openuss.chat.ChatUser, org.openuss.chat.ChatUserInfo)
     */
    public void toChatUserInfo(
        org.openuss.chat.ChatUser sourceEntity,
        org.openuss.chat.ChatUserInfo targetVO)
    {
        // @todo verify behavior of toChatUserInfo
        super.toChatUserInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.chat.ChatUserDao#toChatUserInfo(org.openuss.chat.ChatUser)
     */
    public org.openuss.chat.ChatUserInfo toChatUserInfo(final org.openuss.chat.ChatUser entity)
    {
        // @todo verify behavior of toChatUserInfo
        return super.toChatUserInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.chat.ChatUser loadChatUserFromChatUserInfo(org.openuss.chat.ChatUserInfo chatUserInfo)
    {
        // @todo implement loadChatUserFromChatUserInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.chat.loadChatUserFromChatUserInfo(org.openuss.chat.ChatUserInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.chat.ChatUser chatUser = this.load(chatUserInfo.getId());
        if (chatUser == null)
        {
            chatUser = org.openuss.chat.ChatUser.Factory.newInstance();
        }
        return chatUser;
        */
    }

    
    /**
     * @see org.openuss.chat.ChatUserDao#chatUserInfoToEntity(org.openuss.chat.ChatUserInfo)
     */
    public org.openuss.chat.ChatUser chatUserInfoToEntity(org.openuss.chat.ChatUserInfo chatUserInfo)
    {
        // @todo verify behavior of chatUserInfoToEntity
        org.openuss.chat.ChatUser entity = this.loadChatUserFromChatUserInfo(chatUserInfo);
        this.chatUserInfoToEntity(chatUserInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.chat.ChatUserDao#chatUserInfoToEntity(org.openuss.chat.ChatUserInfo, org.openuss.chat.ChatUser)
     */
    public void chatUserInfoToEntity(
        org.openuss.chat.ChatUserInfo sourceVO,
        org.openuss.chat.ChatUser targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of chatUserInfoToEntity
        super.chatUserInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}