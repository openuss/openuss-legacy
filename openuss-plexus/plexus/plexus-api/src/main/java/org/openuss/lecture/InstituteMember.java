package org.openuss.lecture;

import java.util.List;

/**
 * @author Ingo Dueppe 
 */
public class InstituteMember implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -7319588340554027763L;

	public InstituteMember() {
		this.id = null;
		this.username = null;
		this.firstName = null;
		this.lastName = null;
	}

	public InstituteMember(Long id, String username, String firstName, String lastName) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public InstituteMember(Long id, String username, String firstName, String lastName,
			List<org.openuss.lecture.InstituteGroup> groups) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.groups = groups;
	}

	/**
	 * Copies constructor from other InstituteMember
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public InstituteMember(InstituteMember otherBean) {
		this(otherBean.getId(), otherBean.getUsername(), otherBean.getFirstName(), otherBean.getLastName(), otherBean
				.getGroups());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(InstituteMember otherBean) {
		this.setId(otherBean.getId());
		this.setUsername(otherBean.getUsername());
		this.setFirstName(otherBean.getFirstName());
		this.setLastName(otherBean.getLastName());
		this.setGroups(otherBean.getGroups());
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

	private String firstName;

	/**
     * 
     */
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	private String lastName;

	/**
     * 
     */
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	private List<org.openuss.lecture.InstituteGroup> groups;

	/**
	 * Get the groups
	 * 
	 */
	public List<org.openuss.lecture.InstituteGroup> getGroups() {
		return this.groups;
	}

	/**
	 * Sets the groups
	 */
	public void setGroups(List<org.openuss.lecture.InstituteGroup> groups) {
		this.groups = groups;
	}

	/**
	 * Returns <code>true</code> if the argument is an InstituteMember instance
	 * and all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof InstituteMember)) {
			return false;
		}
		final InstituteMember that = (InstituteMember) object;
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