package org.openuss.chat;

/**
 * 
 */
public abstract class ChatRoomBase
    implements ChatRoom, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 2832889519029983171L;

    private java.lang.Long id;

    /**
     * @see org.openuss.chat.ChatRoom#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.chat.ChatRoom#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.Long domainId;

    /**
     * @see org.openuss.chat.ChatRoom#getDomainId()
     */
    public java.lang.Long getDomainId()
    {
        return this.domainId;
    }

    /**
     * @see org.openuss.chat.ChatRoom#setDomainId(java.lang.Long domainId)
     */
    public void setDomainId(java.lang.Long domainId)
    {
        this.domainId = domainId;
    }

    private java.lang.String name;

    /**
     * @see org.openuss.chat.ChatRoom#getName()
     */
    public java.lang.String getName()
    {
        return this.name;
    }

    /**
     * @see org.openuss.chat.ChatRoom#setName(java.lang.String name)
     */
    public void setName(java.lang.String name)
    {
        this.name = name;
    }

    private java.lang.String topic;

    /**
     * @see org.openuss.chat.ChatRoom#getTopic()
     */
    public java.lang.String getTopic()
    {
        return this.topic;
    }

    /**
     * @see org.openuss.chat.ChatRoom#setTopic(java.lang.String topic)
     */
    public void setTopic(java.lang.String topic)
    {
        this.topic = topic;
    }

    private java.util.Date created;

    /**
     * @see org.openuss.chat.ChatRoom#getCreated()
     */
    public java.util.Date getCreated()
    {
        return this.created;
    }

    /**
     * @see org.openuss.chat.ChatRoom#setCreated(java.util.Date created)
     */
    public void setCreated(java.util.Date created)
    {
        this.created = created;
    }

    private java.util.Collection<org.openuss.chat.ChatMessage> messages = new java.util.ArrayList<org.openuss.chat.ChatMessage>();

    /**
     * 
     */
    public java.util.Collection<org.openuss.chat.ChatMessage> getMessages()
    {
        return this.messages;
    }

    public void setMessages(java.util.Collection<org.openuss.chat.ChatMessage> messages)
    {
        this.messages = messages;
    }

    private java.util.Set<org.openuss.chat.ChatUser> chatUsers = new java.util.HashSet<org.openuss.chat.ChatUser>();

    /**
     * 
     */
    public java.util.Set<org.openuss.chat.ChatUser> getChatUsers()
    {
        return this.chatUsers;
    }

    public void setChatUsers(java.util.Set<org.openuss.chat.ChatUser> chatUsers)
    {
        this.chatUsers = chatUsers;
    }

    private org.openuss.chat.ChatUser owner;

    /**
     * 
     */
    public org.openuss.chat.ChatUser getOwner()
    {
        return this.owner;
    }

    public void setOwner(org.openuss.chat.ChatUser owner)
    {
        this.owner = owner;
    }

    /**
     * 
     */
    public abstract void add(org.openuss.chat.ChatMessage message);

    /**
     * 
     */
    public abstract void add(org.openuss.chat.ChatUser user);

    /**
     * 
     */
    public abstract void remove(org.openuss.chat.ChatUser user);

    /**
     * Returns <code>true</code> if the argument is an ChatRoom instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof ChatRoom))
        {
            return false;
        }
        final ChatRoom that = (ChatRoom)object;
        if (this.id == null || that.getId() == null || !this.id.equals(that.getId()))
        {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code based on this entity's identifiers.
     */
    public int hashCode()
    {
        int hashCode = 0;
        hashCode = 29 * hashCode + (id == null ? 0 : id.hashCode());

        return hashCode;
    }


}