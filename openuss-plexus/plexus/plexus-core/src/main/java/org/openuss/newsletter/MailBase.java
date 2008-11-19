package org.openuss.newsletter;

/**
 * 
 */
public abstract class MailBase
    implements Mail, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 5034151644578582934L;

    private java.lang.Long id;

    /**
     * @see org.openuss.newsletter.Mail#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.newsletter.Mail#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private org.openuss.newsletter.MailingStatus status;

    /**
     * @see org.openuss.newsletter.Mail#getStatus()
     */
    public org.openuss.newsletter.MailingStatus getStatus()
    {
        return this.status;
    }

    /**
     * @see org.openuss.newsletter.Mail#setStatus(org.openuss.newsletter.MailingStatus status)
     */
    public void setStatus(org.openuss.newsletter.MailingStatus status)
    {
        this.status = status;
    }

    private java.lang.String subject;

    /**
     * @see org.openuss.newsletter.Mail#getSubject()
     */
    public java.lang.String getSubject()
    {
        return this.subject;
    }

    /**
     * @see org.openuss.newsletter.Mail#setSubject(java.lang.String subject)
     */
    public void setSubject(java.lang.String subject)
    {
        this.subject = subject;
    }

    private java.lang.String text;

    /**
     * @see org.openuss.newsletter.Mail#getText()
     */
    public java.lang.String getText()
    {
        return this.text;
    }

    /**
     * @see org.openuss.newsletter.Mail#setText(java.lang.String text)
     */
    public void setText(java.lang.String text)
    {
        this.text = text;
    }

    private boolean sms = false;

    /**
     * @see org.openuss.newsletter.Mail#isSms()
     */
    public boolean isSms()
    {
        return this.sms;
    }

    /**
     * @see org.openuss.newsletter.Mail#setSms(boolean sms)
     */
    public void setSms(boolean sms)
    {
        this.sms = sms;
    }

    private java.util.Date sendDate;

    /**
     * @see org.openuss.newsletter.Mail#getSendDate()
     */
    public java.util.Date getSendDate()
    {
        return this.sendDate;
    }

    /**
     * @see org.openuss.newsletter.Mail#setSendDate(java.util.Date sendDate)
     */
    public void setSendDate(java.util.Date sendDate)
    {
        this.sendDate = sendDate;
    }

    private java.lang.Long commandId;

    /**
     * @see org.openuss.newsletter.Mail#getCommandId()
     */
    public java.lang.Long getCommandId()
    {
        return this.commandId;
    }

    /**
     * @see org.openuss.newsletter.Mail#setCommandId(java.lang.Long commandId)
     */
    public void setCommandId(java.lang.Long commandId)
    {
        this.commandId = commandId;
    }

    private java.lang.Long messageId;

    /**
     * @see org.openuss.newsletter.Mail#getMessageId()
     */
    public java.lang.Long getMessageId()
    {
        return this.messageId;
    }

    /**
     * @see org.openuss.newsletter.Mail#setMessageId(java.lang.Long messageId)
     */
    public void setMessageId(java.lang.Long messageId)
    {
        this.messageId = messageId;
    }

    private org.openuss.newsletter.Newsletter newsletter;

    /**
     * 
     */
    public org.openuss.newsletter.Newsletter getNewsletter()
    {
        return this.newsletter;
    }

    public void setNewsletter(org.openuss.newsletter.Newsletter newsletter)
    {
        this.newsletter = newsletter;
    }

    /**
     * Returns <code>true</code> if the argument is an Mail instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Mail))
        {
            return false;
        }
        final Mail that = (Mail)object;
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