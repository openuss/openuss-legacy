package org.openuss.collaboration;

import java.io.Serializable;

import org.openuss.foundation.DomainObject;

/**
 * ValueObject which contains all the necessary info to identify a workspace
 * 
 * @atrib id specific id for a workspace
 * 
 * @atrib name specific name for a workspace
 * 
 * @atrib courseId id from the course in which the workspace has been created
 * 
 * @author Projektseminar WS 07/08, Team Collaboration
 */
public class WorkspaceInfo implements Serializable, DomainObject {

	private static final long serialVersionUID = 1742992250768963803L;

	public WorkspaceInfo() {
		this.id = null;
		this.domainId = null;
		this.name = null;
	}

	public WorkspaceInfo(Long id, Long domainId, String name) {
		this.id = id;
		this.domainId = domainId;
		this.name = name;
	}

	/**
	 * Copies constructor from other WorkspaceInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public WorkspaceInfo(WorkspaceInfo otherBean) {
		this(otherBean.getId(), otherBean.getDomainId(), otherBean.getName());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(WorkspaceInfo otherBean) {
		this.setId(otherBean.getId());
		this.setDomainId(otherBean.getDomainId());
		this.setName(otherBean.getName());
	}

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long domainId;

	public Long getDomainId() {
		return this.domainId;
	}

	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns <code>true</code> if the argument is an WorkspaceInfo instance
	 * and all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof WorkspaceInfo)) {
			return false;
		}
		final WorkspaceInfo that = (WorkspaceInfo) object;
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