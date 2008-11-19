package org.openuss.messaging;

/**
 * 
 */
public abstract class RecipientBase
    implements Recipient, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 8197050268747319432L;

    private java.lang.Long id;

    /**
     * @see org.openuss.messaging.Recipient#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.messaging.Recipient#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private org.openuss.messaging.SendState state;

    /**
     * @see org.openuss.messaging.Recipient#getState()
     */
    public org.openuss.messaging.SendState getState()
    {
        return this.state;
    }

    /**
     * @see org.openuss.messaging.Recipient#setState(org.openuss.messaging.SendState state)
     */
    public void setState(org.openuss.messaging.SendState state)
    {
        this.state = state;
    }

    private java.util.Date send;

    /**
     * @see org.openuss.messaging.Recipient#getSend()
     */
    public java.util.Date getSend()
    {
        return this.send;
    }

    /**
     * @see org.openuss.messaging.Recipient#setSend(java.util.Date send)
     */
    public void setSend(java.util.Date send)
    {
        this.send = send;
    }

    private java.lang.String email;

    /**
     * @see org.openuss.messaging.Recipient#getEmail()
     */
    public java.lang.String getEmail()
    {
        return this.email;
    }

    /**
     * @see org.openuss.messaging.Recipient#setEmail(java.lang.String email)
     */
    public void setEmail(java.lang.String email)
    {
        this.email = email;
    }

    private java.lang.String locale;

    /**
     * @see org.openuss.messaging.Recipient#getLocale()
     */
    public java.lang.String getLocale()
    {
        return this.locale;
    }

    /**
     * @see org.openuss.messaging.Recipient#setLocale(java.lang.String locale)
     */
    public void setLocale(java.lang.String locale)
    {
        this.locale = locale;
    }

    private java.lang.String sms;

    /**
     * @see org.openuss.messaging.Recipient#getSms()
     */
    public java.lang.String getSms()
    {
        return this.sms;
    }

    /**
     * @see org.openuss.messaging.Recipient#setSms(java.lang.String sms)
     */
    public void setSms(java.lang.String sms)
    {
        this.sms = sms;
    }

    private org.openuss.messaging.MessageJob job;

    /**
     * 
     */
    public org.openuss.messaging.MessageJob getJob()
    {
        return this.job;
    }

    public void setJob(org.openuss.messaging.MessageJob job)
    {
        this.job = job;
    }

    /**
     * 
     */
    public abstract boolean hasSmsNotification();

    /**
     * Returns <code>true</code> if the argument is an Recipient instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Recipient))
        {
            return false;
        }
        final Recipient that = (Recipient)object;
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