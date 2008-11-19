package org.openuss.discussion;

/**
 * 
 */
public abstract class DiscussionWatchBase implements DiscussionWatch, java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 4904346669649837008L;

	private org.openuss.discussion.DiscussionWatchPK discussionWatchPk;

	public org.openuss.discussion.DiscussionWatchPK getDiscussionWatchPk() {
		return this.discussionWatchPk;
	}

	public void setDiscussionWatchPk(org.openuss.discussion.DiscussionWatchPK discussionWatchPk) {
		this.discussionWatchPk = discussionWatchPk;
	}

	/**
	 * Returns <code>true</code> if the argument is an DiscussionWatch instance
	 * and all identifiers for this entity equal the identifiers of the argument
	 * entity. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof DiscussionWatch)) {
			return false;
		}
		final DiscussionWatch that = (DiscussionWatch) object;
		if (this.discussionWatchPk == null || that.getDiscussionWatchPk() == null
				|| !this.discussionWatchPk.equals(that.getDiscussionWatchPk())) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a hash code based on this entity's identifiers.
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode = 29 * hashCode + (discussionWatchPk == null ? 0 : discussionWatchPk.hashCode());

		return hashCode;
	}

}