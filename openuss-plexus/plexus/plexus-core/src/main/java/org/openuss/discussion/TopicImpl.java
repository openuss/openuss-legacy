// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;

import java.util.Date;

import org.apache.commons.lang.Validate;

/**
 * @author ingo dueppe
 * @author sebasitan roekens
 * @see org.openuss.discussion.Topic
 */
public class TopicImpl extends TopicBase implements Topic {

	private static final long serialVersionUID = 8403531529041681845L;

	/**
	 * @see org.openuss.discussion.Topic#addPost(org.openuss.discussion.Post)
	 */
	public void addPost(Post post) {
		Validate.notNull(post, "parameter post must not be null");
		post.setTopic(this);
		if (getFirst() == null) {
			setFirst(post);
		}
		setLast(post);
		getPosts().add(post);
	}

	/**
	 * @see org.openuss.discussion.Topic#removePost(org.openuss.discussion.Post)
	 */
	public void removePost(Post post) {
		Validate.notNull(post, "parameter post must not be null");
		getPosts().remove(post);
		if (post.getTopic().equals(this)) {
			post.setTopic(null);
		}
		if (getPosts().isEmpty()) {
			setFirst(null);
			setLast(null);
		} else {
			setFirst(getPosts().get(0));
			setLast(getPosts().get(getPosts().size()-1));
		}
	}

	/**
	 * @see org.openuss.discussion.Topic#getAnswerCount()
	 */
	public Integer getAnswerCount() {
		if (getPosts().isEmpty()) {
			return 0;
		} else {
			return getPosts().size() - 1;
		}
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
		if (getFirst() != null) {
			return getFirst().getTitle();
		} else {
			return null;
		}
	}

	/**
	 * @see org.openuss.discussion.Topic#getLastPostDate()
	 */
	public Date getLastPostDate() {
		if (getLast() != null) {
			return getLast().getLastModification();
		} else {
			return null;
		}
	}

	/**
	 * @see org.openuss.discussion.Topic#getLastPostSubmitterName()
	 */
	public String getLastPostSubmitterName() {
		if (getLast() != null) {
			return getLast().getSubmitterName();
		} else {
			return null;
		}
	}

	@Override
	public Integer getHits() {
		if (super.getHits() == null) {
			return 0;
		} else {
			return super.getHits();
		}
	}
	
	

}