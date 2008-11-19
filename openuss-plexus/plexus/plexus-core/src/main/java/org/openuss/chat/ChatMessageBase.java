package org.openuss.chat;

/**
 * 
 */
public abstract class ChatMessageBase
    implements ChatMessage, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -3626535461040231676L;

    private java.lang.Long id;

    /**
     * @see org.openuss.chat.ChatMessage#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.chat.ChatMessage#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.String text;

    /**
     * @see org.openuss.chat.ChatMessage#getText()
     */
    public java.lang.String getText()
    {
        return this.text;
    }

    /**
     * @see org.openuss.chat.ChatMessage#setText(java.lang.String text)
     */
    public void setText(java.lang.String text)
    {
        this.text = text;
    }

    private java.util.Date time;

    /**
     * @see org.openuss.chat.ChatMessage#getTime()
     */
    public java.util.Date getTime()
    {
        return this.time;
    }

    /**
     * @see org.openuss.chat.ChatMessage#setTime(java.util.Date time)
     */
    public void setTime(java.util.Date time)
    {
        this.time = time;
    }

    private boolean i18n = false;

    /**
     * @see org.openuss.chat.ChatMessage#isI18n()
     */
    public boolean isI18n()
    {
        return this.i18n;
    }

    /**
     * @see org.openuss.chat.ChatMessage#setI18n(boolean i18n)
     */
    public void setI18n(boolean i18n)
    {
        this.i18n = i18n;
    }

    private org.openuss.chat.ChatRoom room;

    /**
     * 
     */
    public org.openuss.chat.ChatRoom getRoom()
    {
        return this.room;
    }

    public void setRoom(org.openuss.chat.ChatRoom room)
    {
        this.room = room;
    }

    private org.openuss.chat.ChatUser sender;

    /**
     * 
     */
    public org.openuss.chat.ChatUser getSender()
    {
        return this.sender;
    }

    public void setSender(org.openuss.chat.ChatUser sender)
    {
        this.sender = sender;
    }

    /**
     * Returns <code>true</code> if the argument is an ChatMessage instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof ChatMessage))
        {
            return false;
        }
        final ChatMessage that = (ChatMessage)object;
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