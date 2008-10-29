package org.openuss.security;

/**
 * 
 */
public abstract class UserBase
    extends org.openuss.security.AuthorityImpl
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -6944541966260510310L;

    private java.lang.String email;

    /**
     * @see org.openuss.security.User#getEmail()
     */
    public java.lang.String getEmail()
    {
        return this.email;
    }

    /**
     * @see org.openuss.security.User#setEmail(java.lang.String email)
     */
    public void setEmail(java.lang.String email)
    {
        this.email = email;
    }

    private java.lang.String password;

    /**
     * @see org.openuss.security.User#getPassword()
     */
    public java.lang.String getPassword()
    {
        return this.password;
    }

    /**
     * @see org.openuss.security.User#setPassword(java.lang.String password)
     */
    public void setPassword(java.lang.String password)
    {
        this.password = password;
    }

    private java.lang.String lastName;

    /**
     * @see org.openuss.security.User#getLastName()
     */
    public java.lang.String getLastName()
    {
        return this.lastName;
    }

    /**
     * @see org.openuss.security.User#setLastName(java.lang.String lastName)
     */
    public void setLastName(java.lang.String lastName)
    {
        this.lastName = lastName;
    }

    private java.lang.String firstName;

    /**
     * @see org.openuss.security.User#getFirstName()
     */
    public java.lang.String getFirstName()
    {
        return this.firstName;
    }

    /**
     * @see org.openuss.security.User#setFirstName(java.lang.String firstName)
     */
    public void setFirstName(java.lang.String firstName)
    {
        this.firstName = firstName;
    }

    private java.lang.String title;

    /**
     * @see org.openuss.security.User#getTitle()
     */
    public java.lang.String getTitle()
    {
        return this.title;
    }

    /**
     * @see org.openuss.security.User#setTitle(java.lang.String title)
     */
    public void setTitle(java.lang.String title)
    {
        this.title = title;
    }

    private java.lang.Long imageId;

    /**
     * @see org.openuss.security.User#getImageId()
     */
    public java.lang.Long getImageId()
    {
        return this.imageId;
    }

    /**
     * @see org.openuss.security.User#setImageId(java.lang.Long imageId)
     */
    public void setImageId(java.lang.Long imageId)
    {
        this.imageId = imageId;
    }

    private java.lang.String locale;

    /**
     * @see org.openuss.security.User#getLocale()
     */
    public java.lang.String getLocale()
    {
        return this.locale;
    }

    /**
     * @see org.openuss.security.User#setLocale(java.lang.String locale)
     */
    public void setLocale(java.lang.String locale)
    {
        this.locale = locale;
    }

    private java.lang.String theme;

    /**
     * @see org.openuss.security.User#getTheme()
     */
    public java.lang.String getTheme()
    {
        return this.theme;
    }

    /**
     * @see org.openuss.security.User#setTheme(java.lang.String theme)
     */
    public void setTheme(java.lang.String theme)
    {
        this.theme = theme;
    }

    private java.lang.String timezone;

    /**
     * @see org.openuss.security.User#getTimezone()
     */
    public java.lang.String getTimezone()
    {
        return this.timezone;
    }

    /**
     * @see org.openuss.security.User#setTimezone(java.lang.String timezone)
     */
    public void setTimezone(java.lang.String timezone)
    {
        this.timezone = timezone;
    }

    private java.lang.String address;

    /**
     * @see org.openuss.security.User#getAddress()
     */
    public java.lang.String getAddress()
    {
        return this.address;
    }

    /**
     * @see org.openuss.security.User#setAddress(java.lang.String address)
     */
    public void setAddress(java.lang.String address)
    {
        this.address = address;
    }

    private java.lang.String ageGroup;

    /**
     * @see org.openuss.security.User#getAgeGroup()
     */
    public java.lang.String getAgeGroup()
    {
        return this.ageGroup;
    }

    /**
     * @see org.openuss.security.User#setAgeGroup(java.lang.String ageGroup)
     */
    public void setAgeGroup(java.lang.String ageGroup)
    {
        this.ageGroup = ageGroup;
    }

    private java.lang.String postcode;

    /**
     * @see org.openuss.security.User#getPostcode()
     */
    public java.lang.String getPostcode()
    {
        return this.postcode;
    }

    /**
     * @see org.openuss.security.User#setPostcode(java.lang.String postcode)
     */
    public void setPostcode(java.lang.String postcode)
    {
        this.postcode = postcode;
    }

    private java.lang.String telephone;

    /**
     * @see org.openuss.security.User#getTelephone()
     */
    public java.lang.String getTelephone()
    {
        return this.telephone;
    }

    /**
     * @see org.openuss.security.User#setTelephone(java.lang.String telephone)
     */
    public void setTelephone(java.lang.String telephone)
    {
        this.telephone = telephone;
    }

    private java.lang.String country;

    /**
     * @see org.openuss.security.User#getCountry()
     */
    public java.lang.String getCountry()
    {
        return this.country;
    }

    /**
     * @see org.openuss.security.User#setCountry(java.lang.String country)
     */
    public void setCountry(java.lang.String country)
    {
        this.country = country;
    }

    private java.lang.String city;

    /**
     * @see org.openuss.security.User#getCity()
     */
    public java.lang.String getCity()
    {
        return this.city;
    }

    /**
     * @see org.openuss.security.User#setCity(java.lang.String city)
     */
    public void setCity(java.lang.String city)
    {
        this.city = city;
    }

    private java.lang.String profession;

    /**
     * @see org.openuss.security.User#getProfession()
     */
    public java.lang.String getProfession()
    {
        return this.profession;
    }

    /**
     * @see org.openuss.security.User#setProfession(java.lang.String profession)
     */
    public void setProfession(java.lang.String profession)
    {
        this.profession = profession;
    }

    private java.lang.String smsEmail;

    /**
     * @see org.openuss.security.User#getSmsEmail()
     */
    public java.lang.String getSmsEmail()
    {
        return this.smsEmail;
    }

    /**
     * @see org.openuss.security.User#setSmsEmail(java.lang.String smsEmail)
     */
    public void setSmsEmail(java.lang.String smsEmail)
    {
        this.smsEmail = smsEmail;
    }

    private java.util.Date lastLogin;

    /**
     * @see org.openuss.security.User#getLastLogin()
     */
    public java.util.Date getLastLogin()
    {
        return this.lastLogin;
    }

    /**
     * @see org.openuss.security.User#setLastLogin(java.util.Date lastLogin)
     */
    public void setLastLogin(java.util.Date lastLogin)
    {
        this.lastLogin = lastLogin;
    }

    private boolean credentialsExpired;

    /**
     * @see org.openuss.security.User#isCredentialsExpired()
     */
    public boolean isCredentialsExpired()
    {
        return this.credentialsExpired;
    }

    /**
     * @see org.openuss.security.User#setCredentialsExpired(boolean credentialsExpired)
     */
    public void setCredentialsExpired(boolean credentialsExpired)
    {
        this.credentialsExpired = credentialsExpired;
    }

    private boolean accountLocked;

    /**
     * @see org.openuss.security.User#isAccountLocked()
     */
    public boolean isAccountLocked()
    {
        return this.accountLocked;
    }

    /**
     * @see org.openuss.security.User#setAccountLocked(boolean accountLocked)
     */
    public void setAccountLocked(boolean accountLocked)
    {
        this.accountLocked = accountLocked;
    }

    private boolean accountExpired;

    /**
     * @see org.openuss.security.User#isAccountExpired()
     */
    public boolean isAccountExpired()
    {
        return this.accountExpired;
    }

    /**
     * @see org.openuss.security.User#setAccountExpired(boolean accountExpired)
     */
    public void setAccountExpired(boolean accountExpired)
    {
        this.accountExpired = accountExpired;
    }

    private boolean enabled;

    /**
     * @see org.openuss.security.User#isEnabled()
     */
    public boolean isEnabled()
    {
        return this.enabled;
    }

    /**
     * @see org.openuss.security.User#setEnabled(boolean enabled)
     */
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    private java.lang.String studies;

    /**
     * @see org.openuss.security.User#getStudies()
     */
    public java.lang.String getStudies()
    {
        return this.studies;
    }

    /**
     * @see org.openuss.security.User#setStudies(java.lang.String studies)
     */
    public void setStudies(java.lang.String studies)
    {
        this.studies = studies;
    }

    private java.lang.String matriculation;

    /**
     * @see org.openuss.security.User#getMatriculation()
     */
    public java.lang.String getMatriculation()
    {
        return this.matriculation;
    }

    /**
     * @see org.openuss.security.User#setMatriculation(java.lang.String matriculation)
     */
    public void setMatriculation(java.lang.String matriculation)
    {
        this.matriculation = matriculation;
    }

    private java.lang.String portrait;

    /**
     * @see org.openuss.security.User#getPortrait()
     */
    public java.lang.String getPortrait()
    {
        return this.portrait;
    }

    /**
     * @see org.openuss.security.User#setPortrait(java.lang.String portrait)
     */
    public void setPortrait(java.lang.String portrait)
    {
        this.portrait = portrait;
    }

    private boolean profilePublic = false;

    /**
     * @see org.openuss.security.User#isProfilePublic()
     */
    public boolean isProfilePublic()
    {
        return this.profilePublic;
    }

    /**
     * @see org.openuss.security.User#setProfilePublic(boolean profilePublic)
     */
    public void setProfilePublic(boolean profilePublic)
    {
        this.profilePublic = profilePublic;
    }

    private boolean imagePublic = false;

    /**
     * @see org.openuss.security.User#isImagePublic()
     */
    public boolean isImagePublic()
    {
        return this.imagePublic;
    }

    /**
     * @see org.openuss.security.User#setImagePublic(boolean imagePublic)
     */
    public void setImagePublic(boolean imagePublic)
    {
        this.imagePublic = imagePublic;
    }

    private boolean portraitPublic = false;

    /**
     * @see org.openuss.security.User#isPortraitPublic()
     */
    public boolean isPortraitPublic()
    {
        return this.portraitPublic;
    }

    /**
     * @see org.openuss.security.User#setPortraitPublic(boolean portraitPublic)
     */
    public void setPortraitPublic(boolean portraitPublic)
    {
        this.portraitPublic = portraitPublic;
    }

    private boolean telephonePublic = false;

    /**
     * @see org.openuss.security.User#isTelephonePublic()
     */
    public boolean isTelephonePublic()
    {
        return this.telephonePublic;
    }

    /**
     * @see org.openuss.security.User#setTelephonePublic(boolean telephonePublic)
     */
    public void setTelephonePublic(boolean telephonePublic)
    {
        this.telephonePublic = telephonePublic;
    }

    private boolean addressPublic = false;

    /**
     * @see org.openuss.security.User#isAddressPublic()
     */
    public boolean isAddressPublic()
    {
        return this.addressPublic;
    }

    /**
     * @see org.openuss.security.User#setAddressPublic(boolean addressPublic)
     */
    public void setAddressPublic(boolean addressPublic)
    {
        this.addressPublic = addressPublic;
    }

    private boolean emailPublic = false;

    /**
     * @see org.openuss.security.User#isEmailPublic()
     */
    public boolean isEmailPublic()
    {
        return this.emailPublic;
    }

    /**
     * @see org.openuss.security.User#setEmailPublic(boolean emailPublic)
     */
    public void setEmailPublic(boolean emailPublic)
    {
        this.emailPublic = emailPublic;
    }

    private boolean discussionSubscriptionEnabled = false;

    /**
     * @see org.openuss.security.User#isDiscussionSubscriptionEnabled()
     */
    public boolean isDiscussionSubscriptionEnabled()
    {
        return this.discussionSubscriptionEnabled;
    }

    /**
     * @see org.openuss.security.User#setDiscussionSubscriptionEnabled(boolean discussionSubscriptionEnabled)
     */
    public void setDiscussionSubscriptionEnabled(boolean discussionSubscriptionEnabled)
    {
        this.discussionSubscriptionEnabled = discussionSubscriptionEnabled;
    }

    private boolean newsletterSubscriptionEnabled = false;

    /**
     * @see org.openuss.security.User#isNewsletterSubscriptionEnabled()
     */
    public boolean isNewsletterSubscriptionEnabled()
    {
        return this.newsletterSubscriptionEnabled;
    }

    /**
     * @see org.openuss.security.User#setNewsletterSubscriptionEnabled(boolean newsletterSubscriptionEnabled)
     */
    public void setNewsletterSubscriptionEnabled(boolean newsletterSubscriptionEnabled)
    {
        this.newsletterSubscriptionEnabled = newsletterSubscriptionEnabled;
    }

    private boolean deleted = false;

    /**
     * @see org.openuss.security.User#isDeleted()
     */
    public boolean isDeleted()
    {
        return this.deleted;
    }

    /**
     * @see org.openuss.security.User#setDeleted(boolean deleted)
     */
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }

    /**
     * 
     */
    public abstract java.lang.String getDisplayName();

    /**
     * 
     */
    public abstract boolean hasSmsNotification();

    /**
     * 
     */
    public abstract java.lang.String getUsername();

    /**
     * 
     */
    public abstract void setUsername(java.lang.String username);

    /**
     * 
     */
    public abstract boolean isCentralUser();

    /**
     * This entity does not have any identifiers
     * but since it extends the <code>org.openuss.security.AuthorityImpl</code> class
     * it will simply delegate the call up there.
     *
     * @see org.openuss.security.Authority#equals(Object)
     */
    public boolean equals(Object object)
    {
        return super.equals(object);
    }

    /**
     * This entity does not have any identifiers
     * but since it extends the <code>org.openuss.security.AuthorityImpl</code> class
     * it will simply delegate the call up there.
     *
     * @see org.openuss.security.Authority#hashCode()
     */
    public int hashCode()
    {
        return super.hashCode();
    }

	public static class Factory extends User.Factory {
		
		public User createUser() {
			return new UserImpl();
		}
	}
	
// HibernateEntity.vsl merge-point
}