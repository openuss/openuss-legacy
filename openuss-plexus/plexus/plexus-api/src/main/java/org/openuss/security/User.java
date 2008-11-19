package org.openuss.security;

import java.util.Date;
import java.util.List;

/**
 * 
 */
public interface User extends org.openuss.security.Authority, org.openuss.foundation.DomainObject {

	/**
     * 
     */
	public String getEmail();

	public void setEmail(String email);

	/**
     * 
     */
	public String getPassword();

	public void setPassword(String password);

	/**
     * 
     */
	public String getLastName();

	public void setLastName(String lastName);

	/**
     * 
     */
	public String getFirstName();

	public void setFirstName(String firstName);

	/**
     * 
     */
	public String getTitle();

	public void setTitle(String title);

	/**
     * 
     */
	public Long getImageId();

	public void setImageId(Long imageId);

	/**
     * 
     */
	public String getLocale();

	public void setLocale(String locale);

	/**
     * 
     */
	public String getTheme();

	public void setTheme(String theme);

	/**
     * 
     */
	public String getTimezone();

	public void setTimezone(String timezone);

	/**
     * 
     */
	public String getAddress();

	public void setAddress(String address);

	/**
     * 
     */
	public String getAgeGroup();

	public void setAgeGroup(String ageGroup);

	/**
     * 
     */
	public String getPostcode();

	public void setPostcode(String postcode);

	/**
     * 
     */
	public String getTelephone();

	public void setTelephone(String telephone);

	/**
     * 
     */
	public String getCountry();

	public void setCountry(String country);

	/**
     * 
     */
	public String getCity();

	public void setCity(String city);

	/**
     * 
     */
	public String getProfession();

	public void setProfession(String profession);

	/**
     * 
     */
	public String getSmsEmail();

	public void setSmsEmail(String smsEmail);

	/**
     * 
     */
	public Date getLastLogin();

	public void setLastLogin(Date lastLogin);

	/**
     * 
     */
	public boolean isCredentialsExpired();

	public void setCredentialsExpired(boolean credentialsExpired);

	/**
     * 
     */
	public boolean isAccountLocked();

	public void setAccountLocked(boolean accountLocked);

	/**
     * 
     */
	public boolean isAccountExpired();

	public void setAccountExpired(boolean accountExpired);

	/**
     * 
     */
	public boolean isEnabled();

	public void setEnabled(boolean enabled);

	/**
     * 
     */
	public String getStudies();

	public void setStudies(String studies);

	/**
     * 
     */
	public String getMatriculation();

	public void setMatriculation(String matriculation);

	/**
     * 
     */
	public String getPortrait();

	public void setPortrait(String portrait);

	/**
     * 
     */
	public boolean isProfilePublic();

	public void setProfilePublic(boolean profilePublic);

	/**
     * 
     */
	public boolean isImagePublic();

	public void setImagePublic(boolean imagePublic);

	/**
     * 
     */
	public boolean isPortraitPublic();

	public void setPortraitPublic(boolean portraitPublic);

	/**
     * 
     */
	public boolean isTelephonePublic();

	public void setTelephonePublic(boolean telephonePublic);

	/**
     * 
     */
	public boolean isAddressPublic();

	public void setAddressPublic(boolean addressPublic);

	/**
     * 
     */
	public boolean isEmailPublic();

	public void setEmailPublic(boolean emailPublic);

	/**
	 * <p>
	 * Enable discussion auto subscription if subscribing a course.
	 * </p>
	 */
	public boolean isDiscussionSubscriptionEnabled();

	public void setDiscussionSubscriptionEnabled(boolean discussionSubscriptionEnabled);

	/**
	 * <p>
	 * Enable newsletter auto subscription if subscribing a course.
	 * </p>
	 */
	public boolean isNewsletterSubscriptionEnabled();

	public void setNewsletterSubscriptionEnabled(boolean newsletterSubscriptionEnabled);

	/**
     * 
     */
	public boolean isDeleted();

	public void setDeleted(boolean deleted);

	/**
     * 
     */
	public abstract String getDisplayName();

	/**
     * 
     */
	public abstract boolean hasSmsNotification();

	/**
     * 
     */
	public abstract String getUsername();

	/**
     * 
     */
	public abstract void setUsername(String username);

	/**
     * 
     */
	public abstract boolean isCentralUser();

	/**
	 * Constructs new instances of {@link org.openuss.security.User}.
	 */
	public static abstract class Factory {

		/**
		 * Singleton instance of the concrete factory
		 */
		private static User.Factory factory = null;

		/**
		 * Singleton method to obtain an instance of the concrete factory
		 */
		private static User.Factory getFactory() {
			if (factory == null) {
				factory = (User.Factory) org.openuss.api.utilities.FactoryFinder
						.find("org.openuss.security.User.Factory");
			}
			return factory;
		}

		/**
		 * Abstract factory method for the concrete product.
		 */
		public abstract User createUser();

		/**
		 * Constructs a new instance of {@link org.openuss.security.User}.
		 */
		public static org.openuss.security.User newInstance() {
			return getFactory().createUser();
		}

		/**
		 * Constructs a new instance of {@link org.openuss.security.User},
		 * taking all required and/or read-only properties as arguments.
		 */
		public static org.openuss.security.User newInstanceByIdentifier(Long id) {
			final org.openuss.security.User entity = getFactory().createUser();
			entity.setId(id);
			return entity;
		}

		/**
		 * Constructs a new instance of {@link org.openuss.security.User},
		 * taking all required and/or read-only properties as arguments.
		 */
		public static org.openuss.security.User newInstance(String email, String password, String lastName,
				String firstName, String locale, String theme, String timezone, boolean credentialsExpired,
				boolean accountLocked, boolean accountExpired, boolean enabled, String portrait, boolean profilePublic,
				boolean imagePublic, boolean portraitPublic, boolean telephonePublic, boolean addressPublic,
				boolean emailPublic, boolean discussionSubscriptionEnabled, boolean newsletterSubscriptionEnabled,
				boolean deleted, String name) {
			final org.openuss.security.User entity = getFactory().createUser();
			entity.setEmail(email);
			entity.setPassword(password);
			entity.setLastName(lastName);
			entity.setFirstName(firstName);
			entity.setLocale(locale);
			entity.setTheme(theme);
			entity.setTimezone(timezone);
			entity.setCredentialsExpired(credentialsExpired);
			entity.setAccountLocked(accountLocked);
			entity.setAccountExpired(accountExpired);
			entity.setEnabled(enabled);
			entity.setPortrait(portrait);
			entity.setProfilePublic(profilePublic);
			entity.setImagePublic(imagePublic);
			entity.setPortraitPublic(portraitPublic);
			entity.setTelephonePublic(telephonePublic);
			entity.setAddressPublic(addressPublic);
			entity.setEmailPublic(emailPublic);
			entity.setDiscussionSubscriptionEnabled(discussionSubscriptionEnabled);
			entity.setNewsletterSubscriptionEnabled(newsletterSubscriptionEnabled);
			entity.setDeleted(deleted);
			entity.setName(name);
			return entity;
		}

		/**
		 * Constructs a new instance of {@link org.openuss.security.User},
		 * taking all possible properties (except the identifier(s))as
		 * arguments.
		 */
		public static org.openuss.security.User newInstance(String email, String password, String lastName,
				String firstName, String title, Long imageId, String locale, String theme, String timezone,
				String address, String ageGroup, String postcode, String telephone, String country, String city,
				String profession, String smsEmail, Date lastLogin, boolean credentialsExpired, boolean accountLocked,
				boolean accountExpired, boolean enabled, String studies, String matriculation, String portrait,
				boolean profilePublic, boolean imagePublic, boolean portraitPublic, boolean telephonePublic,
				boolean addressPublic, boolean emailPublic, boolean discussionSubscriptionEnabled,
				boolean newsletterSubscriptionEnabled, boolean deleted, String name,
				List<org.openuss.security.Group> groups) {
			final org.openuss.security.User entity = getFactory().createUser();
			entity.setEmail(email);
			entity.setPassword(password);
			entity.setLastName(lastName);
			entity.setFirstName(firstName);
			entity.setTitle(title);
			entity.setImageId(imageId);
			entity.setLocale(locale);
			entity.setTheme(theme);
			entity.setTimezone(timezone);
			entity.setAddress(address);
			entity.setAgeGroup(ageGroup);
			entity.setPostcode(postcode);
			entity.setTelephone(telephone);
			entity.setCountry(country);
			entity.setCity(city);
			entity.setProfession(profession);
			entity.setSmsEmail(smsEmail);
			entity.setLastLogin(lastLogin);
			entity.setCredentialsExpired(credentialsExpired);
			entity.setAccountLocked(accountLocked);
			entity.setAccountExpired(accountExpired);
			entity.setEnabled(enabled);
			entity.setStudies(studies);
			entity.setMatriculation(matriculation);
			entity.setPortrait(portrait);
			entity.setProfilePublic(profilePublic);
			entity.setImagePublic(imagePublic);
			entity.setPortraitPublic(portraitPublic);
			entity.setTelephonePublic(telephonePublic);
			entity.setAddressPublic(addressPublic);
			entity.setEmailPublic(emailPublic);
			entity.setDiscussionSubscriptionEnabled(discussionSubscriptionEnabled);
			entity.setNewsletterSubscriptionEnabled(newsletterSubscriptionEnabled);
			entity.setDeleted(deleted);
			entity.setName(name);
			entity.setGroups(groups);
			return entity;
		}
	}

	// Interface HibernateEntity.vsl merge-point
}