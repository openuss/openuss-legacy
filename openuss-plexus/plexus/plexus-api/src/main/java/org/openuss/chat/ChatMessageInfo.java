package org.openuss.chat;

import java.io.Serializable;
import java.util.Date;

public class ChatMessageInfo implements Serializable {

	private static final long serialVersionUID = -2808663185404889546L;

	public ChatMessageInfo() {
		this.id = null;
		this.text = null;
		this.time = null;
		this.displayName = null;
		this.i18n = false;
	}

	public ChatMessageInfo(Long id, String text, Date time, String displayName, boolean i18n) {
		this.id = id;
		this.text = text;
		this.time = time;
		this.displayName = displayName;
		this.i18n = i18n;
	}

	/**
	 * Copies constructor from other ChatMessageInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public ChatMessageInfo(ChatMessageInfo otherBean) {
		this(otherBean.getId(), otherBean.getText(), otherBean.getTime(), otherBean.getDisplayName(), otherBean
				.isI18n());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(ChatMessageInfo otherBean) {
		this.setId(otherBean.getId());
		this.setText(otherBean.getText());
		this.setTime(otherBean.getTime());
		this.setDisplayName(otherBean.getDisplayName());
		this.setI18n(otherBean.isI18n());
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

	private String text;

	/**
     * 
     */
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private Date time;

	/**
     * 
     */
	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
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

	private boolean i18n;

	/**
     * 
     */
	public boolean isI18n() {
		return this.i18n;
	}

	public void setI18n(boolean i18n) {
		this.i18n = i18n;
	}

	/**
	 * Returns <code>true</code> if the argument is an ChatMessageInfo instance
	 * and all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof ChatMessageInfo)) {
			return false;
		}
		final ChatMessageInfo that = (ChatMessageInfo) object;
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