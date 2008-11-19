package org.openuss.security;

/**
 * 
 */
public class GroupItem implements java.io.Serializable, org.openuss.foundation.DomainObject {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 1386033770220151565L;

	public GroupItem() {
		this.id = null;
		this.name = null;
		this.label = null;
		this.groupType = null;
		this.password = null;
	}

	public GroupItem(Long id, String name, String label, org.openuss.security.GroupType groupType, String password) {
		this.id = id;
		this.name = name;
		this.label = label;
		this.groupType = groupType;
		this.password = password;
	}

	/**
	 * Copies constructor from other GroupItem
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public GroupItem(GroupItem otherBean) {
		this(otherBean.getId(), otherBean.getName(), otherBean.getLabel(), otherBean.getGroupType(), otherBean
				.getPassword());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(GroupItem otherBean) {
		this.setId(otherBean.getId());
		this.setName(otherBean.getName());
		this.setLabel(otherBean.getLabel());
		this.setGroupType(otherBean.getGroupType());
		this.setPassword(otherBean.getPassword());
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

	private String label;

	/**
     * 
     */
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	private org.openuss.security.GroupType groupType;

	/**
     * 
     */
	public org.openuss.security.GroupType getGroupType() {
		return this.groupType;
	}

	public void setGroupType(org.openuss.security.GroupType groupType) {
		this.groupType = groupType;
	}

	private String password;

	/**
     * 
     */
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns <code>true</code> if the argument is an GroupItem instance and
	 * all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof GroupItem)) {
			return false;
		}
		final GroupItem that = (GroupItem) object;
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