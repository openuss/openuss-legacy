// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import java.util.Locale;
import java.util.Set;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.lang.StringUtils;

/**
 * @see org.openuss.security.User
 * @author Ingo Dueppe
 */
public class UserImpl extends UserBase implements User, UserDetails {

	private static final long serialVersionUID = 4562337137383225187L;

	public UserImpl() {
		setLocale("de");
		setTimezone("Europe/Berlin");
		setTheme("plexus");
	}

	public void setUsername(String username) {
		setName(username);
	}

	public String getUsername() {
		return super.getName();
	}

	@Override
	public void setName(String name) {
		if (StringUtils.isNotBlank(name)) {
			if (StringUtils.isBlank(getName()) || !isCentralUser()) {
				super.setName(name.toLowerCase(Locale.ENGLISH).trim());
			}
		} else {
			if (StringUtils.isBlank(getName()) || !isCentralUser()) {
				super.setName("");
			}
		}
	}

	@Override
	public void setEmail(String email) {
		super.setEmail(email.toLowerCase(Locale.ENGLISH).trim());
	}

	/**
	 * @see org.openuss.security.User#getGrantedAuthorities()
	 */
	public org.acegisecurity.GrantedAuthority[] getAuthorities() {
		// expected that all groups beans implements GrantedAuthority interface
		Set<Group> authorities = getGrantedGroups();
		return (GrantedAuthority[]) authorities.toArray(new GrantedAuthority[authorities.size()]);
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

	@Override
	public int hashCode() {
		return super.hashCode();
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

	@Override
	public String getDisplayName() {
		return StringUtils.trimToEmpty(getTitle()) + " " + StringUtils.trimToEmpty(getFirstName()) + " "
				+ StringUtils.trimToEmpty(getLastName());
	}

	@Override
	public boolean hasSmsNotification() {
		return StringUtils.isNotBlank(getSmsEmail());
	}

	public String[] getGrantedAuthorities() {
		GrantedAuthority[] authorities = getAuthorities();
		String[] authorityNames = new String[authorities.length];
		for (int i = 0; i < authorities.length; i++) {
			authorityNames[i] = authorities[i].getAuthority();
		}
		return authorityNames;
	}

	@Override
	public String getPortrait() {
		return super.getPortrait() != null ? super.getPortrait() : "";
	}

	@Override
	public boolean isCentralUser() {
		return SecurityDomainUtility.containsDomain(getUsername());
	}

}