package org.openuss.security.ldap;

import java.util.List;

/**
 * 
 */
public class AuthenticationDomainInfo implements java.io.Serializable, org.openuss.foundation.DomainObject {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -8920548087559525603L;

	public AuthenticationDomainInfo() {
		this.id = null;
		this.name = null;
		this.description = null;
		this.changePasswordUrl = null;
		this.attributeMappingId = null;
		this.ldapServerIds = null;
	}

	public AuthenticationDomainInfo(Long id, String name, String description, String changePasswordUrl,
			Long attributeMappingId, List ldapServerIds) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.changePasswordUrl = changePasswordUrl;
		this.attributeMappingId = attributeMappingId;
		this.ldapServerIds = ldapServerIds;
	}

	/**
	 * Copies constructor from other AuthenticationDomainInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public AuthenticationDomainInfo(AuthenticationDomainInfo otherBean) {
		this(otherBean.getId(), otherBean.getName(), otherBean.getDescription(), otherBean.getChangePasswordUrl(),
				otherBean.getAttributeMappingId(), otherBean.getLdapServerIds());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(AuthenticationDomainInfo otherBean) {
		this.setId(otherBean.getId());
		this.setName(otherBean.getName());
		this.setDescription(otherBean.getDescription());
		this.setChangePasswordUrl(otherBean.getChangePasswordUrl());
		this.setAttributeMappingId(otherBean.getAttributeMappingId());
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

	private String description;

	/**
     * 
     */
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String changePasswordUrl;

	/**
	 * <p>
	 * Optional URL to a web site, where the user can change her central
	 * password.
	 * </p>
	 */
	public String getChangePasswordUrl() {
		return this.changePasswordUrl;
	}

	public void setChangePasswordUrl(String changePasswordUrl) {
		this.changePasswordUrl = changePasswordUrl;
	}

	private Long attributeMappingId;

	/**
     * 
     */
	public Long getAttributeMappingId() {
		return this.attributeMappingId;
	}

	public void setAttributeMappingId(Long attributeMappingId) {
		this.attributeMappingId = attributeMappingId;
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
	 * Returns <code>true</code> if the argument is an AuthenticationDomainInfo
	 * instance and all identifiers for this object equal the identifiers of the
	 * argument object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof AuthenticationDomainInfo)) {
			return false;
		}
		final AuthenticationDomainInfo that = (AuthenticationDomainInfo) object;
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