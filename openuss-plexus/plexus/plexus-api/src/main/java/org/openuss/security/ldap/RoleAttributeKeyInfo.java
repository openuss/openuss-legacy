package org.openuss.security.ldap;

import java.util.List;

/**
 * 
 */
public class RoleAttributeKeyInfo implements java.io.Serializable, org.openuss.foundation.DomainObject {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 6688071661743825464L;

	public RoleAttributeKeyInfo() {
		this.id = null;
		this.name = null;
		this.attributeMappingIds = null;
	}

	public RoleAttributeKeyInfo(Long id, String name, List attributeMappingIds) {
		this.id = id;
		this.name = name;
		this.attributeMappingIds = attributeMappingIds;
	}

	/**
	 * Copies constructor from other RoleAttributeKeyInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public RoleAttributeKeyInfo(RoleAttributeKeyInfo otherBean) {
		this(otherBean.getId(), otherBean.getName(), otherBean.getAttributeMappingIds());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(RoleAttributeKeyInfo otherBean) {
		this.setId(otherBean.getId());
		this.setName(otherBean.getName());
		this.setAttributeMappingIds(otherBean.getAttributeMappingIds());
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

	private String name = "memberOf";

	/**
     * 
     */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private List attributeMappingIds;

	/**
     * 
     */
	public List getAttributeMappingIds() {
		return this.attributeMappingIds;
	}

	public void setAttributeMappingIds(List attributeMappingIds) {
		this.attributeMappingIds = attributeMappingIds;
	}

	/**
	 * Returns <code>true</code> if the argument is an RoleAttributeKeyInfo
	 * instance and all identifiers for this object equal the identifiers of the
	 * argument object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof RoleAttributeKeyInfo)) {
			return false;
		}
		final RoleAttributeKeyInfo that = (RoleAttributeKeyInfo) object;
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