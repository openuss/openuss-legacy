package org.openuss.security.acegi;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.lang.StringUtils;
import org.openuss.security.User;
import org.openuss.security.UserInfo;

/**
 * Adapter class between UserInfo and Acegi UserDetails
 * @author Ingo Dueppe
 *
 */
public class UserInfoDetailsAdapter extends UserInfo implements UserDetails{

	private static final long serialVersionUID = -1828012593245034508L;
	
	private GrantedAuthority[] grantedAuthorities;
	
	public UserInfoDetailsAdapter(UserInfo userInfo, String[] authorities) {
		super(userInfo);
		
		grantedAuthorities = new StringGrantedAuthority[authorities.length];
		for(int i = 0; i < authorities.length; i++) {
			grantedAuthorities[i] = new StringGrantedAuthority(authorities[i]);
		}
	}
	
	public GrantedAuthority[] getAuthorities() {
		return this.grantedAuthorities;
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
		if (object instanceof User) {
			return this.getId().equals(((User)object).getId()); 
		}
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
}