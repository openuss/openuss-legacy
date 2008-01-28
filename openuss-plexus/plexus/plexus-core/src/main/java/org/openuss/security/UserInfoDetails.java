package org.openuss.security;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.lang.StringUtils;

public class UserInfoDetails extends UserInfo implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1828012593245034508L;
	private GrantedAuthority[] authorities;
	
	public GrantedAuthority[] getAuthorities() {
		return this.authorities;
	}

	
	/**
	 * Returns equals if it is compared to a string that represents the name of
	 * the user.
	 * 
	 * @see org.acegisecurity.acl.basic.GrantedAuthorityEffectiveAclsResolver
	 *      {@inheritDoc}
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
		} else {
			return super.toString();
		}
	}


	public void setAuthorities(GrantedAuthority[] authorities) {
		this.authorities = authorities;
	}	
}