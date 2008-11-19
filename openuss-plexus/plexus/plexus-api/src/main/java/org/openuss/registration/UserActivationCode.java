package org.openuss.registration;

/**
 * <p>
 * The RegistrationKey is generated during the registration process of a new
 * user. To verify the user email adress. After the registration forms were
 * completed a registration key is generated and send to the users email adress.
 * Within the email is a link to submit the generated registration key. After
 * the user has submitted the key the user account is activated.
 * </p>
 */
public interface UserActivationCode extends org.openuss.registration.ActivationCode,
		org.openuss.foundation.DomainObject {

	/**
     * 
     */
	public String getCode();

	public void setCode(String code);

	/**
	 * Modified
	 * 
	 */
	public org.openuss.security.User getUser();

	public void setUser(org.openuss.security.User user);

	/**
	 * Constructs new instances of
	 * {@link org.openuss.registration.UserActivationCode}.
	 */
	public static abstract class Factory {

		/**
		 * Singleton instance of the concrete factory
		 */
		private static UserActivationCode.Factory factory = null;

		/**
		 * Singleton method to obtain an instance of the concrete factory
		 */
		private static UserActivationCode.Factory getFactory() {
			if (factory == null) {
				factory = (UserActivationCode.Factory) org.openuss.api.utilities.FactoryFinder
						.find("org.openuss.registration.UserActivationCode.Factory");
			}
			return factory;
		}

		/**
		 * Abstract factory method for the concrete product.
		 */
		public abstract UserActivationCode createUserActivationCode();

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.registration.UserActivationCode}.
		 */
		public static org.openuss.registration.UserActivationCode newInstance() {
			return getFactory().createUserActivationCode();
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.registration.UserActivationCode}, taking all
		 * required and/or read-only properties as arguments.
		 */
		public static org.openuss.registration.UserActivationCode newInstanceByIdentifier(Long id) {
			final org.openuss.registration.UserActivationCode entity = getFactory().createUserActivationCode();
			entity.setId(id);
			return entity;
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.registration.UserActivationCode}, taking all
		 * possible properties (except the identifier(s))as arguments.
		 */
		public static org.openuss.registration.UserActivationCode newInstance(String code,
				java.sql.Timestamp createdAt, org.openuss.security.User user) {
			final org.openuss.registration.UserActivationCode entity = getFactory().createUserActivationCode();
			entity.setCode(code);
			entity.setCreatedAt(createdAt);
			entity.setUser(user);
			return entity;
		}
	}

	// Interface HibernateEntity.vsl merge-point
}