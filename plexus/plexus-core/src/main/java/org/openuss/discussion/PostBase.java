package org.openuss.discussion;

/**
 * 
 */
public abstract class PostBase
    implements Post, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -1991074769055684223L;

    private java.lang.String title;

    /**
     * @see org.openuss.discussion.Post#getTitle()
     */
    public java.lang.String getTitle()
    {
        return this.title;
    }

    /**
     * @see org.openuss.discussion.Post#setTitle(java.lang.String title)
     */
    public void setTitle(java.lang.String title)
    {
        this.title = title;
    }

    private java.lang.String text;

    /**
     * @see org.openuss.discussion.Post#getText()
     */
    public java.lang.String getText()
    {
        return this.text;
    }

    /**
     * @see org.openuss.discussion.Post#setText(java.lang.String text)
     */
    public void setText(java.lang.String text)
    {
        this.text = text;
    }

    private java.util.Date created;

    /**
     * @see org.openuss.discussion.Post#getCreated()
     */
    public java.util.Date getCreated()
    {
        return this.created;
    }

    /**
     * @see org.openuss.discussion.Post#setCreated(java.util.Date created)
     */
    public void setCreated(java.util.Date created)
    {
        this.created = created;
    }

    private java.util.Date lastModification;

    /**
     * @see org.openuss.discussion.Post#getLastModification()
     */
    public java.util.Date getLastModification()
    {
        return this.lastModification;
    }

    /**
     * @see org.openuss.discussion.Post#setLastModification(java.util.Date lastModification)
     */
    public void setLastModification(java.util.Date lastModification)
    {
        this.lastModification = lastModification;
    }

    private java.lang.Long id;

    /**
     * @see org.openuss.discussion.Post#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.discussion.Post#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.String ip;

    /**
     * @see org.openuss.discussion.Post#getIp()
     */
    public java.lang.String getIp()
    {
        return this.ip;
    }

    /**
     * @see org.openuss.discussion.Post#setIp(java.lang.String ip)
     */
    public void setIp(java.lang.String ip)
    {
        this.ip = ip;
    }

    private org.openuss.security.User submitter;

    /**
     * 
     */
    public org.openuss.security.User getSubmitter()
    {
        return this.submitter;
    }

    public void setSubmitter(org.openuss.security.User submitter)
    {
        this.submitter = submitter;
    }

    private org.openuss.discussion.Topic topic;

    /**
     * 
     */
    public org.openuss.discussion.Topic getTopic()
    {
        return this.topic;
    }

    public void setTopic(org.openuss.discussion.Topic topic)
    {
        this.topic = topic;
    }

    private org.openuss.discussion.Formula formula;

    /**
     * 
     */
    public org.openuss.discussion.Formula getFormula()
    {
        return this.formula;
    }

    public void setFormula(org.openuss.discussion.Formula formula)
    {
        this.formula = formula;
    }

    private org.openuss.security.User editor;

    /**
     * 
     */
    public org.openuss.security.User getEditor()
    {
        return this.editor;
    }

    public void setEditor(org.openuss.security.User editor)
    {
        this.editor = editor;
    }

    /**
     * 
     */
    public abstract java.lang.String getSubmitterName();

    /**
     * 
     */
    public abstract java.lang.String getEditorName();

    /**
     * 
     */
    public abstract boolean isEdited();

    /**
     * 
     */
    public abstract java.lang.String getFormulaString();

    /**
     * 
     */
    public abstract void setFormulaString(java.lang.String formula);

    /**
     * 
     */
    public abstract java.lang.Long getSubmitterPicture();

    /**
     * Returns <code>true</code> if the argument is an Post instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Post))
        {
            return false;
        }
        final Post that = (Post)object;
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