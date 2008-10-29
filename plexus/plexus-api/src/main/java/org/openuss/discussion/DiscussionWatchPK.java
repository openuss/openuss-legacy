package org.openuss.discussion;

/**
 * Primary key class for DiscussionWatch
 */
public class DiscussionWatchPK implements java.io.Serializable {

	private static final long serialVersionUID = 4904346669649837008L;

	private org.openuss.security.User user;

	public org.openuss.security.User getUser() {
		return this.user;
	}

	public void setUser(org.openuss.security.User user) {
		this.user = user;
	}

	private org.openuss.discussion.Topic topic;

	public org.openuss.discussion.Topic getTopic() {
		return this.topic;
	}

	public void setTopic(org.openuss.discussion.Topic topic) {
		this.topic = topic;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof DiscussionWatchPK)) {
			return false;
		}
		final DiscussionWatchPK that = (DiscussionWatchPK) object;
		return new org.apache.commons.lang.builder.EqualsBuilder().append(this.getUser(), that.getUser()).append(
				this.getTopic(), that.getTopic()).isEquals();
	}

	public int hashCode() {
		return new org.apache.commons.lang.builder.HashCodeBuilder().append(getUser()).append(getTopic()).toHashCode();
	}
}