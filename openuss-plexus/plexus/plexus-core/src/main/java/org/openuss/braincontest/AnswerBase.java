package org.openuss.braincontest;

public abstract class AnswerBase implements Answer, java.io.Serializable {

	private static final long serialVersionUID = 1092934489770073831L;

	private org.openuss.braincontest.AnswerPK answerPk;

	public org.openuss.braincontest.AnswerPK getAnswerPk() {
		return this.answerPk;
	}

	public void setAnswerPk(org.openuss.braincontest.AnswerPK answerPk) {
		this.answerPk = answerPk;
	}

	private java.util.Date answeredAt;

	/**
	 * @see org.openuss.braincontest.Answer#getAnsweredAt()
	 */
	public java.util.Date getAnsweredAt() {
		return this.answeredAt;
	}

	/**
	 * @see org.openuss.braincontest.Answer#setAnsweredAt(java.util.Date
	 *      answeredAt)
	 */
	public void setAnsweredAt(java.util.Date answeredAt) {
		this.answeredAt = answeredAt;
	}

	public abstract Long getImageId();

	public abstract String getDisplayName();

	/**
	 * Returns <code>true</code> if the argument is an Answer instance and all
	 * identifiers for this entity equal the identifiers of the argument entity.
	 * Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof Answer)) {
			return false;
		}
		final Answer that = (Answer) object;
		if (this.answerPk == null || that.getAnswerPk() == null || !this.answerPk.equals(that.getAnswerPk())) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a hash code based on this entity's identifiers.
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode = 29 * hashCode + (answerPk == null ? 0 : answerPk.hashCode());

		return hashCode;
	}

}