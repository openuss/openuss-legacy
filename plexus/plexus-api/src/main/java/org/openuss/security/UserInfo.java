package org.openuss.security;

import java.util.Date;

/**
 * 
 */
public class UserInfo implements java.io.Serializable, org.openuss.foundation.DomainObject {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 4911747984128801123L;

	public UserInfo() {
		this.id = null;
		this.username = null;
		this.password = null;
		this.email = null;
		this.enabled = false;
		this.accountExpired = false;
		this.accountLocked = false;
		this.lastLogin = null;
		this.credentialsExpired = false;
		this.imageId = null;
		this.displayName = null;
		this.smsNotification = false;
		this.theme = null;
		this.locale = null;
		this.timezone = null;
		this.emailPublic = false;
		this.addressPublic = false;
		this.telephonePublic = false;
		this.portraitPublic = false;
		this.imagePublic = false;
		this.profilePublic = false;
		this.portrait = null;
		this.address = null;
		this.title = null;
		this.firstName = null;
		this.lastName = null;
		this.newsletterSubscriptionEnabled = false;
		this.discussionSubscriptionEnabled = false;
		this.centralUser = false;
		this.domainName = null;
		this.deleted = false;
	}

	public UserInfo(Long id, String username, String password, String email, boolean enabled, boolean accountExpired,
			boolean accountLocked, Date lastLogin, boolean credentialsExpired, Long imageId, String displayName,
			boolean smsNotification, String theme, String locale, String timezone, boolean emailPublic,
			boolean addressPublic, boolean telephonePublic, boolean portraitPublic, boolean imagePublic,
			boolean profilePublic, String portrait, String address, String title, String firstName, String lastName,
			boolean newsletterSubscriptionEnabled, boolean discussionSubscriptionEnabled, boolean centralUser,
			String domainName, boolean deleted) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
		this.accountExpired = accountExpired;
		this.accountLocked = accountLocked;
		this.lastLogin = lastLogin;
		this.credentialsExpired = credentialsExpired;
		this.imageId = imageId;
		this.displayName = displayName;
		this.smsNotification = smsNotification;
		this.theme = theme;
		this.locale = locale;
		this.timezone = timezone;
		this.emailPublic = emailPublic;
		this.addressPublic = addressPublic;
		this.telephonePublic = telephonePublic;
		this.portraitPublic = portraitPublic;
		this.imagePublic = imagePublic;
		this.profilePublic = profilePublic;
		this.portrait = portrait;
		this.address = address;
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.newsletterSubscriptionEnabled = newsletterSubscriptionEnabled;
		this.discussionSubscriptionEnabled = discussionSubscriptionEnabled;
		this.centralUser = centralUser;
		this.domainName = domainName;
		this.deleted = deleted;
	}

	public UserInfo(Long id, String username, String password, String email, boolean enabled, boolean accountExpired,
			boolean accountLocked, Date lastLogin, boolean credentialsExpired, Long imageId, String displayName,
			boolean smsNotification, String theme, String locale, String timezone, boolean emailPublic,
			boolean addressPublic, boolean telephonePublic, boolean portraitPublic, boolean imagePublic,
			boolean profilePublic, String portrait, String matriculation, String studies, String smsEmail,
			String profession, String city, String country, String telephone, String postcode, String ageGroup,
			String address, String title, String firstName, String lastName, boolean newsletterSubscriptionEnabled,
			boolean discussionSubscriptionEnabled, boolean centralUser, String domainName, boolean deleted) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
		this.accountExpired = accountExpired;
		this.accountLocked = accountLocked;
		this.lastLogin = lastLogin;
		this.credentialsExpired = credentialsExpired;
		this.imageId = imageId;
		this.displayName = displayName;
		this.smsNotification = smsNotification;
		this.theme = theme;
		this.locale = locale;
		this.timezone = timezone;
		this.emailPublic = emailPublic;
		this.addressPublic = addressPublic;
		this.telephonePublic = telephonePublic;
		this.portraitPublic = portraitPublic;
		this.imagePublic = imagePublic;
		this.profilePublic = profilePublic;
		this.portrait = portrait;
		this.matriculation = matriculation;
		this.studies = studies;
		this.smsEmail = smsEmail;
		this.profession = profession;
		this.city = city;
		this.country = country;
		this.telephone = telephone;
		this.postcode = postcode;
		this.ageGroup = ageGroup;
		this.address = address;
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.newsletterSubscriptionEnabled = newsletterSubscriptionEnabled;
		this.discussionSubscriptionEnabled = discussionSubscriptionEnabled;
		this.centralUser = centralUser;
		this.domainName = domainName;
		this.deleted = deleted;
	}

	/**
	 * Copies constructor from other UserInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public UserInfo(UserInfo otherBean) {
		this(otherBean.getId(), otherBean.getUsername(), otherBean.getPassword(), otherBean.getEmail(), otherBean
				.isEnabled(), otherBean.isAccountExpired(), otherBean.isAccountLocked(), otherBean.getLastLogin(),
				otherBean.isCredentialsExpired(), otherBean.getImageId(), otherBean.getDisplayName(), otherBean
						.isSmsNotification(), otherBean.getTheme(), otherBean.getLocale(), otherBean.getTimezone(),
				otherBean.isEmailPublic(), otherBean.isAddressPublic(), otherBean.isTelephonePublic(), otherBean
						.isPortraitPublic(), otherBean.isImagePublic(), otherBean.isProfilePublic(), otherBean
						.getPortrait(), otherBean.getMatriculation(), otherBean.getStudies(), otherBean.getSmsEmail(),
				otherBean.getProfession(), otherBean.getCity(), otherBean.getCountry(), otherBean.getTelephone(),
				otherBean.getPostcode(), otherBean.getAgeGroup(), otherBean.getAddress(), otherBean.getTitle(),
				otherBean.getFirstName(), otherBean.getLastName(), otherBean.isNewsletterSubscriptionEnabled(),
				otherBean.isDiscussionSubscriptionEnabled(), otherBean.isCentralUser(), otherBean.getDomainName(),
				otherBean.isDeleted());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(UserInfo otherBean) {
		this.setId(otherBean.getId());
		this.setUsername(otherBean.getUsername());
		this.setPassword(otherBean.getPassword());
		this.setEmail(otherBean.getEmail());
		this.setEnabled(otherBean.isEnabled());
		this.setAccountExpired(otherBean.isAccountExpired());
		this.setAccountLocked(otherBean.isAccountLocked());
		this.setLastLogin(otherBean.getLastLogin());
		this.setCredentialsExpired(otherBean.isCredentialsExpired());
		this.setImageId(otherBean.getImageId());
		this.setDisplayName(otherBean.getDisplayName());
		this.setSmsNotification(otherBean.isSmsNotification());
		this.setTheme(otherBean.getTheme());
		this.setLocale(otherBean.getLocale());
		this.setTimezone(otherBean.getTimezone());
		this.setEmailPublic(otherBean.isEmailPublic());
		this.setAddressPublic(otherBean.isAddressPublic());
		this.setTelephonePublic(otherBean.isTelephonePublic());
		this.setPortraitPublic(otherBean.isPortraitPublic());
		this.setImagePublic(otherBean.isImagePublic());
		this.setProfilePublic(otherBean.isProfilePublic());
		this.setPortrait(otherBean.getPortrait());
		this.setMatriculation(otherBean.getMatriculation());
		this.setStudies(otherBean.getStudies());
		this.setSmsEmail(otherBean.getSmsEmail());
		this.setProfession(otherBean.getProfession());
		this.setCity(otherBean.getCity());
		this.setCountry(otherBean.getCountry());
		this.setTelephone(otherBean.getTelephone());
		this.setPostcode(otherBean.getPostcode());
		this.setAgeGroup(otherBean.getAgeGroup());
		this.setAddress(otherBean.getAddress());
		this.setTitle(otherBean.getTitle());
		this.setFirstName(otherBean.getFirstName());
		this.setLastName(otherBean.getLastName());
		this.setNewsletterSubscriptionEnabled(otherBean.isNewsletterSubscriptionEnabled());
		this.setDiscussionSubscriptionEnabled(otherBean.isDiscussionSubscriptionEnabled());
		this.setCentralUser(otherBean.isCentralUser());
		this.setDomainName(otherBean.getDomainName());
		this.setDeleted(otherBean.isDeleted());
	}

	private Long id;

	/**
     * 
     */
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String username;

	/**
     * 
     */
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private String password;

	/**
     * 
     */
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String email;

	/**
     * 
     */
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private boolean enabled;

	/**
     * 
     */
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	private boolean accountExpired;

	/**
     * 
     */
	public boolean isAccountExpired() {
		return this.accountExpired;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	private boolean accountLocked;

	/**
     * 
     */
	public boolean isAccountLocked() {
		return this.accountLocked;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	private Date lastLogin;

	/**
     * 
     */
	public Date getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	private boolean credentialsExpired;

	/**
     * 
     */
	public boolean isCredentialsExpired() {
		return this.credentialsExpired;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	private Long imageId;

	/**
     * 
     */
	public Long getImageId() {
		return this.imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	private String displayName;

	/**
     * 
     */
	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	private boolean smsNotification;

	/**
     * 
     */
	public boolean isSmsNotification() {
		return this.smsNotification;
	}

	public void setSmsNotification(boolean smsNotification) {
		this.smsNotification = smsNotification;
	}

	private String theme;

	/**
     * 
     */
	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	private String locale;

	/**
     * 
     */
	public String getLocale() {
		return this.locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	private String timezone;

	/**
     * 
     */
	public String getTimezone() {
		return this.timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	private boolean emailPublic = false;

	/**
     * 
     */
	public boolean isEmailPublic() {
		return this.emailPublic;
	}

	public void setEmailPublic(boolean emailPublic) {
		this.emailPublic = emailPublic;
	}

	private boolean addressPublic = false;

	/**
     * 
     */
	public boolean isAddressPublic() {
		return this.addressPublic;
	}

	public void setAddressPublic(boolean addressPublic) {
		this.addressPublic = addressPublic;
	}

	private boolean telephonePublic = false;

	/**
     * 
     */
	public boolean isTelephonePublic() {
		return this.telephonePublic;
	}

	public void setTelephonePublic(boolean telephonePublic) {
		this.telephonePublic = telephonePublic;
	}

	private boolean portraitPublic = false;

	/**
     * 
     */
	public boolean isPortraitPublic() {
		return this.portraitPublic;
	}

	public void setPortraitPublic(boolean portraitPublic) {
		this.portraitPublic = portraitPublic;
	}

	private boolean imagePublic = false;

	/**
     * 
     */
	public boolean isImagePublic() {
		return this.imagePublic;
	}

	public void setImagePublic(boolean imagePublic) {
		this.imagePublic = imagePublic;
	}

	private boolean profilePublic = false;

	/**
     * 
     */
	public boolean isProfilePublic() {
		return this.profilePublic;
	}

	public void setProfilePublic(boolean profilePublic) {
		this.profilePublic = profilePublic;
	}

	private String portrait;

	/**
     * 
     */
	public String getPortrait() {
		return this.portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	private String matriculation;

	/**
     * 
     */
	public String getMatriculation() {
		return this.matriculation;
	}

	public void setMatriculation(String matriculation) {
		this.matriculation = matriculation;
	}

	private String studies;

	/**
     * 
     */
	public String getStudies() {
		return this.studies;
	}

	public void setStudies(String studies) {
		this.studies = studies;
	}

	private String smsEmail;

	/**
     * 
     */
	public String getSmsEmail() {
		return this.smsEmail;
	}

	public void setSmsEmail(String smsEmail) {
		this.smsEmail = smsEmail;
	}

	private String profession;

	/**
     * 
     */
	public String getProfession() {
		return this.profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	private String city;

	/**
     * 
     */
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	private String country;

	/**
     * 
     */
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	private String telephone;

	/**
     * 
     */
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	private String postcode;

	/**
     * 
     */
	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	private String ageGroup;

	/**
     * 
     */
	public String getAgeGroup() {
		return this.ageGroup;
	}

	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}

	private String address;

	/**
     * 
     */
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private String title;

	/**
     * 
     */
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private String firstName;

	/**
     * 
     */
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	private String lastName;

	/**
     * 
     */
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	private boolean newsletterSubscriptionEnabled;

	/**
	 * <p>
	 * Enable newsletter auto subscription if subscribing a course.
	 * </p>
	 */
	public boolean isNewsletterSubscriptionEnabled() {
		return this.newsletterSubscriptionEnabled;
	}

	public void setNewsletterSubscriptionEnabled(boolean newsletterSubscriptionEnabled) {
		this.newsletterSubscriptionEnabled = newsletterSubscriptionEnabled;
	}

	private boolean discussionSubscriptionEnabled;

	/**
	 * <p>
	 * Enable discussion auto subscription if subscribing a course.
	 * </p>
	 */
	public boolean isDiscussionSubscriptionEnabled() {
		return this.discussionSubscriptionEnabled;
	}

	public void setDiscussionSubscriptionEnabled(boolean discussionSubscriptionEnabled) {
		this.discussionSubscriptionEnabled = discussionSubscriptionEnabled;
	}

	private boolean centralUser;

	/**
     * 
     */
	public boolean isCentralUser() {
		return this.centralUser;
	}

	public void setCentralUser(boolean centralUser) {
		this.centralUser = centralUser;
	}

	private String domainName;

	/**
     * 
     */
	public String getDomainName() {
		return this.domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	private boolean deleted;

	/**
     * 
     */
	public boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * Returns <code>true</code> if the argument is an UserInfo instance and all
	 * identifiers for this object equal the identifiers of the argument object.
	 * Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof UserInfo)) {
			return false;
		}
		final UserInfo that = (UserInfo) object;
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