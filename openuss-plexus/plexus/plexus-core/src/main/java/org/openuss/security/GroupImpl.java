// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import org.acegisecurity.GrantedAuthority;
import org.apache.commons.lang.StringUtils;

/**
 * @see org.openuss.security.Group
 * @author ingo düppe
 */
public class GroupImpl extends org.openuss.security.GroupBase implements org.openuss.security.Group, GrantedAuthority {

	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -8497294784138124010L;

	public GroupImpl() {}

	/**
	 * @param id
	 */
	public GroupImpl(Long id) {
		setId(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addMember(Authority authority) {
		if (authority != null && !equals(authority)) { 
			if (!getMembers().contains(authority)){
				getMembers().add(authority);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeMember(Authority authority) {
		if (authority != null) {
			getMembers().remove(authority);
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getAuthority() {
		return getName();
	}
	
	
	/**
	 * Returns equals if it is compared to a string that represents the name of
	 * the user.
	 * 
	 * @see org.acegisecurity.acl.basic.GrantedAuthorityEffectiveAclsResolver
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object object) {
		if (object instanceof String && !StringUtils.isBlank(getName())) {
			return this.getName().equals(object);
		}
		return super.equals(object);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		if (!StringUtils.isBlank(getName())) {
			return getName();
		} else {
			return super.toString();
		}
	}


}