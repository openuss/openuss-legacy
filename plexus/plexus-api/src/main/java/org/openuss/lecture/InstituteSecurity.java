package org.openuss.lecture;

import java.util.List;

/**
 * @author Ingo Dueppe
 */
public class InstituteSecurity implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 872498003234748230L;

	public InstituteSecurity() {
	}

	public InstituteSecurity(List<InstituteGroup> groups, List<InstituteMember> members) {
		this.groups = groups;
		this.members = members;
	}

	/**
	 * Copies constructor from other InstituteSecurity
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public InstituteSecurity(InstituteSecurity otherBean) {
		this(otherBean.getGroups(), otherBean.getMembers());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(InstituteSecurity otherBean) {
		this.setGroups(otherBean.getGroups());
		this.setMembers(otherBean.getMembers());
	}

	private List<InstituteGroup> groups;

	/**
	 * Get the groups
	 * 
	 */
	public List<InstituteGroup> getGroups() {
		return this.groups;
	}

	/**
	 * Sets the groups
	 */
	public void setGroups(List<InstituteGroup> groups) {
		this.groups = groups;
	}

	private List<InstituteMember> members;

	/**
	 * Get the members
	 * 
	 */
	public List<InstituteMember> getMembers() {
		return this.members;
	}

	/**
	 * Sets the members
	 */
	public void setMembers(List<InstituteMember> members) {
		this.members = members;
	}

}