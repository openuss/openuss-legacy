// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openuss.foundation.DomainObject;

/**
 * @see org.openuss.security.Authority
 * @author Ingo Dueppe
 */
public abstract class AuthorityImpl extends AuthorityBase implements Authority {

	private static final long serialVersionUID = -8268493244078008798L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addGroup(Group group) {
		if (group != null && !getGroups().contains(group)) {
			getGroups().add(group);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeGroup(Group group) {
		if (group != null) {
			getGroups().remove(group);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<Group> getGrantedGroups() {
		return fetchSuperGroups(getGroups());
	}

	/**
	 * Gets all groups that this group is depending on. 
	 * @param groups
	 * @return
	 */
	private Set<Group> fetchSuperGroups(List<Group> groups) {
		Set<Group> authorities = new HashSet<Group>();
		authorities.addAll(groups);

		for (Group group : groups) {
			if (group.getGroups().size() > 0) {
				authorities.addAll(fetchSuperGroups(group.getGroups()));
			}
		}
		return authorities;
	}

	@Override
	public boolean equals(Object object) {
		if (getId()  != null && object instanceof DomainObject) {
			return getId().equals(((DomainObject)object).getId());
		}
		return super.equals(object);
	}
}