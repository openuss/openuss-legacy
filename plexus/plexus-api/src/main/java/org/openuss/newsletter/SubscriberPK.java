package org.openuss.newsletter;

/**
 * Primary key class for Subscriber
 */
public class SubscriberPK implements java.io.Serializable {

	private static final long serialVersionUID = -7222328325065408963L;

	private org.openuss.newsletter.Newsletter newsletter;

	public org.openuss.newsletter.Newsletter getNewsletter() {
		return this.newsletter;
	}

	public void setNewsletter(org.openuss.newsletter.Newsletter newsletter) {
		this.newsletter = newsletter;
	}

	private org.openuss.security.User user;

	public org.openuss.security.User getUser() {
		return this.user;
	}

	public void setUser(org.openuss.security.User user) {
		this.user = user;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof SubscriberPK)) {
			return false;
		}
		final SubscriberPK that = (SubscriberPK) object;
		return new org.apache.commons.lang.builder.EqualsBuilder().append(this.getNewsletter(), that.getNewsletter())
				.append(this.getUser(), that.getUser()).isEquals();
	}

	public int hashCode() {
		return new org.apache.commons.lang.builder.HashCodeBuilder().append(getNewsletter()).append(getUser())
				.toHashCode();
	}
}