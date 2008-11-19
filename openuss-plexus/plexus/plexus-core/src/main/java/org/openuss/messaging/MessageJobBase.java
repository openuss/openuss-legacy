package org.openuss.messaging;

/**
 * 
 */
public abstract class MessageJobBase
    implements MessageJob, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -4152108942886392241L;

    private java.lang.Long id;

    /**
     * @see org.openuss.messaging.MessageJob#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.messaging.MessageJob#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.util.Date created;

    /**
     * @see org.openuss.messaging.MessageJob#getCreated()
     */
    public java.util.Date getCreated()
    {
        return this.created;
    }

    /**
     * @see org.openuss.messaging.MessageJob#setCreated(java.util.Date created)
     */
    public void setCreated(java.util.Date created)
    {
        this.created = created;
    }

    private boolean sendAsSms;

    /**
     * @see org.openuss.messaging.MessageJob#isSendAsSms()
     */
    public boolean isSendAsSms()
    {
        return this.sendAsSms;
    }

    /**
     * @see org.openuss.messaging.MessageJob#setSendAsSms(boolean sendAsSms)
     */
    public void setSendAsSms(boolean sendAsSms)
    {
        this.sendAsSms = sendAsSms;
    }

    private org.openuss.messaging.JobState state;

    /**
     * @see org.openuss.messaging.MessageJob#getState()
     */
    public org.openuss.messaging.JobState getState()
    {
        return this.state;
    }

    /**
     * @see org.openuss.messaging.MessageJob#setState(org.openuss.messaging.JobState state)
     */
    public void setState(org.openuss.messaging.JobState state)
    {
        this.state = state;
    }

    private java.util.Set<org.openuss.messaging.Recipient> recipients = new java.util.HashSet<org.openuss.messaging.Recipient>();

    /**
     * 
     */
    public java.util.Set<org.openuss.messaging.Recipient> getRecipients()
    {
        return this.recipients;
    }

    public void setRecipients(java.util.Set<org.openuss.messaging.Recipient> recipients)
    {
        this.recipients = recipients;
    }

    private org.openuss.messaging.Message message;

    /**
     * 
     */
    public org.openuss.messaging.Message getMessage()
    {
        return this.message;
    }

    public void setMessage(org.openuss.messaging.Message message)
    {
        this.message = message;
    }

    /**
     * <p>
     * Number of mails that are already send
     * </p>
     */
    public abstract int getSend();

    /**
     * <p>
     * Number of mails that produces an error during sending
     * </p>
     */
    public abstract int getError();

    /**
     * 
     */
    public abstract int getToSend();

    /**
     * 
     */
    public abstract void addRecipient(org.openuss.security.User user);

    /**
     * 
     */
    public abstract void addRecipients(java.util.Collection users);

    /**
     * 
     */
    public abstract void addRecipient(java.lang.String email, java.lang.String locale);

    /**
     * Returns <code>true</code> if the argument is an MessageJob instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof MessageJob))
        {
            return false;
        }
        final MessageJob that = (MessageJob)object;
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