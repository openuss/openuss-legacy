package org.openuss.chat;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.chat.ChatService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.chat.ChatService
 */
public abstract class ChatServiceBase
    implements org.openuss.chat.ChatService
{

    private org.openuss.security.SecurityService securityService;

    /**
     * Sets the reference to <code>securityService</code>.
     */
    public void setSecurityService(org.openuss.security.SecurityService securityService)
    {
        this.securityService = securityService;
    }

    /**
     * Gets the reference to <code>securityService</code>.
     */
    protected org.openuss.security.SecurityService getSecurityService()
    {
        return this.securityService;
    }

    private org.openuss.chat.ChatRoomDao chatRoomDao;

    /**
     * Sets the reference to <code>chatRoom</code>'s DAO.
     */
    public void setChatRoomDao(org.openuss.chat.ChatRoomDao chatRoomDao)
    {
        this.chatRoomDao = chatRoomDao;
    }

    /**
     * Gets the reference to <code>chatRoom</code>'s DAO.
     */
    protected org.openuss.chat.ChatRoomDao getChatRoomDao()
    {
        return this.chatRoomDao;
    }

    private org.openuss.chat.ChatMessageDao chatMessageDao;

    /**
     * Sets the reference to <code>chatMessage</code>'s DAO.
     */
    public void setChatMessageDao(org.openuss.chat.ChatMessageDao chatMessageDao)
    {
        this.chatMessageDao = chatMessageDao;
    }

    /**
     * Gets the reference to <code>chatMessage</code>'s DAO.
     */
    protected org.openuss.chat.ChatMessageDao getChatMessageDao()
    {
        return this.chatMessageDao;
    }

    private org.openuss.chat.ChatUserDao chatUserDao;

    /**
     * Sets the reference to <code>chatUser</code>'s DAO.
     */
    public void setChatUserDao(org.openuss.chat.ChatUserDao chatUserDao)
    {
        this.chatUserDao = chatUserDao;
    }

    /**
     * Gets the reference to <code>chatUser</code>'s DAO.
     */
    protected org.openuss.chat.ChatUserDao getChatUserDao()
    {
        return this.chatUserDao;
    }

    /**
     * @see org.openuss.chat.ChatService#createRoom(org.openuss.foundation.DomainObject, java.lang.String, java.lang.String)
     */
    public org.openuss.chat.ChatRoomInfo createRoom(org.openuss.foundation.DomainObject domain, java.lang.String name, java.lang.String topic)
    {
        try
        {
            return this.handleCreateRoom(domain, name, topic);
        }
        catch (Throwable th)
        {
            throw new org.openuss.chat.ChatServiceException(
                "Error performing 'org.openuss.chat.ChatService.createRoom(org.openuss.foundation.DomainObject domain, java.lang.String name, java.lang.String topic)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createRoom(org.openuss.foundation.DomainObject, java.lang.String, java.lang.String)}
      */
    protected abstract org.openuss.chat.ChatRoomInfo handleCreateRoom(org.openuss.foundation.DomainObject domain, java.lang.String name, java.lang.String topic)
        throws java.lang.Exception;

    /**
     * @see org.openuss.chat.ChatService#deleteRoom(java.lang.Long)
     */
    public void deleteRoom(java.lang.Long roomId)
        throws org.openuss.chat.ChatRoomServiceException
    {
        try
        {
            this.handleDeleteRoom(roomId);
        }
        catch (org.openuss.chat.ChatRoomServiceException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.chat.ChatServiceException(
                "Error performing 'org.openuss.chat.ChatService.deleteRoom(java.lang.Long roomId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #deleteRoom(java.lang.Long)}
      */
    protected abstract void handleDeleteRoom(java.lang.Long roomId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.chat.ChatService#getRooms(org.openuss.foundation.DomainObject)
     */
    public java.util.List getRooms(org.openuss.foundation.DomainObject domain)
    {
        try
        {
            return this.handleGetRooms(domain);
        }
        catch (Throwable th)
        {
            throw new org.openuss.chat.ChatServiceException(
                "Error performing 'org.openuss.chat.ChatService.getRooms(org.openuss.foundation.DomainObject domain)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getRooms(org.openuss.foundation.DomainObject)}
      */
    protected abstract java.util.List handleGetRooms(org.openuss.foundation.DomainObject domain)
        throws java.lang.Exception;

    /**
     * @see org.openuss.chat.ChatService#enterRoom(java.lang.Long)
     */
    public void enterRoom(java.lang.Long roomId)
    {
        try
        {
            this.handleEnterRoom(roomId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.chat.ChatServiceException(
                "Error performing 'org.openuss.chat.ChatService.enterRoom(java.lang.Long roomId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #enterRoom(java.lang.Long)}
      */
    protected abstract void handleEnterRoom(java.lang.Long roomId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.chat.ChatService#leaveRoom(java.lang.Long)
     */
    public void leaveRoom(java.lang.Long roomId)
    {
        try
        {
            this.handleLeaveRoom(roomId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.chat.ChatServiceException(
                "Error performing 'org.openuss.chat.ChatService.leaveRoom(java.lang.Long roomId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #leaveRoom(java.lang.Long)}
      */
    protected abstract void handleLeaveRoom(java.lang.Long roomId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.chat.ChatService#getMessages(java.lang.Long)
     */
    public java.util.List getMessages(java.lang.Long roomId)
    {
        try
        {
            return this.handleGetMessages(roomId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.chat.ChatServiceException(
                "Error performing 'org.openuss.chat.ChatService.getMessages(java.lang.Long roomId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getMessages(java.lang.Long)}
      */
    protected abstract java.util.List handleGetMessages(java.lang.Long roomId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.chat.ChatService#getChatUsers(java.lang.Long)
     */
    public java.util.List getChatUsers(java.lang.Long roomId)
    {
        try
        {
            return this.handleGetChatUsers(roomId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.chat.ChatServiceException(
                "Error performing 'org.openuss.chat.ChatService.getChatUsers(java.lang.Long roomId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getChatUsers(java.lang.Long)}
      */
    protected abstract java.util.List handleGetChatUsers(java.lang.Long roomId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.chat.ChatService#getRecentMessages(java.lang.Long, java.lang.Long)
     */
    public java.util.List getRecentMessages(java.lang.Long roomId, java.lang.Long messageId)
    {
        try
        {
            return this.handleGetRecentMessages(roomId, messageId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.chat.ChatServiceException(
                "Error performing 'org.openuss.chat.ChatService.getRecentMessages(java.lang.Long roomId, java.lang.Long messageId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getRecentMessages(java.lang.Long, java.lang.Long)}
      */
    protected abstract java.util.List handleGetRecentMessages(java.lang.Long roomId, java.lang.Long messageId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.chat.ChatService#getRecentMessages(java.lang.Long, java.util.Date)
     */
    public java.util.List getRecentMessages(java.lang.Long roomId, java.util.Date since)
    {
        try
        {
            return this.handleGetRecentMessages(roomId, since);
        }
        catch (Throwable th)
        {
            throw new org.openuss.chat.ChatServiceException(
                "Error performing 'org.openuss.chat.ChatService.getRecentMessages(java.lang.Long roomId, java.util.Date since)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getRecentMessages(java.lang.Long, java.util.Date)}
      */
    protected abstract java.util.List handleGetRecentMessages(java.lang.Long roomId, java.util.Date since)
        throws java.lang.Exception;

    /**
     * @see org.openuss.chat.ChatService#sendMessage(java.lang.Long, java.lang.String)
     */
    public void sendMessage(java.lang.Long roomId, java.lang.String text)
    {
        try
        {
            this.handleSendMessage(roomId, text);
        }
        catch (Throwable th)
        {
            throw new org.openuss.chat.ChatServiceException(
                "Error performing 'org.openuss.chat.ChatService.sendMessage(java.lang.Long roomId, java.lang.String text)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #sendMessage(java.lang.Long, java.lang.String)}
      */
    protected abstract void handleSendMessage(java.lang.Long roomId, java.lang.String text)
        throws java.lang.Exception;

    /**
     * @see org.openuss.chat.ChatService#getRoom(java.lang.Long)
     */
    public org.openuss.chat.ChatRoomInfo getRoom(java.lang.Long roomId)
    {
        try
        {
            return this.handleGetRoom(roomId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.chat.ChatServiceException(
                "Error performing 'org.openuss.chat.ChatService.getRoom(java.lang.Long roomId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getRoom(java.lang.Long)}
      */
    protected abstract org.openuss.chat.ChatRoomInfo handleGetRoom(java.lang.Long roomId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.chat.ChatService#getLastMessage(java.lang.Long)
     */
    public java.lang.Long getLastMessage(java.lang.Long roomId)
    {
        try
        {
            return this.handleGetLastMessage(roomId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.chat.ChatServiceException(
                "Error performing 'org.openuss.chat.ChatService.getLastMessage(java.lang.Long roomId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getLastMessage(java.lang.Long)}
      */
    protected abstract java.lang.Long handleGetLastMessage(java.lang.Long roomId)
        throws java.lang.Exception;

    /**
     * Gets the current <code>principal</code> if one has been set,
     * otherwise returns <code>null</code>.
     *
     * @return the current principal
     */
    protected java.security.Principal getPrincipal()
    {
        return org.andromda.spring.PrincipalStore.get();
    }
}