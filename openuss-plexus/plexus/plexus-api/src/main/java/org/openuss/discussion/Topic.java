package org.openuss.discussion;

import java.util.Date;
import java.util.List;

import org.openuss.foundation.DomainObject;

/**
 * 
 * @author Ingo Dueppe
 * 
 */
public interface Topic extends DomainObject {

	public Long getId();

	public void setId(Long id);

	public Integer getHits();

	public void setHits(Integer hits);

	public boolean isReadOnly();

	public void setReadOnly(boolean readOnly);

	public org.openuss.security.User getSubmitter();

	public void setSubmitter(org.openuss.security.User submitter);

	public List<org.openuss.discussion.Post> getPosts();

	public void setPosts(List<org.openuss.discussion.Post> posts);

	public org.openuss.discussion.Post getFirst();

	public void setFirst(org.openuss.discussion.Post first);

	public org.openuss.discussion.Post getLast();

	public void setLast(org.openuss.discussion.Post last);

	public org.openuss.discussion.Forum getForum();

	public void setForum(org.openuss.discussion.Forum forum);

	public void addPost(org.openuss.discussion.Post post);

	public void removePost(org.openuss.discussion.Post post);

	/**
	 * @return Integer - number of replies
	 */
	public Integer getAnswerCount();

	public Date getCreated();

	/**
	 * @return string - the title of the first post
	 */
	public String getTitle();

	public Date getLastPostDate();

	public String getLastPostSubmitterName();

}