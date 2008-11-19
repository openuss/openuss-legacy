package org.openuss.statistics;

import java.util.Date;

/**
 * 
 */
public class OnlineUserInfo implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -6809453591251417019L;

	public OnlineUserInfo() {
		this.id = null;
		this.startTime = null;
		this.endTime = null;
		this.userId = null;
		this.username = null;
		this.displayName = null;
		this.imageId = null;
	}

	public OnlineUserInfo(Long id, Date startTime, Date endTime, Long userId, String username, String displayName,
			Long imageId) {
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.userId = userId;
		this.username = username;
		this.displayName = displayName;
		this.imageId = imageId;
	}

	/**
	 * Copies constructor from other OnlineUserInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public OnlineUserInfo(OnlineUserInfo otherBean) {
		this(otherBean.getId(), otherBean.getStartTime(), otherBean.getEndTime(), otherBean.getUserId(), otherBean
				.getUsername(), otherBean.getDisplayName(), otherBean.getImageId());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(OnlineUserInfo otherBean) {
		this.setId(otherBean.getId());
		this.setStartTime(otherBean.getStartTime());
		this.setEndTime(otherBean.getEndTime());
		this.setUserId(otherBean.getUserId());
		this.setUsername(otherBean.getUsername());
		this.setDisplayName(otherBean.getDisplayName());
		this.setImageId(otherBean.getImageId());
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

	private Date startTime;

	/**
     * 
     */
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	private Date endTime;

	/**
     * 
     */
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	private String username;

	/**
     * 
     */
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	private Long imageId;

	/**
     * 
     */
	public Long getImageId() {
		return this.imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	/**
	 * Returns <code>true</code> if the argument is an OnlineUserInfo instance
	 * and all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof OnlineUserInfo)) {
			return false;
		}
		final OnlineUserInfo that = (OnlineUserInfo) object;
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