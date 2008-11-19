package org.openuss.security.ldap;

import java.util.List;

/**
 * 
 */
public class AttributeMappingInfo implements java.io.Serializable, org.openuss.foundation.DomainObject {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 7715428053618605941L;

	public AttributeMappingInfo() {
		this.id = null;
		this.mappingName = null;
		this.usernameKey = null;
		this.firstNameKey = null;
		this.emailKey = null;
		this.lastNameKey = null;
		this.groupRoleAttributeKey = null;
		this.roleAttributeKeyIds = null;
		this.authenticationDomainIds = null;
	}

	public AttributeMappingInfo(Long id, String mappingName, String usernameKey, String firstNameKey, String emailKey,
			String lastNameKey, String groupRoleAttributeKey, List roleAttributeKeyIds, List authenticationDomainIds) {
		this.id = id;
		this.mappingName = mappingName;
		this.usernameKey = usernameKey;
		this.firstNameKey = firstNameKey;
		this.emailKey = emailKey;
		this.lastNameKey = lastNameKey;
		this.groupRoleAttributeKey = groupRoleAttributeKey;
		this.roleAttributeKeyIds = roleAttributeKeyIds;
		this.authenticationDomainIds = authenticationDomainIds;
	}

	/**
	 * Copies constructor from other AttributeMappingInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public AttributeMappingInfo(AttributeMappingInfo otherBean) {
		this(otherBean.getId(), otherBean.getMappingName(), otherBean.getUsernameKey(), otherBean.getFirstNameKey(),
				otherBean.getEmailKey(), otherBean.getLastNameKey(), otherBean.getGroupRoleAttributeKey(), otherBean
						.getRoleAttributeKeyIds(), otherBean.getAuthenticationDomainIds());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(AttributeMappingInfo otherBean) {
		this.setId(otherBean.getId());
		this.setMappingName(otherBean.getMappingName());
		this.setUsernameKey(otherBean.getUsernameKey());
		this.setFirstNameKey(otherBean.getFirstNameKey());
		this.setEmailKey(otherBean.getEmailKey());
		this.setLastNameKey(otherBean.getLastNameKey());
		this.setGroupRoleAttributeKey(otherBean.getGroupRoleAttributeKey());
		this.setRoleAttributeKeyIds(otherBean.getRoleAttributeKeyIds());
		this.setAuthenticationDomainIds(otherBean.getAuthenticationDomainIds());
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

	private String mappingName;

	/**
     * 
     */
	public String getMappingName() {
		return this.mappingName;
	}

	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}

	private String usernameKey = "cn";

	/**
     * 
     */
	public String getUsernameKey() {
		return this.usernameKey;
	}

	public void setUsernameKey(String usernameKey) {
		this.usernameKey = usernameKey;
	}

	private String firstNameKey = "givenName";

	/**
     * 
     */
	public String getFirstNameKey() {
		return this.firstNameKey;
	}

	public void setFirstNameKey(String firstNameKey) {
		this.firstNameKey = firstNameKey;
	}

	private String emailKey = "mail";

	/**
     * 
     */
	public String getEmailKey() {
		return this.emailKey;
	}

	public void setEmailKey(String emailKey) {
		this.emailKey = emailKey;
	}

	private String lastNameKey = "sn";

	/**
     * 
     */
	public String getLastNameKey() {
		return this.lastNameKey;
	}

	public void setLastNameKey(String lastNameKey) {
		this.lastNameKey = lastNameKey;
	}

	private String groupRoleAttributeKey = "cn";

	/**
     * 
     */
	public String getGroupRoleAttributeKey() {
		return this.groupRoleAttributeKey;
	}

	public void setGroupRoleAttributeKey(String groupRoleAttributeKey) {
		this.groupRoleAttributeKey = groupRoleAttributeKey;
	}

	private List roleAttributeKeyIds;

	/**
     * 
     */
	public List getRoleAttributeKeyIds() {
		return this.roleAttributeKeyIds;
	}

	public void setRoleAttributeKeyIds(List roleAttributeKeyIds) {
		this.roleAttributeKeyIds = roleAttributeKeyIds;
	}

	private List authenticationDomainIds;

	/**
     * 
     */
	public List getAuthenticationDomainIds() {
		return this.authenticationDomainIds;
	}

	public void setAuthenticationDomainIds(List authenticationDomainIds) {
		this.authenticationDomainIds = authenticationDomainIds;
	}

	/**
	 * Returns <code>true</code> if the argument is an AttributeMappingInfo
	 * instance and all identifiers for this object equal the identifiers of the
	 * argument object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof AttributeMappingInfo)) {
			return false;
		}
		final AttributeMappingInfo that = (AttributeMappingInfo) object;
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