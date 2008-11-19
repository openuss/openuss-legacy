package org.openuss.discussion;

import java.io.Serializable;

/**
 * Primary key class for ForumWatch
 */
public class ForumWatchPK implements Serializable {

	private static final long serialVersionUID = -3359760830304783682L;

	private org.openuss.security.User user;

	public org.openuss.security.User getUser() {
		return this.user;
	}

	public void setUser(org.openuss.security.User user) {
		this.user = user;
	}

	private Forum forum;

	public Forum getForum() {
		return this.forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof ForumWatchPK)) {
			return false;
		}
		final ForumWatchPK that = (ForumWatchPK) object;
		return new org.apache.commons.lang.builder.EqualsBuilder().append(this.getUser(), that.getUser()).append(
				this.getForum(), that.getForum()).isEquals();
	}

	public int hashCode() {
		return new org.apache.commons.lang.builder.HashCodeBuilder().append(getUser()).append(getForum()).toHashCode();
	}
}