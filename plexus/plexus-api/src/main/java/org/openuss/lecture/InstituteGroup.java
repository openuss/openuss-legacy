package org.openuss.lecture;

import java.io.Serializable;
import java.util.List;

/**
 *  @author Ingo Dueppe
 */
public class InstituteGroup implements Serializable {

	private static final long serialVersionUID = 2225592607261708339L;

	public InstituteGroup() {
		this.id = null;
		this.name = null;
		this.label = null;
	}

	public InstituteGroup(Long id, String name, String label) {
		this.id = id;
		this.name = name;
		this.label = label;
	}

	public InstituteGroup(Long id, String name, String label, List<org.openuss.lecture.InstituteMember> members) {
		this.id = id;
		this.name = name;
		this.label = label;
		this.members = members;
	}

	/**
	 * Copies constructor from other InstituteGroup
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public InstituteGroup(InstituteGroup otherBean) {
		this(otherBean.getId(), otherBean.getName(), otherBean.getLabel(), otherBean.getMembers());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(InstituteGroup otherBean) {
		this.setId(otherBean.getId());
		this.setName(otherBean.getName());
		this.setLabel(otherBean.getLabel());
		this.setMembers(otherBean.getMembers());
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

	private List<org.openuss.lecture.InstituteMember> members;

	/**
	 * Get the members
	 * 
	 */
	public List<org.openuss.lecture.InstituteMember> getMembers() {
		return this.members;
	}

	/**
	 * Sets the members
	 */
	public void setMembers(List<org.openuss.lecture.InstituteMember> members) {
		this.members = members;
	}

	/**
	 * Returns <code>true</code> if the argument is an InstituteGroup instance
	 * and all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof InstituteGroup)) {
			return false;
		}
		final InstituteGroup that = (InstituteGroup) object;
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