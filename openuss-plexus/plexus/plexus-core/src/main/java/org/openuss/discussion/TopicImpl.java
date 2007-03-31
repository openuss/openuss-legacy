// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;

/**
 * @see org.openuss.discussion.Topic
 */
public class TopicImpl
    extends org.openuss.discussion.TopicBase
	implements org.openuss.discussion.Topic
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 8403531529041681845L;

    /**
     * @see org.openuss.discussion.Topic#addPost(org.openuss.discussion.Post)
     */
    public void addPost(org.openuss.discussion.Post post)
    {
    	getPosts().add(post);
    }

    /**
     * @see org.openuss.discussion.Topic#removePost(org.openuss.discussion.Post)
     */
    public void removePost(org.openuss.discussion.Post post)
    {
    	getPosts().remove(post);
    }

    /**
     * @see org.openuss.discussion.Topic#getAnswerCount()
     */
    public java.lang.Integer getAnswerCount()
    {
    	return getPosts().size()-1;
    }

    /**
     * @see org.openuss.discussion.Topic#getCreated()
     */
    public java.util.Date getCreated()
    {
        return getFirst().getCreated();
    }

    /**
     * @see org.openuss.discussion.Topic#getTitle()
     */
    public java.lang.String getTitle()
    {
    	if (getFirst()!=null) return getFirst().getTitle();
    	return null;
    }

    /**
     * @see org.openuss.discussion.Topic#getLastPostDate()
     */
    public java.util.Date getLastPostDate()
    {
	     if (getLast()!=null) getLast().getLastModification();
	     return null;
    }

    /**
     * @see org.openuss.discussion.Topic#getLastPostSubmitterName()
     */
    public java.lang.String getLastPostSubmitterName()
    {
    	if (getLast()!=null) getLast().getSubmitterName();
    	return null;
    }

}