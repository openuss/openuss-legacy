package org.openuss.discussion;

import java.io.Serializable;

import org.openuss.foundation.DomainObject;

/**
 * @author Ingo Dueppe
 */
public class ForumInfo implements Serializable, DomainObject {

	private static final long serialVersionUID = 3319166831448246399L;

	public ForumInfo() {
		this.domainIdentifier = null;
		this.readOnly = false;
		this.id = null;
	}

	public ForumInfo(Long domainIdentifier, boolean readOnly, Long id) {
		this.domainIdentifier = domainIdentifier;
		this.readOnly = readOnly;
		this.id = id;
	}

	/**
	 * Copies constructor from other ForumInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public ForumInfo(ForumInfo otherBean) {
		this(otherBean.getDomainIdentifier(), otherBean.isReadOnly(), otherBean.getId());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(ForumInfo otherBean) {
		this.setDomainIdentifier(otherBean.getDomainIdentifier());
		this.setReadOnly(otherBean.isReadOnly());
		this.setId(otherBean.getId());
	}

	private Long domainIdentifier;

	/**
     * 
     */
	public Long getDomainIdentifier() {
		return this.domainIdentifier;
	}

	public void setDomainIdentifier(Long domainIdentifier) {
		this.domainIdentifier = domainIdentifier;
	}

	private boolean readOnly;

	/**
     * 
     */
	public boolean isReadOnly() {
		return this.readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
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

	/**
	 * Returns <code>true</code> if the argument is an ForumInfo instance and
	 * all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof ForumInfo)) {
			return false;
		}
		final ForumInfo that = (ForumInfo) object;
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