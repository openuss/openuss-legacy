// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import java.util.List;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.lang.StringUtils;

/**
 * @see org.openuss.security.User
 * @author Ingo Dueppe
 */
public class UserImpl extends UserBase implements User, UserDetails {

	private static final long serialVersionUID = 4562337137383225187L;
	
	/**
	 * @see org.openuss.security.User#getGrantedAuthorities()
	 */
	public org.acegisecurity.GrantedAuthority[] getAuthorities() {
		// expected that all groups beans implements GrantedAuthority interface
		List<Group> authorities = getGrantedGroups(); 
		
		return (GrantedAuthority[]) authorities.toArray(new GrantedAuthority[authorities.size()]);
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
		if (object instanceof String) {
			return this.getUsername().equals(object);
		}
		return super.equals(object);
	}


	/**
	 * @see org.openuss.security.User#isAccountNonLocked()
	 */
	public boolean isAccountNonLocked() {
		return !isAccountLocked();
	}

	/**
	 * @see org.openuss.security.User#isAccountNonExpired()
	 */
	public boolean isAccountNonExpired() {
		return !isAccountExpired();
	}

	/**
	 * @see org.openuss.security.User#isCredentialsNonExpired()
	 */
	public boolean isCredentialsNonExpired() {
		return !isCredentialsExpired();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		if (!StringUtils.isBlank(getUsername())) {
			return getUsername();
		} else  {
			return super.toString();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public String getFirstName() {
		if (getContact() == null) {
			return null;
		} else {
			return getContact().getFirstName();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public String getLastName() {
		if (getContact() == null) {
			return null;
		} else {
			return getContact().getLastName();
		}
	}

	@Override
	public String getTitle() {
		if (getContact() == null) {
			return null;
		} else {
			return getContact().getTitle();			
		}
	}

}