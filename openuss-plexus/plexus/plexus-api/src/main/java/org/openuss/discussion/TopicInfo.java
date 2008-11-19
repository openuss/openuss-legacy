package org.openuss.discussion;

import java.io.Serializable;
import java.util.Date;

import org.openuss.foundation.DomainObject;

/**
 *  @author Ingo Dueppe
 */
public class TopicInfo implements Serializable, DomainObject {

	private static final long serialVersionUID = 669946994318411685L;

	public TopicInfo() {
		this.topicId = null;
		this.forumId = null;
		this.title = null;
		this.created = null;
		this.submitter = null;
		this.answerCount = null;
		this.viewState = null;
		this.lastPost = null;
		this.lastPostSubmitterName = null;
		this.hits = null;
		this.readOnly = false;
		this.id = null;
	}

	public TopicInfo(Long topicId, Long forumId, String title, Date created, String submitter, Integer answerCount,
			org.openuss.viewtracking.ViewState viewState, Date lastPost, String lastPostSubmitterName, Integer hits,
			boolean readOnly, Long id) {
		this.topicId = topicId;
		this.forumId = forumId;
		this.title = title;
		this.created = created;
		this.submitter = submitter;
		this.answerCount = answerCount;
		this.viewState = viewState;
		this.lastPost = lastPost;
		this.lastPostSubmitterName = lastPostSubmitterName;
		this.hits = hits;
		this.readOnly = readOnly;
		this.id = id;
	}

	/**
	 * Copies constructor from other TopicInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public TopicInfo(TopicInfo otherBean) {
		this(otherBean.getTopicId(), otherBean.getForumId(), otherBean.getTitle(), otherBean.getCreated(), otherBean
				.getSubmitter(), otherBean.getAnswerCount(), otherBean.getViewState(), otherBean.getLastPost(),
				otherBean.getLastPostSubmitterName(), otherBean.getHits(), otherBean.isReadOnly(), otherBean.getId());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(TopicInfo otherBean) {
		this.setTopicId(otherBean.getTopicId());
		this.setForumId(otherBean.getForumId());
		this.setTitle(otherBean.getTitle());
		this.setCreated(otherBean.getCreated());
		this.setSubmitter(otherBean.getSubmitter());
		this.setAnswerCount(otherBean.getAnswerCount());
		this.setViewState(otherBean.getViewState());
		this.setLastPost(otherBean.getLastPost());
		this.setLastPostSubmitterName(otherBean.getLastPostSubmitterName());
		this.setHits(otherBean.getHits());
		this.setReadOnly(otherBean.isReadOnly());
		this.setId(otherBean.getId());
	}

	private Long topicId;

	/**
     * 
     */
	public Long getTopicId() {
		return this.topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	private Long forumId;

	/**
     * 
     */
	public Long getForumId() {
		return this.forumId;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}

	private String title;

	/**
     * 
     */
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private Date created;

	/**
     * 
     */
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	private String submitter;

	/**
     * 
     */
	public String getSubmitter() {
		return this.submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	private Integer answerCount;

	/**
     * 
     */
	public Integer getAnswerCount() {
		return this.answerCount;
	}

	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}

	private org.openuss.viewtracking.ViewState viewState;

	/**
     * 
     */
	public org.openuss.viewtracking.ViewState getViewState() {
		return this.viewState;
	}

	public void setViewState(org.openuss.viewtracking.ViewState viewState) {
		this.viewState = viewState;
	}

	private Date lastPost;

	/**
     * 
     */
	public Date getLastPost() {
		return this.lastPost;
	}

	public void setLastPost(Date lastPost) {
		this.lastPost = lastPost;
	}

	private String lastPostSubmitterName;

	/**
     * 
     */
	public String getLastPostSubmitterName() {
		return this.lastPostSubmitterName;
	}

	public void setLastPostSubmitterName(String lastPostSubmitterName) {
		this.lastPostSubmitterName = lastPostSubmitterName;
	}

	private Integer hits;

	/**
     * 
     */
	public Integer getHits() {
		return this.hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	private boolean readOnly;

	/**
     * 
     */
	public boolean isReadOnly() {
		return this.readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	private Long id;

	/**
     * 
     */
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns <code>true</code> if the argument is an TopicInfo instance and
	 * all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof TopicInfo)) {
			return false;
		}
		final TopicInfo that = (TopicInfo) object;
		if (this.id == null || that.getId() == null || !this.id.equals(that.getId())) {
			return false;
		}

		return true;
	}

	/**
	 * Returns a hash code based on this entity's identifiers.
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode = 29 * hashCode + (id == null ? 0 : id.hashCode());

		return hashCode;
	}

}