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
	
	public UserImpl() {
		setPreferences(UserPreferences.Factory.newInstance());
		setContact(UserContact.Factory.newInstance());
		setProfile(UserProfile.Factory.newInstance());
	}
	

	@Override
	public void setUsername(String username) {
		if (getUsername() == null || !isCentralUser()) { 
			super.setUsername(username.toLowerCase().trim());
		}
	}
	
	@Override
	public void setEmail(String email) {
		super.setEmail(email.toLowerCase().trim());
	}

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

	@SuppressWarnings("deprecation")
	@Override
	public String getTitle() {
		if (getContact() == null) {
			return null;
		} else {
			return getContact().getTitle();
		}
	}

	@Override
	public String getName() {
		return getUsername();
	}

	@SuppressWarnings("deprecation")
	@Override
	public Long getImageId() {
		if (getProfile() == null || getProfile().getImageFileId() == null) {
			return null;
		} else {
			return getProfile().getImageFileId();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setImageId(Long imageId) {
		if (getProfile() == null) {
			setProfile(UserProfile.Factory.newInstance());
		}
		getProfile().setImageFileId(imageId);
	}

	@Override
	public String getDisplayName() {
		return StringUtils.trimToEmpty(getTitle()) + " " + StringUtils.trimToEmpty(getFirstName()) + " "
				+ StringUtils.trimToEmpty(getLastName());
	}

	@SuppressWarnings("deprecation")
	public String getTimezone() {
		return getPreferences().getTimezone();
	}

	@SuppressWarnings("deprecation")
	public void setTimezone(String timezone) {
		getPreferences().setTimezone(timezone);
	}

//	@Override
//	@SuppressWarnings("deprecation")
//	public UserPreferences getPreferences() {
//		if (super.getPreferences() != null) {
//			return super.getPreferences();
//		} else {
//			return UserPreferences.Factory.newInstance();
//		}
//	}

	@SuppressWarnings("deprecation")
	@Override
	public String getLocale() {
		return getPreferences().getLocale();
	}

	@SuppressWarnings("deprecation")
	public void setLocale(String locale) {
		getPreferences().setLocale(locale);
	}

	@Override
	@SuppressWarnings("deprecation")
	public String getSmsEmail() {
		if (getContact() != null) {
			return getContact().getSmsEmail();
		}
		return null;
	}

	@Override
	public boolean hasSmsNotification() {
		return StringUtils.isNotBlank(getSmsEmail());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setFirstName(String firstName) {
		getContact().setFirstName(firstName);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setLastName(String lastName) {
		getContact().setLastName(lastName);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setTitle(String title) {
		getContact().setTitle(title);
	}

	@SuppressWarnings("deprecation")
	@Override
	public UserContact getContact() {
		if (super.getContact() == null) {
			setContact(UserContact.Factory.newInstance());
		}
		return super.getContact();

	}

	@Override
	public boolean isCentralUser() {
		return getUsername().indexOf(SecurityConstants.USERNAME_DOMAIN_DELIMITER)!=-1 ? true : false;
	}

}