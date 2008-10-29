package org.openuss.security.ldap;

import java.util.List;

/**
 * 
 */
public class UserDnPatternInfo implements java.io.Serializable, org.openuss.foundation.DomainObject {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 1169591335098429692L;

	public UserDnPatternInfo() {
		this.id = null;
		this.name = null;
		this.ldapServerIds = null;
	}

	public UserDnPatternInfo(Long id, String name, List ldapServerIds) {
		this.id = id;
		this.name = name;
		this.ldapServerIds = ldapServerIds;
	}

	/**
	 * Copies constructor from other UserDnPatternInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public UserDnPatternInfo(UserDnPatternInfo otherBean) {
		this(otherBean.getId(), otherBean.getName(), otherBean.getLdapServerIds());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(UserDnPatternInfo otherBean) {
		this.setId(otherBean.getId());
		this.setName(otherBean.getName());
		this.setLdapServerIds(otherBean.getLdapServerIds());
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

	private String name;

	/**
     * 
     */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private List ldapServerIds;

	/**
     * 
     */
	public List getLdapServerIds() {
		return this.ldapServerIds;
	}

	public void setLdapServerIds(List ldapServerIds) {
		this.ldapServerIds = ldapServerIds;
	}

	/**
	 * Returns <code>true</code> if the argument is an UserDnPatternInfo
	 * instance and all identifiers for this object equal the identifiers of the
	 * argument object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof UserDnPatternInfo)) {
			return false;
		}
		final UserDnPatternInfo that = (UserDnPatternInfo) object;
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