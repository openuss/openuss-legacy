package org.openuss.newsletter;

/**
 * 
 */
public class SubscriberInfo implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -2676155768275163304L;

	public SubscriberInfo() {
		this.blocked = false;
		this.displayName = null;
		this.email = null;
		this.userId = null;
		this.newsletterId = null;
	}

	public SubscriberInfo(boolean blocked, String displayName, String email, Long userId, Long newsletterId) {
		this.blocked = blocked;
		this.displayName = displayName;
		this.email = email;
		this.userId = userId;
		this.newsletterId = newsletterId;
	}

	/**
	 * Copies constructor from other SubscriberInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public SubscriberInfo(SubscriberInfo otherBean) {
		this(otherBean.isBlocked(), otherBean.getDisplayName(), otherBean.getEmail(), otherBean.getUserId(), otherBean
				.getNewsletterId());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(SubscriberInfo otherBean) {
		this.setBlocked(otherBean.isBlocked());
		this.setDisplayName(otherBean.getDisplayName());
		this.setEmail(otherBean.getEmail());
		this.setUserId(otherBean.getUserId());
		this.setNewsletterId(otherBean.getNewsletterId());
	}

	private boolean blocked = false;

	/**
     * 
     */
	public boolean isBlocked() {
		return this.blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	private String displayName;

	/**
     * 
     */
	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	private String email;

	/**
     * 
     */
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private Long userId;

	/**
     * 
     */
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	private Long newsletterId;

	/**
     * 
     */
	public Long getNewsletterId() {
		return this.newsletterId;
	}

	public void setNewsletterId(Long newsletterId) {
		this.newsletterId = newsletterId;
	}

}