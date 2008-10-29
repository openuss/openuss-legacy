package org.openuss.discussion;

/**
 * 
 */
public abstract class TopicBase
    implements Topic, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -7040717772435076881L;

    private java.lang.Long id;

    /**
     * @see org.openuss.discussion.Topic#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.discussion.Topic#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.Integer hits;

    /**
     * @see org.openuss.discussion.Topic#getHits()
     */
    public java.lang.Integer getHits()
    {
        return this.hits;
    }

    /**
     * @see org.openuss.discussion.Topic#setHits(java.lang.Integer hits)
     */
    public void setHits(java.lang.Integer hits)
    {
        this.hits = hits;
    }

    private boolean readOnly;

    /**
     * @see org.openuss.discussion.Topic#isReadOnly()
     */
    public boolean isReadOnly()
    {
        return this.readOnly;
    }

    /**
     * @see org.openuss.discussion.Topic#setReadOnly(boolean readOnly)
     */
    public void setReadOnly(boolean readOnly)
    {
        this.readOnly = readOnly;
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

    private java.util.List<org.openuss.discussion.Post> posts = new java.util.ArrayList<org.openuss.discussion.Post>();

    /**
     * 
     */
    public java.util.List<org.openuss.discussion.Post> getPosts()
    {
        return this.posts;
    }

    public void setPosts(java.util.List<org.openuss.discussion.Post> posts)
    {
        this.posts = posts;
    }

    private org.openuss.discussion.Post first;

    /**
     * 
     */
    public org.openuss.discussion.Post getFirst()
    {
        return this.first;
    }

    public void setFirst(org.openuss.discussion.Post first)
    {
        this.first = first;
    }

    private org.openuss.discussion.Post last;

    /**
     * 
     */
    public org.openuss.discussion.Post getLast()
    {
        return this.last;
    }

    public void setLast(org.openuss.discussion.Post last)
    {
        this.last = last;
    }

    private org.openuss.discussion.Forum forum;

    /**
     * 
     */
    public org.openuss.discussion.Forum getForum()
    {
        return this.forum;
    }

    public void setForum(org.openuss.discussion.Forum forum)
    {
        this.forum = forum;
    }

    /**
     * 
     */
    public abstract void addPost(org.openuss.discussion.Post post);

    /**
     * 
     */
    public abstract void removePost(org.openuss.discussion.Post post);

    /**
     * <p>
     * @return Integer - number of replies
     * </p>
     */
    public abstract java.lang.Integer getAnswerCount();

    /**
     * 
     */
    public abstract java.util.Date getCreated();

    /**
     * <p>
     * @return string - the title of the first post
     * </p>
     */
    public abstract java.lang.String getTitle();

    /**
     * 
     */
    public abstract java.util.Date getLastPostDate();

    /**
     * 
     */
    public abstract java.lang.String getLastPostSubmitterName();

    /**
     * Returns <code>true</code> if the argument is an Topic instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Topic))
        {
            return false;
        }
        final Topic that = (Topic)object;
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