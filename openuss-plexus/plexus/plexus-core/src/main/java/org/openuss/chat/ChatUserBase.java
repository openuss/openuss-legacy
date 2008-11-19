package org.openuss.chat;

/**
 * <p>
 * Id of the chatuser is equal to the user id
 * </p>
 */
public abstract class ChatUserBase
    implements ChatUser, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -6995233571590067792L;

    private java.lang.Long id;

    /**
     * @see org.openuss.chat.ChatUser#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.chat.ChatUser#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.String displayName;

    /**
     * @see org.openuss.chat.ChatUser#getDisplayName()
     */
    public java.lang.String getDisplayName()
    {
        return this.displayName;
    }

    /**
     * @see org.openuss.chat.ChatUser#setDisplayName(java.lang.String displayName)
     */
    public void setDisplayName(java.lang.String displayName)
    {
        this.displayName = displayName;
    }

    private java.lang.String email;

    /**
     * @see org.openuss.chat.ChatUser#getEmail()
     */
    public java.lang.String getEmail()
    {
        return this.email;
    }

    /**
     * @see org.openuss.chat.ChatUser#setEmail(java.lang.String email)
     */
    public void setEmail(java.lang.String email)
    {
        this.email = email;
    }

    /**
     * Returns <code>true</code> if the argument is an ChatUser instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof ChatUser))
        {
            return false;
        }
        final ChatUser that = (ChatUser)object;
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