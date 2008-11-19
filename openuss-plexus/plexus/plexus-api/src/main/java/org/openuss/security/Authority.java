package org.openuss.security;

import java.util.List;
import java.util.Set;

import org.openuss.foundation.DomainObject;

/**
 * @author Ingo Dueppe
 */
public interface Authority extends DomainObject {

	public Long getId();

	public void setId(Long id);

	public String getName();

	public void setName(String name);

	public List<Group> getGroups();

	public void setGroups(List<Group> groups);

	/**
	 * Get all groups where this groups is a direct or indirect member.
	 */
	public abstract Set getGrantedGroups();

	/**
	 * Adds the given role to the users authority list.
	 */
	public abstract void addGroup(Group group);

	/**
	 * Removes the given authority from the users authority list.
	 */
	public abstract void removeGroup(Group group);

}