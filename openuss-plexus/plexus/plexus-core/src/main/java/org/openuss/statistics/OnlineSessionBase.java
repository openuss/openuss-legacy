package org.openuss.statistics;

/**
 * @author Ingo Dueppe
 */
public abstract class OnlineSessionBase implements OnlineSession, java.io.Serializable {

	private static final long serialVersionUID = 7662748194074883939L;

	private java.lang.Long id;

	/**
	 * @see org.openuss.statistics.OnlineSession#getId()
	 */
	public java.lang.Long getId() {
		return this.id;
	}

	/**
	 * @see org.openuss.statistics.OnlineSession#setId(java.lang.Long id)
	 */
	public void setId(java.lang.Long id) {
		this.id = id;
	}

	private java.util.Date startTime;

	/**
	 * @see org.openuss.statistics.OnlineSession#getStartTime()
	 */
	public java.util.Date getStartTime() {
		return this.startTime;
	}

	/**
	 * @see org.openuss.statistics.OnlineSession#setStartTime(java.util.Date
	 *      startTime)
	 */
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	private java.util.Date endTime;

	/**
	 * @see org.openuss.statistics.OnlineSession#getEndTime()
	 */
	public java.util.Date getEndTime() {
		return this.endTime;
	}

	/**
	 * @see org.openuss.statistics.OnlineSession#setEndTime(java.util.Date
	 *      endTime)
	 */
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	private org.openuss.security.User user;

	/**
     * 
     */
	public org.openuss.security.User getUser() {
		return this.user;
	}

	public void setUser(org.openuss.security.User user) {
		this.user = user;
	}

	/**
	 * <p>
	 * If the sessionStart is older then 120 minutes the user session is defined
	 * as expired.
	 * </p>
	 */
	public abstract boolean isExpired();

	/**
	 * <p>
	 * Wether or not this user session is still active, so the user is not loged
	 * out.
	 * </p>
	 * <p>
	 * 
	 * @return true - if session is active
	 *         </p>
	 */
	public abstract boolean isActive();

	/**
	 * Returns <code>true</code> if the argument is an OnlineSession instance
	 * and all identifiers for this entity equal the identifiers of the argument
	 * entity. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof OnlineSession)) {
			return false;
		}
		final OnlineSession that = (OnlineSession) object;
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