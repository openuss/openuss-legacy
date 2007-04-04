// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;

import java.util.Date;

/**
 * @see org.openuss.discussion.Topic
 */
public class TopicImpl extends TopicBase implements Topic {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 8403531529041681845L;

	/**
	 * @see org.openuss.discussion.Topic#addPost(org.openuss.discussion.Post)
	 */
	public void addPost(Post post) {
		getPosts().add(post);
	}

	/**
	 * @see org.openuss.discussion.Topic#removePost(org.openuss.discussion.Post)
	 */
	public void removePost(Post post) {
		getPosts().remove(post);
	}

	/**
	 * @see org.openuss.discussion.Topic#getAnswerCount()
	 */
	public Integer getAnswerCount() {
		return getPosts().size() - 1;
	}

	/**
	 * @see org.openuss.discussion.Topic#getCreated()
	 */
	public Date getCreated() {
		return getFirst().getCreated();
	}

	/**
	 * @see org.openuss.discussion.Topic#getTitle()
	 */
	public String getTitle() {
		if (getFirst() != null)
			return getFirst().getTitle();
		return null;
	}

	/**
	 * @see org.openuss.discussion.Topic#getLastPostDate()
	 */
	public Date getLastPostDate() {
		if (getLast() != null)
			return getLast().getLastModification();
		return null;
	}

	/**
	 * @see org.openuss.discussion.Topic#getLastPostSubmitterName()
	 */
	public String getLastPostSubmitterName() {
		if (getLast() != null)
			return getLast().getSubmitterName();
		return null;
	}

}