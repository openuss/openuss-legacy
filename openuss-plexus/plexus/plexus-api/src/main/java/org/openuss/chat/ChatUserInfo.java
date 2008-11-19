package org.openuss.chat;

import java.io.Serializable;

/**
 * 
 * @author Ingo Dueppe
 * 
 */
public class ChatUserInfo implements Serializable {

	private static final long serialVersionUID = -34834425037696936L;

	public ChatUserInfo() {
		this.id = null;
		this.displayName = null;
	}

	public ChatUserInfo(Long id, String displayName) {
		this.id = id;
		this.displayName = displayName;
	}

	/**
	 * Copies constructor from other ChatUserInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public ChatUserInfo(ChatUserInfo otherBean) {
		this(otherBean.getId(), otherBean.getDisplayName());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(ChatUserInfo otherBean) {
		this.setId(otherBean.getId());
		this.setDisplayName(otherBean.getDisplayName());
	}

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String displayName;

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Returns <code>true</code> if the argument is an ChatUserInfo instance and
	 * all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof ChatUserInfo)) {
			return false;
		}
		final ChatUserInfo that = (ChatUserInfo) object;
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